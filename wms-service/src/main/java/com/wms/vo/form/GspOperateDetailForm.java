package com.wms.vo.form;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/6
 */
@Data
public class GspOperateDetailForm implements Serializable {
    private String enterpriseId;
    private String operateId;
}