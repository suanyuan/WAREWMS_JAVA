package com.wms.mybatis.dao;


import com.wms.entity.DocPkRecords;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>DocPkRecordsDao<br>
 */
public interface DocPkRecordsMybatisDao extends BaseDao {

    DocPkRecords queryAvailablePackedDetail(DocPkRecords docPkRecords);



    int updateDocPkRecords(DocPkRecords docPkRecords);



    /**
     * 获取包装明细中最大的行号
     * @param orderno ~
     * @return ~
     */
    int getMaxPacklineno(@Param("orderno") String orderno);

}
