package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCustomerQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasCustomerVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCustomerForm;
import com.wms.vo.form.GspSupplierForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("basCustomerService")
public class BasCustomerService extends BaseService {

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

	@Autowired
	private BasSkuHistoryMybatisDao basSkuHistoryMybatisDao;

	@Autowired
	private GspReceivingMybatisDao gspReceivingMybatisDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;

	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

	public EasyuiDatagrid<BasCustomerVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCustomerQuery query) {
		EasyuiDatagrid<BasCustomerVO> datagrid = new EasyuiDatagrid<BasCustomerVO>();

		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryByPageList(mybatisCriteria);
		BasCustomerVO basCustomerVO = null;
		List<BasCustomerVO> basCustomerVOList = new ArrayList<BasCustomerVO>();
		for (BasCustomer basCustomer : basCustomerList) {
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
			}


			basCustomerVOList.add(basCustomerVO);
		}
		datagrid.setTotal((long) basCustomerMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basCustomerVOList);
		return datagrid;
	}

	public Json addBasCustomer(BasCustomerForm basCustomerForm) throws Exception {
		Json json = null;
		try {


			StringBuilder resultMsg = new StringBuilder();
			this.validateCustomer(basCustomerForm, resultMsg);// 验证客户是否存在

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
				gspReceiving.setReceivingId(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				gspReceiving.setFirstState("40");
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());

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
	}


	public Json supplierAddCustomer(GspSupplierForm gspSupplierForm) {
		Json json = new Json();
		BasCustomer basCustomer = new BasCustomer();
		basCustomer.setCustomerType("VE");
		basCustomer.setEnterpriseId(gspSupplierForm.getEnterpriseId());
		int num = basCustomerMybatisDao.selectBySelective(basCustomer);
		if(num!=0){
			basCustomerMybatisDao.delete(basCustomer);
		}

		basCustomer = new BasCustomer();
		basCustomer.setCustomerType("VE");
		basCustomer.setEnterpriseId(gspSupplierForm.getEnterpriseId());
		basCustomer.setCustomerid(commonService.generateSeq(Constant.BASSUPNO, SfcUserLoginUtil.getLoginUser().getId()));
		basCustomer.setOperateType(gspSupplierForm.getEnterpriseType());
		basCustomer.setActiveFlag("1");
		basCustomer.setDescrC(commonService.generateSeq(Constant.BASSUPNO, SfcUserLoginUtil.getLoginUser().getId()));
		basCustomer.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomer.setAddtime(new Date());
		basCustomer.setEdittime(new Date());
		basCustomerMybatisDao.add(basCustomer);



		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json editBasCustomer(BasCustomerForm basCustomerForm) {
		Json json = new Json();
		BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
		basCustomerQuery.setCustomerid(basCustomerForm.getCustomerid());
		basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());
		BasCustomer basCustomer = basCustomerMybatisDao.queryById(basCustomerQuery);
		BeanUtils.copyProperties(basCustomerForm, basCustomer);
		
		basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomerMybatisDao.updateBySelective(basCustomer);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}
	
	private void validateCustomer(BasCustomerForm basCustomerForm, StringBuilder resultMsg) {
		BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
		basCustomerQuery.setCustomerid(basCustomerForm.getCustomerid());
		basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());
		BasCustomer basCustomer = basCustomerMybatisDao.queryById(basCustomerQuery);
		if(basCustomer != null){
			resultMsg.append("客户代码：").append(basCustomerForm.getCustomerid())
					 .append("，客户类型：").append(basCustomerForm.getCustomerType()).append("，重复").append(" ");
		}
	}	

	public Json deleteBasCustomer(String enterpriseId, String customertype) {
		Json json = new Json();
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setEnterpriseId(enterpriseId);
		customerQuery.setCustomerType(customertype);

		BasCustomer basCustomer = basCustomerMybatisDao.queryById(customerQuery);
		if (basCustomer != null) {
			basCustomerMybatisDao.delete(basCustomer);
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
		}
		return json;
	}


	public BasCustomer selectCustomer(String enterpriseId, String customertype) {


		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setEnterpriseId(enterpriseId);
		customerQuery.setCustomerType(customertype);

		return basCustomerMybatisDao.queryById(customerQuery);
	}
	public Json goonBasCustomer(String enterpriseId, String customertype) {
		Json json = new Json();
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setEnterpriseId(enterpriseId);
		customerQuery.setCustomerType(customertype);

		BasCustomer basCustomer = basCustomerMybatisDao.queryById(customerQuery);
		if (basCustomer != null) {
			basCustomerMybatisDao.goon(basCustomer);
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
		}
		return json;
	}
	public List<EasyuiCombobox> getCustomerTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryCustomerTypeByAll();
		if(basCustomerList != null && basCustomerList.size() > 0){
			for(BasCustomer basCustomer : basCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCustomer.getCustomerType());
				combobox.setValue(basCustomer.getCustomerTypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	public List<EasyuiCombobox> getOperateTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryOperateTypeByAll();
		if(basCustomerList != null && basCustomerList.size() > 0){
			for(BasCustomer basCustomer : basCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCustomer.getOperateType());
				combobox.setValue(basCustomer.getOperateTypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	/**
	 * 获取收货地址基础信息
	 * @param enterpriseId 企业信息流水号
	 * @return
	 */
	public Json getReceivingAddressInfo(String enterpriseId, String receivingAddressId){
		 GspReceivingAddress gspReceivingAddress = basCustomerMybatisDao.getReceivingAddressInfo(receivingAddressId);
		if(gspReceivingAddress == null){
			return Json.error("收货地址信息不存在！");
		}
		return Json.success("",gspReceivingAddress);
	}

}