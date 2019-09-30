package com.wms.mybatis.dao;


import com.wms.query.DocQsmDetailsQuery;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocQsmDetailsDao<br>
 */
public interface DocQsmDetailsMybatisDao extends BaseDao {

    void qualityStatus(Map<String, Object> map);

    public <T> List<T> queryByqcudocno(Object id);
//根据行号删除
    public void deletelineno(Object id);
//获得最大行号
    long getqcudoclinenoById(String qcudocno);
//根据主单号 状态查询
    public <T> int queryByListqcustatus(DocQsmDetailsQuery query);
}
