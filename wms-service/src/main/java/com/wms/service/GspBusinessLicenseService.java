package com.wms.service;

import java.util.ArrayList;
import java.util.List;
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
	private GspBusinessLicenseDao gspBusinessLicenseDao;

	public EasyuiDatagrid<GspBusinessLicenseVO> getPagedDatagrid(EasyuiDatagridPager pager, GspBusinessLicenseQuery query) {
		EasyuiDatagrid<GspBusinessLicenseVO> datagrid = new EasyuiDatagrid<GspBusinessLicenseVO>();
		List<GspBusinessLicense> gspBusinessLicenseList = gspBusinessLicenseDao.getPagedDatagrid(pager, query);
		GspBusinessLicenseVO gspBusinessLicenseVO = null;
		List<GspBusinessLicenseVO> gspBusinessLicenseVOList = new ArrayList<GspBusinessLicenseVO>();
		for (GspBusinessLicense gspBusinessLicense : gspBusinessLicenseList) {
			gspBusinessLicenseVO = new GspBusinessLicenseVO();
			BeanUtils.copyProperties(gspBusinessLicense, gspBusinessLicenseVO);
			gspBusinessLicenseVOList.add(gspBusinessLicenseVO);
		}
		datagrid.setTotal(gspBusinessLicenseDao.countAll(query));
		datagrid.setRows(gspBusinessLicenseVOList);
		return datagrid;
	}

	public Json addGspBusinessLicense(GspBusinessLicenseForm gspBusinessLicenseForm) throws Exception {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = new GspBusinessLicense();
		BeanUtils.copyProperties(gspBusinessLicenseForm, gspBusinessLicense);
		gspBusinessLicenseDao.save(gspBusinessLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editGspBusinessLicense(GspBusinessLicenseForm gspBusinessLicenseForm) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseDao.findById(gspBusinessLicenseForm.getBusinessId().toString());
		BeanUtils.copyProperties(gspBusinessLicenseForm, gspBusinessLicense);
		gspBusinessLicenseDao.update(gspBusinessLicense);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspBusinessLicense(String id) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseDao.findById(id);
		if(gspBusinessLicense != null){
			gspBusinessLicenseDao.delete(gspBusinessLicense);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspBusinessLicenseCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspBusinessLicense> gspBusinessLicenseList = gspBusinessLicenseDao.findAll();
		if(gspBusinessLicenseList != null && gspBusinessLicenseList.size() > 0){
			for(GspBusinessLicense gspBusinessLicense : gspBusinessLicenseList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspBusinessLicense.getBusinessId()));
				combobox.setValue(gspBusinessLicense.getLicenseName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}