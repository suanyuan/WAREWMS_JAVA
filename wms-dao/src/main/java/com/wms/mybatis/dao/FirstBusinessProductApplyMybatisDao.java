package com.wms.mybatis.dao;

import com.wms.result.FirstBusinessProductApplyResult;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>FirstBusinessProductApplyDao<br>
 */
public interface FirstBusinessProductApplyMybatisDao extends BaseDao {
	
	List<FirstBusinessProductApplyResult> queryPageList(MybatisCriteria criteria);

    Long queryPageListCount(MybatisCriteria criteria);
}
