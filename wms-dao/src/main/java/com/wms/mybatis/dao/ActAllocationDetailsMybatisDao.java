package com.wms.mybatis.dao;

import com.wms.entity.ActAllocationDetails;

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
	List<ActAllocationDetails> queryByOrder(ActAllocationDetails details);
}
