package com.wms.service.sfExpress.sfXmlParse;

import com.wms.utils.StringUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.awt.image.ImagingOpException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 解析xml
 *
 * @author Haki
 * @date 2019/8/18
 */

public class XmlHelper {
    /**
     * 顺丰快递返回xml解析为实体bean
     *
     * @param content xml内容
     */
    public static ShunFengResponse xmlToBeanForSF(String content) {
        ShunFengResponse response = new ShunFengResponse();
        try {
            SAXReader saxReader = new SAXReader();
            Document docDom4j = saxReader.read(new ByteArrayInputStream(content.getBytes("utf-8")));
            Element root = docDom4j.getRootElement();
            String serviceValue = root.attributeValue("service");
            // 响应实体
            String head = root.elementTextTrim("Head");
            if ("OK".equals(head)) {
                response.setResultFlag(true);
                if ("OrderSearchService".equals(serviceValue) ||
                        "OrderService".equals(serviceValue)) {
                    // 下单返回结果处理 <元素>响应
                    Element orderResponse = root.element("Body").element("OrderResponse");
                    Element rlsInfo = orderResponse.element("rls_info");
                    List skills = root.element("Body").element("OrderResponse").elements("rls_info");
                    //顺丰运单号
                    String mailno = orderResponse.attributeValue("mailno");

                    /* 下单成功返回结果，参数具体含义看实体bean注释 */
                    CreateExpressOrderDTO expressOrderResponse = new CreateExpressOrderDTO();
                    //顺丰运单号
                    expressOrderResponse.setMailNo(mailno);
                    response.setMailNo(mailno);
                    //签回单号
                    String returnTrackingNo = orderResponse.attributeValue("return_tracking_no");
                    /** 签回单号 */
                    expressOrderResponse.setReturnTrackingNo(returnTrackingNo);
                    //丰密信息
                    List<RlsInfoDto> rlsInfoDtoList = new ArrayList<>(1);
                    //<OrderResponse/ rls_info/ rls_detail>
                    RlsInfoDto rlsInfoDto = new RlsInfoDto();
                    for (Iterator<Object> it = skills.iterator(); it.hasNext(); ) {
                        Element e = (Element) it.next();
                        String rlsErrormsgAll = e.attributeValue("rls_errormsg");
                        String rlsErrormsg = null;
                        if(rlsErrormsgAll == null){
                            response.setResultFlag(true);
                            response.setErrorMsg(head);
                            response.setMailNo("");
                            response.setOrderResponse(null);
                            response.setOpCode("");
                            response.setCom(null);
                            return response;
                        }else{
                             rlsErrormsg = rlsErrormsgAll.replaceAll(":","");

                        }
                        //<rls_detail  节点
                        Element rlsDetail = e.element("rls_detail");
                        //顺丰单号
                        if (mailno.equals(rlsErrormsg)){
                            //目的地区域代码,可用于顺丰电子面单标签打印
                            expressOrderResponse.setDestCode(orderResponse.attributeValue("destcode"));
                            //原寄地区域代码,可用于顺丰电子面单标签打印。
                            expressOrderResponse.setZipCode(orderResponse.attributeValue("origincode"));
                            //时效代码, 如:T4
                            expressOrderResponse.setLimitTypeCode(rlsDetail.attributeValue("limitTypeCode"));
                            //入港映射码
                            rlsInfoDto.setCodingMapping(rlsDetail.attributeValue("codingMapping"));
                            //出港映射码
                            rlsInfoDto.setCodingMappingOut(rlsDetail.attributeValue("codingMappingOut"));
                            //产品名称
                            rlsInfoDto.setProName(rlsDetail.attributeValue("proName"));
                            //目的地代码
                            rlsInfoDto.setDestRouteLabel(rlsDetail.attributeValue("destRouteLabel"));
                            // 打印图标
                            rlsInfoDto.setPrintIcon("00001000");
                            //时效类型
                            rlsInfoDto.setProCode(rlsDetail.attributeValue("proCode"));
                            //二维码
                            rlsInfoDto.setQrcode(rlsDetail.attributeValue("twoDimensionCode"));
                            //原寄地中转场
                            rlsInfoDto.setSourceTransferCode(rlsDetail.attributeValue("sourceTransferCode"));
                        }else if(returnTrackingNo.equals(rlsErrormsg)){
                            rlsInfoDto.setReturnestRouteLabel(rlsDetail.attributeValue("destRouteLabel"));
                            rlsInfoDto.setReturnQrcode(rlsDetail.attributeValue("twoDimensionCode"));
                            rlsInfoDto.setReturnCodingMapping(rlsDetail.attributeValue("codingMapping"));
                            rlsInfoDto.setReturnCodingMappingOut(rlsDetail.attributeValue("codingMappingOut"));
                            rlsInfoDto.setReturnSourceTransferCode(rlsDetail.attributeValue("sourceTransferCode"));

                        }
                    }
                    //把解析<rlsDetail>节点的信息加入到丰密订单集合
                    rlsInfoDtoList.add(rlsInfoDto);
                    //丰密信息加入到解析对象中
                    expressOrderResponse.setRlsInfoDtoList(rlsInfoDtoList);
                    //解析对象信息加入到顺丰响应实体
                    response.setOrderResponse(expressOrderResponse);
                }
            } else {
                response.setResultFlag(false);
                String headf = root.elementTextTrim("ERROR");
                response.setErrorMsg(headf);
                response.setMailNo("");
                response.setOrderResponse(null);
                response.setOpCode("");
                response.setCom(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //顺丰响应实体
        return response;
    }

    public static ShunFengResponse xmlToBeanForSFAb(String content) {
        ShunFengResponse response = new ShunFengResponse();
        try {
            SAXReader saxReader = new SAXReader();
            Document docDom4j = saxReader.read(new ByteArrayInputStream(content.getBytes("utf-8")));
            Element root = docDom4j.getRootElement();
            String serviceValue = root.attributeValue("service");
            // 响应实体
            String head = root.elementTextTrim("Head");
            if ("OK".equals(head)) {
                response.setResultFlag(true);
                response.setErrorMsg(head);
                response.setMailNo("");
                response.setOrderResponse(null);
                response.setOpCode("");
                response.setCom(null);
            } else {
                response.setResultFlag(false);
                String headf = root.elementTextTrim("ERROR");
                response.setErrorMsg(headf);
                response.setMailNo("");
                response.setOrderResponse(null);
                response.setOpCode("");
                response.setCom(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //顺丰响应实体
        return response;
    }

    public static void main(String[] args) {
        xmlToBeanForSFAb("<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderConfirmService\"><Head>OK</Head><ERROR code=\"8024\">未下单</ERROR></Response>\n");
    }

}
