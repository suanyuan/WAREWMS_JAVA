package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.form.BasCustomerForm;
import com.wms.vo.form.BasSkuForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.FirstReviewLogMybatisDao;
import com.wms.vo.FirstReviewLogVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstReviewLogForm;
import com.wms.query.FirstReviewLogQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("firstReviewLogService")
public class FirstReviewLogService extends BaseService {

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;
	@Autowired
	private BasCustomerService basCustomerService;
	@Autowired
	private GspCustomerService gspCustomerService;
	@Autowired
	private GspSupplierService gspSupplierService;
	@Autowired
	private GspReceivingService gspReceivingService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private FirstBusinessApplyService firstBusinessApplyService;
	@Autowired
	private FirstBusinessProductApplyService firstBusinessProductApplyService;
	@Autowired
	private BasSkuService basSkuService;
	@Autowired
	private GspProductRegisterSpecsService gspProductRegisterSpecsService;
	@Autowired
	private GspProductRegisterService gspProductRegisterService;


	public EasyuiDatagrid<FirstReviewLogVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstReviewLogQuery query) {
		EasyuiDatagrid<FirstReviewLogVO> datagrid = new EasyuiDatagrid<FirstReviewLogVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setOrderByClause("create_date desc");
		List<FirstReviewLog> firstReviewLogList = firstReviewLogMybatisDao.queryByList(criteria);
		FirstReviewLogVO firstReviewLogVO = null;
		List<FirstReviewLogVO> firstReviewLogVOList = new ArrayList<FirstReviewLogVO>();
		for (FirstReviewLog firstReviewLog : firstReviewLogList) {
			firstReviewLogVO = new FirstReviewLogVO();
			BeanUtils.copyProperties(firstReviewLog, firstReviewLogVO);
			firstReviewLogVOList.add(firstReviewLogVO);
		}
		int count = firstReviewLogMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(firstReviewLogVOList);
		return datagrid;
	}

	public Json addFirstReviewLog(FirstReviewLogForm firstReviewLogForm) throws Exception {
		Json json = new Json();
		FirstReviewLog firstReviewLog = new FirstReviewLog();
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.add(firstReviewLog);
		json.setSuccess(true);
		return json;
	}

	public Json editFirstReviewLog(FirstReviewLogForm firstReviewLogForm) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(firstReviewLogForm.getReviewId());
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.update(firstReviewLog);
		json.setSuccess(true);
		return json;
	}

	public Json updateByReviewTypeId(FirstReviewLogForm firstReviewLogForm) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = new FirstReviewLog();
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.updateByReviewTypeId(firstReviewLog);
		json.setSuccess(true);
		return json;
	}



	public Json deleteFirstReviewLog(String id) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(id);
		if(firstReviewLog != null){
			firstReviewLogMybatisDao.delete(firstReviewLog);
		}
		json.setSuccess(true);
		return json;
	}

	public Json checkFirstReview(String id,String remark){
		try{
			Json json = new Json();
			FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(id);
			FirstReviewLog updateLog = new FirstReviewLog();
			//未审核
			if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING)){
				updateLog.setCheckIdQc(SfcUserLoginUtil.getLoginUser().getId());
				updateLog.setCheckDateQc(new Date());
				updateLog.setCheckRemarkQc(remark);
				updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE);
			}else if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE)){
				updateLog.setCheckIdHead(SfcUserLoginUtil.getLoginUser().getId());
				updateLog.setCheckDateHead(new Date());
				updateLog.setCheckRemarkHead(remark);
				updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_PASS);

				//下发数据
				publishData(firstReviewLog.getReviewTypeId());

			}
			updateLog.setReviewId(id);
			firstReviewLogMybatisDao.updateBySelective(updateLog);
			return json;
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}

	}

	public Json updateFirstReviewByNo(String no,String state){
		Long result = firstReviewLogMybatisDao.updateFirstReviewByNo(no,state,SfcUserLoginUtil.getLoginUser().getId());
		if(result>0){
			return Json.success("");
		}
		return Json.error("");
	}

	private Json publishData(String no) throws Exception{
		//TODO 把数据至为失效
		//下发委托客户数据
		if(no.indexOf(Constant.APLCUSNO)!=-1){
			Json json = gspCustomerService.getGspCustomerById(no);
			if(!json.isSuccess()){
				return Json.error("查询不到对应的委托客户申请");
			}
			GspCustomer customer = (GspCustomer)json.getObj();
			BasCustomerForm form = new BasCustomerForm();
			form.setEnterpriseId(customer.getEnterpriseId());
			form.setOperateType(customer.getOperateType());
			form.setContractNo(customer.getContractNo());
			form.setContractUrl(customer.getContractUrl());
			form.setClientContent(customer.getClientContent());
			form.setClientStartDate(customer.getClientStartDate());
			form.setClientEndDate(customer.getClientEndDate());
			form.setClientTerm(Long.parseLong(customer.getClientTerm()));
			form.setIsChineseLabel(Long.parseLong(customer.getIsChineseLabel()));
			form.setActiveFlag(Constant.IS_USE_YES);
			return basCustomerService.addBasCustomer(form);
		}else if(no.indexOf(Constant.APLSUPNO)!=-1){
			Json json = gspSupplierService.getGspSupplierInfo(no);
			if(!json.isSuccess()){
				return Json.error("查询不到对应的供应商申请");
			}
			GspSupplier supplier = (GspSupplier)json.getObj();
			BasCustomerForm form = new BasCustomerForm();
			form.setEnterpriseId(supplier.getEnterpriseId());
			form.setOperateType(supplier.getOperateType());
			form.setActiveFlag(Constant.IS_USE_YES);
			return basCustomerService.addBasCustomer(form);
		}else if(no.indexOf(Constant.APLRECNO)!=-1){
			/*Json json = gspReceivingService
			return Constant.APLRECNO;*/
			return Json.error("待开发");
		}else if(no.indexOf(Constant.APLPRONO)!=-1){
			Json json = firstBusinessApplyService.queryFirstBusinessApply(no);
			if(!json.isSuccess()){
				return Json.error("查询不到对应的产品申请");
			}
			FirstBusinessApply firstBusinessApply = (FirstBusinessApply)json.getObj();
			Json productJson = firstBusinessProductApplyService.getListByApplyId(firstBusinessApply.getApplyId());
			if(!productJson.isSuccess()){
				return productJson;
			}
			List<FirstBusinessProductApply> productApplyList = (List<FirstBusinessProductApply>)productJson.getObj();
			for(FirstBusinessProductApply f:productApplyList){
				BasSkuForm skuForm = new BasSkuForm();
				Json spec = gspProductRegisterSpecsService.getGspProductRegisterSpecsInfo(f.getSpecsId());
				GspProductRegisterSpecs specObj = (GspProductRegisterSpecs)spec.getObj();
				GspProductRegister register = gspProductRegisterService.queryById(specObj.getProductRegisterId());
				skuForm.setDefaultreceivinguom(specObj.getUnit());
				skuForm.setDescrC(specObj.getSpecsName());
				skuForm.setDescrE(specObj.getProductModel());
				skuForm.setPackid(specObj.getPackingUnit());
				skuForm.setReservedfield01(specObj.getProductName());
				skuForm.setReservedfield02(specObj.getProductRemark());
				skuForm.setReservedfield03(specObj.getProductRegisterNo());
				skuForm.setReservedfield04(register.getClassifyId());
				skuForm.setReservedfield05(register.getClassifyCatalog());
				skuForm.setSku(specObj.getProductCode());
				skuForm.setSkuGroup1(specObj.getProductLine());
				//skuForm.setSkuGroup2();//附卡类别
				skuForm.setSkuGroup3(specObj.getPackingRequire());//包装要求
				skuForm.setSkuGroup4(specObj.getStorageCondition());
				skuForm.setSkuGroup5(specObj.getTransportCondition());
				skuForm.setSkuGroup6(register.getEnterpriseId());
				skuForm.setSkuGroup7(specObj.getIsDoublec());//是否需要双证
				skuForm.setSkuGroup8(specObj.getIsCertificate());//是否需要产品合格证
				skuForm.setSkuGroup9(specObj.getProductionAddress());
				//skuForm
				basSkuService.addBasSku(skuForm);
			}
			return Json.error("操作成功");
		}
		return Json.error("单据号无效："+no);
	}
}