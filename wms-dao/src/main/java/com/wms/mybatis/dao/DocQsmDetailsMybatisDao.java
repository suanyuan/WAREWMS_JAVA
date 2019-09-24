package com.wms.mybatis.dao;


import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocQsmDetailsDao<br>
 */
public interface DocQsmDetailsMybatisDao extends BaseDao {

    void qualityStatus(Map<String, Object> map);
}
