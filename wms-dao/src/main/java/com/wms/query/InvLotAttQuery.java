package com.wms.query;

import com.wms.entity.InvLotAtt;
import lombok.Data;

@Data
public class InvLotAttQuery implements IQuery {

	private String lotnum;
	private String customerid;
	private String sku;
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
    private String lotatt13;//双证
    private String lotatt14;//入库单号
    private String lotatt15;
    private String lotatt16;
    private String lotatt17;
    private String lotatt18;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String receivingtime;
	private String qcreportfilename;

    /**
     * 批量验收的时候使用的，参考DocQcDetailService.configAllQc
     * @param invLotAtt 批次
     */
    public InvLotAttQuery(InvLotAtt invLotAtt) {
        this.sku = invLotAtt.getSku();
        this.customerid = invLotAtt.getCustomerid();
        this.lotatt01 = invLotAtt.getLotatt01();
        this.lotatt02 = invLotAtt.getLotatt02();
        this.lotatt03 = invLotAtt.getLotatt03();
        this.lotatt04 = invLotAtt.getLotatt04();
        this.lotatt05 = invLotAtt.getLotatt05();
        this.lotatt06 = invLotAtt.getLotatt06();
        this.lotatt07 = invLotAtt.getLotatt07();
        this.lotatt08 = invLotAtt.getLotatt08();
        this.lotatt09 = invLotAtt.getLotatt09();
        this.lotatt10 = invLotAtt.getLotatt10();
        this.lotatt11 = invLotAtt.getLotatt11();
        this.lotatt12 = invLotAtt.getLotatt12();
        this.lotatt13 = invLotAtt.getLotatt13();
        this.lotatt14 = invLotAtt.getLotatt14();
        this.lotatt15 = invLotAtt.getLotatt15();
        this.lotatt16 = invLotAtt.getLotatt16();
        this.lotatt17 = invLotAtt.getLotatt17();
        this.lotatt18 = invLotAtt.getLotatt18();
    }
}