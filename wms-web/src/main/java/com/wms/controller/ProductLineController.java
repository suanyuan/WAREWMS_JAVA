package com.wms.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.wms.entity.BasCustomer;
import com.wms.entity.ProductLine;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.ProductLineMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.ProductLineService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.ProductLineVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ProductLineForm;
import com.wms.query.ProductLineQuery;

@Controller
@RequestMapping("productLineController")
public class ProductLineController {

	@Autowired
	private ProductLineService productLineService;

	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;



	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("productLine/main", model);
	}
	@Login
	@RequestMapping(params = "toAddProduct")
	public ModelAndView toAddProduct(String productLineId) {
		Map<String, Object> model = new HashMap<String, Object>();
		ProductLine productLine = productLineMybatisDao.queryById(productLineId);
		if (productLine != null) {

			BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(productLine.getCustomerid());
			if (basCustomer != null) {

				model.put("descrC", basCustomer.getDescrC());
			}
		}
		model.put("productLine", productLine);
		return new ModelAndView("productLine/addProduct", model);
	}

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<ProductLineVO> showDatagrid(EasyuiDatagridPager pager, ProductLineQuery query) throws Exception {
		EasyuiDatagrid<ProductLineVO> pagedDatagrid = productLineService.getPagedDatagrid(pager, query);
		return pagedDatagrid;
	}


	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(ProductLineForm productLineForm) throws Exception {
		Json json = productLineService.addProductLine(productLineForm);
		if(json == null){
			json = new Json();
		}
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(ProductLineForm productLineForm) throws Exception {
		Json json = productLineService.updateProductLine(productLineForm);
		if(json == null){
			json = new Json();
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String productLineId) {
		Json json = productLineService.deleteProductLine(productLineId);
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
		return productLineService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return productLineService.getProductLineCombobox();
	}

}