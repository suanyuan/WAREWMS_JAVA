package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class BasCodes  implements Serializable {

	private String codeid;

	private String code;

	private String codenameC;

	private String codenameE;

	private Long showSequence;

	private String udf1;

	private String udf2;

	private String udf3;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private String udfOprChk;

}
