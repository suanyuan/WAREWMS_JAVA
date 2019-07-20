package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.IdSequence;
import com.wms.mybatis.entity.pda.PdaDocQcDetailForm;
import com.wms.mybatis.entity.pda.PdaDocQcStatusForm;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.DocQcDetailsQuery;
import com.wms.query.InvLotAttQuery;
import com.wms.query.InvLotLocIdQuery;
import com.wms.query.InvLotQuery;
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

import java.util.*;

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

	@Autowired
	private InvLotLocIdMybatisDao invLotLocIdMybatisDao;

	@Autowired
	private InvLotMybatisDao invLotMybatisDao;

	@Autowired
	private DocPaHeaderMybatisDao docPaHeaderMybatisDao;

	@Autowired
	private DocPaDetailsMybatisDao docPaDetailsMybatisDao;

	@Autowired
	private DocQcHeaderMybatisDao docQcHeaderMybatisDao;


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

	public Json editDocQcDetails(DocQcDetailsForm docQcDetailsForm) {
		Json json = new Json();
		DocQcDetails docQcDetails = docQcDetailsDao.queryById(docQcDetailsForm);
		BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
		docQcDetailsDao.update(docQcDetails);
		json.setSuccess(true);
		return json;
	}

    //TODO WARNING!! 此处不可用个，删除条件欠缺 应该qcno + qclineno
