package com.wms.mybatis.dao;

import com.wms.entity.DocPkRecords;
import com.wms.query.DocPkRecordsQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>DocPkRecordsDao<br>
 */
public interface DocPkRecordsMybatisDao extends BaseDao {

    /**
     * 获取拣货的记录
     * 拣货记录和分配记录是一一对应的
     */
    DocPkRecords queryPickedRecord(DocPkRecordsQuery docPkRecords);

    /**
     * 拣货完成之后更新分配明细
     */
    int updatePickedQty(DocPkRecords docPkRecords);

    /**
     * 获取拣货明细中最大的行号
     */
    int getMaxPklineno(@Param("orderno") String orderno);

    /**
     * 清除拣货记录
     */
    void cancelPkRecords(@Param("orderno") String orderno);
}
