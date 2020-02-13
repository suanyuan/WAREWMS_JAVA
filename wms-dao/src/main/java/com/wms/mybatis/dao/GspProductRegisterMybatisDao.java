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

    /**
     * 获取相同注册证号的list，且进行排序
     * 排序1，根据批准日期；排序2，根据有效日期
     */
    List<GspProductRegister> queryByNoAndOrderBy(@Param("registerNo") String registerNo, @Param("orderby") String orderby);


    //查询需要比较的字段
    public <T> T selectProductRegisterByCompare(Object id);


    int countByproductResisterNo(Object id);


    /**
     * 通过sku查询所有关联的注册证
     * @param sku   产品代码
     * @return ~
     */
    List<GspProductRegister> queryBysku(@Param("sku") String sku,@Param("orderBy") String orderBy);
}
