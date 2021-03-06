package com.wms.controller.gsp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.wms.entity.BasCustomer;
import com.wms.entity.GspSupplier;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.GspSupplierMybatisDao;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.GspSupplierService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.GspSupplierVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspSupplierForm;
import com.wms.query.GspSupplierQuery;

@Controller
@RequestMapping("gspSupplierController")
public class GspSupplierController {

	@Autowired
	private GspSupplierService gspSupplierService;
	@Autowired
	private GspSupplierMybatisDao gspSupplierMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;


	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("gspSupplier/main", model);
	}

	@Login
	@RequestMapping(params = "toAdd")
	public ModelAndView toAdd(String id) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		GspSupplier gspSupplier =gspSupplierMybatisDao.queryById(id);
		if(gspSupplier!=null){
			BasCustomer basCustomer =  basCustomerMybatisDao.queryByIdType(gspSupplier.getCostomerid(),"OW");
			if(basCustomer!=null){
				model.put("clientEnterpriseId", basCustomer.getEnterpriseId());
				model.put("clientEnterpriseName", basCustomer.getDescrC());
			}
//			BasCustomer b = basCustomerMybatisDao.queryById(gspSupplier.getCostomerid());

		}

		model.put("gspSupplier", gspSupplier);
		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate",df.format(new Date()));
		model.put("isCheck",1);

//		model.put("specsId", specsId);
		model.put("isUse", 1);
		return new ModelAndView("gspSupplier/info", model);
	}

	@Login
	@RequestMapping(params = "basCustomerToAdd")
	public ModelAndView basCustomerToAdd(String id) {
		/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		GspSupplier gspSupplierQ = new GspSupplier();
		gspSupplierQ.setEnterpriseId(id);
//		gspSupplierQ.setEnterpriseType();
		GspSupplier gspSupplier1 = gspSupplierMybatisDao.queryByEnterpriseId(gspSupplierQ);
		if(gspSupplier1!=null){
			id = gspSupplier1.getSupplierId();
		}else{
			return new ModelAndView("gspSupplier/info", model);
		}
		GspSupplier gspSupplier =gspSupplierMybatisDao.queryById(id);
		if(gspSupplier!=null){
			BasCustomer basCustomer =  basCustomerMybatisDao.queryByIdType(gspSupplier.getCostomerid(),"OW");
			if(basCustomer!=null){
				model.put("clientEnterpriseId", basCustomer.getEnterpriseId());
			}
		}
		model.put("gspSupplier", gspSupplier);

		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate",df.format(new Date()));
		model.put("isCheck",1);

//		model.put("specsId", specsId);
		model.put("isUse", 1);
		return new ModelAndView("gspSupplier/info", model);*/
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		Json result = gspSupplierService.getGspSupplierInfo(id);
		GspSupplierVO supplier = (GspSupplierVO) result.getObj();
		GspSupplier g =new GspSupplier();
		BeanUtils.copyProperties(supplier, g);
		try {
			if(supplier.getClientEndDate()!=null){
				g.setClientEndDate(format1.parse(supplier.getClientEndDate()));
			}
			if(supplier.getClientStartDate()!=null){
				g.setClientStartDate(format1.parse(supplier.getClientStartDate()));
			}
			if(supplier.getEmpowerEnddate()!=null){
				g.setEmpowerEnddate(format1.parse(supplier.getEmpowerEnddate()));
			}
			if(supplier.getEmpowerStartdate()!=null){
				g.setEmpowerStartdate(format1.parse(supplier.getEmpowerStartdate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		model.put("gspSupplier", g);

		if(supplier!=null){
			BasCustomer basCustomer =  basCustomerMybatisDao.queryByIdType(supplier.getCostomerid(),"OW");
			if(basCustomer!=null){
				model.put("clientEnterpriseId", basCustomer.getEnterpriseId());
				model.put("clientEnterpriseName", basCustomer.getDescrC());
			}
		}

		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate",df.format(new Date()));
		model.put("isCheck",1);

//		model.put("specsId", specsId);
		model.put("isUse", 1);
		return new ModelAndView("gspSupplier/info", model);
	}


	@Login
	@RequestMapping(params = "showSupplierDetail")
	public ModelAndView showSupplierDetail(String id) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Map<String, Object> model = new HashMap<String, Object>();
		GspSupplier gspSupplier =gspSupplierMybatisDao.queryById(id);
		model.put("gspSupplier", gspSupplier);
        if(gspSupplier!=null){
            BasCustomer basCustomer =  basCustomerMybatisDao.queryByIdType(gspSupplier.getCostomerid(),"OW");
            if(basCustomer!=null){
                model.put("clientEnterpriseId", basCustomer.getEnterpriseId());
                model.put("clientEnterpriseName", basCustomer.getDescrC());
            }
        }
		model.put("createId", SfcUserLoginUtil.getLoginUser().getId());
		model.put("createDate",df.format(new Date()));
		model.put("isCheck",1);
		model.put("isUse", 1);
		return new ModelAndView("gspSupplier/detail", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<GspSupplierVO> showDatagrid(EasyuiDatagridPager pager, GspSupplierQuery query) {

		return gspSupplierService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(@RequestParam(value="gspSupplierForm",required=true) String gspSupplierFormStr) throws Exception {
		GspSupplierForm gspSupplierForm = JSON.parseObject(gspSupplierFormStr,GspSupplierForm.class);
		Json json = gspSupplierService.addGspSupplier(gspSupplierForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(@RequestParam(value="gspSupplierForm",required=true) String gspSupplierFormStr) throws Exception {

		GspSupplierForm gspProductRegisterSpecsForm = JSON.parseObject(gspSupplierFormStr,GspSupplierForm.class);
		Json json = gspSupplierService.editGspSupplierVerify(gspProductRegisterSpecsForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));

		}
		return json;
	}

	@Login
	@RequestMapping(params = "commit")
	@ResponseBody
	public Json commit(@RequestParam(value="gspSupplierForm",required=true) String gspSupplierFormStr) throws Exception {

		GspSupplierForm gspProductRegisterSpecsForm = JSON.parseObject(gspSupplierFormStr,GspSupplierForm.class);
		Json json = gspSupplierService.commitGspSupplier(gspProductRegisterSpecsForm);
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
		Json json = gspSupplierService.deleteGspSupplier(id);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}
	//发起新申请
	@Login
	@RequestMapping(params = "reApply")
	@ResponseBody
	public Json reApply(String id){
		return gspSupplierService.reApply(id);
	}

	@Login
	@RequestMapping(params = "getInfo")
	@ResponseBody
	public Object getInfo(String supplierId) {
		return gspSupplierService.getGspSupplierInfo(supplierId);
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return gspSupplierService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return gspSupplierService.getGspSupplierCombobox();
	}

}