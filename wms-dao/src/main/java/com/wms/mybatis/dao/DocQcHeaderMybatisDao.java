package com.wms.mybatis.dao;

import com.wms.entity.DocQcHeader;
import com.wms.mybatis.entity.pda.PdaDocAsnDetailForm;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.mybatis.entity.pda.PdaDocQcStatusForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>DocQcHeaderDao<br>
 */
public interface DocQcHeaderMybatisDao extends BaseDao {

    /**
     * 查询未完成的验收任务单
     * @return pda-上架任务单lsit
     */
    List<DocQcHeader> queryUndoneList(@Param("start") int start, @Param("pageSize") int pageSize);

    /**
     * 修改验收单状态
     * 如果验收完成，相关联的预入库单会设置成 "70" 完全验收
     * @param form warehouseid, qcno, returncode
     */
    void updateTaskStatus(PdaDocQcDetailForm form);

    DocQcHeader queryByPano(@Param("pano") String pano);


    public <T> List<T> queryByList1(MybatisCriteria criteria);//总查询不分页，一般导出时使用
}
