package com.wms.mybatis.entity;

import lombok.Data;

@Data
public class CleanInventory {

    private String warehouseid;

    private String language;

    private String userid;

    private String returnCode;

    public CleanInventory(String warehouseid, String language, String userid) {
        this.warehouseid = warehouseid;
        this.language = language;
        this.userid = userid;
    }
}
