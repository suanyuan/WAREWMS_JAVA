package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.BasZonegroupDao;
import com.wms.entity.BasSku;
import com.wms.entity.BasZonegroup;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasZonegroupVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasZonegroupForm;
import com.wms.mybatis.dao.BasZoneGroupMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasZonegroupQuery;

@Service("basZonegroupService")
public class BasZonegroupService extends BaseService {

	@Autowired
//	private BasZonegroupDao basZonegroupDao;
	private BasZoneGroupMybatisDao basZoneGroupMybatisDao;

	public EasyuiDatagrid<BasZonegroupVO> getPagedDatagrid(EasyuiDatagridPager pager, BasZonegroupQuery query) {
		EasyuiDatagrid<BasZonegroupVO> datagrid = new EasyuiDatagrid<BasZonegroupVO>();
		BasZonegroupVO basZonegroupVO = null;
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<BasZonegroup> basZonegroupList = basZoneGroupMybatisDao.queryByPageList(mybatisCriteria);
		List<BasZonegroupVO> basZonegroupVOList = new ArrayList<BasZonegroupVO>();
		for (BasZonegroup basZonegroup : basZonegroupList) {
			basZonegroupVO = new BasZonegroupVO();
			BeanUtils.copyProperties(basZonegroup, basZonegroupVO);
			basZonegroupVOList.add(basZonegroupVO);
		}
		datagrid.setTotal((long) basZoneGroupMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basZonegroupVOList);
		return datagrid;
	}

	public Json addBasZonegroup(BasZonegroupForm basZonegroupForm) throws Exception {
		Json json = new Json();
		Date today = new Date();
		BasZonegroup basZonegroup = new BasZonegroup();
		BeanUtils.copyProperties(basZonegroupForm, basZonegroup);
		
		basZonegroup.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		basZonegroup.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		basZonegroup.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basZonegroup.setAddtime(today);
		basZonegroup.setEdittime(today);
		
		basZoneGroupMybatisDao.add(basZonegroup);
		json.setSuccess(true);
		return json;
	}

	public Json editBasZonegroup(BasZonegroupForm basZonegroupForm) {
		Json json = new Json();
		Date today = new Date();
		BasZonegroup basZonegroup = basZoneGroupMybatisDao.queryById(basZonegroupForm.getZonegroup());
		BeanUtils.copyProperties(basZonegroupForm, basZonegroup);
		basZonegroup.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basZonegroup.setEdittime(today);
		basZoneGroupMybatisDao.update(basZonegroup);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasZonegroup(String id) {
		Json json = new Json();
		BasZonegroup basZonegroup = basZoneGroupMybatisDao.queryById(id);
		if(basZonegroup != null){
			basZoneGroupMybatisDao.delete(basZonegroup);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasZonegroupCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasZonegroup> basZonegroupList = basZoneGroupMybatisDao.queryByAll();
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