package com.wms.mybatis.dao;


import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>ProductRegisterRelationDao<br>
 */
public interface ProductRegisterRelationMybatisDao extends BaseDao {
    public void deleteByProductAndregister(@Param("specsId") String specsId, @Param("productRegisterId") String productRegisterId);


    public <T> void updateSelectiveByspecsIdAndproductRegisterId(T t);

}
