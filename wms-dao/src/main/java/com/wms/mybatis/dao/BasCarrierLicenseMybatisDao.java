package com.wms.mybatis.dao;


import com.wms.entity.BasCarrierLicense;

/**
 * 
 * <br>
 * <b>功能：</b>BasCarrierLicenseDao<br>
 */
public interface BasCarrierLicenseMybatisDao extends BaseDao {
	public BasCarrierLicense queryByEnterId(String enterpriseId);
	
}
