package com.wms.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.wms.entity.DocOrderHeader;
import com.wms.entity.DocOrderPacking;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.utils.BarcodeGeneratorUtil;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.PDFUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocOrderPackingVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.dao.DocOrderHeaderMybatisDao;
import com.wms.mybatis.dao.DocOrderPackingMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocOrderHeaderQuery;
import com.wms.query.DocOrderPackingQuery;

@Service("docOrderPackingService")
public class DocOrderPackingService extends BaseService {

	@Autowired
	private DocOrderPackingMybatisDao docOrderPackingMybatisDao;
	
	@Autowired
	private DocOrderHeaderMybatisDao docOrderHeaderMybatisDao;
	
	public EasyuiDatagrid<DocOrderPackingVO> getPagedDatagrid(EasyuiDatagridPager pager, DocOrderPackingQuery query) {
		EasyuiDatagrid<DocOrderPackingVO> datagrid = new EasyuiDatagrid<DocOrderPackingVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.queryByPageList(mybatisCriteria);
		DocOrderPackingVO docOrderPackingVO = null;
		List<DocOrderPackingVO> docOrderPackingVOList = new ArrayList<DocOrderPackingVO>();
		for (DocOrderPacking docOrderPacking : docOrderPackingList) {
			docOrderPackingVO = new DocOrderPackingVO();
			BeanUtils.copyProperties(docOrderPacking, docOrderPackingVO);
			docOrderPackingVOList.add(docOrderPackingVO);
		}
		datagrid.setTotal((long) docOrderPackingMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docOrderPackingVOList);
		return datagrid;
	}

	public Json orderStatusCheck(String orderno) {
		Json json = new Json();
		DocOrderHeaderQuery docOrderHeaderQuery = new DocOrderHeaderQuery();
		docOrderHeaderQuery.setOrderno(orderno);
		DocOrderHeader docOrderHeader = docOrderHeaderMybatisDao.queryById(docOrderHeaderQuery);
		if(docOrderHeader != null){
			if (!docOrderHeader.getSostatus().equals("30") && !docOrderHeader.getSostatus().equals("40")) {
				json.setMsg("订单状态异常!" + docOrderHeader.getSostatusName());
			} else if (!docOrderHeader.getReleasestatus().equals("Y")) {
				json.setMsg("订单释放状态异常!" + docOrderHeader.getReleasestatusName());
			} else {
				json.setMsg("000");
			}
		} else {
			json.setMsg("订单号不存在!");
		}
		json.setSuccess(true);
		return json;
	}

