package com.wms.mybatis.dao;

import java.util.List;

public interface BaseDao {
	
	public <T> void add(T t);
	
	public <T> void update(T t);
	
	public <T> void updateBySelective(T t); 	
	
	public void delete(Object id);

	public int queryByCount(MybatisCriteria criteria);
	
	public <T> List<T> queryByList(MybatisCriteria criteria);//总查询不分页，一般导出时使用
	
	public <T> List<T> queryByPageList(MybatisCriteria criteria);//总查询分页
	
	public <T> T queryById(Object id);
	
	public <T> T queryByAll();
	
	public <T> List<T> queryListByAll();

	public Long  queryTotalCount();
}
