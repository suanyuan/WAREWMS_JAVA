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
import com.wms.entity.RptPackPerformance;
import com.wms.entity.RptSortPerformance;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptPackPerformanceMybatisDao;
import com.wms.mybatis.dao.RptSortPerformanceMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
import com.wms.query.RptPackPerformanceQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.PackPerformExportForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptPackPerformanceForm;
import com.wms.vo.form.RptSortPerformanceForm;
import com.wms.vo.form.SortPerformExportForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("SortPerformExportService")
public class SortPerformExportService {
	
	@Autowired
	private RptSortPerformanceMybatisDao rptSortPerformanceMybatisDao;
	
	private static final String ORDER_HEAD = "日期,工号,姓名,客户编码,计件,计时";
	
	public void exportSortPerformDataToExcel(HttpServletResponse response, SortPerformExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptSortPerformanceForm rptsortPerformanceForm = new RptSortPerformanceForm();
		
		rptsortPerformanceForm.setCustomerid(form.getCustomerid());
		rptsortPerformanceForm.setUserId(form.getUserId());
		rptsortPerformanceForm.setSdate(form.getSdate());
		rptsortPerformanceForm.setSdatetext(form.getSdatetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptPackPerformanceQuery query = new RptPackPerformanceQuery();
			BeanUtils.copyProperties(rptsortPerformanceForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "订单分拣绩效统计";   
	        // excel要导出的数据  
	        List<RptSortPerformance> sortPerform = rptSortPerformanceMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (sortPerform == null || sortPerform.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(sortPerform, fieldMap, sheetName, response);  
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
		
		superClassMap.put("sdate", "日期");
		superClassMap.put("userId", "工号");
		superClassMap.put("userName", "姓名");
		superClassMap.put("customerid", "客户编码");
		superClassMap.put("qtyordered", "计件");
		superClassMap.put("ttim", "计时");
	
		return superClassMap;
	}

}
