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
            String sheetName = "双证导入";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] { "序号" };
            //获取需要导入的具体的表
            Class asn = new ImportDoublecData().getClass();
            //excel转化成的list集合
            List<ImportDoublecData> GPRSList = null;
            try {
                //调用excle共用类，转化成list
				GPRSList = ExcelUtil.excelToList(in, sheetName, asn, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<DocAsnDoublecVO> importDataList = this.listToBean(GPRSList, resultMsg);
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
	private List<DocAsnDoublecVO> listToBean(List<ImportDoublecData> GPRSList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<DocAsnDoublecVO> importData = new ArrayList<DocAsnDoublecVO>();
		DocAsnDoublecVO importDataVO =  null;
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

		for (ImportDoublecData dataArray : GPRSList) {
			importDataVO = new DocAsnDoublecVO();
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

				importDataVO.setDoublecno(dataArray.getDoublecno());
				DocAsnDoublec docAsnDoublec = docAsnDoublecMybatisDao.queryById(dataArray.getDoublecno());
				if(docAsnDoublec!=null){
					throw new Exception();
				}
//				GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.selectByProductCode(dataArray.getProductCode());
//
//				if(gspProductRegisterSpecs==null  ){
//					importDataVO.setProductCode(dataArray.getDoublecno());
//				}else if("0".equals(gspProductRegisterSpecs.getIsUse())){
//
//				}else{
//					throw new Exception();
//				}
				if (StringUtils.isEmpty(dataArray.getDoublecno())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[任务号]，未输入或者任务号重复").append(" ");
			}

//			try {
//				importDataVO.setProductName(dataArray.getCustomerid());
//
//				if (StringUtils.isEmpty(dataArray.getCustomerid())) {
//					throw new Exception();
//				}
//			} catch (Exception e) {
//				rowResult.append("[客户编号]，未输入").append(" ");
//			}

			try {
				importDataVO.setContext1(dataArray.getContext1());
				if (StringUtils.isEmpty(dataArray.getContext1())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[产品型号]，未输入").append(" ");
			}

			try {
				importDataVO.setContext2(dataArray.getContext2());
				if (StringUtils.isEmpty(dataArray.getContext2())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[名称]，未输入").append(" ");
			}

			try {
				importDataVO.setContext3(dataArray.getContext3());

				if (StringUtils.isEmpty(dataArray.getContext3())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[执行标准]，未输入").append(" ");
			}

			try {
				importDataVO.setContext4(dataArray.getContext4());

				if (StringUtils.isEmpty(dataArray.getContext4())) {//判日期是否为空
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[备注]，未输入").append(" ");
			}






			if (rowResult.length() > 0) {
				if(rowResult.lastIndexOf("，") > -1){
					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				}
				System.out.println();
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
	    map.put("任务号", "doublecno");
	    map.put("产品型号", "context1");
	    map.put("名称", "context2");
	    map.put("执行标准", "context3");
	    map.put("备注", "context4");



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

	@Transactional
	private void saveProductRegisterSpecs(List<DocAsnDoublecVO> importDataList, StringBuilder resultMsg) {
		DocAsnDoublec gspProductRegisterSpecs = null;
		for(DocAsnDoublecVO importDataVO : importDataList){
			gspProductRegisterSpecs = new DocAsnDoublec();
			BeanUtils.copyProperties(importDataVO, gspProductRegisterSpecs);
//			//获取SO编号
//			Map<String ,Object> map=new HashMap<String, Object>();
//			map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//			docAsnHeaderMybatisDao.getIdSequence(map);
//			String resultCode = map.get("resultCode").toString();
//			String resultNo = map.get("resultNo").toString();
//			if (resultCode.substring(0,3).equals("000")) {
				//赋值
				//gspProductRegisterSpecs.setSpecsId(RandomUtil.getUUID());
				//gspProductRegisterSpecs.setProductRegisterId("PR");
				//gspProductRegisterSpecs.setSpecsName(importDataVO.getSpecsName());
				//gspProductRegisterSpecs.setSpecsId(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				gspProductRegisterSpecs.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
				gspProductRegisterSpecs.setAddtime(new Date());
				gspProductRegisterSpecs.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
				gspProductRegisterSpecs.setEdittime(new Date());
				//gspProductRegisterSpecs.setIsUse("1");
				//保存订单主信息
			docAsnDoublecMybatisDao.add(gspProductRegisterSpecs);

				resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
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
