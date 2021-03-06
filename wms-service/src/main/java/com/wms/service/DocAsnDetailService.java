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
import com.wms.utils.DateUtil;
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
	private BasPackageMybatisDao basPackageMybatisDao;

	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

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

	@Autowired
	private BasSkuService basSkuService;
	@Autowired
	private GspVerifyService gspVerifyService;

	@Autowired
	private GspProductRegisterService gspProductRegisterService;

	public EasyuiDatagrid<DocAsnDetailVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnDetailQuery query) {
		EasyuiDatagrid<DocAsnDetailVO> datagrid = new EasyuiDatagrid<DocAsnDetailVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByPageList(mybatisCriteria);

        DocAsnDetail docAsnDetailSum = new DocAsnDetail();
        if(query.getAsnno()!=null){
            docAsnDetailSum = docAsnDetailsMybatisDao.queryBySum(query.getAsnno());
        }

        double zero = 0;
        DocAsnDetailVO docAsnDetailVO = null;
        ProductLine productLineList = null;
        List<DocAsnDetailVO> docAsnDetailVOList = new ArrayList<DocAsnDetailVO>();
        //没有数据的话 总合计设为0
        if(docAsnDetailList.size()==0){
            docAsnDetailVO = new DocAsnDetailVO();
            docAsnDetailVO.setExpectedqtySum(zero);
            docAsnDetailVO.setExpectedqtyEachSum(zero);
            docAsnDetailVO.setReceivedqtySum(zero);
            docAsnDetailVOList.add(docAsnDetailVO);
        }
		for (DocAsnDetail docAsnDetail : docAsnDetailList) {
			docAsnDetailVO = new DocAsnDetailVO();
			BeanUtils.copyProperties(docAsnDetail, docAsnDetailVO);

            if(docAsnDetailSum != null){
                docAsnDetailVO.setExpectedqtySum(docAsnDetailSum.getExpectedqty().doubleValue());
                docAsnDetailVO.setExpectedqtyEachSum(docAsnDetailSum.getExpectedqtyEach().doubleValue());
                docAsnDetailVO.setReceivedqtySum(docAsnDetailSum.getReceivedqty().doubleValue());
            }
            //产品线
            productLineList = productLineMybatisDao.queryByDocAsn(docAsnDetailVO.getCustomerid(),docAsnDetailVO.getSku());
            if(productLineList !=null){
                docAsnDetailVO.setPname(productLineList.getName());
            }
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

    /**
     * 判断是否输入必填项
     * @param form 生产日期, 效期, 生产批号, 序列号
     * @return ~
     */
    private Json verifyRequiredFiled(DocAsnDetailForm form) {

        Json json = new Json();
        if (StringUtil.isEmpty(form.getLotatt01())) {

            json.setSuccess(false);
            json.setMsg("请输入生产日期");
            return json;
        }else if (StringUtil.isEmpty(form.getLotatt02())) {

            json.setSuccess(false);
            json.setMsg("请输入有效期/失效期");
            return json;
        }else if (StringUtil.isEmpty(form.getLotatt04()) && StringUtil.isEmpty(form.getLotatt05())) {

            json.setSuccess(false);
            json.setMsg("请输入生产批号或者序列号");
            return json;
        }
        json.setSuccess(true);
        return json;
    }

    /**
     * 根据所输入的、所选的生产日期适配一个最适宜的注册证号和生产厂家给入库明细
     * @param lotatt06 传入注册证，就匹配这个证号的
     * 其中有个逻辑漏洞，生产日期为空，但是传入了注册证，结果返回可能不是填入的注册证号
     */
    public DocAsnDetail adaptSuitableRegisterNo(String lotatt06, BasSku basSku, String lotatt01) {

        DocAsnDetail subAsnDetail = new DocAsnDetail();
        List<GspProductRegister> gspProductRegisterList;
        GspEnterpriseInfo enterpriseInfo;

        if (StringUtil.isNotEmpty(lotatt06)) {

            gspProductRegisterList = gspProductRegisterMybatisDao.queryByNoAndOrderBy(
                    lotatt06,
                    GspProductRegister.ORDERBY_EXPIRY_DATE_DESC);
            if (gspProductRegisterList.size() == 0) {

                subAsnDetail.setLotatt06(lotatt06);
                subAsnDetail.setLotatt15(basSku.getReservedfield14());
                return subAsnDetail;
            } else {

                GspProductRegister gspProductRegister = gspProductRegisterList.get(0);
                enterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspProductRegister.getEnterpriseId());
                subAsnDetail.setLotatt06(gspProductRegister.getProductRegisterNo());
                subAsnDetail.setLotatt15(
                        null == enterpriseInfo || StringUtil.isEmpty(enterpriseInfo.getEnterpriseName()) ?
                        basSku.getReservedfield14() :
                        enterpriseInfo.getEnterpriseName());
                return subAsnDetail;
            }
        }

        //如果导入的内容中没有生产日期，则将产品上下发的那个注册证赋上并查出生产厂家
        if (StringUtil.isEmpty(lotatt01)) {

            subAsnDetail.setLotatt06(basSku.getReservedfield03());
            PdaGspProductRegister productRegister = gspProductRegisterMybatisDao.queryByNo(basSku.getReservedfield03());
            //生产厂家
            subAsnDetail.setLotatt15(
                    productRegister != null && productRegister.getEnterpriseInfo() != null ?
                            productRegister.getEnterpriseInfo().getEnterpriseName() :
                            basSku.getReservedfield14());
            return subAsnDetail;
        }

        gspProductRegisterList = gspProductRegisterMybatisDao.queryBysku(
                basSku.getSku(),
                GspProductRegister.ORDERBY_EXPIRY_DATE_DESC_SKU);

        //没有注册证的产品直接返回产品档案里的生产企业,注册证是没有的（非医疗器械）
        if (gspProductRegisterList.size() == 0) {

            subAsnDetail.setLotatt06("");
            subAsnDetail.setLotatt15(basSku.getReservedfield14());
            return subAsnDetail;
        }

        //根据所输入的、所选的生产日期适配一个最适宜的注册证号和生产厂家给入库明细
        for (GspProductRegister gspProductRegister : gspProductRegisterList) {

            if (DateUtil.betweenOn(lotatt01, gspProductRegister.getApproveDate(), gspProductRegister.getProductRegisterExpiryDate())) {

                subAsnDetail.setLotatt06(gspProductRegister.getProductRegisterNo());
                enterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspProductRegister.getEnterpriseId());
                subAsnDetail.setLotatt15(
                        null == enterpriseInfo || StringUtil.isEmpty(enterpriseInfo.getEnterpriseName()) ?
                                basSku.getReservedfield14() :
                                enterpriseInfo.getEnterpriseName());
                break;
            }
        }

        return subAsnDetail;
    }

    /**
     * 私有公共方法 通过产品中的注册证号查询所有的注册证历史，来做产品注册证适宜判断
     */
    private DocAsnDetail adaptSuitableRegisterNo(BasSku basSku, String lotatt01) {

        return adaptSuitableRegisterNo("", basSku, lotatt01);
    }

	public Json addDocAsnDetail(DocAsnDetailForm docAsnDetailForm) throws Exception {

	    //Json statusJson = asnStatusCheck(docAsnDetailForm.getAsnno());

		DocAsnHeaderQuery query = new DocAsnHeaderQuery();
		query.setAsnno(docAsnDetailForm.getAsnno());
		DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(query);
		Json statusJson =  asnObjCheck(docAsnHeader);

		//单据状态判断
	    if (!statusJson.isSuccess()) {
	        return statusJson;
        }

        //detail内容判断
        statusJson = verifyRequiredFiled(docAsnDetailForm);
	    if (!statusJson.isSuccess()) {
	        return statusJson;
        }

		Json verJson = gspVerifyService.verifyOperate(docAsnHeader.getCustomerid(),docAsnHeader.getSupplierid(),docAsnDetailForm.getSku(),docAsnDetailForm.getLotatt01(),docAsnDetailForm.getLotatt02());
		if(!verJson.isSuccess()){
			return verJson;
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

		//预期收货数量
        BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
        docAsnDetail.setExpectedqtyEach(basPackage.getQty1().multiply(docAsnDetail.getExpectedqty()));

		//入库日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        docAsnDetail.setLotatt03(formatter.format(new Date()));

        //产品双证
//        docAsnDetail.setLotatt13(basSku.getSkuGroup7());

        //供应商取表头的
        docAsnDetail.setLotatt08(docAsnHeader.getSupplierid());

        //质量状态
        docAsnDetail.setLotatt10("DJ");

        //储存条件
        docAsnDetail.setLotatt11(basSku.getSkuGroup4());

        //产品名称
        docAsnDetail.setLotatt12(basSku.getReservedfield01());

		//预入库单号
        docAsnDetail.setLotatt14(docAsnDetailForm.getAsnno());

        //根据所输入的生产日期适配一个最适宜的注册证号和生产厂家给入库明细
        DocAsnDetail subAsnDetail = adaptSuitableRegisterNo(basSku, docAsnDetail.getLotatt01());
        //产品注册证
        docAsnDetail.setLotatt06(subAsnDetail.getLotatt06());
        //生产厂家
        docAsnDetail.setLotatt15(subAsnDetail.getLotatt15());

		//赋值
		docAsnDetail.setPackid(basSku.getPackid());
		docAsnDetail.setAlternativesku(basSku.getAlternateSku1());

		//判断是否要插入扫码批次匹配表
        BasGtnLotattQuery basGtnLotattQuery = new BasGtnLotattQuery();
        basGtnLotattQuery.setCustomerid(docAsnDetail.getCustomerid());
        basGtnLotattQuery.setSku(docAsnDetail.getSku());
        basGtnLotattQuery.setLotatt02(docAsnDetail.getLotatt02());
        basGtnLotattQuery.setLotatt04(docAsnDetail.getLotatt04());
        basGtnLotattQuery.setLotatt05(docAsnDetail.getLotatt05());
		basGtnLotattService.queryInsertGtnLotatt(basGtnLotattQuery);

        //定向订单库位
        docAsnDetail = configDxLocation(docAsnDetail);

		docAsnDetailsMybatisDao.add(docAsnDetail);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}

	public Json editDocAsnDetail(DocAsnDetailForm docAsnDetailForm) {
        Json statusJson = asnStatusCheck(docAsnDetailForm.getAsnno());
        //单据状态判断
        if (!statusJson.isSuccess()) {
            return statusJson;
        }

        //detail内容判断
        statusJson = verifyRequiredFiled(docAsnDetailForm);
        if (!statusJson.isSuccess()) {
            return statusJson;
        }

		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(docAsnDetailForm.getAsnno());
		docAsnDetailQuery.setAsnlineno(docAsnDetailForm.getAsnlineno());
		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(docAsnDetailQuery);
//		BeanUtils.copyProperties(docAsnDetailForm, docAsnDetail);
        docAsnDetail.setExpectedqty(docAsnDetailForm.getExpectedqty());
        docAsnDetail.setLotatt01(docAsnDetailForm.getLotatt01());
        docAsnDetail.setLotatt02(docAsnDetailForm.getLotatt02());
        docAsnDetail.setLotatt04(docAsnDetailForm.getLotatt04());
        docAsnDetail.setLotatt05(docAsnDetailForm.getLotatt05());
        docAsnDetail.setLotatt09(docAsnDetailForm.getLotatt09());
        docAsnDetail.setReceivinglocation(docAsnDetailForm.getReceivinglocation());
		docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());

        BasSku basSku = basSkuService.getSkuInfo(docAsnDetail.getCustomerid(),docAsnDetail.getSku());
        if(basSku!=null){

            //产品名称
            docAsnDetail.setLotatt12(basSku.getReservedfield01());
            //储存条件
            docAsnDetail.setLotatt11(basSku.getSkuGroup4());

            //数量换算
            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            docAsnDetail.setExpectedqtyEach(docAsnDetail.getExpectedqty().multiply(basPackage.getQty1()));

            //根据所输入的生产日期适配一个最适宜的注册证号和生产厂家给入库明细
            DocAsnDetail subAsnDetail = adaptSuitableRegisterNo(basSku, docAsnDetail.getLotatt01());
            //产品注册证
            docAsnDetail.setLotatt06(subAsnDetail.getLotatt06());
            //生产厂家
            docAsnDetail.setLotatt15(subAsnDetail.getLotatt15());
        }

        //判断是否要插入扫码批次匹配表
        BasGtnLotattQuery basGtnLotattQuery = new BasGtnLotattQuery();
        basGtnLotattQuery.setCustomerid(docAsnDetail.getCustomerid());
        basGtnLotattQuery.setSku(docAsnDetail.getSku());
        basGtnLotattQuery.setLotatt02(docAsnDetail.getLotatt02());
        basGtnLotattQuery.setLotatt04(docAsnDetail.getLotatt04());
        basGtnLotattQuery.setLotatt05(docAsnDetail.getLotatt05());
        basGtnLotattService.queryInsertGtnLotatt(basGtnLotattQuery);

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
            if (docAsnDetail.getLinestatus().equals("00")) {
                if (docAsnDetail.getAddwho().contains("EDI") && !SfcUserLoginUtil.getLoginUser().getId().equals("admin")) {
                    json.setSuccess(false);
                    json.setMsg("接口订单,不可删除!");
                } else {
                    docAsnDetailsMybatisDao.delete(docAsnDetail);
                    json.setSuccess(true);
                    json.setMsg("删除成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("新建状态的行明细才可删除!");
            }
		} else {
            json.setSuccess(false);
            json.setMsg("行明细查无数据");
        }
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
			BasSku basSku = basSkuService.getSkuInfo(docAsnDetail.getCustomerid(), docAsnDetail.getSku());
			docAsnDetail.setPackid(basSku.getPackid());
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

            List<BasSerialNum> basSerialNumList = basSerialNumMybatisDao.queryValidatedId(StringUtil.isNotEmpty(query.getOtherCode()) ? query.getOtherCode() : query.getLotatt05());
            if (basSerialNumList != null && basSerialNumList.size() > 0) {

                basSerialNum = basSerialNumList.get(0);
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

        String editwho = form.getEditwho();
        String warehouseid = form.getWarehouseid();
        PdaBasSkuQuery skuQuery = new PdaBasSkuQuery();
        PdaDocAsnDetailQuery detailQuery = new PdaDocAsnDetailQuery();

        //如果是序列号扫码，验证是否存在对应序列号记录（bas_serial_num）
        // 这里处理的有两种情况：
        //  1.扫描序列号出库
        //  2.扫描带序列号的条码出库
        BasSerialNum basSerialNum = null;
        if (StringUtil.isNotEmpty(form.getOtherCode()) ||
                StringUtil.isNotEmpty(form.getLotatt05())) {

            List<BasSerialNum> basSerialNumList = basSerialNumMybatisDao.queryValidatedId(StringUtil.isNotEmpty(form.getOtherCode()) ? form.getOtherCode() : form.getLotatt05());
            if (basSerialNumList != null && basSerialNumList.size() > 0) {

                basSerialNum = basSerialNumList.get(0);
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
        form.setEditwho(editwho);
        form.setWarehouseid(warehouseid);
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