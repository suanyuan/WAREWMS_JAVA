package com.wms.mybatis.dao;


import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocOrderPackingCartonDao<br>
 */
public interface DocOrderPackingCartonMybatisDao extends BaseDao {

    public <T> List<T> queryByListPrinth(MybatisCriteria criteria);//总查询不分页，一般导出时使用
	
}
