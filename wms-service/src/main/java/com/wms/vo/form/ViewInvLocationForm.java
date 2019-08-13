package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class ViewInvLocationForm {

	private java.lang.String fmcustomerid;
	private java.lang.String fmlocation;
	private java.math.BigDecimal fmqty;
	private java.lang.String fmsku;
	private java.lang.String fmuomName;
	private java.math.BigDecimal iMv;
	private java.math.BigDecimal iPa;
	private java.math.BigDecimal iRp;
	private java.math.BigDecimal oMv;
	private java.math.BigDecimal oRp;
	private java.math.BigDecimal qtyallocated;
	private java.math.BigDecimal qtyavailed;
	private java.math.BigDecimal qtyholded;
	private java.lang.String skudescrc;
	private java.lang.String skudescre;
	private java.math.BigDecimal totalcubic;
	private java.math.BigDecimal totalgrossweight;
	private java.lang.String warehouseid;
	private java.lang.String name;
	private java.lang.String lotatt04;//批号
	private java.lang.String lotatt05;//序列号
	private java.lang.String lotatt12;//产品名称
	private Date lotatt02Start;      //失效期查询开始
	private  Date lotatt02End;      //失效期查询结束

	public Date getLotatt02Start() {
		return lotatt02Start;
	}

	public void setLotatt02Start(Date lotatt02Start) {
		this.lotatt02Start = lotatt02Start;
	}

	public Date getLotatt02End() {
		return lotatt02End;
	}

	public void setLotatt02End(Date lotatt02End) {
		this.lotatt02End = lotatt02End;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.lang.String getFmcustomerid() {
		return fmcustomerid;
	}

	public void setFmcustomerid(java.lang.String fmcustomerid) {
		this.fmcustomerid = fmcustomerid;
	}

	public java.lang.String getFmlocation() {
		return fmlocation;
	}

	public void setFmlocation(java.lang.String fmlocation) {
		this.fmlocation = fmlocation;
	}

	public java.math.BigDecimal getFmqty() {
		return fmqty;
	}

	public void setFmqty(java.math.BigDecimal fmqty) {
		this.fmqty = fmqty;
	}

	public java.lang.String getFmsku() {
		return fmsku;
	}

	public void setFmsku(java.lang.String fmsku) {
		this.fmsku = fmsku;
	}

	public java.lang.String getFmuomName() {
		return fmuomName;
	}

	public void setFmuomName(java.lang.String fmuomName) {
		this.fmuomName = fmuomName;
	}

	public java.math.BigDecimal getIMv() {
		return iMv;
	}

	public void setIMv(java.math.BigDecimal iMv) {
		this.iMv = iMv;
	}

	public java.math.BigDecimal getIPa() {
		return iPa;
	}

	public void setIPa(java.math.BigDecimal iPa) {
		this.iPa = iPa;
	}

	public java.math.BigDecimal getIRp() {
		return iRp;
	}

	public void setIRp(java.math.BigDecimal iRp) {
		this.iRp = iRp;
	}

	public java.math.BigDecimal getOMv() {
		return oMv;
	}

	public void setOMv(java.math.BigDecimal oMv) {
		this.oMv = oMv;
	}

	public java.math.BigDecimal getORp() {
		return oRp;
	}

	public void setORp(java.math.BigDecimal oRp) {
		this.oRp = oRp;
	}

	public java.math.BigDecimal getQtyallocated() {
		return qtyallocated;
	}

	public void setQtyallocated(java.math.BigDecimal qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public java.math.BigDecimal getQtyavailed() {
		return qtyavailed;
	}

	public void setQtyavailed(java.math.BigDecimal qtyavailed) {
		this.qtyavailed = qtyavailed;
	}

	public java.math.BigDecimal getQtyholded() {
		return qtyholded;
	}

	public void setQtyholded(java.math.BigDecimal qtyholded) {
		this.qtyholded = qtyholded;
	}

	public java.lang.String getSkudescrc() {
		return skudescrc;
	}

	public void setSkudescrc(java.lang.String skudescrc) {
		this.skudescrc = skudescrc;
	}

	public java.lang.String getSkudescre() {
		return skudescre;
	}

	public void setSkudescre(java.lang.String skudescre) {
		this.skudescre = skudescre;
	}

	public java.math.BigDecimal getTotalcubic() {
		return totalcubic;
	}

	public void setTotalcubic(java.math.BigDecimal totalcubic) {
		this.totalcubic = totalcubic;
	}

	public java.math.BigDecimal getTotalgrossweight() {
		return totalgrossweight;
	}

	public void setTotalgrossweight(java.math.BigDecimal totalgrossweight) {
		this.totalgrossweight = totalgrossweight;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}

}