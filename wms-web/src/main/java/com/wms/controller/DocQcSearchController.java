package com.wms.controller;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocQcDetailsQuery;
import com.wms.service.DocQcSearchService;
import com.wms.utils.annotation.Login;
import com.wms.vo.DocQcDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("docQcSearchController")
public class DocQcSearchController {

	@Autowired
	private DocQcSearchService docQcSearchService;

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);
		return new ModelAndView("docQcSearch/main", model);
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
	public EasyuiDatagrid<DocQcDetailsVO> showDatagrid(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
		return docQcSearchService.getPagedDatagrid(pager, query);
	}


}