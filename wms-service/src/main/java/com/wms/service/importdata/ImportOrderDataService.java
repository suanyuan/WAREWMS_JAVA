package com.wms.service.importdata;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.constant.Constant;
import com.wms.entity.BasPackage;
import com.wms.query.BasPackageQuery;
import com.wms.service.BasPackageService;
import com.wms.service.CommonService;
import com.wms.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wms.entity.BasSku;
import com.wms.entity.BasCustomer;
import com.wms.entity.ImportOrderData;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.OrderDetailsForNormalMybatisDao;
import com.wms.mybatis.dao.OrderHeaderForNormalMybatisDao;
import com.wms.query.BasCustomerQuery;
import com.wms.query.BasSkuQuery;
import com.wms.query.OrderDetailsForNormalQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.OrderDetailsForNormalVO;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.Json;

@Service("ImportOrderDataService")
public class ImportOrderDataService {

    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
    @Autowired
    private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BasPackageService basPackageService;

    /**
     * 导入订单数据
     *
     * @param
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
            String sheetName = "订单明细";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields = new String[]{"序号"};
            //获取需要导入的具体的表
            Class order = new ImportOrderData().getClass();
            //excel转化成的list集合
            List<ImportOrderData> orderList = null;
            try {
                //调用excle共用类，转化成list
                orderList = ExcelUtil.excelToList(in, sheetName, order, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
                json.setMsg(e.getMessage());
                json.setSuccess(false);
                return json;
            }
            //保存实体集合
            List<OrderHeaderForNormalVO> importDataList = this.listToBean(orderList, resultMsg);
            if (resultMsg.length() == 0 && importDataList != null && importDataList.size() > 0) {
                this.validateCustomer(importDataList, resultMsg);// 验证客户是否存在
                if (resultMsg.length() == 0) {
                    //this.validateCustomerPermission(importDataList, resultMsg);// 验证客户权限是否存在
                    if (resultMsg.length() == 0) {
                        this.validateSku(importDataList, resultMsg);// 验证商品是否存在
                        if (resultMsg.length() == 0) {
                            this.saveOrder(importDataList, resultMsg);// 转成订单资料存入资料库
                            isSuccess = true;
                        }
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            resultMsg.append("Excel表格中字段缺少或输入有误").append(" ");
            json.setMsg(resultMsg.toString());
            return json;
        }
        json.setMsg(resultMsg.toString());
        json.setSuccess(isSuccess);
        return json;
    }

    private List<OrderHeaderForNormalVO> listToBean(List<ImportOrderData> orderList, StringBuilder resultMsg) {
        StringBuilder rowResult = new StringBuilder();
        List<OrderHeaderForNormalVO> importData = new ArrayList<OrderHeaderForNormalVO>();
        OrderHeaderForNormalVO importDataVO = null;
        List<OrderDetailsForNormalVO> importDetailsDataVOList = new ArrayList<OrderDetailsForNormalVO>();
        OrderDetailsForNormalVO importDetailsDataVO = null;
        String quantityData = null;
        Integer count = 1;
        String customerId = "", soreference1 = "", requiredDeliveryTime = "", cContact = "", address = "", tel = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format.setLenient(false);
        for (ImportOrderData dataArray : orderList) {
            try {
                if (Integer.parseInt(dataArray.getSeq()) <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
            }
            try {
                if (StringUtils.isEmpty(dataArray.getCustomerid())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[客户代码]，未输入").append(" ");
            }
            try {
                if (StringUtils.isEmpty(dataArray.getCustomerid())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[订单号]，未输入").append(" ");
            }
			/*try {
				if (StringUtils.isEmpty(dataArray.getRequiredDeliveryTime())) {//判日期是否为
					throw new Exception();
				}
			} catch (Exception e) {
				rowResult.append("[预计送达时间]，未输入").append(" ");
			}
			try {
				 format.parse(dataArray.getRequiredDeliveryTime());
			} catch (ParseException e) {
				 //如果throw java.text.ParseException或者NullPointerException，就说明格式不对
				 rowResult.append("[预计送达时间]，格式错误").append(" ");
			}*/
            try {
                if (StringUtils.isEmpty(dataArray.getcContact())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[收货人]，未输入").append(" ");
            }
            try {
                if (StringUtils.isEmpty(dataArray.getcAddress1())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[收货地址]，未输入").append(" ");
            }
            try {
                if (StringUtils.isEmpty(dataArray.getcTel1())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[联系电话]，未输入").append(" ");
            }
            try {
                if (StringUtils.isEmpty(dataArray.getSku())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[产品代码]，未输入").append(" ");
            }
            try {
                if (StringUtils.isNotEmpty(dataArray.getLotatt08())) {
                    BasCustomer customer = null;
                    BasCustomerQuery customerQuery = new BasCustomerQuery();
                    customerQuery.setCustomerid(dataArray.getLotatt08());
                    customerQuery.setCustomerType(Constant.CODE_CUS_TYP_VE);
                    customer = basCustomerMybatisDao.queryByIdType(customerQuery.getCustomerid(), customerQuery.getCustomerType());
                    if (customer == null) {// 是否有供应商资料
                       throw new Exception();
                    }
                    try {
                        if (customer.getActiveFlag().equals(Constant.IS_USE_NO)){
                            throw new Exception();
                        }
                    }catch (Exception e){
                        rowResult.append("[供应商代码],合作状态为未合作").append(" ");
                    }
                }
            } catch (Exception e) {
                rowResult.append("[供应商代码],查无供应商资料").append(" ");
            }

            try {
                if ((dataArray.getQtyordered() == null || dataArray.getQtyordered().equals("")) && (dataArray.getQtyorderedEach() == null || dataArray.getQtyorderedEach().equals(""))) {
                    throw new Exception();
                }
                if (ExcelUtil.isNotNumeric(dataArray.getQtyordered()) && !dataArray.getQtyordered().equals("")) {
                    rowResult.append("[件数]，必须为数字").append(" ");
                }
                if (ExcelUtil.isNotNumeric(dataArray.getQtyorderedEach()) && !dataArray.getQtyorderedEach().equals("")) {
                    rowResult.append("[数量]，必须为数字").append(" ");
                }
            } catch (Exception e) {
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
                    importDataVO = new OrderHeaderForNormalVO();
                    importDataVO.setSeq(dataArray.getSeq());// 序号
                    importDataVO.setCustomerid(dataArray.getCustomerid());//客户编号
                    importDataVO.setSoreference1(dataArray.getSoreference1());
                    importDataVO.setSoreference2(dataArray.getSoreference2());
					/*try {
						importDataVO.setRequiredDeliveryTime(format.parse(dataArray.getRequiredDeliveryTime()));
					} catch (ParseException e) {
						 rowResult.append("[预计送达时间]，格式错误").append(" ");
					}*/
                    importDataVO.setOrderTypeName(dataArray.getOrderTypeName());
                    importDataVO.setCContact(dataArray.getcContact());
                    importDataVO.setCAddress1(dataArray.getcAddress1());
                    importDataVO.setCTel1(dataArray.getcTel1());
                    importDataVO.setCarrierid(dataArray.getCarrierid());
                    importDataVO.setUserdefine1(dataArray.getUserdefine1());
                    importDataVO.setUserdefine2(dataArray.getUserdefine2());

                    importDetailsDataVO = initOrderDetails(dataArray);
                    importDetailsDataVOList.add(importDetailsDataVO);
                } else if (dataArray.getCustomerid().equals(customerId) &&
                        dataArray.getSoreference1().equals(soreference1) &&
                        //dataArray.getRequiredDeliveryTime().equals(requiredDeliveryTime) &&
                        dataArray.getcContact().equals(cContact) &&
                        dataArray.getcAddress1().equals(address) &&
                        dataArray.getcTel1().equals(tel)) {
                    //表头信息一致则只增加明细信息
                    importDetailsDataVO = initOrderDetails(dataArray);
                    importDetailsDataVOList.add(importDetailsDataVO);
                } else {
                    //表头信息不一致则生成新的订单
                    importDataVO.setOrderDetailsForNormalVOList(importDetailsDataVOList);
                    importData.add(importDataVO);
                    importDetailsDataVOList = new ArrayList<OrderDetailsForNormalVO>();
                    importDataVO = new OrderHeaderForNormalVO();

                    importDataVO.setSeq(dataArray.getSeq());// 序号
                    importDataVO.setCustomerid(dataArray.getCustomerid());//客户代码
                    importDataVO.setSoreference1(dataArray.getSoreference1());
                    importDataVO.setSoreference2(dataArray.getSoreference2());

                    importDataVO.setOrderTypeName(dataArray.getOrderTypeName());
                    importDataVO.setCContact(dataArray.getcContact());
                    importDataVO.setCAddress1(dataArray.getcAddress1());
                    importDataVO.setCTel1(dataArray.getcTel1());

                    importDetailsDataVO = initOrderDetails(dataArray);
                    importDetailsDataVOList.add(importDetailsDataVO);
                }
                //最后一行结束操作
                if (count == orderList.size()) {
                    importDataVO.setOrderDetailsForNormalVOList(importDetailsDataVOList);
                    importData.add(importDataVO);
                }
                customerId = importDataVO.getCustomerid();
                soreference1 = importDataVO.getSoreference1();
                //requiredDeliveryTime = dataArray.getRequiredDeliveryTime();
                cContact = importDataVO.getCContact();
                address = importDataVO.getCAddress1();
                tel = importDataVO.getCTel1();
                count = count + 1;
            }


        }
        for (OrderHeaderForNormalVO aa : importData) {
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
        map.put("客户单号1", "soreference1");
        map.put("定向入库单号", "soreference2");
        map.put("订单类型", "orderTypeName");
        map.put("收货人", "cContact");
        map.put("收货地址", "cAddress1");
        map.put("收货人电话", "cTel1");
        map.put("产品代码", "sku");
        map.put("快递公司", "carrierid");
        map.put("发运方式", "userdefine1");
        map.put("快递结算方式", "userdefine2");
        map.put("订单件数", "qtyordered");
        map.put("订单数量", "qtyorderedEach");
        map.put("生产日期", "lotatt01");
        map.put("效期", "lotatt02");
        map.put("入库日期", "lotatt03");
        map.put("生产批号", "lotatt04");
        map.put("序列号", "lotatt05");
        map.put("产品注册证号", "lotatt06");
        map.put("灭菌批号", "lotatt07");
        map.put("供应商", "lotatt08");
        map.put("样品属性", "lotatt09");
        map.put("质量状态", "lotatt10");
        map.put("存储条件", "lotatt11");
        map.put("产品名称", "lotatt12");
        map.put("双证", "lotatt13");
        map.put("入库单号", "lotatt14");
        map.put("生产厂商名称", "lotatt15");
        return map;
    }

    private void validateSku(List<OrderHeaderForNormalVO> importDataList, StringBuilder resultMsg) {
        BasSku sku = null;
        BasSkuQuery skuQuery = new BasSkuQuery();
        for (OrderHeaderForNormalVO importDataVO : importDataList) {
            for (OrderDetailsForNormalVO importDetailsDataVO : importDataVO.getOrderDetailsForNormalVOList()) {
                skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
                skuQuery.setSku(importDetailsDataVO.getSku());
                sku = basSkuMybatisDao.queryById(skuQuery);
                if (sku == null) {
                    resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
                            .append("，货主代码：").append(importDataVO.getCustomerid())
                            .append("，产品代码：").append(importDetailsDataVO.getSku()).append("，产品代码查无商品资料").append(" ");
                }else if (!sku.getActiveFlag().equals(Constant.IS_USE_YES)) {
                    resultMsg.append("序号：").append(importDetailsDataVO.getSeq())
                            .append("，货主代码：").append(importDataVO.getCustomerid())
                            .append("，产品代码：").append(importDetailsDataVO.getSku()).append("，产品已失效").append(" ");
                }
            }
        }
    }



    private void validateCustomer(List<OrderHeaderForNormalVO> importDataList, StringBuilder resultMsg) {
        BasCustomer customer = null;
        BasCustomerQuery customerQuery = new BasCustomerQuery();
        for (OrderHeaderForNormalVO importDataVO : importDataList) {
            customerQuery.setCustomerid(importDataVO.getCustomerid());
            customerQuery.setCustomerType("OW");
            customer = basCustomerMybatisDao.queryByIdType(customerQuery.getCustomerid(), customerQuery.getCustomerType());
            if (customer == null) {// 是否有客户资料
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码查无客户资料").append(" ");
            }else
            if (customer.getActiveFlag().equals(Constant.IS_USE_NO)) {
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码合作状态为未合作状态").append(" ");

            }
        }
    }

    private void validateCustomerPermission(List<OrderHeaderForNormalVO> importDataList, StringBuilder resultMsg) {
        BasCustomer customer = null;
        BasCustomerQuery customerQuery = new BasCustomerQuery();
        for (OrderHeaderForNormalVO importDataVO : importDataList) {
            customerQuery.setCustomerid(importDataVO.getCustomerid());
            customerQuery.setCustomerType("OW");
            customerQuery.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
            customer = basCustomerMybatisDao.queryById(customerQuery);
            if (customer == null) {// 是否有客户权限
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("，货主代码查无客户权限").append(" ");
            }
        }
    }

    private void saveOrder(List<OrderHeaderForNormalVO> importDataList, StringBuilder resultMsg) {
        OrderHeaderForNormal orderHeader = null;
        for (OrderHeaderForNormalVO importDataVO : importDataList) {
            orderHeader = new OrderHeaderForNormal();
            BeanUtils.copyProperties(importDataVO, orderHeader);
            //获取SO编号
			/*Map<String ,Object> map=new HashMap<String, Object>();
			map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			orderHeaderForNormalMybatisDao.getIdSequence(map);
			String resultCode = map.get("resultCode").toString();
			String resultNo = map.get("resultNo").toString();*/
            String resultNo = commonService.generateSeq("ORDERNO", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            if (!StringUtils.isEmpty(resultNo)) {
                //赋值
                orderHeader.setOrderno(resultNo);
                orderHeader.setOrdertype(importDataVO.getOrderTypeName());
                orderHeader.setOrdertime(new Date());
                orderHeader.setConsigneeid(importDataVO.getConsigneeid());
                orderHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                orderHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
                orderHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
                orderHeader.setOrdertime(new Date());
                orderHeader.setEdittime(new Date());
                orderHeader.setEdisendflag(Constant.IS_USE_YES);
                orderHeader.setArchiveflag(Constant.IS_USE_YES);
                orderHeader.setSostatus("00");
                orderHeader.setReleasestatus("Y");
                orderHeader.setSoreference1(importDataVO.getSoreference1());
                orderHeader.setSoreference2(importDataVO.getSoreference2());
                orderHeader.setConsigneeid(importDataVO.getConsigneeid());
                //保存订单主信息
                orderHeaderForNormalMybatisDao.add(orderHeader);
                for (OrderDetailsForNormalVO importDetailsDataVO : importDataVO.getOrderDetailsForNormalVOList()) {
                    OrderDetailsForNormal orderDetails = new OrderDetailsForNormal();
                    BeanUtils.copyProperties(importDetailsDataVO, orderDetails);
                    orderDetails.setOrderno(resultNo);
                    OrderDetailsForNormalQuery orderDetailsForNormalQuery = new OrderDetailsForNormalQuery();
                    orderDetailsForNormalQuery.setOrderno(resultNo);
                    //获取订单明细行号
                    int orderlineno = orderDetailsForNormalMybatisDao.getOrderLineNoById(orderDetailsForNormalQuery);
                    //获取SKU信息(条码、包装、重量、体积、金额)
                    BasSkuQuery skuQuery = new BasSkuQuery();
                    skuQuery.setCustomerid(importDetailsDataVO.getCustomerid());
                    skuQuery.setSku(importDetailsDataVO.getSku());
                    skuQuery.setQty(new BigDecimal(importDetailsDataVO.getQtyordered()));
                    BasSku basSku = basSkuMybatisDao.queryBySkuInfo(skuQuery);
                    //赋值
                    if (basSku != null) {
                        BasPackageQuery query = new BasPackageQuery();
                        query.setPackid(basSku.getPackid());
                        BasPackage basPackage = basPackageService.queryBasPackBy(query);
                        orderDetails.setUom(basPackage.getPackuom1());
                        if (importDetailsDataVO.getQtyordered() > 0 && importDetailsDataVO.getQtyorderedEach() > 0) {
                            orderDetails.setQtyorderedEach(importDetailsDataVO.getQtyorderedEach());
                            orderDetails.setQtyordered(importDetailsDataVO.getQtyordered());
                        } else {
                            //有件数计算
                            if (importDetailsDataVO.getQtyordered() > 0) {
                                orderDetails.setQtyorderedEach(basPackage.getQty1().doubleValue() * (importDetailsDataVO.getQtyordered()));
                                orderDetails.setQtyordered(importDetailsDataVO.getQtyordered());
                            } else if (importDetailsDataVO.getQtyorderedEach() > 0) {
                                //有数量计算件数
                                //orderDetails.setQtyorderedEach(basPackage.getQty1().multiply(importDetailsDataVO.getQtyordered()));
                                if (importDetailsDataVO.getQtyorderedEach() % basPackage.getQty1().doubleValue() == 0) {
                                    double qty = importDetailsDataVO.getQtyorderedEach() / basPackage.getQty1().doubleValue();
                                    orderDetails.setQtyorderedEach(importDetailsDataVO.getQtyorderedEach());
                                    orderDetails.setQtyordered(qty);
                                } else {
                                    resultMsg.append("序号：").append(importDataVO.getSeq()).append("数量计算件数失败，不是整数").append(" ");
                                    continue;
                                }
                            }

                        }
                    }
                    orderDetails.setPackid(basSku.getPackid());
                    orderDetails.setOrderlineno((double) (orderlineno + 1));
                    orderDetails.setNetweight(basSku.getGrossweight().doubleValue());
                    orderDetails.setGrossweight(basSku.getGrossweight().doubleValue());
                    orderDetails.setCubic(basSku.getCube().doubleValue());
                    orderDetails.setPrice(basSku.getPrice().doubleValue());
                    orderDetails.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
                    orderDetails.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
                    orderDetails.setAddtime(new Date());
                    if (StringUtils.isEmpty(orderDetails.getLotatt10())) {
                        orderDetails.setLotatt10("HG");
                    }
                    //保存订单明细信息
                    orderDetailsForNormalMybatisDao.add(orderDetails);
                }
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
            } else {
                resultMsg.append("序号：").append(importDataVO.getSeq()).append("SO号获取失败").append(" ");
            }
        }
    }

    private OrderDetailsForNormalVO initOrderDetails(ImportOrderData dataArray) {
        OrderDetailsForNormalVO importDetailsDataVO = new OrderDetailsForNormalVO();
        importDetailsDataVO.setSeq(dataArray.getSeq());
        importDetailsDataVO.setOrderno(dataArray.getOrderno());
        importDetailsDataVO.setCustomerid(dataArray.getCustomerid());
        importDetailsDataVO.setConsigneeid(dataArray.getcContact());
        importDetailsDataVO.setSku(dataArray.getSku());
        importDetailsDataVO.setSoreference1(dataArray.getSoreference1());
        importDetailsDataVO.setSoreference2(dataArray.getSoreference2());
        if (!StringUtils.isEmpty(dataArray.getQtyorderedEach()) || !dataArray.getQtyorderedEach().equals("")) {
            importDetailsDataVO.setQtyorderedEach(Double.parseDouble(dataArray.getQtyorderedEach()));
        } else {
            importDetailsDataVO.setQtyorderedEach(0.0);
        }
        if (!StringUtils.isEmpty(dataArray.getQtyordered()) || !dataArray.getQtyordered().equals("")) {
            importDetailsDataVO.setQtyordered(Double.parseDouble(dataArray.getQtyordered()));
        } else {
            importDetailsDataVO.setQtyordered(0.0);
        }
        importDetailsDataVO.setLotatt01(dataArray.getLotatt01());
        importDetailsDataVO.setLotatt02(dataArray.getLotatt02());
        importDetailsDataVO.setLotatt03(dataArray.getLotatt03());
        importDetailsDataVO.setLotatt04(dataArray.getLotatt04());
        importDetailsDataVO.setLotatt05(dataArray.getLotatt05());
        importDetailsDataVO.setLotatt06(dataArray.getLotatt06());
        importDetailsDataVO.setLotatt07(dataArray.getLotatt07());
        importDetailsDataVO.setLotatt08(dataArray.getLotatt08());
        importDetailsDataVO.setLotatt09(dataArray.getLotatt09());
        importDetailsDataVO.setLotatt10(dataArray.getLotatt10());
        importDetailsDataVO.setLotatt11(dataArray.getLotatt11());
        importDetailsDataVO.setLotatt12(dataArray.getLotatt12());
        importDetailsDataVO.setLotatt13(dataArray.getLotatt13());
        importDetailsDataVO.setLotatt14(dataArray.getLotatt14());
        importDetailsDataVO.setLotatt15(dataArray.getLotatt15());

        return importDetailsDataVO;
    }
}
