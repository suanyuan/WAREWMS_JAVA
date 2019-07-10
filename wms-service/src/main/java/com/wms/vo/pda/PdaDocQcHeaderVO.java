package com.wms.vo.pda;

import lombok.Data;

@Data
public class PdaDocQcHeaderVO {

    /**
     * 验收任务单号
     */
    private String qcno;

    /**
     * 上架任务单号
     */
    private String pano;

    /**
     * 客户id
     */
    private String customerid;

    /**
     * 验收状态
     */
    private String qcstatus;

    /**
     * 仓库id
     */
    private String warehouseid;
}
