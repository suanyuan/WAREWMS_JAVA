package com.wms.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspReceivingDao;
import com.wms.entity.GspReceiving;
import com.wms.vo.GspReceivingVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspReceivingForm;
import com.wms.query.GspReceivingQuery;

@Service("gspReceivingService")
public class GspReceivingService extends BaseService {

	@Autowired
	private GspReceivingDao gspReceivingDao;

	public EasyuiDatagrid<GspReceivingVO> getPagedDatagrid(EasyuiDatagridPager pager, GspReceivingQuery query) {
		EasyuiDatagrid<GspReceivingVO> datagrid = new EasyuiDatagrid<GspReceivingVO>();
		List<GspReceiving> gspReceivingList = gspReceivingDao.getPagedDatagrid(pager, query);
		GspReceivingVO gspReceivingVO = null;
		List<GspReceivingVO> gspReceivingVOList = new ArrayList<GspReceivingVO>();
		for (GspReceiving gspReceiving : gspReceivingList) {
			gspReceivingVO = new GspReceivingVO();
			BeanUtils.copyProperties(gspReceiving, gspReceivingVO);
			gspReceivingVOList.add(gspReceivingVO);
		}
		datagrid.setTotal(gspReceivingDao.countAll(query));
		datagrid.setRows(gspReceivingVOList);
		return datagrid;
	}

	public Json addGspReceiving(GspReceivingForm gspReceivingForm) throws Exception {
		Json json = new Json();
		GspReceiving gspReceiving = new GspReceiving();
		BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
		gspReceivingDao.save(gspReceiving);
		json.setSuccess(true);
		return json;
	}

	public Json editGspReceiving(GspReceivingForm gspReceivingForm) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingDao.findById(gspReceivingForm.getReceivingId());
		BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
		gspReceivingDao.update(gspReceiving);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspReceiving(String id) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingDao.findById(id);
		if(gspReceiving != null){
			gspReceivingDao.delete(gspReceiving);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspReceivingCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspReceiving> gspReceivingList = gspReceivingDao.findAll();
		if(gspReceivingList != null && gspReceivingList.size() > 0){
			for(GspReceiving gspReceiving : gspReceivingList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspReceiving.getReceivingId()));
				combobox.setValue(gspReceiving.getSupplierId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}