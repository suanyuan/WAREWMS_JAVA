package com.wms.service;

import com.wms.entity.CouRequestDetails;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.CouRequestDetailsMybatisDao;
import com.wms.query.CouRequestDetailsQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.CouRequestDetailsExportVO;
import com.wms.vo.form.CouRequestExportForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("couRequestExportService")
public class CouRequestExportService {
	
	@Autowired
	private CouRequestDetailsMybatisDao couRequestDetailsMybatisDao;
	
	public void exportCouRequestDataToExcel(HttpServletResponse response,  CouRequestExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());

		CouRequestDetailsQuery query = new CouRequestDetailsQuery();
		query.setCycleCountno(form.getCycleCountno());
		try {
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
	        // excel的sheetName  
	        String fileName =form.getCycleCountno();
	        String sheetName ="couRequest_template";
	        // excel要导出的数据
	        List<CouRequestDetails> vList = couRequestDetailsMybatisDao.queryListById(query); //要权限！
			List<CouRequestDetailsExportVO> exportVOS =new ArrayList<>();
			CouRequestDetailsExportVO vo = null;
			for (CouRequestDetails couRequestDetails : vList) {
				vo = new CouRequestDetailsExportVO();
				BeanUtils.copyProperties(couRequestDetails, vo);
				//editime转换成string  setCountdate
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				if (vo.getEdittime()!=null) {
					vo.setCountdate(sdf.format(vo.getEdittime()));
				}else{
				}
				exportVOS.add(vo);
			}
	        // 导出  
	        if (vList == null || vList.size() == 0) {  
	        	System.out.println("题库为空");  
	        }else {  
	            //将list集合转化为excle  
	            ExcelUtil.listToExcel(exportVOS, fieldMap, sheetName,65535, response,fileName);
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
	
    	superClassMap.put("cycleCountno","盘点单号");
    	superClassMap.put("cycleCountlineno","行号");
		superClassMap.put("customerid", "货主");
		superClassMap.put("sku", "产品代码");
		superClassMap.put("reservedfield01", "产品名称");
		superClassMap.put("descrc", "规格/型号");
		superClassMap.put("lotatt04", "生产批号");
		superClassMap.put("lotatt05", "序列号");
		superClassMap.put("qtyInv", "库存件数");
		superClassMap.put("locationid", "库位");
		superClassMap.put("qtyAct", "实际盘点件数");
		superClassMap.put("userdefined1", "盘点差异");
		superClassMap.put("productLineName", "产品线");
		superClassMap.put("userdefined2", "备注");
		superClassMap.put("countdate", "盘点日期");//editime转换成string
		superClassMap.put("editwho", "盘点人");
		superClassMap.put("userdefined3", "复核人");

		return superClassMap;
	}


}
