package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.BasLocation;
import com.wms.entity.BasZone;
import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>BasZoneDao<br>
 */
public interface BasZoneMybatisDao extends BaseDao {
	
	public List<BasZone> queryZonegroupByAll();
	public String basZoneCheck(Map<String, Object> map);
}
