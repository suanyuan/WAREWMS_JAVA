package com.wms.mybatis.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>FirstReviewLogDao<br>
 */
public interface FirstReviewLogMybatisDao extends BaseDao {
	
	Long updateFirstReviewByNo(@Param("no")String no,@Param("state")String state,@Param("editId")String editId);
}
