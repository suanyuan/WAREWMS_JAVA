package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocQcDetailsQuery;
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

    @Autowired
    private BasCodesService basCodesService;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private DocQcDetailsMybatisDao docQcDetailsMybatisDao;

    @Autowired
    private BasPackageMybatisDao basPackageMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;

    /**
     * 分页 显示主单
     * @param pager
     * @param query
     * @return
     */
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
     *
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
     *
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

    /**
     * 打印验收记录
     *
     * @return
     */
    public List<DocQcHeader> printQcHeader(String qcno, String linestatus, String lotatt10) {

        //验收记录
        List<DocQcHeader> docQcHeaderList = new ArrayList<DocQcHeader>();
        MybatisCriteria mybatisCriteria1 = new MybatisCriteria();
        BasSkuQuery skuQuery = new BasSkuQuery();
        List<BasSku> basSkuList;
        DocQcHeader docQcHeader = new DocQcHeader();
        List<EasyuiCombobox> easyuiComboboxListUom = basCodesService.getBy(Constant.CODE_CATALOG_UOM);//查询单位
        List<EasyuiCombobox> easyuiComboboxListZl = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);//查询质量状态
        Double paQtySum = 0.00;//到货数量
        Double qcQtySum = 0.00;
        Double qcQtyComSum = 0.00;
        //ASN编号
        String asnNo;

        docQcHeader.setDetls(new ArrayList<DocQcDetails>());
        DocQcDetailsQuery docQcDetailsQuery = new DocQcDetailsQuery();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        docQcDetailsQuery.setQcno(qcno);
        docQcDetailsQuery.setLinestatus(linestatus);
        docQcDetailsQuery.setLotatt10(lotatt10);
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docQcDetailsQuery));
        List<DocQcDetails> docQcDetailsList = docQcDetailsMybatisDao.queryByListPano(mybatisCriteria);//获取需要打印的数据
        if (docQcDetailsList.size() > 0) {
            for (DocQcDetails docQcDetails1 : docQcDetailsList) { //规格  数量 不合格数量
                //到货数量
                skuQuery.setCustomerid(docQcDetails1.getCustomerid());
                skuQuery.setSku(docQcDetails1.getSku());
                BasSku basSku = basSkuMybatisDao.queryById(skuQuery);//得到sku的packid
                BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
                //规格
                BasSkuQuery basSkuQuery = new BasSkuQuery();
                basSkuQuery.setSku(docQcDetails1.getSku());
                basSkuQuery.setCustomerid(docQcDetails1.getCustomerid());
                mybatisCriteria1.setCondition(BeanConvertUtil.bean2Map(basSkuQuery));
                basSkuList = basSkuMybatisDao.queryByList(mybatisCriteria1);
                for (BasSku basSku1 : basSkuList) {
                    docQcDetails1.setDescrc(basSku1.getDescrC());
                    //单位
                    for (EasyuiCombobox easyuiComboboxUom : easyuiComboboxListUom) {
                        if (basSku1.getDefaultreceivinguom().equals(easyuiComboboxUom.getId())) {//单位类型
                            docQcDetails1.setQcUnit(easyuiComboboxUom.getValue());
                        }
                    }
                }


                docQcDetails1.setQcqtyCompleted(null);
                docQcDetails1.setQcqtyExpected(null);


                docQcDetails1.setNotes("");
                docQcDetails1.setUserdefine5(null);
                docQcDetails1.setPaqtyExpected(basPackage.getQty1().doubleValue() * (docQcDetails1.getPaqtyExpected()));//到货件数（这里是拆开的后面需要合计下）
                //合计数量
                paQtySum += docQcDetails1.getPaqtyExpected();
                docQcDetails1.setPaqtyExpectedSum(paQtySum);
                docQcDetails1.setQcqtyCompletedSum(null);
                docQcDetails1.setQcqtyExpectedSum(null);
                docQcDetails1.setEdittime(null);
                docQcDetails1.setEditwho(null);

                docQcHeader.getDetls().add(docQcDetails1);
            }
            List<String> stringList = new ArrayList<>();
            //如果lotatt14存在不同的就全部为空
            for (DocQcDetails docqcDetailsLotatt14 : docQcHeader.getDetls()) {//这里判断不行 先去list中去重如果size大于1就正面不是同一个ASN编号
                stringList.add(docqcDetailsLotatt14.getLotatt14());

            }
            //同一个ASN编号的 赋值到头档
            if (removeDuplicate(stringList).size() == 1) {
                for (DocQcDetails docqcDetails : docQcHeader.getDetls()) {//这里判断不行 先去list中去重如果size大于1就正面不是同一个ASN编号
                    //这里还有查询。
                    DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docqcDetails.getLotatt14());//根据ASN编号查询预期入库头档
                    docQcHeader.setCustomerid(docAsnHeader.getCustomerid());
                    //供应商
                    BasCustomer basCustomer = basCustomerMybatisDao.queryByIdType(docqcDetails.getLotatt08(), Constant.CODE_CUS_TYP_VE);
                    if (basCustomer == null) {
                        docQcHeader.setDescrC(" ");

                    } else {
                        docQcHeader.setDescrC(basCustomer.getDescrC());
                    }
                    //入库日期
                    docQcHeader.setLotatt03(docqcDetails.getLotatt03());
                    //入库单号
                    docQcHeader.setLotatt14(docAsnHeader.getAsnno());
                    //冷链随货温度
                    docQcHeader.setUserdefine1Temp(docAsnHeader.getUserdefine1());
                }
            } else {
                //货主
                docQcHeader.setCustomerid("");
                //供应商
                docQcHeader.setDescrC("");
                //入库日期
                docQcHeader.setLotatt03("");
                //入库单号
                docQcHeader.setLotatt14("");
                //冷链随货温度
                docQcHeader.setUserdefine1Temp("");
            }
            docQcHeaderList.add(docQcHeader);
        }

        return docQcHeaderList;
    }

    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }


}