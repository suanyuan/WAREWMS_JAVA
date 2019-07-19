package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.DocQcDetails;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.DocQcDetailsQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocQcDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocQcDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQcDetailsForm;
import com.wms.vo.pda.PdaDocQcDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("docQcDetailsService")
public class DocQcDetailsService extends BaseService {

	@Autowired
	private DocQcDetailsMybatisDao docQcDetailsDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private GspEnterpriseInfoMybatisDao enterpriseInfoMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private GspProductRegisterMybatisDao productRegisterMybatisDao;



	public EasyuiDatagrid<DocQcDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
        EasyuiDatagrid<DocQcDetailsVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocQcDetails> docQcHeaderList = docQcDetailsDao.queryByPageList(mybatisCriteria);
        DocQcDetailsVO docQcHeaderVO = null;
        List<DocQcDetailsVO> docQcHeaderVOList = new ArrayList<>();
        for (DocQcDetails docPaDetails : docQcHeaderList) {
            docQcHeaderVO = new DocQcDetailsVO();
            BeanUtils.copyProperties(docPaDetails, docQcHeaderVO);
            docQcHeaderVOList.add(docQcHeaderVO);
        }
        datagrid.setTotal((long) docQcDetailsDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docQcHeaderVOList);
        return datagrid;
	}

	public Json addDocQcDetails(DocQcDetailsForm docQcDetailsForm) throws Exception {
		Json json = new Json();
		DocQcDetails docQcDetails = new DocQcDetails();
		BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
		docQcDetailsDao.add(docQcDetails);
		json.setSuccess(true);
		return json;
	}

	//TODO WARNING!! 此处不可用个，查询条件欠缺 no + lineno
	public Json editDocQcDetails(DocQcDetailsForm docQcDetailsForm) {
		Json json = new Json();
		DocQcDetails docQcDetails = docQcDetailsDao.queryById(docQcDetailsForm.getQcno());
		BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
		docQcDetailsDao.update(docQcDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocQcDetails(String id) {
		Json json = new Json();
		DocQcDetails docQcDetails = docQcDetailsDao.queryById(id);
		if(docQcDetails != null){
			docQcDetailsDao.delete(docQcDetails);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocQcDetailsCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocQcDetails> docQcDetailsList = docQcDetailsDao.findAll();
//		if(docQcDetailsList != null && docQcDetailsList.size() > 0){
//			for(DocQcDetails docQcDetails : docQcDetailsList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docQcDetails.getId()));
//				combobox.setValue(docQcDetails.getDocQcDetailsName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

    /**
     * 获取验收详情
     * @param query ~
     * @return ~
     */
    public Map<String, Object> queryDocQcDetail(PdaDocQcDetailQuery query) {

        Map<String, Object> map = new HashMap<>();
        PdaDocQcDetailVO pdaDocQcDetailVO = new PdaDocQcDetailVO();

        //获取BasSku
        PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
        BeanUtils.copyProperties(query, basSkuQuery);
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

        if (basSku == null || basSku.getSku() == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "无产品信息"));
            return map;
        }
        pdaDocQcDetailVO.setBasSku(basSku);

        //DocQcDetails
        query.setSku(basSku.getSku());
        DocQcDetails docQcDetails = docQcDetailsDao.queryDocQcDetail(query);

        if (docQcDetails == null || docQcDetails.getQcno() == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "无验收明细"));
            return map;
        }
        BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);


        //批次
        InvLotAtt lotAtt = invLotAttMybatisDao.queryById(docQcDetails.getLotnum());
        if (lotAtt == null || lotAtt.getLotnum() == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "无批次信息"));
            return map;
        }
        pdaDocQcDetailVO.setInvLotAtt(lotAtt);

        //历史注册证(+生产企业详情)
        List<PdaGspProductRegister> registerList = productRegisterMybatisDao.queryHistoryRegister(basSku.getSku(), basSku.getCustomerid());
        pdaDocQcDetailVO.setProductRegisterList(registerList == null ? new ArrayList<PdaGspProductRegister>() : registerList);

        //当前批次-产品注册证对应的 生产厂家
        PdaGspProductRegister productRegister = productRegisterMybatisDao.queryByNo(lotAtt.getLotatt06());
        if (productRegister == null || productRegister.getEnterpriseId() == null) {
            map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "无生产厂家信息"));
            return map;
        }
        pdaDocQcDetailVO.setEnterpriseInfo(productRegister.getEnterpriseInfo());

        map.put(Constant.DATA, pdaDocQcDetailVO);
        map.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));

        return map;
    }

    /**
     * 查询任务明细列表
     * @param qcno ~
     * @return ~
     */
    public List<PdaDocQcDetailVO> queryDocQcList(String qcno, int pageNum) {

        List<PdaDocQcDetailVO> docQcDetailVOList = new ArrayList<>();
        PdaDocQcDetailVO pdaDocQcDetailVO;

        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryDocQcList(qcno, (pageNum - 1) * Constant.pageSize, Constant.pageSize);
        for (DocQcDetails docQcDetails : docQcDetailsList) {

            pdaDocQcDetailVO = new PdaDocQcDetailVO();
            BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);
            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docQcDetails.getLotnum());
            String jsonStr1 = JSON.toJSONString(invLotAtt, SerializerFeature.DisableCircularReferenceDetect);
            pdaDocQcDetailVO.setInvLotAtt(JSONObject.parseObject(jsonStr1, InvLotAtt.class));
            docQcDetailVOList.add(pdaDocQcDetailVO);
        }

        return docQcDetailVOList;
    }

    /**
     * 更新已验收的验收说明
     * @param query  ~
     * @return ~
     */
    public PdaResult editQcDesc(DocQcDetailsQuery query) {

        query.setEditwho("Gizmo");
        int result = docQcDetailsDao.updateQcDesc(query);
        if (result == 0) {
            return new PdaResult(PdaResult.CODE_FAILURE, "操作失败, 任务单不存在");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "操作成功");
    }

    /**
     * 验收提交
     * @param form ~
     * @return ~
     */
    public PdaResult submitDocQc(PdaDocQcDetailForm form) {

        form.setUserid("Gizmo");
        form.setLanguage("CN");
        form.setReturncode("");
        try {

            docQcDetailsDao.submitDocQc(form);
        }catch (Exception e) {
            e.printStackTrace();

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        if (form.getReturncode().equals(Constant.PROCEDURE_OK)) {

            return new PdaResult(PdaResult.CODE_SUCCESS, "验收成功");
        } else {

            return new PdaResult(PdaResult.CODE_FAILURE, form.getReturncode());
        }
    }
}