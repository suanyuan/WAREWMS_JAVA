package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.ActAllocationDetailsQuery;
import com.wms.query.ActTransactionLogQuery;
import com.wms.query.CouRequestDetailsQuery;
import com.wms.query.CouRequestHeaderQuery;
import com.wms.service.importdata.ImportCouRequestDataService;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.CouRequestHeaderVO;
import com.wms.vo.Json;
import com.wms.vo.form.pda.PageForm;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service("couRequestHeaderService")
public class CouRequestHeaderService extends BaseService {

    @Autowired
    private CouRequestHeaderMybatisDao couRequestHeaderMybatisDao;
    @Autowired
    private InvLotLocIdMybatisDao invLotLocIdMybatisDao;
  @Autowired
    private CouRequestDetailsMybatisDao couRequestDetailsMybatisDao;
  @Autowired
    private ImportCouRequestDataService importCouRequestDataService;
    @Autowired
    private ActTransactionLogMybatisDao actTransactionLogMybatisDao;
    @Autowired
    private ActAllocationDetailsMybatisDao actAllocationDetailsMybatisDao;

    //分页查询
    public EasyuiDatagrid<CouRequestHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, CouRequestHeaderQuery query) {
        EasyuiDatagrid<CouRequestHeaderVO> datagrid = new EasyuiDatagrid<CouRequestHeaderVO>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<CouRequestHeader> couRequestHeaderList = couRequestHeaderMybatisDao.queryByList(mybatisCriteria);
        CouRequestHeaderVO couRequestHeaderVO = null;
        List<CouRequestHeaderVO> couRequestHeaderVOList = new ArrayList<CouRequestHeaderVO>();
        for (CouRequestHeader couRequestHeader : couRequestHeaderList) {
            couRequestHeaderVO = new CouRequestHeaderVO();
            BeanUtils.copyProperties(couRequestHeader, couRequestHeaderVO);
            couRequestHeaderVOList.add(couRequestHeaderVO);
        }
        datagrid.setTotal((long)couRequestHeaderMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(couRequestHeaderVOList);
        return datagrid;
    }

    //获得盘点计划
    public EasyuiDatagrid<InvLotLocId> getcouRequestInfo(EasyuiDatagridPager pager,CouRequestDetailsQuery query) {
        EasyuiDatagrid<InvLotLocId> datagrid = new EasyuiDatagrid<InvLotLocId>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<InvLotLocId> lotLocIdList=invLotLocIdMybatisDao.queryByListByCouRequest(mybatisCriteria);
        datagrid.setTotal((long)invLotLocIdMybatisDao.queryByListByCouRequestCount(mybatisCriteria));
        datagrid.setRows(lotLocIdList);
        return datagrid;
    }

    //生成盘点计划
    public Json ToGenerateInventoryPlan(String forms) {
        Json json = new Json();
        //json转集合
        List<InvLotLocId> list = JSON.parseArray(forms, InvLotLocId.class);
        List<InvLotLocId> listAll=new ArrayList<>();
        List<InvLotLocId> listAdd=new ArrayList<>();
         for(InvLotLocId locId:list){
             MybatisCriteria mybatisCriteria = new MybatisCriteria();
             mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(locId));
             listAll=invLotLocIdMybatisDao.queryByListByCouRequest(mybatisCriteria);
             for(InvLotLocId locIdAll:listAll){
                 listAdd.add(locIdAll);
             }
         }
         if(listAdd.size()<=0){
             json.setSuccess(true);
             json.setMsg("生成计划失败,没有匹配的产品!");
             return json;
         }
       try {
           /*获取新的号 生成主单*/
           Map<String, Object> map = new HashMap<>();
           map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
           couRequestHeaderMybatisDao.getIdSequence(map);
           String resultCode = "";
           String resultNo = "";
           if (map.get("resultCode") != null) {
               resultCode = map.get("resultCode").toString();
           }
           if (map.get("resultNo") != null) {
               resultNo = map.get("resultNo").toString();
           }
           if (resultCode.substring(0, 3).equals("000")) {
               CouRequestHeader couRequestHeader = new CouRequestHeader();
               couRequestHeader.setCycleCountno(resultNo);
               couRequestHeader.setStatus("00");
               couRequestHeader.setFuzzyc(null);
               couRequestHeader.setAddtime(new Date());  //申请时间
               couRequestHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());//申请人
               couRequestHeader.setStarttime(new Date());
//            CouRequestHeader.setEndtime(null);
               couRequestHeaderMybatisDao.add(couRequestHeader);
               if (couRequestHeaderMybatisDao.queryById(couRequestHeader) == null) {
                   json.setSuccess(true);
                   json.setMsg("生成计划失败!");
                   return json;
               }

//循环加入细单
               for (InvLotLocId invLotLocId : listAdd) {
                   CouRequestDetails couRequestDetails = new CouRequestDetails();
                   couRequestDetails.setCycleCountno(resultNo);
                   couRequestDetails.setCycleCountlineno(couRequestDetailsMybatisDao.getCycleCountlineno(resultNo) + 1);
                   couRequestDetails.setCustomerid(invLotLocId.getCustomerid());
                   couRequestDetails.setSku(invLotLocId.getSku());
                   couRequestDetails.setLocationid(invLotLocId.getLocationid());
                   couRequestDetails.setQtyInv(invLotLocId.getQty() == null ? 0 : invLotLocId.getQty());
                   couRequestDetails.setQtyAct(0);
                   couRequestDetails.setLotatt04(invLotLocId.getLotatt04());
                   couRequestDetails.setLotatt05(invLotLocId.getLotatt05());
                   couRequestDetails.setAddtime(new Date());
                   couRequestDetails.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
//                couRequestDetails.setEdittime(null);
//                couRequestDetails.setEditwho(null);
                   couRequestDetailsMybatisDao.add(couRequestDetails);
               }
               json.setSuccess(true);
               json.setMsg("生成任务成功!");
           } else {
               json.setSuccess(true);
               json.setMsg("生成任务失败!未获取到主单号!");
               throw new Exception();
           }
       }catch (Exception e){
           e.printStackTrace();
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
       }
        return json;
    }

    //获得盘点计划 骨科
    public EasyuiDatagrid<ActAllocationDetails> getcouRequestInfoGuKe(EasyuiDatagridPager pager,CouRequestDetailsQuery query) {
        EasyuiDatagrid<ActAllocationDetails> datagrid = new EasyuiDatagrid<ActAllocationDetails>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        //根据时间查询出库事务
        ActTransactionLogQuery logQuery=new ActTransactionLogQuery();
        logQuery.setTransactiontype("SO");  //事务类型
        logQuery.setDoctype("SO");  //单据类型
        logQuery.setStatus("99");//事务状态99=close
        logQuery.setTransactiontime(query.getSoTimeStart());//事务开始时间
        logQuery.setAddtime(query.getSoTimeEnd());         //事务结束时间
        logQuery.setSkuGroup1(query.getProductLineName());  //产品线
        List<ActTransactionLog> transactionLogList =actTransactionLogMybatisDao.queryByListByTypeAndTime(logQuery);
        //根据事务查询出来的出库单号从分配表查询出所有分配明细
        List<ActAllocationDetails>  detailsList=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        for (ActTransactionLog t: transactionLogList) {
            map.put(t.getDocno(),"");
        }
        Object[] array =map.keySet().toArray();
        if(array.length>0) {
            //根据出库单号查出分配明细
            query.setArray(array);
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
            detailsList=actAllocationDetailsMybatisDao.queryByLocByOrderNo(mybatisCriteria);
        }else{
            datagrid.setTotal((long)0);
            datagrid.setRows(detailsList);
            return datagrid;
        }

        datagrid.setTotal((long)actAllocationDetailsMybatisDao.queryByLocByOrderNoCount(mybatisCriteria));
        datagrid.setRows(detailsList);
        return datagrid;
    }

    //生成盘点计划 骨科
    public Json ToGenerateInventoryPlanGuKe(CouRequestDetailsQuery query) {
        Json json = new Json();
        List<InvLotLocId> listAdd=new ArrayList<>();

        //根据时间查询拿到所有出库分配明细
        EasyuiDatagridPager pager=new EasyuiDatagridPager();
        pager.setRows(0);
        EasyuiDatagrid<ActAllocationDetails>  datagrid=getcouRequestInfoGuKe(pager,query);
        List<ActAllocationDetails> detailsList=datagrid.getRows();
        //根据出库分配明细查出对应库存 如果没有插入0库存
        for (ActAllocationDetails s: detailsList) {
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            ActAllocationDetailsQuery q=new ActAllocationDetailsQuery();
            q.setSku(s.getSku());
            q.setCustomerid(s.getCustomerid());
            q.setLocation(s.getLocation());
            q.setLotatt04(s.getLotatt04());
            q.setLotatt05(s.getLotatt05());
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(q));
            List<InvLotLocId> lotLocIdList=invLotLocIdMybatisDao.queryByListByCouRequest(mybatisCriteria);
            if(lotLocIdList.size()>0){
                listAdd.addAll(lotLocIdList);
            }else{
                //如果库位库存为空 插入一条为0的库存
                InvLotLocId add=new InvLotLocId();
                add.setLocationid(s.getLocation());
                add.setCustomerid(s.getCustomerid());
                add.setSku(s.getSku());
                add.setLotatt01(s.getLotatt01());
                add.setLotatt02(s.getLotatt02());
                add.setLotatt04(s.getLotatt04());
                add.setLotatt05(s.getLotatt05());
                add.setLotatt06(s.getLotatt06());
                add.setProductLineName(s.getProductLineName());
                add.setQty(0.0);
                listAdd.add(add);
            }
        }



        if(listAdd.size()<=0){
            json.setSuccess(true);
            json.setMsg("生成计划失败,没有匹配的库存!");
            return json;
        }
        try {
            /*获取新的号 生成主单*/
            Map<String, Object> map = new HashMap<>();
            map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            couRequestHeaderMybatisDao.getIdSequence(map);
            String resultCode = "";
            String resultNo = "";
            if (map.get("resultCode") != null) {
                resultCode = map.get("resultCode").toString();
            }
            if (map.get("resultNo") != null) {
                resultNo = map.get("resultNo").toString();
            }
            if (resultCode.substring(0, 3).equals("000")) {
                CouRequestHeader couRequestHeader = new CouRequestHeader();
                couRequestHeader.setCycleCountno(resultNo);
                couRequestHeader.setStatus("00");
                couRequestHeader.setFuzzyc(null);
                couRequestHeader.setAddtime(new Date());  //申请时间
                couRequestHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());//申请人
                couRequestHeader.setStarttime(new Date());
//            CouRequestHeader.setEndtime(null);
                couRequestHeaderMybatisDao.add(couRequestHeader);
                if (couRequestHeaderMybatisDao.queryById(couRequestHeader) == null) {
                    json.setSuccess(true);
                    json.setMsg("生成计划失败!");
                    return json;
                }

//循环加入细单
                for (InvLotLocId invLotLocId : listAdd) {
                    CouRequestDetails couRequestDetails = new CouRequestDetails();
                    couRequestDetails.setCycleCountno(resultNo);
                    couRequestDetails.setCycleCountlineno(couRequestDetailsMybatisDao.getCycleCountlineno(resultNo) + 1);
                    couRequestDetails.setCustomerid(invLotLocId.getCustomerid());
                    couRequestDetails.setSku(invLotLocId.getSku());
                    couRequestDetails.setLocationid(invLotLocId.getLocationid());
                    couRequestDetails.setQtyInv(invLotLocId.getQty() == null ? 0 : invLotLocId.getQty());
                    couRequestDetails.setQtyAct(0);
//                    couRequestDetails.setLotatt01(invLotLocId.getLotatt01());
//                    couRequestDetails.setLotatt02(invLotLocId.getLotatt02());
                    couRequestDetails.setLotatt04(invLotLocId.getLotatt04());//批号
                    couRequestDetails.setLotatt05(invLotLocId.getLotatt05());//序列号
//                    couRequestDetails.setLotatt06(invLotLocId.getLotatt06());
                    couRequestDetails.setAddtime(new Date());
                    couRequestDetails.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
//                couRequestDetails.setEdittime(null);
//                couRequestDetails.setEditwho(null);
                    couRequestDetailsMybatisDao.add(couRequestDetails);
                }
                json.setSuccess(true);
                json.setMsg("生成任务成功!");
            } else {
                json.setSuccess(true);
                json.setMsg("生成任务失败!未获取到主单号!");
                throw new Exception();
            }
        }catch (Exception e){
            json.setSuccess(true);
            json.setMsg("生成任务失败!请重试!");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return json;
    }





    //导入
    public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
        Json json = null;
        MultipartFile excelFile = mhsr.getFile("uploadData");
        //System.out.println("======excelFile.getSize()=="+excelFile.getSize()+"======="+excelFile.getInputStream().getClass().getName());
        if(excelFile != null && excelFile.getSize() > 0){
           json = importCouRequestDataService.importExcelData(excelFile);
        }
        return json;
    }


    //删除盘点计划单
    public Json deleteCouRequstHeader(String id) {
        Json json = new Json();
        CouRequestHeader couRequestHeader = couRequestHeaderMybatisDao.queryById(id);
        if (couRequestHeader != null&& couRequestHeader.getStatus().equals("00")) {
            couRequestHeaderMybatisDao.delete(couRequestHeader);
            couRequestDetailsMybatisDao.delete(couRequestHeader);
            json.setSuccess(true);
            json.setMsg("删除成功!");
        } else {
            json.setSuccess(false);
            json.setMsg("单号状态不可删除!");
        }

        return json;
    }

    /**
     * 关单
     * 目前行状态只有 00 && 40
     *
     * @param form ~
     * @return ~
     */
    public Json endCouRequest(CouRequestHeader form) {
          Json json=new Json();
        CouRequestHeader couRequestHeader = new CouRequestHeader();
        couRequestHeader.setCycleCountno(form.getCycleCountno());
        CouRequestHeader requestHeader = couRequestHeaderMybatisDao.queryById(couRequestHeader);
        switch (requestHeader.getStatus()) {
            case "00":
                json.setSuccess(false);
                json.setMsg("盘点单[" + form.getCycleCountno() + "]有未完成的盘点任务，不可结束!");
                break;
            case "30":
            case "40":
                couRequestHeader.setStatus("99");
                couRequestHeader.setNotes(form.getNotes());
                couRequestHeaderMybatisDao.updateBySelective(couRequestHeader);
                json.setSuccess(true);
                json.setMsg("盘点单[" + form.getCycleCountno() + "]操作成功!");
                break;
            default:
                json.setSuccess(false);
                json.setMsg("盘点单[" + form.getCycleCountno() + "]当前状态不可操作!");
                break;
        }

        return json;
    }
    /**
     * 取消单
     * 目前行状态只有 00 && 40
     *
     * @param form ~
     * @return ~
     */
    public Json cancelCouRequest(CouRequestHeader form) {
          Json json=new Json();
        CouRequestHeader couRequestHeader = new CouRequestHeader();
        couRequestHeader.setCycleCountno(form.getCycleCountno());
        CouRequestHeader requestHeader = couRequestHeaderMybatisDao.queryById(couRequestHeader);
        switch (requestHeader.getStatus()) {
            case "00":
                couRequestHeader.setStatus("90");
                couRequestHeaderMybatisDao.updateBySelective(couRequestHeader);
                json.setSuccess(true);
                json.setMsg("盘点单[" + form.getCycleCountno() + "]操作成功!");
                break;
            default:
                json.setSuccess(false);
                json.setMsg("盘点单[" + form.getCycleCountno() + "]当前状态不可操作!");
                break;
        }

        return json;
    }

    /**
     * 未完成盘点任务
     * @param pageForm ~
     * @return ~
     */
    public Json queryUndoneList(PageForm pageForm) {

        Json json = new Json();

        List<CouRequestHeader> couRequestHeaderList = couRequestHeaderMybatisDao.queryUndoneList(pageForm.getStart(), pageForm.getPageSize());
        List<CouRequestHeaderVO> couRequestHeaderVOList = new ArrayList<>();
        CouRequestHeaderVO couRequestHeaderVO;
        for (CouRequestHeader couRequestHeader : couRequestHeaderList) {

            couRequestHeaderVO = new CouRequestHeaderVO();
            BeanUtils.copyProperties(couRequestHeader, couRequestHeaderVO);
            couRequestHeaderVOList.add(couRequestHeaderVO);
        }

        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_MSG);
        json.setObj(couRequestHeaderVOList);
        return json;
    }

    /**
     * 查询盘点任务头档
     * @param cycleCountno 盘点单号
     * @return  ~
     */
    public Json queryCouRequestHeader(String cycleCountno) {

        Json json = new Json();

        CouRequestHeader couRequestHeader = couRequestHeaderMybatisDao.queryById(cycleCountno);
        CouRequestHeaderVO couRequestHeaderVO = new CouRequestHeaderVO();
        if (couRequestHeader != null) {

            BeanUtils.copyProperties(couRequestHeader, couRequestHeaderVO);
        }else {

            json.setSuccess(false);
            json.setMsg("查无此盘点单数据");
            return json;
        }

        json.setObj(couRequestHeaderVO);
        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_MSG);
        return json;
    }
}