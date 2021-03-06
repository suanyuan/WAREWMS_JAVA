package com.wms.mybatis.dao;


import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.mybatis.entity.pda.PdaDocAsnEndForm;
import com.wms.result.AsnDetailResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnHeaderDao<br>
 */
public  interface  DocAsnHeaderMybatisDao extends BaseDao {
	List<DocAsnHeader> queryByAsnType();

	List<DocAsnHeader> queryByAsnstatus();

	List<DocAsnHeader> queryByReleasestatus();

	List<DocAsnDetail> queryByPageDetailList(MybatisCriteria criteria);

	int queryByDetailCount(MybatisCriteria criteria);
	
	void getIdSequence(Map<String, Object> map);
	
	void close(Map<String, Object> map);
	
	void cancel(Map<String, Object> map);

	/**
	 * 查询未完成的收货任务单
	 * @return pda-收货任务单lsit
	 */
	List<DocAsnHeader> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * 结束收货 procedure
     * @param form ~
     * @return 0 || 1
     */
	int endTask(PdaDocAsnEndForm form);

    /**
     * 通过验收任务单号查询预入库通知单
     */
	DocAsnHeader queryByQcNo(@Param("qcno") String qcno);


	/**
	 * 客户单号是否存在
	 * @param asnreference1
	 * @return
	 */
	int showAsnreference1(@Param("asnreference1") String asnreference1);

	List<AsnDetailResult> queryAsnDetailResult(@Param("asnNo")String asnNo);

	/**
	 *  明细复用查询orderno
	*/
	<T> List<T> queryAsnno(MybatisCriteria criteria);


	<T> T queryBySum(String asnno);
}
