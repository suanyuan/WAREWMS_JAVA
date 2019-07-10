package com.wms.mybatis.dao;

import com.wms.entity.DocQcDetails;
import com.wms.query.pda.PdaDocQcDetailQuery;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocQcDetailsDao<br>
 */
public interface DocQcDetailsMybatisDao extends BaseDao {

    /**
     * 根据扫码结果查询验收明细，
     * @param query ~
     * @return 可能会有多条,让用户自行选择
     */
	List<DocQcDetails> queryDocQcDetail(PdaDocQcDetailQuery query);
}
