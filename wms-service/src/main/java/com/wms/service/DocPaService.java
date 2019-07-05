package com.wms.service;

import com.wms.constant.Constant;
import com.wms.dto.DocPaDTO;
import com.wms.entity.DocAsnDetail;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.Json;
import com.wms.vo.form.DocPaDetailsForm;
import com.wms.vo.form.DocPaHeaderForm;
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
                docPaHeaderForm.setPastatus("000");
                docPaHeaderForm.setAddtime(new Date());
                docPaHeaderForm.setAddwho(login.getId());
                docPaHeaderForm.setPaPrintFlag(Constant.CODE_YES_OR_NO);
                //docPaHeader.setWarehouseid();
                Json json = docPaHeaderService.addDocPaHeader(docPaHeaderForm);
                if (!json.isSuccess()) {
                    return Json.error("上架作业表头信息生成失败");
                }
                for (DocPaDTO docPaDTO : listDTO) {
                    DocPaDetailsForm detailsForm = new DocPaDetailsForm();
                    detailsForm.setPano(panno);
                    detailsForm.setLinestatus(docPaDTO.getLinestatus());
                    detailsForm.setAsnno(docPaDTO.getAsnno());
                    detailsForm.setAsnlineno(Double.parseDouble(docPaDTO.getAsnlineno()+""));
                    detailsForm.setCustomerid(docPaDTO.getCustomerid());
                    detailsForm.setSku(docPaDTO.getSku());
                    detailsForm.setUserdefine2(docPaDTO.getLotatt02());
                    detailsForm.setUserdefine3(docPaDTO.getLotatt04());
                    detailsForm.setUserdefine4(docPaDTO.getLotatt05());
                    detailsForm.setUserdefine5(docPaDTO.getLotatt10());
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
}