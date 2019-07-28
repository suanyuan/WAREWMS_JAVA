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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.avalon.framework.configuration.ConfigurationException;
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
import com.wms.dao.BasLocationDao;
import com.wms.entity.BasCustomer;
import com.wms.entity.BasLocation;
import com.wms.entity.BasSku;
import com.wms.entity.BasZone;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.service.importdata.ImportLocationDataService;
import com.wms.utils.BarcodeGeneratorUtil;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.LoginUtil;
import com.wms.utils.PDFUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasLocationVO;
import com.wms.vo.BasSkuVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasLocationForm;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasLocationQuery;
import com.wms.mybatis.dao.BasLocationMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;

@Service("basLocationService")
public class BasLocationService extends BaseService {

	@Autowired
	private BasLocationDao basLocationDao;
	
	@Autowired
	private BasLocationMybatisDao basLocationMybatisDao;
	
	@Autowired
	private ImportLocationDataService importLocationDataService;

	public EasyuiDatagrid<BasLocationVO> getPagedDatagrid(EasyuiDatagridPager pager, BasLocationQuery query) {
		EasyuiDatagrid<BasLocationVO> datagrid = new EasyuiDatagrid<BasLocationVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		//List<BasLocation> basLocationList = basLocationDao.getPagedDatagrid(pager, query);
		List<BasLocation> basLocationList = basLocationMybatisDao.queryByPageList(mybatisCriteria);
		BasLocationVO basLocationVO = null;
		List<BasLocationVO> basLocationVOList = new ArrayList<BasLocationVO>();
		for (BasLocation basLocation : basLocationList) {
			basLocationVO = new BasLocationVO();
			BeanUtils.copyProperties(basLocation, basLocationVO);
			basLocationVOList.add(basLocationVO);
		}
		//datagrid.setTotal(basLocationDao.countAll(query));
		datagrid.setTotal((long) basLocationMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basLocationVOList);
		return datagrid;
	}

	public Json addBasLocation(BasLocationForm basLocationForm) throws Exception {
		Json json = new Json();
		Date today = new Date();
		BasLocation basLocation = new BasLocation();
		StringBuilder resultMsg = new StringBuilder();
		BeanUtils.copyProperties(basLocationForm, basLocation);
		
		this.validateLocation(basLocationForm, resultMsg);// 验证库区是否存在
		
		
		if (resultMsg.length() == 0) {
					
			        basLocation.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			        basLocation.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			        basLocation.setAddtime(today);
			        basLocation.setEdittime(today);
			        //basLocation.setMixFlag("N");
			        //basLocation.setMixLotflag("N");
			        //basLocation.setLoseidFlag("N");
			        //basLocation.setFacilityId("001");
			        //basLocation.setStatus("OK");
			        //basLocation.setCscount(0);
					
			        //basLocationDao.save(basLocation);
			        basLocationMybatisDao.add(basLocation);
				}else {
					json.setSuccess(false);
					json.setMsg(resultMsg.toString());
					return json;
				}
				
				json.setSuccess(true);
				return json;
			}
		
	private void validateLocation(BasLocationForm basLocationForm, StringBuilder resultMsg) {
		BasLocation location = null;
		BasLocationQuery LocationQuery = new BasLocationQuery();
		LocationQuery.setLocationid(basLocationForm.getLocationid());
		//location = basLocationDao.getUniqueByQuery(LocationQuery);
		location = basLocationMybatisDao.queryById(LocationQuery);
		if(location != null){
			resultMsg.append("库位：").append(basLocationForm.getLocationid())
					 .append("，重复").append(" ");
		}
	}
	
	public Json editBasLocation(BasLocationForm basLocationForm) {
		Json json = new Json();
		Date today = new Date();
		BasLocationQuery locationQuery = new BasLocationQuery();
		locationQuery.setLocationid(basLocationForm.getLocationid());
		BasLocation basLocation = basLocationMybatisDao.queryById(locationQuery);
		BeanUtils.copyProperties(basLocationForm, basLocation);
		
		basLocation.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basLocation.setEdittime(today);
		basLocationMybatisDao.update(basLocation);
		json.setSuccess(true);
		json.setMsg("操作成功");
		return json;
		
	}

