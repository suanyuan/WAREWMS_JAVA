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
}
