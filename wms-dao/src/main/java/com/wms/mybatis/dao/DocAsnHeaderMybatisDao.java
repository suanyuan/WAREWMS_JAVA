package com.wms.mybatis.dao;


import java.util.List;
import java.util.Map;

import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.mybatis.dao.BaseDao;
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
	
}
