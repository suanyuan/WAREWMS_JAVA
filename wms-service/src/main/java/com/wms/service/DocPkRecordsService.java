package com.wms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wms.entity.*;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.BasSkuQuery;
import com.wms.result.PdaResult;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.pda.PageForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.DocPkRecordsDao;
import com.wms.vo.DocPkRecordsVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocPkRecordsForm;
import com.wms.query.DocPkRecordsQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("docPkRecordsService")
public class DocPkRecordsService extends BaseService {

	@Autowired
	private DocPkRecordsMybatisDao docPkRecordsMybatisDao;
	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private BasPackageMybatisDao basPackageMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	public EasyuiDatagrid<DocPkRecordsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocPkRecordsQuery query) {
		EasyuiDatagrid<DocPkRecordsVO> datagrid = new EasyuiDatagrid<DocPkRecordsVO>();

		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());

		List<DocPkRecords> docPkRecordsList = docPkRecordsMybatisDao.queryByList(criteria);
		DocPkRecordsVO docPkRecordsVO = null;
		List<DocPkRecordsVO> docPkRecordsVOList = new ArrayList<DocPkRecordsVO>();
		for (DocPkRecords docPkRecords : docPkRecordsList) {
			docPkRecordsVO = new DocPkRecordsVO();
			BeanUtils.copyProperties(docPkRecords, docPkRecordsVO);
			docPkRecordsVOList.add(docPkRecordsVO);
		}
		datagrid.setTotal((long)docPkRecordsMybatisDao.queryByCount(criteria));
		datagrid.setRows(docPkRecordsVOList);
		return datagrid;
	}


	public List<OrderHeaderForNormalVO> getUndoneList(PageForm form) {
		List<OrderHeaderForNormal> orderHeaderForNormals = orderHeaderForNormalMybatisDao.queryPackageList(form.getStart(), form.getPageSize());
		OrderHeaderForNormalVO orderHeaderForNormalVO;
		List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = new ArrayList<>();
		System.out.println();
		for (OrderHeaderForNormal orderHeaderForNormal : orderHeaderForNormals) {

			orderHeaderForNormalVO = new OrderHeaderForNormalVO();
			BeanUtils.copyProperties(orderHeaderForNormal, orderHeaderForNormalVO);
			orderHeaderForNormalVOS.add(orderHeaderForNormalVO);
		}

		return orderHeaderForNormalVOS;
	}



	public Json selectDocPKRecord(DocPkRecordsQuery DocPkRecordsQuery) throws Exception {
		Json json = new Json();
		DocPkRecords docPkRecords = new DocPkRecords();
//		BeanUtils.copyProperties(docPkRecordsForm, docPkRecords);
		docPkRecordsMybatisDao.queryById(docPkRecords);
		json.setSuccess(true);
		return json;
	}


	/**
	 * 拣货提交
	 */
	public PdaResult singlePkCommit(DocPkRecordsForm form) {
		int packQty = 1;//单次提交，件数为1

//		Json statusJson = orderStatusCheck(form.getOrderno());
//		if (!statusJson.isSuccess()) {
//			return new PdaResult(PdaResult.CODE_FAILURE, statusJson.getMsg());
//		}
//		DocOrderPackingCarton packingCartonQuery = new DocOrderPackingCarton();
		DocPkRecords docPkRecordsQuery = new DocPkRecords();
		docPkRecordsQuery.setOrderno(form.getOrderno());
		docPkRecordsQuery.setAllocationdetailsid(form.getAllocationdetailsid());



		//批次获取
		InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(form.getLotnum());
//		Map<String, Object> map = new Map<String, Object>();
		BasSkuQuery skuQuery = new BasSkuQuery();
		skuQuery.setCustomerid(form.getCustomerid());
		skuQuery.setSku(invLotAtt.getSku());
		BasSku sku = basSkuMybatisDao.queryById(skuQuery);
		BasPackage p = basPackageMybatisDao.queryById(sku.getPackid());
		try {

//			DocPkRecords packingCartonInfo = docPkRecordsMybatisDao.queryPackingCartonInfo(form.getOrderno(), form.getTraceid());
//			if (packingCartonInfo == null) {
//
//				DocOrderPackingCartonInfo docOrderPackingCartonInfo = new DocOrderPackingCartonInfo();
//				BeanUtils.copyProperties(form, docOrderPackingCartonInfo);
//				docOrderPackingCartonInfo.setCartonno(Integer.valueOf(form.getTraceid().split("#")[1]));
//				docOrderPackingCartonInfo.setAddwho(form.getEditwho());
//				docOrderPackingMybatisDao.packingCartonInfoInsert(docOrderPackingCartonInfo);
//			}

//			docPkRecordsQuery.setTraceid(form.getTraceid());
			docPkRecordsQuery.setSku(form.getSku());
			docPkRecordsQuery.setLotnum(form.getLotnum());
			DocPkRecords docPkRecords = docPkRecordsMybatisDao.queryAvailablePackedDetail(docPkRecordsQuery);
			if (docPkRecords == null) {

				DocPkRecords docPkRecordsInsert = new DocPkRecords();
				BeanUtils.copyProperties(invLotAtt, docPkRecordsInsert);
				int maxlineno = docPkRecordsMybatisDao.getMaxPacklineno(form.getOrderno());
				docPkRecordsInsert.setPklineno(maxlineno + 1);
				docPkRecordsInsert.setOrderno(form.getOrderno());
//				docPkRecordsInsert.setTraceid(form.getTraceid());
//				docPkRecordsInsert.setCartonno(Integer.valueOf(form.getTraceid().split("#")[1]));
				docPkRecordsInsert.setCustomerid(form.getCustomerid());
				docPkRecordsInsert.setSku(form.getSku());
				docPkRecordsInsert.setSkudesce(form.getSkudesce());
				docPkRecordsInsert.setPickedqty(packQty/p.getQty1().intValue());

				docPkRecordsInsert.setPickedqtyEach(packQty);

				docPkRecordsInsert.setLotnum(form.getLotnum());
				docPkRecordsInsert.setAllocationdetailsid(form.getAllocationdetailsid());
//				packingCartonInsert.setDescription(form.getDescription());
//				packingCartonInsert.setConclusion(form.getConclusion());
				docPkRecordsInsert.setAddwho(form.getEditwho());
				docPkRecordsInsert.setEditwho(form.getEditwho());
				docPkRecordsMybatisDao.add(docPkRecordsInsert);
			}else {


				docPkRecords.setPickedqtyEach(docPkRecords.getPickedqty() + packQty);
				docPkRecords.setPickedqty((docPkRecords.getPickedqty() + packQty)/p.getQty1().intValue());

				docPkRecords.setEditwho(form.getEditwho());
				docPkRecordsMybatisDao.updateDocPkRecords(docPkRecords);
				//这边的packingCartonInfo的pickflag肯定是0，在上yigefangfa获取的时候已经作了处理
//				packingCarton.setQty(packingCarton.getQty() + form.getQty());
//				packingCarton.setEditwho(form.getEditwho());
//				docOrderPackingMybatisDao.updatePackingCarton(packingCarton);
			}

		}catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务

			return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "拣货失败(系统错误:" + e.getMessage() + ")");
		}
		return new PdaResult(PdaResult.CODE_SUCCESS, "拣货成功");
	}





	public Json addDocPkRecords(DocPkRecordsForm docPkRecordsForm) throws Exception {
		Json json = new Json();
		DocPkRecords docPkRecords = new DocPkRecords();
		BeanUtils.copyProperties(docPkRecordsForm, docPkRecords);
		docPkRecordsMybatisDao.add(docPkRecords);
		json.setSuccess(true);
		return json;
	}

	public Json editDocPkRecords(DocPkRecordsForm docPkRecordsForm) {
		Json json = new Json();
//		DocPkRecords docPkRecords = docPkRecordsMybatisDao.queryByList(docPkRecordsForm.getAllocationdetailsid());
//		BeanUtils.copyProperties(docPkRecordsForm, docPkRecords);
		docPkRecordsMybatisDao.updateBySelective(docPkRecordsForm);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocPkRecords(String id) {
		Json json = new Json();
		DocPkRecords docPkRecords = docPkRecordsMybatisDao.queryById(id);
		if(docPkRecords != null){
			docPkRecordsMybatisDao.delete(docPkRecords);
		}
		json.setSuccess(true);
		return json;
	}




	public List<EasyuiCombobox> getDocPkRecordsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		MybatisCriteria criteria = new MybatisCriteria();

		List<DocPkRecords> docPkRecordsList = docPkRecordsMybatisDao.queryByList(criteria);
		if(docPkRecordsList != null && docPkRecordsList.size() > 0){
			for(DocPkRecords docPkRecords : docPkRecordsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docPkRecords.getAllocationdetailsid()));
				combobox.setValue(docPkRecords.getOrderno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}