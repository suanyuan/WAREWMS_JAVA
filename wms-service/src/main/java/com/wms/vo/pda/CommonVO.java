package com.wms.vo.pda;

import com.wms.entity.BasSku;
import lombok.Data;

@Data
public class CommonVO {

    /**
     * 是否成功获取BasSku
     */
    private boolean success;

    /**
     * 如果获取失败，返回提示语
     */
    private String message;

    /**
     * 是否序列号管理
     */
    private boolean isSerialManagement;

    /**
     * 生产批号
     */
    private String batchNum;

    /**
     * 序列号
     */
    private String serialNum;

    /**
     * 产品档案
     */
    private BasSku basSku;
}
