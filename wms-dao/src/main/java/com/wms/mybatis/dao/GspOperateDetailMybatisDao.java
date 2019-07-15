package com.wms.mybatis.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>GspOperateDetailDao<br>
 */
public interface GspOperateDetailMybatisDao extends BaseDao {
    Long deleteByLicenseId(@Param("licenseId") String licenseId,@Param("licenseType") String licenseType);
}
