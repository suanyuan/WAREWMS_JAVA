package com.wms.mybatis.dao;


import com.wms.entity.*;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface SearchBasCustomerMybatisDao extends BaseDao {
//委托客户
	public List<SearchBasCustomer> querySearchCustomer(MybatisCriteria criteria);
//货主商品
    public List<CustomerProduct> queryCustomerProduct(MybatisCriteria criteria);
	public int queryCustomerProductByCount(MybatisCriteria criteria);
//库存信息
	public List<SearchInvLocation> querySearchInvLocation(MybatisCriteria criteria);
	public int querySearchInvLocationCount(MybatisCriteria criteria);
//入库信息
	public List<SearchEnterInvLocation> querySearchEnterInvLocation(MybatisCriteria criteria);
	public int querySearchEnterInvLocationCount(MybatisCriteria criteria);
//出库信息
	public List<SearchOutInvLocation> querySearchOutInvLocation(MybatisCriteria criteria);
	public int querySearchOutInvLocationCount(MybatisCriteria criteria);




/**
 * 药监核查-历史数据
 */


//历史委托客户
   public List<SearchBasCustomer> querySearchBasCustomerHistory(MybatisCriteria criteria);
   public int querySearchBasCustomerHistoryCount(MybatisCriteria criteria);

}
