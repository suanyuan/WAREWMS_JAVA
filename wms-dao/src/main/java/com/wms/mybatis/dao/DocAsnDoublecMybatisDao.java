package com.wms.mybatis.dao;




/**
 * 
 * <br>
 * <b>功能：</b>DocAsnDoublecDao<br>
 */
public interface DocAsnDoublecMybatisDao extends BaseDao {


//根据序列号查询
    public <T> T queryByContext2(Object id);
}
