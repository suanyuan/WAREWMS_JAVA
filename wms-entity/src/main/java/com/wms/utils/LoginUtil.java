package com.wms.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wms.entity.UserLogin;
import com.wms.utils.ResourceUtil;

public class LoginUtil {

	public static UserLogin getLoginUser(){
		return (UserLogin) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute(ResourceUtil.getUserInfo());
	}
}
