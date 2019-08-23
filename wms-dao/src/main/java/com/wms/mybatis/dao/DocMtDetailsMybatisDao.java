package com.wms.mybatis.dao;


import com.wms.entity.DocMtDetails;
import com.wms.entity.DocMtHeader;
import com.wms.query.DocMtHeaderQuery;

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

}
