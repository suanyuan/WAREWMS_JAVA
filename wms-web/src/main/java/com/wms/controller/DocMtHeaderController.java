package com.wms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.entity.DocMtHeader;
import com.wms.entity.InvLotLocId;
import com.wms.result.PdaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.DocMtHeaderService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.DocMtHeaderVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocMtHeaderForm;
import com.wms.query.DocMtHeaderQuery;

@Controller
@RequestMapping("docMtHeaderController")
public class DocMtHeaderController {

    @Autowired
    private DocMtHeaderService docMtHeaderService;

    @Login
    @RequestMapping(params = "toMain")
    public ModelAndView toMain(String menuId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("menuId", menuId);
        return new ModelAndView("docMtHeader/main", model);
    }

    //分页查询
    @Login
    @RequestMapping(params = "showDatagrid")
    @ResponseBody
    public EasyuiDatagrid<DocMtHeaderVO> showDatagrid(EasyuiDatagridPager pager, DocMtHeaderQuery query) {
        return docMtHeaderService.getPagedDatagrid(pager, query);
    }

    //获得养护信息 根据时间段
    @Login
    @RequestMapping(params = "getGenerationInfo")
    @ResponseBody
    public EasyuiDatagrid<InvLotLocId>  getGenerationInfo(EasyuiDatagridPager pager,DocMtHeaderQuery query) {
        return docMtHeaderService.getGenerationInfo(pager,query);
    }

    //生成养护信息 根据时间段
    @Login
    @RequestMapping(params = "ToGenerationInfo")
    @ResponseBody
    public Json ToGenerationInfo(DocMtHeaderQuery query) {
        return docMtHeaderService.ToGenerationInfo(query);
    }

    //关闭计划单
    @Login
    @RequestMapping(params = "closegenerationPlan")
    @ResponseBody
    public Json closegenerationPlan(DocMtHeaderForm form) {
        return docMtHeaderService.endDocMtJson(form);
    }

    //取消计划单
    @Login
    @RequestMapping(params = "cancel")
    @ResponseBody
    public Json cancel(DocMtHeader form) {
        return docMtHeaderService.cancel(form);
    }

    //增加
    @Login
    @RequestMapping(params = "add")
    @ResponseBody
    public Json add(DocMtHeaderForm docMtHeaderForm) throws Exception {
        Json json = docMtHeaderService.addDocMtHeader(docMtHeaderForm);
        if (json == null) {
            json = new Json();
        }
        json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        return json;
    }

    //编辑
    @Login
    @RequestMapping(params = "edit")
    @ResponseBody
    public Json edit(DocMtHeaderForm docMtHeaderForm) throws Exception {
        Json json = docMtHeaderService.editDocMtHeader(docMtHeaderForm);
        if (json == null) {
            json = new Json();
        }
        json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        return json;
    }

    //删除
    @Login
    @RequestMapping(params = "delete")
    @ResponseBody
    public Json delete(String id) {
        Json json = docMtHeaderService.deleteDocMtHeader(id);
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
        return docMtHeaderService.getBtn(id, (SfcUserLogin) session.getAttribute(ResourceUtil.getUserInfo()));
    }

    @Login
    @RequestMapping(params = "getCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getCombobox() {
        return docMtHeaderService.getDocMtHeaderCombobox();
    }

}