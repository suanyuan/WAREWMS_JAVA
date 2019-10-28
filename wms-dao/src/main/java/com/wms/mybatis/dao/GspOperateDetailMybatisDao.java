package com.wms.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspOperateDetailDao<br>
 */
public interface GspOperateDetailMybatisDao extends BaseDao {
    /**
     * 根据证照主键删除经营范围
     * @param licenseId
     * @param licenseType
     * @return
     */
    Long deleteByLicenseId(@Param("licenseId") String licenseId,@Param("licenseType") String licenseType);

    /**
     * 根据证照主键失效经营范围
     * @param licenseId
     * @param licenseType
     * @return
     */
    Long updateByLicenseId(@Param("licenseId") String licenseId,@Param("licenseType") String licenseType);


    public <T> List<T> queryListByLicenseId(String id);//通过id查询分类目录


}
