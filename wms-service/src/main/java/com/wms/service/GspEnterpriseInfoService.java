package com.wms.service;

import com.wms.dao.GspEnterpriseInfoDao;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.query.GspEnterpriseInfoQuery;
import com.wms.vo.GspEnterpriseInfoVO;
import com.wms.vo.Json;
import com.wms.vo.form.GspEnterpriseInfoForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("gspEnterpriseInfoService")
public class GspEnterpriseInfoService extends BaseService {

	@Autowired
	private GspEnterpriseInfoDao gspEnterpriseInfoDao;

	public EasyuiDatagrid<GspEnterpriseInfoVO> getPagedDatagrid(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) {
		EasyuiDatagrid<GspEnterpriseInfoVO> datagrid = new EasyuiDatagrid<GspEnterpriseInfoVO>();
		List<GspEnterpriseInfo> gspEnterpriseInfoList = gspEnterpriseInfoDao.getPagedDatagrid(pager, query);
		GspEnterpriseInfoVO gspEnterpriseInfoVO = null;
		List<GspEnterpriseInfoVO> gspEnterpriseInfoVOList = new ArrayList<GspEnterpriseInfoVO>();
		for (GspEnterpriseInfo gspEnterpriseInfo : gspEnterpriseInfoList) {
			gspEnterpriseInfoVO = new GspEnterpriseInfoVO();
			BeanUtils.copyProperties(gspEnterpriseInfo, gspEnterpriseInfoVO);
			gspEnterpriseInfoVOList.add(gspEnterpriseInfoVO);
		}
		datagrid.setTotal(gspEnterpriseInfoDao.countAll(query));
		datagrid.setRows(gspEnterpriseInfoVOList);
		return datagrid;
	}

	public Json addGspEnterpriseInfo(GspEnterpriseInfoForm gspEnterpriseInfoForm) throws Exception {
		Json json = new Json();
		GspEnterpriseInfo gspEnterpriseInfo = new GspEnterpriseInfo();
		BeanUtils.copyProperties(gspEnterpriseInfoForm, gspEnterpriseInfo);
		gspEnterpriseInfoDao.save(gspEnterpriseInfo);
		json.setSuccess(true);
		return json;
	}

	public Json editGspEnterpriseInfo(GspEnterpriseInfoForm gspEnterpriseInfoForm) {
		Json json = new Json();
		GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoDao.findById(gspEnterpriseInfoForm.getEnterpriseId().toString());
		BeanUtils.copyProperties(gspEnterpriseInfoForm, gspEnterpriseInfo);
		gspEnterpriseInfoDao.update(gspEnterpriseInfo);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspEnterpriseInfo(String id) {
		Json json = new Json();
		GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoDao.findById(id);
		if(gspEnterpriseInfo != null){
			gspEnterpriseInfoDao.delete(gspEnterpriseInfo);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspEnterpriseInfoCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspEnterpriseInfo> gspEnterpriseInfoList = gspEnterpriseInfoDao.findAll();
		if(gspEnterpriseInfoList != null && gspEnterpriseInfoList.size() > 0){
			for(GspEnterpriseInfo gspEnterpriseInfo : gspEnterpriseInfoList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspEnterpriseInfo.getEnterpriseId()));
				combobox.setValue(gspEnterpriseInfo.getEnterpriseName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}