package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptOrdershipment2bDao;
import com.wms.entity.RptAsnDetail;
import com.wms.entity.RptOrdershipment2b;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptAsnDetailVO;
import com.wms.vo.RptOrdershipment2bVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptOrdershipment2bForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDetailMybatisDao;
import com.wms.mybatis.dao.RptOrdershipment2bMybatisDao;
import com.wms.query.RptOrdershipment2bQuery;

@Service("rptOrdershipment2bService")
public class RptOrdershipment2bService extends BaseService {

	//@Autowired
	//private RptOrdershipment2bDao rptOrdershipment2bDao;
	
	@Autowired
	private RptOrdershipment2bMybatisDao rptOrdershipment2bMybatisDao;

	public EasyuiDatagrid<RptOrdershipment2bVO> getPagedDatagrid(EasyuiDatagridPager pager, RptOrdershipment2bQuery query) {
		EasyuiDatagrid<RptOrdershipment2bVO> datagrid = new EasyuiDatagrid<RptOrdershipment2bVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrdershipment2b> rptOrdershipment2bList = rptOrdershipment2bMybatisDao.queryByPageList(mybatisCriteria);
		RptOrdershipment2bVO rptOrdershipment2bVO = null;
		List<RptOrdershipment2bVO> rptOrdershipment2bVOList = new ArrayList<RptOrdershipment2bVO>();
		for (RptOrdershipment2b rptOrdershipment2b : rptOrdershipment2bList) {
			rptOrdershipment2bVO = new RptOrdershipment2bVO();
			BeanUtils.copyProperties(rptOrdershipment2b, rptOrdershipment2bVO);
			rptOrdershipment2bVOList.add(rptOrdershipment2bVO);
		}
		datagrid.setTotal((long) rptOrdershipment2bMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptOrdershipment2bVOList);
		return datagrid;
	}

	public Json addRptOrdershipment2b(RptOrdershipment2bForm rptOrdershipment2bForm) throws Exception {
		Json json = new Json();
		RptOrdershipment2b rptOrdershipment2b = new RptOrdershipment2b();
		BeanUtils.copyProperties(rptOrdershipment2bForm, rptOrdershipment2b);
		rptOrdershipment2bMybatisDao.add(rptOrdershipment2b);
		json.setSuccess(true);
		return json;
	}

	public Json editRptOrdershipment2b(RptOrdershipment2bForm rptOrdershipment2bForm) {
		Json json = new Json();
		RptOrdershipment2b rptOrdershipment2b = rptOrdershipment2bMybatisDao.queryById(rptOrdershipment2bForm.getOrderno());
		BeanUtils.copyProperties(rptOrdershipment2bForm, rptOrdershipment2b);
		rptOrdershipment2bMybatisDao.update(rptOrdershipment2b);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptOrdershipment2b(String id) {
		Json json = new Json();
		RptOrdershipment2b rptOrdershipment2b = rptOrdershipment2bMybatisDao.queryById(id);
		if(rptOrdershipment2b != null){
			rptOrdershipment2bMybatisDao.delete(rptOrdershipment2b);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptOrdershipment2bCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptOrdershipment2b> rptOrdershipment2bList = rptOrdershipment2bMybatisDao.queryByAll();
		if(rptOrdershipment2bList != null && rptOrdershipment2bList.size() > 0){
			for(RptOrdershipment2b rptOrdershipment2b : rptOrdershipment2bList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptOrdershipment2b.getOrderno()));
				combobox.setValue(rptOrdershipment2b.getOrderno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}