	@Transactional
	public Json addBoxno(String orderno) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderno(orderno);
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryBoxnoById(docOrderPackingQuery);
		if(docOrderPacking == null){
			docOrderPacking = new DocOrderPacking();
			docOrderPacking.setOrderno(orderno);
			docOrderPacking.setTraceid(orderno + "#001");
			docOrderPacking.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			docOrderPackingMybatisDao.packingBoxnoAdd(docOrderPacking);
			docOrderPackingMybatisDao.packingTimeAdd(docOrderPacking);
		} else {
			DecimalFormat df = new DecimalFormat("000");
			docOrderPacking.setTraceid(orderno + "#" + df.format(docOrderPacking.getQty()));
			docOrderPacking.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			docOrderPackingMybatisDao.packingBoxnoUpdate(docOrderPacking);
			docOrderPackingMybatisDao.packingTimeAdd(docOrderPacking);
		}
		json.setSuccess(true);
		json.setMsg("000");
		json.setObj(docOrderPacking);
		return json;
	}

	public Json skuScanCheck(String orderno, String traceid, String sku) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderno(orderno);
		docOrderPackingQuery.setTraceid(traceid);
		docOrderPackingQuery.setSku(sku.toUpperCase());
		//产品信息校验
		List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.checkSkuById(docOrderPackingQuery);
		if (docOrderPackingList.size() == 0) {
			json.setMsg("无效的产品信息!");
			json.setSuccess(true);
			return json;
		} else if (docOrderPackingList.size() > 1) {
			json.setMsg("异常的产品信息!");
			json.setSuccess(true);
			return json;
		}
		docOrderPackingQuery.setSku(docOrderPackingList.get(0).getSku());
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryById(docOrderPackingQuery);
		json.setMsg("000");
		json.setSuccess(true);
		json.setObj(docOrderPacking);
		return json;
	}

	@Transactional
	public Json skuScan(String orderno, String traceid, String sku, Integer qty) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderno(orderno);
		docOrderPackingQuery.setTraceid(traceid);
		docOrderPackingQuery.setSku(sku.toUpperCase());
		docOrderPackingQuery.setQty(new BigDecimal(qty));
		//扫描数量校验
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.checkQtyById(docOrderPackingQuery);
		if(docOrderPacking == null) {
			json.setMsg("产品已超量!");
			json.setSuccess(true);
			return json;
		} else {
			docOrderPackingQuery.setSku(docOrderPacking.getSku());
			docOrderPackingQuery.setAllocationdetailsid(docOrderPacking.getAllocationdetailsid());
			long checkCount = docOrderPackingMybatisDao.checkCountById(docOrderPackingQuery);
			docOrderPacking.setTraceid(traceid);
			docOrderPacking.setQty(new BigDecimal(qty));
			docOrderPacking.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			if (checkCount == 0) {
				docOrderPackingMybatisDao.packingAdd(docOrderPacking);
			} else {
				docOrderPackingMybatisDao.packingUpdate(docOrderPacking);
			}
			docOrderPackingMybatisDao.packingSkuAdd(docOrderPacking);
			docOrderPackingMybatisDao.packingTimeUpdate(docOrderPacking);
		}
		DocOrderPacking allDocOrderPacking = docOrderPackingMybatisDao.queryById(docOrderPackingQuery);
		json.setMsg("000");
		json.setSuccess(true);
		json.setObj(allDocOrderPacking);
		return json;
	}

	public Json commitDocOrderPacking(String orderno) {
		Json json = new Json();
		/*获取新的订单号*/
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("orderno", orderno);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		docOrderPackingMybatisDao.commitOrderPacking(map);
		String result = map.get("result").toString();
		json.setSuccess(true);
		json.setMsg(result);
		return json;
	}

	public Json packingSingleDelete(String traceid, String sku, String allocationdetailsid) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setTraceid(traceid);
		docOrderPackingQuery.setSku(sku);
		docOrderPackingQuery.setAllocationdetailsid(allocationdetailsid);
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryPackingById(docOrderPackingQuery);
		if(docOrderPacking != null){
			if (docOrderPacking.getQty().intValue() == 1) {
				docOrderPackingMybatisDao.packingSkuDelete(docOrderPackingQuery);
			} else {
				docOrderPackingMybatisDao.packingSingleDelete(docOrderPackingQuery);
			}
		} else {
			json.setSuccess(true);
			json.setMsg("装箱数据不存在");
			return json;
		}
		json.setMsg("资料处理成功");
		json.setSuccess(true);
		return json;
	}

	public Json packingSkuDelete(String traceid, String sku, String allocationdetailsid) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setTraceid(traceid);
		docOrderPackingQuery.setSku(sku);
		docOrderPackingQuery.setAllocationdetailsid(allocationdetailsid);
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryPackingById(docOrderPackingQuery);
		if(docOrderPacking != null){
			docOrderPackingMybatisDao.packingSkuDelete(docOrderPackingQuery);
		} else {
			json.setSuccess(true);
			json.setMsg("装箱数据不存在");
			return json;
		}
		json.setMsg("资料处理成功");
		json.setSuccess(true);
		return json;
	}

	public void exportPackingLabelPdf(HttpServletResponse response, String orderno, String traceid) {
		StringBuilder sb = new StringBuilder();
		try (OutputStream os = response.getOutputStream()){
			sb.append("inline; filename=")
			  .append(URLEncoder.encode("装箱标签PDF","UTF-8"))
			  .append(".pdf");
			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
			response.setContentType(ContentTypeEnum.pdf.getContentType());
			
			Document document = null;
			AcroFields form = null;
			PdfStamper stamper = null;
			PdfImportedPage page = null;
			ByteArrayOutputStream baos = null;

			if (StringUtils.isNotEmpty(orderno)) {
				
				document = new Document(PDFUtil.getTemplate("wms_packing_label.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();
				
				DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
				docOrderPackingQuery.setOrderno(orderno);
				docOrderPackingQuery.setTraceid(traceid);
				List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.queryPackingLabelById(docOrderPackingQuery);
				for(DocOrderPacking docOrderPacking : docOrderPackingList){
					
					baos = new ByteArrayOutputStream();
					stamper = new PdfStamper(PDFUtil.getTemplate("wms_packing_label.pdf"), baos);
					form = stamper.getAcroFields();
					form.setField("packageCode", docOrderPacking.getTraceid());
					//form.setField("quantity", "1/10");
					form.setField("merchantName", docOrderPacking.getMerchantName() == null ? "***" : docOrderPacking.getMerchantName());
					form.setField("name", docOrderPacking.getConsigneename());
					//form.setField("createTime", docOrderPacking.getSkuName());
					form.setField("address", docOrderPacking.getcAddress1());
					form.replacePushbuttonField("packageBarcodeImg", PDFUtil.genPdfButton(form, "packageBarcodeImg", BarcodeGeneratorUtil.genBarcode(docOrderPacking.getTraceid(), 800)));
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

	public void exportPackingListPdf(HttpServletResponse response,
			String orderno, String traceid) {
		// TODO Auto-generated method stub
		
	}
}