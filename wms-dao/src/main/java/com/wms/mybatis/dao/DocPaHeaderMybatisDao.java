package com.wms.mybatis.dao;

import com.wms.entity.DocPaHeader;
import com.wms.mybatis.entity.pda.PdaDocPaEndForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 结束上架任务单
     * @param form ~
     * @return 1 || 0
     */
    int endTask(PdaDocPaEndForm form);

    /**
     * 通过验收任务单获取上架头档
     * @param qcno !
     * @return !
     */
    DocPaHeader queryByQcno(@Param("qcno") String qcno);

    /**
     * 通过预入通知单号查询上架头档 适用于 定向订单的 上架头档查询
     * @param asnno
     * @return
     */
    DocPaHeader queryByAsnno(@Param("asnno") String asnno);

    /**
     * 收货回写
     * @param map
     */
    void resetAsn(Map<String, Object> map);
}
