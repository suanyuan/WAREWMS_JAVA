package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.math.BigDecimal;

public class DocAsnDetailVO {
	
	private Integer seq;
	private java.lang.String asnno;
	private int asnlineno;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.lang.String alternativedescrC;
	private java.lang.String alternativesku;
	private java.lang.String asnlinefilter;
	private java.lang.String containerid;
	private java.lang.String createsource;
	private java.lang.String customerid;
	private java.lang.String dEdi01;
	private java.lang.String dEdi02;
	private java.lang.String dEdi03;
	private java.lang.String dEdi04;
	private java.lang.String dEdi05;
	private java.lang.String dEdi06;
	private java.lang.String dEdi07;
	private java.lang.String dEdi08;
	private java.math.BigDecimal dEdi09;
	private java.math.BigDecimal dEdi10;
	private java.lang.String dEdi11;
	private java.lang.String dEdi12;
	private java.lang.String dEdi13;
	private java.lang.String dEdi14;
	private java.lang.String dEdi15;
	private java.lang.String dEdi16;
	private java.lang.String dEdi17;
	private java.lang.String dEdi18;
	private java.lang.String dEdi19;
	private java.lang.String dEdi20;
	private java.math.BigDecimal damagedqtyEach;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.math.BigDecimal expectedqty;
	private java.math.BigDecimal expectedqtyEach;
	private java.lang.String holdrejectcode;
	private java.lang.String holdrejectreason;
	private java.lang.String linestatus;
	private java.lang.String linestatusName;
	private java.lang.String lotatt01;
	private java.lang.String lotatt02;
	private java.lang.String lotatt03;
	private java.lang.String lotatt04;
	private java.lang.String lotatt05;
	private java.lang.String lotatt06;
	private java.lang.String lotatt07;
	private java.lang.String lotatt08;
	private java.lang.String lotatt09;
	private java.lang.String lotatt10;
	private java.lang.String lotatt11;
	private java.lang.String lotatt12;
	private java.lang.String lotatt13;
	private java.lang.String lotatt14;
    private java.lang.String lotatt15;
    private java.lang.String lotatt16;
    private java.lang.String lotatt17;
    private java.lang.String lotatt18;
	private java.lang.String cusdescr;
	private java.lang.String notes;
	private java.lang.String operator;
	private java.math.BigDecimal overrcvpercentage;
	private java.lang.String packid;
	private java.lang.String palletizemethod;
	private java.math.BigDecimal palletizeqtyEach;
	private java.lang.String plantoloc;
	private java.math.BigDecimal polineno;
	private java.lang.String pono;
	private java.math.BigDecimal prereceivedqtyEach;
	private java.lang.String printlabel;
	private java.lang.String productstatus;
	private java.lang.String productstatusDescr;
	private java.lang.String qcstatus;
	private java.math.BigDecimal receivedqty;
	private java.math.BigDecimal receivedqtyEach;
	private java.util.Date receivedtime;
	private java.lang.String receivinglocation;
	private java.math.BigDecimal referencelineno;
	private java.math.BigDecimal rejectedqty;
	private java.math.BigDecimal rejectedqtyEach;
	private java.lang.String reserveFlag;
	private java.lang.String sku;
	private java.lang.String skudescrc;
	private java.lang.String skudescre;
	private java.math.BigDecimal totalcubic;
	private java.math.BigDecimal totalgrossweight;
	private java.math.BigDecimal totalnetweight;
	private java.math.BigDecimal totalprice;
	private java.lang.String uom;
	private java.lang.String userdefine1;
	private java.lang.String userdefine2;
	private java.lang.String userdefine3;
	private java.lang.String userdefine4;
	private java.lang.String userdefine5;
	private java.lang.String userdefine6;
	private String coldName;

	private Double expectedqtySum;//预期到货数
	private Double receivedqtySum;//实际到货


	public Double getExpectedqtySum() {
		return expectedqtySum;
	}

	public void setExpectedqtySum(Double expectedqtySum) {
		this.expectedqtySum = expectedqtySum;
	}

	public Double getReceivedqtySum() {
		return receivedqtySum;
	}

