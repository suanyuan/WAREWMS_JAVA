package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.ActAllocationDetailsQuery;
import com.wms.query.DocPkRecordsQuery;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.query.pda.PdaDocPkQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.form.pda.ScanResultForm;
import com.wms.vo.pda.CommonVO;
import com.wms.vo.pda.PdaActAllocationDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service("docPkRecordsService")
public class DocPkRecordsService extends BaseService {

	@Autowired
	private DocPkRecordsMybatisDao docPkRecordsMybatisDao;
	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
	@Autowired
	private CommonService commonService;
	@Autowired
	private BasPackageMybatisDao basPackageMybatisDao;
	@Autowired
	private ActAllocationDetailsMybatisDao actAllocationDetailsMybatisDao;
	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	public List<OrderHeaderForNormalVO> getUndoneList(PageForm form) {

		List<OrderHeaderForNormal> orderHeaderForNormals = orderHeaderForNormalMybatisDao.queryPkList(form.getStart(), form.getPageSize());
		OrderHeaderForNormalVO orderHeaderForNormalVO;
		List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = new ArrayList<>();
		for (OrderHeaderForNormal orderHeaderForNormal : orderHeaderForNormals) {

			orderHeaderForNormalVO = new OrderHeaderForNormalVO();
			BeanUtils.copyProperties(orderHeaderForNormal, orderHeaderForNormalVO);
			orderHeaderForNormalVOS.add(orderHeaderForNormalVO);
		}

		return orderHeaderForNormalVOS;
	}

	/**
	 * 获取出库单中需要拣货的列表
	 */
	public List<PdaActAllocationDetailVO> pickingLocations(PageForm form, String orderno) {

		List<PdaActAllocationDetailVO> voList = new ArrayList<>();
		PdaActAllocationDetailVO detailsVO;

		ActAllocationDetailsQuery allocationDetailsQuery = new ActAllocationDetailsQuery();
		allocationDetailsQuery.setOrderno(orderno);

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(form.getPageNum());
		mybatisCriteria.setPageSize(form.getPageSize());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(allocationDetailsQuery));
		mybatisCriteria.setOrderByClause("printflag asc, location asc");

