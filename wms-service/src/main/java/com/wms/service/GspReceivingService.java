package com.wms.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.SfcWarehouse;
import com.wms.query.BasCustomerQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.form.BasCustomerForm;
import com.wms.vo.form.FirstReviewLogForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspReceivingDao;
import com.wms.vo.GspReceivingVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspReceivingForm;
import com.wms.query.GspReceivingQuery;
import org.springframework.transaction.annotation.Transactional;

@Service("gspReceivingService")
public class GspReceivingService extends BaseService {

	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

	//@Autowired
	//private GspReceivingDao gspReceivingDao;

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;

	@Autowired
	private GspReceivingMybatisDao gspReceivingMybatisDao;

	@Autowired
	private FirstReviewLogService firstReviewLogService;

	@Autowired
	private DataPublishService dataPublishService;

	@Autowired
	private GspReceivingAddressMybatisDao gspReceivingAddressMybatisDao;


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

	public EasyuiDatagrid<GspReceivingVO> getPagedDatagrid(EasyuiDatagridPager pager, GspReceivingQuery query)  {
		EasyuiDatagrid<GspReceivingVO> datagrid = new EasyuiDatagrid<GspReceivingVO>();

		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCurrentPage(pager.getPage());
			mybatisCriteria.setPageSize(pager.getRows());
			mybatisCriteria.setOrderByClause("edit_date desc");
			mybatisCriteria.setCondition(query);
			//mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			List<GspReceiving> gspReceivingList = gspReceivingMybatisDao.queryByList(mybatisCriteria);

			List<GspReceivingVO> gspReceivingVOList = new ArrayList<GspReceivingVO>();
			GspReceivingVO gspReceivingVO = null;
			/*List<GspEnterpriseInfo> gspEnterpriseInFoList = gspEnterpriseInfoMybatisDao.queryByList(mybatisCriteria);
			if (gspEnterpriseInFoList != null) {
				for (GspEnterpriseInfo gspEnterpriseInfo: gspEnterpriseInFoList){
					gspReceivingVO = new GspReceivingVO();
					gspReceivingVO.setShorthandName(gspEnterpriseInfo.getShorthandName());
					gspReceivingVO.setEnterpriseNo(gspEnterpriseInfo.getEnterpriseNo());
					gspReceivingVO.setEnterpriseName(gspEnterpriseInfo.getShorthandName());
					gspReceivingVOList.add(gspReceivingVO);
				}
			}*/




			for (GspReceiving gspReceiving : gspReceivingList) {
                gspReceivingVO = new GspReceivingVO();


				GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryIsDefault(gspReceiving);
				GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspReceiving.getEnterpriseId());
				BasCustomer basCustomer1 = new BasCustomer();
				basCustomer1.setEnterpriseId(gspReceiving.getEnterpriseId());
				basCustomer1.setCustomerType("CO");
                BasCustomer basCustomer = null;
				try {
                     basCustomer = basCustomerMybatisDao.queryByenterId(basCustomer1);//俩条数据不能有一样的企业id和企业类型

                }catch (Exception e){

                }
//                if(basCustomer!=null){
//					json.setSuccess(true);
//					json.setMsg("该收获单位已经存在");
//					json.setMsg(resultMsg.toString());
//				return json;
//				}
                BeanUtils.copyProperties(gspReceiving, gspReceivingVO);
				if (gspReceivingAddress!=null ){

					gspReceivingVO.setDeliveryAddress(gspReceivingAddress.getDeliveryAddress());
					gspReceivingVO.setContacts(gspReceivingAddress.getContacts());
					gspReceivingVO.setPhone(gspReceivingAddress.getPhone());
					//gspReceivingVO.setDeliveryAddress(gspReceivingAddress.getDeliveryAddress());

					/*GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(gspReceiving.getClientId());

					if (gspCustomer!=null) {
						gspReceivingVO.setIsCooperation(gspCustomer.getIsCooperation());
					}*/
				}
				if ( gspEnterpriseInfo!=null){
					gspReceivingVO.setEnterpriseName(gspEnterpriseInfo.getEnterpriseName());
					gspReceivingVO.setEnterpriseNo(gspEnterpriseInfo.getEnterpriseNo());
					gspReceivingVO.setShorthandName(gspEnterpriseInfo.getShorthandName());

				}
				if (basCustomer!=null){
					gspReceivingVO.setCustomerid(basCustomer.getCustomerid());
				}
				gspReceivingVOList.add(gspReceivingVO);
			}
			datagrid.setTotal((long) gspReceivingMybatisDao.queryByCount(mybatisCriteria));
			datagrid.setRows(gspReceivingVOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datagrid;
	}
	@Transactional
	public Json addGspReceiving(GspReceivingForm gspReceivingForm,String newreceivingId) throws Exception {
		Json json = new Json();
		try {
			GspReceiving gspReceiving = new GspReceiving();

			//发起新申请
			if (StringUtils.isNotEmpty(gspReceivingForm.getReceivingId())){
				GspReceiving oldgspReceiving =gspReceivingMybatisDao.queryById(gspReceivingForm.getReceivingId());
				oldgspReceiving.setIsUse("0");
				gspReceivingMybatisDao.updateBySelective(oldgspReceiving);

				BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
				gspReceiving.setIsUse("1");
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setReceivingId(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				gspReceiving.setFirstState("00");

				gspReceivingMybatisDao.add(gspReceiving);
			}else {

//				GspReceiving gspReceiving1 =gspReceivingMybatisDao.queryByEnterpriseId(gspReceivingForm.getEnterpriseId());
//				if(gspReceiving1!=null){
//					return Json.error("同一个收货单位不能重复申请！");
//				}

				int num = gspReceivingMybatisDao.selectByClientIdAndReceiving(gspReceivingForm);
				if(num>0){
					return Json.error("同一货主和收获单位！不能重复申请！");
				}
				// 新增
				/*if (newreceivingId!=null&&newreceivingId!=""){

					BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
					gspReceiving.setIsUse("1");
					gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
					gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
					gspReceiving.setReceivingId(newreceivingId);
					gspReceiving.setFirstState("00");

				}else {*/
				BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
				gspReceiving.setIsUse("1");
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setReceivingId(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				gspReceiving.setFirstState("00");
				String id ="";
				List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryByReceivingId(id);
				if (gspReceivingAddressList != null && gspReceivingAddressList.size()!=0) {
					for (GspReceivingAddress gspReceivingAddress : gspReceivingAddressList){

						gspReceivingAddress.setReceivingId(gspReceiving.getReceivingId());
						gspReceivingAddressMybatisDao.updateBySelective(gspReceivingAddress);
					}

				}
				gspReceivingMybatisDao.add(gspReceiving);
			}



			//插入一条首营申请日志记录
			FirstReviewLog firstReviewLog = new FirstReviewLog();
			firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLog.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLog.setReviewTypeId(gspReceivingForm.getReceivingId());
			firstReviewLog.setApplyState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
			firstReviewLog.setReviewId(RandomUtil.getUUID());
			firstReviewLogMybatisDao.add(firstReviewLog);
		} catch (BeansException e) {
			throw new Exception("服务器忙!");
		}
		json.setMsg("资料添加成功");
		json.setSuccess(true);
		return json;
	}




	public Json confirmApply(GspReceivingForm gspReceivingForm) throws Exception {
		Json json = new Json();
		try {
			FirstReviewLog firstReviewLog = new FirstReviewLog();
			GspReceiving gspReceiving =	gspReceivingMybatisDao.queryById(gspReceivingForm.getReceivingId());
			if (gspReceiving == null) {
				return null;
			}
			gspReceiving.setFirstState("10");
			gspReceivingMybatisDao.updateBySelective(gspReceiving);
			firstReviewLog.setReviewTypeId(gspReceiving.getReceivingId());
			//插入一条首营申请日志记录
			firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLog.setApplyState("20");//提交审核 状态变质量部审核


			FirstReviewLog firstReviewLog111 = firstReviewLogMybatisDao.queryByreviewTypeId(gspReceivingForm.getReceivingId());
			if(firstReviewLog111!=null){
				firstReviewLog.setReviewId(firstReviewLog111.getReviewId());
				firstReviewLogMybatisDao.updateBySelective(firstReviewLog);
			}else{
				firstReviewLog.setReviewId(RandomUtil.getUUID());
				firstReviewLogMybatisDao.add(firstReviewLog);
			}
		} catch (BeansException e) {
			throw new Exception("服务器忙!");
		}

		json.setSuccess(true);
		return json;
	}

	public GspReceiving validateReceiv(String receivingId) throws Exception {

		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(receivingId);
		return gspReceiving;
	}

	public Json reApply(String receivingId) throws Exception {
		Json json = new Json();
		if(StringUtils.isEmpty(receivingId)){
			return Json.error("请选择要重新申请的数据");
		}
		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(receivingId);
		String newNo = commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		BasCustomerQuery CustomerQ = new BasCustomerQuery();
		CustomerQ.setCustomerType(Constant.CODE_CUS_TYP_OW);
		CustomerQ.setDescrC(gspReceiving.getClientId());
		BasCustomer customer = basCustomerMybatisDao.selectSupplierByIdTypeActiveFlag(CustomerQ);
		if(customer!=null){
			if("0".equals(customer.getActiveFlag())){
				return Json.error("对应的货主已不合作");
			}
		}else{
			return Json.error("货主不存在");
		}
		GspEnterpriseInfo g = gspEnterpriseInfoMybatisDao.queryNewByEnterpriseId(gspReceiving.getEnterpriseId());
		if(g==null){
			return  Json.error("收货单位的企业信息已失效");
		}


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//报废旧的首营申请
		String oldClient = gspReceiving.getClientId();
		String oldNo = receivingId;
		String oldGYSEnterpriseId = gspReceiving.getEnterpriseId();
		gspReceiving.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
		gspReceiving.setIsUse(Constant.IS_USE_NO);
		gspReceivingMybatisDao.updateBySelective(gspReceiving);
//		firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_FAIL); //发起新申请不修改GSP里这条审核通过的数据


		// 通过enterpriseId 查询该公司的最新的enterpriseId   赋值给申请
		gspReceiving.setEnterpriseId(g.getEnterpriseId());

		//新增审核状态为未通过数据
		gspReceiving.setReceivingId(newNo);
		gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		gspReceiving.setCreateDate(sdf.format(new Date()));
		gspReceiving.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
//		gspReceiving.setIsCheck(Constant.CODE_YES_OR_YES);
		gspReceiving.setIsUse(Constant.IS_USE_YES);
		gspReceivingMybatisDao.add(gspReceiving);

		//该旧收货单位下所有的收获地址   新增入新增收货单位首营中
		List<GspReceivingAddress> gspReceivingAddressList =  gspReceivingAddressMybatisDao.queryByReceivingId(receivingId);
		for(GspReceivingAddress gspReceivingAddress:gspReceivingAddressList){
			gspReceivingAddress.setReceivingId(newNo);
			gspReceivingAddress.setReceivingAddressId(RandomUtil.getUUID());

			gspReceivingAddressMybatisDao.add(gspReceivingAddress);
		}



		//插入新的日志
		FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
		firstReviewLogForm.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		firstReviewLogForm.setReviewTypeId(newNo);
		firstReviewLogForm.setReviewId(RandomUtil.getUUID());

		firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);
		//todo 内容变更
		String content ="";
//旧申请
//		GspEnterpriseInfo newHZ =  gspEnterpriseInfoMybatisDao.queryById(newC.getEnterpriseId());
		GspEnterpriseInfo oldGYS = gspEnterpriseInfoMybatisDao.selectEnterpriseByCompare(oldGYSEnterpriseId);
		GspBusinessLicense oldClientBusiness = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(oldGYSEnterpriseId);
		GspOperateLicense oldGspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId,Constant.LICENSE_TYPE_PROD);
		GspOperateLicense oldGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId,Constant.LICENSE_TYPE_OPERATE);
		GspFirstRecord oldGspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId);
		GspSecondRecord oldGspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId);
		GspMedicalRecord oldGspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId);


		//新申请
