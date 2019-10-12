package com.wms.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCustomerQuery;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.form.FirstReviewLogForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspSupplierDao;
import com.wms.vo.GspSupplierVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspSupplierForm;
import com.wms.query.GspSupplierQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("gspSupplierService")
public class GspSupplierService extends BaseService {

	//@Autowired
	//private GspSupplierDao gspSupplierDao;
	@Autowired
	private GspSupplierMybatisDao gspSupplierMybatisDao;
	@Autowired
	private CommonService commonService;
	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;
	@Autowired
	private FirstReviewLogService firstReviewLogService;
	@Autowired
	private DataPublishService dataPublishService;
    @Autowired
    private GspVerifyService gspVerifyService;
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

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public EasyuiDatagrid<GspSupplierVO> getPagedDatagrid(EasyuiDatagridPager pager, GspSupplierQuery query) {
//		EasyuiDatagrid<GspSupplierVO> datagrid = new EasyuiDatagrid<GspSupplierVO>();
//		List<GspSupplier> gspSupplierList = gspSupplierDao.getPagedDatagrid(pager, query);
//		GspSupplierVO gspSupplierVO = null;
//		List<GspSupplierVO> gspSupplierVOList = new ArrayList<GspSupplierVO>();
//		for (GspSupplier gspSupplier : gspSupplierList) {
//			gspSupplierVO = new GspSupplierVO();
//			BeanUtils.copyProperties(gspSupplier, gspSupplierVO);
//			gspSupplierVOList.add(gspSupplierVO);
//		}
//		datagrid.setTotal(gspSupplierDao.countAll(query));
//		datagrid.setRows(gspSupplierVOList);
//		return datagrid;
		//System.out.println(query.getOperateType()+"========query.getOperateType()=======");
		EasyuiDatagrid<GspSupplierVO> datagrid = new EasyuiDatagrid<GspSupplierVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		criteria.setOrderByClause("create_date desc");
		GspSupplierVO gspSupplierVO = null;
		List<GspSupplierVO> basGtnVOList = new ArrayList<GspSupplierVO>();
		List<GspSupplier> basGtnList = gspSupplierMybatisDao.queryByList(criteria);
		for (GspSupplier gspSupplier : basGtnList) {
			gspSupplierVO = new GspSupplierVO();
			BeanUtils.copyProperties(gspSupplier, gspSupplierVO);
			if(gspSupplier.getCreateDate()!=null){
				gspSupplierVO.setCreateDate(simpleDateFormat.format(gspSupplier.getCreateDate()));
			}
			if(gspSupplier.getEditDate()!=null){
				gspSupplierVO.setEditDate(simpleDateFormat.format(gspSupplier.getEditDate()));
			}
			if(gspSupplier.getClientStartDate()!=null){
				gspSupplierVO.setClientStartDate(simpleDateFormat.format(gspSupplier.getClientStartDate()));
			}
			if(gspSupplier.getClientEndDate()!=null){
				gspSupplierVO.setClientEndDate(simpleDateFormat.format(gspSupplier.getClientEndDate()));
			}

			basGtnVOList.add(gspSupplierVO);
		}

		int total = gspSupplierMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}

