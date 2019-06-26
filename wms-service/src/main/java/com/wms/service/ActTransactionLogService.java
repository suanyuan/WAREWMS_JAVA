package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.ActTransactionLog;
import com.wms.mybatis.dao.ActTransactionLogMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.ActTransactionLogQuery;
import com.wms.vo.ActTransactionLogVO;
import com.wms.vo.Json;
import com.wms.vo.form.ActTransactionLogForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("actTransactionLogService")
public class ActTransactionLogService extends BaseService {

	@Autowired
	private ActTransactionLogMybatisDao actTransactionLogMybatisDao;

	public EasyuiDatagrid<ActTransactionLogVO> getPagedDatagrid(EasyuiDatagridPager pager, ActTransactionLogQuery query) {
		EasyuiDatagrid<ActTransactionLogVO> datagrid = new EasyuiDatagrid<ActTransactionLogVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		ActTransactionLogVO actTransactionLogVO = null;
		List<ActTransactionLogVO> actTransactionLogVOList = new ArrayList<ActTransactionLogVO>();
		List<ActTransactionLog> actTransactionLogList = actTransactionLogMybatisDao.queryByList(criteria);
		for (ActTransactionLog actTransactionLog : actTransactionLogList) {
			actTransactionLogVO = new ActTransactionLogVO();
			BeanUtils.copyProperties(actTransactionLog, actTransactionLogVO);
			actTransactionLogVOList.add(actTransactionLogVO);
		}
		int total = actTransactionLogMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(actTransactionLogVOList);
		return datagrid;
	}

	public Json addActTransactionLog(ActTransactionLogForm actTransactionLogForm) throws Exception {
		Json json = new Json();
		ActTransactionLog actTransactionLog = new ActTransactionLog();
		BeanUtils.copyProperties(actTransactionLogForm, actTransactionLog);
		actTransactionLogMybatisDao.add(actTransactionLog);
		json.setSuccess(true);
		return json;
	}

	public Json editActTransactionLog(ActTransactionLogForm actTransactionLogForm) {
		Json json = new Json();
		ActTransactionLog actTransactionLog = actTransactionLogMybatisDao.queryById(actTransactionLogForm.getTransactionid());
		BeanUtils.copyProperties(actTransactionLogForm, actTransactionLog);
		actTransactionLogMybatisDao.update(actTransactionLog);
		json.setSuccess(true);
		return json;
	}

	public Json deleteActTransactionLog(String id) {
		Json json = new Json();
		ActTransactionLog actTransactionLog = actTransactionLogMybatisDao.queryById(id);
		if (actTransactionLog != null) {
			actTransactionLogMybatisDao.delete(actTransactionLog);
		}
		json.setSuccess(true);
		return json;
	}



}