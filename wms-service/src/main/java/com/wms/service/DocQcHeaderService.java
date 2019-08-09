package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocAsnHeader;
import com.wms.entity.DocPaHeader;
import com.wms.entity.DocQcHeader;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.dao.DocPaHeaderMybatisDao;
import com.wms.mybatis.dao.DocQcHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocQcHeaderQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocQcHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQcHeaderForm;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocQcHeaderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docQcHeaderService")
public class DocQcHeaderService extends BaseService {

	@Autowired
	private DocQcHeaderMybatisDao docQcHeaderMybatisDao;

	@Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;

	@Autowired
	private DocPaHeaderMybatisDao docPaHeaderMybatisDao;

	public EasyuiDatagrid<DocQcHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocQcHeaderQuery query) {
        EasyuiDatagrid<DocQcHeaderVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocQcHeader> docQcHeaderList = docQcHeaderMybatisDao.queryByList(mybatisCriteria);
        DocQcHeaderVO docQcHeaderVO = null;
        List<DocQcHeaderVO> docQcHeaderVOList = new ArrayList<>();
        for (DocQcHeader docPaDetails : docQcHeaderList) {
            docQcHeaderVO = new DocQcHeaderVO();
            BeanUtils.copyProperties(docPaDetails, docQcHeaderVO);
            docQcHeaderVOList.add(docQcHeaderVO);
        }
        datagrid.setTotal((long) docQcHeaderMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docQcHeaderVOList);
        return datagrid;
	}

	public Json addDocQcHeader(DocQcHeaderForm docQcHeaderForm) throws Exception {
		Json json = new Json();
		DocQcHeader docQcHeader = new DocQcHeader();
		BeanUtils.copyProperties(docQcHeaderForm, docQcHeader);
        docQcHeaderMybatisDao.add(docQcHeader);
		json.setSuccess(true);
		return json;
	}

	public Json editDocQcHeader(DocQcHeaderForm docQcHeaderForm) {
		Json json = new Json();
		DocQcHeader docQcHeader = docQcHeaderMybatisDao.queryById(docQcHeaderForm.getQcno());
		BeanUtils.copyProperties(docQcHeaderForm, docQcHeader);
        docQcHeaderMybatisDao.update(docQcHeader);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocQcHeader(String id) {
		Json json = new Json();
		DocQcHeader docQcHeader = docQcHeaderMybatisDao.queryById(id);
		if(docQcHeader != null){
            docQcHeaderMybatisDao.delete(docQcHeader);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocQcHeaderCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocQcHeader> docQcHeaderList = docQcHeaderMybatisDao.queryByAll();
//		if(docQcHeaderList != null && docQcHeaderList.size() > 0){
//			for(DocQcHeader docQcHeader : docQcHeaderList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docQcHeader.getQcno()));
//				combobox.setValue(docQcHeader.getDocQcHeaderName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

    /**
     * 查询未完成的验收任务单
     * @param form 分页
     * @return ~
     */
    public List<PdaDocQcHeaderVO> queryUndoneList(PageForm form) {

        List<DocQcHeader> docQcHeaderList = docQcHeaderMybatisDao.queryUndoneList(form.getStart(), form.getPageSize());
        List<PdaDocQcHeaderVO> pdaDocQcHeaderVOList = new ArrayList<>();
        PdaDocQcHeaderVO pdaDocQcHeaderVO;
        for (DocQcHeader docQcHeader : docQcHeaderList) {

            pdaDocQcHeaderVO = new PdaDocQcHeaderVO();
            BeanUtils.copyProperties(docQcHeader, pdaDocQcHeaderVO);

            //订单类型
            DocPaHeader docPaHeader = docPaHeaderMybatisDao.queryByQcno(docQcHeader.getQcno());
            String[] asnnos = docPaHeader.getAsnno().split(",");
            String asnno = asnnos[0];
            DocAsnHeader queryHeader = new DocAsnHeader();
            queryHeader.setAsnno(asnno);

            DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(queryHeader);
            pdaDocQcHeaderVO.setAsnType(docAsnHeader.getAsntype());
            pdaDocQcHeaderVOList.add(pdaDocQcHeaderVO);
        }
        return pdaDocQcHeaderVOList;
    }

    /**
     * 通过qcno查询header
     * @param qcno ~
     * @return ~
     */
    public PdaDocQcHeaderVO queryByQcno(String qcno) {

        DocQcHeader docQcHeader = docQcHeaderMybatisDao.queryById(qcno);
        PdaDocQcHeaderVO pdaDocQcHeaderVO = new PdaDocQcHeaderVO();
        if (docQcHeader != null) {

            BeanUtils.copyProperties(docQcHeader, pdaDocQcHeaderVO);

            //订单类型
            DocPaHeader docPaHeader = docPaHeaderMybatisDao.queryByQcno(docQcHeader.getQcno());
            String[] asnnos = docPaHeader.getAsnno().split(",");
            String asnno = asnnos[0];
            DocAsnHeader queryHeader = new DocAsnHeader();
            queryHeader.setAsnno(asnno);

            DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(queryHeader);
            pdaDocQcHeaderVO.setAsnType(docAsnHeader.getAsntype());
        }
        return pdaDocQcHeaderVO;
    }

    public PdaDocQcHeaderVO queryDocQcHeaderByPano(String pano) {

        DocQcHeader docQcHeader = queryByPano(pano);
        if (docQcHeader == null) {
            return null;
        }

        PdaDocQcHeaderVO pdaDocQcHeaderVO = new PdaDocQcHeaderVO();
        BeanUtils.copyProperties(docQcHeader, pdaDocQcHeaderVO);

        //订单类型
        DocPaHeader docPaHeader = docPaHeaderMybatisDao.queryByQcno(docQcHeader.getQcno());
        String[] asnnos = docPaHeader.getAsnno().split(",");
        String asnno = asnnos[0];
        DocAsnHeader queryHeader = new DocAsnHeader();
        queryHeader.setAsnno(asnno);

        DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(queryHeader);
        pdaDocQcHeaderVO.setAsnType(docAsnHeader.getAsntype());

        return pdaDocQcHeaderVO;
    }

    /**
     * 通过上架单号查询验收头档 适用于 定向订单的 验收头档查询
     */
    public DocQcHeader queryByPano(String pano) {

        return docQcHeaderMybatisDao.queryByPano(pano);
    }
}