package com.wms.mybatis.dao;


import java.util.Map;

import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>ViewInvLotattDao<br>
 */
public interface ViewInvLotattMybatisDao extends BaseDao {
	public String invAdj(Map<String, Object> map);
	public String invMov(Map<String, Object> map);
	public String invHold(Map<String, Object> map);
}
