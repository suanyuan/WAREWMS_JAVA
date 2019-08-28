package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.UserSessionMybatisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.UserSessionDao;
import com.wms.entity.UserSession;
import com.wms.vo.UserSessionVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.UserSessionForm;
import com.wms.query.UserSessionQuery;

@Service("userSessionService")
public class UserSessionService extends BaseService {

	@Autowired
	private UserSessionMybatisDao userSessionMybatisDao;

	public EasyuiDatagrid<UserSessionVO> getPagedDatagrid(EasyuiDatagridPager pager, UserSessionQuery query) {
//		EasyuiDatagrid<UserSessionVO> datagrid = new EasyuiDatagrid<UserSessionVO>();
//		List<UserSession> userSessionList = userSessionMybatisDao.queryByList(pager, query);
//		UserSessionVO userSessionVO = null;
//		List<UserSessionVO> userSessionVOList = new ArrayList<UserSessionVO>();
//		for (UserSession userSession : userSessionList) {
//			userSessionVO = new UserSessionVO();
//			BeanUtils.copyProperties(userSession, userSessionVO);
//			userSessionVOList.add(userSessionVO);
//		}
//		datagrid.setTotal(userSessionDao.countAll(query));
//		datagrid.setRows(userSessionVOList);
//		return datagrid;
		return null;
	}

	public Json addUserSession(UserSessionForm userSessionForm) throws Exception {
		Json json = new Json();
		UserSession userSession = new UserSession();
		BeanUtils.copyProperties(userSessionForm, userSession);
		userSessionMybatisDao.add(userSession);
		json.setSuccess(true);
		return json;
	}

	public Json editUserSession(UserSessionForm userSessionForm) {
		Json json = new Json();
		UserSession userSession = userSessionMybatisDao.queryById(userSessionForm.getUserSessionId()+"");
		BeanUtils.copyProperties(userSessionForm, userSession);
		userSessionMybatisDao.update(userSession);
		json.setSuccess(true);
		return json;
	}

	public Json deleteUserSession(String id) {
		Json json = new Json();
		UserSession userSession = userSessionMybatisDao.queryById(id);
		if(userSession != null){
			userSessionMybatisDao.delete(userSession);
		}
		json.setSuccess(true);
		return json;
	}
}