    @Transactional
	public Json addGspSupplier(GspSupplierForm gspSupplierForm) throws Exception {


        Json checkScopeResult =gspVerifyService.verifyOperate(gspSupplierForm.getCostomerid(),gspSupplierForm.getEnterpriseId(),"");
		if(!checkScopeResult.isSuccess()){
			return checkScopeResult;
		}


	    System.out.println("gspSupplierForm.getContractUrl()===="+gspSupplierForm.getContractUrl());
		Json json = new Json();
		GspSupplier gspSupplier = new GspSupplier();
		BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
		System.out.println("SfcUserLoginUtil.getLoginUser().getId()========="+SfcUserLoginUtil.getLoginUser().getId());
		gspSupplier.setSupplierId(commonService.generateSeq(Constant.APLSUPNO, SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
		FirstReviewLog firstReviewLog = new FirstReviewLog();
		firstReviewLog.setReviewId(RandomUtil.getUUID());
		firstReviewLog.setReviewTypeId(gspSupplier.getSupplierId());
		firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		firstReviewLog.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		firstReviewLog.setCreateDate(new Date());
		firstReviewLog.setEditDate(new Date());
		if("1".equals(gspSupplier.getIsCheck())){
			firstReviewLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);//审核状态 新建 代码
			gspSupplier.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);//首营状态 新建 代码
		}else if("0".equals(gspSupplier.getIsCheck())){
			firstReviewLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_PASS);//审核状态 已通过 代码
			gspSupplier.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_PASS);//首营状态 审核通过 代码
			firstReviewLog.setApplyContent("无需审核");
		}

		int num =gspSupplierMybatisDao.countByEnterpriseIdAndClient(gspSupplier.getEnterpriseId(),gspSupplier.getCostomerid());
		if(num>0){
			return Json.error("存在同一个供应商企业和货主,不能重复申请!!!");
		}


		BasCustomer b = new BasCustomer();
		b.setCustomerid(gspSupplierForm.getCostomerid());
		b.setCustomerType(Constant.CODE_CUS_TYP_OW);
		BasCustomer C =basCustomerMybatisDao.selectByIdTypeActiveFlag(b);
		GspEnterpriseInfo HZ =gspEnterpriseInfoMybatisDao.queryById(C.getEnterpriseId());
		GspEnterpriseInfo GYS = gspEnterpriseInfoMybatisDao.queryById(gspSupplierForm.getEnterpriseId());




		String content =
		"供应商企业名称:"+GYS.getEnterpriseName()+" "+"供应商企业代码:"+GYS.getEnterpriseNo()+" "+
		"供应商企业类型:"+regred(GYS.getEnterpriseType())+";"+"对应货主企业名称:"+HZ.getEnterpriseName()+" "+
		"对应货主企业代码:"+HZ.getEnterpriseNo()+" "+"对应货主企业类型:"+regred(HZ.getEnterpriseType());
		firstReviewLog.setApplyContent(content);
		firstReviewLogMybatisDao.add(firstReviewLog);

		gspSupplierMybatisDao.add(gspSupplier);
		json.setSuccess(true);
		return Json.success("添加成功");
