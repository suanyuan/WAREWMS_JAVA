package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class BasGtnLotatt  implements Serializable {

	private String lotnum;

	private String customerid;

	private String sku;

	private String lotatt02;

	private String lotatt04;

	private String lotatt05;

	private java.sql.Date addtime;

	private String addasnno;

}
