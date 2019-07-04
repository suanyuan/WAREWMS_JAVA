package com.wms.mybatis.dao;


import com.wms.entity.DocPaDetails;
import com.wms.query.pda.PdaDocPaDetailQuery;

/**
 * 
 * <br>
 * <b>功能：</b>DocPaDetailsDao<br>
 */
public interface DocPaDetailsMybatisDao extends BaseDao {

    /**
     * 通过
     * @param query
     * @return
     */
	DocPaDetails queryDocPaDetail(PdaDocPaDetailQuery query);
}
