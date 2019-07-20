package com.wms.mybatis.dao;


import com.wms.entity.InvLotLocId;

/**
 * 
 * <br>
 * <b>功能：</b>InvLotLocIdDao<br>
 */
public interface InvLotLocIdMybatisDao extends BaseDao {

    /**
     * 更新库存数量-批量验收
     * @param invLotLocId ~
     * @return ~
     */
	int updateQty(InvLotLocId invLotLocId);
}
