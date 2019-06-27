package com.wms.service;

import java.util.List;

import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.GspSecondRecordMybatisDao;
import com.wms.entity.GspSecondRecord;
import com.wms.vo.Json;
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

	public GspSecondRecord getGspSecondRecordBy(GspSecondRecordQuery gspSecondRecordQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspSecondRecordQuery);
		List<GspSecondRecord> list = gspSecondRecordMybatisDao.queryByList(mybatisCriteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void updateGspSecondRecordActiveTag(String id,String tag) {
		GspSecondRecordForm form = new GspSecondRecordForm();
		form.setEnterpriseId(id);
		form.setIsUse(tag);
		gspSecondRecordMybatisDao.updateBySelective(form);
	}
}