package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class DocOrderPackingCartonInfo  implements Serializable {

	private String orderno;//出库单号 打印

	private String traceid;

	private Integer cartonno;

	private String cartontype;

	private Double grossweight;

	private Double cube;

	private int packingflag;

	private String addwho;

	private java.util.Date addtime;

	/**
	 * 用于打印
	 */
	public List<DocOrderPackingCarton> detls;


	//收货单位
	private String consigneeid;
	//收货地址
	private String placeofdelivery;
	//客户单号
	private String orderCode;
	//发货日期
	private Date edittime;
	//联系人
	private String cContact;
	//联系电话
	private String bTel1;
	//备注
	private String notes;
	//快递公司
	private String carriercontact;
	//发运方式
	private String userdefine1 ;
	//快递结算方式
	private String userdefine2;


}
