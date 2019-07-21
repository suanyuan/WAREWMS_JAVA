package com.wms.mybatis.dao;

import com.wms.entity.DocQcHeader;
import com.wms.mybatis.entity.pda.PdaDocQcStatusForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocQcHeaderDao<br>
 */
public interface DocQcHeaderMybatisDao extends BaseDao {

    /**
     * 查询未完成的验收任务单
     * @return pda-上架任务单lsit
     */
    List<DocQcHeader> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * 更新验收任务单
     * @param form ~
     * @return 1 || 0
     */
    int updateTaskStatus(PdaDocQcStatusForm form);

    DocQcHeader queryByPano(@Param("pano") String pano);
}
