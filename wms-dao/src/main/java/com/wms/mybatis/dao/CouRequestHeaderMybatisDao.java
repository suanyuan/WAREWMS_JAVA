package com.wms.mybatis.dao;


import com.wms.entity.CouRequestHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>CouRequestHeaderDao<br>
 */
public interface CouRequestHeaderMybatisDao extends BaseDao {
//新增单号
    void getIdSequence(Map<String, Object> map);

    List<CouRequestHeader> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);
}
