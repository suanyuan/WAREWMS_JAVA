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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.wms.constant.Constant;
import com.wms.entity.FirstBusinessApply;
import com.wms.mybatis.dao.FirstBusinessApplyMybatisDao;
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
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasSkuQuery;
import com.wms.service.importdata.ImportSkuDataService;
import com.wms.utils.BarcodeGeneratorUtil;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.PDFUtil;
import com.wms.utils.ResourceUtil;
import com.wms.vo.BasSkuVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasSkuForm;

@Service("basSkuService")
public class BasSkuService extends BaseService {

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	@Autowired
	private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;

	@Autowired
	private ImportSkuDataService importSkuDataService;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//显示主页datagrid
	public EasyuiDatagrid<BasSkuVO> getPagedDatagrid(EasyuiDatagridPager pager, BasSkuQuery query) {
		EasyuiDatagrid<BasSkuVO> datagrid = new EasyuiDatagrid<BasSkuVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
//		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("addtime desc");
		System.out.println(query.getAddTimeStart()+"=====query.getAddTimeStart()====== query.getAddTimeStart()======"+query.getAddTimeEnd());
		List<BasSku> basSkuList = basSkuMybatisDao.queryByPageList(mybatisCriteria);

		BasSkuVO basSkuVO = null;
		List<BasSkuVO> basSkuVOList = new ArrayList<BasSkuVO>();

		for (BasSku basSku : basSkuList) {
			basSkuVO = new BasSkuVO();
			BeanUtils.copyProperties(basSku, basSkuVO);



			int num =firstBusinessApplyMybatisDao.selectSupplierNumByProductAndState(basSkuVO.getSku());
			basSkuVO.setSupplierNum(num);
			basSkuVO.setAddtime(simpleDateFormat.format(basSku.getAddtime()));
			basSkuVO.setEdittime(simpleDateFormat.format(basSku.getEdittime()));
			basSkuVOList.add(basSkuVO);
		}
		datagrid.setTotal((long) basSkuMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basSkuVOList);
		return datagrid;
	}
//显示主页datagrid
	public EasyuiDatagrid<BasSkuVO> getPagedDatagridByInvLot(EasyuiDatagridPager pager, BasSkuQuery query) {
		EasyuiDatagrid<BasSkuVO> datagrid = new EasyuiDatagrid<BasSkuVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("sku");
		List<BasSku> basSkuList = basSkuMybatisDao.queryByPageListByInvLot(mybatisCriteria);

		BasSkuVO basSkuVO = null;
		List<BasSkuVO> basSkuVOList = new ArrayList<BasSkuVO>();

		for (BasSku basSku : basSkuList) {
			basSkuVO = new BasSkuVO();
			BeanUtils.copyProperties(basSku, basSkuVO);
			basSkuVO.setAddtime(simpleDateFormat.format(basSku.getAddtime()));
			basSkuVO.setEdittime(simpleDateFormat.format(basSku.getEdittime()));
			basSkuVOList.add(basSkuVO);
		}
		datagrid.setTotal((long) basSkuMybatisDao.queryByCountByInvLot(mybatisCriteria));
		datagrid.setRows(basSkuVOList);
		return datagrid;
	}

	public Json addBasSku(BasSkuForm basSkuForm) throws Exception {
		Json json = new Json();
		Date today = new Date();
		BasSku basSku = new BasSku();
		StringBuilder resultMsg = new StringBuilder();
		BeanUtils.copyProperties(basSkuForm, basSku);
		
		this.validateSku(basSkuForm, resultMsg);// 验证商品是否存在

		if (resultMsg.length() == 0) {

			//
			basSku.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			basSku.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			basSku.setAddtime(today);
			basSku.setEdittime(today);
			//
			basSkuMybatisDao.add(basSku);
		}else{
			basSku.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			basSku.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			basSku.setAddtime(today);
			basSku.setEdittime(today);
			basSku.setActiveFlag(Constant.IS_USE_YES);
			basSku.setFirstop(Constant.CODE_CATALOG_FIRSTSTATE_PASS);
			basSkuMybatisDao.updateBySelective(basSku);
		}
//		else {
//			json.setSuccess(false);
//
//			json.setMsg(resultMsg.toString());
//			return json;
//		}
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}
	
