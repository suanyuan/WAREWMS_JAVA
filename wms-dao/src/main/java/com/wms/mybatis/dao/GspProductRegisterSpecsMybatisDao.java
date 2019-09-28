package com.wms.mybatis.dao;


import java.util.Map;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspProductRegisterSpecsDao<br>
 */
public interface GspProductRegisterSpecsMybatisDao extends BaseDao {

    public String getIdSequence(Map<String, Object> map);

    <T> List<T> queryByListUnBind(MybatisCriteria criteria);

    int queryByCountUnBind(MybatisCriteria criteria);

    public <T> T selectById(Object id);

    public void deleteByid(Object id);


    public <T> T selectByProductCode(Object id);

    public <T> List<T> queryProductSUPByList(MybatisCriteria criteria);//总查询不分页，一般导出时使用
    public int queryProductSUPByCount(MybatisCriteria criteria);

    //根据specsid查询 最新有效的该产品
    public <T> T selectNewBySpecsId(Object id);


    //查询需要比较的字段
    public <T> T selectProductByCompare(Object id);



}
