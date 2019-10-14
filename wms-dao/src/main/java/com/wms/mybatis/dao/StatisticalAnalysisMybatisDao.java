package com.wms.mybatis.dao;


import com.wms.entity.RptAsnList;
import com.wms.entity.RptSoAsnDailyLocation;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface StatisticalAnalysisMybatisDao extends BaseDao {

//出入库流水
	public List<RptSoAsnDailyLocation> querySoAsnInvLocation(MybatisCriteria criteria);
	public int querySoAsnInvLocationCount(MybatisCriteria criteria);
//入库单列表
	public List<RptAsnList> queryAsnList(MybatisCriteria criteria);
	public int queryAsnListCount(MybatisCriteria criteria);




}
