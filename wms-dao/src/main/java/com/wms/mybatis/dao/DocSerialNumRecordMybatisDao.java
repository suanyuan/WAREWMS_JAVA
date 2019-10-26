package com.wms.mybatis.dao;

import com.wms.entity.DocSerialNumRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocSerialNumRecordDao<br>
 */
public interface DocSerialNumRecordMybatisDao extends BaseDao {

    /**
     * 根据箱号来删除序列号记录
     * @param docSerialNumRecord cartonno + orderno
     */
    void clearRecordByTraceid(DocSerialNumRecord docSerialNumRecord);

    /**
     * 根据出库单号俩删除序列号记录
     * @param orderno 出库单号
     */
    void clearRecordByOrderno(@Param("orderNo") String orderNo);

    /**
     *
     * 查询导出序列号记录
    */
    public <T> List<T> queryExport(@Param("orderNo") String orderNo);

    /**
     *  修改docOrderHeader中的udfprintflag1信息为1 导出过序列号信息
     *
    */
    void updateDocOrder (@Param("orderNo") String orderNo);


}
