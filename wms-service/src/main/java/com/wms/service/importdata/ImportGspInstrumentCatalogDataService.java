package com.wms.service.importdata;

import com.wms.entity.BasGtn;
import com.wms.entity.GspInstrumentCatalog;
import com.wms.entity.ImportGICData;
import com.wms.entity.ImportGTNData;
import com.wms.mybatis.dao.*;
import com.wms.service.BasPackageService;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.BasGtnVO;
import com.wms.vo.GspInstrumentCatalogVO;
import com.wms.vo.Json;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service("ImportGspInstrumentCatalogDataService")
public class ImportGspInstrumentCatalogDataService {

	@Autowired
	private GspInstrumentCatalogMybatisDao gspInstrumentCatalogMybatisDao;
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
	private BasGtnMybatisDao basGtnMybatisDao;
	@Autowired
	private BasPackageService basPackageService;
	/**
	 * 导入GTIN码
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
            String sheetName = "器械目录";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] { "序号" };
            //获取需要导入的具体的表
            Class asn = new ImportGICData().getClass();
            //excel转化成的list集合
            List<ImportGICData> GTNList = null;
            try {
                //调用excle共用类，转化成list
				GTNList = ExcelUtil.excelToList(in, sheetName, asn, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<GspInstrumentCatalogVO> importDataList = this.listToBean(GTNList, resultMsg);
            if (true) {
//				this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
//				if (resultMsg.length() == 0) {
//					this.validateCustomerPermission(importDataList, resultMsg);// 验证客户权限是否存在
//					if (resultMsg.length() == 0) {
//						this.validateSku(importDataList, resultMsg);// 验证商品是否存在
//						if (resultMsg.length() == 0) {
//							this.validateLocation(importDataList, resultMsg);// 验证库位是否存在
							if (true) {
								this.saveBasGtn(importDataList, resultMsg);// 转成订单资料存入资料库
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
	private List<GspInstrumentCatalogVO> listToBean(List<ImportGICData> GTNList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<GspInstrumentCatalogVO> importData = new ArrayList<GspInstrumentCatalogVO>();
		GspInstrumentCatalogVO importDataVO =  null;
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

		for (ImportGICData dataArray : GTNList) {
			importDataVO = new GspInstrumentCatalogVO();
			int arrayIndex = 0;
			try {
				importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));

				if (Integer.parseInt(dataArray.getSeq()) <= 0) {
					throw new Exception();
				}
				System.out.println();
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}





//			try {
//				importDataVO.setSku(dataArray.getSku());
//				BasGtn basGtn = basGtnMybatisDao.queryById(dataArray.getSku());
//
//				if(basGtn!=null){
//					basGtnMybatisDao.delete(dataArray.getSku());
//				}
//
//				if (StringUtils.isEmpty(dataArray.getSku())) {
//					throw new Exception();
//				}
//			} catch (Exception e) {
//				rowResult.append("[产品代码]，未输入").append(" ");
//			}
//


			try {
				importDataVO.setInstrumentCatalogNo(dataArray.getInstrumentCatalogNo());
				if (StringUtils.isEmpty(dataArray.getInstrumentCatalogNo())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[编号]，未输入").append(" ");
			}

			try {
				importDataVO.setInstrumentCatalogName(dataArray.getInstrumentCatalogName());
				if (StringUtils.isEmpty(dataArray.getInstrumentCatalogName())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[名称]，未输入").append(" ");
			}

//			try {
//				importDataVO.setClassifyId(dataArray.getClassifyId());
//				if(!StringUtils.isEmpty(dataArray.getInstrumentCatalogNo())
//						&&!StringUtils.isEmpty(dataArray.getInstrumentCatalogNo() )){
//
//				}
//
//
//
//				if (StringUtils.isEmpty(dataArray.getClassifyId())) {
//					throw new Exception();
//				}
//			} catch (Exception e) {
//				rowResult.append("[分类]，未输入").append(" ");
//			}

			//已存在该编号加分类的器械目录




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
	    map.put("编号", "instrumentCatalogNo");
	    map.put("名称", "instrumentCatalogName");
		map.put("描述", "instrumentCatalogRemark");
		map.put("分类(I、II、III)", "classifyId");
		map.put("版本(2002、2017)", "version");


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


	private void saveBasGtn(List<GspInstrumentCatalogVO> importDataList, StringBuilder resultMsg) {
		GspInstrumentCatalog gspInstrumentCatalog = null;
		for(GspInstrumentCatalogVO importDataVO : importDataList){
			gspInstrumentCatalog = new GspInstrumentCatalog();
			BeanUtils.copyProperties(importDataVO, gspInstrumentCatalog);
				//赋值

				//保存订单主信息
			gspInstrumentCatalog.setInstrumentCatalogId(RandomUtil.getUUID());
			gspInstrumentCatalog.setCretaeDate(new Date());
			gspInstrumentCatalog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());

			gspInstrumentCatalogMybatisDao.add(gspInstrumentCatalog);

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
