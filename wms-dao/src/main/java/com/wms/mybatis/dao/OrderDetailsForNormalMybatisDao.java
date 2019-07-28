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
     * 通过扫码的效期、批号、序列号查询出库明细
     * TODO 可靠性待验证
     * @param query ~
     * @return ~
     */
	List<OrderDetailsForNormal> queryForScan(OrderDetailsForNormalQuery query);
}
