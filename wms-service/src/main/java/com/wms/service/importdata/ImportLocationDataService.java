package com.wms.service.importdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.wms.entity.BasLocation;
import com.wms.entity.BasLocationToExcel;
import com.wms.mybatis.dao.BasLocationMybatisDao;
import com.wms.query.BasLocationQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.LoginUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.ImportLocationDataVO;
import com.wms.vo.ImportSkuDataVO;
import com.wms.vo.Json;

@Service("ImportLocationDataService")
public class ImportLocationDataService {

	private static final String[] LOCATION_COLUMN = {"序号","库位编码","拣货顺序","库位使用","库位类型","上架顺序","库位属性","库位处理","周转需求","上架区","拣货区","体积限制","重量限制","箱数限制","数量限制","托盘限制","允许混放产品","允许混放批次","忽略ID","长度","宽度","高度"};
	
	@Autowired
	private BasLocationMybatisDao basLocationMybatisDao;
	
	
	/**
	 * 导入库位资料
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
					List<ImportLocationDataVO> importDataList = this.dataToBean(dataArrayList, resultMsg);// 资料转成Java Bean
					//this.saveSku(importDataList, resultMsg);
 					if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
 						this.validateLocation(importDataList, resultMsg);// 验证库位是否存在
							if (resultMsg.length() == 0) {
								this.saveLocation(importDataList, resultMsg);// 转成库位资料存入资料库
								isSuccess = true;
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
			for (int i = 0; i < LOCATION_COLUMN.length; i++) {
				if (StringUtils.isNotEmpty(LOCATION_COLUMN[i]) && dataArray.length > i && StringUtils.isEmpty(dataArray[i])) {
					rowResult.append(LOCATION_COLUMN[i]).append("错误").append("，");
				}
			}
			if (rowResult.length() > 0) {
				rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				resultMsg.append("序号：").append(dataArray[0]).append("资料未输入=>").append(rowResult).append(" ");
				rowResult.setLength(0);
			}
		}
	}
	
	private List<ImportLocationDataVO> dataToBean(List<String[]> dataArrayList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<ImportLocationDataVO> importData = new ArrayList<ImportLocationDataVO>();
		ImportLocationDataVO importDataVO = null;
		String quantityData = null;
		for(String[] dataArray : dataArrayList){
			int arrayIndex = 0;
			importDataVO = new ImportLocationDataVO();
			try {
				importDataVO.setSeq(Integer.parseInt(dataArray[arrayIndex++]));// 序号
				if (importDataVO.getSeq() <= 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}
			try {
				importDataVO.setLocationid(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getLocationid())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[库位编码]，未输入").append(" ");
			}
			try {
				importDataVO.setPicklogicalsequence(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getPicklogicalsequence())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[拣货顺序]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationusage(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getLocationusage())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[库位使用]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationcategory(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getLocationcategory())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[库位类型]，未输入").append(" ");
			}
			try {
				importDataVO.setLogicalsequence(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getLogicalsequence())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[上架顺序]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationattribute(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getLocationattribute())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[库位属性]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationhandling(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getLocationhandling())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[库位处理]，未输入").append(" ");
			}
			try {
				importDataVO.setDemand(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getDemand())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[周转需求]，未输入").append(" ");
			}
			try {
				importDataVO.setPutawayzone(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getPutawayzone())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[上架区]，未输入").append(" ");
			}
			try {
				importDataVO.setPickzone(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getPickzone())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[拣货区]，未输入").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCubiccapacity(new BigDecimal(quantityData));
					if (importDataVO.getCubiccapacity().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[体积限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setWeightcapacity(new BigDecimal(quantityData));
					if (importDataVO.getWeightcapacity().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[重量限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCscount(new BigDecimal(quantityData));
					if (importDataVO.getCscount().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[箱数限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCountcapacity(new BigDecimal(quantityData));
					if (importDataVO.getCountcapacity().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[数量限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setPlcount(new BigDecimal(quantityData));
					if (importDataVO.getPlcount().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[托盘限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				importDataVO.setMixFlag(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getMixFlag())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[允许混放产品]，未输入").append(" ");
			}
			try {
				importDataVO.setMixLotflag(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getMixLotflag())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[允许混放批次]，未输入").append(" ");
			}
			try {
				importDataVO.setLoseidFlag(dataArray[arrayIndex++]);
				if (StringUtils.isEmpty(importDataVO.getLoseidFlag())) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[忽略ID]，未输入").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setLength(new BigDecimal(quantityData));
					if (importDataVO.getLength().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[长度]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setWidth(new BigDecimal(quantityData));
					if (importDataVO.getWidth().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[宽度]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray[arrayIndex++];
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setHeight(new BigDecimal(quantityData));
					if (importDataVO.getHeight().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[高度]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
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

	/**
	 * 导入库位
 	 * @param excelFile
	 * @return
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
            String sheetName = "库位";
            // excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excle表头数组，防止重复用的
            String[] uniqueFields =new String[] { "库位编码" };
            //获取需要导入的具体的表
            Class basLocation = new BasLocationToExcel().getClass();
            //excel转化成的list集合
            List<BasLocationToExcel> basLocationList = null;
            try {
            	
                //调用excle共用类，转化成list
            	basLocationList = ExcelUtil.excelToList(in, sheetName, basLocation, map, null);
            	
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<ImportLocationDataVO> importDataList = this.listToBean(basLocationList, resultMsg);
           
            if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
            	
            	this.validateLocation(importDataList, resultMsg);// 验证库位是否存在
				if (resultMsg.length() == 0) {
					
					        this.saveLocation(importDataList, resultMsg);// 转成库位资料存入资料库
					  
							isSuccess = true;
						}
					}
				
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		json.setMsg(resultMsg.toString());
		json.setSuccess(isSuccess);
		return json;
	}
	
	public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
	    // excel的表头与文字对应
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	    map.put("序号", "seq");
	    map.put("库位编码", "locationid");
	    map.put("拣货顺序", "picklogicalsequence");
	    map.put("库位使用", "locationusage");
	    map.put("库位类型", "locationcategory");
	    map.put("上架顺序", "logicalsequence");
	    map.put("库位属性", "locationattribute");
	    map.put("库位处理", "locationhandling");
	    map.put("周转需求", "demand");
	    map.put("上架区", "putawayzone");
	    map.put("拣货区", "pickzone");
	    map.put("体积限制", "cubiccapacity");
	    map.put("重量限制", "weightcapacity");
	    map.put("箱数限制", "cscount");
	    map.put("数量限制", "countcapacity");
	    map.put("托盘限制", "plcount");
	    map.put("允许混放产品", "mixFlag");
	    map.put("允许混放批次", "mixLotflag");
	    map.put("忽略ID", "loseidFlag");
	    map.put("长度", "length");
	    map.put("宽度", "width");
	    map.put("高度", "height");
	    return map;
	}
	
	private List<ImportLocationDataVO> listToBean(List<BasLocationToExcel> basLocationList, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<ImportLocationDataVO> importData = new ArrayList<ImportLocationDataVO>();
		ImportLocationDataVO importDataVO = null;
		String quantityData = null;
		for(BasLocationToExcel dataArray : basLocationList){
			importDataVO = new ImportLocationDataVO();
			try {
				importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
				if (importDataVO.getSeq() <= 0) {
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
			}
			try {
				importDataVO.setLocationid(dataArray.getLocationid());
				if (StringUtils.isEmpty(importDataVO.getLocationid())) {
					throw new Exception();
				}else{
					for (ImportLocationDataVO vo : importData) {
						if(dataArray.getLocationid().equals(vo.getLocationid())){
							throw new Exception();
						}
					}
				}
			} catch (Exception e) {
				rowResult.append("[库位编码]，未输入或者和列表中其他库位编码重复!请检查!").append(" ");
			}
			try {
				importDataVO.setPicklogicalsequence(dataArray.getPicklogicalsequence());
				if (StringUtils.isEmpty(importDataVO.getPicklogicalsequence())) {
//					throw new Exception();
					importDataVO.setPicklogicalsequence(dataArray.getLocationid());
				}
			} catch (Exception e) {
				rowResult.append("[拣货顺序]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationusage(dataArray.getLocationusage());
				if (StringUtils.isEmpty(importDataVO.getLocationusage())) {
//					throw new Exception();
					importDataVO.setLocationusage("EA");

				}
			} catch (Exception e) {
				rowResult.append("[库位使用]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationcategory(dataArray.getLocationcategory());
				if (StringUtils.isEmpty(importDataVO.getLocationcategory())) {
					importDataVO.setLocationcategory("RK");
				}
			} catch (Exception e) {
				rowResult.append("[库位类型]，未输入").append(" ");
			}
			try {
				importDataVO.setLogicalsequence(dataArray.getLogicalsequence());
				if (StringUtils.isEmpty(importDataVO.getLogicalsequence())) {
//					throw new Exception();
					importDataVO.setLogicalsequence(dataArray.getLocationid());
				}
			} catch (Exception e) {
				rowResult.append("[上架顺序]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationattribute(dataArray.getLocationattribute());
				if (StringUtils.isEmpty(importDataVO.getLocationattribute())) {
//					throw new Exception();
					importDataVO.setLocationattribute("OK");
				}
			} catch (Exception e) {
				rowResult.append("[库位属性]，未输入").append(" ");
			}
			try {
				importDataVO.setLocationhandling(dataArray.getLocationhandling());
				if (StringUtils.isEmpty(importDataVO.getLocationhandling())) {
//					throw new Exception();
					importDataVO.setLocationhandling("OT");
				}
			} catch (Exception e) {
				rowResult.append("[库位处理]，未输入").append(" ");
			}
			try {
				importDataVO.setDemand(dataArray.getDemand());
				if (StringUtils.isEmpty(importDataVO.getDemand())) {
//					throw new Exception();
					importDataVO.setDemand("A");
				}
			} catch (Exception e) {
				rowResult.append("[周转需求]，未输入").append(" ");
			}
			try {
				importDataVO.setPutawayzone(dataArray.getPutawayzone());
				if (StringUtils.isEmpty(importDataVO.getPutawayzone())) {
//					throw new Exception();
					importDataVO.setPutawayzone("1B");
				}
			} catch (Exception e) {
				rowResult.append("[上架区]，未输入").append(" ");
			}
			try {
				importDataVO.setPickzone(dataArray.getPickzone());
				if (StringUtils.isEmpty(importDataVO.getPickzone())) {
//					throw new Exception();
					importDataVO.setPickzone("1B");
				}
			} catch (Exception e) {
				rowResult.append("[拣货区]，未输入").append(" ");
			}
			try {
				quantityData = dataArray.getCubiccapacity();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCubiccapacity(new BigDecimal(quantityData));
					if (importDataVO.getCubiccapacity().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else{
					importDataVO.setCubiccapacity(BigDecimal.ZERO);
				}
			} catch (Exception e) {
				rowResult.append("[体积限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getWeightcapacity();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setWeightcapacity(new BigDecimal(quantityData));
					if (importDataVO.getWeightcapacity().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else{
					importDataVO.setWeightcapacity(BigDecimal.ZERO);
				}
			} catch (Exception e) {
				rowResult.append("[重量限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getCscount();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCscount(new BigDecimal(quantityData));
					if (importDataVO.getCscount().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else{
					importDataVO.setCscount(BigDecimal.ZERO);

				}
			} catch (Exception e) {
				rowResult.append("[箱数限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getCountcapacity();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setCountcapacity(new BigDecimal(quantityData));
					if (importDataVO.getCountcapacity().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else{
					importDataVO.setCountcapacity(BigDecimal.ZERO);

				}
			} catch (Exception e) {
				rowResult.append("[数量限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getPlcount();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setPlcount(new BigDecimal(quantityData));
					if (importDataVO.getPlcount().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else{
					importDataVO.setPlcount(BigDecimal.ZERO);
				}
			} catch (Exception e) {
				rowResult.append("[托盘限制]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				importDataVO.setMixFlag(dataArray.getMixFlag());
				if (StringUtils.isEmpty(importDataVO.getMixFlag())) {
//					throw new Exception();
					importDataVO.setMixFlag("Y");

				}
			} catch (Exception e) {
				rowResult.append("[允许混放产品]，未输入").append(" ");
			}
			try {
				importDataVO.setMixLotflag(dataArray.getMixLotflag());
				if (StringUtils.isEmpty(importDataVO.getMixLotflag())) {
//					throw new Exception();
					importDataVO.setMixLotflag("Y");

				}
			} catch (Exception e) {
				rowResult.append("[允许混放批次]，未输入").append(" ");
			}
			try {
				importDataVO.setLoseidFlag(dataArray.getLoseidFlag());
				if (StringUtils.isEmpty(importDataVO.getLoseidFlag())) {
//					throw new Exception();
					importDataVO.setLoseidFlag("Y");

				}
			} catch (Exception e) {
				rowResult.append("[忽略ID]，未输入").append(" ");
			}
			try {
				quantityData = dataArray.getLength();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setLength(new BigDecimal(quantityData));
					if (importDataVO.getLength().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else{
					importDataVO.setLength(BigDecimal.ZERO);

				}
			} catch (Exception e) {
				rowResult.append("[长度]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getWidth();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setWidth(new BigDecimal(quantityData));
					if (importDataVO.getWidth().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else {
					importDataVO.setWidth(BigDecimal.ZERO);

				}
			} catch (Exception e) {
				rowResult.append("[宽度]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
			}
			try {
				quantityData = dataArray.getHeight();
				if(StringUtils.isNotEmpty(quantityData)){
					importDataVO.setHeight(new BigDecimal(quantityData));
					if (importDataVO.getHeight().compareTo(BigDecimal.ZERO)==-1) {
						throw new Exception();
					}
				}else{
					importDataVO.setHeight(BigDecimal.ZERO);

				}
			} catch (Exception e) {
				rowResult.append("[高度]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
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
	
	private void validateLocation(List<ImportLocationDataVO> importDataList, StringBuilder resultMsg) {
		BasLocation basLocation = null;
		BasLocationQuery locationQuery = new BasLocationQuery();
		for (ImportLocationDataVO importDataVO : importDataList) {
			locationQuery.setLocationid(importDataVO.getLocationid());
			basLocation = basLocationMybatisDao.queryById(locationQuery);
			if(basLocation != null){
				resultMsg.append("序号：").append(importDataVO.getSeq())
						 .append("，库位：").append(importDataVO.getLocationid()).append("，重复").append(" ");
			}
		}
	}
	
	private void saveLocation(List<ImportLocationDataVO> importDataList, StringBuilder resultMsg) {
		BasLocation basLocation = null;
		Date today = new Date();
		try {
			for (ImportLocationDataVO importDataVO : importDataList) {
				basLocation = new BasLocation();
				BeanUtils.copyProperties(importDataVO, basLocation);

				//赋默认值
				basLocation.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
				basLocation.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
				basLocation.setAddtime(today);
				basLocation.setEdittime(today);
				basLocation.setFacilityId("001");
				basLocation.setStatus("OK");

				basLocationMybatisDao.add(basLocation);
				resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
			}
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

}
