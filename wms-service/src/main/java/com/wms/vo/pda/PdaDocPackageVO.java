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
     */
    private DocOrderPackingCarton docOrderPackingCarton;
}
