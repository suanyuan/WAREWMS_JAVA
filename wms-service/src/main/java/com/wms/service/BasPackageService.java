package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.BasPackage;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasPackageVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasPackageForm;
import com.wms.mybatis.dao.BasPackageMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasPackageQuery;

@Service("basPackageService")
public class BasPackageService extends BaseService {

	@Autowired
	private BasPackageMybatisDao basPackageMybatisDao;

	public EasyuiDatagrid<BasPackageVO> getPagedDatagrid(EasyuiDatagridPager pager, BasPackageQuery query) {
		EasyuiDatagrid<BasPackageVO> datagrid = new EasyuiDatagrid<BasPackageVO>();
		MybatisCriteria criteria = new MybatisCriteria();
//
//		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		List<BasPackage> basPackageList = basPackageMybatisDao.queryByList(criteria);
		BasPackageVO basPackageVO = null;
		List<BasPackageVO> basPackageVOList = new ArrayList<BasPackageVO>();
		for (BasPackage basPackage : basPackageList) {
			basPackageVO = new BasPackageVO();
			BeanUtils.copyProperties(basPackage, basPackageVO);
			basPackageVOList.add(basPackageVO);
		}
		datagrid.setTotal((long) basPackageMybatisDao.queryByCount(criteria));
		datagrid.setRows(basPackageVOList);
		return datagrid;
	}

	public Json addBasPackage(BasPackageForm basPackageForm) throws Exception {
		Json json = new Json();
		BasPackage basPackage = new BasPackage();
		//System.out.println(basPackageForm.getPackid()+"=====================================");
		BeanUtils.copyProperties(basPackageForm, basPackage);
		basPackageMybatisDao.add(basPackageForm);
		json.setSuccess(true);
		return json;
	}

	public Json editBasPackage(BasPackageForm basPackageForm) {
		Json json = new Json();
		//BasPackageQuery basPackageQuery = new BasPackageQuery();
		//basPackageQuery.setPackid(basPackageForm.getPackid());
		//BasPackage basPackage = basPackageMybatisDao.queryById(basPackageQuery);
		//BeanUtils.copyProperties(basPackageForm, basPackage);
		basPackageMybatisDao.update(basPackageForm);
		json.setSuccess(true);

		return json;
	}

	public Json deleteBasPackage(String packid) {
		Json json = new Json();
		//BasPackageQuery basPackageQuery = new BasPackageQuery();
		//basPackageQuery.setPackid(packid);
		//BasPackage basPackage = basPackageMybatisDao.queryById(basPackageQuery);
		//System.out.println(packid+"==============================================");
		if(packid != null){
			basPackageMybatisDao.delete(packid);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasPackageCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasPackage> basPackageList = basPackageMybatisDao.queryByAll();
		if(basPackageList != null && basPackageList.size() > 0){
			for(BasPackage basPackage : basPackageList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basPackage.getPackid()));
				combobox.setValue(basPackage.getPackid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}