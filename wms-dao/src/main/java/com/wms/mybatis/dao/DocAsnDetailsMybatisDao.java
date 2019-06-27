package com.wms.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocOrderDetail;
import com.wms.mybatis.dao.BaseDao;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocOrderDetailQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnDetailsDao<br>
 */
public interface DocAsnDetailsMybatisDao extends BaseDao {
	
	long getAsnlinenoById(DocAsnDetailQuery docAsnDetailQuery);
	
	void receiveByAsn(DocAsnDetail docAsnDetail);

    /**
     * 通过效期反推获取收货明细
     * @param asnno 收货任务单号
     * @param lotatt 批次属性-效期 lotatt06
     * @return 收货明细
     */
	DocAsnDetail queryDetailByLotatt(@Param("asnno") String asnno, @Param("lotatt") String lotatt);
}
