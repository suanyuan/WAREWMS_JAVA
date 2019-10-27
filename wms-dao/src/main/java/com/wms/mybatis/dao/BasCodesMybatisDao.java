package com.wms.mybatis.dao;

import com.wms.entity.BasCodes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasCodesDao<br>
 */
public interface BasCodesMybatisDao extends BaseDao {

    /**
     * 查询出库状态码 for OrderDetailsForNormalMapper
     * @param code ~
     * @return ~
     */
    BasCodes queryForSO(@Param("code") String code);

    List<BasCodes> queryByTransactionType();

    BasCodes query(@Param("code")String code);

    void delByIDCode(@Param("codeid") String codeid,@Param("code") String code);
}
