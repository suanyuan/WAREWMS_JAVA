package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaOrderPackingForm;
import com.wms.query.*;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocPackageQuery;
import com.wms.result.PdaResult;
import com.wms.utils.*;
import com.wms.vo.DocOrderPackingVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocOrderPackingForm;
import com.wms.vo.pda.PdaDocPackageVO;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("docOrderPackingService")
public class DocOrderPackingService extends BaseService {

	@Autowired
	private DocOrderPackingMybatisDao docOrderPackingMybatisDao;
	
	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;

	@Autowired
	private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;

	@Autowired
	private ActAllocationDetailsMybatisDao actAllocationDetailsMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private BasSerialNumMybatisDao basSerialNumMybatisDao;

	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;

	@Autowired
	private DocSerialNumRecordMybatisDao docSerialNumRecordMybatisDao;
	
	public EasyuiDatagrid<DocOrderPackingVO> getPagedDatagrid(EasyuiDatagridPager pager, DocOrderPackingQuery query) {
		EasyuiDatagrid<DocOrderPackingVO> datagrid = new EasyuiDatagrid<DocOrderPackingVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.queryByPageList(mybatisCriteria);
		DocOrderPackingVO docOrderPackingVO;
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

    /**
     * 出库单状态：
     * 00 - 订单创建
     * 30 - 部分分配          ✅
     * 40 - 分配完成          ✅
     * 50 - 部分拣货/部分装箱  ✅
     * 60 - 完全拣货/完全装箱
     * 80 - 发运/部分发运
     * 90 - 单据取消
     * 99 - 单据关闭
     *
     * 目前来说，分配完成之后，到出库单的包装复核结束，状态是不做变更的
     */
	public Json orderStatusCheck(String orderNo) {
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		return orderObjCheck(orderHeaderForNormal);
	}

	private Json orderObjCheck(OrderHeaderForNormal orderHeaderForNormal) {
        Json json = new Json();
        if(orderHeaderForNormal != null){

            /*
             * H - 订单冻结
             * N - 订单未释放
             * R - 订单任务下发
             * Y - 订单释放
             */
            switch (orderHeaderForNormal.getReleasestatus()) {
                case "H":
                    json.setSuccess(false);
                    json.setMsg("当前订单已冻结");
                    return json;
                case "N":
                    json.setSuccess(false);
                    json.setMsg("当前订单未释放");
                    return json;
                    default:
                        break;
            }

            switch (orderHeaderForNormal.getSostatus()) {
                case "30":
                case "40":
                case "50":
                    json.setSuccess(true);
                    json.setMsg("000");
                    return json;
                case "60":
                    json.setSuccess(false);
                    json.setMsg("当前订单已完成装箱操作！");
                    return json;
                default:
                    json.setSuccess(false);
                    json.setMsg("当前状态订单不允许进行装箱操作！");
                    return json;
            }
        } else {
            json.setSuccess(false);
            json.setMsg("查无此出库单号！");
            return json;
        }
    }

	public Json generateCartonNo(String orderNo) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderNo(orderNo);
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryCartonNoById(docOrderPackingQuery);
		if(docOrderPacking == null){
			docOrderPacking = new DocOrderPacking();
			docOrderPacking.setOrderNo(orderNo);
			docOrderPacking.setCartonNo(1);
		} else {
			docOrderPacking.setCartonNo(docOrderPacking.getCartonNo());
		}
		json.setSuccess(true);
		json.setMsg("000");
		json.setObj(docOrderPacking);
		return json;
	}

