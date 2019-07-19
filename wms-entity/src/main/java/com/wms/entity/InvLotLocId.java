package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class InvLotLocId  implements Serializable {

	private String lotnum;

	private String locationid;

	private String traceid;

	private String customerid;

	private String sku;

	private Double qty;

	private Double qtyallocated;

	private Double qtyrpin;

	private Double qtyrpout;

	private Double qtymvin;

	private Double qtymvout;

	private Double qtyonhold;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private Double netweight;

	private Double grossweight;

	private Double cubic;

	private Double price;

	private Long onholdlocker;

	private String lpn;

	private Double qtypa;

}
