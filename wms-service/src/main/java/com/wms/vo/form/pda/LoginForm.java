package com.wms.vo.form.pda;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/8/28
 */
@Getter
@Setter
public class LoginForm {
    private String userId;
    private String pwd;
    private String wareHouseId;
    private String version;
}