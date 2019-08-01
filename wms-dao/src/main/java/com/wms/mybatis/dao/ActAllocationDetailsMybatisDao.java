package com.wms.mybatis.dao;

import com.wms.entity.ActAllocationDetails;
import com.wms.mybatis.entity.pda.PdaOrderPackingForm;
import com.wms.query.ActAllocationDetailsQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ActAllocationDetailsDao<br>
 */
public interface ActAllocationDetailsMybatisDao extends BaseDao {

    /**
     * 通过订单明细来查询分配明细 status = '60' || '62'
     */
//	List<ActAllocationDetails> queryByOrder(ActAllocationDetails details);

    /**
     * 通过orderno, sku, customerid, lotatt02, lotatt04, lotatt05
     * packflag 传入基本上是0的，查询未装箱结束的
     * 可能存在多条，因为分配的同批号的产品可能库位不同
     * @param query ~
     * @return ~
     */
	List<ActAllocationDetails> queryForScan(ActAllocationDetailsQuery query);

    /**
     * 此分配明细已装箱结束
     * @param query ~
     * @return ~
     */
	int finishPacking(ActAllocationDetailsQuery query);

    /**
     * 通过已完成的包装复合回写到分配明细中
     */
	void callPickingProcedure(PdaOrderPackingForm form);
}