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

    /** TODO 会查出来多条 因为is_use = 1 || 0
     * 通过注册证号查询
     * @param registerNo 注册证编号
     * @return ~
     */
    PdaGspProductRegister queryByNo(@Param("registerNo") String registerNo);

    /**
     * 通过注册证号查询当前正在使用的注册证
     * @param registerNo ~
     * @return ~
     */
    PdaGspProductRegister queryUsingItemByNo(@Param("registerNo") String registerNo);

    /** TODO 会查出来多条 因为is_use = 1 || 0
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


}
