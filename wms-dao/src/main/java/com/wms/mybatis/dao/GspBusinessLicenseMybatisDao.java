package com.wms.mybatis.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>GspBusinessLicenseDao<br>
 */
public interface GspBusinessLicenseMybatisDao extends BaseDao {

    void updateGspBusinessLicenseActiveTag(@Param("enterpriseId") String enterpriseId,@Param("tag") String tag);

}
