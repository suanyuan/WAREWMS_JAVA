package com.wms.mybatis.dao;


import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocMtHeaderDao<br>
 */
public interface DocMtHeaderMybatisDao extends BaseDao {
//新增MTNO
    String getIdSequence(Map<String, Object> map);
}
