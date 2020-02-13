package com.wms.service.importdata;

import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.BasPackageService;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.BasGtnVO;
import com.wms.vo.Json;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service("ImportSerialNumDataService")
public class ImportSerialNumDataService {

    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
    @Autowired
    private DocSerialNumRecordMybatisDao docSerialNumRecordMybatisDao;

    /**
     * 导入序列号记录
     */
    public Json importExcelData(MultipartFile excelFile, String asnno) {
        Json json = new Json();
        boolean isSuccess = false;
        StringBuilder resultMsg = new StringBuilder();
        InputStream in;

        try {
            // 获取前台exce的输入流
            in = excelFile.getInputStream();

            //获取sheetName名字
            String sheetName = "PACKING_LINE";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取组合excel表头数组，防止重复用的
            String[] uniqueFields = new String[]{"序号"};
            //获取需要导入的具体的表
            Class asn = new ImportSerialNumData().getClass();
            //excel转化成的list集合
            List<ImportSerialNumData> GTNList = null;
            try {
                //调用excle共用类，转化成list
                GTNList = ExcelUtil.excelToList(in, sheetName, asn, map, uniqueFields);
            } catch (ExcelException e) {
                e.printStackTrace();
            }

            //判断导入条数和预期件数是否一致
            DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryBySum(asnno);
            if (GTNList == null || GTNList.size() == 0) {

                resultMsg.append("导入内容为空，请填写相应数据");
            } else if (GTNList.size() == docAsnDetail.getExpectedqty().intValue()) {

                List<DocSerialNumRecord> importDataList = this.listToBean(GTNList, resultMsg);
                this.save(importDataList, resultMsg, asnno);// 转成订单资料存入资料库
                isSuccess = true;
            } else {

                resultMsg.append("导入失败，件数不匹配。").append(" ");
                resultMsg.append("预期收货件数:").append(docAsnDetail.getExpectedqty().intValue()).append(" ");
                resultMsg.append("Excel条数:").append(GTNList.size()).append(" ");
            }
        } catch (IOException e1) {

            resultMsg.append("系统错误，").append(e1.toString());
        }
        json.setMsg(resultMsg.toString());
        json.setSuccess(isSuccess);
        return json;
    }


    private List<DocSerialNumRecord> listToBean(List<ImportSerialNumData> GTNList, StringBuilder resultMsg) {
        StringBuilder rowResult = new StringBuilder();
        List<DocSerialNumRecord> importData = new ArrayList<DocSerialNumRecord>();
        DocSerialNumRecord importDataVO = null;
//		List<DocAsnDetailVO> importDetailsDataVOList = new ArrayList<DocAsnDetailVO>();
//		DocAsnDetailVO importDetailsDataVO = null;
        String quantityData = null;
        Integer count = 1;
        String customerid = "", asnreference1 = "", asnreference2 = "", expectedarrivetime1 = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
        //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format.setLenient(false);
        formatRQ.setLenient(false);

        for (ImportSerialNumData dataArray : GTNList) {
            importDataVO = new DocSerialNumRecord();
            int arrayIndex = 0;
            try {
                importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
                System.out.println();
                if (Integer.parseInt(dataArray.getSeq()) <= 0) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[序号]，资料格式转换失败，请输入大于0之正整数数字格式").append(" ");
            }

            try {
                importDataVO.setBatchNum(dataArray.getBatchNum());

                if (StringUtils.isEmpty(dataArray.getBatchNum())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[批号]，未输入").append(" ");
            }

            try {
                importDataVO.setSerialNum(dataArray.getSerialNum());

                if (StringUtils.isEmpty(dataArray.getSerialNum())) {
                    throw new Exception();
                }
            } catch (Exception e) {
                rowResult.append("[序列号]，未输入").append(" ");
            }

            if (rowResult.length() > 0) {
                if (rowResult.lastIndexOf("，") > -1) {
                    rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
                }
                resultMsg.append("序号：").append(dataArray.getSeq()).append("资料有错 ").append(rowResult).append(" ");
                rowResult.setLength(0);
            } else {
                importData.add(importDataVO);
            }
        }
        return importData;
    }

    public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
        // excel的表头与文字对应
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("序号", "seq");
        map.put("发货凭证号", "userdefine1");    //无用
        map.put("产品代码(必填)", "sku");            //无用
        map.put("批号(必填)", "batchNum");
        map.put("序列号(必填)", "serialNum");

        return map;
    }


    private void save(List<DocSerialNumRecord> importDataList, StringBuilder resultMsg, String asnno) {

        //导入之前清除之前的导入记录
        docSerialNumRecordMybatisDao.clearRecordByOrderno(asnno);

        DocSerialNumRecord docSerialNumRecord;
        DocAsnHeader docAsnHeader = docAsnHeaderMybatisDao.queryById(asnno);
        for (DocSerialNumRecord importDataVO : importDataList) {
            docSerialNumRecord = new DocSerialNumRecord();
            BeanUtils.copyProperties(importDataVO, docSerialNumRecord);
            //赋值
            docSerialNumRecord.setCustomerid(docAsnHeader.getCustomerid());
            docSerialNumRecord.setCartonNo(1);
            docSerialNumRecord.setSoreference(docAsnHeader.getAsnreference1());
            docSerialNumRecord.setOrderNo(asnno);
            docSerialNumRecord.setBatchNum(importDataVO.getBatchNum());
            docSerialNumRecord.setSerialNum(importDataVO.getSerialNum());
            docSerialNumRecord.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
            docSerialNumRecord.setUserdefine1("IN");
            //保存订单主信息

            docSerialNumRecordMybatisDao.add(docSerialNumRecord);
        }
        resultMsg.append("导入成功，共导入").append(importDataList.size()).append("条");
    }
}
