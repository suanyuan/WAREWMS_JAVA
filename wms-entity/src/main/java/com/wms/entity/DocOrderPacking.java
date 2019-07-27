package com.wms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Data
@Entity
public class DocOrderPacking implements Serializable {

	private static final long serialVersionUID = -7632448225174925914L;

	private String traceId;

	private String scanCode;

	private String orderNo;

	private String orderCode;

	private Integer cartonNo;

	private String sku;

	private String unit;

	private String skuName;

	private Integer scanQty;

	private Integer allocationQty;

	private String allocationDetailsId;
	
	private String merchantName;
	
	private String custStore;
	
	private String fenceName;

	private String name;

	private Date requiredDeliveryTime;

	private Integer cartonQty;

	private Double grossWeight;

	private Double cube;

	private String locationId;

	private String address;

	private String addwho;

	private String userId;

	public DocOrderPacking() {
		
	}
}