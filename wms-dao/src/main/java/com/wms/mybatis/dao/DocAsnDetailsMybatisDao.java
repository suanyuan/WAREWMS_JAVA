package com.wms.mybatis.dao;

import com.wms.entity.DocAsnDetail;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.pda.PdaDocAsnDetailQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnDetailsDao<br>
 */
public interface DocAsnDetailsMybatisDao extends BaseDao {
	
	long getAsnlinenoById(DocAsnDetailQuery docAsnDetailQuery);
	
	void receiveByAsn(DocAsnDetail docAsnDetail);

	void testProcedure(Map<String, Object> map);

    void receiveGoods();
}
