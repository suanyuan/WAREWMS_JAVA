package com.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wms.constant.Constant;
import com.wms.mybatis.dao.GSPProductRegisterMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspProductRegister;
import com.wms.vo.GspProductRegisterVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterForm;
import com.wms.query.GspProductRegisterQuery;

@Service("gspProductRegisterService")
public class GspProductRegisterService extends BaseService {

	@Autowired
	private GSPProductRegisterMybatisDao gspProductRegisterMybatisDao;

	public EasyuiDatagrid<GspProductRegisterVO> getPagedDatagrid(EasyuiDatagridPager pager, GspProductRegisterQuery query) {
		EasyuiDatagrid<GspProductRegisterVO> datagrid = new EasyuiDatagrid<GspProductRegisterVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");
		List<GspProductRegister> gspProductRegisterList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
		GspProductRegisterVO gspProductRegisterVO = null;
		List<GspProductRegisterVO> gspProductRegisterVOList = new ArrayList<GspProductRegisterVO>();
		for (GspProductRegister gspProductRegister : gspProductRegisterList) {
			gspProductRegisterVO = new GspProductRegisterVO();
			BeanUtils.copyProperties(gspProductRegister, gspProductRegisterVO);
			gspProductRegisterVOList.add(gspProductRegisterVO);
		}
		int count = gspProductRegisterMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspProductRegisterVOList);
		return datagrid;
	}

	public Json addGspProductRegister(GspProductRegisterForm gspProductRegisterForm) throws Exception {
		Json json = new Json();
		GspProductRegister gspProductRegister = new GspProductRegister();
		BeanUtils.copyProperties(gspProductRegisterForm, gspProductRegister);
		gspProductRegisterMybatisDao.add(gspProductRegister);
		json.setSuccess(true);
		return json;
	}

	public Json editGspProductRegister(GspProductRegisterForm gspProductRegisterForm) {
		Json json = new Json();
		GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(gspProductRegisterForm.getProductRegisterId());
		BeanUtils.copyProperties(gspProductRegisterForm, gspProductRegister);
		gspProductRegisterMybatisDao.update(gspProductRegister);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspProductRegister(String id) {
		Json json = new Json();
		GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(id);
		if(gspProductRegister != null){
			gspProductRegisterMybatisDao.delete(gspProductRegister);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspProductRegisterCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		Map<String,Object> query = new HashMap<>();
		query.put("is_use", Constant.IS_USE_YES);
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");
		EasyuiCombobox combobox = null;
		List<GspProductRegister> gspProductRegisterList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
		if(gspProductRegisterList != null && gspProductRegisterList.size() > 0){
			for(GspProductRegister gspProductRegister : gspProductRegisterList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspProductRegister.getProductRegisterId()));
				combobox.setValue(gspProductRegister.getProductRegisterNo()+"-"+gspProductRegister.getProductRegisterName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}