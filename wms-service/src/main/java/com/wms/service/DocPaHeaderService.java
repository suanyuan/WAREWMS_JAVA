package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.dao.DocPaDetailsMybatisDao;
import com.wms.mybatis.dao.DocPaHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.pda.PdaDocPaEndForm;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocPaDetailsQuery;
import com.wms.query.DocPaHeaderQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BarcodeGeneratorUtil;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.DateUtil;
import com.wms.utils.PDFUtil;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.DocPaDetailsVO;
import com.wms.vo.DocPaHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocPaHeaderForm;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private GspProductRegisterService gspProductRegisterService;

    @Autowired
    private DocPaDetailsMybatisDao docPaDetailsMybatisDao;

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

/*打印*/
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

}