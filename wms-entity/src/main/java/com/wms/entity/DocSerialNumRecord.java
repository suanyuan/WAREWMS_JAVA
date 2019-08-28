package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class DocSerialNumRecord  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String customerid;

	private int cartonNo;

	private String soreference;

	private String orderNo;

	private String batchNum;

	private String serialNum;

	private Date addtime;

	private String addwho;

	private String userdefine1;

    private String userdefine2;

    private String userdefine3;

    private String userdefine4;

    private String userdefine5;

    private String skuName;

    private String addTimeSetting;


    public DocSerialNumRecord() {
    }

    public DocSerialNumRecord(String orderNo, String serialNum) {
        this.orderNo = orderNo;
        this.serialNum = serialNum;
    }

    public DocSerialNumRecord(String customerid, int cartonNo, String soreference, String orderNo, String batchNum, String serialNum, String addwho) {
        this.customerid = customerid;
        this.cartonNo = cartonNo;
        this.soreference = soreference;
        this.orderNo = orderNo;
        this.batchNum = batchNum;
        this.serialNum = serialNum;
        this.addwho = addwho;
    }
}
