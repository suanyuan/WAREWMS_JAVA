package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasSkuHistoryVO {

	private String customerid;
	private String sku;
	private String hazardFlag;
	private String descrE;
	private String descrC;
	private String packid;
	private Double tare;
	private String activeFlag;
	private String lotid;
	private String cartongroup;
	private String putawayzone;
	private Double cube;
	private String putawaylocation;
	private Double grossweight;
	private Double netweight;
	private String cycleclass;
	private java.sql.Date lastcyclecount;
	private Double reorderqty;
	private Double price;
	private String shelflifeflag;
	private Long inboundlifedays;
	private String softallocationrule;
	private String allocationrule;
	private String shelflifetype;
	private Long outboundlifedays;
	private java.sql.Date addtime;
	private String addwho;
	private java.sql.Date edittime;
	private String editwho;
	private String alternateSku4;
	private String alternateSku5;
	private String notes;
	private String putawayrule;
	private String alternateSku1;
	private String alternateSku2;
	private String alternateSku3;
	private String reservedfield05;
	private String skuGroup1;
	private String skuGroup2;
	private String skuGroup3;
	private String skuGroup4;
	private String skuGroup5;
	private String reservedfield01;
	private String reservedfield02;
	private String reservedfield03;
	private String reservedfield04;
	private String defaultreceivinguom;
	private String defaultshipmentuom;
	private String defaulthold;
	private String rotationid;
	private String copypackidtolotatt12;
	private Double qtymin;
	private Double qtymax;
	private Long shelflife;
	private String hscode;
	private String reservedfield06;
	private String reservedfield07;
	private String reservedfield08;
	private String reservedfield09;
	private String reservedfield10;
	private String reservedfield11;
	private String reservedfield12;
	private Double overrcvpercentage;
	private String replenishrule;
	private String specialmaintenance;
	private java.sql.Date lastmaintenancedate;
	private String firstop;
	private String medicaltype;
	private String approvalno;
	private String snAsnQty;
	private String snSoQty;
	private String invchgwithshipment;
	private String freightclass;
	private String tariffid;
	private String kitflag;
	private String reservecode;
	private String reportuom;
	private Double skulength;
	private Double skuwidth;
	private Double skuhigh;
	private Double qctime;
	private String qcrule;
	private java.sql.Date firstinbounddate;
	private String reservedfield13;
	private String reservedfield14;
	private String reservedfield15;
	private String skuGroup6;
	private String skuGroup7;
	private String skuGroup8;
	private String skuGroup9;
	private String medicinespecicalcontrol;
	private String serialnocatch;
	private String scanwhencasepicking;
	private String scanwhenpiecepicking;
	private String scanwhencheck;
	private String scanwhenreceive;
	private String scanwhenputaway;
	private Long shelflifealertdays;
	private String reservedfield17;
	private String reservedfield18;
	private String reservedfield16;
	private String chkScnUom;
	private String alternativePutawayzone1;
	private String alternativePutawayzone2;
	private String scanwhenpack;
	private String scanwhenmove;
	private String imageaddress;
	private String onestepallocation;
	private String orderbysql;
	private String secondserialnocatch;
	private String allowreceiving;
	private String allowshipment;
	private String inboundserialnoqtycontrol;
	private String outboundserialnoqtycontrol;
	private String defaultcartontype;
	private String breakcs;
	private String breakea;
	private String breakip;
	private String printmedicineqcreport;
	private String scanwhenqc;
	private String overreceiving;
	private String defaultsupplierid;

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

	public String getHazardFlag() {
		return hazardFlag;
	}

	public void setHazardFlag(String hazardFlag) {
		this.hazardFlag = hazardFlag;
	}

	public String getDescrE() {
		return descrE;
	}

	public void setDescrE(String descrE) {
		this.descrE = descrE;
	}

	public String getDescrC() {
		return descrC;
	}

	public void setDescrC(String descrC) {
		this.descrC = descrC;
	}

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public Double getTare() {
		return tare;
	}

	public void setTare(Double tare) {
		this.tare = tare;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getLotid() {
		return lotid;
	}

	public void setLotid(String lotid) {
		this.lotid = lotid;
	}

	public String getCartongroup() {
		return cartongroup;
	}

	public void setCartongroup(String cartongroup) {
		this.cartongroup = cartongroup;
	}

	public String getPutawayzone() {
		return putawayzone;
	}

	public void setPutawayzone(String putawayzone) {
		this.putawayzone = putawayzone;
	}

	public Double getCube() {
		return cube;
	}

	public void setCube(Double cube) {
		this.cube = cube;
	}

	public String getPutawaylocation() {
		return putawaylocation;
	}

	public void setPutawaylocation(String putawaylocation) {
		this.putawaylocation = putawaylocation;
	}

	public Double getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(Double grossweight) {
		this.grossweight = grossweight;
	}

	public Double getNetweight() {
		return netweight;
	}

	public void setNetweight(Double netweight) {
		this.netweight = netweight;
	}

	public String getCycleclass() {
		return cycleclass;
	}

	public void setCycleclass(String cycleclass) {
		this.cycleclass = cycleclass;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getLastcyclecount() {
		return lastcyclecount;
	}

	public void setLastcyclecount(java.sql.Date lastcyclecount) {
		this.lastcyclecount = lastcyclecount;
	}

	public Double getReorderqty() {
		return reorderqty;
	}

	public void setReorderqty(Double reorderqty) {
		this.reorderqty = reorderqty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getShelflifeflag() {
		return shelflifeflag;
	}

	public void setShelflifeflag(String shelflifeflag) {
		this.shelflifeflag = shelflifeflag;
	}

	public Long getInboundlifedays() {
		return inboundlifedays;
	}

	public void setInboundlifedays(Long inboundlifedays) {
		this.inboundlifedays = inboundlifedays;
	}

	public String getSoftallocationrule() {
		return softallocationrule;
	}

	public void setSoftallocationrule(String softallocationrule) {
		this.softallocationrule = softallocationrule;
	}

	public String getAllocationrule() {
		return allocationrule;
	}

	public void setAllocationrule(String allocationrule) {
		this.allocationrule = allocationrule;
	}

	public String getShelflifetype() {
		return shelflifetype;
	}

	public void setShelflifetype(String shelflifetype) {
		this.shelflifetype = shelflifetype;
	}

	public Long getOutboundlifedays() {
		return outboundlifedays;
	}

	public void setOutboundlifedays(Long outboundlifedays) {
		this.outboundlifedays = outboundlifedays;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.sql.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.sql.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getAlternateSku4() {
		return alternateSku4;
	}

	public void setAlternateSku4(String alternateSku4) {
		this.alternateSku4 = alternateSku4;
	}

	public String getAlternateSku5() {
		return alternateSku5;
	}

	public void setAlternateSku5(String alternateSku5) {
		this.alternateSku5 = alternateSku5;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPutawayrule() {
		return putawayrule;
	}

	public void setPutawayrule(String putawayrule) {
		this.putawayrule = putawayrule;
	}

	public String getAlternateSku1() {
		return alternateSku1;
	}

	public void setAlternateSku1(String alternateSku1) {
		this.alternateSku1 = alternateSku1;
	}

	public String getAlternateSku2() {
		return alternateSku2;
	}

	public void setAlternateSku2(String alternateSku2) {
		this.alternateSku2 = alternateSku2;
	}

	public String getAlternateSku3() {
		return alternateSku3;
	}

	public void setAlternateSku3(String alternateSku3) {
		this.alternateSku3 = alternateSku3;
	}

	public String getReservedfield05() {
		return reservedfield05;
	}

	public void setReservedfield05(String reservedfield05) {
		this.reservedfield05 = reservedfield05;
	}

	public String getSkuGroup1() {
		return skuGroup1;
	}

	public void setSkuGroup1(String skuGroup1) {
		this.skuGroup1 = skuGroup1;
	}

	public String getSkuGroup2() {
		return skuGroup2;
	}

	public void setSkuGroup2(String skuGroup2) {
		this.skuGroup2 = skuGroup2;
	}

	public String getSkuGroup3() {
		return skuGroup3;
	}

	public void setSkuGroup3(String skuGroup3) {
		this.skuGroup3 = skuGroup3;
	}

	public String getSkuGroup4() {
		return skuGroup4;
	}

	public void setSkuGroup4(String skuGroup4) {
		this.skuGroup4 = skuGroup4;
	}

	public String getSkuGroup5() {
		return skuGroup5;
	}

	public void setSkuGroup5(String skuGroup5) {
		this.skuGroup5 = skuGroup5;
	}

	public String getReservedfield01() {
		return reservedfield01;
	}

	public void setReservedfield01(String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public String getReservedfield02() {
		return reservedfield02;
	}

	public void setReservedfield02(String reservedfield02) {
		this.reservedfield02 = reservedfield02;
	}

	public String getReservedfield03() {
		return reservedfield03;
	}

	public void setReservedfield03(String reservedfield03) {
		this.reservedfield03 = reservedfield03;
	}

	public String getReservedfield04() {
		return reservedfield04;
	}

	public void setReservedfield04(String reservedfield04) {
		this.reservedfield04 = reservedfield04;
	}

	public String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public String getDefaultshipmentuom() {
		return defaultshipmentuom;
	}

	public void setDefaultshipmentuom(String defaultshipmentuom) {
		this.defaultshipmentuom = defaultshipmentuom;
	}

	public String getDefaulthold() {
		return defaulthold;
	}

	public void setDefaulthold(String defaulthold) {
		this.defaulthold = defaulthold;
	}

	public String getRotationid() {
		return rotationid;
	}

	public void setRotationid(String rotationid) {
		this.rotationid = rotationid;
	}

	public String getCopypackidtolotatt12() {
		return copypackidtolotatt12;
	}

	public void setCopypackidtolotatt12(String copypackidtolotatt12) {
		this.copypackidtolotatt12 = copypackidtolotatt12;
	}

	public Double getQtymin() {
		return qtymin;
	}

	public void setQtymin(Double qtymin) {
		this.qtymin = qtymin;
	}

	public Double getQtymax() {
		return qtymax;
	}

	public void setQtymax(Double qtymax) {
		this.qtymax = qtymax;
	}

	public Long getShelflife() {
		return shelflife;
	}

	public void setShelflife(Long shelflife) {
		this.shelflife = shelflife;
	}

	public String getHscode() {
		return hscode;
	}

	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	public String getReservedfield06() {
		return reservedfield06;
	}

	public void setReservedfield06(String reservedfield06) {
		this.reservedfield06 = reservedfield06;
	}

	public String getReservedfield07() {
		return reservedfield07;
	}

	public void setReservedfield07(String reservedfield07) {
		this.reservedfield07 = reservedfield07;
	}

	public String getReservedfield08() {
		return reservedfield08;
	}

	public void setReservedfield08(String reservedfield08) {
		this.reservedfield08 = reservedfield08;
	}

	public String getReservedfield09() {
		return reservedfield09;
	}

	public void setReservedfield09(String reservedfield09) {
		this.reservedfield09 = reservedfield09;
	}

	public String getReservedfield10() {
		return reservedfield10;
	}

	public void setReservedfield10(String reservedfield10) {
		this.reservedfield10 = reservedfield10;
	}

	public String getReservedfield11() {
		return reservedfield11;
	}

	public void setReservedfield11(String reservedfield11) {
		this.reservedfield11 = reservedfield11;
	}

	public String getReservedfield12() {
		return reservedfield12;
	}

	public void setReservedfield12(String reservedfield12) {
		this.reservedfield12 = reservedfield12;
	}

	public Double getOverrcvpercentage() {
		return overrcvpercentage;
	}

	public void setOverrcvpercentage(Double overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public String getReplenishrule() {
		return replenishrule;
	}

	public void setReplenishrule(String replenishrule) {
		this.replenishrule = replenishrule;
	}

	public String getSpecialmaintenance() {
		return specialmaintenance;
	}

	public void setSpecialmaintenance(String specialmaintenance) {
		this.specialmaintenance = specialmaintenance;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getLastmaintenancedate() {
		return lastmaintenancedate;
	}

	public void setLastmaintenancedate(java.sql.Date lastmaintenancedate) {
		this.lastmaintenancedate = lastmaintenancedate;
	}

	public String getFirstop() {
		return firstop;
	}

	public void setFirstop(String firstop) {
		this.firstop = firstop;
	}

	public String getMedicaltype() {
		return medicaltype;
	}

	public void setMedicaltype(String medicaltype) {
		this.medicaltype = medicaltype;
	}

	public String getApprovalno() {
		return approvalno;
	}

	public void setApprovalno(String approvalno) {
		this.approvalno = approvalno;
	}

	public String getSnAsnQty() {
		return snAsnQty;
	}

	public void setSnAsnQty(String snAsnQty) {
		this.snAsnQty = snAsnQty;
	}

	public String getSnSoQty() {
		return snSoQty;
	}

	public void setSnSoQty(String snSoQty) {
		this.snSoQty = snSoQty;
	}

	public String getInvchgwithshipment() {
		return invchgwithshipment;
	}

	public void setInvchgwithshipment(String invchgwithshipment) {
		this.invchgwithshipment = invchgwithshipment;
	}

	public String getFreightclass() {
		return freightclass;
	}

	public void setFreightclass(String freightclass) {
		this.freightclass = freightclass;
	}

	public String getTariffid() {
		return tariffid;
	}

	public void setTariffid(String tariffid) {
		this.tariffid = tariffid;
	}

	public String getKitflag() {
		return kitflag;
	}

	public void setKitflag(String kitflag) {
		this.kitflag = kitflag;
	}

	public String getReservecode() {
		return reservecode;
	}

	public void setReservecode(String reservecode) {
		this.reservecode = reservecode;
	}

	public String getReportuom() {
		return reportuom;
	}

	public void setReportuom(String reportuom) {
		this.reportuom = reportuom;
	}

	public Double getSkulength() {
		return skulength;
	}

	public void setSkulength(Double skulength) {
		this.skulength = skulength;
	}

	public Double getSkuwidth() {
		return skuwidth;
	}

	public void setSkuwidth(Double skuwidth) {
		this.skuwidth = skuwidth;
	}

	public Double getSkuhigh() {
		return skuhigh;
	}

	public void setSkuhigh(Double skuhigh) {
		this.skuhigh = skuhigh;
	}

	public Double getQctime() {
		return qctime;
	}

	public void setQctime(Double qctime) {
		this.qctime = qctime;
	}

	public String getQcrule() {
		return qcrule;
	}

	public void setQcrule(String qcrule) {
		this.qcrule = qcrule;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getFirstinbounddate() {
		return firstinbounddate;
	}

	public void setFirstinbounddate(java.sql.Date firstinbounddate) {
		this.firstinbounddate = firstinbounddate;
	}

	public String getReservedfield13() {
		return reservedfield13;
	}

	public void setReservedfield13(String reservedfield13) {
		this.reservedfield13 = reservedfield13;
	}

	public String getReservedfield14() {
		return reservedfield14;
	}

	public void setReservedfield14(String reservedfield14) {
		this.reservedfield14 = reservedfield14;
	}

	public String getReservedfield15() {
		return reservedfield15;
	}

	public void setReservedfield15(String reservedfield15) {
		this.reservedfield15 = reservedfield15;
	}

	public String getSkuGroup6() {
		return skuGroup6;
	}

	public void setSkuGroup6(String skuGroup6) {
		this.skuGroup6 = skuGroup6;
	}

	public String getSkuGroup7() {
		return skuGroup7;
	}

	public void setSkuGroup7(String skuGroup7) {
		this.skuGroup7 = skuGroup7;
	}

	public String getSkuGroup8() {
		return skuGroup8;
	}

	public void setSkuGroup8(String skuGroup8) {
		this.skuGroup8 = skuGroup8;
	}

	public String getSkuGroup9() {
		return skuGroup9;
	}

	public void setSkuGroup9(String skuGroup9) {
		this.skuGroup9 = skuGroup9;
	}

	public String getMedicinespecicalcontrol() {
		return medicinespecicalcontrol;
	}

	public void setMedicinespecicalcontrol(String medicinespecicalcontrol) {
		this.medicinespecicalcontrol = medicinespecicalcontrol;
	}

	public String getSerialnocatch() {
		return serialnocatch;
	}

	public void setSerialnocatch(String serialnocatch) {
		this.serialnocatch = serialnocatch;
	}

	public String getScanwhencasepicking() {
		return scanwhencasepicking;
	}

	public void setScanwhencasepicking(String scanwhencasepicking) {
		this.scanwhencasepicking = scanwhencasepicking;
	}

	public String getScanwhenpiecepicking() {
		return scanwhenpiecepicking;
	}

	public void setScanwhenpiecepicking(String scanwhenpiecepicking) {
		this.scanwhenpiecepicking = scanwhenpiecepicking;
	}

	public String getScanwhencheck() {
		return scanwhencheck;
	}

	public void setScanwhencheck(String scanwhencheck) {
		this.scanwhencheck = scanwhencheck;
	}

	public String getScanwhenreceive() {
		return scanwhenreceive;
	}

	public void setScanwhenreceive(String scanwhenreceive) {
		this.scanwhenreceive = scanwhenreceive;
	}

	public String getScanwhenputaway() {
		return scanwhenputaway;
	}

	public void setScanwhenputaway(String scanwhenputaway) {
		this.scanwhenputaway = scanwhenputaway;
	}

	public Long getShelflifealertdays() {
		return shelflifealertdays;
	}

	public void setShelflifealertdays(Long shelflifealertdays) {
		this.shelflifealertdays = shelflifealertdays;
	}

	public String getReservedfield17() {
		return reservedfield17;
	}

	public void setReservedfield17(String reservedfield17) {
		this.reservedfield17 = reservedfield17;
	}

	public String getReservedfield18() {
		return reservedfield18;
	}

	public void setReservedfield18(String reservedfield18) {
		this.reservedfield18 = reservedfield18;
	}

	public String getReservedfield16() {
		return reservedfield16;
	}

	public void setReservedfield16(String reservedfield16) {
		this.reservedfield16 = reservedfield16;
	}

	public String getChkScnUom() {
		return chkScnUom;
	}

	public void setChkScnUom(String chkScnUom) {
		this.chkScnUom = chkScnUom;
	}

	public String getAlternativePutawayzone1() {
		return alternativePutawayzone1;
	}

	public void setAlternativePutawayzone1(String alternativePutawayzone1) {
		this.alternativePutawayzone1 = alternativePutawayzone1;
	}

	public String getAlternativePutawayzone2() {
		return alternativePutawayzone2;
	}

	public void setAlternativePutawayzone2(String alternativePutawayzone2) {
		this.alternativePutawayzone2 = alternativePutawayzone2;
	}

	public String getScanwhenpack() {
		return scanwhenpack;
	}

	public void setScanwhenpack(String scanwhenpack) {
		this.scanwhenpack = scanwhenpack;
	}

	public String getScanwhenmove() {
		return scanwhenmove;
	}

	public void setScanwhenmove(String scanwhenmove) {
		this.scanwhenmove = scanwhenmove;
	}

	public String getImageaddress() {
		return imageaddress;
	}

	public void setImageaddress(String imageaddress) {
		this.imageaddress = imageaddress;
	}

	public String getOnestepallocation() {
		return onestepallocation;
	}

	public void setOnestepallocation(String onestepallocation) {
		this.onestepallocation = onestepallocation;
	}

	public String getOrderbysql() {
		return orderbysql;
	}

	public void setOrderbysql(String orderbysql) {
		this.orderbysql = orderbysql;
	}

	public String getSecondserialnocatch() {
		return secondserialnocatch;
	}

	public void setSecondserialnocatch(String secondserialnocatch) {
		this.secondserialnocatch = secondserialnocatch;
	}

	public String getAllowreceiving() {
		return allowreceiving;
	}

	public void setAllowreceiving(String allowreceiving) {
		this.allowreceiving = allowreceiving;
	}

	public String getAllowshipment() {
		return allowshipment;
	}

	public void setAllowshipment(String allowshipment) {
		this.allowshipment = allowshipment;
	}

	public String getInboundserialnoqtycontrol() {
		return inboundserialnoqtycontrol;
	}

	public void setInboundserialnoqtycontrol(String inboundserialnoqtycontrol) {
		this.inboundserialnoqtycontrol = inboundserialnoqtycontrol;
	}

	public String getOutboundserialnoqtycontrol() {
		return outboundserialnoqtycontrol;
	}

	public void setOutboundserialnoqtycontrol(String outboundserialnoqtycontrol) {
		this.outboundserialnoqtycontrol = outboundserialnoqtycontrol;
	}

	public String getDefaultcartontype() {
		return defaultcartontype;
	}

	public void setDefaultcartontype(String defaultcartontype) {
		this.defaultcartontype = defaultcartontype;
	}

	public String getBreakcs() {
		return breakcs;
	}

	public void setBreakcs(String breakcs) {
		this.breakcs = breakcs;
	}

	public String getBreakea() {
		return breakea;
	}

	public void setBreakea(String breakea) {
		this.breakea = breakea;
	}

	public String getBreakip() {
		return breakip;
	}

	public void setBreakip(String breakip) {
		this.breakip = breakip;
	}

	public String getPrintmedicineqcreport() {
		return printmedicineqcreport;
	}

	public void setPrintmedicineqcreport(String printmedicineqcreport) {
		this.printmedicineqcreport = printmedicineqcreport;
	}

	public String getScanwhenqc() {
		return scanwhenqc;
	}

	public void setScanwhenqc(String scanwhenqc) {
		this.scanwhenqc = scanwhenqc;
	}

	public String getOverreceiving() {
		return overreceiving;
	}

	public void setOverreceiving(String overreceiving) {
		this.overreceiving = overreceiving;
	}

	public String getDefaultsupplierid() {
		return defaultsupplierid;
	}

	public void setDefaultsupplierid(String defaultsupplierid) {
		this.defaultsupplierid = defaultsupplierid;
	}

}