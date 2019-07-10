package com.wms.mybatis.entity.pda;

import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspProductRegister;
import lombok.Data;

@Data
public class PdaGspProductRegister extends GspProductRegister {

    //生产厂家信息
    private GspEnterpriseInfo enterpriseInfo;
}
