package com.wms.mybatis.dao;

import com.wms.entity.InvLotAtt;
import com.wms.mybatis.entity.IdSequence;
import com.wms.query.InvLotAttQuery;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>InvLotAttDao<br>
 */
public interface InvLotAttMybatisDao extends BaseDao {
	
	InvLotAtt queryForScan(InvLotAttQuery query);
	/**
	 *根据LotAtt05查询 质量状态不为dj
	 * @param query 如上参数
	 * @return ~
	 */
	List<InvLotAtt> queryByLotatts05(InvLotAttQuery query);




	/**
	 * 查询所有
	 * @param
	 * @return ~
	 */
	List<InvLotAtt> queryAll();

//	InvLotAtt queryByLotnum(InvLotAttQuery query);


	/**
	 *根据LotAtt05查询 质量状态不为dj 修改 lotatt13为1
	 * @param query 如上参数
	 * @return ~
	 */
	int updatelotatt13Bylotatt05Andlotatt10(InvLotAttQuery query);

	void getIdSequence(IdSequence idSequence);

    /**
     * 通过18个批属 + 客户id + 产品代码 匹配批次明细
     * @param query 如上参数
     * @return ~
     */
	InvLotAtt queryByLotatts(InvLotAttQuery query);

	List<InvLotAtt> queryLotNoRepet(MybatisCriteria criteria);
}
