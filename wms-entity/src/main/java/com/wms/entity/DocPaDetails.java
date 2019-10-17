package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class DocPaDetails  implements Serializable {

	private String pano;

	private String palineno;

	private String linestatus;

	private String asnno;

	private int asnlineno;

	private String customerid;

	private String sku;  //产品代码

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

    /**
     * 批次属性
     */
	private InvLotAtt invLotAtt;

	List<String> lotnumList;

	//明细
	private String reservedfield01;//产品名称
	private String descrs;//规格型号
	private String lotatt01;//生产日期
	private String lotatt02;//有效期/失效日期
	private String lotatt04;//生产批号
	private String lotatt05;//序列号
	private Double putwayqtynum;//待上数量
	private Double putwayqtynums = 0.0;//待上数量合计
	private Double putwayqtyExpecteds = 0.0;//待上件数合计
	private String codename;//单位
	private Integer index;//序号
}
