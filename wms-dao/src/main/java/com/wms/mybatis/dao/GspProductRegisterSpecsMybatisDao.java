package com.wms.mybatis.dao;


import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspProductRegisterSpecsDao<br>
 */
public interface GspProductRegisterSpecsMybatisDao extends BaseDao {

    <T> List<T> queryByListUnBind(MybatisCriteria criteria);

    int queryByCountUnBind(MybatisCriteria criteria);
}
