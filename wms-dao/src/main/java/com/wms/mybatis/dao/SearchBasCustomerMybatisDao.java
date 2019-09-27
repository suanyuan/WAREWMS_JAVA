package com.wms.mybatis.dao;


import com.wms.entity.SearchBasCustomer;
import com.wms.entity.SearchInvLocation;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface SearchBasCustomerMybatisDao extends BaseDao {
//委托客户
	public List<SearchBasCustomer> querySearchCustomer(MybatisCriteria criteria);
//库存信息
	public List<SearchInvLocation> querySearchInvLocation(MybatisCriteria criteria);
	public int querySearchInvLocationCount(MybatisCriteria criteria);



}
