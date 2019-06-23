package com.wms.mybatis.dao;


import java.util.List;

import com.wms.mybatis.dao.BaseDao;
import com.wms.mybatis.entity.SfcRole;
import com.wms.query.SfcRoleQuery;
/**
 * 
 * <br>
 * <b>功能：</b>SfcRoleDao<br>
 */
public interface SfcRoleMybatisDao extends BaseDao {

	public SfcRole queryBtnListById(SfcRoleQuery sfcRoleQuery);
	
	public SfcRole queryMenuListById(SfcRoleQuery sfcRoleQuery);

	public SfcRole queryUniqueIdByName(SfcRoleQuery sfcRoleQuery);

	public Long queryByCount();

	public List<SfcRole> queryRoleListByAll();

	public void addBtnByRole(SfcRole sfcRole);

	public void deleteBtnByRole(SfcRole sfcRole);

}
