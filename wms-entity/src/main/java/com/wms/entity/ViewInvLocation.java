package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the VIEW_INV_LOCATION database table.
 * 
 */
@Entity
@Table(name="VIEW_INV_LOCATION")
@NamedQuery(name="ViewInvLocation.findAll", query="SELECT v FROM ViewInvLocation v")
public class ViewInvLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fmcustomerid;

	private String fmlocation;

	private BigDecimal fmqty;
	
	@Id
	private String fmsku;

	@Column(name="FMUOM_NAME")
	private String fmuomName;

	@Column(name="I_MV")
	private BigDecimal iMv;

	@Column(name="I_PA")
	private BigDecimal iPa;

	@Column(name="I_RP")
	private BigDecimal iRp;

	@Column(name="O_MV")
	private BigDecimal oMv;

	@Column(name="O_RP")
	private BigDecimal oRp;

	private BigDecimal qtyallocated;

	private BigDecimal qtyavailed;

	private BigDecimal qtyholded;

	private String skudescrc;

	private String skudescre;

	private BigDecimal totalcubic;

	private BigDecimal totalgrossweight;

	private String warehouseid;
	private String lotatt14;
	private String customerid;
	private String lotatt03;
	private String lotatt12;
	private String lotatt04;
	private String lotatt05;
	private String lotatt07;
	private String lotatt01;
	private String lotatt02;
	private String lotatt08;
	private String lotatt11;
	private String lotatt10;
	private String name;
	private String productRegisterNo;
	private String enterpriseName;

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductRegisterNo() {
		return productRegisterNo;
	}

	public void setProductRegisterNo(String productRegisterNo) {
		this.productRegisterNo = productRegisterNo;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public ViewInvLocation() {
	}

	public String getFmcustomerid() {
		return this.fmcustomerid;
	}

	public void setFmcustomerid(String fmcustomerid) {
		this.fmcustomerid = fmcustomerid;
	}

	public String getFmlocation() {
		return this.fmlocation;
	}

	public void setFmlocation(String fmlocation) {
		this.fmlocation = fmlocation;
	}

	public BigDecimal getFmqty() {
		return this.fmqty;
	}

	public void setFmqty(BigDecimal fmqty) {
		this.fmqty = fmqty;
	}

	public String getFmsku() {
		return this.fmsku;
	}

	public void setFmsku(String fmsku) {
		this.fmsku = fmsku;
	}

	public String getFmuomName() {
		return this.fmuomName;
	}

	public void setFmuomName(String fmuomName) {
		this.fmuomName = fmuomName;
	}

	public BigDecimal getIMv() {
		return this.iMv;
	}

	public void setIMv(BigDecimal iMv) {
		this.iMv = iMv;
	}

	public BigDecimal getIPa() {
		return this.iPa;
	}

	public void setIPa(BigDecimal iPa) {
		this.iPa = iPa;
	}

	public BigDecimal getIRp() {
		return this.iRp;
	}

	public void setIRp(BigDecimal iRp) {
		this.iRp = iRp;
	}

	public BigDecimal getOMv() {
		return this.oMv;
	}

	public void setOMv(BigDecimal oMv) {
		this.oMv = oMv;
	}

	public BigDecimal getORp() {
		return this.oRp;
	}

	public void setORp(BigDecimal oRp) {
		this.oRp = oRp;
	}

	public BigDecimal getQtyallocated() {
		return this.qtyallocated;
	}

	public void setQtyallocated(BigDecimal qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public BigDecimal getQtyavailed() {
		return this.qtyavailed;
	}

	public void setQtyavailed(BigDecimal qtyavailed) {
		this.qtyavailed = qtyavailed;
	}

	public BigDecimal getQtyholded() {
		return this.qtyholded;
	}

	public void setQtyholded(BigDecimal qtyholded) {
		this.qtyholded = qtyholded;
	}

	public String getSkudescrc() {
		return this.skudescrc;
	}

	public void setSkudescrc(String skudescrc) {
		this.skudescrc = skudescrc;
	}

	public String getSkudescre() {
		return this.skudescre;
	}

	public void setSkudescre(String skudescre) {
		this.skudescre = skudescre;
	}

	public BigDecimal getTotalcubic() {
		return this.totalcubic;
	}

	public void setTotalcubic(BigDecimal totalcubic) {
		this.totalcubic = totalcubic;
	}

	public BigDecimal getTotalgrossweight() {
		return this.totalgrossweight;
	}

	public void setTotalgrossweight(BigDecimal totalgrossweight) {
		this.totalgrossweight = totalgrossweight;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}