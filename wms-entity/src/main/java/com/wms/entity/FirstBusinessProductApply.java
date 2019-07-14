package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class FirstBusinessProductApply  implements Serializable {

	private String productApplyId;

	private String applyId;

	private String specsId;

	private String isCheck;

	private String isOperate;

	private String isCold;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String isUse;

	private String firstState;

}
