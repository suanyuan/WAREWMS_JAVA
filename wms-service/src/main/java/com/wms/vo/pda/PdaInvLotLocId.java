package com.wms.vo.pda;

import com.wms.entity.BasPackage;
import com.wms.entity.BasSku;
import com.wms.entity.InvLotLocId;
import lombok.Data;

@Data
public class PdaInvLotLocId extends InvLotLocId {

    private BasSku basSku;

    private BasPackage basPackage;

    private double sameBatchNum;//同批件数
}
