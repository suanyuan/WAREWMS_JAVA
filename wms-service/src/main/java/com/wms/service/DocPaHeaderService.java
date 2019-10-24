package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocPaEndForm;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocPaDetailsQuery;
import com.wms.query.DocPaHeaderQuery;
import com.wms.result.PdaResult;
import com.wms.service.importdata.ImportPaDataService;
import com.wms.utils.*;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.*;
import com.wms.vo.form.DocPaHeaderForm;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service("docPaHeaderService")
public class DocPaHeaderService extends BaseService {

	@Autowired
	private DocPaHeaderMybatisDao docPaHeaderDao;
	@Autowired
	private DocPaDetailsService docPaDetailsService;
	@Autowired
	private BasSkuService basSkuService;
	@Autowired
	private DocAsnDetailService docAsnDetailService;
	@Autowired
	private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
	@Autowired
    private DocQcHeaderMybatisDao docQcHeaderMybatisDao;
	@Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;
    @Autowired
    private DocPaDetailsMybatisDao docPaDetailsMybatisDao;
    @Autowired
    private ImportPaDataService importPaDataService;
    @Autowired
    private BasCustomerService basCustomerService;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    private InvLotAttService invLotAttService;
    @Autowired
    private BasPackageMybatisDao basPackageMybatisDao;
    @Autowired
    private BasCodesMybatisDao basCodesMybatisDao;

