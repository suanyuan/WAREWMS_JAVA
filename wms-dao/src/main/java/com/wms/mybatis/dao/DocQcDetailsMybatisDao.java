package com.wms.mybatis.dao;

import com.wms.entity.DocQcDetails;
import com.wms.query.pda.PdaDocQcDetailQuery;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 获取验收任务的明细进度清单
     * @param qcno 验收任务单号
     * @return ~
     */
	List<DocQcDetails> queryDocQcList(@Param("qcno") String qcno, @Param("start") int start, @Param("pageSize") int pageSize);
}
