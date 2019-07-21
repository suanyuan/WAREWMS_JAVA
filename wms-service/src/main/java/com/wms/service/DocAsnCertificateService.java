package com.wms.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.DocAsnCertificateDao;
import com.wms.entity.DocAsnCertificate;
import com.wms.vo.DocAsnCertificateVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocAsnCertificateForm;
import com.wms.query.DocAsnCertificateQuery;

@Service("docAsnCertificateService")
public class DocAsnCertificateService extends BaseService {

	@Autowired
	private DocAsnCertificateDao docAsnCertificateDao;

	public EasyuiDatagrid<DocAsnCertificateVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnCertificateQuery query) {
		EasyuiDatagrid<DocAsnCertificateVO> datagrid = new EasyuiDatagrid<DocAsnCertificateVO>();
		List<DocAsnCertificate> docAsnCertificateList = docAsnCertificateDao.getPagedDatagrid(pager, query);
		DocAsnCertificateVO docAsnCertificateVO = null;
		List<DocAsnCertificateVO> docAsnCertificateVOList = new ArrayList<DocAsnCertificateVO>();
		for (DocAsnCertificate docAsnCertificate : docAsnCertificateList) {
			docAsnCertificateVO = new DocAsnCertificateVO();
			BeanUtils.copyProperties(docAsnCertificate, docAsnCertificateVO);
			docAsnCertificateVOList.add(docAsnCertificateVO);
		}
		datagrid.setTotal(docAsnCertificateDao.countAll(query));
		datagrid.setRows(docAsnCertificateVOList);
		return datagrid;
	}

	public Json addDocAsnCertificate(DocAsnCertificateForm docAsnCertificateForm) throws Exception {
		Json json = new Json();
		DocAsnCertificate docAsnCertificate = new DocAsnCertificate();
		BeanUtils.copyProperties(docAsnCertificateForm, docAsnCertificate);
		docAsnCertificateDao.save(docAsnCertificate);
		json.setSuccess(true);
		return json;
	}

	public Json editDocAsnCertificate(DocAsnCertificateForm docAsnCertificateForm) {
		Json json = new Json();
		DocAsnCertificate docAsnCertificate = docAsnCertificateDao.findById(docAsnCertificateForm.getSku());
		BeanUtils.copyProperties(docAsnCertificateForm, docAsnCertificate);
		docAsnCertificateDao.update(docAsnCertificate);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocAsnCertificate(String id) {
		Json json = new Json();
		DocAsnCertificate docAsnCertificate = docAsnCertificateDao.findById(id);
		if(docAsnCertificate != null){
			docAsnCertificateDao.delete(docAsnCertificate);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getDocAsnCertificateCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocAsnCertificate> docAsnCertificateList = docAsnCertificateDao.findAll();
		if(docAsnCertificateList != null && docAsnCertificateList.size() > 0){
			for(DocAsnCertificate docAsnCertificate : docAsnCertificateList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docAsnCertificate.getCustomerid()));
				combobox.setValue(docAsnCertificate.getCertificateContext());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}