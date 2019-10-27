package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocAsnEndForm;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.BasSkuQuery;
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
import com.wms.vo.DocAsnDetailVO;
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
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    private DocAsnCertificateMybatisDao docAsnCertificateMybatisDao;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private DocAsnDetailService docAsnDetailService;
    @Autowired
    private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
    @Autowired
    private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
    @Autowired
    private BasCustomerService basCustomerService;
    @Autowired
    private BasCodesService basCodesService;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;

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
            //判断冷链标记
            if (docAsnHeader.getReservedfield07() != null) {
                switch (docAsnHeader.getReservedfield07()) {
                    case "FLL":
                        docAsnHeaderVO.setColdTag("非冷链");
                        break;
                    case "LD":
                        docAsnHeaderVO.setColdTag("冷冻");
                        break;
                    case "LC":
                        docAsnHeaderVO.setColdTag("冷藏");
                        break;
                    default:
                        docAsnHeaderVO.setColdTag("非冷链");
                        break;
                }
            }
			/*DocAsnDetailQuery query1 = new DocAsnDetailQuery();
			query1.setAsnno(docAsnHeader.getAsnno());
			query1.setAsnlineno(1);
			DocAsnDetailVO detailVO = docAsnDetailService.queryDocAsnDetail(query1);
			if(detailVO!=null){
				BasSku sku = basSkuService.getSkuInfo(detailVO.getCustomerid(),detailVO.getSku());
				if(sku!=null && sku.getReservedfield07()!=null){
					switch (sku.getReservedfield07()){
						case "FLL":docAsnHeaderVO.setColdTag("非冷链");break;
						case "LD":docAsnHeaderVO.setColdTag("冷冻");break;
						case "LC":docAsnHeaderVO.setColdTag("冷藏");break;
						default:docAsnHeaderVO.setColdTag("非冷链");break;
					}

				}
			}*/
            docAsnHeaderVOList.add(docAsnHeaderVO);
        }
        datagrid.setTotal((long) docAsnHeaderMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docAsnHeaderVOList);
        return datagrid;
    }

    /**
     * 判断客户单号是否重复
     *
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
        int flag = docAsnHeaderMybatisDao.showAsnreference1(docAsnHeaderForm.getAsnreference1());
        if (flag != 0) {
            json.setSuccess(false);
            json.setMsg("此客户单号已存在！");
            return json;
        }

        /*获取新的订单号*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        docAsnHeaderMybatisDao.getIdSequence(map);
        String resultCode = map.get("resultCode").toString();
        String resultNo = map.get("resultNo").toString();
        DocAsnHeader docAsnHeader = new DocAsnHeader();
        if (resultCode.substring(0, 3).equals("000")) {
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

        if (!docAsnHeader.getAsnstatus().equals("00") &&
                !docAsnHeader.getSupplierid().equals(docAsnHeaderForm.getSupplierid())) {

            json.setSuccess(false);
            json.setMsg("只有订单创建状态下才可修改供应商");
            return json;
        }

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
        docAsnDetailQuery.setAsnno(docAsnHeader.getAsnno());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docAsnDetailQuery));
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByList(mybatisCriteria);
        //批量修改明细中的供应商
        //add by Gizmo 2019-09-08
        if (!docAsnHeader.getSupplierid().equals(docAsnHeaderForm.getSupplierid())) {

            for (DocAsnDetail docAsnDetail : docAsnDetailList) {

                docAsnDetail.setLotatt08(docAsnHeader.getSupplierid());
                docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
                docAsnDetailsMybatisDao.update(docAsnDetail);
            }
        }

        BeanUtils.copyProperties(docAsnHeaderForm, docAsnHeader);
        docAsnHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
        docAsnHeaderMybatisDao.update(docAsnHeader);

        //如果是定向 || 引用订单，修改了单据类型之后需要修改明细中的库位数据
        //add by Gizmo 2019-08-30
        if (docAsnHeader.getAsntype().equals(DocAsnHeader.ASN_TYPE_DX) ||
                docAsnHeader.getAsntype().equals(DocAsnHeader.ASN_TYPE_YY)) {

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

    /**
     * 检查入库单号下面是否有没有导入的质量合格证
     */
    private Json checkAsnCertification(String asnno) {

        if (StringUtil.isEmpty(asnno)) return Json.error("未传入入库单号");

        Json json = new Json();
        json.setSuccess(true);
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByAsnNo(asnno);
        StringBuilder message = new StringBuilder();
        for (DocAsnDetail docAsnDetail : docAsnDetailList) {

            BasSku basSku = basSkuService.getSkuInfo(docAsnDetail.getCustomerid(), docAsnDetail.getSku());
            if (null == basSku) {//没有产品信息，一般不可能

                json.setSuccess(false);
                message.append(" ").
                        append("[单号]:").append(docAsnDetail.getAsnno()).
                        append(" [明细行号]:").append(docAsnDetail.getAsnlineno()).
                        append(" [产品代码]:").append(docAsnDetail.getSku()).
                        append(" 查无产品档案数据");
            } else if (StringUtil.fixNull(basSku.getSkuGroup8()).equals("1")) {//产品有质量合格证

                if (StringUtil.isEmpty(docAsnDetail.getLotatt04())) {//质量合格证是有对应的生产批号的

                    json.setSuccess(false);
                    message.append(" ").
                            append("[单号]:").append(docAsnDetail.getAsnno()).
                            append(" [明细行号]:").append(docAsnDetail.getAsnlineno()).
                            append(" [产品代码]:").append(docAsnDetail.getSku()).
                            append(" 数据错误，此产品有质量合格证，但入库数据未包含生产批号，请联系管理员");
                } else {

                    DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.queryBylotatt04(docAsnDetail.getCustomerid(), docAsnDetail.getSku(), docAsnDetail.getLotatt04());
                    if (docAsnCertificate == null) {

                        json.setSuccess(false);
                        message.append(" ").
                                append("[单号]:").append(docAsnDetail.getAsnno()).
                                append(" [明细行号]:").append(docAsnDetail.getAsnlineno()).
                                append(" [产品代码]:").append(docAsnDetail.getSku()).
                                append(" 生产批号(").append(docAsnDetail.getLotatt04()).
                                append(")，未导入质量合格证;");
                    }
                }
            }
        }

        json.setMsg(message.toString());
        return json;
    }

    /**
     * 在关闭订单之前，1,是否导入质量合格证 2，判断订单是否部分收货
     */
    public Json checkCloseAsn(String asnnos) {

        Json json = new Json();
        StringBuilder message = new StringBuilder();
        if (StringUtil.isNotEmpty(asnnos)) {

            String[] asnnoList = asnnos.split(",");
            List<String> partReceivedAsns = new ArrayList<>();//部分收货入库的预入库单号
            for (String asnno : asnnoList) {

                //是否导入质量合格证
                json = checkAsnCertification(asnno);
                if (!json.isSuccess()) return json;

                DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(asnno);
                List<DocAsnDetail> unfinishedDetailList = docAsnDetailsMybatisDao.queryPartReceivedAsn(asnno);
                if (unfinishedDetailList.size() > 0 && docAsnHeader.getAsnstatus().equals("70")) {//表示有部分收货入库的预期到货通知明细,前提是完全验收的单子
                    partReceivedAsns.add(asnno);
                }
            }
            if (partReceivedAsns.size() > 1 || (partReceivedAsns.size() == 1 && asnnoList.length > 1)) {//asnnos中有多个部分收货的需要单条操作

                message.append("请逐条关闭部分收货入库的通知单;").append(partReceivedAsns.toString());
                json.setSuccess(false);
            } else if (partReceivedAsns.size() == 1) {

                String partAsnno = partReceivedAsns.get(0);
                message.append("[").append(partAsnno).append("] 此单部分收货入库，");
                json.setSuccess(true);
            } else {

                json.setSuccess(true);
            }
            json.setMsg(message.toString());
        } else {

            json.setSuccess(false);
            json.setMsg("关单失败！(无预入库单号传入)");
        }
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
                count++;
            }
            json.setSuccess(true);
            json.setMsg(message.toString());
        } else {

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
                count++;
            }
            json.setSuccess(true);
            json.setMsg(message.toString());
        } else {

            json.setSuccess(false);
            json.setMsg("取消失败！(无预入库单号传入)");
        }
        return json;
    }

    public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
        Json json = null;
        MultipartFile excelFile = mhsr.getFile("uploadData");
        if (excelFile != null && excelFile.getSize() > 0) {
            json = importAsnDataService.importExcelData(excelFile);
        }
        return json;
    }

    public void exportTemplate(HttpServletResponse response, String token) {
        try (OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
            File file = new File(ResourceUtil.getImportRootPath("asn_template.xls"));
            response.reset();
            Cookie cookie = new Cookie("downloadToken", token);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            response.setContentType(ContentTypeEnum.stream.getContentType());
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
            response.addHeader("Content-Length", "" + file.length());

            try (InputStream fis = new BufferedInputStream(new FileInputStream(file))) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                toClient.write(buffer);
                toClient.flush();
            } catch (IOException ex) {
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
        if (docAsnHeaderList != null && docAsnHeaderList.size() > 0) {
            for (DocAsnHeader docAsnHeader : docAsnHeaderList) {
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
        if (docAsnHeaderList != null && docAsnHeaderList.size() > 0) {
            for (DocAsnHeader docAsnHeader : docAsnHeaderList) {
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
        if (docAsnHeaderList != null && docAsnHeaderList.size() > 0) {
            for (DocAsnHeader docAsnHeader : docAsnHeaderList) {
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
     *
     * @param form 分页
     * @return ~
     */
    public List<PdaDocAsnHeaderVO> queryUndoneList(PageForm form) {

        List<DocAsnHeader> docAsnHeaderList = docAsnHeaderMybatisDao.queryUndoneList(form.getStart(), form.getPageSize());
        List<PdaDocAsnHeaderVO> pdaDocAsnHeaderVOList = new ArrayList<>();
        PdaDocAsnHeaderVO pdaDocAsnHeaderVO;
        for (DocAsnHeader docAsnHeader : docAsnHeaderList) {

            pdaDocAsnHeaderVO = new PdaDocAsnHeaderVO();
            BeanUtils.copyProperties(docAsnHeader, pdaDocAsnHeaderVO);
            pdaDocAsnHeaderVOList.add(pdaDocAsnHeaderVO);
        }
        return pdaDocAsnHeaderVOList;
    }

    /**
     * 通过asnno查询header
     *
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
     *
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
     *
     * @param orderno
     * @param refOrderno
     * @return
     * @throws Exception
     */
    public Json doRefIn(String orderno, String refOrderno) throws Exception {
        DocAsnHeader head = docAsnHeaderMybatisDao.queryById(orderno);
        if (head == null) {
            return Json.error("查询不到对应的单据");
        }
        if (!head.getAsnstatus().equals("00")) {
            return Json.error("只有新建状态的出库单才能引用入库");
        }
        DocAsnDetailQuery query = new DocAsnDetailQuery();
        query.setAsnno(orderno);
        MybatisCriteria criteria = new MybatisCriteria();
        criteria.setCondition(query);
        List<DocAsnDetail> list = docAsnDetailsMybatisDao.queryByPageList(criteria);
        if (list == null || list.size() == 0) {
            return Json.error("操作失败，入库单明细为空");
        } else {
            OrderDetailsForNormalQuery queryRef = new OrderDetailsForNormalQuery();
            queryRef.setOrderno(refOrderno);
            MybatisCriteria criteriaRef = new MybatisCriteria();
            criteriaRef.setCondition(queryRef);
            List<OrderDetailsForNormal> detailsForNormalsRef = orderDetailsForNormalMybatisDao.queryByPageList(criteriaRef);
            if (detailsForNormalsRef == null || detailsForNormalsRef.size() == 0) {
                return Json.error("操作失败，引用出库单明细为空");
            }
            Map<String, OrderDetailsForNormal> map = new HashMap<>();
            for (OrderDetailsForNormal detail : detailsForNormalsRef) {
                map.put(getKey(detail), detail);
            }

            List<String> arrList = new ArrayList<>();
            //判断是否明细正确
            for (DocAsnDetail d : list) {
                OrderDetailsForNormal dRef = map.get(getAsnKey(d));
                if (dRef == null) {
                    return Json.error("操作失败，引用单据明细不匹配");
                }
                arrList.add(d.getAsnno());
            }

            Json result = docPaService.confirmReceiving(arrList.toString());
            if (!result.isSuccess()) {
                return Json.error("确认收货失败");
            } else {
                head = docAsnHeaderMybatisDao.queryById(orderno);
                if (head != null && head.getAsnstatus().equals("40")) {
                    closeDocAsnHeader(orderno);
                    return Json.success("引用入库成功");
                } else {
                    return Json.error("确认收货失败");
                }
            }
        }

    }


    /**
     * 明细复用
     */
    public Json addDoDetailReuse(String generalNo, String detailAssno, String customerid, String copyFlag) throws Exception {
        Json json = new Json();
        DocAsnDetailForm details = new DocAsnDetailForm();//入库明细
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Integer index = 1;
        /*
         *  1:复用出库明细 -1 : 复用入库明细
         */
        if (!generalNo.equals("") && generalNo != null) {
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


                        Json verJson = docAsnDetailService.addDocAsnDetail(details);
                        if (!verJson.isSuccess()) {
                            json.setSuccess(false);
                            json.setMsg(verJson.getMsg());
                        } else {
                            json.setSuccess(true);
                            json.setMsg("明细复用成功");
                        }
//						docAsnDetailsMybatisDao.add(details);
                    }

                } else {
                    json.setSuccess(false);
                    json.setMsg("明细复用失败-(该明细不存在或该货主下没有此明细)");
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
        } else {
            json.setSuccess(false);
            json.setMsg("请填写编号");
        }
        return json;
    }

    public Json qlDetails(String generalNo) {
        Json json = new Json();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        DocAsnDetailQuery DetailQuery = new DocAsnDetailQuery();
        DetailQuery.setAsnno(generalNo);

        mybatisCriteria.setCondition(DetailQuery);
        long numLong = docAsnDetailsMybatisDao.queryByCount(mybatisCriteria);
        if (numLong > 0) {
            json.setSuccess(true);
        } else {
            json.setSuccess(false);
        }

        return json;
    }

    /**
     * 引用新增
     *
     * @param orderno 出库单号
     * @return ~
     */
    public Json quoteDocOrder(String orderno, String customerId, String supplierId) {

        Json json = new Json();

        try {

            BasCustomer basCustomer = basCustomerService.selectCustomerById(customerId, Constant.CODE_CUS_TYP_OW);
            if (basCustomer == null) {
                return Json.error("查询不到有效的货主");
            }

            BasCustomer basSuppler = basCustomerService.selectCustomerById(supplierId, Constant.CODE_CUS_TYP_VE);
            if (basSuppler == null) {
                return Json.error("查询不到有效的供应商");
            }

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
            asnHeaderForm.setCustomerid(customerId);
            asnHeaderForm.setAsnreference1(docOrderHeader.getSoreference1());
//        asnHeaderForm.setAsnreference2(docOrderHeader.getSoreference2()); 采购单号需用户手填
            asnHeaderForm.setExpectedarrivetime1(new Date());
            asnHeaderForm.setAsnstatus("00");
            asnHeaderForm.setAsntype("PR");
            asnHeaderForm.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
            asnHeaderForm.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            asnHeaderForm.setSupplierid(supplierId);
            json = addDocAsnHeader(asnHeaderForm);
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
                asnDetailForm.setCustomerid(customerId);
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
                asnlineno++;
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


    public EasyuiDatagrid<AsnDetailResult> getAsnDetail(String asnNo) {
        EasyuiDatagrid<AsnDetailResult> datagrid = new EasyuiDatagrid<>();
        List<AsnDetailResult> asnDetailResultList = docAsnHeaderMybatisDao.queryAsnDetailResult(asnNo);
        datagrid.setTotal((long) asnDetailResultList.size());
        datagrid.setRows(asnDetailResultList);
        return datagrid;
    }

    public Json deleteDocAsn(String asnnos) {
        Json json = new Json();

        if (StringUtil.isEmpty(asnnos)) {
            return Json.error("请选择要删除的预入库通知单");
        }

        String[] noarr = asnnos.split(",");
        StringBuilder resultMsg = new StringBuilder();
        for (String asnno : noarr) {

            DocAsnHeaderQuery docAsnHeaderQuery = new DocAsnHeaderQuery();
            docAsnHeaderQuery.setAsnno(asnno);
            DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docAsnHeaderQuery);
            resultMsg.append(asnno);

            if (docAsnHeader != null) {
                if (docAsnHeader.getAsnstatus().equals("00") || docAsnHeader.getAsnstatus().equals("90")) {
                    if (docAsnHeader.getAddwho().equals("EDI")) {
                        json.setSuccess(false);
                        resultMsg.append(": 接口订单,不可删除!").append("</br>");
                    } else {
                        docAsnHeaderMybatisDao.delete(docAsnHeader);
                        docAsnDetailsMybatisDao.deleteByHead(docAsnHeader.getAsnno());
                        resultMsg.append(": 删除成功;").append("</br>");
                    }
                } else {
                    json.setSuccess(false);
                    resultMsg.append(": 只有订单创建和订单取消状态才可删除!").append("</br>");
                }
            } else {
                json.setSuccess(false);
                resultMsg.append(": 订单不存在!").append("</br>");
            }
            json.setMsg(resultMsg.toString());
        }
        return json;
    }


    public List<DocAsnHeader> printTaskList(String asnno) {
        String[] noarr = asnno.split(",");
        Double expectedqtySum = 0.0;
        Double expectedqtyEachSum = 0.0;
        List<DocAsnHeader> docAsnHeaderList = new ArrayList<DocAsnHeader>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        DocAsnHeader docAsnHeader = new DocAsnHeader();
        docAsnHeader.setHeaderTakedetls(new ArrayList<DocAsnDetail>());
        DocAsnDetail docAsnDetailQuery = new DocAsnDetail();
        for (String strAssno : noarr) {
            System.out.println(strAssno);
            docAsnDetailQuery.setAsnno(strAssno);
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docAsnDetailQuery));
            List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByList(mybatisCriteria);
            List<EasyuiCombobox> easyuiComboboxListUom = basCodesService.getBy(Constant.CODE_CATALOG_UOM);//查询单位
            for (DocAsnDetail docAsnDetail1 : docAsnDetailList) {
                docAsnDetail1.setExpectedqty(docAsnDetail1.getExpectedqty().setScale(1, RoundingMode.HALF_UP));
                docAsnDetail1.setExpectedqtyEach(docAsnDetail1.getExpectedqtyEach().setScale(1, RoundingMode.HALF_UP));
                docAsnDetail1.setReceivedqty(null);
                //单位
                for (EasyuiCombobox easyuiCombobox : easyuiComboboxListUom) {
                    if (easyuiCombobox.getId().equals(docAsnDetail1.getUom())) {
                        docAsnDetail1.setUom(easyuiCombobox.getValue());
                    }
                }
                //合计 件数
                expectedqtySum += docAsnDetail1.getExpectedqty().doubleValue();
                BigDecimal bigDecimalExpectedqrySum = new BigDecimal(expectedqtySum);
                docAsnDetail1.setExpectedqtySum(bigDecimalExpectedqrySum.setScale(1, RoundingMode.HALF_UP));
                // 数量
                expectedqtyEachSum += docAsnDetail1.getExpectedqtyEach().doubleValue();
                docAsnDetail1.setExpectedqtyEachSum(expectedqtyEachSum);
                //规格
                Map<String, Object> skuMap = new HashMap<String, Object>();
                skuMap.put("customerid", docAsnDetail1.getCustomerid());
                skuMap.put("sku", docAsnDetail1.getSku());
                BasSku basSku = basSkuMybatisDao.queryById(skuMap);
                if (basSku != null) {
                    //产品名称
                    docAsnDetail1.setSkudescrc(basSku.getDescrC());
                    docAsnDetail1.setDescre(basSku.getDescrE());
                } else {
                    docAsnDetail1.setDescre(null);
                }

                docAsnHeader.getHeaderTakedetls().add(docAsnDetail1);
            }
            DocAsnHeader docAsnHeaderQuery = new DocAsnHeader();
            docAsnHeaderQuery.setAsnno(asnno);
            MybatisCriteria mybatisCriteriaHeader = new MybatisCriteria();
            mybatisCriteriaHeader.setCondition(BeanConvertUtil.bean2Map(docAsnHeaderQuery));
            List<DocAsnHeader> docAsnHeaderListQuery = docAsnHeaderMybatisDao.queryAsnno(mybatisCriteriaHeader);
            for (DocAsnHeader docAsnHeader1 : docAsnHeaderListQuery) {
                //BeanUtils.copyProperties(docAsnHeader1);
                docAsnHeader.setAsnno(docAsnHeader1.getAsnno());
                docAsnHeader.setExpectedarrivetime1(docAsnHeader1.getExpectedarrivetime1());
                docAsnHeader.setWarehouseid(docAsnHeader1.getWarehouseid());
                BasCustomer basCustomer = basCustomerMybatisDao.queryByIdType(docAsnHeader1.getCustomerid(), Constant.CODE_CUS_TYP_OW);//货主
                if (basCustomer != null) {
                    docAsnHeader.setCustomerIdRef(basCustomer.getDescrC());
                } else {
                    docAsnHeader.setCustomerIdRef(" ");

                }
                BasCustomer basCustomer1 = basCustomerMybatisDao.queryByIdType(docAsnHeader1.getSupplierid(), Constant.CODE_CUS_TYP_VE);//供应商
                if (basCustomer1 != null) {
                    docAsnHeader.setSupplierIdRef(basCustomer1.getDescrC());
                } else {
                    docAsnHeader.setSupplierIdRef("");
                }
                docAsnHeader.setColdTag(docAsnHeader1.getColdTag());
            }
            docAsnHeaderList.add(docAsnHeader);
        }
        return docAsnHeaderList;
    }

    public List<DocAsnHeader> printTaskResult(String asnno) {
        Double expectedqtySum = 0.0;
        Double expectedqtyEachSum = 0.0;
        List<DocAsnHeader> docAsnHeaderList = new ArrayList<DocAsnHeader>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        DocAsnHeader docAsnHeader = new DocAsnHeader();
        docAsnHeader.setHeaderTakedetls(new ArrayList<DocAsnDetail>());
        DocAsnDetail docAsnDetailQuery = new DocAsnDetail();
        docAsnDetailQuery.setAsnno(asnno);
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docAsnDetailQuery));
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByList(mybatisCriteria);
        List<EasyuiCombobox> easyuiComboboxListUom = basCodesService.getBy(Constant.CODE_CATALOG_UOM);//查询单位
        for (DocAsnDetail docAsnDetail1 : docAsnDetailList) {
            docAsnDetail1.setExpectedqty(docAsnDetail1.getExpectedqty().setScale(1, RoundingMode.HALF_UP));
            docAsnDetail1.setExpectedqtyEach(docAsnDetail1.getExpectedqtyEach().setScale(1, RoundingMode.HALF_UP));
            docAsnDetail1.setReceivedqty(docAsnDetail1.getReceivedqty().setScale(1, RoundingMode.HALF_UP));
            //单位
            for (EasyuiCombobox easyuiCombobox : easyuiComboboxListUom) {
                if (easyuiCombobox.getId().equals(docAsnDetail1.getUom())) {
                    docAsnDetail1.setUom(easyuiCombobox.getValue());
                }
            }
            //合计 件数
            expectedqtySum += docAsnDetail1.getExpectedqty().doubleValue();
            BigDecimal bigDecimalExpectedqrySum = new BigDecimal(expectedqtySum);
            docAsnDetail1.setExpectedqtySum(bigDecimalExpectedqrySum.setScale(1, RoundingMode.HALF_UP));
            // 数量
            expectedqtyEachSum += docAsnDetail1.getExpectedqtyEach().doubleValue();
            docAsnDetail1.setExpectedqtyEachSum(expectedqtyEachSum);
            //规格
            Map<String, Object> skuMap = new HashMap<String, Object>();
            skuMap.put("customerid", docAsnDetail1.getCustomerid());
            skuMap.put("sku", docAsnDetail1.getSku());
            BasSku basSku = basSkuMybatisDao.queryById(skuMap);
            if (basSku != null) {
                docAsnDetail1.setDescre(basSku.getDescrE());
            } else {
                docAsnDetail1.setDescre(null);

            }

            docAsnHeader.getHeaderTakedetls().add(docAsnDetail1);
        }
        DocAsnHeader docAsnHeaderQuery = new DocAsnHeader();
        docAsnHeaderQuery.setAsnno(asnno);
        MybatisCriteria mybatisCriteriaHeader = new MybatisCriteria();
        mybatisCriteriaHeader.setCondition(BeanConvertUtil.bean2Map(docAsnHeaderQuery));
        List<DocAsnHeader> docAsnHeaderListQuery = docAsnHeaderMybatisDao.queryAsnno(mybatisCriteriaHeader);
        for (DocAsnHeader docAsnHeader1 : docAsnHeaderListQuery) {
            //BeanUtils.copyProperties(docAsnHeader1);
            docAsnHeader.setAsnno(docAsnHeader1.getAsnno());
            docAsnHeader.setExpectedarrivetime1(docAsnHeader1.getExpectedarrivetime1());
            docAsnHeader.setWarehouseid(docAsnHeader1.getWarehouseid());
            BasCustomer basCustomer = basCustomerMybatisDao.queryByIdType(docAsnHeader1.getCustomerid(), Constant.CODE_CUS_TYP_OW);//货主
            if (basCustomer != null) {
                docAsnHeader.setCustomerIdRef(basCustomer.getDescrC());
            } else {
                docAsnHeader.setCustomerIdRef(" ");
            }
            BasCustomer basCustomer1 = basCustomerMybatisDao.queryByIdType(docAsnHeader1.getSupplierid(), Constant.CODE_CUS_TYP_VE);//供应商
            if (basCustomer1 != null) {
                docAsnHeader.setSupplierIdRef(basCustomer1.getDescrC());
            } else {
                docAsnHeader.setSupplierIdRef("");
            }
            docAsnHeader.setColdTag(docAsnHeader1.getColdTag());
        }

        docAsnHeaderList.add(docAsnHeader);
        return docAsnHeaderList;
    }


    private String getKey(OrderDetailsForNormal detail) {
		/*return detail.getSku()+""+detail.getLotatt01()+""+detail.getLotatt02()+""+detail.getLotatt03()+""+detail.getLotatt04()
				+""+detail.getLotatt05()+""+detail.getLotatt06()+""+detail.getLotatt07()+""+detail.getLotatt08()
				+""+detail.getLotatt09()+""+detail.getLotatt10()+""+detail.getLotatt11()+""+detail.getLotatt12()
				+""+detail.getLotatt13();*/
        return detail.getSku() + detail.getLotatt01() + detail.getLotatt02() + detail.getLotatt04()
                + detail.getLotatt05() + detail.getLotatt06() + detail.getLotatt08()
                + detail.getLotatt09();
    }

    private String getAsnKey(DocAsnDetail detail) {
    	/*return detail.getSku()+""+detail.getLotatt01()+""+detail.getLotatt02()+""+detail.getLotatt03()+""+detail.getLotatt04()
				+""+detail.getLotatt05()+""+detail.getLotatt06()+""+detail.getLotatt07()+""+detail.getLotatt08()
				+""+detail.getLotatt09()+""+detail.getLotatt10()+""+detail.getLotatt11()+""+detail.getLotatt12()
				+""+detail.getLotatt13();*/
        return detail.getSku() + detail.getLotatt01() + detail.getLotatt02() + detail.getLotatt04()
                + detail.getLotatt05() + detail.getLotatt06() + detail.getLotatt08()
                + detail.getLotatt09();
    }


}