package com.wms.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspBusinessLicenseDao<br>
 */
public interface GspBusinessLicenseMybatisDao extends BaseDao {

    void updateGspBusinessLicenseActiveTag(@Param("enterpriseId") String enterpriseId,@Param("tag") String tag);


    public <T> T selectCompareByEnterpriseId(Object id);



    //查询所有企业的  最新的营业执照
    public <T> List<T> queryNewByList(MybatisCriteria criteria);//总查询不分页，一般导出时使用

}
