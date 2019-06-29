package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.GspCustomerMybatisDao;
import com.wms.entity.GspCustomer;
import com.wms.vo.GspCustomerVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspCustomerForm;
import com.wms.query.GspCustomerQuery;

@Service("gspCustomerService")
public class GspCustomerService extends BaseService {

	@Autowired
	private GspCustomerMybatisDao gspCustomerMybatisDao;

	public EasyuiDatagrid<GspCustomerVO> getPagedDatagrid(EasyuiDatagridPager pager, GspCustomerQuery query) {
		EasyuiDatagrid<GspCustomerVO> datagrid = new EasyuiDatagrid<GspCustomerVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		criteria.setOrderByClause("create_date desc");
		List<GspCustomer> gspCustomerList = gspCustomerMybatisDao.queryByList(criteria);
		GspCustomerVO gspCustomerVO = null;
		List<GspCustomerVO> gspCustomerVOList = new ArrayList<GspCustomerVO>();
		for (GspCustomer gspCustomer : gspCustomerList) {
			gspCustomerVO = new GspCustomerVO();
			BeanUtils.copyProperties(gspCustomer, gspCustomerVO);
			gspCustomerVOList.add(gspCustomerVO);
		}
		int count = gspCustomerMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspCustomerVOList);
		return datagrid;
	}

	public Json addGspCustomer(GspCustomerForm gspCustomerForm) throws Exception {
		Json json = new Json();
		GspCustomer gspCustomer = new GspCustomer();
		BeanUtils.copyProperties(gspCustomerForm, gspCustomer);
		gspCustomer.setClientId(RandomUtil.getUUID());
		gspCustomer.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		gspCustomerMybatisDao.add(gspCustomer);
		json.setSuccess(true);
		return json;
	}

	public Json editGspCustomer(GspCustomerForm gspCustomerForm) {
		Json json = new Json();
		GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(gspCustomerForm.getClientId());
		BeanUtils.copyProperties(gspCustomerForm, gspCustomer);
		gspCustomerMybatisDao.update(gspCustomer);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspCustomer(String id) {
		Json json = new Json();
		GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(id);
		if(gspCustomer != null){
			gspCustomerMybatisDao.delete(gspCustomer);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspCustomerCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspCustomer> gspCustomerList = gspCustomerMybatisDao.queryByAll();
		if(gspCustomerList != null && gspCustomerList.size() > 0){
			for(GspCustomer gspCustomer : gspCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspCustomer.getClientId()));
				combobox.setValue(gspCustomer.getClientName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}