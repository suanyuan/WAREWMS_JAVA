package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.StatisticalAnalysisMybatisDao;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Service("statisticalAnalysisService")
public class StatisticalAnalysisService extends BaseService {

	@Autowired
	private StatisticalAnalysisMybatisDao statisticalAnalysisMybatisDao;
	@Autowired
	private BasCodesService basCodesService;


	/**************************************出入库流水****************************************/
	public EasyuiDatagrid<RptSoAsnDailyLocation> getPagedDatagridRptSoAsnDailyLocation(EasyuiDatagridPager pager, RptSoAsnDailyLocation query) {
		EasyuiDatagrid<RptSoAsnDailyLocation> datagrid = new EasyuiDatagrid<RptSoAsnDailyLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptSoAsnDailyLocation> rptSoAsnDailyLocationList = statisticalAnalysisMybatisDao.querySoAsnInvLocation(mybatisCriteria);
		for (RptSoAsnDailyLocation s: rptSoAsnDailyLocationList) {
			//上海仓
			s.setWarehouse("上海仓");
			//计算数量
			if(s.getQty1()!=null&&s.getSoqty()!=null){
				s.setSoqtyeach(s.getQty1()*s.getSoqty());
			}
			if(s.getQty1()!=null&&s.getAsnqty()!=null){
				s.setAsnqtyeach(s.getQty1()*s.getAsnqty());
			}
		}
		datagrid.setTotal((long) statisticalAnalysisMybatisDao.querySoAsnInvLocationCount(mybatisCriteria));
		datagrid.setRows(rptSoAsnDailyLocationList);
		return datagrid;
	}
    public void exportSoAsnDailyDataToExcel(HttpServletResponse response, RptSoAsnDailyLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankSoAsnDaily();
			// excel的sheetName
			String sheetName = "出入库流水";
			// excel要导出的数据
			List<RptSoAsnDailyLocation> rptSoAsnDailyLocationList = statisticalAnalysisMybatisDao.querySoAsnInvLocation(mybatisCriteria);
			// 导出
			if (rptSoAsnDailyLocationList == null || rptSoAsnDailyLocationList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (RptSoAsnDailyLocation s: rptSoAsnDailyLocationList) {
                //上海仓
					s.setWarehouse("上海仓");
				//计算数量
				    if(s.getQty1()!=null&&s.getSoqty()!=null){
				    	s.setSoqtyeach(s.getQty1()*s.getSoqty());
					}
				    if(s.getQty1()!=null&&s.getAsnqty()!=null){
				    	s.setAsnqtyeach(s.getQty1()*s.getAsnqty());
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptSoAsnDailyLocationList, fieldMap, sheetName, response);
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
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankSoAsnDaily() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("documentNo", "单据号");
		superClassMap.put("soasndate", "日期");
		superClassMap.put("documentType", "单据类型");
		superClassMap.put("warehouse", "仓库");
		superClassMap.put("customerid", "供货单位");
		superClassMap.put("sku", "存货编码");
		superClassMap.put("lotatt12", "存货名称");
		superClassMap.put("descrc", "规格型号");
		superClassMap.put("asnqtyeach", "入库数量");
		superClassMap.put("asnqty", "入库件数");
		superClassMap.put("soqtyeach", "出库数量");
		superClassMap.put("soqty", "出库件数");
		superClassMap.put("uom", "主计量单位");
		superClassMap.put("lotatt04", "批号");
		superClassMap.put("purchaseOrderNumber", "采购订单号");
		superClassMap.put("notes", "备注");
		superClassMap.put("planPrice", "计划价");
		return superClassMap;
	}







	/**************************************入库单列表****************************************/
	public EasyuiDatagrid<RptAsnList> getPagedDatagridRptAsnList(EasyuiDatagridPager pager, RptAsnList query) {
		EasyuiDatagrid<RptAsnList> datagrid = new EasyuiDatagrid<RptAsnList>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptAsnList> rptAsnList= statisticalAnalysisMybatisDao.queryAsnList(mybatisCriteria);
		for (RptAsnList s: rptAsnList) {
			//上海仓
			s.setWarehouse("上海仓");
			//计算数量
			if(s.getQty1()!=null&&s.getAsnqty()!=null){
				s.setAsnqtyeach(s.getQty1()*s.getAsnqty());
			}
		}
		datagrid.setTotal((long) statisticalAnalysisMybatisDao.queryAsnListCount(mybatisCriteria));
		datagrid.setRows(rptAsnList);
		return datagrid;
	}
	public void exportAsnListDataToExcel(HttpServletResponse response, RptAsnList form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankAsnList();
			// excel的sheetName
			String sheetName = "入库单列表";
			// excel要导出的数据
			List<RptAsnList> rptAsnList = statisticalAnalysisMybatisDao.queryAsnList(mybatisCriteria);
			// 导出
			if (rptAsnList== null || rptAsnList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (RptAsnList s: rptAsnList) {
					//上海仓
					s.setWarehouse("上海仓");
					//计算数量
					if(s.getQty1()!=null&&s.getAsnqty()!=null){
						s.setAsnqtyeach(s.getQty1()*s.getAsnqty());
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptAsnList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankAsnList() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("documentNo", "单据号");
		superClassMap.put("asndate", "日期");
		superClassMap.put("documentType", "单据类型");
		superClassMap.put("warehouse", "仓库");
		superClassMap.put("customerid", "供货单位");
		superClassMap.put("sku", "存货编码");
		superClassMap.put("lotatt12", "存货名称");
		superClassMap.put("descrc", "规格型号");
		superClassMap.put("asnqtyeach", "入库数量");
		superClassMap.put("asnqty", "入库件数");
		superClassMap.put("soqtyeach", "出库数量");
		superClassMap.put("soqty", "出库件数");
		superClassMap.put("uom", "主计量单位");
		superClassMap.put("lotatt04", "批号");
		superClassMap.put("purchaseOrderNumber", "采购订单号");
		superClassMap.put("notes", "备注");
		superClassMap.put("planPrice", "计划价");
		return superClassMap;
	}





	/**************************************收发存汇总表****************************************/
	public EasyuiDatagrid<RptSendReceiveAndSave> getPagedDatagridRptSendReceiveAndSaveAllList(EasyuiDatagridPager pager, RptSendReceiveAndSave query) {
		EasyuiDatagrid<RptSendReceiveAndSave> datagrid = new EasyuiDatagrid<RptSendReceiveAndSave>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptSendReceiveAndSave> rptSendReceiveAndSaveList= statisticalAnalysisMybatisDao.querySendReceiveAndSaveAllList(mybatisCriteria);
		for (RptSendReceiveAndSave s: rptSendReceiveAndSaveList) {

		}
		datagrid.setTotal((long) statisticalAnalysisMybatisDao.querySendReceiveAndSaveAllListCount(mybatisCriteria));
		datagrid.setRows(rptSendReceiveAndSaveList);
		return datagrid;
	}
	public void exportSendReceiveAndSaveAllListDataToExcel(HttpServletResponse response, RptSendReceiveAndSave form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankRptSendReceiveAndSaveAllList();
			// excel的sheetName
			String sheetName = "收发存汇中总表";
			// excel要导出的数据
			List<RptSendReceiveAndSave> rptSendReceiveAndSaveList = statisticalAnalysisMybatisDao.querySendReceiveAndSaveAllList(mybatisCriteria);
			// 导出
			if (rptSendReceiveAndSaveList== null || rptSendReceiveAndSaveList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (RptSendReceiveAndSave s: rptSendReceiveAndSaveList) {

				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptSendReceiveAndSaveList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankRptSendReceiveAndSaveAllList() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("warehouse", "仓库名称");
		superClassMap.put("sku", "存货编码");
		superClassMap.put("lotatt12", "存货名称");
		superClassMap.put("descrc", "规格型号");
		superClassMap.put("uom", "主计量单位");
		superClassMap.put("skuCategoryName", "存货分类名称");
		superClassMap.put("startResultQty", "期初结存件数");
		superClassMap.put("asnqtySum", "总计_入库件数");
		superClassMap.put("soqtySum", "总计_出库件数");
		superClassMap.put("endResultQty", "期末结存件数");
		return superClassMap;
	}




	/**************************************销售出库单列表****************************************/
	public EasyuiDatagrid<RptSalesSo> getPagedDatagridRptSalesSoList(EasyuiDatagridPager pager, RptSalesSo query) {
		EasyuiDatagrid<RptSalesSo> datagrid = new EasyuiDatagrid<RptSalesSo>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptSalesSo> rptRptSalesSoList= statisticalAnalysisMybatisDao.querySalesSoList(mybatisCriteria);
		for (RptSalesSo s: rptRptSalesSoList) {
             //计算数量
			if(s.getQty1()!=null&&s.getQty()!=null) {
				s.setQtyeach(s.getQty1() * s.getQty());
			}
			//仓库编码
			s.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			s.setWarehouse(SfcUserLoginUtil.getLoginUser().getWarehouse().getWarehouseName());
		}
		datagrid.setTotal((long) statisticalAnalysisMybatisDao.querySalesSoListCount(mybatisCriteria));
		datagrid.setRows(rptRptSalesSoList);
		return datagrid;
	}
	public void exportSalesSoListDataToExcel(HttpServletResponse response, RptSalesSo form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankRptSalesSoList();
			// excel的sheetName
			String sheetName = "销售出库单列表";
			// excel要导出的数据
			List<RptSalesSo> rptRptSalesSoList = statisticalAnalysisMybatisDao.querySalesSoList(mybatisCriteria);
			// 导出
			if (rptRptSalesSoList== null || rptRptSalesSoList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (RptSalesSo s: rptRptSalesSoList) {
					//计算数量
					if(s.getQty1()!=null&&s.getQty()!=null) {
						s.setQtyeach(s.getQty1() * s.getQty());
					}
					//仓库编码
					s.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
					s.setWarehouse(SfcUserLoginUtil.getLoginUser().getWarehouse().getWarehouseName());
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptRptSalesSoList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankRptSalesSoList() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("serialNumber", "序号");
		superClassMap.put("choose", "选择");
		superClassMap.put("warehouseid", "仓库编码");
		superClassMap.put("sourceOrder", "来源订单号");
		superClassMap.put("soOrderNum", "发货单号码");
		superClassMap.put("soOrderNo", "出库单号");
		superClassMap.put("soDate", "出库日期");
		superClassMap.put("soType", "出库类别");
		superClassMap.put("warehouse", "仓库");
		superClassMap.put("customerid", "客户名称");
		superClassMap.put("sku", "存货编码");
		superClassMap.put("lotatt12", "存货名称");
		superClassMap.put("descrc", "规格型号");
		superClassMap.put("qtyeach", "数量");
		superClassMap.put("qty", "件数");
		superClassMap.put("uom", "主计量单位");
		superClassMap.put("lotatt04", "批号");
		superClassMap.put("lotatt02", "失效日期");
		superClassMap.put("addwho", "制单人");
		superClassMap.put("reviewWho", "审核人");
		superClassMap.put("trackingNumber", "快递单号码");

		return superClassMap;
	}





	/**************************************订单装箱清单-按发运单号****************************************/
	public EasyuiDatagrid<RptOrderPackingcartonByOrderNo> getPagedDatagridRptOrderPackingcartonByOrderNo(EasyuiDatagridPager pager, RptOrderPackingcartonByOrderNo query) {
		EasyuiDatagrid<RptOrderPackingcartonByOrderNo> datagrid = new EasyuiDatagrid<RptOrderPackingcartonByOrderNo>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrderPackingcartonByOrderNo> rptOrderPackingcartonByOrderNoList= statisticalAnalysisMybatisDao.queryOrderPackingcartonByOrderNo(mybatisCriteria);
		for (RptOrderPackingcartonByOrderNo s: rptOrderPackingcartonByOrderNoList) {
 //计算数量
			s.setQtyEach(s.getQty() * s.getQty1());
		}
		datagrid.setTotal((long) statisticalAnalysisMybatisDao.queryOrderPackingcartonByOrderNoCount(mybatisCriteria));
		datagrid.setRows(rptOrderPackingcartonByOrderNoList);
		return datagrid;
	}
	public void exportOrderPackingcartonByOrderNoDataToExcel(HttpServletResponse response, RptOrderPackingcartonByOrderNo form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankRptOrderPackingcartonByOrderNo();
			// excel的sheetName
			String sheetName = "订单装箱清单-按发运单号";
			// excel要导出的数据
			List<RptOrderPackingcartonByOrderNo> rptOrderPackingcartonByOrderNoList = statisticalAnalysisMybatisDao.queryOrderPackingcartonByOrderNo(mybatisCriteria);
			// 导出
			if (rptOrderPackingcartonByOrderNoList== null || rptOrderPackingcartonByOrderNoList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (RptOrderPackingcartonByOrderNo s: rptOrderPackingcartonByOrderNoList) {
					//计算数量
					s.setQtyEach(s.getQty() * s.getQty1());
					//质量状态
					List<EasyuiCombobox> Lotatt10List = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
					for (EasyuiCombobox easyuiCombobox : Lotatt10List)
					{
						//质量状态id对比
						if (s.getLotatt10().equals(easyuiCombobox.getId())) {
							s.setLotatt10(easyuiCombobox.getValue());
							break;
						}
					}
					//是否装箱
					if(s.getPackingflag()!=null){
						if(s.getPackingflag().equals("1")){
							s.setPackingflag("是");
						}else{
							s.setPackingflag("否");
						}
					}
					//双证
					if(s.getLotatt13()!=null){
						if(s.getLotatt13().equals("1")){
							s.setLotatt13("已匹配");
						}else{
							s.setLotatt13("");
						}
					}
					//产品属性
					List<EasyuiCombobox> Lotatt09List = basCodesService.getBy(Constant.CODE_CATALOG_SAMPLEATTR);
					for (EasyuiCombobox easyuiCombobox : Lotatt09List)
					{
						//状态id对比
						if (s.getLotatt09().equals(easyuiCombobox.getId())) {
							s.setLotatt09(easyuiCombobox.getValue());
							break;
						}
					}

				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptOrderPackingcartonByOrderNoList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankRptOrderPackingcartonByOrderNo() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();

		superClassMap.put("orderno", "出库单号");
		superClassMap.put("traceid", "箱号");
		superClassMap.put("packingflag", "是否装箱完成");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("customerid", "货主代码");
		superClassMap.put("shippershortname", "货主简称");
		superClassMap.put("lotatt08Name", "供应商");
		superClassMap.put("sku", "产品代码");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("lotatt06", "注册证号");
		superClassMap.put("skudescrc", "规格/型号");
		superClassMap.put("lotatt04", "生产批号");
		superClassMap.put("lotatt07", "灭菌批号");
		superClassMap.put("lotatt05", "序列号");
		superClassMap.put("lotatt01", "生产日期");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("lotatt02", "有效期/失效期");
		superClassMap.put("lotatt11", "存储条件");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("reservedfield06", "生产许可证号/备案号");
		superClassMap.put("qty", "装箱件数");
		superClassMap.put("qtyEach", "装箱数量");
		superClassMap.put("uom", "单位");
		superClassMap.put("description", "复核说明");
		superClassMap.put("conclusion", "复核结论");
		superClassMap.put("editwho", "复核人");
		superClassMap.put("edittime", "复核时间");
		superClassMap.put("addtime", "创建时间");
		superClassMap.put("addwho", "创建人");
		superClassMap.put("lotatt14", "入库单号");
		superClassMap.put("lotatt09", "产品属性");
		superClassMap.put("lotatt13", "双证");
		superClassMap.put("qty1", "转换率");

		return superClassMap;
	}
}