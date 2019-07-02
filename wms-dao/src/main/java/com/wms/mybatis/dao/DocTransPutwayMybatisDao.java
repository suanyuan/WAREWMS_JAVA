package com.wms.mybatis.dao;

import com.wms.entity.DocAsnHeader;
import com.wms.entity.DocTransPutway;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocTransPutwayDao<br>
 */
public interface DocTransPutwayMybatisDao extends BaseDao {

    /**
     * 查询未完成的上架任务单
     * @return pda-上架任务单lsit
     */
    List<DocTransPutway> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);
}