	public void setReceivedqtySum(Double receivedqtySum) {
		this.receivedqtySum = receivedqtySum;
	}
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public java.lang.String getLinestatusName() {
		return linestatusName;
	}

	public void setLinestatusName(java.lang.String linestatusName) {
		this.linestatusName = linestatusName;
	}

	public java.lang.String getAsnno() {
		return asnno;
	}
    public String getCusdescr() {
        return cusdescr;
    }

    public void setCusdescr(String cusdescr) {
        this.cusdescr = cusdescr;
    }

	public void setAsnno(java.lang.String asnno) {
		this.asnno = asnno;
	}

	public int getAsnlineno() {
		return asnlineno;
	}

	public void setAsnlineno(int asnlineno) {
		this.asnlineno = asnlineno;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddwho() {
		return addwho;
	}

	public void setAddwho(java.lang.String addwho) {
		this.addwho = addwho;
	}

	public java.lang.String getAlternativedescrC() {
		return alternativedescrC;
	}

	public void setAlternativedescrC(java.lang.String alternativedescrC) {
		this.alternativedescrC = alternativedescrC;
	}

	public java.lang.String getAlternativesku() {
		return alternativesku;
	}

	public void setAlternativesku(java.lang.String alternativesku) {
		this.alternativesku = alternativesku;
	}

	public java.lang.String getAsnlinefilter() {
		return asnlinefilter;
	}

	public void setAsnlinefilter(java.lang.String asnlinefilter) {
		this.asnlinefilter = asnlinefilter;
	}

	public java.lang.String getContainerid() {
		return containerid;
	}

	public void setContainerid(java.lang.String containerid) {
		this.containerid = containerid;
	}

	public java.lang.String getCreatesource() {
		return createsource;
	}

	public void setCreatesource(java.lang.String createsource) {
		this.createsource = createsource;
	}

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.lang.String getDEdi01() {
		return dEdi01;
	}

	public void setDEdi01(java.lang.String dEdi01) {
		this.dEdi01 = dEdi01;
	}

	public java.lang.String getDEdi02() {
		return dEdi02;
	}

	public void setDEdi02(java.lang.String dEdi02) {
		this.dEdi02 = dEdi02;
	}

	public java.lang.String getDEdi03() {
		return dEdi03;
	}

	public void setDEdi03(java.lang.String dEdi03) {
		this.dEdi03 = dEdi03;
	}

	public java.lang.String getDEdi04() {
		return dEdi04;
	}

	public void setDEdi04(java.lang.String dEdi04) {
		this.dEdi04 = dEdi04;
	}

	public java.lang.String getDEdi05() {
		return dEdi05;
	}

	public void setDEdi05(java.lang.String dEdi05) {
		this.dEdi05 = dEdi05;
	}

	public java.lang.String getDEdi06() {
		return dEdi06;
	}

	public void setDEdi06(java.lang.String dEdi06) {
		this.dEdi06 = dEdi06;
	}

	public java.lang.String getDEdi07() {
		return dEdi07;
	}

	public void setDEdi07(java.lang.String dEdi07) {
		this.dEdi07 = dEdi07;
	}

	public java.lang.String getDEdi08() {
		return dEdi08;
	}

	public void setDEdi08(java.lang.String dEdi08) {
		this.dEdi08 = dEdi08;
	}

	public java.math.BigDecimal getDEdi09() {
		return dEdi09;
	}

	public void setDEdi09(java.math.BigDecimal dEdi09) {
		this.dEdi09 = dEdi09;
	}

	public java.math.BigDecimal getDEdi10() {
		return dEdi10;
	}

	public void setDEdi10(java.math.BigDecimal dEdi10) {
		this.dEdi10 = dEdi10;
	}

	public java.lang.String getDEdi11() {
		return dEdi11;
	}

	public void setDEdi11(java.lang.String dEdi11) {
		this.dEdi11 = dEdi11;
	}

	public java.lang.String getDEdi12() {
		return dEdi12;
	}

	public void setDEdi12(java.lang.String dEdi12) {
		this.dEdi12 = dEdi12;
	}

	public java.lang.String getDEdi13() {
		return dEdi13;
	}

	public void setDEdi13(java.lang.String dEdi13) {
		this.dEdi13 = dEdi13;
	}

	public java.lang.String getDEdi14() {
		return dEdi14;
	}

	public void setDEdi14(java.lang.String dEdi14) {
		this.dEdi14 = dEdi14;
	}

	public java.lang.String getDEdi15() {
		return dEdi15;
	}

	public void setDEdi15(java.lang.String dEdi15) {
		this.dEdi15 = dEdi15;
	}

	public java.lang.String getDEdi16() {
		return dEdi16;
	}

	public void setDEdi16(java.lang.String dEdi16) {
		this.dEdi16 = dEdi16;
	}

	public java.lang.String getDEdi17() {
		return dEdi17;
	}

	public void setDEdi17(java.lang.String dEdi17) {
		this.dEdi17 = dEdi17;
	}

	public java.lang.String getDEdi18() {
		return dEdi18;
	}

	public void setDEdi18(java.lang.String dEdi18) {
		this.dEdi18 = dEdi18;
	}

	public java.lang.String getDEdi19() {
		return dEdi19;
	}

	public void setDEdi19(java.lang.String dEdi19) {
		this.dEdi19 = dEdi19;
	}

	public java.lang.String getDEdi20() {
		return dEdi20;
	}

	public void setDEdi20(java.lang.String dEdi20) {
		this.dEdi20 = dEdi20;
	}

	public java.math.BigDecimal getDamagedqtyEach() {
		return damagedqtyEach;
	}

	public void setDamagedqtyEach(java.math.BigDecimal damagedqtyEach) {
		this.damagedqtyEach = damagedqtyEach;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	public java.math.BigDecimal getExpectedqty() {
		return expectedqty;
	}

	public void setExpectedqty(java.math.BigDecimal expectedqty) {
		this.expectedqty = expectedqty;
	}

	public java.math.BigDecimal getExpectedqtyEach() {
		return expectedqtyEach;
	}

	public void setExpectedqtyEach(java.math.BigDecimal expectedqtyEach) {
		this.expectedqtyEach = expectedqtyEach;
	}

	public java.lang.String getHoldrejectcode() {
		return holdrejectcode;
	}

	public void setHoldrejectcode(java.lang.String holdrejectcode) {
		this.holdrejectcode = holdrejectcode;
	}

	public java.lang.String getHoldrejectreason() {
		return holdrejectreason;
	}

	public void setHoldrejectreason(java.lang.String holdrejectreason) {
		this.holdrejectreason = holdrejectreason;
	}

	public java.lang.String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(java.lang.String linestatus) {
		this.linestatus = linestatus;
	}

	public java.lang.String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(java.lang.String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public java.lang.String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(java.lang.String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public java.lang.String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(java.lang.String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public java.lang.String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(java.lang.String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public java.lang.String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(java.lang.String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public java.lang.String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(java.lang.String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public java.lang.String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(java.lang.String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public java.lang.String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(java.lang.String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public java.lang.String getLotatt09() {
		return lotatt09;
	}

	public void setLotatt09(java.lang.String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public java.lang.String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(java.lang.String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public java.lang.String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(java.lang.String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public java.lang.String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(java.lang.String lotatt12) {
		this.lotatt12 = lotatt12;
	}

    public String getLotatt15() {
        return lotatt15;
    }

    public void setLotatt15(String lotatt15) {
        this.lotatt15 = lotatt15;
    }

    public String getLotatt16() {
        return lotatt16;
    }

    public void setLotatt16(String lotatt16) {
        this.lotatt16 = lotatt16;
    }

    public String getLotatt17() {
        return lotatt17;
    }

    public void setLotatt17(String lotatt17) {
        this.lotatt17 = lotatt17;
    }

    public String getLotatt18() {
        return lotatt18;
    }

    public void setLotatt18(String lotatt18) {
        this.lotatt18 = lotatt18;
    }

    public java.lang.String getNotes() {
		return notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}

	public java.lang.String getOperator() {
		return operator;
	}

	public void setOperator(java.lang.String operator) {
		this.operator = operator;
	}

	public java.math.BigDecimal getOverrcvpercentage() {
		return overrcvpercentage;
	}

	public void setOverrcvpercentage(java.math.BigDecimal overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public java.lang.String getPackid() {
		return packid;
	}

	public void setPackid(java.lang.String packid) {
		this.packid = packid;
	}

	public java.lang.String getPalletizemethod() {
		return palletizemethod;
	}

	public void setPalletizemethod(java.lang.String palletizemethod) {
		this.palletizemethod = palletizemethod;
	}

	public java.math.BigDecimal getPalletizeqtyEach() {
		return palletizeqtyEach;
	}

	public void setPalletizeqtyEach(java.math.BigDecimal palletizeqtyEach) {
		this.palletizeqtyEach = palletizeqtyEach;
	}

	public java.lang.String getPlantoloc() {
		return plantoloc;
	}

	public void setPlantoloc(java.lang.String plantoloc) {
		this.plantoloc = plantoloc;
	}

	public java.math.BigDecimal getPolineno() {
		return polineno;
	}

	public void setPolineno(java.math.BigDecimal polineno) {
		this.polineno = polineno;
	}

	public java.lang.String getPono() {
		return pono;
	}

	public void setPono(java.lang.String pono) {
		this.pono = pono;
	}

	public java.math.BigDecimal getPrereceivedqtyEach() {
		return prereceivedqtyEach;
	}

	public void setPrereceivedqtyEach(java.math.BigDecimal prereceivedqtyEach) {
		this.prereceivedqtyEach = prereceivedqtyEach;
	}

	public java.lang.String getPrintlabel() {
		return printlabel;
	}

	public void setPrintlabel(java.lang.String printlabel) {
		this.printlabel = printlabel;
	}

	public java.lang.String getProductstatus() {
		return productstatus;
	}

	public void setProductstatus(java.lang.String productstatus) {
		this.productstatus = productstatus;
	}

	public java.lang.String getProductstatusDescr() {
		return productstatusDescr;
	}

	public void setProductstatusDescr(java.lang.String productstatusDescr) {
		this.productstatusDescr = productstatusDescr;
	}

	public java.lang.String getQcstatus() {
		return qcstatus;
	}

	public void setQcstatus(java.lang.String qcstatus) {
		this.qcstatus = qcstatus;
	}

	public java.math.BigDecimal getReceivedqty() {
		return receivedqty;
	}

	public void setReceivedqty(java.math.BigDecimal receivedqty) {
		this.receivedqty = receivedqty;
	}

	public java.math.BigDecimal getReceivedqtyEach() {
		return receivedqtyEach;
	}

	public void setReceivedqtyEach(java.math.BigDecimal receivedqtyEach) {
		this.receivedqtyEach = receivedqtyEach;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getReceivedtime() {
		return receivedtime;
	}

	public void setReceivedtime(java.util.Date receivedtime) {
		this.receivedtime = receivedtime;
	}

	public java.lang.String getReceivinglocation() {
		return receivinglocation;
	}

	public void setReceivinglocation(java.lang.String receivinglocation) {
		this.receivinglocation = receivinglocation;
	}

	public java.math.BigDecimal getReferencelineno() {
		return referencelineno;
	}

	public void setReferencelineno(java.math.BigDecimal referencelineno) {
		this.referencelineno = referencelineno;
	}

	public java.math.BigDecimal getRejectedqty() {
		return rejectedqty;
	}

	public void setRejectedqty(java.math.BigDecimal rejectedqty) {
		this.rejectedqty = rejectedqty;
	}

	public java.math.BigDecimal getRejectedqtyEach() {
		return rejectedqtyEach;
	}

	public void setRejectedqtyEach(java.math.BigDecimal rejectedqtyEach) {
		this.rejectedqtyEach = rejectedqtyEach;
	}

	public java.lang.String getReserveFlag() {
		return reserveFlag;
	}

	public void setReserveFlag(java.lang.String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
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

	public java.math.BigDecimal getTotalnetweight() {
		return totalnetweight;
	}

	public void setTotalnetweight(java.math.BigDecimal totalnetweight) {
		this.totalnetweight = totalnetweight;
	}

	public java.math.BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(java.math.BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public java.lang.String getUom() {
		return uom;
	}

	public void setUom(java.lang.String uom) {
		this.uom = uom;
	}

	public java.lang.String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(java.lang.String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public java.lang.String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(java.lang.String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public java.lang.String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(java.lang.String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public java.lang.String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(java.lang.String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public java.lang.String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(java.lang.String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public java.lang.String getUserdefine6() {
		return userdefine6;
	}

	public void setUserdefine6(java.lang.String userdefine6) {
		this.userdefine6 = userdefine6;
	}

	public String getLotatt13() {
		return lotatt13;
	}

	public void setLotatt13(String lotatt13) {
		this.lotatt13 = lotatt13;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public String getdEdi01() {
		return dEdi01;
	}

	public void setdEdi01(String dEdi01) {
		this.dEdi01 = dEdi01;
	}

	public String getdEdi02() {
		return dEdi02;
	}

	public void setdEdi02(String dEdi02) {
		this.dEdi02 = dEdi02;
	}

	public String getdEdi03() {
		return dEdi03;
	}

	public void setdEdi03(String dEdi03) {
		this.dEdi03 = dEdi03;
	}

	public String getdEdi04() {
		return dEdi04;
	}

	public void setdEdi04(String dEdi04) {
		this.dEdi04 = dEdi04;
	}

	public String getdEdi05() {
		return dEdi05;
	}

	public void setdEdi05(String dEdi05) {
		this.dEdi05 = dEdi05;
	}

	public String getdEdi06() {
		return dEdi06;
	}

	public void setdEdi06(String dEdi06) {
		this.dEdi06 = dEdi06;
	}

	public String getdEdi07() {
		return dEdi07;
	}

	public void setdEdi07(String dEdi07) {
		this.dEdi07 = dEdi07;
	}

	public String getdEdi08() {
		return dEdi08;
	}

	public void setdEdi08(String dEdi08) {
		this.dEdi08 = dEdi08;
	}

	public BigDecimal getdEdi09() {
		return dEdi09;
	}

	public void setdEdi09(BigDecimal dEdi09) {
		this.dEdi09 = dEdi09;
	}

	public BigDecimal getdEdi10() {
		return dEdi10;
	}

	public void setdEdi10(BigDecimal dEdi10) {
		this.dEdi10 = dEdi10;
	}

	public String getdEdi11() {
		return dEdi11;
	}

	public void setdEdi11(String dEdi11) {
		this.dEdi11 = dEdi11;
	}

	public String getdEdi12() {
		return dEdi12;
	}

	public void setdEdi12(String dEdi12) {
		this.dEdi12 = dEdi12;
	}

	public String getdEdi13() {
		return dEdi13;
	}

	public void setdEdi13(String dEdi13) {
		this.dEdi13 = dEdi13;
	}

	public String getdEdi14() {
		return dEdi14;
	}

	public void setdEdi14(String dEdi14) {
		this.dEdi14 = dEdi14;
	}

	public String getdEdi15() {
		return dEdi15;
	}

	public void setdEdi15(String dEdi15) {
		this.dEdi15 = dEdi15;
	}

	public String getdEdi16() {
		return dEdi16;
	}

	public void setdEdi16(String dEdi16) {
		this.dEdi16 = dEdi16;
	}

	public String getdEdi17() {
		return dEdi17;
	}

	public void setdEdi17(String dEdi17) {
		this.dEdi17 = dEdi17;
	}

	public String getdEdi18() {
		return dEdi18;
	}

	public void setdEdi18(String dEdi18) {
		this.dEdi18 = dEdi18;
	}

	public String getdEdi19() {
		return dEdi19;
	}

	public void setdEdi19(String dEdi19) {
		this.dEdi19 = dEdi19;
	}

	public String getdEdi20() {
		return dEdi20;
	}

	public void setdEdi20(String dEdi20) {
		this.dEdi20 = dEdi20;
	}

	public String getColdName() {
		return coldName;
	}

	public void setColdName(String coldName) {
		this.coldName = coldName;
	}
}