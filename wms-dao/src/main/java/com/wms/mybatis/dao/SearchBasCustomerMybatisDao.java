package com.wms.mybatis.dao;


import com.wms.entity.*;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface SearchBasCustomerMybatisDao extends BaseDao {

    //委托客户
    List<SearchBasCustomer> querySearchCustomer(MybatisCriteria criteria);

    //货主商品
    List<CustomerProduct> queryCustomerProduct(MybatisCriteria criteria);

    int queryCustomerProductByCount(MybatisCriteria criteria);

    //库存信息
    List<SearchInvLocation> querySearchInvLocation(MybatisCriteria criteria);

    int querySearchInvLocationCount(MybatisCriteria criteria);

    //入库信息
    List<SearchEnterInvLocation> querySearchEnterInvLocation(MybatisCriteria criteria);

    int querySearchEnterInvLocationCount(MybatisCriteria criteria);

    //出库信息
    List<SearchOutInvLocation> querySearchOutInvLocation(MybatisCriteria criteria);

    int querySearchOutInvLocationCount(MybatisCriteria criteria);


    /**
     * 药监核查-历史数据
     */

    //历史委托客户
    List<SearchBasCustomer> querySearchBasCustomerHistory(MybatisCriteria criteria);

    int querySearchBasCustomerHistoryCount(MybatisCriteria criteria);

    //货主商品
    List<CustomerProduct> queryCustomerHistoryProduct(MybatisCriteria criteria);

    int queryCustomerProductHistoryByCount(MybatisCriteria criteria);

    //入库信息
    List<SearchEnterInvLocation> querySearchEnterInvLocationHistory(MybatisCriteria criteria);

    int querySearchEnterInvLocationHistoryCount(MybatisCriteria criteria);

    //出库信息
    List<SearchOutInvLocation> querySearchOutInvLocationHistory(MybatisCriteria criteria);

    int querySearchOutInvLocationHistoryCount(MybatisCriteria criteria);

}
