package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.BasCustomer;
import com.wms.entity.GspReceivingAddress;
import com.wms.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

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

	public int  selectBySelective(Object id);


	//public void delectByCustomerTypeAndEnterpriseId(Object id);

	public void goon(Object id);
	public BasCustomer queryByenterId(Object id);
	public BasCustomer queryByCustomerId(Object id);

	public void deleteBascustomer(@Param("enterpriseId") String enterpriseId,@Param("customerType") String customerType);
	public void deleteByEnterpriseId(Object id);
	void deleteBascustomerByCustomerID(@Param("customerid") String customerid,@Param("customerType") String customerType);

	BasCustomer selectByIdType(Object id);

	BasCustomer selectByIdTypeActiveFlag(Object id);
	BasCustomer selectSupplierByIdTypeActiveFlag(Object id);


	BasCustomer queryByIdType(@Param("customerId") String customerId,@Param("customerType") String customerType);

	List<BasCustomer> querySupplierByCustomer(@Param("customerId") String customerId);
}
