package com.wms.mybatis.dao;


import com.wms.entity.ProductLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ProductLineMybatisDao<br>
 */
public interface ProductLineMybatisDao extends BaseDao {
    public <T> List<T> queryByPage(MybatisCriteria criteria);//总查询分页

    ProductLine queryByDocAsn(@Param("customerid") String customerid, @Param("sku") String sku);//总查询分页
    public <T> List<T> queryByName(String bame);
    public  void insert(ProductLine t);


     List<ProductLine> getProductLineName();
}
