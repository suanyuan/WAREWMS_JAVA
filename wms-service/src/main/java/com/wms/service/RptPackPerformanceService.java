package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptPackPerformanceDao;
import com.wms.entity.RptPackPerformance;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptPackPerformanceVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptPackPerformanceForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptPackPerformanceMybatisDao;
import com.wms.query.RptPackPerformanceQuery;

@Service("rptPackPerformanceService")
public class RptPackPerformanceService extends BaseService {

	//@Autowired
	//private RptPackPerformanceDao rptPackPerformanceDao;
	
	@Autowired
	private RptPackPerformanceMybatisDao rptPackPerformanceMybatisDao;

	public EasyuiDatagrid<RptPackPerformanceVO> getPagedDatagrid(EasyuiDatagridPager pager, RptPackPerformanceQuery query) {
		EasyuiDatagrid<RptPackPerformanceVO> datagrid = new EasyuiDatagrid<RptPackPerformanceVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptPackPerformance> rptPackPerformanceList = rptPackPerformanceMybatisDao.queryByPageList(mybatisCriteria);
		RptPackPerformanceVO rptPackPerformanceVO = null;
		List<RptPackPerformanceVO> rptPackPerformanceVOList = new ArrayList<RptPackPerformanceVO>();
		for (RptPackPerformance rptPackPerformance : rptPackPerformanceList) {
			rptPackPerformanceVO = new RptPackPerformanceVO();
			BeanUtils.copyProperties(rptPackPerformance, rptPackPerformanceVO);
			rptPackPerformanceVOList.add(rptPackPerformanceVO);
		}
		datagrid.setTotal((long) rptPackPerformanceMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptPackPerformanceVOList);
		return datagrid;
	}

	public Json addRptPackPerformance(RptPackPerformanceForm rptPackPerformanceForm) throws Exception {
		Json json = new Json();
		RptPackPerformance rptPackPerformance = new RptPackPerformance();
		BeanUtils.copyProperties(rptPackPerformanceForm, rptPackPerformance);
		rptPackPerformanceMybatisDao.add(rptPackPerformance);
		json.setSuccess(true);
		return json;
	}

	public Json editRptPackPerformance(RptPackPerformanceForm rptPackPerformanceForm) {
		Json json = new Json();
		RptPackPerformance rptPackPerformance = rptPackPerformanceMybatisDao.queryById(rptPackPerformanceForm.getCustomerid());
		BeanUtils.copyProperties(rptPackPerformanceForm, rptPackPerformance);
		rptPackPerformanceMybatisDao.update(rptPackPerformance);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptPackPerformance(String id) {
		Json json = new Json();
		RptPackPerformance rptPackPerformance = rptPackPerformanceMybatisDao.queryById(id);
		if(rptPackPerformance != null){
			rptPackPerformanceMybatisDao.delete(rptPackPerformance);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptPackPerformanceCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptPackPerformance> rptPackPerformanceList = rptPackPerformanceMybatisDao.queryByAll();
		if(rptPackPerformanceList != null && rptPackPerformanceList.size() > 0){
			for(RptPackPerformance rptPackPerformance : rptPackPerformanceList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptPackPerformance.getCustomerid()));
				combobox.setValue(rptPackPerformance.getCustomerid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}