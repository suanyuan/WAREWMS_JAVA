package com.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.ViewInvHold;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.ViewInvHoldVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ViewInvHoldForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvHoldMybatisDao;
import com.wms.query.ViewInvHoldQuery;

@Service("viewInvHoldService")
public class ViewInvHoldService extends BaseService {

	@Autowired
	private ViewInvHoldMybatisDao viewInvHoldMybaitsDao;

	public EasyuiDatagrid<ViewInvHoldVO> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvHoldQuery query) {
		EasyuiDatagrid<ViewInvHoldVO> datagrid = new EasyuiDatagrid<ViewInvHoldVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ViewInvHold> viewInvHoldList = viewInvHoldMybaitsDao.queryByPageList(mybatisCriteria);
		ViewInvHoldVO viewInvHoldVO = null;
		List<ViewInvHoldVO> viewInvHoldVOList = new ArrayList<ViewInvHoldVO>();
		for (ViewInvHold viewInvHold : viewInvHoldList) {
			viewInvHoldVO = new ViewInvHoldVO();
			BeanUtils.copyProperties(viewInvHold, viewInvHoldVO);
			viewInvHoldVOList.add(viewInvHoldVO);
		}
		datagrid.setTotal((long)viewInvHoldMybaitsDao.queryByCount(mybatisCriteria));
		datagrid.setRows(viewInvHoldVOList);
		return datagrid;
	}
	
	public Json holdViewInvHold(ViewInvHoldForm viewInvHoldForm) {
		Json json = new Json();
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("customerid", viewInvHoldForm.getCustomerid());
		map.put("sku",  viewInvHoldForm.getSku());
		map.put("lotnum",  viewInvHoldForm.getLotnum());
		map.put("location",  viewInvHoldForm.getLocationid());
		map.put("traceid",  viewInvHoldForm.getTraceid());
		map.put("holdcode",  viewInvHoldForm.getHoldcode());//冻结原因
		map.put("reason",  viewInvHoldForm.getReason());//原因说明
		map.put("whoon", SfcUserLoginUtil.getLoginUser().getId());
		map.put("inventoryholdid",  viewInvHoldForm.getInventoryholdid());
		ViewInvHold viewInvHold = viewInvHoldMybaitsDao.queryById(map);
		if(viewInvHold != null){ 
			viewInvHoldMybaitsDao.invHold(map);
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
	
	public Json releaseViewInvHold(ViewInvHoldForm viewInvHoldForm) {
		Json json = new Json();
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("inventoryholdid",  viewInvHoldForm.getInventoryholdid());
		map.put("whooff", SfcUserLoginUtil.getLoginUser().getId());
		ViewInvHold viewInvHold = viewInvHoldMybaitsDao.queryById(map);
		if(viewInvHold != null){ 
			viewInvHoldMybaitsDao.invRelease(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("库存释放成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("库存释放失败！"+result);
			}
		}
		return json;
	}
	
		
}