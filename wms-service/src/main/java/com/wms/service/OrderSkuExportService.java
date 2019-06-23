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
import com.wms.entity.RptOrdershipmentSku;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptOrdershipmentSkuMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
import com.wms.query.RptOrdershipmentSkuQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.OrderSkuExportForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptOrdershipmentSkuForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("OrderSkuExportService")
public class OrderSkuExportService {
	
	@Autowired
	private RptOrdershipmentSkuMybatisDao rptOrdershipmentSkuMybatisDao;
	
	private static final String ORDER_HEAD = "客户编码,客户订单号1,客户订单号2,SO编号,订单类型C,订单类型N,面单号,箱号,包裹商品数,件数,体积,发货时间,订单状态,销售渠道";
	
	public void exportOrderSkuDataToExcel(HttpServletResponse response, OrderSkuExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptOrdershipmentSkuForm rptOrdershipmentSkuForm = new RptOrdershipmentSkuForm();
		
		rptOrdershipmentSkuForm.setCustomerid(form.getCustomerid());
		rptOrdershipmentSkuForm.setOrderno(form.getOrderno());
		//rptOrdershipmentSkuForm.setSku(form.getSku());
		rptOrdershipmentSkuForm.setLastshipmenttime(form.getLastshipmenttime());
		rptOrdershipmentSkuForm.setLastshipmenttimetext(form.getLastshipmenttimetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptOrdershipmentSkuQuery query = new RptOrdershipmentSkuQuery();
			BeanUtils.copyProperties(rptOrdershipmentSkuForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "发运信息_SKU";   
	        // excel要导出的数据  
	        List<RptOrdershipmentSku> orderskuList = rptOrdershipmentSkuMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (orderskuList == null || orderskuList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(orderskuList, fieldMap, sheetName, response);  
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
		
		superClassMap.put("customerid", "客户编码");
		superClassMap.put("soreference1", "客户订单号1");
		superClassMap.put("soreference2", "客户订单号2");
		superClassMap.put("orderno", "SO编号");
		superClassMap.put("ordertype", "订单类型C");
		superClassMap.put("codenameC", "订单类型N");
		superClassMap.put("deliveryno", "面单号");
		superClassMap.put("traceid", "箱号");
		superClassMap.put("cnt", "包裹商品数");
		superClassMap.put("qtyEach", "件数");
		superClassMap.put("cube", "体积");
		superClassMap.put("lastshipmenttime", "发货时间");
		superClassMap.put("sostatus", "订单状态");
		superClassMap.put("issuepartyid", "销售渠道");
	
		return superClassMap;
	}

}
