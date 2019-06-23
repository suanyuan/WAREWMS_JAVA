package com.wms.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.ViewInvLocation;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvLocationMybatisDao;
import com.wms.query.ViewInvLocationQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.ViewInvLocationExportForm;
import com.wms.vo.form.ViewInvLocationForm;


/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("viewInvLocationExportService")
public class ViewInvLocationExportService {
	
	@Autowired
	private ViewInvLocationMybatisDao viewInvLocationMybatisDao;
	
	public void exportViewInvLocationDataToExcel(HttpServletResponse response, ViewInvLocationExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		ViewInvLocationForm viewInvLocationForm = new ViewInvLocationForm();
		
		viewInvLocationForm.setFmcustomerid(form.getFmcustomerid());
		viewInvLocationForm.setFmsku(form.getFmsku());
		viewInvLocationForm.setSkudescrc(form.getSkudescrc());
		viewInvLocationForm.setFmlocation(form.getFmlocation());

		try {  
			ViewInvLocationQuery query = new ViewInvLocationQuery();
			//权限控制
			query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			BeanUtils.copyProperties(viewInvLocationForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "库存查询结果";  
	        // excel要导出的数据  
	        List<ViewInvLocation> vList = viewInvLocationMybatisDao.queryByList(mybatisCriteria); //要权限！james
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
	
		superClassMap.put("fmcustomerid", "货主");
		superClassMap.put("fmsku", "商品编码");
		superClassMap.put("skudescrc", "商品名称");
		superClassMap.put("fmlocation", "库位");
		superClassMap.put("fmqty", "库存数量");
		superClassMap.put("qtyallocated", "分配数量");
		superClassMap.put("qtyavailed", "可用数量");
		superClassMap.put("qtyholded", "冻结数量");
		superClassMap.put("totalgrossweight", "毛重");
		superClassMap.put("totalcubic", "体积");
		superClassMap.put("iPa", "待上架数量");
		superClassMap.put("iMv", "待移入数量");
		superClassMap.put("oMv", "待移出数量");
		superClassMap.put("iRp", "补货待上架");
		superClassMap.put("oRp", "补货待下架");
		superClassMap.put("fmuomName", "单位");
		superClassMap.put("warehouseid", "仓库ID");
	
		return superClassMap;
	}

}
