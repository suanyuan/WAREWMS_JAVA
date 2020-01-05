package com.wms.mybatis.dao;


import com.wms.entity.InvLotLocIdSkuInvLotAtt;
import com.wms.entity.ViewInvLocation;
import com.wms.entity.ViewInvLotatt;
import com.wms.query.ViewInvLotattQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>ViewInvLotattDao<br>
 */
public interface ViewInvLotattMybatisDao extends BaseDao {

    void invAdj(Map<String, Object> map);

    void invMov(ViewInvLotattQuery viewInvLotattQuery);

    void invHold(Map<String, Object> map);

    public <T> List<T> queryByPageListByData(MybatisCriteria criteria);//总查询分页
    public int queryByPageListByDataCount(MybatisCriteria criteria);
    public int queryByCountNotDJ(MybatisCriteria criteria);

    //	产品库存查询接口
    List<InvLotLocIdSkuInvLotAtt> getInvLotLocIdSkuInvLotAttList(@Param("sku") String sku,
                                                                 @Param("lotatt04") String lotatt04,
                                                                 @Param("lotatt05") String lotatt05);

    //计算库存总计

    public ViewInvLotatt queryByListSum(MybatisCriteria criteria);
}
