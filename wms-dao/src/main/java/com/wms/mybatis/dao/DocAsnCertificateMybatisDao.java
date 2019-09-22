package com.wms.mybatis.dao;


import com.wms.entity.DocAsnCertificate;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>DocAsnCertificateDao<br>
 */
public interface DocAsnCertificateMybatisDao extends BaseDao {

    /**
     * 查询生产批号下的产品是否导入了质量合格证
     * @param customerid 货主
     * @param sku 产品代码
     * @param lotatt04 生产批号
     * @return ~
     */
	DocAsnCertificate queryBylotatt04(@Param("customerid") String customerid,
                                      @Param("sku") String sku,
                                      @Param("lotatt04") String lotatt04);
}
