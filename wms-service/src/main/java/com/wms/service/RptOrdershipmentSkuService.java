package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptOrdershipmentSkuDao;
import com.wms.entity.RptOrdershipmentSku;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptOrdershipmentSkuVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptOrdershipmentSkuForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptOrdershipmentSkuMybatisDao;
import com.wms.query.RptOrdershipmentSkuQuery;

@Service("rptOrdershipmentSkuService")
public class RptOrdershipmentSkuService extends BaseService {

	//@Autowired
	//private RptOrdershipmentSkuDao rptOrdershipmentSkuDao;
	
	@Autowired
	private RptOrdershipmentSkuMybatisDao rptOrdershipmentSkuMybatisDao;

	public EasyuiDatagrid<RptOrdershipmentSkuVO> getPagedDatagrid(EasyuiDatagridPager pager, RptOrdershipmentSkuQuery query) {
		EasyuiDatagrid<RptOrdershipmentSkuVO> datagrid = new EasyuiDatagrid<RptOrdershipmentSkuVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrdershipmentSku> rptOrdershipmentSkuList = rptOrdershipmentSkuMybatisDao.queryByPageList(mybatisCriteria);
		RptOrdershipmentSkuVO rptOrdershipmentSkuVO = null;
		List<RptOrdershipmentSkuVO> rptOrdershipmentSkuVOList = new ArrayList<RptOrdershipmentSkuVO>();
		for (RptOrdershipmentSku rptOrdershipmentSku : rptOrdershipmentSkuList) {
			rptOrdershipmentSkuVO = new RptOrdershipmentSkuVO();
			BeanUtils.copyProperties(rptOrdershipmentSku, rptOrdershipmentSkuVO);
			rptOrdershipmentSkuVOList.add(rptOrdershipmentSkuVO);
		}
		datagrid.setTotal((long) rptOrdershipmentSkuMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptOrdershipmentSkuVOList);
		return datagrid;
	}

	public Json addRptOrdershipmentSku(RptOrdershipmentSkuForm rptOrdershipmentSkuForm) throws Exception {
		Json json = new Json();
		RptOrdershipmentSku rptOrdershipmentSku = new RptOrdershipmentSku();
		BeanUtils.copyProperties(rptOrdershipmentSkuForm, rptOrdershipmentSku);
		rptOrdershipmentSkuMybatisDao.add(rptOrdershipmentSku);
		json.setSuccess(true);
		return json;
	}

	public Json editRptOrdershipmentSku(RptOrdershipmentSkuForm rptOrdershipmentSkuForm) {
		Json json = new Json();
		RptOrdershipmentSku rptOrdershipmentSku = rptOrdershipmentSkuMybatisDao.queryById(rptOrdershipmentSkuForm.getOrderno());
		BeanUtils.copyProperties(rptOrdershipmentSkuForm, rptOrdershipmentSku);
		rptOrdershipmentSkuMybatisDao.update(rptOrdershipmentSku);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptOrdershipmentSku(String id) {
		Json json = new Json();
		RptOrdershipmentSku rptOrdershipmentSku = rptOrdershipmentSkuMybatisDao.queryById(id);
		if(rptOrdershipmentSku != null){
			rptOrdershipmentSkuMybatisDao.delete(rptOrdershipmentSku);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptOrdershipmentSkuCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptOrdershipmentSku> rptOrdershipmentSkuList = rptOrdershipmentSkuMybatisDao.queryByAll();
		if(rptOrdershipmentSkuList != null && rptOrdershipmentSkuList.size() > 0){
			for(RptOrdershipmentSku rptOrdershipmentSku : rptOrdershipmentSkuList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptOrdershipmentSku.getOrderno()));
				combobox.setValue(rptOrdershipmentSku.getOrderno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}