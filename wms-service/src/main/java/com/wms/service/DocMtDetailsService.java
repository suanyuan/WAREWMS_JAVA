package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.*;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.DocMtDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocMtDetailsForm;
import org.apache.camel.language.Bean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("docMtDetailsService")
public class DocMtDetailsService extends BaseService {

	@Autowired
	private DocMtDetailsMybatisDao docMtDetailsMybatisDao;

	@Autowired
	private DocMtHeaderMybatisDao docMtHeaderMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private BasPackageMybatisDao basPackageMybatisDao;

	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;

	@Autowired
	private BasSerialNumMybatisDao basSerialNumMybatisDao;

	public EasyuiDatagrid<DocMtDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocMtDetailsQuery query) {
		EasyuiDatagrid<DocMtDetailsVO> datagrid = new EasyuiDatagrid<DocMtDetailsVO>();
		List<DocMtDetailsVO> docMtDetailsVOList = new ArrayList<DocMtDetailsVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		if(query.getMtno()==null||query.getMtno()==""){
			datagrid.setRows(docMtDetailsVOList);
			datagrid.setTotal((long)0);
			return datagrid;
		}

		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByList(mybatisCriteria);
		DocMtDetailsVO docMtDetailsVO = null;
		for (DocMtDetails docMtDetails : docMtDetailsList) {
			docMtDetailsVO = new DocMtDetailsVO();
			BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);
			docMtDetailsVOList.add(docMtDetailsVO);
		}
		datagrid.setTotal((long)docMtDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docMtDetailsVOList);
		return datagrid;
	}

	public Json addDocMtDetails(DocMtDetailsForm docMtDetailsForm) throws Exception {
		Json json = new Json();
		DocMtDetails docMtDetails = new DocMtDetails();
		BeanUtils.copyProperties(docMtDetailsForm, docMtDetails);
		docMtDetailsMybatisDao.add(docMtDetails);
		json.setSuccess(true);
		return json;
	}

	public Json editDocMtDetails(DocMtDetailsForm docMtDetailsForm) {
		Json json = new Json();
		DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryById(docMtDetailsForm.getMtno());
		BeanUtils.copyProperties(docMtDetailsForm, docMtDetails);
		docMtDetailsMybatisDao.update(docMtDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocMtDetails(String id) {
		Json json = new Json();
		DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryById(id);
		if(docMtDetails != null){
			docMtDetailsMybatisDao.delete(docMtDetails);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getDocMtDetailsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByAll();
		if(docMtDetailsList != null && docMtDetailsList.size() > 0){
			for(DocMtDetails docMtDetails : docMtDetailsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docMtDetails.getMtno()));
				combobox.setValue(docMtDetails.getLotnum());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

    /**
     * 根据扫码结果查询养护明细
     * 可能存在不同货主的相同产品
     * @param query ~
     * @return ~
     */
	public List<DocMtDetailsVO> queryMtDetail(DocMtDetailsQuery query) {

        //如果是序列号扫码，验证是否存在对应序列号记录（bas_serial_num）
        BasSerialNum basSerialNum = null;
        if (StringUtil.isNotEmpty(query.getOtherCode()) ||
                StringUtil.isNotEmpty(query.getLotatt05())) {

            BasSerialNumQuery serialNumQuery = new BasSerialNumQuery(StringUtil.isNotEmpty(query.getOtherCode()) ? query.getOtherCode() : query.getLotatt05());
            basSerialNum = basSerialNumMybatisDao.queryById(serialNumQuery);
            if (basSerialNum != null) {

                //序列号扫码数据缺失 效期、生产批号（注：序列号不需要传，效期不参与查询）
                query.setLotatt04(basSerialNum.getBatchNum());
            }
        }

	    List<DocMtDetails> docMtDetailsList= docMtDetailsMybatisDao.queryUnfinishedDetails(query);
	    List<DocMtDetailsVO> docMtDetailsVOList = new ArrayList<>();
	    DocMtDetailsVO docMtDetailsVO;
        for (DocMtDetails docMtDetails : docMtDetailsList) {

            //产品档案
            BasSku basSku = basSkuMybatisDao.queryById(docMtDetails);
            if (basSku == null) continue;

            /*
            产品线 为空则默认正常流程
            不为空的情况下，如果记录序列号的serial_flag为1，则在下方需要清除查询条件-序列号
             */
            ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
            ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
            if (productLine == null) continue;
            boolean isSerialManagement = (productLine.getSerialFlag() == 1);

            //批次属性
            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docMtDetails);
            if (invLotAtt == null) continue;

            if (isSerialManagement) {

                if (!StringUtil.fixNull(invLotAtt.getLotatt04()).equals(StringUtil.fixNull(query.getLotatt04()))) {
                    continue;
                }
            }else {

                if (!StringUtil.fixNull(invLotAtt.getLotatt04()).equals(StringUtil.fixNull(query.getLotatt04())) &&
                        !StringUtil.fixNull(invLotAtt.getLotatt05()).equals(StringUtil.fixNull(query.getLotatt05()))) {
                    continue;
                }
            }

            //验证通过开始塞数据
            docMtDetailsVO = new DocMtDetailsVO();
            BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);
            //产品档案
            docMtDetailsVO.setBasSku(basSku);
            //产品线
            docMtDetailsVO.setProductLine(productLine);
            //批属
            docMtDetailsVO.setInvLotAtt(invLotAtt);


            //客户档案
            BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(docMtDetails);
            if (basCustomer == null) continue;
            String jsonStr = JSON.toJSONString(basCustomer, SerializerFeature.DisableCircularReferenceDetect);
            docMtDetailsVO.setBasCustomer(JSONObject.parseObject(jsonStr, BasCustomer.class));



            //包装规格
            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            if (basPackage == null) continue;
            docMtDetailsVO.setBasPackage(basPackage);

            docMtDetailsVOList.add(docMtDetailsVO);
        }
 	    return docMtDetailsVOList;
    }

    /**
     * 养护提交
     */
    public PdaResult mtSubmit(DocMtDetailsForm form) {

	    DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryById(form);//mtno + mtlineno

        if (docMtDetails == null) return new PdaResult(PdaResult.CODE_FAILURE, "查无此养护明细");

        if (form.getMtqtyCompleted() > docMtDetails.getMtqtyExpected() - docMtDetails.getMtqtyCompleted())
            return new PdaResult(PdaResult.CODE_FAILURE, "养护数超出预期");

        try {

            //明细减去养护数
            BasSku basSku = basSkuMybatisDao.queryById(docMtDetails);
            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            DocMtDetails executionDetail = new DocMtDetails();
            BeanUtils.copyProperties(docMtDetails, executionDetail);
            executionDetail.setMtqtyExpected(executionDetail.getMtqtyExpected() - form.getMtqtyCompleted());
            executionDetail.setMtqtyEachExpected(executionDetail.getMtqtyEachExpected() - (form.getMtqtyCompleted() * basPackage.getQty1().doubleValue()));
            executionDetail.setEditwho("Gizmo");
            docMtDetailsMybatisDao.updateDetailQty(executionDetail);

            //养护结果明细是否存在，存在合并，不存在新增
            DocMtDetailsQuery existQuery = new DocMtDetailsQuery();
            existQuery.setMtno(form.getMtno());
            existQuery.setLinestatus("40");
            existQuery.setCustomerid(docMtDetails.getCustomerid());
            existQuery.setSku(docMtDetails.getSku());
            existQuery.setLocationid(docMtDetails.getLocationid());
            existQuery.setLotnum(docMtDetails.getLotnum());

            existQuery.setCheckFlag(String.valueOf(form.getCheckFlag()));
            existQuery.setConclusion(form.getConclusion());
            existQuery.setRemark(form.getRemark());
            existQuery.setConversewho("Gizmo");

            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(existQuery));
            List<DocMtDetails> existDetailList = docMtDetailsMybatisDao.queryByList(mybatisCriteria);
            if (existDetailList.size() > 0) {

                DocMtDetails changeDetail = existDetailList.get(0);
                changeDetail.setMtqtyExpected(changeDetail.getMtqtyExpected() + form.getMtqtyCompleted());
                changeDetail.setMtqtyEachExpected(changeDetail.getMtqtyEachExpected() + (form.getMtqtyCompleted() * basPackage.getQty1().doubleValue()));
                changeDetail.setMtqtyCompleted(changeDetail.getMtqtyCompleted() + form.getMtqtyCompleted());
                changeDetail.setMtqtyEachCompleted(changeDetail.getMtqtyEachCompleted() + (form.getMtqtyCompleted() * basPackage.getQty1().doubleValue()));
                changeDetail.setEditwho("Gizmo");
                docMtDetailsMybatisDao.updateDetailQty(changeDetail);
            }else {

                DocMtHeaderQuery docMtHeaderQuery = new DocMtHeaderQuery();
                docMtHeaderQuery.setMtno(docMtDetails.getMtno());
                docMtDetails.setMtlineno(docMtDetailsMybatisDao.getMtlinenoByMtno(docMtHeaderQuery) + 1);
                docMtDetails.setLinestatus("40");
                docMtDetails.setMtqtyExpected(form.getMtqtyCompleted());
                docMtDetails.setMtqtyEachExpected(form.getMtqtyCompleted() * basPackage.getQty1().doubleValue());
                docMtDetails.setMtqtyCompleted(docMtDetails.getMtqtyExpected());
                docMtDetails.setMtqtyEachCompleted(docMtDetails.getMtqtyEachExpected());
                docMtDetails.setCheckFlag(form.getCheckFlag());
                docMtDetails.setConclusion(form.getConclusion());
                docMtDetails.setConversedate(new Date());
                docMtDetails.setConversewho("Gizmo");
                docMtDetails.setRemark(form.getRemark());
                docMtDetails.setAddwho("Gizmo");
                docMtDetailsMybatisDao.add(docMtDetails);
            }

            //清除0预期数的明细
            docMtDetailsMybatisDao.clearZeroTask();

            //修改头档装填
            DocMtDetailsQuery detailsQuery = new DocMtDetailsQuery();
            detailsQuery.setLinestatus("00");
            detailsQuery.setMtno(form.getMtno());
            MybatisCriteria mybatisCriteria22 = new MybatisCriteria();
            mybatisCriteria.setCondition(detailsQuery);
            List<DocMtDetails> unfinishedDetailList = docMtDetailsMybatisDao.queryByList(mybatisCriteria22);

            //更新养护单状态
            DocMtHeader docMtHeader = new DocMtHeader();
            docMtHeader.setMtno(form.getMtno());
            if (unfinishedDetailList.size() > 0) {
                docMtHeader.setMtstatus("30");
            }else {
                docMtHeader.setMtstatus("40");
            }
            docMtHeaderMybatisDao.updateStatus(docMtHeader);

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

	    return new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUBMIT_SUCCESS_MSG);
    }

    /**
     * 获取养护单进度明细
     * @param mtno ~
     * @return `
     */
    public List<DocMtProgressDetail> queryDocMtList(String mtno) {

        return docMtDetailsMybatisDao.queryDocMtList(mtno);
    }
}