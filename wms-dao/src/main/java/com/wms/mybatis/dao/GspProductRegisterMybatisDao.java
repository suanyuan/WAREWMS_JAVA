package com.wms.mybatis.dao;

import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GSPProductRegisterDao<br>
 */
public interface GspProductRegisterMybatisDao extends BaseDao {

    /**
     * 通过注册证号查询
     * @param registerNo 注册证编号
     * @return ~
     */
//    GspProductRegister queryByNo(@Param("registerNo") String registerNo);

    /**
     * 通过bas_sku_history.reservedfiled03 匹配使用过的注册证
     * @param sku ~
     * @param customerid ~
     * @return ~
     */
    List<PdaGspProductRegister> queryHistoryRegister(@Param("sku") String sku, @Param("customerid") String customerid);
}
