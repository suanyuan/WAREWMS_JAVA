package com.wms.mybatis.dao;


import com.wms.entity.BasSerialNum;
import com.wms.query.BasSerialNumQuery;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasSerialNumDao<br>
 */
public interface BasSerialNumMybatisDao extends BaseDao {

    /**
     * 查询未匹配的序列号
     * @param query batch_name, material_num, delivery_num
     * @return ~
     */
	List<BasSerialNum> queryAvailableNum(BasSerialNumQuery query);
}
