package com.wms.service;

import com.wms.constant.Constant;
import com.wms.dto.DocPaDTO;
import com.wms.entity.*;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.dao.InvLotAttMybatisDao;
import com.wms.mybatis.entity.IdSequence;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.result.PdaResult;
import com.wms.utils.BeanUtils;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.Json;
import com.wms.vo.form.DocPaDetailsForm;
import com.wms.vo.form.DocPaHeaderForm;
import com.wms.vo.form.DocQcDetailsForm;
import com.wms.vo.form.DocQcHeaderForm;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/4
 */
@Service("docPaService")
public class DocPaService {

    @Autowired
    private DocPaHeaderService docPaHeaderService;
    @Autowired
    private DocPaDetailsService docPaDetailService;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocAsnDetailService docAsnDetailService;
    @Autowired
    private DocQcHeaderService docQcHeaderService;
    @Autowired
    private DocQcDetailsService docQcDetailsService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BasSkuService basSkuService;

    @Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;

    @Autowired
    private InvLotAttService invLotAttService;

    /**
     * 编号列表
     */
    public Json mergeDocPa(String asnNos) {
        if (StringUtils.isEmpty(asnNos)) {
            return Json.error("请选择需要操作的单据");
        }
        try {
            String[] arr = asnNos.split(",");
            List<DocPaDTO> listDTO = docAsnDetailsMybatisDao.queryDocPaDTO(arr);
            if (listDTO != null && listDTO.size() > 0) {

                /* Begin ========================
                * add by Gizmo 2019-08-20
                * 定向订单/引用订单无需生成上架任务，否则打印出来的上架单是错误的。
                * 这两个类型的订单在确认收货后已经上架完成了
                * */
                String asnReference1 = "";
                String asnReference2 = "";
                for (DocPaDTO typeDto:
                     listDTO) {
                    if (typeDto.getAsntype().equals(DocAsnHeader.ASN_TYPE_DX) ||
                    typeDto.getAsntype().equals(DocAsnHeader.ASN_TYPE_YY)) {
                        return Json.error("定向订单/引用订单无需生成上架任务！");
                    }
                    if (!asnReference1.contains(StringUtil.fixNull(typeDto.getAsnreference1()))) {
                        asnReference1 += StringUtil.fixNull(typeDto.getAsnreference1());
                        asnReference1 += ",";
                    }
                    if (!asnReference2.contains(StringUtil.fixNull(typeDto.getAsnreference2()))) {
                        asnReference2 += StringUtil.fixNull(typeDto.getAsnreference2());
                        asnReference2 += ",";
                    }
                }
                if (asnReference1.length() > 0) asnReference1 = asnReference1.substring(0, asnReference1.length() - 1);
                if (asnReference2.length() > 0) asnReference2 = asnReference2.substring(0, asnReference2.length() - 1);
                /*
                * End    ======================== */

                SfcUserLogin login = SfcUserLoginUtil.getLoginUser();
                DocPaHeaderForm docPaHeaderForm = new DocPaHeaderForm();
                Map<String, Object> map = new HashMap<>();
                map.put("warehouseid", login.getWarehouse().getId());
                map.put("resultNo", "");
                map.put("resultCode", "");
                //int length = asnNos.length() >= 200 ? 200 : asnNos.length();
                docAsnDetailsMybatisDao.getIdSequence(map);
                String panno = map.get("resultNo").toString();
                docPaHeaderForm.setPano(panno);
                docPaHeaderForm.setAsnno(asnNos);
                docPaHeaderForm.setPastatus("00");
                docPaHeaderForm.setAddtime(new Date());
                docPaHeaderForm.setAddwho(login.getId());
                docPaHeaderForm.setPareference1(asnReference1);
                docPaHeaderForm.setPareference2(asnReference2);
                docPaHeaderForm.setPaPrintFlag(Constant.CODE_YES_OR_NO);
                docPaHeaderForm.setCustomerid(listDTO.get(0).getCustomerid());
                //docPaHeader.setWarehouseid();
                Json json = docPaHeaderService.addDocPaHeader(docPaHeaderForm);
                if (!json.isSuccess()) {
                    return Json.error("上架作业表头信息生成失败");
                }
                for (DocPaDTO docPaDTO : listDTO) {
                    DocPaDetailsForm detailsForm = new DocPaDetailsForm();
                    detailsForm.setPano(panno);
                    detailsForm.setLinestatus("00");
                    detailsForm.setLotnum(docPaDTO.getLotnum());
                    detailsForm.setAsnno(docPaDTO.getAsnno());
                    detailsForm.setAsnlineno(docPaDTO.getAsnlineno());
                    detailsForm.setAsnqtyExpected(docPaDTO.getReceivedqty().doubleValue());
                    detailsForm.setPutwayqtyExpected(docPaDTO.getReceivedqty().doubleValue());
                    detailsForm.setPutwayqtyCompleted(0d);
                    detailsForm.setCustomerid(docPaDTO.getCustomerid());
                    detailsForm.setSku(docPaDTO.getSku());
                    detailsForm.setUserdefine1("STAGE01");
                    detailsForm.setUserdefine2(docPaDTO.getLotatt02());
                    detailsForm.setUserdefine3(docPaDTO.getLotatt04());
                    detailsForm.setUserdefine4(docPaDTO.getLotatt05());
                    detailsForm.setUserdefine5(docPaDTO.getLotatt10());
                    detailsForm.setPalineno((docPaDetailService.queryMaxLineNo(panno)+1) + "");
                    detailsForm.setPackid(docPaDTO.getPackid());
                    detailsForm.setAddwho(login.getId());
                    detailsForm.setAddtime(new Date());
                    Json rest = docPaDetailService.addDocPaDetails(detailsForm);
                    /*if(rest.isSuccess()){
                        DocAsnDetail detail = new DocAsnDetail();
                        detail.setAsnlineno(Long.parseLong(docPaDTO.getAsnno()));
                        detail.setAsnno(docPaDTO.getAsnno());
                        detail.set
                        docAsnDetailsMybatisDao.updateBySelective();
                    }else {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Json.error("合并上架清单系统异常");
                    }*/
                }
                for(String asnNo : arr){
                    DocAsnHeader header = new DocAsnHeader();
                    header.setAsnno(asnNo);
                    header.setAsnPrintFlag("Y");
                    header.setUserdefine2(panno);//预期到货通知头档记录上架任务单号
                    docAsnHeaderMybatisDao.updateBySelective(header);
                }
                return Json.success("合并成功");
            }else{
                return Json.error("单据已经合并过");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("合并上架清单系统异常");
        }
    }

    public Json confirmReceiving(String asnNos){
        if (StringUtils.isEmpty(asnNos)) {
            return Json.error("请选择需要操作的单据");
        }
        try {
            String[] arr = asnNos.split(",");
            SfcUserLogin login = SfcUserLoginUtil.getLoginUser();
            List<DocPaDTO> listDTO = docAsnDetailsMybatisDao.queryDocPaDTO(arr);
            if (listDTO != null && listDTO.size() > 0) {

                List<PdaDocQcDetailForm> pdaDocQcDetailFormArrayList = new ArrayList<>();

                for(DocPaDTO docPaDTO : listDTO){
                    if(!docPaDTO.getAsnstatus().equals("00")){
                        return Json.error("只有创建状态的通知单才能确认收货");
                    }
                    Json json = docAsnDetailService.receiveDocAsnDetail(docPaDTO.getAsnno(),docPaDTO.getAsnlineno());
                    if(!json.isSuccess()){
                        throw new Exception(json.getMsg());
                    }
                    //定向订单预期到货通知单（一键收货）时，往DOCQCHEAD 质检表插入一个质检任务 + 上架任务插入
                    //引用入库和定向订单基本上相同，多出来的就是需要自动生成验收结果
                    //预期到货设置了上架库位可直接完成上架操作
                    if(docPaDTO.getAsntype().equals(DocAsnHeader.ASN_TYPE_DX) ||
                            docPaDTO.getAsntype().equals(DocAsnHeader.ASN_TYPE_YY) ||
                            StringUtil.isNotEmpty(docPaDTO.getReceivinglocation())){

                        String paNo;
                        String qcNo;
                        BasSku basSku = basSkuService.getSkuInfo(docPaDTO.getCustomerid(),docPaDTO.getSku());

                        DocAsnDetail docAsnDetail = new DocAsnDetail();
                        BeanUtils.copyProperties(docPaDTO, docAsnDetail);
                        docAsnDetail = docAsnDetailService.configDxLocation(docAsnDetail);

                        //上架
                        DocPaHeader docPaHeader = docPaHeaderService.queryByAsnno(docPaDTO.getAsnno());
                        if (docPaHeader == null || docPaHeader.getPano() == null) {

                            paNo = commonService.generateSeq(IdSequence.SEQUENCE_TYPE_PA, login.getWarehouse().getId());

                            DocPaHeaderForm docPaHeaderForm = new DocPaHeaderForm();
                            docPaHeaderForm.setPano(paNo);
                            docPaHeaderForm.setAsnno(docPaDTO.getAsnno());
                            docPaHeaderForm.setCustomerid(docPaDTO.getCustomerid());
                            docPaHeaderForm.setPatype("*");//随便插的
                            docPaHeaderForm.setPastatus("40");
                            docPaHeaderForm.setAddtime(new Date());
                            docPaHeaderForm.setAddwho(login.getId());
                            docPaHeaderForm.setPaPrintFlag("0");
                            docPaHeaderForm.setPareference1(docPaDTO.getAsnreference1());
                            docPaHeaderForm.setPareference2(docPaDTO.getAsnreference2());
                            docPaHeaderForm.setWarehouseid(login.getWarehouse().getId());
                            docPaHeaderService.addDocPaHeader(docPaHeaderForm);
                        }else {

                            paNo = docPaHeader.getPano();
                        }

                        //预期到货通知头档记录上架任务单号
                        DocAsnHeader header = new DocAsnHeader();
                        header.setAsnno(docPaDTO.getAsnno());
                        header.setAsnPrintFlag("Y");
                        header.setUserdefine1(paNo);
                        docAsnHeaderMybatisDao.updateBySelective(header);

                        //处理无批次属性的情况
                        InvLotAtt invLotAttLeak = invLotAttService.queryInsertLotatts(docAsnDetail);
                        docPaDTO.setLotnum(invLotAttLeak.getLotnum());

                        DocPaDetailsForm docPaDetailsForm = new DocPaDetailsForm();
                        docPaDetailsForm.setPano(paNo);
                        docPaDetailsForm.setPalineno((docPaDetailService.queryMaxLineNo(paNo)+1) + "");
                        docPaDetailsForm.setLinestatus("40");
                        docPaDetailsForm.setAsnno(docPaDTO.getAsnno());
                        docPaDetailsForm.setAsnlineno(docPaDTO.getAsnlineno());
                        docPaDetailsForm.setCustomerid(docPaDTO.getCustomerid());
                        docPaDetailsForm.setSku(docPaDTO.getSku());
                        docPaDetailsForm.setLotnum(docPaDTO.getLotnum());
                        docPaDetailsForm.setAsnqtyExpected(docPaDTO.getExpectedqty().doubleValue());
                        docPaDetailsForm.setPutwayqtyExpected(docPaDTO.getExpectedqty().doubleValue());
                        docPaDetailsForm.setPutwayqtyCompleted(docPaDTO.getExpectedqty().doubleValue());
                        docPaDetailsForm.setUserdefine1(docAsnDetail.getReceivinglocation());
                        docPaDetailsForm.setUserdefine2(docPaDTO.getLotatt02());
                        docPaDetailsForm.setUserdefine3(docPaDTO.getLotatt04());
                        docPaDetailsForm.setUserdefine4(docPaDTO.getLotatt05());
                        docPaDetailsForm.setUserdefine5(docPaDTO.getAsntype().equals(DocAsnHeader.ASN_TYPE_YY) ? "HG" : "DJ");
                        docPaDetailsForm.setAddtime(new Date());
                        docPaDetailsForm.setAddwho(login.getId());
                        docPaDetailsForm.setPackid(basSku.getPackid());
                        docPaDetailsForm.setTransactionid("");
                        docPaDetailService.addDocPaDetails(docPaDetailsForm);

                        //验收
                        DocQcHeader docQcHeader = docQcHeaderService.queryByPano(paNo);
                        if (docQcHeader == null || docQcHeader.getQcno() == null) {

                            qcNo = commonService.generateSeq(IdSequence.SEQUENCE_TYPE_QC,login.getWarehouse().getId());

                            DocQcHeaderForm docQcHeaderForm = new DocQcHeaderForm();
                            docQcHeaderForm.setQcno(qcNo);
                            docQcHeaderForm.setPano(paNo);
                            docQcHeaderForm.setCustomerid(docPaDTO.getCustomerid());
                            docQcHeaderForm.setQcstatus("00");
                            docQcHeaderForm.setQccreationtime(new Date());
                            docQcHeaderForm.setAddwho(login.getId());
                            docQcHeaderForm.setAddtime(new Date());
                            docQcHeaderForm.setWarehouseid(login.getWarehouse().getId());
                            docQcHeaderForm.setQcPrintFlag("0");
                            docQcHeaderService.addDocQcHeader(docQcHeaderForm);
                        }else {

                            qcNo = docQcHeader.getQcno();
                        }


                        DocQcDetailsForm docQcDetailsForm = new DocQcDetailsForm();
                        docQcDetailsForm.setQcno(qcNo);
                        docQcDetailsForm.setQclineno((docQcDetailsService.queryMaxLineNo(qcNo)+1)+"");
                        docQcDetailsForm.setCustomerid(docPaDTO.getCustomerid());
                        docQcDetailsForm.setSku(docPaDTO.getSku());
                        docQcDetailsForm.setLinestatus("00");
                        docQcDetailsForm.setPalineno(Double.valueOf(docPaDetailsForm.getPalineno()));
                        docQcDetailsForm.setLotnum(docPaDTO.getLotnum());
                        docQcDetailsForm.setPaqtyExpected(docPaDTO.getExpectedqty().doubleValue());
                        docQcDetailsForm.setQcqtyExpected(docPaDTO.getExpectedqty().doubleValue());
                        docQcDetailsForm.setQcqtyCompleted(0d);
                        docQcDetailsForm.setUserdefine1(docAsnDetail.getReceivinglocation());
                        docQcDetailsForm.setUserdefine2(docPaDTO.getLotatt02());
                        docQcDetailsForm.setUserdefine3(docPaDTO.getLotatt04());
                        docQcDetailsForm.setUserdefine4(docPaDTO.getLotatt05());
                        docQcDetailsForm.setUserdefine5("DJ");
                        docQcDetailsForm.setQcresult("*");
                        docQcDetailsForm.setFilecontent("*");
                        docQcDetailsForm.setNotes("*");
                        docQcDetailsForm.setAddtime(new Date());
                        docQcDetailsForm.setAddwho(login.getId());
                        docQcDetailsForm.setPackid(basSku.getPackid());
                        docQcDetailsForm.setTransactionid("");
                        docQcDetailsService.addDocQcDetails(docQcDetailsForm);

                        if (docPaDTO.getAsntype().equals(DocAsnHeader.ASN_TYPE_YY)) {

                            PdaDocQcDetailForm pdaDocQcDetailForm = new PdaDocQcDetailForm();
                            BeanUtils.copyProperties(docQcDetailsForm, pdaDocQcDetailForm);
                            pdaDocQcDetailForm.setAllqcflag(0);
                            pdaDocQcDetailForm.setQcno(docQcDetailsForm.getQcno());
                            pdaDocQcDetailForm.setQclineno(docQcDetailsForm.getQclineno());
                            pdaDocQcDetailForm.setQcqty(docQcDetailsForm.getQcqtyExpected().toString());
                            pdaDocQcDetailForm.setQcdescr("未见异常，检查验收合格");
                            pdaDocQcDetailForm.setWarehouseid(login.getWarehouse().getId());
                            pdaDocQcDetailForm.setUserid(login.getId());
                            pdaDocQcDetailForm.setLanguage("CN");

                            pdaDocQcDetailForm.setLotatt10("HG");

                            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docPaDTO.getLotnum());
                            if (invLotAtt != null) {
                                pdaDocQcDetailForm.setLotatt01(invLotAtt.getLotatt01());
                                pdaDocQcDetailForm.setLotatt02(invLotAtt.getLotatt02());
                                pdaDocQcDetailForm.setLotatt04(invLotAtt.getLotatt04());
                                pdaDocQcDetailForm.setLotatt06(invLotAtt.getLotatt06());
                                pdaDocQcDetailForm.setLotatt11(invLotAtt.getLotatt11());
                                pdaDocQcDetailForm.setLotatt15(invLotAtt.getLotatt15());
                            }

                            pdaDocQcDetailFormArrayList.add(pdaDocQcDetailForm);
                        }
                    }
                }

                if (pdaDocQcDetailFormArrayList.size() > 0) {

                    //------------------------  验收执行(引用入库)
                    for (PdaDocQcDetailForm form : pdaDocQcDetailFormArrayList) {

                        PdaResult result = docQcDetailsService.submitDocQc(form);
                        if (result.getErrorCode() != PdaResult.CODE_SUCCESS) {

                            throw new Exception();
                        }
                    }
                }
                return Json.success("收货成功");
            }
            return Json.error("操作失败");
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("确认收货系统异常"+e.getMessage());
        }
    }
}