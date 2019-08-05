package com.wms.service;

import com.wms.constant.Constant;
import com.wms.dto.DocPaDTO;
import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocPaHeader;
import com.wms.entity.DocQcHeader;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.entity.IdSequence;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.utils.SfcUserLoginUtil;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 编号列表
     *
     * @param asnNos
     * @return
     */
    public Json mergeDocPa(String asnNos) {
        if (StringUtils.isEmpty(asnNos)) {
            return Json.error("请选择需要操作的单据");
        }
        try {
            String[] arr = asnNos.split(",");
            List<DocPaDTO> listDTO = docAsnDetailsMybatisDao.queryDocPaDTO(arr);
            if (listDTO != null && listDTO.size() > 0) {
                SfcUserLogin login = SfcUserLoginUtil.getLoginUser();
                DocPaHeaderForm docPaHeaderForm = new DocPaHeaderForm();
                Map<String, Object> map = new HashMap<>();
                map.put("warehouseid", login.getWarehouse().getId());
                map.put("resultNo", "");
                map.put("resultCode", "");
                docAsnDetailsMybatisDao.getIdSequence(map);
                String panno = map.get("resultNo").toString();
                docPaHeaderForm.setPano(panno);
                docPaHeaderForm.setPastatus("00");
                docPaHeaderForm.setAddtime(new Date());
                docPaHeaderForm.setAddwho(login.getId());
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
            }
            return Json.success("合并成功");
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
                for(DocPaDTO docPaDTO : listDTO){
                    if(!docPaDTO.getAsnstatus().equals("00")){
                        return Json.error("只有创建状态的通知单才能确认收货");
                    }
                    Json json = docAsnDetailService.receiveDocAsnDetail(docPaDTO.getAsnno(),docPaDTO.getAsnlineno());
                    if(!json.isSuccess()){
                        throw new Exception("收货失败");
                    }
                    //定向订单预期到货通知单（一键收货）时，往DOCQCHEAD 质检表插入一个质检任务 + 上架任务插入
                    if(docPaDTO.getAsntype().equals("DX")){

                        String paNo;
                        String qcNo;
                        BasSku basSku = basSkuService.getSkuInfo(docPaDTO.getCustomerid(),docPaDTO.getSku());

                        //上架
                        DocPaHeader docPaHeader = docPaHeaderService.queryByAsnno(docPaDTO.getAsnno());
                        if (docPaHeader == null || docPaHeader.getPano() == null) {

                            paNo = commonService.generateSeq(IdSequence.SEQUENCE_TYPE_PA, login.getWarehouse().getId());

                            DocPaHeaderForm docPaHeaderForm = new DocPaHeaderForm();
                            docPaHeaderForm.setPano(paNo);
                            docPaHeaderForm.setAsnno(docPaDTO.getAsnno());
                            docPaHeaderForm.setCustomerid(docPaDTO.getCustomerid());
                            docPaHeaderForm.setPatype("EA");//随便插的
                            docPaHeaderForm.setPastatus("40");
                            docPaHeaderForm.setAddtime(new Date());
                            docPaHeaderForm.setAddwho(login.getId());
                            docPaHeaderForm.setPaPrintFlag("0");
                            docPaHeaderForm.setWarehouseid(login.getWarehouse().getId());
                            docPaHeaderService.addDocPaHeader(docPaHeaderForm);
                        }else {

                            paNo = docPaHeader.getPano();
                        }

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
                        docPaDetailsForm.setUserdefine1(docPaDTO.getReceivinglocation());
                        docPaDetailsForm.setUserdefine2(docPaDTO.getLotatt02());
                        docPaDetailsForm.setUserdefine3(docPaDTO.getLotatt04());
                        docPaDetailsForm.setUserdefine4(docPaDTO.getLotatt05());
                        docPaDetailsForm.setUserdefine5("DJ");
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
                        docQcDetailsForm.setUserdefine1(docPaDTO.getReceivinglocation());
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
                    }
                }
                return Json.success("收货成功");
            }
            return Json.error("操作失败");
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("确认收货系统异常");
        }
    }
}