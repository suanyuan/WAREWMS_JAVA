package com.wms.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.utils.ResourceUtil;

public class SfcUserLoginUtil {

	public static SfcUserLogin getLoginUser(){
		return (SfcUserLogin) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute(ResourceUtil.getUserInfo());
	}
}
