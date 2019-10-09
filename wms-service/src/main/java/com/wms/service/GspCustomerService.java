package com.wms.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;

import com.wms.utils.DateUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.form.FirstReviewLogForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.vo.GspCustomerVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspCustomerForm;
import com.wms.query.GspCustomerQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("gspCustomerService")
public class GspCustomerService extends BaseService {

	@Autowired
	private GspCustomerMybatisDao gspCustomerMybatisDao;
	@Autowired
	private  FirstReviewLogMybatisDao firstReviewLogMybatisDao;
	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;
	@Autowired
	private FirstReviewLogService firstReviewLogService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private DataPublishService dataPublishService;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;
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

	public EasyuiDatagrid<GspCustomerVO> getPagedDatagrid(EasyuiDatagridPager pager, GspCustomerQuery query) {
		EasyuiDatagrid<GspCustomerVO> datagrid = new EasyuiDatagrid<GspCustomerVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		criteria.setOrderByClause("create_date desc");
		List<GspCustomer> gspCustomerList = gspCustomerMybatisDao.queryByList(criteria);
		GspCustomerVO gspCustomerVO = null;
		List<GspCustomerVO> gspCustomerVOList = new ArrayList<GspCustomerVO>();
		for (GspCustomer gspCustomer : gspCustomerList) {
			gspCustomerVO = new GspCustomerVO();
			BeanUtils.copyProperties(gspCustomer, gspCustomerVO);
			gspCustomerVOList.add(gspCustomerVO);
		}
		int count = gspCustomerMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspCustomerVOList);
		return datagrid;
	}
	//增加
	public Json addGspCustomer(GspCustomerForm gspCustomerForm) throws Exception {
		try{

			Integer result = gspCustomerMybatisDao.queryGspCustomerByClientNo(gspCustomerForm.getClientNo());
			if(result>0){
				return Json.error("同一个企业不能重复申请");
			}

			String no = commonService.generateSeq(Constant.APLCUSNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			Json json = new Json();
			GspCustomer gspCustomer = new GspCustomer();
			BeanUtils.copyProperties(gspCustomerForm, gspCustomer);
			gspCustomer.setClientId(no);
			gspCustomer.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			gspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
			gspCustomer.setIsCheck(Constant.CODE_YES_OR_YES);
			gspCustomer.setIsCooperation(Constant.CODE_YES_OR_YES);
			gspCustomer.setClientStartDate(DateUtil.parse(gspCustomerForm.getClientStartDate(),"yyyy-MM-dd"));
			gspCustomer.setClientEndDate(DateUtil.parse(gspCustomerForm.getClientEndDate(),"yyyy-MM-dd"));
			gspCustomer.setEmpowerEndDate(DateUtil.parse(gspCustomerForm.getEmpowerEndDate(),"yyyy-MM-dd"));
			gspCustomer.setEmpowerStartDate(DateUtil.parse(gspCustomerForm.getEmpowerStartDate(),"yyyy-MM-dd"));

			gspCustomer.setIsUse(Constant.IS_USE_YES);
			if(" value=".equals(gspCustomer.getIdCardFront())){
				gspCustomer.setIdCardFront("");
			}
			if(" value=".equals(gspCustomer.getIdCardBack())){
				gspCustomer.setIdCardBack("");
			}if(" value=".equals(gspCustomer.getContractUrl())){
				gspCustomer.setContractUrl("");
			}if(" value=".equals(gspCustomer.getEmpowerPhoto())){

				gspCustomer.setEmpowerPhoto("");
			}
			gspCustomerMybatisDao.add(gspCustomer);

			FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
			firstReviewLogForm.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLogForm.setReviewTypeId(no);
			firstReviewLogForm.setReviewId(RandomUtil.getUUID());
			firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);
			GspEnterpriseInfo HZ =gspEnterpriseInfoMybatisDao.queryById(gspCustomerForm.getEnterpriseId());
			String content = "货主企业名称:"+HZ.getEnterpriseName()+" "+
							 "货主企业代码:"+HZ.getEnterpriseNo()+" "+
					 		 "货主企业类型:"+regred(HZ.getEnterpriseType());
			firstReviewLogForm.setApplyContent(content);
			firstReviewLogService.addFirstReviewLog(firstReviewLogForm);

			json.setSuccess(true);
            json.setMsg("资料处理成功!");
			return json;
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("保存失败");
		}

	}



	//企业类型翻译
	public  Object  regred(String entertype){
		switch(entertype){
			case "GNSC": entertype = "生产";
				break;
			case "JY": entertype = "经营";
				break;
			case "YL": entertype = "医疗机构";
				break;
			case "ZT": entertype = "主体";
				break;
			case "GW": entertype = "国外企业";
				break;
			case "SCJY": entertype = "生产和经营";

		}
		return entertype;
	}
