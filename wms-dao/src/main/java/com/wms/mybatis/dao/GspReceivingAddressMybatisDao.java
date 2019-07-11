package com.wms.mybatis.dao;


import com.wms.entity.GspReceivingAddress;
import com.wms.entity.PCD;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>GspReceivingAddressMybatisDao<br>
 */
public interface GspReceivingAddressMybatisDao extends BaseDao {


    public List<PCD> findPCDByPid(int pid);
    public List<GspReceivingAddress> queryByReceivingId(String receivingId);

}
