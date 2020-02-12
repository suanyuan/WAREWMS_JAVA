package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.DocAsnHeaderQuery;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.OrderHeaderForNormalVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service("statisticalAnalysisService")
public class StatisticalAnalysisService extends BaseService {

	@Autowired
	private StatisticalAnalysisMybatisDao statisticalAnalysisMybatisDao;
	@Autowired
	private BasCodesService basCodesService;
	@Autowired
	private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
	@Autowired
	private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
	@Autowired
	private BasCodesMybatisDao basCodesMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;
	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;

	/**************************************出入库流水****************************************/
	public EasyuiDatagrid<RptSoAsnDailyLocation> getPagedDatagridRptSoAsnDailyLocation(EasyuiDatagridPager pager, RptSoAsnDailyLocation query) {
		EasyuiDatagrid<RptSoAsnDailyLocation> datagrid = new EasyuiDatagrid<RptSoAsnDailyLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows()/2);
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptSoAsnDailyLocation> rptSoAsnDailyLocationList = statisticalAnalysisMybatisDao.querySoAsnInvLocation(mybatisCriteria);
//		for (RptSoAsnDailyLocation s: rptSoAsnDailyLocationList) {
//			//计算数量
//			if(s.getQty1()!=null&&s.getSoqty()!=null){
//				s.setSoqtyeach(s.getQty1()*s.getSoqty());
//			}
//			if(s.getQty1()!=null&&s.getAsnqty()!=null){
//				s.setAsnqtyeach(s.getQty1()*s.getAsnqty());
//			}
//		}
		long a=(long) statisticalAnalysisMybatisDao.queryAsnInvLocationCount(mybatisCriteria);
		long b=(long) statisticalAnalysisMybatisDao.querySoInvLocationCount(mybatisCriteria);
//		datagrid.setTotal(a>b?a*2:b*2);
		datagrid.setTotal(a+b);
		datagrid.setRows(rptSoAsnDailyLocationList);
		return datagrid;
	}
    public void exportSoAsnDailyDataToExcel(HttpServletResponse response, RptSoAsnDailyLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankSoAsnDaily();
			// excel的sheetName
			String sheetName = "出入库流水";
			// excel要导出的数据
			List<RptSoAsnDailyLocation> rptSoAsnDailyLocationList = statisticalAnalysisMybatisDao.querySoAsnInvLocation(mybatisCriteria);
			// 导出
			if (rptSoAsnDailyLocationList == null || rptSoAsnDailyLocationList.size() == 0) {
				System.out.println("题库为空");
			}else {
//				for (RptSoAsnDailyLocation s: rptSoAsnDailyLocationList) {
//				//计算数量
//				    if(s.getQty1()!=null&&s.getSoqty()!=null){
//				    	s.setSoqtyeach(s.getQty1()*s.getSoqty());
//					}
//				    if(s.getQty1()!=null&&s.getAsnqty()!=null){
//				    	s.setAsnqtyeach(s.getQty1()*s.getAsnqty());
//					}
//				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptSoAsnDailyLocationList, fieldMap, sheetName, response);
//				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankSoAsnDaily() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("documentNo", "单据号");
		superClassMap.put("soasndate", "日期");
		superClassMap.put("documentType", "单据类型");
		superClassMap.put("warehouse", "仓库");
		superClassMap.put("customerid", "供货单位");
		superClassMap.put("sku", "存货编码");
		superClassMap.put("lotatt12", "存货名称");
		superClassMap.put("descrc", "规格型号");
		superClassMap.put("asnqtyeach", "入库数量");
		superClassMap.put("asnqty", "入库件数");
		superClassMap.put("soqtyeach", "出库数量");
		superClassMap.put("soqty", "出库件数");
		superClassMap.put("uom", "主计量单位");
		superClassMap.put("lotatt04", "批号");
		superClassMap.put("lotatt05", "序列号");
		superClassMap.put("purchaseOrderNumber", "采购订单号");
		superClassMap.put("notes", "备注");
		superClassMap.put("planPrice", "计划价");
		return superClassMap;
	}







	/**************************************入库单列表****************************************/
	public EasyuiDatagrid<DocAsnHeaderVO> getPagedDatagridRptAsnList(EasyuiDatagridPager pager, DocAsnHeaderQuery query) {
		EasyuiDatagrid<DocAsnHeaderVO> datagrid = new EasyuiDatagrid<DocAsnHeaderVO>();

		if(query.getSkuGroup1() != null){
			query.setSkuGroup1(query.getSkuGroup1());
		}else {
			query.setSkuGroup1("");
		}
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("asnno desc");
		List<DocAsnHeader> docAsnHeaderList = docAsnHeaderMybatisDao.queryByPageList(mybatisCriteria);
		List<DocAsnDetail> docAsnDetails ;
		DocAsnHeaderVO docAsnHeaderVO = null;
		BasCustomer basCustomer = null;
		ProductLine productLineList = null;
		List<DocAsnHeaderVO> docAsnHeaderVOList = new ArrayList<DocAsnHeaderVO>();
		Double expectedqty = 0.0;
		Double expectedqtyNum = 0.0;
		for (DocAsnHeader docAsnHeader : docAsnHeaderList) {
			docAsnHeaderVO = new DocAsnHeaderVO();
			BeanUtils.copyProperties(docAsnHeader, docAsnHeaderVO);

			//入库日期
			docAsnDetails = docAsnDetailsMybatisDao.queryByAsnNo(docAsnHeaderVO.getAsnno());
			if(docAsnDetails != null && docAsnDetails.size()>0){
				docAsnHeaderVO.setLoatt03(docAsnDetails.get(0).getLotatt03());
				//产品线
				productLineList = productLineMybatisDao.queryByDocAsn(docAsnDetails.get(0).getCustomerid(),docAsnDetails.get(0).getSku());
				if(productLineList !=null){
					docAsnHeaderVO.setPname(productLineList.getName());
				}
			}
			//件数  数量
			for(DocAsnDetail docAsnDetail:docAsnDetails){
				expectedqty  = docAsnDetail.getExpectedqty().doubleValue()+expectedqty;
				expectedqtyNum = docAsnDetail.getExpectedqtyEach().doubleValue()+expectedqtyNum;
			}
			docAsnHeaderVO.setExpectedqty(expectedqty);
			docAsnHeaderVO.setExpectedqtyNum(expectedqtyNum);
			//货主
			basCustomer = basCustomerMybatisDao.queryByIdType(docAsnHeaderVO.getCustomerid(), Constant.CODE_CUS_TYP_OW);
			if (basCustomer != null) {
				docAsnHeaderVO.setCustomerIdRef(basCustomer.getDescrC());
			} else {
				docAsnHeaderVO.setCustomerIdRef(" ");
			}
			docAsnHeaderVOList.add(docAsnHeaderVO);
		}
		datagrid.setTotal((long) docAsnHeaderMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docAsnHeaderVOList);
		return datagrid;
	}
	public void exportAsnListDataToExcel(HttpServletResponse response, DocAsnHeaderQuery form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setOrderByClause("asnno desc");
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankAsnList();
			// excel的sheetName
			String sheetName = "入库单列表";
			// excel要导出的数据
			List<DocAsnHeader> rptAsnList = docAsnHeaderMybatisDao.queryByPageList(mybatisCriteria);
			// 导出
			if (rptAsnList== null || rptAsnList.size() == 0) {
				System.out.println("题库为空");
			}else {
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptAsnList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankAsnList() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("customerid", "客户编码");
		superClassMap.put("asnno", "入库单编号");
		superClassMap.put("asntypeName", "入库类型");
		superClassMap.put("asnstatusName", "入库状态");
		superClassMap.put("sup", "供应商");
		superClassMap.put("asnreference1", "客户单号1");
		superClassMap.put("asnreference2", "客户单号2");
		superClassMap.put("userdefine2", "上架单号");
		superClassMap.put("coldTag", "冷链标记");
		superClassMap.put("edisendflag", "回传标识");
		superClassMap.put("expectedarrivetime1", "预期到货时间");
//		superClassMap.put("soqtyeach", "出库数量");
//		superClassMap.put("soqty", "出库件数");
		superClassMap.put("addtime", "创建时间");
		superClassMap.put("addwho", "创建人");
		superClassMap.put("edittime", "最后编辑时间");
		superClassMap.put("editwho", "编辑人");
		superClassMap.put("releasestatusName", "释放状态");
		superClassMap.put("warehouseid", "仓库编码");
		superClassMap.put("notes", "备注");
		return superClassMap;
	}





	/**************************************收发存汇总表****************************************/
	public EasyuiDatagrid<RptSendReceiveAndSave> getPagedDatagridRptSendReceiveAndSaveAllList(EasyuiDatagridPager pager, RptSendReceiveAndSave query) {
		EasyuiDatagrid<RptSendReceiveAndSave> datagrid = new EasyuiDatagrid<RptSendReceiveAndSave>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptSendReceiveAndSave> rptSendReceiveAndSaveList= statisticalAnalysisMybatisDao.querySendReceiveAndSaveAllList(mybatisCriteria);
		for (RptSendReceiveAndSave s: rptSendReceiveAndSaveList) {

		}
		datagrid.setTotal((long) statisticalAnalysisMybatisDao.querySendReceiveAndSaveAllListCount(mybatisCriteria));
		datagrid.setRows(rptSendReceiveAndSaveList);
		return datagrid;
	}
	public void exportSendReceiveAndSaveAllListDataToExcel(HttpServletResponse response, RptSendReceiveAndSave form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankRptSendReceiveAndSaveAllList();
			// excel的sheetName
			String sheetName = "收发存汇中总表";
			// excel要导出的数据
			List<RptSendReceiveAndSave> rptSendReceiveAndSaveList = statisticalAnalysisMybatisDao.querySendReceiveAndSaveAllList(mybatisCriteria);
			// 导出
			if (rptSendReceiveAndSaveList== null || rptSendReceiveAndSaveList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (RptSendReceiveAndSave s: rptSendReceiveAndSaveList) {

				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptSendReceiveAndSaveList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankRptSendReceiveAndSaveAllList() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("warehouse", "仓库名称");
		superClassMap.put("sku", "存货编码");
		superClassMap.put("lotatt12", "存货名称");
		superClassMap.put("descrc", "规格型号");
		superClassMap.put("uom", "主计量单位");
		superClassMap.put("skuCategoryName", "存货分类名称");
		superClassMap.put("startResultQty", "期初结存件数");
		superClassMap.put("asnqtySum", "总计_入库件数");
		superClassMap.put("soqtySum", "总计_出库件数");
		superClassMap.put("endResultQty", "期末结存件数");
		return superClassMap;
	}




	/**************************************销售出库单列表****************************************/
	public EasyuiDatagrid<OrderHeaderForNormalVO> getPagedDatagridRptSalesSoList(EasyuiDatagridPager pager, OrderHeaderForNormalQuery query) {
		EasyuiDatagrid<OrderHeaderForNormalVO> datagrid = new EasyuiDatagrid<>();

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		mybatisCriteria.setOrderByClause("orderno desc");
		List<OrderHeaderForNormal> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryByList(mybatisCriteria);
		List<OrderDetailsForNormal> odForNormalList = null;
		List<RptSalesSo> rptRptSalesSoList= null;
		OrderHeaderForNormalVO orderHeaderForNormalVO;
		BasCustomer basCustomer = null;
		ProductLine productLineList = null;
		List<OrderHeaderForNormalVO> orderHeaderForNormalVOList = new ArrayList<>();
		Double qtyshipped = 0.0;//件数
		Double qtyshippedEach = 0.0;//数量
		for (OrderHeaderForNormal orderHeaderForNormal : orderHeaderForNormalList) {
			orderHeaderForNormalVO = new OrderHeaderForNormalVO();
			BeanUtils.copyProperties(orderHeaderForNormal, orderHeaderForNormalVO);
			rptRptSalesSoList= statisticalAnalysisMybatisDao.querySalesSoList(mybatisCriteria);
			if(rptRptSalesSoList !=null && rptRptSalesSoList.size()>0){
				orderHeaderForNormalVO.setSoOrderNum(rptRptSalesSoList.get(0).getSoOrderNum());//发货单号码
				orderHeaderForNormalVO.setSourceOrder(rptRptSalesSoList.get(0).getSourceOrder()); //来源订单号
			}
			odForNormalList = orderDetailsForNormalMybatisDao.queryByOrderNo1(orderHeaderForNormalVO.getOrderno());
			for (OrderDetailsForNormal docOrderDetail:odForNormalList){
				qtyshipped = docOrderDetail.getQty()+qtyshipped;//件数
				qtyshippedEach = docOrderDetail.getQtyeach()+qtyshippedEach;//数量
			}
			orderHeaderForNormalVO.setQtyshipped(qtyshipped);
			orderHeaderForNormalVO.setQtyshippedEach(qtyshippedEach);
			//产品线
			if(odForNormalList != null && odForNormalList.size()>0){
				productLineList = productLineMybatisDao.queryByDocAsn(odForNormalList.get(0).getCustomerid(),odForNormalList.get(0).getSku());
				if(productLineList !=null ){
					orderHeaderForNormalVO.setPname(productLineList.getName());
				}
			}
			//货主
			basCustomer = basCustomerMybatisDao.queryByIdType(orderHeaderForNormalVO.getCustomerid(), Constant.CODE_CUS_TYP_OW);
			if (basCustomer != null) {
				orderHeaderForNormalVO.setCustomerIdRef(basCustomer.getDescrC());
			} else {
				orderHeaderForNormalVO.setCustomerIdRef(" ");
			}
			orderHeaderForNormalVOList.add(orderHeaderForNormalVO);
		}
		datagrid.setTotal((long) orderHeaderForNormalMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(orderHeaderForNormalVOList);
		return datagrid;
	}
	public void exportSalesSoListDataToExcel(HttpServletResponse response, OrderHeaderForNormalQuery form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {

			List<OrderHeaderForNormal> orderHeaderForNormalList = new ArrayList<OrderHeaderForNormal>();
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setOrderByClause("orderno desc");
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankRptSalesSoList();
			// excel的sheetName
			String sheetName = "销售出库单列表";
			// excel要导出的数据
			List<OrderHeaderForNormal> rptRptSalesSoList = orderHeaderForNormalMybatisDao.queryByList(mybatisCriteria);
			// 导出
			if (rptRptSalesSoList== null || rptRptSalesSoList.size() == 0) {
				System.out.println("题库为空");
			}else {
				//发运方式 ZT BK LY 暂缓
				for (OrderHeaderForNormal orderHeaderForNormal:rptRptSalesSoList){
					Map<String, Object> param = new HashMap<>();
					param.put("codeid", "EXP_TYP");
					param.put("code", orderHeaderForNormal.getRoute());
					BasCodes bascodes = basCodesMybatisDao.queryById(param);
					if(bascodes!=null){
						orderHeaderForNormal.setRoute(bascodes.getCodenameC());
					}
					if(rptRptSalesSoList!=null){
						orderHeaderForNormalList.add(orderHeaderForNormal);
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(orderHeaderForNormalList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankRptSalesSoList() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("customerid", "客户编码");
		superClassMap.put("orderno", "SO编号");
		superClassMap.put("orderTypeName", "订单类型");
		superClassMap.put("orderStatusName", "订单状态");
		superClassMap.put("soreference1", "客户单号");
		superClassMap.put("soreference2", "定向入库单号");
		superClassMap.put("cAddress4", "快递单号");
		superClassMap.put("notes", "备注");
		superClassMap.put("edisendflag", "回传标识");
		superClassMap.put("consigneeid", "公司抬头");
		superClassMap.put("cAddress1", "收货地址");
		superClassMap.put("cTel1", "联系方式");
		superClassMap.put("addtime", "创建时间");
		superClassMap.put("addwho", "创建人");
		superClassMap.put("edittime", "编辑时间");
		superClassMap.put("editwho", "编辑人");
		superClassMap.put("courierComplaint", "快递投诉内容");
		superClassMap.put("udfprintflag2", "拣货单打印次数");
		superClassMap.put("udfprintflag3", "随货单打印次数");

		return superClassMap;
	}





	/**************************************订单装箱清单-按发运单号****************************************/
	public EasyuiDatagrid<RptOrderPackingcartonByOrderNo> getPagedDatagridRptOrderPackingcartonByOrderNo(EasyuiDatagridPager pager, RptOrderPackingcartonByOrderNo query) {
		EasyuiDatagrid<RptOrderPackingcartonByOrderNo> datagrid = new EasyuiDatagrid<RptOrderPackingcartonByOrderNo>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptOrderPackingcartonByOrderNo> rptOrderPackingcartonByOrderNoList= statisticalAnalysisMybatisDao.queryOrderPackingcartonByOrderNo(mybatisCriteria);
		for (RptOrderPackingcartonByOrderNo s: rptOrderPackingcartonByOrderNoList) {
 			//计算数量
			s.setQtyEach(s.getQty() * s.getQty1());
			s.setTraceid1(s.getTraceid().substring(5,10)+s.getTraceid().substring(11,14));
		}
		datagrid.setTotal((long) statisticalAnalysisMybatisDao.queryOrderPackingcartonByOrderNoCount(mybatisCriteria));
		datagrid.setRows(rptOrderPackingcartonByOrderNoList);
		return datagrid;
	}
	public void exportOrderPackingcartonByOrderNoDataToExcel(HttpServletResponse response, RptOrderPackingcartonByOrderNo form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBankRptOrderPackingcartonByOrderNo();
			// excel的sheetName
			String sheetName = "订单装箱清单-按发运单号";
			// excel要导出的数据
			List<RptOrderPackingcartonByOrderNo> rptOrderPackingcartonByOrderNoList = statisticalAnalysisMybatisDao.queryOrderPackingcartonByOrderNo(mybatisCriteria);
			// 导出
			if (rptOrderPackingcartonByOrderNoList== null || rptOrderPackingcartonByOrderNoList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (RptOrderPackingcartonByOrderNo s: rptOrderPackingcartonByOrderNoList) {
					//计算数量
					s.setQtyEach(s.getQty() * s.getQty1());
					//质量状态
					List<EasyuiCombobox> Lotatt10List = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
					for (EasyuiCombobox easyuiCombobox : Lotatt10List)
					{
						//质量状态id对比
						if (s.getLotatt10().equals(easyuiCombobox.getId())) {
							s.setLotatt10(easyuiCombobox.getValue());
							break;
						}
					}
					//是否装箱
					if(s.getPackingflag()!=null){
						if(s.getPackingflag().equals("1")){
							s.setPackingflag("是");
						}else{
							s.setPackingflag("否");
						}
					}
					//双证
					if(s.getLotatt13()!=null){
						if(s.getLotatt13().equals("1")){
							s.setLotatt13("已匹配");
						}else{
							s.setLotatt13("");
						}
					}
					//产品属性
					List<EasyuiCombobox> Lotatt09List = basCodesService.getBy(Constant.CODE_CATALOG_SAMPLEATTR);
					for (EasyuiCombobox easyuiCombobox : Lotatt09List)
					{
						//状态id对比
						if (s.getLotatt09().equals(easyuiCombobox.getId())) {
							s.setLotatt09(easyuiCombobox.getValue());
							break;
						}
					}

				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(rptOrderPackingcartonByOrderNoList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) 	{
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBankRptOrderPackingcartonByOrderNo() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();

		superClassMap.put("orderno", "出库单号");
		superClassMap.put("traceid", "箱号");
		superClassMap.put("packingflag", "是否装箱完成");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("customerid", "货主代码");
		superClassMap.put("shippershortname", "货主简称");
		superClassMap.put("lotatt08Name", "供应商");
		superClassMap.put("sku", "产品代码");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("lotatt06", "注册证号");
		superClassMap.put("skudescrc", "规格/型号");
		superClassMap.put("lotatt04", "生产批号");
		superClassMap.put("lotatt05", "序列号");
		superClassMap.put("lotatt07", "灭菌批号");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("lotatt01", "生产日期");
		superClassMap.put("lotatt02", "有效期/失效期");
		superClassMap.put("lotatt11", "存储条件");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("reservedfield06", "生产许可证号/备案号");
		superClassMap.put("qty", "装箱件数");
		superClassMap.put("qtyEach", "装箱数量");
		superClassMap.put("uom", "单位");
		superClassMap.put("description", "复核说明");
		superClassMap.put("conclusion", "复核结论");
		superClassMap.put("editwho", "复核人");
		superClassMap.put("edittime", "复核时间");
		superClassMap.put("addtime", "创建时间");
		superClassMap.put("addwho", "创建人");
		superClassMap.put("lotatt14", "入库单号");
		superClassMap.put("lotatt09", "产品属性");
		superClassMap.put("lotatt13", "双证");
		superClassMap.put("qty1", "转换率");

		return superClassMap;
	}
}