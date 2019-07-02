package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocTransPutway;
import com.wms.mybatis.dao.DocTransPutwayMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocTransPutwayQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocTransPutwayVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocTransPutwayForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docTransPutwayService")
public class DocTransPutwayService extends BaseService {

	@Autowired
	private DocTransPutwayMybatisDao docTransPutwayDao;

	public EasyuiDatagrid<DocTransPutwayVO> getPagedDatagrid(EasyuiDatagridPager pager, DocTransPutwayQuery query) {
        EasyuiDatagrid<DocTransPutwayVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocTransPutway> docTransPutwayList = docTransPutwayDao.queryByPageList(mybatisCriteria);
        DocTransPutwayVO docTransPutwayVO = null;
        List<DocTransPutwayVO> docTransPutwayVOList = new ArrayList<>();
        for (DocTransPutway docTransPutway : docTransPutwayList) {
            docTransPutwayVO = new DocTransPutwayVO();
            BeanUtils.copyProperties(docTransPutway, docTransPutwayVO);
            docTransPutwayVOList.add(docTransPutwayVO);
        }
        datagrid.setTotal((long) docTransPutwayDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docTransPutwayVOList);
        return datagrid;
	}

	public Json addDocTransPutway(DocTransPutwayForm docTransPutwayForm) throws Exception {
		Json json = new Json();
		DocTransPutway docTransPutway = new DocTransPutway();
		BeanUtils.copyProperties(docTransPutwayForm, docTransPutway);
		docTransPutwayDao.add(docTransPutway);
		json.setSuccess(true);
		return json;
	}

	public Json editDocTransPutway(DocTransPutwayForm docTransPutwayForm) {
		Json json = new Json();
		DocTransPutway docTransPutway = docTransPutwayDao.queryById(docTransPutwayForm.getPutwayno());
		BeanUtils.copyProperties(docTransPutwayForm, docTransPutway);
		docTransPutwayDao.update(docTransPutway);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocTransPutway(String id) {
		Json json = new Json();
		DocTransPutway docTransPutway = docTransPutwayDao.queryById(id);
		if(docTransPutway != null){
			docTransPutwayDao.delete(docTransPutway);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocTransPutwayCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocTransPutway> docTransPutwayList = docTransPutwayDao.queryByAll();
//		if(docTransPutwayList != null && docTransPutwayList.size() > 0){
//			for(DocTransPutway docTransPutway : docTransPutwayList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docTransPutway.getPutwayno()));
//				combobox.setValue(docTransPutway.get());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

}