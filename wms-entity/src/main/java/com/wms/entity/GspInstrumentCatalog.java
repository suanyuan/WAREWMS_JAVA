package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspInstrumentCatalog  implements Serializable {

	private String instrumentCatalogId;

	private String instrumentCatalogNo;

	private String instrumentCatalogName;

	private String instrumentCatalogRemark;

	private String classifyId;

	private String version;

	private String createId;

	private java.util.Date cretaeDate;

	private String editId;

	private java.util.Date editDate;

	private String isUse;

}
