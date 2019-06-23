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
import com.wms.entity.RptAsnDaily;
import com.wms.entity.RptOrderPackingbox;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptOrderPackingboxMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
import com.wms.query.RptOrderPackingboxQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.OrderPackExportForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptOrderPackingboxForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("OrderPackExportService")
public class OrderPackExportService {
	
	@Autowired
	private RptOrderPackingboxMybatisDao rptOrderPackingboxMybatisDao;
	
	private static final String ORDER_HEAD = "箱号,SO编号,客户编码,承运人,参考编号1,参考编号2,商品编码,商品名称,装箱数,装箱时间,收货方,电话,地址,商品条码";
	
	public void exportOrderPackDataToExcel(HttpServletResponse response, OrderPackExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptOrderPackingboxForm rptOrderPackingboxForm = new RptOrderPackingboxForm();
		
		rptOrderPackingboxForm.setCustomerid(form.getCustomerid());
		rptOrderPackingboxForm.setOrderno(form.getOrderno());
		rptOrderPackingboxForm.setSku(form.getSku());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptOrderPackingboxQuery query = new RptOrderPackingboxQuery();
			BeanUtils.copyProperties(rptOrderPackingboxForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "订单装箱清单_按箱";   
	        // excel要导出的数据  
	        List<RptOrderPackingbox> orderpackList = rptOrderPackingboxMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (orderpackList == null || orderpackList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(orderpackList, fieldMap, sheetName, response);  
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
		
		superClassMap.put("traceid", "箱号");
		superClassMap.put("orderno", "SO编号");
		superClassMap.put("customerid", "客户编码");
		superClassMap.put("carrierid", "承运人");
		superClassMap.put("soreference1", "参考编号1");
		superClassMap.put("soreference2", "参考编号2");
		superClassMap.put("sku", "商品编码");
		superClassMap.put("descrC", "商品名称");
		superClassMap.put("qty", "装箱数");
		superClassMap.put("addtime", "装箱时间");
		superClassMap.put("consigneeid", "收货方");
		superClassMap.put("tel", "电话");
		superClassMap.put("address", "地址");
		superClassMap.put("alternateSku1", "商品条码");
	
		return superClassMap;
	}

}
