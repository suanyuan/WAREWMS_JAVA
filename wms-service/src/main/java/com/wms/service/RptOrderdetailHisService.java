package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptOrderdetailHisDao;
import com.wms.entity.RptOrderdetailHis;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptOrderdetailHisVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptOrderdetailHisForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptOrderdetailHisMybatisDao;
import com.wms.query.RptOrderdetailHisQuery;

@Service("rptOrderdetailHisService")
public class RptOrderdetailHisService extends BaseService {

	@Autowired
	private RptOrderdetailHisDao rptOrderdetailHisDao;
	
	@Autowired
	private RptOrderdetailHisMybatisDao rptOrderdetailHisMybatisDao;

	public EasyuiDatagrid<RptOrderdetailHisVO> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderdetailHisQuery query) {
		EasyuiDatagrid<RptOrderdetailHisVO> datagrid = new EasyuiDatagrid<RptOrderdetailHisVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrderdetailHis> rptOrderdetailHisList = rptOrderdetailHisMybatisDao.queryByPageList(mybatisCriteria);
		RptOrderdetailHisVO rptOrderdetailHisVO = null;
		List<RptOrderdetailHisVO> rptOrderdetailHisVOList = new ArrayList<RptOrderdetailHisVO>();
		for (RptOrderdetailHis rptOrderdetailHis : rptOrderdetailHisList) {
			rptOrderdetailHisVO = new RptOrderdetailHisVO();
			BeanUtils.copyProperties(rptOrderdetailHis, rptOrderdetailHisVO);
			rptOrderdetailHisVOList.add(rptOrderdetailHisVO);
		}
		datagrid.setTotal((long) rptOrderdetailHisMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptOrderdetailHisVOList);
		return datagrid;
	}

	public Json addRptOrderdetailHis(RptOrderdetailHisForm rptOrderdetailHisForm) throws Exception {
		Json json = new Json();
		RptOrderdetailHis rptOrderdetailHis = new RptOrderdetailHis();
		BeanUtils.copyProperties(rptOrderdetailHisForm, rptOrderdetailHis);
		rptOrderdetailHisDao.save(rptOrderdetailHis);
		json.setSuccess(true);
		return json;
	}

	public Json editRptOrderdetailHis(RptOrderdetailHisForm rptOrderdetailHisForm) {
		Json json = new Json();
		RptOrderdetailHis rptOrderdetailHis = rptOrderdetailHisDao.findById(rptOrderdetailHisForm.getOrderno());
		BeanUtils.copyProperties(rptOrderdetailHisForm, rptOrderdetailHis);
		rptOrderdetailHisDao.update(rptOrderdetailHis);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptOrderdetailHis(String id) {
		Json json = new Json();
		RptOrderdetailHis rptOrderdetailHis = rptOrderdetailHisDao.findById(id);
		if(rptOrderdetailHis != null){
			rptOrderdetailHisDao.delete(rptOrderdetailHis);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptOrderdetailHisCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptOrderdetailHis> rptOrderdetailHisList = rptOrderdetailHisDao.findAll();
		if(rptOrderdetailHisList != null && rptOrderdetailHisList.size() > 0){
			for(RptOrderdetailHis rptOrderdetailHis : rptOrderdetailHisList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptOrderdetailHis.getOrderno()));
				combobox.setValue(rptOrderdetailHis.getOrderno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}