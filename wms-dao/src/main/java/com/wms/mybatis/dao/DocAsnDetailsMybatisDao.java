package com.wms.mybatis.dao;

import com.wms.dto.DocPaDTO;
import com.wms.entity.DocAsnDetail;
import com.wms.mybatis.entity.pda.PdaDocAsnDetailForm;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.pda.PdaDocAsnDetailQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnDetailsDao<br>
 */
public interface DocAsnDetailsMybatisDao extends BaseDao {
	
	int getAsnlinenoById(DocAsnDetailQuery docAsnDetailQuery);
	
	void receiveByAsn(DocAsnDetail docAsnDetail);

	void testProcedure(Map<String, Object> map);

    void receiveGoods(PdaDocAsnDetailForm form);

    List<DocPaDTO> queryDocPaDTO(String[] asnNos);

	String getIdSequence(Map<String, Object> map);

	DocAsnDetail queryForScan(PdaDocAsnDetailQuery query);

    /**
     * 验证单据是 完全收货 || 部分收货
     * @param asnno ~
     * @return ~
     */
    List<DocAsnDetail> queryPartReceivedAsn(@Param("asnno") String asnno);
}
