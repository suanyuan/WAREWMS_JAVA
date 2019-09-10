package com.wms.mybatis.dao;

import com.wms.entity.BasSku;
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

}
