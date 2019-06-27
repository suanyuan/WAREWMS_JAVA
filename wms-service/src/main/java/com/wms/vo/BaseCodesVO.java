package com.wms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/27
 */
@Data
public class BaseCodesVO implements Serializable {
    private String code;
    private String name;
}