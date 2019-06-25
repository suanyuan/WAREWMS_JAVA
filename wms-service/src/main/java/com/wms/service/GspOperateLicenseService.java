package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.query.GspOperateLicenseQuery;
import com.wms.vo.GspOperateLicenseVO;
import com.wms.vo.form.GspOperateLicenseForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspOperateLicense;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;

@Service("gspOperateLicenseService")
public class GspOperateLicenseService extends BaseService {

	@Autowired
	private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;

	public Json addGspOperateLicense(GspOperateLicenseForm gspOperateLicenseForm) throws Exception {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = new GspOperateLicense();
		BeanUtils.copyProperties(gspOperateLicenseForm, gspOperateLicense);
		gspBusinessLicenseMybatisDao.add(gspOperateLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editGspOperateLicense(GspOperateLicenseForm gspOperateLicenseForm) {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = gspBusinessLicenseMybatisDao.queryById(gspOperateLicenseForm.getOperateId());
		BeanUtils.copyProperties(gspOperateLicenseForm, gspOperateLicense);
		gspBusinessLicenseMybatisDao.update(gspOperateLicense);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspOperateLicense(String id) {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = gspBusinessLicenseMybatisDao.queryById(id);
		if(gspOperateLicense != null){
			gspBusinessLicenseMybatisDao.delete(gspOperateLicense);
		}
		json.setSuccess(true);
		return json;
	}

}