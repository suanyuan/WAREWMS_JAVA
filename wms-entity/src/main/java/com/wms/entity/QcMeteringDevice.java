package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class QcMeteringDevice  implements Serializable {

	private String calId;

	private String calName;

	private String calNumber;

	private String calTerm;

	private String calCardUrl;

	private String createId;

	private java.util.Date createDate;

	private String editId;

	private java.util.Date editDate;

	private String activeFlag;

}
