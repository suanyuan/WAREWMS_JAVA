package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasCodes;
import com.wms.mybatis.dao.BasCodesMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasCodesQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasCodesVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCodesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

    public EasyuiDatagrid<BasCodesVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
        EasyuiDatagrid<BasCodesVO> datagrid = new EasyuiDatagrid<BasCodesVO>();
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<BasCodes> basCodesList = basCodesMybatisDao.queryByList(mybatisCriteria);
        BasCodesVO basCodesVO = null;
        List<BasCodesVO> basCodesVOList = new ArrayList<BasCodesVO>();
        for (BasCodes basCodes : basCodesList) {
            basCodesVO = new BasCodesVO();
            BeanUtils.copyProperties(basCodes, basCodesVO);
            basCodesVOList.add(basCodesVO);
        }
        datagrid.setTotal((long)basCodesMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(basCodesVOList);
        return datagrid;
    }

    public Json addBasCodes(BasCodesForm basCodesForm) throws Exception {
        Json json = new Json();
        BasCodes basCodes = new BasCodes();
        BeanUtils.copyProperties(basCodesForm, basCodes);
        basCodesMybatisDao.add(basCodes);
        json.setSuccess(true);
        return json;
    }

    public Json editBasCodes(BasCodesForm basCodesForm) {
        Json json = new Json();
//        BasCodes basCodes = basCodesMybatisDao.queryById(basCodesForm.getCodeid());
//        BeanUtils.copyProperties(basCodesForm, basCodes);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        basCodesForm.setEdittime(date);
        basCodesForm.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
        basCodesMybatisDao.updateBySelective(basCodesForm);
        json.setSuccess(true);
        return json;
    }

    public Json deleteBasCodes(String codeid,String code) {
        Json json = new Json();
//        BasCodes basCodes = basCodesMybatisDao.queryById(id);
//        if(basCodes != null){
            basCodesMybatisDao.delByIDCode(codeid,code);
//        }
        json.setSuccess(true);
        return json;
    }

    public List<EasyuiCombobox> getTransactionTypeCombobox() {
        List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
        EasyuiCombobox combobox = null;
        List<BasCodes> bascodesList = basCodesMybatisDao.queryByTransactionType();
        if(bascodesList != null && bascodesList.size() > 0){
            for(BasCodes basCodes : bascodesList){
                combobox = new EasyuiCombobox();
                combobox.setId(basCodes.getCodeid());
                combobox.setValue(basCodes.getUdf2());
                comboboxList.add(combobox);
            }
        }
        return comboboxList;
    }

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
        mybatisCriteria.setOrderByClause("show_sequence");
        List<BasCodes> list =  basCodesMybatisDao.queryByList(mybatisCriteria);
        if(list!=null && list.size()>0){
            EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
            easyuiCombobox.setId("");
            easyuiCombobox.setValue("");
            //easyuiCombobox.setSelected(true);
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

    /**
     * 根据codeid查询code
     * @param codeid
     * @return
     */
    public List<EasyuiCombobox> getByIsUse(String codeid){
        List<EasyuiCombobox> baseCodesVOList = new ArrayList<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Map<String,Object> map = new HashMap<>();
        map.put("codeid",codeid);
        map.put("select","00");
        mybatisCriteria.setCondition(map);
        mybatisCriteria.setOrderByClause("show_sequence");
        List<BasCodes> list =  basCodesMybatisDao.queryByList(mybatisCriteria);
        if(list!=null && list.size()>0){
            EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
            easyuiCombobox.setId("");
            easyuiCombobox.setValue("");
            //easyuiCombobox.setSelected(true);
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