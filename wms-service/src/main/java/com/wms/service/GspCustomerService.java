package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
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

	public Json addGspCustomer(GspCustomerForm gspCustomerForm) throws Exception {
		try{
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
			gspCustomer.setIsUse(Constant.IS_USE_YES);
			gspCustomerMybatisDao.add(gspCustomer);

			FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
			firstReviewLogForm.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLogForm.setReviewTypeId(no);
			firstReviewLogForm.setReviewId(RandomUtil.getUUID());
			firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);
			firstReviewLogService.addFirstReviewLog(firstReviewLogForm);

			json.setSuccess(true);

			return json;
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("保存失败");
		}

	}

	public Json editGspCustomer(GspCustomerForm gspCustomerForm) {
		Json json = new Json();
		GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(gspCustomerForm.getClientId());
		if(gspCustomer ==null){
			return Json.error("没有查询到对应的申请单号");
		}

		BeanUtils.copyProperties(gspCustomerForm, gspCustomer);
		gspCustomerMybatisDao.updateBySelective(gspCustomer);
		json.setSuccess(true);
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
				//更新就数据状态
				GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(s);
				String oldNo = gspCustomer.getClientId();
				gspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
				gspCustomer.setIsUse(Constant.IS_USE_NO);
				gspCustomerMybatisDao.updateBySelective(gspCustomer);
				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_FAIL);

				//更新审核状态为未通过
				gspCustomer.setClientId(no);
				gspCustomer.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspCustomer.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
				gspCustomer.setIsCheck(Constant.CODE_YES_OR_YES);
				gspCustomer.setIsCooperation(Constant.CODE_YES_OR_YES);
				gspCustomer.setIsUse(Constant.IS_USE_NO);
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
			return Json.success("重新申请成功成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}

	}

}