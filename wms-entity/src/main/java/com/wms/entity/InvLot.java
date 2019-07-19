package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class InvLot  implements Serializable {

	private String lotnum;

	private String customerid;

	private String sku;

	private Double qty;

	private Double cubic;

	private Double grossweight;

	private Double netweight;

	private Double price;

	private Double qtypreallocated;

	private Double qtyallocated;

	private Double qtyonhold;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

}
