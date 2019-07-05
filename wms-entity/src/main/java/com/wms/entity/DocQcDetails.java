package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocQcDetails  implements Serializable {

	private String qcno;

	private String qclineno;

	private String linestatus;

	private String asnno;

	private Double asnlineno;

	private String customerid;

	private String sku;

	private String lotnum;

	private Double asnqtyExpected;

	private Double qcqtyExpected;

	private Double qcqtyCompleted;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String qcdescr;

	private String qcresult;

	private String filecontent;

	private String notes;

	private java.util.Date addtime;

	private String addwho;

	private java.util.Date edittime;

	private String editwho;

	private String packid;

	private String transactionid;

}
