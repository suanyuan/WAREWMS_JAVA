package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.DocMtHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.vo.form.pda.PageForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.DocMtHeader;
import com.wms.vo.DocMtHeaderVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocMtHeaderForm;
import com.wms.query.DocMtHeaderQuery;

@Service("docMtHeaderService")
public class DocMtHeaderService extends BaseService {

	@Autowired
	private DocMtHeaderMybatisDao docMtHeaderMybatisDao;

	public EasyuiDatagrid<DocMtHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocMtHeaderQuery query) {
		EasyuiDatagrid<DocMtHeaderVO> datagrid = new EasyuiDatagrid<DocMtHeaderVO>();
		MybatisCriteria mybatisCriteria=new MybatisCriteria();
		mybatisCriteria.setPageSize(pager.getPage());
		mybatisCriteria.setOrderByClause(pager.getOrderBy());
		List<DocMtHeader> docMtHeaderList = docMtHeaderMybatisDao.queryByList(mybatisCriteria);
		DocMtHeaderVO docMtHeaderVO = null;
		List<DocMtHeaderVO> docMtHeaderVOList = new ArrayList<DocMtHeaderVO>();
		for (DocMtHeader docMtHeader : docMtHeaderList) {
			docMtHeaderVO = new DocMtHeaderVO();
			BeanUtils.copyProperties(docMtHeader, docMtHeaderVO);
			docMtHeaderVOList.add(docMtHeaderVO);
		}
		datagrid.setTotal(docMtHeaderMybatisDao.queryTotalCount());
		datagrid.setRows(docMtHeaderVOList);
		return datagrid;
	}

	public Json addDocMtHeader(DocMtHeaderForm docMtHeaderForm) throws Exception {
		Json json = new Json();
		DocMtHeader docMtHeader = new DocMtHeader();
		BeanUtils.copyProperties(docMtHeaderForm, docMtHeader);
		docMtHeaderMybatisDao.add(docMtHeader);
		json.setSuccess(true);
		return json;
	}

	public Json editDocMtHeader(DocMtHeaderForm docMtHeaderForm) {
		Json json = new Json();
		DocMtHeader docMtHeader = docMtHeaderMybatisDao.queryById(docMtHeaderForm.getMtno());
		BeanUtils.copyProperties(docMtHeaderForm, docMtHeader);
		docMtHeaderMybatisDao.updateBySelective(docMtHeader);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocMtHeader(String id) {
		Json json = new Json();
		DocMtHeader docMtHeader = docMtHeaderMybatisDao.queryById(id);
		if(docMtHeader != null){
			docMtHeaderMybatisDao.delete(docMtHeader);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getDocMtHeaderCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocMtHeader> docMtHeaderList = docMtHeaderMybatisDao.queryListByAll();
		if(docMtHeaderList != null && docMtHeaderList.size() > 0){
			for(DocMtHeader docMtHeader : docMtHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(docMtHeader.getMtno());
				combobox.setValue(docMtHeader.getUserdefine1());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

    /**
     * 查询未完成的预入库通知单
     * @param form 分页
     * @return ~
     */
    public List<DocMtHeaderVO> queryUndoneList(PageForm form) {

        List<DocMtHeader> docMtHeaderList = docMtHeaderMybatisDao.queryUndoneList(form.getStart(), form.getPageSize());
        List<DocMtHeaderVO> docMtHeaderVOList = new ArrayList<>();
        DocMtHeaderVO docMtHeaderVO;
        for (DocMtHeader docMtHeader : docMtHeaderList) {

            docMtHeaderVO = new DocMtHeaderVO();
            BeanUtils.copyProperties(docMtHeader, docMtHeaderVO);
            docMtHeaderVOList.add(docMtHeaderVO);
        }
        return docMtHeaderVOList;
    }

    /**
     * 通过mtno查询header
     * @param mtno ~
     * @return ~
     */
    public DocMtHeaderVO queryByMtno(String mtno) {

        DocMtHeader docMtHeader = docMtHeaderMybatisDao.queryById(mtno);
        DocMtHeaderVO docMtHeaderVO = new DocMtHeaderVO();
        if (docMtHeader != null) {

            BeanUtils.copyProperties(docMtHeader, docMtHeaderVO);
        }
        return docMtHeaderVO;
    }
}