package com.wms.service.sfExpress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下单请求连接方法
 * @author Haki
 * @date 2019/8/18
*/
public class CallExpressServiceTools {
    private static Logger logger = LoggerFactory.getLogger(CallExpressServiceTools.class);

    public static final String REQUEST_URL = "http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";

    //校验码
    public static final String CHECKWORD = "Ixi8k5Icv21UucHiZy7a627vO4NATqSm";
    //月卡号
    public static final String CUST_ID = "0213071013";

    public static final String COMPANY = "shunfeng";

    private CallExpressServiceTools() {
    }

    /**
     *发送请求(请求报文)
     * @param reqXML 请求报文
     * @return 响应的xml报文
     */
    public static String callSfExpressServiceByCSIM(String reqXML) {
        //验证码
        String verifyCode = VerifyCodeUtil.md5EncryptAndBase64(reqXML + CHECKWORD);
        /**
         * 结果
         */
        String result = querySFAPIservice(reqXML, verifyCode);
        return result;
    }

    /**
     * 询问顺丰的api服务
     * @param xml 反馈报文Xml的形式
     * @param verifyCode (效验码+请求报文 生成 验证码)
     * @return
     */
    public static String querySFAPIservice(String xml, String verifyCode) {
       HttpClientUtil httpclient = new HttpClientUtil();
        try {
            //post请求(请求地址、请求xml报文、验证码)
            String result = httpclient.postSFAPI(REQUEST_URL, xml, verifyCode);
            return result;
        } catch (Exception var6) {
            logger.warn(" " + var6);
            return null;
        }
    }
}