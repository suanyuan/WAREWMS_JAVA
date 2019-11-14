package com.wms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.GspInstrumentCatalog;
import com.wms.mybatis.dao.GspOperateDetailMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.GspOperateLicenseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspOperateDetail;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspOperateDetailForm;
import com.wms.query.GspOperateDetailQuery;

@Service("gspOperateDetailService")
public class GspOperateDetailService extends BaseService {

	@Autowired
	private GspOperateDetailMybatisDao gspOperateDetailMybatisDao;
	@Autowired
	private GspInstrumentCatalogService gspInstrumentCatalogService;

	/**
	 * 初始化经营范围
	 * @param scope
	 * @return
	 */
	public String initScope(String scope){
		String[] scopeArr = scope.split(",");
		StringBuffer resultArr = new StringBuffer();
		for(String str : scopeArr){
			resultArr.append("{");
			resultArr.append("enterpriseId:''");
			resultArr.append(",operateId:'"+str+"'");
			resultArr.append("},");

		}
		return "["+resultArr.substring(0,resultArr.length()-1)+"]";
	}

	public EasyuiDatagrid<GspOperateDetailVO> getPagedDatagrid(EasyuiDatagridPager pager, GspOperateDetailQuery query) {
		EasyuiDatagrid<GspOperateDetailVO> datagrid = new EasyuiDatagrid<GspOperateDetailVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		List<GspOperateDetail> gspOperateDetailList = gspOperateDetailMybatisDao.queryByList(criteria);
		GspOperateDetailVO gspOperateDetailVO = null;
		List<GspOperateDetailVO> gspOperateDetailVOList = new ArrayList<GspOperateDetailVO>();
		for (GspOperateDetail gspOperateDetail : gspOperateDetailList) {
			gspOperateDetailVO = new GspOperateDetailVO();
			BeanUtils.copyProperties(gspOperateDetail, gspOperateDetailVO);
			gspOperateDetailVOList.add(gspOperateDetailVO);
		}
		int total = gspOperateDetailMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(gspOperateDetailVOList);
		return datagrid;
	}

	public Json addGspOperateDetail(GspOperateDetailForm gspOperateDetailForm,String licenseType) throws Exception {
		Json json = new Json();
		GspOperateDetail gspOperateDetail = new GspOperateDetail();
		gspOperateDetail.setLicenseType(licenseType);
		gspOperateDetail.setLicenseId(gspOperateDetailForm.getEnterpriseId());
		gspOperateDetail.setOperateId(gspOperateDetailForm.getOperateId());
		gspOperateDetail.setIsUse(Constant.IS_USE_YES);
		//BeanUtils.copyProperties(gspOperateDetailForm, gspOperateDetail);
		gspOperateDetailMybatisDao.add(gspOperateDetail);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspOperateDetail(String id,String licenseType) {
		Json json = new Json();
		GspOperateDetailQuery query = new GspOperateDetailQuery();
		query.setLicenseId(id);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		Long result = gspOperateDetailMybatisDao.deleteByLicenseId(id,licenseType);
		if(result>0){
			json.setSuccess(true);
		}else{
			json.setSuccess(false);
		}

		return json;
	}

	public List<GspOperateDetailVO> queryOperateDetailByLicense(String license){
		List<GspOperateDetailVO> voList = new ArrayList<>();
		List<GspOperateDetailVO> IList = new ArrayList<>();
		List<GspOperateDetailVO> IIList = new ArrayList<>();
		List<GspOperateDetailVO> IIIList = new ArrayList<>();
		GspOperateDetailQuery query = new GspOperateDetailQuery();
		query.setLicenseId(license);
		query.setIsUse(Constant.IS_USE_YES);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		List<GspOperateDetail> list = gspOperateDetailMybatisDao.queryByList(criteria);
		for(GspOperateDetail g : list){
			GspOperateDetailVO v = new GspOperateDetailVO();
			v.setOperateId(g.getOperateId().trim());
			GspInstrumentCatalog gspInstrumentCatalog = gspInstrumentCatalogService.getGspInstrumentCatalog(g.getOperateId());
			if(gspInstrumentCatalog!=null){

				gspInstrumentCatalog.getInstrumentCatalogNo();
				if(gspInstrumentCatalog.getClassifyId()!=null){
					v.setOperateName("["+gspInstrumentCatalog.getClassifyId()+"]"+gspInstrumentCatalog.getInstrumentCatalogName().trim());
				}else{
					v.setOperateName(gspInstrumentCatalog.getInstrumentCatalogName().trim());
				}
				v.setInstrumentCatalogNo(Integer.parseInt(gspInstrumentCatalog.getInstrumentCatalogNo()));
				if("I".equals(gspInstrumentCatalog.getClassifyId())){
					IList.add(v);
					Collections.sort(IList);
				}else if("II".equals(gspInstrumentCatalog.getClassifyId())){
					IIList.add(v);
					Collections.sort(IIList);
				}else if("III".equals(gspInstrumentCatalog.getClassifyId())){
					IIIList.add(v);
					Collections.sort(IIIList);
				}else{
					IList.add(v);
				}
			}

		}
		voList.addAll(IList);
		voList.addAll(IIList);
		voList.addAll(IIIList);
		return voList;
	}



	public Json invalidGspOperateDetail(String id,String licenseType) {
		Json json = new Json();
		GspOperateDetailQuery query = new GspOperateDetailQuery();
		query.setLicenseId(id);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		Long result = gspOperateDetailMybatisDao.updateByLicenseId(id,licenseType);
		if(result>0){
			json.setSuccess(true);
		}else{
			json.setSuccess(false);
		}

		return json;
	}

	public GspOperateDetail queryOperateById(String id){
		GspOperateDetailQuery query = new GspOperateDetailQuery();
		query.setLicenseId(id);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		List<GspOperateDetail> operateDetails = gspOperateDetailMybatisDao.queryByList(criteria);
		if(operateDetails!=null && operateDetails.size()>0){
			return operateDetails.get(0);
		}
		return null;
	}

}