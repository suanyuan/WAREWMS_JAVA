package com.wms.mybatis.dao;


import com.wms.entity.CustomerProduct;
import com.wms.entity.SearchBasCustomer;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface CustomerProductMybatisDao extends BaseDao {

//	public List<SearchBasCustomer> querySearchCustomer(MybatisCriteria criteria);

	public List<CustomerProduct> queryCustomerProduct(MybatisCriteria criteria);
	public int queryCustomerProductByCount(MybatisCriteria criteria);



}
