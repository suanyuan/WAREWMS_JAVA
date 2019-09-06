package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.entity.InvLotAtt;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocAsnEndForm;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocAsnHeaderQuery;
import com.wms.query.OrderDetailsForNormalQuery;
import com.wms.result.AsnDetailResult;
import com.wms.result.PdaResult;
import com.wms.service.importdata.ImportAsnDataService;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnDetailForm;
import com.wms.vo.form.DocAsnHeaderForm;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaDocAsnHeaderVO;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.hibernate.Transaction;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("docAsnHeaderService")
public class DocAsnHeaderService extends BaseService {

	@Autowired
	private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
	
	@Autowired
	private ImportAsnDataService importAsnDataService;
	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
	@Autowired
	private DocPaService docPaService;
    @Autowired
    private InvLotAttService invLotAttService;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private DocAsnDetailService docAsnDetailService;
    @Autowired
    private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
    @Autowired
    private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;

	public EasyuiDatagrid<DocAsnHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnHeaderQuery query) {
		EasyuiDatagrid<DocAsnHeaderVO> datagrid = new EasyuiDatagrid<DocAsnHeaderVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("asnno desc");
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
	/* boolean checkIsRept(String customerNo){
		DocAsnHeaderQuery query = new DocAsnHeaderQuery();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		List<DocAsnHeader> list = docAsnHeaderMybatisDao.queryByList(criteria);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}*/

	public Json addDocAsnHeader(DocAsnHeaderForm docAsnHeaderForm) throws Exception {

		Json json = new Json();
        int flag =  docAsnHeaderMybatisDao.showAsnreference1(docAsnHeaderForm.getAsnreference1());
        if (flag != 0) {
            json.setSuccess(false);
            json.setMsg("此客户单号已存在！");
            return json;
        }

        /*获取新的订单号*/
        Map<String ,Object> map=new HashMap<String, Object>();
        map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        docAsnHeaderMybatisDao.getIdSequence(map);
        String resultCode = map.get("resultCode").toString();
        String resultNo = map.get("resultNo").toString();
        DocAsnHeader docAsnHeader = new DocAsnHeader();
		if (resultCode.substring(0,3).equals("000")) {
			BeanUtils.copyProperties(docAsnHeaderForm, docAsnHeader);
			docAsnHeader.setAsnno(resultNo);
			docAsnHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			docAsnHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			docAsnHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			docAsnHeaderMybatisDao.add(docAsnHeader);
            json.setSuccess(true);
			json.setMsg(resultNo);
			json.setObj(resultNo);
			return json;
		} else {
			json.setSuccess(false);
			json.setMsg("新增头档失败!");
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

		//如果是定向 || 引用订单，修改了单据类型之后需要修改明细中的库位数据
        //add by Gizmo 2019-08-30
		if (docAsnHeader.getAsntype().equals(DocAsnHeader.ASN_TYPE_DX) ||
        docAsnHeader.getAsntype().equals(DocAsnHeader.ASN_TYPE_YY)) {

		    MybatisCriteria mybatisCriteria = new MybatisCriteria();
		    DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		    docAsnDetailQuery.setAsnno(docAsnHeader.getAsnno());
		    mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docAsnDetailQuery));
		    List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByList(mybatisCriteria);
		    for (DocAsnDetail docAsnDetail : docAsnDetailList) {

		        docAsnDetail = docAsnDetailService.configDxLocation(docAsnDetail);
		        docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		        docAsnDetailsMybatisDao.update(docAsnDetail);
            }
        }
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}
	
	public Json closeDocAsnHeader(String asnnos) {
		Json json = new Json();

        StringBuilder message = new StringBuilder();
        if (StringUtil.isNotEmpty(asnnos)) {

            String[] asnnoList = asnnos.split(",");
            int count = 0;
            String lineBreak = "<br/>";
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
                        docAsnHeaderMybatisDao.close(map);
                        String result = map.get("result").toString();
                        message.append("[").append(asnno).append("]");
                        if (result.substring(0, 3).equals("000")) {
                            message.append(" 关单成功").append(";").append(lineBreak);
                        } else {
                            message.append(" 关单失败：").append(result).append(";").append(lineBreak);
                        }
                    }
                    if (count == asnnoList.length - 1) {
                        message = new StringBuilder(message.substring(0, message.length() - lineBreak.length()));
                    }
                }
                count ++;
            }
            json.setSuccess(true);
            json.setMsg(message.toString());
        }else {

            json.setSuccess(false);
            json.setMsg("关单失败！(无预入库单号传入)");
        }
        return json;

