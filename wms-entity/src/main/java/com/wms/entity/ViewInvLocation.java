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