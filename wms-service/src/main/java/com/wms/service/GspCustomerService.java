package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;

import com.wms.utils.DateUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.form.FirstReviewLogForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.GspCustomerMybatisDao;
import com.wms.entity.GspCustomer;
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
	private GspEnterpriseInfoService gspEnterpriseInfoService;
	@Autowired
	private FirstReviewLogService firstReviewLogService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private DataPublishService dataPublishService;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;



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

				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING);
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

				Integer result = gspCustomerMybatisDao.queryGspCustomerByClientNo(gspCustomer.getClientNo());
				if(result>0){
					return Json.error("同一个企业不能重复申请");
				}

				String oldNo = gspCustomer.getClientId();
				gspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
				gspCustomer.setIsUse(Constant.IS_USE_NO);
				gspCustomerMybatisDao.updateBySelective(gspCustomer);
				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_FAIL);

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