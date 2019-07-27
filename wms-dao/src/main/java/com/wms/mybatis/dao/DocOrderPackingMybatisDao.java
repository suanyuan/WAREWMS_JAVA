package com.wms.mybatis.dao;

import com.wms.entity.DocOrderPacking;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.query.DocOrderPackingQuery;

import java.util.List;
/**
 * 
 * <br>
 * <b>功能：</b>DocOrderPackingDao<br>
 */
public interface DocOrderPackingMybatisDao extends BaseDao {

	List<DocOrderPacking> checkSkuById(DocOrderPackingQuery docOrderPackingQuery);

	List<DocOrderPacking> checkPackingCommitById(DocOrderPacking docOrderPacking);

	List<DocOrderPacking> checkOrderCommitById(DocOrderPacking docOrderPacking);

	Integer checkCountById(DocOrderPackingQuery docOrderPackingQuery);

	DocOrderPacking checkQtyById(DocOrderPackingQuery docOrderPackingQuery);

	DocOrderPacking queryCartonNoById(DocOrderPackingQuery docOrderPackingQuery);

	List<DocOrderPacking> queryPackingById(DocOrderPackingQuery docOrderPackingQuery);

	void packingSkuAdd(DocOrderPacking docOrderPacking);

	void packingTimeAdd(DocOrderPacking docOrderPacking);

	void packingTimeUpdate(DocOrderPacking docOrderPacking);

	void packingBoxnoAdd(DocOrderPacking docOrderPacking);

	void packingBoxnoUpdate(DocOrderPacking docOrderPacking);

	void packingSingleDelete(DocOrderPackingQuery docOrderPackingQuery);

	void packingSkuDelete(DocOrderPackingQuery docOrderPackingQuery);

	List<DocOrderPacking> queryPackingLabelById(DocOrderPackingQuery docOrderPackingQuery);

	void packingCartonInsert(DocOrderPacking docOrderPacking);

	void packingCartonUpdate(DocOrderPacking docOrderPacking);

	void packingCartonInfoInsert(DocOrderPacking docOrderPacking);

	OrderHeaderForNormal queryCartonInfoById(DocOrderPacking docOrderPacking);

	OrderHeaderForNormal queryPackingListById(DocOrderPackingQuery docOrderPackingQuery);
	//记录单箱装箱重量、体积
	void packingCartonInfoUpdate(DocOrderPacking docOrderPacking);
	//记录整单装箱重量、体积
	void orderCartonInfoUpdate(DocOrderPacking docOrderPacking);
	//更新复核标记（取消复核）
	void packingCartonFlagUpdate(DocOrderPacking docOrderPacking);

	Integer queryCartonInfoCountById(DocOrderPackingQuery docOrderPackingQuery);

	void packingCartonDelete(DocOrderPacking docOrderPacking);

	void packingCartonInfoDelete(DocOrderPacking docOrderPacking);
	//按产品档案计算装箱重量、体积
	DocOrderPacking queryPackingInfoById(DocOrderPackingQuery docOrderPackingQuery);
	//按已装箱数据合计重量、体积
	DocOrderPacking queryOrderPackingInfoById(DocOrderPackingQuery docOrderPackingQuery);
	
	
}
