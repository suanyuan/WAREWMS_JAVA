package com.wms.service.importdata;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.*;
import com.wms.service.BasGtnLotattService;
import com.wms.service.GspVerifyService;
import com.wms.service.InvLotAttService;
import com.wms.utils.*;
import com.wms.vo.BasSkuVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wms.utils.exception.ExcelException;
import com.wms.vo.DocAsnHeaderVO;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.Json;

@Service("ImportAsnDataService")
public class ImportAsnDataService {

    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private BasLocationMybatisDao basLocationMybatisDao;
    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
    @Autowired
    private InvLotAttService invLotAttService;
    @Autowired
    private BasGtnLotattService basGtnLotattService;
    @Autowired
    private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;
    @Autowired
    private BasPackageMybatisDao basPackageMybatisDao;
    @Autowired
    private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
    @Autowired
    private GspVerifyService gspVerifyService;

    /**
     * 导入入库单
     *
     * @param excelFile
     * @return
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public Json importExcelData(MultipartFile excelFile) {
        Json json = new Json();
        boolean isSuccess = false;
        StringBuilder resultMsg = new StringBuilder();
        InputStream in;

        try {
            // 获取前台exce的输入流
            in = excelFile.getInputStream();

            //获取sheetName名字
            String sheetName = "入库单明细";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields = new String[]{"序号"};
            //获取需要导入的具体的表
            Class asn = new ImportAsnData().getClass();
            //excel转化成的list集合
            List<ImportAsnData> asnList = null;
            try {
                //调用excle共用类，转化成list
                asnList = ExcelUtil.excelToList(in, sheetName, asn, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
                json.setMsg(e.getMessage());
                json.setSuccess(false);
                return json;
            }
            //保存实体集合
            List<DocAsnHeaderVO> importDataList = this.listToBean(asnList, resultMsg);
            if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
                this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
                if (resultMsg.length() == 0) {
                  //  this.validateCustomerPermission(importDataList, resultMsg);// 验证客户权限是否存在 TODO 货主权限验证
                    if (resultMsg.length() == 0) {
                        this.validateSku(importDataList, resultMsg);// 验证商品是否存在
                        if (resultMsg.length() == 0) {
                            this.validateLocation(importDataList, resultMsg);// 验证库位是否存在
                            if (resultMsg.length() == 0) {
                                this.saveAsn(importDataList, resultMsg);// 转成订单资料存入资料库
                                isSuccess = true;
                            }
                        }
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        json.setMsg(resultMsg.toString());
        json.setSuccess(isSuccess);
        return json;
    }

    private List<DocAsnHeaderVO> listToBean(List<ImportAsnData> asnList, StringBuilder resultMsg) {
        StringBuilder rowResult = new StringBuilder();
        List<DocAsnHeaderVO> importData = new ArrayList<DocAsnHeaderVO>();
        DocAsnHeaderVO importDataVO = null;
        List<DocAsnDetailVO> importDetailsDataVOList = new ArrayList<DocAsnDetailVO>();
        DocAsnDetailVO importDetailsDataVO = null;
        String quantityData = null;
        Integer count = 1;
        SimpleDateFormat formatEx1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
        //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format.setLenient(false);
        formatRQ.setLenient(false);
        List<ImportAsnData> compareList = new ArrayList<>();
        for (ImportAsnData dataArray : asnList) {
            try {
                if (Integer.parseInt(dataArray.getSeq()) <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("序号：").append(dataArray.getSeq()).append(" 资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
            }
            try {
//                if (StringUtils.isEmpty(dataArray.getCustomerid().toUpperCase())) {
                if (StringUtils.isEmpty(dataArray.getCustomerid())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("序号：").append(dataArray.getSeq()).append("[货主代码]，未输入").append(" ");
            }
            try {
                if (StringUtils.isEmpty(dataArray.getAsnreference1())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("序号：").append(dataArray.getSeq()).append("[客户单号1]，未输入").append(" ");
            }
            try {
                if (StringUtils.isEmpty(dataArray.getExpectedarrivetime1())) {//判日期是否为空
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("序号：").append(dataArray.getSeq()).append("[预期到货时间]，未输入").append(" ");
            }
            try {
                format.parse(dataArray.getExpectedarrivetime1());
            } catch (ParseException e) {
                //如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                rowResult.append("序号：").append(dataArray.getSeq()).append("[预期到货时间]，格式错误").append(" ");
            }
            //生产日期、失效日期、入库日期co
            try {
                if (StringUtils.isNotEmpty(dataArray.getLotatt01())) {
                    formatRQ.parse(dataArray.getLotatt01());
                }
            } catch (ParseException e) {
                rowResult.append("序号：").append(dataArray.getSeq()).append("[生产日期]，格式错误").append(" ");
            }
            try {
                formatRQ.parse(dataArray.getLotatt02());
            } catch (ParseException e) {
                rowResult.append("序号：").append(dataArray.getSeq()).append("[失效日期]，格式错误").append(" ");
            }
            if (StringUtils.isNotEmpty(dataArray.getLotatt08())) {//判供应商是否为空
                    //gsp校验
                    Json verifyJson = gspVerifyService.verifyOperate(dataArray.getCustomerid(), dataArray.getLotatt08(), dataArray.getSku(), dataArray.getLotatt01(), dataArray.getLotatt02());
                    if (!verifyJson.isSuccess()) {
                        rowResult.append("序号：").append(dataArray.getSeq()).append(verifyJson.getMsg()).append(" ");
                    }
            }

			/*try {
				formatRQ.parse(dataArray.getLotatt03());
			} catch (ParseException e) {
				 rowResult.append("[入库日期]，格式错误").append(" ");
			}*/
            //重量、体积、单价
            try {
                if (ExcelUtil.isNotNumeric(dataArray.getTotalgrossweight())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[重量]，须为数字").append(" ");
            }
            try {
                if (ExcelUtil.isNotNumeric(dataArray.getTotalcubic())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[体积]，须为数字").append(" ");
            }
            try {
                if (ExcelUtil.isNotNumeric(dataArray.getTotalprice())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[单价]，须为数字").append(" ");
            }

            //库存状态
			/*try {
				if (StringUtils.isNotEmpty(dataArray.getLotatt04())){
					if(!(dataArray.getLotatt04().equals("HG") || dataArray.getLotatt04().equals("CP"))){
						throw new Exception();
					}
				}
			} catch (Exception e) {
				rowResult.append("[库存状态]，输入异常").append(" ");
			}*/
            /*try {
                quantityData = dataArray.getExpectedqty();
                if (StringUtils.isNotEmpty(quantityData)) {
                    if ((new BigDecimal(quantityData)).compareTo(BigDecimal.ZERO) == -1) {
                        throw new Exception();
                    } else if ((new BigDecimal(quantityData)).intValue() <= 0) {
                        throw new Exception();
                    }
                }
            } catch (Exception e) {
                rowResult.append("[订单数量]，资料格式转换失败，请输入不小于0的数字格式").append(" ");
            }*/

            if (dataArray.getExpectedqty() == null && dataArray.getExpectedqtyEach() == null) {
                rowResult.append("[件数、数量]，必须填入一个").append(" ");
            }

            if (rowResult.length() > 0) {
                if (rowResult.lastIndexOf("，") > -1) {
                    rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
                }
                resultMsg.append("序号：").append(dataArray.getSeq()).append("资料有错 ").append(rowResult).append(" ");
                rowResult.setLength(0);
            } else {


                if (count == 1) {
                    //第一行操作
                    importDataVO = new DocAsnHeaderVO();
                    importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
//                    importDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());//货主代码
                    importDataVO.setCustomerid(dataArray.getCustomerid());//货主代码
                    importDataVO.setAsnreference1(dataArray.getAsnreference1());
                    importDataVO.setAsnreference2(dataArray.getAsnreference2());
                    importDataVO.setAsntype(dataArray.getAsntype());
                    try {
                        importDataVO.setExpectedarrivetime1(format.parse(dataArray.getExpectedarrivetime1()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    importDetailsDataVO = new DocAsnDetailVO();
                    importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
//                    importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
                    importDetailsDataVO.setCustomerid(dataArray.getCustomerid());
//                    importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
                    importDetailsDataVO.setSku(dataArray.getSku());

                    if (!StringUtils.isEmpty(dataArray.getExpectedqty())) {
                        importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
                    } else {
                        importDetailsDataVO.setExpectedqty(BigDecimal.ZERO);
                    }
                    if (!StringUtils.isEmpty(dataArray.getExpectedqtyEach())) {
                        importDetailsDataVO.setExpectedqtyEach(new BigDecimal(dataArray.getExpectedqtyEach()));
                    } else {
                        importDetailsDataVO.setExpectedqtyEach(BigDecimal.ZERO);
                    }
                    importDetailsDataVO.setReceivinglocation(dataArray.getReceivinglocation());
                    importDetailsDataVO.setTotalgrossweight(new BigDecimal(dataArray.getTotalgrossweight()));
                    importDetailsDataVO.setTotalcubic(new BigDecimal(dataArray.getTotalcubic()));
                    importDetailsDataVO.setTotalprice(new BigDecimal(dataArray.getTotalprice()));
                    importDetailsDataVO.setLotatt01(dataArray.getLotatt01());
                    importDetailsDataVO.setLotatt02(dataArray.getLotatt02());
                    importDetailsDataVO.setLotatt03(dataArray.getLotatt03());
                    importDetailsDataVO.setLotatt04(dataArray.getLotatt04());
                    importDetailsDataVO.setLotatt05(dataArray.getLotatt05());
                    importDetailsDataVO.setLotatt06(dataArray.getLotatt06());
                    importDetailsDataVO.setLotatt07(dataArray.getLotatt07());
                    importDetailsDataVO.setLotatt08(dataArray.getLotatt08());
                    importDetailsDataVO.setLotatt09(dataArray.getLotatt09());
                    importDetailsDataVO.setLotatt11(dataArray.getLotatt11());
                    //查询条件的保存
                    importDetailsDataVO.setNotes(dataArray.getNotes());
                    List<DocAsnDetailVO> DocAsnDetailVoTake = new ArrayList<>();
                    DocAsnDetailVoTake.add(importDetailsDataVO);
//                     importDetailsDataVOList.add(importDetailsDataVO);
                    importDataVO.setDocAsnDetailVOList(DocAsnDetailVoTake);
                    importData.add(importDataVO);
                } else {
                    ImportAsnData compareData = new ImportAsnData();
                    compareData.setCustomerid(dataArray.getCustomerid());
                    compareData.setAsnreference1(dataArray.getAsnreference1());
                    compareData.setExpectedarrivetime1(dataArray.getExpectedarrivetime1());
                    //读取表中的数据与当前读取的数据相等

                    if (compareList.contains(compareData)) {

                        boolean isBreak = false;

                        DocAsnHeaderVO DocAsnHeaderVoSense = null;
                        for (DocAsnHeaderVO docAsnHeaderVO : importData) {

//                            System.out.println(formatEx1.format(docAsnHeaderVO.getExpectedarrivetime1()));
//                            System.out.println(compareData.getExpectedarrivetime1());

                            if (docAsnHeaderVO.getCustomerid().equals(compareData.getCustomerid()) &&
                                    docAsnHeaderVO.getAsnreference1().equals(compareData.getAsnreference1()) &&
                                    format.format(docAsnHeaderVO.getExpectedarrivetime1()).equals(compareData.getExpectedarrivetime1())) {
                                DocAsnHeaderVoSense = docAsnHeaderVO;
                            }
                            //遍历明细信息
                            for (DocAsnDetailVO docAsnDetailVO : docAsnHeaderVO.getDocAsnDetailVOList()) {
                                if (docAsnDetailVO.getCustomerid().equals(dataArray.getCustomerid()) &&
//                                        docAsnDetailVO.getSku().equals(dataArray.getSku().toUpperCase()) &&
                                        docAsnDetailVO.getSku().equals(dataArray.getSku()) &&
                                        docAsnDetailVO.getLotatt04().equals(dataArray.getLotatt04()) &&
                                        docAsnHeaderVO.getAsnreference1().equals(dataArray.getAsnreference1()) &&
                                        docAsnDetailVO.getLotatt05().equals(dataArray.getLotatt05())
                                ) {
                                    if (StringUtil.isEmpty(dataArray.getExpectedqtyEach())) {
                                        docAsnDetailVO.setExpectedqty(docAsnDetailVO.getExpectedqty().add(new BigDecimal(dataArray.getExpectedqty())));
                                    } else {
                                        docAsnDetailVO.setExpectedqtyEach(docAsnDetailVO.getExpectedqtyEach().add(new BigDecimal(dataArray.getExpectedqtyEach())));
                                    }
                                    isBreak = true;
                                    break;
                                }
                            }
                            if (isBreak) break;
                        }
                        if (!isBreak) {
                            //DocasnDetail new
//                          DocAsnDetailVO docAsnDetailVO = new DocAsnDetailVO();
                            importDetailsDataVO = new DocAsnDetailVO();
                            importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
//                            importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
                            importDetailsDataVO.setCustomerid(dataArray.getCustomerid());
//                            importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
                            importDetailsDataVO.setSku(dataArray.getSku());
                            //importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
                            //importDetailsDataVO.setExpectedqtyEach(new BigDecimal(dataArray.getExpectedqtyEach()));
                            if (!StringUtils.isEmpty(dataArray.getExpectedqty())) {
                                importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
                            } else {
                                importDetailsDataVO.setExpectedqty(BigDecimal.ZERO);
                            }
                            if (!StringUtils.isEmpty(dataArray.getExpectedqtyEach())) {
                                importDetailsDataVO.setExpectedqtyEach(new BigDecimal(dataArray.getExpectedqtyEach()));
                            } else {
                                importDetailsDataVO.setExpectedqtyEach(BigDecimal.ZERO);
                            }
                            importDetailsDataVO.setReceivinglocation(dataArray.getReceivinglocation());
                            importDetailsDataVO.setTotalgrossweight(new BigDecimal(dataArray.getTotalgrossweight()));
                            importDetailsDataVO.setTotalcubic(new BigDecimal(dataArray.getTotalcubic()));
                            importDetailsDataVO.setTotalprice(new BigDecimal(dataArray.getTotalprice()));
                            importDetailsDataVO.setLotatt01(dataArray.getLotatt01());
                            importDetailsDataVO.setLotatt02(dataArray.getLotatt02());
                            importDetailsDataVO.setLotatt03(dataArray.getLotatt03());
                            importDetailsDataVO.setLotatt04(dataArray.getLotatt04());
                            importDetailsDataVO.setLotatt05(dataArray.getLotatt05());
                            importDetailsDataVO.setLotatt06(dataArray.getLotatt06());
                            importDetailsDataVO.setLotatt07(dataArray.getLotatt07());
                            importDetailsDataVO.setLotatt08(dataArray.getLotatt08());
                            importDetailsDataVO.setLotatt09(dataArray.getLotatt09());
                            importDetailsDataVO.setLotatt11(dataArray.getLotatt11());
                            importDetailsDataVO.setNotes(dataArray.getNotes());
                            importDetailsDataVO.setNotes(dataArray.getNotes());
                            if (DocAsnHeaderVoSense != null)
                                DocAsnHeaderVoSense.getDocAsnDetailVOList().add(importDetailsDataVO);
                        }
                    } else {

                        //Header
                        importDataVO = new DocAsnHeaderVO();
                        importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));// 序号
//                        importDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());//货主代码
                        importDataVO.setCustomerid(dataArray.getCustomerid());//货主代码
                        importDataVO.setAsnreference1(dataArray.getAsnreference1());
                        importDataVO.setAsnreference2(dataArray.getAsnreference2());
                        importDataVO.setAsntype(dataArray.getAsntype());
                        try {
                            importDataVO.setExpectedarrivetime1(format.parse(dataArray.getExpectedarrivetime1()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //Detail
                        importDetailsDataVO = new DocAsnDetailVO();
                        importDetailsDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
//                        importDetailsDataVO.setCustomerid(dataArray.getCustomerid().toUpperCase());
//                        importDetailsDataVO.setSku(dataArray.getSku().toUpperCase());
                        importDetailsDataVO.setCustomerid(dataArray.getCustomerid());
                        importDetailsDataVO.setSku(dataArray.getSku());
                        //importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
                        //importDetailsDataVO.setExpectedqtyEach(new BigDecimal(dataArray.getExpectedqtyEach()));
                        if (!StringUtils.isEmpty(dataArray.getExpectedqty())) {
                            importDetailsDataVO.setExpectedqty(new BigDecimal(dataArray.getExpectedqty()));
                        } else {
                            importDetailsDataVO.setExpectedqty(BigDecimal.ZERO);
                        }
                        if (!StringUtils.isEmpty(dataArray.getExpectedqtyEach())) {
                            importDetailsDataVO.setExpectedqtyEach(new BigDecimal(dataArray.getExpectedqtyEach()));
                        } else {
                            importDetailsDataVO.setExpectedqtyEach(BigDecimal.ZERO);
                        }
                        importDetailsDataVO.setReceivinglocation(dataArray.getReceivinglocation());
                        importDetailsDataVO.setTotalgrossweight(new BigDecimal(dataArray.getTotalgrossweight()));
                        importDetailsDataVO.setTotalcubic(new BigDecimal(dataArray.getTotalcubic()));
                        importDetailsDataVO.setTotalprice(new BigDecimal(dataArray.getTotalprice()));
                        importDetailsDataVO.setLotatt01(dataArray.getLotatt01());
                        importDetailsDataVO.setLotatt02(dataArray.getLotatt02());
                        importDetailsDataVO.setLotatt03(dataArray.getLotatt03());
                        importDetailsDataVO.setLotatt04(dataArray.getLotatt04());
                        importDetailsDataVO.setLotatt05(dataArray.getLotatt05());
                        importDetailsDataVO.setLotatt06(dataArray.getLotatt06());
                        importDetailsDataVO.setLotatt07(dataArray.getLotatt07());
                        importDetailsDataVO.setLotatt08(dataArray.getLotatt08());
                        importDetailsDataVO.setLotatt09(dataArray.getLotatt09());
                        importDetailsDataVO.setLotatt11(dataArray.getLotatt11());
                        importDetailsDataVO.setNotes(dataArray.getNotes());
//                        importDetailsDataVOList.add(importDetailsDataVO);
                        List<DocAsnDetailVO> tempDetailDataList = new ArrayList<>();
                        tempDetailDataList.add(importDetailsDataVO);
                        importDataVO.setDocAsnDetailVOList(tempDetailDataList);
                        importData.add(importDataVO);
                    }

                }
                ImportAsnData compareData = new ImportAsnData();
                compareData.setCustomerid(dataArray.getCustomerid());
                compareData.setAsnreference1(dataArray.getAsnreference1());
//            compareData.setSku(dataArray.getSku());
//            compareData.setLotatt04(dataArray.getLotatt04());
                compareData.setExpectedarrivetime1(dataArray.getExpectedarrivetime1());
                compareList.add(compareData);


                //最后一行结束操作
//                if (count == asnList.size()) {
//                    importDataVO.setDocAsnDetailVOList(importDetailsDataVOList);
//                    importData.add(importDataVO);
//                }
            }

//            customerid = dataArray.getCustomerid();
//            asnreference1 = dataArray.getAsnreference1();
//            asnreference2 = dataArray.getAsnreference2();
//            expectedarrivetime1 = dataArray.getExpectedarrivetime1();
            count = count + 1;
        }
        for (DocAsnHeaderVO aa : importData) {
            System.out.println(aa.getSeq());
            System.out.println(aa.getCustomerid());
        }
        return importData;
    }

    public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
        // excel的表头与文字对应
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("序号", "seq");
        map.put("货主代码", "customerid");
        map.put("客户单号1", "asnreference1");
        map.put("客户采购单号", "asnreference2");
        map.put("入库单类型", "asntype");
        map.put("预期到货时间", "expectedarrivetime1");
        map.put("产品代码", "sku");
        map.put("预期数量", "expectedqtyEach");
        map.put("预期件数", "expectedqty");
        map.put("收货库位", "receivinglocation");
        map.put("重量", "totalgrossweight");
        map.put("体积", "totalcubic");
        map.put("单价", "totalprice");
        map.put("生产日期", "lotatt01");
        map.put("效期", "lotatt02");
        map.put("生产批号", "lotatt04");
        map.put("序列号", "lotatt05");
        map.put("产品注册证号", "lotatt06");
        map.put("灭菌批号", "lotatt07");
        map.put("供应商代码", "lotatt08");
        map.put("样品属性", "lotatt09");
        map.put("存储条件", "lotatt11");
        return map;
    }

    private void validateSku(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
        BasSku sku = null;
        BasSkuQuery skuQuery = new BasSkuQuery();
        for (DocAsnHeaderVO importDataVO : importDataList) {
            for (DocAsnDetailVO importDetailsDataVO : importDataVO.getDocAsnDetailVOList()) {
                skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
                skuQuery.setSku(importDetailsDataVO.getSku());
                sku = basSkuMybatisDao.queryById(skuQuery);
                if (sku == null) {
                    resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
                            .append("，货主代码：").append(importDataVO.getCustomerid())
                            .append("，产品代码：").append(importDetailsDataVO.getSku()).append("，查无资料").append(" ");
                }else if (!sku.getActiveFlag().equals(Constant.IS_USE_YES)) {
                    resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
                            .append("，货主代码：").append(importDataVO.getCustomerid())
                            .append("，产品代码：").append(importDetailsDataVO.getSku()).append("，产品已失效").append(" ");
                }
            }
        }
    }

    private void validateLocation(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
        BasLocation loc = null;
        BasLocationQuery locQuery = new BasLocationQuery();
        for (DocAsnHeaderVO importDataVO : importDataList) {
            for (DocAsnDetailVO importDetailsDataVO : importDataVO.getDocAsnDetailVOList()) {
                if (StringUtils.isNotEmpty(importDetailsDataVO.getReceivinglocation())) {
                    locQuery.setLocationid(importDetailsDataVO.getReceivinglocation());
                    loc = basLocationMybatisDao.queryById(locQuery);
                    if (loc == null) {
                        resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
                                .append("，库位编码：").append(importDetailsDataVO.getReceivinglocation()).append("，查无资料").append(" ");
                    }
                }
            }
        }
    }

    private void validateCustomer(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
        BasCustomer customer = null;
        BasCustomerQuery customerQuery = new BasCustomerQuery();
        for (DocAsnHeaderVO importDataVO : importDataList) {
            customerQuery.setCustomerid(importDataVO.getCustomerid());
            customerQuery.setCustomerType("OW");
            customer = basCustomerMybatisDao.queryByIdType(customerQuery.getCustomerid(), customerQuery.getCustomerType());
            if (customer == null) {// 是否有客户资料
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码查无客户资料").append(" ");
            }else
            if(customer.getActiveFlag().equals(Constant.IS_USE_NO)){
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码合作状态为未合作状态").append(" ");

            }
        }
    }

    private void validateCustomerPermission(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
        BasCustomer customer = null;
        BasCustomerQuery customerQuery = new BasCustomerQuery();
        for (DocAsnHeaderVO importDataVO : importDataList) {
            customerQuery.setCustomerid(importDataVO.getCustomerid());
            customerQuery.setCustomerType("OW");
            customerQuery.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
            customer = basCustomerMybatisDao.queryById(customerQuery);
            if (customer == null) {// 是否有客户权限
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码查无客户权限").append(" ");
            }

        }
    }

    private void saveAsn(List<DocAsnHeaderVO> importDataList, StringBuilder resultMsg) {
        DocAsnHeader asnHeader = null;
        Boolean addfalg = true;
        for (DocAsnHeaderVO importDataVO : importDataList) {
            asnHeader = new DocAsnHeader();
            BeanUtils.copyProperties(importDataVO, asnHeader);
            //获取SO编号
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            docAsnHeaderMybatisDao.getIdSequence(map);
            String resultCode = map.get("resultCode").toString();
            String resultNo = map.get("resultNo").toString();
            if (resultCode.substring(0, 3).equals("000")) {
                for (DocAsnDetailVO importDetailsDataVO : importDataVO.getDocAsnDetailVOList()) {

                    //判断预入库明细里面的sku和客户id下的18个批属是否存在
                    /*DocAsnDetail docAsnDetail = new DocAsnDetail();
                    BeanUtils.copyProperties(importDetailsDataVO, docAsnDetail);
                    InvLotAtt invLotAtt = invLotAttService.queryInsertLotatts(docAsnDetail);
                    //判断是否要插入扫码批次匹配表
                    basGtnLotattService.queryInsertGtnLotatt(invLotAtt, importDetailsDataVO.getAsnno());*/

                    DocAsnDetail asnDetails = new DocAsnDetail();
                    BeanUtils.copyProperties(importDetailsDataVO, asnDetails);
                    asnDetails.setAsnno(resultNo);
                    DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
                    docAsnDetailQuery.setAsnno(resultNo);

                    //获取订单明细行号
                    int asnlineno = docAsnDetailsMybatisDao.getAsnlinenoById(docAsnDetailQuery);
                    //获取SKU信息(条码、包装、重量、体积、金额)
                    BasSkuQuery skuQuery = new BasSkuQuery();
                    skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
                    skuQuery.setSku(importDetailsDataVO.getSku());
//					    skuQuery.setQty(importDetailsDataVO.getExpectedqty());
                    BasSku basSku = basSkuMybatisDao.queryById(skuQuery);


                    //有件数计算数量
                    BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());

                    if (!(importDetailsDataVO.getExpectedqty().compareTo(BigDecimal.ZERO) == 0) && !(importDetailsDataVO.getExpectedqtyEach().compareTo(BigDecimal.ZERO) == 0)) {
                        asnDetails.setExpectedqtyEach(importDetailsDataVO.getExpectedqtyEach());
                        asnDetails.setExpectedqty(importDetailsDataVO.getExpectedqty());
                    } else {
                        //有件数计算
                        if (!(importDetailsDataVO.getExpectedqty().compareTo(BigDecimal.ZERO) == 0)) {
                            asnDetails.setExpectedqtyEach(basPackage.getQty1().multiply(importDetailsDataVO.getExpectedqty()));
                            asnDetails.setExpectedqty(importDetailsDataVO.getExpectedqty());
                        } else if (!(importDetailsDataVO.getExpectedqtyEach().compareTo(BigDecimal.ZERO) == 0)) {//有数量计算件数
                            //asnDetails.setExpectedqtyEach(basPackage.getQty1().multiply(importDetailsDataVO.getExpectedqty()));
                            if (importDetailsDataVO.getExpectedqtyEach().doubleValue() % basPackage.getQty1().doubleValue() == 0) {
                                double qty = importDetailsDataVO.getExpectedqtyEach().doubleValue() / basPackage.getQty1().doubleValue();
                                asnDetails.setExpectedqtyEach(importDetailsDataVO.getExpectedqtyEach());
                                asnDetails.setExpectedqty(new BigDecimal(qty));
                            } else {
                                resultMsg.append("序号：").append(importDataVO.getSeq()).append("数量计算件数失败，不是整数").append(" ");
                                continue;
                            }
                        }

                    }

                    //入库日期
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    asnDetails.setLotatt03(formatter.format(new Date()));

                    //产品注册证
                    if (StringUtils.isEmpty(asnDetails.getLotatt06())) {
                        asnDetails.setLotatt06(basSku.getReservedfield03());
                    }

                    if (asnDetails.getLotatt08().equals("") || asnDetails.getLotatt08() == null) {
                        Json verifyJson = gspVerifyService.verifyOperate(asnDetails.getCustomerid(),basSku.getSkuGroup6(), asnDetails.getSku(), asnDetails.getLotatt01(), asnDetails.getLotatt02());
                        if (!verifyJson.isSuccess()) {
                            addfalg = false;
                            resultMsg.append(verifyJson.getMsg()).append(" ");
                            continue;
                        }else {
                            asnDetails.setLotatt08(basSku.getSkuGroup6());
                        }
                    }

                    //样品属性
                    if (StringUtils.isEmpty(asnDetails.getLotatt09())) {
                        asnDetails.setLotatt09("ZC");
                    }

                    //质量状态
                    asnDetails.setLotatt10("DJ");

                    //储存条件
                    asnDetails.setLotatt11(basSku.getSkuGroup4());

                    //产品名称
                    asnDetails.setLotatt12(basSku.getReservedfield01());

                    //预入库单号
                    asnDetails.setLotatt14(resultNo);
                    //生产厂家
                    if (StringUtil.isNotEmpty(asnDetails.getLotatt06())) {
                        PdaGspProductRegister productRegister = gspProductRegisterMybatisDao.queryByNo(asnDetails.getLotatt06());
                        //生产厂家
                        if (productRegister != null && productRegister.getEnterpriseInfo() != null) {

                            asnDetails.setLotatt15(productRegister.getEnterpriseInfo().getEnterpriseName());
                        }

                    }

                    //赋值
                    asnDetails.setAsnlineno(asnlineno + 1);
                    asnDetails.setPackid(basSku.getPackid());
                    asnDetails.setAlternativesku(basSku.getAlternateSku1());
                    //体积重量单价若不输入则从SKU里读取
                    if (importDetailsDataVO.getTotalgrossweight().compareTo(BigDecimal.ZERO) == 1) {
                        asnDetails.setTotalgrossweight(importDetailsDataVO.getTotalgrossweight());
                    } else {
                        asnDetails.setTotalgrossweight(basSku.getGrossweight());
                    }
                    if (importDetailsDataVO.getTotalcubic().compareTo(BigDecimal.ZERO) == 1) {
                        asnDetails.setTotalcubic(importDetailsDataVO.getTotalcubic());
                    } else {
                        asnDetails.setTotalcubic(basSku.getCube());
                    }
                    if (importDetailsDataVO.getTotalprice().compareTo(BigDecimal.ZERO) == 1) {
                        asnDetails.setTotalprice(importDetailsDataVO.getTotalprice());
                    } else {
                        asnDetails.setTotalprice(basSku.getPrice());
                    }
                    asnDetails.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
                    asnDetails.setEditwho(SfcUserLoginUtil.getLoginUser().getId());

                    if (asnHeader.getAsntype().equals(DocAsnHeader.ASN_TYPE_DX) &&
                            (importDetailsDataVO.getReceivinglocation() == null ||
                                    importDetailsDataVO.getReceivinglocation().length() == 0)) {
                        asnDetails.setReceivinglocation(DocAsnDetail.DX_RECEIVING_LOCATION);//定向订单库位
                    }

                    //判断是否要插入扫码批次匹配表
                    BasGtnLotattQuery basGtnLotattQuery = new BasGtnLotattQuery();
                    basGtnLotattQuery.setCustomerid(asnDetails.getCustomerid());
                    basGtnLotattQuery.setSku(asnDetails.getSku());
                    basGtnLotattQuery.setLotatt02(asnDetails.getLotatt02());
                    basGtnLotattQuery.setLotatt04(asnDetails.getLotatt04());
                    basGtnLotattQuery.setLotatt05(asnDetails.getLotatt05());
                    basGtnLotattService.queryInsertGtnLotatt(basGtnLotattQuery);
                    //保存订单明细信息
                    docAsnDetailsMybatisDao.add(asnDetails);
                }
                if(addfalg == true){
                    //赋值
                    //asnHeader.setAsntype("PR");
                    asnHeader.setAsntype(importDataVO.getAsntype());
                    asnHeader.setReleasestatus("Y");
                    asnHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                    asnHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
                    asnHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
                    asnHeader.setAsnno(resultNo);
                    docAsnHeaderMybatisDao.add(asnHeader);
                    resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
                }
            } else {
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("预入库单号获取失败").append(" ");
            }
        }
    }

	/*public BasSku getSkuBy(String sku,String customerId){
		BasSkuQuery query = new BasSkuQuery();
		query.setCustomerid(customerId);
		query.setSku(sku);

	}*/
}
