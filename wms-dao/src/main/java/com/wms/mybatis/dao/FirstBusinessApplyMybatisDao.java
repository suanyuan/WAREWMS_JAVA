package com.wms.mybatis.dao;

import com.wms.entity.BasSku;
import com.wms.entity.FirstBusinessApply;
import com.wms.result.FirstBusinessApplyResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>FirstBusinessApplyDao<br>
 */
public interface FirstBusinessApplyMybatisDao extends BaseDao {

	List<FirstBusinessApplyResult> queryPageList(MybatisCriteria criteria);

	Long queryPageListCount(MybatisCriteria criteria);

	Long updateBusinessByNo(@Param("no") String no,@Param("state") String state);

	public int  selectFirstBusinessBySupplierAndProduct(Object id);

	public int   selectSupplierNumByProductAndState(Object id);


	public List<String> selectSupplierNamesByProductAndState(BasSku basSku);//

	//通过产品注册证id查询其下的产品的首营申请
	List<FirstBusinessApply> selectByProductRegisterId(Object id);

	/**
	 * 更新审核中或者新建状态单据供应商编号
	 * @param supplerId
	 * @return
	 */
	Long updateCheckingApplySupplierNo(String supplerId);

    //通过产品注册证id查询其下的产品的首营申请
    List<FirstBusinessApplyResult> selectByProductRegisterIdAll(Object id);


	public  int countByClientAndProduct(@Param("clientId") String clientId,@Param("sku") String sku);


	//通过 产品首营申请单号 和 产品的分类目录 找产品首营申请
	List<FirstBusinessApply> selectByOperateId(@Param("operateId") String id,@Param("applyId") String applyId);



	//通过产品代码  查询该产品的所有首营
	List<String> queryListByProductCode(String productCode);

	List<String> selectBySpecsId(String specsId);


}
