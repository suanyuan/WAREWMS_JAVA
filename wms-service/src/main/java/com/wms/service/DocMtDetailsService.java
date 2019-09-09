package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocMtDetailsQuery;
import com.wms.query.DocMtHeaderQuery;
import com.wms.query.ProductLineQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocMtDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocMtDetailsForm;
import com.wms.vo.form.pda.ScanResultForm;
import com.wms.vo.pda.CommonVO;
import org.apache.commons.lang.StringUtils;
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
    private CommonService commonService;

	@Autowired
    private BasCodesService basCodesService;

    /**
     * 养护作业界面分页显示 根据mtno
     * @param pager
     * @param query
     * @return
     */
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

		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByListLotatt(mybatisCriteria);
		DocMtDetailsVO docMtDetailsVO = null;
		for (DocMtDetails docMtDetails : docMtDetailsList) {
			docMtDetailsVO = new DocMtDetailsVO();
			docMtDetails.setMtqtyExpected(docMtDetails.getMtqtyExpected()-docMtDetails.getMtqtyCompleted());
			docMtDetails.setMtqtyEachExpected(docMtDetails.getMtqtyEachExpected()-docMtDetails.getMtqtyEachCompleted());
			BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);
			docMtDetailsVOList.add(docMtDetailsVO);
		}
		datagrid.setTotal((long)docMtDetailsMybatisDao.queryByCountLotatt(mybatisCriteria));
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
	 * 批量养护
	 */
	public Json submitDocMtList(String forms){
		Json json=new Json();
		StringBuffer result=new StringBuffer();
//        json转集合
		List<DocMtDetailsForm> list=JSON.parseArray(forms,DocMtDetailsForm.class);
		Boolean con=true;
		for (DocMtDetailsForm detailForm : list) {
            detailForm.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            detailForm.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			PdaResult pdaResult = mtSubmit(detailForm);//调用养护作业方法 单个验收
			if(pdaResult.getErrorCode()==400){
				result.append("养护单号:"+detailForm.getMtno()).append(","+pdaResult.getMsg()).append("<br/>");
				con=false;
			}
		}
		if(con){
			json.setSuccess(true);
			json.setMsg("养护成功!");
		}else{
			json.setSuccess(false);
			json.setMsg("部分养护未成功!<br/>"+result.toString());
		}
		return json;
	}

    /**
     * 根据扫码结果查询养护明细
     * @param query ~
     * @return ~
     */
	public Json queryMtDetail(DocMtDetailsQuery query) {

	    Json resultJson = new Json();
        DocMtDetailsVO docMtDetailsVO = new DocMtDetailsVO();

        /*
        111，处理BasSku获取问题
        并且返回准确的批号、序列号匹配条件
         */
        ScanResultForm scanResultForm = new ScanResultForm();
        //customerid, GTIN, lotatt04, lotatt05, otherCode
        BeanUtils.copyProperties(query, scanResultForm);
        CommonVO commonVO = commonService.adaptScanResult4SKU(scanResultForm);

        if (!commonVO.isSuccess()) {

            resultJson.setSuccess(false);
            resultJson.setMsg(commonVO.getMessage());
            return resultJson;
        }

        BasSku basSku = commonVO.getBasSku();
        query.setLotatt04(commonVO.getBatchNum());
        query.setLotatt05(commonVO.getSerialNum());
        query.setSku(basSku.getSku());
        docMtDetailsVO.setBasSku(basSku);

        /*
        222，判断获取养护扫码数据是否齐全
         */
        Json scanJson = commonService.judgeMtScanResult(query, commonVO);
        if (!scanJson.isSuccess()) {

            resultJson.setSuccess(false);
            resultJson.setMsg(scanJson.getMsg());
            return resultJson;
        }

        /*
        333,养护明细
         */
	    DocMtDetails docMtDetails = (DocMtDetails) scanJson.getObj();
	    BeanUtils.copyProperties(docMtDetails,docMtDetailsVO);

	    /*
	    444,批次属性
	     */
        InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docMtDetails);
        if (invLotAtt == null) {

            resultJson.setSuccess(false);
            resultJson.setMsg("查无此产品的批次数据");
            return resultJson;
        }
        docMtDetailsVO.setInvLotAtt(invLotAtt);

        /*
        555,产品线
         */
        ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
        ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
        if (productLine == null) {

            resultJson.setSuccess(false);
            resultJson.setMsg("查无此产品的产品线数据");
            return resultJson;
        }
        docMtDetailsVO.setProductLine(productLine);

        /*
        666，客户档案
         */
        BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(docMtDetails);
        if (basCustomer == null) {

            resultJson.setSuccess(false);
            resultJson.setMsg("查无此产品的客户档案数据");
            return resultJson;
        }
        docMtDetailsVO.setBasCustomer(basCustomer);

        //包装规格
        BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
        if (basPackage == null) {

            resultJson.setSuccess(false);
            resultJson.setMsg("查无此产品的包装规格数据");
            return resultJson;
        }
        docMtDetailsVO.setBasPackage(basPackage);

        resultJson.setSuccess(true);
        resultJson.setMsg(Constant.SUCCESS_MSG);
        resultJson.setObj(docMtDetailsVO);
 	    return resultJson;
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
            executionDetail.setEditwho(form.getEditwho());
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
            existQuery.setConversewho(form.getEditwho());

            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(existQuery));
            List<DocMtDetails> existDetailList = docMtDetailsMybatisDao.queryByList(mybatisCriteria);
            if (existDetailList.size() > 0) {

                DocMtDetails changeDetail = existDetailList.get(0);
                changeDetail.setMtqtyExpected(changeDetail.getMtqtyExpected() + form.getMtqtyCompleted());
                changeDetail.setMtqtyEachExpected(changeDetail.getMtqtyEachExpected() + (form.getMtqtyCompleted() * basPackage.getQty1().doubleValue()));
                changeDetail.setMtqtyCompleted(changeDetail.getMtqtyCompleted() + form.getMtqtyCompleted());
                changeDetail.setMtqtyEachCompleted(changeDetail.getMtqtyEachCompleted() + (form.getMtqtyCompleted() * basPackage.getQty1().doubleValue()));
                changeDetail.setEditwho(form.getEditwho());
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
                docMtDetails.setConversewho(form.getEditwho());
                docMtDetails.setRemark(form.getRemark());
                docMtDetails.setAddwho(form.getEditwho());
                docMtDetails.setEditwho(form.getEditwho());
                docMtDetailsMybatisDao.add(docMtDetails);
            }

            //清除0预期数的明细
            docMtDetailsMybatisDao.clearZeroTask();

            //修改头档装填
            DocMtDetailsQuery detailsQuery = new DocMtDetailsQuery();
            detailsQuery.setLinestatus("00");
            detailsQuery.setMtno(form.getMtno());
            MybatisCriteria mybatisCriteria22 = new MybatisCriteria();
            mybatisCriteria22.setCondition(detailsQuery);
            List<DocMtDetails> unfinishedDetailList = docMtDetailsMybatisDao.queryByList(mybatisCriteria22);

            //更新养护单状态
            DocMtHeader docMtHeader = new DocMtHeader();
            docMtHeader.setMtno(form.getMtno());
            docMtHeader.setEditwho(form.getEditwho());
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

    /**
     * 获取养护指导列表，用户根据指导列表进行养护
     * @param mtno ~
     * @return ~
     */
    public List<DocMtDetailsVO> getGuidanceList(String mtno, int pageNum) {

        List<DocMtDetailsVO> docMtDetailsVOList = new ArrayList<>();

        DocMtDetailsVO docMtDetailsVO;

        List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryMtGuideList(mtno, (pageNum - 1) * Constant.pageSize, Constant.pageSize);
        for (DocMtDetails docMtDetails : docMtDetailsList) {

            docMtDetailsVO = new DocMtDetailsVO();
            BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);

            //批次属性
            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docMtDetails.getLotnum());
            String jsonStr1 = JSON.toJSONString(invLotAtt, SerializerFeature.DisableCircularReferenceDetect);
            docMtDetailsVO.setInvLotAtt(JSONObject.parseObject(jsonStr1, InvLotAtt.class));

            //产品档案
            BasSkuQuery basSkuQuery = new BasSkuQuery(docMtDetails.getCustomerid(), docMtDetails.getSku());
            BasSku basSku = basSkuMybatisDao.queryById(basSkuQuery);
            docMtDetailsVO.setBasSku(JSONObject.parseObject(JSON.toJSONString(basSku, SerializerFeature.DisableCircularReferenceDetect), BasSku.class));

            docMtDetailsVOList.add(docMtDetailsVO);
        }
        return docMtDetailsVOList;
    }

    /**
     *
     * 打印养护检查记录
    */
    public List<DocMtHeader> printMtDetails(String mtNo, String  mtlineNo ){

        DocMtDetailsQuery detailsQuery = new DocMtDetailsQuery();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        List<DocMtDetails> MtDetailsList = new ArrayList<DocMtDetails>();
        DocMtHeader docMtHeader = new DocMtHeader();//头档信息
        Double numberSum = 0.00;//初始化总数量
        List<EasyuiCombobox> easyuiComboboxListUom = basCodesService.getBy(Constant.CODE_CATALOG_UOM);//查询单位

        if(StringUtils.isNotEmpty(mtlineNo) && StringUtils.isNotEmpty(mtNo)){
            String [] mtlineNoArr = mtlineNo.split(",");
            String [] mtNoArr = mtNo.split(",");
            Boolean ibjFlag = false;
            for (String mtNoArrObj:mtNoArr) {
                for (String mtlineNoObj:mtlineNoArr) {
                    detailsQuery.setMtno(mtNoArrObj);//养护行号
                    detailsQuery.setMtlineno(mtlineNoObj);//养护单号
                    mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(detailsQuery));
                    List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByListLotatt(mybatisCriteria);
                    for (DocMtDetails docMtDetails1:docMtDetailsList) {
                        MtDetailsList.add(docMtDetails1);
                        ibjFlag = true;
                    }
                }
                if (ibjFlag) break;;
            }
            Boolean headerFlag = false;
            for (String mtHeader:mtNoArr) {
                MybatisCriteria mybatisCriteria1 = new MybatisCriteria();
                DocMtHeaderQuery docMtHeaderQuery = new DocMtHeaderQuery();
                docMtHeaderQuery.setMtno(mtHeader);
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docMtHeaderQuery));
                List<DocMtHeader> docMtHeaderList =  docMtHeaderMybatisDao.queryByList(mybatisCriteria1);
                for (DocMtHeader docMtHeader1: docMtHeaderList) {
                    BeanUtils.copyProperties(docMtHeader1,docMtHeader);
                    if(docMtHeader.getFenceFlag() == 1){
                        docMtHeader.setFenceFlagName("符合");
                    }else if (docMtHeader.getFenceFlag() == 0){
                        docMtHeader.setFenceFlagName("不符合");
                    }else{
                        docMtHeader.setFenceFlagName("未检查");
                    }
                    if(docMtHeader.getFlowFlag() == 1){
                        docMtHeader.setFlowFlagName("符合");
                    }else if (docMtHeader.getFlowFlag() == 0){
                        docMtHeader.setFlowFlagName("不符合");
                    }else{
                        docMtHeader.setFlowFlagName("未检查");
                    }
                    if(docMtHeader.getSignFlag() == 1){
                        docMtHeader.setSignFlagName("符合");
                    }else if (docMtHeader.getSignFlag() == 0){
                        docMtHeader.setSignFlagName("不符合");
                    }else{
                        docMtHeader.setSignFlagName("未检查");
                    }
                    if(docMtHeader.getSanitationFlag() == 1){
                        docMtHeader.setSanitationFlagName("符合");
                    }else if (docMtHeader.getSanitationFlag() == 0){
                        docMtHeader.setSanitationFlagName("不符合");
                    }else{
                        docMtHeader.setSanitationFlagName("未检查");
                    }
                    if(docMtHeader.getStorageFlag() == 1){
                        docMtHeader.setStorageFlagName("符合");
                    }else if (docMtHeader.getStorageFlag() == 0){
                        docMtHeader.setStorageFlagName("不符合");
                    }else{
                        docMtHeader.setStorageFlagName("未检查");
                    }
                    headerFlag = true;
                }
                if(headerFlag) break;
            }
        }
        List<DocMtHeader> dataHeader = new ArrayList<DocMtHeader>(); // 头档
        docMtHeader.setDetls(new ArrayList<DocMtDetails>());
        if(MtDetailsList.size() > 0){
            for ( DocMtDetails docMtDetails1: MtDetailsList) {
                for (EasyuiCombobox easyuiComboboxUom: easyuiComboboxListUom) {//包装单位
                    if(docMtDetails1.getUom().equals(easyuiComboboxUom.getId())){
                        docMtDetails1.setUomName(easyuiComboboxUom.getValue());
                    }
                }
                //养护结论
                if(docMtDetails1.getConclusion().equals("1")){
                    docMtDetails1.setConclusion("合格");
                }else if(docMtDetails1.getConclusion().equals("0")){
                    docMtDetails1.setConclusion("不合格");
                }else {
                    docMtDetails1.setConclusion("未检查");
                }
                //检查内容
                if(docMtDetails1.getCheckFlag().equals("1")){
                    docMtDetails1.setCheckFlag("合格");
                }else if(docMtDetails1.getCheckFlag().equals("0")){
                    docMtDetails1.setCheckFlag("不合格");
                }else{
                    docMtDetails1.setCheckFlag("未检查");
                }
                numberSum += docMtDetails1.getMtqtyEachExpected();//加上数量
                docMtDetails1.setMtqtyEachCompletedSum(numberSum);
                docMtHeader.getDetls().add(docMtDetails1);
            }
            dataHeader.add(docMtHeader);
        }
        return dataHeader;
    }
}