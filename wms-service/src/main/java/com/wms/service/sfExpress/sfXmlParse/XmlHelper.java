package com.wms.service.sfExpress.sfXmlParse;

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
     * @param content
     * @return
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
                if ("OrderService".equals(serviceValue)) {
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
                        String rlsErrormsg = e.attributeValue("rls_errormsg").replaceAll(":","");
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

    public static void main(String[] args) throws IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Response service=\"OrderService\"><Head>OK</Head><Body>\n" +
                "    <OrderResponse filter_result=\"2\" destcode=\"512\" mailno=\"SF1013398203430\" return_tracking_no=\"SF1060269897732\" origincode=\"021\" orderid=\"SO2019110800002\">\n" +
                "      <rls_info rls_errormsg=\"SF1013398203430:\" invoke_result=\"OK\" rls_code=\"1000\">\n" +
                "        <rls_detail \n" +
                "        \twaybillNo=\"SF1013398203430\" \n" +
                "        \tsourceCityCode=\"021\" \n" +
                "        \tdestCityCode=\"512\" \n" +
                "        \tdestDeptCode=\"512TU\" \n" +
                "        \tdestDeptCodeMapping=\"512WB\" \n" +
                "        \tdestTeamCode=\"008\" \n" +
                "        \tdestTransferCode=\"512WB\" \n" +
                "        \tdestRouteLabel=\"512WB-512TU\" \n" +
                "        \tproName=\"顺丰标快\" \n" +
                "        \tcargoTypeCode=\"C201\" \n" +
                "        \tlimitTypeCode=\"T4\" \n" +
                "        \texpressTypeCode=\"B1\" \n" +
                "        \tcodingMapping=\"B54\" \n" +
                "        \tcodingMappingOut=\"2K\" \n" +
                "        \txbFlag=\"0\" \n" +
                "        \tprintFlag=\"000000000\" \n" +
                "        \ttwoDimensionCode=\"MMM={'k1':'512WB','k2':'512TU','k3':'008','k4':'T4','k5':'SF1013398203430','k6':'','k7':'63df5ee6'}\" \n" +
                "        \tproCode=\"T4\" \n" +
                "        \tprintIcon=\"00001000\" \n" +
                "        \tcheckCode=\"63df5ee6\" \n" +
                "        \tdestGisDeptCode=\"512TU\"/>\n" +
                "      </rls_info>\n" +
                "      <rls_info rls_errormsg=\"SF1060269897732:\" invoke_result=\"OK\" rls_code=\"1000\">\n" +
                "        <rls_detail \n" +
                "        \twaybillNo=\"SF1060269897732\" \n" +
                "        \tsourceCityCode=\"512\" \n" +
                "        \tdestCityCode=\"021\" \n" +
                "        \tdestDeptCode=\"021NA\" \n" +
                "        \tdestTeamCode=\"141\" \n" +
                "        \tdestTransferCode=\"021WG\" \n" +
                "        \tdestRouteLabel=\"021WG-021NA\" \n" +
                "        \tproName=\"顺丰标快\" \n" +
                "        \tcargoTypeCode=\"C201\" \n" +
                "        \tlimitTypeCode=\"T4\" \n" +
                "        \texpressTypeCode=\"B1\"\n" +
                "        \tcodingMapping=\"5NA\" \n" +
                "        \txbFlag=\"0\" \n" +
                "        \tprintFlag=\"000000000\" \n" +
                "        \ttwoDimensionCode=\"MMM={'k1':'021WG','k2':'021NA','k3':'141','k4':'T4','k5':'SF1060269897732','k6':'','k7':'e752f43b'}\" \n" +
                "        \tproCode=\"T4\" \n" +
                "        \tprintIcon=\"00001000\" \n" +
                "        \tcheckCode=\"e752f43b\" \n" +
                "        \tdestGisDeptCode=\"021NA\"/>\n" +
                "      </rls_info>\n" +
                "    </OrderResponse>\n" +
                "  </Body>\n" +
                "</Response>\n";


        String xml1 = "<?xml version='1.0' encoding='UTF-8'?><Response service=\"OrderService\"><Head>OK</Head><Body><OrderResponse filter_result=\"2\" destcode=\"371\" mailno=\"SF1013146859755\" return_tracking_no=\"SF1060256007334\" origincode=\"021\" orderid=\"SO2019102700002\"><rls_info rls_errormsg=\"SF1013146859755:\" invoke_result=\"OK\" rls_code=\"1000\"><rls_detail waybillNo=\"SF1013146859755\" sourceCityCode=\"021\" destCityCode=\"371\" destDeptCode=\"371BA\" destTeamCode=\"003\" destTransferCode=\"371WA\" destRouteLabel=\"371WA\" proName=\"顺丰标快\" cargoTypeCode=\"C201\" limitTypeCode=\"T4\" expressTypeCode=\"B1\" codingMapping=\"S6\" codingMappingOut=\"1B\" xbFlag=\"0\" printFlag=\"000000000\" twoDimensionCode=\"MMM={'k1':'371WA','k2':'371BA','k3':'003','k4':'T4','k5':'SF1013146859755','k6':'','k7':'d4050f29'}\" proCode=\"T4\" printIcon=\"00001000\" checkCode=\"d4050f29\" destGisDeptCode=\"371BA\"/></rls_info></OrderResponse></Body></Response>";
        xmlToBeanForSF(xml);
    }
}
