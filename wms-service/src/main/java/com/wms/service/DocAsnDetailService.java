package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocAsnDetailForm;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.*;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocAsnDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnDetailForm;
import com.wms.vo.pda.PdaDocAsnDetailVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("docAsnDetailService")
public class DocAsnDetailService extends BaseService {

	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;

	@Autowired
	private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private BasGtnMybatisDao basGtnMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private BasGtnLotattService basGtnLotattService;

	@Autowired
	private InvLotAttService invLotAttService;

	@Autowired
	private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;

	@Autowired
	private BasSerialNumMybatisDao basSerialNumMybatisDao;

	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;

	public EasyuiDatagrid<DocAsnDetailVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnDetailQuery query) {
		EasyuiDatagrid<DocAsnDetailVO> datagrid = new EasyuiDatagrid<DocAsnDetailVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByPageList(mybatisCriteria);
		DocAsnDetailVO docAsnDetailVO = null;
		List<DocAsnDetailVO> docAsnDetailVOList = new ArrayList<DocAsnDetailVO>();
		for (DocAsnDetail docAsnDetail : docAsnDetailList) {
			docAsnDetailVO = new DocAsnDetailVO();
			BeanUtils.copyProperties(docAsnDetail, docAsnDetailVO);
			docAsnDetailVOList.add(docAsnDetailVO);
		}
		datagrid.setTotal((long) docAsnDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docAsnDetailVOList);
		return datagrid;
	}

    /**
     * 检查预入库单是否可新增
     * @param asnno 预入库单号
     * @return 反馈
     */
	public Json asnStatusCheck(String asnno) {
        DocAsnHeaderQuery query = new DocAsnHeaderQuery();
        query.setAsnno(asnno);
	    DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(query);
	    return asnObjCheck(docAsnHeader);
    }

    private Json asnObjCheck(DocAsnHeader docAsnHeader) {
        Json json = new Json();
        if(docAsnHeader != null){

            /*
             * H - 订单冻结
             * N - 订单未释放
             * R - 订单任务下发
             * Y - 订单释放
             */
            switch (docAsnHeader.getReleasestatus()) {
                case "H":
                    json.setSuccess(false);
                    json.setMsg("当前预入库单已冻结！");
                    return json;
//                case "N":
//                    json.setSuccess(false);
//                    json.setMsg("当前预入库未释放");
//                    return json;
                default:
                    break;
            }

            switch (docAsnHeader.getAsnstatus()) {
                case "00":
                case "10":
                    json.setSuccess(true);
                    json.setMsg("000");
                    return json;
                case "30":
//                    json.setSuccess(false);
//                    json.setMsg("当前预入库单已部分收货！");
//                    return json;
                case "40":
//                    json.setSuccess(false);
//                    json.setMsg("当前预入库单已完全收货！");
//                    return json;
                case "70":
//                    json.setSuccess(false);
//                    json.setMsg("当前预入库单已完全验收！");
//                    return json;
                default:
                    json.setSuccess(false);
                    json.setMsg("当前状态下预入库单不允许进行操作！");
                    return json;
            }
        } else {
            json.setSuccess(false);
            json.setMsg("查无此预入库单号！");
            return json;
        }
    }


	public Json addDocAsnDetail(DocAsnDetailForm docAsnDetailForm) throws Exception {

	    Json statusJson = asnStatusCheck(docAsnDetailForm.getAsnno());
	    if (!statusJson.isSuccess()) {
	        return statusJson;
        }

	    DocAsnDetail docAsnDetail = new DocAsnDetail();
	    BeanUtils.copyProperties(docAsnDetailForm, docAsnDetail);

		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(docAsnDetailForm.getAsnno());
		/*获取新的明细行号*/
		int orderlineno = docAsnDetailsMybatisDao.getAsnlinenoById(docAsnDetailQuery);
		docAsnDetail.setAsnlineno(orderlineno + 1);
		docAsnDetail.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());

		//获取SKU信息(条码、包装、重量、体积、金额)
		BasSkuQuery skuQuery = new BasSkuQuery();
		skuQuery.setCustomerid(docAsnDetail.getCustomerid());
		skuQuery.setSku(docAsnDetail.getSku());
