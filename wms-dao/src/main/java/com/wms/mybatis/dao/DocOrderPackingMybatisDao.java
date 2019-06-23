package com.wms.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.wms.entity.DocOrderPacking;
import com.wms.mybatis.dao.BaseDao;
import com.wms.query.DocOrderPackingQuery;
/**
 * 
 * <br>
 * <b>功能：</b>DocOrderPackingDao<br>
 */
public interface DocOrderPackingMybatisDao extends BaseDao {

	List<DocOrderPacking> checkSkuById(DocOrderPackingQuery docOrderPackingQuery);

	long checkCountById(DocOrderPackingQuery docOrderPackingQuery);

	DocOrderPacking checkQtyById(DocOrderPackingQuery docOrderPackingQuery);

	DocOrderPacking queryBoxnoById(DocOrderPackingQuery docOrderPackingQuery);

	DocOrderPacking queryPackingById(DocOrderPackingQuery docOrderPackingQuery);

	void packingAdd(DocOrderPacking docOrderPacking);

	void packingUpdate(DocOrderPacking docOrderPacking);

	void packingSkuAdd(DocOrderPacking docOrderPacking);

	void packingTimeAdd(DocOrderPacking docOrderPacking);

	void packingTimeUpdate(DocOrderPacking docOrderPacking);

	void packingBoxnoAdd(DocOrderPacking docOrderPacking);

	void packingBoxnoUpdate(DocOrderPacking docOrderPacking);

	void commitOrderPacking(Map<String, Object> map);

	void packingSingleDelete(DocOrderPackingQuery docOrderPackingQuery);

	void packingSkuDelete(DocOrderPackingQuery docOrderPackingQuery);

	void packingFlagUpdate(DocOrderPacking docOrderPacking);

	List<DocOrderPacking> queryPackingLabelById(DocOrderPackingQuery docOrderPackingQuery);

	void packingBoxnoDelete(DocOrderPacking docOrderPacking);

	void packingTimeDelete(DocOrderPacking docOrderPacking);

	void packingTimeSkuDelete(DocOrderPacking docOrderPacking);

	void packingDelete(DocOrderPacking docOrderPacking);
	
	
}
