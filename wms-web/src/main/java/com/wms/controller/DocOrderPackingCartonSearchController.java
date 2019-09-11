package com.wms.controller;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocOrderPackingCarton;
import com.wms.entity.DocOrderPackingCartonInfo;
import com.wms.entity.DocQcHeader;
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
	public void exportDocOrderPackingCartonSearchDataToExcel(HttpServletResponse response, DocOrderPackingCarton form) throws Exception {
		docOrderPackingCartonSearchExportService.exportDocQcSearchDataToExcel(response, form);
	}

    /**
     * 打印验收报告
     */

    @Login
    @RequestMapping(params = "printQcSearch")
    public String printQcSearch(Model model, String orderno, String traceid, String lotatt10, String skudesce, String customerid, String shippershortname, String sku, String lotatt12, String lotatt08, String lotatt15, String edittimeStart, String edittimeEnd, String lotatt14, String packingflag) {
        List<DocOrderPackingCartonInfo> docQcHeaderList = docOrderPackingCartonSearchService.printQcSearch(orderno, traceid, lotatt10, skudesce, customerid, shippershortname, sku, lotatt12, lotatt08, lotatt15, edittimeStart, edittimeEnd, lotatt14, packingflag);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(docQcHeaderList);
        model.addAttribute("url", "WEB-INF/jasper/docOrderPackingCartonSearchPdf.jasper");
        model.addAttribute("format", Constant.JASPER_PDF);
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";
    }

}