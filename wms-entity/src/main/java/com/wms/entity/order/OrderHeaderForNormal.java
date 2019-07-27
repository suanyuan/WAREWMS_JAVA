package com.wms.entity.order;

import java.io.Serializable;

import javax.persistence.*;

import com.wms.entity.DocOrderPacking;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class OrderHeaderForNormal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -256332372295072959L;
	/**
	 * WMS单号
	 */
	@Id
	private String orderNo;
	/**
	 * 客户编号
	 */
	private String customerId;
	/**
	 * 客户订单号
	 */
	private String soreference1;
	/**
	 * 订单创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderTime;
	/**
	 * 预期发货时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expectedShipmentTime1;
	/**
	 * 预期发货时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expectedShipmentTime2;
	/**
	 * 要求交货时间
	 */
	@Temporal(TemporalType.DATE)
	private Date requiredDeliveryTime;
	/**
	 * 订单优先级
	 */
	private String priority;
	/**
	 * 收货人
	 */
	private String consigneeId;
	/**
	 * 收货人联系人
	 */
	private String cContact;
	/**
	 * 收货人
	 */
	private String consigneeName;
	/**
	 * 收货人地址
	 */
	private String cAddress1;
	private String cAddress2;
	private String cAddress3;
	private String cAddress4;
	/**
	 * 收货人省份
	 */
	private String cProvince;
	/**
	 * 收货人城市
	 */
	private String cCity;
	/**
	 * 收货人国家
	 */
	private String cCountry;
	/**
	 * 邮编
	 */
	private String cZip;
	/**
	 * 传真
	 */
	private String cFax;
	/**
	 * 结算人
	 */
	private String billingId;
	/**
	 * 结算人联系人
	 */
	private String bContact;
	/**
	 * 结算人名称
	 */
	private String billingName;
	/**
	 * 结算人地址
	 */
	private String bAddress1;
	private String bAddress2;
	private String bAddress3;
	private String bAddress4;
	/**
	 * 结算人城市
	 */
	private String bCity;
	/**
	 * 结算人国家
	 */
	private String bCountry;
	/**
	 * 结算人邮编
	 */
	private String bZip;
	/**
	 * 传真
	 */
	private String bFax;
	/**
	 * 邮箱
	 */
	private String bEmail;

	private String bTel1;
	private String bTel2;
	/**
	 * 交货描述
	 */
	private String deliveryTermsDescr;
	/**
	 * 交货条款
	 */
	private String deliveryterms;
	/**
	 * 支付描述
	 */
	private String paymenttermsdescr;
	/**
	 * 支付条款
	 */
	private String paymentterms;
	/**
	 * 发货月台
	 */
	private String door;
	/**
	 * 路径
	 */
	private String route;
	/**
	 * 站点
	 */
	private String stop;
	/**
	 * 订单状态
	 */
	private String sostatus;
	/**
	 * 卸货地点
	 */
	private String placeOfDischarge;
	/**
	 * 交货地点
	 */
	private String placeOfDelivery;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 自定义1
	 */
	private String userDefine1;
	/**
	 * 自定义2
	 */
	private String userDefine2;
	/**
	 * 自定义3
	 */
	private String userDefine3;
	/**
	 * 自定义4
	 */
	private String userDefine4;
	/**
	 * 自定义5
	 */
	private String userDefine5;
	/**
	 * 自定义5
	 */
	private String userDefine6;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 订单创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;
	/**
	 * 订单创建人
	 */
	private String addwho;
	/**
	 * 订单最后修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;
	/**
	 * 订单最后修改人
	 */
	private String editwho;

	private String soreference2;
	private String soreference3;

	/**
	 * 运输
	 */
	private String carrierid;
	private String carriername;
	private String carriercontact;
	private String carriermail;
	private String carrierfax;
	private String carriertel1;
	private String carriertel2;
	private String carrieraddress1;
	private String carrieraddress2;
	private String carrieraddress3;
	private String carrieraddress4;
	private String cMail;
	private String cTel1;
	private String cTel2;

	/**
	 * 运输人省份
	 */
	private String carrierprovince;
	/**
	 * 运输人城市
	 */
	private String carriercity;
	/**
	 * 运输人国家
	 */
	private String carriercountry;
	private String carrierzip;


	private String issuepartyid;
	private String issuepartyname;
	private String iContact;
	private String i_fax;
	private String iTel1;
	private String iTel2;
	private String iAddress1;
	private String iAddress2;
	private String iAddress3;
	private String iAddress4;

	/**
	 * 运输人省份
	 */
	private String iProvince;
	/**
	 * 运输人城市
	 */
	private String iCity;
	/**
	 * 运输人国家
	 */
	private String iCountry;

	private String iZip;

	private String lastshipmenttime;

	private String edisendflag;

	private String pickingPrintFlag;

	private String createsource;

	private String edisendtime;
	private String edisendtime2;
	private String edisendtime3;
	private String edisendtime4;
	private String edisendtime5;

	private String H_EDI_01;
	private String H_EDI_02;
	private String H_EDI_03;
	private String H_EDI_04;
	private String H_EDI_05;
	private String H_EDI_06;
	private String H_EDI_07;
	private String H_EDI_08;
	private String H_EDI_09;
	private String H_EDI_10;
	private String EDISendTime;

	private String zoneGroup;
	/**
	 * 出库复核完成标价
	 */
	private String packingFlag;

	//------------------------------------------

	/**
	 * 客户单号
	 */
	private String orderCode;
	/**
	 * 外部单号
	 */
	private String externalOrderCode;

	/**
	 * 订单状态描述
	 */
	private String orderStatusName;

	/**
	 * 订单类型描述
	 */
	private String orderTypeName;

	/**
	 * 围栏区域
	 */
	private String fenceName;

	/**
	 * 快递
	 */
	private String carrierId;
	/**
	 * 快递名称
	 */
	private String carrierName;

	/**
	 * 所属仓库
	 */
	private String warehouseId;

	/**
	 * 分配ID
	 */
	private String allocationDetailsId;
	/**
	 * 总数量
	 */
	private Integer totalQty;
	/**
	 * 装箱箱数
	 */
	private Integer boxQty;
	/**
	 * 装箱体积
	 */
	private double totalCubic;
	/**
	 * 装箱重量
	 */
	private double totalGrossWeight;
	/**
	 * 总价格
	 */
	private double totalPrice;

	/**
	 * 打印模板
	 */
	private String printTemplate;
	
	private List<OrderDetailsForNormal> orderDetailsForNormalList;
	
	private List<DocOrderPacking> docOrderPackingList;

	public OrderHeaderForNormal() {
		
	}
}