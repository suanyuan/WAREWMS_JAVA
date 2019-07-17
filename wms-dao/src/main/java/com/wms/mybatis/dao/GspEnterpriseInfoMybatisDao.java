package com.wms.mybatis.dao;


import com.wms.dto.GspEnterpriseBusinessDTO;
import com.wms.dto.GspEnterpriseTypeDTO;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.BaseDao;
import com.wms.query.GspEnterpriseInfoQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspEnterpriseInfoDao<br>
 */
public interface GspEnterpriseInfoMybatisDao extends BaseDao {
	 Boolean deleteGspEnterprise(String enterpriceId);

	 List<GspEnterpriseInfo> queryPageListByType(MybatisCriteria mybatisCriteria);

	 Long queryByCountByType(MybatisCriteria mybatisCriteria);

	 List<GspEnterpriseBusinessDTO> queryBusinessLicenseOutTime(@Param("enterpriseId") String enterpriseId,@Param("type") String type,@Param("diffCount") Integer diffCount);

	/**
	 * 根据企业信息id查询企业申请列表
	 * @param enterpriseId
	 * @return
	 */
	 List<GspEnterpriseTypeDTO> queryEnterpriseApplyListById(@Param("enterpriseId")String enterpriseId);
}
