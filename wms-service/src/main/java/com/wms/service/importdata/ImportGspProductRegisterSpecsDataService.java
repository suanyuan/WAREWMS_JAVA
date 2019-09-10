package com.wms.service.importdata;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasLocationQuery;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocAsnDetailQuery;
import com.wms.service.BasCodesService;
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
	private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
	@Autowired
	private DocAsnDoublecMybatisDao docAsnDoublecMybatisDao;
	@Autowired
	private BasPackageService basPackageService;
	@Autowired
	private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;
	@Autowired
	private GspSecondRecordMybatisDao gspSecondRecordMybatisDao;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private BasCodesService basCodesService;


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
				resultMsg.append("execel的Sheet表名应为:产品基础信息,字段需按模板匹配");
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
				System.out.println();
				importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
				if (Integer.parseInt(dataArray.getSeq()) <= 0) {
					throw new Exception();
				}
				if (StringUtils.isEmpty(dataArray.getSeq())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败 ，请输入大于0之正整数数字格式 ").append(" ");
			}
			System.out.println(dataArray.getSeq()+"=======================");
			System.out.println(Integer.parseInt(dataArray.getSeq())+"=============");



			try {
				GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryByNo(dataArray.getProductRegisterId());
				try{
					if(gspProductRegister!=null){
						importDataVO.setProductRegisterId(gspProductRegister.getProductRegisterId());
					}else {
						throw new Exception();
					}
				}catch (Exception e){
					rowResult.append("[注册证编号]，该注册证编号不存在").append(" ");
				}
				if (StringUtils.isEmpty(dataArray.getProductRegisterId())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[注册证编号]，未输入").append(" ");
			}

			try {
				boolean flag1 = false;

				GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.selectByProductCode(dataArray.getProductCode());

				if(gspProductRegisterSpecs==null  ){
					importDataVO.setProductCode(dataArray.getProductCode());
				}else if("0".equals(gspProductRegisterSpecs.getIsUse())){
					importDataVO.setProductCode(dataArray.getProductCode());
				}else{
					throw new Exception();
				}
				for(GspProductRegisterSpecsVO  a: importData){
					if(dataArray.getProductCode().equals(a.getProductCode())){
						flag1 = true;
					}
				}
				if(flag1){
					throw new Exception();
				}

				if (StringUtils.isEmpty(dataArray.getProductCode())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[产品代码]，未输入或已有该产品信息并且有效 无法重复添加").append(" ");
			}



			try {
				if("0".equals(dataArray.getProductName()) || dataArray.getProductName()==""||dataArray.getProductName()==null){
					//自动带入
					GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryByNo(dataArray.getProductRegisterId());
					if(gspProductRegister.getProductNameMain()!=null ){
						importDataVO.setProductName(gspProductRegister.getProductNameMain());
					}
				}else{
					//人工手输
					importDataVO.setProductName(dataArray.getProductName());
				}
			} catch (Exception e) {
//				rowResult.append("[产品名称]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getProductionAddress()) || dataArray.getProductionAddress()==""||dataArray.getProductionAddress()==null){
					//自动带入
					GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryByNo(dataArray.getProductRegisterId());

//					GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.selectByProductCode(dataArray.getProductCode());
					if(gspProductRegister.getProductionAddress()!=null){
						importDataVO.setProductionAddress(gspProductRegister.getProductionAddress());
					}
				}else{
					//人工手输
					importDataVO.setProductionAddress(dataArray.getProductionAddress());
				}
			} catch (Exception e) {
//				rowResult.append("[产地]，未输入").append(" ");
			}

			if("0".equals(dataArray.getProductRemark()) || dataArray.getProductRemark()==""||dataArray.getProductRemark()==null){

			}else{
				importDataVO.setProductRemark(dataArray.getProductRemark());//产品描述

			}


			try {
					importDataVO.setSpecsName(dataArray.getSpecsName());


				if (StringUtils.isEmpty(dataArray.getSpecsName())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[规格]，未输入").append(" ");
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
				if("0".equals(dataArray.getBarCode()) || dataArray.getBarCode()==""||dataArray.getBarCode()==null){

				}else{
					importDataVO.setBarCode(dataArray.getBarCode());
				}

//				if (StringUtils.isEmpty(dataArray.getBarCode())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[商品条码]，未输入").append(" ");
			}

			try {
				List<EasyuiCombobox>  easyuiComboboxList = 	basCodesService.getBy(Constant.CODE_CATALOG_UOM);
					for (EasyuiCombobox easyuiCombobox:easyuiComboboxList) {
						//判断单位是否存在
						if(easyuiCombobox.getValue().equals(dataArray.getUnit())){
							importDataVO.setUnit(easyuiCombobox.getId());
						}
					}
				if (StringUtils.isEmpty(importDataVO.getUnit())) {//如果单位不存在那么就没有赋值
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[单位]，未输入或查无此单位信息").append(" ");
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

				if (StringUtils.isEmpty(dataArray.getUnit()) || !flag) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[包装规格]，未输入或不存在该包装规格").append(" ");
			}

			try {

				if("0".equals(dataArray.getLlong()) || dataArray.getLlong()==""||dataArray.getLlong()==null){

				}else{
					importDataVO.setLlong(dataArray.getLlong());
					if(dataArray.getLlong()!=null ){

					}
					if (isNumeric(dataArray.getLlong())) {

					}else {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[长]，须为数字").append(" ");
			}

			try {
				if("0".equals(dataArray.getWide()) || dataArray.getWide()==""||dataArray.getWide()==null){

				}else {
					importDataVO.setWide(dataArray.getWide());
					if (isNumeric(dataArray.getWide())) {

					}else {
						throw new Exception();
					}
				}


			} catch (Exception e) {
				rowResult.append("[宽]，须为数字").append(" ");
			}

			try {
				if("0".equals(dataArray.getHight()) || dataArray.getHight()==""||dataArray.getHight()==null){

				}else {
					importDataVO.setHight(dataArray.getHight());
					if (isNumeric(dataArray.getHight())) {

					}else {
						throw new Exception();
					}
				}

			} catch (Exception e) {
				rowResult.append("[高]，须为数字").append(" ");
			}

			try {
                if(dataArray.getIsCertificate().equals("是") || dataArray.getIsCertificate().equals("否") ){
				if("是".equals(dataArray.getIsCertificate())){
					importDataVO.setIsCertificate("1");
				}else if("否".equals(dataArray.getIsCertificate())){
					importDataVO.setIsCertificate("0");
				}
                }else{
                    throw new Exception();
                }
				if (StringUtils.isEmpty(dataArray.getIsCertificate())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[产品合格证]，未输入或输入标准有误").append(" ");
			}


			try {
				//importDataVO.setIsDoublec(dataArray.getIsDoublec());
                if(dataArray.getIsDoublec().equals("是") || dataArray.getIsDoublec().equals("否") ) {
                    if ("是".equals(dataArray.getIsDoublec())) {
                        importDataVO.setIsDoublec("1");
                    } else if ("否".equals(dataArray.getIsDoublec())) {
                        importDataVO.setIsDoublec("0");
                    }
                }else{
                    throw new Exception();
                }
				if (StringUtils.isEmpty(dataArray.getIsDoublec())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[双证]，未输入或输入标准有误").append(" ");
			}

			try {
//				importDataVO.setTransportCondition(dataArray.getColdHainMark());
				if("非冷链".equals(dataArray.getColdHainMark())){
					importDataVO.setColdHainMark("FLL");
				}else if("冷冻".equals(dataArray.getColdHainMark())){
					importDataVO.setColdHainMark("LD");
				}else if("冷藏".equals(dataArray.getColdHainMark())){
					importDataVO.setColdHainMark("LC");
				}

				if (StringUtils.isEmpty(dataArray.getColdHainMark())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[冷链标志]，未输入").append(" ");
			}

			try {
//				importDataVO.setTransportCondition(dataArray.getColdHainMark());
                if(dataArray.getMedicalDeviceMark().equals("是") || dataArray.getMedicalDeviceMark().equals("否") ) {
                    if ("是".equals(dataArray.getMedicalDeviceMark())) {
                        importDataVO.setMedicalDeviceMark("1");
                    } else if ("否".equals(dataArray.getMedicalDeviceMark())) {
                        importDataVO.setMedicalDeviceMark("0");
                    }
                }else{
                    throw new Exception();
                }

				if (StringUtils.isEmpty(dataArray.getMedicalDeviceMark())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[医疗器械标志]，未输入或输入标准有误").append(" ");
			}

			try {
				importDataVO.setMaintenanceCycle(dataArray.getMaintenanceCycle());

				if (StringUtils.isEmpty(dataArray.getMaintenanceCycle())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[养护周期]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getAttacheCardCategory()) || dataArray.getAttacheCardCategory()==""||dataArray.getAttacheCardCategory()==null){

				}else {
					importDataVO.setAttacheCardCategory(dataArray.getAttacheCardCategory());
				}
//				if (StringUtils.isEmpty(dataArray.getPackingRequire())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[附卡类别]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getSterilizationMarkers()) || dataArray.getSterilizationMarkers()==""||dataArray.getSterilizationMarkers()==null){

				}else {
					importDataVO.setSterilizationMarkers(dataArray.getSterilizationMarkers());
				}
//				if (StringUtils.isEmpty(dataArray.getPackingRequire())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[灭菌标志]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getPackingRequire()) || dataArray.getPackingRequire()==""||dataArray.getPackingRequire()==null){

				}else {
					importDataVO.setPackingRequire(dataArray.getPackingRequire());
				}
//				if (StringUtils.isEmpty(dataArray.getPackingRequire())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[包装要求]，未输入").append(" ");
			}
			try {
					importDataVO.setStorageCondition(dataArray.getStorageCondition());

					if (StringUtils.isEmpty(dataArray.getStorageCondition())) {//判日期是否为空
						throw new Exception();
					}
				} catch (Exception e) {
					rowResult.append("[储存条件]，未输入").append(" ");
				}

//			if(dataArray.getProductRegisterId()){
//
//			}

			//生产许可证号/备案号    //放到产品注册证去   后面做
			GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryByProductRegisterId(dataArray.getProductRegisterId());
			try {
//				GspEnterpriseInfo gspEnterpriseInfo =  new GspEnterpriseInfo();
//					if(gspEnterpriseInfo==null){
//						throw new Exception();
//					}
//					if(!"GW".equals(gspEnterpriseInfo.getEnterpriseType())) {
//						GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.queryByProductRegisterId(dataArray.getProductRegisterId());
//						GspSecondRecord gspSecondRecord = gspSecondRecordMybatisDao.queryByProductRegisterId(dataArray.getProductRegisterId());
//						if(dataArray.getLicenseOrRecordNo()!=null && dataArray.getLicenseOrRecordNo()!="无" && dataArray.getLicenseOrRecordNo()!="" ){
//							importDataVO.setLicenseOrRecordNo(dataArray.getLicenseOrRecordNo());
//						}else if(!"".equals(gspOperateLicense.getLicenseNo()) && gspOperateLicense.getLicenseNo()!=null){
//							importDataVO.setLicenseOrRecordNo(gspOperateLicense.getLicenseNo());
//						}else if(!"".equals(gspSecondRecord.getRecordNo()) && gspSecondRecord.getRecordNo()!=null){
//							importDataVO.setLicenseOrRecordNo(gspSecondRecord.getRecordNo());
//						}else{
//							throw new Exception();
//						}
//
//						if (StringUtils.isEmpty(dataArray.getStorageCondition())) {//判日期是否为空
//							throw new Exception();
//						}
//					}else{
//						importDataVO.setLicenseOrRecordNo(dataArray.getLicenseOrRecordNo());
//					}
						importDataVO.setLicenseOrRecordNo(dataArray.getLicenseOrRecordNo());


			} catch (Exception e) {
				rowResult.append("[生产许可号/备案号]，未输入并且生产企业没有生产许可号备案号").append(" ");
			}



			//如果为非冷链，运输条件可以为空
			if("非冷链".equals(dataArray.getColdHainMark())){
				importDataVO.setStorageCondition(dataArray.getStorageCondition());

			}else {
				try {
					importDataVO.setTransportCondition(dataArray.getTransportCondition());

					if (StringUtils.isEmpty(dataArray.getTransportCondition())) {
						throw new Exception();
					}
				} catch (Exception e) {
					rowResult.append("[运输条件]，未输入").append(" ");
				}
			}
			try {
				if("0".equals(dataArray.getAlternatName1()) || dataArray.getAlternatName1()==""||dataArray.getAlternatName1()==null){

				}else {
					importDataVO.setAlternatName1(dataArray.getAlternatName1());
				}

//				if (StringUtils.isEmpty(dataArray.getAlternatName1())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[自赋码1]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getAlternatName2()) || dataArray.getAlternatName2()==""||dataArray.getAlternatName2()==null){

				}else {
					importDataVO.setAlternatName2(dataArray.getAlternatName2());
				}

//				if (StringUtils.isEmpty(dataArray.getAlternatName2())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[自赋码2]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getAlternatName3()) || dataArray.getAlternatName3()==""||dataArray.getAlternatName3()==null){

				}else {
					importDataVO.setAlternatName3(dataArray.getAlternatName3());
				}
//				if (StringUtils.isEmpty(dataArray.getAlternatName3())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[自赋码3]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getAlternatName4()) || dataArray.getAlternatName4()==""||dataArray.getAlternatName4()==null){

				}else {
					importDataVO.setAlternatName4(dataArray.getAlternatName4());
				}

//				if (StringUtils.isEmpty(dataArray.getAlternatName4())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[自赋码4]，未输入").append(" ");
			}

			try {
				if("0".equals(dataArray.getAlternatName5()) || dataArray.getAlternatName5()==""||dataArray.getAlternatName5()==null){

				}else {
					importDataVO.setAlternatName5(dataArray.getAlternatName5());
				}
//				if (StringUtils.isEmpty(dataArray.getAlternatName5())) {//判日期是否为空
//					throw new Exception();
//				}
			} catch (Exception e) {
//				rowResult.append("[自赋码5]，未输入").append(" ");
			}
			try {
				if(StringUtils.isNotEmpty(dataArray.getProductEnterpriseName())){
					importDataVO.setEnterpriseName(dataArray.getProductEnterpriseName());
				}else {
					//如果注册号有生产企业就带入
					GspProductRegister gspProductRegister =	gspProductRegisterMybatisDao.queryByNo(dataArray.getProductRegisterId());
					if(gspProductRegister.getEnterpriseId() != null && !gspProductRegister.getEnterpriseId().equals("")){
						GspEnterpriseInfo gspEnterpriseInfo1 =gspEnterpriseInfoMybatisDao.queryByEnterpriseId(gspProductRegister.getEnterpriseId());
						importDataVO.setEnterpriseName(gspEnterpriseInfo1.getEnterpriseName());
					}
				}
			} catch (Exception e) {
					//rowResult.append("[生产企业]，未输入").append(" ");
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
		map.put("注册证编号", "productRegisterId");
		map.put("产品名称", "productName");   //注册证带出


	    map.put("产品代码", "productCode");
	    map.put("产品描述", "productRemark");
		map.put("规格", "specsName");
	    map.put("型号", "productModel");
		map.put("单位", "unit");
		map.put("包装规格", "packingUnit");
		map.put("运输条件", "transportCondition");
		map.put("储存条件", "storageCondition");
		map.put("生产企业","productEnterpriseName");
		map.put("生产许可号/备案号", "licenseOrRecordNo");

		map.put("产地", "productionAddress");//注册证带出
		map.put("双证(是/否)", "isDoublec");
		map.put("产品合格证(是/否)", "isCertificate");
		map.put("附卡类别", "attacheCardCategory");
		map.put("冷链标志(非冷链、冷藏、冷冻)", "coldHainMark");
		map.put("灭菌标志", "sterilizationMarkers");
		map.put("医疗器械标志(是/否)", "medicalDeviceMark");
		map.put("养护周期(天)", "maintenanceCycle");

		map.put("包装单位", "packagingUnit");
		map.put("包装要求", "packingRequire");


	    map.put("长(m)", "llong");
	    map.put("宽(m)", "wide");
	    map.put("高(m)", "hight");
		map.put("重量(kg)", "wight");
		map.put("商品条码", "barCode");



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
