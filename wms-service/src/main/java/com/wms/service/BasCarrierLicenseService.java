package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.BasCarrierLicenseMybatisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.BasCarrierLicenseDao;
import com.wms.entity.BasCarrierLicense;
import com.wms.vo.BasCarrierLicenseVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasCarrierLicenseForm;
import com.wms.query.BasCarrierLicenseQuery;

@Service("basCarrierLicenseService")
public class BasCarrierLicenseService extends BaseService {


	@Autowired
	private BasCarrierLicenseMybatisDao basCarrierLicenseMybatisDao;

	@Autowired
	private BasCarrierLicenseDao basCarrierLicenseDao;

	public EasyuiDatagrid<BasCarrierLicenseVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCarrierLicenseQuery query) {
		EasyuiDatagrid<BasCarrierLicenseVO> datagrid = new EasyuiDatagrid<BasCarrierLicenseVO>();
		List<BasCarrierLicense> basCarrierLicenseList = basCarrierLicenseDao.getPagedDatagrid(pager, query);
		BasCarrierLicenseVO basCarrierLicenseVO = null;
		List<BasCarrierLicenseVO> basCarrierLicenseVOList = new ArrayList<BasCarrierLicenseVO>();
		for (BasCarrierLicense basCarrierLicense : basCarrierLicenseList) {
			basCarrierLicenseVO = new BasCarrierLicenseVO();
			BeanUtils.copyProperties(basCarrierLicense, basCarrierLicenseVO);
			basCarrierLicenseVOList.add(basCarrierLicenseVO);
		}
		datagrid.setTotal(basCarrierLicenseDao.countAll(query));
		datagrid.setRows(basCarrierLicenseVOList);
		return datagrid;
	}

	public Json addBasCarrierLicense(BasCarrierLicenseForm basCarrierLicenseForm) throws Exception {
		Json json = new Json();
		BasCarrierLicense basCarrierLicense = new BasCarrierLicense();
		BeanUtils.copyProperties(basCarrierLicenseForm, basCarrierLicense);
		basCarrierLicenseMybatisDao.add(basCarrierLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editBasCarrierLicense(BasCarrierLicenseForm basCarrierLicenseForm) {
		Json json = new Json();
		BasCarrierLicense basCarrierLicense = basCarrierLicenseDao.findById(basCarrierLicenseForm.getCarrierLicenseId());
		BeanUtils.copyProperties(basCarrierLicenseForm, basCarrierLicense);
		basCarrierLicenseDao.update(basCarrierLicense);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasCarrierLicense(String id) {
		Json json = new Json();
		BasCarrierLicense basCarrierLicense = basCarrierLicenseDao.findById(id);
		if(basCarrierLicense != null){
			basCarrierLicenseDao.delete(basCarrierLicense);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasCarrierLicenseCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasCarrierLicense> basCarrierLicenseList = basCarrierLicenseDao.findAll();
		if(basCarrierLicenseList != null && basCarrierLicenseList.size() > 0){
			for(BasCarrierLicense basCarrierLicense : basCarrierLicenseList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCarrierLicense.getCarrierLicenseId());
				combobox.setValue(basCarrierLicense.getCarrierNo());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public Json getBasCarrierLicenseInfo(String enterpriceId){



		BasCarrierLicense basCarrierLicense = basCarrierLicenseMybatisDao.queryById(enterpriceId);
		if(basCarrierLicense == null){
			return Json.error("企业信息不存在！");
		}
		return Json.success("",basCarrierLicense);
	}

}