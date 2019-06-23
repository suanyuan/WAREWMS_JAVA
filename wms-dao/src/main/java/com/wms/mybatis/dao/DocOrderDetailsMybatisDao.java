package com.wms.mybatis.dao;

import com.wms.mybatis.dao.BaseDao;
import com.wms.query.DocOrderDetailQuery;
/**
 * 
 * <br>
 * <b>功能：</b>DocOrderDetailsDao<br>
 */
public interface DocOrderDetailsMybatisDao extends BaseDao {

	long getOrderlinenoById(DocOrderDetailQuery docOrderDetailQuery);
	
	
}
