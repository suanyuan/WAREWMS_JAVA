package com.wms.mybatis.dao;


import java.util.List;

import com.wms.mybatis.dao.BaseDao;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.SfcWarehouse;
/**
 * 
 * <br>
 * <b>功能：</b>SfcUserDao<br>
 */
public interface SfcUserLoginMybatisDao extends BaseDao {

	public List<SfcWarehouse> queryWarehouseByUser(Object id);
	
}
