package com.wms.controller.gsp;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspProductRegister;
import com.wms.query.GspProductRegisterSpecsQuery;
import com.wms.service.GspEnterpriseInfoService;
import com.wms.service.GspOperateDetailService;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.GspProductRegisterSpecsVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspProductRegisterService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspProductRegisterVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterForm;
import com.wms.query.GspProductRegisterQuery;

@Controller
@RequestMapping("gspProductRegisterController")
public class GspProductRegisterController {

	@Autowired
	private GspProductRegisterService gspProductRegisterService;
	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class,"addtime",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"edisendtime5",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime1",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"expectedarrivetime2",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"createDate",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class,"editDate",new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspProductRegister/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterVO> showDatagrid(EasyuiDatagridPager pager, GspProductRegisterQuery query) {
		return gspProductRegisterService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "showDatagridSearch")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterVO> showDatagridSearch(EasyuiDatagridPager pager, GspProductRegisterQuery query) {
//		query.setIsUse(Constant.IS_USE_YES);
		query.setCheckerId("all");
		return gspProductRegisterService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(GspProductRegisterForm gspProductRegisterForm) throws Exception {
		Json json = gspProductRegisterService.addGspProductRegister(gspProductRegisterForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "addSpec",method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestParam(value="gspProductRegisterId",required=true) String gspProductRegisterId,@RequestParam(value="specId",required=true) String specId) throws Exception {
		Json json = gspProductRegisterService.bindProduct(gspProductRegisterId,specId);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;

	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(GspProductRegisterForm gspProductRegisterForm) throws Exception {
		Json json = gspProductRegisterService.editGspProductRegister(gspProductRegisterForm);
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = gspProductRegisterService.deleteGspProductRegister(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));

		}
		return json;
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspProductRegisterService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}



	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspProductRegisterService.getGspProductRegisterCombobox();
	}

	@Login
	@RequestMapping(params = "confirmSubmit")
	@ResponseBody
	public Json confirmSubmit(String id){
		return gspProductRegisterService.confirmSubmit(id);
	}

	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(@RequestParam(defaultValue = "") String id){
		Map<String,Object> map = new HashMap<>();
		GspProductRegister gspProductRegister;
		if(!"".equals(id)){
			gspProductRegister = gspProductRegisterService.queryById(id);
			//根据EnterpriseId查出getEnterpriseName 生产企业
			GspEnterpriseInfo info = gspEnterpriseInfoService.getGspEnterpriseInfo(gspProductRegister.getEnterpriseId());
			if(info!=null){
				map.put("enterpriseName",info.getEnterpriseName());
			}
			List<GspOperateDetailVO> gspOperateDetailList = gspOperateDetailService.queryOperateDetailByLicense(gspProductRegister.getProductRegisterId());
			if(gspOperateDetailList!=null && gspOperateDetailList.size()>0){
				map.put("choseScope",gspOperateDetailList.get(0).getOperateId());
			}
		}else {
			gspProductRegister = new GspProductRegister();
			gspProductRegister.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			gspProductRegister.setCreateDate(new Date());
		}
		map.put("gspProductRegister",gspProductRegister);
		map.put("gspProductRegisterId",id);
		return new ModelAndView("gspProductRegister/detail",map);
	}

	/**
	 * 查询产品注册证规格子表
	 * @param pager
	 * @param query
	 * @return
	 */
	@Login
	@RequestMapping(params = "showSpecsList")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterSpecsVO> showSpecsList(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query){
		if(StringUtils.isEmpty(query.getProductRegisterId())){
			EasyuiDatagrid<GspProductRegisterSpecsVO> specsVOEasyuiDatagrid = new EasyuiDatagrid<GspProductRegisterSpecsVO>();
			specsVOEasyuiDatagrid.setRows(new ArrayList<GspProductRegisterSpecsVO>());
			specsVOEasyuiDatagrid.setTotal(0L);
			return specsVOEasyuiDatagrid;
		}
		return gspProductRegisterService.queryProductPageListByRegisterId(pager,query);
	}

	@Login
	@RequestMapping(params = "unBind")
	@ResponseBody
	public Object unBind(@RequestParam(defaultValue = "") String gspProductRegisterId,@RequestParam(defaultValue = "") String id){
		return gspProductRegisterService.unBindProduct(gspProductRegisterId,id);
	}
//下载导入模板
	@Login
	@RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
	public void exportTemplate(HttpServletResponse response, String token) throws Exception {
		gspProductRegisterService.exportTemplate(response, token);
	}
//导入excel到表单
	@Login
	@RequestMapping(params = "importExcelData")
	@ResponseBody
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws Exception {
		return gspProductRegisterService.importExcelData(mhsr);
	}

	//导出
	@Login
	@RequestMapping(params = "exportDataToExcel")
	public void exportDataToExcel(HttpServletResponse response, GspProductRegisterQuery form) throws Exception {
		gspProductRegisterService.exportDataToExcel(response, form);
	}
	@Login
	@RequestMapping(params = "showHistoryDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspProductRegisterVO> showHistoryDatagrid(EasyuiDatagridPager pager, GspProductRegisterQuery query) {
		return gspProductRegisterService.showHistoryDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "queryGspProductRegister")
	@ResponseBody
	public GspProductRegisterVO queryGspProductRegister(String registerId) throws Exception{
		GspProductRegisterVO vo = new GspProductRegisterVO();
		GspProductRegister gspProductRegister = gspProductRegisterService.queryById(registerId);
		BeanUtils.copyProperties(gspProductRegister,vo);
		return vo;
	}
}