package com.wms.mybatis.dao;

import com.wms.entity.DocSerialNumRecord;

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
}
