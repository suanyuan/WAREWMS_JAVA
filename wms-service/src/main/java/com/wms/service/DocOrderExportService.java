package com.wms.service;

import com.wms.entity.BasCodes;
import com.wms.entity.BasCustomer;
import com.wms.entity.BasSku;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.form.DocOrderHeaderExportForm;
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


    /**
     * 打印order检查记录
     */
    public List<OrderHeaderForNormal> docOrderToExcel(String order) {
        List<OrderHeaderForNormal> orderHeaderForNormalList=new ArrayList<>();
        //获得单头
        OrderHeaderForNormal orderHeaderForNormal=orderHeaderForNormalMybatisDao.queryById(order);
        //获得细单
        List<OrderDetailsForNormal> orderDetailsForNormalList=orderDetailsForNormalMybatisDao.queryByOrderNo(order);
        if(orderHeaderForNormal!=null&&orderDetailsForNormalList!=null){
//            头像显示地址和电话  发运结算方式
            orderHeaderForNormal.setExcaddress1(orderHeaderForNormal.getCAddress1()==null?"":orderHeaderForNormal.getCAddress1());
            orderHeaderForNormal.setExctel1(orderHeaderForNormal.getCTel1()==null?"":orderHeaderForNormal.getCTel1());
            if(orderHeaderForNormal.getUserdefine1()!=null) {
                orderHeaderForNormal.setUserdefine1(getU1Name(orderHeaderForNormal.getUserdefine1()));
            }
            if(orderHeaderForNormal.getUserdefine2()!=null) {
                orderHeaderForNormal.setUserdefine2(getU2Name(orderHeaderForNormal.getUserdefine2()));
            }
            double qtyorderedSum=0;   //订货件数总和
            double qtyorderedEachSum=0;   //订货数量总和
            for (OrderDetailsForNormal d : orderDetailsForNormalList) {
//                供应商中文
                BasCustomer basCustomer=basCustomerMybatisDao.queryByCustomerId(d.getLotatt08());
                if(basCustomer!=null){
                    d.setLotatt08(basCustomer.getDescrC());
                }
                //计算总数
                qtyorderedSum+=d.getQtyordered();
                qtyorderedEachSum+=d.getQtyorderedEach();

                d.setQtyorderedSum(qtyorderedSum);
                d.setQtyorderedEachSum(qtyorderedEachSum);
                BasSku basSku = basSkuService.getSkuInfo(d.getCustomerid(),d.getSku());
//                细单部分01转换是否
                if(basSku!=null){
                    d.setCard(getYesNo(basSku.getSkuGroup2()));
                    d.setReport(getYesNo(basSku.getSkuGroup8()));
                    d.setDoublec(getYesNo(basSku.getSkuGroup7()));
                    d.setDescrc(basSku.getDescrC());
                }
            }

            orderHeaderForNormal.setOrderDetailsForNormalList(orderDetailsForNormalList);
        }
        orderHeaderForNormalList.add(orderHeaderForNormal);
        return orderHeaderForNormalList;
    }

    public void docOrderToExcel1(HttpServletResponse response, DocOrderHeaderExportForm from) throws UnsupportedEncodingException, ExcelException {
        Cookie cookie = new Cookie("exportToken",from.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());

        try{
            //获得单头
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(from));
            mybatisCriteria.setOrderByClause("orderno desc");
            List<OrderHeaderForNormal> orderHeaderForNormalList=orderHeaderForNormalMybatisDao.queryByList(mybatisCriteria);
            //发运方式 ZT BK LY 暂缓
            for (OrderHeaderForNormal a:orderHeaderForNormalList) {
                Map<String, Object> param = new HashMap<>();
                param.put("codeid", "EXP_TYP");
                param.put("code", a.getRoute());
                BasCodes bascodes = basCodesMybatisDao.queryById(param);
                if(bascodes!=null){
                    a.setRoute(bascodes.getCodenameC());
                }
            }
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getCustomerProductLeadToFiledPublicQuestionBank();
            // excel的sheetName
            String sheetName = "发运订单";
            //导出表格名称
            String timeNow=sdf.format(new Date());
            String fileName="发运订单"+timeNow;
            //将list集合转化为excle
            ExcelUtil.listToExcel(orderHeaderForNormalList, fieldMap, sheetName,-1,response,fileName);
            System.out.println("导出成功~~~~");
        } catch (ExcelException e) {
            e.printStackTrace();
        }

    }
    public LinkedHashMap<String, String> getCustomerProductLeadToFiledPublicQuestionBank() {

        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
        superClassMap.put("customerid", "客户编码");
        superClassMap.put("orderStatusName", "订单状态");
        superClassMap.put("orderno", "SO编号");
        superClassMap.put("soreference1", "客户单号");
        superClassMap.put("route", "发运方式");
        superClassMap.put("soreference2", "定向入库单号");
        superClassMap.put("cAddress4", "快递单号");
        superClassMap.put("ordertime", "创建时间");
        superClassMap.put("orderTypeName", "订单类型");
        superClassMap.put("cContact", "收货方");
        superClassMap.put("cAddress1", "收货地址");
        superClassMap.put("cTel1", "联系方式");
        superClassMap.put("addwho", "创建人");
        superClassMap.put("notes", "备注");
        superClassMap.put("courierComplaint", "快递投诉内容");
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
