package com.wms.mybatis.dao;

import com.wms.entity.InvLotAtt;
import com.wms.mybatis.entity.IdSequence;
import com.wms.query.InvLotAttQuery;

/**
 * 
 * <br>
 * <b>功能：</b>InvLotAttDao<br>
 */
public interface InvLotAttMybatisDao extends BaseDao {
	
	InvLotAtt queryForScan(InvLotAttQuery query);

	void getIdSequence(IdSequence idSequence);

    /**
     * 通过18个批属 + 客户id + 产品代码 匹配批次明细
     * @param query 如上参数
     * @return ~
     */
	InvLotAtt queryByLotatts(InvLotAttQuery query);
}
