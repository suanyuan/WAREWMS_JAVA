package com.wms.service.importdata;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspInstrumentCatalog;
import com.wms.entity.GspOperateDetail;
import com.wms.entity.GspProductRegister;
import com.wms.entity.ImportGPRData;
import com.wms.mybatis.dao.*;
import com.wms.query.GspEnterpriseInfoQuery;
import com.wms.service.BasCodesService;
import com.wms.service.BasPackageService;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.service.GspProductRegisterService;
import com.wms.utils.*;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.GspEnterpriseInfoVO;
import com.wms.vo.GspProductRegisterVO;
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

@Service("ImportGspProductRegisterDataService")
public class ImportGspProductRegisterDataService {

	@Autowired
	private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;
	@Autowired
	private BasCodesService basCodesService;
	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;
	@Autowired
	private GspProductRegisterService gspProductRegisterService;
	@Autowired
	private GspInstrumentCatalogMybatisDao gspInstrumentCatalogMybatisDao;
	@Autowired
	private GspOperateDetailMybatisDao gspOperateDetailMybatisDao;
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
            String sheetName = "产品注册证";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] { "序号" };
            //获取需要导入的具体的表
            Class asn = new ImportGPRData().getClass();
            //excel转化成的list集合
            List<ImportGPRData> GPRSList = null;
            try {
                //调用excle共用类，转化成list
				GPRSList = ExcelUtil.excelToList(in, sheetName, asn, map, uniqueFields);

            } catch (ExcelException e) {
                //e.printStackTrace();

            }
            //保存实体集合
			if(GPRSList==null){
				resultMsg.append("execel的Sheet表名应为:产品注册证");
			}else {
				List<GspProductRegisterVO> importDataList = this.listToBean(GPRSList, resultMsg);
				if (true) {
					if (true) {
						System.out.println("=============");
						this.saveProductRegister(importDataList, resultMsg);// 转成订单资料存入资料库
						isSuccess = true;
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











	private List<GspProductRegisterVO> listToBean(List<ImportGPRData> GPRSList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<GspProductRegisterVO> importData = new ArrayList<GspProductRegisterVO>();
		GspProductRegisterVO importDataVO =  null;
       //定义时间格式转换
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
		//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
		format.setLenient(false);
		formatRQ.setLenient(false);

		for (ImportGPRData dataArray : GPRSList) {
			importDataVO = new GspProductRegisterVO();
			int arrayIndex = 0;
//序号
			try {
				System.out.println();
				importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
				if (Integer.parseInt(dataArray.getSeq()) <= 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}
			System.out.println(dataArray.getSeq()+"=======================");
			System.out.println(Integer.parseInt(dataArray.getSeq())+"=============");

//注册证编号
				try{
					if (StringUtils.isEmpty(dataArray.getProductRegisterNo())) {
						throw new Exception();
					}else if(dataArray.getProductRegisterNo()!=null){
						GspProductRegister productRegisters=gspProductRegisterService.queryByRegisterNo(dataArray.getProductRegisterNo());
						//循环去重
						for(GspProductRegisterVO vo:importData){
							if(vo.getProductRegisterNo().equals(dataArray.getProductRegisterNo())){
								throw new Exception();
							}
						}
						if(productRegisters!=null){
							throw new Exception();

						}else {

							importDataVO.setProductRegisterNo(dataArray.getProductRegisterNo());
						}
					}else {
						throw new Exception();
					}
				}catch (Exception e){
					rowResult.append("[注册证编号]，该注册证编号已经存在或者没有输入").append(" ");
				}


//产品名称
			try {
                if (StringUtils.isEmpty(dataArray.getProductNameMain())) {
					throw new Exception();
				}else{

						importDataVO.setProductNameMain(dataArray.getProductNameMain());


				}

			} catch (Exception e) {
				rowResult.append("[产品名称],该产品名称没有输入").append(" ");
			}

			GspInstrumentCatalog  gspInstrumentCatalog = new GspInstrumentCatalog();
//验证是否是存在的器械目录
			try {
				GspInstrumentCatalog gspInstrumentCatalog1 = new GspInstrumentCatalog();
				gspInstrumentCatalog1.setClassifyId(dataArray.getClassifyId());			   //分类
				gspInstrumentCatalog1.setVersion(dataArray.getProductRegisterVersion());   //版本
				gspInstrumentCatalog1.setInstrumentCatalogName(dataArray.getClassifyCatalog()); //名称

				  gspInstrumentCatalog = gspInstrumentCatalogMybatisDao.queryByCPC(gspInstrumentCatalog1);
				if(gspInstrumentCatalog==null){
					throw new Exception();
				}

			} catch (Exception e) {
				rowResult.append("[管理分类,注册证版本,分类目录]，匹配器械目录错误").append(" ");
			}

//管理分类
			try {
				if(dataArray.getClassifyId()!=null){
					boolean con=false;
                      List<EasyuiCombobox>  catalogClassifyList=basCodesService.getBy(Constant.CODE_CATALOG_CLASSIFY);
                      for(EasyuiCombobox e:catalogClassifyList){
                      	if(e.getValue().equals(dataArray.getClassifyId())){
                             importDataVO.setClassifyId(e.getId());
                             con=true;
						}
					  }
                      if(con==false){
						  throw new Exception();
					  }
				}else if(StringUtils.isEmpty(dataArray.getClassifyId())){
					throw new Exception();
				}

			} catch (Exception e) {
       			rowResult.append("[管理分类]，未输入或输入错误").append(" ");
			}
//注册证版本
			try {
				if(dataArray.getProductRegisterVersion()!=null){
					boolean con=false;
					List<EasyuiCombobox>  productRegisterVersionList=basCodesService.getBy(Constant.CODE_CATALOG_VERSION);
					for(EasyuiCombobox e:productRegisterVersionList){
						if(e.getValue().equals(dataArray.getProductRegisterVersion())){
							importDataVO.setProductRegisterVersion(e.getId());
							con=true;
						}
					}
					if(con==false){
						throw new Exception();
					}
				}
				else if(StringUtils.isEmpty(dataArray.getClassifyId())){
					throw new Exception();
				}
			} catch (Exception e) {
      			rowResult.append("[注册证版本]，未输入或者输入错误").append(" ");
			}

//分类目录
			try{
				if(dataArray.getClassifyCatalog()==null||dataArray.getClassifyCatalog().equals("")||dataArray.getClassifyCatalog().equals("/xxxxxx")){
					throw new Exception();
				}else{
					importDataVO.setClassifyCatalog(dataArray.getClassifyCatalog());
				}

			}catch (Exception e){
        			rowResult.append("[分类目录]，未输入").append(" ");


			}
//产地
			try{
				if(dataArray.getProductionAddress()==null||dataArray.getProductionAddress().equals("")||dataArray.getProductionAddress().equals("/xxxxxx")){
//				    throw new Exception();
				}else{
					importDataVO.setProductionAddress(dataArray.getProductionAddress());
				}

			}catch (Exception e){
//			    rowResult.append("[产地]，未输入").append(" ");


			}
//储存条件
			try{
				if(dataArray.getStorageConditions()==null||dataArray.getStorageConditions().equals("")||dataArray.getStorageConditions().equals("/xxxxxx")){
					throw new Exception();
				}else{
					importDataVO.setStorageConditions(dataArray.getStorageConditions());
				}

			}catch (Exception e){
				rowResult.append("[储存条件]，未输入").append(" ");


			}
//运输条件
			try{
				if(dataArray.getTransportConditionMain()==null||dataArray.getTransportConditionMain().equals("")||dataArray.getTransportConditionMain().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setTransportConditionMain(dataArray.getTransportConditionMain());
				}

			}catch (Exception e){
//				rowResult.append("[运输条件]，未输入").append(" ");


			}
//注册人名称,生产企业
			try{
				if(dataArray.getEnterpriseName()==null||dataArray.getEnterpriseName().equals("")||dataArray.getEnterpriseName().equals("/xxxxxx")){
					throw new Exception();
				}else{
					boolean con=false;
					EasyuiDatagridPager e = new EasyuiDatagridPager();
					e.setRows(100000000);
					GspEnterpriseInfoQuery enQuery = new GspEnterpriseInfoQuery();
					enQuery.setEnterpriseName(dataArray.getEnterpriseName());
					EasyuiDatagrid<GspEnterpriseInfoVO>  pagedDatagrid=gspEnterpriseInfoService.getPagedDatagrid(e,enQuery);
					List<GspEnterpriseInfoVO> gspEnterpriseInfoVOList=pagedDatagrid.getRows();
					for(GspEnterpriseInfoVO info :gspEnterpriseInfoVOList){
						if(info.getEnterpriseName().equals(dataArray.getEnterpriseName())){
							importDataVO.setEnterpriseId(info.getEnterpriseId());
							con=true;
							break;
						}
					}
					if(con==false){
						throw  new Exception();
					}
				}

			}catch (Exception e){
				rowResult.append("[注册人名称,生产企业]，未输入或者输入错误").append(" ");


			}
//注册人住所
			try{
				if(dataArray.getProductRegisterAddress()==null||dataArray.getProductRegisterAddress().equals("")||dataArray.getProductRegisterAddress().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setProductRegisterAddress(dataArray.getProductRegisterAddress());
				}

			}catch (Exception e){
//				rowResult.append("[注册人住所]，未输入").append(" ");


			}
//生产地址
			try{
				if(dataArray.getProductProductionAddress()==null||dataArray.getProductProductionAddress().equals("")||dataArray.getProductProductionAddress().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setProductProductionAddress(dataArray.getProductProductionAddress());
				}

			}catch (Exception e){
//				rowResult.append("[生产地址]，未输入").append(" ");


			}
//代理人名称
			try{
				if(dataArray.getAgentName()==null||dataArray.getAgentName().equals("")||dataArray.getAgentName().equals("/xxxxxx")){
//					throw new Exception();
				}else{
     				importDataVO.setAgentName(dataArray.getAgentName());
				}

			}catch (Exception e){
//				rowResult.append("[代理人名称]，未输入").append(" ");


			}
//代理人住所
			try{
				if(dataArray.getAgentAddress()==null||dataArray.getAgentAddress().equals("")||dataArray.getAgentAddress().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setAgentAddress(dataArray.getAgentAddress());
				}

			}catch (Exception e){
//				rowResult.append("[代理人住所]，未输入").append(" ");


			}
//审批部门
			try{
				if(dataArray.getApprovalDepartment()==null||dataArray.getApprovalDepartment().equals("")||dataArray.getApprovalDepartment().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setApprovalDepartment(dataArray.getApprovalDepartment());
				}

			}catch (Exception e){
//				rowResult.append("[审批部门]，未输入").append(" ");


			}
//批准日期
			try{
				if(dataArray.getApproveDate()==null||dataArray.getApproveDate().equals("")||dataArray.getApproveDate().equals("/xxxxxx")){
					throw new Exception();
				}else{
//					importDataVO.setApproveDate(new Date());
					importDataVO.setApproveDate(DateUtil.parse(dataArray.getApproveDate(),"yyyy-MM-dd"));
				}

			}catch (Exception e){
				rowResult.append("[批准日期]，未输入或者格式错误").append(" ");


			}
//有效期至
			try{
				if(dataArray.getProductRegisterExpiryDate()==null||dataArray.getProductRegisterExpiryDate().equals("")||dataArray.getProductRegisterExpiryDate().equals("/xxxxxx")){
					throw new Exception();
				}else{
					//importDataVO.setProductRegisterExpiryDate(new Date());
					importDataVO.setProductRegisterExpiryDate(DateUtil.parse(dataArray.getProductRegisterExpiryDate(),"yyyy-MM-dd"));
				}

			}catch (Exception e){
				rowResult.append("[有效期至]，未输入或者格式错误").append(" ");


			}
//结构及组成
			try{
				if(dataArray.getStructureAndComposition()==null||dataArray.getStructureAndComposition().equals("")||dataArray.getStructureAndComposition().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setStructureAndComposition(dataArray.getStructureAndComposition());
				}

			}catch (Exception e){
//				rowResult.append("[结构及组成]，未输入").append(" ");


			}
//适用范围
			try{
				if(dataArray.getApplyScope()==null||dataArray.getApplyScope().equals("")||dataArray.getApplyScope().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setApplyScope(dataArray.getApplyScope());
				}

			}catch (Exception e){
//				rowResult.append("[适用范围]，未输入").append(" ");


			}
//预期用途
			try{
				if(dataArray.getExpectUse()==null||dataArray.getExpectUse().equals("")||dataArray.getExpectUse().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setExpectUse(dataArray.getExpectUse());
				}

			}catch (Exception e){
//				rowResult.append("[预期用途]，未输入").append(" ");


			}
//主要组成部门
			try{
				if(dataArray.getMainPart()==null||dataArray.getMainPart().equals("")||dataArray.getMainPart().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setMainPart(dataArray.getMainPart());
				}

			}catch (Exception e){
//				rowResult.append("[主要组成部门]，未输入").append(" ");


			}
//附件
			try{
				if(dataArray.getProductRegsiterUrl()==null||dataArray.getProductRegsiterUrl().equals("")||dataArray.getProductRegsiterUrl().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setProductRegsiterUrl(dataArray.getProductRegsiterUrl());
				}

			}catch (Exception e){
//				rowResult.append("[附件]，未输入").append(" ");


			}
//其他内容
			try{
				if(dataArray.getOtherContent()==null||dataArray.getOtherContent().equals("")||dataArray.getOtherContent().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setOtherContent(dataArray.getOtherContent());
				}

			}catch (Exception e){
//				rowResult.append("[其他内容]，未输入").append(" ");


			}
//备注
			try{
				if(dataArray.getRemark()==null||dataArray.getRemark().equals("")||dataArray.getRemark().equals("/xxxxxx")){
//					throw new Exception();
				}else{
					importDataVO.setRemark(dataArray.getRemark());
				}

			}catch (Exception e){
//				rowResult.append("[其他内容]，未输入").append(" ");


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
		map.put("注册证编号", "productRegisterNo");
		map.put("产品名称", "productNameMain");
		map.put("管理分类", "classifyId");

	    map.put("注册证版本", "productRegisterVersion");
	    map.put("分类目录", "classifyCatalog");
		map.put("产地", "productionAddress");
	    map.put("储存条件", "storageConditions");

	    map.put("运输条件", "transportConditionMain");
	    map.put("注册人名称/生产企业", "enterpriseName");
	    map.put("注册人住所", "productRegisterAddress");
	    map.put("生产地址", "productProductionAddress");
	    map.put("代理人名称", "agentName");
	    map.put("代理人住所", "agentAddress");
		map.put("审批部门", "approvalDepartment");
		map.put("批准日期", "approveDate");
		map.put("有效期至", "productRegisterExpiryDate");
		map.put("结构及组成", "structureAndComposition");
		map.put("适用范围", "applyScope");
		map.put("预期用途", "expectUse");
		map.put("主要组成部分", "mainPart");
		map.put("附件", "productRegsiterUrl");

	    map.put("其他内容", "otherContent");
	    map.put("备注", "remark");
	    return map;
	}
	

//保存importDataList
	private void saveProductRegister(List<GspProductRegisterVO> importDataList, StringBuilder resultMsg) {
		GspProductRegister gspProductRegister = null;


		for(GspProductRegisterVO importDataVO : importDataList){
			gspProductRegister = new GspProductRegister();
			BeanUtils.copyProperties(importDataVO, gspProductRegister);

				String i = RandomUtil.getUUID();
			    gspProductRegister.setProductRegisterId(i);
				gspProductRegister.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspProductRegister.setCreateDate(new Date());
				gspProductRegister.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspProductRegister.setEditDate(new Date());
				gspProductRegister.setIsUse(Constant.IS_USE_YES);
				//保存订单主信息
			    gspProductRegisterMybatisDao.add(gspProductRegister);


				GspInstrumentCatalog gspInstrumentCatalog1 = new GspInstrumentCatalog();
				gspInstrumentCatalog1.setClassifyId(gspProductRegister.getClassifyId());				 //分类
				gspInstrumentCatalog1.setVersion(gspProductRegister.getProductRegisterVersion());   	 //版本
				gspInstrumentCatalog1.setInstrumentCatalogName(gspProductRegister.getClassifyCatalog()); //名称
				GspInstrumentCatalog gspInstrumentCatalog = gspInstrumentCatalogMybatisDao.queryByCPC1(gspInstrumentCatalog1);

				GspOperateDetail gspOperateDetail = new GspOperateDetail();
				gspOperateDetail.setOperateId(gspInstrumentCatalog.getInstrumentCatalogId());
				gspOperateDetail.setLicenseId(i);
				gspOperateDetail.setCreateDate(new Date());
				gspOperateDetail.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspOperateDetail.setIsUse("1");
				gspOperateDetail.setLicenseType(Constant.LICENSE_TYPE_REGISTER);
				gspOperateDetailMybatisDao.add(gspOperateDetail);

					resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");

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
