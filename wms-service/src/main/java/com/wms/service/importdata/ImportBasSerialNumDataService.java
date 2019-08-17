package com.wms.service.importdata;

import com.wms.entity.BasSerialNum;
import com.wms.entity.ImportBasSerialNumPackingLineData;
import com.wms.entity.ImportBasSerialNumPackingListData;
import com.wms.mybatis.dao.BasSerialNumMybatisDao;
import com.wms.service.BasCodesService;
import com.wms.service.BasSerialNumService;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.BasSerialNumVO;
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
import java.util.LinkedHashMap;
import java.util.List;

@Service("ImportBasSerialNumDataService")
public class ImportBasSerialNumDataService {


	@Autowired
	private BasSerialNumMybatisDao basSerialNumMybatisDao;
	@Autowired
	private BasCodesService basCodesService;
	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;
	@Autowired
	private BasSerialNumService basSerialNumService;
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
            String sheetName = "PACKING_LIST";
            String sheetName1 = "PACKING_LINE";

            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> PackingListMap = getLeadInFiledPublicQuestionBank();
            LinkedHashMap<String, String> PackingLineMap = getLeadInFiledPublicQuestionBank1();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields =new String[] {"SERIAL_NUMBER"};
            String[] uniqueFields1 =new String[] {"DELIVERY_NUMBER","PACKAGE_NUMBER","MATERIAL_NUMBER","BATCH_NUMBER"};
            //获取需要导入的具体的表
            Class list = new ImportBasSerialNumPackingListData().getClass();
            Class line = new ImportBasSerialNumPackingLineData().getClass();
            //excel转化成的list集合
            List<ImportBasSerialNumPackingListData>  BasList = null;
            List<ImportBasSerialNumPackingLineData>  BasLine = null;
            try {
//调用excle共用类，转化成list
				BasList = ExcelUtil.excelToList(in, sheetName, list, PackingListMap,null);
				in = excelFile.getInputStream();
				BasLine = ExcelUtil.excelToList(in, sheetName1, line, PackingLineMap,null);


            } catch (ExcelException e) {
                //e.printStackTrace();

            }
//保存实体集合
			if(BasList==null){
				resultMsg.append("错误:execel的Sheet表名应为:PACKING_LIST!");
			}
			if(BasLine==null){
				resultMsg.append("错误:execel的Sheet表名应为:PACKING_LINE!");
			}
			else {
				List<BasSerialNumVO> importDataList = this.listToBean(BasList,BasLine,resultMsg);


					if (true) {

						this.saveBasSerialNum(importDataList, resultMsg);// 转成订单资料存入资料库
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


	/**
	 * 把导入的list数据转换成BasSerialNumVO
	 * @param BasList
	 * @param resultMsg
	 * @return
	 */
	private List<BasSerialNumVO> listToBean(List<ImportBasSerialNumPackingListData>  BasList,List<ImportBasSerialNumPackingLineData>  BasLine, StringBuilder resultMsg) {
		StringBuilder rowResult = new StringBuilder();
		List<BasSerialNumVO> importData = new ArrayList<BasSerialNumVO>();
		List<BasSerialNumVO> importDataR = new ArrayList<BasSerialNumVO>();
		BasSerialNumVO importDataVO =  null;
//定义时间格式转换
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
		format.setLenient(false);
		formatRQ.setLenient(false);

		for (ImportBasSerialNumPackingListData dataArray : BasList) {
			importDataVO = new BasSerialNumVO();

//SERIAL_NUMBER
				try{
					if (StringUtils.isEmpty(dataArray.getSerialNum())) {
						 throw new Exception();
					}else if(dataArray.getSerialNum()!=null) {
//						判断importData是否已经又已经存在的序列号
						for (BasSerialNumVO i : importData) {
							if(i.getSerialNum().equals(dataArray.getSerialNum())){
								throw new Exception();
							}
						}
//						判断数据库是否已经又已经存在的序列号
                      BasSerialNum num=basSerialNumMybatisDao.queryById(dataArray.getSerialNum());
                      if(num!=null){
						  throw new Exception();
					  }else{
						  importDataVO.setSerialNum(dataArray.getSerialNum());
					  }



					}
				}catch (Exception e){
					rowResult.append("[SERIAL_NUMBER]该SERIAL_NUMBER已经存在或者没有输入").append(" ");
				}


//BATCH_NUMBER
			try {
                if (StringUtils.isEmpty(dataArray.getBatchNum())) {
					throw new Exception();
				}else if(dataArray.getBatchNum()!=null){

						importDataVO.setBatchNum(dataArray.getBatchNum());
					}
			} catch (Exception e) {
				rowResult.append("[BATCH_NUMBER]该BATCH_NUMBER没有输入").append(" ");
			}
//MATERIAL_NUMBER
			try {
                if (StringUtils.isEmpty(dataArray.getMaterialNum())) {
					throw new Exception();
				}else if(dataArray.getBatchNum()!=null){

						importDataVO.setMaterialNum(dataArray.getMaterialNum());
					}
			} catch (Exception e) {
				rowResult.append("[MATERIAL_NUMBER]该MATERIAL_NUMBER没有输入").append(" ");
			}
//DELIVERY_NUMBER
			try {
                if (StringUtils.isEmpty(dataArray.getDeliveryNum())) {
					throw new Exception();
				}else if(dataArray.getBatchNum()!=null){

						importDataVO.setDeliveryNum(dataArray.getDeliveryNum());
					}
			} catch (Exception e) {
				rowResult.append("[DELIVERY_NUMBER]该DELIVERY_NUMBER已经存在或者没有输入").append(" ");
			}
//PACKAGE_NUMBER
			try {
                if (StringUtils.isEmpty(dataArray.getPackageNum())) {
					throw new Exception();
				}else if(dataArray.getBatchNum()!=null){

						importDataVO.setPackageNum(dataArray.getPackageNum());
					}
			} catch (Exception e) {
				rowResult.append("[PACKAGE_NUMBER]该PACKAGE_NUMBER没有输入").append(" ");
			}




//增加resultMsg
			if (rowResult.length() > 0) {
				if(rowResult.lastIndexOf("，") > -1){
					rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
				}
				resultMsg.append("SERIAL_NUMBER：").append(dataArray.getSerialNum()).append("资料有错 ").append(rowResult).append(" ");
				rowResult.setLength(0);
			} else {
				importData.add(importDataVO);
			}

		}
//循环importData 判断BasLine是否为空 插入BasLine数据 为空要去除
		if(importData.size()>0){
          boolean con;
			for (BasSerialNumVO serialNumVO : importData) {
                       con=false;
				for (ImportBasSerialNumPackingLineData line: BasLine) {
                      if(serialNumVO.getDeliveryNum().equals(line.getDeliveryNum())&&serialNumVO.getBatchNum().equals(line.getBatchNum())
					  &&serialNumVO.getMaterialNum().equals(line.getMaterialNum())&&serialNumVO.getPackageNum().equals(line.getPackageNum())){
                      	serialNumVO.setPurchaseOrder(line.getPurchaseOrder());
                      	serialNumVO.setUom(line.getUom());
                      	serialNumVO.setExpireDate(line.getExpireDate());
                      	serialNumVO.setBatchFlag(line.getBatchFlag());
                        serialNumVO.setProductDate(line.getProductDate());
                        importDataR.add(serialNumVO);
                        con=true;
                        break;
                      }
				}
     if(!con){
		 resultMsg.append("SERIAL_NUMBER：").append(serialNumVO.getSerialNum()).append("资料有错 ").append("[SERIAL_NUMBER]该SERIAL_NUMBER在PACKING_LINE没有对应的行!").append(" ");

	 }
			}
		}
		return importDataR;
	}

	/**
	 * excel的表头与文字对应
	 * @return
	 */
	public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
	    // excel的表头与文字对应
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	    map.put("SERIAL_NUMBER", "serialNum");
		map.put("BATCH_NUMBER", "batchNum");
		map.put("MATERIAL_NUMBER", "materialNum");
		map.put("PACKAGE_NUMBER", "packageNum");
		map.put("DELIVERY_NUMBER", "deliveryNum");
	    return map;
	}
public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank1() {
	    // excel的表头与文字对应
	    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("BATCH_NUMBER", "batchNum");
		map.put("MATERIAL_NUMBER", "materialNum");
		map.put("PACKAGE_NUMBER", "packageNum");
		map.put("DELIVERY_NUMBER", "deliveryNum");
		map.put("PURCHASE_ORDER", "purchaseOrder");
//			map.put("DELIVERY_QTY", "deliveryNum");
		map.put("UOM", "uom");
		map.put("EXP_DATE", "expireDate");
		map.put("生产日期", "productDate");
		map.put("BATCH_FLAG", "batchFlag");

	    return map;
	}


	/**
	 * 保存importDataList
	 * @param importDataList
	 * @param resultMsg
	 */
	private void saveBasSerialNum(List<BasSerialNumVO> importDataList, StringBuilder resultMsg) {
		BasSerialNum basSerialNum = null;

		for(BasSerialNumVO importDataVO : importDataList){
			basSerialNum = new BasSerialNum();
			BeanUtils.copyProperties(importDataVO, basSerialNum);

				basSerialNum.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
//				basSerialNum.setAddtime(new Date()+"");
				basSerialNum.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
//				basSerialNum.setEdittime(new Date()+"");
				//保存订单主信息
			   basSerialNumMybatisDao.add(basSerialNum);

              resultMsg.append("SERIAL_NUMBER：").append(importDataVO.getSerialNum()).append("资料导入成功").append(" ");

			}

		}



}
