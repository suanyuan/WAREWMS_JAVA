package com.wms.mybatis.dao;


import com.wms.entity.SearchBasCustomer;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface SearchBasCustomerMybatisDao extends BaseDao {

	public List<SearchBasCustomer> querySearchCustomer(MybatisCriteria criteria);




}
