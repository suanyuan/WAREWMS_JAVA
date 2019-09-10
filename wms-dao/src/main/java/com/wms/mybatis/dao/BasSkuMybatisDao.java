package com.wms.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.wms.entity.BasGtn;
import com.wms.entity.BasSku;
import com.wms.mybatis.dao.BaseDao;
import com.wms.query.BasSkuQuery;
import com.wms.query.pda.PdaBasSkuQuery;

/**
 * 
 * <br>
 * <b>功能：</b>BasSkuDao<br>
 */
public interface BasSkuMybatisDao extends BaseDao {

    void basSkuCheck(Map<String, Object> map);

	BasSku queryBySkuInfo(BasSkuQuery skuQuery);

	BasSku queryById(Map<String, Object> map);
//商品档案 列表总数ByInvLot
     int queryByCountByInvLot(MybatisCriteria criteria);
//	和invlot联接查询带出库存件数
    <T> List<T> queryByPageListByInvLot(MybatisCriteria criteria);
    /**
     * 通过扫码结果查找，自编码(1~5)、GTIN、批号、序列号
     * @param query ~
     * @return ~
     */
    BasSku queryForScan(PdaBasSkuQuery query);


}
