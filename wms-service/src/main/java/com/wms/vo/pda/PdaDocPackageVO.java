package com.wms.vo.pda;

import com.wms.entity.ActAllocationDetails;
import com.wms.entity.BasSku;
import com.wms.entity.DocOrderPackingCarton;
import com.wms.entity.InvLotAtt;
import com.wms.entity.order.OrderDetailsForNormal;
import lombok.Data;

@Data
public class PdaDocPackageVO {

    /**
     * 箱号
     */
    String cartonNum;

    /**
     * 行号
     */
    int packlineno;

    /**
     * 记录本次扫码是否是序列号扫码出库类型，是的话就返回给前端、移动端，用作包装复核提交中进行序列号记录
     */
    String serialNum;

    /**
     * 分配明细
     */
    private ActAllocationDetails actAllocationDetails;

    /**
     * 分配明细对应的出库明细
     */
    private OrderDetailsForNormal orderDetailsForNormal;

    /**
     * 批次属性
     */
    private InvLotAtt invLotAtt;

    /**
     * 产品信息(是否需要双证、产品合格证)
     */
    private BasSku basSku;

    /**
     * 如果此产品已经包装复核合格过了，会有
     * **************************
     * ******* Deprecated *******
     * **************************
     */
    private DocOrderPackingCarton docOrderPackingCarton;
}
