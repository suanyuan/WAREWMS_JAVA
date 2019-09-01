package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasZonegroup;
import com.wms.entity.InvLotLocIdSkuInvLotAtt;
import com.wms.entity.ViewInvLotatt;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvLotattMybatisDao;
import com.wms.query.ViewInvLotattQuery;
import com.wms.query.pda.PdaInventoryQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.Json;
import com.wms.vo.ViewInvLotattVO;
import com.wms.vo.form.ViewInvLotattForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CommonService commonService;

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
        ViewInvLotattVO viewInvLotattVO = null;
        List<ViewInvLotattVO> viewInvLotattVOList = new ArrayList<ViewInvLotattVO>();
        for (ViewInvLotatt viewInvLotatt : viewInvLotattList) {//james
            viewInvLotattVO = new ViewInvLotattVO();
            BeanUtils.copyProperties(viewInvLotatt, viewInvLotattVO);
            viewInvLotattVOList.add(viewInvLotattVO);
        }
        datagrid.setTotal((long) viewInvLotattMybatisDao.queryByCount(mybatisCriteria));
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

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("warehouseid", viewInvLotattForm.getWarehouseid());//仓库
        map.put("fmcustomerid", viewInvLotattForm.getFmcustomerid());//货主
        map.put("fmsku", viewInvLotattForm.getFmsku());             //产品代码
        map.put("fmlotnum", viewInvLotattForm.getFmlotnum());       //批次
        map.put("fmlocation", viewInvLotattForm.getFmlocation());   //库位
        map.put("fmid", '*');               //跟踪号
        map.put("fmqty", String.valueOf(viewInvLotattForm.getFmqty()));//可用件数
        map.put("lotatt11text", viewInvLotattForm.getLotatt11text());//目标库位
        map.put("lotatt11", viewInvLotattForm.getLotatt11());//移动数量
        map.put("lotatt12", viewInvLotattForm.getLotatt12());//移动原因
        map.put("lotatt12text", viewInvLotattForm.getLotatt12text());//原因描述
        map.put("userid", viewInvLotattForm.getEditwho());
        ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(map);
        if (viewInvLotatt != null) {
            viewInvLotattMybatisDao.invMov(map);
            String result = map.get("result").toString();
            if (result.substring(0, 3).equals("000")) {
                json.setSuccess(true);
                json.setMsg("库存移动成功！");
            } else {
                json.setSuccess(false);
                String loc = map.get("fmlocation") + "";
                String sku = map.get("fmsku") + "";
                String loattt05=viewInvLotatt.getLotatt05() + "";
                String loattt04 =viewInvLotatt.getLotatt04() + "";
                json.setMsg("库位:" + loc + ",产品代码:" + sku + ",序列号:"+loattt05+"生产批号:" + loattt04 + ",移动失败！" + result);
            }
        }else {
            json.setSuccess(false);
            json.setMsg("查无此库存数据");
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
        Boolean con = true;
        for (ViewInvLotattForm form : list) {

            form.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
            form.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            Json json1 = movViewInvLotatt(form);
            if (json1.isSuccess()) {

            } else {
                con = false;
                results.append(json1.getMsg()).append("<br/>");
            }

        }
        if (con) {
            json.setSuccess(true);
            json.setMsg("库存移动成功！!");
        } else {
            json.setSuccess(false);
            json.setMsg("部分库存移动失败!<br/>" + results.toString());
        }


        return json;
    }

    /**
     * 库存冻结
     *
     * @param viewInvLotattForm
     * @return
     */
    public Json holdViewInvLotatt(ViewInvLotattForm viewInvLotattForm) {
        Json json = new Json();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        map.put("fmcustomerid", viewInvLotattForm.getFmcustomerid());
        map.put("fmsku", viewInvLotattForm.getFmsku());
        map.put("fmlotnum", viewInvLotattForm.getFmlotnum());
        map.put("fmlocation", viewInvLotattForm.getFmlocation());
        map.put("fmid", viewInvLotattForm.getFmid());
        map.put("fmqty", String.valueOf(viewInvLotattForm.getFmqty()));
        map.put("lotatt12", viewInvLotattForm.getLotatt12());//冻结原因
        map.put("lotatt12text", viewInvLotattForm.getLotatt12text());//原因说明
        map.put("userid", SfcUserLoginUtil.getLoginUser().getId());
        ViewInvLotatt viewInvLotatt = viewInvLotattMybatisDao.queryById(map);
        if (viewInvLotatt != null) {
            viewInvLotattMybatisDao.invHold(map);
            String result = map.get("result").toString();
            if (result.substring(0, 3).equals("000")) {
                json.setSuccess(true);
                json.setMsg("库存冻结成功！");
            } else {
                json.setSuccess(false);
                json.setMsg("库存冻结失败！" + result);
            }
        }
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
     * 获取扫码库位产品的库存数据
     * @param query 详见Controller描述
     * @return ~
     */
    public Json queryInventory(PdaInventoryQuery query) {

        Json json = new Json();

        return json;
    }

    /**
     * 产品库存查询接口
     */
  public List<InvLotLocIdSkuInvLotAtt> getInvLotLocIdSkuInvLotAttList(String sku,String lotatt04,String lotatt05){

      List<InvLotLocIdSkuInvLotAtt> invLotLocIdSkuInvLotAttList=new ArrayList<InvLotLocIdSkuInvLotAtt>();
      invLotLocIdSkuInvLotAttList=viewInvLotattMybatisDao.getInvLotLocIdSkuInvLotAttList(sku,lotatt04,lotatt05);

      return invLotLocIdSkuInvLotAttList;
  };

}