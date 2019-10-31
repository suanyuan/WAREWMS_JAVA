package com.wms.mybatis.dao;


import com.wms.entity.InvLotLocId;
import com.wms.query.CouRequestDetailsQuery;
import com.wms.query.pda.PdaInventoryQuery;
import org.apache.ibatis.annotations.Param;

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
    /**
     * 查出in_lot_att_id的list 盘点任务
     * @return ~
     */
    List<InvLotLocId> queryByListByCouRequest(CouRequestDetailsQuery query);

    /**
     * 获取在库的产品记录，多库位, 多货主
     * @param query lotatt04, lotatt05, GTIN, SKU
     * @return ~
     */
    List<InvLotLocId> queryInventoryForScan(PdaInventoryQuery query);

    /**
     * PDA扫描库位获取库位上产品的库存数据
     */
    List<InvLotLocId> queryInventoryForLocation(PdaInventoryQuery query);

    /**
     * 查询货主下同批的产品件数总和，不区分库位
     * @param customerid ~
     * @param lotatt04 ~
     * @return ~
     */
    double sumSameBatchInventory(@Param("customerid") String customerid, @Param("lotatt04") String lotatt04);

     /**
      * 根据唯一主键获取单条InvLotLocId
     */
    InvLotLocId queryByKey(InvLotLocId invLotLocId);
    /**
      * 根据唯一主键获取单条InvLotLocId和其lotatt批属
     */
    InvLotLocId queryByKeyLotatt(InvLotLocId invLotLocId);

    /**
     * 根据唯一主键修改InvLotLocId的冻结状态
     */
    void updateByKey(InvLotLocId invLotLocId);

    /**
     * 根据序列号查询产品的双证匹配状态
     * @param lotatt05 序列号
     * @return 双证是否匹配
     */
    InvLotLocId queryByLotatt05(@Param("lotatt05") String lotatt05, @Param("customerId") String customerId);
}
