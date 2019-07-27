package com.wms.query;

import java.util.Set;

import com.wms.mybatis.entity.SfcCustomer;
import lombok.Data;

@Data
public class OrderHeaderForNormalQuery implements IQuery {

	private String orderNo;
	private String customerId;
	private java.util.Date orderStartTime;
	private java.util.Date orderEndTime;
	private String orderCode;
	private String orderStatus;
	private java.util.Date currentTime;
	private String warehouseId;
	private Set<SfcCustomer> customerSet;
}