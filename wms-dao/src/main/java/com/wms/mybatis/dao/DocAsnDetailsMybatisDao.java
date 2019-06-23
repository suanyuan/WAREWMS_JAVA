package com.wms.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocOrderDetail;
import com.wms.mybatis.dao.BaseDao;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocOrderDetailQuery;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnDetailsDao<br>
 */
public interface DocAsnDetailsMybatisDao extends BaseDao {
	
	long getAsnlinenoById(DocAsnDetailQuery docAsnDetailQuery);
	
	void receiveByAsn(DocAsnDetail docAsnDetail);
	
}
