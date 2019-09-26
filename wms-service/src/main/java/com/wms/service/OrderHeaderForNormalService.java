package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.*;
import com.wms.result.OrderStatusResult;
import com.wms.result.ReceiptResult;
import com.wms.service.importdata.ImportOrderDataService;
import com.wms.service.sfExpress.CallExpressServiceTools;
import com.wms.service.sfExpress.RequestXmlUtil;
import com.wms.service.sfExpress.sfXmlParse.ShunFengResponse;
import com.wms.service.sfExpress.sfXmlParse.XmlHelper;
import com.wms.utils.*;
import com.wms.vo.ActAllocationDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.OrderHeaderForNormalForm;
import com.wms.vo.form.pda.PageForm;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderHeaderForNormalService")
public class OrderHeaderForNormalService extends BaseService {

    static Logger logger = Logger.getLogger(OrderHeaderForNormalService.class.getName());

    private static final String END_POINT = ResourceUtil.geteEndpoint();

    @Autowired
    private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
    @Autowired
    private CommonService commonService;

    @Autowired
    private DocOrderPackingService docOrderPackingService;
    @Autowired
    private BasCustomerService basCustomerService;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private BasPackageService basPackageService;
    @Autowired
    private ActAllocationDetailsMybatisDao actAllocationDetailsMybatisDao;

    //	@Autowired
//	private DocOrderImportMybatisDao docOrderImportMybatisDao;
//
    @Autowired
    private ImportOrderDataService importOrderDataService;
    @Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;
    @Autowired
    private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
    @Autowired
    private DocAsnDoublecMybatisDao docAsnDoublecMybatisDao;
    @Autowired
    private DocAsnCertificateMybatisDao docAsnCertificateMybatisDao;
    @Autowired
    private DocSerialNumRecordMybatisDao docSerialNumRecordMybatisDao;

    @Autowired
    private DocOrderPackingMybatisDao docOrderPackingMybatisDao;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
    @Autowired
    private InvLotLocIdMybatisDao invLotLocIdMybatisDao;

    /**
     * 订单列表显示
     */
    public EasyuiDatagrid<OrderHeaderForNormalVO> getPagedDatagrid(EasyuiDatagridPager pager, OrderHeaderForNormalQuery query) {
        EasyuiDatagrid<OrderHeaderForNormalVO> datagrid = new EasyuiDatagrid<OrderHeaderForNormalVO>();
        query.setWarehouseId(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        mybatisCriteria.setOrderByClause("orderno desc");
        List<OrderHeaderForNormal> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryByList(mybatisCriteria);
        OrderHeaderForNormalVO orderHeaderForNormalVO = null;
        List<OrderHeaderForNormalVO> orderHeaderForNormalVOList = new ArrayList<OrderHeaderForNormalVO>();
        for (OrderHeaderForNormal orderHeaderForNormal : orderHeaderForNormalList) {
            orderHeaderForNormalVO = new OrderHeaderForNormalVO();
            BeanUtils.copyProperties(orderHeaderForNormal, orderHeaderForNormalVO);

            BasCustomer customer = basCustomerService.selectCustomerById(orderHeaderForNormalVO.getConsigneeid(),Constant.CODE_CUS_TYP_CO);
            if(customer!=null){
                orderHeaderForNormalVO.setConsigneename(customer.getDescrC());
            }
            orderHeaderForNormalVOList.add(orderHeaderForNormalVO);
        }
        datagrid.setTotal((long) orderHeaderForNormalMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(orderHeaderForNormalVOList);
        return datagrid;
    }

    public EasyuiDatagrid<ActAllocationDetailsVO> getPageAllocation(EasyuiDatagridPager pager, ActAllocationDetailsQuery query) {
        EasyuiDatagrid<ActAllocationDetailsVO> datagrid = new EasyuiDatagrid<ActAllocationDetailsVO>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<ActAllocationDetails> orderHeaderForNormalList = actAllocationDetailsMybatisDao.queryByList(mybatisCriteria);
        List<ActAllocationDetailsVO> actAllocationDetailsVOList = new ArrayList<>();
        ActAllocationDetailsVO vo = null;
        for (ActAllocationDetails act : orderHeaderForNormalList) {
            vo = new ActAllocationDetailsVO();
            BeanUtils.copyProperties(act, vo);
            BasSku basSku = basSkuService.getSkuInfo(act.getCustomerid(), act.getSku());
            BasPackageQuery packQuery = new BasPackageQuery();
            packQuery.setPackid(basSku.getPackid());
            BasPackage basPackage = basPackageService.queryBasPackBy(packQuery);
            vo.setSkuName(basSku.getReservedfield01());
            vo.setPickName(basPackage.getDescr());
            actAllocationDetailsVOList.add(vo);
        }
        datagrid.setTotal((long) actAllocationDetailsMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(actAllocationDetailsVOList);
        return datagrid;
    }

    /**
     * 新增订单
     */
    public Json add(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
        Json json = new Json();
        //获取新的订单号
		/*Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		orderHeaderForNormalMybatisDao.getIdSequence(map);
		String resultCode = map.get("resultCode").toString();
		String resultNo = map.get("resultNo").toString();*/

        String resultNo = commonService.generateSeq("ORDERNO", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        //if (resultCode.substring(0,3).equals("000")) {
        if (!StringUtils.isEmpty(resultNo)) {
            OrderHeaderForNormal orderHeaderForNormal = new OrderHeaderForNormal();
            BeanUtils.copyProperties(orderHeaderForNormalForm, orderHeaderForNormal);
            orderHeaderForNormal.setOrderno(resultNo);
            orderHeaderForNormal.setOrdertype(orderHeaderForNormal.getOrdertype());
            orderHeaderForNormal.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            orderHeaderForNormal.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
            orderHeaderForNormal.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
            orderHeaderForNormal.setOrdertime(new Date());
            orderHeaderForNormal.setEdittime(new Date());
            orderHeaderForNormal.setEdisendflag(Constant.IS_USE_YES);
            orderHeaderForNormal.setArchiveflag(Constant.IS_USE_YES);
            orderHeaderForNormalMybatisDao.add(orderHeaderForNormal);
            json.setSuccess(true);
            json.setMsg("资料处理成功！");
            json.setObj(orderHeaderForNormal);
            return json;
        } else {
            json.setSuccess(false);
            //json.setMsg(resultCode);
            return json;
        }
    }

    /**
     * 编辑订单
     */
    public Json edit(OrderHeaderForNormalForm orderHeaderForNormalForm) {
        Json json = new Json();
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderHeaderForNormalForm.getOrderno());
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        Date addtime = orderHeaderForNormal.getAddtime();
        BeanUtils.copyProperties(orderHeaderForNormalForm, orderHeaderForNormal);
        orderHeaderForNormal.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
        orderHeaderForNormal.setAddtime(addtime);
        orderHeaderForNormalMybatisDao.update(orderHeaderForNormal);
        json.setSuccess(true);
        json.setMsg("资料处理成功！");
        return json;
    }

    /**
     * 删除订单
     */
    public Json delete(String orderno) {
        Json json = new Json();
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderno);
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        if (orderHeaderForNormal != null) {
            if (orderHeaderForNormal.getSostatus().equals("00") || orderHeaderForNormal.getSostatus().equals("90")) {
                if (orderHeaderForNormal.getAddwho().equals("EDI")) {
                    json.setSuccess(false);
                    json.setMsg("EDI订单,不能删除!");
                    return json;
                } else {
                    orderHeaderForNormalMybatisDao.delete(orderHeaderForNormal);
                    orderDetailsForNormalMybatisDao.orderHeaderdelete(orderno);
                    return Json.success("000");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("当前状态订单,不能删除!");
                return json;
            }
        }
        json.setSuccess(true);
        json.setMsg("资料处理成功！");
        return json;
    }

    /**
     * 分配
     */
    public Json allocation(String orderNo) {
        Json json = new Json();
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderNo);
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        if (orderHeaderForNormal != null) {
            if (orderHeaderForNormal.getSostatus().equals("00") || orderHeaderForNormal.getSostatus().equals("30")) {// || orderHeaderForNormal.getSostatus().equals("40")

                //判断双证/质量合格证
                json = fixCertificateFlag(orderNo);
                if (!json.isSuccess()) return json;

                Map<String, Object> map = new HashMap<>();
                //map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                map.put("orderNo", orderNo);
                map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                //map.put("result", "");
                orderHeaderForNormalMybatisDao.allocationByOrder(map);

                String result = map.get("result").toString();

                if (StringUtil.isNotEmpty(result) && result.equals("000")) {

                    if (orderHeaderForNormal.getOrdertype().equals("DX")) {

                        Json fixReuslt = docOrderPackingService.fixOrderPacking(orderNo);
                        if (!fixReuslt.isSuccess()) {

                            json.setSuccess(false);
                            json.setMsg(fixReuslt.getMsg());
                            return json;
                        }else {

                            json.setSuccess(true);
                            json.setMsg("000");
                            return json;
                        }
                    }else {

                        json.setSuccess(true);
                        json.setMsg("000");
                        return json;
                    }
                }else {

                    json.setSuccess(false);
                    json.setMsg("分配失败");
                    return json;
                }
            } else {
                json.setSuccess(false);
                json.setMsg("当前状态订单,不能操作分配!");
                return json;
            }
        } else {
            json.setSuccess(false);
            json.setMsg("查无出库单数据");
            return json;
        }
    }

    /**
     * 遍历子单查看产品是否需要质量合格证或者双证，
     * 如果没有匹配或者导入的，则不可进行分配操作
     * @param orderno 出库单号
     * @return ~
     */
    private Json fixCertificateFlag(String orderno) {

        Json json = new Json();
        json.setSuccess(true);

        StringBuilder message = new StringBuilder();
        List<OrderDetailsForNormal> docOrderDetailList = orderDetailsForNormalMybatisDao.queryByOrderNo(orderno);
        for (OrderDetailsForNormal docOrderDetail : docOrderDetailList) {

            BasSku basSku = docOrderDetail.getBasSku() == null ? new BasSku() : docOrderDetail.getBasSku();
            if (StringUtil.fixNull(basSku.getSkuGroup7()).equals("1") && StringUtil.isNotEmpty(docOrderDetail.getLotatt05())) {

                InvLotLocId invLotLocId = invLotLocIdMybatisDao.queryByLotatt05(docOrderDetail.getLotatt05());
                if (invLotLocId == null) {

                    json.setSuccess(false);
                    message.append(" ").
                            append("序列号:").
                            append(docOrderDetail.getLotatt05()).
                            append("，无库存;");
                }else if (!invLotLocId.isDoublecflag()) {

                    json.setSuccess(false);
                    message.append(" ").
                            append("序列号:").
                            append(docOrderDetail.getLotatt05()).
                            append("，未匹配双证;");
                }
            }else if (StringUtil.fixNull(basSku.getSkuGroup8()).equals("1") && StringUtil.isNotEmpty(docOrderDetail.getLotatt04())) {

                DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.queryBylotatt04(docOrderDetail.getCustomerid(), docOrderDetail.getSku(), docOrderDetail.getLotatt04());
                if (docAsnCertificate == null) {

                    json.setSuccess(false);
                    message.append(" ").
                            append("生产批号:").
                            append(docOrderDetail.getLotatt04()).
                            append("，未导入质量合格证;");
                }
            }
        }

        json.setMsg(message.toString());
        return json;
    }

    /**
     * 取消分配
     */
    public Json deAllocation(String orderNo) {
        Json json = new Json();
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderNo);
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        if (orderHeaderForNormal != null) {
            if (orderHeaderForNormal.getSostatus().equals("00") || orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {
                Map<String, Object> map = new HashMap<String, Object>();
                //map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                map.put("orderNo", orderNo);
                map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                orderHeaderForNormalMybatisDao.deAllocationByOrder(map);
                String result = map.get("result").toString();
                //删除序列号记录 add by Gizmo 2019-08-22 21:39
                if (result.contains("000")) {
                    docSerialNumRecordMybatisDao.clearRecordByOrderno(orderNo);
                }
                json.setSuccess(true);
                json.setMsg(result);
                return json;
            } else {
                json.setSuccess(true);
                json.setMsg("当前状态订单,不能取消分配!");
                return json;
            }
        } else {
            json.setSuccess(true);
            json.setMsg("000");
            return json;
        }
    }

    /**
     * 拣货/确认复核
     */
    public Json picking(String orderNo) {
        Json json = new Json();
        //
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderNo);
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        if (orderHeaderForNormal != null) {
            //判断订单状态
            if (orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {

                List<DocOrderPacking> docOrderPackingList = docOrderPackingMybatisDao.queryPackageExist(orderNo);
                if (docOrderPackingList.size() > 0) {
                    json.setSuccess(false);
                    json.setMsg("此出库单已开始装箱，不可一键拣货");
                    return json;
                }

                List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByAllocationDetailsId(orderNo);
                if (allocationDetailsIdList != null && allocationDetailsIdList.size() > 0) {
                    for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        //map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                        map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
                        map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                        orderHeaderForNormalMybatisDao.pickingByOrder(map);
                        String pickResult = map.get("result").toString();
                        if (pickResult != null && pickResult.length() > 0) {
                            if (pickResult.equals("000")) {
                                continue;
                            } else {
                                json.setSuccess(false);
                                json.setMsg("出库处理失败：" + pickResult);
                                return json;
                            }
                        } else {
                            json.setSuccess(false);
                            json.setMsg("出库处理失败：订单数据异常！");
                            return json;
                        }
                    }
                    return Json.success("拣货成功");
                } else {
                    return Json.success("拣货失败,查询不到对应的分配明细");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("拣货处理失败：当前状态订单,不能操作拣货!");
                return json;
            }
        } else {
            json.setSuccess(false);
            json.setMsg("拣货处理失败：订单数据异常！");
            return json;
        }
    }

    /**
     * 取消拣货/取消复核
     */
    public Json unPicking(String orderNo) {
        Json json = new Json();
        //
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderNo);
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        if (orderHeaderForNormal != null) {
            int sosStatus = 0;
            sosStatus = Integer.parseInt(orderHeaderForNormal.getSostatus());
            //判断订单状态
            if (sosStatus <= 40 || sosStatus > 60) {
                json.setSuccess(false);
                json.setMsg("取消拣货处理失败：当前状态订单,不能操作取消拣货!");
                return json;
            } else {
                List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByUnAllocationDetailsId(orderNo);
                if (allocationDetailsIdList != null) {
                    for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        //map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                        map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
                        map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                        orderHeaderForNormalMybatisDao.unPickingByOrder(map);
                        String pickResult = map.get("result").toString();
                        if (pickResult != null && pickResult.length() > 0) {
                            if (pickResult.equals("000")) {
                                continue;
                            } else {
                                json.setSuccess(false);
                                json.setMsg("取消拣货处理失败：" + pickResult);
                                return json;
                            }
                        } else {
                            json.setSuccess(false);
                            json.setMsg("取消拣货处理失败：订单数据异常！");
                            return json;
                        }
                    }
                    //删除序列号记录 add by Gizmo 2019-08-29
                    docSerialNumRecordMybatisDao.clearRecordByOrderno(orderNo);
                    return Json.success("取消拣货成功");
                } else {
                    return Json.success("取消拣货失败");
                }
            }
        } else {
            json.setSuccess(false);
            json.setMsg("取消拣货处理失败：订单数据异常！");
            return json;
        }
    }

