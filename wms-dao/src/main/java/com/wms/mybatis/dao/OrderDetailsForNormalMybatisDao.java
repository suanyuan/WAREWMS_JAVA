package com.wms.mybatis.dao;

import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.query.OrderDetailsForNormalQuery;

import java.util.List;
import java.util.Map;

public interface OrderDetailsForNormalMybatisDao extends BaseDao {

	Integer getOrderLineNoById(OrderDetailsForNormalQuery query);

	void allocationByOrderLine(Map<String, Object> map);

	void deAllocationByOrderLine(Map<String, Object> map);

    /**
     * 用户删除发运订单头档 关联明细删除
     * @param orderno 出口so编号
     */
	void orderHeaderdelete (String orderno);
}
