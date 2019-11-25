package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasCodes;
import com.wms.entity.GspReceiving;
import com.wms.mybatis.dao.BasCodesMybatisDao;
import com.wms.mybatis.dao.GspReceivingMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasCodesQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
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

    @Autowired
    private GspReceivingMybatisDao gspReceivingMybatisDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public EasyuiDatagrid<BasCodesVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
        EasyuiDatagrid<BasCodesVO> datagrid = new EasyuiDatagrid<BasCodesVO>();
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<BasCodes> basCodesList = basCodesMybatisDao.queryByList(mybatisCriteria);

        List<BasCodesVO> basCodesVOList = new ArrayList<BasCodesVO>();
        for (BasCodes basCodes : basCodesList) {
            BasCodesVO basCodesVO = new BasCodesVO();
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
        Date date = new Date(System.currentTimeMillis());
        basCodesForm.setAddtime(date);
        basCodesForm.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
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
     * 查询码表内容
     */
    public BasCodes queryBasCode(String codeid, String code) {

        BasCodesQuery query = new BasCodesQuery();
        query.setCodeid(codeid);
        query.setCode(code);
        return basCodesMybatisDao.queryById(query);
    }



    /**
     * 分页查询 历史档案管理页面
     * @param
     * @return
     */
    //历史档案管理
    public EasyuiDatagrid<BasCodesVO> showHistoryFileDatagrid(EasyuiDatagridPager pager, BasCodesQuery query) {
        EasyuiDatagrid<BasCodesVO> datagrid = new EasyuiDatagrid<BasCodesVO>();
        MybatisCriteria criteria = new MybatisCriteria();
        criteria.setCurrentPage(pager.getPage());
        criteria.setPageSize(pager.getRows());
        query.setCodeid("HIS_FILE");
        criteria.setCondition(query);
        BasCodes basGtnVO = null;
//		List<Remind> basGtnVOList = new ArrayList<Remind>();
//		System.out.println();
//		List<Remind> remindList = .queryByList(criteria);
        List<BasCodesVO> basCodesVOList = new ArrayList<BasCodesVO>();
        List<BasCodes> list =  basCodesMybatisDao.queryByList(criteria);
        for (BasCodes basCodes : list) {
            BasCodesVO basCodesVO = new BasCodesVO();
//            BeanUtils.copyProperties(basCodes, basCodesVO);
            basCodesVO.setCodenameC(basCodes.getCodenameC());
            basCodesVO.setCodenameE(basCodes.getCodenameE());
            basCodesVO.setEditwho(basCodes.getEditwho());
            if(basCodes.getEdittime()!=null){
                basCodesVO.setEdittime(basCodes.getEdittime());
            }
            basCodesVOList.add(basCodesVO);
        }
//		int total = basGtnMybatisDao.queryByCount(criteria);
        datagrid.setTotal((long)basCodesVOList.size() );
        datagrid.setRows(basCodesVOList);
        return datagrid;
    }
//历史档案管理
    public Json getHistoryFileInfo(BasCodesQuery query){
        BasCodes basCodes = basCodesMybatisDao.queryByIdAndCodenameC(query);
        if(basCodes == null){
            return Json.error("历史文档不存在！");
        }
        return Json.success("",basCodes);
    }
//历史档案管理
    public Json editDocAsnCertificate(BasCodesForm basCodesForm) {
        Json json = new Json();
        //DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.findById(docAsnCertificateForm.getSku());
        //BeanUtils.copyProperties(docAsnCertificateForm, docAsnCertificate);
        basCodesForm.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
        basCodesMybatisDao.updateCodenameCBySelective(basCodesForm);
        json.setSuccess(true);
        return json;
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

    /**
     * 收货单位首营申请  通过收货单位查询货主
     * @param enterpriseId
     * @return
     */
    public List<EasyuiCombobox> getClientByRecevingId(String enterpriseId){
        List<EasyuiCombobox> baseCodesVOList = new ArrayList<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        Map<String,Object> map = new HashMap<>();
//        map.put("codeid",codeid);
        map.put("isUse","1");
        map.put("enterpriseId",enterpriseId);
        map.put("firstState","40");
        mybatisCriteria.setCondition(map);
//        mybatisCriteria.setOrderByClause("show_sequence");
        List<GspReceiving> list =  gspReceivingMybatisDao.queryByList(mybatisCriteria);
        if(list!=null && list.size()>0){
            EasyuiCombobox easyuiCombobox = new EasyuiCombobox();
            easyuiCombobox.setId("");
            easyuiCombobox.setValue("");
            //easyuiCombobox.setSelected(true);
            System.out.println();
            baseCodesVOList.add(easyuiCombobox);
            for(GspReceiving b : list){
                easyuiCombobox = new EasyuiCombobox();
                easyuiCombobox.setId(b.getReceivingId());
                easyuiCombobox.setValue(b.getClientId());
                baseCodesVOList.add(easyuiCombobox);
            }

        }
        return baseCodesVOList;
    }






    /**
     * 验证PDA请求版本
     */
    public Json verifyRequestValidation(String version) {

        Json json = new Json();
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtil.isEmpty(version)) {

            PdaResult result = new PdaResult(PdaResult.CODE_FAILURE, "当前版本过旧，请根据首页右上角个人中心中的更新流程进行更新");
            resultMap.put(Constant.RESULT, result);
            json.setSuccess(false);
            json.setObj(resultMap);
            return json;
        }

        BasCodesQuery basCodesQuery = new BasCodesQuery();
        basCodesQuery.setCodeid(Constant.CODE_CATALOG_PDA_VERSION);
        basCodesQuery.setCode(Constant.CODE_PDA_VERSION);
        BasCodes basCodes = basCodesMybatisDao.queryById(basCodesQuery);
        if (null == basCodes || StringUtil.isEmpty(basCodes.getUdf1())) {

            PdaResult result = new PdaResult(PdaResult.CODE_FAILURE, "服务器配置出错，请联系管理员");
            resultMap.put(Constant.RESULT, result);
            json.setSuccess(false);
            json.setObj(resultMap);
            return json;
        }

        if (!version.equals(basCodes.getUdf1())) {
            PdaResult result = new PdaResult(PdaResult.CODE_FAILURE, "当前版本过旧，请根据首页右上角个人中心中的更新流程进行更新");
            resultMap.put(Constant.RESULT, result);
            json.setSuccess(false);
            json.setObj(resultMap);
            return json;
        }

        return Json.success("");
    }
}