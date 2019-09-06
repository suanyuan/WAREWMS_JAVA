package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.CouRequestDetails;
import com.wms.entity.CouRequestHeader;
import com.wms.entity.InvLotLocId;
import com.wms.mybatis.dao.CouRequestDetailsMybatisDao;
import com.wms.mybatis.dao.CouRequestHeaderMybatisDao;
import com.wms.mybatis.dao.InvLotLocIdMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.CouRequestDetailsQuery;
import com.wms.query.CouRequestHeaderQuery;
import com.wms.service.importdata.ImportCouRequestDataService;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.CouRequestHeaderVO;
import com.wms.vo.Json;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<InvLotLocId> getcouRequestInfo(CouRequestDetailsQuery query) {
        List<InvLotLocId> lotLocIdList=invLotLocIdMybatisDao.queryByListByCouRequest(query);

        return lotLocIdList;
    }

    //生成盘点计划
    public Json ToGenerateInventoryPlan(String forms) {
        Json json = new Json();
        //json转集合
        List<InvLotLocId> list = JSON.parseArray(forms, InvLotLocId.class);
        List<InvLotLocId> listAll=getcouRequestInfo(null);
        List<InvLotLocId> listAdd=new ArrayList<>();
         for(InvLotLocId locId:list){
             for(InvLotLocId locIdAll:listAll){
                 if(locId.getCustomerid().equals(locIdAll.getCustomerid())
                    &&locId.getSku().equals(locIdAll.getSku())
                    &&locId.getLocationid().equals(locIdAll.getLocationid())
                    &&locId.getLotatt04().equals(locIdAll.getLotatt04())
                    &&locId.getLotatt05().equals(locIdAll.getLotatt05())){
                     listAdd.add(locIdAll);
                     break;
                 }
             }
         }
         if(listAdd.size()<=0){
             json.setSuccess(true);
             json.setMsg("生成计划失败,没有匹配的产品!");
             return json;
         }
        /*获取新的号 生成主单*/
        Map<String, Object> map = new HashMap<>();
        map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        couRequestHeaderMybatisDao.getIdSequence(map);
        String resultCode = map.get("resultCode").toString();
        String resultNo = map.get("resultNo").toString();
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
                couRequestDetails.setQtyInv(invLotLocId.getQty()==null?0:invLotLocId.getQty());
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
            json.setMsg("生成任务失败!");
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

//
//    //增加
//    public Json addDocMtHeader(DocMtHeaderForm docMtHeaderForm) throws Exception {
//        Json json = new Json();
//        DocMtHeader docMtHeader = new DocMtHeader();
//        BeanUtils.copyProperties(docMtHeaderForm, docMtHeader);
//        couRequestHeaderMybatisDao.add(docMtHeader);
//        json.setSuccess(true);
//        return json;
//    }
//
//    //编辑
//    public Json editDocMtHeader(DocMtHeaderForm docMtHeaderForm) {
//        Json json = new Json();
//        DocMtHeader docMtHeader = couRequestHeaderMybatisDao.queryById(docMtHeaderForm.getMtno());
//        BeanUtils.copyProperties(docMtHeaderForm, docMtHeader);
//        couRequestHeaderMybatisDao.updateBySelective(docMtHeader);
//        json.setSuccess(true);
//        return json;
//    }
//
    //删除养护计划单
    public Json deleteCouRequstHeader(String id) {
        Json json = new Json();
        CouRequestHeader couRequestHeader = couRequestHeaderMybatisDao.queryById(id);
        if (couRequestHeader != null) {
            couRequestHeaderMybatisDao.delete(couRequestHeader);
            couRequestDetailsMybatisDao.delete(couRequestHeader);
            json.setSuccess(true);
            json.setMsg("删除成功!");
        } else {
            json.setSuccess(false);
            json.setMsg("单号不存在!");
        }

        return json;
    }
//
//    public List<EasyuiCombobox> getDocMtHeaderCombobox() {
//        List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//        EasyuiCombobox combobox = null;
//        List<DocMtHeader> couRequestList = couRequestHeaderMybatisDao.queryListByAll();
//        if (couRequestList != null && couRequestList.size() > 0) {
//            for (DocMtHeader docMtHeader : couRequestList) {
//                combobox = new EasyuiCombobox();
//                combobox.setId(docMtHeader.getMtno());
//                combobox.setValue(docMtHeader.getUserdefine1());
//                comboboxList.add(combobox);
//            }
//        }
//        return comboboxList;
//    }
//
//    //string转date
//    public Date StringtoDate(String time) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null;
//        try {
//            date = format.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return date;
//    }
//
//    /**
//     * 查询未完成的预入库通知单
//     *
//     * @param form 分页
//     * @return ~
//     */
//    public List<DocMtHeaderVO> queryUndoneList(PageForm form) {
//
//        List<DocMtHeader> couRequestList = couRequestHeaderMybatisDao.queryUndoneList(form.getStart(), form.getPageSize());
//        List<DocMtHeaderVO> docMtHeaderVOList = new ArrayList<>();
//        DocMtHeaderVO docMtHeaderVO;
//        for (DocMtHeader docMtHeader : couRequestList) {
//
//            docMtHeaderVO = new DocMtHeaderVO();
//            BeanUtils.copyProperties(docMtHeader, docMtHeaderVO);
//            docMtHeaderVOList.add(docMtHeaderVO);
//        }
//        return docMtHeaderVOList;
//    }
//
//    /**
//     * 通过mtno查询header
//     *
//     * @param mtno ~
//     * @return ~
//     */
//    public DocMtHeaderVO queryByMtno(String mtno) {
//
//        DocMtHeader docMtHeader = couRequestHeaderMybatisDao.queryById(mtno);
//        DocMtHeaderVO docMtHeaderVO = new DocMtHeaderVO();
//        if (docMtHeader != null) {
//
//            BeanUtils.copyProperties(docMtHeader, docMtHeaderVO);
//        }
//        return docMtHeaderVO;
//    }
//
//    /**
//     * 关单
//     * 目前行状态只有 00 && 40
//     *
//     * @param form ~
//     * @return ~
//     */
//    public PdaResult endDocMt(DocMtHeaderForm form) {
//
//        DocMtHeader mtHeader = new DocMtHeader();
//        mtHeader.setMtno(form.getMtno());
//        DocMtHeader docMtHeader = couRequestHeaderMybatisDao.queryById(mtHeader);
//        switch (docMtHeader.getMtstatus()) {
//            case "00":
//            case "30":
//
//                return new PdaResult(PdaResult.CODE_FAILURE, "养护单[" + form.getMtno() + "] 有未完成的养护任务，不可结束!");
//            case "40":
//
//                //storageFlag, flowFlag, signFlag, fenceFlag, sanitationFlag, remark
//                docMtHeader.setStorageFlag(form.getStorageFlag());
//                docMtHeader.setFlowFlag(form.getFlowFlag());
//                docMtHeader.setSignFlag(form.getSignFlag());
//                docMtHeader.setFenceFlag(form.getFenceFlag());
//                docMtHeader.setSanitationFlag(form.getSanitationFlag());
//                docMtHeader.setRemark(form.getRemark());
//                docMtHeader.setEditwho(form.getEditwho());
//                docMtHeader.setMtstatus("99");
//                couRequestHeaderMybatisDao.updateStatus(docMtHeader);
//                return new PdaResult(PdaResult.CODE_SUCCESS, "操作成功");
//            default:
//
//                return new PdaResult(PdaResult.CODE_FAILURE, "养护单[" + form.getMtno() + "] 当前状态不可操作!");
//        }
//    }
//
//    /**
//     * 关单
//     * 目前行状态只有 00 && 40
//     *
//     * @param form ~
//     * @return ~
//     */
//    public Json endDocMtJson(DocMtHeaderForm form) {
//        Json json=new Json();
//        form.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
//        PdaResult pdaResult=endDocMt(form);
//        if(pdaResult.getErrorCode()==200){
//            json.setSuccess(true);
//            json.setMsg(pdaResult.getMsg());
//        }else{
//            json.setSuccess(false);
//            json.setMsg(pdaResult.getMsg());
//        }
//        return json;
//    }
}