package com.wms.controller;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.Country;
import com.wms.entity.DocQcHeader;
import com.wms.entity.DocQcSearchExportForm;
import com.wms.query.DocQcDetailsQuery;
import com.wms.service.DocQcSearchExportService;
import com.wms.service.DocQcSearchService;
import com.wms.utils.annotation.Login;
import com.wms.vo.DocQcDetailsVO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("docQcSearchController")
public class DocQcSearchController {

	@Autowired
	private DocQcSearchService docQcSearchService;
	@Autowired
	private DocQcSearchExportService docQcSearchExportService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docQcSearch/main", model);
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
	public EasyuiDatagrid<DocQcDetailsVO> showDatagrid(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
		return docQcSearchService.getPagedDatagrid(pager, query);
	}
//	导出
	@Login
	@RequestMapping(params = "exportDocQcSearchDataToExcel")
	public void exportDocQcSearchDataToExcel(HttpServletResponse response, DocQcSearchExportForm form) throws Exception {
		docQcSearchExportService.exportDocQcSearchDataToExcel(response, form);
	}

	/**
	 * 打印验收报告
	 *
	 */
	@Login
	@RequestMapping(params = "printQcSearch")
	public String printQcSearch(Model model,String qcno, String linestatus,String lotatt10,String descrc,String customerid,
								String shippershortname,String sku,String lotatt12,String lotatt08,String lotatt15,String lotatt03Start,
								String lotatt03End,String lotatt14){

		List<DocQcHeader> docQcHeaderList = docQcSearchService.printQcSearch(qcno, linestatus,lotatt10,descrc,customerid,shippershortname,sku,lotatt12,lotatt08,lotatt15,lotatt03Start,lotatt03End,lotatt14);

		JRDataSource jrDataSource = new JRBeanCollectionDataSource(docQcHeaderList);
		model.addAttribute("url", "WEB-INF/jasper/reportDocQcHeader.jasper");
		model.addAttribute("format", Constant.JASPER_PDF);
		model.addAttribute("jrMainDataSource", jrDataSource);
		return "iReportView";
	}

}