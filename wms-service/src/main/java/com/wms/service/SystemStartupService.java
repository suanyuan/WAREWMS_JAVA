package com.wms.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

/**
 * 系統啟動時執行
 * @author OwenHuang
 * @Date 2013/5/24
 */
@Service("systemStartupService")
public class SystemStartupService {
	
	@PostConstruct
	public void init(){
		System.out.println("系統啟動執行一次");
	}
	
}
