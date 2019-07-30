package com.wms.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.wms.mybatis.entity.pda.PdaDocAsnEndForm;
import com.wms.result.PdaResult;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocAsnHeaderVO;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.entity.ViewInvTran;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.service.importdata.ImportAsnDataService;
import com.wms.service.importdata.ImportOrderDataService;
import com.wms.utils.BarcodeGeneratorUtil;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.PDFUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocAsnHeaderForm;
import com.wms.vo.form.DocAsnHeaderForm;
import com.wms.vo.form.ViewInvTranForm;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocAsnHeaderQuery;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocAsnHeaderQuery;

@Service("docAsnHeaderService")
public class DocAsnHeaderService extends BaseService {

	@Autowired
	private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
	
	@Autowired
	private ImportAsnDataService importAsnDataService;

	public EasyuiDatagrid<DocAsnHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnHeaderQuery query) {
		EasyuiDatagrid<DocAsnHeaderVO> datagrid = new EasyuiDatagrid<DocAsnHeaderVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		mybatisCriteria.setOrderByClause("addtime desc");
		List<DocAsnHeader> docAsnHeaderList = docAsnHeaderMybatisDao.queryByPageList(mybatisCriteria);
		DocAsnHeaderVO docAsnHeaderVO = null;
		List<DocAsnHeaderVO> docAsnHeaderVOList = new ArrayList<DocAsnHeaderVO>();
		for (DocAsnHeader docAsnHeader : docAsnHeaderList) {
			docAsnHeaderVO = new DocAsnHeaderVO();
			BeanUtils.copyProperties(docAsnHeader, docAsnHeaderVO);
			docAsnHeaderVOList.add(docAsnHeaderVO);
		}
		datagrid.setTotal((long) docAsnHeaderMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docAsnHeaderVOList);
		return datagrid;
	}

	public Json addDocAsnHeader(DocAsnHeaderForm docAsnHeaderForm) throws Exception {
		Json json = new Json();
		/*获取新的订单号*/
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		docAsnHeaderMybatisDao.getIdSequence(map);
		String resultCode = map.get("resultCode").toString();
		String resultNo = map.get("resultNo").toString();
		if (resultCode.substring(0,3).equals("000")) {
			DocAsnHeader docAsnHeader = new DocAsnHeader();
			BeanUtils.copyProperties(docAsnHeaderForm, docAsnHeader);
			docAsnHeader.setAsnno(resultNo);
			docAsnHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			docAsnHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			docAsnHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			docAsnHeaderMybatisDao.add(docAsnHeader);
			json.setSuccess(true);
			json.setMsg(resultNo);
			return json;
		} else {
			json.setSuccess(false);
			json.setMsg(resultCode);
			return json;
		}
	}

	public Json editDocAsnHeader(DocAsnHeaderForm docAsnHeaderForm) {
		Json json = new Json();
		DocAsnHeaderQuery docAsnHeaderQuery = new DocAsnHeaderQuery();
		docAsnHeaderQuery.setAsnno(docAsnHeaderForm.getAsnno());
		DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docAsnHeaderQuery);
		BeanUtils.copyProperties(docAsnHeaderForm, docAsnHeader);
		docAsnHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docAsnHeaderMybatisDao.update(docAsnHeader);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}
	
	public Json closeDocAsnHeader(String id) {
		Json json = new Json();
		
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("asnno", id);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		DocAsnHeaderQuery docAsnHeaderQuery = new DocAsnHeaderQuery();
		docAsnHeaderQuery.setAsnno(id);
		DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docAsnHeaderQuery);
		if(docAsnHeader != null){
			docAsnHeaderMybatisDao.close(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("关单成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("关单失败！"+result);
			}
		}
		return json;
	}
	
	public Json cancelDocAsnHeader(String id) {
		Json json = new Json();
		System.out.print("111111111111111");
		System.out.print("++++++++++++++++++++++++++"+id);
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("asnno", id);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		DocAsnHeaderQuery docAsnHeaderQuery = new DocAsnHeaderQuery();
		docAsnHeaderQuery.setAsnno(id);
		DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docAsnHeaderQuery);
		if(docAsnHeader != null){
			System.out.print("222222222222");
			System.out.print("++++++++++++++++++++++++++"+map.get("asnno"));
			docAsnHeaderMybatisDao.cancel(map);
			String result = map.get("result").toString();
			if (result.substring(0,3).equals("000")) {
				json.setSuccess(true);
				json.setMsg("取消成功！");
			} else {
				json.setSuccess(false);
				json.setMsg("取消失败！"+result);
			}
		}
		return json;
	}
	
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		if(excelFile != null && excelFile.getSize() > 0){
			json = importAsnDataService.importExcelData(excelFile); 
		}	
		return json;
	}
	
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("asn_template.xls"));
			response.reset();
			Cookie cookie = new Cookie("downloadToken",token);
			cookie.setMaxAge(60);	
			response.addCookie(cookie);
			response.setContentType(ContentTypeEnum.stream.getContentType());
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			
			try(InputStream fis = new BufferedInputStream(new FileInputStream(file))){
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				toClient.write(buffer);
				toClient.flush();
			}catch(IOException ex){
//				log.error(ExceptionUtil.getExceptionMessage(ex));
			}
		} catch (Exception e) {
//			log.error(ExceptionUtil.getExceptionMessage(e));
		}
	}
	