	private void validateSku(BasSkuForm basSkuForm, StringBuilder resultMsg) {
		BasSkuQuery skuQuery = new BasSkuQuery();
		skuQuery.setCustomerid(basSkuForm.getCustomerid());
		skuQuery.setSku(basSkuForm.getSku());
		BasSku basSku = basSkuMybatisDao.queryById(skuQuery);
		if(basSku != null){
			resultMsg.append("客户代码：").append(basSkuForm.getCustomerid())
					 .append("，商品代码：").append(basSkuForm.getSku()).append("，重复").append(" ");
		}
	}

	public Json editBasSku(BasSkuForm basSkuForm) {
		Json json = new Json();
		Date today = new Date();
		BasSkuQuery skuQuery = new BasSkuQuery();
		skuQuery.setCustomerid(basSkuForm.getCustomerid());
		skuQuery.setSku(basSkuForm.getSku());
		BasSku basSku = basSkuMybatisDao.queryById(skuQuery);
		if(basSku == null){
			return Json.error("查询不到对应的产品");
		}
		BeanUtils.copyProperties(basSkuForm, basSku);

		basSku.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basSku.setEdittime(today);
		basSkuMybatisDao.updateBySelective(basSku);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json deleteBasSku(String customerid, String sku) {
		Json json = new Json();
		BasSkuQuery skuQuery = new BasSkuQuery();
		skuQuery.setCustomerid(customerid);
		skuQuery.setSku(sku);
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("customerid", customerid);
		map.put("sku", sku);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		BasSku basSku = basSkuMybatisDao.queryById(skuQuery);
		if(basSku != null){
			basSkuMybatisDao.basSkuCheck(map);
			String result = map.get("result").toString();
			if (result.equals("000")) {
				basSkuMybatisDao.delete(basSku);
			} else {
				json.setSuccess(false);
				json.setMsg(result);
				return json;
			}
		}
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public BasSku getSkuInfo(String customerid, String sku, BigDecimal qty) {
		BasSkuQuery skuQuery = new BasSkuQuery();
		skuQuery.setCustomerid(customerid);
		skuQuery.setSku(sku);
		skuQuery.setQty(qty);
		BasSku basSku = basSkuMybatisDao.queryBySkuInfo(skuQuery);
		return basSku;
	}

	public List<EasyuiCombobox> getBasSkuCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasSku> basSkuList = basSkuMybatisDao.queryByAll();
		if(basSkuList != null && basSkuList.size() > 0){
			for(BasSku basSku : basSkuList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basSku.getSku()));
				combobox.setValue(basSku.getDescrC());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public Json importData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile csvFile = mhsr.getFile("uploadData");
		if(csvFile != null && csvFile.getSize() > 0){
			json = importSkuDataService.importData(csvFile); 
		}	
		return json;
	}

	public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		if(excelFile != null && excelFile.getSize() > 0){
			json = importSkuDataService.importExcelData(excelFile); 
		}	
		return json;
	}
	
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("sku_template.xls"));
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
	
	public void exportPdf(HttpServletResponse response, String customerid, String sku) {
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
			
			BasSkuQuery skuQuery = new BasSkuQuery();
			skuQuery.setCustomerid(customerid);
			skuQuery.setSku(sku);
			BasSku basSku = basSkuMybatisDao.queryById(skuQuery);
			
			document = new Document(PDFUtil.getTemplate("crossdocking_package.pdf").getPageSize(1));
			PdfCopy pdfCopy = new PdfCopy(document, os);
			document.open();
//			for(int i = 0 ; i < order.getTotalNum() ; i++){
				baos = new ByteArrayOutputStream();
				stamper = new PdfStamper(PDFUtil.getTemplate("crossdocking_package.pdf"), baos);
				form = stamper.getAcroFields();
				
//				sb.append(order.getTotalNum()).append("/").append(i+1);
				form.setField("quantity", basSku.getAddwho());sb.setLength(0);
//				form.setField("createTime", DateUtil.format(order.getCreateTime()));
				form.setField("storeName", basSku.getCustomerid());
				form.setField("merchantName", basSku.getSku());
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


	public EasyuiDatagrid<BasSkuVO> getPagedDatagridSearch(EasyuiDatagridPager pager, BasSkuQuery query) {
		EasyuiDatagrid<BasSkuVO> datagrid = new EasyuiDatagrid<BasSkuVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
//		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		query.setActiveFlag(Constant.IS_USE_YES);
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		List<BasSku> basSkuList = basSkuMybatisDao.queryByPageList(mybatisCriteria);
		BasSkuVO basSkuVO = null;
		List<BasSkuVO> basSkuVOList = new ArrayList<BasSkuVO>();
		for (BasSku basSku : basSkuList) {
			basSkuVO = new BasSkuVO();
			BeanUtils.copyProperties(basSku, basSkuVO);
			basSkuVOList.add(basSkuVO);
		}
		datagrid.setTotal((long) basSkuMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basSkuVOList);
		return datagrid;
	}

	public BasSku getSkuInfo(String customerid, String sku) {
		Map<String,Object> param = new HashMap<>();
		param.put("customerid",customerid);
		param.put("sku",sku);
		BasSku basSku = basSkuMybatisDao.queryById(param);
		return basSku;
	}

	/**
	 * 根据sku查询
	 * @return
	 */
	public List<BasSku> queryBasSkuBySku(String registerNo){
		BasSku skuQuery = new BasSku();
		skuQuery.setReservedfield03(registerNo);
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(skuQuery);
		List<BasSku> basSkuList = basSkuMybatisDao.queryByList(mybatisCriteria);
		return basSkuList;
	}

	/**
	 * 查询sku列表
	 * @param sku
	 * @return
	 */
	public List<BasSku> getSkuListBySku(String sku) {
		Map<String,Object> param = new HashMap<>();
		param.put("sku",sku);
		param.put("activeFlag","1");
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(param);
		List<BasSku> basSku = basSkuMybatisDao.queryByList(mybatisCriteria);
		return basSku;
	}

	/**
	 * 查询产品的激活状态
	 * @param customerid 货主
	 * @param sku 产品代码
	 * @return
	 */
	public  BasSku selectBasSku(String customerid, String sku){
		BasSkuQuery basSkuQuery = new BasSkuQuery();
		basSkuQuery.setCustomerid(customerid);
		basSkuQuery.setSku(sku);
		BasSku basSku = basSkuMybatisDao.queryById(basSkuQuery);
		return basSku;
	}


	/**
	 * 修改激活状态
	 * @return
	 */
	public Json editActiveFlag(String customerid, String sku,String activeFlag){
		Json json = new Json();
		//根据activeflag 标记来判断是否激活状态
		if(activeFlag == "1" || activeFlag.equals("1")){//修改为未激活状态
			Date today = new Date();
			BasSku basSku = new BasSku();
			basSku.setCustomerid(customerid);
			basSku.setSku(sku);
			basSku.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			basSku.setEdittime(today);
			basSku.setActiveFlag(Constant.IS_USE_NO);
			basSkuMybatisDao.updateBySelective(basSku);
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
		}else{//修改为激活状态
			Date today = new Date();
			BasSku basSku = new BasSku();
			basSku.setCustomerid(customerid);
			basSku.setSku(sku);
			basSku.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			basSku.setEdittime(today);
			basSku.setActiveFlag("1");
			basSkuMybatisDao.updateBySelective(basSku);
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
		}
		return json;
	}

}