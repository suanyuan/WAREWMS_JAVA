package com.wms.mybatis.dao;


import java.util.Map;

import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>RptInOutDao<br>
 */
public interface RptInOutMybatisDao extends BaseDao {
	public String comReport(Map<String, Object> map);
	
}
