package com.wms.service.sfExpress.sfXmlParse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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
                    //<rls_detail  节点
                    Element rlsDetail = orderResponse.element("rls_info").element("rls_detail");
                    //顺丰运单号
                    String mailno = orderResponse.attributeValue("mailno");
                    response.setMailNo(mailno);

                    /* 下单成功返回结果，参数具体含义看实体bean注释 */
                    CreateExpressOrderDTO expressOrderResponse = new CreateExpressOrderDTO();
                    //顺丰运单号
                    expressOrderResponse.setMailNo(mailno);
                    //目的地区域代码,可用于顺丰电子面单标签打印
                    expressOrderResponse.setDestCode(orderResponse.attributeValue("destcode"));
                    //原寄地区域代码,可用于顺丰电子面单标签打印。
                    expressOrderResponse.setZipCode(orderResponse.attributeValue("origincode"));
                    //时效代码, 如:T4
                    expressOrderResponse.setLimitTypeCode(rlsDetail.attributeValue("limitTypeCode"));
                    //丰密信息
                    List<RlsInfoDto> rlsInfoDtoList = new ArrayList<>(1);
                    //<OrderResponse/ rls_info/ rls_detail>
                    RlsInfoDto rlsInfoDto = new RlsInfoDto();
                    //入港映射码
                    rlsInfoDto.setCodingMapping(rlsDetail.attributeValue("codingMapping"));
                    //出港映射码
                    rlsInfoDto.setCodingMappingOut("");
                    //产品名称
                    rlsInfoDto.setProName(rlsDetail.attributeValue("proName"));
                    System.err.println(rlsInfoDto.getProName());
                    rlsInfoDto.setDestRouteLabel(rlsDetail.attributeValue("destRouteLabel"));
                    // 打印图标
                    rlsInfoDto.setPrintIcon("00001000");
                    //时效类型
                    rlsInfoDto.setProCode(rlsDetail.attributeValue("proCode"));
                    //二维码
                    //根据规则生成字符串信息,
                    //格式为MMM={'k1':'(目的地中转场代码)','k2':'(目的地原始网点代码)',
                    // 'k3':'(目的地单元区域)',
                    // 'k4':'(附件通过三维码(express_type_code、 limit_type_code、 cargo_type_code)映射时效类型)',
                    // 'k5':'(运单号)','k6':'(AB标识)','k7':'(校验码)'}
                    rlsInfoDto.setQrcode(rlsDetail.attributeValue("twoDimensionCode"));
                    //原寄地中转场
                    rlsInfoDto.setSourceTransferCode(rlsDetail.attributeValue("sourceTransferCode"));
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //顺丰响应实体
        return response;
    }
}
