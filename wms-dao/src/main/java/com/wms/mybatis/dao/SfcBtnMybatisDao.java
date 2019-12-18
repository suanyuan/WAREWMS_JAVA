package com.wms.mybatis.dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 
 * <br>
 * <b>功能：</b>SfcBtnDao<br>
 */
public interface SfcBtnMybatisDao extends BaseDao {

    <T> List<T> queryListRole(@Param("menuId")Object menuId, @Param("roleId")Object roleId);

	
}
