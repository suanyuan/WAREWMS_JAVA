package com.wms.mybatis.dao;

import com.wms.query.OrderDetailsForNormalQuery;

import java.util.Map;

public interface OrderDetailsForNormalMybatisDao extends BaseDao {

	Integer getOrderLineNoById(OrderDetailsForNormalQuery query);

	void allocationByOrderLine(Map<String, Object> map);

	void deAllocationByOrderLine(Map<String, Object> map);
	
	
}