//	public Json deleteDocQcDetails(String id) {
//		Json json = new Json();
//		DocQcDetails docQcDetails = docQcDetailsDao.queryById(id);
//		if(docQcDetails != null){
//			docQcDetailsDao.delete(docQcDetails);
//		}
//		json.setSuccess(true);
//		return json;
//	}

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

            if (form.getAllqcflag() == 0) {

                return new PdaResult(PdaResult.CODE_SUCCESS, "验收成功");
            }else {

                //处理批量验收合格操作
                return configAllQc(form);
            }
        } else {

            return new PdaResult(PdaResult.CODE_FAILURE, form.getReturncode());
        }
    }

    /**
     * 处理批量验收操作 procedure 拆出来的
     * @param form ~
     * @return ~
     */
    private PdaResult configAllQc(PdaDocQcDetailForm form) {

        /*
        获取本次操作的验收明细
         */
        DocQcDetails currentQcDetail = docQcDetailsDao.queryById(form);

        /*
        获取同生产批号的验收任务明细记录，userdefine5=DJ,相同的QCNO、sku、userdefine3、userdefine5=DJ
         */
        DocQcDetailsQuery qcDetailsQuery = new DocQcDetailsQuery();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        qcDetailsQuery.setQcno(currentQcDetail.getQcno());
        qcDetailsQuery.setSku(currentQcDetail.getSku());
        qcDetailsQuery.setUserdefine3(currentQcDetail.getUserdefine3());
        qcDetailsQuery.setUserdefine5("DJ");

        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(qcDetailsQuery));
        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryByPageList(mybatisCriteria);

        try {
            /*
            对应每条验收明细的库存记录、上架明细、验收明细要做对应的处理
            */
            for (DocQcDetails qcDetails : docQcDetailsList) {

                /* ********************************inv_lot_att******************************** */

                /*
                获取待检的验收明细中的批次属性
                */
                InvLotAtt lotatt_history = invLotAttMybatisDao.queryById(qcDetails.getLotnum());
                InvLotAttQuery lotAttQuery = new InvLotAttQuery(lotatt_history);
                lotAttQuery.setLotatt10("HG");
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(lotAttQuery));
                List<InvLotAtt> invLotAttList = invLotAttMybatisDao.queryByList(mybatisCriteria);
                /*
                判断是否存在此批次
                不存在 ->  插入新批次
                存在 -> 查出来做流转
                */
                InvLotAtt lotatt_hg = new InvLotAtt();
                if (invLotAttList == null || invLotAttList.size() < 1) {

                    IdSequence idSequence = new IdSequence(form.getWarehouseid(), "CN", IdSequence.SEQUENCE_TYPE_LOT_NUM);
                    invLotAttMybatisDao.getIdSequence(idSequence);

                    BeanUtils.copyProperties(lotatt_history, lotatt_hg);
                    lotatt_hg.setLotatt10("HG");
                    lotatt_hg.setAddwho("Gizmo");
                    lotatt_hg.setAddtime(new java.sql.Date((new Date()).getTime()));
                    invLotAttMybatisDao.add(lotatt_hg);
                }else {

                    lotatt_hg = invLotAttList.get(0);//18个批次属性匹配完查询只有一个
                }


                /* ********************************inv_lot_loc_id******************************** */

                /*
                根据qcdetails+历史批次属性，获取库位批次库存详情
                * */
                InvLotLocIdQuery invLotLocIdQuery = new InvLotLocIdQuery();
                invLotLocIdQuery.setLocationid(qcDetails.getUserdefine1());
                invLotLocIdQuery.setCustomerid(qcDetails.getCustomerid());
                invLotLocIdQuery.setSku(qcDetails.getSku());
                invLotLocIdQuery.setLotnum(lotatt_history.getLotnum());
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotLocIdQuery));
                List<InvLotLocId> invLotLocIdList = invLotLocIdMybatisDao.queryByList(mybatisCriteria);
                if (invLotLocIdList == null || invLotLocIdList.size() == 0) {

                    throw new Exception("111");
                }
                InvLotLocId invLotLocId_history = invLotLocIdList.get(0);//历史存在数据
                InvLotLocId invLotLocId_hg = new InvLotLocId();
                BeanUtils.copyProperties(invLotLocId_history, invLotLocId_hg);
                invLotLocId_hg.setLotnum(lotatt_hg.getLotnum());
                invLotLocIdMybatisDao.add(invLotLocId_hg);//插入合格批次的库位库存
                invLotLocIdMybatisDao.delete(invLotLocId_history);//删除待检批次的库位库存


                /* ********************************inv_lot******************************** */

                /*
                根据qcdetails+历史批次属性，获取批次库存详情
                * */
                InvLotQuery invLotQuery = new InvLotQuery();
                invLotQuery.setLotnum(lotatt_history.getLotnum());
                invLotQuery.setCustomerid(qcDetails.getCustomerid());
                invLotQuery.setSku(qcDetails.getSku());
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotQuery));
                List<InvLot> invLotList_history = invLotMybatisDao.queryByList(mybatisCriteria);
                if (invLotList_history == null || invLotList_history.size() == 0) {

                    throw new Exception("222");
                }
                InvLot invLot_history = invLotList_history.get(0);
                InvLot invLot_hg = new InvLot();
                BeanUtils.copyProperties(invLot_history, invLot_hg);
                invLot_history.setQty(invLot_history.getQty() - qcDetails.getQcqtyExpected());
                invLot_history.setEditwho("Gizmo");
                invLotMybatisDao.update(invLot_history);//库存里面减去此明细中的预期验收数
                invLotMybatisDao.deleteEmptyInv();//清除0库存记录

                //库存里面插入或更新加上预期验收数
                invLotQuery.setLotnum(lotatt_hg.getLotnum());
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotQuery));
                List<InvLot> invLotList_hg = invLotMybatisDao.queryByList(mybatisCriteria);
                invLot_hg.setLotnum(lotatt_hg.getLotnum());
                if (invLotList_hg == null || invLotList_hg.size() == 0) {

                    //插入
                    invLot_hg.setQty(qcDetails.getQcqtyExpected());
                    invLot_hg.setAddwho("Gizmo");
                    invLot_hg.setAddtime(new java.sql.Date((new Date()).getTime()));
                    invLotMybatisDao.add(invLot_hg);
                }else {

                    InvLot invLot_exist = invLotList_hg.get(0);

                    //加上件数
                    invLot_hg.setQty(invLot_exist.getQty() + qcDetails.getQcqtyExpected());
                    invLot_hg.setEditwho("Gizmo");
                    invLot_hg.setEdittime(new java.sql.Date((new Date()).getTime()));
                    invLotMybatisDao.updateQty(invLot_hg);
                }


                /* ********************************doc_qc_details******************************** */

                /*
                根据qcdetails,更新批次号为合格的批次号，验收完成数为预期验收数，验收状态为合格
                * */
                qcDetails.setLotnum(lotatt_hg.getLotnum());
                qcDetails.setQcqtyCompleted(qcDetails.getQcqtyExpected());
                qcDetails.setUserdefine5("HG");
                qcDetails.setEditwho("Gizmo");
                qcDetails.setEdittime(new java.sql.Date((new Date()).getTime()));
                docQcDetailsDao.updateQcDetail(qcDetails);


                /* ********************************doc_qc_header******************************** */

                /*
                通过qcdetails.qcno-header-pano，再取得此上架任务单的总件数，all_pa_qty(前提上架任务单 是40)
                获取 doc_qc_details 此验收任务单里总的已验收完成件数，all_qc_qty
                做件数判断
                * */
                //获取上架任务单的状态
                DocPaHeader docPaHeader = docPaHeaderMybatisDao.queryByQcno(qcDetails.getQcno());
                PdaDocQcStatusForm statusForm = new PdaDocQcStatusForm();
                statusForm.setQcno(qcDetails.getQcno());
                statusForm.setEditwho("Gizmo");
                if (docPaHeader.getPastatus().equals("40")) {

                    int all_pa_qty = docQcDetailsDao.queryCompletedPaQty(qcDetails.getQcno());
                    int all_qc_qty = docQcDetailsDao.queryCompletedQcQty(qcDetails.getQcno());
                    if (all_qc_qty < all_pa_qty) {
                        //30
                        statusForm.setQcstatus("30");
                    }else {
                        //40
                        statusForm.setQcstatus("40");
                    }
                }else {
                    //30
                    statusForm.setQcstatus("30");
                }
                docQcHeaderMybatisDao.updateTaskStatus(statusForm);

                /* ********************************doc_pa_details******************************** */
                /*
                因为上架明细中的质量只有DJ 和 HG，所以需要匹配pano、sku、userdefine3、userdefine5（DJ）
                */
                DocPaDetails docPaDetails = new DocPaDetails();
                docPaDetails.setPano(qcDetails.getQcno());
                docPaDetails.setSku(qcDetails.getSku());
                docPaDetails.setUserdefine3(qcDetails.getUserdefine3());
                docPaDetails.setUserdefine5("DJ");
                docPaDetailsMybatisDao.updateBatchQc(docPaDetails);
            }
        } catch (Exception e) {

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            if (e.getMessage().equals("111")) {

                return new PdaResult(PdaResult.CODE_FAILURE, "当前数量验收成功，批量合格失败(库位批次库存错误)");
            }else if (e.getMessage().equals("222")) {

                return new PdaResult(PdaResult.CODE_FAILURE, "当前数量验收成功，批量合格失败(批次库存错误)");
            }
            return new PdaResult(PdaResult.CODE_FAILURE, "当前数量验收成功，批量合格失败(系统错误)");
        }
        return new PdaResult(PdaResult.CODE_SUCCESS, "批量验收成功");
    }
}