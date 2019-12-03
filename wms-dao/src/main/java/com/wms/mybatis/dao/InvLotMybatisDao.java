package com.wms.mybatis.dao;

import com.wms.entity.InvLot;

import java.util.List;

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
     * 查询零库存记录
     */
	List<InvLot> queryZeroInv();
}
