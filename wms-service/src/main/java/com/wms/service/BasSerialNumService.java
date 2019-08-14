package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.BasSerialNumMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.BasSerialNum;
import com.wms.vo.BasSerialNumVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasSerialNumForm;
import com.wms.query.BasSerialNumQuery;

@Service("basSerialNumService")
public class BasSerialNumService extends BaseService {

	@Autowired
	private BasSerialNumMybatisDao basSerialNumDao;

	public EasyuiDatagrid<BasSerialNumVO> getPagedDatagrid(EasyuiDatagridPager pager, BasSerialNumQuery query) {
		EasyuiDatagrid<BasSerialNumVO> datagrid = new EasyuiDatagrid<BasSerialNumVO>();
		List<BasSerialNum> basSerialNumList = basSerialNumDao.queryByList(new MybatisCriteria());
		BasSerialNumVO basSerialNumVO = null;
		List<BasSerialNumVO> basSerialNumVOList = new ArrayList<BasSerialNumVO>();
		for (BasSerialNum basSerialNum : basSerialNumList) {
			basSerialNumVO = new BasSerialNumVO();
			BeanUtils.copyProperties(basSerialNum, basSerialNumVO);
			basSerialNumVOList.add(basSerialNumVO);
		}
		datagrid.setTotal(basSerialNumDao.queryTotalCount());
		datagrid.setRows(basSerialNumVOList);
		return datagrid;
	}

	public Json addBasSerialNum(BasSerialNumForm basSerialNumForm) throws Exception {
		Json json = new Json();
		BasSerialNum basSerialNum = new BasSerialNum();
		BeanUtils.copyProperties(basSerialNumForm, basSerialNum);
		basSerialNumDao.add(basSerialNum);
		json.setSuccess(true);
		return json;
	}

	public Json editBasSerialNum(BasSerialNumForm basSerialNumForm) {
		Json json = new Json();
		BasSerialNum basSerialNum = basSerialNumDao.queryById(basSerialNumForm.getSerialNum());
		BeanUtils.copyProperties(basSerialNumForm, basSerialNum);
		basSerialNumDao.update(basSerialNum);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasSerialNum(String id) {
		Json json = new Json();
		BasSerialNum basSerialNum = basSerialNumDao.queryById(id);
		if(basSerialNum != null){
			basSerialNumDao.delete(basSerialNum);
		}
		json.setSuccess(true);
		return json;
	}
//
//	public List<EasyuiCombobox> getBasSerialNumCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<BasSerialNum> basSerialNumList = basSerialNumDao.();
//		if(basSerialNumList != null && basSerialNumList.size() > 0){
//			for(BasSerialNum basSerialNum : basSerialNumList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(basSerialNum.getId()));
//				combobox.setValue(basSerialNum.getBasSerialNumName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

}