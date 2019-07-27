package com.wms.entity.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class OrderDetailsForNormal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8082934748203656481L;
	/**
	 * WMS单号
	 */
	@Id
	private String orderNo;
	/**
	 * WMS行号
	 */
	private int orderLineNo;
	/**
	 * 客户编号
	 */
	private String customerId;
	/**
	 * 客户简称
	 */
	private String customerShortName;
	/**
	 * 订单行状态
	 */
	private String lineStatus;
	/**
	 * 订单行状态描述
	 */
	private String lineStatusName;
	/**
	 * 商品编码
	 */
	private String sku;
	/**
	 * 商品名称
	 */
	private String skuName;
	/**
	 * 商品英文名称
	 */
	private String skuEnglishName;
	/**
	 * 推送数量
	 */
	private Integer qtyPlaned;
	/**
	 * 订货数量
	 */
	private Integer qtyOrdered;
	/**
	 * 分配数量
	 */
	private Integer qtyAllocated;
	/**
	 * 出库数量
	 */
	private Integer qtyShipped;
	/**
	 * 总体积
	 */
	private double totalCubic;
	/**
	 * 总重量
	 */
	private double totalGrossWeight;
	/**
	 * 总价格
	 */
	private double totalPrice;
	/**
	 * 拣货区
	 */
	private String pickZone;
	/**
	 * 拣货库位
	 */
	private String locationId;
	/**
	 * 拣货批次
	 */
	private String lotnum;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 生产日期
	 */
	@Temporal(TemporalType.DATE)
	private Date lotatt01;
	/**
	 * 失效日期
	 */
	@Temporal(TemporalType.DATE)
	private Date lotatt02;
	/**
	 * 入库日期
	 */
	@Temporal(TemporalType.DATE)
	private Date lotatt03;
	/**
	 * 生产批号
	 */
	private String lotatt04;
    private String lotatt05;//序列号
    private String lotatt06;//产品注册证
    private String lotatt07;//灭菌批号
    private String lotatt08;//供应商
    private String lotatt09;//样品属性
    private String lotatt10;//质量状态
    private String lotatt11;//储存条件
    private String lotatt12;//产品名称
    private String lotatt13;//双证
    private String lotatt14;//入库单号
    private String lotatt15;//生产厂家
    private String lotatt16;
    private String lotatt17;
    private String lotatt18;

	/**
	 * 出库明细创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;
	/**
	 * 出库明细创建人
	 */
	private String addwho;
	/**
	 * 出库明细最后修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;
	/**
	 * 出库明细最后修改人
	 */
	private String editwho;
	/**
	 * 所属仓库
	 */
	private String warehouseId;
	/**
	 * 分配ID
	 */
	private String allocationDetailsId;
	/**
	 * 操作人
	 */
	private String userId;
	/**
	 * 返回结果
	 */
	private String result;

	public OrderDetailsForNormal() {
		
	}
}