package com.wms.mybatis.dao;


/**
 * 
 * <br>
 * <b>功能：</b>GspSecondRecordDao<br>
 */
public interface GspSecondRecordMybatisDao extends BaseDao {
    public <T> T queryByProductRegisterId(Object id);


    public <T> T selectCompareByEnterprisId(Object id);

    public <T> T selectByEnterprisId(Object id);


}