	public Json skuScanCheck(String orderNo, String skuCode) {
		/*Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderNo(orderNo);
		docOrderPackingQuery.setCartonNo(cartonNo);
		docOrderPackingQuery.setSkuCode(skuCode);
		//产品信息校验
		List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.checkSkuById(docOrderPackingQuery);
		if (docOrderPackingList.size() == 0) {
			json.setMsg("无效的产品信息!");
			json.setSuccess(false);
			return json;
		} else if (docOrderPackingList.size() > 1) {
			json.setMsg("异常的产品信息!");
			json.setSuccess(false);
			return json;
		}
		docOrderPackingQuery.setSku(docOrderPackingList.get(0).getSku());
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryById(docOrderPackingQuery);
		json.setMsg("000");
		json.setSuccess(true);
		json.setObj(docOrderPacking);
		return json;*/
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		//解析code
		GS1Code128DataUtil gs1 = new GS1Code128DataUtil(skuCode);
		PdaDocPackageQuery query = new PdaDocPackageQuery();
		query.setOrderno(orderNo);
		query.setGTIN(gs1.getGTIN());
		query.setLotatt05(gs1.getSerialNum());
		query.setLotatt02(gs1.getExpDate());
		query.setLotatt04(gs1.getLotNum());
		query.setOtherCode(gs1.getOtherCode());

		query.setCustomerid(orderHeaderForNormal.getCustomerid());
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		Map<String,Object> map = queryDocPackage(query);
		PdaResult result = (PdaResult)map.get(Constant.RESULT);
		if(result.getErrorCode() == PdaResult.CODE_SUCCESS){
			DocOrderPackingForm form = new DocOrderPackingForm();
			PdaDocPackageVO packageVO = (PdaDocPackageVO)map.get(Constant.DATA);
			form.setOrderno(orderNo);
			form.setTraceid(packageVO.getCartonNum());//箱号
			form.setCustomerid(packageVO.getBasSku().getCustomerid());
			form.setSku(packageVO.getBasSku().getSku());
			form.setQty(1);
			form.setAllocationdetailsid(packageVO.getActAllocationDetails().getAllocationdetailsid());
			form.setDescription("");
			form.setConclusion("合格");
			form.setSkudesce("");
			form.setLotnum(packageVO.getActAllocationDetails().getLotnum());
			form.setLotatt11(packageVO.getInvLotAtt().getLotatt11());
			form.setSerialNums(packageVO.getSerialNum());
			packageCommit(form);
			return Json.success(result.getMsg(),map.get(Constant.DATA));
		}else{
			return Json.error(result.getMsg());
		}
	}

	@Transactional
	public Json skuScan(String orderNo, Integer cartonNo, String sku, Integer qty) {
		Json json = new Json();
		DecimalFormat df = new DecimalFormat("000");
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderNo(orderNo);
		docOrderPackingQuery.setCartonNo(cartonNo);
		docOrderPackingQuery.setSku(sku);
		docOrderPackingQuery.setQty(qty);
		//扫描数量校验
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.checkQtyById(docOrderPackingQuery);
		if(docOrderPacking == null) {
			json.setMsg("产品已超量!");
			json.setSuccess(false);
			return json;
		} else {
			Integer scanQty = 0, remainQty = qty;
			List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.queryPackingById(docOrderPackingQuery);
			if (docOrderPackingList != null && docOrderPackingList.size() > 0) {
				for (DocOrderPacking addDocOrderPacking : docOrderPackingList) {
					if (addDocOrderPacking.getAllocationQty() - addDocOrderPacking.getScanQty() >= remainQty) {
						scanQty = remainQty;
						remainQty = 0;
					} else {
						scanQty = addDocOrderPacking.getAllocationQty() - addDocOrderPacking.getScanQty();
						remainQty = remainQty - (addDocOrderPacking.getAllocationQty() - addDocOrderPacking.getScanQty());
					}
					docOrderPackingQuery.setSku(addDocOrderPacking.getSku());
					docOrderPackingQuery.setAllocationDetailsId(addDocOrderPacking.getAllocationDetailsId());
					Integer checkCount = docOrderPackingMybatisDao.checkCountById(docOrderPackingQuery);
					Integer cartonCount = docOrderPackingMybatisDao.queryCartonInfoCountById(docOrderPackingQuery);
					docOrderPacking.setOrderNo(orderNo);
					docOrderPacking.setCartonNo(cartonNo);
					docOrderPacking.setAllocationDetailsId(addDocOrderPacking.getAllocationDetailsId());
					docOrderPacking.setTraceId(orderNo + df.format(cartonNo));
					docOrderPacking.setScanQty(scanQty);
					docOrderPacking.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
					if (cartonCount == 0) {
						/*DocOrderPackingCartonInfo docOrderPackingCartonInfo = new DocOrderPackingCartonInfo();
						docOrderPackingCartonInfo.setOrderno(orderNo);
						docOrderPackingCartonInfo.setTraceid(docOrderPacking.getTraceId());
						docOrderPackingCartonInfo.set*/
						docOrderPackingMybatisDao.packingCartonInfoInsert(docOrderPacking);
					}
					if (checkCount == 0) {
						docOrderPackingMybatisDao.packingCartonInsert(docOrderPacking);
					} else {
						docOrderPackingMybatisDao.packingCartonUpdate(docOrderPacking);
					}
					if (remainQty == 0) {
						break;
					}
				}
			}
		}
		DocOrderPacking subDocOrderPacking = docOrderPackingMybatisDao.queryById(docOrderPackingQuery);
		json.setMsg("000");
		json.setSuccess(true);
		json.setObj(subDocOrderPacking);
		return json;
	}

