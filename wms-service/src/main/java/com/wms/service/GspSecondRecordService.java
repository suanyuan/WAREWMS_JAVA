package com.wms.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.GspSecondRecordMybatisDao;
import com.wms.entity.GspSecondRecord;
import com.wms.vo.GspSecondRecordVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspSecondRecordForm;
import com.wms.query.GspSecondRecordQuery;

@Service("gspSecondRecordService")
public class GspSecondRecordService extends BaseService {

	@Autowired
	private GspSecondRecordMybatisDao gspSecondRecordMybatisDao;

	public Json addGspSecondRecord(GspSecondRecordForm gspSecondRecordForm) throws Exception {
		Json json = new Json();
		GspSecondRecord gspSecondRecord = new GspSecondRecord();
		BeanUtils.copyProperties(gspSecondRecordForm, gspSecondRecord);
		gspSecondRecordMybatisDao.add(gspSecondRecord);
		json.setSuccess(true);
		return json;
	}

	public Json editGspSecondRecord(GspSecondRecordForm gspSecondRecordForm) {
		Json json = new Json();
		GspSecondRecord gspSecondRecord = gspSecondRecordMybatisDao.queryById(gspSecondRecordForm.getRecordId());
		BeanUtils.copyProperties(gspSecondRecordForm, gspSecondRecord);
		gspSecondRecordMybatisDao.updateBySelective(gspSecondRecord);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspSecondRecord(String id) {
		Json json = new Json();
		GspSecondRecord gspSecondRecord = gspSecondRecordMybatisDao.queryById(id);
		if(gspSecondRecord != null){
			gspSecondRecordMybatisDao.delete(gspSecondRecord);
		}
		json.setSuccess(true);
		return json;
	}

	public GspSecondRecord getGspSecondRecord(String id) {
		GspSecondRecord gspSecondRecord = gspSecondRecordMybatisDao.queryById(id);
		return gspSecondRecord;
	}
}