package com.wms.mybatis.dao;


import java.util.Map;

import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>ViewInvHoldDao<br>
 */
public interface ViewInvHoldMybatisDao extends BaseDao {
	
	public String invHold(Map<String, Object> map);
	public String invRelease(Map<String, Object> map);
}
