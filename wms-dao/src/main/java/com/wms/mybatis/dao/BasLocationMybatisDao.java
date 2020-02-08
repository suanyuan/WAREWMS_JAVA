package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.BasCustomer;
import com.wms.entity.BasLocation;
import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>BasLocationDao<br>
 */
public interface BasLocationMybatisDao extends BaseDao {
	
	List<BasLocation> queryUsgTypeByAll();

	List<BasLocation> queryCatTypeByAll();

	List<BasLocation> queryAttTypeByAll();

	List<BasLocation> queryHdlTypeByAll();

	List<BasLocation> queryDmdTypeByAll();

	List<BasLocation> queryPtzoneTypeByAll();

	List<BasLocation> queryPizoneTypeByAll();

	String basLocationCheck(Map<String, Object> map);

	/**
	 * 获取可推荐的库位，分页size固定为1,并且分页属性必传
	 */
	BasLocation queryEmptyLocation(MybatisCriteria mybatisCriteria);


	//查库存
	int queryInvLotLocIdByList(String locationid);//

}
