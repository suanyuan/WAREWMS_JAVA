package com.wms.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.utils.editor.CustomDateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocPoHeaderService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocPoHeaderVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocPoHeaderForm;
import com.wms.query.DocPoHeaderQuery;

@Controller
@RequestMapping("docPoHeaderController")
public class DocPoHeaderController {



    @Autowired
    private DocPoHeaderService docPoHeaderService;

    //返回采购订单空页面 返回docPoHeader/main.jsp
    @Login
    @RequestMapping(params = "toMain")
    public ModelAndView toMain(String menuId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("menuId", menuId);
        return new ModelAndView("docPoHeader/main", model);
    }

    //显示分页信息
    @Login
    @RequestMapping(params = "showDatagrid")
    @ResponseBody
    public EasyuiDatagrid<DocPoHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocPoHeaderQuery query) {
        return docPoHeaderService.getPagedDatagrid(pager, query);
    }

    //增加采购订单
    @Login
    @RequestMapping(params = "add")
    @ResponseBody
    public Json add(DocPoHeaderForm docPoHeaderForm) throws Exception {
        Json json = docPoHeaderService.addDocPoHeader(docPoHeaderForm);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }

        return json;
    }

    //编辑采购订单
    @Login
    @RequestMapping(params = "edit")
    @ResponseBody
    public Json edit(DocPoHeaderForm docPoHeaderForm) throws Exception {
        Json json = docPoHeaderService.editDocPoHeader(docPoHeaderForm);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }

        return json;
    }

    //删除采购订单
//	@Login
//	@RequestMapping(params = "delete")
//	@ResponseBody
//	public Json delete(String id) {
//		Json json = docPoHeaderService.deleteDocPoHeader(id);
//		if(json == null){
//			json = new Json();
//			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
//		}
//		return json;
//	}
//取消采购订单
    @Login
    @RequestMapping(params = "cancel")
    @ResponseBody
    public Json cancel(String id) {
        Json json = docPoHeaderService.cancelDocPoHeader(id);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "getBtn")
    @ResponseBody
    public Json getBtn(String id, HttpSession session) {
        return docPoHeaderService.getBtn(id, (SfcUserLogin) session.getAttribute(ResourceUtil.getUserInfo()));
    }

    //获取采购类型
    @Login
    @RequestMapping(params = "getPotypeCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getPotypeCombobox() {
        return docPoHeaderService.getPotypeCombobox();
    }

    //获取采购状态
    @Login
    @RequestMapping(params = "getPostatusCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getPostatusCombobox() {
        return docPoHeaderService.getPostatusCombobox();
    }

    //获取仓库
    @Login
    @RequestMapping(params = "getWarehouseCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getWarehouseCombobox() {
        return docPoHeaderService.getWarehouseCombobox();
    }


}