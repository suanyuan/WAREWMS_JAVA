package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.GspEnterpriseInfoQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.GspEnterpriseInfoVO;
import com.wms.vo.Json;
import com.wms.vo.form.GspEnterpriseInfoForm;
import com.wms.vo.form.GspProductRegisterSpecsForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//		GspProductRegisterSpecsForm gspProductRegisterSpecsForm = JSON.parseObject(query.getIdList(),GspProductRegisterSpecsForm.class);

//		String s1 = query.getIdList().replace("[","");
//		String s2 = s1.replace("]","");
//		String enterpriseId = new String();
		MybatisCriteria criteria = new MybatisCriteria();
		if(query.getIdList()!=null&&query.getIdList()!="" ){
			List<String> enterpriseIdList = jsonToList(query.getIdList(),String.class);
			criteria.setIdList(enterpriseIdList);
		}
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
			total  = Long.parseLong(gspEnterpriseInfoMybatisDao.queryByCountByType(criteria)+"");
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

	//导出
	public void exportDataToExcel(HttpServletResponse response, GspEnterpriseInfoQuery form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			if(form.getIdList()!=null&&form.getIdList()!="" ){
				List<String> enterpriseIdList = jsonToList(form.getIdList(),String.class);
				mybatisCriteria.setIdList(enterpriseIdList);
			}
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getGspEnterpriseToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "企业信息";
			// excel要导出的数据
			List<GspEnterpriseInfo> searchBasCustomerList = gspEnterpriseInfoMybatisDao.queryByList(mybatisCriteria);
			// 导出


			if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
				System.out.println("企业信息为空");
			}else {
				for (GspEnterpriseInfo gspEnterpriseInfo: searchBasCustomerList) {


					//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
					Date date=null;

					if("1".equals(gspEnterpriseInfo.getIsUse())){
						gspEnterpriseInfo.setIsUse("是");
					}else if("0".equals(gspEnterpriseInfo.getIsUse())){
						gspEnterpriseInfo.setIsUse("否");
					}

					//企业类型
					if("GNSC".equals(gspEnterpriseInfo.getEnterpriseType())){
						gspEnterpriseInfo.setEnterpriseType("生产");
					}else if("GW".equals(gspEnterpriseInfo.getEnterpriseType())){
						gspEnterpriseInfo.setEnterpriseType("国外企业");
					}else if("JY".equals(gspEnterpriseInfo.getEnterpriseType())){
						gspEnterpriseInfo.setEnterpriseType("经营");
					}else if("kd".equals(gspEnterpriseInfo.getEnterpriseType())){
						gspEnterpriseInfo.setEnterpriseType("快递/物流");
					}else if("SCJY".equals(gspEnterpriseInfo.getEnterpriseType())){
						gspEnterpriseInfo.setEnterpriseType("生产和经营");
					}else if("YL".equals(gspEnterpriseInfo.getEnterpriseType())){
						gspEnterpriseInfo.setEnterpriseType("医疗机构");
					}else if("ZT".equals(gspEnterpriseInfo.getEnterpriseType())){
						gspEnterpriseInfo.setEnterpriseType("主体");
					}

				}
//                List<FirstReviewLog> searchBasCustomerFormList  = new ArrayList<FirstReviewLog>();

				//将list集合转化为excle
				ExcelUtil.listToExcel(searchBasCustomerList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getGspEnterpriseToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("isUse", "是否有效");
		superClassMap.put("enterpriseNo", "企业代码");
		superClassMap.put("shorthandName", "简称");
		superClassMap.put("enterpriseName", "企业名称");
		superClassMap.put("enterpriseType", "企业类型");
		superClassMap.put("createDate", "创建时间");
		superClassMap.put("createId", "创建人");
		superClassMap.put("editDate", "编辑时间");
		superClassMap.put("editId", "编辑人");

		return superClassMap;
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


	/**
	 * json 转 List<T>
	 */
	public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}
}