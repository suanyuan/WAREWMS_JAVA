package com.wms.service;

import java.util.List;

import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspBusinessLicense;
import com.wms.vo.Json;
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
		gspBusinessLicense.setCreateId(getLoginUserId());
		gspBusinessLicenseMybatisDao.add(gspBusinessLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editGspBusinessLicense(GspBusinessLicenseForm gspBusinessLicenseForm) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(gspBusinessLicenseForm.getBusinessId().toString());
		BeanUtils.copyProperties(gspBusinessLicenseForm, gspBusinessLicense);
		gspBusinessLicenseMybatisDao.updateBySelective(gspBusinessLicense);
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

	public GspBusinessLicense getGspBusinessLicense(String id) {
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(id);
		return gspBusinessLicense;
	}

	public GspBusinessLicense getGspBusinessLicenseBy(GspBusinessLicenseQuery gspBusinessLicenseQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspBusinessLicenseQuery);
		List<GspBusinessLicense> licenses = gspBusinessLicenseMybatisDao.queryByList(mybatisCriteria);
		if(licenses!=null && licenses.size()>0){
			return licenses.get(0);
		}
		return null;
	}

	public void updateGspBusinessLicenseActiveTag(String enterpriseId,String tag) {
		GspBusinessLicenseForm form = new GspBusinessLicenseForm();
		form.setEnterpriseId(enterpriseId);
		form.setIsUse(tag);
		gspBusinessLicenseMybatisDao.updateBySelective(form);
	}

	public void updateGspBusinessLicenseTagById(String id,String tag) {
		GspBusinessLicenseForm form = new GspBusinessLicenseForm();
		form.setBusinessId(id);
		form.setIsUse(tag);
		gspBusinessLicenseMybatisDao.updateBySelective(form);
	}

}