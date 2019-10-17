package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasSkuQuery;
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
    private DocPaDetailsMybatisDao docPaDetailsMybatisDao;

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
     * 打印验收任务
     */
    public List<DocQcHeader> printQcHeader(String qcno, String linestatus, String lotatt10) {

        //验收记录
        List<DocQcHeader> docQcHeaderList = new ArrayList<>();
        BasSkuQuery skuQuery = new BasSkuQuery();
        DocQcHeader docQcHeader = new DocQcHeader();
        List<EasyuiCombobox> easyuiComboboxListUom = basCodesService.getBy(Constant.CODE_CATALOG_UOM);//查询单位
        Double paQtySum = 0.00;//到货数量

        docQcHeader.setDetls(new ArrayList<DocQcDetails>());
        List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.queryPaDetialsByQcno(qcno);
        if (docPaDetailsList.size() > 0) {

            DocQcDetails docQcDetails;
            //SELECT doc_qc_details.*,doc_qc_header.pano,inv_lot_att.*,bas_sku.descr_c,bas_sku.reservedfield06,bas_package.`qty1`,bas_customer.`descr_c`  descrcM,bas_customer.descr_c lotatt08Name
            for (DocPaDetails docPaDetails : docPaDetailsList) {

                docQcDetails = new DocQcDetails();
                skuQuery.setCustomerid(docPaDetails.getCustomerid());
                skuQuery.setSku(docPaDetails.getSku());
                BasSku basSku = basSkuMybatisDao.queryById(skuQuery);//得到sku的packid
                BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());

                docQcDetails.setDescrc(basSku.getDescrC());
                for (EasyuiCombobox easyuiComboboxUom : easyuiComboboxListUom) {
                    if (basSku.getDefaultreceivinguom().equals(easyuiComboboxUom.getId())) {//单位类型
                        docQcDetails.setQcUnit(easyuiComboboxUom.getValue());
                    }
                }

                docQcDetails.setLotatt14(docPaDetails.getAsnno());
                if (null != docPaDetails.getInvLotAtt()) {

                    BeanUtils.copyProperties(docPaDetails.getInvLotAtt(), docQcDetails);
                }

                docQcDetails.setCustomerid(docPaDetails.getCustomerid());
                docQcDetails.setSku(docPaDetails.getSku());
                docQcDetails.setLotnum(docPaDetails.getLotnum());
                docQcDetails.setUserdefine1(docPaDetails.getUserdefine1());
                docQcDetails.setUserdefine2(docPaDetails.getUserdefine2());
                docQcDetails.setUserdefine3(docPaDetails.getUserdefine3());
                docQcDetails.setUserdefine4(docPaDetails.getUserdefine4());
                docQcDetails.setUserdefine5(null);
                docQcDetails.setPackid(docPaDetails.getPackid());
                docQcDetails.setQcqtyCompleted(null);
                docQcDetails.setQcqtyExpected(null);
                docQcDetails.setNotes("");
                docQcDetails.setPaqtyExpected(basPackage.getQty1().doubleValue() * (docPaDetails.getPutwayqtyExpected()));//到货件数（这里是拆开的后面需要合计下）
                docQcDetails.setQcqtyCompletedSum(null);
                docQcDetails.setQcqtyExpectedSum(null);
                docQcDetails.setEdittime(null);
                docQcDetails.setEditwho(null);

                //合计数量
                paQtySum += docQcDetails.getPaqtyExpected();
                docQcDetails.setPaqtyExpectedSum(paQtySum);

                docQcHeader.getDetls().add(docQcDetails);
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