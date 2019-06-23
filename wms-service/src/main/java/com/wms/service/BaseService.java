package com.wms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.wms.mybatis.dao.SfcRoleMybatisDao;
import com.wms.mybatis.dao.SfcUserMybatisDao;
import com.wms.mybatis.entity.SfcBtn;
import com.wms.mybatis.entity.SfcRole;
import com.wms.mybatis.entity.SfcUser;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.SfcRoleQuery;
import com.wms.vo.Json;

/**
 * 	基礎Service
 * 	@author OwenHuang
 *	@Date 2012/6/18
 */
@Service("baseService")
public class BaseService {
	@Autowired
	private SfcUserMybatisDao sfcUserMybatisDao;
	@Autowired
	private SfcRoleMybatisDao sfcRoleMybatisDao;
	
	@Cacheable(value = "btnCache", key = "#sfcUserLogin.id+#menuId")
	public Json getBtn(String menuId, SfcUserLogin sfcUserLogin) {
		Json json = new Json();
		StringBuilder sb = new StringBuilder();
		if(sfcUserLogin != null){
			SfcUser sfcUser = sfcUserMybatisDao.queryListById(sfcUserLogin);
			for(SfcRole sfcRole : sfcUser.getRoleSet()){
				SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
				sfcRoleQuery.setId(sfcRole.getId());
				SfcRole subRole = sfcRoleMybatisDao.queryBtnListById(sfcRoleQuery);
				for(SfcBtn sfcBtn : subRole.getBtnSet()){
					sb.append(sfcBtn.getBtnName()).append(",");
				}
			}
			
			if(sb.length() > 0 && sb.lastIndexOf(",") > -1){
				sb.deleteCharAt(sb.lastIndexOf(","));
			}
			json.setSuccess(true);
			json.setObj(sb.toString());
		}else{
			json.setSuccess(false);
		}
		return json;
	}
}
