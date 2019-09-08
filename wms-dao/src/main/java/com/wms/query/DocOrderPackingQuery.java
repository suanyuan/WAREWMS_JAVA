package com.wms.query;

import lombok.Data;

@Data
public class DocOrderPackingQuery implements IQuery {

	private String orderNo;
	
	private String orderCode;
	
	private Integer cartonNo;
	
	private String sku;
	
	private String skuCode;
	
	private Integer qty;
	
	private String allocationDetailsId;

	private int packlineno;
}