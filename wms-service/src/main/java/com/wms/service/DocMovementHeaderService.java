package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.DocMovementHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.DocMovementHeader;
import com.wms.vo.DocMovementHeaderVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocMovementHeaderForm;
import com.wms.query.DocMovementHeaderQuery;

@Service("docMovementHeaderService")
public class DocMovementHeaderService extends BaseService {

	@Autowired
	private DocMovementHeaderMybatisDao docMovementHeaderDao;

	public EasyuiDatagrid<DocMovementHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocMovementHeaderQuery query) {
		EasyuiDatagrid<DocMovementHeaderVO> datagrid = new EasyuiDatagrid<DocMovementHeaderVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		DocMovementHeaderVO docMovementHeaderVO = null;
		List<DocMovementHeaderVO> docMovementHeaderVOList = new ArrayList<DocMovementHeaderVO>();
		List<DocMovementHeader> docMovementHeaderList = docMovementHeaderDao.queryByList(criteria);
		for (DocMovementHeader docMovementHeader : docMovementHeaderList) {
			docMovementHeaderVO = new DocMovementHeaderVO();
			BeanUtils.copyProperties(docMovementHeader, docMovementHeaderVO);
			docMovementHeaderVOList.add(docMovementHeaderVO);
		}
		int total = docMovementHeaderDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(docMovementHeaderVOList);
		return datagrid;
	}

	public Json addDocMovementHeader(DocMovementHeaderForm docMovementHeaderForm) throws Exception {
		Json json = new Json();
		DocMovementHeader docMovementHeader = new DocMovementHeader();
		BeanUtils.copyProperties(docMovementHeaderForm, docMovementHeader);
		docMovementHeaderDao.add(docMovementHeader);
		json.setSuccess(true);
		return json;
	}

	public Json editDocMovementHeader(DocMovementHeaderForm docMovementHeaderForm) {
		Json json = new Json();
		DocMovementHeader docMovementHeader = docMovementHeaderDao.queryById(docMovementHeaderForm.getMdocno());
		BeanUtils.copyProperties(docMovementHeaderForm, docMovementHeader);
		docMovementHeaderDao.update(docMovementHeader);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocMovementHeader(String id) {
		Json json = new Json();
		DocMovementHeader docMovementHeader = docMovementHeaderDao.queryById(id);
		if(docMovementHeader != null){
			docMovementHeaderDao.delete(docMovementHeader);
		}
		json.setSuccess(true);
		return json;
	}
}