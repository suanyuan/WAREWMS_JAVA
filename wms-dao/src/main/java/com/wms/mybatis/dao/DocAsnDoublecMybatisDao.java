package com.wms.mybatis.dao;


import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnDoublecDao<br>
 */
public interface DocAsnDoublecMybatisDao extends BaseDao {


//根据序列号查询
    public <T> T queryByContext2(Object id);

    /**
     * 双证出库之后，需要删除导入的双证
     * @param orderno 出库单号
     */
    void removeDoublecByContext2(@Param("orderno") String orderno);
}
