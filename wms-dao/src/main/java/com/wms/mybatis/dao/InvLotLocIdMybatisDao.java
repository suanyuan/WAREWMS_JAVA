package com.wms.mybatis.dao;


import com.wms.entity.InvLotLocId;
import com.wms.query.DocMtHeaderQuery;

import java.util.List;

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
    /**
     * 查出in_lot_att_id的list 养护计划
     * @return ~
     */
    List<InvLotLocId> getLotLocIdistListByMaintenanceTime();
}
