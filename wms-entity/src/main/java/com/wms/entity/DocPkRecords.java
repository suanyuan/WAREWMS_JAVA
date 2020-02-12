package com.wms.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class DocPkRecords implements Serializable {

    private String orderno;
    private long pklineno;
    private String customerid;
    private String sku;
    private String skudesce;
    private long pickedqty;
    private long pickedqtyEach;
    private String allocationdetailsid;
    private String locationid;
    private String lotnum;
    private String addwho;
    private java.sql.Timestamp addtime;
    private String editwho;
    private java.sql.Timestamp edittime;

    public DocPkRecords() {
    }

    /**
     * 创建拣货明细内容
     *
     * @param actAllocationDetails 分配明细
     * @param basSku               产品档案
     * @param maxPklineno          最大行号
     * @param basPackage           包装规格
     * @param pickQty              拣货件数，默认为1
     */
    public DocPkRecords(ActAllocationDetails actAllocationDetails, BasSku basSku, int maxPklineno, BasPackage basPackage, int pickQty) {

        this.orderno = actAllocationDetails.getOrderno();
        this.customerid = actAllocationDetails.getCustomerid();
        this.sku = actAllocationDetails.getSku();
        this.allocationdetailsid = actAllocationDetails.getAllocationdetailsid();
        this.lotnum = actAllocationDetails.getLotnum();
//        this.addwho = ?
        this.pklineno = maxPklineno + 1;
        this.skudesce = basSku.getDescrE();
        this.pickedqty = pickQty;
        this.pickedqtyEach = basPackage.getQty1().intValue();
        this.locationid = actAllocationDetails.getLocation();
    }
}
