package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocOrderPackingCartonInfo  implements Serializable {

	private String orderno;

	private String traceid;

	private Integer cartonno;

	private String cartontype;

	private Double grossweight;

	private Double cube;

	private int packingflag;

	private String addwho;

	private java.util.Date addtime;

}
