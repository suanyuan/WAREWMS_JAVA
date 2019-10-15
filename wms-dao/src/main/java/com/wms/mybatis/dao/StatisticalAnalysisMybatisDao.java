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
	public List<RptSoAsnDailyLocation> querySoAsnInvLocation(MybatisCriteria criteria);
	public int querySoAsnInvLocationCount(MybatisCriteria criteria);
//入库单列表
	public List<RptAsnList> queryAsnList(MybatisCriteria criteria);
	public int queryAsnListCount(MybatisCriteria criteria);
//收发存汇总表
	public List<RptSendReceiveAndSave> querySendReceiveAndSaveAllList(MybatisCriteria criteria);
	public int querySendReceiveAndSaveAllListCount(MybatisCriteria criteria);
//销售出库单列表
	public List<RptSalesSo> querySalesSoList(MybatisCriteria criteria);
	public int querySalesSoListCount(MybatisCriteria criteria);
//订单装箱清单-按发运单号
	public List<RptOrderPackingcartonByOrderNo> queryOrderPackingcartonByOrderNo(MybatisCriteria criteria);
	public int queryOrderPackingcartonByOrderNoCount(MybatisCriteria criteria);



}
