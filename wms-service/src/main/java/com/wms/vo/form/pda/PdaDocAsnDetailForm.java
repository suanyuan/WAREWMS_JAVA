package com.wms.vo.form.pda;

import com.wms.entity.BaseLotatt;

public class PdaDocAsnDetailForm extends BaseLotatt {

    private String warehouseid; //pda传参

    private String asnno;

    private int asnlineno;//行号

    private String receivedqty;//收货件数

    public int getAsnlineno() {
        return asnlineno;
    }

    public void setAsnlineno(int asnlineno) {
        this.asnlineno = asnlineno;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getAsnno() {
        return asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno;
    }

    public String getReceivedqty() {
        return receivedqty;
    }

    public void setReceivedqty(String receivedqty) {
        this.receivedqty = receivedqty;
    }
}
