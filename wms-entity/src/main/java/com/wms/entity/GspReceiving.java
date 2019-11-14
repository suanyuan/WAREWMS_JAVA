package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspReceiving  implements Serializable {

	private String receivingId;

	private String enterpriseId;

	private String clientId;

	private String supplierId;

	private String isCheck;

	private String createId;

	private String createDate;

	private String editId;

	private String editDate;



	private String firstState;



	private String isUse;

	private String isReturn;
	private String enterpriseNo;
	private String enterpriseName;
	private String shorthandName;
	private String enterpriseType;
	private String customerid;

	private String clientName;
	private String receivingAllClient; //收货单位对应货主

}
