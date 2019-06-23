package com.wms.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.wms.entity.DocOrderHeader;
import com.wms.entity.ExportOrderData;
import com.wms.mybatis.dao.BaseDao;
/**
 * 
 * <br>
 * <b>功能：</b>DocOrderHeaderDao<br>
 */
public interface DocOrderHeaderMybatisDao extends BaseDao {

	List<DocOrderHeader> queryByOrderType();

	List<DocOrderHeader> queryBySostatus();

	List<DocOrderHeader> queryByReleasestatus();

	List<DocOrderHeader> queryByAllocationDetailsId(String orderno);

	List<DocOrderHeader> queryByUnAllocationDetailsId(String orderno);
	
	DocOrderHeader queryByPickingList(String orderno);

	List<ExportOrderData> queryByExportList(MybatisCriteria mybatisCriteria);

	public int queryByDetailCount(MybatisCriteria criteria);
	
	public String getIdSequence(Map<String, Object> map);

	void allocationByOrder(Map<String, Object> map);

	void deAllocationByOrder(Map<String, Object> map);

	void pickingByOrder(Map<String, Object> map);

	void unPickingByOrder(Map<String, Object> map);

	void shipmentByOrder(Map<String, Object> map);

	void cancelByOrder(Map<String, Object> map);
}
