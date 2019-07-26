package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocOrderDetailsVO {

	private String orderno;
	private Double orderlineno;
	private String customerid;
	private String sku;
	private String linestatus;
	private String lotnum;
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
	private String lotatt13;
	private String lotatt14;
	private String lotatt15;
	private String lotatt16;
	private String lotatt17;
	private String lotatt18;
	private String pickzone;
	private String location;
	private String traceid;
	private Double qtyordered;
	private Double qtysoftallocated;
	private Double qtyallocated;
	private Double qtypicked;
	private Double qtyshipped;
	private String uom;
	private String packid;
	private String softallocationrule;
	private String allocationrule;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String notes;
	private Double qtyorderedEach;
	private Double qtysoftallocatedEach;
	private Double qtyallocatedEach;
	private Double qtypickedEach;
	private Double qtyshippedEach;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private String rotationid;
	private Double netweight;
	private Double grossweight;
	private Double cubic;
	private Double price;
	private String alternativesku;
	private Double kitreferenceno;
	private String dEdi01;
	private String dEdi02;
	private String dEdi03;
	private String dEdi04;
	private String dEdi05;
	private String dEdi06;
	private String dEdi07;
	private String dEdi08;
	private Double dEdi09;
	private Double dEdi10;
	private String orderlinereferenceno;
	private String dEdi11;
	private String dEdi12;
	private String dEdi13;
	private String dEdi14;
	private String dEdi15;
	private String dEdi16;
	private String dEdi17;
	private String dEdi18;
	private String dEdi19;
	private String dEdi20;
	private String kitsku;
	private String erpcancelflag;
	private String userdefine6;
	private String zonegroup;
	private String locgroup1;
	private String locgroup2;
	private String comminglesku;
	private String onestepallocation;
	private String orderlotcontrol;
	private String fullcaselotcontrol;
	private String piecelotcontrol;
	private Double referencelineno;
	private String salesorderno;
	private String salesorderlineno;
	private Double qtyreleased;
	private String freegift;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Double getOrderlineno() {
		return orderlineno;
	}

	public void setOrderlineno(Double orderlineno) {
		this.orderlineno = orderlineno;
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

	public String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
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

	public String getPickzone() {
		return pickzone;
	}

	public void setPickzone(String pickzone) {
		this.pickzone = pickzone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public Double getQtyordered() {
		return qtyordered;
	}

	public void setQtyordered(Double qtyordered) {
		this.qtyordered = qtyordered;
	}

	public Double getQtysoftallocated() {
		return qtysoftallocated;
	}

	public void setQtysoftallocated(Double qtysoftallocated) {
		this.qtysoftallocated = qtysoftallocated;
	}

	public Double getQtyallocated() {
		return qtyallocated;
	}

	public void setQtyallocated(Double qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public Double getQtypicked() {
		return qtypicked;
	}

	public void setQtypicked(Double qtypicked) {
		this.qtypicked = qtypicked;
	}

	public Double getQtyshipped() {
		return qtyshipped;
	}

	public void setQtyshipped(Double qtyshipped) {
		this.qtyshipped = qtyshipped;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
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

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getQtyorderedEach() {
		return qtyorderedEach;
	}

	public void setQtyorderedEach(Double qtyorderedEach) {
		this.qtyorderedEach = qtyorderedEach;
	}

	public Double getQtysoftallocatedEach() {
		return qtysoftallocatedEach;
	}

	public void setQtysoftallocatedEach(Double qtysoftallocatedEach) {
		this.qtysoftallocatedEach = qtysoftallocatedEach;
	}

	public Double getQtyallocatedEach() {
		return qtyallocatedEach;
	}

	public void setQtyallocatedEach(Double qtyallocatedEach) {
		this.qtyallocatedEach = qtyallocatedEach;
	}

	public Double getQtypickedEach() {
		return qtypickedEach;
	}

	public void setQtypickedEach(Double qtypickedEach) {
		this.qtypickedEach = qtypickedEach;
	}

	public Double getQtyshippedEach() {
		return qtyshippedEach;
	}

	public void setQtyshippedEach(Double qtyshippedEach) {
		this.qtyshippedEach = qtyshippedEach;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getRotationid() {
		return rotationid;
	}

	public void setRotationid(String rotationid) {
		this.rotationid = rotationid;
	}

	public Double getNetweight() {
		return netweight;
	}

	public void setNetweight(Double netweight) {
		this.netweight = netweight;
	}

	public Double getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(Double grossweight) {
		this.grossweight = grossweight;
	}

	public Double getCubic() {
		return cubic;
	}

	public void setCubic(Double cubic) {
		this.cubic = cubic;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAlternativesku() {
		return alternativesku;
	}

	public void setAlternativesku(String alternativesku) {
		this.alternativesku = alternativesku;
	}

	public Double getKitreferenceno() {
		return kitreferenceno;
	}

	public void setKitreferenceno(Double kitreferenceno) {
		this.kitreferenceno = kitreferenceno;
	}

	public String getDEdi01() {
		return dEdi01;
	}

	public void setDEdi01(String dEdi01) {
		this.dEdi01 = dEdi01;
	}

	public String getDEdi02() {
		return dEdi02;
	}

	public void setDEdi02(String dEdi02) {
		this.dEdi02 = dEdi02;
	}

	public String getDEdi03() {
		return dEdi03;
	}

	public void setDEdi03(String dEdi03) {
		this.dEdi03 = dEdi03;
	}

	public String getDEdi04() {
		return dEdi04;
	}

	public void setDEdi04(String dEdi04) {
		this.dEdi04 = dEdi04;
	}

	public String getDEdi05() {
		return dEdi05;
	}

	public void setDEdi05(String dEdi05) {
		this.dEdi05 = dEdi05;
	}

	public String getDEdi06() {
		return dEdi06;
	}

	public void setDEdi06(String dEdi06) {
		this.dEdi06 = dEdi06;
	}

	public String getDEdi07() {
		return dEdi07;
	}

	public void setDEdi07(String dEdi07) {
		this.dEdi07 = dEdi07;
	}

	public String getDEdi08() {
		return dEdi08;
	}

	public void setDEdi08(String dEdi08) {
		this.dEdi08 = dEdi08;
	}

	public Double getDEdi09() {
		return dEdi09;
	}

	public void setDEdi09(Double dEdi09) {
		this.dEdi09 = dEdi09;
	}

	public Double getDEdi10() {
		return dEdi10;
	}

	public void setDEdi10(Double dEdi10) {
		this.dEdi10 = dEdi10;
	}

	public String getOrderlinereferenceno() {
		return orderlinereferenceno;
	}

	public void setOrderlinereferenceno(String orderlinereferenceno) {
		this.orderlinereferenceno = orderlinereferenceno;
	}

	public String getDEdi11() {
		return dEdi11;
	}

	public void setDEdi11(String dEdi11) {
		this.dEdi11 = dEdi11;
	}

	public String getDEdi12() {
		return dEdi12;
	}

	public void setDEdi12(String dEdi12) {
		this.dEdi12 = dEdi12;
	}

	public String getDEdi13() {
		return dEdi13;
	}

	public void setDEdi13(String dEdi13) {
		this.dEdi13 = dEdi13;
	}

	public String getDEdi14() {
		return dEdi14;
	}

	public void setDEdi14(String dEdi14) {
		this.dEdi14 = dEdi14;
	}

	public String getDEdi15() {
		return dEdi15;
	}

	public void setDEdi15(String dEdi15) {
		this.dEdi15 = dEdi15;
	}

	public String getDEdi16() {
		return dEdi16;
	}

	public void setDEdi16(String dEdi16) {
		this.dEdi16 = dEdi16;
	}

	public String getDEdi17() {
		return dEdi17;
	}

	public void setDEdi17(String dEdi17) {
		this.dEdi17 = dEdi17;
	}

	public String getDEdi18() {
		return dEdi18;
	}

	public void setDEdi18(String dEdi18) {
		this.dEdi18 = dEdi18;
	}

	public String getDEdi19() {
		return dEdi19;
	}

	public void setDEdi19(String dEdi19) {
		this.dEdi19 = dEdi19;
	}

	public String getDEdi20() {
		return dEdi20;
	}

	public void setDEdi20(String dEdi20) {
		this.dEdi20 = dEdi20;
	}

	public String getKitsku() {
		return kitsku;
	}

	public void setKitsku(String kitsku) {
		this.kitsku = kitsku;
	}

	public String getErpcancelflag() {
		return erpcancelflag;
	}

	public void setErpcancelflag(String erpcancelflag) {
		this.erpcancelflag = erpcancelflag;
	}

	public String getUserdefine6() {
		return userdefine6;
	}

	public void setUserdefine6(String userdefine6) {
		this.userdefine6 = userdefine6;
	}

	public String getZonegroup() {
		return zonegroup;
	}

	public void setZonegroup(String zonegroup) {
		this.zonegroup = zonegroup;
	}

	public String getLocgroup1() {
		return locgroup1;
	}

	public void setLocgroup1(String locgroup1) {
		this.locgroup1 = locgroup1;
	}

	public String getLocgroup2() {
		return locgroup2;
	}

	public void setLocgroup2(String locgroup2) {
		this.locgroup2 = locgroup2;
	}

	public String getComminglesku() {
		return comminglesku;
	}

	public void setComminglesku(String comminglesku) {
		this.comminglesku = comminglesku;
	}

	public String getOnestepallocation() {
		return onestepallocation;
	}

	public void setOnestepallocation(String onestepallocation) {
		this.onestepallocation = onestepallocation;
	}

	public String getOrderlotcontrol() {
		return orderlotcontrol;
	}

	public void setOrderlotcontrol(String orderlotcontrol) {
		this.orderlotcontrol = orderlotcontrol;
	}

	public String getFullcaselotcontrol() {
		return fullcaselotcontrol;
	}

	public void setFullcaselotcontrol(String fullcaselotcontrol) {
		this.fullcaselotcontrol = fullcaselotcontrol;
	}

	public String getPiecelotcontrol() {
		return piecelotcontrol;
	}

	public void setPiecelotcontrol(String piecelotcontrol) {
		this.piecelotcontrol = piecelotcontrol;
	}

	public Double getReferencelineno() {
		return referencelineno;
	}

	public void setReferencelineno(Double referencelineno) {
		this.referencelineno = referencelineno;
	}

	public String getSalesorderno() {
		return salesorderno;
	}

	public void setSalesorderno(String salesorderno) {
		this.salesorderno = salesorderno;
	}

	public String getSalesorderlineno() {
		return salesorderlineno;
	}

	public void setSalesorderlineno(String salesorderlineno) {
		this.salesorderlineno = salesorderlineno;
	}

	public Double getQtyreleased() {
		return qtyreleased;
	}

	public void setQtyreleased(Double qtyreleased) {
		this.qtyreleased = qtyreleased;
	}

	public String getFreegift() {
		return freegift;
	}

	public void setFreegift(String freegift) {
		this.freegift = freegift;
	}

}