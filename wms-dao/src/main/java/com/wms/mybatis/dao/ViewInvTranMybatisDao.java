package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.ViewInvTran;
import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>ViewInvTransDao<br>
 */
public interface ViewInvTranMybatisDao extends BaseDao {

	public String cancelReceive(Map<String, Object> map);
	public String cancelShipment(Map<String, Object> map);
	
	List<ViewInvTran> queryByTransactionType();
	List<ViewInvTran> queryByDocType();
	List<ViewInvTran> queryByStatus();
	
}
