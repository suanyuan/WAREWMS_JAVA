package com.wms.vo.pda;

import com.wms.entity.DocPkRecords;
import com.wms.entity.InvLotAtt;
import lombok.Data;

@Data
public class PdaActAllocationDetailVO {

    private String allocationdetailsid;

    private String orderno;

    private Double orderlineno;

    private String customerid;

    private String sku;

    private String lotnum;

    private String uom;

    private String location;

    private Double qty;

    private Double qtyEach;

    private String packid;

    private String status;

    private String printflag;

    private InvLotAtt invLotAtt;

    private DocPkRecords docPkRecords;
}
