package com.wms.mybatis.dao;


import com.wms.entity.GspReceiving;

/**
 * 
 * <br>
 * <b>功能：</b>GspReceivingDao<br>
 */
public interface GspReceivingMybatisDao extends BaseDao {
	
	public GspReceiving queryByEnterpriseId(String enterpriseId);
}
