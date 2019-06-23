package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptSortPerformanceDao;
import com.wms.entity.RptSortPerformance;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptSortPerformanceVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptSortPerformanceForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptPackPerformanceMybatisDao;
import com.wms.mybatis.dao.RptSortPerformanceMybatisDao;
import com.wms.query.RptSortPerformanceQuery;

@Service("rptSortPerformanceService")
public class RptSortPerformanceService extends BaseService {

	@Autowired
	private RptSortPerformanceDao rptSortPerformanceDao;
	
	@Autowired
	private RptSortPerformanceMybatisDao rptSortPerformanceMybatisDao;

	public EasyuiDatagrid<RptSortPerformanceVO> getPagedDatagrid(EasyuiDatagridPager pager, RptSortPerformanceQuery query) {
		EasyuiDatagrid<RptSortPerformanceVO> datagrid = new EasyuiDatagrid<RptSortPerformanceVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptSortPerformance> rptSortPerformanceList = rptSortPerformanceMybatisDao.queryByPageList(mybatisCriteria);
		RptSortPerformanceVO rptSortPerformanceVO = null;
		List<RptSortPerformanceVO> rptSortPerformanceVOList = new ArrayList<RptSortPerformanceVO>();
		for (RptSortPerformance rptSortPerformance : rptSortPerformanceList) {
			rptSortPerformanceVO = new RptSortPerformanceVO();
			BeanUtils.copyProperties(rptSortPerformance, rptSortPerformanceVO);
			rptSortPerformanceVOList.add(rptSortPerformanceVO);
		}
		datagrid.setTotal((long) rptSortPerformanceMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptSortPerformanceVOList);
		return datagrid;
	}

	public Json addRptSortPerformance(RptSortPerformanceForm rptSortPerformanceForm) throws Exception {
		Json json = new Json();
		RptSortPerformance rptSortPerformance = new RptSortPerformance();
		BeanUtils.copyProperties(rptSortPerformanceForm, rptSortPerformance);
		rptSortPerformanceDao.save(rptSortPerformance);
		json.setSuccess(true);
		return json;
	}

	public Json editRptSortPerformance(RptSortPerformanceForm rptSortPerformanceForm) {
		Json json = new Json();
		RptSortPerformance rptSortPerformance = rptSortPerformanceDao.findById(rptSortPerformanceForm.getCustomerid());
		BeanUtils.copyProperties(rptSortPerformanceForm, rptSortPerformance);
		rptSortPerformanceDao.update(rptSortPerformance);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptSortPerformance(String id) {
		Json json = new Json();
		RptSortPerformance rptSortPerformance = rptSortPerformanceDao.findById(id);
		if(rptSortPerformance != null){
			rptSortPerformanceDao.delete(rptSortPerformance);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptSortPerformanceCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptSortPerformance> rptSortPerformanceList = rptSortPerformanceDao.findAll();
		if(rptSortPerformanceList != null && rptSortPerformanceList.size() > 0){
			for(RptSortPerformance rptSortPerformance : rptSortPerformanceList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptSortPerformance.getCustomerid()));
				combobox.setValue(rptSortPerformance.getCustomerid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}