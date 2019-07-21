package com.wms.service.importdata;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wms.entity.BasLocation;
import com.wms.entity.BasSku;
import com.wms.entity.BasCustomer;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.entity.ImportAsnData;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.BasLocationMybatisDao;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasLocationQuery;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocAsnDetailQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.Json;

@Service("ImportAsnDataService")
public class ImportAsnDataService {

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;
	@Autowired
	private BasLocationMybatisDao basLocationMybatisDao;
	@Autowired
	private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
	/**
	 * 导入入库单
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
            String sheetName = "入库单明细";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] { "序号" };
            //获取需要导入的具体的表
            Class asn = new ImportAsnData().getClass();
            //excel转化成的list集合
            List<ImportAsnData> asnList = null;
            try {
                //调用excle共用类，转化成list
            	asnList = ExcelUtil.excelToList(in, sheetName, asn, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<DocAsnHeaderVO> importDataList = this.listToBean(asnList, resultMsg);
            if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
				this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
				if (resultMsg.length() == 0) {
					//this.validateCustomerPermission(importDataList, resultMsg);// 验证客户权限是否存在 TODO 货主权限验证
					if (resultMsg.length() == 0) {
						this.validateSku(importDataList, resultMsg);// 验证商品是否存在
						if (resultMsg.length() == 0) {
							this.validateLocation(importDataList, resultMsg);// 验证库位是否存在
							if (resultMsg.length() == 0) {
								this.saveAsn(importDataList, resultMsg);// 转成订单资料存入资料库
								isSuccess = true;
							}
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
	
	private List<DocAsnHeaderVO> listToBean(List<ImportAsnData> asnList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<DocAsnHeaderVO> importData = new ArrayList<DocAsnHeaderVO>();
		DocAsnHeaderVO importDataVO = null;
		List<DocAsnDetailVO> importDetailsDataVOList = new ArrayList<DocAsnDetailVO>();
		DocAsnDetailVO importDetailsDataVO = null;
		String quantityData = null;
		Integer count = 1;
		String customerid = "", asnreference1 = "", asnreference2 = "", expectedarrivetime1 = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
		//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
		format.setLenient(false);
		formatRQ.setLenient(false);
		
		for(ImportAsnData dataArray : asnList) {
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
				rowResult.append("[货主代码]，未输入").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getAsnreference1())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[客户单号1]，未输入").append(" ");
			}
			try {
				if (StringUtils.isEmpty(dataArray.getExpectedarrivetime1())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[预期到货时间]，未输入").append(" ");
			}
			try {
				 format.parse(dataArray.getExpectedarrivetime1());
			} catch (ParseException e) {
				 //如果throw java.text.ParseException或者NullPointerException，就说明格式不对
				 rowResult.append("[预期到货时间]，格式错误").append(" ");
			}
			//生产日期、失效日期、入库日期
			try {
				if (StringUtils.isNotEmpty(dataArray.getLotatt01())){
					formatRQ.parse(dataArray.getLotatt01());
				}
			} catch (ParseException e) {
				 rowResult.append("[生产日期]，格式错误").append(" ");
			} 
			try {
				formatRQ.parse(dataArray.getLotatt02());
			} catch (ParseException e) {
				 rowResult.append("[失效日期]，格式错误").append(" ");
			} 
			/*try {
				formatRQ.parse(dataArray.getLotatt03());
			} catch (ParseException e) {
				 rowResult.append("[入库日期]，格式错误").append(" ");
			}*/
			//重量、体积、单价
			try {
				if (isNumeric(dataArray.getTotalgrossweight())) {
					throw new Exception();
				}
			} catch (Exception e) {
				 rowResult.append("[重量]，须为数字").append(" ");
			}
			try {
				if (isNumeric(dataArray.getTotalcubic())) {
					throw new Exception();
				}
			} catch (Exception e) {
				 rowResult.append("[体积]，须为数字").append(" ");
			}
			try {
				if (isNumeric(dataArray.getTotalprice())) {
					throw new Exception();
				}
			} catch (Exception e) {
				 rowResult.append("[单价]，须为数字").append(" ");
			}
			//库存状态
			/*try {
				if (StringUtils.isNotEmpty(dataArray.getLotatt04())){
					if(!(dataArray.getLotatt04().equals("HG") || dataArray.getLotatt04().equals("CP"))){
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[库存状态]，输入异常").append(" ");
			}*/
			try {
				quantityData = dataArray.getExpectedqty();
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
					importDataVO = new DocAsnHeaderVO();
					importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
					importDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());//货主代码
					importDataVO.setAsnreference1(dataArray.getAsnreference1());
					importDataVO.setAsnreference2(dataArray.getAsnreference2());
					try {
						importDataVO.setExpectedarrivetime1(format.parse(dataArray.getExpectedarrivetime1()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					importDetailsDataVO = new DocAsnDetailVO();
					importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
					importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
					importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
					importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
					importDetailsDataVO.setReceivinglocation(dataArray.getReceivinglocation());
					importDetailsDataVO.setTotalgrossweight(new BigDecimal(dataArray.getTotalgrossweight()));
					importDetailsDataVO.setTotalcubic(new BigDecimal(dataArray.getTotalcubic()));
					importDetailsDataVO.setTotalprice(new BigDecimal(dataArray.getTotalprice()));
					importDetailsDataVO.setLotatt01(dataArray.getLotatt01());
					importDetailsDataVO.setLotatt02(dataArray.getLotatt02());
					//importDetailsDataVO.setLotatt03(dataArray.getLotatt03());
					importDetailsDataVO.setLotatt04(dataArray.getLotatt04());
					importDetailsDataVO.setLotatt05(dataArray.getLotatt05());
					importDetailsDataVO.setLotatt06(dataArray.getLotatt06());
					importDetailsDataVO.setLotatt07(dataArray.getLotatt07());
					importDetailsDataVO.setLotatt08(dataArray.getLotatt08());
					importDetailsDataVO.setLotatt09(dataArray.getLotatt09());
					importDetailsDataVO.setLotatt11(dataArray.getLotatt11());
					importDetailsDataVO.setNotes(dataArray.getNotes());
					importDetailsDataVOList.add(importDetailsDataVO);
				} else if (dataArray.getCustomerid().toUpperCase().equals(customerid) &&
					dataArray.getAsnreference1().equals(asnreference1) &&
					dataArray.getAsnreference2().equals(asnreference2) &&
					dataArray.getExpectedarrivetime1().equals(expectedarrivetime1)) {
					//表头信息一致则只增加明细信息
					importDetailsDataVO = new DocAsnDetailVO();
					importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
					importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
					importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
					importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
					importDetailsDataVO.setReceivinglocation(dataArray.getReceivinglocation());
					importDetailsDataVO.setTotalgrossweight(new BigDecimal(dataArray.getTotalgrossweight()));
					importDetailsDataVO.setTotalcubic(new BigDecimal(dataArray.getTotalcubic()));
					importDetailsDataVO.setTotalprice(new BigDecimal(dataArray.getTotalprice()));
					importDetailsDataVO.setLotatt01(dataArray.getLotatt01());
					importDetailsDataVO.setLotatt02(dataArray.getLotatt02());
					importDetailsDataVO.setLotatt03(dataArray.getLotatt03());
					importDetailsDataVO.setLotatt04(dataArray.getLotatt04());
					importDetailsDataVO.setLotatt05(dataArray.getLotatt05());
					importDetailsDataVO.setLotatt06(dataArray.getLotatt06());
					importDetailsDataVO.setLotatt07(dataArray.getLotatt07());
					importDetailsDataVO.setLotatt08(dataArray.getLotatt08());
					importDetailsDataVO.setLotatt09(dataArray.getLotatt09());
					importDetailsDataVO.setLotatt11(dataArray.getLotatt11());
					importDetailsDataVO.setNotes(dataArray.getNotes());
					importDetailsDataVOList.add(importDetailsDataVO);
				} else {
					//表头信息不一致则生成新的订单
					importDataVO.setDocAsnDetailVOList(importDetailsDataVOList);
					importData.add(importDataVO);
					importDetailsDataVOList = new ArrayList<DocAsnDetailVO>();
					importDataVO = new DocAsnHeaderVO();
					
					importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
					importDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());//货主代码
					importDataVO.setAsnreference1(dataArray.getAsnreference1());
					importDataVO.setAsnreference2(dataArray.getAsnreference2());
					try {
						importDataVO.setExpectedarrivetime1(format.parse(dataArray.getExpectedarrivetime1()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					importDetailsDataVO = new DocAsnDetailVO();
					importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
					importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
					importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
					importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
					importDetailsDataVO.setReceivinglocation(dataArray.getReceivinglocation());
					importDetailsDataVO.setTotalgrossweight(new BigDecimal(dataArray.getTotalgrossweight()));
					importDetailsDataVO.setTotalcubic(new BigDecimal(dataArray.getTotalcubic()));
					importDetailsDataVO.setTotalprice(new BigDecimal(dataArray.getTotalprice()));
					importDetailsDataVO.setLotatt01(dataArray.getLotatt01());
					importDetailsDataVO.setLotatt02(dataArray.getLotatt02());
					importDetailsDataVO.setLotatt03(dataArray.getLotatt03());
					importDetailsDataVO.setLotatt04(dataArray.getLotatt04());
					importDetailsDataVO.setLotatt05(dataArray.getLotatt05());
					importDetailsDataVO.setLotatt06(dataArray.getLotatt06());
					importDetailsDataVO.setLotatt07(dataArray.getLotatt07());
					importDetailsDataVO.setLotatt08(dataArray.getLotatt08());
					importDetailsDataVO.setLotatt09(dataArray.getLotatt09());
					importDetailsDataVO.setLotatt11(dataArray.getLotatt11());
					importDetailsDataVO.setNotes(dataArray.getNotes());
					importDetailsDataVOList.add(importDetailsDataVO);
				}
				//最后一行结束操作
				if (count == asnList.size()) {
					importDataVO.setDocAsnDetailVOList(importDetailsDataVOList);
					importData.add(importDataVO);
				}
			}
			
			customerid = dataArray.getCustomerid();
			asnreference1 = dataArray.getAsnreference1();
			asnreference2 = dataArray.getAsnreference2();
			expectedarrivetime1 = dataArray.getExpectedarrivetime1();
			count = count + 1;
		}
		for (DocAsnHeaderVO aa : importData) {
        	System.out.println(aa.getSeq());
        	System.out.println(aa.getCustomerid());
        }
		return importData;
	}

	public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
	    // excel的表头与文字对应
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	    map.put("序号", "seq");
	    map.put("货主代码", "customerid");
	    map.put("客户单号1", "asnreference1");
	    map.put("客户单号2", "asnreference2");
		map.put("入库单类型", "asntype");
	    map.put("预期到货时间", "expectedarrivetime1");
	    map.put("产品代码", "sku");
	    map.put("预期数量", "expectedqty");
	    map.put("收货库位", "receivinglocation");
	    map.put("重量", "totalgrossweight");
	    map.put("体积", "totalcubic");
	    map.put("单价", "totalprice");
	    map.put("生产日期", "lotatt01");
	    map.put("效期", "lotatt02");
	    map.put("生产批号", "lotatt04");
	    map.put("序列号", "lotatt05");
	    map.put("产品注册证号", "lotatt06");
	    map.put("灭菌批号", "lotatt07");
	    map.put("供应商代码", "lotatt08");
		map.put("样品属性", "lotatt09");
		map.put("存储条件", "lotatt11");
	    return map;
	}
	
