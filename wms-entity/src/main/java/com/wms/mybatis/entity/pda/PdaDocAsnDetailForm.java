package com.wms.mybatis.entity.pda;

import com.wms.entity.BaseLotatt;

import java.math.BigDecimal;

public class PdaDocAsnDetailForm extends BaseLotatt {

    private String result = "";//procedure执行结果

    private String warehouseid; //pda传参
    private String asnno;
    private long asnlineno;//行号
    private String receivedqty;//收货件数

    private String receivinglocation;
    private String receivingtime;
    private String customerid;
    private String sku;
    private BigDecimal totalcubic;
    private BigDecimal totalgrossweight;
    private BigDecimal totalprice;
    private String editwho;

    public long getAsnlineno() {
        return asnlineno;
    }

    public void setAsnlineno(long asnlineno) {
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReceivinglocation() {
        return receivinglocation;
    }

    public void setReceivinglocation(String receivinglocation) {
        this.receivinglocation = receivinglocation;
    }

    public String getReceivingtime() {
        return receivingtime;
    }

    public void setReceivingtime(String receivingtime) {
        this.receivingtime = receivingtime;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getTotalcubic() {
        return totalcubic;
    }

    public void setTotalcubic(BigDecimal totalcubic) {
        this.totalcubic = totalcubic;
    }

    public BigDecimal getTotalgrossweight() {
        return totalgrossweight;
    }

    public void setTotalgrossweight(BigDecimal totalgrossweight) {
        this.totalgrossweight = totalgrossweight;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public String getEditwho() {
        return editwho;
    }

    public void setEditwho(String editwho) {
        this.editwho = editwho;
    }
}
