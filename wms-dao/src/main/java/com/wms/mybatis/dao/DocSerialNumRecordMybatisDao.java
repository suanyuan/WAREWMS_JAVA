package com.wms.mybatis.dao;

import com.wms.entity.DocSerialNumRecord;
import org.apache.ibatis.annotations.Param;

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
}
