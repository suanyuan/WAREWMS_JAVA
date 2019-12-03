package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.ViewInvLotatt;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvLotattMybatisDao;
import com.wms.query.ViewInvLotattQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.form.ViewInvLotattExportForm;
import com.wms.vo.form.ViewInvLotattForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

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
	@Autowired
	private BasCodesService basCodesService;
	
	public void exportViewInvLotattDataToExcel(HttpServletResponse response, ViewInvLotattExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		ViewInvLotattForm viewInvLotattForm = new ViewInvLotattForm();
		
		viewInvLotattForm.setFmcustomerid(form.getFmcustomerid());
		viewInvLotattForm.setFmsku(form.getFmsku());
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
		viewInvLotattForm.setLotatt10(form.getLotatt10());
		viewInvLotattForm.setLotatt12(form.getLotatt12());
		viewInvLotattForm.setOnholdlocker(form.getOnholdlocker());
		try {
			ViewInvLotattQuery query = new ViewInvLotattQuery();
			//权限控制
			//query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
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
			//质量状态
			List<EasyuiCombobox> Lotatt10List = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
			for (ViewInvLotatt viewInvLotatt : vList) {

				if(viewInvLotatt.getFmqty()!=null) {
					viewInvLotatt.setFmqty(viewInvLotatt.getFmqty().setScale(1));
				}
				if(viewInvLotatt.getFmqtyEach()!=null) {
					viewInvLotatt.setFmqtyEach(viewInvLotatt.getFmqtyEach().setScale(1));
				}
				if(viewInvLotatt.getQtyallocated()!=null) {
					viewInvLotatt.setQtyallocated(viewInvLotatt.getQtyallocated().setScale(1));
				}
				if(viewInvLotatt.getQtyholded()!=null) {
					viewInvLotatt.setQtyholded(viewInvLotatt.getQtyholded().setScale(1));
				}
				if(viewInvLotatt.getQtyavailed()!=null) {
					viewInvLotatt.setQtyavailed(viewInvLotatt.getQtyavailed().setScale(1));
				}
				for (EasyuiCombobox easyuiCombobox : Lotatt10List) {

					if(viewInvLotatt.getLotatt10()!=null) {
					   //质量状态id对比
					    if (viewInvLotatt.getLotatt10().equals(easyuiCombobox.getId())) {
						    viewInvLotatt.setLotatt10(easyuiCombobox.getValue());
						    break;
					}
					}
				}
				//冻结状态
				if(viewInvLotatt.getOnholdlocker()==99){
					viewInvLotatt.setOnholdlockerEx("库存冻结");
				}else{
					viewInvLotatt.setOnholdlockerEx("库存解冻");
				}
				//样品属性
				if(viewInvLotatt.getLotatt09()!=null){
					String lotatt09=viewInvLotatt.getLotatt09();
					if(lotatt09.equals("ZC")){
						viewInvLotatt.setLotatt09("正常");
					}else if(lotatt09.equals("TS")){
						viewInvLotatt.setLotatt09("投诉");
					}else if(lotatt09.equals("YP")){
						viewInvLotatt.setLotatt09("样品");

					}
				}
			}
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
		superClassMap.put("fmsku", "产品代码");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("uom", "单位");
		superClassMap.put("fmqty", "库存件数");
		superClassMap.put("qtyallocated", "分配件数");
		superClassMap.put("qtyavailed", "可用件数");
		superClassMap.put("qtyholded", "冻结件数");
		superClassMap.put("iPa", "待上架数量");
		superClassMap.put("toadjqty", "待调整数量");
		superClassMap.put("iMv", "待移入数量");
		superClassMap.put("oMv", "待移出数量");
		superClassMap.put("qtyrpin", "补货待上架");
		superClassMap.put("qtyrpout", "补货待下架");
		superClassMap.put("lotatt01", "生产日期");
		superClassMap.put("lotatt02", "有效期/失效期");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("lotatt04", "生产批号");
		superClassMap.put("lotatt05", "序列号");
		superClassMap.put("lotatt06", "注册证号/备案凭证号");
		superClassMap.put("lotatt09", "样品属性");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("onholdlockerEx", "冻结状态");
		superClassMap.put("lpn", "LPN");
		superClassMap.put("netweight", "净重");
		superClassMap.put("totalgrossweight", "毛重");
		superClassMap.put("totalcubic", "体积");
		superClassMap.put("price", "价值");
		superClassMap.put("warehouseid", "仓库ID");
	
		return superClassMap;
	}

}
