package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.BasCustomer;
import com.wms.entity.GspReceivingAddress;
import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface BasCustomerMybatisDao extends BaseDao {

	public List<BasCustomer> queryCustomerTypeByAll();

	public List<BasCustomer> queryOperateTypeByAll();

	public String basCustomerCheck(Map<String, Object> map);

	GspReceivingAddress getReceivingAddressInfo(String receivingAddressId);
}
