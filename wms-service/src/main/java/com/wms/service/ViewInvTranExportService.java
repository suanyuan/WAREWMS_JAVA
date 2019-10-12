package com.wms.service;

import com.wms.entity.ViewInvTran;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvTranMybatisDao;
import com.wms.query.ViewInvTranQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.form.ViewInvTranExportForm;
import com.wms.vo.form.ViewInvTranForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Service("viewInvTranExportService")
public class ViewInvTranExportService {
	@Autowired
	private ViewInvTranMybatisDao viewInvTranMybatisDao;
	
	public void exportViewInvTranDataToExcel(HttpServletResponse response, ViewInvTranExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		ViewInvTranForm viewInvTranForm = new ViewInvTranForm();
		
		viewInvTranForm.setTransactiontype(form.getTransactiontype());
		viewInvTranForm.setDoctype(form.getDoctype());
		viewInvTranForm.setStatus(form.getStatus());
		viewInvTranForm.setTransactiontime(form.getTransactiontime());
		viewInvTranForm.setAddtime(form.getAddtime());
		viewInvTranForm.setDocno(form.getDocno());
		viewInvTranForm.setOperator(form.getOperator());
		viewInvTranForm.setEditwho(form.getEditwho());
		viewInvTranForm.setFmcustomerid(form.getFmcustomerid());
		viewInvTranForm.setFmlocation(form.getFmlocation());
		viewInvTranForm.setFmsku(form.getFmsku());
		viewInvTranForm.setTocustomerid(form.getFmcustomerid());
		viewInvTranForm.setTolocation(form.getTolocation());
		viewInvTranForm.setTosku(form.getTosku());
		try {  
			ViewInvTranQuery query = new ViewInvTranQuery();
			//权限控制
			query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			BeanUtils.copyProperties(viewInvTranForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "库存查询结果";  
	        // excel要导出的数据  
	        List<ViewInvTran> vList = viewInvTranMybatisDao.queryByList(mybatisCriteria); //要权限！james
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
	
		superClassMap.put("transactionid", "事务编号");
		superClassMap.put("transactiontypeName", "事务类型");
		superClassMap.put("docno", "单据编号");
		superClassMap.put("doclineno", "单据行号");
		superClassMap.put("doctypeName", "单据类型");
		superClassMap.put("statusName", "事务状态");
		superClassMap.put("transactiontime", "事务时间");
		superClassMap.put("fmcustomerid", "FM客户编码");
		superClassMap.put("fmsku", "FM商品编码");
		superClassMap.put("fmqtyEach", "FM单位数量");
		superClassMap.put("fmqty", "FM库存数量");
		superClassMap.put("fmuomName", "FM单位");
		superClassMap.put("fmlocation", "FM库位");
		superClassMap.put("fmlotnum", "FM批号");
		superClassMap.put("fmlotatt01", "FM生产日期");
		superClassMap.put("fmlotatt02", "FM失效日期");
		superClassMap.put("fmlotatt03", "FM入库日期");
		superClassMap.put("fmlotatt04", "FM库存状态");
		superClassMap.put("fmlotatt05", "FM批次属性5");
		superClassMap.put("fmlotatt06", "FM批次属性6");
		superClassMap.put("fmpackid", "FM箱号");
		superClassMap.put("tocustomerid", "TO客户编码");
		superClassMap.put("tosku", "TO商品编码");
		superClassMap.put("toqtyEach", "TO单位数量");
		superClassMap.put("toqty", "TO库存数量");
		superClassMap.put("touomName", "TO单位");
		superClassMap.put("totalcubic", "TO体积");
		superClassMap.put("totalgrossweight", "TO重量");
		superClassMap.put("tolocation", "TO库位");
		superClassMap.put("tolotnum", "TO批号");
		superClassMap.put("tolotatt01", "TO生产日期");
		superClassMap.put("tolotatt02", "TO失效日期");
		superClassMap.put("tolotatt03", "TO入库日期");
		superClassMap.put("tolotatt04", "TO库存状态");
		superClassMap.put("tolotatt05", "TO批次属性5");
		superClassMap.put("tolotatt06", "TO批次属性6");
		superClassMap.put("topackid", "TO箱号");
		superClassMap.put("reasoncode", "原因代码");
		superClassMap.put("reason", "原因");
		superClassMap.put("addtime", "系统时间");
		superClassMap.put("editwho", "系统人员");
		superClassMap.put("operator", "操作人员");
			
		return superClassMap;
	}
}
