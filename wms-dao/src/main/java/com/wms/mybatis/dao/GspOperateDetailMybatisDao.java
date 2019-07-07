package com.wms.mybatis.dao;

/**
 * 
 * <br>
 * <b>功能：</b>GspOperateDetailDao<br>
 */
public interface GspOperateDetailMybatisDao extends BaseDao {
    Long deleteByLicenseId(String licenseId,String licenseType);
}
