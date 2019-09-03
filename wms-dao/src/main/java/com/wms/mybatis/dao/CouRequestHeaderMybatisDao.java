package com.wms.mybatis.dao;


import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>CouRequestHeaderDao<br>
 */
public interface CouRequestHeaderMybatisDao extends BaseDao {
//新增单号
    String getIdSequence(Map<String, Object> map);
}
