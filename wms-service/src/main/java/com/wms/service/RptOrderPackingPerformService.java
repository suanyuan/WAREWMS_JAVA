package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptOrderPackingPerformDao;
import com.wms.entity.RptOrderPackingPerform;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptOrderPackingPerformVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptOrderPackingPerformForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptOrderPackingPerformMybatisDao;
import com.wms.query.RptOrderPackingPerformQuery;

@Service("rptOrderPackingPerformService")
public class RptOrderPackingPerformService extends BaseService {

	@Autowired
	private RptOrderPackingPerformDao rptOrderPackingPerformDao;
	
	@Autowired
	private RptOrderPackingPerformMybatisDao rptOrderPackingPerformMybatisDao;

	public EasyuiDatagrid<RptOrderPackingPerformVO> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderPackingPerformQuery query) {
		EasyuiDatagrid<RptOrderPackingPerformVO> datagrid = new EasyuiDatagrid<RptOrderPackingPerformVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrderPackingPerform> rptOrderPackingPerformList = rptOrderPackingPerformMybatisDao.queryByPageList(mybatisCriteria);
		RptOrderPackingPerformVO rptOrderPackingPerformVO = null;
		List<RptOrderPackingPerformVO> rptOrderPackingPerformVOList = new ArrayList<RptOrderPackingPerformVO>();
		for (RptOrderPackingPerform rptOrderPackingPerform : rptOrderPackingPerformList) {
			rptOrderPackingPerformVO = new RptOrderPackingPerformVO();
			BeanUtils.copyProperties(rptOrderPackingPerform, rptOrderPackingPerformVO);
			rptOrderPackingPerformVOList.add(rptOrderPackingPerformVO);
		}
		datagrid.setTotal((long) rptOrderPackingPerformMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptOrderPackingPerformVOList);
		return datagrid;
	}

	public Json addRptOrderPackingPerform(RptOrderPackingPerformForm rptOrderPackingPerformForm) throws Exception {
		Json json = new Json();
		RptOrderPackingPerform rptOrderPackingPerform = new RptOrderPackingPerform();
		BeanUtils.copyProperties(rptOrderPackingPerformForm, rptOrderPackingPerform);
		rptOrderPackingPerformDao.save(rptOrderPackingPerform);
		json.setSuccess(true);
		return json;
	}

	public Json editRptOrderPackingPerform(RptOrderPackingPerformForm rptOrderPackingPerformForm) {
		Json json = new Json();
		RptOrderPackingPerform rptOrderPackingPerform = rptOrderPackingPerformDao.findById(rptOrderPackingPerformForm.getCustomerid());
		BeanUtils.copyProperties(rptOrderPackingPerformForm, rptOrderPackingPerform);
		rptOrderPackingPerformDao.update(rptOrderPackingPerform);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptOrderPackingPerform(String id) {
		Json json = new Json();
		RptOrderPackingPerform rptOrderPackingPerform = rptOrderPackingPerformDao.findById(id);
		if(rptOrderPackingPerform != null){
			rptOrderPackingPerformDao.delete(rptOrderPackingPerform);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptOrderPackingPerformCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptOrderPackingPerform> rptOrderPackingPerformList = rptOrderPackingPerformDao.findAll();
		if(rptOrderPackingPerformList != null && rptOrderPackingPerformList.size() > 0){
			for(RptOrderPackingPerform rptOrderPackingPerform : rptOrderPackingPerformList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptOrderPackingPerform.getCustomerid()));
				combobox.setValue(rptOrderPackingPerform.getCustomerid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}