	public Json deleteBasLocation(String locationid) {
		Json json = new Json();
		BasLocationQuery locationQuery = new BasLocationQuery();
		locationQuery.setLocationid(locationid);
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("locationid", locationid);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		BasLocation basLocation = basLocationMybatisDao.queryById(locationQuery);
		if(basLocation != null){
			//TODO 判断存储过程修改
			//basLocationMybatisDao.basLocationCheck(map);
			//String result = map.get("result").toString();
			//if (result.equals("000")) {
				basLocationMybatisDao.delete(basLocation);
			//} else {
			//	json.setSuccess(false);
			//	json.setMsg(result);
			//	return json;
			//}
		}
		json.setSuccess(true);
		json.setMsg("操作成功");
		return json;
	}

	public List<EasyuiCombobox> getBasLocationCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basLocation.getLocationid()));
				combobox.setValue(basLocation.getLocationid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public Json importData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile csvFile = mhsr.getFile("uploadData");
		if(csvFile != null && csvFile.getSize() > 0){
			json = importLocationDataService.importData(csvFile); 
		}	
		return json;
	}
	
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		if(excelFile != null && excelFile.getSize() > 0){
			json = importLocationDataService.importExcelData(excelFile); 
		}	
		return json;
	}
	
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("location_template.xls"));
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
	
	
	
	public void exportPdf(HttpServletResponse response, String locationid) {
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
			
			BasLocationQuery locationQuery = new BasLocationQuery();
			locationQuery.setLocationid(locationid);
			BasLocation basLocation = basLocationMybatisDao.queryById(locationQuery);
			
			document = new Document(PDFUtil.getTemplate("crossdocking_package.pdf").getPageSize(1));
			PdfCopy pdfCopy = new PdfCopy(document, os);
			document.open();
//			for(int i = 0 ; i < order.getTotalNum() ; i++){
				baos = new ByteArrayOutputStream();
				stamper = new PdfStamper(PDFUtil.getTemplate("crossdocking_package.pdf"), baos);
				form = stamper.getAcroFields();
				
//				sb.append(order.getTotalNum()).append("/").append(i+1);
				form.setField("quantity", basLocation.getAddwho());sb.setLength(0);
//				form.setField("createTime", DateUtil.format(order.getCreateTime()));
//				form.setField("storeName", basLocation.getCustomerid());
//				form.setField("merchantName", basLocation.getSku());
//				sb.append(order.getReceName()).append(",").append(order.getReceMobile());
				form.setField("name", sb.toString());sb.setLength(0);
//				sb.append(order.getReceAddr()).append(",").append(order.getReceAddrRemark());
				form.setField("address", sb.toString());sb.setLength(0);
//				sb.append(i+1);
				while(sb.length() < 3){
					sb.insert(0, "0");
				}
//				sb.insert(0, order.getOrderCode());
				form.setField("packageCode", sb.toString());
				form.replacePushbuttonField("packageBarcodeImg2", PDFUtil.genPdfButton(form, "packageBarcodeImg2", BarcodeGeneratorUtil.genBarcode(sb.toString(), 800)));sb.setLength(0);
				
				stamper.setFormFlattening(true);
				stamper.close();
				page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
				pdfCopy.addPage(page);
//			}
			document.close();
//			order.setPrint(1);
//			sfcOrderDao.update(order);
		} catch (Exception e) {
//			logger.error(ExceptionUtil.getExceptionMessage(e));
		} 
	}
	
	public List<EasyuiCombobox> getHdlTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryHdlTypeByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(basLocation.getLocationhandling());
				combobox.setValue(basLocation.getLocationhandlingName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getUsgTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryUsgTypeByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(basLocation.getLocationusage());
				combobox.setValue(basLocation.getLocationusageName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getCatTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryCatTypeByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(basLocation.getLocationcategory());
				combobox.setValue(basLocation.getLocationcategoryName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getAttTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryAttTypeByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(basLocation.getLocationattribute());
				combobox.setValue(basLocation.getLocationattributeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getDmdTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryDmdTypeByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(basLocation.getDemand());
				combobox.setValue(basLocation.getDemandName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getPtzoneTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryPtzoneTypeByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(basLocation.getPutawayzone());
				combobox.setValue(basLocation.getPutawayzoneName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getPizoneTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasLocation> basLocationList = basLocationMybatisDao.queryPizoneTypeByAll();
		if(basLocationList != null && basLocationList.size() > 0){
			for(BasLocation basLocation : basLocationList){
				combobox = new EasyuiCombobox();
				combobox.setId(basLocation.getPickzone());
				combobox.setValue(basLocation.getPickzoneName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
}