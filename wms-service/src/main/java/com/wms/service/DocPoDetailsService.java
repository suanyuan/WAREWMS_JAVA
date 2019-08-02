package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.DocPoDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocPoDetailsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.DocPoDetailsDao;
import com.wms.entity.DocPoDetails;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocPoDetailsForm;
import com.wms.query.DocPoDetailsQuery;

@Service("docPoDetailsService")
public class DocPoDetailsService extends BaseService {

	//@Autowired
	//private DocPoDetailsDao docPoDetailsDao;
	@Autowired
    private DocPoDetailsMybatisDao docPoDetailsMybatisDao;
//根据主表pono显示datagrid
	public EasyuiDatagrid<DocPoDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocPoDetailsQuery query) {
		EasyuiDatagrid<DocPoDetailsVO> datagrid = new EasyuiDatagrid<DocPoDetailsVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocPoDetails> docPoDetailsList = docPoDetailsMybatisDao.queryByList(mybatisCriteria);
		DocPoDetailsVO docPoDetailsVO = null;
		List<DocPoDetailsVO> docPoDetailsVOList = new ArrayList<DocPoDetailsVO>();
		for (DocPoDetails docPoDetails : docPoDetailsList) {
			docPoDetailsVO = new DocPoDetailsVO();
			BeanUtils.copyProperties(docPoDetails, docPoDetailsVO);
			docPoDetailsVOList.add(docPoDetailsVO);
		}
		datagrid.setTotal((long)docPoDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docPoDetailsVOList);
		return datagrid;
	}
//增加子单明细
	public Json addDocPoDetails(DocPoDetailsForm docPoDetailsForm) throws Exception {
		Json json = new Json();
		DocPoDetailsQuery docPoDetailQuery = new DocPoDetailsQuery();
		docPoDetailQuery.setPono(docPoDetailsForm.getPono());
		/*获取新的明细行号*/
		long orderlineno = docPoDetailsMybatisDao.getAsnlinenoById(docPoDetailQuery);
		DocPoDetails docPoDetails = new DocPoDetails();
		BeanUtils.copyProperties(docPoDetailsForm, docPoDetails);
		docPoDetails.setPolineno((double)(orderlineno + 1));
		docPoDetails.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		docPoDetails.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docPoDetailsMybatisDao.add(docPoDetails);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}
//修改子单明细
	public Json editDocPoDetails(DocPoDetailsForm docPoDetailsForm) {
		Json json = new Json();
		DocPoDetailsQuery docPoDetailsQuery=new DocPoDetailsQuery();
		docPoDetailsQuery.setPono(docPoDetailsForm.getPono());
		docPoDetailsQuery.setPolineno(docPoDetailsForm.getPolineno()+"");
		DocPoDetails docPoDetails = docPoDetailsMybatisDao.queryById(docPoDetailsQuery);
		BeanUtils.copyProperties(docPoDetailsForm, docPoDetails);
		docPoDetails.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docPoDetailsMybatisDao.updateBySelective(docPoDetails);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}
//删除子单明细
	public Json deleteDocPoDetails(DocPoDetailsForm docPoDetailsForm) {
		Json json = new Json();
		DocPoDetails docPoDetails = docPoDetailsMybatisDao.queryById(docPoDetailsForm);
		if(docPoDetails != null){
			docPoDetailsMybatisDao.delete(docPoDetails);
		}
		json.setSuccess(true);
		json.setMsg("删除成功");
		return json;
	}

	public List<EasyuiCombobox> getDocPoDetailsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocPoDetails> docPoDetailsList = docPoDetailsMybatisDao.queryListByAll();
		if(docPoDetailsList != null && docPoDetailsList.size() > 0){
			for(DocPoDetails docPoDetails : docPoDetailsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docPoDetails.getPono()));
				combobox.setValue(docPoDetails.getSku());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}