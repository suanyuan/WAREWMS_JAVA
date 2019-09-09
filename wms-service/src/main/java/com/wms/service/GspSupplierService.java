package com.wms.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.constant.Constant;
import com.wms.entity.BasCustomer;
import com.wms.entity.FirstReviewLog;
import com.wms.entity.GspEnterpriseInfo;
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
import com.wms.entity.GspSupplier;
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
		GspSupplier  gspSupplier1=gspSupplierMybatisDao.queryByEnterpriseId(gspSupplier.getEnterpriseId());
		if(gspSupplier1!=null){
			return Json.error("同一个企业不能重复申请");
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
			gspSupplierMybatisDao.deleteNotUse(id);
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
				String no = commonService.generateSeq(Constant.APLSUPNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId());

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


//				Json checkScopeResult =gspVerifyService.verifyOperate(gspSupplier.getCostomerid(),gspSupplier.getEnterpriseId(),"");
//				if(!checkScopeResult.isSuccess()){
//					return checkScopeResult;
//				}
//				Integer result = gspSupplierMybatisDao.queryGspSupplierByEnterpriseId(gspSupplier);
//				if(result>0){
//					return Json.error("存在同一个供应商和货主且有效,不能重复申请");
//				}

				String oldNo = gspSupplier.getSupplierId();
				gspSupplier.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
				gspSupplier.setIsUse(Constant.IS_USE_NO);
				gspSupplierMybatisDao.updateBySelective(gspSupplier);
				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_FAIL);


                // 通过enterpriseId 查询该公司的最新的enterpriseId
                GspEnterpriseInfo g = gspEnterpriseInfoMybatisDao.queryNewByEnterpriseId(gspSupplier.getEnterpriseId());
                if(g!=null){
                    gspSupplier.setEnterpriseId(g.getEnterpriseId());
                }else{
                    return  Json.error("企业信息已失效");
                }


				//新增审核状态为未通过数据
				gspSupplier.setSupplierId(no);
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
				firstReviewLogForm.setReviewTypeId(no);
				firstReviewLogForm.setReviewId(RandomUtil.getUUID());

				firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);

				BasCustomer b = new BasCustomer();
				b.setCustomerid(gspSupplier.getCostomerid());
				b.setCustomerType(Constant.CODE_CUS_TYP_OW);
				BasCustomer C =basCustomerMybatisDao.selectByIdTypeActiveFlag(b);
				GspEnterpriseInfo HZ =gspEnterpriseInfoMybatisDao.queryById(C.getEnterpriseId());
				GspEnterpriseInfo GYS = gspEnterpriseInfoMybatisDao.queryById(gspSupplier.getEnterpriseId());

				String content =
					"供应商企业名称:"+GYS.getEnterpriseName()+" "+"供应商企业代码:"+GYS.getEnterpriseNo()+" "+
					"供应商企业类型:"+regred(GYS.getEnterpriseType())+";"+"对应货主企业名称:"+HZ.getEnterpriseName()+" "+
					"对应货主企业代码:"+HZ.getEnterpriseNo()+" "+"对应货主企业类型:"+regred(HZ.getEnterpriseType());
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