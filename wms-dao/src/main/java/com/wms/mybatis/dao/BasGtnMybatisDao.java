package com.wms.mybatis.dao;


import com.wms.entity.BasGtn;

/**
 * 
 * <br>
 * <b>功能：</b>BasGtnDao<br>
 */
public interface BasGtnMybatisDao extends BaseDao {

    /**
     * 通过GTIN查找 记录
     * @param GTIN global trade item number
     * @return ~
     */
	BasGtn queryByGTIN(String GTIN);
}
