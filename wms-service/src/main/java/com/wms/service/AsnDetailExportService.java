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
import com.wms.entity.RptAsnDetail;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptAsnDetailMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
import com.wms.query.RptAsnDetailQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.AsnDailyExportForm;
import com.wms.vo.form.AsnDetailExportForm;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptAsnDetailForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("AsnDetailExportService")
public class AsnDetailExportService {
	
	@Autowired
	private RptAsnDetailMybatisDao rptAsnDetailMybatisDao;
	
	private static final String ORDER_HEAD = "客户编码,ASN编号,客户入库编号,商品编码,商品名称,商品单位,预期入库数量,入库数量,入库类型,入库状态,入库体积,商品入库日期,最后入库日期,创建日期,关闭日期,自定义2,备注";
	
	public void exportAsnDetailDataToExcel(HttpServletResponse response, AsnDetailExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptAsnDetailForm rptAsnDetailForm = new RptAsnDetailForm();
		
		rptAsnDetailForm.setCustomerid(form.getCustomerid());
		rptAsnDetailForm.setAsnno(form.getAsnno());
		rptAsnDetailForm.setSku(form.getSku());
		rptAsnDetailForm.setAddtime(form.getAddtime());
		rptAsnDetailForm.setAddtimetext(form.getAddtimetext());
		rptAsnDetailForm.setEdittime(form.getEdittime());
		rptAsnDetailForm.setEdittimetext(form.getEdittimetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptAsnDetailQuery query = new RptAsnDetailQuery();
			BeanUtils.copyProperties(rptAsnDetailForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "入库明细";   
	        // excel要导出的数据  
	        List<RptAsnDetail> asndetailList = rptAsnDetailMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (asndetailList == null || asndetailList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(asndetailList, fieldMap, sheetName, response);  
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
		superClassMap.put("asnreference1", "客户入库编号");
		superClassMap.put("sku", "商品编码");
		superClassMap.put("descrC", "商品名称");
		superClassMap.put("reservedfield01", "商品单位");
		superClassMap.put("expectedqty", "预期入库数量");
		superClassMap.put("receivedqty", "入库数量");
		superClassMap.put("codenameC", "入库类型");
		superClassMap.put("codenameC1", "入库状态");
		superClassMap.put("cube", "入库体积");
		superClassMap.put("receivedtime", "商品入库日期");
		superClassMap.put("lastreceivingtime", "最后入库日期");
		superClassMap.put("addtime", "创建日期");
		superClassMap.put("edittime", "关闭日期");
		superClassMap.put("userdefine2", "自定义2");
		superClassMap.put("notes", "备注");
	
		return superClassMap;
	}

}
