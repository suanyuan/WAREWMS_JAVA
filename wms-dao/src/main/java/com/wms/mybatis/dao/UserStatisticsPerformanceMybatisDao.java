package com.wms.mybatis.dao;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>UserStatisticsPerformanceDao<br>
 */
public interface UserStatisticsPerformanceMybatisDao extends BaseDao {


    public <T> List<T> queryByLists(MybatisCriteria criteria);
}
