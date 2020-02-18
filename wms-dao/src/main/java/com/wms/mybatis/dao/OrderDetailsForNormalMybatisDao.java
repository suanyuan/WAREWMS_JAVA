package com.wms.mybatis.dao;

import com.wms.query.OrderDetailsForNormalQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDetailsForNormalMybatisDao extends BaseDao {

    Integer getOrderLineNoById(OrderDetailsForNormalQuery query);

    void allocationByOrderLine(Map<String, Object> map);

    void deAllocationByOrderLine(Map<String, Object> map);

    /**
     * 用户删除发运订单头档 关联明细删除
     *
     * @param orderno 出口so编号
     */
    void orderHeaderdelete(String orderno);

    /**
     * 根据OrderNo获得细单
     */
    <T> List<T> queryByOrderNo(String orderno);
    <T> List<T> queryByOrderNo1(String orderno);

    /**
     * 查询出库单中是否有需要记录序列号的产品
     */
    int findSerialNumRecordRequired(@Param("orderno") String orderno);

    /**
     * 查询出库单中需要记录序列号的产品的 件数总和
     */
    int sumSerialNumRecordRequired(@Param("orderno") String orderno);

//    <T> List<T> queryBySumList(String orderno);//总查询分页

    <T> T queryBySum(String orderno);
}
