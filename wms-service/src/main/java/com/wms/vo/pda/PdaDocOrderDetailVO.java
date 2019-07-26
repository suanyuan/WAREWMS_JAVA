package com.wms.vo.pda;

import com.wms.entity.BasSku;
import com.wms.entity.InvLotAtt;
import com.wms.vo.DocOrderDetailVO;
import lombok.Data;

@Data
public class PdaDocOrderDetailVO extends DocOrderDetailVO {

    private BasSku basSku;

    private InvLotAtt invLotAtt;
}
