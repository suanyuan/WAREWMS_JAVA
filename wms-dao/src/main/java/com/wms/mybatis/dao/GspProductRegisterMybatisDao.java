package com.wms.mybatis.dao;

import com.wms.entity.GspProductRegister;
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
    PdaGspProductRegister queryByNo(@Param("registerNo") String registerNo);
    /**
     * 通过注册证号查询
     * @param registerNo 注册证编号
     * @return ~
     */
    GspProductRegister queryByRegisterNo(@Param("registerNo") String registerNo);
/**
     * 通过注册证名称查询
     * @param productNameMain 注册证名称
     * @return ~
     */
    GspProductRegister queryByproductNameMain(@Param("productNameMain") String productNameMain);

    /**
     * 通过bas_sku_history.reservedfiled03 匹配使用过的注册证
     * @param sku ~
     * @param customerid ~
     * @return ~
     */
    List<PdaGspProductRegister> queryHistoryRegister(@Param("sku") String sku, @Param("customerid") String customerid);
}
