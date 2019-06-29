package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.GspProductRegisterSpecsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspProductRegisterSpecsDao;
import com.wms.entity.GspProductRegisterSpecs;
import com.wms.vo.GspProductRegisterSpecsVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterSpecsForm;
import com.wms.query.GspProductRegisterSpecsQuery;

@Service("gspProductRegisterSpecsService")
public class GspProductRegisterSpecsService extends BaseService {

	@Autowired
	private GspProductRegisterSpecsDao gspProductRegisterSpecsDao;
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;

	public EasyuiDatagrid<GspProductRegisterSpecsVO> getPagedDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {
//		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<GspProductRegisterSpecsVO>();
//		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsDao.getPagedDatagrid(pager, query);
//		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
//		List<GspProductRegisterSpecsVO> gspProductRegisterSpecsVOList = new ArrayList<GspProductRegisterSpecsVO>();
//		for (GspProductRegisterSpecs gspProductRegisterSpecs : gspProductRegisterSpecsList) {
//			gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
//			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
//			gspProductRegisterSpecsVOList.add(gspProductRegisterSpecsVO);
//		}
//		datagrid.setTotal(gspProductRegisterSpecsDao.countAll(query));
//		datagrid.setRows(gspProductRegisterSpecsVOList);
//		return datagrid;
		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<GspProductRegisterSpecsVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
		List<GspProductRegisterSpecsVO> basGtnVOList = new ArrayList<GspProductRegisterSpecsVO>();
		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsMybatisDao.queryByList(criteria);
		System.out.println(query.getSpecsName()+"==============query================================"+query.getProductRegisterNo());
		for (GspProductRegisterSpecs gspProductRegisterSpecs : gspProductRegisterSpecsList) {
			//System.out.println(gspProductRegisterSpecs.getSpecsName()+"==============================================");
			gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
			basGtnVOList.add(gspProductRegisterSpecsVO);
		}

		int total = gspProductRegisterSpecsMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}

	public Json addGspProductRegisterSpecs(GspProductRegisterSpecsForm gspProductRegisterSpecsForm) throws Exception {
		Json json = new Json();
		GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
		BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		gspProductRegisterSpecs.setSpecsId(RandomUtil.getUUID());
		gspProductRegisterSpecsMybatisDao.add(gspProductRegisterSpecs);
		json.setSuccess(true);
		return json;
	}

	public Json editGspProductRegisterSpecs(GspProductRegisterSpecsForm gspProductRegisterSpecsForm) {
		Json json = new Json();
		GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
		BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		//GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsDao.findById(gspProductRegisterSpecsForm.getSpecsId());
		//BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		gspProductRegisterSpecsMybatisDao.update(gspProductRegisterSpecs);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspProductRegisterSpecs(String id) {
		Json json = new Json();
		//GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.findById(id);
		if(id != null){
			gspProductRegisterSpecsMybatisDao.delete(id);
		}
		json.setSuccess(true);
		return json;
	}


	public Json getGspProductRegisterSpecsInfo(String id){
		GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.queryById(id);
		if(gspProductRegisterSpecs == null){
			return Json.error("企业信息不存在！");
		}
		return Json.success("",gspProductRegisterSpecs);
	}


	public List<EasyuiCombobox> getGspProductRegisterSpecsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsDao.findAll();
		if(gspProductRegisterSpecsList != null && gspProductRegisterSpecsList.size() > 0){
			for(GspProductRegisterSpecs gspProductRegisterSpecs : gspProductRegisterSpecsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspProductRegisterSpecs.getSpecsId()));
				combobox.setValue(gspProductRegisterSpecs.getAlternatName1());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}