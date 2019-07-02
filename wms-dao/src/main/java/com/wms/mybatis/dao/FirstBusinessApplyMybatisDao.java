package com.wms.mybatis.dao;

import com.wms.result.FirstBusinessApplyResult;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>FirstBusinessApplyDao<br>
 */
public interface FirstBusinessApplyMybatisDao extends BaseDao {

	List<FirstBusinessApplyResult> queryPageList(MybatisCriteria criteria);

	Long queryPageListCount(MybatisCriteria criteria);
}
