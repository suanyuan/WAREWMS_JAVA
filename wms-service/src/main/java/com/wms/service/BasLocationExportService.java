package com.wms.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.BasLocation;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasLocationMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasLocationQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.BasLocationExportForm;
import com.wms.vo.form.BasLocationForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("basLocationExportService")
public class BasLocationExportService {
	
	@Autowired
	private BasLocationMybatisDao basLocationMybatisDao;
	
	private static final String ORDER_HEAD = "库位编码,拣货顺序,库位使用,库位类型,上架顺序,库位属性,库位处理,周转需求,上架区,拣货区,体积限制,重量限制,箱数限制,数量限制,托盘限制,允许混放产品,允许混放批次,忽略ID,长度,宽度,高度";
	public void exportLocationData(HttpServletResponse response, BasLocationExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		BasLocationForm basLocationForm = new BasLocationForm();
		
		basLocationForm.setLocationid(form.getLocationid());
		basLocationForm.setLocationusage(form.getLocationusage());
		basLocationForm.setLocationcategory(form.getLocationcategory());
		basLocationForm.setLocationattribute(form.getLocationattribute());
		basLocationForm.setLocationhandling(form.getLocationhandling());
		basLocationForm.setDemand(form.getDemand());
		basLocationForm.setPutawayzone(form.getPutawayzone());
		basLocationForm.setPickzone(form.getPickzone());
		
		StringBuilder filename = new StringBuilder();
		filename.append("location_report_").append(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS")).append(".csv");
		StringBuilder sb = new StringBuilder();
		sb.append("attachment; filename=")
		  .append(URLEncoder.encode(filename.toString(),"UTF-8"));filename.setLength(0);
		response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
		
		try(BufferedWriter csvWtriter = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), 4096)){
			List<String> dataList = new ArrayList<String>();
			BasLocationQuery query = new BasLocationQuery();
			BeanUtils.copyProperties(basLocationForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			List<BasLocation> locationList = basLocationMybatisDao.queryByList(mybatisCriteria);
			
			for(BasLocation location : locationList){
				sb.append(location.getLocationid()).append(",")
				  .append(location.getPicklogicalsequence()).append(",")
				  .append(location.getLocationusage()).append(",")
				  .append(location.getLocationcategory()).append(",")
				  .append(location.getLogicalsequence()).append(",")
				  .append(location.getLocationattribute()).append(",")
				  .append(location.getLocationhandling()).append(",")
				  .append(location.getDemand()).append(",")
				  .append(location.getPutawayzone()==null?"":location.getPutawayzone()).append(",")
				  .append(location.getPickzone()==null?"":location.getPickzone()).append(",")
				  .append(location.getCubiccapacity()==null?0:location.getCubiccapacity()).append(",")
				  .append(location.getWeightcapacity()==null?0:location.getWeightcapacity()).append(",")
				  .append(location.getCscount()).append(",")
				  .append(location.getCountcapacity()==null?0:location.getCscount()).append(",")
				  .append(location.getPlcount()==null?0:location.getPlcount()).append(",")
				  .append(location.getMixFlag()==null?"":location.getMixFlag()).append(",")
				  .append(location.getMixLotflag()==null?"":location.getMixLotflag()).append(",")
				  .append(location.getLoseidFlag()==null?0:location.getLoseidFlag()).append(",")
				  .append(location.getLength()==null?0:location.getLength()).append(",")
				  .append(location.getWidth()==null?0:location.getWidth()).append(",")
				  .append(location.getHeight()==null?0:location.getHeight()).append(",");
				dataList.add(sb.toString());
				sb.setLength(0);
			}
			csvWtriter.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));  
            writeRow(ORDER_HEAD, csvWtriter);
            for (String row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
		}
	}
	
    private void writeRow(String row, BufferedWriter csvWriter) throws IOException {
        csvWriter.write(row);
        csvWriter.newLine();
    }
	

	public void exportLocationDataToExcel(HttpServletResponse response, BasLocationExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		BasLocationForm basLocationForm = new BasLocationForm();
		
		basLocationForm.setLocationid(form.getLocationid());
		basLocationForm.setLocationusage(form.getLocationusage());
		basLocationForm.setLocationcategory(form.getLocationcategory());
		basLocationForm.setLocationattribute(form.getLocationattribute());
		basLocationForm.setLocationhandling(form.getLocationhandling());
		basLocationForm.setDemand(form.getDemand());
		basLocationForm.setPutawayzone(form.getPutawayzone());
		basLocationForm.setPickzone(form.getPickzone());
		
		try {  
	        // 获取前台传来的题型和课程  
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
	        BasLocationQuery query = new BasLocationQuery();
			BeanUtils.copyProperties(basLocationForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "库位";  
	        // excel要导出的数据  
	        List<BasLocation> locationList = basLocationMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (locationList == null || locationList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(locationList, fieldMap, sheetName, response);  
	        	//System.out.println("导出成功~~~~");  
	        }  
	    } catch (ExcelException e) {  
	        e.printStackTrace();  
	    }  
	}

	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {
	
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
	
		superClassMap.put("locationid", "库位编码");
		superClassMap.put("picklogicalsequence", "拣货顺序");
		superClassMap.put("locationusage", "库位使用");
		superClassMap.put("locationcategory", "库位类型");
		superClassMap.put("logicalsequence", "上架顺序");
		superClassMap.put("locationattribute", "库位属性");
		superClassMap.put("locationhandling", "库位处理");
		superClassMap.put("demand", "周转需求");
		superClassMap.put("putawayzone", "上架区");
		superClassMap.put("pickzone", "拣货区");
		superClassMap.put("cubiccapacity", "体积限制");
		superClassMap.put("weightcapacity", "重量限制");
		superClassMap.put("cscount", "箱数限制");
		superClassMap.put("countcapacity", "数量限制");
		superClassMap.put("plcount", "托盘限制");
		superClassMap.put("mixFlag", "允许混放产品");
		superClassMap.put("mixLotflag", "允许混放批次");
		superClassMap.put("loseidFlag", "忽略ID");
		superClassMap.put("length", "长度");
		superClassMap.put("width", "宽度");
		superClassMap.put("height", "高度");
		
		return superClassMap;
	}

}
