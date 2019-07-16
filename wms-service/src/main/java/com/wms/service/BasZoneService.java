package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.BasZoneDao;
import com.wms.entity.BasLocation;
import com.wms.entity.BasZone;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.LoginUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasZoneVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasZoneForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasLocationQuery;
import com.wms.query.BasZoneQuery;
import com.wms.mybatis.dao.BasZoneMybatisDao;

@Service("basZoneService")
public class BasZoneService extends BaseService {

	@Autowired
	private BasZoneDao basZoneDao;
	
	@Autowired
	private BasZoneMybatisDao basZoneMybatisDao;

	public EasyuiDatagrid<BasZoneVO> getPagedDatagrid(EasyuiDatagridPager pager, BasZoneQuery query) {
		EasyuiDatagrid<BasZoneVO> datagrid = new EasyuiDatagrid<BasZoneVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<BasZone> basZoneList = basZoneMybatisDao.queryByPageList(mybatisCriteria);
		BasZoneVO basZoneVO = null;
		List<BasZoneVO> basZoneVOList = new ArrayList<BasZoneVO>();
		for (BasZone basZone : basZoneList) {
			basZoneVO = new BasZoneVO();
			BeanUtils.copyProperties(basZone, basZoneVO);
			basZoneVOList.add(basZoneVO);
		}
		datagrid.setTotal((long) basZoneMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basZoneVOList);
		return datagrid;
	}

	public Json addBasZone(BasZoneForm basZoneForm) throws Exception {
		Json json = new Json();
		Date today = new Date();
		BasZone basZone = new BasZone();
		StringBuilder resultMsg = new StringBuilder();
		BeanUtils.copyProperties(basZoneForm, basZone);
		//System.out.println("112222222222");
		
		this.validateZone(basZoneForm, resultMsg);// 验证库区是否存在

		if (resultMsg.length() == 0) {
			
			 basZone.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			 basZone.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			 basZone.setAddtime(today);
			 basZone.setEdittime(today);
			 //System.out.println("112222222222");
			 basZoneMybatisDao.add(basZone);
			 //System.out.println("222222222222");
		}else {
			json.setSuccess(false);
			json.setMsg(resultMsg.toString());
			return json;
		}
		
		json.setSuccess(true);
		json.setMsg("操作成功");
		return json;
	}
		
		private void validateZone(BasZoneForm basZoneForm, StringBuilder resultMsg) {
			BasZone zone = null;
			BasZoneQuery zoneQuery = new BasZoneQuery();
			zoneQuery.setZone(basZoneForm.getZone());
			zone = basZoneMybatisDao.queryById(zoneQuery);
			if(zone != null){
				resultMsg.append("库区：").append(basZoneForm.getZone())
						 .append("，重复").append(" ");
			}
		}

	public Json editBasZone(BasZoneForm basZoneForm) {
		Json json = new Json();
		Date today = new Date();
		BasZoneQuery zoneQuery = new BasZoneQuery();
		zoneQuery.setZone(basZoneForm.getZone());
		BasZone basZone = basZoneMybatisDao.queryById(zoneQuery);
		BeanUtils.copyProperties(basZoneForm, basZone);
		//System.out.println("222222222222");
		
		basZone.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basZone.setEdittime(today);
		basZoneMybatisDao.update(basZone);
		json.setSuccess(true);
		json.setMsg("操作成功");
		return json;
	}

	public Json deleteBasZone(String zone) {
		Json json = new Json();
		BasZoneQuery zoneQuery = new BasZoneQuery();
		zoneQuery.setZone(zone);
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("zone", zone);
		map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
		BasZone basZone = basZoneMybatisDao.queryById(zoneQuery);
		if(basZone != null){
			//TODO 判断库区能否删除
			//basZoneMybatisDao.basZoneCheck(map);
			//String result = map.get("result").toString();
			//if (result.equals("000")) {
				basZoneMybatisDao.delete(basZone);
			//} else {
			//	json.setSuccess(false);
			//	json.setMsg(result);
			//	return json;
			//}
		}
		json.setMsg("操作成功");
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBasZoneCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasZone> basZoneList = basZoneMybatisDao.queryByAll();
		if(basZoneList != null && basZoneList.size() > 0){
			for(BasZone basZone : basZoneList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(basZone.getZone()));
				combobox.setValue(basZone.getZone());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	
	public List<EasyuiCombobox> getZonegroupCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasZone> basZoneList = basZoneMybatisDao.queryZonegroupByAll();
		if(basZoneList != null && basZoneList.size() > 0){
			for(BasZone basZone : basZoneList){
				combobox = new EasyuiCombobox();
				combobox.setId(basZone.getZonegroup());
				combobox.setValue(basZone.getZonegroupName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
}