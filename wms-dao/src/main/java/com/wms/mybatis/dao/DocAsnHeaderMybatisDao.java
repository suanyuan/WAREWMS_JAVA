package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.mybatis.dao.BaseDao;
import com.wms.mybatis.entity.pda.PdaDocAsnEndForm;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnHeaderDao<br>
 */
public interface DocAsnHeaderMybatisDao extends BaseDao {
	List<DocAsnHeader> queryByAsnType();

	List<DocAsnHeader> queryByAsnstatus();

	List<DocAsnHeader> queryByReleasestatus();

	List<DocAsnDetail> queryByPageDetailList(MybatisCriteria criteria);

	public int queryByDetailCount(MybatisCriteria criteria);
	
	public String getIdSequence(Map<String, Object> map);
	
	public String close(Map<String, Object> map);
	
	public String cancel(Map<String, Object> map);

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
}
