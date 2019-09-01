package com.wms.vo.pda;

import com.wms.entity.BasPackage;
import com.wms.entity.BasSku;
import com.wms.entity.InvLotAtt;
import lombok.Data;

@Data
public class PdaInventoryVO {

    private BasSku basSku;

    private BasPackage basPackage;

    private InvLotAtt invLotAtt;

    private int availablePiece;//可用库存 件数

    private int availableNumber;//可用库存 数量
}
