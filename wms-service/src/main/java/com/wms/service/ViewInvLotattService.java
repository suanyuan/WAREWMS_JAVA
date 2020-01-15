package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.ViewInvLotattQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.Json;
import com.wms.vo.ViewInvLotattVO;
import com.wms.vo.form.ViewInvLotattForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("viewInvLotattService")
public class ViewInvLotattService extends BaseService {

    @Autowired
//	private ViewInvLotattDao viewInvLotattDao;
    private ViewInvLotattMybatisDao viewInvLotattMybatisDao;

    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private BasLocationMybatisDao basLocationMybatisDao;
    @Autowired
    private InvLotLocIdMybatisDao invLotLocIdMybatisDao;
    @Autowired
    private DocOrderUtilService docOrderUtilService;

    /**
     * 根据分页显示
     *
     * @param query
     * @return
     */
    public EasyuiDatagrid<ViewInvLotattVO> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvLotattQuery query) {
        EasyuiDatagrid<ViewInvLotattVO> datagrid = new EasyuiDatagrid<ViewInvLotattVO>();
//		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<ViewInvLotatt> viewInvLotattList = viewInvLotattMybatisDao.queryByPageList(mybatisCriteria);
        //总计
        ViewInvLotatt viewInvLotattSum=viewInvLotattMybatisDao.queryByListSum(mybatisCriteria);

        ViewInvLotattVO viewInvLotattVO = null;
        List<ViewInvLotattVO> viewInvLotattVOList = new ArrayList<ViewInvLotattVO>();
        for (ViewInvLotatt viewInvLotatt : viewInvLotattList) {//james
            viewInvLotattVO = new ViewInvLotattVO();
            BeanUtils.copyProperties(viewInvLotatt, viewInvLotattVO);
            //总计
            viewInvLotattVO.setFmqtySum(viewInvLotattSum.getFmqtySum());
            viewInvLotattVO.setFmqtyEachSum(viewInvLotattSum.getFmqtyEachSum());
            viewInvLotattVO.setQtyallocatedSum(viewInvLotattSum.getQtyallocatedSum());
            viewInvLotattVO.setQtyallocatedEachSum(viewInvLotattSum.getQtyallocatedEachSum());
            viewInvLotattVO.setQtyavailedSum(viewInvLotattSum.getQtyavailedSum());
            viewInvLotattVO.setQtyavailedEachSum(viewInvLotattSum.getQtyavailedEachSum());
            //供应商名称
            if(viewInvLotattVO.getLotatt08()!=null) {
                String loatt08=viewInvLotattVO.getLotatt08();
                BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(loatt08);
                if(basCustomer!=null) {
                    viewInvLotattVO.setLotatt08(basCustomer.getDescrC());
                }
            }
            viewInvLotattVOList.add(viewInvLotattVO);
        }
        datagrid.setTotal((long) viewInvLotattMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(viewInvLotattVOList);
        return datagrid;
    }
    /**
     * 根据分页显示  非待检
     *
     * @param query
     * @return
     */
    public EasyuiDatagrid<ViewInvLotattVO> getPagedDatagridNotDJ(EasyuiDatagridPager pager, ViewInvLotattQuery query) {
        EasyuiDatagrid<ViewInvLotattVO> datagrid = new EasyuiDatagrid<ViewInvLotattVO>();
//		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<ViewInvLotatt> viewInvLotattList = viewInvLotattMybatisDao.queryByPageList(mybatisCriteria);
        ViewInvLotattVO viewInvLotattVO = null;
        List<ViewInvLotattVO> viewInvLotattVOList = new ArrayList<ViewInvLotattVO>();
        for (ViewInvLotatt viewInvLotatt : viewInvLotattList) {//james
            viewInvLotattVO = new ViewInvLotattVO();
            BeanUtils.copyProperties(viewInvLotatt, viewInvLotattVO);
            //供应商名称
            if(viewInvLotattVO.getLotatt08()!=null) {
                String loatt08=viewInvLotattVO.getLotatt08();
                BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(loatt08);
                if(basCustomer!=null) {
                    viewInvLotattVO.setLotatt08(basCustomer.getDescrC());
                }
            }
            //筛选非待检
            if(!viewInvLotattVO.getLotatt10().equals("DJ")) {
                viewInvLotattVOList.add(viewInvLotattVO);
            }
        }
        datagrid.setTotal((long) viewInvLotattMybatisDao.queryByCountNotDJ(mybatisCriteria));
        datagrid.setRows(viewInvLotattVOList);
        return datagrid;
    }
    /**
     * 根据分页显示  非待检或非不合格合格或非待处理
     *
     * @param query
     * @return
     */
    public EasyuiDatagrid<ViewInvLotattVO> getPagedDatagridByData(EasyuiDatagridPager pager, ViewInvLotattQuery query) {
        EasyuiDatagrid<ViewInvLotattVO> datagrid = new EasyuiDatagrid<ViewInvLotattVO>();
//		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<ViewInvLotatt> viewInvLotattList = viewInvLotattMybatisDao.queryByPageListByData(mybatisCriteria);
        ViewInvLotattVO viewInvLotattVO = null;
        List<ViewInvLotattVO> viewInvLotattVOList = new ArrayList<ViewInvLotattVO>();
        for (ViewInvLotatt viewInvLotatt : viewInvLotattList) {//james
            viewInvLotattVO = new ViewInvLotattVO();
            BeanUtils.copyProperties(viewInvLotatt, viewInvLotattVO);
            //供应商名称
            if(StringUtil.isNotEmpty(viewInvLotattVO.getLotatt08())) {
                String loatt08=viewInvLotattVO.getLotatt08();
                BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(loatt08);
                if(basCustomer!=null) {
                    viewInvLotattVO.setLotatt08(basCustomer.getDescrC());
                }
            }
            viewInvLotattVOList.add(viewInvLotattVO);
        }
        datagrid.setTotal((long) viewInvLotattMybatisDao.queryByPageListByDataCount(mybatisCriteria));
        datagrid.setRows(viewInvLotattVOList);
        return datagrid;
    }

    /**
     * 库存调整
     *
     * @param viewInvLotattForm
     * @return
     */
    public Json adjViewInvLotatt(ViewInvLotattForm viewInvLotattForm) {
        Json json = new Json();
//		ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(viewInvLotattForm.getFmid());//james
//		BeanUtils.copyProperties(viewInvLotattForm, viewInvLotatt);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        map.put("fmcustomerid", viewInvLotattForm.getFmcustomerid());
        map.put("fmsku", viewInvLotattForm.getFmsku());
        map.put("fmlotnum", viewInvLotattForm.getFmlotnum());
        map.put("fmlocation", viewInvLotattForm.getFmlocation());
        map.put("fmqty", String.valueOf(viewInvLotattForm.getFmqty()));
        map.put("fmid", '*');               //跟踪号
        map.put("lotatt11", viewInvLotattForm.getLotatt11());//目标数量
        map.put("lotatt12", viewInvLotattForm.getLotatt12());//调整原因
        map.put("lotatt12text", viewInvLotattForm.getLotatt12text());//原因说明
        map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
        ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(map);
        if (viewInvLotatt != null) {
            viewInvLotattMybatisDao.invAdj(map);
            String result = map.get("result").toString();
            if (result.substring(0, 3).equals("000")) {
                json.setSuccess(true);
                json.setMsg("库存调整成功！");
                docOrderUtilService.deleteZeroInventory();
            } else {
                json.setSuccess(false);
                json.setMsg("库存调整失败！" + result);
            }
        }
        return json;
    }

    /**
     * 库存移动
     *
     * @param viewInvLotattForm
     * @return
     */

    public Json movViewInvLotatt(ViewInvLotattForm viewInvLotattForm) {
        Json json = new Json();
        String loattt05=null;
        String loattt04=null;
        String loattt10=null;
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("warehouseid", viewInvLotattForm.getWarehouseid());//仓库
//        map.put("fmcustomerid", viewInvLotattForm.getFmcustomerid());//货主
//        map.put("fmsku", viewInvLotattForm.getFmsku());             //产品代码
//        map.put("fmlotnum", viewInvLotattForm.getFmlotnum());       //批次
//        map.put("fmlocation", viewInvLotattForm.getFmlocation());   //库位
//        map.put("fmid", '*');               //跟踪号
//        map.put("fmqty", String.valueOf(viewInvLotattForm.getFmqty()));//可用件数
//        map.put("lotatt11text", viewInvLotattForm.getLotatt11text());//目标库位
//        map.put("lotatt11", viewInvLotattForm.getLotatt11());//移动数量
//        map.put("lotatt12", viewInvLotattForm.getLotatt12());//移动原因
//        map.put("lotatt12text", viewInvLotattForm.getLotatt12text());//原因描述
//        map.put("userid", viewInvLotattForm.getEditwho());
        //判断移动的库存是否已经冻结
              InvLotLocId invLotLocId=new InvLotLocId();
              invLotLocId.setCustomerid(viewInvLotattForm.getFmcustomerid());
              invLotLocId.setSku(viewInvLotattForm.getFmsku());
              invLotLocId.setLotnum(viewInvLotattForm.getFmlotnum());
              invLotLocId.setLocationid(viewInvLotattForm.getFmlocation());
              InvLotLocId locId=invLotLocIdMybatisDao.queryByKeyInvloatatt(invLotLocId);
                if(locId.getLotatt05()!=null) {
                    loattt05 = locId.getLotatt05() + "";
                    }
                if(locId.getLotatt04()!=null) {
                    loattt04 = locId.getLotatt04() + "";
                 }
                if(locId.getLotatt10()!=null) {
                    loattt10 = locId.getLotatt10() + "";
                     }
              if(locId!=null&&(locId.getOnholdlocker()==99||loattt10.equals("DJ"))){
                  return json.error("库位:" +viewInvLotattForm.getFmlocation() +",货主:" + viewInvLotattForm.getFmcustomerid()+
                          ",产品代码:" +viewInvLotattForm.getFmsku()+ ",序列号:" + loattt05 + "生产批号:" + loattt04 + ",移动失败,此库存已经冻结或者状态为待检不可移动!");
              }else if(locId==null){
                  return json.error("库位:" +viewInvLotattForm.getFmlocation() +",货主:" + viewInvLotattForm.getFmcustomerid()+
                          ",产品代码:" +viewInvLotattForm.getFmsku()+ ",序列号:" + loattt05 + "生产批号:" + loattt04 + ",移动失败,此库存不存在!");
              }
         //1.判断目标库位是否存在    2.调用移库存储过程
            if(islocationid(viewInvLotattForm.getLotatt11text())) {
                ViewInvLotattQuery query=new ViewInvLotattQuery();
                BeanUtils.copyProperties(viewInvLotattForm, query);
                query.setFmid("*");
                viewInvLotattMybatisDao.invMov(query);
//                String result = map.get("result").toString();
                if (query.getResult().substring(0, 3).equals("000")) {
                    json.setSuccess(true);
                    json.setMsg("库存移动成功！");
                } else {
                    json.setSuccess(false);
                    String loc =viewInvLotattForm.getFmlocation();
                    String sku =viewInvLotattForm.getFmsku();
                    String customerid =viewInvLotattForm.getFmcustomerid();
                    json.setMsg("库位:" + loc +",货主:" + customerid + ",产品代码:" + sku + ",序列号:" + loattt05 + "生产批号:" + loattt04 + ",移动失败！" + query.getResult());
                }

            }else{
                json.setSuccess(false);
                json.setMsg("目标库位不存在");
            }

        return json;
    }

    /**
     * 库存移动 多条
     *
     * @param forms
     * @return
     */

    public Json movViewInvLotattList(String forms) {
        Json json = new Json();
        StringBuffer results = new StringBuffer();
//        json转集合
        List<ViewInvLotattForm> list = JSON.parseArray(forms, ViewInvLotattForm.class);
        //判断目标库位是否存在
        if(!islocationid(list.get(0).getLotatt11text())) {

            json.setSuccess(false);
            json.setMsg("目标库位不存在");
            return json;
        }
        Boolean con = true;
        for (ViewInvLotattForm form : list) {

            form.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
            form.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            Json json1 = movViewInvLotatt(form);
            if (json1.isSuccess()) {

            } else {
                con = false;
                results.append(json1.getMsg()).append(" ");
            }

        }
        if (con) {
            json.setSuccess(true);
            json.setMsg("库存移动成功!");
            docOrderUtilService.deleteZeroInventory();
        } else {
            json.setSuccess(false);
            json.setMsg("移动失败库存: " + results.toString());
        }


        return json;
    }

    /**
     * 库位移动
     *
     * @param
     * @return
     */

    public Json movViewInvLotattLoc(String fmlocation,String tolocation) {
        Json json = new Json();
        StringBuffer results = new StringBuffer();
        //原始库位
         String[] arr=fmlocation.split(",");
         if(arr.length<=0){
             return  json.error("请输入起始库位!");
         }
        //判断目标库位是否存在
//        if(!islocationid(tolocation)) {
//
//            json.setSuccess(false);
//            json.setMsg("目标库位不存在");
//            return json;
//        }
        //根据库位查出所有库存
        List<InvLotLocId> lotLocIdList=new ArrayList<>();
        List<ViewInvLotattForm> list=new ArrayList();
        ViewInvLotattForm viewInvLotattForm=null;
        for (String s : arr) {
            lotLocIdList=invLotLocIdMybatisDao.queryAllInvLotLocByLocationid(s);
            if(lotLocIdList.size()>0){
                for (InvLotLocId invLotLocId : lotLocIdList) {
                    viewInvLotattForm=new ViewInvLotattForm();
                    viewInvLotattForm.setFmcustomerid(invLotLocId.getCustomerid()); //货主
                    viewInvLotattForm.setFmsku(invLotLocId.getSku());               //产品代码
                    viewInvLotattForm.setFmlotnum(invLotLocId.getLotnum());         //批次
                    viewInvLotattForm.setFmlocation(invLotLocId.getLocationid());   //起始库位
                    viewInvLotattForm.setFmqty(new BigDecimal(invLotLocId.getQty()));//库存件数
                    viewInvLotattForm.setLotatt11(invLotLocId.getQtyavailed()+"");   //可用件数
                    viewInvLotattForm.setLotatt11text(tolocation);                   //目标库位
                    viewInvLotattForm.setLotatt12("");                               //移动原因
                    viewInvLotattForm.setLotatt12text("");                            //原因描述
                    viewInvLotattForm.setLotatt04(invLotLocId.getLotatt04());        //批号
                    viewInvLotattForm.setLotatt05(invLotLocId.getLotatt05());        //序列号
                    viewInvLotattForm.setLotatt10(invLotLocId.getLotatt10());        //质量状态
                   list.add(viewInvLotattForm);
                }
            }
        }
       //判断是否有可移动库存
        if(list.size()<=0){
            return json.error("库位没有可移动的库存!");
        }
        Boolean con = true;

        for (ViewInvLotattForm form : list) {

            form.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
            form.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            Json json1 = movViewInvLotatt(form);
            if (json1.isSuccess()) {

            } else {
                con = false;
                results.append(json1.getMsg()).append(" ");
            }

        }
        if (con) {
            json.setSuccess(true);
            json.setMsg("库存移动成功!");
            docOrderUtilService.deleteZeroInventory();
        } else {
            json.setSuccess(false);
            json.setMsg("移动失败库存: " + results.toString());
        }


        return json;
    }

    /**
     * 库存冻结
     *
     * @param forms
     * @return
     */
    public Json holdViewInvLotatt(String forms) {
        Json json = new Json();
        //json转集合
        List<InvLotLocId> list = JSON.parseArray(forms, InvLotLocId.class);
        InvLotLocId  invLotLocId=null;
        for (InvLotLocId form : list) {
            invLotLocId=new InvLotLocId();
            invLotLocId.setLotnum(form.getLotnum());
            invLotLocId.setLocationid(form.getLocationid());
            invLotLocId.setSku(form.getSku());
            invLotLocId.setCustomerid(form.getCustomerid());
            InvLotLocId  locId = invLotLocIdMybatisDao.queryByKey(invLotLocId);
            if(locId!=null){
                invLotLocId.setOnholdlocker(99);
                invLotLocIdMybatisDao.updateByKey(invLotLocId);
            }
        }
        json.setSuccess(true);
        json.setMsg("冻结成功");

        return json;
    }
    /**
     * 库存解冻
     *
     * @param forms
     * @return
     */
    public Json noholdViewInvLotatt(String forms) {
        Json json = new Json();
        //json转集合
        List<InvLotLocId> list = JSON.parseArray(forms, InvLotLocId.class);
        InvLotLocId  invLotLocId=null;
        for (InvLotLocId form : list) {
            invLotLocId=new InvLotLocId();
            invLotLocId.setLotnum(form.getLotnum());
            invLotLocId.setLocationid(form.getLocationid());
            invLotLocId.setSku(form.getSku());
            invLotLocId.setCustomerid(form.getCustomerid());
            InvLotLocId  locId = invLotLocIdMybatisDao.queryByKey(invLotLocId);
            if(locId!=null){
                invLotLocId.setOnholdlocker(0);
                invLotLocIdMybatisDao.updateByKey(invLotLocId);
            }
        }
        json.setSuccess(true);
        json.setMsg("解冻成功");

        return json;
    }


    public List<EasyuiCombobox> getViewInvLotattCombobox() {//未用到
        List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
        EasyuiCombobox combobox = null;
        List<BasZonegroup> basZonegroupList = viewInvLotattMybatisDao.queryByAll();
        if (basZonegroupList != null && basZonegroupList.size() > 0) {
            for (BasZonegroup basZonegroup : basZonegroupList) {
                combobox = new EasyuiCombobox();
                combobox.setId(String.valueOf(basZonegroup.getZonegroup()));
                combobox.setValue(basZonegroup.getDescr());
                comboboxList.add(combobox);
            }
        }
        return comboboxList;
    }

    /**
     * 产品库存查询接口
     */
    public List<InvLotLocIdSkuInvLotAtt> getInvLotLocIdSkuInvLotAttList(String sku, String lotatt04, String lotatt05) {

        List<InvLotLocIdSkuInvLotAtt> invLotLocIdSkuInvLotAttList = new ArrayList<InvLotLocIdSkuInvLotAtt>();
        invLotLocIdSkuInvLotAttList = viewInvLotattMybatisDao.getInvLotLocIdSkuInvLotAttList(sku, lotatt04, lotatt05);

        return invLotLocIdSkuInvLotAttList;
    }

    //判断库位是否存在

    private Boolean islocationid(String locationid){
          Boolean con=true;
          BasLocation basLocation =basLocationMybatisDao.queryById(locationid);
          if(basLocation==null){
              con=false;
          }

        return con;
    }

}