package com.wms.service.importdata;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wms.entity.BasSku;
import com.wms.entity.BasCustomer;
import com.wms.entity.DocOrderDetail;
import com.wms.entity.DocOrderHeader;
import com.wms.entity.ImportOrderData;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.DocOrderDetailsMybatisDao;
import com.wms.mybatis.dao.DocOrderHeaderMybatisDao;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocOrderDetailQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.DocOrderDetailVO;
import com.wms.vo.DocOrderHeaderVO;
import com.wms.vo.Json;

@Service("ImportOrderDataService")
public class ImportOrderDataService {

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;
	@Autowired
	private DocOrderHeaderMybatisDao docOrderHeaderMybatisDao;
	@Autowired
	private DocOrderDetailsMybatisDao docOrderDetailsMybatisDao;
	/**
	 * 导入商品资料
	 * @param xlsFile
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public Json importExcelData(MultipartFile excelFile) {
		Json json = new Json();
		boolean isSuccess = false;
		StringBuilder resultMsg = new StringBuilder();
		InputStream in;
		try {  
            // 获取前台exce的输入流
            in = excelFile.getInputStream();
            
            //获取sheetName名字
            String sheetName = "订单明细";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] { "序号" };
            //获取需要导入的具体的表
            Class order = new ImportOrderData().getClass();
            //excel转化成的list集合
            List<ImportOrderData> orderList = null;
            try {
                //调用excle共用类，转化成list
            	orderList = ExcelUtil.excelToList(in, sheetName, order, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<DocOrderHeaderVO> importDataList = this.listToBean(orderList, resultMsg);
            if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
				this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
				if (resultMsg.length() == 0) {
					this.validateCustomerPermission(importDataList, resultMsg);// 验证客户权限是否存在
					if (resultMsg.length() == 0) {
						this.validateSku(importDataList, resultMsg);// 验证商品是否存在
						if (resultMsg.length() == 0) {
							this.saveOrder(importDataList, resultMsg);// 转成订单资料存入资料库
							isSuccess = true;
						}
					}
				}
			}
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		json.setMsg(resultMsg.toString());
		json.setSuccess(isSuccess);
		return json;
	}
	
	private List<DocOrderHeaderVO> listToBean(List<ImportOrderData> orderList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<DocOrderHeaderVO> importData = new ArrayList<DocOrderHeaderVO>();
		DocOrderHeaderVO importDataVO = null;
		List<DocOrderDetailVO> importDetailsDataVOList = new ArrayList<DocOrderDetailVO>();
		DocOrderDetailVO importDetailsDataVO = null;
		String quantityData = null;
		Integer count = 1;
		String customerid = "", soreference1 = "", soreference2 = "", consigneeid = "", cAddress1 = "", cTel1 = "";
		
		for(ImportOrderData dataArray : orderList) {
			try {
				if (Integer.parseInt(dataArray.getSeq()) <= 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getCustomerid().toUpperCase())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[客户代码]，未输入").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getSoreference1())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[客户单号]，未输入").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getConsigneeid())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[收货人]，未输入").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getcAddress1())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[收货地址]，未输入").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getcTel1())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[收货人电话]，未输入").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getSku())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[产品]，未输入").append(" ");
			}
			try {
				quantityData = dataArray.getQtyordered();
				if(StringUtils.isNotEmpty(quantityData)){
					if ((new BigDecimal(quantityData)).compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					} else if ((new BigDecimal(quantityData)).intValue() <= 0) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[订单数量]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			
			if (rowResult.length() > 0) {
				if(rowResult.lastIndexOf("，") > -1){
					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				}
				resultMsg.append("序号：").append(dataArray.getSeq()).append("资料有错 ").append(rowResult).append(" ");
				rowResult.setLength(0);
			} else {
			
				if (count == 1) {
					//第一行操作
					importDataVO = new DocOrderHeaderVO();
					importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
					importDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());//客户代码
					importDataVO.setSoreference1(dataArray.getSoreference1());
					importDataVO.setConsigneename(dataArray.getConsigneeid());
					importDataVO.setCAddress1(dataArray.getcAddress1());
					importDataVO.setCTel1(dataArray.getcTel1());
					
					importDetailsDataVO = new DocOrderDetailVO();
					importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
					importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
					importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
					importDetailsDataVO.setQtyordered(new BigDecimal(dataArray.getQtyordered()));
					importDetailsDataVOList.add(importDetailsDataVO);
				} else if (dataArray.getCustomerid().toUpperCase().equals(customerid) &&
					dataArray.getSoreference1().equals(soreference1) &&
					dataArray.getConsigneeid().equals(consigneeid) &&
					dataArray.getcAddress1().equals(cAddress1) &&
					dataArray.getcTel1().equals(cTel1)) {
					//表头信息一致则只增加明细信息
					importDetailsDataVO = new DocOrderDetailVO();
					importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
					importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
					importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
					importDetailsDataVO.setQtyordered(new BigDecimal(dataArray.getQtyordered()));
					importDetailsDataVOList.add(importDetailsDataVO);
				} else {
					//表头信息不一致则生成新的订单
					importDataVO.setDocOrderDetailVOList(importDetailsDataVOList);
					importData.add(importDataVO);
					importDetailsDataVOList = new ArrayList<DocOrderDetailVO>();
					importDataVO = new DocOrderHeaderVO();
					
					importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
					importDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());//客户代码
					importDataVO.setSoreference1(dataArray.getSoreference1());
					importDataVO.setConsigneename(dataArray.getConsigneeid());
					importDataVO.setCAddress1(dataArray.getcAddress1());
					importDataVO.setCTel1(dataArray.getcTel1());

					importDetailsDataVO = new DocOrderDetailVO();
					importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
					importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
					importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
					importDetailsDataVO.setQtyordered(new BigDecimal(dataArray.getQtyordered()));
					importDetailsDataVOList.add(importDetailsDataVO);
				}
				//最后一行结束操作
				if (count == orderList.size()) {
					importDataVO.setDocOrderDetailVOList(importDetailsDataVOList);
					importData.add(importDataVO);
				}
			}
			
			customerid = importDataVO.getCustomerid();
			soreference1 = importDataVO.getSoreference1();
			soreference2 = importDataVO.getSoreference2();
			consigneeid = importDataVO.getConsigneename();
			cAddress1 = importDataVO.getCAddress1();
			cTel1 = importDataVO.getCTel1();
			count = count + 1;
		}
		for (DocOrderHeaderVO aa : importData) {
        	System.out.println(aa.getSeq());
        	System.out.println(aa.getCustomerid());
        }
		return importData;
	}

	public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
	    // excel的表头与文字对应
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	    map.put("序号", "seq");
	    map.put("客户代码", "customerid");
	    map.put("客户单号1", "soreference1");
	    map.put("客户单号2", "soreference2");
	    map.put("收货人", "consigneeid");
	    map.put("收货地址", "cAddress1");
	    map.put("收货人电话", "cTel1");
	    map.put("产品", "sku");
	    map.put("订单数量", "qtyordered");
	  
	    return map;
	}
	
	private void validateSku(List<DocOrderHeaderVO> importDataList, StringBuilder resultMsg) {
		BasSku sku = null;
		BasSkuQuery skuQuery = new BasSkuQuery();
		for (DocOrderHeaderVO importDataVO : importDataList) {
			for (DocOrderDetailVO importDetailsDataVO : importDataVO.getDocOrderDetailVOList()) {
				skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
				skuQuery.setSku(importDetailsDataVO.getSku());
				sku = basSkuMybatisDao.queryById(skuQuery);
				if(sku == null){
					resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
							 .append("，客户代码：").append(importDataVO.getCustomerid())
							 .append("，产品代码：").append(importDetailsDataVO.getSku()).append("，产品代码查无商品资料").append(" ");
				}
			}
		}
	}

	private void validateCustomer(List<DocOrderHeaderVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (DocOrderHeaderVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customer = basCustomerMybatisDao.queryById(customerQuery);
			if (customer == null) {// 是否有客户资料
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，客户代码查无客户资料").append(" ");
			}
		}
	}

	private void validateCustomerPermission(List<DocOrderHeaderVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (DocOrderHeaderVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customerQuery.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			customer = basCustomerMybatisDao.queryById(customerQuery);
			if (customer == null) {// 是否有客户权限
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，客户代码查无客户权限").append(" ");
			}
		}
	}

	@Transactional
	private void saveOrder(List<DocOrderHeaderVO> importDataList, StringBuilder resultMsg) {
		DocOrderHeader orderHeader = null;
		for(DocOrderHeaderVO importDataVO : importDataList){
			orderHeader = new DocOrderHeader();
			BeanUtils.copyProperties(importDataVO, orderHeader);
			//获取SO编号
			Map<String ,Object> map=new HashMap<String, Object>();
			map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			docOrderHeaderMybatisDao.getIdSequence(map);
			String resultCode = map.get("resultCode").toString();
			String resultNo = map.get("resultNo").toString();
			if (resultCode.substring(0,3).equals("000")) {
				//赋值
				orderHeader.setOrderno(resultNo);
				orderHeader.setOrdertype("SO");
				orderHeader.setReleasestatus("Y");
				orderHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				orderHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
				orderHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
				//保存订单主信息
				docOrderHeaderMybatisDao.add(orderHeader);
				for (DocOrderDetailVO importDetailsDataVO : importDataVO.getDocOrderDetailVOList()) {
					DocOrderDetail orderDetails = new DocOrderDetail();
					BeanUtils.copyProperties(importDetailsDataVO, orderDetails);
					orderDetails.setOrderno(resultNo);
					DocOrderDetailQuery docOrderDetailQuery = new DocOrderDetailQuery();
					docOrderDetailQuery.setOrderno(resultNo);
					//获取订单明细行号
					long orderlineno = docOrderDetailsMybatisDao.getOrderlinenoById(docOrderDetailQuery);
					//获取SKU信息(条码、包装、重量、体积、金额)
					BasSkuQuery skuQuery = new BasSkuQuery();
					skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
					skuQuery.setSku(importDetailsDataVO.getSku());
					skuQuery.setQty(importDetailsDataVO.getQtyordered());
					BasSku basSku = basSkuMybatisDao.queryBySkuInfo(skuQuery);
					//赋值
					orderDetails.setOrderlineno(orderlineno + 1);
					orderDetails.setPackid(basSku.getPackid());
					orderDetails.setAlternativesku(basSku.getAlternateSku1());
					orderDetails.setGrossweight(basSku.getGrossweight());
					orderDetails.setCubic(basSku.getCube());
					orderDetails.setPrice(basSku.getPrice());
					orderDetails.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
					orderDetails.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
					//保存订单明细信息
					docOrderDetailsMybatisDao.add(orderDetails);
				}
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
			} else {
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("SO号获取失败").append(" ");
			}
		}
	}
}
