package com.wms.mybatis.dao;

import com.wms.entity.DocOrderPacking;
import com.wms.entity.DocOrderPackingCarton;
import com.wms.entity.DocOrderPackingCartonInfo;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.query.DocOrderPackingQuery;
import org.apache.ibatis.annotations.Param;

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

	void packingCartonInsert(DocOrderPackingCarton docOrderPackingCarton);
    void packingCartonInsert(DocOrderPacking docOrderPackingCarton);//TODO 这个是错的

	void packingCartonUpdate(DocOrderPacking docOrderPacking);

	void packingCartonInfoInsert(DocOrderPackingCartonInfo docOrderPackingCartonInfo);
    void packingCartonInfoInsert(DocOrderPacking docOrderPacking);//TODO 这个是错的

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

	//获取当前产品的装箱箱号
	DocOrderPackingCarton queryGoodsPackage(DocOrderPackingCarton docOrderPackingCarton);

	//获取当前产品的包装复核记录
	List<DocOrderPackingCarton> queryPackedDetail(DocOrderPackingCarton docOrderPackingCarton);

	DocOrderPackingCarton queryAvailablePackedDetail(DocOrderPackingCarton docOrderPackingCarton);

	//根据orderno traceid查询
	DocOrderPackingCartonInfo queryPackingCartonInfo(@Param("orderno") String orderno, @Param("traceid") String traceid);

	int updatePackingCarton(DocOrderPackingCarton docOrderPackingCarton);

    int updatePackingCartonInfo(DocOrderPackingCartonInfo docOrderPackingCartonInfo);
}
