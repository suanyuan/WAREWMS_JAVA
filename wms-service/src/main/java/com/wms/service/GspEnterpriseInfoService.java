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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
		EasyuiDatagrid<GspEnterpriseInfoVO> datagrid = new EasyuiDatagrid<GspEnterpriseInfoVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setOrderByClause("create_date desc");
		criteria.setCondition(query);
		GspEnterpriseInfoVO gspEnterpriseInfoVO = null;
		List<GspEnterpriseInfoVO> gspEnterpriseInfoVOList = new ArrayList<GspEnterpriseInfoVO>();
		List<GspEnterpriseInfo> gspEnterpriseInfoList;
		if(StringUtils.isEmpty(query.getType())){
			gspEnterpriseInfoList = gspEnterpriseInfoMybatisDao.queryByList(criteria);
		}else{
			gspEnterpriseInfoList = gspEnterpriseInfoMybatisDao.queryPageListByType(criteria);
		}
		for (GspEnterpriseInfo gspEnterpriseInfo : gspEnterpriseInfoList) {
			gspEnterpriseInfoVO = new GspEnterpriseInfoVO();
			BeanUtils.copyProperties(gspEnterpriseInfo, gspEnterpriseInfoVO);
			gspEnterpriseInfoVOList.add(gspEnterpriseInfoVO);
		}
		Long total = 0L;
		if(StringUtils.isEmpty(query.getType())){
			total = Long.parseLong(gspEnterpriseInfoMybatisDao.queryByCount(criteria)+"");
		}else {
			total = gspEnterpriseInfoMybatisDao.queryByCountByType(criteria);
		}

		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(gspEnterpriseInfoVOList);
		return datagrid;
	}

	public EasyuiDatagrid<GspEnterpriseInfoVO> getPagedDatagridType(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) throws Exception{
		EasyuiDatagrid<GspEnterpriseInfoVO> datagrid = new EasyuiDatagrid<GspEnterpriseInfoVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setOrderByClause("create_date desc");
		criteria.setCondition(query);
		GspEnterpriseInfoVO gspEnterpriseInfoVO = null;
		List<GspEnterpriseInfoVO> gspEnterpriseInfoVOList = new ArrayList<GspEnterpriseInfoVO>();
		List<GspEnterpriseInfo> gspEnterpriseInfoList = gspEnterpriseInfoMybatisDao.queryPageListByType(criteria);
		for (GspEnterpriseInfo gspEnterpriseInfo : gspEnterpriseInfoList) {
			gspEnterpriseInfoVO = new GspEnterpriseInfoVO();
			BeanUtils.copyProperties(gspEnterpriseInfo, gspEnterpriseInfoVO);
			/*if(gspEnterpriseInfoVO.getIsUse().equals(Constant.IS_USE_YES)){
				gspEnterpriseInfoVO.setIsUse("有效");
			}else {
				gspEnterpriseInfoVO.setIsUse("失效");
			}*/

			gspEnterpriseInfoVOList.add(gspEnterpriseInfoVO);
		}
		Long total = gspEnterpriseInfoMybatisDao.queryByCountByType(criteria);
		datagrid.setTotal(total);
		datagrid.setRows(gspEnterpriseInfoVOList);
		return datagrid;
	}


	public Json addGspEnterpriseInfo(GspEnterpriseInfoForm gspEnterpriseInfoForm)  {

		if(checkRep(gspEnterpriseInfoForm.getEnterpriseNo(),gspEnterpriseInfoForm.getEnterpriseName())){
			return Json.error("该企业信息已存在，企业名称和编号不能重复！");
		}

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
		SfcUserLogin userLogin =  SfcUserLoginUtil.getLoginUser();
		GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspEnterpriseInfoForm.getEnterpriseId());
		BeanUtils.copyProperties(gspEnterpriseInfoForm, gspEnterpriseInfo);
		gspEnterpriseInfo.setEditId(userLogin.getId());
//		gspEnterpriseInfo.setEditDate(new Date());
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
        MybatisCriteria criteria = new MybatisCriteria();
        GspEnterpriseInfoQuery query = new GspEnterpriseInfoQuery();
        query.setIsUse(Constant.IS_USE_YES);
        criteria.setCondition(query);
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspEnterpriseInfo> gspEnterpriseInfoList = gspEnterpriseInfoMybatisDao.queryByList(criteria);
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

	public void updateGspEnterpriseInfoActiveTag(String id,String tag) {
		GspEnterpriseInfoForm form = new GspEnterpriseInfoForm();
		form.setEnterpriseId(id);
		form.setIsUse(tag);
		form.setEditId(getLoginUserId());
		GspEnterpriseInfo gspEnterpriseInfo = new GspEnterpriseInfo();
		BeanUtils.copyProperties(form,gspEnterpriseInfo);
		gspEnterpriseInfoMybatisDao.updateBySelective(gspEnterpriseInfo);
	}

	public Json addEnterprise(GspEnterpriseInfo gspEnterpriseInfo) throws Exception {
		Json json = new Json();


		gspEnterpriseInfo.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		gspEnterpriseInfo.setEditId(SfcUserLoginUtil.getLoginUser().getId());

		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		gspEnterpriseInfo.setIsUse(Constant.IS_USE_YES);
		gspEnterpriseInfoMybatisDao.add(gspEnterpriseInfo);


		json.setSuccess(true);
		json.setMsg("添加成功");
		return json;
	}

	/**
	 * 检查企业id或者名称是否重复
	 * @param enterpriseNo
	 * @param enterpriseName
	 * @return
	 */
	private boolean checkRep(String enterpriseNo,String enterpriseName){
		GspEnterpriseInfoQuery query = new GspEnterpriseInfoQuery();
		MybatisCriteria criteria = new MybatisCriteria();
		query.setIsUse(Constant.IS_USE_YES);
		query.setEnterpriseNo(enterpriseNo);
		query.setEnterpriseName(enterpriseName);
		query.setSelect("juti");
		criteria.setCondition(query);
		GspEnterpriseInfoVO gspEnterpriseInfoVO = null;
		List<GspEnterpriseInfo> gspEnterpriseInfoList = gspEnterpriseInfoMybatisDao.queryPageListByType(criteria);
		if(gspEnterpriseInfoList!=null && gspEnterpriseInfoList.size()>0){
			return true;
		}else{
			return false;
		}
	}
}