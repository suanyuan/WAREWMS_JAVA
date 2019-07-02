package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocTransPutway  implements Serializable {

	private String putwayno;

	private String putwaystatus;

	private String asnno;

	private Double asnlineno;

	private String customerid;

	private String sku;

	private String lotnum;

	private Double asnqtyExpected;

	private Double putwayqtyExpected;

	private Double putwayqtyCompleted;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String notes;

	private java.util.Date addtime;

	private String addwho;

	private java.util.Date edittime;

	private String editwho;

	private String packid;

	private String transactionid;

}
