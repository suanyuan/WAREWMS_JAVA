package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocPaHeader  implements Serializable {

	private String pano;

	private String asnno;

	private String customerid;

	private String pareference1;

	private String pareference2;

	private String pareference3;

	private String pareference4;

	private String pareference5;

	private String patype;

	private String pastatus;

	private java.util.Date pacreationtime;

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

	private String paPrintFlag;

	private String warehouseid;

}
