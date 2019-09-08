package com.wms.mybatis.dao;

import com.wms.entity.DocOrderPacking;
import com.wms.entity.DocQcDetails;
import com.wms.mybatis.entity.CleanInventory;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.query.DocOrderPackingQuery;
import com.wms.query.DocQcDetailsQuery;
import com.wms.query.pda.PdaDocQcDetailQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

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
     * @return ~区分了生产日期，只会有一条
     */
	DocQcDetails queryDocQcDetail(PdaDocQcDetailQuery query);

    /**
     * 根据扫码结果查询验收明细
     * @param query ~
     * @return 如果只传SKU 可能会有多条，用上面的会报错
     */
	List<DocQcDetails> queryDocQcDetailList(PdaDocQcDetailQuery query);

    /**
     * 获取验收任务的明细进度清单
     * @param qcno 验收任务单号
     * @return ~
     */
	List<DocQcDetails> queryDocQcList(@Param("qcno") String qcno, @Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * 更新验收说明，不用生成的略~
     * @return 1 || 0
     */
	int updateQcDesc(DocQcDetailsQuery query);

    /**
     * 验收提交
     * @param form ~
     */
	void submitDocQc(PdaDocQcDetailForm form);

    /**
     * for 批量验收 修改明细数据
     * @param details ~
     * @return ~
     */
	int updateQcDetail(DocQcDetails details);

    /**
     * 更新验收明细件数（paqty_expected, qcqty_expected, qcqty_completed）
     * @param details ~
     * @return ~
     */
	int updateQcCompletedQty(DocQcDetails details);

    /**
     * 获取验收任务单对应的上架任务单的总完成数（上架单结束的状态下）
     * @param qcno ~
     * @return ~
     */
	int queryCompletedPaQty(@Param("qcno") String qcno);

    /**
     * 获取验收任务单对应的明细单下面做的总验收完成数
     * @param qcno ~
     * @return ~
     */
	int queryCompletedQcQty(@Param("qcno") String qcno);

    /**
     * 清除0库存
     * @param cleanInventory ~
     */
	void cleanInventory(CleanInventory cleanInventory);

	int queryMaxLineNo(@Param("qcNo")String qcNo);

    /**
     * 查询当前批号的已验收件数、未验收件数
     * @param query ~
     * @return ~
     */
	DocQcDetails queryAcceptQty(PdaDocQcDetailQuery query);


	/**
	 * 验收作业分页查询 显示pano
	 * @param criteria
	 * @return
	 */
	public <T> List<T> queryByListPano(MybatisCriteria criteria);
//用于导出
	public <T> List<T> queryByListExport(MybatisCriteria criteria);
	public int queryByCountPano(MybatisCriteria criteria);
}
