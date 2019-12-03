package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.wms.mybatis.dao.BasSkuLeakMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.BasSkuLeakDao;
import com.wms.entity.BasSkuLeak;
import com.wms.vo.BasSkuLeakVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasSkuLeakForm;
import com.wms.query.BasSkuLeakQuery;

@Service("basSkuLeakService")
public class BasSkuLeakService extends BaseService {

	@Autowired
	private BasSkuLeakMybatisDao basSkuLeakMybatisDao;

	public EasyuiDatagrid<BasSkuLeakVO> getPagedDatagrid(EasyuiDatagridPager pager, BasSkuLeakQuery query) {
		EasyuiDatagrid<BasSkuLeakVO> datagrid = new EasyuiDatagrid<BasSkuLeakVO>();
		MybatisCriteria criteria = new MybatisCriteria();


//		if(query.getIdList()!=null&&query.getIdList()!="" ){
//			List<String> enterpriseIdList = jsonToList(query.getIdList(),String.class);
//			criteria.setIdList(enterpriseIdList);
//		}
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		List<BasSkuLeak> basSkuLeakList = basSkuLeakMybatisDao.queryByList(criteria);
		BasSkuLeakVO basSkuLeakVO = null;
		List<BasSkuLeakVO> basSkuLeakVOList = new ArrayList<BasSkuLeakVO>();
		for (BasSkuLeak basSkuLeak : basSkuLeakList) {
			basSkuLeakVO = new BasSkuLeakVO();
			BeanUtils.copyProperties(basSkuLeak, basSkuLeakVO);
			basSkuLeakVOList.add(basSkuLeakVO);
		}
		int total = basSkuLeakMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basSkuLeakVOList);
		return datagrid;
	}

	public Json addBasSkuLeak(BasSkuLeakForm basSkuLeakForm) throws Exception {
		Json json = new Json();
		BasSkuLeak basSkuLeak = new BasSkuLeak();
		BeanUtils.copyProperties(basSkuLeakForm, basSkuLeak);
		basSkuLeakMybatisDao.add(basSkuLeak);
		json.setSuccess(true);
		return json;
	}

	public Json editBasSkuLeak(BasSkuLeakForm basSkuLeakForm) {
		Json json = new Json();
		BasSkuLeak basSkuLeak = basSkuLeakMybatisDao.queryById(basSkuLeakForm.getCustomerid());
		BeanUtils.copyProperties(basSkuLeakForm, basSkuLeak);
		basSkuLeakMybatisDao.updateBySelective(basSkuLeak);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasSkuLeak(String id) {
		Json json = new Json();
		BasSkuLeak basSkuLeak = basSkuLeakMybatisDao.queryById(id);
		if(basSkuLeak != null){
			basSkuLeakMybatisDao.delete(basSkuLeak);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasSkuLeakCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasSkuLeak> basSkuLeakList = basSkuLeakMybatisDao.queryByAll();
		if(basSkuLeakList != null && basSkuLeakList.size() > 0){
			for(BasSkuLeak basSkuLeak : basSkuLeakList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basSkuLeak.getId()));
				combobox.setValue(basSkuLeak.getCustomerid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
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