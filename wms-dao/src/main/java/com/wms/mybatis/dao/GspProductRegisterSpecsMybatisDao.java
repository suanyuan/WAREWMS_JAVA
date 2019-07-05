package com.wms.mybatis.dao;


import java.util.Map;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspProductRegisterSpecsDao<br>
 */
public interface GspProductRegisterSpecsMybatisDao extends BaseDao {

    public String getIdSequence(Map<String, Object> map);

    <T> List<T> queryByListUnBind(MybatisCriteria criteria);

    int queryByCountUnBind(MybatisCriteria criteria);
}
