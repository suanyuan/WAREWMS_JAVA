package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.RptAsnList;
import com.wms.entity.RptSoAsnDailyLocation;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.StatisticalAnalysisMybatisDao;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
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
}