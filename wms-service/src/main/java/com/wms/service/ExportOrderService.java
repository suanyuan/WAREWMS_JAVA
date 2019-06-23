package com.wms.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.ExportOrderData;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocOrderHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocOrderHeaderQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.DocOrderHeaderExportForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("exportOrderService")
public class ExportOrderService {
	
	@Autowired
	private DocOrderHeaderMybatisDao docOrderHeaderMybatisDao;

	public void exportOrderDataToExcel(HttpServletResponse response, DocOrderHeaderExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		DocOrderHeaderQuery docOrderHeaderQuery = new DocOrderHeaderQuery();
		
		docOrderHeaderQuery.setCustomerid(form.getCustomerid());
		docOrderHeaderQuery.setOrderno(form.getOrderno());
		docOrderHeaderQuery.setSoreference1(form.getSoreference1());
		docOrderHeaderQuery.setSostatus(form.getSostatus());
		docOrderHeaderQuery.setSostatusTo(form.getSostatusTo());
		docOrderHeaderQuery.setOrdertime(form.getOrdertime());
		docOrderHeaderQuery.setOrdertimeTo(form.getOrdertimeTo());
		docOrderHeaderQuery.setReleasestatus(form.getReleasestatus());
		docOrderHeaderQuery.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		
		try {  
	        // 获取前台传来的数据
	        MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(docOrderHeaderQuery));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "订单信息";  
	        // excel要导出的数据  
	        List<ExportOrderData> exportOrderDataList = docOrderHeaderMybatisDao.queryByExportList(mybatisCriteria); 
	        // 导出  
	        if (exportOrderDataList == null || exportOrderDataList.size() == 0) {  
	        	//System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(exportOrderDataList, fieldMap, sheetName, response);  
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
	
		superClassMap.put("customerid", "客户代码");
		superClassMap.put("orderno", "SO单号");
		superClassMap.put("soreference1", "客户单号1");
		superClassMap.put("soreference2", "客户单号2");
		superClassMap.put("consigneeid", "收货人");
		superClassMap.put("cAddress1", "收货地址");
		superClassMap.put("cTel1", "收货人电话");
		superClassMap.put("orderlineno", "明细行号");
		superClassMap.put("sku", "产品");
		superClassMap.put("qtyordered", "订单数量");
		superClassMap.put("qtyshipped", "发货数量");
		superClassMap.put("price", "明细金额");
	
		return superClassMap;
	}

}
