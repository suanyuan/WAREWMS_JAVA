package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocPaHeader;
import com.wms.mybatis.dao.DocPaHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.pda.PdaDocPaEndForm;
import com.wms.query.DocPaHeaderQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocPaHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocPaHeaderForm;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docPaHeaderService")
public class DocPaHeaderService extends BaseService {

	@Autowired
	private DocPaHeaderMybatisDao docPaHeaderDao;

	public EasyuiDatagrid<DocPaHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocPaHeaderQuery query) {
        EasyuiDatagrid<DocPaHeaderVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocPaHeader> docOrderHeaderList = docPaHeaderDao.queryByList(mybatisCriteria);
        DocPaHeaderVO docOrderHeaderVO = null;
        List<DocPaHeaderVO> docOrderHeaderVOList = new ArrayList<DocPaHeaderVO>();
        for (DocPaHeader docOrderHeader : docOrderHeaderList) {
            docOrderHeaderVO = new DocPaHeaderVO();
            BeanUtils.copyProperties(docOrderHeader, docOrderHeaderVO);
            docOrderHeaderVOList.add(docOrderHeaderVO);
        }
        datagrid.setTotal((long) docPaHeaderDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docOrderHeaderVOList);
        return datagrid;
	}

	public Json addDocPaHeader(DocPaHeaderForm docPaHeaderForm) throws Exception {
		Json json = new Json();
		DocPaHeader docPaHeader = new DocPaHeader();
		BeanUtils.copyProperties(docPaHeaderForm, docPaHeader);
		docPaHeaderDao.add(docPaHeader);
		json.setSuccess(true);
		return json;
	}

	public Json editDocPaHeader(DocPaHeaderForm docPaHeaderForm) {
		Json json = new Json();
		DocPaHeader docPaHeader = docPaHeaderDao.queryById(docPaHeaderForm.getPano());
		BeanUtils.copyProperties(docPaHeaderForm, docPaHeader);
		docPaHeaderDao.update(docPaHeader);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocPaHeader(String id) {
		Json json = new Json();
		DocPaHeader docPaHeader = docPaHeaderDao.queryById(id);
		if(docPaHeader != null){
			docPaHeaderDao.delete(docPaHeader);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocPaHeaderCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocPaHeader> docPaHeaderList = docPaHeaderDao.queryByAll();
//		if(docPaHeaderList != null && docPaHeaderList.size() > 0){
//			for(DocPaHeader docPaHeader : docPaHeaderList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docPaHeader.getId()));
//				combobox.setValue(docPaHeader.getDocPaHeaderName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

    /**
     * 查询未完成的上架任务单
     * @param form 分页
     * @return ~
     */
    public List<PdaDocPaHeaderVO> queryUndoneList(PageForm form) {

        List<DocPaHeader> docPaHeaderList = docPaHeaderDao.queryUndoneList(form.getStart(), form.getPageSize());
        List<PdaDocPaHeaderVO> pdaDocPaHeaderVOList = new ArrayList<>();
        PdaDocPaHeaderVO pdaDocPaHeaderVO;
        for (DocPaHeader docPaHeader : docPaHeaderList) {

            pdaDocPaHeaderVO = new PdaDocPaHeaderVO();
            BeanUtils.copyProperties(docPaHeader, pdaDocPaHeaderVO);
            pdaDocPaHeaderVOList.add(pdaDocPaHeaderVO);
        }
        return pdaDocPaHeaderVOList;
    }

    /**
     * 通过pano查询header
     * @param pano ~
     * @return ~
     */
    public PdaDocPaHeaderVO queryByPano(String pano) {

        DocPaHeader docAsnHeader = docPaHeaderDao.queryById(pano);
        PdaDocPaHeaderVO pdaDocPaHeaderVO = new PdaDocPaHeaderVO();
        if (docAsnHeader != null) {

            BeanUtils.copyProperties(docAsnHeader, pdaDocPaHeaderVO);
        }
        return pdaDocPaHeaderVO;
    }

    /**
     * 结束收货单任务
     * @param form 预入通知单号
     * @return ~
     */
    public PdaResult endTask(PdaDocPaEndForm form) {

        form.setEditwho("Gizmo");
        int result = docPaHeaderDao.endTask(form);

        if (result == 0) {
            return new PdaResult(PdaResult.CODE_FAILURE, "操作失败, 订单号不存在");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "操作成功");
    }
}