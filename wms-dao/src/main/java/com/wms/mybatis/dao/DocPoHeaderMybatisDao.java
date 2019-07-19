package com.wms.mybatis.dao;


import com.wms.entity.DocPoHeader;
import com.wms.query.DocPoDetailsQuery;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocPoHeaderDao<br>
 */
public interface DocPoHeaderMybatisDao extends BaseDao {
//	获得采购订单的类型状态等
	List<DocPoHeader> getPotypeCombobox();
	List<DocPoHeader> getPostatusCombobox();
	List<DocPoHeader> getWarehouseCombobox();
	String getIdSequence(Map<String, Object> map);
	String cancel(Map<String, Object> map);
}
