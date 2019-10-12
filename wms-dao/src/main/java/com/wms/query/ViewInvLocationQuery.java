package com.wms.query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCustomer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class ViewInvLocationQuery implements IQuery {

	private java.lang.String fmcustomerid; //货主编码
	private java.lang.String fmlocation;   //库位
	private java.math.BigDecimal fmqty;
	private java.lang.String fmsku;        //产品代码
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

	private java.lang.String defaultreceivinguom;
	private  String name;          //产品线
	private  String lotatt04;      //批号
	private  String lotatt05;      //序列号
	private  String lotatt12;      //产品名称
	private  String lotatt14;      //入库单号
	private  Date lotatt02Start;      //失效期查询开始
	private  Date lotatt02End;      //失效期查询结束
	private  Date lotatt03Start;      //入库日期查询开始
	private  Date lotatt03End;      //入库日期查询结束
	private  String lotatt10;      //质量状态

	private Set<SfcCustomer> customerSet;

	public BigDecimal getiMv() {
		return iMv;
	}

	public void setiMv(BigDecimal iMv) {
		this.iMv = iMv;
	}

	public BigDecimal getiPa() {
		return iPa;
	}

	public void setiPa(BigDecimal iPa) {
		this.iPa = iPa;
	}

	public BigDecimal getiRp() {
		return iRp;
	}

	public void setiRp(BigDecimal iRp) {
		this.iRp = iRp;
	}

	public BigDecimal getoMv() {
		return oMv;
	}

	public void setoMv(BigDecimal oMv) {
		this.oMv = oMv;
	}

	public BigDecimal getoRp() {
		return oRp;
	}

	public void setoRp(BigDecimal oRp) {
		this.oRp = oRp;
	}

	public Date getLotatt03Start() {
		return lotatt03Start;
	}

	public void setLotatt03Start(Date lotatt03Start) {
		this.lotatt03Start = lotatt03Start;
	}

	public Date getLotatt03End() {
		return lotatt03End;
	}

	public void setLotatt03End(Date lotatt03End) {
		this.lotatt03End = lotatt03End;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

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

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
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
	
	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}
	
}