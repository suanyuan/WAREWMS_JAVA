package com.wms.mybatis.dao;

import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.result.OrderStatusResult;
import com.wms.result.ReceiptResult;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderHeaderForNormalMybatisDao extends BaseDao {

	List<OrderStatusResult> queryOrderType();

	List<OrderStatusResult> queryOrderStatus();

	List<OrderStatusResult> queryReleaseStatus();

	List<OrderHeaderForNormal> queryByAllocationDetailsId(String orderno);

	List<OrderHeaderForNormal> queryByUnAllocationDetailsId(String orderno);

	//查询所有已完成订单的年份
	List<String> selectALLOrderYear(String edittime);
	//通过时间 模糊查询当天的 发运单数
	String selectOrderByTime(@Param("edittime")String edittime,@Param("carrierLicenseId")String carrierLicenseId);



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


    List<OrderHeaderForNormal> queryByAllocationCustomerid(@Param("customerid") String customerid);
//快递投诉
	public  <T> int updateCourierComplaint(T t);

}
