package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspBusinessLicenseDao;
import com.wms.entity.GspBusinessLicense;
import com.wms.vo.GspBusinessLicenseVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspBusinessLicenseForm;
import com.wms.query.GspBusinessLicenseQuery;

@Service("gspBusinessLicenseService")
public class GspBusinessLicenseService extends BaseService {

	@Autowired
	private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;

	public Json addGspBusinessLicense(GspBusinessLicenseForm gspBusinessLicenseForm) throws Exception {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = new GspBusinessLicense();
		BeanUtils.copyProperties(gspBusinessLicenseForm, gspBusinessLicense);
		gspBusinessLicenseMybatisDao.add(gspBusinessLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editGspBusinessLicense(GspBusinessLicenseForm gspBusinessLicenseForm) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(gspBusinessLicenseForm.getBusinessId().toString());
		BeanUtils.copyProperties(gspBusinessLicenseForm, gspBusinessLicense);
		gspBusinessLicenseMybatisDao.update(gspBusinessLicense);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspBusinessLicense(String id) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(id);
		if(gspBusinessLicense != null){
			gspBusinessLicenseMybatisDao.delete(gspBusinessLicense);
		}
		json.setSuccess(true);
		return json;
	}

}