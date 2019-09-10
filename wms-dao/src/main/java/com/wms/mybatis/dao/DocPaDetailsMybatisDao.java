package com.wms.mybatis.dao;


import com.wms.entity.DocPaDetails;
import com.wms.mybatis.entity.pda.PdaDocPaDetailForm;
import com.wms.query.DocPaDetailsQuery;
import com.wms.query.pda.PdaDocPaDetailQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocPaDetailsDao<br>
 */
public interface DocPaDetailsMybatisDao extends BaseDao {

	List<DocPaDetails> queryDocPaDetail(PdaDocPaDetailQuery query);

    /**
     * 通过单号 + 行号，mybatis-generate生成的代码是只有asnno，❎
     * @param pano 单号
     * @param palineno 行号
     * @return ~
     */
	DocPaDetails queryByLineNo(@Param("pano") String pano, @Param("palineno") long palineno);

    /**
     * 上架提交
     * @param form  e
     */
	void putawayGoods(PdaDocPaDetailForm form);

    /**
     * 获取上架明细
     * @param pano ~
     * @return ~~
     */
	List<DocPaDetails> queryDocPaList(String pano);

    /**
     * 批量更新同生产批号的上架产品为合格状态
     * @param docPaDetails pano、sku、userdefine3、userdefine5(DJ)、 lotnumList
     * @return ~
     */
	int updateBatchQc(DocPaDetails docPaDetails);

    /**
     * 批量合格修改上架单的质量状态 for updateBatchQc
     * @param pano 上架单号
     * @param assno 入库单号
     * @return lotnum list
     */
	List<String> queryMatchLotnum(@Param("pano") String pano, @Param("asnno") String assno);

    int queryMaxLineNo(@Param("pano")String pano);

    /**
     * 根据上架单号删除上架明细
     * @param pano ~
     */
    void clearDetailsByPano(@Param("pano") String pano);

    /**
     * 查询最新一次上架提交的数据（同上架单号、客户代码、产品代码、批号)
     */
    List<DocPaDetails> querySimilarDetail(DocPaDetailsQuery query);

    /**
     * 通过qcno获取当前验收单对应的上架单中，此批号是否有还未上架的产品
     * for 批量验收修改日期需求的校验
     * @param qcno 验收单号
     * @param batchNum 生产批号
     * @return ~
     */
    int queryUndoneNum4BatchNum(@Param("qcno") String qcno, @Param("batchNum") String batchNum);



//    /**
//     * 获取同一个上架任务单中，同货主、SKU、批号的 件数总计
//     * @param pano 上架任务单号
//     * @param batchNum 生产批号
//     * @return ~
//     */
//    DocPaDetails querySumQty4SameBatch(@Param("pano") String pano, @Param("customreid") String customerid,
//                                       @Param("sku") String sku, @Param("lotatt04") String lotatt04);
}
