package com.wms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.ViewInvLotattDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvLotattMybatisDao;
import com.wms.entity.BasZonegroup;
import com.wms.entity.ViewInvLotatt;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.ViewInvLotattVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ViewInvLotattForm;
import com.wms.query.ViewInvLotattQuery;

@Service("viewInvLotattService")
public class ViewInvLotattService extends BaseService {

	@Autowired
//	private ViewInvLotattDao viewInvLotattDao;
	private ViewInvLotattMybatisDao viewInvLotattMybatisDao;

	public EasyuiDatagrid<ViewInvLotattVO> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvLotattQuery query) {
		EasyuiDatagrid<ViewInvLotattVO> datagrid = new EasyuiDatagrid<ViewInvLotattVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ViewInvLotatt> viewInvLotattList = viewInvLotattMybatisDao.queryByPageList(mybatisCriteria);
		ViewInvLotattVO viewInvLotattVO = null;
		List<ViewInvLotattVO> viewInvLotattVOList = new ArrayList<ViewInvLotattVO>();
		for (ViewInvLotatt viewInvLotatt : viewInvLotattList) {//james
			viewInvLotattVO = new ViewInvLotattVO();
			BeanUtils.copyProperties(viewInvLotatt, viewInvLotattVO);
			viewInvLotattVOList.add(viewInvLotattVO);
		}
		datagrid.setTotal((long)viewInvLotattMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(viewInvLotattVOList);
		return datagrid;
	}

	public Json adjViewInvLotatt(ViewInvLotattForm viewInvLotattForm) {
		Json json = new Json();
//		ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(viewInvLotattForm.getFmid());//james
//		BeanUtils.copyProperties(viewInvLotattForm, viewInvLotatt);
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("fmcustomerid", viewInvLotattForm.getFmcustomerid());
		map.put("fmsku",  viewInvLotattForm.getFmsku());
		map.put("fmlotnum",  viewInvLotattForm.getFmlotnum());
		map.put("fmlocation",  viewInvLotattForm.getFmlocation());
		map.put("fmid",  viewInvLotattForm.getFmid());
		map.put("fmqty",  String.valueOf(viewInvLotattForm.getFmqty()));
		map.put("lotatt11", viewInvLotattForm.getLotatt11());//目标数量
		map.put("lotatt12",  viewInvLotattForm.getLotatt12());//调整原因
		map.put("lotatt12text",  viewInvLotattForm.getLotatt12text());//原因说明
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(map);
		if(viewInvLotatt != null){
			viewInvLotattMybatisDao.invAdj(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("库存调整成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("库存调整失败！"+result);
			}
		}
		return json;
	}
	

	public Json movViewInvLotatt(ViewInvLotattForm viewInvLotattForm) {
		Json json = new Json();
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("fmcustomerid", viewInvLotattForm.getFmcustomerid());
		map.put("fmsku",  viewInvLotattForm.getFmsku());
		map.put("fmlotnum",  viewInvLotattForm.getFmlotnum());
		map.put("fmlocation",  viewInvLotattForm.getFmlocation());
		map.put("fmid",  viewInvLotattForm.getFmid());
		map.put("fmqty",  String.valueOf(viewInvLotattForm.getFmqty()));
		map.put("lotatt11text", viewInvLotattForm.getLotatt11text());//目标库位
		map.put("lotatt11", viewInvLotattForm.getLotatt11());//移动数量
		map.put("lotatt12",  viewInvLotattForm.getLotatt12());//移动原因
		map.put("lotatt12text",  viewInvLotattForm.getLotatt12text());//原因说明
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(map);
		if(viewInvLotatt != null){
			viewInvLotattMybatisDao.invMov(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("库存移动成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("库存移动失败！"+result);
			}
		}
		return json;
	}
	
	public Json holdViewInvLotatt(ViewInvLotattForm viewInvLotattForm) {
		Json json = new Json();
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("fmcustomerid", viewInvLotattForm.getFmcustomerid());
		map.put("fmsku",  viewInvLotattForm.getFmsku());
		map.put("fmlotnum",  viewInvLotattForm.getFmlotnum());
		map.put("fmlocation",  viewInvLotattForm.getFmlocation());
		map.put("fmid",  viewInvLotattForm.getFmid());
		map.put("fmqty",  String.valueOf(viewInvLotattForm.getFmqty()));
		map.put("lotatt12",  viewInvLotattForm.getLotatt12());//冻结原因
		map.put("lotatt12text",  viewInvLotattForm.getLotatt12text());//原因说明
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(map);
		if(viewInvLotatt != null){ 
			viewInvLotattMybatisDao.invHold(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("库存冻结成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("库存冻结失败！"+result);
			}
		}
		return json;
	}


	public List<EasyuiCombobox> getViewInvLotattCombobox() {//未用到
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasZonegroup> basZonegroupList = viewInvLotattMybatisDao.queryByAll();
		if(basZonegroupList != null && basZonegroupList.size() > 0){
			for(BasZonegroup basZonegroup : basZonegroupList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basZonegroup.getZonegroup()));
				combobox.setValue(basZonegroup.getDescr());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}