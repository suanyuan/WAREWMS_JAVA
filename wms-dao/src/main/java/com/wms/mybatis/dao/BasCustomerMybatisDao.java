package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.BasCustomer;
import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface BasCustomerMybatisDao extends BaseDao {

	public List<BasCustomer> queryCustomerTypeByAll();
	
	public String basCustomerCheck(Map<String, Object> map);
	
}
