//package com.wms.service.importdata;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.avalon.framework.configuration.ConfigurationException;
//import org.apache.commons.lang.StringUtils;
//import org.krysalis.barcode4j.BarcodeException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.xml.sax.SAXException;
//
//import com.sfc.dao.CosCommonAddressDao;
//import com.sfc.dao.CrmMerchantSimpleDao;
//import com.sfc.dao.SfcOrderDao;
//import com.sfc.entity.CosCommonAddress;
//import com.sfc.entity.CosCommonAddressContact;
//import com.sfc.entity.CrmMerchantSimple;
//import com.sfc.entity.SfcOrder;
//import com.sfc.query.CosCommonAddressQuery;
//import com.sfc.query.CrmMerchantSimpleQuery;
//import com.sfc.query.SfcOrderQuery;
//import com.sfc.utils.BeanUtils;
//import com.sfc.utils.DateUtil;
//import com.sfc.utils.LoginUtil;
//import com.sfc.utils.RandomUtil;
//import com.sfc.vo.ImportDataVO;
//import com.sfc.vo.Json;
//
///**
// * 订单导入Service
// * 
// * @author owenHuang
// *
// */
//@Service("importCrossingDockDataService")
//public class ImportCrossingDockDataService {
////	private static Logger log = Logger.getLogger(ImportCrossingDockDataService.class);
//
//	private static final String[] ORDER_COLUMN = { "序号", "客户ID", "", "", "", "", "", "", "", "到库时间", "送达时间", "发货件数","","" };
//
//	@Autowired
//	private SfcOrderDao sfcOrderDao;
//	
//	@Autowired
//	private CrmMerchantSimpleDao crmMerchantSimpleDao;
//	
//	@Autowired
//	private CosCommonAddressDao cosCommonAddressDao;
//	/**
//	 * 导入一般订单
//	 * 
//	 * @param csvFile
//	 * @return
//	 * @throws IOException 
//	 * @throws UnsupportedEncodingException 
//	 * @throws SAXException 
//	 * @throws BarcodeException 
//	 * @throws ConfigurationException 
//	 * @throws Exception 
//	 */
//	public Json importData(MultipartFile csvFile) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
//		Json json = new Json();
//		boolean isSuccess = false;
//		StringBuilder resultMsg = new StringBuilder();
//		try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), "gb2312"))) {
//			List<String[]> dataArrayList = this.dataToList(br);
//			if (dataArrayList != null && dataArrayList.size() > 0) {
//				this.validateRow(dataArrayList, resultMsg);// 验证每笔资料是否合法
//				if (resultMsg.length() == 0) {
//					List<ImportDataVO> importOrderList = this.dataToBean(dataArrayList, resultMsg);// 资料转成Java Bean
//					if (resultMsg.length() == 0 && importOrderList != null && importOrderList.size() > 0) {
//						this.validateMerchant(importOrderList, resultMsg);// 验证商户是否存在
//						if (resultMsg.length() == 0) {
//							this.saveOrder(importOrderList, resultMsg);// 转成订单资料存入资料库
//							isSuccess = true;
//						}
//					}
//				}
//			}else{
//				resultMsg.append("档案中无资料可以导入，请确认档案内容！");
//			}
//		} 
//		json.setMsg(resultMsg.toString());
//		json.setSuccess(isSuccess);
//		return json;
//	}
//
//	private List<String[]> dataToList(BufferedReader br) throws IOException {
//		List<String[]> dataArrayLit = null;
//		String line = null;
//		String[] dataArray = null;
//		dataArrayLit = new ArrayList<String[]>();
//		while ((line = br.readLine()) != null) {
//			if (line.length() == 0 || line.indexOf("序号") > -1) continue;
//			dataArray = line.split(",");
//			if (dataArray == null || dataArray.length == 0 || StringUtils.isEmpty(dataArray[0])) continue;
//			
//			for (int i = 0; i < dataArray.length; i++) {
//				if (StringUtils.isNotEmpty(dataArray[i])) {
//					dataArray[i] = dataArray[i].trim();
//				}
//			}
//			dataArrayLit.add(dataArray);
//		}
//		return dataArrayLit;
//	}
//
//	private void validateRow(List<String[]> dataArrayList, StringBuilder resultMsg) {
//		StringBuilder rowResult = new StringBuilder();
//		for (String[] dataArray : dataArrayList) {
//			for (int i = 0; i < ORDER_COLUMN.length; i++) {
//				if (StringUtils.isNotEmpty(ORDER_COLUMN[i]) && dataArray.length > i && StringUtils.isEmpty(dataArray[i])) {
//					rowResult.append(ORDER_COLUMN[i]).append("错误").append("，");
//				}
//			}
//			if (rowResult.length() > 0) {
//				rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
//				resultMsg.append("序号：").append(dataArray[0]).append("资料未输入=>").append(rowResult).append(" ");
//				rowResult.setLength(0);
//			}
//		}
//	}
//
//	private List<ImportDataVO> dataToBean(List<String[]> dataArrayList, StringBuilder resultMsg) {
//		StringBuilder rowResult = new StringBuilder();
//		List<ImportDataVO> importOrderList = new ArrayList<ImportDataVO>();
//		ImportDataVO importDataVO = null;
//		for (String[] dataArray : dataArrayList) {
//			int arrayIndex = 0;
//			String receAddr = null;
//			String backReceipt = null;
//			String collectAmt = null;
//			String arrivedTime = null;
//			String orderRemark = null;
//			String abbreviation = null;
//			String storeName = null;
//			importDataVO = new ImportDataVO();
//			try {
//				importDataVO.setSeq(Integer.parseInt(dataArray[arrayIndex++]));// 序号
//				if (importDataVO.getSeq() <= 0) {
//					throw new Exception();
//				}
//			} catch (Exception e) {
//				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
//			}
//			try {
//				importDataVO.setMerchantCode(Integer.parseInt(dataArray[arrayIndex++]));// 客户编码
//				if (importDataVO.getMerchantCode() <= 0) {
//					throw new Exception();
//				}
//			} catch (Exception e) {
//				rowResult.append("[客户编码]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
//			}
//			importDataVO.setExternalOrderCode(dataArray[arrayIndex++]);// 外部订单号
//			
//			abbreviation = dataArray[arrayIndex++];
//			storeName = dataArray[arrayIndex++];
//			if(StringUtils.isNotEmpty(abbreviation) && StringUtils.isNotEmpty(storeName)){
//				importDataVO.setAbbreviation(abbreviation);
//				importDataVO.setStoreName(storeName);
//			}
//			importDataVO.setReceAddr(dataArray[arrayIndex++]);// 收货人地址
//			receAddr = dataArray[arrayIndex++];
//			if (StringUtils.isNotEmpty(receAddr)) {
//				importDataVO.setReceAddrRemark(receAddr);// 详细门牌号
//			}
//			importDataVO.setReceName(dataArray[arrayIndex++]);// 收货人名称
//			importDataVO.setReceMobile(dataArray[arrayIndex++]);// 收货人电话
//
//			if((StringUtils.isEmpty(importDataVO.getAbbreviation()) || StringUtils.isEmpty(importDataVO.getStoreName())) && 
//				(StringUtils.isEmpty(importDataVO.getReceAddr()) || StringUtils.isEmpty(importDataVO.getReceName()) || StringUtils.isEmpty(importDataVO.getReceMobile()))){
//				rowResult.append("[商户简称、门店名称]或[收货人地址、收货人名称、收货人电话]至少输入一组完整资料").append(" ");
//			}
//			
//			try {
//				importDataVO.setArrivedWarehouseTime(DateUtil.parse(dataArray[arrayIndex++], "yyyy/MM/dd"));// 到库时间
//			} catch (Exception e) {
//				rowResult.append("[到库时间]资料格式转换失败，请输入yyyy/MM/dd格式").append(" ");
//			}
//			try {
//				arrivedTime = dataArray[arrayIndex++];
//				importDataVO.setArrivedTime(DateUtil.parse(arrivedTime,"yyyy/MM/dd"));// 送达时间
//			} catch (Exception e) {
//				rowResult.append("[送达时间]资料格式转换失败，请输入yyyy/MM/dd格式").append(" ");
//			}
//			try {
//				importDataVO.setTotalNum(Integer.parseInt(dataArray[arrayIndex++]));// 发货件数
//				if (importDataVO.getTotalNum() <= 0) {
//					throw new Exception();
//				}
//			} catch (Exception e) {
//				rowResult.append("[发货件数]资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
//			}
//			backReceipt = dataArray[arrayIndex++];
//			if (StringUtils.isNotEmpty(backReceipt)) {
//				importDataVO.setTakeReceipt("是".equals(backReceipt) ? 1 : 0);// 需带回单
//			}
//			try {
//				if (dataArray.length > 13) {
//					collectAmt = dataArray[arrayIndex++];
//					if (StringUtils.isNotEmpty(collectAmt)) {// 是否代收货款
//						importDataVO.setCollect(1);
//						importDataVO.setCollectAmt(Double.parseDouble(collectAmt));// 代收货款
//						if (importDataVO.getCollectAmt() < 0) {
//							throw new Exception();
//						}
//					} else {
//						importDataVO.setCollect(0);
//					}
//				} else {
//					importDataVO.setCollect(0);
//				}
//			} catch (Exception e) {
//				rowResult.append("[代收货款]资料格式转换失败，请输入大于0数字格式").append(" ");
//			}
//
//			if(dataArray.length > 14){
//				orderRemark = dataArray[arrayIndex++];
//				if(StringUtils.isNotEmpty(orderRemark)){
//					importDataVO.setOrderRemark(orderRemark);//订单备注
//				}
//			}
//			
//			if (rowResult.length() > 0) {
//				if(rowResult.lastIndexOf("，") > -1){
//					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
//				}
//				resultMsg.append("序号：").append(dataArray[0]).append("资料有错 ").append(rowResult).append(" ");
//				rowResult.setLength(0);
//			} else {
//				importOrderList.add(importDataVO);
//			}
//		}
//		return importOrderList;
//	}
//
//	private void validateMerchant(List<ImportDataVO> importOrderList, StringBuilder resultMsg) {
//		CrmMerchantSimple merchant = null;
//		CosCommonAddress address = null;
//		CrmMerchantSimpleQuery merchantQuery = new CrmMerchantSimpleQuery();
//		CosCommonAddressQuery addressQuery = new CosCommonAddressQuery();
//		for (ImportDataVO importDataVO : importOrderList) {
//			merchantQuery.setMerchantCode(importDataVO.getMerchantCode());
//			merchant = crmMerchantSimpleDao.getUniqueByQuery(merchantQuery);
//			
//			if (merchant == null) {// 是否有商户资料
//				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，商户代码查无商户资料").append(" ");
//			}else if (merchant.getIsSign() == null || merchant.getIsSign() == 0) {// 商户是否已签约且已付款
//				resultMsg.append("序号：").append(importDataVO.getSeq())
//				 		 .append("，商户代码:").append(merchant.getMerchantCode())
//				 		 .append("，商户名称:").append(merchant.getName()).append("，为非签约帐户 ");
//			}else{
//				importDataVO.setMerchant(merchant);
//				if(StringUtils.isNotEmpty(importDataVO.getAbbreviation()) && StringUtils.isNotEmpty(importDataVO.getStoreName())){
//					if(importDataVO.getAbbreviation().equals(merchant.getMerchantAbbreviation())){
//						addressQuery.setName(importDataVO.getStoreName());
//						addressQuery.setMerchantId(merchant.getId());
//						address = cosCommonAddressDao.getUniqueByQuery(addressQuery);
//						if(address == null){
//							resultMsg.append("序号：").append(importDataVO.getSeq())
//									 .append("，门店名称:").append(importDataVO.getStoreName()).append("，查无资料 ");
//						}else{
//							importDataVO.setAddress(address);
//						}
//					}else{
//						resultMsg.append("序号：").append(importDataVO.getSeq())
//						 		 .append("，商户简称:").append(importDataVO.getAbbreviation()).append("，查无资料 ");
//					}
//				}
//			}
//		}
//	}
//
//	private void saveOrder(List<ImportDataVO> importOrderList, StringBuilder resultMsg) throws ConfigurationException, BarcodeException, SAXException, IOException {
//		Date today = new Date();
//		SfcOrder order = null;
//		CosCommonAddressContact contact = null;
//		SfcOrderQuery orderQuery = new SfcOrderQuery();
//		List<CosCommonAddressContact> contactList = null;
//		for (ImportDataVO importDataVO : importOrderList) {
//			orderQuery.setOrderCode(RandomUtil.genCode(8));
//			while(sfcOrderDao.countAll(orderQuery) > 0){//防止订单号重复
//				orderQuery.setOrderCode(RandomUtil.genCode(8));
//			}
//			order = new SfcOrder();
//			BeanUtils.copyProperties(importDataVO, order);
//			if(importDataVO.getAddress() != null){
//				order.setReceAddr(importDataVO.getAddress().getBaiduAddress());
//				order.setReceAddrRemark(importDataVO.getAddress().getAddress());
//				order.setReceAddressName(importDataVO.getAddress().getName());
//				order.setReceLng(importDataVO.getAddress().getLng());
//				order.setReceLat(importDataVO.getAddress().getLat());
//				order.setMileage(importDataVO.getAddress().getMileage());
//				if(StringUtils.isEmpty(order.getReceName()) && StringUtils.isEmpty(order.getReceMobile()) && importDataVO.getAddress().getContactSet() != null && importDataVO.getAddress().getContactSet().size() > 0){
//					contactList = new ArrayList<CosCommonAddressContact>();
//					contactList.addAll(importDataVO.getAddress().getContactSet());
//					contact = contactList.get(0);
//					order.setReceName(contact.getReceName());
//					order.setReceMobile(contact.getReceMobile());
//				}
//			}
//			order.setOrderCode(RandomUtil.genCode(8));
//			order.setOrderType(3);
//			order.setCreateTime(new Date());
//			order.setOrderStatus(0);
//			order.setTimeExt(0);
//			order.setMerchant(importDataVO.getMerchant());
//			order.setCreateUser(LoginUtil.getLoginUser());
//			order.setCreateTime(today);
//			
//			sfcOrderDao.save(order);
//			resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料汇入成功，订单号为：").append(order.getOrderCode()).append(" ");
//		}
//	}
//}
