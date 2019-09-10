package com.wms.vo.pda;

import com.wms.entity.BasSku;
import com.wms.entity.InvLotAtt;
import lombok.Data;

@Data
public class PdaDocPaDetailVO {

    private String pano;
    private String palineno;
    private String linestatus;
    private String asnno;
    private Double asnlineno;
    private String customerid;
    private String sku;
    private String lotnum;
    private Double asnqtyExpected;
    private Double putwayqtyExpected;
    private Double putwayqtyCompleted;
    private String userdefine1;//上架库位
    private String userdefine2;//效期
    private String userdefine3;//批号
    private String userdefine4;//序号
    private String userdefine5;//待上架质量状态（合格 || 待检）
    private String notes;
    private String addtime;
    private String addwho;
    private String edittime;
    private String editwho;
    private String packid;
    private String transactionid;

    private int alertflag;//是否需要提示同批号日期数据不同

    private BasSku basSku;

    private InvLotAtt invLotAtt;//批次属性数据
}
