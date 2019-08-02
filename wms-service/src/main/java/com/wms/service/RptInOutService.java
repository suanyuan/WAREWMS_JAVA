package com.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptInOutDao;
import com.wms.entity.RptInOut;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.RptInOutVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptInOutForm;
import com.wms.mybatis.dao.BasLocationMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptInOutMybatisDao;
import com.wms.query.RptInOutQuery;

@Service("rptInOutService")
public class RptInOutService extends BaseService {

	//@Autowired
	//private RptInOutDao rptInOutDao;
	
	@Autowired
	private RptInOutMybatisDao rptInOutMybatisDao;

	public EasyuiDatagrid<RptInOutVO> getPagedDatagrid(EasyuiDatagridPager pager, RptInOutQuery query) {
		EasyuiDatagrid<RptInOutVO> datagrid = new EasyuiDatagrid<RptInOutVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		query.setUserid(SfcUserLoginUtil.getLoginUser().getId());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		map.put("customerid", query.getCustomerid());
		map.put("startdate", query.getStartdate());
		map.put("startdatetext", query.getStartdatetext());
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		rptInOutMybatisDao.comReport(map);
		//String result = map.get("result").toString();
		//System.out.println("cs");
		//System.out.println(result);
		List<RptInOut> rptInOutList = rptInOutMybatisDao.queryByPageList(mybatisCriteria);
		RptInOutVO rptInOutVO = null;
		List<RptInOutVO> rptInOutVOList = new ArrayList<RptInOutVO>();
		for (RptInOut rptInOut : rptInOutList) {
			rptInOutVO = new RptInOutVO();
			BeanUtils.copyProperties(rptInOut, rptInOutVO);
			rptInOutVOList.add(rptInOutVO);
		}
		datagrid.setTotal((long) rptInOutMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptInOutVOList);
		return datagrid;
	}

	public Json addRptInOut(RptInOutForm rptInOutForm) throws Exception {
		Json json = new Json();
		RptInOut rptInOut = new RptInOut();
		BeanUtils.copyProperties(rptInOutForm, rptInOut);
		rptInOutMybatisDao.add(rptInOut);
		json.setSuccess(true);
		return json;
	}

	public Json editRptInOut(RptInOutForm rptInOutForm) {
		Json json = new Json();
		RptInOut rptInOut = rptInOutMybatisDao.queryById(rptInOutForm.getUserid());
		BeanUtils.copyProperties(rptInOutForm, rptInOut);
		rptInOutMybatisDao.update(rptInOut);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptInOut(String id) {
		Json json = new Json();
		RptInOut rptInOut = rptInOutMybatisDao.queryById(id);
		if(rptInOut != null){
			rptInOutMybatisDao.delete(rptInOut);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptInOutCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptInOut> rptInOutList = rptInOutMybatisDao.queryListByAll();
		if(rptInOutList != null && rptInOutList.size() > 0){
			for(RptInOut rptInOut : rptInOutList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptInOut.getUserid()));
				combobox.setValue(rptInOut.getUserid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}