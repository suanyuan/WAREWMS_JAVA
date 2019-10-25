package com.wms.service.sfExpress;

import java.text.SimpleDateFormat;


import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.utils.StringUtil;
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
        //如:TB1207300000001
        strBuilder.append("orderid='" + orderHeaderForNormal.getOrderno() + "22" + "'").append(" ");
        //返回顺丰运单号
        /*
         运输方式
         您好，可以通过更改“express_type”值来选择不同的产品类型，
         请查阅https://qiao.sf-express.com/pages/developDoc/index.html?level2=689001选择符合您需求的产品。
         1 - 顺丰标快
         2 - 顺丰特惠
         TODO 现在顺丰下单都是标快，不对，需要区分特惠、标快（丰桥）根据发运方式下不同的类型，还要区分快递公司，如果是顺丰的才去下顺丰的单子。其他的快递公司最好提供一个填写快递单号的地方用来记录一下。
         */
        strBuilder.append("express_type='1'").append(" ");
        //寄件方信息
        strBuilder.append("j_province='" + "上海市" + "'").append(" ");
        strBuilder.append("j_city='" + "上海市" + "'").append(" ");
        strBuilder.append("j_county='" + "浦东新区" + "'").append(" ");
        strBuilder.append("j_company='" + "哈尔滨四圣商贸有限公司" + "'").append(" ");
        strBuilder.append("j_contact='" + "郑洁" + "'").append(" ");
        strBuilder.append("j_tel='" + "021-62091927" + "'").append(" ");
        strBuilder.append("j_address='" + "施湾八路1026号2号楼" + "'").append(" ");

        strBuilder.append("d_province='" + orderHeaderForNormal.getCProvince() + "'").append(" ");//省
        strBuilder.append("d_city='" + orderHeaderForNormal.getCCity() + "'").append(" ");//市
        strBuilder.append("d_county='" + orderHeaderForNormal.getCAddress2() + "'").append(" ");//区
        strBuilder.append("d_company='" + orderHeaderForNormal.getConsigneeid() + "'").append(" ");//到件方公司名称
        if (StringUtil.isEmpty(orderHeaderForNormal.getCTel1())) {
            strBuilder.append("d_tel='" + orderHeaderForNormal.getCTel2() + "'").append(" ");//到件方联系电话
        }else {
            strBuilder.append("d_tel='" + orderHeaderForNormal.getCTel1() + "'").append(" ");//到件方联系电话
        }
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

        //付款方式:1寄方付 2.收支付 3.第三方付
        /*
        月结 - 1 寄方付
        到付 - 2 收支付
         */
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