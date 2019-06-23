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
import com.wms.entity.RptSoDaily;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptSoDailyMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
import com.wms.query.RptSoDailyQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptSoDailyForm;
import com.wms.vo.form.SoDailyExportForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("SoDailyExportService")
public class SoDailyExportService {
	
	@Autowired
	private RptSoDailyMybatisDao rptSoDailyMybatisDao;
	
	private static final String ORDER_HEAD = "客户编码,SO编号,SO行号,订单类型,收货人,参考编号1,参考编号2,参考编号3,地址,商品编码,商品名称,英文描述,订货数,发货数,承运人,接单时间,发货时间,自定义1,自定义2,自定义3";
	
	public void exportSoDataToExcel(HttpServletResponse response, SoDailyExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptSoDailyForm rptSoDailyForm = new RptSoDailyForm();
		
		rptSoDailyForm.setCustomerid(form.getCustomerid());
		rptSoDailyForm.setSku(form.getSku());
		rptSoDailyForm.setSkutext(form.getSkutext());
		rptSoDailyForm.setShipmenttime(form.getShipmenttime());
		rptSoDailyForm.setShipmenttimetext(form.getShipmenttimetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptSoDailyQuery query = new RptSoDailyQuery();
			BeanUtils.copyProperties(rptSoDailyForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "出库日报";  
	        // excel要导出的数据  
	        List<RptSoDaily> soList = rptSoDailyMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (soList == null || soList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(soList, fieldMap, sheetName, response);  
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
		superClassMap.put("orderno", "SO编号");
		superClassMap.put("doclineno", "SO行号");
		superClassMap.put("ordertype", "订单类型");
		superClassMap.put("consigneename", "收货人");
		superClassMap.put("asnreference1inreport", "参考编号1");
		superClassMap.put("asnreference2inreport", "参考编号2");
		superClassMap.put("asnreference3inreport", "参考编号3");
		superClassMap.put("address", "地址");
		superClassMap.put("sku", "商品编码");
		superClassMap.put("skuDescrC", "商品名称");
		superClassMap.put("skuDescrE", "英文描述");
		superClassMap.put("qtyordered", "订货数");
		superClassMap.put("qtyshipped", "发货数");
		superClassMap.put("carriername", "承运人");
		superClassMap.put("ordertime", "接单时间");
		superClassMap.put("shipmenttime", "发货时间");
		superClassMap.put("udf1", "自定义1");
		superClassMap.put("udf2", "自定义2");
		superClassMap.put("udf3", "自定义3");
	
		return superClassMap;
	}

}
