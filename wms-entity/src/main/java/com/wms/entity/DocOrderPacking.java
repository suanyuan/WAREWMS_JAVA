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

	private String batchNum;//批号 add by Gizmo 2019-8-16

    private Integer differentQty;//同批号的包装件数和分配件数的相差数 add by Gizmo 2019-8-16

	public DocOrderPacking() {
		
	}
}