package com.wms.controller;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocAsnHeader;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.DocAsnHeaderQuery;
import com.wms.result.AsnDetailResult;
import com.wms.service.DocAsnExportService;
import com.wms.service.DocAsnHeaderService;
import com.wms.service.DocAsnUtilService;
import com.wms.service.DocPaService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnHeaderForm;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("docAsnHeaderController")
public class DocAsnHeaderController {

	@Autowired
	private DocAsnHeaderService docAsnHeaderService;
	@Autowired
	private DocAsnExportService docAsnExportService;
	@Autowired
	private DocPaService docPaService;
	@Autowired
	private DocAsnUtilService docAsnUtilService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edisendtime5",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime1",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime2",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docAsnHeader/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocAsnHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocAsnHeaderQuery query) {
		return docAsnHeaderService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "showAsnHeader")
	@ResponseBody
	public DocAsnHeader showAsnHeader(DocAsnHeaderQuery query) {
		return docAsnHeaderService.getAsnHeader(query);
	}
	
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocAsnHeaderForm docAsnHeaderForm) throws Exception {
		Json json = docAsnHeaderService.addDocAsnHeader(docAsnHeaderForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Json edit(DocAsnHeaderForm docAsnHeaderForm) throws Exception {
		Json json = docAsnHeaderService.editDocAsnHeader(docAsnHeaderForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String asnnos) throws Exception {
		Json json = docAsnHeaderService.deleteDocAsn(asnnos);
		return json;
	}

	@Login
	@RequestMapping(params = "cancel", method = RequestMethod.POST)
	@ResponseBody
	public Json cancel(String asnnos) {
		Json json = docAsnHeaderService.cancelDocAsnHeader(asnnos);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

    @Login
    @RequestMapping(params = "closeCheck", method = RequestMethod.GET)
    @ResponseBody
    public Json closeCheck(String asnnos) {
        Json json = docAsnUtilService.checkCloseAsn(asnnos);
        if(json == null){
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }
	
	@Login
	@RequestMapping(params = "close", method = RequestMethod.POST)
	@ResponseBody
	public Json close(String asnnos) {
		Json json = docAsnHeaderService.closeDocAsnHeader(asnnos);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "exportPdf")
	public void exportPdf(HttpServletResponse response, String orderList) throws Exception {
		try {
//			docAsnHeaderService.exportPdf(response, orderList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData( MultipartHttpServletRequest mhsr) throws Exception {
		return docAsnHeaderService.importExcelData(mhsr);
	}
	
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		docAsnHeaderService.exportTemplate(response, token);
	}


	//保存序列号销退	导入
	@Login
	@RequestMapping(params = "importSerialNumExcelData")
	@ResponseBody
	public Json importSerialNumExcelData( MultipartHttpServletRequest mhsr,String asnno) throws Exception {
		System.out.println(asnno);
		return docAsnHeaderService.importSerialNumExcelData(mhsr,asnno);
	}
	//保存序列号销退 导入模板下载
	@Login
	@RequestMapping(params = "exportSerialNumTemplate", method = RequestMethod.POST)
	public void exportSerialNumTemplate(HttpServletResponse response, String token) throws Exception {
		docAsnHeaderService.exportSerialNumTemplate(response, token);
	}

    @Login
    @RequestMapping(params = "selectTotalReceivingNum")
    @ResponseBody
    public Object selectTotalReceivingNum(String asnnos) {
        return docAsnHeaderService.selectTotalReceivingNum(asnnos);
    }



	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docAsnHeaderService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getAsnTypeCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getAsnTypeCombobox() {
		return docAsnHeaderService.getAsnTypeCombobox();
	}

	@Login
	@RequestMapping(params = "getAsnstatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getAsnstatusCombobox() {
		return docAsnHeaderService.getAsnstatusCombobox();
	}

	@Login
	@RequestMapping(params = "getReleasestatusCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getReleasestatusCombobox() {
		return docAsnHeaderService.getReleasestatusCombobox();
	}

	@Login
	@RequestMapping(params = "addDocPa",method = RequestMethod.POST)
	@ResponseBody
	public Json addDocPa(String asnNos){
		return docPaService.mergeDocPa(asnNos);
	}

	@Login
	@RequestMapping(params = "confirmReveiving",method = RequestMethod.POST)
	@ResponseBody
	public Json confirmReveiving(String asnNos){
		return docPaService.confirmReceiving(asnNos);
	}

	/**
	 * 取消收货
	 * @param asnNos
	 * @return
	 */
	@Login
	@RequestMapping(params = "noconfirmReveiving",method = RequestMethod.POST)
	@ResponseBody
	public Json noconfirmReveiving(String asnNos){
		return docPaService.noconfirmReceiving(asnNos);
	}

	@Login
	@RequestMapping(params = "doRefIn",method = RequestMethod.POST)
	@ResponseBody
	public Json doRefIn(String orderno,String refOrderno) throws Exception{
		return docAsnHeaderService.doRefIn(orderno,refOrderno);
	}

	@Login
	@RequestMapping(params = "showAsnDetal")
	@ResponseBody
	public EasyuiDatagrid<AsnDetailResult> showAsnDetal(EasyuiDatagridPager pager, String ordero) {
		return docAsnHeaderService.getAsnDetail(ordero);
	}

	@Login
	@RequestMapping(params = "addDoDetailReuse",method = RequestMethod.POST)
	@ResponseBody
	public Json addAsnDetailReuse(String generalNo,String detailAssno ,String customerid ,String copyFlag) throws Exception {
		Json json = docAsnHeaderService.addDoDetailReuse(generalNo,detailAssno,customerid,copyFlag);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "qlDetails",method = RequestMethod.POST)
	@ResponseBody
	public Json qlDetails(String generalNo) {
		Json json = docAsnHeaderService.qlDetails(generalNo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

    /**
     * 引用新增
     * @param orderno 出库单号
     * @return ~
     */
	@Login
    @RequestMapping(params = "quoteDocOrder", method = RequestMethod.POST)
    @ResponseBody
	public Json quoteDocOrder(String orderno,String customerId,String supplierId) {

	    Json json = docAsnHeaderService.quoteDocOrder(orderno,customerId,supplierId);
        if(json == null){
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }


//导出
	@Login
	@RequestMapping(params = "exportAsnDataToExcel")
	public String exportAsnDataToExcel(Model model, String asnno){
		List<DocAsnHeader> docAsnHeaderList=docAsnExportService.docAsnToExcel(asnno);
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(docAsnHeaderList);
		model.addAttribute("url", "WEB-INF/jasper/reportDocAsnDetails.jasper");
		model.addAttribute("format", Constant.JASPER_XLS);
		model.addAttribute("jrMainDataSource", jrDataSource);
		return "iReportView";
	}


	/**
	 * 打印收货任务清单
	 * @param model
	 * @param asnno
	 * @return
	 */
	@Login
	@RequestMapping(params = "printTaskList")
	public String printTaskList(Model model, String asnno) {
		List<DocAsnHeader> docAsnHeaderList = docAsnHeaderService.printTaskList(asnno);
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(docAsnHeaderList);
		model.addAttribute("url", "WEB-INF/jasper/reportDocAsnHeaderTakeList.jasper");
		model.addAttribute("format", Constant.JASPER_PDF);
		model.addAttribute("jrMainDataSource", jrDataSource);
		return "iReportView";
	}

	/**
	 * 打印收货记录
	 * @param model
	 * @param asnno
	 * @return
	 */
	@Login
	@RequestMapping(params = "printTaskResult")
	public String printTaskResult(Model model, String asnno) {
		List<DocAsnHeader> docAsnHeaderList = docAsnHeaderService.printTaskResult(asnno);
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(docAsnHeaderList);
		model.addAttribute("url", "WEB-INF/jasper/reportDocAsnHeaderTakeResult.jasper");
		model.addAttribute("format", Constant.JASPER_PDF);
		model.addAttribute("jrMainDataSource", jrDataSource);
		return "iReportView";
	}
}