//		Map<String ,Object> map=new HashMap<String, Object>();
//		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//		map.put("asnno", id);
//		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
//		DocAsnHeaderQuery docAsnHeaderQuery = new DocAsnHeaderQuery();
//		docAsnHeaderQuery.setAsnno(id);
//		DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docAsnHeaderQuery);
//		if(docAsnHeader != null){
//			docAsnHeaderMybatisDao.close(map);
//			String result = map.get("result").toString();
//			if (result.substring(0,3).equals("000")) {
//				json.setSuccess(true);
//				json.setMsg("关单成功！");
//			} else {
//				json.setSuccess(false);
//				json.setMsg("关单失败！"+result);
//			}
//		}
//		return json;
	}
	
	public Json cancelDocAsnHeader(String asnnos) {
		Json json = new Json();
		StringBuilder message = new StringBuilder();
		if (StringUtil.isNotEmpty(asnnos)) {

		    String[] asnnoList = asnnos.split(",");
		    int count = 0;
		    String lineBreak = "<br/>";
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
                        message.append("[").append(asnno).append("]");
                        if (result.substring(0, 3).equals("000")) {
                            message.append(" 取消成功：").append(";").append(lineBreak);
                        } else {
                            message.append(" 取消失败：").append(result).append(";").append(lineBreak);
                        }
                    }
                    if (count == asnnoList.length - 1) {
                        message = new StringBuilder(message.substring(0, message.length() - lineBreak.length()));
                    }
                }
                count ++;
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

        form.setEditwho(form.getEditwho());
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


	/**
	 * 明细复用
	*/
    public  Json addDoDetailReuse(String generalNo,String detailAssno ,String customerid,String copyFlag) throws  Exception{
	    Json json = new Json();
	    DocAsnDetailForm details = new DocAsnDetailForm();//入库明细
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Integer index = 1 ;
	    /*
	     *  1:复用出库明细 -1 : 复用入库明细
	    */
	    if(!generalNo.equals("") && generalNo != null) {
			if (copyFlag.equals("1")) {
				OrderDetailsForNormalQuery normalQuery = new OrderDetailsForNormalQuery();
				normalQuery.setOrderno(generalNo);
				normalQuery.setCustomerid(customerid);

				mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(normalQuery));
				List<OrderDetailsForNormal> normals = orderDetailsForNormalMybatisDao.queryByPageList(mybatisCriteria);
				if (normals.size() > 0) {
					for (OrderDetailsForNormal orderDetailsForNormal : normals) {
						details.setAsnno(detailAssno);
						details.setCustomerid(orderDetailsForNormal.getCustomerid());
						details.setLinestatus("00");
						details.setSku(orderDetailsForNormal.getSku());

                        BasSku basSku = basSkuService.getSkuInfo(orderDetailsForNormal.getCustomerid(), orderDetailsForNormal.getSku());
						details.setSkudescrc(basSku.getDescrC());

						details.setExpectedqty(new BigDecimal(orderDetailsForNormal.getQtyordered()));
						details.setExpectedqtyEach(new BigDecimal(orderDetailsForNormal.getQtyorderedEach()));
						//收货数量
						details.setReceivedqty(BigDecimal.ZERO);//收货件数
						details.setReceivedqtyEach(BigDecimal.ZERO);

						details.setRejectedqty(BigDecimal.ZERO); //拒收件数
						details.setRejectedqtyEach(BigDecimal.ZERO);
						details.setPrereceivedqtyEach(BigDecimal.ZERO);//预收数量

						details.setReceivinglocation("");  //库位
						details.setUom(orderDetailsForNormal.getUom());//件
						details.setPackid(orderDetailsForNormal.getPackid());//包装

						details.setTotalcubic(new BigDecimal(orderDetailsForNormal.getCubic()));//总体积
						details.setTotalgrossweight(new BigDecimal(orderDetailsForNormal.getGrossweight()));//总重量
						details.setTotalnetweight(new BigDecimal(orderDetailsForNormal.getNetweight()));//总净重
						details.setTotalprice(new BigDecimal(orderDetailsForNormal.getPrice())); //总价
						details.setReserveFlag("1");
						details.setHoldrejectcode("OK");//冻结代码
						details.setHoldrejectreason("正常");

						details.setLotatt01(orderDetailsForNormal.getLotatt01());
						details.setLotatt02(orderDetailsForNormal.getLotatt02());
						details.setLotatt03(orderDetailsForNormal.getLotatt03());//取当天的
						details.setLotatt04(orderDetailsForNormal.getLotatt04());
						details.setLotatt05(orderDetailsForNormal.getLotatt05());
						details.setLotatt06(orderDetailsForNormal.getLotatt06());//出库单上可能没有
						details.setLotatt07(orderDetailsForNormal.getLotatt07());
						details.setLotatt08(orderDetailsForNormal.getLotatt08());//默认
						details.setLotatt09("ZC");
						details.setLotatt10("DJ");
						details.setLotatt11(orderDetailsForNormal.getLotatt11());
						details.setLotatt12(orderDetailsForNormal.getLotatt12());
						details.setLotatt13(orderDetailsForNormal.getLotatt13());
						details.setLotatt14(detailAssno);
						details.setLotatt15(orderDetailsForNormal.getLotatt15());

						docAsnDetailService.addDocAsnDetail(details);
//						docAsnDetailsMybatisDao.add(details);
					}
					json.setSuccess(true);
					json.setMsg("明细复用成功");
				} else {
					json.setSuccess(false);
					json.setMsg("没有明细数据—明细复用失败");
				}
			} else {
				DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
				docAsnDetailQuery.setCustomerid(customerid);
				docAsnDetailQuery.setAsnno(generalNo);

				mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docAsnDetailQuery));
				List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByList(mybatisCriteria);
				if (docAsnDetailList.size() > 0) {
					for (DocAsnDetail docAsnDetail : docAsnDetailList) {
                        BeanUtils.copyProperties(docAsnDetail, details);

                        details.setAsnno(detailAssno);
                        details.setAsnlineno(index);
                        details.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
                        details.setAddtime(new Date());

                        details.setLinestatus("00");
                        details.setReserveFlag("1");
                        details.setHoldrejectcode("OK");//冻结代码
                        details.setHoldrejectreason("正常");
                        //收货数量
                        details.setReceivedqty(BigDecimal.ZERO);//收货件数
                        details.setReceivedqtyEach(BigDecimal.ZERO);

                        details.setRejectedqty(BigDecimal.ZERO); //拒收件数
                        details.setRejectedqtyEach(BigDecimal.ZERO);
                        details.setPrereceivedqtyEach(BigDecimal.ZERO);//预收数量

                        details.setLotatt09("ZC");
                        details.setLotatt10("DJ");
                        details.setLotatt14(detailAssno);

                        index += 1;
                        docAsnDetailsMybatisDao.add(details);
					}
					json.setSuccess(true);
					json.setMsg("明细复用成功");
				} else {
					json.setSuccess(false);
					json.setMsg("明细复用失败-(该明细不存在或该货主下没有此明细)");
				}
			}
		}else{
			json.setSuccess(false);
			json.setMsg("请填写编号");
		}
		return json;
	}

	public Json qlDetails(String generalNo){
    	Json json = new Json();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		DocAsnDetailQuery DetailQuery =new DocAsnDetailQuery();
		DetailQuery.setAsnno(generalNo);

		mybatisCriteria.setCondition(DetailQuery);
		long numLong =	docAsnDetailsMybatisDao.queryByCount(mybatisCriteria);
		if(numLong > 0 ){
			json.setSuccess(true);
		}else{
			json.setSuccess(false);
		}

    	return json;
	}

    /**
     * 引用新增
     * @param orderno 出库单号
     * @return ~
     */
	public Json quoteDocOrder(String orderno) {

        Json json = new Json();

        try {

            OrderHeaderForNormal docOrderHeader = orderHeaderForNormalMybatisDao.queryById(orderno);
            if (docOrderHeader == null) {

                json.setSuccess(false);
                json.setMsg("查无引用的出库单头档数据");
                return json;
            }

            MybatisCriteria orderDetailsCriteria = new MybatisCriteria();
            OrderDetailsForNormalQuery orderDetailQuery = new OrderDetailsForNormalQuery();
            orderDetailQuery.setOrderno(orderno);
            orderDetailsCriteria.setCondition(BeanConvertUtil.bean2Map(orderDetailQuery));
            List<OrderDetailsForNormal> docOrderDetailList = orderDetailsForNormalMybatisDao.queryByPageList(orderDetailsCriteria);
            if (docOrderDetailList.size() == 0) {

                json.setSuccess(false);
                json.setMsg("查无引用的出库单明细数据");
                return json;
            }

            //生成预入库头档
            DocAsnHeaderForm asnHeaderForm = new DocAsnHeaderForm();
            asnHeaderForm.setCustomerid(docOrderHeader.getCustomerid());
            asnHeaderForm.setAsnreference1(docOrderHeader.getSoreference1());
//        asnHeaderForm.setAsnreference2(docOrderHeader.getSoreference2()); 采购单号需用户手填
            asnHeaderForm.setExpectedarrivetime1(new Date());
            asnHeaderForm.setAsnstatus("00");
            asnHeaderForm.setAsntype("PR");
            asnHeaderForm.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
            asnHeaderForm.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            json =  addDocAsnHeader(asnHeaderForm);
            if (!json.isSuccess()) {
                return json;
            }
            String asnno = (String) json.getObj();//

            //
            int asnlineno = 1;
            for (OrderDetailsForNormal docOrderDetail : docOrderDetailList) {

                DocAsnDetailForm asnDetailForm = new DocAsnDetailForm();
                asnDetailForm.setAsnno(asnno);
                asnDetailForm.setAsnlineno(asnlineno);
                asnDetailForm.setCustomerid(docOrderDetail.getCustomerid());
                asnDetailForm.setLinestatus("00");
                asnDetailForm.setSku(docOrderDetail.getSku());
                asnDetailForm.setSkudescrc(docOrderDetail.getSkuName());

                asnDetailForm.setExpectedqty(new BigDecimal(docOrderDetail.getQtyordered()));
                asnDetailForm.setExpectedqtyEach(new BigDecimal(docOrderDetail.getQtyorderedEach()));
                //收货数量
                asnDetailForm.setReceivedqty(BigDecimal.ZERO);//收货件数
                asnDetailForm.setReceivedqtyEach(BigDecimal.ZERO);

                asnDetailForm.setRejectedqty(BigDecimal.ZERO); //拒收件数
                asnDetailForm.setRejectedqtyEach(BigDecimal.ZERO);
                asnDetailForm.setPrereceivedqtyEach(BigDecimal.ZERO);//预收数量

                asnDetailForm.setReceivinglocation(docOrderDetail.getLocation());  //库位
                asnDetailForm.setUom(docOrderDetail.getUom());//件
                asnDetailForm.setPackid(docOrderDetail.getPackid());//包装

                asnDetailForm.setTotalcubic(new BigDecimal(docOrderDetail.getCubic()));//总体积
                asnDetailForm.setTotalgrossweight(new BigDecimal(docOrderDetail.getGrossweight()));//总重量
                asnDetailForm.setTotalnetweight(new BigDecimal(docOrderDetail.getNetweight()));//总净重
                asnDetailForm.setTotalprice(new BigDecimal(docOrderDetail.getPrice())); //总价
                asnDetailForm.setReserveFlag("1");
                asnDetailForm.setHoldrejectcode("OK");//冻结代码
                asnDetailForm.setHoldrejectreason("正常");

                asnDetailForm.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
                asnDetailForm.setAddtime(new Date());

                asnDetailForm.setLotatt01(docOrderDetail.getLotatt01());
                asnDetailForm.setLotatt02(docOrderDetail.getLotatt02());
                asnDetailForm.setLotatt03(docOrderDetail.getLotatt03());
                asnDetailForm.setLotatt04(docOrderDetail.getLotatt04());
                asnDetailForm.setLotatt05(docOrderDetail.getLotatt05());
                asnDetailForm.setLotatt06(docOrderDetail.getLotatt06());
                asnDetailForm.setLotatt07(docOrderDetail.getLotatt07());
                asnDetailForm.setLotatt08(docOrderDetail.getLotatt08());
                asnDetailForm.setLotatt09("ZC");
                asnDetailForm.setLotatt10("DJ");
                asnDetailForm.setLotatt11(docOrderDetail.getLotatt11());
                asnDetailForm.setLotatt12(docOrderDetail.getLotatt12());
                asnDetailForm.setLotatt13(docOrderDetail.getLotatt13());
                asnDetailForm.setLotatt14(asnno);
                asnDetailForm.setLotatt15(docOrderDetail.getLotatt15());
                json = docAsnDetailService.addDocAsnDetail(asnDetailForm);//这里面还有逻辑
                if (!json.isSuccess()) {
                    throw new Exception(json.getMsg());
                }
                asnlineno ++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            json.setSuccess(false);
            json.setMsg(e.getMessage());
            return json;
        }
        json.setSuccess(true);
        json.setMsg("引用出库单成功");
        return json;
    }



	public EasyuiDatagrid<AsnDetailResult> getAsnDetail(String asnNo){
		EasyuiDatagrid<AsnDetailResult> datagrid = new EasyuiDatagrid<>();
		List<AsnDetailResult> asnDetailResultList = docAsnHeaderMybatisDao.queryAsnDetailResult(asnNo);
		datagrid.setTotal((long) asnDetailResultList.size());
		datagrid.setRows(asnDetailResultList);
		return datagrid;
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