package com.wms.controller;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocQcHeader;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.DocQcHeaderQuery;
import com.wms.service.DocQcHeaderService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.DocQcHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQcHeaderForm;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("docQcHeaderController")
public class DocQcHeaderController {

    @Autowired
    private DocQcHeaderService docQcHeaderService;

    @Login
    @RequestMapping(params = "toMain")
    public ModelAndView toMain(String menuId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("menuId", menuId);
        return new ModelAndView("docQcHeader/main", model);
    }

    /**
     * 分页 显示主单
     *
     * @param pager
     * @param query
     * @return
     */
    @Login
    @RequestMapping(params = "showDatagrid")
    @ResponseBody
    public EasyuiDatagrid<DocQcHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocQcHeaderQuery query) {
        return docQcHeaderService.getPagedDatagrid(pager, query);
    }

    /**
     * 分页 窗口查询加上客户订单号
     *
     * @param pager
     * @param query
     * @return
     */
    @Login
    @RequestMapping(params = "showDatagrid1")
    @ResponseBody
    public EasyuiDatagrid<DocQcHeaderVO> showDatagrid1(EasyuiDatagridPager pager, DocQcHeaderQuery query) {
        return docQcHeaderService.getPagedDatagrid1(pager, query);
    }

    @Login
    @RequestMapping(params = "add")
    @ResponseBody
    public Json add(DocQcHeaderForm docQcHeaderForm) throws Exception {
        Json json = docQcHeaderService.addDocQcHeader(docQcHeaderForm);
        if (json == null) {
            json = new Json();
        }
        json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        return json;
    }

    @Login
    @RequestMapping(params = "edit")
    @ResponseBody
    public Json edit(DocQcHeaderForm docQcHeaderForm) throws Exception {
        Json json = docQcHeaderService.editDocQcHeader(docQcHeaderForm);
        if (json == null) {
            json = new Json();
        }
        json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        return json;
    }

    @Login
    @RequestMapping(params = "delete")
    @ResponseBody
    public Json delete(String id) {
        Json json = docQcHeaderService.deleteDocQcHeader(id);
        if (json == null) {
            json = new Json();
        }
        json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        return json;
    }

    @Login
    @RequestMapping(params = "getBtn")
    @ResponseBody
    public Json getBtn(String id, HttpSession session) {
        return docQcHeaderService.getBtn(id, (SfcUserLogin) session.getAttribute(ResourceUtil.getUserInfo()));
    }

/*	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return docQcHeaderService.getDocQcHeaderCombobox();
	}*/

    /**
     * 打印验收作业
     */
    @Login
    @RequestMapping(params = "printQcHeader")
    public String printQcHeader(Model model, String qcno, String linestatus, String lotatt10) {

        List<DocQcHeader> docQcHeaderList = docQcHeaderService.printQcHeader(qcno, linestatus, lotatt10);

        JRDataSource jrDataSource = new JRBeanCollectionDataSource(docQcHeaderList);
        model.addAttribute("url", "WEB-INF/jasper/reportDocQcHeaderTask.jasper");
        model.addAttribute("format", Constant.JASPER_PDF);
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";
    }

}