//修改
	public Json editGspCustomer(GspCustomerForm gspCustomerForm) throws Exception {
		Json json = new Json();
		GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(gspCustomerForm.getClientId());
		if(gspCustomer ==null){
			return Json.error("没有查询到对应的申请单号");
		}
		//换证应允许报废企业的委托方客户
		if(!Constant.CODE_CATALOG_FIRSTSTATE_NEW.equals(gspCustomer.getFirstState()) && !Constant.CODE_CATALOG_FIRSTSTATE_PASS.equals(gspCustomer.getFirstState())){

			return Json.error("审核中的单据无法修改");
		}

		BeanUtils.copyProperties(gspCustomerForm, gspCustomer);

		gspCustomer.setClientStartDate(DateUtil.parse(gspCustomerForm.getClientStartDate(),"yyyy-MM-dd"));
		gspCustomer.setClientEndDate(DateUtil.parse(gspCustomerForm.getClientEndDate(),"yyyy-MM-dd"));
		gspCustomer.setEmpowerEndDate(DateUtil.parse(gspCustomerForm.getEmpowerEndDate(),"yyyy-MM-dd"));
		gspCustomer.setEmpowerStartDate(DateUtil.parse(gspCustomerForm.getEmpowerStartDate(),"yyyy-MM-dd"));

		gspCustomerMybatisDao.updateBySelective(gspCustomer);
		json.setSuccess(true);
		json.setMsg("资料处理成功!");
		return json;
	}

	public Json deleteGspCustomer(String id) {
		Json json = new Json();
		GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(id);
		if(gspCustomer ==null){
			return Json.error("没有查询到对应的申请单号");
		}

		if(!gspCustomer.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_NEW)){
			return Json.error("审核中的单据无法修改");
		}
		if(gspCustomer != null){
			gspCustomerMybatisDao.delete(gspCustomer);
		}
		json.setSuccess(true);
		json.setMsg("资料处理成功!");
		return json;
	}

	public List<EasyuiCombobox> getGspCustomerCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspCustomer> gspCustomerList = gspCustomerMybatisDao.queryByAll();
		if(gspCustomerList != null && gspCustomerList.size() > 0){
			for(GspCustomer gspCustomer : gspCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspCustomer.getClientId()));
				combobox.setValue(gspCustomer.getClientName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public Json getGspCustomerById(String id){
		GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(id);
		if(gspCustomer != null){
			GspCustomerVO gspCustomerVO = new GspCustomerVO();
			BeanUtils.copyProperties(gspCustomer,gspCustomerVO);
			//gspCustomerVO.setClientStartDate(DateUtil.format(gspCustomer.getClientStartDate()));
			//gspCustomerVO.setClientEndDate(DateUtil.format(gspCustomer.getClientEndDate()));
			GspEnterpriseInfo info = gspEnterpriseInfoService.getGspEnterpriseInfo(id);
			if(info!=null){
				gspCustomerVO.setClientNo(info.getEnterpriseNo());
				gspCustomerVO.setClientName(info.getShorthandName());
			}
			return Json.success("",gspCustomerVO);
		}
		return Json.error("");
	}

	public Json confirmSubmit(String id){
		try{
			if(StringUtils.isEmpty(id)){
				return Json.error("请选择要确认的数据");
			}
			String[] arr = id.split(",");
			for(String s : arr){
				GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(s);
				gspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING);
				gspCustomerMybatisDao.updateBySelective(gspCustomer);


//				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING);
				FirstReviewLog f = new FirstReviewLog();
				f.setReviewTypeId(s);
				f.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING);
				f.setCheckDateQc(null);
				f.setCheckIdQc("");
				f.setCheckRemarkQc("");
				f.setCheckIdHead("");
				f.setCheckDateHead(null);
				f.setCheckRemarkHead("");
				firstReviewLogMybatisDao.updateBytiJIAOSHENH(f);

			}
			return Json.success("确认成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}

	}

	public Json reApply(String id){
		try{
			if(StringUtils.isEmpty(id)){
				return Json.error("请选择要重新申请的数据");
			}

			String[] arr = id.split(",");
			for(String s : arr){
				String no = commonService.generateSeq(Constant.APLCUSNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId());

				//更新旧数据状态
				GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(s);
//				String oldNo =  gspCustomer.getClientId();
				String oldEnterpriseId = gspCustomer.getEnterpriseId();


				GspEnterpriseInfo g = gspEnterpriseInfoMybatisDao.queryNewByEnterpriseId(gspCustomer.getEnterpriseId());
				if(g==null){
					return  Json.error("企业信息已失效");
				}

				Integer result = gspCustomerMybatisDao.queryGspCustomerByClientNo(gspCustomer.getClientNo());
				if(result>0){
					return Json.error("同一个企业不能重复申请");
				}

				String oldNo = gspCustomer.getClientId();

				gspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
				gspCustomer.setIsUse(Constant.IS_USE_NO);
				gspCustomerMybatisDao.updateBySelective(gspCustomer);
				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_FAIL);


                // 通过enterpriseId 查询该公司的最新的enterpriseId
				gspCustomer.setEnterpriseId(g.getEnterpriseId());


				//新增审核状态为未通过数据
				gspCustomer.setClientId(no);
				gspCustomer.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
				gspCustomer.setIsCheck(Constant.CODE_YES_OR_YES);
				gspCustomer.setIsCooperation(Constant.CODE_YES_OR_YES);
				gspCustomer.setIsUse(Constant.IS_USE_YES);
				gspCustomerMybatisDao.add(gspCustomer);

				//插入新的日志
				FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
				firstReviewLogForm.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstReviewLogForm.setReviewTypeId(no);
				firstReviewLogForm.setReviewId(RandomUtil.getUUID());
				firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);
				GspEnterpriseInfo HZ =gspEnterpriseInfoMybatisDao.queryById(gspCustomer.getEnterpriseId());
				//旧货主信息
				BasCustomer b = basCustomerMybatisDao.querySupplierByBankaccount(oldNo); //必须有
				GspEnterpriseInfo oldClient= gspEnterpriseInfoMybatisDao.selectEnterpriseByCompare(b.getEnterpriseId());
				GspBusinessLicense oldClientBusiness = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(b.getEnterpriseId());
				GspOperateLicense oldGspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(b.getEnterpriseId(),Constant.LICENSE_TYPE_PROD);
				GspOperateLicense oldGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(b.getEnterpriseId(),Constant.LICENSE_TYPE_OPERATE);
				GspFirstRecord oldGspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(b.getEnterpriseId());
				GspSecondRecord oldGspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(b.getEnterpriseId());
				GspMedicalRecord oldGspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(b.getEnterpriseId());

				//新货主信息
				GspEnterpriseInfo newClient = gspEnterpriseInfoMybatisDao.selectEnterpriseByCompare(g.getEnterpriseId());
				GspBusinessLicense newClientBusiness = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(g.getEnterpriseId());
				GspOperateLicense newGspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId(),Constant.LICENSE_TYPE_PROD);
				GspOperateLicense newGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId(),Constant.LICENSE_TYPE_OPERATE);
				GspFirstRecord newGspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());
				GspSecondRecord newGspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());
				GspMedicalRecord newGspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());


				String content = "";
				if(g.getEnterpriseId().equals(b.getEnterpriseId())){
					//无变更
					content = "从申请单号"+oldNo+"变更到申请单号"+no+",无变更内容";
				}else{
					CompareUtil<GspEnterpriseInfo> compareUtil = new CompareUtil<GspEnterpriseInfo>();
					CompareUtil<GspBusinessLicense> compareUtilBusiness = new CompareUtil<GspBusinessLicense>();
					CompareUtil<GspOperateLicense> compareUtilprod = new CompareUtil<GspOperateLicense>();
					CompareUtil<GspFirstRecord> compareUtilFirstRecord = new CompareUtil<GspFirstRecord>();
					CompareUtil<GspSecondRecord> compareUtilSecondRecord = new CompareUtil<GspSecondRecord>();
					CompareUtil<GspMedicalRecord> compareUtilMedical = new CompareUtil<GspMedicalRecord>();


					content = "从申请单号"+oldNo+"变更到申请单号"+no+"["+
							compareUtil.compareT(oldClient,newClient,GspEnterpriseInfo.class.getName(),GspEnterpriseInfo.class.getSimpleName(),"")+
							compareUtilBusiness.compareT(oldClientBusiness,newClientBusiness,GspBusinessLicense.class.getName(),GspBusinessLicense.class.getSimpleName(),"")+
							compareUtilprod.compareT(oldGspProdLicense,newGspProdLicense,GspOperateLicense.class.getName(),GspOperateLicense.class.getSimpleName(),"prod")+
							compareUtilprod.compareT(oldGspOperateLicense,newGspOperateLicense,GspOperateLicense.class.getName(),GspOperateLicense.class.getSimpleName(),"operate")+
							compareUtilFirstRecord.compareT(oldGspFirstRecord,newGspFirstRecord,GspFirstRecord.class.getName(),GspFirstRecord.class.getSimpleName(),"")+
							compareUtilSecondRecord.compareT(oldGspSecondRecord,newGspSecondRecord,GspSecondRecord.class.getName(),GspSecondRecord.class.getSimpleName(),"")+
							compareUtilMedical.compareT(oldGspMedicalRecord,newGspMedicalRecord,GspMedicalRecord.class.getName(),GspMedicalRecord.class.getSimpleName(),"")+
							"]";
				}
				//没下发的新货主企业信息
				firstReviewLogForm.setApplyContent(content);

				firstReviewLogService.addFirstReviewLog(firstReviewLogForm);
				//TODO 审核通过的数据需要更新为失败(待测试)
                dataPublishService.cancelData(oldNo);

			}
			return Json.success("重新申请成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}

	}

	public class CompareUtil<T> {

		public List<String> compareT(T t1,T t2,String className,String simpleClassName,String type){
			List<String> list = new ArrayList<String>();
			//内容没改变直接返回
			String result ="";
			String s = "1";
			String b = "1";
			if(t1!=null){
				if(t1.equals(t2)){
					list.add(result);
					return list;
				}
			}else {
				s = "0";
			}
			if(t2==null){
				b= "0";
			}
			try {
				Class c = Class.forName(className);
				//读取t1和t2中的所有属性
				Field[] fields = c.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field field =fields[i];
					field.setAccessible(true);
					Object value1=null;
					Object value2=null;
					if("1".equals(s)){
						value1=field.get(t1);
					}
//					field.get(t1);
					if("1".equals(b)){
						value2=field.get(t2);
					}
					Date date = new Date();
					SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
					try{
						value2 = sdf.format(value2);
					}catch (Exception e){
					}
					try{
						value1 = sdf.format(value1);
					}catch (Exception e){
					}
					//判断这两个值是否相等
					if(!isValueEquals(value1,value2)){
//						Map map = new HashMap();
//						map.put("name",field.getName());
//						map.put("before",value1);
//						map.put("after",value2);
//						list.add(map);
						if("GspEnterpriseInfo".equals(simpleClassName)){
							result = " 原货主"+GspEnterpriseInfoChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为"+": "+(value2==null|| value2==""? "无":value2);
						}else if("GspBusinessLicense".equals(simpleClassName)){

							result = " 原货主"+GspBusinessLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}else if("GspOperateLicense".equals(simpleClassName)){
							if("prod".equals(type)){
								result = " 原货主"+GspProdLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
										",变更为 "+":"+(value2==null || value2==""? "无":value2);
							}else if("operate".equals(type)){
								result = " 原货主"+GspOpreateLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
										",变更为 "+":"+(value2==null || value2==""? "无":value2);
							}
						}else if("GspFirstRecord".equals(simpleClassName)){
							result = " 原货主"+GspFirstRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);

						}else if("GspSecondRecord".equals(simpleClassName)){
							result = " 原货主"+GspSecondRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
							",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}else if("GspMedicalRecord".equals(simpleClassName)){
							result = " 原货主"+GspMedicalRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}

						list.add(result);
					}

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return list;
		}
		private boolean isValueEquals(Object value1, Object value2) {
			if(value1==null&&value2==null){
				return true;
			}
			if(value1==null&&value2!=null){
				return false;
			}
			if(value1.equals(value2)){
				return true;
			}
			return false;
		}
		public String GspEnterpriseInfoChange(String zd){
			if("enterpriseNo".equals(zd))return "企业代码";
			if("shorthandName".equals(zd))return "企业简称";
			if("enterpriseName".equals(zd))return "企业名称";
			if("enterpriseType".equals(zd))return "企业类型";
			if("contacts".equals(zd))return "企业联系人";
			if("contactsPhone".equals(zd))return "企业联系人电话";
			return "";
		}
		public String GspBusinessLicenseChange(String zd){
			if("socialCreditCode".equals(zd))return "营业执照统一社会信用代码";
			if("licenseNumber".equals(zd))return "营业执照证照编号";
			if("licenseName".equals(zd))return "名称";
			if("licenseType".equals(zd))return "类型";
			if("juridicalPerson".equals(zd))return "法定代表人";
			if("registeredCapital".equals(zd))return "注册资本";
			if("residence".equals(zd))return "住所";
			if("registrationAuthority".equals(zd))return "登记机关";
			if("establishmentDate".equals(zd))return "成立日期";
			if("issueDate".equals(zd))return "发证日期";
			if("businessStartDate".equals(zd))return "营业期限开始时间";
			if("businessEndDate".equals(zd))return "营业期限结束时间";
			if("attachmentUrl".equals(zd))return "营业执照照片";
			if("businessScope".equals(zd))return "经营范围";
			return "";
		}
		public String GspProdLicenseChange(String zd){
			if("licenseNo".equals(zd))return "生产许可证编号";
			if("juridicalPerson".equals(zd))return "法定代表人";
			if("businessResidence".equals(zd))return "生产场所";
			if("headName".equals(zd))return "企业负责人";
			if("residence".equals(zd))return "住所";
			if("warehouseAddress".equals(zd))return "库房地址";
			if("registrationAuthority".equals(zd))return "发证部门";
			if("approveDate".equals(zd))return "发证日期";
			if("licenseUrl".equals(zd))return "生产许可证照片";
			if("licenseExpiryDate".equals(zd))return "有效期限";
			if("businessScope".equals(zd))return "生产范围";
			return "";
		}
		public String GspOpreateLicenseChange(String zd){
			if("licenseNo".equals(zd))return "经营许可证编号";
			if("juridicalPerson".equals(zd))return "法定代表人";
			if("businessResidence".equals(zd))return "经营场所";
			if("headName".equals(zd))return "企业负责人";
			if("residence".equals(zd))return "住所";
			if("operateMode".equals(zd))return "经营方式";
			if("warehouseAddress".equals(zd))return "库房地址";
			if("registrationAuthority".equals(zd))return "发证部门";
			if("approveDate".equals(zd))return "发证日期";
			if("licenseUrl".equals(zd))return "许可证照片";
			if("licenseExpiryDate".equals(zd))return "有效期限";
			if("businessScope".equals(zd))return "经营范围";
			return "";
		}
		public String GspFirstRecordChange(String zd){
			if("recordNo".equals(zd))return "一类备案编号";
			if("residence".equals(zd))return "住所";
			if("reservedfield1".equals(zd))return "生产场所";
			if("reservedfield2".equals(zd))return "法定代表人";
			if("headName".equals(zd))return "企业负责人";
			if("registrationAuthority".equals(zd))return "备案部门";
			if("approveDate".equals(zd))return "发证日期";
			if("recordUrl".equals(zd))return "备案照片";
			if("businessScope".equals(zd))return "生产范围";
			return "";
		}
		public String GspSecondRecordChange(String zd){
			if("recordNo".equals(zd))return "二类备案编号";
			if("juridicalPerson".equals(zd))return "法定代表人";
			if("residence".equals(zd))return "住所";
			if("headName".equals(zd))return "企业负责人";
			if("operatePlace".equals(zd))return "经营场所";
			if("operateMode".equals(zd))return "经营方式";
			if("warehouseAddress".equals(zd))return "库房地址";
			if("registrationAuthority".equals(zd))return "备案部门";
			if("approveDate".equals(zd))return "备案批准日期";
			if("recordUrl".equals(zd))return "备案照片";
			if("businessScope".equals(zd))return "经营范围";
			return "";
		}
		public String GspMedicalRecordChange(String zd){
			if("medicalName".equals(zd))return "医疗机构名称";
			if("medicalRegisterNo".equals(zd))return "医疗机构登记号";
			if("medicalAddress".equals(zd))return "地址";
			if("juridicalPerson".equals(zd))return "法定代表人";
			if("registrationAuthority".equals(zd))return "发证机关";
			if("headName".equals(zd))return "主要负责人";
			if("approveDate".equals(zd))return "发证日期";
			if("recordUrl".equals(zd))return "许可证照片";
			if("licenseExpiryDateBegin".equals(zd))return "有效期限起始时间";
			if("licenseExpiryDateEnd".equals(zd))return "有效期限结束时间";
			return "";
		}
	}








	/**
	 * 更新首营状态
	 * @param no
	 * @param state
	 * @return
	 */
	public Json updateFirstState(String no,String state){
		Long result = gspCustomerMybatisDao.updateFirstState(no,state);
		if(result>0){
			return Json.success("更新委托客户申请单首营状态成功");
		}
		return Json.error("更新委托客户申请单首营状态失败");
	}

}