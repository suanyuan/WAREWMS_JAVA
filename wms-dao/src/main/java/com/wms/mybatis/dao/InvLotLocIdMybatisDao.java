package com.wms.mybatis.dao;


import com.wms.entity.InvLotLocId;
import com.wms.query.DocMtHeaderQuery;
import com.wms.query.pda.PdaInventoryQuery;

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
     * 查询库位上对应扫码的产品数据，可能存在多货主、多质量状态
     * @param query ~
     * @return ~
     */
	List<InvLotLocId> queryInventorys(PdaInventoryQuery query);

    /**
     * 查出in_lot_att_id的list 养护计划
     * @return ~
     */
    List<InvLotLocId> getLotLocIdistListByMaintenanceTime();
}
