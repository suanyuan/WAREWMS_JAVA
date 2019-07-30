package com.wms.mybatis.dao;


import com.wms.entity.GspReceiving;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>GspReceivingDao<br>
 */
public interface GspReceivingMybatisDao extends BaseDao {
	
	public GspReceiving queryByEnterpriseId(String enterpriseId);

	Long updateFirstState(@Param("no") String no,@Param("state") String state);

	//public GspReceiving queryByEnterprise(Object id);
}
