package com.wms.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuLeakMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.BasSkuLeakDao;
import com.wms.entity.BasSkuLeak;
import com.wms.vo.BasSkuLeakVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasSkuLeakForm;
import com.wms.query.BasSkuLeakQuery;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("basSkuLeakService")
public class BasSkuLeakService extends BaseService {

	@Autowired
	private BasSkuLeakMybatisDao basSkuLeakMybatisDao;

	public EasyuiDatagrid<BasSkuLeakVO> getPagedDatagrid(EasyuiDatagridPager pager, BasSkuLeakQuery query) {
		EasyuiDatagrid<BasSkuLeakVO> datagrid = new EasyuiDatagrid<BasSkuLeakVO>();
		MybatisCriteria criteria = new MybatisCriteria();


//		if(query.getIdList()!=null&&query.getIdList()!="" ){
//			List<String> enterpriseIdList = jsonToList(query.getIdList(),String.class);
//			criteria.setIdList(enterpriseIdList);
//		}
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		List<BasSkuLeak> basSkuLeakList = basSkuLeakMybatisDao.queryByList(criteria);
		BasSkuLeakVO basSkuLeakVO = null;
		List<BasSkuLeakVO> basSkuLeakVOList = new ArrayList<BasSkuLeakVO>();
		for (BasSkuLeak basSkuLeak : basSkuLeakList) {
			basSkuLeakVO = new BasSkuLeakVO();
			BeanUtils.copyProperties(basSkuLeak, basSkuLeakVO);
			basSkuLeakVOList.add(basSkuLeakVO);
		}
		int total = basSkuLeakMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basSkuLeakVOList);
		return datagrid;
	}

	public Json addBasSkuLeak(BasSkuLeakForm basSkuLeakForm) throws Exception {
		Json json = new Json();
		BasSkuLeak basSkuLeak = new BasSkuLeak();
		BeanUtils.copyProperties(basSkuLeakForm, basSkuLeak);
		basSkuLeakMybatisDao.add(basSkuLeak);
		json.setSuccess(true);
		return json;
	}

	public Json editBasSkuLeak(BasSkuLeakForm basSkuLeakForm) {
		Json json = new Json();
		BasSkuLeak basSkuLeak = basSkuLeakMybatisDao.queryById(basSkuLeakForm.getCustomerid());
		BeanUtils.copyProperties(basSkuLeakForm, basSkuLeak);
		basSkuLeakMybatisDao.updateBySelective(basSkuLeak);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasSkuLeak(String id) {
		Json json = new Json();
		BasSkuLeak basSkuLeak = basSkuLeakMybatisDao.queryById(id);
		if(basSkuLeak != null){
			basSkuLeakMybatisDao.delete(basSkuLeak);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasSkuLeakCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasSkuLeak> basSkuLeakList = basSkuLeakMybatisDao.queryByAll();
		if(basSkuLeakList != null && basSkuLeakList.size() > 0){
			for(BasSkuLeak basSkuLeak : basSkuLeakList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basSkuLeak.getId()));
				combobox.setValue(basSkuLeak.getCustomerid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}



	public void exportSkuDataToExcel(HttpServletResponse response, BasSkuLeakForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());

		BasSkuLeakForm basSkuForm = new BasSkuLeakForm();

//		basSkuForm.setCustomerid(form.getCustomerid());
//		basSkuForm.setSku(form.getSku());
//		basSkuForm.setActiveFlag(form.getActiveFlag());
//		basSkuForm.setEditwho();
		BeanUtils.copyProperties(form, basSkuForm);
		try {
			// 获取前台传来的数据
			//String cutomerid = form.getCustomerid();
			//String sku = form.getSku();
			//String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");
			//String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");
			BasSkuLeakQuery query = new BasSkuLeakQuery();
			BeanUtils.copyProperties(basSkuForm, query);
//			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "未备案产品档案";
			// excel要导出的数据
			List<BasSkuLeak> basSkuList = basSkuLeakMybatisDao.queryByList(mybatisCriteria);
			// 导出
			if (basSkuList == null || basSkuList.size() == 0) {
				//System.out.println("题库为空");
			}else {

				for (BasSkuLeak basSku : basSkuList) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=null;

				}


				ExcelUtil.listToExcel(basSkuList, fieldMap, sheetName, response);
				//System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("customerid", "货主");
		superClassMap.put("sku", "产品代码");
		superClassMap.put("standard", "规格");
		superClassMap.put("lotatt06", "注册证");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("conversionRatio", "换算率");
		superClassMap.put("unit", "单位");
		superClassMap.put("productline", "产品线");
		superClassMap.put("supplier", "供应商");


		return superClassMap;
	}


	/**
	 * json 转 List<T>
	 */
	public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}
}