    /**
     * 发运
     */
    public Json shipment(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
        Json json = new Json();
        //
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderHeaderForNormalForm.getOrderno());
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        if (orderHeaderForNormal != null) {
            //判断订单状态
            if (orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {
                //
//				try {
//					StockInXmlVo stockInXmlVo = new StockInXmlVo();
//					stockInXmlVo.setOrderCode(orderHeaderForNormal.getOrderCode());
//					stockInXmlVo.setOrderStatus(28);
//					String xmldata = JaxbUtil.convertToXml(stockInXmlVo, false);
//					logger.error("orderHeaderForNormalService-推送：" + xmldata);
//					ResponseVO responseVO = new ServiceControllerProxy(END_POINT).updateOrder(xmldata);
//					logger.error("orderHeaderForNormalService-接收：" + responseVO.getSuccess());
//					if (responseVO.getSuccess() == false) {
//						json.setSuccess(false);
//						json.setMsg("出库处理失败：订单尚未配载！");
//						return json;
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                //操作拣货
                List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByAllocationDetailsId(orderHeaderForNormalForm.getOrderno());
                if (allocationDetailsIdList != null) {
                    /*如果订单发运成功那么就进行顺丰下单  下单报文*/
                   /* String requestXml = RequestXmlUtil.getOrderServiceRequestXml(orderHeaderForNormalForm);
                    //响应报文
                    String callRequestXml = CallExpressServiceTools.callSfExpressServiceByCSIM(requestXml);

                     String callXml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>OK</Head><Body><OrderResponse filter_result=\"2\" destcode=\"755\" mailno=\"SF1011547784874\" return_tracking_no=\"SF1060174366080\" origincode=\"755\" orderid=\"SFKD-20160219000029\"><rls_info rls_errormsg=\"SF1011547784874:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"SF1011547784874\" sourceTransferCode=\"755W\" sourceCityCode=\"755\" sourceDeptCode=\"755AP\" sourceTeamCode=\"028\" destCityCode=\"755\" destDeptCode=\"755FG\" destDeptCodeMapping=\"755W\" destTeamCode=\"016\" destTransferCode=\"755W\" destRouteLabel=\"755W-755FG\" proName=\"顺丰标快\" cargoTypeCode=\"C201\" limitTypeCode=\"T4\" expressTypeCode=\"B1\" codingMapping=\"C2\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'755W','k2':'755FG','k3':'016','k4':'T4','k5':'SF1011547784874','k6':'','k7':'613f0c59'}\" proCode=\"T4\" printIcon=\"00000000\" checkCode=\"613f0c59\"/></rls_info></OrderResponse></Body></Response>";
                    String errXml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>ERR</Head><ERROR code=\"8119\">月结卡号不存在或已失效</ERROR></Response>";
                     //System.err.println("响应报文"+callXml);
                    //解析响应报文
                    ShunFengResponse shunFengResponse = XmlHelper.xmlToBeanForSF(callRequestXml);
                    if(!shunFengResponse.isResultFlag()){
                        json.setSuccess(false);
                        json.setMsg("顺丰下单失败,原因:"+shunFengResponse.getErrorMsg());
                        return json;
                    }
                    System.err.println("//解析响应报文" + shunFengResponse.toString());
                    //解析后修改到表中
                    OrderHeaderForNormal orderHeaderForNormalSf = new OrderHeaderForNormal();
                    orderHeaderForNormalSf.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
                    orderHeaderForNormalSf.setEdittime(new Date());
                    orderHeaderForNormalSf.setOrderno(orderHeaderForNormalForm.getOrderno());
                    //运单号
                    orderHeaderForNormalSf.setCAddress4(shunFengResponse.getOrderResponse().getMailNo());
                    //签回单号
                    orderHeaderForNormalSf.setCAddress3(shunFengResponse.getOrderResponse().getReturnTrackingNo());

                    orderHeaderForNormalMybatisDao.updateBySelective(orderHeaderForNormalSf);*/
                     /*顺丰下单end*/
                    for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                        map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
                        map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                        orderHeaderForNormalMybatisDao.pickingByOrder(map);
                        String pickResult = map.get("result").toString();
                        if (pickResult != null && pickResult.length() > 0) {
                            if (pickResult.equals("000")) {
                                continue;
                            } else {
                                /*//取消顺丰下单
                                ShunFengResponse responseCancel=CancelShunFengOrder(shunFengResponse.getOrderResponse().getMailNo());
                                if(!responseCancel.isResultFlag()){
                                    json.setSuccess(false);
                                    String error="出库处理失败：" + pickResult+"/br";
                                    json.setMsg(error+"顺丰单号:"+shunFengResponse.getOrderResponse().getMailNo()+"取消失败,原因:"+shunFengResponse.getErrorMsg());
                                    return json;
                                }else {*/
                                    json.setSuccess(false);
                                    json.setMsg("出库处理失败：" + pickResult);
                                    return json;
                              /*  }*/
                            }
                        } else {
                           /* //取消顺丰下单
                            ShunFengResponse responseCancel=CancelShunFengOrder(shunFengResponse.getOrderResponse().getMailNo());
                            if(!responseCancel.isResultFlag()){
                                json.setSuccess(false);
                                String error="出库处理失败：订单数据异常！/br";
                                json.setMsg(error+"顺丰单号:"+shunFengResponse.getOrderResponse().getMailNo()+"取消失败,原因:"+shunFengResponse.getErrorMsg());
                                return json;
                            }else {*/
                              json.setSuccess(false);
                               json.setMsg("出库处理失败：订单数据异常！");
                               return json;
                           /* }*/
                        }
                    }
                    //操作发运
                    Map<String, Object> map = new HashMap<String, Object>();
                    //map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                    map.put("orderNo", orderHeaderForNormalForm.getOrderno());
                    map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                    orderHeaderForNormalMybatisDao.shipmentByOrder(map);
                    String shippmentResult = map.get("result").toString();
                    if (shippmentResult != null && shippmentResult.length() > 0) {
                        if (shippmentResult.equals("000")) {
                            orderHeaderForNormalQuery.setCurrentTime(new Date());
                            orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
                            //删除序列号出库记录 edit by Gizmo 2019-08-29 出库不删除序列号记录
//                            docSerialNumRecordMybatisDao.clearRecordByOrderno(orderHeaderForNormalForm.getOrderno());
                            json.setSuccess(true);
                            json.setMsg("出库处理成功！");
                            json.setObj(orderHeaderForNormal);
                            return json;
                        } else {
                           /* //取消顺丰下单
                            ShunFengResponse responseCancel=CancelShunFengOrder(shunFengResponse.getOrderResponse().getMailNo());
                            if(!responseCancel.isResultFlag()){
                                json.setSuccess(false);
                                String error="出库处理失败：" + shippmentResult+"/br";
                                json.setMsg(error+"顺丰单号:"+shunFengResponse.getOrderResponse().getMailNo()+"取消失败,原因:"+shunFengResponse.getErrorMsg());
                                return json;
                            }else {*/
                               json.setSuccess(false);
                               json.setMsg("出库处理失败：" + shippmentResult);
                               return json;
                           /* }*/
                        }
                    } else {
                       /* //取消顺丰下单
                        ShunFengResponse responseCancel=CancelShunFengOrder(shunFengResponse.getOrderResponse().getMailNo());
                        if(!responseCancel.isResultFlag()){
                            json.setSuccess(false);
                            String error="出库处理失败：订单数据异常！/br";
                            json.setMsg(error+"顺丰单号:"+shunFengResponse.getOrderResponse().getMailNo()+"取消失败,原因:"+shunFengResponse.getErrorMsg());
                            return json;
                        }else {*/
                            json.setSuccess(false);
                            json.setMsg("出库处理失败：订单数据异常！");
                            return json;
                       /* }*/
                    }
                } else {
                    json.setSuccess(false);
                    json.setMsg("出库处理失败：订单数据异常！");
                    return json;
                }
            } else if (orderHeaderForNormal.getSostatus().equals("50") ||
                    orderHeaderForNormal.getSostatus().equals("60") ||
                    orderHeaderForNormal.getSostatus().equals("62") ||
                    orderHeaderForNormal.getSostatus().equals("63")) {
                /*如果订单发运成功那么就进行顺丰下单  下单报文*/
               /* String requestXml = RequestXmlUtil.getOrderServiceRequestXml(orderHeaderForNormalForm);
                //响应报文
                String callRequestXml = CallExpressServiceTools.callSfExpressServiceByCSIM(requestXml);
                String callXml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>OK</Head><Body><OrderResponse filter_result=\"2\" destcode=\"755\" mailno=\"SF1011547784874\" return_tracking_no=\"SF1060174366080\" origincode=\"755\" orderid=\"SFKD-20160219000029\"><rls_info rls_errormsg=\"SF1011547784874:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"SF1011547784874\" sourceTransferCode=\"755W\" sourceCityCode=\"755\" sourceDeptCode=\"755AP\" sourceTeamCode=\"028\" destCityCode=\"755\" destDeptCode=\"755FG\" destDeptCodeMapping=\"755W\" destTeamCode=\"016\" destTransferCode=\"755W\" destRouteLabel=\"755W-755FG\" proName=\"顺丰标快\" cargoTypeCode=\"C201\" limitTypeCode=\"T4\" expressTypeCode=\"B1\" codingMapping=\"C2\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'755W','k2':'755FG','k3':'016','k4':'T4','k5':'SF1011547784874','k6':'','k7':'613f0c59'}\" proCode=\"T4\" printIcon=\"00000000\" checkCode=\"613f0c59\"/></rls_info></OrderResponse></Body></Response>";
                String errXml = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>ERR</Head><ERROR code=\"8119\">月结卡号不存在或已失效</ERROR></Response>";
                //System.err.println("响应报文"+callXml);
                //解析响应报文
                ShunFengResponse shunFengResponse = XmlHelper.xmlToBeanForSF(callRequestXml);
                if(!shunFengResponse.isResultFlag()){
                    json.setSuccess(false);
                    json.setMsg("顺丰下单失败:"+shunFengResponse.getErrorMsg());
                    return json;
                }
                System.err.println("//解析响应报文" + shunFengResponse.toString());
                //解析后修改到表中
                OrderHeaderForNormal orderHeaderForNormalSf = new OrderHeaderForNormal();
                orderHeaderForNormalSf.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
                orderHeaderForNormalSf.setEdittime(new Date());
                orderHeaderForNormalSf.setOrderno(orderHeaderForNormalForm.getOrderno());
                //运单号
                orderHeaderForNormalSf.setCAddress4(shunFengResponse.getOrderResponse().getMailNo());
                //签回单号
                orderHeaderForNormalSf.setCAddress3(shunFengResponse.getOrderResponse().getReturnTrackingNo());

                orderHeaderForNormalMybatisDao.updateBySelective(orderHeaderForNormalSf);*/
                /*顺丰下单end*/
                //
//				try {
//					StockInXmlVo stockInXmlVo = new StockInXmlVo();
//					stockInXmlVo.setOrderCode(orderHeaderForNormal.getOrderCode());
//					stockInXmlVo.setOrderStatus(28);
//					String xmldata = JaxbUtil.convertToXml(stockInXmlVo, false);
//					logger.error("orderHeaderForNormalService-推送：" + xmldata);
//					ResponseVO responseVO = new ServiceControllerProxy(END_POINT).updateOrder(xmldata);
//					logger.error("orderHeaderForNormalService-接收：" + responseVO.getSuccess());
//					if (responseVO.getSuccess() == false) {
//						json.setSuccess(false);
//						json.setMsg("出库处理失败：订单尚未配载！");
//						return json;
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                //拣货/装箱状态订单直接操作发运
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                map.put("orderNo", orderHeaderForNormalForm.getOrderno());
                map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                orderHeaderForNormalMybatisDao.shipmentByOrder(map);
                String shippmentResult = map.get("result").toString();
                if (shippmentResult != null && shippmentResult.length() > 0) {
                    if (shippmentResult.equals("000")) {
                        orderHeaderForNormalQuery.setCurrentTime(new Date());
                        orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
                        //删除序列号出库记录 edit by Gizmo 2019-08-29 出库不删除序列号记录
//                        docSerialNumRecordMybatisDao.clearRecordByOrderno(orderHeaderForNormalForm.getOrderno());
                        json.setSuccess(true);
                        json.setMsg("出库处理成功！");
                        json.setObj(orderHeaderForNormal);
                        return json;
                    } else {
                        //取消顺丰下单
                       /* ShunFengResponse responseCancel=CancelShunFengOrder(shunFengResponse.getOrderResponse().getMailNo());
                        if(!responseCancel.isResultFlag()){
                            json.setSuccess(false);
                            String error="出库处理失败：" + shippmentResult+"/br";
                            json.setMsg(error+"顺丰单号:"+shunFengResponse.getOrderResponse().getMailNo()+"取消失败,原因:"+shunFengResponse.getErrorMsg());
                            return json;
                        }else {*/
                          json.setSuccess(false);
                          json.setMsg("出库处理失败：" + shippmentResult);
                          return json;
                       /* }*/
                    }
                } else {
                   /* //取消顺丰下单
                    ShunFengResponse responseCancel=CancelShunFengOrder(shunFengResponse.getOrderResponse().getMailNo());
                    if(!responseCancel.isResultFlag()){
                        json.setSuccess(false);
                        String error="出库处理失败：订单数据异常！/br";
                        json.setMsg(error+"顺丰单号:"+shunFengResponse.getOrderResponse().getMailNo()+"取消失败,原因:"+shunFengResponse.getErrorMsg());
                        return json;
                    }else {*/
                      json.setSuccess(false);
                      json.setMsg("出库处理失败：订单数据异常！");
                      return json;
                   /* }*/
                }
            } else {
                json.setSuccess(false);
                json.setMsg("出库处理失败：当前状态订单,不能操作出库!");
                return json;
            }
        } else {
            json.setSuccess(false);
            json.setMsg("出库处理失败：订单数据异常！");
            return json;
        }
    }
 /*取消顺丰下单*/
    public ShunFengResponse CancelShunFengOrder(String Order){
        String cancelXml = CallExpressServiceTools.callSfExpressServiceByCSIM(RequestXmlUtil.getOrderCancelServiceRequestXml(Order));
        ShunFengResponse shunFengResponseCanel = XmlHelper.xmlToBeanForSF(cancelXml);
        return shunFengResponseCanel;
    }

    /**
     * 取消订单
     */
    public Json cancel(String orderNo) {
        Json json = new Json();
        OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
        orderHeaderForNormalQuery.setOrderno(orderNo);
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
        if (orderHeaderForNormal != null) {
            if (orderHeaderForNormal.getSostatus().equals("00")) {
                //创建状态订单直接操作取消
                Map<String, Object> map = new HashMap<String, Object>();
                //map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                map.put("orderNo", orderNo);
                map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                orderHeaderForNormalMybatisDao.cancelByOrder(map);
                String cancelResult = map.get("result").toString();
                if (cancelResult != null && cancelResult.length() > 0) {
                    if (cancelResult.equals("000")) {
                        orderHeaderForNormalQuery.setCurrentTime(new Date());
                        orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
                        json.setSuccess(true);
                        json.setMsg("出库取消成功！");
                        json.setObj(orderHeaderForNormal);
                        return json;
                    } else {
                        json.setSuccess(false);
                        json.setMsg("出库取消失败：" + cancelResult);
                        return json;
                    }
                } else {
                    json.setSuccess(false);
                    json.setMsg("出库取消失败！");
                    return json;
                }
            } else if (orderHeaderForNormal.getSostatus().equals("30") ||
                    orderHeaderForNormal.getSostatus().equals("40")) {
                //分配状态订单先取消分配再取消订单
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                map.put("orderNo", orderNo);
                map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                orderHeaderForNormalMybatisDao.deAllocationByOrder(map);
                String allocationResult = map.get("result").toString();
                if (allocationResult != null && allocationResult.length() > 0) {
                    if (allocationResult.equals("000")) {
                        Map<String, Object> cancelMap = new HashMap<String, Object>();
                        cancelMap.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                        cancelMap.put("orderNo", orderNo);
                        cancelMap.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                        orderHeaderForNormalMybatisDao.cancelByOrder(map);
                        String cancelResult = map.get("result").toString();
                        if (cancelResult != null && cancelResult.length() > 0) {
                            if (cancelResult.equals("000")) {
                                orderHeaderForNormalQuery.setCurrentTime(new Date());
                                orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
                                json.setSuccess(true);
                                json.setMsg("出库取消成功！");
                                json.setObj(orderHeaderForNormal);
                                return json;
                            } else {
                                json.setSuccess(false);
                                json.setMsg("出库取消失败：" + cancelResult);
                                return json;
                            }
                        } else {
                            json.setSuccess(false);
                            json.setMsg("出库取消失败！");
                            return json;
                        }
                    } else {
                        json.setSuccess(false);
                        json.setMsg("出库取消失败：" + allocationResult);
                        return json;
                    }
                } else {
                    json.setSuccess(false);
                    json.setMsg("出库取消失败！");
                    return json;
                }
            } else if (orderHeaderForNormal.getSostatus().equals("50") ||
                    orderHeaderForNormal.getSostatus().equals("60") ||
                    orderHeaderForNormal.getSostatus().equals("62") ||
                    orderHeaderForNormal.getSostatus().equals("63")) {
                //拣货/装箱状态订单先取消拣货再取消分配最后取消订单
                List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByUnAllocationDetailsId(orderNo);
                if (allocationDetailsIdList != null) {
                    //取消拣货
                    for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                        map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
                        map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                        orderHeaderForNormalMybatisDao.unPickingByOrder(map);
                        String pickResult = map.get("result").toString();
                        if (pickResult != null && pickResult.length() > 0) {
                            if (pickResult.equals("000")) {
                                continue;
                            } else {
                                json.setSuccess(false);
                                json.setMsg("出库取消失败：" + pickResult);
                                return json;
                            }
                        } else {
                            json.setSuccess(false);
                            json.setMsg("出库取消失败！");
                            return json;
                        }
                    }
                    //取消分配
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                    map.put("orderNo", orderNo);
                    map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                    orderHeaderForNormalMybatisDao.deAllocationByOrder(map);
                    String allocationResult = map.get("result").toString();
                    if (allocationResult != null && allocationResult.length() > 0) {
                        if (allocationResult.equals("000")) {
                            //取消订单
                            Map<String, Object> cancelMap = new HashMap<String, Object>();
                            cancelMap.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                            cancelMap.put("orderNo", orderNo);
                            cancelMap.put("userId", SfcUserLoginUtil.getLoginUser().getId());
                            orderHeaderForNormalMybatisDao.cancelByOrder(map);
                            String cancelResult = map.get("result").toString();
                            if (cancelResult != null && cancelResult.length() > 0) {
                                if (cancelResult.equals("000")) {
                                    orderHeaderForNormalQuery.setCurrentTime(new Date());
                                    orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
                                    json.setSuccess(true);
                                    json.setMsg("出库取消成功！");
                                    json.setObj(orderHeaderForNormal);
                                    return json;
                                } else {
                                    json.setSuccess(false);
                                    json.setMsg("出库取消失败：" + cancelResult);
                                    return json;
                                }
                            } else {
                                json.setSuccess(false);
                                json.setMsg("出库取消失败！");
                                return json;
                            }
                        } else {
                            json.setSuccess(false);
                            json.setMsg("出库取消失败：" + allocationResult);
                            return json;
                        }
                    } else {
                        json.setSuccess(false);
                        json.setMsg("出库取消失败！");
                        return json;
                    }
                } else {
                    json.setSuccess(false);
                    json.setMsg("出库取消失败！");
                    return json;
                }
            } else {
                json.setSuccess(false);
                json.setMsg("出库取消失败：当前状态订单,不能操作取消！");
                return json;
            }
        } else {
            json.setSuccess(false);
            json.setMsg("出库取消成功！");
            return json;
        }
    }

    public Json importExcelData(MultipartHttpServletRequest mhsr) {
        Json json = null;
        MultipartFile excelFile = mhsr.getFile("uploadData");
        if (excelFile != null && excelFile.getSize() > 0) {
            json = importOrderDataService.importExcelData(excelFile);
        }
        return json;
    }

    public void exportTemplate(HttpServletResponse response, String token) {
        try (OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
            File file = new File(ResourceUtil.getImportRootPath("order_template.xls"));
            response.reset();
            Cookie cookie = new Cookie("downloadToken", token);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            response.setContentType(ContentTypeEnum.stream.getContentType());
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
            response.addHeader("Content-Length", "" + file.length());

            try (InputStream fis = new BufferedInputStream(new FileInputStream(file))) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                toClient.write(buffer);
                toClient.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportPickingPdf(HttpServletResponse response, String orderNo) {
        StringBuilder sb = new StringBuilder();
        try (OutputStream os = response.getOutputStream()) {
            sb.append("inline; filename=")
                    .append(URLEncoder.encode("拣货单PDF", "UTF-8"))
                    .append(".pdf");
            response.setHeader("Content-disposition", sb.toString());
            sb.setLength(0);
            response.setContentType(ContentTypeEnum.pdf.getContentType());

            Document document = null;
            AcroFields form = null;
            PdfStamper stamper = null;
            PdfImportedPage page = null;
            ByteArrayOutputStream baos = null;

            if (StringUtils.isNotEmpty(orderNo)) {
                document = new Document(PDFUtil.getTemplate("wms_picking_jhck.pdf").getPageSize(1));
                PdfCopy pdfCopy = new PdfCopy(document, os);
                document.open();

                String[] orderNos = orderNo.split(",");

                for(String s : orderNos){
                    int totalNum = 0;
                    List<OrderDetailsForNormal> detailsList = null;
                    int row = 7;
                    int pageSize = 0;
                    int totalQtyE = 0;
                    int totalQty = 0;

                    detailsList = new ArrayList<OrderDetailsForNormal>();
                    OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
                    orderHeaderForNormalQuery.setOrderno(s);
                    OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryByPickingList(orderHeaderForNormalQuery);
                    if (orderHeaderForNormal == null) {
                        return;
                    }

                    if (orderHeaderForNormal.getOrderDetailsForNormalList() == null) {
                        return;
                    }
                    for (OrderDetailsForNormal orderDetails : orderHeaderForNormal.getOrderDetailsForNormalList()) {
                        totalNum++;
                        detailsList.add(orderDetails);
                    }

                    pageSize = (int) Math.ceil((double) totalNum / row);

                    for (int i = 0; i < pageSize; i++) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        baos = new ByteArrayOutputStream();
                        stamper = new PdfStamper(PDFUtil.getTemplate("wms_picking_jhck.pdf"), baos);
                        form = stamper.getAcroFields();
                        form.setField("orderNo", orderHeaderForNormal.getOrderno());
                        //basCustomerService.selectCustomerById(orderHeaderForNormal.getCustomerId(), Constant.);
                        form.setField("expectedShipmentTime", DateUtil.format(orderHeaderForNormal.getExpectedshipmenttime1(), "yyyy-MM-dd"));
                        form.setField("carrierName", orderHeaderForNormal.getCarriername());
                        form.setField("consigneeName", orderHeaderForNormal.getConsigneename());
                        form.setField("cContact", orderHeaderForNormal.getCContact());
                        form.setField("userdefine1", orderHeaderForNormal.getUserdefine1());
                        form.setField("cAddress1", orderHeaderForNormal.getCAddress1());
                        form.setField("c_Tel1", orderHeaderForNormal.getCTel1());
                        form.setField("userdefine2", orderHeaderForNormal.getUserdefine2());
                        form.setField("sOReference1", orderHeaderForNormal.getOrderCode());
                        //form.setField("notes", orderHeaderForNormal.getNotes());
                        form.setField("page", "第" + (i + 1) + "页,共" + pageSize + "页");

                        String note = "";
                        for (int j = 0; j < row; j++) {
                            if (totalNum > (row * i + j)) {
                                BasSku basSku = basSkuService.getSkuInfo(orderHeaderForNormal.getCustomerid(), detailsList.get(row * i + j).getSku());

                                //获取冷链标志
                                if (StringUtils.isEmpty(note)) {
                                    if (!StringUtils.isEmpty(basSku.getReservedfield07())) {
                                        switch (basSku.getReservedfield07()) {
                                            case "LD":
                                                note = "冷冻";
                                                break;
                                            case "FLL":
                                                note = "非冷链";
                                                break;
                                            case "LC":
                                                note = "冷藏";
                                                break;
                                        }
                                    }
                                }

                                form.setField("lineNo." + (j), (j+1)+"");
                                form.setField("location." + (j), detailsList.get(row * i + j).getLocation());
                                form.setField("sku." + (j), detailsList.get(row * i + j).getSku());
                                form.setField("skuN." + (j), detailsList.get(row * i + j).getSkuName());
                                form.setField("regNo." + (j), basSku.getReservedfield03());
                                form.setField("desc." + (j), basSku.getDescrE());
                                form.setField("batchNo." + (j), detailsList.get(row * i + j).getLotatt04());
                                form.setField("seriNo." + (j), detailsList.get(row * i + j).getLotatt05());
                                form.setField("ill." + (j), detailsList.get(row * i + j).getLotatt01());
                                form.setField("lot01." + (j), detailsList.get(row * i + j).getLotatt02());
                                form.setField("qtyE." + (j), doubleTrans(detailsList.get(row * i + j).getQtyallocated()));
                                form.setField("uom." + (j), doubleTrans(detailsList.get(row * i + j).getQtyallocatedEach()));
                                form.setField("qty." + (j), "");
                                form.setField("double." + (j), getYesNo(basSku.getSkuGroup7()));
                                form.setField("card." + (j), getYesNo(basSku.getSkuGroup2()));
                                form.setField("report." + (j), getYesNo(basSku.getSkuGroup8()));
                                form.setField("remark." + (j), "");
                                totalQtyE += detailsList.get(row * i + j).getQtyallocated();
                                totalQty += detailsList.get(row * i + j).getQtyallocatedEach();
                            }
                        }
                        if(orderHeaderForNormal.getNotes() == null){
                            form.setField("notes",note);
                        }else{
                            form.setField("notes", orderHeaderForNormal.getNotes() +"    "+note);
                        }
                    }
                    form.setField("sumqtyPage","合计(数量:"+totalQtyE+"");
                    form.setField("sumqty","件数:"+totalQty+")");
                    form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(orderHeaderForNormal.getOrderno(), 800)));
                    stamper.setFormFlattening(true);
                    stamper.close();
                    page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
                    pdfCopy.addPage(page);
                }
                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//	public void exportReceiptPdf(HttpServletResponse response, String orderNo) {
//		StringBuilder sb = new StringBuilder();
//		try (OutputStream os = response.getOutputStream()){
//			sb.append("inline; filename=")
//			  .append(URLEncoder.encode("拣货单PDF","UTF-8"))
//			  .append(".pdf");
//			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
//			response.setContentType(ContentTypeEnum.pdf.getContentType());
//
//			Document document = null;
//			AcroFields form = null;
//			PdfStamper stamper = null;
//			PdfImportedPage page = null;
//			ByteArrayOutputStream baos = null;
//
//			if (StringUtils.isNotEmpty(orderNo)) {
//
//				OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
//				orderHeaderForNormalQuery.setOrderNo(orderNo);
//
//				OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryPrintTemplate(orderHeaderForNormalQuery);
//
//				if (orderHeaderForNormal.getPrintTemplate() != null && orderHeaderForNormal.getPrintTemplate().length() > 0) {
//					//贝业签收单打印模板
//					if (orderHeaderForNormal.getPrintTemplate().equals("wms_receipt_beiye.pdf")) {
//
//
//						document = new Document(PDFUtil.getTemplate("wms_receipt_beiye.pdf").getPageSize(1));
//						PdfCopy pdfCopy = new PdfCopy(document, os);
//						document.open();
//
//						int totalNum = 0;
//						int row = 10;
//						int pageSize = 0;
//						DocOrderImportQuery docOrderImportQuery = new DocOrderImportQuery();
//						docOrderImportQuery.setOrderNo(orderNo);
//
//						DocReceiptHeader docReceiptHeader = docOrderImportMybatisDao.queryReceiptByBeiYe(docOrderImportQuery);
//						List<DocReceiptDetails> docReceiptDetailsList = new ArrayList<DocReceiptDetails>();
//
//						for(DocReceiptDetails docReceiptDetails : docReceiptHeader.getDocReceiptDetailsList()){
//							totalNum++;
//							docReceiptDetailsList.add(docReceiptDetails);
//						}
//
//						pageSize = (int)Math.ceil((double)totalNum / row);
//						for(int i = 0 ; i < pageSize ; i++){
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//							baos = new ByteArrayOutputStream();
//							stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt_beiye.pdf"), baos);
//							form = stamper.getAcroFields();
//							form.setField("customerOrderCode", docReceiptHeader.getCustomerOrderCode() == null ? "" : docReceiptHeader.getCustomerOrderCode());
//							form.setField("externalOrderCode", docReceiptHeader.getExternalOrderCode() == null ? "" : docReceiptHeader.getExternalOrderCode());
//							form.setField("orderDate", sdf.format(new Date()));
//							form.setField("city", docReceiptHeader.getCity() == null ? "" : docReceiptHeader.getCity());
//							form.setField("name", docReceiptHeader.getName() == null ? "" : docReceiptHeader.getName());
//							form.setField("address", docReceiptHeader.getAddress() == null ? "" : docReceiptHeader.getAddress());
//							form.setField("tel", docReceiptHeader.getTel() == null ? "" : docReceiptHeader.getTel());
//							for(int j = 0 ; j < row ; j++){
//								if(totalNum > (row * i + j)){
//									form.setField("seq"+(j+1), String.valueOf(j+1));
//									form.setField("itemCode"+(j+1), docReceiptDetailsList.get(row * i + j).getItemCode());
//									form.setField("itemName"+(j+1), docReceiptDetailsList.get(row * i + j).getItemName());
//									form.setField("cartons"+(j+1), docReceiptDetailsList.get(row * i + j).getQty());
//									form.setField("quantity"+(j+1), docReceiptDetailsList.get(row * i + j).getQty());
//									form.setField("lotnum"+(j+1), "*");
//								}
//							}
//							stamper.setFormFlattening(true);
//							stamper.close();
//							page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
//							pdfCopy.addPage(page);
//						}
//						document.close();
//					}
//				} else {
//
//					document = new Document(PDFUtil.getTemplate("wms_receipt.pdf").getPageSize(1));
//					PdfCopy pdfCopy = new PdfCopy(document, os);
//					document.open();
//
//					int totalNum = 0;
//					int row = 10;
//					int pageSize = 0;
//					List<OrderDetailsForNormal> detailsList = new ArrayList<OrderDetailsForNormal>();
//					OrderHeaderForNormal orderHeader = orderHeaderForNormalMybatisDao.queryByReceiptList(orderHeaderForNormalQuery);
//					for(OrderDetailsForNormal orderDetails : orderHeader.getOrderDetailsForNormalList()){
//						totalNum++;
//						detailsList.add(orderDetails);
//					}
//
//					pageSize = (int)Math.ceil((double)totalNum / row);
//					for(int i = 0 ; i < pageSize ; i++){
//						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//						baos = new ByteArrayOutputStream();
//						stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt.pdf"), baos);
//						form = stamper.getAcroFields();
//						form.setField("orderCode", orderHeader.getOrderCode() == null ? "" : orderHeader.getOrderCode());
//						form.setField("custName", orderHeader.getCustomerShortName());
//						form.setField("cartonQty", orderHeader.getBoxQty() == null ? "0" : String.valueOf(orderHeader.getBoxQty()));
//						form.setField("name", orderHeader.getConsigneeName() == null ? "" : orderHeader.getConsigneeName());
//						form.setField("address", orderHeader.getAddress() == null ? "" : orderHeader.getAddress());
//						form.setField("tel", orderHeader.getTel() == null ? "" : orderHeader.getTel());
//						form.setField("requiredTime", orderHeader.getRequiredDeliveryTime() == null ? "" : format.format(orderHeader.getRequiredDeliveryTime()));
//						form.setField("notes", orderHeader.getNotes() == null ? "" : orderHeader.getNotes());
//						form.setField("totalAmt", String.valueOf(orderHeader.getTotalPrice()));
//						for(int j = 0 ; j < row ; j++){
//							if(totalNum > (row * i + j)){
//								form.setField("seq"+(j+1), String.valueOf(detailsList.get(row * i + j).getOrderLineNo()));
//								form.setField("skuCode"+(j+1), detailsList.get(row * i + j).getSku());
//								form.setField("skuName"+(j+1), detailsList.get(row * i + j).getSkuName());
//								form.setField("qty"+(j+1), String.valueOf(detailsList.get(row * i + j).getQtyOrdered()));
//								form.setField("unit"+(j+1), detailsList.get(row * i + j).getUnit() == null ? "" : detailsList.get(row * i + j).getUnit());
//								form.setField("price"+(j+1), String.valueOf(detailsList.get(row * i + j).getTotalPrice()));
//								form.setField("totalPrice"+(j+1), String.valueOf(detailsList.get(row * i + j).getQtyOrdered() * detailsList.get(row * i + j).getTotalPrice()));
//							}
//						}
//						form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(orderHeader.getOrderCode(), 800)));
//						stamper.setFormFlattening(true);
//						stamper.close();
//						page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
//						pdfCopy.addPage(page);
//					}
//					document.close();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

    public List<EasyuiCombobox> getOrderTypeCombobox() {
        List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
        EasyuiCombobox combobox = null;
        List<OrderStatusResult> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryOrderType();
        if (orderHeaderForNormalList != null && orderHeaderForNormalList.size() > 0) {
            //下拉框添加数据
            for (OrderStatusResult orderHeaderForNormal : orderHeaderForNormalList) {
                combobox = new EasyuiCombobox();
                combobox.setId(String.valueOf(orderHeaderForNormal.getOrderType()));
                combobox.setValue(orderHeaderForNormal.getOrderTypeName());
                comboboxList.add(combobox);
            }
        }
        return comboboxList;
    }

    public List<EasyuiCombobox> getOrderStatusCombobox() {
        List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
        EasyuiCombobox combobox = null;
        List<OrderStatusResult> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryOrderStatus();
        if (orderHeaderForNormalList != null && orderHeaderForNormalList.size() > 0) {
            //下拉框添加数据
            for (OrderStatusResult orderHeaderForNormal : orderHeaderForNormalList) {
                combobox = new EasyuiCombobox();
                combobox.setId(String.valueOf(orderHeaderForNormal.getOrderStatus()));
                combobox.setValue(orderHeaderForNormal.getOrderStatusName());
                comboboxList.add(combobox);
            }
        }
        return comboboxList;
    }

    public List<EasyuiCombobox> getReleasestatusCombobox() {
        List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
        EasyuiCombobox combobox = null;
        List<OrderStatusResult> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryReleaseStatus();
        if (orderHeaderForNormalList != null && orderHeaderForNormalList.size() > 0) {
            //下拉框添加数据
            for (OrderStatusResult orderHeaderForNormal : orderHeaderForNormalList) {
                combobox = new EasyuiCombobox();
                combobox.setId(String.valueOf(orderHeaderForNormal.getOrderStatus()));
                combobox.setValue(orderHeaderForNormal.getOrderStatusName());
                comboboxList.add(combobox);
            }
        }
        return comboboxList;
    }

    public OrderHeaderForNormal getOrderHeader(OrderHeaderForNormalQuery query) {
        query.setOrderno(query.getOrderno().replace(",", ""));
        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(query);
        return orderHeaderForNormal;
    }

    public void exportBatchPdf(HttpServletResponse response, String orderCodeList) {
        StringBuilder sb = new StringBuilder();
        try (OutputStream os = response.getOutputStream()) {
            sb.append("inline; filename=")
                    .append(URLEncoder.encode("PDF", "UTF-8"))
                    .append(".pdf");
            response.setHeader("Content-disposition", sb.toString());
            sb.setLength(0);
            response.setContentType(ContentTypeEnum.pdf.getContentType());

            Document document = null;
            AcroFields form = null;
            PdfStamper stamper = null;
            PdfImportedPage page = null;
            ByteArrayOutputStream baos = null;

            if (StringUtils.isNotEmpty(orderCodeList)) {

                document = new Document(PDFUtil.getTemplate("wms_packing_jhck.pdf").getPageSize(1));
                PdfCopy pdfCopy = new PdfCopy(document, os);
                document.open();
                //分割orderCodeList中的主单号pano
                String[] orderCodeArray = orderCodeList.split(",");

                for (String orderCode : orderCodeArray) {
                    //根据主单pano查询对象DocPaHeader
					/*DocPaHeader docPaHeader = docPaHeaderDao.queryById(orderCode);
					if(docPaHeader!=null){

						int totalNum = 0;
						int row = 15;//每页条数
						int pageSize = 0;

						DocPaDetailsQuery query = new DocPaDetailsQuery();
						query.setAsnno(orderCode);
						//根据主单pano获取子单所有的产品 orderCode==pano
						List<DocPaDetails> detailsList =  docPaDetailsService.queryDocPdaDetails(orderCode);

						totalNum = detailsList.size();//总条数
						pageSize = (int)Math.ceil( (double) totalNum / row);//总页数
						for(int i=0;i<pageSize;i++){//单头内容
							baos = new ByteArrayOutputStream();
							stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt_jhck.pdf"), baos);
							form = stamper.getAcroFields();
							form.setField("putwayCode",docPaHeader.getPano());
							form.setField("receviedDdate", DateUtil.format(docPaHeader.getAddtime(),"yyyy-MM-dd"));
							form.setField("warehouseid", docPaHeader.getWarehouseid());
							form.setField("custName", docPaHeader.getCustomerid());
							form.setField("supplier", "");
							form.setField("notes", docPaHeader.getNotes());
							form.setField("page", "第"+(i+1)+"页,共"+pageSize+"页");
							//form.setField("barCode1", docPaHeader.getAsnno());
							form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(docPaHeader.getPano(), 800)));
							//form.replacePushbuttonField("orderCodeImg1", PDFUtil.genPdfButton(form, "orderCodeImg1", BarcodeGeneratorUtil.genBarcode(docPaHeader.getAsnno(), 800)));

							for(int j=0;j<row;j++){//主单产品明细
								if(totalNum > (row*i+j)){
									DocPaDetails docPaDetails = detailsList.get(row * i + j);
									BasSku basSku = basSkuService.getSkuInfo(docPaHeader.getCustomerid(),detailsList.get(row * i + j).getSku());
									//根据某一个订单明细查询关联的Doc_Asn_Details
									DocAsnDetailQuery queryDetail = new DocAsnDetailQuery();
									queryDetail.setAsnno(docPaDetails.getAsnno());
									queryDetail.setAsnlineno(docPaDetails.getAsnlineno());
									DocAsnDetailVO detailVO = docAsnDetailService.queryDocAsnDetail(queryDetail);

									form.setField("sku."+j, docPaDetails.getSku());
									form.setField("skuN."+j, basSku.getDescrC());
									form.setField("regNo."+j, basSku.getReservedfield03());
									form.setField("desc."+j, basSku.getDescrE());
									form.setField("batchNo."+j, docPaDetails.getUserdefine3());
									form.setField("seriNo."+j, docPaDetails.getUserdefine4());
									form.setField("ill."+j, detailVO.getLotatt07());
									form.setField("lot01."+j, detailVO.getLotatt01());
									form.setField("lot02."+j, detailVO.getLotatt02());
									form.setField("qtyE."+j, docPaDetails.getAsnqtyExpected().toString());
									form.setField("uom."+j, basSku.getDefaultreceivinguom());
									form.setField("qty."+j, docPaDetails.getPutwayqtyExpected().toString());
									form.setField("qtyA."+j, docPaDetails.getPutwayqtyCompleted().toString());
								}
							}
							stamper.setFormFlattening(true);
							stamper.close();
							page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
							pdfCopy.addPage(page);
						}
					}*/

                }
                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OrderHeaderForNormalVO> getUndoneList(PageForm form) {

        List<OrderHeaderForNormal> orderHeaderForNormals = orderHeaderForNormalMybatisDao.queryPackageList(form.getStart(), form.getPageSize());

        OrderHeaderForNormalVO orderHeaderForNormalVO;
        List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = new ArrayList<>();

        for (OrderHeaderForNormal orderHeaderForNormal : orderHeaderForNormals) {

            orderHeaderForNormalVO = new OrderHeaderForNormalVO();
            BeanUtils.copyProperties(orderHeaderForNormal, orderHeaderForNormalVO);
            orderHeaderForNormalVOS.add(orderHeaderForNormalVO);
        }

        return orderHeaderForNormalVOS;
    }

    public OrderHeaderForNormalVO queryByOrderno(String orderno) {

        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderno);
        OrderHeaderForNormalVO headerVO = new OrderHeaderForNormalVO();

        if (orderHeaderForNormal != null) {

            BeanUtils.copyProperties(orderHeaderForNormal, headerVO);
        }

        return headerVO;
    }

    public void exportAccompanyingPdf(HttpServletResponse response, String orderNo) {
        StringBuilder sb = new StringBuilder();
        try (OutputStream os = response.getOutputStream()) {
            sb.append("inline; filename=")
                    .append(URLEncoder.encode("出库随货同行单PDF", "UTF-8"))
                    .append(".pdf");
            response.setHeader("Content-disposition", sb.toString());
            sb.setLength(0);
            response.setContentType(ContentTypeEnum.pdf.getContentType());

            Document document = null;
            AcroFields form = null;
            PdfStamper stamper = null;
            PdfImportedPage page = null;
            ByteArrayOutputStream baos = null;

            if (StringUtils.isNotEmpty(orderNo)) {

                document = new Document(PDFUtil.getTemplate("wms_shipped_jhck.pdf").getPageSize(1));
                PdfCopy pdfCopy = new PdfCopy(document, os);
                document.open();

                String[] orderNos = orderNo.split(",");

                for(String s : orderNos){

                    int totalNum = 0;
                    List<ReceiptResult> detailsList = null;
                    int row = 7;
                    int pageSize = 0;
                    int totalQtyE = 0;
                    int totalQty = 0;

                    detailsList = new ArrayList<ReceiptResult>();
                    OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
                    orderHeaderForNormalQuery.setOrderno(s);
                    List<ReceiptResult> receiptResult = orderHeaderForNormalMybatisDao.queryByReceiptList(orderHeaderForNormalQuery);
                    if (receiptResult == null || receiptResult.size() == 0) {
                        return;
                    }

                    for (ReceiptResult result : receiptResult) {
                        totalNum++;
                        detailsList.add(result);
                    }
                    pageSize = (int) Math.ceil((double) totalNum / row);
                    for (int i = 0; i < pageSize; i++) {
                        ReceiptResult obj = receiptResult.get(0);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        baos = new ByteArrayOutputStream();
                        stamper = new PdfStamper(PDFUtil.getTemplate("wms_shipped_jhck.pdf"), baos);
                        form = stamper.getAcroFields();
                        form.setField("orderNo", obj.getOrderno());
                        //basCustomerService.selectCustomerById(orderHeaderForNormal.getCustomerId(), Constant.);
                        form.setField("expectedShipmentTime", DateUtil.format(obj.getEdittime(), "yyyy-MM-dd"));
                        form.setField("carrierName", obj.getCarrierName());
                        form.setField("consigneeName", obj.getConsigneeCompany());
                        form.setField("cContact", obj.getCContact());
                        form.setField("userdefine1", obj.getUserdefine1());
                        form.setField("cAddress1", obj.getAddress());
                        form.setField("c_Tel1", obj.getCTel1());
                        form.setField("userdefine2", obj.getUserdefine2());
                        form.setField("sOReference1", obj.getSoreference1());
                        form.setField("notes", obj.getNotes());
                        form.setField("page","第"+(i+1)+"页,共"+pageSize+"页");

                        for (int j = 0; j < row; j++) {
                            if (totalNum > (row * i + j)) {
                                BasSku basSku = basSkuService.getSkuInfo(obj.getCustomerid(), detailsList.get(row * i + j).getSku());
                                form.setField("lineNo." + j, j + 1 + "");
                                form.setField("location." + (j), detailsList.get(row * i + j).getDescrC());
                                form.setField("sku." + (j), detailsList.get(row * i + j).getSku());
                                form.setField("skuN." + (j), detailsList.get(row * i + j).getLotatt12());
                                form.setField("regNo." + (j), basSku.getReservedfield03());
                                form.setField("desc." + (j), basSku.getDescrE());
                                form.setField("batchNo." + (j), detailsList.get(row * i + j).getLotatt04());
                                form.setField("seriNo." + (j), detailsList.get(row * i + j).getLotatt05());
                                form.setField("ill." + (j), detailsList.get(row * i + j).getLotatt07());
                                form.setField("lot01." + (j), detailsList.get(row * i + j).getLotatt02());
                                form.setField("qtyE." + (j), detailsList.get(row * i + j).getQty());
                                form.setField("uom." + (j), detailsList.get(row * i + j).getQtyEach());
                                form.setField("qty." + (j), detailsList.get(row * i + j).getLotatt11());
                                form.setField("double." + (j), detailsList.get(row * i + j).getLotatt15());
                                form.setField("card." + (j), detailsList.get(row * i + j).getDoubleCertificate());
                                form.setField("report." + (j), detailsList.get(row * i + j).getCertificate());
                                form.setField("remark." + (j), "");
                                totalQtyE += Integer.parseInt(detailsList.get(row * i + j).getQtyEach());
                                totalQty += Integer.parseInt(detailsList.get(row * i + j).getQty());
                            }
                        }
                        form.setField("sumqtyPage","件数合计："+totalQtyE);
                        form.setField("sumqty","数量合计："+totalQty);
                        form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(obj.getOrderno(), 800)));
                        stamper.setFormFlattening(true);
                        stamper.close();
                        page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
                        pdfCopy.addPage(page);
                    }
                }
                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据sku和customerid
     *
     * @param sku
     * @param customerId
     * @return
     */
    public List<EasyuiCombobox> getLotAttBySkuCustomerId(String sku, String customerId) {
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        InvLotAttQuery query = new InvLotAttQuery();
        query.setCustomerid(customerId);
        query.setSku(sku);
        mybatisCriteria.setCondition(query);
        mybatisCriteria.setDistinct(true);
        List<EasyuiCombobox> comboboxList = new ArrayList<>();
        List<InvLotAtt> list = invLotAttMybatisDao.queryLotNoRepet(mybatisCriteria);
        if (list != null && list.size() > 0) {
            for (InvLotAtt i : list) {
				/*Map<String,Object> option = new HashMap<>();
				option.put("lotatt09",i.getLotatt09());
				option.put("lotatt10",i.getLotatt10());
				option.put("lotatt14",i.getLotatt14());
				option.put("lotatt15",i.getLotatt15());*/
                EasyuiCombobox com = new EasyuiCombobox();
                com.setId(i.getLotatt04());
                com.setValue(i.getLotatt04());
                //com.setOption(option);
                comboboxList.add(com);
            }
        }
        return comboboxList;
    }

    public List<EasyuiCombobox> getRefOut() {
        MybatisCriteria criteria = new MybatisCriteria();
        OrderHeaderForNormalQuery query = new OrderHeaderForNormalQuery();
        query.setOrderStatus("80");
        criteria.setCondition(query);
        List<EasyuiCombobox> comboboxList = new ArrayList<>();
        List<OrderHeaderForNormal> list = orderHeaderForNormalMybatisDao.queryByList(criteria);
        if (list != null && list.size() > 0) {
            for (OrderHeaderForNormal h : list) {
                EasyuiCombobox comb = new EasyuiCombobox();
                comb.setId(h.getOrderno());
                comb.setValue(h.getOrderno());
                comboboxList.add(comb);
            }
        }
        return comboboxList;
    }

    /**
     * 引用出库
     *
     * @param orderno
     * @return
     */
    public Json doRefOut(String orderno, String refOrderno) throws Exception {
        OrderHeaderForNormal head = orderHeaderForNormalMybatisDao.queryById(orderno);
        if (head == null || !head.getSostatus().equals("00")) {
            return Json.error("只有新建状态的出库单才能引用出库");
        }
        //已选出库单明细
        OrderDetailsForNormalQuery query = new OrderDetailsForNormalQuery();
        query.setOrderno(orderno);
        MybatisCriteria criteria = new MybatisCriteria();
        criteria.setCondition(query);
        List<OrderDetailsForNormal> detailsForNormals = orderDetailsForNormalMybatisDao.queryByPageList(criteria);
        if (detailsForNormals != null && detailsForNormals.size() > 0) {
            OrderDetailsForNormalQuery queryRef = new OrderDetailsForNormalQuery();
            queryRef.setOrderno(refOrderno);
            MybatisCriteria criteriaRef = new MybatisCriteria();
            criteriaRef.setCondition(queryRef);
            List<OrderDetailsForNormal> detailsForNormalsRef = orderDetailsForNormalMybatisDao.queryByPageList(criteriaRef);
            if (detailsForNormalsRef == null || detailsForNormalsRef.size() == 0) {
                return Json.error("操作失败，引用出库单明细为空");
            }
            Map<String, OrderDetailsForNormal> map = new HashMap<>();
            for (OrderDetailsForNormal detail : detailsForNormalsRef) {
                map.put(getKey(detail), detail);
            }
            //判断是否明细正确
            for (OrderDetailsForNormal d : detailsForNormals) {
                OrderDetailsForNormal dRef = map.get(getKey(d));
                if (dRef == null) {
                    return Json.error("操作失败，引用单据明细不匹配");
                }
            }
            //分配库存
            Json result = allocation(orderno);
            if (result.isSuccess()) {
                head = orderHeaderForNormalMybatisDao.queryById(orderno);
                if (head.getSostatus().equals("40")) {
                    Json json = picking(orderno);
                    if (json.isSuccess()) {
                        OrderHeaderForNormalForm form = new OrderHeaderForNormalForm();
                        form.setOrderno(orderno);
                        result = shipment(form);
                        if (result.isSuccess()) {
                            return Json.error("发运成功");
                        } else {
                            return Json.error("发运失败");
                        }
                    } else {
                        return Json.error("拣货失败");
                    }
                } else {
                    return Json.error("分配库存失败");
                }
            } else {
                return Json.error("分配库存失败");
            }
        } else {
            return Json.error("操作失败，出库单明细为空");
        }
    }

    /**
     * 检验双证
     *
     * @param orderno
     * @return
     */
    public Json reqDouble(String orderno) {
        try {
            OrderHeaderForNormal head = orderHeaderForNormalMybatisDao.queryById(orderno);
            if (head == null || !head.getSostatus().equals("00")) {
                return Json.error("只有新建状态的出库单才能引用出库");
            }
            //已选出库单明细
            OrderDetailsForNormalQuery query = new OrderDetailsForNormalQuery();
            query.setOrderno(orderno);
            MybatisCriteria criteria = new MybatisCriteria();
            criteria.setCondition(query);
            List<OrderDetailsForNormal> detailsForNormals = orderDetailsForNormalMybatisDao.queryByPageList(criteria);
            if (detailsForNormals != null && detailsForNormals.size() > 0) {
                List<OrderDetailsForNormal> needDouble = new ArrayList<>();
                for (OrderDetailsForNormal d : detailsForNormals) {
                    BasSku basSku = basSkuService.getSkuInfo(d.getCustomerid(), d.getSku());
                    if (basSku != null) {
                        if (basSku.getSkuGroup7().equals("1")) {
                            d.setBasSku(basSku);
                            needDouble.add(d);
                        }
                    }
                }
                DocAsnDoublecQuery doublecQuery = new DocAsnDoublecQuery();
                MybatisCriteria criteria1 = new MybatisCriteria();
                criteria1.setCondition(doublecQuery);
                List<DocAsnDoublec> docAsnDoublecList = docAsnDoublecMybatisDao.queryByList(criteria1);
                if (needDouble.size() == 0) {
                    return Json.error("没有需要匹配双证的产品");
                }
                if (docAsnDoublecList != null && docAsnDoublecList.size() >= needDouble.size()) {
                    for (OrderDetailsForNormal d : needDouble) {
                        d.setLotatt13("Y");
                        orderDetailsForNormalMybatisDao.updateBySelective(d);
                        docAsnDoublecList.remove(0);
                        DocAsnDoublec doublec = new DocAsnDoublec();
                        doublec.setDoublecno(d.getLotatt13());
                        doublec.setMatchFlag(Constant.IS_USE_YES);
                        docAsnDoublecMybatisDao.updateBySelective(doublec);
                    }
                    return Json.success("匹配双证完成");
                } else {
                    return Json.error("没有足够的双证可供匹配");
                }
            } else {
                return Json.error("查询不到出库单明细");
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Json.error("匹配双证失败");
        }
    }

    /**
     * 获取合格证
     * @param customerId 客户id
     * @param sku 产品代码
     * @param lotatt4 生产批号
     * @return
     */
    public String getCertificate(String customerId,String sku,String lotatt4) {
        MybatisCriteria criteria = new MybatisCriteria();
        DocAsnCertificateQuery query = new DocAsnCertificateQuery();
        query.setCustomerid(customerId);
        query.setSku(sku);
        query.setLotatt04(lotatt4);
        criteria.setCondition(query);
        List<DocAsnCertificate> list = docAsnCertificateMybatisDao.queryByList(criteria);
        if(list!=null && list.size()>0){
            return list.get(0).getCertificateContext();
        }
        return "";
    }

    public void printH(HttpServletResponse response, String orderCodeList) {
        StringBuilder sb = new StringBuilder();
        try (OutputStream os = response.getOutputStream()) {
            sb.append("inline; filename=")
                    .append(URLEncoder.encode("打印合格证", "UTF-8"))
                    .append(".pdf");
            response.setHeader("Content-disposition", sb.toString());
            sb.setLength(0);
            response.setContentType(ContentTypeEnum.pdf.getContentType());

            Document doc = new Document();
            AcroFields form = null;
            PdfStamper stamper = null;
            PdfImportedPage page = null;
            ByteArrayOutputStream baos = null;

            if (StringUtils.isNotEmpty(orderCodeList)) {
                String[] orderNoArr = orderCodeList.split(",");
                //PdfWriter writer = PdfWriter.getInstance(doc,os);
                PdfCopy copy = new PdfCopy(doc,os);
                doc.open();
                for(String order : orderNoArr){
                    MybatisCriteria criteria = new MybatisCriteria();
                    OrderDetailsForNormalQuery query = new OrderDetailsForNormalQuery();
                    query.setOrderno(order);
                    criteria.setCondition(query);
                    List<OrderDetailsForNormal> details = orderDetailsForNormalMybatisDao.queryByPageList(criteria);
                    for(OrderDetailsForNormal de : details){
                        doc.newPage();
                        String url = getCertificate(de.getCustomerid(),de.getSku(),de.getLotatt04());
                        if(!"".equals(url)){
                            PdfReader reader = new PdfReader(Constant.uploadUrl+File.separator+url);

                            PdfImportedPage newPage = copy.getImportedPage(reader,1);

                            copy.addPage(newPage);
                            /*Image png = Image.getInstance(Constant.uploadUrl+File.separator+url);
                            png.scaleAbsolute(500.0F,750F);
                            doc.add(png);*/
                        }

                    }
                }
                doc.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printExpress(HttpServletResponse response, String orderCodeList, Model model) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();



        map.put("logo", "imgFile/sflogo.jpg");
        map.put("sftelLogo", "imgFile/qiao.jpg");
        map.put("proCode", "imgFile/FM/T4.jpg");
        map.put("so", "imgFile/FM/so.jpg");

        //二维码
        // String binary = SfQrCodeUtils.creatRrCode("MMM={'k1':'755WF','k2':'755AQ','k3':'036','k4':'T4','k5':'619428034014','k6':'','k7':'dce4e1c6','k7':'3fc52389'}", 200,200);

      /*  byte[] bytes = null;
        bytes = new Base64().decode(binary);
        for (int K = 0; K < bytes.length; ++K) {
            if (bytes[K] < 0) {
                bytes[K] = (byte) (bytes[K] + 256);
            }
        }*/

        //map.put("QRcode",new ByteArrayInputStream(Base64.decodeBase64(binary.getBytes())));
        map.put("ji", "imgFile/FM/ji.jpg");
        map.put("tips5", "imgFile/FM/POD.jpg");

        map.put("expressType", "1");
        map.put("codingMappingOut", "3A");
        //打印时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(new Date());
        map.put("electric", "SF  打印时间    " + str);
        //piece
        map.put("addedService", "POD");
        //订单条码的生成
        map.put("childMailNo", "217276730473");
        //子单号
        //map.put("mailNo", "217276730473");
        //打印单号
        //map.put("addedService","A");
        //母单号
        map.put("mailNoStr", "单号   " + "217 276 730 473");
        //map.put("mailNoStr", "签回单号");
        //map.put("childMailNoStr", "217276730473");
        //打印原寄地
        map.put("destRouteLabel", "021WG-021NA");
        //收件人相关信息
        map.put("consignerName", "郑洁");
        map.put("consignerTel", "021-62091927");
        map.put("consignerCompany", "上海嘉事嘉意医疗器材有限公司");
        map.put("consignerProvince", "上海市");
        map.put("consignerCity", "上海市");
        map.put("consignerCounty", "浦东新区");
        map.put("consignerAddress", "施湾八路1026号2号楼");
        //支付方式
        map.put("monthAccount", "0213071013");//月结卡号
        map.put("payMethod", "1");
        //金额
        // map.put("codValue","9999.9");
        //进港信息<去识别不同的代码>
        map.put("codingMapping", "021NA");
        //出港中转场代码
        map.put("sourceTransferCode", "451W");

        //寄件人的相关信息
        map.put("deliverName", "高俊");
        //map.put("deliverTel", "");
        map.put("deliverMobile", "13766809097");
        map.put("consignerCompany", "哈尔滨四圣商贸有限公司");
        map.put("deliverProvince", "黑龙江省");
        map.put("deliverCity", "哈尔滨市");
        map.put("deliverCounty", "香坊区");
        map.put("deliverAddress", "旭升街乐民小区4栋3单元-1层1号");

        //备注
        map.put("remark",orderCodeList);
        //托寄物
        map.put("cargo","2");
        //计费重量
        map.put("cargoTotalWeight","3");
        //实际重量
        map.put("cargoTotalWeight","4");
        //费用合计
        map.put("totalFee","5");


        map.put("PALINENO", System.currentTimeMillis());
        list.add(map);
        JRDataSource jrDataSource = new JRMapArrayDataSource(list.toArray());
        model.addAttribute("url", "WEB-INF/jasper/V3.1.FM_poster_100mm210mmTeseSf.jasper");
        model.addAttribute("format", Constant.JASPER_PDF);
        model.addAttribute("jrMainDataSource", jrDataSource);

    }



    /**
     * 复用入库明细信息
     */
    public Json copyDetailGo(String generalNO, String detailOrderno, String customerid, String soreference2, String copyFlag) {
        Json json = new Json();
        OrderDetailsForNormal orderDetailsForNormal = new OrderDetailsForNormal();
        MybatisCriteria criteria = new MybatisCriteria();
        Double index = 1.000;


        /*
         *  1:复用出库明细 -1 : 复用入库明细
         */
        if(!generalNO.equals("") && generalNO != null) {
            if (copyFlag.equals("1")) {
                OrderDetailsForNormalQuery normalQuery = new OrderDetailsForNormalQuery();
                normalQuery.setCustomerid(customerid);
                normalQuery.setOrderno(generalNO);

                criteria.setCondition(BeanConvertUtil.bean2Map(normalQuery));
                List<OrderDetailsForNormal> detailsForNormalList = orderDetailsForNormalMybatisDao.queryByPageList(criteria);

                if (detailsForNormalList.size() > 0) {
                    for (OrderDetailsForNormal detailsForNormal : detailsForNormalList) {

                        BeanUtils.copyProperties(detailsForNormal, orderDetailsForNormal);

                        orderDetailsForNormal.setOrderno(detailOrderno);
                        orderDetailsForNormal.setOrderlineno(index);

                        orderDetailsForNormal.setLinestatus("00");
                        orderDetailsForNormal.setQtysoftallocated(0.00);
                        orderDetailsForNormal.setQtysoftallocatedEach(0.00);

                        orderDetailsForNormal.setQtyallocated(0.00);
                        orderDetailsForNormal.setQtyallocatedEach(0.00);

                        orderDetailsForNormal.setQtypicked(0.00);
                        orderDetailsForNormal.setQtypickedEach(0.00);

                        orderDetailsForNormal.setQtyshipped(0.00);
                        orderDetailsForNormal.setQtyshippedEach(0.00);

                        orderDetailsForNormal.setAddwho(SfcUserLoginUtil.getLoginUser().getId());  //日期
                        orderDetailsForNormal.setAddtime(new Date());

                        orderDetailsForNormal.setLotatt10("HG");
                        //是否有入库单号 todo 定向、引用订单插入库单号(必填)
                        if (soreference2.equals("") && soreference2 == null) {
                            orderDetailsForNormal.setLotatt14("");
                        } else {
                            orderDetailsForNormal.setLotatt14(soreference2);
                        }
                        orderDetailsForNormalMybatisDao.add(orderDetailsForNormal);
                        index += 1.000;
                    }
                    json.setSuccess(true);
                    json.setMsg("明细复用成功");
                } else {
                    json.setSuccess(false);
                    json.setMsg("请填写正确编号<明细信息不存在或该货主没有该明细>");
                }
            } else {
                DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
                docAsnDetailQuery.setAsnno(generalNO);
                docAsnDetailQuery.setCustomerid(customerid);
                criteria.setCondition(BeanConvertUtil.bean2Map(docAsnDetailQuery));
                List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByList(criteria);
                if (docAsnDetailList.size() > 0) {
                    for (DocAsnDetail docAsnDetail : docAsnDetailList) {
                        orderDetailsForNormal.setOrderno(detailOrderno);
                        orderDetailsForNormal.setOrderlineno(index);
                        //货主代码
                        orderDetailsForNormal.setCustomerid(docAsnDetail.getCustomerid());
                        orderDetailsForNormal.setLinestatus("00");
                        orderDetailsForNormal.setSku(docAsnDetail.getSku());
                        orderDetailsForNormal.setSkuName(docAsnDetail.getSkuName());
                        orderDetailsForNormal.setUom(docAsnDetail.getUom());
                        orderDetailsForNormal.setPackid(docAsnDetail.getPackid());

                        orderDetailsForNormal.setQtyordered(docAsnDetail.getExpectedqty().doubleValue());
                        orderDetailsForNormal.setQtyorderedEach(docAsnDetail.getExpectedqtyEach().doubleValue());

                        orderDetailsForNormal.setQtysoftallocated(0.00);
                        orderDetailsForNormal.setQtysoftallocatedEach(0.00);

                        orderDetailsForNormal.setQtyallocated(0.00);
                        orderDetailsForNormal.setQtyallocatedEach(0.00);

                        orderDetailsForNormal.setQtypicked(0.00);
                        orderDetailsForNormal.setQtypickedEach(0.00);

                        orderDetailsForNormal.setQtyshipped(0.00);
                        orderDetailsForNormal.setQtyshippedEach(0.00);


                        orderDetailsForNormal.setAddwho(SfcUserLoginUtil.getLoginUser().getId());  //日期
                        orderDetailsForNormal.setAddtime(new Date());


                        orderDetailsForNormal.setLocation(docAsnDetail.getReceivinglocation()); //库位
                        orderDetailsForNormal.setNetweight(docAsnDetail.getTotalnetweight().doubleValue()); //净重
                        orderDetailsForNormal.setGrossweight(docAsnDetail.getTotalgrossweight().doubleValue()); //毛重
                        orderDetailsForNormal.setCubic(docAsnDetail.getTotalcubic().doubleValue());//体积
                        orderDetailsForNormal.setPrice(docAsnDetail.getTotalprice().doubleValue()); //价格

                        orderDetailsForNormal.setLotatt01(docAsnDetail.getLotatt01());
                        orderDetailsForNormal.setLotatt02(docAsnDetail.getLotatt02());
                        orderDetailsForNormal.setLotatt03(docAsnDetail.getLotatt03());
                        orderDetailsForNormal.setLotatt04(docAsnDetail.getLotatt04());
                        orderDetailsForNormal.setLotatt05(docAsnDetail.getLotatt05());
                        orderDetailsForNormal.setLotatt06(docAsnDetail.getLotatt06());
                        orderDetailsForNormal.setLotatt07(docAsnDetail.getLotatt07());
                        orderDetailsForNormal.setLotatt08(docAsnDetail.getLotatt08());
                        orderDetailsForNormal.setLotatt09(docAsnDetail.getLotatt09());
                        orderDetailsForNormal.setLotatt10("HG");
                        orderDetailsForNormal.setLotatt11(docAsnDetail.getLotatt11());
                        orderDetailsForNormal.setLotatt12(docAsnDetail.getLotatt12());
                        orderDetailsForNormal.setLotatt13(docAsnDetail.getLotatt13());
                        orderDetailsForNormal.setLotatt15(docAsnDetail.getLotatt15());

                        //是否有入库单号
                        if (soreference2.equals("") && soreference2 == null) {
                            orderDetailsForNormal.setLotatt14("");
                        } else {
                            orderDetailsForNormal.setLotatt14(soreference2);
                        }
                        orderDetailsForNormalMybatisDao.add(orderDetailsForNormal);
                        index += 1.000;
                    }

                    json.setSuccess(true);
                    json.setMsg("明细复用成功");
                } else {

                    json.setSuccess(false);
                    json.setMsg("请填写正确编号<明细信息不存在或该货主没有该明细>");

                }
            }
        }else {
            json.setSuccess(false);
            json.setMsg("请填写编号");
        }
        return json;
    }

//导出序列号记录
    public void exportbasSerialNumToExcel(HttpServletResponse response, OrderHeaderForNormalForm orderNofrom)throws Exception {
        Cookie cookie = new Cookie("exportToken", orderNofrom.getOrderFlag());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        String str = null;
        try {
            //Excel表头数据
            LinkedHashMap<String, String> serialMap = getDocSerialNumRecord();
            String sheetName = "序列号记录";
            //Excel需要的数据
            List<DocSerialNumRecord> docSerialNumRecordList = docSerialNumRecordMybatisDao.queryExport(orderNofrom.getOrderno());
            if (docSerialNumRecordList.size() > 0) {
                for (int i = 0; i < docSerialNumRecordList.size(); i++) {
                    str = format.format(docSerialNumRecordList.get(i).getAddtime());
                    docSerialNumRecordList.get(i).setAddTimeSetting(str);
                }
                //将查询出来的数据转化为Excel数据
                ExcelUtil.listToExcel(docSerialNumRecordList, serialMap, sheetName, response);
                //修改docOrderHeader中的udfprintflag1信息为1 导出过序列号信息
                docSerialNumRecordMybatisDao.updateDocOrder(orderNofrom.getOrderno());
            } else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//检查orderno是否存在
    public Json isexportOrderNo(String orderno){
            Json json=new Json();
            List<DocSerialNumRecord> docSerialNumRecordList = docSerialNumRecordMybatisDao.queryExport(orderno);
            if(docSerialNumRecordList.size() >0){
               json.setSuccess(true);
            }else{
               json.setSuccess(false);
               json.setMsg("当前出库单号没有序列号记录,不可导出");
            }

            return json;
    }

    /**
     * 得到导出Excle时题型的英中文map
     *
     * @return 返回题型的属性map
     */
    public LinkedHashMap<String, String> getDocSerialNumRecord() {

        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();

        superClassMap.put("skuName", "客户");
        superClassMap.put("orderNo", "出库单号");
        superClassMap.put("soreference", "客户单号");
        superClassMap.put("batchNum", "生产批号");
        superClassMap.put("serialNum", "序列号");
        superClassMap.put("addTimeSetting", "制单日期");
        superClassMap.put("addwho", "制单人");

        return superClassMap;
    }

    public Json qlOrderDetails(String orderno){
        Json json = new Json();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();

        OrderDetailsForNormalQuery orderDetailsForNormalQuery = new OrderDetailsForNormalQuery();
        orderDetailsForNormalQuery.setOrderno(orderno);
        mybatisCriteria.setCondition(orderDetailsForNormalQuery);
        long numberFlag =  orderDetailsForNormalMybatisDao.queryByCount(mybatisCriteria);
        if(numberFlag > 0){
            json.setSuccess(true);
        }else{
            json.setSuccess(false);
        }

        return json;
    }


    private String getKey(OrderDetailsForNormal detail) {
        return detail.getSku() + "" + detail.getLotatt01() + detail.getLotatt02() + detail.getLotatt03() + detail.getLotatt04()
                + detail.getLotatt05() + detail.getLotatt06() + detail.getLotatt07() + detail.getLotatt08() + detail.getLotatt09()
                + detail.getLotatt10() + detail.getLotatt11() + detail.getLotatt12() + detail.getLotatt13() + detail.getLotatt13();
    }

    private String getYesNo(String obj){
        if(obj == null || obj.equals("0")){
            return "否";
        }else{
            return "是";
        }
    }

    public static String doubleTrans(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }


    /**
     * 快递投诉
     */
    public Json courierComplaint(OrderHeaderForNormalForm orderHeaderForNormalForm) {
        Json json = new Json();
        orderHeaderForNormalForm.setCourierComplaint(orderHeaderForNormalForm.getCourierComplaintU());
        int num=orderHeaderForNormalMybatisDao.updateCourierComplaint(orderHeaderForNormalForm);
        if(num>0){
            json.setSuccess(true);
            json.setMsg("资料处理成功！");
        }else {
            json.setSuccess(true);
            json.setMsg("资料处理失败！");
        }

        return json;
    }

}