package com.wms.vo.pda;

import com.wms.entity.BasSku;
import com.wms.entity.DocQcDetails;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import lombok.Data;

import java.util.List;

@Data
public class PdaDocQcDetailVO extends DocQcDetails {

    private InvLotAtt invLotAtt;//批次属性

    private BasSku basSku;//规格、sku

    private List<PdaGspProductRegister> productRegisterList;//产品注册证list,每个注册证携带生产厂家信息

    private GspEnterpriseInfo enterpriseInfo;//当前批次-产品注册证对应的生产厂家信息
}
