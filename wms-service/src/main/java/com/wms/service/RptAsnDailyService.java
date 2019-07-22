package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptAsnDailyDao;
import com.wms.entity.RptAsnDaily;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptAsnDailyVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptAsnDetailMybatisDao;
import com.wms.query.RptAsnDailyQuery;

@Service("rptAsnDailyService")
public class RptAsnDailyService extends BaseService {

	@Autowired
	private RptAsnDailyDao rptAsnDailyDao;
	
	@Autowired
	private RptAsnDailyMybatisDao rptAsnDailyMybatisDao;
//显示首页datagrid
	public EasyuiDatagrid<RptAsnDailyVO> getPagedDatagrid(EasyuiDatagridPager pager, RptAsnDailyQuery query) {
		EasyuiDatagrid<RptAsnDailyVO> datagrid = new EasyuiDatagrid<RptAsnDailyVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptAsnDaily> rptAsnDailyList = rptAsnDailyMybatisDao.queryByPageList(mybatisCriteria);
		RptAsnDailyVO rptAsnDailyVO = null;
		List<RptAsnDailyVO> rptAsnDailyVOList = new ArrayList<RptAsnDailyVO>();
		for (RptAsnDaily rptAsnDaily : rptAsnDailyList) {
			rptAsnDailyVO = new RptAsnDailyVO();
			BeanUtils.copyProperties(rptAsnDaily, rptAsnDailyVO);
			rptAsnDailyVOList.add(rptAsnDailyVO);
		}
		datagrid.setTotal((long) rptAsnDailyMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptAsnDailyVOList);
		return datagrid;
	}

	public Json addRptAsnDaily(RptAsnDailyForm rptAsnDailyForm) throws Exception {
		Json json = new Json();
		RptAsnDaily rptAsnDaily = new RptAsnDaily();
		BeanUtils.copyProperties(rptAsnDailyForm, rptAsnDaily);
		rptAsnDailyDao.save(rptAsnDaily);
		json.setSuccess(true);
		return json;
	}

	public Json editRptAsnDaily(RptAsnDailyForm rptAsnDailyForm) {
		Json json = new Json();
		RptAsnDaily rptAsnDaily = rptAsnDailyDao.findById(rptAsnDailyForm.getAsnno());
		BeanUtils.copyProperties(rptAsnDailyForm, rptAsnDaily);
		rptAsnDailyDao.update(rptAsnDaily);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptAsnDaily(String id) {
		Json json = new Json();
		RptAsnDaily rptAsnDaily = rptAsnDailyDao.findById(id);
		if(rptAsnDaily != null){
			rptAsnDailyDao.delete(rptAsnDaily);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptAsnDailyCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptAsnDaily> rptAsnDailyList = rptAsnDailyDao.findAll();
		if(rptAsnDailyList != null && rptAsnDailyList.size() > 0){
			for(RptAsnDaily rptAsnDaily : rptAsnDailyList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptAsnDaily.getAsnno()));
				combobox.setValue(rptAsnDaily.getAsnno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}