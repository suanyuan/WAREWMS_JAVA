package com.wms.mybatis.dao;



/**
 * 
 * <br>
 * <b>功能：</b>GspMedicalRecordDao<br>
 */
public interface GspMedicalRecordMybatisDao extends BaseDao {

    public <T> T selectCompareByEnterprisId(Object id);
}
