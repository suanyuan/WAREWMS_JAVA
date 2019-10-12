package com.wms.mybatis.dao;


import com.wms.entity.GspSupplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspSupplierDao<br>
 */
public interface GspSupplierMybatisDao extends BaseDao {
    public void deleteNotUse(Object id);

    Long updateFirstState(@Param("no") String no, @Param("state") String state);


    public <T> T queryByEnterpriseId(Object id);

    public List<GspSupplier> queryListByEnterpriseId(Object id);



    Integer queryGspSupplierByEnterpriseId(GspSupplier gspSupplier);

    public int  countByEnterpriseId(Object id);

    public int  countByEnterpriseIdAnd40(Object id);

    public int  countByEnterpriseIdAndClient(@Param("enterpriseId") String enterpriseId, @Param("costomerid") String costomerid);

}
