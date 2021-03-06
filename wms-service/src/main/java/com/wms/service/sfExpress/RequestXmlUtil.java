package com.wms.service.sfExpress;

import com.wms.entity.sfExpress.SFOrderHeader;
import com.wms.service.sfExpress.sfXmlParse.RlsInfoDto;
import com.wms.service.sfExpress.sfXmlParse.ShunFengResponse;
import com.wms.service.sfExpress.sfXmlParse.XmlHelper;
import com.wms.utils.StringUtil;

import java.util.List;

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
    public static String getOrderServiceRequestXml(SFOrderHeader sfOrderHeader, String returnSfOrder) {


        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<?xml version='1.0' encoding='UTF-8'?>");
        strBuilder.append("<Request service='OrderService' lang='zh-CN'>");
        strBuilder.append("<Head>" + CLIENT_CODE + "</Head>");
        strBuilder.append("<Body>");
        strBuilder.append("<Order").append(" ");
        //客户订单号,建议英文字母+
        //YYMMDD(日期)+流水号,
        //如:TB1207300000001
        strBuilder.append("orderid='" + sfOrderHeader.getOrderno() + "'").append(" ");
        //返回顺丰运单号
        /*
         运输方式
         您好，可以通过更改“express_type”值来选择不同的产品类型，
         请查阅https://qiao.sf-express.com/pages/developDoc/index.html?level2=689001选择符合您需求的产品。
         1 - 顺丰标快
         2 - 顺丰特惠
         */
        if (sfOrderHeader.getRoute().equals("TH")) {
            strBuilder.append("express_type='2'").append(" ");
        } else {
            strBuilder.append("express_type='1'").append(" ");
        }
        //寄件方信息
        strBuilder.append("j_province='" + "上海市" + "'").append(" ");
        strBuilder.append("j_city='" + "上海市" + "'").append(" ");
        strBuilder.append("j_county='" + "浦东新区" + "'").append(" ");
        strBuilder.append("j_company='").append(sfOrderHeader.getJ_company()).append("'").append(" ");
        strBuilder.append("j_contact='").append(sfOrderHeader.getJ_contact()).append("'").append(" ");
        strBuilder.append("j_tel='").append(sfOrderHeader.getJ_tel()).append("'").append(" ");
        strBuilder.append("j_address='" + "施湾八路1026号2号楼" + "'").append(" ");

        strBuilder.append("d_province='").append(sfOrderHeader.getCProvince()).append("'").append(" ");//省
        strBuilder.append("d_city='").append(sfOrderHeader.getCCity()).append("'").append(" ");//市
        strBuilder.append("d_county='").append(sfOrderHeader.getCAddress2()).append("'").append(" ");//区
        strBuilder.append("d_company='").append(sfOrderHeader.getConsigneeid()).append("'").append(" ");//到件方公司名称
        if (StringUtil.isEmpty(sfOrderHeader.getCTel1())) {
            strBuilder.append("d_tel='").append(sfOrderHeader.getCTel2()).append("'").append(" ");//到件方联系电话
        } else {
            strBuilder.append("d_tel='").append(sfOrderHeader.getCTel1()).append("'").append(" ");//到件方联系电话
        }
        strBuilder.append("d_contact='").append(sfOrderHeader.getCContact()).append("'").append(" ");//到件方联系人
        strBuilder.append("d_address='").append(sfOrderHeader.getCAddress1()).append("'").append(" ");//到件方详细地址
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
            strBuilder.append("routelabelForReturn='1'").append(" ");//签回单路由标签返回:
            strBuilder.append("routelabelService='1'").append(" ");

        }

        //货物信息
        strBuilder.append("parcel_quantity='1'").append(" ");
        //货物-总-重量
        strBuilder.append("cargo_total_weight='1'").append(" ");
        //需要写实体类获取
        strBuilder.append("custid ='" + sfOrderHeader.getCustid() + "'").append(" ");//顺丰月结卡号

        //付款方式:1寄方付(月结) 2.收方付(到付) 3.第三方付(不用)
        if (StringUtil.isNotEmpty(sfOrderHeader.getStop()) && sfOrderHeader.getStop().equals("DF")) {

            strBuilder.append("pay_method = '" + "2" + "'").append(" ");
        } else {

            strBuilder.append("pay_method = '" + "1" + "'").append(" ");
        }
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
     * 查询顺丰订单请求体
     * @param orderNo 订单号
     */
    public static String getOrderSearchServiceRequestXml(String orderNo) {

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<Request service='OrderSearchService' lang='zh-CN'>");
        strBuilder.append("<Head>" + CLIENT_CODE + "</Head>");
        strBuilder.append("<Body>");
        strBuilder.append("<OrderSearch").append(" ");
        strBuilder.append("orderid='" + orderNo + "'").append(" ");
        strBuilder.append("dealtype='2'");
        strBuilder.append(" /> ");
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