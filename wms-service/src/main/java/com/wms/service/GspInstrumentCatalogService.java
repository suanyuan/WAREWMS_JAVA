package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.GspInstrumentCatalogMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspInstrumentCatalog;
import com.wms.vo.GspInstrumentCatalogVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspInstrumentCatalogForm;
import com.wms.query.GspInstrumentCatalogQuery;

@Service("gspInstrumentCatalogService")
public class GspInstrumentCatalogService extends BaseService {

	@Autowired
	private GspInstrumentCatalogMybatisDao gspInstrumentCatalogMybatisDao;

	public EasyuiDatagrid<GspInstrumentCatalogVO> getPagedDatagrid(EasyuiDatagridPager pager, GspInstrumentCatalogQuery query) {
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);

		EasyuiDatagrid<GspInstrumentCatalogVO> datagrid = new EasyuiDatagrid<GspInstrumentCatalogVO>();
		List<GspInstrumentCatalog> gspInstrumentCatalogList = gspInstrumentCatalogMybatisDao.queryByList(mybatisCriteria);
		GspInstrumentCatalogVO gspInstrumentCatalogVO = null;
		List<GspInstrumentCatalogVO> gspInstrumentCatalogVOList = new ArrayList<GspInstrumentCatalogVO>();
		for (GspInstrumentCatalog gspInstrumentCatalog : gspInstrumentCatalogList) {
			gspInstrumentCatalogVO = new GspInstrumentCatalogVO();
			BeanUtils.copyProperties(gspInstrumentCatalog, gspInstrumentCatalogVO);
			gspInstrumentCatalogVOList.add(gspInstrumentCatalogVO);
		}
		int count = gspInstrumentCatalogMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspInstrumentCatalogVOList);
		return datagrid;
	}

	public Json addGspInstrumentCatalog(GspInstrumentCatalogForm gspInstrumentCatalogForm) throws Exception {
		Json json = new Json();
		GspInstrumentCatalog gspInstrumentCatalog = new GspInstrumentCatalog();
		BeanUtils.copyProperties(gspInstrumentCatalogForm, gspInstrumentCatalog);
		gspInstrumentCatalogMybatisDao.add(gspInstrumentCatalog);
		json.setSuccess(true);
		return json;
	}

	public Json editGspInstrumentCatalog(GspInstrumentCatalogForm gspInstrumentCatalogForm) {
		Json json = new Json();
		GspInstrumentCatalog gspInstrumentCatalog = gspInstrumentCatalogMybatisDao.queryById(gspInstrumentCatalogForm.getInstrumentCatalogId());
		BeanUtils.copyProperties(gspInstrumentCatalogForm, gspInstrumentCatalog);
		gspInstrumentCatalogMybatisDao.update(gspInstrumentCatalog);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspInstrumentCatalog(String id) {
		Json json = new Json();
		GspInstrumentCatalog gspInstrumentCatalog = gspInstrumentCatalogMybatisDao.queryById(id);
		if(gspInstrumentCatalog != null){
			gspInstrumentCatalogMybatisDao.delete(gspInstrumentCatalog);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspInstrumentCatalogCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspInstrumentCatalog> gspInstrumentCatalogList = gspInstrumentCatalogMybatisDao.queryByAll();
		if(gspInstrumentCatalogList != null && gspInstrumentCatalogList.size() > 0){
			for(GspInstrumentCatalog gspInstrumentCatalog : gspInstrumentCatalogList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspInstrumentCatalog.getInstrumentCatalogId()));
				combobox.setValue(gspInstrumentCatalog.getInstrumentCatalogName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}