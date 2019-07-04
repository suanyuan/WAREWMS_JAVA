package com.wms.mybatis.dao;

import com.wms.entity.DocPaHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocPaHeaderDao<br>
 */
public interface DocPaHeaderMybatisDao extends BaseDao {

    /**
     * 查询未完成的上架任务单
     * @return pda-上架任务单lsit
     */
    List<DocPaHeader> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);
}
