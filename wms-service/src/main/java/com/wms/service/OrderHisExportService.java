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
import com.wms.entity.RptOrderdetailHis;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptOrderdetailHisMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
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
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptOrderdetailHisForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("OrderHisExportService")
public class OrderHisExportService {
	
	@Autowired
	private RptOrderdetailHisMybatisDao rptOrderdetailHisMybatisDao;
	
	private static final String ORDER_HEAD = "SO编号,商品编码,订货数量,发运数量,订单明细金额,订单实际总金额,订单报价金额,商品名称,承运人,订单状态,收货人,省,市,区,地址,客户编号,客户订单号1,客户订单号2,波次号,销售渠道,创建时间,发运时间,取消原因";
	
	public void exportOrderHisDataToExcel(HttpServletResponse response, OrderHisExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptOrderdetailHisForm rptOrderdetailHisForm = new RptOrderdetailHisForm();
		
		rptOrderdetailHisForm.setCustomerid(form.getCustomerid());
		rptOrderdetailHisForm.setOrderno(form.getOrderno());
		rptOrderdetailHisForm.setSku(form.getSku());
		rptOrderdetailHisForm.setSostatus(form.getSostatus());
		rptOrderdetailHisForm.setAddtime(form.getAddtime());
		rptOrderdetailHisForm.setAddtimetext(form.getAddtimetext());
		rptOrderdetailHisForm.setLastshipmenttime(form.getLastshipmenttime());
		rptOrderdetailHisForm.setLastshipmenttimetext(form.getLastshipmenttimetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptOrderdetailHisQuery query = new RptOrderdetailHisQuery();
			BeanUtils.copyProperties(rptOrderdetailHisForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "订单明细查询_历史";   
	        // excel要导出的数据  
	        List<RptOrderdetailHis> orderhisList = rptOrderdetailHisMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (orderhisList == null || orderhisList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(orderhisList, fieldMap, sheetName, response);  
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
	
		superClassMap.put("orderno", "SO编号");
		superClassMap.put("sku", "商品编码");
		superClassMap.put("qtyordered", "订货数量");
		superClassMap.put("qtyshipped", "发运数量");
		superClassMap.put("price", "订单明细金额");
		superClassMap.put("hEdi02", "订单实际总金额");
		superClassMap.put("hEdi04", "订单报价金额");
		superClassMap.put("descrC", "商品名称");
		superClassMap.put("carrierid", "承运人");
		superClassMap.put("sostatus", "订单状态");
		superClassMap.put("consigneeid", "收货人");
		superClassMap.put("cProvince", "省");
		superClassMap.put("cCity", "市");
		superClassMap.put("cAddress2", "区");
		superClassMap.put("cAddress1", "地址");
		superClassMap.put("customerid", "客户编号");
		superClassMap.put("soreference1", "客户订单号1");
		superClassMap.put("soreference2", "客户订单号2");
		superClassMap.put("waveno", "波次号");
		superClassMap.put("issuepartyid", "销售渠道");
		superClassMap.put("addtime", "创建时间");
		superClassMap.put("lastshipmenttime", "发运时间");
		superClassMap.put("userdefine5", "取消原因");
	
		return superClassMap;
	}

}
