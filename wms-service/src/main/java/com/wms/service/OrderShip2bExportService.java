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
import com.wms.entity.RptOrdershipment2b;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDailyMybatisDao;
import com.wms.mybatis.dao.RptOrderPackingboxMybatisDao;
import com.wms.mybatis.dao.RptOrderPackingshipMybatisDao;
import com.wms.mybatis.dao.RptOrderdetailHisMybatisDao;
import com.wms.mybatis.dao.RptOrdershipment2bMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.RptAsnDailyQuery;
import com.wms.query.RptOrderPackingshipQuery;
import com.wms.query.RptOrderdetailHisQuery;
import com.wms.query.RptOrdershipment2bQuery;
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
import com.wms.vo.form.OrderShip2bExportForm;
import com.wms.vo.form.OrderShipExportForm;
import com.wms.vo.form.RptAsnDailyForm;
import com.wms.vo.form.RptOrderPackingshipForm;
import com.wms.vo.form.RptOrderdetailHisForm;
import com.wms.vo.form.RptOrdershipment2bForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("OrderShip2bExportService")
public class OrderShip2bExportService {
	
	@Autowired
	private RptOrdershipment2bMybatisDao rptOrdershipment2bMybatisDao;
	
	private static final String ORDER_HEAD = "客户编码,SO编号,客户订单号1,客户订单号2,系统箱数,订单重量,订单体积,创建日期,发运日期,收件人N,收件人I,电话1,电话2,省,市,区,地址,订单状态,订单类型,客户备注";
	
	public void exportOrderShip2bDataToExcel(HttpServletResponse response, OrderShip2bExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		RptOrdershipment2bForm rptOrdershipment2bForm = new RptOrdershipment2bForm();
		
		rptOrdershipment2bForm.setCustomerid(form.getCustomerid());
		rptOrdershipment2bForm.setOrderno(form.getOrderno());
		rptOrdershipment2bForm.setAddtime(form.getAddtime());
		rptOrdershipment2bForm.setAddtimetext(form.getAddtimetext());
		rptOrdershipment2bForm.setLastshipmenttime(form.getLastshipmenttime());
		rptOrdershipment2bForm.setLastshipmenttimetext(form.getLastshipmenttimetext());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
			RptOrdershipment2bQuery query = new RptOrdershipment2bQuery();
			BeanUtils.copyProperties(rptOrdershipment2bForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "订单2B发运信息";   
	        // excel要导出的数据  
	        List<RptOrdershipment2b> ordership2bList = rptOrdershipment2bMybatisDao.queryByList(mybatisCriteria); 
	        // 导出  
	        if (ordership2bList == null || ordership2bList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(ordership2bList, fieldMap, sheetName, response);  
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
		superClassMap.put("soreference1", "客户订单号1");
		superClassMap.put("soreference2", "客户订单号2");
		superClassMap.put("cnt", "系统箱数");
		superClassMap.put("edi09", "订单重量");
		superClassMap.put("edi10", "订单体积");
		superClassMap.put("addtime", "创建日期");
		superClassMap.put("lastshipmenttime", "发运日期");
		superClassMap.put("consigneeid", "收件人N");
		superClassMap.put("consigneename", "收件人I");
		superClassMap.put("cTel1", "电话1");
		superClassMap.put("cTel2", "电话2");
		superClassMap.put("cProvince", "省");
		superClassMap.put("cCity", "市");
		superClassMap.put("cAddress2", "区");
		superClassMap.put("cAddress1", "地址");
		superClassMap.put("sostatus", "订单状态");
		superClassMap.put("codenameC", "订单类型");
		superClassMap.put("notes", "客户备注");
	
		return superClassMap;
	}

}
