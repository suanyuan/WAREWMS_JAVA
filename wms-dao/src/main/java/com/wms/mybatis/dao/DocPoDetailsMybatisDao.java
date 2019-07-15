package com.wms.mybatis.dao;


import com.wms.query.DocPoDetailsQuery;

/**
 * 
 * <br>
 * <b>功能：</b>DocPoDetailsDao<br>
 */
public interface DocPoDetailsMybatisDao extends BaseDao {

    long getAsnlinenoById(DocPoDetailsQuery docPoDetailsQuery);
}