	public Json packingCommit(String orderNo) {
		DocOrderPackingForm form = new DocOrderPackingForm();
		form.setOrderno(orderNo);
		PdaResult pdaResult = commitCartonType(form);
		if(pdaResult == null || pdaResult.getErrorCode() == PdaResult.CODE_FAILURE){
			return Json.error(pdaResult.getMsg());
		}else{
			return Json.success(pdaResult.getMsg());
		}
	}

	public Json orderCommit(String orderNo, String cartonNo, String cartontype) {
		PdaResult pdaResult = endPacking(orderNo);
		if(pdaResult == null || pdaResult.getErrorCode() == PdaResult.CODE_FAILURE){
			return Json.error(pdaResult.getMsg());
		}else{
			return Json.success(pdaResult.getMsg());
		}
		/*Json json = new Json();
		DocOrderPacking docOrderPacking = new DocOrderPacking();
		docOrderPacking.setOrderNo(orderNo);
		docOrderPacking.setTraceId(cartonNo);
		docOrderPacking
		List<DocOrderPacking> subDocOrderPackingList = null;
		subDocOrderPackingList = docOrderPackingMybatisDao.checkPackingCommitById(docOrderPacking);
		if (subDocOrderPackingList != null && subDocOrderPackingList.size() > 0) {
			StringBuilder resultMsg = new StringBuilder();
			for (DocOrderPacking subDocOrderPacking : subDocOrderPackingList) {
				resultMsg.append("箱号：").append(subDocOrderPacking.getCartonNo()).append("，未完成装箱").append("  ");
			}
			if (resultMsg.length() == 0) {
				json.setSuccess(false);
				json.setMsg("复核完成提交失败！");
				return json;
			} else {
				json.setSuccess(false);
				json.setMsg(resultMsg.toString());
				return json;
			}
		}
		subDocOrderPackingList = docOrderPackingMybatisDao.checkOrderCommitById(docOrderPacking);
		if (subDocOrderPackingList != null && subDocOrderPackingList.size() > 0) {
			StringBuilder resultMsg = new StringBuilder();
			for (DocOrderPacking subDocOrderPacking : subDocOrderPackingList) {
				resultMsg.append(subDocOrderPacking.getSku()).append("（").append(subDocOrderPacking.getSkuName()).append("）未完全装箱").append("<br>");
			}
			if (resultMsg.length() == 0) {
				json.setSuccess(false);
				json.setMsg("复核完成提交失败！");
				return json;
			} else {
				json.setSuccess(false);
				json.setMsg(resultMsg.toString());
				return json;
			}
		}
		docOrderPackingMybatisDao.orderCartonInfoUpdate(docOrderPacking);
//		OrderHeaderForNormal orderHeaderForNormal = docOrderPackingMybatisDao.queryCartonInfoById(docOrderPacking);
		//推送装箱信息（箱数、体积、重量）
//		try {
//			DecimalFormat df = new DecimalFormat("########0.000");
//			StockInXmlVo stockInXmlVo = new StockInXmlVo();
//			List<StockInPackageCodeXmlVo> stockInPackageCodeXmlVoList = new ArrayList<StockInPackageCodeXmlVo>();
//			stockInXmlVo.setOrderCode(orderHeaderForNormal.getOrderCode());
//			stockInXmlVo.setTotalVolume(Double.valueOf(df.format(cube)));
//			stockInXmlVo.setTotalWeight(Double.valueOf(df.format(grossWeight)));
//			stockInXmlVo.setPackageNum(orderHeaderForNormal.getBoxQty());
//			stockInXmlVo.setOrderStatus(28);
//			for (DocOrderPacking subDocOrderPacking : orderHeaderForNormal.getDocOrderPackingList()) {
//				StockInPackageCodeXmlVo stockInPackageCodeXmlVo = new StockInPackageCodeXmlVo();
//				stockInPackageCodeXmlVo.setCode(subDocOrderPacking.getTraceId());
//				stockInPackageCodeXmlVo.setStatus(0);
//				stockInPackageCodeXmlVoList.add(stockInPackageCodeXmlVo);
//			}
//			stockInXmlVo.setPackageCodes(stockInPackageCodeXmlVoList);
//			String xmldata = JaxbUtil.convertToXml(stockInXmlVo, false);
//			logger.error("docOrderPackingService-推送：" + xmldata);
//			ResponseVO responseVO = new ServiceControllerProxy(END_POINT).updateOrder(xmldata);
//			logger.error("docOrderPackingService-接收：" + responseVO.getSuccess());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		json.setSuccess(true);
		json.setMsg("复核完成提交成功！");
		return json;*/
	}

