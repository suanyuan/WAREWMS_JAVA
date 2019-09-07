package com.wms.mybatis.dao;


import com.wms.entity.CouRequestDetails;
import com.wms.query.CouRequestDetailsQuery;

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

    /**
     * 查询盘点任务
     * @param query 盘点单号、sku、库位、批号、序列号
     * @return ~
     */
    List<CouRequestDetails> queryCouRequestDetails(CouRequestDetailsQuery query);
}
