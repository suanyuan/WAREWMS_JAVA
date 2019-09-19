package com.wms.service.importdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wms.entity.BasSku;
import com.wms.entity.BasCustomer;
import com.wms.entity.ImportSkuData;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasSkuQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.ImportSkuDataVO;
import com.wms.vo.Json;

@Service("ImportSkuDataService")
public class ImportSkuDataService {

	private static final String[] SKU_COLUMN = {"序号","客户代码","产品","中文名称","英文名称","条形码","包装代码","单位","体积","重量","单价"};
	
	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;
	/**
	 * 导入商品资料
	 * @param csvFile
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public Json importData(MultipartFile csvFile) throws UnsupportedEncodingException, IOException {
		Json json = new Json();
		boolean isSuccess = false;
		StringBuilder resultMsg = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), "gb2312"))) {
			List<String[]> dataArrayList = this.dataToList(br);
			if (dataArrayList != null && dataArrayList.size() > 0) {
				this.validateRow(dataArrayList, resultMsg);// 验证每笔资料是否合法
					List<ImportSkuDataVO> importDataList = this.dataToBean(dataArrayList, resultMsg);// 资料转成Java Bean
					//this.saveSku(importDataList, resultMsg);
 					if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
 						this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
						if (resultMsg.length() == 0) {
							this.validateSku(importDataList, resultMsg);// 验证商品是否存在
							if (resultMsg.length() == 0) {
								this.saveSku(importDataList, resultMsg);// 转成商品资料存入资料库
								isSuccess = true;
							}
						}
 					}
			}else{
				resultMsg.append("档案中无资料可以导入，请确认档案内容！");
			}
		} 
		json.setMsg(resultMsg.toString());
		json.setSuccess(isSuccess);
		return json;
	}

	private List<String[]> dataToList(BufferedReader br) throws IOException {
		List<String[]> dataArrayLit = null;
		String line = null;
		String[] dataArray = null;
		dataArrayLit = new ArrayList<String[]>();
		while ((line = br.readLine()) != null) {
			if (line.length() == 0 || line.indexOf("序号") > -1) continue;
			dataArray = line.split(",");
			if (dataArray == null || dataArray.length == 0 || StringUtils.isEmpty(dataArray[0])) continue;
			for (int i = 0; i < dataArray.length; i++) {
				if (StringUtils.isNotEmpty(dataArray[i])) {
					dataArray[i] = dataArray[i].trim();
				}
			}
			dataArrayLit.add(dataArray);
		}
		return dataArrayLit;
	}

	private void validateRow(List<String[]> dataArrayList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		for (String[] dataArray : dataArrayList) {
			for (int i = 0; i < SKU_COLUMN.length; i++) {
				if (StringUtils.isNotEmpty(SKU_COLUMN[i]) && dataArray.length > i && StringUtils.isEmpty(dataArray[i])) {
					rowResult.append(SKU_COLUMN[i]).append("错误").append("，");
				}
			}
			if (rowResult.length() > 0) {
				rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				resultMsg.append("序号：").append(dataArray[0]).append("资料未输入=>").append(rowResult).append(" ");
				rowResult.setLength(0);
			}
		}
	}

	private List<ImportSkuDataVO> dataToBean(List<String[]> dataArrayList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<ImportSkuDataVO> importData = new ArrayList<ImportSkuDataVO>();
		ImportSkuDataVO importDataVO = null;
		String quantityData = null;
		for(String[] dataArray : dataArrayList){
			int arrayIndex = 0;
			importDataVO = new ImportSkuDataVO();
			try {
				importDataVO.setSeq(Integer.parseInt(dataArray[arrayIndex++]));// 序号
				if (importDataVO.getSeq() <= 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}
			try {
				importDataVO.setCustomerid(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getCustomerid())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[客户代码]，未输入").append(" ");
			}
			try {
				importDataVO.setSku(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getSku())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[商品代码]，未输入").append(" ");
			}
			try {
				importDataVO.setDescrC(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getDescrC())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[中文名称]，未输入").append(" ");
			}
			try {
				importDataVO.setDescrE(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getDescrE())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[英文名称]，未输入").append(" ");
			}
			try {
				importDataVO.setAlternateSku1(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getAlternateSku1())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[条形码]，未输入").append(" ");
			}
			try {
				importDataVO.setPackid(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getPackid())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[包装代码]，未输入").append(" ");
			}
			try {
				importDataVO.setReservedfield01(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getReservedfield01())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[单位]，未输入").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCube(new BigDecimal(quantityData));
					if (importDataVO.getCube().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[体积]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setGrossweight(new BigDecimal(quantityData));
					if (importDataVO.getGrossweight().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[重量]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setPrice(new BigDecimal(quantityData));
					if (importDataVO.getPrice().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[单价]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			if (rowResult.length() > 0) {
				if(rowResult.lastIndexOf("，") > -1){
					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				}
				resultMsg.append("序号：").append(dataArray[0]).append("资料有错 ").append(rowResult).append(" ");
				rowResult.setLength(0);
			} else {
				importData.add(importDataVO);
			}
		}
		return importData;
	}

	public Json importExcelData(MultipartFile excelFile) {
		Json json = new Json();
		boolean isSuccess = false;
		StringBuilder resultMsg = new StringBuilder();
		InputStream in;
		try {  
            // 获取前台exce的输入流
            in = excelFile.getInputStream();
            
            //获取sheetName名字
            String sheetName = "产品档案";
            // excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] { "客户代码", "产品" };
            //获取需要导入的具体的表
            Class basSku = new ImportSkuData().getClass();
            //excel转化成的list集合
            List<ImportSkuData> basSkuList = null;
            try {
                //调用excle共用类，转化成list
            	basSkuList = ExcelUtil.excelToList(in, sheetName, basSku, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<ImportSkuDataVO> importDataList = this.listToBean(basSkuList, resultMsg);
            if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
				this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
				if (resultMsg.length() == 0) {
					this.validateCustomerPermission(importDataList, resultMsg);// 验证客户权限是否存在
					if (resultMsg.length() == 0) {
						this.validateSku(importDataList, resultMsg);// 验证商品是否存在
						if (resultMsg.length() == 0) {
							this.saveSku(importDataList, resultMsg);// 转成商品资料存入资料库
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
	
	private List<ImportSkuDataVO> listToBean(List<ImportSkuData> basSkuList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<ImportSkuDataVO> importData = new ArrayList<ImportSkuDataVO>();
		ImportSkuDataVO importDataVO = null;
		String quantityData = null;
		for(ImportSkuData dataArray : basSkuList){
			importDataVO = new ImportSkuDataVO();
			try {
				importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
				if (importDataVO.getSeq() <= 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}
			try {
//				importDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
				importDataVO.setCustomerid(dataArray.getCustomerid());
				if (StringUtils.isEmpty(importDataVO.getCustomerid())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[客户代码]，未输入").append(" ");
			}
			try {
//				importDataVO.setSku(dataArray.getSku().toUpperCase());
				importDataVO.setSku(dataArray.getSku());
				if (StringUtils.isEmpty(importDataVO.getSku())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[商品代码]，未输入").append(" ");
			}
			try {
				importDataVO.setDescrC(dataArray.getDescrC());
				if (StringUtils.isEmpty(importDataVO.getDescrC())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[中文名称]，未输入").append(" ");
			}
			try {
				importDataVO.setDescrE(dataArray.getDescrE());
				if (StringUtils.isEmpty(importDataVO.getDescrE())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[英文名称]，未输入").append(" ");
			}
			try {
				importDataVO.setAlternateSku1(dataArray.getAlternateSku1());
				if (StringUtils.isEmpty(importDataVO.getAlternateSku1())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[条形码]，未输入").append(" ");
			}
			try {
				importDataVO.setPackid(dataArray.getPackid());
				if (StringUtils.isEmpty(importDataVO.getPackid())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[包装代码]，未输入").append(" ");
			}
			try {
				importDataVO.setReservedfield01(dataArray.getReservedfield01());
				if (StringUtils.isEmpty(importDataVO.getReservedfield01())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[单位]，未输入").append(" ");
			}
			try {
				quantityData = dataArray.getCube();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCube(new BigDecimal(quantityData));
					if (importDataVO.getCube().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[体积]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getGrossweight();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setGrossweight(new BigDecimal(quantityData));
					if (importDataVO.getGrossweight().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[重量]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getPrice();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setPrice(new BigDecimal(quantityData));
					if (importDataVO.getPrice().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[单价]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			if (rowResult.length() > 0) {
				if(rowResult.lastIndexOf("，") > -1){
					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				}
				resultMsg.append("序号：").append(dataArray.getSeq()).append("资料有错 ").append(rowResult).append(" ");
				rowResult.setLength(0);
			} else {
				importData.add(importDataVO);
			}
		}
		return importData;
	}

	public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
	    // excel的表头与文字对应
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	    map.put("序号", "seq");
	    map.put("客户代码", "customerid");
	    map.put("产品", "sku");
	    map.put("中文名称", "descrC");
	    map.put("英文名称", "descrE");
	    map.put("条形码", "alternateSku1");
	    map.put("包装代码", "packid");
	    map.put("单位", "reservedfield01");
	    map.put("体积", "cube");
	    map.put("重量", "grossweight");
	    map.put("单价", "price");
	  
	    return map;
	}
	
	private void validateSku(List<ImportSkuDataVO> importDataList, StringBuilder resultMsg) {
		BasSku sku = null;
		BasSkuQuery skuQuery = new BasSkuQuery();
		for (ImportSkuDataVO importDataVO : importDataList) {
			skuQuery.setCustomerid(importDataVO.getCustomerid());
			skuQuery.setSku(importDataVO.getSku());
			sku = basSkuMybatisDao.queryById(skuQuery);
			if(sku != null){
				resultMsg.append("序号：").append(importDataVO.getSeq())
						 .append("，客户代码：").append(importDataVO.getCustomerid())
						 .append("，商品代码：").append(importDataVO.getSku()).append("，重复").append(" ");
			}
		}
	}

	private void validateCustomer(List<ImportSkuDataVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (ImportSkuDataVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customer = basCustomerMybatisDao.queryById(customerQuery);
			if (customer == null) {// 是否有客户资料
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，客户代码查无客户资料").append(" ");
			}
		}
	}

	private void validateCustomerPermission(List<ImportSkuDataVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (ImportSkuDataVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customerQuery.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			customer = basCustomerMybatisDao.queryById(customerQuery);
			if (customer == null) {// 是否有客户权限
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，客户代码查无客户权限").append(" ");
			}
		}
	}

	private void saveSku(List<ImportSkuDataVO> importDataList, StringBuilder resultMsg) {
		BasSku sku = null;
		for(ImportSkuDataVO importDataVO : importDataList){
			sku = new BasSku();
			BeanUtils.copyProperties(importDataVO, sku);
			
			//赋默认值
			sku.setActiveFlag("Y");
			sku.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			sku.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			
			basSkuMybatisDao.add(sku);
			resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
		}
	}
}