//	public void exportPdf(HttpServletResponse response, String orderList) {
//		StringBuilder sb = new StringBuilder();
//		try (OutputStream os = response.getOutputStream()){
//			sb.append("inline; filename=")
//			  .append(URLEncoder.encode("测试用Pdf","UTF-8"))
//			  .append(".pdf");
//			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
//			response.setContentType(ContentTypeEnum.pdf.getContentType());
//			
//			Document document = null;
//			AcroFields form = null;
//			PdfStamper stamper = null;
//			PdfImportedPage page = null;
//			ByteArrayOutputStream baos = null;
//
//			if (StringUtils.isNotEmpty(orderList)) {
//				
//				document = new Document(PDFUtil.getTemplate("dzmd_sfx.pdf").getPageSize(1));
//				PdfCopy pdfCopy = new PdfCopy(document, os);
//				document.open();
//				
//				String[] orderArray = orderList.split(",");
//				for(String orderno : orderArray){
//					DocAsnHeader order = docAsnHeaderMybatisDao.queryById(orderno);
//					baos = new ByteArrayOutputStream();
//					stamper = new PdfStamper(PDFUtil.getTemplate("dzmd_sfx.pdf"), baos);
//					form = stamper.getAcroFields();
//					
//					form.setField("cod", "111");
//					form.setField("mdcode", "222");
//					form.setField("contact1_tel1", "0521-12345678");
//					form.setField("address1", "望石路");
//					form.setField("dqcode", "0521");
//					form.setField("consigneeid", order.getConsigneename());
//					form.setField("tel", order.getCTel1());
//					form.setField("c_zip", order.getCZip());
//					form.setField("c_address", order.getCAddress1());
//					form.setField("address", "021");
//					form.setField("deliveryno", order.getOrderno() + "0001");
//					form.setField("orderno", order.getOrderno());
//					form.replacePushbuttonField("packageBarcodeImg1", PDFUtil.genPdfButton(form, "packageBarcodeImg1", BarcodeGeneratorUtil.genBarcode(order.getOrderno() + "0001", 800)));
//					form.replacePushbuttonField("packageBarcodeImg2", PDFUtil.genPdfButton(form, "packageBarcodeImg2", BarcodeGeneratorUtil.genBarcode(order.getOrderno() + "0001", 800)));
//					form.replacePushbuttonField("packageBarcodeImg3", PDFUtil.genPdfButton(form, "packageBarcodeImg3", BarcodeGeneratorUtil.genBarcode(order.getOrderno(), 800)));
//					
//					stamper.setFormFlattening(true);
//					stamper.close();
//					page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
//					pdfCopy.addPage(page);
//				}
//				document.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//	}

	public List<EasyuiCombobox> getAsnTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocAsnHeader> docAsnHeaderList = docAsnHeaderMybatisDao.queryByAsnType();
		if(docAsnHeaderList != null && docAsnHeaderList.size() > 0){
			for(DocAsnHeader docAsnHeader : docAsnHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docAsnHeader.getAsntype()));
				combobox.setValue(docAsnHeader.getAsntypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getAsnstatusCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocAsnHeader> docAsnHeaderList = docAsnHeaderMybatisDao.queryByAsnstatus();
		if(docAsnHeaderList != null && docAsnHeaderList.size() > 0){
			for(DocAsnHeader docAsnHeader : docAsnHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docAsnHeader.getAsnstatus()));
				combobox.setValue(docAsnHeader.getAsnstatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getReleasestatusCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocAsnHeader> docAsnHeaderList = docAsnHeaderMybatisDao.queryByReleasestatus();
		if(docAsnHeaderList != null && docAsnHeaderList.size() > 0){
			for(DocAsnHeader docAsnHeader : docAsnHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docAsnHeader.getReleasestatus()));
				combobox.setValue(docAsnHeader.getReleasestatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public DocAsnHeader getAsnHeader(DocAsnHeaderQuery query) {
		query.setAsnno(query.getAsnno().replace(",", ""));
		DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(query);
//		System.out.print("4444444444");
//		System.out.print(docAsnHeader.getAsnno());
		return docAsnHeader;
	}

    /**
     * 查询未完成的预入库通知单
     * @param form 分页
     * @return ~
     */
	public List<PdaDocAsnHeaderVO> queryUndoneList(PageForm form) {

//	    int pagesize = form.getPageSize();
//	    int start = form.getStart();
	    List<DocAsnHeader> docAsnHeaderList = docAsnHeaderMybatisDao.queryUndoneList(form.getStart(), form.getPageSize());
	    List<PdaDocAsnHeaderVO> pdaDocAsnHeaderVOList = new ArrayList<>();
	    PdaDocAsnHeaderVO pdaDocAsnHeaderVO = new PdaDocAsnHeaderVO();
	    for (DocAsnHeader docAsnHeader : docAsnHeaderList) {

	        pdaDocAsnHeaderVO = new PdaDocAsnHeaderVO();
	        BeanUtils.copyProperties(docAsnHeader, pdaDocAsnHeaderVO);
	        pdaDocAsnHeaderVOList.add(pdaDocAsnHeaderVO);
        }
	    return pdaDocAsnHeaderVOList;
	}

    /**
     * 通过asnno查询header
     * @param asnno ~
     * @return ~
     */
	public PdaDocAsnHeaderVO queryByAsnno(String asnno) {

	    DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(asnno);
	    PdaDocAsnHeaderVO pdaDocAsnHeaderVO = new PdaDocAsnHeaderVO();
	    if (docAsnHeader != null) {

	        BeanUtils.copyProperties(docAsnHeader, pdaDocAsnHeaderVO);
        }
        return pdaDocAsnHeaderVO;
    }

    /**
     * 结束收货单任务
     * @param form 预入通知单号
     * @return ~
     */
    public PdaResult endTask(PdaDocAsnEndForm form) {

        form.setEditwho("Gizmo");
        int result = docAsnHeaderMybatisDao.endTask(form);

        if (result == 0) {
            return new PdaResult(PdaResult.CODE_FAILURE, "操作失败, 订单号不存在");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "操作成功");
    }
}