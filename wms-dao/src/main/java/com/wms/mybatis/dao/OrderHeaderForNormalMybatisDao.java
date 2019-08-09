package com.wms.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.wms.entity.ExportOrderData;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.result.OrderStatusResult;
import com.wms.result.ReceiptResult;
import org.apache.ibatis.annotations.Param;

public interface OrderHeaderForNormalMybatisDao extends BaseDao {

	List<OrderStatusResult> queryOrderType();

	List<OrderStatusResult> queryOrderStatus();

	List<OrderStatusResult> queryReleaseStatus();

	List<OrderHeaderForNormal> queryByAllocationDetailsId(String orderno);

	List<OrderHeaderForNormal> queryByUnAllocationDetailsId(String orderno);

	List<OrderHeaderForNormal> queryPackageList(@Param("start") int start, @Param("pageSize") int pageSize);
	
	OrderHeaderForNormal queryByPickingList(OrderHeaderForNormalQuery query);

	List<ReceiptResult> queryByReceiptList(OrderHeaderForNormalQuery query);

	//List<ExportOrderData> queryByExportList(MybatisCriteria mybatisCriteria);
	
	//void getIdSequence(Map<String, Object> map);

	void allocationByOrder(Map<String, Object> map);

	void deAllocationByOrder(Map<String, Object> map);

	void pickingByOrder(Map<String, Object> map);

	void unPickingByOrder(Map<String, Object> map);

	void shipmentByOrder(Map<String, Object> map);

	void cancelByOrder(Map<String, Object> map);

	int updateSOTask(OrderHeaderForNormal orderHeaderForNormal);

	//OrderHeaderForNormal queryPrintTemplate(OrderHeaderForNormalQuery query);
}
