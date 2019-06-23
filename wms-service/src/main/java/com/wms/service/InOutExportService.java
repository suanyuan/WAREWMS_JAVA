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



import com.wms.entity.RptAsnDaily;
import com.wms.entity.RptInOut;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptInOutMybatisDao;
import com.wms.query.RptInOutQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.InOutExportForm;
import com.wms.vo.form.RptInOutForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("InOutExportService")
public class InOutExportService {
	
	@Autowired
	private RptInOutMybatisDao rptInOutMybatisDao;
	
	private static final String ORDER_HEAD = "客户编码,商品编码,商品名称,英文描述,期初存,本期入,本期出,本期调整,本期转移,期末存,自定义1,自定义2,自定义3";
	
	public void exportInOutDataToExcel(HttpServletResponse response, InOutExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptInOutForm rptInOutForm = new RptInOutForm();
		
		rptInOutForm.setCustomerid(form.getCustomerid());
		rptInOutForm.setSku(form.getSku());
		rptInOutForm.setSkutext(form.getSkutext());
		rptInOutForm.setStartdate(form.getStartdate());
		rptInOutForm.setStartdatetext(form.getStartdatetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptInOutQuery query = new RptInOutQuery();
			BeanUtils.copyProperties(rptInOutForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "进出存合并报表";   
	        // excel要导出的数据  
	        List<RptInOut> InoutList = rptInOutMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (InoutList == null || InoutList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(InoutList, fieldMap, sheetName, response);  
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
		superClassMap.put("sku", "商品编码");
		superClassMap.put("skuDescrC", "商品名称");
		superClassMap.put("skuDescrE", "英文描述");
		superClassMap.put("ivqty", "期初存");
		superClassMap.put("ibqty", "本期入");
		superClassMap.put("obqty", "本期出");
		superClassMap.put("adqty", "本期调整");
		superClassMap.put("trqty", "本期转移");
		superClassMap.put("fvqty", "期末存");
		superClassMap.put("userdefine1", "自定义1");
		superClassMap.put("userdefine2", "自定义2");
		superClassMap.put("userdefine3", "自定义3");
	
		return superClassMap;
	}

}
