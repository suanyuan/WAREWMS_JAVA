package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocOrderDetails;
import com.wms.mybatis.dao.DocOrderDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocOrderDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocOrderDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocOrderDetailsForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docOrderDetailsService")
public class DocOrderDetailsService extends BaseService {

	@Autowired
	private DocOrderDetailsMybatisDao docOrderDetailsMybatisDao;

	public EasyuiDatagrid<DocOrderDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocOrderDetailsQuery query) {
        EasyuiDatagrid<DocOrderDetailsVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocOrderDetails> docOrderDetailsList = docOrderDetailsMybatisDao.queryByPageList(mybatisCriteria);
        DocOrderDetailsVO docOrderDetailsVO = null;
        List<DocOrderDetailsVO> docOrderDetailsVOList = new ArrayList<>();
        for (DocOrderDetails docOrderDetails : docOrderDetailsList) {
            docOrderDetailsVO = new DocOrderDetailsVO();
            BeanUtils.copyProperties(docOrderDetails, docOrderDetailsVO);
            docOrderDetailsVOList.add(docOrderDetailsVO);
        }
        datagrid.setTotal((long) docOrderDetailsMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docOrderDetailsVOList);
        return datagrid;
	}

	public Json addDocOrderDetails(DocOrderDetailsForm docOrderDetailsForm) throws Exception {
		Json json = new Json();
		DocOrderDetails docOrderDetails = new DocOrderDetails();
		BeanUtils.copyProperties(docOrderDetailsForm, docOrderDetails);
        docOrderDetailsMybatisDao.add(docOrderDetails);
		json.setSuccess(true);
		return json;
	}

	public Json editDocOrderDetails(DocOrderDetailsForm docOrderDetailsForm) {
		Json json = new Json();
		DocOrderDetails docOrderDetails = docOrderDetailsMybatisDao.queryById(docOrderDetailsForm);
		BeanUtils.copyProperties(docOrderDetailsForm, docOrderDetails);
        docOrderDetailsMybatisDao.update(docOrderDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocOrderDetails(String id) {
		Json json = new Json();
		DocOrderDetails docOrderDetails = docOrderDetailsMybatisDao.queryById(id);
		if(docOrderDetails != null){
            docOrderDetailsMybatisDao.delete(docOrderDetails);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocOrderDetailsCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocOrderDetails> docOrderDetailsList = docOrderDetailsMybatisDao.queryByAll();
//		if(docOrderDetailsList != null && docOrderDetailsList.size() > 0){
//			for(DocOrderDetails docOrderDetails : docOrderDetailsList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docOrderDetails.getId()));
//				combobox.setValue(docOrderDetails.getDocOrderDetailsName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

}