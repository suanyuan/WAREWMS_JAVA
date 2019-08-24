package com.wms.vo;

import com.wms.entity.BasCustomer;
import com.wms.entity.BasPackage;
import com.wms.entity.BasSku;
import com.wms.entity.InvLotAtt;
import lombok.Data;

@Data
public class DocMtDetailsVO {

	private String mtno;
	private long mtlineno;
	private String linestatus;
	private String customerid;
	private String sku;
	private String inventoryage;
	private String locationid;
	private String lotnum;
	private double mtqtyExpected;
	private double mtqtyEachExpected;
	private double mtqtyCompleted;
	private double mtqtyEachCompleted;
	private String uom;
	private long checkFlag;
	private String conclusion;
	private java.util.Date conversedate;
	private String conversewho;
	private String remark;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;

	private BasCustomer basCustomer;

	private InvLotAtt invLotAtt;

	private BasSku basSku;

	private BasPackage basPackage;
}