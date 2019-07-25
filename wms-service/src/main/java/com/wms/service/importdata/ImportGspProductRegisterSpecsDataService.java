package com.wms.service.importdata;

import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasLocationQuery;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocAsnDetailQuery;
import com.wms.service.BasPackageService;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("ImportGspProductRegisterSpecsDataService")
public class ImportGspProductRegisterSpecsDataService {

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
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
	@Autowired
	private DocAsnDoublecMybatisDao docAsnDoublecMybatisDao;
	@Autowired
	private BasPackageService basPackageService;
	/**
	 * 导入入库单
	 * @param excelFile
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
            String sheetName = "产品基础信息";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] { "序号" };
            //获取需要导入的具体的表
            Class asn = new ImportGPRSData().getClass();
            //excel转化成的list集合
            List<ImportGPRSData> GPRSList = null;
            try {
                //调用excle共用类，转化成list
				GPRSList = ExcelUtil.excelToList(in, sheetName, asn, map, uniqueFields);

            } catch (ExcelException e) {
                //e.printStackTrace();

            }
            //保存实体集合
			if(GPRSList==null){
				resultMsg.append("execel的Sheet表名应为:产品基础信息");
			}else {
				List<GspProductRegisterSpecsVO> importDataList = this.listToBean(GPRSList, resultMsg);
				if (true) {
//				this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
//				if (resultMsg.length() == 0) {
//					this.validateCustomerPermission(importDataList, resultMsg);// 验证客户权限是否存在
//					if (resultMsg.length() == 0) {
//						this.validateSku(importDataList, resultMsg);// 验证商品是否存在
//						if (resultMsg.length() == 0) {
//							this.validateLocation(importDataList, resultMsg);// 验证库位是否存在
					if (true) {
						System.out.println("=============");
						this.saveProductRegisterSpecs(importDataList, resultMsg);// 转成订单资料存入资料库
						isSuccess = true;
					}
//						}
//					}
//				}
				}
			}
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		json.setMsg(resultMsg.toString());
		json.setSuccess(isSuccess);
		return json;
	}










//	private List<GspProductRegisterSpecsVO> listToBean(List<ImportGPRSData> GPRSList, StringBuilder resultMsg) {
//		List<GspProductRegisterSpecsVO> importData =  new ArrayList<GspProductRegisterSpecsVO>();
//
//
//		return importData;
//	}
	private List<GspProductRegisterSpecsVO> listToBean(List<ImportGPRSData> GPRSList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<GspProductRegisterSpecsVO> importData = new ArrayList<GspProductRegisterSpecsVO>();
		GspProductRegisterSpecsVO importDataVO =  null;
//		List<DocAsnDetailVO> importDetailsDataVOList = new ArrayList<DocAsnDetailVO>();
//		DocAsnDetailVO importDetailsDataVO = null;
		String quantityData = null;
		Integer count = 1;
		String customerid = "", asnreference1 = "", asnreference2 = "", expectedarrivetime1 = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
		//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
		format.setLenient(false);
		formatRQ.setLenient(false);

		for (ImportGPRSData dataArray : GPRSList) {
			importDataVO = new GspProductRegisterSpecsVO();
			int arrayIndex = 0;
			try {
				importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
				//System.out.println(Integer.parseInt(dataArray.getSeq()) <= 0);
				//importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
				System.out.println(importDataVO.getSeq());
				if (Integer.parseInt(dataArray.getSeq()) <= 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}
			System.out.println(dataArray.getSeq()+"=======================");
			System.out.println(Integer.parseInt(dataArray.getSeq())+"=============");



			try {


				GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.selectByProductCode(dataArray.getProductCode());

				if(gspProductRegisterSpecs==null  ){
					importDataVO.setProductCode(dataArray.getProductCode());
				}else if("0".equals(gspProductRegisterSpecs.getIsUse())){
					importDataVO.setProductCode(dataArray.getProductCode());
				}else{
					throw new Exception();
				}
				if (StringUtils.isEmpty(dataArray.getProductCode())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[商品代码]，未输入或已有该产品信息并且有效 无法重复添加").append(" ");
			}

			try {
				importDataVO.setProductName(dataArray.getProductName());

				if (StringUtils.isEmpty(dataArray.getProductName())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[产品名称]，未输入").append(" ");
			}

			try {
				importDataVO.setProductRemark(dataArray.getProductRemark());
				if (StringUtils.isEmpty(dataArray.getProductRemark())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[产品描述]，未输入").append(" ");
			}

			try {
				importDataVO.setSpecsName(dataArray.getSpecsName());
				if (StringUtils.isEmpty(dataArray.getSpecsName())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[规格名称]，未输入").append(" ");
			}

			try {
				importDataVO.setProductModel(dataArray.getProductModel());

				if (StringUtils.isEmpty(dataArray.getProductModel())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[型号]，未输入").append(" ");
			}

			try {
				importDataVO.setProductionAddress(dataArray.getProductionAddress());

				if (StringUtils.isEmpty(dataArray.getProductionAddress())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[产地]，未输入").append(" ");
			}

			try {
				importDataVO.setBarCode(dataArray.getBarCode());

				if (StringUtils.isEmpty(dataArray.getBarCode())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[商品条码]，未输入").append(" ");
			}

			try {
				importDataVO.setUnit(dataArray.getUnit());

				if (StringUtils.isEmpty(dataArray.getUnit())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[单位]，未输入").append(" ");
			}

			try {
				importDataVO.setPackingUnit(dataArray.getPackingUnit());
				List<EasyuiCombobox> s =basPackageService.getBasPackageCombobox();
				boolean flag = false;

				for(EasyuiCombobox d :s){
					d.getId();
					if(d.getValue().equals(dataArray.getPackingUnit())){
						 flag = true;
					}
				}



				if (StringUtils.isEmpty(dataArray.getPackingUnit())|| !flag) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[包装单位]，未输入或不存在该包装单位").append(" ");
			}

			try {
				importDataVO.setLlong(dataArray.getLlong());

				if (isNumeric(dataArray.getLlong())) {

				}else {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[长]，须为数字").append(" ");
			}

			try {
				importDataVO.setWide(dataArray.getWide());

				if (isNumeric(dataArray.getWide())) {

				}else {
					throw new Exception();
				}

			} catch (Exception e) {
				rowResult.append("[宽]，须为数字").append(" ");
			}

			try {
				importDataVO.setHight(dataArray.getHight());

				if (isNumeric(dataArray.getHight())) {

				}else {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[高]，须为数字").append(" ");
			}

			try {
				if("是".equals(dataArray.getIsCertificate())){
					importDataVO.setIsCertificate("1");
				}else if("否".equals(dataArray.getIsCertificate())){
					importDataVO.setIsCertificate("0");
				}


				if (StringUtils.isEmpty(dataArray.getIsCertificate())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[是否需要产品合格证]，未输入").append(" ");
			}


			try {
				//importDataVO.setIsDoublec(dataArray.getIsDoublec());
				if("是".equals(dataArray.getIsDoublec())){
					importDataVO.setIsDoublec("1");
				}else if("否".equals(dataArray.getIsDoublec())){
					importDataVO.setIsDoublec("0");
				}
				if (StringUtils.isEmpty(dataArray.getIsDoublec())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[是否需要双证]，未输入").append(" ");
			}

			try {
				importDataVO.setPackingRequire(dataArray.getPackingRequire());

				if (StringUtils.isEmpty(dataArray.getPackingRequire())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[包装要求]，未输入").append(" ");
			}

			try {
				importDataVO.setStorageCondition(dataArray.getStorageCondition());

				if (StringUtils.isEmpty(dataArray.getStorageCondition())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[存储条件]，未输入").append(" ");
			}

			try {
				importDataVO.setTransportCondition(dataArray.getTransportCondition());

				if (StringUtils.isEmpty(dataArray.getTransportCondition())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[运输条件]，未输入").append(" ");
			}

			try {
				importDataVO.setAlternatName1(dataArray.getAlternatName1());

				if (StringUtils.isEmpty(dataArray.getAlternatName1())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[自赋码1]，未输入").append(" ");
			}

			try {
				importDataVO.setAlternatName2(dataArray.getAlternatName2());

				if (StringUtils.isEmpty(dataArray.getAlternatName2())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[自赋码2]，未输入").append(" ");
			}

			try {
				importDataVO.setAlternatName3(dataArray.getAlternatName3());

				if (StringUtils.isEmpty(dataArray.getAlternatName3())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[自赋码3]，未输入").append(" ");
			}

			try {
				importDataVO.setAlternatName4(dataArray.getAlternatName4());

				if (StringUtils.isEmpty(dataArray.getAlternatName4())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[自赋码4]，未输入").append(" ");
			}

			try {
				importDataVO.setAlternatName5(dataArray.getAlternatName5());

				if (StringUtils.isEmpty(dataArray.getAlternatName5())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[自赋码5]，未输入").append(" ");
			}





			//importDataList.add(importDataVO);
			//importDataVO.setGspProductRegisterSpecsVOList(importDataList);
			if (rowResult.length() > 0) {
				if(rowResult.lastIndexOf("，") > -1){
					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				}
				resultMsg.append("序号：").append(dataArray.getSeq()).append("资料有错 ").append(rowResult).append(" ");
				rowResult.setLength(0);
			} else {
				importData.add(importDataVO);
			}
			//importData.add(importDataVO);
//			for (GspProductRegisterSpecsVO aa : importData) {
//				//System.out.println(aa.getSeq());
//				//System.out.println(aa.getCustomerid());
//			}

		}
		return importData;
	}

	public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
	    // excel的表头与文字对应
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	    map.put("序号", "seq");
	    map.put("商品代码", "productCode");
	    map.put("产品名称", "productName");
	    map.put("产品描述", "productRemark");
	    map.put("规格名称", "specsName");
	    map.put("型号", "productModel");
	    map.put("产地", "productionAddress");
	    map.put("商品条码", "barCode");
	    map.put("单位", "unit");
	    map.put("包装单位", "packingUnit");
	    map.put("长", "llong");
	    map.put("宽", "wide");
	    map.put("高", "hight");
		map.put("是否需要产品合格证", "isCertificate");
		map.put("是否需要双证", "isDoublec");
	    map.put("包装要求", "packingRequire");
	    map.put("存储条件", "storageCondition");
		map.put("运输条件", "transportCondition");
	    map.put("自赋码1", "alternatName1");
		map.put("自赋码2", "alternatName2");
		map.put("自赋码3", "alternatName3");
		map.put("自赋码4", "alternatName4");
		map.put("自赋码5", "alternatName5");

	    return map;
	}
	
	/*private void validateSku(List<GspProductRegisterSpecsVO> importDataList, StringBuilder resultMsg) {
		BasSku sku = null;
		BasSkuQuery skuQuery = new BasSkuQuery();
		for (DocAsnHeaderVO importDataVO : importDataList) {
			for (DocAsnDetailVO importDetailsDataVO : importDataVO.getDocAsnDetailVOList()) {
				skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
				skuQuery.setSku(importDetailsDataVO.getSku());
				sku = basSkuMybatisDao.queryById(skuQuery);
				if(sku == null){
					resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
							 .append("，客户代码：").append(importDataVO.getCustomerid())
							 .append("，产品代码：").append(importDetailsDataVO.getSku()).append("，查无资料").append(" ");
				}
			}
		}
	}*/
	
