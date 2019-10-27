package com.wms.entity.sfExpress;

import com.wms.entity.order.OrderHeaderForNormal;
import lombok.Data;

public class SFOrderHeader extends OrderHeaderForNormal {

    private String j_company;
    private String j_contact;
    private String j_tel;
    private String custid;//月结账号

    public String getJ_company() {
        return j_company;
    }

    public void setJ_company(String j_company) {
        this.j_company = j_company;
    }

    public String getJ_contact() {
        return j_contact;
    }

    public void setJ_contact(String j_contact) {
        this.j_contact = j_contact;
    }

    public String getJ_tel() {
        return j_tel;
    }

    public void setJ_tel(String j_tel) {
        this.j_tel = j_tel;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }
}
