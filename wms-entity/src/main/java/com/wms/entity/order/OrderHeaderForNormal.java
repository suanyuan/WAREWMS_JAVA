package com.wms.entity.order;

import com.wms.entity.DocOrderPacking;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
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
	private String orderno;
	/**
	 * 客户编号
	 */
	private String customerid;
	/**
	 * 客户订单号
	 */
	private String soreference1;
	private String soreference2;
	private String soreference3;
	private String soreference4;
	private String soreference5;
	private String soreference6;
	private String releasestatus;

	/**
	 * 订单创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date ordertime;
	/**
	 * 预期发货时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expectedshipmenttime1;
	/**
	 * 预期发货时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expectedshipmenttime2;
	/**
	 * 要求交货时间
	 */
	@Temporal(TemporalType.DATE)
	private Date requireddeliverytime;
	/**
	 * 订单优先级
	 */
	private String priority;
	/**
	 * 收货人
	 */
	private String consigneeid;
	/**
	 * 收货人联系人
	 */
	private String cContact;
	/**
	 * 收货人
	 */
	private String consigneename;
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
	private String billingid;
	/**
	 * 结算人联系人
	 */
	private String bContact;
	/**
	 * 结算人名称
	 */
	private String billingname;
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

	private String bProvince;
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
	private String deliverytermsdescr;
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
	private String placeofdischarge;
	/**
	 * 交货地点
	 */
	private String placeofdelivery;
	/**
	 * 订单类型
	 */
	private String ordertype;
	/**
	 * 自定义1
	 */
	private String userdefine1;
	/**
	 * 自定义2
	 */
	private String userdefine2;
	/**
	 * 自定义3
	 */
	private String userdefine3;
	/**
	 * 自定义4
	 */
	private String userdefine4;
	/**
	 * 自定义5
	 */
	private String userdefine5;
	/**
	 * 自定义5
	 */
	private String userdefine6;
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

	private String iMail;

	private String iFax;

	private String lastshipmenttime;

	private String edisendflag;

	private String pickingPrintFlag;

	private String createsource;

	private String edisendtime;
	private String edisendtime2;
	private String edisendtime3;
	private String edisendtime4;
	private String edisendtime5;

	private String hEdi01;
	private String hEdi02;
	private String hEdi03;
	private String hEdi04;
	private String hEdi05;
	private String hEdi06;
	private String hEdi07;
	private String hEdi08;
	private String hEdi09;
	private String hEdi10;
	private String hEdi11;
	private String hEdi12;
	private String hEdi13;
	private String hEdi14;
	private String hEdi15;

	private String EDISendTime;

	private String zonegroup;
	/**
	 * 出库复核完成标价
	 */
	private String packingFlag;
	/**
	 * 运输
	 */
	private String transportation;

	private String orderPrintFlag;

	private String rfgettask;
	private String warehouseid;
	private String medicalxmltime;
	private String erpcancelflag;
	private String placeofloading;
	private String requiredeliveryno;
	private String singlematch;
	private String serialnocatch;
	private String followup;
	private String userdefinea;
	private String userdefineb;
	private String salesorderno;
	private String invoiceprintflag;
	private String invoiceno;
	private String invoicetitle;

	private String invoicetype;

	private String invoiceitem;

	private Double invoiceamount;

	private String archiveflag;

	private String consigneenameE;

	private String puttolocation;

	private String fulAlc;

	private String deliveryno;

	private String channel;

	private String waveno;

	private Double allocationcount;

	private String expressprintflag;

	private String deliverynoteprintflag;

	private String weightingflag;

	private String udfprintflag1;

	private String udfprintflag2;

	private String udfprintflag3;

	private String edisendflag3;

	private String edisendflag2;

	private String signDay;


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
//	private String carrierId;
	/**
	 * 快递名称
	 */
	private String carrierName;

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
	private String printmen;//收货方
	private String endmode;//快递结算方式
	private String hedi01pdf;//发货单号
	private String descrc;//供应商
	private List<OrderDetailsForNormal> orderDetailsForNormalList;
	private List<DocOrderPacking> docOrderPackingList;
	private String reservedfield07;//冷链标志
	private String detailsNum;//明细总条数
	private Double qtypickedNum;//拣货总件数

//	导出

	private String excaddress1;   //地址
	private String exctel1;       //联系电话


//快递投诉
private String courierComplaint;

	public OrderHeaderForNormal() {
		
	}
}