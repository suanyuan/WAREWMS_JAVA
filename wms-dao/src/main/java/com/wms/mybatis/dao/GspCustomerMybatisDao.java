package com.wms.mybatis.dao;

import com.wms.entity.GspCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspCustomerDao<br>
 */
public interface GspCustomerMybatisDao extends BaseDao {
    Long updateFirstState(@Param("no") String no, @Param("state") String state);

    Integer queryGspCustomerByClientNo(@Param("clientNo") String clientNo);

    public void deleteF(Object id);

    public <T> T queryByEnterpriseId(Object id);

    public <T> List<T> queryCustomerNoByAll();

}
