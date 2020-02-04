package com.wms.mybatis.dao;


import com.wms.entity.WaybillStatistics;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>WaybillStatisticsDao<br>
 */
public interface WaybillStatisticsMybatisDao extends BaseDao {

    //通过年份查询发运单数
    public <T> List<T> queryByYear(String year);

    //通过年月日查询具体数据
    public <T> T selectByTimeAndCarrier(WaybillStatistics waybillStatistics);


}
