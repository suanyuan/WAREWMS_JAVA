package com.wms.mybatis.dao;


import com.wms.entity.DocMtDetails;
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
     * 总数 lotatt
     * @return
     */
    int queryByCountLotatt(MybatisCriteria criteria);
    /**
     * 分页查询  带出lotatt
     * @return
     */
    <T> List<T> queryByListLotatt(MybatisCriteria criteria);
    /**
     * 分页查询 养护记录查询
     * @return
     */
    <T> List<T> queryByListSearch(MybatisCriteria criteria);
    /**
     * 分页查询 养护记录导出
     * @return
     */
    <T> List<T> queryByListExport(MybatisCriteria criteria);
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
    DocMtDetails queryUnfinishedDetail(DocMtDetailsQuery docMtDetails);

    /**
     * 查询养护指导列表（PDA）
     * @param mtno 养护单号
     * @param start 开始
     * @param pageSize 范围
     * @return ~
     */
    List<DocMtDetails> queryMtGuideList(@Param("mtno") String mtno, @Param("start") int start, @Param("pageSize") int pageSize);

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
