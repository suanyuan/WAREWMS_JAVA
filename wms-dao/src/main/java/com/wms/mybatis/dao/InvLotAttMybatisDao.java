package com.wms.mybatis.dao;

import com.wms.entity.InvLotAtt;
import com.wms.query.InvLotAttQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>InvLotAttDao<br>
 */
public interface InvLotAttMybatisDao extends BaseDao {
	
	InvLotAtt queryForScan(InvLotAttQuery query);
}
