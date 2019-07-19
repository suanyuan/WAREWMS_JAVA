package com.wms.mybatis.dao;

import com.wms.entity.InvLot;

/**
 * 
 * <br>
 * <b>功能：</b>InvLotDao<br>
 */
public interface InvLotMybatisDao extends BaseDao {

    /**
     * 更新库存
     * @param invLot ~
     * @return ~
     */
	int updateQty(InvLot invLot);

    /**
     * 删除0库存的记录
     * @return ~
     */
	int deleteEmptyInv();
}
