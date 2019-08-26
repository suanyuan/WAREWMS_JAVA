package com.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.ViewInvTranDao;
import com.wms.entity.ViewInvLotatt;
import com.wms.entity.ViewInvTran;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.ViewInvTranVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ViewInvLotattForm;
import com.wms.vo.form.ViewInvTranExportForm;
import com.wms.vo.form.ViewInvTranForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvTranMybatisDao;
import com.wms.query.ViewInvTranQuery;

@Service("viewInvTranService")
public class ViewInvTranService extends BaseService {

	@Autowired
	private ViewInvTranMybatisDao viewInvTranMybatisDao;
//返回主页datagrid
	public EasyuiDatagrid<ViewInvTranVO> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvTranQuery query) {
		EasyuiDatagrid<ViewInvTranVO> datagrid = new EasyuiDatagrid<ViewInvTranVO>();
		//query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId()); TODO 事务表暂时没有仓库id
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ViewInvTran> viewInvTranList = viewInvTranMybatisDao.queryByPageList(mybatisCriteria);
		ViewInvTranVO viewInvTranVO = null;
		List<ViewInvTranVO> viewInvTranVOList = new ArrayList<ViewInvTranVO>();
		for (ViewInvTran viewInvTran : viewInvTranList) {
			viewInvTranVO = new ViewInvTranVO();
			BeanUtils.copyProperties(viewInvTran, viewInvTranVO);
			viewInvTranVOList.add(viewInvTranVO);
		}
		datagrid.setTotal((long)viewInvTranMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(viewInvTranVOList);
		return datagrid;
	}
	
	public Json cancelReceive(ViewInvTranForm viewInvTranForm) {
		Json json = new Json();
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("transactionid", viewInvTranForm.getTransactionid());
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		ViewInvTran viewInvTran = viewInvTranMybatisDao.queryById(map);
		if(viewInvTran != null){
			viewInvTranMybatisDao.cancelReceive(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("取消收货成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("取消收货失败！"+result);
			}
		}
		return json;
	}
	
	public Json cancelShipment(ViewInvTranForm viewInvTranForm) {
		Json json = new Json();
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("transactionid", viewInvTranForm.getTransactionid());
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		ViewInvTran viewInvTran = viewInvTranMybatisDao.queryById(map);
		if(viewInvTran != null){
			viewInvTranMybatisDao.cancelShipment(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("取消发货成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("取消发货失败！"+result);
			}
		}
		return json;
	}
	
	public List<EasyuiCombobox> getTransactionTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<ViewInvTran> viewInvTranList = viewInvTranMybatisDao.queryByTransactionType();
		if(viewInvTranList != null && viewInvTranList.size() > 0){
			for(ViewInvTran viewInvTran : viewInvTranList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(viewInvTran.getTransactiontype()));
				combobox.setValue(viewInvTran.getTransactiontypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getDocTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<ViewInvTran> viewInvTranList = viewInvTranMybatisDao.queryByDocType();
		if(viewInvTranList != null && viewInvTranList.size() > 0){
			for(ViewInvTran viewInvTran : viewInvTranList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(viewInvTran.getDoctype()));
				combobox.setValue(viewInvTran.getDoctypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getStatusCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<ViewInvTran> viewInvTranList = viewInvTranMybatisDao.queryByStatus();
		if(viewInvTranList != null && viewInvTranList.size() > 0){
			for(ViewInvTran viewInvTran : viewInvTranList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(viewInvTran.getStatus()));
				combobox.setValue(viewInvTran.getStatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	

}