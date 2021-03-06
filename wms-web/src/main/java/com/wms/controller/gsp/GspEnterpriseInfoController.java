package com.wms.controller.gsp;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.query.*;
import com.wms.service.*;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.vo.*;
import com.wms.vo.form.GspEnterpriceFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspEnterpriseInfoForm;

@Controller
@RequestMapping("gspEnterpriseInfoController")
public class GspEnterpriseInfoController {

	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;

	//企业信息主体业务

	@Autowired
	private GspEnterpriceService gspEnterpriceService;
	@Autowired
	private GspBusinessLicenseService gspBusinessLicenseService;
	@Autowired
	private GspOperateLicenseService gspOperateLicenseService;
	@Autowired
	private GspSecondRecordService gspSecondRecordService;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	@Autowired
	private GspMedicalRecordService gspMedicalRecordService;
	@Autowired
	private GspFirstRecordService gspFirstRecordService;

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

	private String initOperateDetail(List<GspOperateDetailVO> list){
		List<String> arr = new ArrayList<>();
		for(GspOperateDetailVO vo : list){
			arr.add(vo.getOperateId());
		}
		String result = arr.toString();
		return result.substring(1,result.length()-1);
	}


	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspEnterpriseInfo/main", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspEnterpriseInfoVO> showDatagrid(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) throws Exception{
		return gspEnterpriseInfoService.getPagedDatagrid(pager, query);
	}


	@Login
	@RequestMapping(params = "addEnterprise")
	@ResponseBody
	public Json addEnterprise(GspEnterpriseInfo gspEnterpriseInfo) throws Exception {
		Json json = gspEnterpriseInfoService.addEnterprise(gspEnterpriseInfo);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}


	@Login
	@RequestMapping(params = "add",method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestParam(value="enterpriseId",required = false,defaultValue = "") String enterpriceId,@RequestParam(value="gspEnterpriceFrom",required=true) String gspEnterpriceFromStr) throws Exception {
		GspEnterpriceFrom gspEnterpriceFrom = JSON.parseObject(gspEnterpriceFromStr,GspEnterpriceFrom.class);
		Json json = gspEnterpriceService.addGspEnterprice(gspEnterpriceFrom);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;

	}

	@Login
	@RequestMapping(params = "edit",method = RequestMethod.POST)
	@ResponseBody
	public Json edit(@RequestParam(value="enterpriseId",required=true) String enterpriceId,@RequestParam(value="gspEnterpriceFrom",required=true) String gspEnterpriceFromStr) throws Exception {
		GspEnterpriceFrom gspEnterpriceFrom = JSON.parseObject(gspEnterpriceFromStr,GspEnterpriceFrom.class);
		Json json = gspEnterpriceService.editGspEnterprice(enterpriceId,gspEnterpriceFrom);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		return gspEnterpriceService.deleteGspEnterprice(id);
		/*Json json = gspEnterpriseInfoService.deleteGspEnterpriseInfo(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;*/
	}


	//导出
	@Login
	@RequestMapping(params = "exportDataToExcel")
	public void exportDataToExcel(HttpServletResponse response, GspEnterpriseInfoQuery form) throws Exception {
		gspEnterpriseInfoService.exportDataToExcel(response, form);
	}

	@Login
	@RequestMapping(params = "deleteEnter")
	@ResponseBody
	public Json deleteEnter(String enterpriseId) {
		Json json = gspEnterpriceService.delete(enterpriseId);
		//Json json = gspEnterpriseInfoService.deleteGspEnterpriseInfo(id);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}

		 json.setMsg("解除成功！");
		return json;
	}
	//获取企业是否存在 首营申请
	@Login
	@RequestMapping(params = "verify")
	@ResponseBody
	public Object verify(String enterpriseId) {
		return gspEnterpriceService.verify(enterpriseId);
	}
	//查询6个证照是否存在,提示手动初始化（点击）
	@Login
	@RequestMapping(params = "selectSixLicense")
	@ResponseBody
	public Object selectSixLicense(String enterpriseId) {
		return gspEnterpriceService.selectSixLicense(enterpriseId);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspEnterpriseInfoService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspEnterpriseInfoService.getGspEnterpriseInfoCombobox();
	}

	@Login
	@RequestMapping(params = "toDetail")
	public ModelAndView toDetail(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		return new ModelAndView("gspEnterpriseInfo/detail", model);
	}

	@Login
	@RequestMapping(params = "toInfo")
	public ModelAndView toInfo(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		if(!"".equals(id)){
			Json json = gspEnterpriceService.getGspEnterpriceInfo(id);
			model.put("gspEnterpriseInfo",json.getObj());
		}
		return new ModelAndView("gspEnterpriseInfo/info", model);
	}

	@Login
    @RequestMapping(params = "toBusinessLicense")
    public ModelAndView toBusinessLicense(@RequestParam(defaultValue = "") String id) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", id);
        if(!"".equals(id)){
            Json json = gspEnterpriceService.getGspBusinessLicense(id);
            if(json.isSuccess() && json.getObj()!=null){
                GspBusinessLicense businessLicense = (GspBusinessLicense)json.getObj();
                List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(businessLicense.getBusinessId());
                if(detailVOS!=null){
                    System.out.println();
                    model.put("choseScope",initOperateDetail(detailVOS));
                }
                model.put("gspBusinessLicense",businessLicense);
            }
        }
        return new ModelAndView("gspEnterpriseInfo/businessLicense", model);
    }

    @Login
    @RequestMapping(params = "toMedicalLicense")
    public ModelAndView toMedicalLicense(@RequestParam(defaultValue = "") String id) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", id);
        if(!"".equals(id)){
            Json json = gspEnterpriceService.getGspMedicalRecord(id);
            if(json.isSuccess() && json.getObj()!=null){
                GspMedicalRecord medicalLicense = (GspMedicalRecord)json.getObj();
                List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(medicalLicense.getMedicalId());
                if(detailVOS!=null){
                    System.out.println();model.put("choseScope",initOperateDetail(detailVOS));
                }
                model.put("gspMedicalLicense",medicalLicense);
            }
        }
        return new ModelAndView("gspEnterpriseInfo/medicalLicense", model);
    }


	@Login
	@RequestMapping(params = "toOperateLicense")
	public ModelAndView toOperateLicense(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		if(!"".equals(id)) {
			Json json = gspEnterpriceService.getGspOperateLicense(id);
			if(json.isSuccess() && json.getObj()!=null){
				GspOperateLicense operateLicense = (GspOperateLicense)json.getObj();
				List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(operateLicense.getOperateId());
				if(detailVOS!=null){
					model.put("choseScope",initOperateDetail(detailVOS));
				}
				model.put("gspOperateLicense", operateLicense);
			}
		}
		return new ModelAndView("gspEnterpriseInfo/operateLicense", model);
	}

	@Login
	@RequestMapping(params = "toProdLicense")
	public ModelAndView toProdLicense(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		if(!"".equals(id)) {
			Json json = gspEnterpriceService.getGspProdLicense(id);
			if(json.isSuccess() && json.getObj()!=null){
				GspOperateLicense prodLicense = (GspOperateLicense)json.getObj();
				List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(prodLicense.getOperateId());
				if(detailVOS!=null){
					model.put("choseScope",initOperateDetail(detailVOS));
				}
				System.out.println();
				model.put("gspProdLicense", prodLicense);
			}
		}
		return new ModelAndView("gspEnterpriseInfo/prodLicense", model);
	}

    @Login
    @RequestMapping(params = "toFirstRecord")
    public ModelAndView toFirstRecord(@RequestParam(defaultValue = "") String id) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("enterpriseId", id);
        if(!"".equals(id)) {
            Json json = gspEnterpriceService.getGspFirstRecord(id);
            if(json.isSuccess() && json.getObj()!=null){
                GspFirstRecord firstRecord = (GspFirstRecord)json.getObj();
                List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(firstRecord.getRecordId());
                if(detailVOS!=null){
                    model.put("choseScope",initOperateDetail(detailVOS));
                }
                model.put("gspFirstRecord", firstRecord);
            }
        }
        return new ModelAndView("gspEnterpriseInfo/firstRecord", model);
    }
	@Login
	@RequestMapping(params = "toSecondRecord")
	public ModelAndView toSecondRecord(@RequestParam(defaultValue = "") String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("enterpriseId", id);
		if(!"".equals(id)) {
			Json json = gspEnterpriceService.getGspSecondRecord(id);
			if(json.isSuccess() && json.getObj()!=null){
				GspSecondRecord secondRecord = (GspSecondRecord)json.getObj();
				List<GspOperateDetailVO> detailVOS = gspOperateDetailService.queryOperateDetailByLicense(secondRecord.getRecordId());
				if(detailVOS!=null){
					model.put("choseScope",initOperateDetail(detailVOS));
				}
				model.put("gspSecondRecord", secondRecord);
			}
		}
		return new ModelAndView("gspEnterpriseInfo/secondRecord", model);
	}

	@Login
	@RequestMapping(params = "toSearchDialog")
	public ModelAndView toSearchDialog(@RequestParam(defaultValue = "") String target,
									   @RequestParam(defaultValue = "") String type,
									   @RequestParam(defaultValue = "") String enterpriseType) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("target",target);
		model.put("type",type);
		model.put("enterpriseType",enterpriseType);
		return new ModelAndView("gspEnterpriseInfo/search", model);
	}

	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(String enterpriseId) {
		return gspEnterpriceService.getGspEnterpriceInfo(enterpriseId);
	}

	@Login
	@RequestMapping(params = "getBusness")
	@ResponseBody
	public Object getBusness(String enterpriseId) {
		return gspEnterpriceService.getGspBusinessLicense(enterpriseId);
	}

	@Login
	@RequestMapping(params = "getOperate")
	@ResponseBody
	public Object getOperate(String enterpriseId) {
		return gspEnterpriceService.getGspOperateLicense(enterpriseId);
	}

	@Login
	@RequestMapping(params = "getProdLicense")
	@ResponseBody
	public Object getSCLicense(String enterpriseId) {
		return gspEnterpriceService.getGspProdLicense(enterpriseId);
	}


	@Login
	@RequestMapping(params = "getSecondRecord")
	@ResponseBody
	public Object getSecondRecord(String enterpriseId) {
		return gspEnterpriceService.getGspSecondRecord(enterpriseId);
	}

	@Login
	@RequestMapping(params = "showDatagridSearch")
	@ResponseBody
	public EasyuiDatagrid<GspEnterpriseInfoVO> showDatagridSearch(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) throws Exception{
		return gspEnterpriseInfoService.getPagedDatagridType(pager, query);
	}

	@Login
	@RequestMapping(params = "businessHistoryDatagridList")
	@ResponseBody
	public EasyuiDatagrid<GspBusinessLicenseVO> businessHistoryDatagridList(EasyuiDatagridPager pager, GspBusinessLicenseQuery query){
		return gspBusinessLicenseService.getGspBusinessLicenseHistory(pager,query);
	}
	@Login
	@RequestMapping(params = "medicalHistoryDatagridList")
	@ResponseBody
	public EasyuiDatagrid<GspMedicalRecordVO> medicalHistoryDatagridList(EasyuiDatagridPager pager, GspMedicalRecordQuery query){
		return gspMedicalRecordService.getGspMedicalRecordHistory(pager,query);
	}
	@Login
	@RequestMapping(params = "operateHistoryDatagridList")
	@ResponseBody
	public EasyuiDatagrid<GspOperateLicenseVO> operateHistoryDatagridList(EasyuiDatagridPager pager, GspOperateLicenseQuery query){
		return gspOperateLicenseService.getGspOperateLicenseHistory(pager,query);
	}

	@Login
	@RequestMapping(params = "recordHistoryDatagridList")
	@ResponseBody
	public EasyuiDatagrid<GspSecondRecordVO> recordHistoryDatagridList(EasyuiDatagridPager pager, GspSecondRecordQuery query){
		return gspSecondRecordService.getGspSecondRecordHistory(pager,query);
	}
	@Login
	@RequestMapping(params = "firstRecordHistoryDatagridList")
	@ResponseBody
	public EasyuiDatagrid<GspFirstRecordVO> firstRecordHistoryDatagridList(EasyuiDatagridPager pager, GspFirstRecordQuery query){
		return gspFirstRecordService.getGspFirstRecordHistory(pager,query);
	}
	@Login
	@RequestMapping(params = "getBusinessLicenseOutTime")
	@ResponseBody
	public Object getBusinessLicenseOutTime(){
		Json json = gspEnterpriceService.getBusinessLicenseOutTime("", Constant.CODE_ENT_TYP_ZT,1);
		return json;
	}


	//查询企业证照有效期过期的企业并报废  返回报废数量
	@Login
	@RequestMapping(params = "enterpriseOutTime")
	@ResponseBody
	public Object enterpriseOutTime() throws Exception {
		return gspEnterpriceService.enterpriseOutTime();
	}


}