package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

	private Date addtime;

	private String addwho;

	private Date edittime;

	private String editwho;

	private String udfOprChk;

}
