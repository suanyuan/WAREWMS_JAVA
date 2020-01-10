package com.wms.mybatis.dao;

import com.wms.query.ActTransactionLogQuery;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ActTransactionLogDao<br>
 */
public interface ActTransactionLogMybatisDao extends BaseDao {

    public <T> List<T> queryByListByTypeAndTime(ActTransactionLogQuery actTransactionLogQuery);

}
