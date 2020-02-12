package com.wms.mybatis.dao;

import com.wms.query.UserStatisticsPerformanceQuery;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>UserStatisticsPerformanceDao<br>
 */
public interface UserStatisticsPerformanceMybatisDao extends BaseDao {

    /**
     * 统计前一天所有人效绩
     * @param
     * @param <T>
     * @return
     */
    public <T> List<T> performanceStatisticsList(UserStatisticsPerformanceQuery query);

}
