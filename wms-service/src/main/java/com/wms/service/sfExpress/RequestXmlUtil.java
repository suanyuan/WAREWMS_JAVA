package com.wms.service.sfExpress;

import java.text.SimpleDateFormat;


import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.vo.form.OrderHeaderForNormalForm;

/**
 * 顺丰请求的工具类
 *
 * @author 李宇
 */
public class RequestXmlUtil {

    /**
     * 顾客编码
     */
    public static final String CLIENT_CODE = "HJXXJS";


    /**
     * 获取下单请求体
     */
    public static String getOrderServiceRequestXml(OrderHeaderForNormal orderHeaderForNormal, String returnSfOrder) {


        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<?xml version='1.0' encoding='UTF-8'?>");
        strBuilder.append("<Request service='OrderService' lang='zh-CN'>");
        strBuilder.append("<Head>" + CLIENT_CODE + "</Head>");
        strBuilder.append("<Body>");
        strBuilder.append("<Order").append(" ");
        //客户订单号,建议英文字母+
        //YYMMDD(日期)+流水号,
        //如:TB1207300000001  TODO 测试使用流水号
        strBuilder.append("orderid='" + orderHeaderForNormal.getOrderno() + "22" + "'").append(" ");
        //返回顺丰运单号  //运输方式
        strBuilder.append("express_type='1'").append(" ");
        //寄件方信息
        strBuilder.append("j_province='" + "江苏省" + "'").append(" ");
        strBuilder.append("j_city='" + "太仓市" + "'").append(" ");
        strBuilder.append("j_county='" + "经济开发区" + "'").append(" ");
        strBuilder.append("j_company='" + "数宗信息技术有限公司" + "'").append(" ");
        strBuilder.append("j_contact='" + "Skr" + "'").append(" ");
        strBuilder.append("j_tel='" + "13246025290" + "'").append(" ");
        strBuilder.append("j_address='" + "北京东路77号(中德大厦)16楼" + "'").append(" ");
        //收件方信息
//        strBuilder.append("d_province='" + "广东省" + "'").append(" ");//省
//        strBuilder.append("d_city='" + "广州市" + "'").append(" ");//市
//        strBuilder.append("d_county='" + "白云区" + "'").append(" ");//区
//        strBuilder.append("d_company='" + "四胜贸易有限公司"+ "'").append(" ");//到件方公司名称
//        strBuilder.append("d_tel='" + "15674991723" + "'").append(" ");//到件方联系电话
//        strBuilder.append("d_contact='" + "高俊" + "'").append(" ");//到件方联系人
//        strBuilder.append("d_address='" + "白云东路130号" + "'").append(" ");//到件方详细地址

        strBuilder.append("d_province='" + orderHeaderForNormal.getCProvince() + "'").append(" ");//省
        strBuilder.append("d_city='" + orderHeaderForNormal.getCCity() + "'").append(" ");//市
        strBuilder.append("d_county='" + orderHeaderForNormal.getCAddress2() + "'").append(" ");//区
        strBuilder.append("d_company='" + orderHeaderForNormal.getConsigneeid() + "'").append(" ");//到件方公司名称
        strBuilder.append("d_tel='" + orderHeaderForNormal.getCTel1() + "'").append(" ");//到件方联系电话
        strBuilder.append("d_contact='" + orderHeaderForNormal.getCContact() + "'").append(" ");//到件方联系人
        strBuilder.append("d_address='" + orderHeaderForNormal.getCAddress1() + "'").append(" ");//到件方详细地址
        //备注不为空就进行xml append()拼接
       /* try {
            if(!expressOrder.getRemark().equals(" ") || expressOrder != null){
                strBuilder.append("remark = '"+expressOrder.getRemark()+"'").append(" ");
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

        //温度范围类型 1为冷藏   3为冷冻
        //strBuilder.append("temp_range='1'").append(" ");
        //是否要求签回单号  根据前端选中是否
        if (returnSfOrder.equals("1")) {
            strBuilder.append("need_return_tracking_no='1'").append(" ");
            strBuilder.append("routelabelService='1'").append(" ");
        }

        //货物信息
        strBuilder.append("parcel_quantity='1'").append(" ");
        //货物-总-重量
        strBuilder.append("cargo_total_weight='1'").append(" ");
        //需要写实体类获取（没有实现）
        strBuilder.append("custid ='" + CallExpressServiceTools.CUST_ID + "'").append(" ");//顺丰月结卡号

        //付款方式:1寄付 2.收支付 3.第三方付
        strBuilder.append("pay_method = '" + "1" + "'").append(" ");
        strBuilder.append(" is_unified_waybill_no='1'>").append(" ");

        //海关批次（默认空的）
        // strBuilder.append("customs_batchs=''").append(" ");
        //货物类型
        strBuilder.append("<Cargo").append(" ");
        strBuilder.append("name='医药器械'>").append(" ");
        strBuilder.append("</Cargo>").append(" ");
        strBuilder.append("</Order>");
        strBuilder.append("</Body>");
        strBuilder.append("</Request>");
        return strBuilder.toString();
    }


    /**
     * 取消顺丰订单请求体
     *
     * @param orderNo 订单号
     * @return
     */
    public static String getOrderCancelServiceRequestXml(String orderNo) {

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<Request service='OrderConfirmService' lang='zh-CN'>");
        strBuilder.append("<Head>" + CLIENT_CODE + "</Head>");
        strBuilder.append("<Body>");
        strBuilder.append("<OrderConfirm").append(" ");
        strBuilder.append("orderid='" + orderNo + "'").append(" ");
        strBuilder.append("dealtype='2'");
        strBuilder.append(" /> ");
        strBuilder.append("</Body>");
        strBuilder.append("</Request>");
        return strBuilder.toString();
    }
}