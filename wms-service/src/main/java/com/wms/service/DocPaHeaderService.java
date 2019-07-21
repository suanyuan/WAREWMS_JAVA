package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocPaDetails;
import com.wms.entity.DocPaHeader;
import com.wms.entity.enumerator.ContentTypeEnum;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
     * 结束上架单任务
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

                String[] orderCodeArray = orderCodeList.split(",");

                for (String orderCode : orderCodeArray) {

                    DocPaHeader docPaHeader = docPaHeaderDao.queryById(orderCode);
                    if(docPaHeader!=null){

                        int totalNum = 0;
                        int row = 15;
                        int pageSize = 0;

                        DocPaDetailsQuery query = new DocPaDetailsQuery();
                        query.setAsnno(orderCode);

                        List<DocPaDetails> detailsList =  docPaDetailsService.queryDocPdaDetails(orderCode);

                        totalNum = detailsList.size();
                        pageSize = (int)Math.ceil( (double) totalNum / row);
                        for(int i=0;i<pageSize;i++){
                            baos = new ByteArrayOutputStream();
                            stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt_jhck.pdf"), baos);
                            form = stamper.getAcroFields();
                            form.setField("putwmsCode",docPaHeader.getAsnno());
                            form.setField("receviedDdate", DateUtil.format(docPaHeader.getAddtime(),"yyyy-MM-dd"));
                            form.setField("warehouseid", docPaHeader.getWarehouseid());
                            form.setField("custName", docPaHeader.getCustomerid());
                            form.setField("supplier", "");
                            form.setField("notes", "");
                            form.setField("page", "第"+(i+1)+"页,共"+pageSize+"页");
                            //form.setField("barCode1", docPaHeader.getAsnno());
                            form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(docPaHeader.getAsnno(), 800)));
                            //form.replacePushbuttonField("orderCodeImg1", PDFUtil.genPdfButton(form, "orderCodeImg1", BarcodeGeneratorUtil.genBarcode(docPaHeader.getAsnno(), 800)));

                            for(int j=0;j<row;j++){
                                if(totalNum > (row*i+j)){
                                    DocPaDetails docPaDetails = detailsList.get(row * i + j);
                                    BasSku basSku = basSkuService.getSkuInfo(docPaHeader.getCustomerid(),detailsList.get(row * i + j).getSku());

                                    DocAsnDetailQuery queryDetail = new DocAsnDetailQuery();
                                    DocAsnDetailVO detailVO = docAsnDetailService.queryDocAsnDetail(queryDetail);

                                    form.setField("sku."+j, docPaDetails.getSku());
                                    form.setField("skuN."+j, basSku.getDescrC());
                                    form.setField("regNo."+j, basSku.getReservedfield03());
                                    form.setField("desc."+j, basSku.getDescrE());
                                    form.setField("batchNo."+j, docPaDetails.getUserdefine3());
                                    form.setField("seriNo."+j, docPaDetails.getUserdefine4());
                                    form.setField("ill."+j, detailVO.getLotatt07());
                                    form.setField("lot01."+j, detailVO.getLotatt01());
                                    form.setField("lot02."+j, detailVO.getLotatt02());
                                    form.setField("qtyE."+j, docPaDetails.getAsnqtyExpected().toString());
                                    form.setField("uom."+j, basSku.getDefaultreceivinguom());
                                    form.setField("qty."+j, docPaDetails.getPutwayqtyExpected().toString());
                                    form.setField("qtyA."+j, docPaDetails.getPutwayqtyCompleted().toString());
                                }
                            }
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

}