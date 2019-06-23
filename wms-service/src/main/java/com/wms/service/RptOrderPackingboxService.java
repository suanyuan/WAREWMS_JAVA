package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptOrderPackingboxDao;
import com.wms.entity.RptOrderPackingbox;
import com.wms.entity.RptOrdershipment2b;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptOrderPackingboxVO;
import com.wms.vo.Json;
import com.wms.vo.RptOrdershipment2bVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptOrderPackingboxForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptOrderPackingboxMybatisDao;
import com.wms.mybatis.dao.RptOrdershipment2bMybatisDao;
import com.wms.query.RptOrderPackingboxQuery;

@Service("rptOrderPackingboxService")
public class RptOrderPackingboxService extends BaseService {

	@Autowired
	private RptOrderPackingboxDao rptOrderPackingboxDao;
	
	@Autowired
	private RptOrderPackingboxMybatisDao rptOrderPackingboxMybatisDao;

	public EasyuiDatagrid<RptOrderPackingboxVO> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderPackingboxQuery query) {
		EasyuiDatagrid<RptOrderPackingboxVO> datagrid = new EasyuiDatagrid<RptOrderPackingboxVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrderPackingbox> rptOrderPackingboxList = rptOrderPackingboxMybatisDao.queryByPageList(mybatisCriteria);
		RptOrderPackingboxVO rptOrderPackingboxVO = null;
		List<RptOrderPackingboxVO> rptOrderPackingboxVOList = new ArrayList<RptOrderPackingboxVO>();
		for (RptOrderPackingbox rptOrderPackingbox : rptOrderPackingboxList) {
			rptOrderPackingboxVO = new RptOrderPackingboxVO();
			BeanUtils.copyProperties(rptOrderPackingbox, rptOrderPackingboxVO);
			rptOrderPackingboxVOList.add(rptOrderPackingboxVO);
		}
		datagrid.setTotal((long) rptOrderPackingboxMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptOrderPackingboxVOList);
		return datagrid;
	}

	public Json addRptOrderPackingbox(RptOrderPackingboxForm rptOrderPackingboxForm) throws Exception {
		Json json = new Json();
		RptOrderPackingbox rptOrderPackingbox = new RptOrderPackingbox();
		BeanUtils.copyProperties(rptOrderPackingboxForm, rptOrderPackingbox);
		rptOrderPackingboxDao.save(rptOrderPackingbox);
		json.setSuccess(true);
		return json;
	}

	public Json editRptOrderPackingbox(RptOrderPackingboxForm rptOrderPackingboxForm) {
		Json json = new Json();
		RptOrderPackingbox rptOrderPackingbox = rptOrderPackingboxDao.findById(rptOrderPackingboxForm.getOrderno());
		BeanUtils.copyProperties(rptOrderPackingboxForm, rptOrderPackingbox);
		rptOrderPackingboxDao.update(rptOrderPackingbox);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptOrderPackingbox(String id) {
		Json json = new Json();
		RptOrderPackingbox rptOrderPackingbox = rptOrderPackingboxDao.findById(id);
		if(rptOrderPackingbox != null){
			rptOrderPackingboxDao.delete(rptOrderPackingbox);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptOrderPackingboxCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptOrderPackingbox> rptOrderPackingboxList = rptOrderPackingboxDao.findAll();
		if(rptOrderPackingboxList != null && rptOrderPackingboxList.size() > 0){
			for(RptOrderPackingbox rptOrderPackingbox : rptOrderPackingboxList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptOrderPackingbox.getOrderno()));
				combobox.setValue(rptOrderPackingbox.getOrderno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}