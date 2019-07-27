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
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户简称
	 */
	private String customerShortName;
	/**
	 * 客户门店
	 */
	private String customerStore;
	/**
	 * 订单创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderTime;
	/**
	 * 预期到货时间
	 */
	@Temporal(TemporalType.DATE)
	private Date requiredDeliveryTime;
	/**
	 * 客户单号
	 */
	private String orderCode;
	/**
	 * 外部单号
	 */
	private String externalOrderCode;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 订单状态描述
	 */
	private String orderStatusName;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 订单类型描述
	 */
	private String orderTypeName;
	/**
	 * 收货人
	 */
	private String consigneeName;
	/**
	 * 收货人省份
	 */
	private String province;
	/**
	 * 收货人城市
	 */
	private String city;
	/**
	 * 收货人区县
	 */
	private String district;
	/**
	 * 收货人地址
	 */
	private String address;
	/**
	 * 围栏区域
	 */
	private String fenceName;
	/**
	 * 收货人联系方式
	 */
	private String tel;
	/**
	 * 收货人地址邮编
	 */
	private String zipCode;
	/**
	 * 快递
	 */
	private String carrierId;
	/**
	 * 快递名称
	 */
	private String carrierName;
	/**
	 * 发货时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastShipmentTime;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 所属仓库
	 */
	private String warehouseId;
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
	 * 出库复核完成标价
	 */
	private String packingFlag;
	/**
	 * 打印模板
	 */
	private String printTemplate;
	
	private List<OrderDetailsForNormal> orderDetailsForNormalList;
	
	private List<DocOrderPacking> docOrderPackingList;

	public OrderHeaderForNormal() {
		
	}
}