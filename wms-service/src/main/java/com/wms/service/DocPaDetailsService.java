package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.DocPaDetails;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.DocPaDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocPaDetailsQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocPaDetailQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocPaDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocPaDetailsForm;
import com.wms.vo.pda.PdaDocPaDetailVO;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docPaDetailsService")
public class DocPaDetailsService extends BaseService {

	@Autowired
	private DocPaDetailsMybatisDao docPaDetailsMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	public EasyuiDatagrid<DocPaDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocPaDetailsQuery query) {
        EasyuiDatagrid<DocPaDetailsVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocPaDetails> docOrderHeaderList = docPaDetailsMybatisDao.queryByPageList(mybatisCriteria);
        DocPaDetailsVO docPaDetailsVO = null;
        List<DocPaDetailsVO> docOrderHeaderVOList = new ArrayList<>();
        for (DocPaDetails docPaDetails : docOrderHeaderList) {
            docPaDetailsVO = new DocPaDetailsVO();
            BeanUtils.copyProperties(docPaDetails, docPaDetailsVO);
            docOrderHeaderVOList.add(docPaDetailsVO);
        }
        datagrid.setTotal((long) docPaDetailsMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docOrderHeaderVOList);
        return datagrid;
	}

	public Json addDocPaDetails(DocPaDetailsForm docPaDetailsForm) throws Exception {
		Json json = new Json();
		DocPaDetails docPaDetails = new DocPaDetails();
		BeanUtils.copyProperties(docPaDetailsForm, docPaDetails);
		docPaDetailsMybatisDao.add(docPaDetails);
		json.setSuccess(true);
		return json;
	}

	public Json editDocPaDetails(DocPaDetailsForm docPaDetailsForm) {
		Json json = new Json();
		DocPaDetails docPaDetails = docPaDetailsMybatisDao.queryById(docPaDetailsForm.getPano());
		BeanUtils.copyProperties(docPaDetailsForm, docPaDetails);
		docPaDetailsMybatisDao.update(docPaDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocPaDetails(String id) {
		Json json = new Json();
		DocPaDetails docPaDetails = docPaDetailsMybatisDao.queryById(id);
		if(docPaDetails != null){
			docPaDetailsMybatisDao.delete(docPaDetails);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocPaDetailsCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.findAll();
//		if(docPaDetailsList != null && docPaDetailsList.size() > 0){
//			for(DocPaDetails docPaDetails : docPaDetailsList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docPaDetails.getId()));
//				combobox.setValue(docPaDetails.getDocPaDetailsName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}


    /**
     * TODO 获取上架详情
     * @param query ~
     * @return 明细
     */
    public PdaDocPaDetailVO queryDocPaDetail(PdaDocPaDetailQuery query) {

        PdaDocPaDetailVO docPaDetailVO = new PdaDocPaDetailVO();

        //TODO 别忘了开开
//	    if (query.getLotatt02() != null) {
//
//	        StringBuilder stringBuilder = new StringBuilder(query.getLotatt02());
//	        stringBuilder.insert(4, "-");
//	        stringBuilder.insert(2, "-");
//	        stringBuilder.insert(0, "20");
//	        query.setLotatt02(stringBuilder.toString());
//        }

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.queryByPageList(mybatisCriteria);
        if (docPaDetailsList.size() == 1) {

            DocPaDetails docPaDetails = docPaDetailsList.get(0);
            BeanUtils.copyProperties(docPaDetails, docPaDetailVO);

            //获取BasSku
            PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
            BeanUtils.copyProperties(query, basSkuQuery);
            BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

            docPaDetailVO.setBasSku(basSku);
        }
        return docPaDetailVO;
    }

    /**
     * 获取上架任务明细
     * @param pano 上架任务单
     * @return ~
     */
    public List<PdaDocPaDetailVO> queryDocPaDetailList(String pano) {


        return null;
    }
}