//		skuQuery.setQty(docAsnDetail.getExpectedqty());
		BasSku basSku = basSkuMybatisDao.queryById(skuQuery);

		//入库日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        docAsnDetail.setLotatt03(formatter.format(new Date()));

        //产品名称
		if(basSku!=null){
//			docAsnDetail.setLotatt13(basSku.getSkuGroup7());
            docAsnDetail.setLotatt06(basSku.getReservedfield03());
			docAsnDetail.setLotatt12(basSku.getReservedfield01());
			docAsnDetail.setLotatt08(basSku.getSkuGroup6());
		}

		//质量状态
        docAsnDetail.setLotatt10("DJ");

		//预入库单号
        docAsnDetail.setLotatt14(docAsnDetailForm.getAsnno());

		//生产厂家
		if (docAsnDetail.getLotatt06() != null && !docAsnDetail.getLotatt06().equals("")) {

            PdaGspProductRegister productRegister = gspProductRegisterMybatisDao.queryByNo(docAsnDetail.getLotatt06());
            docAsnDetail.setLotatt15(productRegister.getEnterpriseInfo().getEnterpriseName());

		}

		//判断预入库明细里面的sku和客户id下的18个批属是否存在
		InvLotAtt invLotAtt = invLotAttService.queryInsertLotatts(docAsnDetail);
		//判断是否要插入扫码批次匹配表
		basGtnLotattService.queryInsertGtnLotatt(invLotAtt, docAsnDetailForm.getAsnno());

        //定向订单库位
        docAsnDetail = configDxLocation(docAsnDetail);

		docAsnDetailsMybatisDao.add(docAsnDetail);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}

	public Json editDocAsnDetail(DocAsnDetailForm docAsnDetailForm) {
        Json statusJson = asnStatusCheck(docAsnDetailForm.getAsnno());
        if (!statusJson.isSuccess()) {
            return statusJson;
        }

		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(docAsnDetailForm.getAsnno());
		docAsnDetailQuery.setAsnlineno(docAsnDetailForm.getAsnlineno());
		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(docAsnDetailQuery);
		BeanUtils.copyProperties(docAsnDetailForm, docAsnDetail);
		docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());

        //判断预入库明细里面的sku和客户id下的18个批属是否存在
        InvLotAtt invLotAtt = invLotAttService.queryInsertLotatts(docAsnDetail);
        //判断是否要插入扫码批次匹配表
        basGtnLotattService.queryInsertGtnLotatt(invLotAtt, docAsnDetailForm.getAsnno());

		//定向订单库位
		docAsnDetail = configDxLocation(docAsnDetail);

		docAsnDetailsMybatisDao.update(docAsnDetail);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}

	public Json deleteDocAsnDetail(String asnno, long asnlineno) {
		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(asnno);
		docAsnDetailQuery.setAsnlineno(asnlineno);
		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(docAsnDetailQuery);
		if(docAsnDetail != null){
			docAsnDetailsMybatisDao.delete(docAsnDetail);
		}
		json.setSuccess(true);
		json.setMsg("删除成功");
		return json;
	}
	
	public Json receiveDocAsnDetail(String asnno, long asnlineno) {
		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(asnno);
		docAsnDetailQuery.setAsnlineno(asnlineno);
		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(docAsnDetailQuery);
		String result = "";
		if(docAsnDetail != null){
			if(StringUtils.isEmpty(docAsnDetail.getReceivinglocation())){
				//docAsnDetail.setPlantoloc("STAGE01");
				docAsnDetail.setReceivinglocation("STAGE01");
			}
			docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			docAsnDetail.setReceivedtime(new Date());
			docAsnDetail.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			docAsnDetailsMybatisDao.receiveByAsn(docAsnDetail);
			result = docAsnDetail.getResult();
		}
		if (result.substring(0,3).equals("000")) {
			json.setSuccess(true);
			json.setMsg("收货成功！");
		} else {
			json.setSuccess(false);
			json.setMsg("收货失败！"+result);
		}
		return json;
	}
	
	public List<EasyuiCombobox> getDocAsnDetailCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByAll();
		if(docAsnDetailList != null && docAsnDetailList.size() > 0){
			for(DocAsnDetail docAsnDetail : docAsnDetailList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docAsnDetail.getAsnlineno()));
				combobox.setValue(docAsnDetail.getAsnno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

    /**
     * 获取入库预报
     * @param query ~
     * @return 明细
     */
	public PdaDocAsnDetailVO queryDocAsnDetail(PdaDocAsnDetailQuery query) {

	    PdaDocAsnDetailVO pdaDocAsnDetailVO = new PdaDocAsnDetailVO();

        //如果是序列号扫码，验证是否存在对应序列号记录（bas_serial_num）
        // 这里处理的有两种情况：
        //  1.扫描序列号出库
        //  2.扫描带序列号的条码出库
        BasSerialNum basSerialNum = null;
        if (StringUtil.isNotEmpty(query.getOtherCode()) ||
                StringUtil.isNotEmpty(query.getLotatt05())) {

            BasSerialNumQuery serialNumQuery = new BasSerialNumQuery(StringUtil.isNotEmpty(query.getOtherCode()) ? query.getOtherCode() : query.getLotatt05());
            basSerialNum = basSerialNumMybatisDao.queryById(serialNumQuery);
            if (basSerialNum != null) {

                //序列号扫码数据缺失 效期、生产批号（注：序列号不需要传，效期不参与查询）
                query.setLotatt04(basSerialNum.getBatchNum());
            }
        }

        PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
        BeanUtils.copyProperties(query, basSkuQuery);
        if (basSerialNum != null) basSkuQuery.setLotatt05("");
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

        if (basSku == null) {
            pdaDocAsnDetailVO.setBasSku(null);
            return pdaDocAsnDetailVO;
        }

        /*
        产品线 为空则默认正常流程
        不为空的情况下，如果记录序列号的serial_flag为1，则在下方需要清除查询条件-序列号
         */
        ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
        ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
        boolean isSerialManagement = (productLine != null && productLine.getSerialFlag() == 1);

        query.setSku(basSku.getSku());
        if (isSerialManagement) query.setLotatt05("");
        DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryForScan(query);
        if (docAsnDetail != null) {
            BeanUtils.copyProperties(docAsnDetail, pdaDocAsnDetailVO);
            pdaDocAsnDetailVO.setBasSku(basSku);
        }
        return pdaDocAsnDetailVO;
    }

    /**
     * 收货提交
     * @param form pda上传表单数据
     * @return 结论
     */
    public PdaResult receiveGoods(PdaDocAsnDetailForm form) {

        PdaBasSkuQuery skuQuery = new PdaBasSkuQuery();
        PdaDocAsnDetailQuery detailQuery = new PdaDocAsnDetailQuery();

        //如果是序列号扫码，验证是否存在对应序列号记录（bas_serial_num）
        // 这里处理的有两种情况：
        //  1.扫描序列号出库
        //  2.扫描带序列号的条码出库
        BasSerialNum basSerialNum = null;
        if (StringUtil.isNotEmpty(form.getOtherCode()) ||
                StringUtil.isNotEmpty(form.getLotatt05())) {

            BasSerialNumQuery serialNumQuery = new BasSerialNumQuery(StringUtil.isNotEmpty(form.getOtherCode()) ? form.getOtherCode() : form.getLotatt05());
            basSerialNum = basSerialNumMybatisDao.queryById(serialNumQuery);
            if (basSerialNum != null) {

                //序列号扫码数据缺失 效期、生产批号（注：序列号不需要传，效期不参与查询）
                form.setLotatt04(basSerialNum.getBatchNum());
            }
        }

        BeanUtils.copyProperties(form, skuQuery);
        BeanUtils.copyProperties(form, detailQuery);

        //SKU
        if (basSerialNum != null) skuQuery.setLotatt05("");
        BasSku basSku = basSkuMybatisDao.queryForScan(skuQuery);

        if (basSku == null) return new PdaResult(PdaResult.CODE_FAILURE, "产品档案缺失");

        //DocAsnDetails
        detailQuery.setSku(basSku.getSku());
        DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryForScan(detailQuery);

        if (docAsnDetail == null) return new PdaResult(PdaResult.CODE_FAILURE, "收货明细数据缺失");

        BeanUtils.copyProperties(docAsnDetail, form);
        form.setEditwho("Gizmo");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        form.setReceivingtime(dateFormat.format(new Date()));
        form.setReceivinglocation("STAGE01");

        /*
        产品线 为空则默认正常流程
        不为空的情况下，如果记录序列号的serial_flag为1，则在下方需要清除查询条件-序列号
         */
        ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
        ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
        boolean isSerialManagement = (productLine != null && productLine.getSerialFlag() == 1);
        if (isSerialManagement) form.setLotatt05("");

        //收货
        try {
            docAsnDetailsMybatisDao.receiveGoods(form);
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
        }
        if (form.getResult().equals(Constant.PROCEDURE_OK)) {

            return new PdaResult(PdaResult.CODE_SUCCESS, "收货成功");
        } else {

            return new PdaResult(PdaResult.CODE_FAILURE, form.getResult());
        }
    }

	/**
	 * 获取入库预报
	 * @param query ~
	 * @return 明细
	 */
	public DocAsnDetailVO queryDocAsnDetail(DocAsnDetailQuery query) {

		DocAsnDetailVO docAsnDetailVO = new DocAsnDetailVO();

		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(query);
		if (docAsnDetail != null) {
			BeanUtils.copyProperties(docAsnDetail, docAsnDetailVO);
		}
		return docAsnDetailVO;
	}

    /**
     * 处理定向订单 || 引用入库 的上架库位
     */
	public DocAsnDetail configDxLocation(DocAsnDetail docAsnDetail) {

	    if (docAsnDetail == null || docAsnDetail.getAsnno() == null || docAsnDetail.getAsnno().length() == 0) {
	        return docAsnDetail;
        }
        DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(docAsnDetail.getAsnno());
	    if (docAsnHeader == null || docAsnHeader.getAsntype() == null || docAsnHeader.getAsntype().length() == 0) {
	        return docAsnDetail;
        }
        if (docAsnDetail.getReceivinglocation() == null ||
                docAsnDetail.getReceivinglocation().length() == 0) {

            if (docAsnHeader.getAsntype().equals(DocAsnHeader.ASN_TYPE_DX)) {

                docAsnDetail.setReceivinglocation(DocAsnDetail.DX_RECEIVING_LOCATION);
            }else if (docAsnHeader.getAsntype().equals(DocAsnHeader.ASN_TYPE_YY)) {

                docAsnDetail.setReceivinglocation(DocAsnDetail.YY_RECEIVING_LOCATION);
            }
        }

        return docAsnDetail;
    }
}