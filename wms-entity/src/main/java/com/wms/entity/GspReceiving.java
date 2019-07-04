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



	private String isUse;

	private String isReturn;

}
