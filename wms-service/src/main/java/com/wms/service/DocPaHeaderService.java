package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocPaDetails;
import com.wms.entity.DocPaHeader;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocPaHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.pda.PdaDocPaEndForm;
import com.wms.query.DocPaDetailsQuery;
import com.wms.query.DocPaHeaderQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.PDFUtil;
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
import java.util.List;

@Service("docPaHeaderService")
public class DocPaHeaderService extends BaseService {

	@Autowired
	private DocPaHeaderMybatisDao docPaHeaderDao;
	@Autowired
	private DocPaDetailsService docPaDetailsService;

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

                document = new Document(PDFUtil.getTemplate("wms_crossdocking_package.pdf").getPageSize(1));
                PdfCopy pdfCopy = new PdfCopy(document, os);
                document.open();

               /* String[] orderCodeArray = orderCodeList.split(",");
                for (String orderCode : orderCodeArray) {

                    DocPaHeader docPaHeader = docPaHeaderDao.queryById(orderCode);
                    if(docPaHeader!=null){
                        DocPaDetailsQuery query = new DocPaDetailsQuery();
                        query.setAsnno(orderCode);
                        List<DocPaDetails> detailsList =  docPaDetailsService.queryDocPdaDetails(orderCode);
                        if(detailsList!=null && detailsList.size()>0){
                            for(DocPaDetails details : detailsList){
                                baos = new ByteArrayOutputStream();
                                stamper = new PdfStamper(PDFUtil.getTemplate("wms_crossdocking_package.pdf"), baos);
                                form = stamper.getAcroFields();
                                form.setField("fenceName",);
                                form.setField("barCode", );
                                form.setField("quantity", );
                                form.setField("custShortName", );
                                form.setField("custStore", );
                                form.setField("name", );
                                form.setField("address", );
                                form.setField("expectedArriveTime", ;
                                form.replacePushbuttonField("barCodeImg", PDFUtil.genPdfButton(form, "barCodeImg", BarcodeGeneratorUtil.genBarcode(orderPackageForCrossDocking.getBarCode(), 800)));
                                stamper.setFormFlattening(true);
                                stamper.close();
                                page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
                                pdfCopy.addPage(page);
                            }
                        }
                    }

                }
                document.close();*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}