	private void validateSku(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
		BasSku sku = null;
		BasSkuQuery skuQuery = new BasSkuQuery();
		for (DocAsnHeaderVO importDataVO : importDataList) {
			for (DocAsnDetailVO importDetailsDataVO : importDataVO.getDocAsnDetailVOList()) {
				skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
				skuQuery.setSku(importDetailsDataVO.getSku());
				sku = basSkuMybatisDao.queryById(skuQuery);
				if(sku == null){
					resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
							 .append("，货主代码：").append(importDataVO.getCustomerid())
							 .append("，产品代码：").append(importDetailsDataVO.getSku()).append("，查无资料").append(" ");
				}
			}
		}
	}
	
	private void validateLocation(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
		BasLocation loc = null;
		BasLocationQuery locQuery = new BasLocationQuery();
		for (DocAsnHeaderVO importDataVO : importDataList) {
			for (DocAsnDetailVO importDetailsDataVO : importDataVO.getDocAsnDetailVOList()) {
				if (StringUtils.isNotEmpty(importDetailsDataVO.getReceivinglocation())){
					locQuery.setLocationid(importDetailsDataVO.getReceivinglocation());
					loc = basLocationMybatisDao.queryById(locQuery);
					if(loc == null){
						resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
								 .append("，库位编码：").append(importDetailsDataVO.getReceivinglocation()).append("，查无资料").append(" ");
					}
				}
			}
		}
	}
	
