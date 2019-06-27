package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.mybatis.dao.GspOperateLicenseMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
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
	private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;

	public Json addGspOperateLicense(GspOperateLicenseForm gspOperateLicenseForm) throws Exception {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = new GspOperateLicense();
		BeanUtils.copyProperties(gspOperateLicenseForm, gspOperateLicense);
		gspOperateLicenseMybatisDao.add(gspOperateLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editGspOperateLicense(GspOperateLicenseForm gspOperateLicenseForm) {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.queryById(gspOperateLicenseForm.getOperateId());
		BeanUtils.copyProperties(gspOperateLicenseForm, gspOperateLicense);
		gspOperateLicenseMybatisDao.updateBySelective(gspOperateLicense);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspOperateLicense(String id) {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.queryById(id);
		if(gspOperateLicense != null){
			gspOperateLicenseMybatisDao.delete(gspOperateLicense);
		}
		json.setSuccess(true);
		return json;
	}

	public GspOperateLicense getGspOperateLicense(String id) {
		GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.queryById(id);
		return gspOperateLicense;
	}

	public GspOperateLicense getGspOperateLicenseBy(GspOperateLicenseQuery gspOperateLicenseQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspOperateLicenseQuery);
		List<GspOperateLicense> list = gspOperateLicenseMybatisDao.queryByList(mybatisCriteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void updateGspOperateLicenseActiveTag(String id,String tag) {
		GspOperateLicenseForm form = new GspOperateLicenseForm();
		form.setEnterpriseId(id);
		form.setIsUse(tag);
		gspOperateLicenseMybatisDao.updateBySelective(form);
	}

}