package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptSoDailyDao;
import com.wms.entity.RptSoDaily;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptSoDailyVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptSoDailyForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptSoDailyMybatisDao;
import com.wms.query.RptSoDailyQuery;

@Service("rptSoDailyService")
public class RptSoDailyService extends BaseService {

	//@Autowired
	//private RptSoDailyDao rptSoDailyDao;
	
	@Autowired
	private RptSoDailyMybatisDao rptSoDailyMybatisDao;

	public EasyuiDatagrid<RptSoDailyVO> getPagedDatagrid(EasyuiDatagridPager pager, RptSoDailyQuery query) {
		EasyuiDatagrid<RptSoDailyVO> datagrid = new EasyuiDatagrid<RptSoDailyVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptSoDaily> rptSoDailyList = rptSoDailyMybatisDao.queryByPageList(mybatisCriteria);
		RptSoDailyVO rptSoDailyVO = null;
		List<RptSoDailyVO> rptSoDailyVOList = new ArrayList<RptSoDailyVO>();
		for (RptSoDaily rptSoDaily : rptSoDailyList) {
			rptSoDailyVO = new RptSoDailyVO();
			BeanUtils.copyProperties(rptSoDaily, rptSoDailyVO);
			rptSoDailyVOList.add(rptSoDailyVO);
		}
		datagrid.setTotal((long) rptSoDailyMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptSoDailyVOList);
		return datagrid;
	}

	public Json addRptSoDaily(RptSoDailyForm rptSoDailyForm) throws Exception {
		Json json = new Json();
		RptSoDaily rptSoDaily = new RptSoDaily();
		BeanUtils.copyProperties(rptSoDailyForm, rptSoDaily);
		rptSoDailyMybatisDao.add(rptSoDaily);
		json.setSuccess(true);
		return json;
	}

	public Json editRptSoDaily(RptSoDailyForm rptSoDailyForm) {
		Json json = new Json();
		RptSoDaily rptSoDaily = rptSoDailyMybatisDao.queryById(rptSoDailyForm.getOrderno());
		BeanUtils.copyProperties(rptSoDailyForm, rptSoDaily);
		rptSoDailyMybatisDao.update(rptSoDaily);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptSoDaily(String id) {
		Json json = new Json();
		RptSoDaily rptSoDaily = rptSoDailyMybatisDao.queryById(id);
		if(rptSoDaily != null){
			rptSoDailyMybatisDao.delete(rptSoDaily);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptSoDailyCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptSoDaily> rptSoDailyList = rptSoDailyMybatisDao.queryByAll();
		if(rptSoDailyList != null && rptSoDailyList.size() > 0){
			for(RptSoDaily rptSoDaily : rptSoDailyList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptSoDaily.getOrderno()));
				combobox.setValue(rptSoDaily.getOrderno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}