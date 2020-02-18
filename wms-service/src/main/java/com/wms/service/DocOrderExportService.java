package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.StringUtil;
import com.wms.utils.exception.ExcelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 *
 * @author
 * @Date
 */
@Service("docOrderExportService")
public class DocOrderExportService {


    @Autowired
    private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
    @Autowired
    private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private BasCodesMybatisDao basCodesMybatisDao;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HHmmss");
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;
    @Autowired
    private BasCarrierLicenseMybatisDao basCarrierLicenseMybatisDao;
    @Autowired
    private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

    /**
     * 打印order检查记录
     */
    public List<OrderHeaderForNormal> docOrderToExcel(String order) {
        List<OrderHeaderForNormal> orderHeaderForNormalList=new ArrayList<>();
        //获得单头
        OrderHeaderForNormal orderHeaderForNormal=orderHeaderForNormalMybatisDao.queryById(order);
        //获得细单
        List<OrderDetailsForNormal> orderDetailsForNormalList=orderDetailsForNormalMybatisDao.queryByOrderNo1(order);
        if(orderHeaderForNormal!=null&&orderDetailsForNormalList!=null){
//            头像显示地址和电话  发运结算方式
            orderHeaderForNormal.setExcaddress1(orderHeaderForNormal.getCAddress1()==null?"":orderHeaderForNormal.getCAddress1());
            orderHeaderForNormal.setExctel1(orderHeaderForNormal.getCTel1()==null?"":orderHeaderForNormal.getCTel1());
            //快递公司
            if (orderHeaderForNormal.getCarrierid() != null) {
                BasCarrierLicense basCarrierLicense = basCarrierLicenseMybatisDao.queryById(orderHeaderForNormal.getCarrierid());
                if (basCarrierLicense != null) {
                    GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryByEnterpriseId(basCarrierLicense.getEnterpriseId());
                    if (gspEnterpriseInfo != null) {
                        orderHeaderForNormal.setCarriername(gspEnterpriseInfo.getEnterpriseName());
                    }
                }
            }
            //发运方式 ZT BK LY 暂缓
            Map<String, Object> param = new HashMap<>();
            param.put("codeid", "EXP_TYP");
            param.put("code", orderHeaderForNormal.getRoute());
            BasCodes bascodes = basCodesMybatisDao.queryById(param);
            if (bascodes != null) {
                orderHeaderForNormal.setUserdefine1(bascodes.getCodenameC());
            }
            //快递结算方式
            Map<String, Object> param1 = new HashMap<>();
            param1.put("codeid", "JS_FS");
            param1.put("code", orderHeaderForNormal.getStop());
            BasCodes bascodes1 = basCodesMybatisDao.queryById(param1);
            if (bascodes1 != null) {
                orderHeaderForNormal.setUserdefine2(bascodes1.getCodenameC());
            }
            double qtyorderedSum=0;   //订货件数总和
            double qtyorderedEachSum=0;   //订货数量总和
            for (OrderDetailsForNormal d : orderDetailsForNormalList) {

                //计算总数
                qtyorderedSum+=d.getQtyordered();
                qtyorderedEachSum+=d.getQtyorderedEach();
                d.setQtyorderedSum(qtyorderedSum);
                d.setQtyorderedEachSum(qtyorderedEachSum);

                Map<String, Object> param2 = new HashMap<>();
                param2.put("customerid", d.getCustomerid());
                param2.put("sku", d.getSku());
                BasSku basSku = basSkuMybatisDao.queryById(param2);
//                细单部分01转换是否
                if(basSku!=null){
                    d.setCard(getYesNo(basSku.getSkuGroup2()));
                    d.setReport(getYesNo(basSku.getSkuGroup8()));
                    d.setDoublec(getYesNo(basSku.getSkuGroup7()));
                    d.setLotatt12(basSku.getReservedfield01());
                    d.setLotatt11(basSku.getSkuGroup4());
                    d.setDescrc(basSku.getDescrC());
                }
                InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(d.getLotnum());
                if(invLotAtt!=null){
                    d.setLotatt06(invLotAtt.getLotatt06());
                    d.setLotatt07(invLotAtt.getLotatt07());
                    d.setLotatt02(invLotAtt.getLotatt02());
                    d.setLotatt15(invLotAtt.getLotatt15() + "/" + StringUtil.fixNull(basSku.getReservedfield06()));
                }
                //供应商中文
                BasCustomer basCustomer=basCustomerMybatisDao.queryByIdType(invLotAtt.getLotatt08(), Constant.CODE_CUS_TYP_VE);
                if(basCustomer!=null){
                    d.setLotatt08(basCustomer.getDescrC());
                }
            }

            orderHeaderForNormal.setOrderDetailsForNormalList(orderDetailsForNormalList);
        }
        orderHeaderForNormalList.add(orderHeaderForNormal);
        return orderHeaderForNormalList;
    }
    //导出Excel格式所有信息
    public void docOrderToExcel1(HttpServletResponse response, EasyuiDatagridPager pager, OrderHeaderForNormalQuery from) throws UnsupportedEncodingException, ExcelException {
        Cookie cookie = new Cookie("exportToken",from.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());

        try{
            String[] s = from.getOrderno().split(",");
            List<OrderHeaderForNormal> orderHeaderForNormalList = new ArrayList<OrderHeaderForNormal>();
            List<OrderDetailsForNormal> orderDetailsForNormalList = new ArrayList<OrderDetailsForNormal>();
            List<OrderDetailsForNormal> orderDetailsForNormalListShow = new ArrayList<OrderDetailsForNormal>();
            //获得单头
            OrderHeaderForNormal ohForNormal ;
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            //1 为打印头档，否则为明细
            if(from.getOuttype().equals("1")){
                MybatisCriteria mybatisCriteriaHeader = new MybatisCriteria();
                mybatisCriteriaHeader.setCurrentPage(pager.getPage());
                mybatisCriteriaHeader.setPageSize(pager.getRows());
                mybatisCriteriaHeader.setCondition(BeanConvertUtil.bean2Map(from));
                mybatisCriteriaHeader.setOrderByClause("orderno desc");
                List<OrderHeaderForNormal> orderHeaderForNormals = orderHeaderForNormalMybatisDao.queryByList(mybatisCriteriaHeader);
                //发运方式 ZT BK LY 暂缓
                for (OrderHeaderForNormal orderHeaderForNormal:orderHeaderForNormals){
                    Map<String, Object> param = new HashMap<>();
                    param.put("codeid", "EXP_TYP");
                    param.put("code", orderHeaderForNormal.getRoute());
                    BasCodes bascodes = basCodesMybatisDao.queryById(param);
                    if(bascodes!=null){
                        orderHeaderForNormal.setRoute(bascodes.getCodenameC());
                    }
                    orderHeaderForNormalList.add(orderHeaderForNormal);
                }

            }else{
                for (String a:s) {
                    from.setOrderno(a);
                    mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(from));
                    orderDetailsForNormalList = orderDetailsForNormalMybatisDao.queryByPageList(mybatisCriteria);
                    if(orderDetailsForNormalList.size()>0){
                        for (OrderDetailsForNormal orderDetailsForNormal:orderDetailsForNormalList) {
                            orderDetailsForNormalListShow.add(orderDetailsForNormal);
                        }
                    }
                }
            }

            //导出表格名称
            String timeNow=sdf.format(new Date());
            String fileName="发运订单"+timeNow;
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap;
            if(from.getOuttype().equals("1")){
                // excel的sheetName
                String sheetName = "发运订单-头档";
                fieldMap = getOutOrderInfo();
                //将list集合转化为excle
                ExcelUtil.listToExcel(orderHeaderForNormalList, fieldMap, sheetName,-1,response,fileName);
            }else{
                // excel的sheetName
                String sheetName = "发运订单-明细";
                fieldMap = getOutDetilesInfo();
                //将list集合转化为excle
                ExcelUtil.listToExcel(orderDetailsForNormalListShow, fieldMap, sheetName,-1,response,fileName);
            }
            System.out.println("导出成功~~~~");
        } catch (ExcelException e) {
            e.printStackTrace();
        }

    }
    public LinkedHashMap<String, String> getOutOrderInfo() {

        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
        superClassMap.put("customerid", "客户编码");
        superClassMap.put("orderno", "SO编号");
        superClassMap.put("orderTypeName", "订单类型");
        superClassMap.put("orderStatusName", "订单状态");
        superClassMap.put("soreference1", "客户单号");
        superClassMap.put("route", "发运方式");
        superClassMap.put("soreference2", "定向入库单号");
        superClassMap.put("cAddress4", "快递单号");
        superClassMap.put("notes", "备注");
        superClassMap.put("edisendflag", "回传标识");
        superClassMap.put("ordertime", "创建时间");
        superClassMap.put("cContact", "收货方");
        superClassMap.put("cAddress1", "收货地址");
        superClassMap.put("cTel1", "联系方式");
        superClassMap.put("addwho", "创建人");
        superClassMap.put("courierComplaint", "快递投诉内容");
        return superClassMap;
    }
    public LinkedHashMap<String, String> getOutDetilesInfo() {

        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
        superClassMap.put("orderno", "单号");
        superClassMap.put("orderlineno", "行号");
        superClassMap.put("sku", "商品编码");
        superClassMap.put("lotatt04", "生产批号");
        superClassMap.put("lotatt05", "序列号");
        superClassMap.put("skuName", "商品名称");
        superClassMap.put("linestatus", "状态");
        superClassMap.put("qtyorderedEach", "订货数量");
        superClassMap.put("qtyordered", "订货件数");
        superClassMap.put("qtyallocatedEach", "分配数量");
        superClassMap.put("qtyallocated", "分配件数");
        superClassMap.put("qtypickedEach", "拣货数量");
        superClassMap.put("qtypicked", "拣货件数");
        superClassMap.put("qtyshippedEach", "发货数量");
        superClassMap.put("qtyshipped", "发货件数");
        superClassMap.put("alternativesku", "商品条码");
        superClassMap.put("lotatt01", "生产日期");
        superClassMap.put("lotatt02", "效期");
        superClassMap.put("lotatt03", "入库日期");
        superClassMap.put("lotatt06", "产品注册证");
        superClassMap.put("lotatt07", "灭菌批号");
        superClassMap.put("lotatt08", "供应商");
        superClassMap.put("lotatt09", "样品属性");
        superClassMap.put("lotatt10", "质量状态");
        superClassMap.put("lotatt11", "存储条件");
        superClassMap.put("lotatt12", "产品名称");
        superClassMap.put("lotatt13", "双证");
        superClassMap.put("lotatt14", "入库单号");
        superClassMap.put("lotatt15", "生产厂商名称");



        return superClassMap;
    }
    private String getYesNo(String obj){
        if(obj == null || obj.equals("0")||obj.equals("")){
            return "否";
        }else{
            return "是";
        }
    }
    private String getU1Name(String obj){
        if(obj.equals("KY")){
            return "空运";
        }else if(obj.equals("HY")){
            return "海运";
        }else{
            return "陆运";
        }
    }
    private String getU2Name(String obj){
        if(obj.equals("JF")){
            return "季付";
        }else{
            return "月结";
        }
    }

}
