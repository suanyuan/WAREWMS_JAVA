package com.wms.mybatis.dao;


import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.BaseDao;
import com.wms.query.GspEnterpriseInfoQuery;
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
}
