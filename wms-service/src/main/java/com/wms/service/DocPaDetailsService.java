package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.DocPaDetails;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.DocPaDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.pda.PdaDocPaDetailForm;
import com.wms.query.DocPaDetailsQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocPaDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocPaDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocPaDetailsForm;
import com.wms.vo.pda.PdaDocPaDetailVO;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        //TODO 别忘了开开 扫出来的效期 是201029 这种格式 要转成 2020-10-29
//	    if (query.getLotatt02() != null) {
//
//	        StringBuilder stringBuilder = new StringBuilder(query.getLotatt02());
//	        stringBuilder.insert(4, "-");
//	        stringBuilder.insert(2, "-");
//	        stringBuilder.insert(0, "20");
//	        query.setLotatt02(stringBuilder.toString());
//        }

        //获取BasSku
        PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
        BeanUtils.copyProperties(query, basSkuQuery);
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

        query.setSku(basSku.getSku());
        DocPaDetails docPaDetails = docPaDetailsMybatisDao.queryDocPaDetail(query);
        if (docPaDetails != null) {

            BeanUtils.copyProperties(docPaDetails, docPaDetailVO);
            docPaDetailVO.setBasSku(basSku);
        }

        return docPaDetailVO;
    }

    /**
     * 获取上架任务明细
     * @param pano 上架任务单
     * @return ~
     */
    public List<PdaDocPaDetailVO> queryDocPaList(String pano) {

        List<DocPaDetails> detailsList = docPaDetailsMybatisDao.queryDocPaList(pano);
        PdaDocPaDetailVO detailVO;
        List<PdaDocPaDetailVO> detailVOList = new ArrayList<>();
        for (DocPaDetails detail:
             detailsList) {
            detailVO = new PdaDocPaDetailVO();
            BeanUtils.copyProperties(detail, detailVO);
            detailVOList.add(detailVO);
        }
        return detailVOList;
    }

    /**
     * 上架提交
     * @param form pda上传表单数据
     * @return 结论
     */
    public PdaResult putawayGoods(PdaDocPaDetailForm form) {

        //pano + customerid + userdefine 2~4 = DocPaDetails,少了个SKU
        // 所以用上面queryDocPaDetail的方法现获取detail
        PdaDocPaDetailQuery detailQuery = new PdaDocPaDetailQuery();
        detailQuery.setWarehouseid(form.getWarehouseid());
        detailQuery.setPano(form.getPano());
        detailQuery.setCustomerid(form.getCustomerid());
        detailQuery.setGTIN(form.getGTIN());
        detailQuery.setLotatt02(form.getUserdefine2());
        detailQuery.setLotatt04(form.getUserdefine3());
        detailQuery.setLotatt05(form.getUserdefine4());

        PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
        BeanUtils.copyProperties(detailQuery, basSkuQuery);
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

        detailQuery.setSku(basSku.getSku());
        DocPaDetails docPaDetails = docPaDetailsMybatisDao.queryDocPaDetail(detailQuery);

        if (docPaDetails != null) {

            BeanUtils.copyProperties(docPaDetails, form);
            form.setUserid("Gizmo");
            form.setLanguage("CN");
            try {
                docPaDetailsMybatisDao.putawayGoods(form);
            }catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
            }
            if (form.getReturncode().equals(Constant.PROCEDURE_OK)) {

                return new PdaResult(PdaResult.CODE_SUCCESS, "上架成功");
            } else {

                return new PdaResult(PdaResult.CODE_FAILURE, form.getReturncode());
            }
        }else {

            return new PdaResult(PdaResult.CODE_FAILURE, "上架明细数据缺失");
        }
    }
}