	/*private void validateLocation(List<GspProductRegisterSpecsVO> importDataList, StringBuilder resultMsg) {
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
	}*/
	
	/*private void validateCustomer(List<GspProductRegisterSpecsVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (DocAsnHeaderVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customer = basCustomerMybatisDao.queryById(customerQuery);
			if (customer == null) {// 是否有客户资料
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，客户代码查无客户资料").append(" ");
			}
		}
	}

	private void validateCustomerPermission(List<GspProductRegisterSpecsVO> importDataList, StringBuilder resultMsg) {
		BasCustomer customer = null;
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		for (DocAsnHeaderVO importDataVO : importDataList) {
			customerQuery.setCustomerid(importDataVO.getCustomerid());
			customerQuery.setCustomerType("OW");
			customerQuery.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			customer = basCustomerMybatisDao.queryById(customerQuery);
			if (customer == null) {// 是否有客户权限
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("，客户代码查无客户权限").append(" ");
			}
		}
	}*/


	private void saveProductRegisterSpecs(List<GspProductRegisterSpecsVO> importDataList, StringBuilder resultMsg) {
		GspProductRegisterSpecs gspProductRegisterSpecs = null;
//		boolean flag = false;
//		if(importDataList.size()==0){
//			flag = true;
//		}

		for(GspProductRegisterSpecsVO importDataVO : importDataList){
			gspProductRegisterSpecs = new GspProductRegisterSpecs();
			BeanUtils.copyProperties(importDataVO, gspProductRegisterSpecs);
//			//获取SO编号
//			Map<String ,Object> map=new HashMap<String, Object>();
//			map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//			docAsnHeaderMybatisDao.getIdSequence(map);
//			String resultCode = map.get("resultCode").toString();
//			String resultNo = map.get("resultNo").toString();
//			if (resultCode.substring(0,3).equals("000")) {
				//赋值

				gspProductRegisterSpecs.setSpecsId(RandomUtil.getUUID());
				//gspProductRegisterSpecs.setProductRegisterId("PR");
				//gspProductRegisterSpecs.setSpecsName(importDataVO.getSpecsName());
				//gspProductRegisterSpecs.setSpecsId(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				gspProductRegisterSpecs.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspProductRegisterSpecs.setCreateDate(new Date());
				gspProductRegisterSpecs.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspProductRegisterSpecs.setEditDate(new Date());
				gspProductRegisterSpecs.setIsUse("1");
				//保存订单主信息
			gspProductRegisterSpecsMybatisDao.add(gspProductRegisterSpecs);
//				if(flag==true){
//					resultMsg.delete(0, resultMsg.length());
//					StringBuilder rowResult = new StringBuilder();
//					resultMsg = new StringBuilder();
//					rowResult.append("execel的Sheet表名应为:产品基础信息").append(" ");
//					resultMsg.append(rowResult);
//				}else if(!flag){
					resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
//
//				}
			}
//			else {
//				resultMsg.append("序号：").append(importDataVO.getSeq()).append("SO号获取失败").append(" ");
//			}
		}
//	}
	
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}

//	public static boolean isNumeric(String str){
//		for (int i = 0; i < str.length(); i++){
//			System.out.println(str.charAt(i));
//			if (!Character.isDigit(str.charAt(i))){
//				return false;
//			}
//		}
//		return true;
//	}
}