	private void validateCustomer(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (DocAsnHeaderVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customer = basCustomerMybatisDao.queryByIdType(customerQuery.getCustomerid(),customerQuery.getCustomerType());
			if (customer == null) {// 是否有客户资料
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码查无客户资料").append(" ");
			}
		}
	}

	private void validateCustomerPermission(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (DocAsnHeaderVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customerQuery.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			customer = basCustomerMybatisDao.queryById(customerQuery);
			if (customer == null) {// 是否有客户权限
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码查无客户权限").append(" ");
			}
		}
	}

	private void saveAsn(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
		DocAsnHeader asnHeader = null;
		for(DocAsnHeaderVO importDataVO : importDataList){
			asnHeader = new DocAsnHeader();
			BeanUtils.copyProperties(importDataVO, asnHeader);
			//获取SO编号
			Map<String ,Object> map=new HashMap<String, Object>();
			map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			docAsnHeaderMybatisDao.getIdSequence(map);
			String resultCode = map.get("resultCode").toString();
			String resultNo = map.get("resultNo").toString();
			if (resultCode.substring(0,3).equals("000")) {
				//赋值
				asnHeader.setAsnno(resultNo);
				//asnHeader.setAsntype("PR");
				asnHeader.setAsntype(importDataVO.getAsntype());
				asnHeader.setReleasestatus("Y");
				asnHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				asnHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
				asnHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
				//保存订单主信息
				docAsnHeaderMybatisDao.add(asnHeader);
				for (DocAsnDetailVO importDetailsDataVO : importDataVO.getDocAsnDetailVOList()) {
					DocAsnDetail asnDetails = new DocAsnDetail();
					BeanUtils.copyProperties(importDetailsDataVO, asnDetails);
					asnDetails.setAsnno(resultNo);
					DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
					docAsnDetailQuery.setAsnno(resultNo);
					//获取订单明细行号
					long asnlineno = docAsnDetailsMybatisDao.getAsnlinenoById(docAsnDetailQuery);
					//获取SKU信息(条码、包装、重量、体积、金额)
					BasSkuQuery skuQuery = new BasSkuQuery();
					skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
					skuQuery.setSku(importDetailsDataVO.getSku());
					skuQuery.setQty(importDetailsDataVO.getExpectedqty());
					BasSku basSku = basSkuMybatisDao.queryBySkuInfo(skuQuery);
					//赋值
					asnDetails.setAsnlineno(asnlineno + 1);
					asnDetails.setPackid(basSku.getPackid());
					asnDetails.setAlternativesku(basSku.getAlternateSku1());
					asnDetails.setLotatt10("DJ");
					//体积重量单价若不输入则从SKU里读取
					if(importDetailsDataVO.getTotalgrossweight().compareTo(BigDecimal.ZERO) == 1){
						asnDetails.setTotalgrossweight(importDetailsDataVO.getTotalgrossweight());
					}else{
						asnDetails.setTotalgrossweight(basSku.getGrossweight());
					}
					if(importDetailsDataVO.getTotalcubic().compareTo(BigDecimal.ZERO) == 1){
						asnDetails.setTotalcubic(importDetailsDataVO.getTotalcubic());
					}else{
						asnDetails.setTotalcubic(basSku.getCube());
					}
					if(importDetailsDataVO.getTotalprice().compareTo(BigDecimal.ZERO) == 1){
						asnDetails.setTotalprice(importDetailsDataVO.getTotalprice());
					}else{
						asnDetails.setTotalprice(basSku.getPrice());
					}
					asnDetails.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
					asnDetails.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
					//保存订单明细信息
					docAsnDetailsMybatisDao.add(asnDetails);
				}
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
			} else {
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("SO号获取失败").append(" ");
			}
		}
	}
	
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
}
