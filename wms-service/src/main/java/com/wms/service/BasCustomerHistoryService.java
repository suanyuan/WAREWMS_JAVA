package com.wms.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCustomerQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.BasCustomerVO;
import com.wms.vo.form.BasCustomerForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("basCustomerHistoryService")
public class BasCustomerHistoryService extends BaseService {

	@Autowired
	private BasCustomerHistoryMybatisDao basCustomerHistoryMybatisDao;

	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;
	@Autowired
	private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;
	@Autowired
	private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;

	@Autowired
	private GspFirstRecordMybatisDao gspFirstRecordMybatisDao;
	@Autowired
	private GspSecondRecordMybatisDao gspSecondRecordMybatisDao;
	@Autowired
	private GspMedicalRecordMybatisDao gspMedicalRecordMybatisDao;

	public EasyuiDatagrid<BasCustomerVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCustomerQuery query) {
		EasyuiDatagrid<BasCustomerVO> datagrid = new EasyuiDatagrid<BasCustomerVO>();

		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));

		mybatisCriteria.setOrderByClause("addtime desc");
		List<BasCustomerHistory> basCustomerList = basCustomerHistoryMybatisDao.queryByList(mybatisCriteria);
		BasCustomerVO basCustomerVO = null;
		List<BasCustomerVO> basCustomerVOList = new ArrayList<BasCustomerVO>();
		for (BasCustomerHistory basCustomer : basCustomerList) {
			basCustomerVO = new BasCustomerVO();
			BeanUtils.copyProperties(basCustomer, basCustomerVO);
			GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(basCustomer.getEnterpriseId());

			
			if (gspEnterpriseInfo != null) {
				basCustomerVO.setEnterpriseName(gspEnterpriseInfo.getEnterpriseName());
				basCustomerVO.setShorthandName(gspEnterpriseInfo.getShorthandName());
				basCustomerVO.setEnterpriseNo(gspEnterpriseInfo.getEnterpriseNo());
				basCustomerVO.setContacts(gspEnterpriseInfo.getContacts());
				basCustomerVO.setContactsPhone(gspEnterpriseInfo.getContactsPhone());
				basCustomerVO.setEnterpriseType(gspEnterpriseInfo.getEnterpriseType());
				basCustomerVO.setRemark(gspEnterpriseInfo.getRemark());
			}
			basCustomerVOList.add(basCustomerVO);
		}
		datagrid.setTotal((long) basCustomerHistoryMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basCustomerVOList);
		return datagrid;
	}

	public Json addBasCustomerHistory(BasCustomerForm basCustomerHistoryForm) throws Exception {
		Json json = new Json();
		BasCustomerHistory basCustomerHistory = new BasCustomerHistory();
		BeanUtils.copyProperties(basCustomerHistoryForm, basCustomerHistory);
		basCustomerHistoryMybatisDao.add(basCustomerHistory);
		json.setSuccess(true);
		return json;
	}

	public Json editBasCustomerHistory(BasCustomerForm basCustomerHistoryForm) {
		Json json = new Json();
		BasCustomerHistory basCustomerHistory = basCustomerHistoryMybatisDao.queryById(basCustomerHistoryForm.getCustomerid());
		BeanUtils.copyProperties(basCustomerHistoryForm, basCustomerHistory);
		basCustomerHistoryMybatisDao.update(basCustomerHistory);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasCustomerHistory(String id) {
		Json json = new Json();
		BasCustomerHistory basCustomerHistory = basCustomerHistoryMybatisDao.queryById(id);
		if(basCustomerHistory != null){
			basCustomerHistoryMybatisDao.delete(basCustomerHistory);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasCustomerHistoryCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasCustomerHistory> basCustomerHistoryList = basCustomerHistoryMybatisDao.queryListByAll();
		if(basCustomerHistoryList != null && basCustomerHistoryList.size() > 0){
			for(BasCustomerHistory basCustomerHistory : basCustomerHistoryList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basCustomerHistory.getCustomerid()));
				combobox.setValue(basCustomerHistory.getDescrC());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	public void exportBasCustomerDataToExcel(HttpServletResponse response, BasCustomerForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());

		BasCustomerForm basCustomerForm = new BasCustomerForm();

		basCustomerForm.setCustomerType(form.getCustomerType());
		basCustomerForm.setCustomerid(form.getCustomerid());
		basCustomerForm.setDescrC(form.getDescrC());
		basCustomerForm.setActiveFlag(form.getActiveFlag());
		basCustomerForm.setEnterpriseNo(form.getEnterpriseNo());
		try {
			BasCustomerQuery query = new BasCustomerQuery();
			//权限控制
			query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			com.wms.utils.BeanUtils.copyProperties(basCustomerForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "客户档案历史查询结果";
			// excel要导出的数据
//			List<BasCustomerHistory> basCustomerList = basCustomerHistoryMybatisDao.queryByList(mybatisCriteria);
			EasyuiDatagridPager page = new EasyuiDatagridPager();
			EasyuiDatagrid<BasCustomerVO> pagedDatagrid = getPagedDatagrid(page, query);
			List<BasCustomerVO> basCustomerVOList = pagedDatagrid.getRows();


			// 导出
			if (basCustomerVOList == null || basCustomerVOList.size() == 0) {
				System.out.println("为空");
			}else {
				//将list集合转化为excle
				for (BasCustomerVO basCustomerVO : basCustomerVOList) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

					GspBusinessLicense BusinessLicense = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(basCustomerVO.getEnterpriseId());
					GspOperateLicense GspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId(), Constant.LICENSE_TYPE_PROD);
					GspOperateLicense GspGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId(),Constant.LICENSE_TYPE_OPERATE);
					GspFirstRecord GspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId());
					GspSecondRecord GspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId());
					GspMedicalRecord GspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId());


					if(BusinessLicense!=null){
						basCustomerVO.setSocialCreditCode(BusinessLicense.getSocialCreditCode());
						basCustomerVO.setLicenseNumber(BusinessLicense.getLicenseNumber());
						if(BusinessLicense.getBusinessStartDate()!=null){
							basCustomerVO.setBusinessStartDate(sdf.format(BusinessLicense.getBusinessStartDate()));
						}else{
							basCustomerVO.setBusinessStartDate("无期限");
						}
						if(BusinessLicense.getBusinessEndDate()!=null){
							basCustomerVO.setBusinessEndDate(sdf.format(BusinessLicense.getBusinessEndDate()));
						}else{
							basCustomerVO.setBusinessEndDate("无期限");
						}
					}else{
						basCustomerVO.setSocialCreditCode("无");
						basCustomerVO.setLicenseNumber("无");
						basCustomerVO.setBusinessStartDate("无");
						basCustomerVO.setBusinessEndDate("无");
					}

					if(GspProdLicense!=null){
						basCustomerVO.setProdLicenseNo(GspProdLicense.getLicenseNo());
						basCustomerVO.setProdLicenseExpiryDate(sdf.format(GspProdLicense.getLicenseExpiryDate()));
					}else{
						basCustomerVO.setProdLicenseNo("无");
						basCustomerVO.setProdLicenseExpiryDate("无");
					}

					if(GspGspOperateLicense!=null){
						basCustomerVO.setOperateLicenseNo(GspGspOperateLicense.getLicenseNo());
						basCustomerVO.setOperateLicenseExpiryDate(sdf.format(GspGspOperateLicense.getLicenseExpiryDate()));
					}else{
						basCustomerVO.setOperateLicenseNo("无");
						basCustomerVO.setOperateLicenseExpiryDate("无");
					}

					if(GspFirstRecord!=null){
						basCustomerVO.setFirstRecordNo(GspFirstRecord.getRecordNo());
						basCustomerVO.setFirstRecordApproveDate(sdf.format(GspFirstRecord.getApproveDate()));
					}else{
						basCustomerVO.setFirstRecordNo("无");
						basCustomerVO.setFirstRecordApproveDate("无");
					}
					if(GspSecondRecord!=null){
						basCustomerVO.setSecondRecordNo(GspSecondRecord.getRecordNo());
						basCustomerVO.setSecondRecordApproveDate(sdf.format(GspSecondRecord.getApproveDate()));
					}else{
						basCustomerVO.setSecondRecordNo("无");
						basCustomerVO.setSecondRecordApproveDate("无");
					}
					if(GspMedicalRecord!=null){
						basCustomerVO.setMedicalRegisterNo(GspMedicalRecord.getMedicalRegisterNo());
						basCustomerVO.setMedicalRegisterApproveDate(sdf.format(GspMedicalRecord.getApproveDate()));
					}else{
						basCustomerVO.setMedicalRegisterNo("无");
						basCustomerVO.setMedicalRegisterApproveDate("无");
					}


					if ("1".equals(basCustomerVO.getActiveFlag())) {
						basCustomerVO.setActiveFlag("是");
					}else if("0".equals(basCustomerVO.getActiveFlag())){
						basCustomerVO.setActiveFlag("否");
					}

					if(Constant.CODE_CUS_TYP_VE.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("供应商");
					}else if(Constant.CODE_CUS_TYP_OW.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("货主");
					}else if(Constant.CODE_CUS_TYP_CO.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("收货单位");
					}else if(Constant.CODE_CUS_TYP_WH.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("主体");
					}


					if(basCustomerVO.getClientStartDate()!=null) {
						basCustomerVO.setClientStartDateDc(sdf.format(basCustomerVO.getClientStartDate()));
					}
					if(basCustomerVO.getClientEndDate()!=null) {
						basCustomerVO.setClientEndDateDc(sdf.format(basCustomerVO.getClientEndDate()));
					}



					if(basCustomerVO.getClientTerm()!=null&& !"".equals(basCustomerVO.getClientTerm())){
						basCustomerVO.setClientTerm(basCustomerVO.getClientTerm()+"天");
					}

					if ("1".equals(basCustomerVO.getIsChineseLabel())) {
						basCustomerVO.setIsChineseLabel("是");
					}else if("0".equals(basCustomerVO.getIsChineseLabel())){
						basCustomerVO.setIsChineseLabel("否");
					}

				}



				ExcelUtil.listToExcel(basCustomerVOList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("activeFlag", "是否合作");
		superClassMap.put("customerType", "客户类型");
		superClassMap.put("customerid", "客户代码");
		superClassMap.put("descrC", "客户名称");
		superClassMap.put("enterpriseNo", "企业代码");
		superClassMap.put("shorthandName", "简称");
		superClassMap.put("socialCreditCode", "营业执照统一社会会信用代码");
		superClassMap.put("licenseNumber", "营业执照编号");
		superClassMap.put("businessStartDate", "营业执照开始时间");
		superClassMap.put("businessEndDate", "营业执照结束时间");
		superClassMap.put("prodLicenseNo", "生产许可编号");
		superClassMap.put("prodLicenseExpiryDate", "生产许可有效期");
		superClassMap.put("firstRecordNo", "一类生产备案许可编号");
		superClassMap.put("firstRecordApproveDate", "一类生产备案发证日期");
		superClassMap.put("operateLicenseNo", "经营许可证编号");
		superClassMap.put("operateLicenseExpiryDate", "经营许可有效期");
		superClassMap.put("secondRecordNo", "二类生产备案许可编号");
		superClassMap.put("secondRecordApproveDate", "二类生产备案发证日期");
		superClassMap.put("medicalRegisterNo", "医疗机构登记号许可编号");
		superClassMap.put("medicalRegisterApproveDate", "医疗机构登记号发证日期");
//		superClassMap.put("enterpriseName", "企业名称");
//		superClassMap.put("allClient", "供应商对应货主");
		superClassMap.put("contacts", "联系人");
		superClassMap.put("contactsPhone", "联系人电话");
		superClassMap.put("supContractNo", "合同编号");
		superClassMap.put("contractUrl", "合同文件");
		superClassMap.put("clientContent", "委托/合同内容");
		superClassMap.put("clientStartDateDc", "委托/合同开始时间");
		superClassMap.put("clientEndDateDc", "委托/合同结束时间");
		superClassMap.put("clientTerm", "委托/合同结束时间 ");
		superClassMap.put("isChineseLabel", "是否贴中文标签");
		//superClassMap.put("descrC", "中文描述");
		//superClassMap.put("descrE", "英文描述");
		//superClassMap.put("operateType", "类型");
		superClassMap.put("notes", "备注");
		return superClassMap;
	}
}