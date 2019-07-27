package com.wms.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.wms.entity.ExportOrderData;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.query.OrderHeaderForNormalQuery;

public interface OrderHeaderForNormalMybatisDao extends BaseDao {

	List<OrderHeaderForNormal> queryOrderType();

	List<OrderHeaderForNormal> queryOrderStatus();

	List<OrderHeaderForNormal> queryReleaseStatus();

	List<OrderHeaderForNormal> queryByAllocationDetailsId(String orderno);

	List<OrderHeaderForNormal> queryByUnAllocationDetailsId(String orderno);
	
	OrderHeaderForNormal queryByPickingList(OrderHeaderForNormalQuery query);

	OrderHeaderForNormal queryByReceiptList(OrderHeaderForNormalQuery query);

	//List<ExportOrderData> queryByExportList(MybatisCriteria mybatisCriteria);
	
	void getIdSequence(Map<String, Object> map);

	void allocationByOrder(Map<String, Object> map);

	void deAllocationByOrder(Map<String, Object> map);

	void pickingByOrder(Map<String, Object> map);

	void unPickingByOrder(Map<String, Object> map);

	void shipmentByOrder(Map<String, Object> map);

	void cancelByOrder(Map<String, Object> map);

	OrderHeaderForNormal queryPrintTemplate(OrderHeaderForNormalQuery query);
}
