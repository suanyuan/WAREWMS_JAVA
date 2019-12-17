package com.wms.mybatis.dao;


import com.wms.mybatis.entity.SfcMenu;
import com.wms.query.SfcMenuQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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

	public void deleteMenuBtn(@Param("menuID")String menuID, @Param("roleID")String roleID);

	public void addMenuBtn(@Param("menuID")String menuID, @Param("btnID")String btnID,@Param("roleID")String roleID);
}
