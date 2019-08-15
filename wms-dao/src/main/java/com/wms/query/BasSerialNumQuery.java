package com.wms.query;

import lombok.Data;

@Data
public class BasSerialNumQuery implements IQuery {

	private String serialNum;
	private String batchNum;
	private String materialNum;
	private String expireDate;
	private String productDate;
	private String batchFlag;
	private String outFlag; //是否出库0,1
	private String uom;
	private String purchaseOrder;
	private String packageNum;
	private String deliveryNum;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String addwho;
	private String addtime;
	private String editwho;
	private String edittime;

    public BasSerialNumQuery() {
    }

    //Query
    public BasSerialNumQuery(String serialNum) {
        this.serialNum = serialNum;
    }
}