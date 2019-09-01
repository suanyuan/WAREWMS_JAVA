package com.wms.mybatis.dao;


import com.wms.entity.InvLotLocIdSkuInvLotAtt;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>ViewInvLotattDao<br>
 */
public interface ViewInvLotattMybatisDao extends BaseDao {
	public String invAdj(Map<String, Object> map);
	public String invMov(Map<String, Object> map);
	public String invHold(Map<String, Object> map);
//	产品库存查询接口
	List<InvLotLocIdSkuInvLotAtt> getInvLotLocIdSkuInvLotAttList(@Param("sku")String sku,@Param("lotatt04")String lotatt04,@Param("lotatt05")String lotatt05);
}