//		GspEnterpriseInfo oldHZ =  gspEnterpriseInfoMybatisDao.queryById(oldC.getEnterpriseId());
		GspEnterpriseInfo newGYS = gspEnterpriseInfoMybatisDao.selectEnterpriseByCompare(g.getEnterpriseId());
		GspBusinessLicense newClientBusiness = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(g.getEnterpriseId());
		GspOperateLicense newGspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId(),Constant.LICENSE_TYPE_PROD);
		GspOperateLicense newGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId(),Constant.LICENSE_TYPE_OPERATE);
		GspFirstRecord newGspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());
		GspSecondRecord newGspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());
		GspMedicalRecord newGspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());

		if(oldGYSEnterpriseId.equals(gspReceiving.getEnterpriseId())){
			//无变更
			content = "从申请单号"+oldNo+"变更到申请单号"+newNo+",无变更内容";
		}else{
			CompareUtil<GspEnterpriseInfo> compareUtil = new CompareUtil<GspEnterpriseInfo>();
			CompareUtil<GspBusinessLicense> compareUtilBusiness = new CompareUtil<GspBusinessLicense>();
			CompareUtil<GspOperateLicense> compareUtilprod = new CompareUtil<GspOperateLicense>();
			CompareUtil<GspFirstRecord> compareUtilFirstRecord = new CompareUtil<GspFirstRecord>();
			CompareUtil<GspSecondRecord> compareUtilSecondRecord = new CompareUtil<GspSecondRecord>();
			CompareUtil<GspMedicalRecord> compareUtilMedical = new CompareUtil<GspMedicalRecord>();


			content = "从申请单号"+oldNo+"变更到申请单号"+newNo+"["+
					compareUtil.compareT(oldGYS,newGYS,GspEnterpriseInfo.class.getName(),GspEnterpriseInfo.class.getSimpleName(),"")+
					compareUtilBusiness.compareT(oldClientBusiness,newClientBusiness,GspBusinessLicense.class.getName(),GspBusinessLicense.class.getSimpleName(),"")+
					compareUtilprod.compareT(oldGspProdLicense,newGspProdLicense,GspOperateLicense.class.getName(),GspOperateLicense.class.getSimpleName(),"prod")+
					compareUtilprod.compareT(oldGspOperateLicense,newGspOperateLicense,GspOperateLicense.class.getName(),GspOperateLicense.class.getSimpleName(),"operate")+
					compareUtilFirstRecord.compareT(oldGspFirstRecord,newGspFirstRecord,GspFirstRecord.class.getName(),GspFirstRecord.class.getSimpleName(),"")+
					compareUtilSecondRecord.compareT(oldGspSecondRecord,newGspSecondRecord,GspSecondRecord.class.getName(),GspSecondRecord.class.getSimpleName(),"")+
					compareUtilMedical.compareT(oldGspMedicalRecord,newGspMedicalRecord,GspMedicalRecord.class.getName(),GspMedicalRecord.class.getSimpleName(),"")+
					"]";
		}





		firstReviewLogForm.setApplyContent(content);
		firstReviewLogService.addFirstReviewLog(firstReviewLogForm);

		//TODO 审核通过的数据需要更新为失败(待测试)
		dataPublishService.cancelData(receivingId);
		json.setMsg("发起新申请成功");
		json.setSuccess(true);
		return json;
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
					result ="无变更内容";
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
							result = " 原收货单位"+GspEnterpriseInfoChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为"+": "+(value2==null|| value2==""? "无":value2);
						}else if("GspBusinessLicense".equals(simpleClassName)){

							result = " 原收货单位"+GspBusinessLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}else if("GspOperateLicense".equals(simpleClassName)){
							if("prod".equals(type)){
								result = " 原收货单位"+GspProdLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
										",变更为 "+":"+(value2==null || value2==""? "无":value2);
							}else if("operate".equals(type)){
								result = " 原收货单位"+GspOpreateLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
										",变更为 "+":"+(value2==null || value2==""? "无":value2);
							}
						}else if("GspFirstRecord".equals(simpleClassName)){
							result = " 原收货单位"+GspFirstRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);

						}else if("GspSecondRecord".equals(simpleClassName)){
							result = " 原收货单位"+GspSecondRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}else if("GspMedicalRecord".equals(simpleClassName)){
							result = " 原收货单位"+GspMedicalRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}

						list.add(result);
					}else {

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



	public Json editGspReceiving(GspReceivingForm gspReceivingForm) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(gspReceivingForm.getReceivingId());
		//java.lang.IllegalArgumentException: Target must not be null
		BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
		gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
        gspReceivingMybatisDao.updateBySelective(gspReceiving);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspReceiving(String id) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(id);
		if(gspReceiving != null){
            gspReceivingMybatisDao.delete(gspReceiving);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspReceivingCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspReceiving> gspReceivingList = gspReceivingMybatisDao.queryListByAll();
		if(gspReceivingList != null && gspReceivingList.size() > 0){
			for(GspReceiving gspReceiving : gspReceivingList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspReceiving.getReceivingId()));
				combobox.setValue(gspReceiving.getSupplierId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public GspReceiving getGspReceiving(String id){
		return gspReceivingMybatisDao.queryById(id);
	}

	/**
	 * 更新首营状态
	 * @param no
	 * @param state
	 * @return
	 */
	public Json updateFirstState(String no,String state){
		Long result = gspReceivingMybatisDao.updateFirstState(no,state);
		if(result>0){
			return Json.success("更新申请单首营状态成功");
		}
		return Json.error("更新申请单首营状态失败");
	}

/*	public Json addBasCustomer(BasCustomerForm basCustomerForm) throws Exception {
		*//*Json json = null;
		try {


			StringBuilder resultMsg = new StringBuilder();
			BasCustomerService.validateCustomer(basCustomerForm, resultMsg);// 验证客户是否存在

			json = new Json();


			if (resultMsg.length() == 0) {
				BasCustomer basCustomer = new BasCustomer();

				if (StringUtils.isNotEmpty(basCustomerForm.getReceivingId())){
					GspReceiving oldgspReceiving =gspReceivingMybatisDao.queryById(basCustomerForm.getReceivingId());
					oldgspReceiving.setIsUse("0");
					gspReceivingMybatisDao.updateBySelective(oldgspReceiving);
				}
				BasCustomerQuery customerQuery = new BasCustomerQuery();
				customerQuery.setEnterpriseId(basCustomerForm.getEnterpriseId());
				customerQuery.setCustomerType(basCustomerForm.getCustomerType());


				BasCustomer oldbasCustomer=basCustomerMybatisDao.queryById(customerQuery);

				if (oldbasCustomer != null) {
					BasSkuHistory basSkuHistory = new BasSkuHistory();
					BeanUtils.copyProperties(oldbasCustomer,basSkuHistory);
					basCustomerMybatisDao.deleteBascustomer(oldbasCustomer.getEnterpriseId(),oldbasCustomer.getCustomerType());
					basSkuHistoryMybatisDao.add(basSkuHistory);
				}
				//下发到客户档案
				BeanUtils.copyProperties(basCustomerForm, basCustomer);
				basCustomer.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
				basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
				basCustomer.setCustomerid(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				basCustomer.setEnterpriseId(basCustomerForm.getEnterpriseId());
				basCustomer.setActiveFlag(basCustomerForm.getIsUse());
				//
				basCustomerMybatisDao.add(basCustomer);
				GspReceiving gspReceiving = new GspReceiving();

				BeanUtils.copyProperties(basCustomerForm,gspReceiving);
				gspReceiving.setFirstState("40");
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				if (StringUtils.isNotEmpty(basCustomerForm.getNewreceivingId())){

					gspReceiving.setReceivingId(basCustomerForm.getNewreceivingId());
				}else {

					gspReceiving.setReceivingId(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				}
				gspReceivingMybatisDao.add(gspReceiving);

				//插入一条首营申请日志记录
				FirstReviewLog firstReviewLog = new FirstReviewLog();
				firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstReviewLog.setReviewTypeId(basCustomerForm.getReceivingId());
				firstReviewLog.setApplyContent("不需要申请直接下发");
				firstReviewLog.setApplyState("40");
				firstReviewLog.setReviewId(RandomUtil.getUUID());
				firstReviewLogMybatisDao.add(firstReviewLog);


			} else {
				json.setSuccess(false);
				json.setMsg(resultMsg.toString());
				return json;
			}
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
		} catch (BeansException e) {
			throw new Exception("系统忙！");
		}
		return json;
	}*//*
		return basCustomerService.xiaFaBasCustomer(basCustomerForm);

	}*/

}