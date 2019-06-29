package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasCustomer;
import com.wms.entity.GspCustomer;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspReceivingAddress;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasCustomerQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasCustomerVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCustomerForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("basCustomerService")
public class BasCustomerService extends BaseService {

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

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
			basCustomerVOList.add(basCustomerVO);
		}
		datagrid.setTotal((long) basCustomerMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basCustomerVOList);
		return datagrid;
	}

	public Json addBasCustomer(BasCustomerForm basCustomerForm) throws Exception {
		StringBuilder resultMsg = new StringBuilder();
		this.validateCustomer(basCustomerForm, resultMsg);// 验证客户是否存在

			Json json = new Json();


		if (resultMsg.length() == 0) {
			BasCustomer basCustomer = new BasCustomer();
			GspEnterpriseInfo gspEnterpriseInfo = new GspEnterpriseInfo();
			GspCustomer gspCustomer = new GspCustomer();
			BeanUtils.copyProperties(basCustomerForm,gspEnterpriseInfo);
			BeanUtils.copyProperties(basCustomerForm, basCustomer);
			BeanUtils.copyProperties(basCustomerForm, gspCustomer);
			//获取操作工号
			basCustomer.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());

			//
			basCustomerMybatisDao.add(basCustomer);
			basCustomerMybatisDao.add(gspCustomer);
			basCustomerMybatisDao.add(gspEnterpriseInfo);


		} else {
			json.setSuccess(false);
			json.setMsg(resultMsg.toString());
			return json;
		}
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
		basCustomerMybatisDao.update(basCustomer);
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