package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.math.BigDecimal;
import java.sql.Date;

public class ViewInvLocationVO {
	private String enterpriseName;
	private String enterpriseId;
	private String defaultreceivinguom;
	private String skuGroup1;
	private String lotnum;

	private String customerid;

	private String sku;
	private String qty;
	private String descrC;

	/**
	 * 规格/型号
	 */
	private String descrE;

	private String lotatt01;

	private String lotatt02;

	private String lotatt03;

	private String lotatt04;

	private String lotatt05;

	private String lotatt06;

	private String lotatt07;

	private String lotatt08;

	private String lotatt09;

	private String lotatt10;

	private String lotatt11;

	private String lotatt12;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private java.sql.Date receivingtime;

	private String qcreportfilename;

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

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public String getSkuGroup1() {
		return skuGroup1;
	}

	public void setSkuGroup1(String skuGroup1) {
		this.skuGroup1 = skuGroup1;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getDescrC() {
		return descrC;
	}

	public void setDescrC(String descrC) {
		this.descrC = descrC;
	}

	public String getDescrE() {
		return descrE;
	}

	public void setDescrE(String descrE) {
		this.descrE = descrE;
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

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
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

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt09() {
		return lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public Date getReceivingtime() {
		return receivingtime;
	}

	public void setReceivingtime(Date receivingtime) {
		this.receivingtime = receivingtime;
	}

	public String getQcreportfilename() {
		return qcreportfilename;
	}

	public void setQcreportfilename(String qcreportfilename) {
		this.qcreportfilename = qcreportfilename;
	}

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