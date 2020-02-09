package com.wms.query;

import com.wms.query.pda.PdaDocPkQuery;
import lombok.Data;

@Data
public class ActAllocationDetailsQuery implements IQuery {

    private String allocationdetailsid;

    private String orderno;

    private Double orderlineno;

    private Double skulineno;

    private String customerid;

    private String sku;

    private String lotnum;

    private String uom;

    private String location;

    private Double qty;

    private String traceid;

    private Double qtyEach;

    private String packid;

    private String waveno;

    private String status;

    private String addwho;

    private java.util.Date addtime;

    private String editwho;

    private java.util.Date edittime;

    private Double qtypickedEach;

    private Double qtyshippedEach;

    private String notes;

    private String picktolocation;

    private String picktotraceid;

    private java.util.Date pickedtime;

    private String pickedwho;

    /**
     * 用作是否包装复核完成
     */
    private String packflag;

    private String checkwho;

    private java.util.Date checktime;

    private java.util.Date shipmenttime;

    private String reasoncode;

    private String shipmentwho;

    private String softallocationdetailsid;

    private Double cubic;

    private Double grossweight;

    private Double netweight;

    private Double price;

    private String sortationlocation;

    private String ordernoOld;

    private Double orderlinenoOld;

    private String allocationdetailsidOld;

    /**
     * 用作是否拣货完成
     */
    private String printflag;

    private String contrainerid;

    private String doublecheckby;

    private String shipmentconfirmby;

    private Double cartonseqno;

    private String dropid;

    private String pickingtransactionid;

    private String cartonid;

    private String palletize;

    private String workstation;

    private String udfprintflag1;

    //---------

    private String lotatt02;

    private String lotatt04;

    private String lotatt05;

    public ActAllocationDetailsQuery() {
    }

    /**
     * 拣货查询分配明细的查询条件
     */
    public ActAllocationDetailsQuery(PdaDocPkQuery query) {

        this.orderno = query.getOrderno();
        this.customerid = query.getCustomerid();
        this.sku = query.getSku();
        this.lotatt04 = query.getLotatt04();
        this.lotatt05 = query.getLotatt05();
        this.location = query.getLocationid();
        this.printflag = "0";
    }
}
