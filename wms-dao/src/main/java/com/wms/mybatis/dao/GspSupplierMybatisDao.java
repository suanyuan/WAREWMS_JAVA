package com.wms.mybatis.dao;


import com.wms.entity.GspSupplier;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>GspSupplierDao<br>
 */
public interface GspSupplierMybatisDao extends BaseDao {
    public void deleteNotUse(Object id);

    Long updateFirstState(@Param("no") String no, @Param("state") String state);


    public <T> T queryByEnterpriseId(Object id);

    Integer queryGspSupplierByEnterpriseId(GspSupplier gspSupplier);


}
