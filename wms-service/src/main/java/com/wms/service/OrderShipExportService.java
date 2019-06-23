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
import com.wms.entity.RptOrderPackingship;
import com.wms.entity.RptOrderdetailHis;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptOrderPackingboxMybatisDao;
import com.wms.mybatis.dao.RptOrderPackingshipMybatisDao;
import com.wms.mybatis.dao.RptOrderdetailHisMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
import com.wms.query.RptOrderPackingshipQuery;
import com.wms.query.RptOrderdetailHisQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.OrderHisExportForm;
import com.wms.vo.form.OrderShipExportForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptOrderPackingshipForm;
import com.wms.vo.form.RptOrderdetailHisForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("OrderShipExportService")
public class OrderShipExportService {
	
	@Autowired
	private RptOrderPackingshipMybatisDao rptOrderPackingshipMybatisDao;
	
	private static final String ORDER_HEAD = "客户编码,订单号,箱号,重量,体积,商品编码,商品名称,装箱数,订单创建时间,订单发运时间,配载单号,SO编号";
	
	public void exportOrderShipDataToExcel(HttpServletResponse response, OrderShipExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptOrderPackingshipForm rptOrderPackingshipForm = new RptOrderPackingshipForm();
		
		rptOrderPackingshipForm.setCustomerid(form.getCustomerid());
		rptOrderPackingshipForm.setOrderno(form.getOrderno());
		rptOrderPackingshipForm.setSku(form.getSku());
		rptOrderPackingshipForm.setAddtime(form.getAddtime());
		rptOrderPackingshipForm.setAddtimetext(form.getAddtimetext());
		rptOrderPackingshipForm.setLastshipmenttime(form.getLastshipmenttime());
		rptOrderPackingshipForm.setLastshipmenttimetext(form.getLastshipmenttimetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptOrderPackingshipQuery query = new RptOrderPackingshipQuery();
			BeanUtils.copyProperties(rptOrderPackingshipForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "订单装箱清单_发运";   
	        // excel要导出的数据  
	        List<RptOrderPackingship> ordershipList = rptOrderPackingshipMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (ordershipList == null || ordershipList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(ordershipList, fieldMap, sheetName, response);  
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
		superClassMap.put("soreference1", "订单号");
		superClassMap.put("traceid", "箱号");
		superClassMap.put("grossweight", "重量");
		superClassMap.put("cube", "体积");
		superClassMap.put("sku", "商品编码");
		superClassMap.put("descrC", "商品名称");
		superClassMap.put("qty", "装箱数");
		superClassMap.put("addtime", "订单创建时间");
		superClassMap.put("lastshipmenttime", "订单发运时间");
		superClassMap.put("soreference3", "配载单号");
		superClassMap.put("orderno", "SO编号");
	
		return superClassMap;
	}

}
