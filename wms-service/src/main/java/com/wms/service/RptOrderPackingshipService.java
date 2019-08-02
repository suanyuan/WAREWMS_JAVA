package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptOrderPackingshipDao;
import com.wms.entity.RptOrderPackingbox;
import com.wms.entity.RptOrderPackingship;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptOrderPackingboxVO;
import com.wms.vo.RptOrderPackingshipVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptOrderPackingshipForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptOrderPackingshipMybatisDao;
import com.wms.query.RptOrderPackingshipQuery;

@Service("rptOrderPackingshipService")
public class RptOrderPackingshipService extends BaseService {

	//@Autowired
	//private RptOrderPackingshipDao rptOrderPackingshipDao;
	
	@Autowired
	private RptOrderPackingshipMybatisDao rptOrderPackingshipMybatisDao;

	public EasyuiDatagrid<RptOrderPackingshipVO> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderPackingshipQuery query) {
		EasyuiDatagrid<RptOrderPackingshipVO> datagrid = new EasyuiDatagrid<RptOrderPackingshipVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrderPackingship> rptOrderPackingshipList = rptOrderPackingshipMybatisDao.queryByPageList(mybatisCriteria);
		RptOrderPackingshipVO rptOrderPackingshipVO = null;
		List<RptOrderPackingshipVO> rptOrderPackingshipVOList = new ArrayList<RptOrderPackingshipVO>();
		for (RptOrderPackingship rptOrderPackingship : rptOrderPackingshipList) {
			rptOrderPackingshipVO = new RptOrderPackingshipVO();
			BeanUtils.copyProperties(rptOrderPackingship, rptOrderPackingshipVO);
			rptOrderPackingshipVOList.add(rptOrderPackingshipVO);
		}
		datagrid.setTotal((long) rptOrderPackingshipMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptOrderPackingshipVOList);
		return datagrid;
	}

	public Json addRptOrderPackingship(RptOrderPackingshipForm rptOrderPackingshipForm) throws Exception {
		Json json = new Json();
		RptOrderPackingship rptOrderPackingship = new RptOrderPackingship();
		BeanUtils.copyProperties(rptOrderPackingshipForm, rptOrderPackingship);
		rptOrderPackingshipMybatisDao.add(rptOrderPackingship);
		json.setSuccess(true);
		return json;
	}

	public Json editRptOrderPackingship(RptOrderPackingshipForm rptOrderPackingshipForm) {
		Json json = new Json();
		RptOrderPackingship rptOrderPackingship = rptOrderPackingshipMybatisDao.queryById(rptOrderPackingshipForm.getTraceid());
		BeanUtils.copyProperties(rptOrderPackingshipForm, rptOrderPackingship);
		rptOrderPackingshipMybatisDao.update(rptOrderPackingship);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptOrderPackingship(String id) {
		Json json = new Json();
		RptOrderPackingship rptOrderPackingship = rptOrderPackingshipMybatisDao.queryById(id);
		if(rptOrderPackingship != null){
			rptOrderPackingshipMybatisDao.delete(rptOrderPackingship);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptOrderPackingshipCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptOrderPackingship> rptOrderPackingshipList = rptOrderPackingshipMybatisDao.queryByAll();
		if(rptOrderPackingshipList != null && rptOrderPackingshipList.size() > 0){
			for(RptOrderPackingship rptOrderPackingship : rptOrderPackingshipList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptOrderPackingship.getTraceid()));
				combobox.setValue(rptOrderPackingship.getTraceid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}