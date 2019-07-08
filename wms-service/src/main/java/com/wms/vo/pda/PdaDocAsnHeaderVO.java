package com.wms.vo.pda;

import lombok.Data;

import java.util.Date;

@Data
public class PdaDocAsnHeaderVO {

    /**
     * 预期到货通知编号 ≈ 收货任务单号
     */
    private String asnno;

    /**
     * ASN创建时间
     */
    private Date asncreationtime;

    /**
     * 预期到货通知状态
     */
    private String asnstatus;

    /**
     * 预期到货通知类型
     */
    private String asntype;

    /**
     * 客户单号
     */
    private String asnreference1;

    /**
     * 仓库id
     */
    private String warehouseid;

    private String customerid;
}
