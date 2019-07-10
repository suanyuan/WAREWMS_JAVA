package com.wms.mybatis.dao;

/**
 * 
 * <br>
 * <b>功能：</b>GspBusinessLicenseDao<br>
 */
public interface GspBusinessLicenseMybatisDao extends BaseDao {

    void updateGspBusinessLicenseActiveTag(String enterpriseId,String tag);

}
