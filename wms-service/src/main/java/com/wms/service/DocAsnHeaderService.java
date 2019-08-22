package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.OrderDetailsForNormalMybatisDao;
import com.wms.mybatis.entity.pda.PdaDocAsnEndForm;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocAsnHeaderQuery;
import com.wms.query.OrderDetailsForNormalQuery;
import com.wms.result.PdaResult;
import com.wms.service.importdata.ImportAsnDataService;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnHeaderForm;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocAsnHeaderVO;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("docAsnHeaderService")
public class DocAsnHeaderService extends BaseService {

	@Autowired
	private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
	
	@Autowired
	private ImportAsnDataService importAsnDataService;
	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
	@Autowired
	private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
	@Autowired
	private DocPaService docPaService;

	public EasyuiDatagrid<DocAsnHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnHeaderQuery query) {
		EasyuiDatagrid<DocAsnHeaderVO> datagrid = new EasyuiDatagrid<DocAsnHeaderVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
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

	/**
	 * 判断客户单号是否重复
	 * @param customerNo
	 * @return
	 */
	public boolean checkIsRept(String customerNo){
		DocAsnHeaderQuery query = new DocAsnHeaderQuery();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		List<DocAsnHeader> list = docAsnHeaderMybatisDao.queryByList(criteria);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}





	public Json addDocAsnHeader(DocAsnHeaderForm docAsnHeaderForm) throws Exception {
		Json json = new Json();
		/*获取新的订单号*/
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		docAsnHeaderMybatisDao.getIdSequence(map);
		String resultCode = map.get("resultCode").toString();
		String resultNo = map.get("resultNo").toString();
        DocAsnHeader docAsnHeader = new DocAsnHeader();
        int flag =  docAsnHeaderMybatisDao.showAsnreference1(docAsnHeaderForm.getAsnreference1());
		if (resultCode.substring(0,3).equals("000") && flag == 0) {
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
			json.setMsg("客户单号重复！");
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
	
	public Json cancelDocAsnHeader(String asnnos) {
		Json json = new Json();
		StringBuilder message = new StringBuilder();
		if (StringUtil.isNotEmpty(asnnos)) {

		    String[] asnnoList = asnnos.split(",");
            for (String asnno : asnnoList) {

                if (StringUtil.isNotEmpty(asnno)) {

                    DocAsnHeaderQuery docAsnHeaderQuery = new DocAsnHeaderQuery();
                    docAsnHeaderQuery.setAsnno(asnno);
                    DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docAsnHeaderQuery);
                    if (docAsnHeader != null) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                        map.put("asnno", asnno);
                        map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
                        docAsnHeaderMybatisDao.cancel(map);
                        String result = map.get("result").toString();
                        if (result.substring(0, 3).equals("000")) {
                            message.append("取消成功：").append(asnno).append(";");
                        } else {
                            message.append("取消失败：").append(asnno).append("(").append(result).append(");");
                        }
                    }
                }
            }
            json.setSuccess(true);
            json.setMsg(message.toString());
        }else {

            json.setSuccess(false);
            json.setMsg("取消失败！(无预入库单号传入)");
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

	/**
	 * 引用入库
	 * @param orderno
	 * @param refOrderno
	 * @return
	 * @throws Exception
	 */
    public Json doRefIn(String orderno,String refOrderno) throws Exception {
		DocAsnHeader head = docAsnHeaderMybatisDao.queryById(orderno);
		if(head == null){
			return Json.error("查询不到对应的单据");
		}
		if(!head.getAsnstatus().equals("00")){
			return Json.error("只有新建状态的出库单才能引用入库");
		}
		DocAsnDetailQuery query = new DocAsnDetailQuery();
		query.setAsnno(orderno);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		List<DocAsnDetail> list = docAsnDetailsMybatisDao.queryByPageList(criteria);
		if(list == null || list.size() == 0){
			return Json.error("操作失败，入库单明细为空");
		}else{
			OrderDetailsForNormalQuery queryRef = new OrderDetailsForNormalQuery();
			queryRef.setOrderno(refOrderno);
			MybatisCriteria criteriaRef = new MybatisCriteria();
			criteriaRef.setCondition(queryRef);
			List<OrderDetailsForNormal> detailsForNormalsRef = orderDetailsForNormalMybatisDao.queryByPageList(criteriaRef);
			if(detailsForNormalsRef == null || detailsForNormalsRef.size() == 0){
				return Json.error("操作失败，引用出库单明细为空");
			}
			Map<String,OrderDetailsForNormal> map = new HashMap<>();
			for(OrderDetailsForNormal detail : detailsForNormalsRef){
				map.put(getKey(detail),detail);
			}

			List<String> arrList = new ArrayList<>();
			//判断是否明细正确
			for(DocAsnDetail d : list){
				OrderDetailsForNormal dRef = map.get(getAsnKey(d));
				if(dRef == null){
					return Json.error("操作失败，引用单据明细不匹配");
				}
				arrList.add(d.getAsnno());
			}

			Json result = docPaService.confirmReceiving(arrList.toString());
			if(!result.isSuccess()){
				return Json.error("确认收货失败");
			}else {
				head = docAsnHeaderMybatisDao.queryById(orderno);
				if(head!=null && head.getAsnstatus().equals("40")){
					closeDocAsnHeader(orderno);
					return Json.success("引用入库成功");
				}else {
					return Json.error("确认收货失败");
				}
			}
		}

	}

	private String getKey(OrderDetailsForNormal detail){
		/*return detail.getSku()+""+detail.getLotatt01()+""+detail.getLotatt02()+""+detail.getLotatt03()+""+detail.getLotatt04()
				+""+detail.getLotatt05()+""+detail.getLotatt06()+""+detail.getLotatt07()+""+detail.getLotatt08()
				+""+detail.getLotatt09()+""+detail.getLotatt10()+""+detail.getLotatt11()+""+detail.getLotatt12()
				+""+detail.getLotatt13();*/
		return detail.getSku()+detail.getLotatt01()+detail.getLotatt02()+detail.getLotatt04()
				+detail.getLotatt05()+detail.getLotatt06()+detail.getLotatt08()
				+detail.getLotatt09();
	}

	private String getAsnKey(DocAsnDetail detail){
    	/*return detail.getSku()+""+detail.getLotatt01()+""+detail.getLotatt02()+""+detail.getLotatt03()+""+detail.getLotatt04()
				+""+detail.getLotatt05()+""+detail.getLotatt06()+""+detail.getLotatt07()+""+detail.getLotatt08()
				+""+detail.getLotatt09()+""+detail.getLotatt10()+""+detail.getLotatt11()+""+detail.getLotatt12()
				+""+detail.getLotatt13();*/
		return detail.getSku()+detail.getLotatt01()+detail.getLotatt02()+detail.getLotatt04()
				+detail.getLotatt05()+detail.getLotatt06()+detail.getLotatt08()
				+detail.getLotatt09();
	}

}