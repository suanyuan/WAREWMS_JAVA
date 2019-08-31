package com.wms.mybatis.dao;


import com.wms.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspOperateLicenseDao<br>
 */
public interface GspOperateLicenseMybatisDao extends BaseDao {

    public <T> T queryByProductRegisterId(Object id);


    public <T> List<T> queryByListLicenseTime(@Param("licenseId") String licenseId , @Param("licenseType") String licenseType );

}
