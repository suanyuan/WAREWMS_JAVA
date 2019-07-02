package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOC_ASN_DETAILS database table.
 * 
 */
@Entity
@Table(name="DOC_ASN_DETAILS")
@NamedQuery(name="DocAsnDetail.findAll", query="SELECT d FROM DocAsnDetail d")
public class DocAsnDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String warehouseid;
	private String result;

	@Id
	private String asnno;

	private long asnlineno;
	
	@Temporal(TemporalType.DATE)
	private Date addtime;

	private String addwho;

	@Column(name="ALTERNATIVEDESCR_C")
	private String alternativedescrC;

	private String alternativesku;

	private String asnlinefilter;

	private String containerid;

	private String createsource;

	private String customerid;

	@Column(name="D_EDI_01")
	private String dEdi01;

	@Column(name="D_EDI_02")
	private String dEdi02;

	@Column(name="D_EDI_03")
	private String dEdi03;

	@Column(name="D_EDI_04")
	private String dEdi04;

	@Column(name="D_EDI_05")
	private String dEdi05;

	@Column(name="D_EDI_06")
	private String dEdi06;

	@Column(name="D_EDI_07")
	private String dEdi07;

	@Column(name="D_EDI_08")
	private String dEdi08;

	@Column(name="D_EDI_09")
	private BigDecimal dEdi09;

	@Column(name="D_EDI_10")
	private BigDecimal dEdi10;

	@Column(name="D_EDI_11")
	private String dEdi11;

	@Column(name="D_EDI_12")
	private String dEdi12;

	@Column(name="D_EDI_13")
	private String dEdi13;

	@Column(name="D_EDI_14")
	private String dEdi14;

	@Column(name="D_EDI_15")
	private String dEdi15;

	@Column(name="D_EDI_16")
	private String dEdi16;

	@Column(name="D_EDI_17")
	private String dEdi17;

	@Column(name="D_EDI_18")
	private String dEdi18;

	@Column(name="D_EDI_19")
	private String dEdi19;

	@Column(name="D_EDI_20")
	private String dEdi20;

	@Column(name="DAMAGEDQTY_EACH")
	private BigDecimal damagedqtyEach;

	@Temporal(TemporalType.DATE)
	private Date edittime;

	private String editwho;

	private BigDecimal expectedqty;

	@Column(name="EXPECTEDQTY_EACH")
	private BigDecimal expectedqtyEach;

	private String holdrejectcode;

	private String holdrejectreason;

	private String linestatus;
	private String linestatusName;

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

	private String notes;

	private String operator;

	private BigDecimal overrcvpercentage;

	private String packid;

	private String palletizemethod;

	@Column(name="PALLETIZEQTY_EACH")
	private BigDecimal palletizeqtyEach;

	private String plantoloc;

	private BigDecimal polineno;

	private String pono;

	@Column(name="PRERECEIVEDQTY_EACH")
	private BigDecimal prereceivedqtyEach;

	private String printlabel;

	private String productstatus;

	@Column(name="PRODUCTSTATUS_DESCR")
	private String productstatusDescr;

	private String qcstatus;

	private BigDecimal receivedqty;

	@Column(name="RECEIVEDQTY_EACH")
	private BigDecimal receivedqtyEach;

	@Temporal(TemporalType.DATE)
	private Date receivedtime;

	private String receivinglocation;

	private BigDecimal referencelineno;

	private BigDecimal rejectedqty;

	@Column(name="REJECTEDQTY_EACH")
	private BigDecimal rejectedqtyEach;

	@Column(name="RESERVE_FLAG")
	private String reserveFlag;

	private String sku;
	private String skuName;

	private String skudescrc;

	private String skudescre;

	private BigDecimal totalcubic;

	private BigDecimal totalgrossweight;

	private BigDecimal totalnetweight;

	private BigDecimal totalprice;

	private String uom;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String userdefine6;

    private BasPackage basPackage;

	public DocAsnDetail() {
	}
	
	
	public String getSkuName() {
		return skuName;
	}


	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	
	public String getLinestatusName() {
		return linestatusName;
	}

	public void setLinestatusName(String linestatusName) {
		this.linestatusName = linestatusName;
	}

	public String getAsnno() {
		return asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public long getAsnlineno() {
		return asnlineno;
	}

	public void setAsnlineno(long asnlineno) {
		this.asnlineno = asnlineno;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return this.addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getAlternativedescrC() {
		return this.alternativedescrC;
	}

	public void setAlternativedescrC(String alternativedescrC) {
		this.alternativedescrC = alternativedescrC;
	}

	public String getAlternativesku() {
		return this.alternativesku;
	}

	public void setAlternativesku(String alternativesku) {
		this.alternativesku = alternativesku;
	}

	public String getAsnlinefilter() {
		return this.asnlinefilter;
	}

	public void setAsnlinefilter(String asnlinefilter) {
		this.asnlinefilter = asnlinefilter;
	}

	public String getContainerid() {
		return this.containerid;
	}

	public void setContainerid(String containerid) {
		this.containerid = containerid;
	}

	public String getCreatesource() {
		return this.createsource;
	}

	public void setCreatesource(String createsource) {
		this.createsource = createsource;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getDEdi01() {
		return this.dEdi01;
	}

	public void setDEdi01(String dEdi01) {
		this.dEdi01 = dEdi01;
	}

	public String getDEdi02() {
		return this.dEdi02;
	}

	public void setDEdi02(String dEdi02) {
		this.dEdi02 = dEdi02;
	}

	public String getDEdi03() {
		return this.dEdi03;
	}

	public void setDEdi03(String dEdi03) {
		this.dEdi03 = dEdi03;
	}

	public String getDEdi04() {
		return this.dEdi04;
	}

	public void setDEdi04(String dEdi04) {
		this.dEdi04 = dEdi04;
	}

	public String getDEdi05() {
		return this.dEdi05;
	}

	public void setDEdi05(String dEdi05) {
		this.dEdi05 = dEdi05;
	}

	public String getDEdi06() {
		return this.dEdi06;
	}

	public void setDEdi06(String dEdi06) {
		this.dEdi06 = dEdi06;
	}

	public String getDEdi07() {
		return this.dEdi07;
	}

	public void setDEdi07(String dEdi07) {
		this.dEdi07 = dEdi07;
	}

	public String getDEdi08() {
		return this.dEdi08;
	}

	public void setDEdi08(String dEdi08) {
		this.dEdi08 = dEdi08;
	}

	public BigDecimal getDEdi09() {
		return this.dEdi09;
	}

	public void setDEdi09(BigDecimal dEdi09) {
		this.dEdi09 = dEdi09;
	}

	public BigDecimal getDEdi10() {
		return this.dEdi10;
	}

	public void setDEdi10(BigDecimal dEdi10) {
		this.dEdi10 = dEdi10;
	}

	public String getDEdi11() {
		return this.dEdi11;
	}

	public void setDEdi11(String dEdi11) {
		this.dEdi11 = dEdi11;
	}

	public String getDEdi12() {
		return this.dEdi12;
	}

	public void setDEdi12(String dEdi12) {
		this.dEdi12 = dEdi12;
	}

	public String getDEdi13() {
		return this.dEdi13;
	}

	public void setDEdi13(String dEdi13) {
		this.dEdi13 = dEdi13;
	}

	public String getDEdi14() {
		return this.dEdi14;
	}

	public void setDEdi14(String dEdi14) {
		this.dEdi14 = dEdi14;
	}

	public String getDEdi15() {
		return this.dEdi15;
	}

	public void setDEdi15(String dEdi15) {
		this.dEdi15 = dEdi15;
	}

	public String getDEdi16() {
		return this.dEdi16;
	}

	public void setDEdi16(String dEdi16) {
		this.dEdi16 = dEdi16;
	}

	public String getDEdi17() {
		return this.dEdi17;
	}

	public void setDEdi17(String dEdi17) {
		this.dEdi17 = dEdi17;
	}

	public String getDEdi18() {
		return this.dEdi18;
	}

	public void setDEdi18(String dEdi18) {
		this.dEdi18 = dEdi18;
	}

	public String getDEdi19() {
		return this.dEdi19;
	}

	public void setDEdi19(String dEdi19) {
		this.dEdi19 = dEdi19;
	}

	public String getDEdi20() {
		return this.dEdi20;
	}

	public void setDEdi20(String dEdi20) {
		this.dEdi20 = dEdi20;
	}

	public BigDecimal getDamagedqtyEach() {
		return this.damagedqtyEach;
	}

	public void setDamagedqtyEach(BigDecimal damagedqtyEach) {
		this.damagedqtyEach = damagedqtyEach;
	}

	public Date getEdittime() {
		return this.edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return this.editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public BigDecimal getExpectedqty() {
		return this.expectedqty;
	}

	public void setExpectedqty(BigDecimal expectedqty) {
		this.expectedqty = expectedqty;
	}

	public BigDecimal getExpectedqtyEach() {
		return this.expectedqtyEach;
	}

	public void setExpectedqtyEach(BigDecimal expectedqtyEach) {
		this.expectedqtyEach = expectedqtyEach;
	}

	public String getHoldrejectcode() {
		return this.holdrejectcode;
	}

	public void setHoldrejectcode(String holdrejectcode) {
		this.holdrejectcode = holdrejectcode;
	}

	public String getHoldrejectreason() {
		return this.holdrejectreason;
	}

	public void setHoldrejectreason(String holdrejectreason) {
		this.holdrejectreason = holdrejectreason;
	}

	public String getLinestatus() {
		return this.linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
	}

	public String getLotatt01() {
		return this.lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return this.lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt03() {
		return this.lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt04() {
		return this.lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return this.lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt06() {
		return this.lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt07() {
		return this.lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt08() {
		return this.lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt09() {
		return this.lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt10() {
		return this.lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt11() {
		return this.lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt12() {
		return this.lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getOverrcvpercentage() {
		return this.overrcvpercentage;
	}

	public void setOverrcvpercentage(BigDecimal overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public String getPackid() {
		return this.packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getPalletizemethod() {
		return this.palletizemethod;
	}

	public void setPalletizemethod(String palletizemethod) {
		this.palletizemethod = palletizemethod;
	}

	public BigDecimal getPalletizeqtyEach() {
		return this.palletizeqtyEach;
	}

	public void setPalletizeqtyEach(BigDecimal palletizeqtyEach) {
		this.palletizeqtyEach = palletizeqtyEach;
	}

	public String getPlantoloc() {
		return this.plantoloc;
	}

	public void setPlantoloc(String plantoloc) {
		this.plantoloc = plantoloc;
	}

	public BigDecimal getPolineno() {
		return this.polineno;
	}

	public void setPolineno(BigDecimal polineno) {
		this.polineno = polineno;
	}

	public String getPono() {
		return this.pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public BigDecimal getPrereceivedqtyEach() {
		return this.prereceivedqtyEach;
	}

	public void setPrereceivedqtyEach(BigDecimal prereceivedqtyEach) {
		this.prereceivedqtyEach = prereceivedqtyEach;
	}

	public String getPrintlabel() {
		return this.printlabel;
	}

	public void setPrintlabel(String printlabel) {
		this.printlabel = printlabel;
	}

	public String getProductstatus() {
		return this.productstatus;
	}

	public void setProductstatus(String productstatus) {
		this.productstatus = productstatus;
	}

	public String getProductstatusDescr() {
		return this.productstatusDescr;
	}

	public void setProductstatusDescr(String productstatusDescr) {
		this.productstatusDescr = productstatusDescr;
	}

	public String getQcstatus() {
		return this.qcstatus;
	}

	public void setQcstatus(String qcstatus) {
		this.qcstatus = qcstatus;
	}

	public BigDecimal getReceivedqty() {
		return this.receivedqty;
	}

	public void setReceivedqty(BigDecimal receivedqty) {
		this.receivedqty = receivedqty;
	}

	public BigDecimal getReceivedqtyEach() {
		return this.receivedqtyEach;
	}

	public void setReceivedqtyEach(BigDecimal receivedqtyEach) {
		this.receivedqtyEach = receivedqtyEach;
	}

	public Date getReceivedtime() {
		return this.receivedtime;
	}

	public void setReceivedtime(Date receivedtime) {
		this.receivedtime = receivedtime;
	}

	public String getReceivinglocation() {
		return this.receivinglocation;
	}

	public void setReceivinglocation(String receivinglocation) {
		this.receivinglocation = receivinglocation;
	}

	public BigDecimal getReferencelineno() {
		return this.referencelineno;
	}

	public void setReferencelineno(BigDecimal referencelineno) {
		this.referencelineno = referencelineno;
	}

	public BigDecimal getRejectedqty() {
		return this.rejectedqty;
	}

	public void setRejectedqty(BigDecimal rejectedqty) {
		this.rejectedqty = rejectedqty;
	}

	public BigDecimal getRejectedqtyEach() {
		return this.rejectedqtyEach;
	}

	public void setRejectedqtyEach(BigDecimal rejectedqtyEach) {
		this.rejectedqtyEach = rejectedqtyEach;
	}

	public String getReserveFlag() {
		return this.reserveFlag;
	}

	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public BigDecimal getTotalnetweight() {
		return this.totalnetweight;
	}

	public void setTotalnetweight(BigDecimal totalnetweight) {
		this.totalnetweight = totalnetweight;
	}

	public BigDecimal getTotalprice() {
		return this.totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getUom() {
		return this.uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUserdefine1() {
		return this.userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return this.userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return this.userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return this.userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return this.userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public String getUserdefine6() {
		return this.userdefine6;
	}

	public void setUserdefine6(String userdefine6) {
		this.userdefine6 = userdefine6;
	}

    public BasPackage getBasPackage() {
        return basPackage;
    }

    public void setBasPackage(BasPackage basPackage) {
        this.basPackage = basPackage;
    }
}