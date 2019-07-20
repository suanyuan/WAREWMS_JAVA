package com.wms.mybatis.entity.pda;

import lombok.Data;

@Data
public class PdaDocQcStatusForm {

    private String qcno;//单号

    private String qcstatus;

    private String editwho;
}
