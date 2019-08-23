package com.wms.mybatis.dao;


import com.wms.entity.DocMtHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocMtHeaderDao<br>
 */
public interface DocMtHeaderMybatisDao extends BaseDao {

    //新增MTNO
    String getIdSequence(Map<String, Object> map);

    /**
     * 查询未完成的养护任务单
     * @return pda-收货任务单lsit
     */
    List<DocMtHeader> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);
}