		List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsMybatisDao.queryByList(mybatisCriteria);
		for (ActAllocationDetails details : actAllocationDetailsList) {

			detailsVO = new PdaActAllocationDetailVO();
			BeanUtils.copyProperties(details, detailsVO);

			InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(details.getLotnum());
			detailsVO.setInvLotAtt(invLotAtt);

			DocPkRecordsQuery docPkRecordsQuery = new DocPkRecordsQuery(details.getOrderno(), details.getAllocationdetailsid());
			DocPkRecords docPkRecords = docPkRecordsMybatisDao.queryPickedRecord(docPkRecordsQuery);
			detailsVO.setDocPkRecords(docPkRecords);

			voList.add(
					JSONObject.parseObject(
							JSON.toJSONString(detailsVO, SerializerFeature.DisableCircularReferenceDetect),
							PdaActAllocationDetailVO.class
					)
			);
		}
		return voList;
	}

	/**
	 * 当前模块出库单操作订单状态判断
	 */
	private Json orderStatusCheck(String orderNo) {
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		return orderObjCheck(orderHeaderForNormal);
	}

	//Check Order Status
	private Json orderObjCheck(OrderHeaderForNormal orderHeaderForNormal) {

		if(orderHeaderForNormal != null){

			if (orderHeaderForNormal.getReleasestatus().equals("H")) {
				return Json.error("当前订单已冻结");
			} else if (orderHeaderForNormal.getReleasestatus().equals("N")) {
				return Json.error("当前订单未释放");
			}

			switch (orderHeaderForNormal.getSostatus()) {
				case Constant.CODE_SO_STS_PART_ALLOCATED:
				case Constant.CODE_SO_STS_ALLOCATED:
				case Constant.CODE_SO_STS_PART_PICKED:
					return Json.success("000");
				case Constant.CODE_SO_STS_PICKED:
					return Json.error("当前订单已完成拣货操作！");
				default:
					return Json.error("当前状态订单不允许进行拣货操作！");
			}
		} else {

			return Json.error("查无此出库单号！");
		}
	}


	/**
	 * 拣货提交，单次提交一件
	 */
	public Map<String, Object> singlePkCommit(PdaDocPkQuery query) {

        Map<String, Object> resultMap = new HashMap<>();
        PdaActAllocationDetailVO actAllocationDetailVO = new PdaActAllocationDetailVO();
		try {

			int pickQty = query.getQty();
			BasSku basSku;
			DocPkRecords docPkRecords;
			ActAllocationDetails actAllocationDetails;

			//订单状态校验
			Json statusJson = orderStatusCheck(query.getOrderno());
			if (!statusJson.isSuccess()) {
			    resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, statusJson.getMsg()));
				return resultMap;
			}

            /*
            1，处理BasSku获取问题，并且返回准确的批号、序列号匹配条件
             */
			ScanResultForm scanResultForm = new ScanResultForm();
			//customerid, GTIN, lotatt04, lotatt05, otherCode
			BeanUtils.copyProperties(query, scanResultForm);
			CommonVO commonVO = commonService.adjustScanResult(scanResultForm);

			if (commonVO.isSuccess()) query.setSku(commonVO.getBasSku().getSku());
			query.setLotatt04(commonVO.getBatchNum());
			query.setLotatt05(commonVO.isSerialManagement() ? "" : commonVO.getSerialNum());

            /*
             2,判断获取的拣货扫码数据是否齐全，获取分配明细 + 产品档案
             如果查询不到分配明细，要么就是扫错货了，要么就是拣货完成了
             */
			Json scanJson = commonService.matchPkDetails(query);
			if (!scanJson.isSuccess()) {
                resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, scanJson.getMsg()));
                return resultMap;
            }

			actAllocationDetails = (ActAllocationDetails) scanJson.getObj();
			BeanUtils.copyProperties(actAllocationDetails, actAllocationDetailVO);

			Json skuJson = commonService.fixBasSku(actAllocationDetails.getCustomerid(), actAllocationDetails.getSku());
			if (!skuJson.isSuccess()) {
                resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, skuJson.getMsg()));
                return resultMap;
            }

			basSku = (BasSku) skuJson.getObj();
			BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
			if (null == basPackage) {
                resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无此产品包装规格数据"));
                return resultMap;
            }

            /*
             3,获取拣货记录明细（可能存在未创建的情况）
             */
			DocPkRecordsQuery pkRecordsQuery = new DocPkRecordsQuery(actAllocationDetails.getOrderno(), actAllocationDetails.getAllocationdetailsid());
			docPkRecords = docPkRecordsMybatisDao.queryPickedRecord(pkRecordsQuery);
			if (null == docPkRecords) {

				if (pickQty > actAllocationDetails.getQty()) {
					resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "当前提交超出分配明细件数(" + actAllocationDetails.getQty() + ")"));
					return resultMap;
				}

				int maxPklineno = docPkRecordsMybatisDao.getMaxPklineno(actAllocationDetails.getOrderno());
				docPkRecords = new DocPkRecords(actAllocationDetails, basSku, maxPklineno, basPackage, pickQty);
				docPkRecords.setAddwho(query.getEditwho());
				docPkRecordsMybatisDao.add(docPkRecords);
			} else {

				if ((docPkRecords.getPickedqty() + pickQty) > actAllocationDetails.getQty()) {
					resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "当前提交超出分配明细件数(" + actAllocationDetails.getQty() + ")"));
					return resultMap;
				}

				docPkRecords.setPickedqty(docPkRecords.getPickedqty() + pickQty);
				docPkRecords.setPickedqtyEach(docPkRecords.getPickedqtyEach() + pickQty * basPackage.getQty1().intValue());
				docPkRecords.setEditwho(query.getEditwho());
				docPkRecordsMybatisDao.updatePickedQty(docPkRecords);
			}
			actAllocationDetailVO.setDocPkRecords(docPkRecords);

            /*
             4,判断当前分配明细是否拣货完成
             */
			if (docPkRecords.getPickedqty() == actAllocationDetails.getQty()) {

				//分配明细结束拣货
				ActAllocationDetailsQuery actAllocationDetailsQuery = new ActAllocationDetailsQuery();
				actAllocationDetailsQuery.setEditwho(query.getEditwho());
				actAllocationDetailsQuery.setAllocationdetailsid(actAllocationDetails.getAllocationdetailsid());
				actAllocationDetailsMybatisDao.finishPicking(actAllocationDetailsQuery);
			}

            /*
             5,判断出库单是否拣货完成
             */
            endPicking(query);
		} catch (Exception e) {

			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "系统错误，" + e.toString()));
            return resultMap;
		}

		InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(actAllocationDetailVO.getLotnum());
		actAllocationDetailVO.setInvLotAtt(invLotAtt);
		resultMap.put(Constant.DATA, actAllocationDetailVO);
        resultMap.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, "提交成功"));
        return resultMap;
	}

	/**
	 * 结束拣货
	 */
	private void endPicking(PdaDocPkQuery query) {

		ActAllocationDetailsQuery actAllocationDetailsQuery = new ActAllocationDetailsQuery();
		actAllocationDetailsQuery.setOrderno(query.getOrderno());
		actAllocationDetailsQuery.setPrintflag("0");
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(actAllocationDetailsQuery));

		List<ActAllocationDetails> undoneList = actAllocationDetailsMybatisDao.queryByList(mybatisCriteria);

		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(query.getOrderno());
		orderHeaderForNormal.setSostatus(undoneList.size() == 0 ? Constant.CODE_SO_STS_PICKED : Constant.CODE_SO_STS_PART_PICKED);
		orderHeaderForNormal.setEditwho(query.getEditwho());
		orderHeaderForNormal.setEdittime(new Date());
		orderHeaderForNormalMybatisDao.updateBySelective(orderHeaderForNormal);
	}
}