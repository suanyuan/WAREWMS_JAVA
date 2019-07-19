package com.wms.mybatis.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>GspCustomerDao<br>
 */
public interface GspCustomerMybatisDao extends BaseDao {
    Long updateFirstState(@Param("no") String no, @Param("state") String state);
	
}
