package com.wms.mybatis.dao;



/**
 * 
 * <br>
 * <b>功能：</b>GspInstrumentCatalogDao<br>
 */
public interface GspInstrumentCatalogMybatisDao extends BaseDao {


    public <T> T queryByCPC(Object id);



    public <T> T queryByCPC1(Object id);
}
