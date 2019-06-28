package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.entity.BasGtn;
import com.wms.mybatis.dao.BasGtnMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.BasGtnDao;
import com.wms.vo.BasGtnVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasGtnForm;
import com.wms.query.BasGtnQuery;

@Service("basGtnService")
public class BasGtnService extends BaseService {

	@Autowired
	private BasGtnDao basGtnDao;
	@Autowired
	private BasGtnMybatisDao basGtnMybatisDao;

	public EasyuiDatagrid<BasGtnVO> getPagedDatagrid(EasyuiDatagridPager pager, BasGtnQuery query) {
		/*EasyuiDatagrid<BasGtnVO> datagrid = new EasyuiDatagrid<BasGtnVO>();
		List<BasGtn> basGtnList = basGtnDao.getPagedDatagrid(pager, query);
		BasGtnVO basGtnVO = null;
		List<BasGtnVO> basGtnVOList = new ArrayList<BasGtnVO>();
		for (BasGtn basGtn : basGtnList) {
			basGtnVO = new BasGtnVO();
			BeanUtils.copyProperties(basGtn, basGtnVO);
			basGtnVOList.add(basGtnVO);
		}
		datagrid.setTotal(basGtnDao.countAll(query));
		datagrid.setRows(basGtnVOList);


		return datagrid;*/
		//System.out.println(query.getSku()+"============================="+query.getGtncode());
		EasyuiDatagrid<BasGtnVO> datagrid = new EasyuiDatagrid<BasGtnVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		BasGtnVO basGtnVO = null;
		List<BasGtnVO> basGtnVOList = new ArrayList<BasGtnVO>();
		List<BasGtn> basGtnList = basGtnMybatisDao.queryByList(criteria);
		for (BasGtn basGtn : basGtnList) {
			basGtnVO = new BasGtnVO();
			BeanUtils.copyProperties(basGtn, basGtnVO);
			basGtnVOList.add(basGtnVO);
		}
		int total = basGtnMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}

	public Json addBasGtn(BasGtnForm basGtnForm) throws Exception {
		System.out.println(basGtnForm.getSku()+"============================="+basGtnForm.getGtncode());
		Json json = new Json();
		BasGtn basGtn = new BasGtn();

		BeanUtils.copyProperties(basGtnForm, basGtn);
		//basGtn.setSku(basGtnForm.getSku());
		//basGtn.setGtncode(basGtnForm.getGtncode());

		basGtnMybatisDao.add(basGtnForm);
		json.setSuccess(true);
		return json;
	}

	public Json editBasGtn(BasGtnForm basGtnForm) {
		Json json = new Json();
		System.out.println(basGtnForm.getSku()+"============================="+basGtnForm.getGtncode());
		//BasGtn basGtn = basGtnDao.findById(basGtnForm.getSku());
		//BeanUtils.copyProperties(basGtnForm, basGtn);
		basGtnMybatisDao.update(basGtnForm);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasGtn(String id) {
		Json json = new Json();
		//BasGtn basGtn = basGtnMybatisDao;
		//System.out.println("================================================"+id);
		basGtnMybatisDao.delete(id);

		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasGtnCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasGtn> basGtnList = basGtnDao.findAll();
		if(basGtnList != null && basGtnList.size() > 0){
			for(BasGtn basGtn : basGtnList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basGtn.getSku()));
				combobox.setValue(basGtn.getGtncode());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}