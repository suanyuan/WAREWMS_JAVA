package com.wms.mybatis.dao;


import com.wms.entity.BasSerialNum;
import com.wms.query.BasSerialNumQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>BasSerialNumDao<br>
 */
public interface BasSerialNumMybatisDao extends BaseDao {

    /**
     * 查询未匹配的序列号
     * @param query batch_name, material_num, delivery_num
     * @return ~
     */
	List<BasSerialNum> queryAvailableNum(BasSerialNumQuery query);

    /**
     * 根据序号删除出库日期出库单号为空的数据
     * @param id
     * @param
     * @return
     */
      int  deleteById(Object id);
    /**
     * 根据序列号查询出库日期出库单号为空的数据
     * @param id
     * @param <T>
     * @return
     */
    <T> T queryExistBySerialNum(Object id);

    /**
     * 通过发运订单号，查看关联的发货凭证号是否导入了入库序列号,并返回行数量
     */
    int countSerialNum4Match(@Param("orderno") String orderno);

    /**
     * 查询可用的序列号记录（没出库的）
     */
    List<BasSerialNum> queryValidatedId(@Param("serialNum") String serialNum);

    /**
     * 记录序列号出库的出库单号+出库时间
     */
    void recordSerialNumOut(BasSerialNum basSerialNum);
}
