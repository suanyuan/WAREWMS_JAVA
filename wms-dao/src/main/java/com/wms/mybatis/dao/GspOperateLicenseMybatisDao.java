package com.wms.mybatis.dao;


import com.wms.mybatis.dao.BaseDao;

/**
 * 
 * <br>
 * <b>功能：</b>GspOperateLicenseDao<br>
 */
public interface GspOperateLicenseMybatisDao extends BaseDao {

    public <T> T queryByProductRegisterId(Object id);
}
