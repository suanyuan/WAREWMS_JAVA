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

import com.wms.entity.BasSku;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasSkuQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("basSkuExportService")
public class BasSkuExportService {
	
	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	
	private static final String ORDER_HEAD = "客户代码,产品,是否激活,中文名称,英文名称,条形码,包装代码,单位,首次入库,重量,体积,单价";
	public void exportSkuData(HttpServletResponse response, BasSkuExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		BasSkuForm basSkuForm = new BasSkuForm();
		
		basSkuForm.setCustomerid(form.getCustomerid());
		basSkuForm.setSku(form.getSku());
		basSkuForm.setActiveFlag(form.getActiveFlag());
		
		StringBuilder filename = new StringBuilder();
		filename.append("sku_report_").append(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS")).append(".csv");
		StringBuilder sb = new StringBuilder();
		sb.append("attachment; filename=")
		  .append(URLEncoder.encode(filename.toString(),"UTF-8"));filename.setLength(0);
		response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
		
		try(BufferedWriter csvWtriter = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), 4096)){
			List<String> dataList = new ArrayList<String>();
			BasSkuQuery query = new BasSkuQuery();
			BeanUtils.copyProperties(basSkuForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			List<BasSku> skuList = basSkuMybatisDao.queryByList(mybatisCriteria);
			
			for(BasSku sku : skuList){
				sb.append(sku.getCustomerid()).append(",")
				  .append(sku.getSku()).append(",")
				  .append(sku.getActiveFlag()).append(",")
				  .append(sku.getDescrC()).append(",")
				  .append(sku.getDescrE()==null?"":sku.getDescrE()).append(",")
				  .append(sku.getAlternateSku1()==null?"":sku.getAlternateSku1()).append(",")
				  .append(sku.getPackid()).append(",")
				  .append(sku.getReservedfield01()==null?"":sku.getReservedfield01()).append(",")
				  .append(sku.getFirstinbounddate()==null?"":sku.getFirstinbounddate()).append(",")
				  .append(sku.getGrossweight()==null?0:sku.getGrossweight()).append(",")
				  .append(sku.getCube()==null?0:sku.getCube()).append(",")
				  .append(sku.getPrice()==null?0:sku.getPrice()).append(",");
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
	

	public void exportSkuDataToExcel(HttpServletResponse response, BasSkuExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		BasSkuForm basSkuForm = new BasSkuForm();
		
		basSkuForm.setCustomerid(form.getCustomerid());
		basSkuForm.setSku(form.getSku());
		basSkuForm.setActiveFlag(form.getActiveFlag());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
	        BasSkuQuery query = new BasSkuQuery();
			BeanUtils.copyProperties(basSkuForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "产品档案";  
	        // excel要导出的数据  
	        List<BasSku> skuList = basSkuMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (skuList == null || skuList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(skuList, fieldMap, sheetName, response);  
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
	
		superClassMap.put("customerid", "客户代码");
		superClassMap.put("sku", "产品");
		superClassMap.put("activeFlag", "激活");
		superClassMap.put("descrC", "中文名称");
		superClassMap.put("descrE", "英文名称");
		superClassMap.put("descrE", "英文名称");
		superClassMap.put("packid", "包装代码");
		superClassMap.put("alternateSku1", "条形码");
		superClassMap.put("reservedfield01", "单位");
		superClassMap.put("grossweight", "重量");
		superClassMap.put("cube", "体积");
		superClassMap.put("price", "单价");
	
		return superClassMap;
	}

}
