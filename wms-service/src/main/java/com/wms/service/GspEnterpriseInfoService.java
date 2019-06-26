package com.wms.service;

import com.wms.constant.Constant;
import com.wms.dao.GspEnterpriseInfoDao;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.SfcUser;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.GspEnterpriseInfoQuery;
import com.wms.utils.SfcUserLoginUtil;
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
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private GspBusinessLicenseService gspBusinessLicenseService;
	@Autowired
	private GspOperateLicenseService gspOperateLicenseService;
	@Autowired
	private GspSecondRecordService gspSecondRecordService;


	public EasyuiDatagrid<GspEnterpriseInfoVO> getPagedDatagrid(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) throws Exception{
		/*EasyuiDatagrid<GspEnterpriseInfoVO> datagrid = new EasyuiDatagrid<GspEnterpriseInfoVO>();
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
		return datagrid;*/
		EasyuiDatagrid<GspEnterpriseInfoVO> datagrid = new EasyuiDatagrid<GspEnterpriseInfoVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		GspEnterpriseInfoVO gspEnterpriseInfoVO = null;
		List<GspEnterpriseInfoVO> gspEnterpriseInfoVOList = new ArrayList<GspEnterpriseInfoVO>();
		List<GspEnterpriseInfo> gspEnterpriseInfoList = gspEnterpriseInfoMybatisDao.queryByList(criteria);
		for (GspEnterpriseInfo gspEnterpriseInfo : gspEnterpriseInfoList) {
			gspEnterpriseInfoVO = new GspEnterpriseInfoVO();
			BeanUtils.copyProperties(gspEnterpriseInfo, gspEnterpriseInfoVO);
			gspEnterpriseInfoVOList.add(gspEnterpriseInfoVO);
		}
		int total = gspEnterpriseInfoMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(gspEnterpriseInfoVOList);
		return datagrid;
	}

	public Json addGspEnterpriseInfo(GspEnterpriseInfoForm gspEnterpriseInfoForm) throws Exception {
		SfcUserLogin userLogin =  SfcUserLoginUtil.getLoginUser();
		Json json = new Json();
		GspEnterpriseInfo gspEnterpriseInfo = new GspEnterpriseInfo();
		BeanUtils.copyProperties(gspEnterpriseInfoForm, gspEnterpriseInfo);
		gspEnterpriseInfo.setIsUse(Constant.IS_USE_YES);
		gspEnterpriseInfo.setCreateId(userLogin.getId());
		gspEnterpriseInfoMybatisDao.add(gspEnterpriseInfo);
		json.setSuccess(true);
		return json;
	}

	public Json editGspEnterpriseInfo(GspEnterpriseInfoForm gspEnterpriseInfoForm) {
		Json json = new Json();
		GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspEnterpriseInfoForm.getEnterpriseId());
		BeanUtils.copyProperties(gspEnterpriseInfoForm, gspEnterpriseInfo);
		gspEnterpriseInfoMybatisDao.updateBySelective(gspEnterpriseInfo);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspEnterpriseInfo(String id) {
		Json json = new Json();
		GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(id);
		if(gspEnterpriseInfo != null){
			gspEnterpriseInfoMybatisDao.delete(gspEnterpriseInfo);
		}
		json.setSuccess(true);
		return json;
	}

	public GspEnterpriseInfo getGspEnterpriseInfo(String id) {
		GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(id);
		return gspEnterpriseInfo;
	}

	public List<EasyuiCombobox> getGspEnterpriseInfoCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspEnterpriseInfo> gspEnterpriseInfoList = gspEnterpriseInfoMybatisDao.queryListByAll();
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