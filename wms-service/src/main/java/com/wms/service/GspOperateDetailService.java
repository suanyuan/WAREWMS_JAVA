package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.GspOperateDetailMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
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
		BeanUtils.copyProperties(gspOperateDetailForm, gspOperateDetail);
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
		return null;
	}

}