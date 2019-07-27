package com.wms.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.wms.vo.form.pda.PageForm;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.wms.entity.DocOrderPacking;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.utils.BarcodeGeneratorUtil;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.JaxbUtil;
import com.wms.utils.PDFUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocOrderPackingVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.dao.OrderHeaderForNormalMybatisDao;
import com.wms.mybatis.dao.DocOrderPackingMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.query.DocOrderPackingQuery;

@Service("docOrderPackingService")
public class DocOrderPackingService extends BaseService {
	
	static Logger logger = Logger.getLogger("myLog");
	
	private static final String END_POINT = ResourceUtil.geteEndpoint();

	@Autowired
	private DocOrderPackingMybatisDao docOrderPackingMybatisDao;
	
	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
	
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

	public List<DocOrderPackingVO> getUndoneList(PageForm form) {

	    MybatisCriteria mybatisCriteria = new MybatisCriteria();
	    mybatisCriteria.setCurrentPage(form.getPageNum());
	    mybatisCriteria.setPageSize(form.getPageSize());
	    List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.queryByList(mybatisCriteria);
	    DocOrderPackingVO docOrderPackingVO;
	    List<DocOrderPackingVO> docOrderPackingVOList = new ArrayList<>();
	    for (DocOrderPacking docOrderPacking : docOrderPackingList) {

	        docOrderPackingVO = new DocOrderPackingVO();
	        BeanUtils.copyProperties(docOrderPacking, docOrderPackingVO);
	        docOrderPackingVOList.add(docOrderPackingVO);
        }
        return docOrderPackingVOList;
    }

	public Json orderStatusCheck(String orderNo) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderNo(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if(orderHeaderForNormal != null){
			if (orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {
				if (orderHeaderForNormal.getPackingFlag().equals("Y")) {
					json.setSuccess(false);
					json.setMsg("装箱失败：当前订单已完成装箱操作！");
					return json;
				} else {
					json.setSuccess(true);
					json.setMsg("000");
					return json;
				}
			} else {
				json.setSuccess(false);
				json.setMsg("装箱失败：当前状态订单不允许进行装箱操作！");
				return json;
			}
		} else {
			json.setSuccess(false);
			json.setMsg("装箱失败：查无此出库单号！");
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

	public Json skuScanCheck(String orderNo, Integer cartonNo, String skuCode) {
		Json json = new Json();
		DocOrderPackingQuery docOrderPackingQuery = new DocOrderPackingQuery();
		docOrderPackingQuery.setOrderNo(orderNo);
		docOrderPackingQuery.setCartonNo(cartonNo);
		docOrderPackingQuery.setSkuCode(skuCode);;
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
		return json;
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

	public Json packingCommit(String orderNo, Integer cartonNo, Double grossWeight, Double cube) {
		Json json = new Json();
		DocOrderPacking docOrderPacking = new DocOrderPacking();
		docOrderPacking.setOrderNo(orderNo);
		docOrderPacking.setCartonNo(cartonNo);
		docOrderPacking.setGrossWeight(grossWeight);
		docOrderPacking.setCube(cube);
		docOrderPackingMybatisDao.packingCartonInfoUpdate(docOrderPacking);
		json.setSuccess(true);
		json.setMsg("装箱完成提交成功！");
		return json;
	}

	public Json orderCommit(String orderNo, Integer cartonNo, Double grossWeight, Double cube) {
		Json json = new Json();
		DocOrderPacking docOrderPacking = new DocOrderPacking();
		docOrderPacking.setOrderNo(orderNo);
		docOrderPacking.setGrossWeight(grossWeight);
		docOrderPacking.setCube(cube);
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
		return json;
	}

	public Json singlePackingCancel(String orderNo, Integer cartonNo) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderNo(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			if (orderHeaderForNormal.getSostatus().equals("70") || orderHeaderForNormal.getSostatus().equals("80")) {
				json.setSuccess(false);
				json.setMsg("取消装箱失败：当前状态订单不允许进行取消装箱操作！");
				return json;
			} else {
				if (orderHeaderForNormal.getPackingFlag().equals("Y")) {
					json.setSuccess(false);
					json.setMsg("取消装箱失败：当前状态订单不允许进行取消装箱操作！");
					return json;
				} else {
					DocOrderPacking docOrderPacking = new DocOrderPacking();
					docOrderPacking.setOrderNo(orderNo);
					docOrderPacking.setCartonNo(cartonNo);
					docOrderPackingMybatisDao.packingCartonDelete(docOrderPacking);
					docOrderPackingMybatisDao.packingCartonInfoDelete(docOrderPacking);
					docOrderPackingMybatisDao.packingCartonFlagUpdate(docOrderPacking);
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
		orderHeaderForNormalQuery.setOrderNo(orderNo);
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
}