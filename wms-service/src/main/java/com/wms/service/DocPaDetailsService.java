package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocPaDetailForm;
import com.wms.query.BasSerialNumQuery;
import com.wms.query.BasSkuQuery;
import com.wms.query.DocPaDetailsQuery;
import com.wms.query.ProductLineQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocPaDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.DocPaDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocPaDetailsForm;
import com.wms.vo.form.pda.ScanResultForm;
import com.wms.vo.pda.CommonVO;
import com.wms.vo.pda.PdaDocPaDetailVO;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.apache.camel.language.Bean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("docPaDetailsService")
@SuppressWarnings("unchecked")
public class DocPaDetailsService extends BaseService {

	@Autowired
	private DocPaDetailsMybatisDao docPaDetailsMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private BasSerialNumMybatisDao basSerialNumMybatisDao;

	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;

	@Autowired
	private CommonService commonService;

	public EasyuiDatagrid<DocPaDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocPaDetailsQuery query) {
        EasyuiDatagrid<DocPaDetailsVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocPaDetails> docOrderHeaderList = docPaDetailsMybatisDao.queryByPageList(mybatisCriteria);
        DocPaDetailsVO docPaDetailsVO = null;
        List<DocPaDetailsVO> docOrderHeaderVOList = new ArrayList<>();
        for (DocPaDetails docPaDetails : docOrderHeaderList) {
            docPaDetailsVO = new DocPaDetailsVO();
            BeanUtils.copyProperties(docPaDetails, docPaDetailsVO);
            docOrderHeaderVOList.add(docPaDetailsVO);
        }
        datagrid.setTotal((long) docPaDetailsMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docOrderHeaderVOList);
        return datagrid;
	}

	public Json addDocPaDetails(DocPaDetailsForm docPaDetailsForm) throws Exception {
		Json json = new Json();
		DocPaDetails docPaDetails = new DocPaDetails();
		BeanUtils.copyProperties(docPaDetailsForm, docPaDetails);
		docPaDetailsMybatisDao.add(docPaDetails);
		json.setSuccess(true);
		return json;
	}

	public Json editDocPaDetails(DocPaDetailsForm docPaDetailsForm) {
		Json json = new Json();
		DocPaDetails docPaDetails = docPaDetailsMybatisDao.queryById(docPaDetailsForm.getPano());
		BeanUtils.copyProperties(docPaDetailsForm, docPaDetails);
		docPaDetailsMybatisDao.update(docPaDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocPaDetails(String id) {
		Json json = new Json();
		DocPaDetails docPaDetails = docPaDetailsMybatisDao.queryById(id);
		if(docPaDetails != null){
			docPaDetailsMybatisDao.delete(docPaDetails);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocPaDetailsCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocPaDetails> docPaDetailsList = docPaDetailsMybatisDao.findAll();
//		if(docPaDetailsList != null && docPaDetailsList.size() > 0){
//			for(DocPaDetails docPaDetails : docPaDetailsList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docPaDetails.getId()));
//				combobox.setValue(docPaDetails.getDocPaDetailsName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

    //获取上架任务单里的最大行号
    public int queryMaxLineNo(String pano){
        return docPaDetailsMybatisDao.queryMaxLineNo(pano);
    }


    /**
     * TODO 获取上架详情
     * @param query ~
     * @return 明细
     */
    public Json queryDocPaDetail(PdaDocPaDetailQuery query) {

        Json resultJson = new Json();
        PdaDocPaDetailVO docPaDetailVO = new PdaDocPaDetailVO();

        /*
        11111111，处理BasSku获取问题
        并且返回准确的批号、序列号匹配条件
         */
        ScanResultForm scanResultForm = new ScanResultForm();
        //customerid, GTIN, lotatt04, lotatt05, otherCode
        BeanUtils.copyProperties(query, scanResultForm);
        CommonVO commonVO = commonService.adaptScanResult4SKU(scanResultForm);

        if (!commonVO.isSuccess()) {

            resultJson.setSuccess(false);
            resultJson.setMsg(commonVO.getMessage());
            return resultJson;
        }

        //SKU获取成功，设置准确的批属
        BasSku basSku = commonVO.getBasSku();
        query.setLotatt04(commonVO.getBatchNum());
        query.setLotatt05(commonVO.getSerialNum());
        query.setSku(basSku.getSku());

        /*
        22222222，判断获取上架扫码数据是否齐全
         */
        Json scanJson = commonService.judgePaScanResult(query, commonVO);
        if (!scanJson.isSuccess()) {

            resultJson.setSuccess(false);
            resultJson.setMsg(scanJson.getMsg());
            return resultJson;
        }

        /*
        33333333，获取上架明细数据
        如果记录序列号的serial_flag为1，则在下方需要清除查询条件-序列号
         */
        List<DocPaDetails> docPaDetailsList = (List<DocPaDetails>) scanJson.getObj();//注解"unchecked"
        DocPaDetails docPaDetails = docPaDetailsList.get(0);//2222中已经做了为空判断

        if (docPaDetails.getInvLotAtt() == null) {

            resultJson.setSuccess(false);
            resultJson.setMsg("查无产品批次属性数据");
            return resultJson;
        }

        BeanUtils.copyProperties(docPaDetails, docPaDetailVO);
        docPaDetailVO.setBasSku(basSku);

        /*
        44444444444，查询最新一次上架提交的数据（同上架单号、客户代码、产品代码、批号）
        新增一个逻辑：此处还需要判断同批已上架的和目前扫描匹配的明细，是否日期数据匹配
         */
        DocPaDetailsQuery similarQuery = new DocPaDetailsQuery();
        similarQuery.setPano(query.getPano());
        similarQuery.setCustomerid(query.getCustomerid());
        similarQuery.setSku(basSku.getSku());
        similarQuery.setUserdefine3(query.getLotatt04());
        similarQuery.setSumflag(0);//看下SQL就知道了
        List<DocPaDetails> similarDetailList = docPaDetailsMybatisDao.querySimilarDetail(similarQuery);

        InvLotAtt invLotAtt = docPaDetailVO.getInvLotAtt();
        String lotatt01 = invLotAtt == null ? "" : invLotAtt.getLotatt01();
        String lotatt02 = docPaDetailVO.getUserdefine2();

        docPaDetailVO.setAlertflag(0);//默认不提醒
        if (similarDetailList.size() == 0) {

            docPaDetailVO.setUserdefine1("");
        }else {

            DocPaDetails similarDetail = similarDetailList.get(0);
            docPaDetailVO.setUserdefine1(similarDetail.getUserdefine1());
            for (DocPaDetails compareDetail : similarDetailList) {

                InvLotAtt compareLotatt = compareDetail.getInvLotAtt();
                String lotatt01Com = compareLotatt == null ? "" : compareLotatt.getLotatt01();
                String lotatt02Com = compareDetail.getUserdefine2();

                if (!StringUtil.fixNull(lotatt01Com).equals(StringUtil.fixNull(lotatt01)) ||
                        !StringUtil.fixNull(lotatt02Com).equals(StringUtil.fixNull(lotatt02))) {

                    docPaDetailVO.setAlertflag(1);//匹配到同批已上架和本次扫描上架的产品日期数据不同，返回前端提示
                    break;
                }
            }
        }

        /*
        5555555555，已上架件数计算
         */
        Double paCompleted = 0d;
        Double asnExpected = 0d;
        similarQuery.setSumflag(1);
        List<DocPaDetails> sameBatchDetailList = docPaDetailsMybatisDao.querySimilarDetail(similarQuery);
        for (DocPaDetails qtyDetail : sameBatchDetailList) {

            paCompleted += qtyDetail.getPutwayqtyCompleted();
            asnExpected += qtyDetail.getAsnqtyExpected();
        }
        docPaDetailVO.setPutwayqtyCompleted(paCompleted);//同批号的上架件数（跨单合计）
        docPaDetailVO.setAsnqtyExpected(asnExpected);//同批号收货件数（跨单合计）

        resultJson.setSuccess(true);
        resultJson.setMsg(Constant.SUCCESS_MSG);
        resultJson.setObj(docPaDetailVO);

        return resultJson;
    }

    /**
     * 上架提交
     * @param form pda上传表单数据
     * @return 结论
     */
    public PdaResult putawayGoods(PdaDocPaDetailForm form) {

        /*
        111，处理BasSku获取问题，并返回准确的批号、序列号条件
         */
        ScanResultForm scanResultForm = new ScanResultForm();
        BeanUtils.copyProperties(form, scanResultForm);
        scanResultForm.setLotatt04(form.getUserdefine3());
        scanResultForm.setLotatt05(form.getUserdefine4());
        CommonVO commonVO = commonService.adaptScanResult4SKU(scanResultForm);

        if (!commonVO.isSuccess()) return new PdaResult(PdaResult.CODE_FAILURE, commonVO.getMessage());

        //SKU获取成功，设置准确的批属
        BasSku basSku = commonVO.getBasSku();
        form.setUserdefine3(commonVO.getBatchNum());
        form.setUserdefine4(commonVO.getSerialNum());
        form.setSku(basSku.getSku());

        /*
        222，判断获取上架扫码数据是否齐全
         */
        PdaDocPaDetailQuery pdaDocPaDetailQuery = new PdaDocPaDetailQuery();
        pdaDocPaDetailQuery.setPano(form.getPano());
        pdaDocPaDetailQuery.setSku(basSku.getSku());
        pdaDocPaDetailQuery.setCustomerid(form.getCustomerid());
        pdaDocPaDetailQuery.setLotatt04(form.getUserdefine3());
        pdaDocPaDetailQuery.setLotatt05(form.getUserdefine4());
        Json scanJson = commonService.judgePaScanResult(pdaDocPaDetailQuery, commonVO);
        if (!scanJson.isSuccess()) return new PdaResult(PdaResult.CODE_FAILURE, scanJson.getMsg());

        /*
        333，获取上架明细
         */
        DocPaDetails docPaDetails = null;
        if (StringUtil.isNotEmpty(form.getPalineno())) {

            docPaDetails = docPaDetailsMybatisDao.queryByLineNo(form.getPano(), Integer.valueOf(form.getPalineno()));
        }else {

            List<DocPaDetails> docPaDetailsList = (List<DocPaDetails>) scanJson.getObj();
            docPaDetails = docPaDetailsList.get(0);//默认取第一个进行上架操作,judgePaScanResult判空操作已有
            form.setUserdefine5(docPaDetails.getUserdefine5());//连续上架都是DJ过来的，需要考虑HG的
        }

        InvLotAtt invLotAtt = docPaDetails.getInvLotAtt();
        if (invLotAtt == null) return new PdaResult(PdaResult.CODE_FAILURE, "查无产品批次属性数据");


        /*
         ********************************* 日期校验 *********************************
         */
        if (StringUtil.isEmpty(form.getLotatt01())) {
            form.setLotatt01(invLotAtt.getLotatt01());
        }
        if (StringUtil.isEmpty(form.getUserdefine2())) {
            form.setUserdefine2(invLotAtt.getLotatt02());
        }
        if (StringUtil.isEmpty(form.getLotatt01())) {
            return new PdaResult(PdaResult.CODE_FAILURE, "请选择生产日期");
        }else if (StringUtil.isEmpty(form.getUserdefine2())) {
            return new PdaResult(PdaResult.CODE_FAILURE, "请选择有效期/失效期");
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date prdDate = format.parse(form.getLotatt01());
            Date expiryDate = format.parse(form.getUserdefine2());
            if (prdDate.getTime() >= expiryDate.getTime()) {
                return new PdaResult(PdaResult.CODE_FAILURE, "有效期/失效期不可小于生产日期");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        上架操作
         */
        String locationid = form.getUserdefine1();
        String userdefine2 = form.getUserdefine2();
        String userdefine5 = form.getUserdefine5();
        String editwho = form.getEditwho();
        BeanUtils.copyProperties(docPaDetails, form);
        form.setUserdefine1(locationid);
        form.setUserdefine2(userdefine2);
        form.setUserdefine5(userdefine5);

        form.setAsnlineno(docPaDetails.getAsnlineno());
        form.setUserid(editwho);
        form.setLanguage("CN");
        form.setReturncode("");
        try {
            docPaDetailsMybatisDao.putawayGoods(form);
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
        }

        if (form.getReturncode().equals(Constant.PROCEDURE_OK)) {

            return new PdaResult(PdaResult.CODE_SUCCESS, "上架成功");
        } else {

            return new PdaResult(PdaResult.CODE_FAILURE, form.getReturncode());
        }
    }

    /**
     * 获取上架任务明细
     * @param pano 上架任务单
     * @return ~
     */
    public List<PdaDocPaDetailVO> queryDocPaList(String pano) {

        List<DocPaDetails> detailsList = docPaDetailsMybatisDao.queryDocPaList(pano);
        PdaDocPaDetailVO detailVO;
        List<PdaDocPaDetailVO> detailVOList = new ArrayList<>();
        for (DocPaDetails detail:
                detailsList) {
            detailVO = new PdaDocPaDetailVO();
            BeanUtils.copyProperties(detail, detailVO);

            //bassku
            BasSkuQuery basSkuQuery = new BasSkuQuery(detail.getCustomerid(), detail.getSku());
            BasSku basSku = basSkuMybatisDao.queryById(basSkuQuery);
            String jsonStr = JSON.toJSONString(basSku, SerializerFeature.DisableCircularReferenceDetect);
            detailVO.setBasSku(JSONObject.parseObject(jsonStr, BasSku.class));

            detailVOList.add(detailVO);
        }
        return detailVOList;
    }

    /**
     * 查询上架任务明细
     * @param pano
     * @return
     */
    public List<DocPaDetails> queryDocPdaDetails(String pano){
        List<DocPaDetails> detailsList = docPaDetailsMybatisDao.queryDocPaList(pano);
        return detailsList;
    }
}