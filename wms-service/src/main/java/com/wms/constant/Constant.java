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

    public static final String LICENSE_TYPE_BUSINESS = "BUSINESS";//营业
    public static final String LICENSE_TYPE_OPERATE = "OPERATE"; //经营
    public static final String LICENSE_TYPE_RECORD = "RECORD";   //二类
    public static final String LICENSE_TYPE_REGISTER = "REGISTER";
    public static final String LICENSE_TYPE_PROD = "PROD";      //生产
    public static final String LICENSE_TYPE_MEDICAL = "MEDICAL";      //医疗
    public static final String LICENSE_TYPE_FIRSTRECORD = "FIRSTRECORD"; //一类



    //  codeid
    public static final String CODE_CATALOG_CLASSIFY = "CATALOG_CLASSIFY"; //器械目录分类
    public static final String CODE_CATALOG_VERSION = "CATALOG_VERSION"; //器械目录版本
    public static final String CODE_CATALOG_FIRSTSTATE = "CATALOG_FIRSTSTATE"; //首营状态
    public static final String CODE_CATALOG_CHECKSTATE = "CATALOG_CHECKSTATE"; //审核状态
    public static final String CODE_CATALOG_ENTTYPE = "ENT_TYP";//企业类型
    public static final String CODE_CATALOG_CUS_TYP = "CUS_TYP";//客户类型
    public static final String CODE_CATALOG_UOM = "UOM";//单位
    public static final String CODE_CATALOG_SAMPLEATTR = "YP_TYP";//样品属性
    public static final String CODE_CATALOG_SENDFUNCTION = "EXP_TYP";//发运方式
    public static final String CODE_CATALOG_SENDCOMPANY = "EXP_CO";//发运公司
    public static final String CODE_CATALOG_SETTLEMENT = "JS_FS";//结算方式
    public static final String CODE_CATALOG_QCSTATE = "ZL_TYP";//质量状态
    public static final String CODE_CATALOG_COLDHAINMARK= "LL_TYP";//冷链标志
    public static final String CODE_CATALOG_PASTS= "PA_STS";//上架状态
    public static final String CODE_CATALOG_MT_STS= "MT_STS";//养护状态
    public static final String CODE_CATALOG_COU_REQUEST= "CO_STS";//盘点状态
    public static final String CODE_CATALOG_CLASSIFY_ONE = "CLASSIFY_ONE";//分类一
    public static final String CODE_CATALOG_CLASSIFY_TWO = "CLASSIFY_TWO";//分类二
    public static final String CODE_CATALOG_CLASSIFY_THREE = "CLASSIFY_THREE";//分类三
    public static final String CODE_ASN_TYP = "ASN_TYP";//入库类型
    public static final String CODE_CATALOG_SF_EXPRESS = "SF_SET";//月结账号



//  企业类型
    public static final String CODE_ENT_TYP_JY = "JY";//经营
    public static final String CODE_ENT_TYP_GNSC = "GNSC";//国内生产
    public static final String CODE_ENT_TYP_GWSC = "GWSC";//国外生产
    public static final String CODE_ENT_TYP_KD = "KD";//快递
    public static final String CODE_ENT_TYP_YL = "YL";//医疗单位
    public static final String CODE_ENT_TYP_ZT = "ZT";//主体
    public static final String CODE_ENT_TYP_GW = "GW";

//  客户类型
    public static final String CODE_CUS_TYP_CA = "CA";//承运商
    public static final String CODE_CUS_TYP_CO = "CO";//收货单位
    public static final String CODE_CUS_TYP_OT = "OT";//其他
    public static final String CODE_CUS_TYP_OW = "OW";//货主
    public static final String CODE_CUS_TYP_PR = "PR";//生产企业
    public static final String CODE_CUS_TYP_VE = "VE";//供应商
    public static final String CODE_CUS_TYP_WH = "WH";//主体


// 权限
    public static final String USER_GRADE_NONE = "00";
    public static final String USER_GRADE_QC = "10";
    public static final String USER_GRADE_HEAD = "01";
    public static final String USER_GRADE_QCHEAD = "11";



//是否
    public static final String CODE_YES_OR_YES = "1";
    public static final String CODE_YES_OR_NO = "0";

//Seq
    public static final String APLCUSNO = "APCUS";//委托客户申请
    public static final String APLSUPNO = "APSUP";//供应商申请
    public static final String APLRECNO = "APREC";//收货单位申请
    public static final String APLPRONO = "APPRO";//产品申请

    public static final String BASRECNO = "BASRECNO";//收货单位客户档案编号
    public static final String BASSUPNO = "BASSUPNO";//供应商客户档案编号

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

    //jasper导出格式
    public static final String JASPER_CSV = "csv";
    public static final String JASPER_HTML = "html";
    public static final String JASPER_PDF = "pdf";
    public static final String JASPER_XLS = "xls";

//GSP开关
    public static final String QC_FLAG = "QC_FLAG";

    public static final String uploadUrl = "/Users/quendi/fileUpload";
//    public static final String uploadUrl = "/root/uploadDir";






//  PDA专属 (╯‵□′)╯︵┻━┻ ********************* <<<<<<<<<<<

    public static final String DATA = "data";

    public static final String RESULT = "result";

    public static final String SUCCESS_MSG = "获取成功";

    public static final String SUBMIT_SUCCESS_MSG = "提交成功";

    public static final int pageSize = 10;

    public static final String PROCEDURE_OK = "000";//存储过程执行成功返回值
}