	public EasyuiDatagrid<DocPaHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocPaHeaderQuery query) {
        EasyuiDatagrid<DocPaHeaderVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        mybatisCriteria.setOrderByClause("addtime desc");
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
		if(docPaHeader != null && docPaHeader.getPastatus().equals("00")) {
            //更新打印标记
            String asn = docPaHeader.getAsnno();
            String[] arrAsn = asn.split(",");
            for (String s : arrAsn) {
                DocAsnHeader docAsnHeader = new DocAsnHeader();
                docAsnHeader.setAsnPrintFlag("N");
                docAsnHeader.setAsnno(s);
                docAsnHeaderMybatisDao.updateBySelective(docAsnHeader);
            }
            docPaHeaderDao.delete(docPaHeader);

            //删除上架明细
            docPaDetailsMybatisDao.clearDetailsByPano(id);
            json.setMsg("上架任务删除成功");
            json.setSuccess(true);
            return json;
        }
        json.setMsg("当前状态的上架单不可删除");
		json.setSuccess(false);
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
     * 通过预入通知单号查询上架头档 适用于 定向订单的 上架头档查询
     * @param asnno
     * @return
     */
    public DocPaHeader queryByAsnno(String asnno) {

        return docPaHeaderDao.queryByAsnno(asnno);
    }

    /**
     * 结束上架单任务
     * @param form 预入通知单号
     * @return ~
     */
    public PdaResult endTask(PdaDocPaEndForm form) {

        form.setEditwho(form.getEditwho());
        int result = docPaHeaderDao.endTask(form);
        if (result == 0) {
            return new PdaResult(PdaResult.CODE_FAILURE, "操作失败, 订单号不存在");
        }

        DocQcHeader docQcHeader = docQcHeaderMybatisDao.queryByPano(form.getPano());
        if (docQcHeader != null) {

            PdaDocQcDetailForm pdaDocQcDetailForm = new PdaDocQcDetailForm();
            pdaDocQcDetailForm.setQcno(docQcHeader.getQcno());
            docQcHeaderMybatisDao.updateTaskStatus(pdaDocQcDetailForm);
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "操作成功");
    }

    /**
     * 结束上架可行性校验
     * @param form 上架任务单号
     * @return ~
     */
    public List<DocPaDetailsVO> endAvailableQuery(PdaDocPaEndForm form) {

        List<DocPaDetails> detailsList = docPaDetailsMybatisDao.queryDocPaList(form.getPano());
        List<DocPaDetailsVO> docPaDetailsVOList = new ArrayList<>();
        DocPaDetailsVO docPaDetailsVO;
        for (DocPaDetails docPaDetails : detailsList) {

            if (docPaDetails.getLinestatus().equals("00")) {

                docPaDetailsVO = new DocPaDetailsVO();
                BeanUtils.copyProperties(docPaDetails, docPaDetailsVO);
                docPaDetailsVOList.add(docPaDetailsVO);
            }
        }

        return docPaDetailsVOList;
    }

    /*
        数据打印
     */
    public DocPaHeader printPaTaskPdf(String pano) {
        // 上架表头  docPaHeaderList 1 doc_pa_header
       /* List<DocPaHeader> docPaHeaderList = new ArrayList<DocPaHeader>();*/
        DocPaHeader docPaHeader = docPaHeaderDao.queryById(pano);
        DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docPaHeader.getAsnno());

        //制单日期 docpaheader.addtime
        if(docAsnHeader !=null){
            //客户单号1 docasnheader.asnreference1
            docPaHeader.setAsnreference1(docAsnHeader.getAsnreference1());
            //客户单号2 docasnheader.asnreference2
            docPaHeader.setAsnreference2(docAsnHeader.getAsnreference2());
        }
        //仓库名 docpaheader.warehousid
        //货主 docpaheader.customerid JSGR  BasCustomerService.query   BasCustomer.DESCR_C
        BasCustomer basCustomerList = basCustomerMybatisDao.queryByCustomerId(docPaHeader.getCustomerid());
        if(basCustomerList != null){
            docPaHeader.setDescrC(basCustomerList.getDescrC());
        }
        //供应商 docasnheader.supplierid BasCustomerService.query   BasCustomer.DESCR_C
        if (null != docAsnHeader) {
            BasCustomer basCustomerList1 = basCustomerMybatisDao.queryByCustomerId(docAsnHeader.getSupplierid());
            if (basCustomerList1 != null) {
                docPaHeader.setDescrC1(basCustomerList1.getDescrC());
            }
        }
        //冷链 todo 待定


        List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.queryDocPaList(pano);
        //总页数
        int pageSize = (int) Math.ceil((double) docPaDetailsList.size() / 9);
        docPaHeader.setPageSize(pageSize);
        int pageNo=1;//页码
        docPaHeader.setPageNo(pageNo);
        if(docPaDetailsList.size()>9){
            pageNo +=1;
        }
        docPaHeader.setPageNo(pageNo);


        double a=0;
        double b=0;
        Integer c=0;
        for(int i=0;i<docPaDetailsList.size();i++){
            DocPaDetails docPaDetails = docPaDetailsList.get(i);
            //序号 1~。。。
            //产品代码docpadetails.sku
            //产品名称 1,details.customerid + details.sku => BasSku     BasSku.reservedfield01
            Map<String,Object> param = new HashMap<>();
            param.put("customerid",docPaDetails.getCustomerid());
            param.put("sku",docPaDetails.getSku());
            BasSku basSku =  basSkuMybatisDao.queryById(param);
            if (basSku != null) {
                docPaDetails.setReservedfield01(basSku.getReservedfield01());
                //规格/型号 bassku.descr_c
                docPaDetails.setDescrs(basSku.getDescrC());
            }
            String notes = basSku.getReservedfield07();
            if(notes.equals("LD")){
                docPaHeader.setNotes("冷冻");
            }else if (notes.equals("FLL")){
                docPaHeader.setNotes("非冷链");
            }else if (notes.equals("LC")){
                docPaHeader.setNotes("冷藏");
            }
            //生产批号 1,details.lotnum =》 InvLotAttmybatisdao.querybyid =>Invlotatt   invlotatt.lotatt04
            InvLotAtt invLotAtt= invLotAttMybatisDao.queryById(docPaDetails.getLotnum());
            if(invLotAtt != null){
                docPaDetails.setLotatt04(invLotAtt.getLotatt04());
                //序列号 invlotatt.lotatt05
                docPaDetails.setLotatt05(invLotAtt.getLotatt05());
                //生产日期 invlotatt.lotatt01
                docPaDetails.setLotatt01(invLotAtt.getLotatt01());
                //有效期/失效日期 invlotatt.lotatt02
                docPaDetails.setLotatt02(invLotAtt.getLotatt02());
            }

            //待上数量 bassku.packid => BasPackage.querybyid => docpadetails.putwayqtyExpected * baspackage.qty1
            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            if(basPackage != null){
                docPaDetails.setPutwayqtynum(docPaDetails.getPutwayqtyExpected()*basPackage.getQty1().doubleValue());
                a=a+docPaDetails.getPutwayqtynum();//待上数量合计
                b=b+docPaDetails.getPutwayqtyExpected();//待上件数合计
                docPaDetails.setPutwayqtynums(a);
                docPaDetails.setPutwayqtyExpecteds(b);
            }
            //单位 bassku.defaultreceivinguom => BasCode query (条件 codeid=UOM，code = defaultreceivinguom) => bascode.codename_c
            BasCodes basCodes = basCodesMybatisDao.query(basSku.getDefaultreceivinguom());
            if(basCodes != null){
                docPaDetails.setCodename(basCodes.getCodenameC());
            }

            c+=1;
            docPaDetails.setIndex(c);
        }

        docPaHeader.setDetls(docPaDetailsList);
        //待上件数 docpadetails.putwayqtyExpected
        //已上件数 null
        //库位 null
        //上架人 null
        // 上架明细  padetails'多个 doc_pa_details
        return docPaHeader;
    }

    /**
     * 打印
     * @param response
     * @param orderCodeList
     */
    public void exportBatchPdf(HttpServletResponse response, String orderCodeList) {
        StringBuilder sb = new StringBuilder();
        try (OutputStream os = response.getOutputStream()){
            sb.append("inline; filename=")
                    .append(URLEncoder.encode("PDF","UTF-8"))
                    .append(".pdf");
            response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
            response.setContentType(ContentTypeEnum.pdf.getContentType());

            Document document = null;
            AcroFields form = null;
            PdfStamper stamper = null;
            PdfImportedPage page = null;
            ByteArrayOutputStream baos = null;

            if (StringUtils.isNotEmpty(orderCodeList)) {

                document = new Document(PDFUtil.getTemplate("wms_receipt_jhck.pdf").getPageSize(1));
                PdfCopy pdfCopy = new PdfCopy(document, os);
                document.open();
                 //分割orderCodeList中的主单号pano
                String[] orderCodeArray = orderCodeList.split(",");

                for (String orderCode : orderCodeArray){

                    //根据主单pano查询对象DocPaHeader
                    DocPaHeader docPaHeader = docPaHeaderDao.queryById(orderCode);
                    if(docPaHeader!=null){

                        int totalNum = 0;
                        int row = 15;//每页条数
                        int pageSize = 0;

                        DocPaDetailsQuery query = new DocPaDetailsQuery();
                        query.setAsnno(orderCode);
                       //根据主单pano获取子单所有的产品 orderCode==pano
                        List<DocPaDetails> detailsList =  docPaDetailsService.queryDocPdaDetails(orderCode);

//                        DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docPaHeader.getAsnno());

                        totalNum = detailsList.size();//总条数
                        pageSize = (int)Math.ceil( (double) totalNum / row);//总页数
                        int index = 0;
                        for(int i=0;i<pageSize;i++){//单头内容
                            baos = new ByteArrayOutputStream();
                            stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt_jhck.pdf"), baos);
                            form = stamper.getAcroFields();
                            form.setField("orderNo1",docPaHeader.getPareference1());
                            form.setField("orderNo2",docPaHeader.getPareference2());
                            form.setField("putwayCode",docPaHeader.getPano());
                            form.setField("receviedDdate", DateUtil.format(docPaHeader.getAddtime(),"yyyy-MM-dd"));
                            form.setField("warehouseid", docPaHeader.getWarehouseid());
                            form.setField("custName", docPaHeader.getCustomerid());
                            form.setField("supplier", "");
                            form.setField("page", "第"+(i+1)+"页,共"+pageSize+"页");
                            //form.setField("barCode1", docPaHeader.getAsnno());
                            form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(docPaHeader.getPano(), 800)));
                            //form.replacePushbuttonField("orderCodeImg1", PDFUtil.genPdfButton(form, "orderCodeImg1", BarcodeGeneratorUtil.genBarcode(docPaHeader.getAsnno(), 800)));
                            String note ="";
                            double totalEachPage = 0;
                            double totalPage = 0;
                            for(int j=0;j<row;j++){//主单产品明细
                                if(totalNum > (row*i+j)){
                                    index++;

                                    DocPaDetails docPaDetails = detailsList.get(row * i + j);
                                    BasSku basSku = basSkuService.getSkuInfo(docPaHeader.getCustomerid(),detailsList.get(row * i + j).getSku());

                                    //获取冷链标志
                                    if(StringUtils.isEmpty(note)){
                                        if(!StringUtils.isEmpty(basSku.getReservedfield07())){
                                            switch (basSku.getReservedfield07()){
                                                case "LD" : note = "冷冻";break;
                                                case "FLL" : note = "非冷链";break;
                                                case "LC" : note = "冷藏";break;
                                            }
                                        }
                                    }

                                    //根据某一个订单明细查询关联的Doc_Asn_Details
                                    DocAsnDetailQuery queryDetail = new DocAsnDetailQuery();
                                    queryDetail.setAsnno(docPaDetails.getAsnno());
                                    queryDetail.setAsnlineno(docPaDetails.getAsnlineno());
                                    DocAsnDetailVO detailVO = docAsnDetailService.queryDocAsnDetail(queryDetail);
                                    form.setField("line."+j, index+"");
                                    form.setField("sku."+j, docPaDetails.getSku());
                                    form.setField("skuN."+j, basSku.getReservedfield01());
                                    form.setField("regNo."+j, basSku.getReservedfield03());
                                    form.setField("desc."+j, basSku.getDescrE());
                                    form.setField("batchNo."+j, docPaDetails.getUserdefine3());
                                    form.setField("seriNo."+j, docPaDetails.getUserdefine4());
                                    form.setField("ill."+j, detailVO.getLotatt07());
                                    form.setField("lot01."+j, detailVO.getLotatt01());
                                    form.setField("lot02."+j, detailVO.getLotatt02());
                                    form.setField("qtyE."+j, docPaDetails.getAsnqtyExpected().toString());
                                    form.setField("uom."+j, basSku.getDefaultreceivinguom());
                                    form.setField("qty."+j, doubleTrans(docPaDetails.getPutwayqtyExpected()));
                                    form.setField("qtyA."+j, doubleTrans(docPaDetails.getPutwayqtyCompleted()));

                                    totalEachPage += docPaDetails.getAsnqtyExpected().doubleValue();
                                    totalPage += docPaDetails.getPutwayqtyExpected().doubleValue();
                                }
                            }
                            form.setField("notes",note);
                            form.setField("sumqtyPage","合计(数量:"+doubleTrans(totalEachPage)+"  件数:"+doubleTrans(totalPage)+")");
                            form.setField("sumqty","");


                            stamper.setFormFlattening(true);
                            stamper.close();
                            page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
                            pdfCopy.addPage(page);
                        }

                        /*if(detailsList!=null && detailsList.size()>0){
                            for(DocPaDetails details : detailsList){
                                baos = new ByteArrayOutputStream();
                                stamper = new PdfStamper(PDFUtil.getTemplate("wms_crossdocking_package.pdf"), baos);
                                form = stamper.getAcroFields();
                                form.setField("putwmsCode",docPaHeader.getAsnno());
                                form.setField("receviedDdate", "");
                                form.setField("warehouseid", docPaHeader.getWarehouseid());
                                form.setField("custName", docPaHeader.getCustomerid());
                                form.setField("supplier", "");
                                form.setField("notes", "");
                                form.setField("sku."+, );
                                form.setField("expectedArriveTime", ;
                                form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(docPaHeader.getAsnno(), 800)));
                                stamper.setFormFlattening(true);
                                stamper.close();
                                page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
                                pdfCopy.addPage(page);
                            }
                        }*/
                    }

                }
                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Json resetDocPa(String orderNo){
        if(StringUtils.isEmpty(orderNo)){
            return Json.error("请选择需要回写收货的上架任务单");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("paNo",orderNo);
        docPaHeaderDao.resetAsn(result);
        String codo = result.get("codo").toString();
        if(codo.equals("000")){
            return Json.success("收货回写成功");
        }else{
            return Json.error(codo);
        }
    }

    public static String doubleTrans(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }

    /**
     * 导出上架任务清单
     */
    public void exportDocPaDataToExcel(HttpServletResponse response, String token, String pano) throws IOException {
        Cookie cookie = new Cookie("exportToken",token);
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());

        DocPaHeaderForm query = new DocPaHeaderForm();
        query.setPano(pano);
        try {
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
            // excel的sheetName
            String sheetName ="上架任务清单";
            // excel要导出的数据

            DocPaHeaderQuery docPaHeaderQuery = new DocPaHeaderQuery();
            docPaHeaderQuery.setPano(pano);
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docPaHeaderQuery));
            mybatisCriteria.setOrderByClause("palineno asc");
            List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.queryByList(mybatisCriteria);
            List<DocPaDetailsExportVO> exportVOs = new ArrayList<>();
            DocPaDetailsExportVO docPaDetailsExportVO;

            for (DocPaDetails docPaDetails : docPaDetailsList) {

                docPaDetailsExportVO = new DocPaDetailsExportVO();
                com.wms.utils.BeanUtils.copyProperties(docPaDetails, docPaDetailsExportVO);

                //产品档案
                BasSku basSku = basSkuService.getSkuInfo(docPaDetails.getCustomerid(), docPaDetails.getSku());
                if (basSku != null) {

                    docPaDetailsExportVO.setDescrc(basSku.getDescrC());
                    docPaDetailsExportVO.setReservedfield01(basSku.getReservedfield01());
                }

                InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docPaDetails.getLotnum());
                if (invLotAtt != null) {

                    docPaDetailsExportVO.setLotatt01(invLotAtt.getLotatt01());
                    docPaDetailsExportVO.setLotatt02(invLotAtt.getLotatt02());
                    docPaDetailsExportVO.setLotatt04(invLotAtt.getLotatt04());
                    docPaDetailsExportVO.setLotatt05(invLotAtt.getLotatt05());
                    docPaDetailsExportVO.setLotatt07(invLotAtt.getLotatt07());
                }
                if (docPaDetailsExportVO.getPutwayqtyCompleted() == 0) docPaDetailsExportVO.setPutwayqtyCompleted(null);
                if (docPaDetailsExportVO.getUserdefine1().equals("STAGE01")) docPaDetailsExportVO.setUserdefine1(null);


                exportVOs.add(docPaDetailsExportVO);
            }
            // 导出
            if (exportVOs.size() == 0) {
                System.out.println("上架任务清单内容为空");
            }else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(exportVOs, fieldMap, sheetName,65535, response,pano);
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到导出Excle时题型的英中文map
     *
     * @return 返回题型的属性map
     */
    public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {

        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();

        superClassMap.put("pano","上架任务单号");
        superClassMap.put("palineno","任务行号");
        superClassMap.put("customerid", "货主");
        superClassMap.put("sku", "产品代码");
        superClassMap.put("reservedfield01", "产品名称");
        superClassMap.put("descrc", "规格/型号");
        superClassMap.put("lotatt04", "生产批号");
        superClassMap.put("lotatt05", "序列号");
        superClassMap.put("lotatt07", "灭菌批号");
        superClassMap.put("lotatt01", "生产日期");
        superClassMap.put("lotatt02", "有效期/失效期");
        superClassMap.put("asnqtyExpected", "收货件数");
        superClassMap.put("putwayqtyCompleted", "已上架件数");
        superClassMap.put("userdefine1", "库位");
        superClassMap.put("editwho", "上架人");

        return superClassMap;
    }

    /**
     * 导入上架结果
     * 结束上架操作
     */
    public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {

        Json json = null;
        MultipartFile excelFile = mhsr.getFile("uploadData");

        if(excelFile != null && excelFile.getSize() > 0){

            json = importPaDataService.importExcelData(excelFile);
        }
        return json;
    }
}