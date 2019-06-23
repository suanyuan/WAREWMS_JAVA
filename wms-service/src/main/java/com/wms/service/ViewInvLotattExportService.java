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
import com.wms.entity.ViewInvLotatt;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvLotattMybatisDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.ViewInvLotattQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;
import com.wms.vo.form.ViewInvLotattExportForm;
import com.wms.vo.form.ViewInvLotattForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("viewInvLotattExportService")
public class ViewInvLotattExportService {
	
	@Autowired
	private ViewInvLotattMybatisDao viewInvLotattMybatisDao;
	
	public void exportViewInvLotattDataToExcel(HttpServletResponse response, ViewInvLotattExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		ViewInvLotattForm viewInvLotattForm = new ViewInvLotattForm();
		
		viewInvLotattForm.setFmcustomerid(form.getFmcustomerid());
		viewInvLotattForm.setFmsku(form.getFmsku());
		viewInvLotattForm.setSkudescrc(form.getSkudescrc());
		viewInvLotattForm.setFmlocation(form.getFmlocation());
		viewInvLotattForm.setLotatt01(form.getLotatt01());
		viewInvLotattForm.setLotatt01text(form.getLotatt01text());
		viewInvLotattForm.setLotatt02(form.getLotatt02());
		viewInvLotattForm.setLotatt02text(form.getLotatt02text());
		viewInvLotattForm.setLotatt03(form.getLotatt03());
		viewInvLotattForm.setLotatt03text(form.getLotatt03text());
		viewInvLotattForm.setLotatt04(form.getLotatt04());
		viewInvLotattForm.setLotatt05(form.getLotatt05());
		viewInvLotattForm.setLotatt06(form.getLotatt06());
		try {  
			ViewInvLotattQuery query = new ViewInvLotattQuery();
			//权限控制
			query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			BeanUtils.copyProperties(viewInvLotattForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "库存查询结果";  
	        // excel要导出的数据  
	        List<ViewInvLotatt> vList = viewInvLotattMybatisDao.queryByList(mybatisCriteria); //要权限！james
	        // 导出  
	        if (vList == null || vList.size() == 0) {  
	        	System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(vList, fieldMap, sheetName, response);  
	        	System.out.println("导出成功~~~~");  
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
	
//		superClassMap.put("pkey", "No.");
		superClassMap.put("fmcustomerid", "货主");
		superClassMap.put("fmid", "跟踪号");
		superClassMap.put("fmlotnum", "批号");
		superClassMap.put("fmlocation", "库位");
		superClassMap.put("fmsku", "商品编码");
		superClassMap.put("skudescrc", "商品名称");
		superClassMap.put("uom", "单位");
		superClassMap.put("fmqty", "库存数量");
		superClassMap.put("qtyallocated", "分配数量");
		superClassMap.put("qtyavailed", "可用数量");
		superClassMap.put("qtyholded", "冻结数量");
		superClassMap.put("iPa", "待上架数量");
		superClassMap.put("toadjqty", "待调整数量");
		superClassMap.put("iMv", "待移入数量");
		superClassMap.put("oMv", "待移出数量");
		superClassMap.put("qtyrpin", "补货待上架");
		superClassMap.put("qtyrpout", "补货待下架");
		superClassMap.put("lotatt01", "批属1");
		superClassMap.put("lotatt02", "批属2");
		superClassMap.put("lotatt03", "批属3");
		superClassMap.put("lotatt04", "批属4");
		superClassMap.put("lotatt05", "批属5");
		superClassMap.put("lotatt06", "批属6");
		superClassMap.put("lpn", "LPN");
		superClassMap.put("netweight", "净重");
		superClassMap.put("totalgrossweight", "毛重");
		superClassMap.put("totalcubic", "体积");
		superClassMap.put("price", "价值");
		superClassMap.put("warehouseid", "仓库ID");
	
		return superClassMap;
	}

}
