package com.wms.mybatis.dao;

import java.util.Map;

import com.wms.entity.BasSku;
import com.wms.mybatis.dao.BaseDao;
import com.wms.query.BasSkuQuery;
/**
 * 
 * <br>
 * <b>功能：</b>BasSkuDao<br>
 */
public interface BasSkuMybatisDao extends BaseDao {

	public String basSkuCheck(Map<String, Object> map);

	public BasSku queryBySkuInfo(BasSkuQuery skuQuery);
	
}
