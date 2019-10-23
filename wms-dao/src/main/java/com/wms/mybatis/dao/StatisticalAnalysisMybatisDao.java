package com.wms.mybatis.dao;


import com.wms.entity.*;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasCustomerDao<br>
 */
public interface StatisticalAnalysisMybatisDao extends BaseDao {

	//出入库流水
	List<RptSoAsnDailyLocation> querySoAsnInvLocation(MybatisCriteria criteria);
	int querySoAsnInvLocationCount(MybatisCriteria criteria);

	//入库单列表
	List<RptAsnList> queryAsnList(MybatisCriteria criteria);
	int queryAsnListCount(MybatisCriteria criteria);

	//收发存汇总表
	List<RptSendReceiveAndSave> querySendReceiveAndSaveAllList(MybatisCriteria criteria);
	int querySendReceiveAndSaveAllListCount(MybatisCriteria criteria);

	//销售出库单列表
	List<RptSalesSo> querySalesSoList(MybatisCriteria criteria);
	int querySalesSoListCount(MybatisCriteria criteria);

	//订单装箱清单-按发运单号
	List<RptOrderPackingcartonByOrderNo> queryOrderPackingcartonByOrderNo(MybatisCriteria criteria);
	int queryOrderPackingcartonByOrderNoCount(MybatisCriteria criteria);
}
