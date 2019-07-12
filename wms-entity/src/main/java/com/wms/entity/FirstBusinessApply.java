package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class FirstBusinessApply  implements Serializable {

	private String applyId;

	private String clientId;

	private String supplierId;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String isUse;

	private String firstState;

}
