package com.wms.mybatis.dao;


import com.wms.entity.DocMtHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocMtHeaderDao<br>
 */
public interface DocMtHeaderMybatisDao extends BaseDao {

    /**
     * 查询未完成的养护任务单
     * @return pda-收货任务单lsit
     */
    List<DocMtHeader> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);
}
