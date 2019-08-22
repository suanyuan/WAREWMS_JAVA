package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocMtDetails;
import com.wms.mybatis.dao.DocMtDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocMtDetailsQuery;
import com.wms.vo.DocMtDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocMtDetailsForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docMtDetailsService")
public class DocMtDetailsService extends BaseService {

	@Autowired
	private DocMtDetailsMybatisDao docMtDetailsMybatisDao;

	public EasyuiDatagrid<DocMtDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocMtDetailsQuery query) {
		EasyuiDatagrid<DocMtDetailsVO> datagrid = new EasyuiDatagrid<DocMtDetailsVO>();
		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByList(new MybatisCriteria());
		DocMtDetailsVO docMtDetailsVO = null;
		List<DocMtDetailsVO> docMtDetailsVOList = new ArrayList<DocMtDetailsVO>();
		for (DocMtDetails docMtDetails : docMtDetailsList) {
			docMtDetailsVO = new DocMtDetailsVO();
			BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);
			docMtDetailsVOList.add(docMtDetailsVO);
		}
		datagrid.setTotal((long)docMtDetailsMybatisDao.queryByCount(new MybatisCriteria()));
		datagrid.setRows(docMtDetailsVOList);
		return datagrid;
	}

	public Json addDocMtDetails(DocMtDetailsForm docMtDetailsForm) throws Exception {
		Json json = new Json();
		DocMtDetails docMtDetails = new DocMtDetails();
		BeanUtils.copyProperties(docMtDetailsForm, docMtDetails);
		docMtDetailsMybatisDao.add(docMtDetails);
		json.setSuccess(true);
		return json;
	}

	public Json editDocMtDetails(DocMtDetailsForm docMtDetailsForm) {
		Json json = new Json();
		DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryById(docMtDetailsForm.getMtno());
		BeanUtils.copyProperties(docMtDetailsForm, docMtDetails);
		docMtDetailsMybatisDao.update(docMtDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocMtDetails(String id) {
		Json json = new Json();
		DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryById(id);
		if(docMtDetails != null){
			docMtDetailsMybatisDao.delete(docMtDetails);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getDocMtDetailsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByAll();
		if(docMtDetailsList != null && docMtDetailsList.size() > 0){
			for(DocMtDetails docMtDetails : docMtDetailsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docMtDetails.getMtno()));
				combobox.setValue(docMtDetails.getLotnum());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}