	public Json singlePackingCancel(String orderNo, Integer cartonNo) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			if (orderHeaderForNormal.getSostatus().equals("70") || orderHeaderForNormal.getSostatus().equals("80")) {
				json.setSuccess(false);
				json.setMsg("取消装箱失败：当前状态订单不允许进行取消装箱操作！");
				return json;
			} else {
				if (orderHeaderForNormal.getPackingFlag()!=null && orderHeaderForNormal.getPackingFlag().equals("Y")) {
					json.setSuccess(false);
					json.setMsg("取消装箱失败：当前状态订单不允许进行取消装箱操作！");
					return json;
				} else {

				    //删除整箱的序列号记录
                    DocSerialNumRecord docSerialNumRecord = new DocSerialNumRecord();
                    docSerialNumRecord.setOrderNo(orderNo);
                    docSerialNumRecord.setCartonNo(cartonNo);
                    docSerialNumRecordMybatisDao.clearRecordByTraceid(docSerialNumRecord);

                    //删除包装记录
					DocOrderPacking docOrderPacking = new DocOrderPacking();
					docOrderPacking.setOrderNo(orderNo);
					docOrderPacking.setCartonNo(cartonNo);
					docOrderPackingMybatisDao.packingCartonDelete(docOrderPacking);
					docOrderPackingMybatisDao.packingCartonInfoDelete(docOrderPacking);
//					docOrderPackingMybatisDao.packingCartonFlagUpdate(docOrderPacking);//？？？TODO 待确认
					json.setSuccess(true);
					json.setMsg("取消装箱成功！");
					return json;
				}
			}
		} else {
			json.setSuccess(false);
			json.setMsg("取消装箱失败：查无此出库单号和对应箱号！");
			return json;
		}
	}

	public Json orderPackingCancel(String orderNo) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			if (orderHeaderForNormal.getSostatus().equals("70") || orderHeaderForNormal.getSostatus().equals("80")) {
				json.setSuccess(false);
				json.setMsg("取消复核失败：当前状态订单不允许进行取消复核操作！");
				return json;
			} else {
				DocOrderPacking docOrderPacking = new DocOrderPacking();
				docOrderPacking.setOrderNo(orderNo);
				docOrderPackingMybatisDao.packingCartonFlagUpdate(docOrderPacking);
				json.setSuccess(true);
				json.setMsg("取消复核成功！");
				return json;
			}
		} else {
			json.setSuccess(false);
			json.setMsg("取消复核失败：查无此出库单号！");
			return json;
		}
	}

	public Json getPackingInfo(String orderNo, Integer cartonNo) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderNo(orderNo);
		docOrderPackingQuery.setCartonNo(cartonNo);
		//
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryPackingInfoById(docOrderPackingQuery);
		if (docOrderPacking != null) {
			json.setSuccess(true);
			json.setMsg("");
			json.setObj(docOrderPacking);
			return json;
		} else {
			json.setSuccess(false);
			json.setMsg("数据异常！");
			return json;
		}
	}

	public Json getOrderPackingInfo(String orderNo) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderNo(orderNo);
		//
		DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryOrderPackingInfoById(docOrderPackingQuery);
		if (docOrderPacking != null) {
			json.setSuccess(true);
			json.setMsg("");
			json.setObj(docOrderPacking);
			return json;
		} else {
			json.setSuccess(false);
			json.setMsg("数据异常！");
			return json;
		}
	}

	public void exportPackingLabelPdf(HttpServletResponse response, String orderNo, String orderCode, Integer cartonNo) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

			if (StringUtils.isNotEmpty(orderNo) || StringUtils.isNotEmpty(orderCode)) {
				
				document = new Document(PDFUtil.getTemplate("wms_packing_label.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();
				
				DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
				if (orderNo != null && orderNo.length() > 0) {
					docOrderPackingQuery.setOrderNo(orderNo);
				}
				if (orderCode != null && orderCode.length() > 0) {
					docOrderPackingQuery.setOrderCode(orderCode);
				}
				if (cartonNo != null) {
					docOrderPackingQuery.setCartonNo(cartonNo);
				}
				List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.queryPackingLabelById(docOrderPackingQuery);
				for(DocOrderPacking docOrderPacking : docOrderPackingList){
					
					baos = new ByteArrayOutputStream();
					stamper = new PdfStamper(PDFUtil.getTemplate("wms_packing_label.pdf"), baos);
					form = stamper.getAcroFields();
					form.setField("fenceName", docOrderPacking.getFenceName() == null ? "" : docOrderPacking.getFenceName());
					form.setField("packageCode", docOrderPacking.getOrderCode() == null ? docOrderPacking.getOrderCode() : docOrderPacking.getOrderCode());
					form.setField("cartonNo", df.format(docOrderPacking.getCartonNo()));
					form.setField("quantity", String.valueOf(docOrderPacking.getCartonQty()));
					form.setField("custShortName", docOrderPacking.getMerchantName() == null ? "***" : docOrderPacking.getMerchantName());
					form.setField("custStore", docOrderPacking.getCustStore() == null ? "***" : docOrderPacking.getCustStore());
					form.setField("name", docOrderPacking.getName());
					form.setField("expectedArriveTime", docOrderPacking.getRequiredDeliveryTime() == null ? "" : sdf.format(docOrderPacking.getRequiredDeliveryTime()));
					form.setField("address", docOrderPacking.getAddress());
					form.replacePushbuttonField("packageBarcodeImg", PDFUtil.genPdfButton(form, "packageBarcodeImg", BarcodeGeneratorUtil.genBarcode(docOrderPacking.getTraceId(), 800)));
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

	public void exportPackingListPdf(HttpServletResponse response, String orderNo, String orderCode, Integer cartonNo) {
		StringBuilder sb = new StringBuilder();
		try (OutputStream os = response.getOutputStream()){
			sb.append("inline; filename=")
			  .append(URLEncoder.encode("装箱清单PDF","UTF-8"))
			  .append(".pdf");
			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
			response.setContentType(ContentTypeEnum.pdf.getContentType());
			
			Document document = null;
			AcroFields form = null;
			PdfStamper stamper = null;
			PdfImportedPage page = null;
			ByteArrayOutputStream baos = null;

			if (StringUtils.isNotEmpty(orderNo) || StringUtils.isNotEmpty(orderCode)) {
				
				/*document = new Document(PDFUtil.getTemplate("wms_packing_list.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();
				
				int totalNum = 0;
				List<DocOrderPacking> packingList = new ArrayList<DocOrderPacking>();
				int row = 16;
				int pageSize = 0;
				DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
				if (orderNo != null && orderNo.length() > 0) {
					docOrderPackingQuery.setOrderNo(orderNo);
				}
				if (orderCode != null && orderCode.length() > 0) {
					docOrderPackingQuery.setOrderCode(orderCode);
				}
				OrderHeaderForNormal orderHeader = docOrderPackingMybatisDao.queryPackingListById(docOrderPackingQuery);
				for(DocOrderPacking packingDetails : orderHeader.getDocOrderPackingList()){
					totalNum++;
					packingList.add(packingDetails);
				}
				
				pageSize = (int)Math.ceil((double)totalNum / row);
				for(int i = 0 ; i < pageSize ; i++) {
					baos = new ByteArrayOutputStream();
					stamper = new PdfStamper(PDFUtil.getTemplate("wms_packing_list.pdf"), baos);
					form = stamper.getAcroFields();
					form.setField("orderNo", orderHeader.getOrderNo());
					form.setField("orderCode", orderHeader.getOrderCode() == null ? "" : orderHeader.getOrderCode());
					form.setField("custName", orderHeader.getCustomerShortName());
					form.setField("cartonQty", String.valueOf(orderHeader.getBoxQty()));
					form.setField("name", orderHeader.getConsigneeName() == null ? "" : orderHeader.getConsigneeName());
					form.setField("tel", orderHeader.getTel() == null ? "" : orderHeader.getTel());
					form.setField("address", orderHeader.getAddress() == null ? "" : orderHeader.getAddress());
					form.setField("totalQty", String.valueOf(orderHeader.getTotalQty()));
					for(int j = 0 ; j < row ; j++){
						if(totalNum > (row * i + j)){
							form.setField("seq"+(j+1), String.valueOf(row * i + j + 1));
							form.setField("traceId"+(j+1), packingList.get(row * i + j).getTraceId());
							form.setField("sku"+(j+1), packingList.get(row * i + j).getSku());
							form.setField("skuName"+(j+1), packingList.get(row * i + j).getSkuName());
							form.setField("unit"+(j+1), packingList.get(row * i + j).getUnit());
							form.setField("qty"+(j+1), String.valueOf(packingList.get(row * i + j).getScanQty()));
						}
					}
					//form.replacePushbuttonField("packageBarcodeImg", PDFUtil.genPdfButton(form, "packageBarcodeImg", BarcodeGeneratorUtil.genBarcode(orderHeader.getOrderNo(), 800)));
					stamper.setFormFlattening(true);
					stamper.close();
					page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
					pdfCopy.addPage(page);
				}
				document.close();*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 扫码查询复核任务明细
     * @param query ~
     * @return ~
     */
	public Map<String, Object> queryDocPackage(PdaDocPackageQuery query) {

	    Map<String, Object> map = new HashMap<>();
	    Json statusJson = orderStatusCheck(query.getOrderno());
	    if (!statusJson.isSuccess()) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, statusJson.getMsg()));
            return map;
        }

        //如果是序列号扫码，验证是否存在对应序列号记录（bas_serial_num）
        // 这里处理的有两种情况：
        //  1.扫描序列号出库
        //  2.扫描带序列号的条码出库
        if (StringUtil.isNotEmpty(query.getOtherCode()) ||
        StringUtil.isNotEmpty(query.getLotatt05())) {

            BasSerialNumQuery serialNumQuery = new BasSerialNumQuery(StringUtil.isNotEmpty(query.getOtherCode()) ? query.getOtherCode() : query.getLotatt05());
            BasSerialNum basSerialNum = basSerialNumMybatisDao.queryById(serialNumQuery);
            if (basSerialNum != null) {

                //序列号扫码数据缺失 效期、生产批号（注：序列号不需要传，效期不参与查询）
                query.setLotatt04(basSerialNum.getBatchNum());
            }
        }

	    PdaDocPackageVO pdaDocPackageVO = new PdaDocPackageVO();

	    PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
	    BeanUtils.copyProperties(query, basSkuQuery);
	    basSkuQuery.setLotatt05("");//序列号不参与查询，同批号的SKU必然是相同的。（DISTINCT）
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);
        if (basSku == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无产品档案"));
            return map;
        }

        /*
         * 产品档案
         */
        pdaDocPackageVO.setBasSku(basSku);


        /*
        产品线 为空则默认正常流程
        不为空的情况下，如果记录序列号的serial_flag为1，则在下方需要清除查询条件-序列号
         */
        ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
        ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
        boolean isSerialManagement = (productLine != null && productLine.getSerialFlag() == 1);

        /*
        如果是强生产品线的，需要将序列号记录下来，在复核提交的时候保存起来
         */
        if (isSerialManagement) {
            //如果在出库序列号记录中查询到对应的，则提示已复核过了
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            DocSerialNumRecord docSerialNumRecord = new DocSerialNumRecord(query.getOrderno(),
                    StringUtil.isNotEmpty(query.getOtherCode()) ? query.getOtherCode() : query.getLotatt05());
            mybatisCriteria.setCondition(docSerialNumRecord);
            List<DocSerialNumRecord> docSerialNumRecordList = docSerialNumRecordMybatisDao.queryByList(mybatisCriteria);
            if (docSerialNumRecordList.size() > 0) {
                map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "此序列号已记录复核"));
                return map;
            }

            pdaDocPackageVO.setSerialNum(StringUtil.isNotEmpty(query.getOtherCode()) ? query.getOtherCode() : query.getLotatt05());
        }

        ActAllocationDetailsQuery actAllocationDetailsQuery = new ActAllocationDetailsQuery();
        actAllocationDetailsQuery.setOrderno(query.getOrderno());
        actAllocationDetailsQuery.setCustomerid(query.getCustomerid());
        actAllocationDetailsQuery.setSku(basSku.getSku());
//        actAllocationDetailsQuery.setLotatt02(DateUtil.lotatt02DateFormat(query.getLotatt02()));//效期不参与查询
        actAllocationDetailsQuery.setLotatt04(query.getLotatt04());
        if (isSerialManagement) actAllocationDetailsQuery.setLotatt05(query.getLotatt05());
        actAllocationDetailsQuery.setPackflag("0");
        List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsMybatisDao.queryForScan(actAllocationDetailsQuery);
        if (actAllocationDetailsList == null || actAllocationDetailsList.size() == 0) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无此产品的分配明细"));
            return map;
        }

        //list里面任一条都是对应的出库单明细都是相同的，所以根据orderno和orderlineno可以拿到分配数量
        ActAllocationDetails actAllocationDetails = actAllocationDetailsList.get(0);
        OrderDetailsForNormalQuery orderDetailQuery = new OrderDetailsForNormalQuery();
        orderDetailQuery.setOrderno(actAllocationDetails.getOrderno());
        orderDetailQuery.setOrderlineno(actAllocationDetails.getOrderlineno());
        OrderDetailsForNormal orderDetail = orderDetailsForNormalMybatisDao.queryById(orderDetailQuery);
        if (orderDetail == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无此产品的出库明细"));
            return map;
        }

        /**
         * 分配明细 + 出库明细
         */
        pdaDocPackageVO.setActAllocationDetails(actAllocationDetails);
        pdaDocPackageVO.setOrderDetailsForNormal(orderDetail);


        InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(actAllocationDetails.getLotnum());
        if (invLotAtt == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "查无此产品批次信息"));
            return map;
        }
        //批次属性
        pdaDocPackageVO.setInvLotAtt(invLotAtt);

        //箱号
        //查询这个出库单下面的这个sku有没有装过箱，packflag是否为0（装箱还没结束）,可能存在多个箱号，所以是个list
        //如果没有packflag为0的，就在未被使用的箱号中生成一个，暂时不写入，有包装复核提交动作的时候再插数据到doc_order_packing_carton表中
        String cartonNum;

        DocOrderPackingCarton cartonQuery = new DocOrderPackingCarton();
        cartonQuery.setOrderno(actAllocationDetails.getOrderno());
        cartonQuery.setSku(actAllocationDetails.getSku());
        cartonQuery.setCustomerid(actAllocationDetails.getCustomerid());
        cartonQuery.setLotnum(actAllocationDetails.getLotnum());
        DocOrderPackingCarton docOrderPackingCarton = docOrderPackingMybatisDao.queryGoodsPackage(cartonQuery);
        if (docOrderPackingCarton == null) {
            DocOrderPackingQuery packingQuery = new DocOrderPackingQuery();
            packingQuery.setOrderNo(actAllocationDetails.getOrderno());
            DocOrderPacking docOrderPacking = docOrderPackingMybatisDao.queryCartonNoById(packingQuery);
            cartonNum = String.format("%s#%03d", actAllocationDetails.getOrderno(), docOrderPacking.getCartonNo());
        }else {

            cartonNum = docOrderPackingCarton.getTraceid();
        }
        pdaDocPackageVO.setCartonNum(cartonNum);

        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        map.put(Constant.DATA, pdaDocPackageVO);
        return map;
    }

    /**
     * 复核/包装复核提交
     */
    public PdaResult packageCommit(DocOrderPackingForm form) {

        Json statusJson = orderStatusCheck(form.getOrderno());
        if (!statusJson.isSuccess()) {
            return new PdaResult(PdaResult.CODE_FAILURE, statusJson.getMsg());
        }

        ActAllocationDetailsQuery allocationQuery = new ActAllocationDetailsQuery();
        DocOrderPackingCarton packingCartonQuery = new DocOrderPackingCarton();

        //分配明细
        allocationQuery.setAllocationdetailsid(form.getAllocationdetailsid());
        ActAllocationDetails matchDetails = actAllocationDetailsMybatisDao.queryById(allocationQuery);

        //分配明细中装箱的件数
        packingCartonQuery.setOrderno(form.getOrderno());
        packingCartonQuery.setAllocationdetailsid(form.getAllocationdetailsid());
        int packedNum = docOrderPackingMybatisDao.queryPackedNum(packingCartonQuery);
        if (matchDetails.getQty() < (form.getQty() + packedNum)) {
            return new PdaResult(PdaResult.CODE_FAILURE, "当前提交数超出分配数");
        }

        try {

            //记录序列号出库
            if (StringUtil.isNotEmpty(form.getSerialNums())) {

                String[] serialNumList = form.getSerialNums().split(",");
                for (String serialNum : serialNumList) {

                    if (StringUtil.isNotEmpty(serialNum)) {

                        DocSerialNumRecord docSerialNumRecord = new DocSerialNumRecord(Integer.valueOf(form.getTraceid().split("#")[1]), form.getOrderno(), serialNum);
                        docSerialNumRecordMybatisDao.add(docSerialNumRecord);
                    }
                }
            }

            //如果包装数和分配数相同要将分配明细的packflag set 1
            packingCartonQuery.setTraceid(form.getTraceid());
            packingCartonQuery.setSku(form.getSku());
            packingCartonQuery.setLotnum(form.getLotnum());
            DocOrderPackingCarton packingCarton = docOrderPackingMybatisDao.queryAvailablePackedDetail(packingCartonQuery);
            int matchQty = packingCarton == null ? 0 : packingCarton.getQty();
            if (matchDetails.getQty() == (form.getQty() + matchQty)) {

                actAllocationDetailsMybatisDao.finishPacking(allocationQuery);
            }

            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(form.getLotnum());
//            invLotAtt.setLotatt11(form.getLotatt11());//2019-08-15 仓储部不需要储存条件编辑功能

            DocOrderPackingCartonInfo packingCartonInfo = docOrderPackingMybatisDao.queryPackingCartonInfo(form.getOrderno(), form.getTraceid());
            if (packingCartonInfo == null) {

                DocOrderPackingCartonInfo docOrderPackingCartonInfo = new DocOrderPackingCartonInfo();
                BeanUtils.copyProperties(form, docOrderPackingCartonInfo);
                docOrderPackingCartonInfo.setCartonno(Integer.valueOf(form.getTraceid().split("#")[1]));
                docOrderPackingCartonInfo.setAddwho("Gizmo");
                docOrderPackingMybatisDao.packingCartonInfoInsert(docOrderPackingCartonInfo);
            }

            if (packingCarton == null) {

                DocOrderPackingCarton packingCartonInsert = new DocOrderPackingCarton();
                BeanUtils.copyProperties(invLotAtt, packingCartonInsert);
//                packingCartonInsert.setLotnum(null);
                packingCartonInsert.setOrderno(form.getOrderno());
                packingCartonInsert.setTraceid(form.getTraceid());
                packingCartonInsert.setCartonno(Integer.valueOf(form.getTraceid().split("#")[1]));
                packingCartonInsert.setCustomerid(form.getCustomerid());
                packingCartonInsert.setSku(form.getSku());
                packingCartonInsert.setSkudesce(form.getSkudesce());
                packingCartonInsert.setQty(form.getQty());
                packingCartonInsert.setAllocationdetailsid(form.getAllocationdetailsid());
                packingCartonInsert.setDescription(form.getDescription());
                packingCartonInsert.setConclusion(form.getConclusion());
                packingCartonInsert.setAddwho("Gizmo");
                packingCartonInsert.setEdittime(null);
                packingCartonInsert.setEditwho(null);
                docOrderPackingMybatisDao.packingCartonInsert(packingCartonInsert);
            }else {

                //这边的packingCartonInfo的pickflag肯定是0，在上yigefangfa获取的时候已经作了处理
                packingCarton.setQty(packingCarton.getQty() + form.getQty());
                packingCarton.setEditwho("Gizmo");
                docOrderPackingMybatisDao.updatePackingCarton(packingCarton);
            }

        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务

            return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "包装复核失败(系统错误:" + e.getMessage() + ")");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "包装复核成功");
    }

    /**
     * 提交记录箱型
     */
    public PdaResult commitCartonType(DocOrderPackingForm form) {

        Json statusJson = orderStatusCheck(form.getOrderno());
        if (!statusJson.isSuccess()) {
            return new PdaResult(PdaResult.CODE_FAILURE, statusJson.getMsg());
        }

        DocOrderPackingCartonInfo packingCartonInfo = docOrderPackingMybatisDao.queryPackingCartonInfo(form.getOrderno(), form.getTraceid());
        if (packingCartonInfo == null) {
            return new PdaResult(PdaResult.CODE_FAILURE, "查无此箱号的装箱数据");
        }
	    try {

            packingCartonInfo.setCartontype(form.getCartontype());
            packingCartonInfo.setPackingflag(1);
            docOrderPackingMybatisDao.updatePackingCartonInfo(packingCartonInfo);
        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "装箱失败(系统错误:" + e.getMessage() + ")");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "装箱结束成功");
    }

    /**
     * 结束复核
     * @param orderno 单号
     * @return ~
     */
    public PdaResult endPacking(String orderno) {

	    OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderno);
        Json statusJson = orderObjCheck(orderHeaderForNormal);
	    if (!statusJson.isSuccess()) {
            return new PdaResult(PdaResult.CODE_FAILURE, statusJson.getMsg());
        }
        try {

            //查询包装明细包装总数，回写到act_allocation_details中,用price记录装箱件数
            Map<String, Object> condition = new HashMap<>();
            condition.put("orderno", orderno);
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(condition);
            List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsMybatisDao.queryByList(mybatisCriteria);
            for (ActAllocationDetails allocationDetails : actAllocationDetailsList) {

                DocOrderPackingCarton packedQuery = new DocOrderPackingCarton();
                packedQuery.setAllocationdetailsid(allocationDetails.getAllocationdetailsid());
                packedQuery.setOrderno(orderno);
                int packedNum = docOrderPackingMybatisDao.queryPackedNum(packedQuery);
                allocationDetails.setPrice(packedNum + 0.0);
                actAllocationDetailsMybatisDao.update(allocationDetails);//分配明细

                PdaOrderPackingForm packingForm = new PdaOrderPackingForm(
                        orderHeaderForNormal.getWarehouseid(),
                        PdaOrderPackingForm.ACTION_RV,
                        allocationDetails.getAllocationdetailsid(),
                        "Gizmo");
                actAllocationDetailsMybatisDao.callPickingProcedure(packingForm);
                if (!packingForm.getResult().equals("000")) {
                    throw new Exception("包装复核拣货数回写失败");
                }
            }
        }catch (Exception e) {

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new PdaResult(PdaResult.CODE_FAILURE, PdaResult.PDA_FAILURE_IDENTIFIER + "结束失败(系统错误:" + e.getMessage() + ")");
        }

        return new PdaResult(PdaResult.CODE_SUCCESS, "包装复核结束成功");
    }
}