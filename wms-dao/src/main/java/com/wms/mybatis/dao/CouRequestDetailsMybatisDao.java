package com.wms.mybatis.dao;


import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>CouRequestDetailsDao<br>
 */
public interface CouRequestDetailsMybatisDao extends BaseDao {

    int getCycleCountlineno(String id);
    //根据单号和查询条件获得细单
    <T> List<T> queryListById(Object id);

}