//		return json;
	}

	//企业类型翻译
	public  String  regred(String entertype){
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

	public Json editGspSupplier(GspSupplierForm gspSupplierForm) {
//        Json checkScopeResult =gspVerifyService.verifyOperate(gspSupplierForm.getCostomerid(),gspSupplierForm.getEnterpriseId(),"");
//        if(!checkScopeResult.isSuccess()){
//            return checkScopeResult;
//        }

		Json json = new Json();
		//GspSupplier gspSupplier = gspSupplierDao.findById(gspSupplierForm.getSupplierId());
		//BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
		gspSupplierMybatisDao.updateBySelective(gspSupplierForm);
		return Json.success("修改成功");
//		json.setSuccess(true);
//		return json;
	}

    public Json editGspSupplierVerify(GspSupplierForm gspSupplierForm) {
        Json checkScopeResult =gspVerifyService.verifyOperate(gspSupplierForm.getCostomerid(),gspSupplierForm.getEnterpriseId(),"");
        if(!checkScopeResult.isSuccess()){
            return checkScopeResult;
        }

        Json json = new Json();
        //GspSupplier gspSupplier = gspSupplierDao.findById(gspSupplierForm.getSupplierId());
        //BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
        gspSupplierMybatisDao.updateBySelective(gspSupplierForm);
        return Json.success("修改成功");
//		json.setSuccess(true);
//		return json;
    }

    public Json commitGspSupplier(GspSupplierForm gspSupplierForm) {
        Json json = new Json();
        //GspSupplier gspSupplier = gspSupplierDao.findById(gspSupplierForm.getSupplierId());
        //BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
        gspSupplierMybatisDao.updateBySelective(gspSupplierForm);
        return Json.success("修改成功");
//		json.setSuccess(true);
//		return json;
    }
	public Json getGspSupplierInfo(String supplierId){
		GspSupplierVO gspSupplierVO = new GspSupplierVO();
		System.out.println("supplierId==========="+supplierId);
		GspSupplier gspSupplier = gspSupplierMybatisDao.queryById(supplierId);
		BeanUtils.copyProperties(gspSupplier, gspSupplierVO);
		gspSupplierVO.setEditDate(simpleDateFormat.format(new Date()));
		gspSupplierVO.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		if(gspSupplier.getCreateDate()!=null){
            gspSupplierVO.setCreateDate(simpleDateFormat.format(gspSupplier.getCreateDate()));
        }
		if(gspSupplier.getClientStartDate()!=null){
			gspSupplierVO.setClientStartDate(simpleDateFormat.format(gspSupplier.getClientStartDate()));
		}
		if(gspSupplier.getClientEndDate()!=null){
			gspSupplierVO.setClientEndDate(simpleDateFormat.format(gspSupplier.getClientEndDate()));
		}
		if(gspSupplier.getEmpowerEnddate()!=null){
			gspSupplierVO.setEmpowerEnddate(simpleDateFormat.format(gspSupplier.getEmpowerEnddate()));
		}
		if(gspSupplier.getEmpowerStartdate()!=null){
			gspSupplierVO.setEmpowerStartdate(simpleDateFormat.format(gspSupplier.getEmpowerStartdate()));
		}
		//System.out.println("gspSupplierVO============="+gspSupplierVO.getCreateDate()+"==========="+gspSupplierVO.getCreateDate());
		if(gspSupplier == null){
			return Json.error("不存在！");
		}

		return Json.success("",gspSupplierVO);
	}
	public Json deleteGspSupplier(String id) {
		Json json = new Json();
		//GspSupplier gspSupplier = gspSupplierDao.findById(id);
		if(id != null){
//			gspSupplierMybatisDao.deleteNotUse(id);
			gspSupplierMybatisDao.delete(id);
		}
		json.setSuccess(true);
		return json;
	}


	@Transactional
	public Json reApply(String id){
		try{

			if(StringUtils.isEmpty(id)){
				return Json.error("请选择要重新申请的数据");
			}

			String[] arr = id.split(",");
			for(String s : arr){
				String newNo = commonService.generateSeq(Constant.APLSUPNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId());

				//更新旧数据状态
				GspSupplier gspSupplier = gspSupplierMybatisDao.queryById(s);

                BasCustomerQuery CustomerQ = new BasCustomerQuery();
                CustomerQ.setCustomerType(Constant.CODE_CUS_TYP_OW);
                CustomerQ.setCustomerid(gspSupplier.getCostomerid());
                BasCustomer customer = basCustomerMybatisDao.selectByIdTypeActiveFlag(CustomerQ);
                if(customer!=null){
                    if("0".equals(customer.getActiveFlag())){
                        return Json.error("对应的货主已不合作");
                    }
                }else{
                    return Json.error("货主不存在");
                }
				GspEnterpriseInfo g = gspEnterpriseInfoMybatisDao.queryNewByEnterpriseId(gspSupplier.getEnterpriseId());
				if(g==null){
					return  Json.error("企业信息已失效");
				}

//				Json checkScopeResult =gspVerifyService.verifyOperate(gspSupplier.getCostomerid(),gspSupplier.getEnterpriseId(),"");
//				if(!checkScopeResult.isSuccess()){
//					return checkScopeResult;
//				}
//				Integer result = gspSupplierMybatisDao.queryGspSupplierByEnterpriseId(gspSupplier);
//				if(result>0){
//					return Json.error("存在同一个供应商和货主且有效,不能重复申请");
//				}
				String oldClient = gspSupplier.getCostomerid();
				String oldNo = gspSupplier.getSupplierId();
				String oldGYSEnterpriseId = gspSupplier.getEnterpriseId();
				gspSupplier.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
				gspSupplier.setIsUse(Constant.IS_USE_NO);
				gspSupplierMybatisDao.updateBySelective(gspSupplier);
//				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_FAIL);


                // 通过enterpriseId 查询该公司的最新的enterpriseId   赋值给申请
				gspSupplier.setEnterpriseId(g.getEnterpriseId());


				//新增审核状态为未通过数据
				gspSupplier.setSupplierId(newNo);
				gspSupplier.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspSupplier.setCreateDate(new Date());
				gspSupplier.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
				gspSupplier.setIsCheck(Constant.CODE_YES_OR_YES);
//				gspSupplier.setIsCooperation(Constant.CODE_YES_OR_YES);
				gspSupplier.setIsUse(Constant.IS_USE_YES);
				gspSupplierMybatisDao.add(gspSupplier);

				//插入新的日志
				FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
				firstReviewLogForm.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstReviewLogForm.setReviewTypeId(newNo);
				firstReviewLogForm.setReviewId(RandomUtil.getUUID());

				firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);
				String content ="";
//				BasCustomer b = new BasCustomer();
//				b.setCustomerid(gspSupplier.getCostomerid());
//				b.setCustomerType(Constant.CODE_CUS_TYP_OW);
//				BasCustomer newC =basCustomerMybatisDao.selectByIdTypeActiveFlag(b);
//				b.setCustomerid(oldClient);
//				BasCustomer oldC =basCustomerMybatisDao.selectByIdTypeActiveFlag(b);


				//旧申请
//				GspEnterpriseInfo newHZ =  gspEnterpriseInfoMybatisDao.queryById(newC.getEnterpriseId());
				GspEnterpriseInfo oldGYS = gspEnterpriseInfoMybatisDao.selectEnterpriseByCompare(oldGYSEnterpriseId);
				GspBusinessLicense oldClientBusiness = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(oldGYSEnterpriseId);
				GspOperateLicense oldGspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId,Constant.LICENSE_TYPE_PROD);
				GspOperateLicense oldGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId,Constant.LICENSE_TYPE_OPERATE);
				GspFirstRecord oldGspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId);
				GspSecondRecord oldGspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId);
				GspMedicalRecord oldGspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(oldGYSEnterpriseId);


				//新申请
