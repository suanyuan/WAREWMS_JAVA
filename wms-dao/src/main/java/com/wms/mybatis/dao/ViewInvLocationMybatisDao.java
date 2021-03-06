package com.wms.mybatis.dao;


import com.wms.entity.ViewInvLocation;
import com.wms.mybatis.dao.BaseDao;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>ViewInvLocationDao<br>
 */
public interface ViewInvLocationMybatisDao extends BaseDao {
// 查询库所有列表 不分页
    public <T> List<T> queryByListAll(MybatisCriteria criteria);
//导出查询
    public <T> List<T> queryByListExport(MybatisCriteria criteria);
//计算库存总计

    public ViewInvLocation queryByListSum(MybatisCriteria criteria);

}
