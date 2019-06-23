package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.BasCustomer;
import com.wms.entity.BasLocation;
import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>BasLocationDao<br>
 */
public interface BasLocationMybatisDao extends BaseDao {
	
	public List<BasLocation> queryUsgTypeByAll();
	public List<BasLocation> queryCatTypeByAll();
	public List<BasLocation> queryAttTypeByAll();
	public List<BasLocation> queryHdlTypeByAll();
	public List<BasLocation> queryDmdTypeByAll();
	public List<BasLocation> queryPtzoneTypeByAll();
	public List<BasLocation> queryPizoneTypeByAll();
	public String basLocationCheck(Map<String, Object> map);
}
