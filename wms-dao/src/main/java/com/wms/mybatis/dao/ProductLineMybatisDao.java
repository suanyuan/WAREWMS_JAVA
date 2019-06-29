package com.wms.mybatis.dao;


import com.wms.entity.ProductLine;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ProductLineMybatisDao<br>
 */
public interface ProductLineMybatisDao extends BaseDao {
    public <T> List<T> queryByPage(MybatisCriteria criteria);//总查询分页



    public  void insert(ProductLine t);
}
