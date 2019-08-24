package com.wms.mybatis.dao;


import com.wms.entity.DocMtDetails;
import com.wms.entity.DocMtHeader;
import com.wms.entity.DocMtProgressDetail;
import com.wms.query.DocMtDetailsQuery;
import com.wms.query.DocMtHeaderQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocMtDetailsDao<br>
 */
public interface DocMtDetailsMybatisDao extends BaseDao {

    /**
     * 根据时间段查询养护时间 主单状态99 40
     * @return
     */
    List<DocMtDetails>  getDocMtDetailsList();

    /**
     * 查询养护列表 主单状态00 30
     * @return
     */
    List<DocMtDetails>  getDocMtDetailsListByStatus();
    /**
     * 细单号
     * @return
     */
    long  getMtlinenoByMtno(DocMtHeaderQuery query);

    /**
     * 养护明细查询
     */
    List<DocMtDetails> queryUnfinishedDetails(DocMtDetailsQuery docMtDetails);

    /**
     * 更新养护数
     */
    void updateDetailQty(DocMtDetails docMtDetails);

    /**
     * 清除预期数为0 的养护明细
     */
    void clearZeroTask();

    /**
     * 查询养护进度明细列表
     * @param mtno ~
     * @return ~
     */
    List<DocMtProgressDetail> queryDocMtList(@Param("mtno") String mtno);
}
