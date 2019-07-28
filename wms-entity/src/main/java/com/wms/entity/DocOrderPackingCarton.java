package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocOrderPackingCarton extends BaseLotatt implements Serializable {

	private String orderno;

	private String traceid;

	private Integer cartonno;

	private String customerid;

	private String sku;

	private int qty;

	private String allocationdetailsid;

	private String lotnum;

	private String skudesce;//规格型号

    private String description;//复核说明

    private String conclusion;//复核结论

    private String editwho;

    private java.util.Date edittime;

	private String addwho;

	private java.util.Date addtime;

}
