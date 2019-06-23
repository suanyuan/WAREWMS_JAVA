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
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
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

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("AsnDailyExportService")
public class AsnDailyExportService {
	
	@Autowired
	private RptAsnDailyMybatisDao rptAsnDailyMybatisDao;
	
	private static final String ORDER_HEAD = "客户编码,ASN编号,ASN类型,ASN行号,入库时间,创建时间,商品编码,商品名称,英文描述,包装,数量,体积,重量,生产日期,失效日期,入库日期";
	
	public void exportAsnDataToExcel(HttpServletResponse response, AsnDailyExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptAsnDailyForm rptAsnDailyForm = new RptAsnDailyForm();
		
		rptAsnDailyForm.setCustomerid(form.getCustomerid());
		rptAsnDailyForm.setSku(form.getSku());
		rptAsnDailyForm.setSkutext(form.getSkutext());
		rptAsnDailyForm.setInbounddate(form.getInbounddate());
		rptAsnDailyForm.setInbounddatetext(form.getInbounddatetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptAsnDailyQuery query = new RptAsnDailyQuery();
			BeanUtils.copyProperties(rptAsnDailyForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "入库日报";   
	        // excel要导出的数据  
	        List<RptAsnDaily> asnList = rptAsnDailyMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (asnList == null || asnList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(asnList, fieldMap, sheetName, response);  
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
		superClassMap.put("asnno", "ASN编号");
		superClassMap.put("asntype", "ASN类型");
		superClassMap.put("asnlineno", "ASN行号");
		superClassMap.put("inbounddate", "入库时间");
		superClassMap.put("asncreatetime", "创建时间");
		superClassMap.put("sku", "商品编码");
		superClassMap.put("skuDescrC", "商品名称");
		superClassMap.put("skuDescrE", "英文描述");
		superClassMap.put("packid", "包装");
		superClassMap.put("qty", "数量");
		superClassMap.put("cubic", "体积");
		superClassMap.put("grossweight", "重量");
		superClassMap.put("lotatt01", "生产日期");
		superClassMap.put("lotatt02", "失效日期");
		superClassMap.put("lotatt03", "入库日期");
	
		return superClassMap;
	}

}
