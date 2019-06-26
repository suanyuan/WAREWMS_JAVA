package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocMovementHeader  implements Serializable {

	private String mdocno;

	private String mdoctype;

	private String customerid;

	private java.sql.Date mdoccreationtime;

	private java.sql.Date movementtime;

	private String reasoncode;

	private String reason;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private String status;

	private String zonegroup;

	private String fmwarehouseid;

	private String towarehouseid;

	private String userdefinea;

	private String userdefineb;

	private String source;

	private String sourceno;

	private String fromloc;

}
