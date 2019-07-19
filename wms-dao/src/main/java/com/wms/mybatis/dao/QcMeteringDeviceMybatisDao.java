package com.wms.mybatis.dao;

import com.wms.entity.QcMeteringDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>QcMeteringDeviceDao<br>
 */
public interface QcMeteringDeviceMybatisDao extends BaseDao {
	
	List<QcMeteringDevice> getQcMeteringDeviceOutTime(@Param("diffCount") Integer diffCount);
}
