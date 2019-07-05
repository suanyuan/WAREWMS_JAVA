package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.GspSupplierMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspSupplierDao;
import com.wms.entity.GspSupplier;
import com.wms.vo.GspSupplierVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspSupplierForm;
import com.wms.query.GspSupplierQuery;

@Service("gspSupplierService")
public class GspSupplierService extends BaseService {

	@Autowired
	private GspSupplierDao gspSupplierDao;
	@Autowired
	private GspSupplierMybatisDao GspSupplierMybatisDao;


	public EasyuiDatagrid<GspSupplierVO> getPagedDatagrid(EasyuiDatagridPager pager, GspSupplierQuery query) {
//		EasyuiDatagrid<GspSupplierVO> datagrid = new EasyuiDatagrid<GspSupplierVO>();
//		List<GspSupplier> gspSupplierList = gspSupplierDao.getPagedDatagrid(pager, query);
//		GspSupplierVO gspSupplierVO = null;
//		List<GspSupplierVO> gspSupplierVOList = new ArrayList<GspSupplierVO>();
//		for (GspSupplier gspSupplier : gspSupplierList) {
//			gspSupplierVO = new GspSupplierVO();
//			BeanUtils.copyProperties(gspSupplier, gspSupplierVO);
//			gspSupplierVOList.add(gspSupplierVO);
//		}
//		datagrid.setTotal(gspSupplierDao.countAll(query));
//		datagrid.setRows(gspSupplierVOList);
//		return datagrid;

		EasyuiDatagrid<GspSupplierVO> datagrid = new EasyuiDatagrid<GspSupplierVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		GspSupplierVO basGtnVO = null;
		List<GspSupplierVO> basGtnVOList = new ArrayList<GspSupplierVO>();
		List<GspSupplier> basGtnList = GspSupplierMybatisDao.queryByList(criteria);
		for (GspSupplier basGtn : basGtnList) {
			basGtnVO = new GspSupplierVO();
			BeanUtils.copyProperties(basGtn, basGtnVO);
			basGtnVOList.add(basGtnVO);
		}
		int total = GspSupplierMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}

	public Json addGspSupplier(GspSupplierForm gspSupplierForm) throws Exception {
		Json json = new Json();
		GspSupplier gspSupplier = new GspSupplier();
		BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
		gspSupplier.setSupplierId(RandomUtil.getUUID());
		GspSupplierMybatisDao.add(gspSupplier);
		json.setSuccess(true);
		return json;
	}

	public Json editGspSupplier(GspSupplierForm gspSupplierForm) {
		Json json = new Json();
		//GspSupplier gspSupplier = gspSupplierDao.findById(gspSupplierForm.getSupplierId());
		//BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
		GspSupplierMybatisDao.update(gspSupplierForm);
		json.setSuccess(true);
		return json;
	}
	public Json getGspSupplierInfo(String supplierId){

		System.out.println("supplierId==========="+supplierId);
		GspSupplier gspSupplier = GspSupplierMybatisDao.queryById(supplierId);
		if(gspSupplier == null){
			return Json.error("不存在！");
		}
		return Json.success("",gspSupplier);
	}
	public Json deleteGspSupplier(String id) {
		Json json = new Json();
		//GspSupplier gspSupplier = gspSupplierDao.findById(id);
		if(id != null){
			GspSupplierMybatisDao.delete(id);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspSupplierCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspSupplier> gspSupplierList = gspSupplierDao.findAll();
		if(gspSupplierList != null && gspSupplierList.size() > 0){
			for(GspSupplier gspSupplier : gspSupplierList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspSupplier.getSupplierId()));
				combobox.setValue(gspSupplier.getEnterpriseId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}