package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.SfcUserLoginUtil;
import jdk.nashorn.internal.scripts.JO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.FirstReviewLogMybatisDao;
import com.wms.entity.FirstReviewLog;
import com.wms.vo.FirstReviewLogVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstReviewLogForm;
import com.wms.query.FirstReviewLogQuery;

@Service("firstReviewLogService")
public class FirstReviewLogService extends BaseService {

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;

	public EasyuiDatagrid<FirstReviewLogVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstReviewLogQuery query) {
		EasyuiDatagrid<FirstReviewLogVO> datagrid = new EasyuiDatagrid<FirstReviewLogVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		List<FirstReviewLog> firstReviewLogList = firstReviewLogMybatisDao.queryByList(criteria);
		FirstReviewLogVO firstReviewLogVO = null;
		List<FirstReviewLogVO> firstReviewLogVOList = new ArrayList<FirstReviewLogVO>();
		for (FirstReviewLog firstReviewLog : firstReviewLogList) {
			firstReviewLogVO = new FirstReviewLogVO();
			BeanUtils.copyProperties(firstReviewLog, firstReviewLogVO);
			firstReviewLogVOList.add(firstReviewLogVO);
		}
		int count = firstReviewLogMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(firstReviewLogVOList);
		return datagrid;
	}

	public Json addFirstReviewLog(FirstReviewLogForm firstReviewLogForm) throws Exception {
		Json json = new Json();
		FirstReviewLog firstReviewLog = new FirstReviewLog();
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.add(firstReviewLog);
		json.setSuccess(true);
		return json;
	}

	public Json editFirstReviewLog(FirstReviewLogForm firstReviewLogForm) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(firstReviewLogForm.getReviewId());
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.update(firstReviewLog);
		json.setSuccess(true);
		return json;
	}

	public Json deleteFirstReviewLog(String id) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(id);
		if(firstReviewLog != null){
			firstReviewLogMybatisDao.delete(firstReviewLog);
		}
		json.setSuccess(true);
		return json;
	}

	public Json checkFirstReview(String id,String remark){
		Json json = new Json();
		FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(id);
		FirstReviewLog updateLog = new FirstReviewLog();
		if(StringUtils.isEmpty(firstReviewLog.getCheckIdQc())){
			updateLog.setCheckIdQc(SfcUserLoginUtil.getLoginUser().getId());
			updateLog.setCheckDateQc(new Date());
			updateLog.setCheckRemarkQc(remark);
		}else{
			updateLog.setCheckIdHead(SfcUserLoginUtil.getLoginUser().getId());
			updateLog.setCheckDateHead(new Date());
			updateLog.setCheckRemarkHead(remark);
		}
		updateLog.setReviewId(id);
		firstReviewLogMybatisDao.updateBySelective(updateLog);
		return json;
	}
}