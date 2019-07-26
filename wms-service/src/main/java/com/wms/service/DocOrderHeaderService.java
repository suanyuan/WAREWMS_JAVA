package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocOrderDetail;
import com.wms.entity.DocOrderHeader;
import com.wms.entity.DocOrderPacking;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocOrderHeaderMybatisDao;
import com.wms.mybatis.dao.DocOrderPackingMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocOrderHeaderQuery;
import com.wms.service.importdata.ImportOrderDataService;
import com.wms.utils.*;
import com.wms.vo.DocOrderHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocOrderHeaderForm;
import com.wms.vo.form.pda.PageForm;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.wms.constant.Constant;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("docOrderHeaderService")
public class DocOrderHeaderService extends BaseService {

	@Autowired
	private DocOrderHeaderMybatisDao docOrderHeaderMybatisDao;
	
	@Autowired
	private DocOrderPackingMybatisDao docOrderPackingMybatisDao;
	
	@Autowired
	private ImportOrderDataService importOrderDataService;

	public EasyuiDatagrid<DocOrderHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocOrderHeaderQuery query) {
		EasyuiDatagrid<DocOrderHeaderVO> datagrid = new EasyuiDatagrid<DocOrderHeaderVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocOrderHeader> docOrderHeaderList = docOrderHeaderMybatisDao.queryByPageList(mybatisCriteria);
		DocOrderHeaderVO docOrderHeaderVO = null;
		List<DocOrderHeaderVO> docOrderHeaderVOList = new ArrayList<DocOrderHeaderVO>();
		for (DocOrderHeader docOrderHeader : docOrderHeaderList) {
			docOrderHeaderVO = new DocOrderHeaderVO();
			BeanUtils.copyProperties(docOrderHeader, docOrderHeaderVO);
			docOrderHeaderVOList.add(docOrderHeaderVO);
		}
		datagrid.setTotal((long) docOrderHeaderMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docOrderHeaderVOList);
		return datagrid;
	}

	public List<DocOrderHeaderVO> getUndoneList(PageForm form) {

	    MybatisCriteria mybatisCriteria = new MybatisCriteria();
	    mybatisCriteria.setCurrentPage(form.getPageNum());
	    mybatisCriteria.setPageSize(form.getPageSize());
	    List<DocOrderHeader> docOrderHeaderList = docOrderHeaderMybatisDao.queryByList(mybatisCriteria);
	    DocOrderHeaderVO docOrderHeaderVO = null;
	    List<DocOrderHeaderVO> docOrderHeaderVOList = new ArrayList<>();
	    for (DocOrderHeader docOrderHeader : docOrderHeaderList) {

	        docOrderHeaderVO = new DocOrderHeaderVO();
	        BeanUtils.copyProperties(docOrderHeader, docOrderHeaderVO);
	        docOrderHeaderVOList.add(docOrderHeaderVO);
        }
        return docOrderHeaderVOList;
    }

    public DocOrderHeaderVO queryByOrderNo(String orderno) {

	    DocOrderHeader docOrderHeader = docOrderHeaderMybatisDao.queryById(orderno);
	    DocOrderHeaderVO docOrderHeaderVO = null;
	    if (docOrderHeader != null) {

	        docOrderHeaderVO = new DocOrderHeaderVO();
	        BeanUtils.copyProperties(docOrderHeader, docOrderHeaderVO);
        }
        return docOrderHeaderVO;
    }

	public Json addDocOrderHeader(DocOrderHeaderForm docOrderHeaderForm) throws Exception {
		Json json = new Json();
		/*获取新的订单号*/
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		docOrderHeaderMybatisDao.getIdSequence(map);
		String resultCode = map.get("resultCode").toString();
		String resultNo = map.get("resultNo").toString();
		if (resultCode.substring(0,3).equals("000")) {
			DocOrderHeader docOrderHeader = new DocOrderHeader();
			BeanUtils.copyProperties(docOrderHeaderForm, docOrderHeader);
			docOrderHeader.setOrderno(resultNo);
			docOrderHeader.setEdisendflag(Constant.CODE_YES_OR_NO);
			docOrderHeader.setArchiveflag(docOrderHeaderForm.getSostatus());
			docOrderHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			docOrderHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			docOrderHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			docOrderHeaderMybatisDao.add(docOrderHeader);
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
			json.setObj(docOrderHeader);
			return json;
		} else {
			json.setSuccess(false);
			json.setMsg(resultCode);
			return json;
		}
	}

	public Json editDocOrderHeader(DocOrderHeaderForm docOrderHeaderForm) {
		Json json = new Json();
		DocOrderHeaderQuery docOrderHeaderQuery = new DocOrderHeaderQuery();
		docOrderHeaderQuery.setOrderno(docOrderHeaderForm.getOrderno());
		DocOrderHeader docOrderHeader = docOrderHeaderMybatisDao.queryById(docOrderHeaderQuery);
		BeanUtils.copyProperties(docOrderHeaderForm, docOrderHeader);
		docOrderHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docOrderHeaderMybatisDao.update(docOrderHeader);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json deleteDocOrderHeader(String orderno) {
		Json json = new Json();
		DocOrderHeaderQuery docOrderHeaderQuery = new DocOrderHeaderQuery();
		docOrderHeaderQuery.setOrderno(orderno);
		DocOrderHeader docOrderHeader = docOrderHeaderMybatisDao.queryById(docOrderHeaderQuery);
		if(docOrderHeader != null){
			docOrderHeaderMybatisDao.delete(docOrderHeader);
		}
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json allocationDocOrderHeader(String orderno) {
		Json json = new Json();
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("orderno", orderno);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		docOrderHeaderMybatisDao.allocationByOrder(map);
		String result = map.get("result").toString();
		json.setSuccess(true);
		json.setMsg(result);
		return json;
	}

	public Json deAllocationDocOrderHeader(String orderno) {
		Json json = new Json();
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("orderno", orderno);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		docOrderHeaderMybatisDao.deAllocationByOrder(map);
		String result = map.get("result").toString();
		json.setSuccess(true);
		json.setMsg(result);
		return json;
	}

	public Json pickingDocOrderHeader(String orderno) {
		Json json = new Json();
		String result = null;
		List<DocOrderHeader> allocationDetailsIdList = docOrderHeaderMybatisDao.queryByAllocationDetailsId(orderno);
		if (allocationDetailsIdList != null) {
			for (DocOrderHeader allocationDetailsId : allocationDetailsIdList) {
				Map<String ,Object> map=new HashMap<String, Object>();
				map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
				map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
				docOrderHeaderMybatisDao.pickingByOrder(map);
				result = map.get("result").toString();
			}
		} else {
			
		}
		json.setSuccess(true);
		json.setMsg(result);
		return json;
	}

	public Json unPickingDocOrderHeader(String orderno) {
		Json json = new Json();
		String result = null;
		List<DocOrderHeader> allocationDetailsIdList = docOrderHeaderMybatisDao.queryByUnAllocationDetailsId(orderno);
		if (allocationDetailsIdList != null) {
			for (DocOrderHeader allocationDetailsId : allocationDetailsIdList) {
				Map<String ,Object> map=new HashMap<String, Object>();
				map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
				map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
				docOrderHeaderMybatisDao.unPickingByOrder(map);
				result = map.get("result").toString();
			}
		}
		json.setSuccess(true);
		json.setMsg(result);
		return json;
	}

	@Transactional
	public Json unPackingDocOrderHeader(String orderno) {
		Json json = new Json();
		String result = "000";
		List<DocOrderHeader> allocationDetailsIdList = docOrderHeaderMybatisDao.queryByUnAllocationDetailsId(orderno);
		if (allocationDetailsIdList != null) {
			DocOrderPacking docOrderPacking = new DocOrderPacking();
			docOrderPacking.setOrderno(orderno);
			for (DocOrderHeader allocationDetailsId : allocationDetailsIdList) {
				docOrderPacking.setAllocationdetailsid(allocationDetailsId.getAllocationDetailsId());
				docOrderPackingMybatisDao.packingFlagUpdate(docOrderPacking);
				Map<String ,Object> map=new HashMap<String, Object>();
				map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
				map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
				docOrderHeaderMybatisDao.unPickingByOrder(map);
				if (!map.get("result").toString().equals("000")) {
					result = map.get("result").toString();
				}
			}
			docOrderPackingMybatisDao.packingBoxnoDelete(docOrderPacking);
			docOrderPackingMybatisDao.packingTimeDelete(docOrderPacking);
			docOrderPackingMybatisDao.packingTimeSkuDelete(docOrderPacking);
			docOrderPackingMybatisDao.packingDelete(docOrderPacking);
		}
		json.setSuccess(true);
		json.setMsg(result);
		return json;
	}

	public Json shipmentDocOrderHeader(String orderno) {
		Json json = new Json();
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("orderno", orderno);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		docOrderHeaderMybatisDao.shipmentByOrder(map);
		String result = map.get("result").toString();
		json.setSuccess(true);
		json.setMsg(result);
		return json;
	}

	public Json cancelDocOrderHeader(String orderno) {
		Json json = new Json();
		DocOrderHeaderQuery docOrderHeaderQuery = new DocOrderHeaderQuery();
		docOrderHeaderQuery.setOrderno(orderno);
		DocOrderHeader docOrderHeader = docOrderHeaderMybatisDao.queryById(docOrderHeaderQuery);
		if(docOrderHeader != null){
			if (docOrderHeader.getSostatus().equals("00")) {
				Map<String ,Object> map = new HashMap<String, Object>();
				map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("orderno", orderno);
				map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
				docOrderHeaderMybatisDao.cancelByOrder(map);
				String result = map.get("result").toString();
				json.setMsg(result);
			} else if (docOrderHeader.getSostatus().equals("30") || docOrderHeader.getSostatus().equals("40")) {
				Map<String ,Object> map = new HashMap<String, Object>();
				map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("orderno", orderno);
				map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
				docOrderHeaderMybatisDao.deAllocationByOrder(map);
				String result = map.get("result").toString();
				if (result.equals("000")) {
					Map<String ,Object> cancelMap = new HashMap<String, Object>();
					cancelMap.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
					cancelMap.put("orderno", orderno);
					cancelMap.put("userid", SfcUserLoginUtil.getLoginUser().getId());
					docOrderHeaderMybatisDao.cancelByOrder(map);
					result = map.get("result").toString();
					json.setMsg(result);
				} else {
					json.setMsg(result);
					json.setSuccess(true);
					return json;
				}
			} else if (docOrderHeader.getSostatus().equals("50") || docOrderHeader.getSostatus().equals("60")) {
				List<DocOrderHeader> allocationDetailsIdList = docOrderHeaderMybatisDao.queryByUnAllocationDetailsId(orderno);
				String result = "000";
				if (allocationDetailsIdList != null) {
					for (DocOrderHeader allocationDetailsId : allocationDetailsIdList) {
						Map<String ,Object> map=new HashMap<String, Object>();
						map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
						map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
						docOrderHeaderMybatisDao.unPickingByOrder(map);
						if (!map.get("result").toString().equals("000")) {
							result = map.get("result").toString();
						}
					}
				}
				if (result.equals("000")) {
					Map<String ,Object> map = new HashMap<String, Object>();
					map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
					map.put("orderno", orderno);
					map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
					docOrderHeaderMybatisDao.deAllocationByOrder(map);
					result = map.get("result").toString();
					if (result.equals("000")) {
						Map<String ,Object> cancelMap = new HashMap<String, Object>();
						cancelMap.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						cancelMap.put("orderno", orderno);
						cancelMap.put("userid", SfcUserLoginUtil.getLoginUser().getId());
						docOrderHeaderMybatisDao.cancelByOrder(map);
						result = map.get("result").toString();
						json.setMsg(result);
					} else {
						json.setMsg(result);
						json.setSuccess(true);
						return json;
					}
				} else {
					json.setMsg(result);
					json.setSuccess(true);
					return json;
				}
			} else {
				json.setMsg("订单此状态不能操作取消");
				json.setSuccess(true);
				return json;
			}
		}
		json.setSuccess(true);
		return json;
	}
	
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		if(excelFile != null && excelFile.getSize() > 0){
			json = importOrderDataService.importExcelData(excelFile); 
		}	
		return json;
	}
	
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("order_template.xls"));
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
	
	public void exportPdf(HttpServletResponse response, String orderList) {
		StringBuilder sb = new StringBuilder();
		try (OutputStream os = response.getOutputStream()){
			sb.append("inline; filename=")
			  .append(URLEncoder.encode("测试用Pdf","UTF-8"))
			  .append(".pdf");
			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
			response.setContentType(ContentTypeEnum.pdf.getContentType());
			
			Document document = null;
			AcroFields form = null;
			PdfStamper stamper = null;
			PdfImportedPage page = null;
			ByteArrayOutputStream baos = null;

			if (StringUtils.isNotEmpty(orderList)) {
				
				document = new Document(PDFUtil.getTemplate("dzmd_sfx.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();
				
				String[] orderArray = orderList.split(",");
				for(String orderno : orderArray){
					DocOrderHeader order = docOrderHeaderMybatisDao.queryById(orderno);
					baos = new ByteArrayOutputStream();
					stamper = new PdfStamper(PDFUtil.getTemplate("dzmd_sfx.pdf"), baos);
					form = stamper.getAcroFields();
					
					form.setField("cod", "111");
					form.setField("mdcode", "222");
					form.setField("contact1_tel1", "0521-12345678");
					form.setField("address1", "望石路");
					form.setField("dqcode", "0521");
					form.setField("consigneeid", order.getConsigneename());
					form.setField("tel", order.getCTel1());
					form.setField("c_zip", order.getCZip());
					form.setField("c_address", order.getCAddress1());
					form.setField("address", "021");
					form.setField("deliveryno", order.getOrderno() + "0001");
					form.setField("orderno", order.getOrderno());
					form.replacePushbuttonField("packageBarcodeImg1", PDFUtil.genPdfButton(form, "packageBarcodeImg1", BarcodeGeneratorUtil.genBarcode(order.getOrderno() + "0001", 800)));
					form.replacePushbuttonField("packageBarcodeImg2", PDFUtil.genPdfButton(form, "packageBarcodeImg2", BarcodeGeneratorUtil.genBarcode(order.getOrderno() + "0001", 800)));
					form.replacePushbuttonField("packageBarcodeImg3", PDFUtil.genPdfButton(form, "packageBarcodeImg3", BarcodeGeneratorUtil.genBarcode(order.getOrderno(), 800)));
					
					stamper.setFormFlattening(true);
					stamper.close();
					page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
					pdfCopy.addPage(page);
				}
				document.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void exportPickingPdf(HttpServletResponse response, String orderList) {
		StringBuilder sb = new StringBuilder();
		try (OutputStream os = response.getOutputStream()){
			sb.append("inline; filename=")
			  .append(URLEncoder.encode("拣货单PDF","UTF-8"))
			  .append(".pdf");
			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
			response.setContentType(ContentTypeEnum.pdf.getContentType());
			
			Document document = null;
			AcroFields form = null;
			PdfStamper stamper = null;
			PdfImportedPage page = null;
			ByteArrayOutputStream baos = null;

			if (StringUtils.isNotEmpty(orderList)) {
				
				document = new Document(PDFUtil.getTemplate("wms_picking.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();
				
				int totalNum = 0;
				List<DocOrderDetail> detailsList = null;
				int row = 10;
				int pageSize = 0;
				String[] orderArray = orderList.split(",");
				for(String orderno : orderArray){
					detailsList = new ArrayList<DocOrderDetail>();
					DocOrderHeader orderHeader = docOrderHeaderMybatisDao.queryByPickingList(orderno);
					for(DocOrderDetail orderDetails : orderHeader.getDocOrderDetailList()){
						totalNum++;
						detailsList.add(orderDetails);
					}
					
					pageSize = (int)Math.ceil((double)totalNum / row);
					for(int i = 0 ; i < pageSize ; i++){
						baos = new ByteArrayOutputStream();
						stamper = new PdfStamper(PDFUtil.getTemplate("wms_picking.pdf"), baos);
						form = stamper.getAcroFields();
						form.setField("orderno", orderHeader.getOrderno());
						form.setField("customerName", orderHeader.getCustomerid());
						form.setField("soreference1", orderHeader.getSoreference1() == null ? "" : orderHeader.getSoreference1());
						form.setField("soreference2", orderHeader.getSoreference2() == null ? "" : orderHeader.getSoreference2());
						form.setField("soreference3", orderHeader.getSoreference3() == null ? "" : orderHeader.getSoreference3());
						form.setField("totalqty", String.valueOf(Double.parseDouble(orderHeader.getTotalQty().toString())));
						form.setField("totalgrossweight", String.valueOf(Double.parseDouble(orderHeader.getTotalGrossWeight().toString())));
						form.setField("totalcube", String.valueOf(Double.parseDouble(orderHeader.getTotalCube().toString())));
						form.setField("notes", orderHeader.getNotes() == null ? "" : orderHeader.getNotes());
						for(int j = 0 ; j < row ; j++){
							if(totalNum > (row * i + j)){
								form.setField("seq"+(j+1), String.valueOf(detailsList.get(row * i + j).getOrderlineno()));
								form.setField("sku"+(j+1), detailsList.get(row * i + j).getSku());
								form.setField("skuName"+(j+1), detailsList.get(row * i + j).getSkuName());
								form.setField("packid"+(j+1), detailsList.get(row * i + j).getPackid());
								form.setField("uom"+(j+1), detailsList.get(row * i + j).getUom());
								form.setField("qtyallocated"+(j+1), String.valueOf(detailsList.get(row * i + j).getQtyallocated()));
								form.setField("locationid"+(j+1), detailsList.get(row * i + j).getLocation());
								form.setField("lotatt01"+(j+1), detailsList.get(row * i + j).getLotatt01());
								form.setField("lotatt02"+(j+1), detailsList.get(row * i + j).getLotatt02());
								form.setField("lotatt03"+(j+1), detailsList.get(row * i + j).getLotatt03());
							}
						}
						form.replacePushbuttonField("packageBarcodeImg", PDFUtil.genPdfButton(form, "packageBarcodeImg", BarcodeGeneratorUtil.genBarcode(orderHeader.getOrderno(), 800)));
						stamper.setFormFlattening(true);
						stamper.close();
						page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
						pdfCopy.addPage(page);
					}
					totalNum = 0;
				}
				document.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public List<EasyuiCombobox> getOrderTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocOrderHeader> docOrderHeaderList = docOrderHeaderMybatisDao.queryByOrderType();
		if(docOrderHeaderList != null && docOrderHeaderList.size() > 0){
			//首行为置空项
			combobox = new EasyuiCombobox();
			combobox.setId("");
			combobox.setValue("　");
			comboboxList.add(combobox);
			//下拉框添加数据
			for(DocOrderHeader docOrderHeader : docOrderHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docOrderHeader.getOrdertype()));
				combobox.setValue(docOrderHeader.getOrdertypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getSostatusCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocOrderHeader> docOrderHeaderList = docOrderHeaderMybatisDao.queryBySostatus();
		if(docOrderHeaderList != null && docOrderHeaderList.size() > 0){
			//首行为置空项
			combobox = new EasyuiCombobox();
			combobox.setId("");
			combobox.setValue("　");
			comboboxList.add(combobox);
			//下拉框添加数据
			for(DocOrderHeader docOrderHeader : docOrderHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docOrderHeader.getSostatus()));
				combobox.setValue(docOrderHeader.getSostatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getReleasestatusCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocOrderHeader> docOrderHeaderList = docOrderHeaderMybatisDao.queryByReleasestatus();
		if(docOrderHeaderList != null && docOrderHeaderList.size() > 0){
			//首行为置空项
			combobox = new EasyuiCombobox();
			combobox.setId("");
			combobox.setValue("　");
			comboboxList.add(combobox);
			//下拉框添加数据
			for(DocOrderHeader docOrderHeader : docOrderHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docOrderHeader.getReleasestatus()));
				combobox.setValue(docOrderHeader.getReleasestatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public DocOrderHeader getOrderHeader(DocOrderHeaderQuery query) {
		query.setOrderno(query.getOrderno().replace(",", ""));
		DocOrderHeader docOrderHeader = docOrderHeaderMybatisDao.queryById(query);
		return docOrderHeader;
	}
}