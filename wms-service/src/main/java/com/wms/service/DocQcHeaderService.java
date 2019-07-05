package com.wms.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.DocQcHeaderDao;
import com.wms.entity.DocQcHeader;
import com.wms.vo.DocQcHeaderVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocQcHeaderForm;
import com.wms.query.DocQcHeaderQuery;

@Service("docQcHeaderService")
public class DocQcHeaderService extends BaseService {

	@Autowired
	private DocQcHeaderDao docQcHeaderDao;

	public EasyuiDatagrid<DocQcHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocQcHeaderQuery query) {
		EasyuiDatagrid<DocQcHeaderVO> datagrid = new EasyuiDatagrid<DocQcHeaderVO>();
		List<DocQcHeader> docQcHeaderList = docQcHeaderDao.getPagedDatagrid(pager, query);
		DocQcHeaderVO docQcHeaderVO = null;
		List<DocQcHeaderVO> docQcHeaderVOList = new ArrayList<DocQcHeaderVO>();
		for (DocQcHeader docQcHeader : docQcHeaderList) {
			docQcHeaderVO = new DocQcHeaderVO();
			BeanUtils.copyProperties(docQcHeader, docQcHeaderVO);
			docQcHeaderVOList.add(docQcHeaderVO);
		}
		datagrid.setTotal(docQcHeaderDao.countAll(query));
		datagrid.setRows(docQcHeaderVOList);
		return datagrid;
	}

	public Json addDocQcHeader(DocQcHeaderForm docQcHeaderForm) throws Exception {
		Json json = new Json();
		DocQcHeader docQcHeader = new DocQcHeader();
		BeanUtils.copyProperties(docQcHeaderForm, docQcHeader);
		docQcHeaderDao.save(docQcHeader);
		json.setSuccess(true);
		return json;
	}

	public Json editDocQcHeader(DocQcHeaderForm docQcHeaderForm) {
		Json json = new Json();
		DocQcHeader docQcHeader = docQcHeaderDao.findById(docQcHeaderForm.getCustomerid());
		BeanUtils.copyProperties(docQcHeaderForm, docQcHeader);
		docQcHeaderDao.update(docQcHeader);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocQcHeader(String id) {
		Json json = new Json();
		DocQcHeader docQcHeader = docQcHeaderDao.findById(id);
		if(docQcHeader != null){
			docQcHeaderDao.delete(docQcHeader);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getDocQcHeaderCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocQcHeader> docQcHeaderList = docQcHeaderDao.findAll();
		if(docQcHeaderList != null && docQcHeaderList.size() > 0){
			for(DocQcHeader docQcHeader : docQcHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docQcHeader.getCustomerid()));
				combobox.setValue(docQcHeader.getCustomerid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}