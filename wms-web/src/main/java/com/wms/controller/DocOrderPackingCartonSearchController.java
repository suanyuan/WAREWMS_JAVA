package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocOrderPackingCarton;
import com.wms.entity.DocQcHeader;
import com.wms.entity.DocQcSearchExportForm;
import com.wms.service.DocOrderPackingCartonSearchExportService;
import com.wms.service.DocOrderPackingCartonSearchService;
import com.wms.utils.annotation.Login;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("docOrderPackingCartonSearchController")
public class DocOrderPackingCartonSearchController {

	@Autowired
	private DocOrderPackingCartonSearchService docOrderPackingCartonSearchService;
	@Autowired
	private DocOrderPackingCartonSearchExportService docOrderPackingCartonSearchExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docOrderPackingCartonSearch/main", model);
	}
	/**
	 * 显示细单 分页
	 * @param pager
	 * @param query
	 * @return
	 */
	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocOrderPackingCarton> showDatagrid(EasyuiDatagridPager pager, DocOrderPackingCarton query) {
		return docOrderPackingCartonSearchService.getPagedDatagrid(pager,query);
	}
//	导出
	@Login
	@RequestMapping(params = "exportDocOrderPackingCartonSearchDataToExcel")
	public void exportDocOrderPackingCartonSearchDataToExcel(HttpServletResponse response, DocQcSearchExportForm form) throws Exception {
		docOrderPackingCartonSearchExportService.exportDocQcSearchDataToExcel(response, form);
	}

	/**
	 * 打印验收报告
	 *
	 */
	@Login
	@RequestMapping(params = "printQcSearch")
	public String printQcSearch(String linestatus , String userdefine4, String userdefine3 , Model model){

		List<DocQcHeader> docQcHeaderList = docOrderPackingCartonSearchService.printQcSearch(linestatus,userdefine4,userdefine3);

		JRDataSource jrDataSource = new JRBeanCollectionDataSource(docQcHeaderList);
		model.addAttribute("url", "WEB-INF/jasper/report1Query.jasper");
		model.addAttribute("format", "pdf");
		model.addAttribute("jrMainDataSource", jrDataSource);
		return "iReportView";
	}

}