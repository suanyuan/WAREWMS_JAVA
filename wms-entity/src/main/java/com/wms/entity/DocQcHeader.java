package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class DocQcHeader  implements Serializable {

	private String qcno;

	private String pano;

	private String customerid;

	private String qcreference1;

	private String qcreference2;

	private String qcreference3;

	private String qcreference4;

	private String qcreference5;

	private String qctype;

	private String qcstatus;

	private java.util.Date qccreationtime;

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

	private String qcPrintFlag;

	private String warehouseid;

	//打印明细
	private List<DocQcDetails> detls;
	private String lotatt03;//入库日期
	private String lotatt14;//入库单号
	private String descrC;//供应商名称
	private String userdefine1Temp;

}
