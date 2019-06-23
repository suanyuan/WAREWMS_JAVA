//package com.wms.service.importdata;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.sfc.dao.CosCommonAddressDao;
//import com.sfc.dao.CrmMerchantSimpleDao;
//import com.sfc.dao.SfcOrderDao;
//import com.sfc.dao.WmsStockDao;
//import com.sfc.entity.CosCommonAddress;
//import com.sfc.entity.CosCommonAddressContact;
//import com.sfc.entity.CrmMerchantSimple;
//import com.sfc.entity.SfcOrder;
//import com.sfc.entity.SfcOrderDetail;
//import com.sfc.entity.WmsStock;
//import com.sfc.query.CosCommonAddressQuery;
//import com.sfc.query.CrmMerchantSimpleQuery;
//import com.sfc.query.SfcOrderQuery;
//import com.sfc.query.WmsStockQuery;
//import com.sfc.utils.BeanUtils;
//import com.sfc.utils.DateUtil;
//import com.sfc.utils.LoginUtil;
//import com.sfc.utils.RandomUtil;
//import com.sfc.vo.ImportDataDetailVO;
//import com.sfc.vo.ImportDataVO;
//import com.sfc.vo.Json;
//
//@Service("importNormalDataService")
//public class ImportNormalDataService {
//	private static final int HEAD_COUNT = 3;
//	private static final String[] ORDER_ITEM_COLUMN = {"序号","","","","","","","","","","","SKU","数量",""};
//	private static final String[] ORDER_COLUMN = {"序号","订单类型","客户编码","","","","","","送达时间","","需带回单","SKU","数量",""};
//	
//	@Autowired
//	private SfcOrderDao sfcOrderDao;
//	
//	@Autowired
//	private CrmMerchantSimpleDao crmMerchantSimpleDao;
//	
//	@Autowired
//	private WmsStockDao wmsStockDao;
//	
//	@Autowired
//	private CosCommonAddressDao cosCommonAddressDao;
//	
//	public Json importData(MultipartFile csvFile) throws UnsupportedEncodingException, IOException {
//		Json json = new Json();
//		boolean isSuccess = false;
//		StringBuilder resultMsg = new StringBuilder();
//		try(BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), "gb2312"))) {
//			List<String[]> dataArrayList = this.dataToList(br);
//			if(dataArrayList != null && dataArrayList.size() > 0){
//				this.validateRow(dataArrayList, resultMsg);//验证每笔资料是否合法
//				if(resultMsg.length() == 0){
//					List<ImportDataVO> importOrderList = this.dataToBean(dataArrayList, resultMsg);//资料转成Java Bean
//					if(resultMsg.length() == 0){
//						this.validateMerchant(importOrderList, resultMsg);//验证商户是否存在
//						if(resultMsg.length() == 0){
//							this.validateStock(importOrderList, resultMsg);//验证库存资料是否合法
//							if(resultMsg.length() == 0){
//								this.saveOrder(importOrderList, resultMsg);//转成订单资料存入资料库
//								isSuccess = true;
//							}
//						}
//					}
//				}
//			}
//		}
//		json.setMsg(resultMsg.toString());
//		json.setSuccess(isSuccess);
//		return json;
//	}
//
//	private List<String[]> dataToList(BufferedReader br) throws IOException {
//		String line = null;
//		String[] dataArray = null;
//		List<String[]> dataArrayLit = new ArrayList<String[]>();
//		while((line = br.readLine()) != null){
//			if(line.indexOf("序号") > -1) continue;
//			dataArray = line.split(",");
//			if(dataArray == null || dataArray.length == 0 || StringUtils.isEmpty(dataArray[0])) continue;
//			dataArrayLit.add(dataArray);
//		}
//		return dataArrayLit;
//	}
//	
//	private void validateRow(List<String[]> dataArrayList, StringBuilder resultMsg) {
//		int cellNum = 0;
//		String[] columns = null;
//		StringBuilder rowResult = new StringBuilder();
//		for(String[] dataArray : dataArrayList){
//			if(this.isHead(dataArray)){
//				cellNum = ORDER_COLUMN.length;
//				columns = ORDER_COLUMN;
//			}else{
//				cellNum = ORDER_ITEM_COLUMN.length;
//				columns = ORDER_ITEM_COLUMN;
//			}
//			for (int i = 0; i < cellNum; i++) {
//				try {
//					if(StringUtils.isNotEmpty(columns[i]) && StringUtils.isEmpty(dataArray[i])){
//						rowResult.append(columns[i]).append("未输入").append("，");
//					}
//				} catch (Exception e) {
//					rowResult.append(columns[i]).append("未输入").append("，");
//					break;
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
//		ImportDataVO importDataVO = null;
//		ImportDataDetailVO importDataDetailVO = null;
//		StringBuilder rowResult = new StringBuilder();
//		List<ImportDataDetailVO> importDataDeatilList = null;
//		List<ImportDataVO> importDataList = new ArrayList<ImportDataVO>();
//		for(String[] dataArray : dataArrayList){
//			int arrayIndex = 0;
//			String orderRemark = null;
//			String backReceipt = null;
//			String collectAmt = null;
//			String arrivedTime = null;
//			String abbreviation = null;
//			String storeName = null;
//			if(this.isHead(dataArray)){
//				importDataVO = new ImportDataVO();
//				try {
//					importDataVO.setSeq(Integer.parseInt(dataArray[arrayIndex++]));//序号
//					if(importDataVO.getSeq() <= 0){
//						throw new Exception();
//					}
//				} catch (Exception e) {
//					rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
//				} 
//				importDataVO.setOrderType("整车".equals(dataArray[arrayIndex++]) ? 2 : 1);//订单类型
//				try {
//					importDataVO.setMerchantCode(Integer.parseInt(dataArray[arrayIndex++]));//客户编码
//					if(importDataVO.getMerchantCode() <= 0){
//						throw new Exception();
//					}
//				} catch (Exception e) {
//					rowResult.append("[客户编码]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
//				}
//				abbreviation = dataArray[arrayIndex++];
//				storeName = dataArray[arrayIndex++];
//				if(StringUtils.isNotEmpty(abbreviation) && StringUtils.isNotEmpty(storeName)){
//					importDataVO.setAbbreviation(abbreviation);
//					importDataVO.setStoreName(storeName);
//				}
//				importDataVO.setReceAddr(dataArray[arrayIndex++]);//收货人地址
//				importDataVO.setReceName(dataArray[arrayIndex++]);//收货人名称
//				importDataVO.setReceMobile(dataArray[arrayIndex++]);//收货人电话
//				
//				if((StringUtils.isEmpty(importDataVO.getAbbreviation()) || StringUtils.isEmpty(importDataVO.getStoreName())) && 
//					(StringUtils.isEmpty(importDataVO.getReceAddr()) || StringUtils.isEmpty(importDataVO.getReceName()) || StringUtils.isEmpty(importDataVO.getReceMobile()))){
//					rowResult.append("[商户简称、门店名称]或[收货人地址、收货人名称、收货人电话]至少输入一组完整资料").append(" ");
//				}
//				
//				try {
//					arrivedTime = dataArray[arrayIndex++];
//					importDataVO.setArrivedTime(DateUtil.parse(arrivedTime, "yyyy/MM/dd"));//送达时间
//				} catch (Exception e) {
//					rowResult.append("[送达时间]资料格式转换失败，请输入yyyy/MM/dd格式").append(" ");
//				} 
//				try {
//					collectAmt = dataArray[arrayIndex++];
//					if(StringUtils.isNotEmpty(collectAmt)){//是否代收货款
//						importDataVO.setCollect(1);
//						importDataVO.setCollectAmt(Double.parseDouble(collectAmt));//代收货款
//						if(importDataVO.getCollectAmt() < 0){
//							throw new Exception();
//						}
//					}else{
//						importDataVO.setCollect(0);
//					}
//				} catch (Exception e) {
//					rowResult.append("[代收货款]资料格式转换失败，请输入大于0数字格式").append(" ");
//				} 
//				backReceipt = dataArray[arrayIndex++];
//				if(StringUtils.isNotEmpty(backReceipt)){
//					importDataVO.setTakeReceipt("是".equals(backReceipt) ? 1 : 0);//需带回单
//				}
//				
//				importDataDeatilList = new ArrayList<ImportDataDetailVO>();
//				importDataDetailVO = new ImportDataDetailVO();
//				importDataDetailVO.setSkuCode(dataArray[arrayIndex++]);
//				try {
//					importDataDetailVO.setQuantity(Integer.parseInt(dataArray[arrayIndex++]));
//				} catch (Exception e) {
//					rowResult.append("[数量]资料格式转换失败，请输入正整数之数字格式").append(" ");
//				} 
//				importDataDeatilList.add(importDataDetailVO);
//				
//				if(dataArray.length > 13){
//					orderRemark = dataArray[arrayIndex++];
//					if(StringUtils.isNotEmpty(orderRemark)){
//						importDataVO.setOrderRemark(orderRemark);//订单备注
//					}
//				}
//				importDataVO.setImportDataDetailList(importDataDeatilList);
//			}else{
//				importDataDetailVO = new ImportDataDetailVO();
//				importDataDetailVO.setSkuCode(dataArray[11]);
//				try {
//					importDataDetailVO.setQuantity(Integer.parseInt(dataArray[12]));
//				} catch (Exception e) {
//					rowResult.append("[数量]资料格式转换失败，资料格式转换失败，请输入正整数之数字格式").append(" ");
//				} 
//			}
//			
//			if (rowResult.length() > 0) {
//				if(rowResult.lastIndexOf("，") > -1){
//					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
//				}
//				resultMsg.append("序号：").append(dataArray[0]).append("资料有错=>").append(rowResult).append(" ");
//				rowResult.setLength(0);
//			}else{
//				if(this.isHead(dataArray)){
//					importDataList.add(importDataVO);
//				}else{
//					if(importDataVO != null && importDataVO.getImportDataDetailList() != null){
//						importDataVO.getImportDataDetailList().add(importDataDetailVO);
//					}
//				}
//			}
//		}
//		return importDataList;
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
//				resultMsg.append("序号：").append(importDataVO.getSeq()).append("商户代码查无商户资料").append(" ");
//			} else if(merchant.getIsSign() == null || merchant.getIsSign() == 0) {// 商户是否已签约且已付款
//				resultMsg.append("序号：").append(importDataVO.getSeq())
//						 .append("，商户代码:").append(merchant.getMerchantCode())
//						 .append("，商户名称:").append(merchant.getName()).append("，为非签约帐户 ");
//			} else{
//				importDataVO.setMerchantId(merchant.getId());
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
//	private void validateStock(List<ImportDataVO> importDataList, StringBuilder resultMsg) {
//		String skuCode = null;
//		Integer needQuantity = null;
//		WmsStock stock = null;
//		WmsStockQuery stockQuery = null;
//		StringBuilder stockResult = null;
//		List<ImportDataDetailVO> importDataDetailList = null;
//		Map<WmsStockQuery, WmsStockQuery> stockQueryMap = new LinkedHashMap<WmsStockQuery,WmsStockQuery>();
//		for(ImportDataVO importDataVO : importDataList){
//			importDataDetailList = importDataVO.getImportDataDetailList();
//			for(ImportDataDetailVO importDataDetailVO : importDataDetailList){//归类同skuCode，并计算总和
//				needQuantity = importDataDetailVO.getQuantity();
//				skuCode = importDataDetailVO.getSkuCode();
//				
//				stockQuery = new WmsStockQuery();
//				stockQuery.setSkuCode(skuCode);
//				stockQuery.setMerchantCode(importDataVO.getMerchantCode());
//				stockQuery.setNeedQuantity(needQuantity);
//				if(stockQueryMap.get(stockQuery) == null){
//					stockQueryMap.put(stockQuery, stockQuery);
//				}else{
//					stockQuery = stockQueryMap.get(new WmsStockQuery(skuCode));
//					stockQuery.setNeedQuantity(stockQuery.getNeedQuantity() + needQuantity);
//				}
//			}
//		}
//		
//		if(stockQueryMap.size() > 0){
//			stockResult = new StringBuilder();
//			for(WmsStockQuery key : stockQueryMap.keySet()){
//				stockQuery = stockQueryMap.get(key);
//				stock = wmsStockDao.getUniqueByQuery(stockQuery);
//				
//				if(stock == null || stock.getQuantity() == null){
//					stockResult.append("	").append("客户编码为：").append(stockQuery.getMerchantCode())
//					    	   .append("，SKU编码为：").append(stockQuery.getSkuCode()).append("，查无库存资料！").append(" ");
//				}else if(stockQuery.getNeedQuantity() > stock.getQuantity()){
//					stockResult.append("	").append("客户编码为：").append(stockQuery.getMerchantCode())
//							   .append("，SKU编码为：").append(stockQuery.getSkuCode()).append("，库存不足！目前库存为：").append(stock.getQuantity()).append("，导入订单总出货量为：").append(stockQuery.getNeedQuantity()).append(" ");
//				}
//			}
//			
//			if (stockResult.length() > 0) {
//				resultMsg.append("资料有错＝＞").append(" ").append(stockResult).append(" ");
//				stockResult.setLength(0);
//			}
//		}
//	}
//	
//	private void saveOrder(List<ImportDataVO> importOrderList, StringBuilder resultMsg) {
//		SfcOrder order = null;
//		SfcOrderQuery orderQuery = new SfcOrderQuery();
//		CosCommonAddressContact contact = null;
//		List<CosCommonAddressContact> contactList = null;
//		for(ImportDataVO importDataVO : importOrderList){
//			orderQuery.setOrderCode(RandomUtil.genCode(8));
//			while(sfcOrderDao.countAll(orderQuery) > 0){//防止订单号重复
//				orderQuery.setOrderCode(RandomUtil.genCode(8));
//			}
//			order = new SfcOrder();
//			BeanUtils.copyProperties(importDataVO, order);
//			order.setOrderCode(orderQuery.getOrderCode());
//			order.setCreateUser(LoginUtil.getLoginUser());
//			order.setCreateTime(new Date());
//			order.setOrderStatus(0);
//			order.setMerchant(crmMerchantSimpleDao.findById(importDataVO.getMerchantId()));
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
//			int totalQuantity = 0;
//			Double skuVolume = new Double("0");
//			Double skuWeight = new Double("0");
//			WmsStock stock = null;
//			SfcOrderDetail detail = null;
//			WmsStockQuery stockQuery = null;
//			Set<SfcOrderDetail> detailSet = new HashSet<SfcOrderDetail>();
//			for(ImportDataDetailVO importDataDetailVO : importDataVO.getImportDataDetailList()){
//				detail = new SfcOrderDetail();
//				BeanUtils.copyProperties(importDataDetailVO, detail);
//				
//				stockQuery = new WmsStockQuery();
//				stockQuery.setMerchantCode(importDataVO.getMerchantCode());
//				stockQuery.setSkuCode(importDataDetailVO.getSkuCode());
//				stock = wmsStockDao.getUniqueByQuery(stockQuery);
//				stock.setQuantity(stock.getQuantity() - importDataDetailVO.getQuantity());
//				detail.setStock(stock);
//				detailSet.add(detail);
//				totalQuantity += importDataDetailVO.getQuantity();
//			}
//			order.setTotalNum(totalQuantity);
//			order.setTotalVolume(skuVolume);
//			order.setTotalWeight(skuWeight);
//			order.setDetailSet(detailSet);
//			order.setTimeExt(0);
//			sfcOrderDao.save(order);
//			resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料汇入成功，订单号为：").append(order.getOrderCode()).append(" ");
//		}
//	}
//
//	/**
//	 * 验证资料是订单主表还是子表
//	 * @param dataArray
//	 * @return
//	 */
//	private boolean isHead(String[] dataArray) {
//		int count = 0;
//		boolean result = false;
//		for(int i = 0; i < ORDER_COLUMN.length; i++){
//			try {
//				if(StringUtils.isNotEmpty(ORDER_COLUMN[i]) && StringUtils.isNotEmpty(dataArray[i])){
//					count++;
//				}
//			} catch (Exception e) {
//				break;
//			}
//		}
//		if(count > HEAD_COUNT){
//			result = true;
//		}
//		return result;
//	}
//}
