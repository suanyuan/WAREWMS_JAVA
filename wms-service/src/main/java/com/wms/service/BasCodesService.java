package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.BasCodes;
import com.wms.mybatis.dao.BasCodesMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.vo.BaseCodesVO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/27
 */
@Service("BasCodesService")
public class BasCodesService {

    @Autowired
    private BasCodesMybatisDao basCodesMybatisDao;

    /**
     * 根据codeid查询code
     * @param codeid
     * @return
     */
    public List<EasyuiCombobox> getBy(String codeid){
        List<EasyuiCombobox> baseCodesVOList = new ArrayList<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Map<String,Object> map = new HashMap<>();
        map.put("codeid",codeid);
        mybatisCriteria.setCondition(map);
        List<BasCodes> list =  basCodesMybatisDao.queryByList(mybatisCriteria);
        if(list!=null && list.size()>0){
            EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
            easyuiCombobox.setId("");
            easyuiCombobox.setValue("");
            easyuiCombobox.setSelected(true);
            baseCodesVOList.add(easyuiCombobox);
            for(BasCodes b : list){
                easyuiCombobox = new EasyuiCombobox();
                easyuiCombobox.setId(b.getCode());
                easyuiCombobox.setValue(b.getCodenameC());
                baseCodesVOList.add(easyuiCombobox);
            }

        }
        return baseCodesVOList;
    }
}