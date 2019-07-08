package com.wms.constant;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/6/26
 */
public class Constant {

//  数据是否有效
    public static final String IS_USE_YES = "1"; //有效
    public static final String IS_USE_NO = "0";  //无效

    public static final String LICENSE_SUBMIT_ADD = "add";
    public static final String LICENSE_SUBMIT_UPDATE = "update";

    public static final String LICENSE_TYPE_BUSINESS = "BUSINESS";
    public static final String LICENSE_TYPE_OPERATE = "OPERATE";
    public static final String LICENSE_TYPE_RECORD = "RECORD";

//  codeid
    public static final String CODE_CATALOG_CLASSIFY = "CATALOG_CLASSIFY"; //器械目录分类
    public static final String CODE_CATALOG_VERSION = "CATALOG_VERSION"; //器械目录版本
    public static final String CODE_CATALOG_FIRSTSTATE = "CATALOG_FIRSTSTATE"; //首营状态
    public static final String CODE_CATALOG_ENTTYPE = "ENT_TYP";//企业类型

//  企业类型
    public static final String CODE_ENT_TYP_JY = "JY";//经营
    public static final String CODE_ENT_TYP_GNSC = "GNSC";//国内生产
    public static final String CODE_ENT_TYP_GWSC = "GWSC";//国外生产
    public static final String CODE_ENT_TYP_KD = "KD";//快递
    public static final String CODE_ENT_TYP_YL = "YL";//医疗单位
    public static final String CODE_ENT_TYP_ZT = "ZT";//主体


//是否
    public static final String CODE_YES_OR_YES = "1";
    public static final String CODE_YES_OR_NO = "0";

//Seq
    public static final String APLCUSNO = "APLCUSNO";//委托客户申请
    public static final String APLSUPNO = "APLSUPNO";//供应商申请
    public static final String APLRECNO = "APLRECNO";//收货单位申请
    public static final String APLPRONO = "APLPRONO";//产品申请

    //code
    //首营状态
    public static final String CODE_CATALOG_FIRSTSTATE_NEW = "00"; //首营状态(新建)
    public static final String CODE_CATALOG_FIRSTSTATE_CHECKING = "10"; //首营状态(审核中)
    public static final String CODE_CATALOG_FIRSTSTATE_USELESS = "90"; //首营状态(已报废)
    public static final String CODE_CATALOG_FIRSTSTATE_STOP = "60"; //首营状态(已停止)
    public static final String CODE_CATALOG_FIRSTSTATE_PASS = "40"; //首营状态(审核通过)

    //审核状态
    public static final String CODE_CATALOG_CHECKSTATE_NEW = "00"; //(新建)
    public static final String CODE_CATALOG_CHECKSTATE_QCCHECKING = "20"; //(质量部审核)
    public static final String CODE_CATALOG_CHECKSTATE_RESPONSIBLE = "30"; //(负责人审核)
    public static final String CODE_CATALOG_CHECKSTATE_PASS = "40"; //(已通过)
    public static final String CODE_CATALOG_CHECKSTATE_FAIL = "50"; //(未通过)









//  PDA专属 (╯‵□′)╯︵┻━┻ ********************* <<<<<<<<<<<

    public static final String DATA = "data";

    public static final String RESULT = "result";

    public static final String SUCCESS_MSG = "获取成功";

    public static final int pageSize = 10;

    public static final String PROCEDURE_OK = "000";//存储过程执行成功返回值
}