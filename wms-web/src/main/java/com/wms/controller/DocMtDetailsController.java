package com.wms.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.constant.Constant;
import com.wms.entity.DocMtHeader;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocMtDetailsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocMtDetailsVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocMtDetailsForm;
import com.wms.query.DocMtDetailsQuery;

@Controller
@RequestMapping("docMtDetailsController")
public class DocMtDetailsController {

	@Autowired
	private DocMtDetailsService docMtDetailsService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docMtDetails/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<DocMtDetailsVO> showDatagrid(EasyuiDatagridPager pager, DocMtDetailsQuery query) {
		return docMtDetailsService.getPagedDatagrid(pager, query);
	}
	/**
	 * 批量养护 传json集合
	 * @param forms
	 * @return
	 * @throws Exception
	 */
	@Login
	@RequestMapping(params = "submitDocMtList")
	@ResponseBody
	public Json submitDocMtList(String forms) throws Exception {
		return docMtDetailsService.submitDocMtList(forms);
	}
	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(DocMtDetailsForm docMtDetailsForm) throws Exception {
		Json json = docMtDetailsService.addDocMtDetails(docMtDetailsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(DocMtDetailsForm docMtDetailsForm) throws Exception {
		Json json = docMtDetailsService.editDocMtDetails(docMtDetailsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = docMtDetailsService.deleteDocMtDetails(id);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return docMtDetailsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return docMtDetailsService.getDocMtDetailsCombobox();
	}

	/**
	 *  打印养护检查记录
     *
	*/
    @Login
	@RequestMapping(params = "printMtDetails")
	public String printMtDetails( String mtno,String linestatus,String descrc,String customerid,String sku,String lotatt12,
								  String lotatt04,String lotatt05,String productLineName,Model model){

        List<DocMtHeader> docMtHeaderList = docMtDetailsService.printMtDetails(mtno,linestatus,descrc,customerid,sku,lotatt12,lotatt04,lotatt05,productLineName);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(docMtHeaderList);
        model.addAttribute("url", "WEB-INF/jasper/reportDocMtDetails.jasper");
        model.addAttribute("format", Constant.JASPER_PDF);
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";

    }

}