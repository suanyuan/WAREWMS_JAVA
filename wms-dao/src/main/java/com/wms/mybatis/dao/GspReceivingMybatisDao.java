package com.wms.mybatis.dao;


import com.wms.entity.GspReceiving;
import com.wms.entity.GspSupplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspReceivingDao<br>
 */
public interface GspReceivingMybatisDao extends BaseDao {

	public GspReceiving queryByEnterpriseId(String enterpriseId);


	List<GspReceiving> querySHTGByEnterpriseId(String enterpriseId);


	Long updateFirstState(@Param("no") String no,@Param("state") String state);

	//public GspReceiving queryByEnterprise(Object id);

	public int  selectByClientIdAndReceiving(Object id);

	public int  countByEnterpriseIdAnd40(Object id);

}
