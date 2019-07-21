package com.wms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.DocAsnCertificateMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
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
	@Autowired
	private DocAsnCertificateMybatisDao docAsnCertificateMybatisDao;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public EasyuiDatagrid<DocAsnCertificateVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnCertificateQuery query) {
//		EasyuiDatagrid<DocAsnCertificateVO> datagrid = new EasyuiDatagrid<DocAsnCertificateVO>();
//		List<DocAsnCertificate> docAsnCertificateList = docAsnCertificateDao.getPagedDatagrid(pager, query);
//		DocAsnCertificateVO docAsnCertificateVO = null;
//		List<DocAsnCertificateVO> docAsnCertificateVOList = new ArrayList<DocAsnCertificateVO>();
//		for (DocAsnCertificate docAsnCertificate : docAsnCertificateList) {
//			docAsnCertificateVO = new DocAsnCertificateVO();
//			BeanUtils.copyProperties(docAsnCertificate, docAsnCertificateVO);
//			docAsnCertificateVOList.add(docAsnCertificateVO);
//		}
//		datagrid.setTotal(docAsnCertificateDao.countAll(query));
//		datagrid.setRows(docAsnCertificateVOList);
//		return datagrid;
		EasyuiDatagrid<DocAsnCertificateVO> datagrid = new EasyuiDatagrid<DocAsnCertificateVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		DocAsnCertificateVO gspProductRegisterSpecsVO = null;
		List<DocAsnCertificateVO> basGtnVOList = new ArrayList<DocAsnCertificateVO>();
		List<DocAsnCertificate> gspProductRegisterSpecsList = docAsnCertificateMybatisDao.queryByList(criteria);


		//System.out.println(query.getSpecsName()+"==============query================================"+query.getProductRegisterNo());
		for (DocAsnCertificate gspProductRegisterSpecs : gspProductRegisterSpecsList) {
			//System.out.println(gspProductRgisterSpecs.getCreateDate()+"==============================================");
			gspProductRegisterSpecsVO = new DocAsnCertificateVO();
			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
			if (gspProductRegisterSpecs.getAddtime() != null) {
				gspProductRegisterSpecsVO.setAddtime(simpleDateFormat.format(gspProductRegisterSpecs.getAddtime()));
			}
			if (gspProductRegisterSpecs.getEdittime() != null) {
				gspProductRegisterSpecsVO.setEdittime(simpleDateFormat.format(gspProductRegisterSpecs.getEdittime()));
			}

			basGtnVOList.add(gspProductRegisterSpecsVO);
		}
		int total = docAsnCertificateMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
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