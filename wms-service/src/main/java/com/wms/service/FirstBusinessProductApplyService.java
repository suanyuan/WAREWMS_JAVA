package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.FirstBusinessProductApplyMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.FirstBusinessProductApply;
import com.wms.vo.FirstBusinessProductApplyVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstBusinessProductApplyForm;
import com.wms.query.FirstBusinessProductApplyQuery;

@Service("firstBusinessProductApplyService")
public class FirstBusinessProductApplyService extends BaseService {

	@Autowired
	private FirstBusinessProductApplyMybatisDao firstBusinessProductApplyMybatisDao;

	public EasyuiDatagrid<FirstBusinessProductApplyVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstBusinessProductApplyQuery query) {
		EasyuiDatagrid<FirstBusinessProductApplyVO> datagrid = new EasyuiDatagrid<FirstBusinessProductApplyVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		List<FirstBusinessProductApply> firstBusinessProductApplyList = firstBusinessProductApplyMybatisDao.queryByList(criteria);
		FirstBusinessProductApplyVO firstBusinessProductApplyVO = null;
		List<FirstBusinessProductApplyVO> firstBusinessProductApplyVOList = new ArrayList<FirstBusinessProductApplyVO>();
		for (FirstBusinessProductApply firstBusinessProductApply : firstBusinessProductApplyList) {
			firstBusinessProductApplyVO = new FirstBusinessProductApplyVO();
			BeanUtils.copyProperties(firstBusinessProductApply, firstBusinessProductApplyVO);
			firstBusinessProductApplyVOList.add(firstBusinessProductApplyVO);
		}
		int count = firstBusinessProductApplyMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(count + ""));
		datagrid.setRows(firstBusinessProductApplyVOList);
		return datagrid;
	}

	public Json addFirstBusinessProductApply(FirstBusinessProductApplyForm firstBusinessProductApplyForm) throws Exception {
		Json json = new Json();
		FirstBusinessProductApply firstBusinessProductApply = new FirstBusinessProductApply();
		BeanUtils.copyProperties(firstBusinessProductApplyForm, firstBusinessProductApply);
		firstBusinessProductApplyMybatisDao.add(firstBusinessProductApply);
		json.setSuccess(true);
		return json;
	}

	public Json editFirstBusinessProductApply(FirstBusinessProductApplyForm firstBusinessProductApplyForm) {
		Json json = new Json();
		FirstBusinessProductApply firstBusinessProductApply = firstBusinessProductApplyMybatisDao.queryById(firstBusinessProductApplyForm.getProductApplyId());
		BeanUtils.copyProperties(firstBusinessProductApplyForm, firstBusinessProductApply);
		firstBusinessProductApplyMybatisDao.update(firstBusinessProductApply);
		json.setSuccess(true);
		return json;
	}

	public Json deleteFirstBusinessProductApply(String id) {
		Json json = new Json();
		FirstBusinessProductApply firstBusinessProductApply = firstBusinessProductApplyMybatisDao.queryById(id);
		if (firstBusinessProductApply != null) {
			firstBusinessProductApplyMybatisDao.delete(firstBusinessProductApply);
		}
		json.setSuccess(true);
		return json;
	}

	public Json getListByApplyId(String applyId){
		MybatisCriteria criteria = new MybatisCriteria();
		FirstBusinessProductApplyQuery query = new FirstBusinessProductApplyQuery();
		query.setProductApplyId(applyId);
		criteria.setCondition(query);
		List<FirstBusinessProductApply> list = firstBusinessProductApplyMybatisDao.queryByList(criteria);
		if(list!=null && list.size()>0){
			return Json.success("",list);
		}else {
			return Json.error("查询不到对应的产品");
		}
	}
}