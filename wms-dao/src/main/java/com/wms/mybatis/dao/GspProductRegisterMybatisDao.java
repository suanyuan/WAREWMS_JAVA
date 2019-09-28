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
     * 通过注册证名称查询
     * @param productNameMain 注册证名称
     * @return ~
     */
    GspProductRegister queryByproductNameMain(@Param("productNameMain") String productNameMain);

    /**
     * 通过注册证号查询所有
     * @param registerNo 注册证编号
     * @return ~
     */
    List<PdaGspProductRegister> queryAllByNo(@Param("registerNo") String registerNo);


    //查询需要比较的字段
    public <T> T selectProductRegisterByCompare(Object id);

}