//				GspEnterpriseInfo oldHZ =  gspEnterpriseInfoMybatisDao.queryById(oldC.getEnterpriseId());
				GspEnterpriseInfo newGYS = gspEnterpriseInfoMybatisDao.selectEnterpriseByCompare(g.getEnterpriseId());
				GspBusinessLicense newClientBusiness = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(g.getEnterpriseId());
				GspOperateLicense newGspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId(),Constant.LICENSE_TYPE_PROD);
				GspOperateLicense newGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId(),Constant.LICENSE_TYPE_OPERATE);
				GspFirstRecord newGspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());
				GspSecondRecord newGspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());
				GspMedicalRecord newGspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(g.getEnterpriseId());


				if(oldGYSEnterpriseId.equals(gspSupplier.getEnterpriseId())){
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
							result = " 原供应商"+GspEnterpriseInfoChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为"+": "+(value2==null|| value2==""? "无":value2);
						}else if("GspBusinessLicense".equals(simpleClassName)){

							result = " 原供应商"+GspBusinessLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}else if("GspOperateLicense".equals(simpleClassName)){
							if("prod".equals(type)){
								result = " 原供应商"+GspProdLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
										",变更为 "+":"+(value2==null || value2==""? "无":value2);
							}else if("operate".equals(type)){
								result = " 原供应商"+GspOpreateLicenseChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
										",变更为 "+":"+(value2==null || value2==""? "无":value2);
							}
						}else if("GspFirstRecord".equals(simpleClassName)){
							result = " 原供应商"+GspFirstRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);

						}else if("GspSecondRecord".equals(simpleClassName)){
							result = " 原供应商"+GspSecondRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}else if("GspMedicalRecord".equals(simpleClassName)){
							result = " 原供应商"+GspMedicalRecordChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
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


	public List<EasyuiCombobox> getGspSupplierCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspSupplier> gspSupplierList = gspSupplierMybatisDao.queryListByAll();
		if(gspSupplierList != null && gspSupplierList.size() > 0){
			for(GspSupplier gspSupplier : gspSupplierList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspSupplier.getSupplierId()));
				combobox.setValue(gspSupplier.getEnterpriseId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	/**
	 * 更新首营状态
	 * @param no
	 * @param state
	 * @return
	 */
	public Json updateFirstState(String no,String state){
		Long result = gspSupplierMybatisDao.updateFirstState(no,state);
		if(result>0){
			return Json.success("更新供应商申请单首营状态成功");
		}
		return Json.error("更新供应商申请单首营状态失败");
	}

}