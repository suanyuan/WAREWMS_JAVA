package com.wms.mybatis.dao;


import java.util.List;

import com.wms.mybatis.dao.BaseDao;
import com.wms.mybatis.entity.SfcMenu;
import com.wms.query.SfcMenuQuery;
/**
 * 
 * <br>
 * <b>功能：</b>SfcMenuDao<br>
 */
public interface SfcMenuMybatisDao extends BaseDao {

	public SfcMenu queryRoleListById(SfcMenuQuery sfcMenuQuery);

	public void addRoleByMenu(SfcMenu sfcMenu);

	public void deleteRoleByMenu(SfcMenu sfcMenu);

	public List<SfcMenu> queryByParentId(SfcMenuQuery sfcMenuQuery);
	
	
}
