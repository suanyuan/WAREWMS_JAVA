package com.wms.mybatis.dao;



/**
 * 
 * <br>
 * <b>功能：</b>GspFirstRecordDao<br>
 */
public interface GspFirstRecordMybatisDao extends BaseDao {

    public <T> T selectCompareByEnterprisId(Object id);

    public <T> T selectByEnterprisId(Object id);


}
