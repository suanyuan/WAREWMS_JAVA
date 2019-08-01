package com.wms.mybatis.entity.pda;

import lombok.Data;

@Data
public class PdaOrderPackingForm {

    public final static String ACTION_RV = "RV";

    private String result;

    private String warehouseid;

    private String action;

    private String allocationdetailsid;

    private String userid;

    public PdaOrderPackingForm(String warehouseid, String action, String allocationdetailsid, String userid) {
        this.warehouseid = warehouseid;
        this.action = action;
        this.allocationdetailsid = allocationdetailsid;
        this.userid = userid;
    }
}
