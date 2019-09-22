package com.wms.service.importdata;

import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDoublec;
import com.wms.entity.ImportDoublecData;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.DocAsnDoublecMybatisDao;
import com.wms.mybatis.dao.InvLotAttMybatisDao;
import com.wms.query.InvLotAttQuery;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.DocAsnDoublecVO;
import com.wms.vo.Json;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("ImportDocAsnDoubleDataService")
public class ImportDocAsnDoublecDataService {


    @Autowired
    private DocAsnDoublecMybatisDao docAsnDoublecMybatisDao;
    @Autowired
    private InvLotAttMybatisDao invLotAttMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;

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
            String sheetName = "双证导入";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取需要导入的具体的表
            Class asn = new ImportDoublecData().getClass();
            //excel转化成的list集合
            List<ImportDoublecData> GPRSList = null;
            try {
                //调用excle共用类，转化成list
                GPRSList = ExcelUtil.excelToList(in, sheetName, asn, map, null);
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<DocAsnDoublecVO> importDataList = this.listToBean(GPRSList, resultMsg);
            if (true) {

                if (true) {
                    System.out.println("=============");
                    this.saveProductRegisterSpecs(importDataList, resultMsg);// 转成订单资料存入资料库
                    isSuccess = true;
                }

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        json.setMsg(resultMsg.toString());
        json.setSuccess(isSuccess);
        return json;
    }

    private List<DocAsnDoublecVO> listToBean(List<ImportDoublecData> GPRSList, StringBuilder resultMsg) {
        StringBuilder rowResult = new StringBuilder();
        List<DocAsnDoublecVO> importData = new ArrayList<DocAsnDoublecVO>();
        DocAsnDoublecVO importDataVO = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
        //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format.setLenient(false);
        formatRQ.setLenient(false);

        for (ImportDoublecData dataArray : GPRSList) {
            importDataVO = new DocAsnDoublecVO();
            int arrayIndex = 0;
//序号
            try {
                if (Integer.parseInt(dataArray.getSeq()) <= 0) {
                    throw new Exception();
                }
//判断序号是否重复
                for (DocAsnDoublecVO v : importData) {
                    if (v.getSeq().equals(dataArray.getSeq())) {
                        throw new Exception();
                    }
                }
                importDataVO.setSeq(Integer.parseInt(dataArray.getSeq()));
            } catch (Exception e) {
                rowResult.append("[序号],资料格式转换失败,请输入大于0的正整数数字格式").append(" ");
            }


//任务号
            try {

                if (StringUtils.isEmpty(dataArray.getDoublecno())) {
//                    throw new Exception();
                }
//                for (DocAsnDoublecVO v : importData) {
//                    if (v.getDoublecno().equals(dataArray.getDoublecno())) {
//                        throw new Exception();
//                    }
//                }
//                DocAsnDoublec doublecQ = new DocAsnDoublec();
//                doublecQ.setDoublecno(dataArray.getDoublecno());
//                DocAsnDoublec docAsnDoublec = docAsnDoublecMybatisDao.queryById(doublecQ);
//                if (docAsnDoublec != null) {
//                    throw new Exception();
//                }

                importDataVO.setDoublecno(dataArray.getDoublecno());
            } catch (Exception e) {
//                rowResult.append("[任务号],未输入").append(" ");
            }

//产品型号

            try {

                if (StringUtils.isEmpty(dataArray.getContext1())) {
//                    throw new Exception();
                }
                importDataVO.setContext1(dataArray.getContext1());
            } catch (Exception e) {
//                rowResult.append("[产品型号],未输入").append(" ");
            }
//名称
            try {
                if (StringUtils.isEmpty(dataArray.getContext2())) {
                    throw new Exception();
                }
                DocAsnDoublec docAsnDoublec = docAsnDoublecMybatisDao.queryByContext2(dataArray.getContext2());
                if (docAsnDoublec != null) {
                    throw new Exception();
                }
                for (DocAsnDoublecVO v : importData) {
                    if (v.getContext2().equals(dataArray.getContext2())) {
                        throw new Exception();
                    }
                }
                importDataVO.setContext2(dataArray.getContext2());
            } catch (Exception e) {
                rowResult.append("[名称],未输入或者已存在此双证名称").append(" ");
            }
//执行标准
            try {
                if (StringUtils.isEmpty(dataArray.getContext3())) {
//                    throw new Exception();
                }
                importDataVO.setContext3(dataArray.getContext3());
            } catch (Exception e) {
//                rowResult.append("[执行标准],未输入").append(" ");
            }
//备注
            try {
                importDataVO.setContext4(dataArray.getContext4());

                if (StringUtils.isEmpty(dataArray.getContext4())) {
//					throw new Exception();
                }
            } catch (Exception e) {
//				rowResult.append("[备注]，未输入").append(" ");
            }


// 匹配双证 前面没有错误才匹配双证
            if (rowResult.length() <= 0) {
                Json json = new Json();
                try {
                    json = reqDouble(importDataVO);
                    if (!json.isSuccess()) {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    rowResult.append("[匹配双证]," + json.getMsg()).append(" ");
                }

            }
            if (rowResult.length() > 0) {
                if (rowResult.lastIndexOf("，") > -1) {
                    rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
                }
                System.out.println();
                resultMsg.append("序号:").append(dataArray.getSeq()).append("资料有错 ").append(rowResult).append(" ");
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
        map.put("任务号", "doublecno");
        map.put("产品型号", "context1");
        map.put("名称", "context2");
        map.put("执行标准", "context3");
        map.put("备注", "context4");
        return map;
    }


    @Transactional
    public void saveProductRegisterSpecs(List<DocAsnDoublecVO> importDataList, StringBuilder resultMsg) {
        DocAsnDoublec docAsnDoublec = null;
        for (DocAsnDoublecVO importDataVO : importDataList) {
            docAsnDoublec = new DocAsnDoublec();
            BeanUtils.copyProperties(importDataVO, docAsnDoublec);
            docAsnDoublec.setMatchFlag("1");
            docAsnDoublec.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
            docAsnDoublec.setAddtime(new Date());
            docAsnDoublec.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
            docAsnDoublec.setEdittime(new Date());
//保存双证
            docAsnDoublecMybatisDao.add(docAsnDoublec);
//设置inv_lot_att.lotatt13 = 1
            InvLotAttQuery query = new InvLotAttQuery();
            query.setLotatt05(docAsnDoublec.getContext2());
            query.setLotatt10("DJ");
            query.setLotatt13("1");
            invLotAttMybatisDao.updatelotatt13Bylotatt05Andlotatt10(query);

            resultMsg.append("序号：").append(importDataVO.getSeq()).append("资料导入成功").append(" ");
        }

    }


    /**
     * 检验双证
     *
     * @param
     * @return
     */
    public Json reqDouble(DocAsnDoublecVO docAsnDoublecVO) {
        Json json = new Json();
//先根据序列号查出InvLotAtt
        InvLotAttQuery query = new InvLotAttQuery();
        query.setLotatt05(docAsnDoublecVO.getContext2());
        List<InvLotAtt> invLotAttList = invLotAttMybatisDao.queryByLotatts05(query);
        if (invLotAttList.size() > 0) {
            boolean con = false;
            for (InvLotAtt invLotAtt : invLotAttList) {
                if (!invLotAtt.getLotatt10().equals("DJ")) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("customerid", invLotAtt.getCustomerid());
                    map.put("sku", invLotAtt.getSku());


//再根据customerid和sku查出BasSku
                    BasSku basSku = basSkuMybatisDao.queryById(map);
                    if (basSku != null) {
                        if (basSku.getSkuGroup7().equals("1")) {
//                            if (basSku.getDescrE().toUpperCase().equals(docAsnDoublecVO.getContext1().toUpperCase())) {
//                            if (basSku.getDescrE().equals(docAsnDoublecVO.getContext1())) {
                                json.setSuccess(true);
                                docAsnDoublecVO.setCustomerid(invLotAtt.getCustomerid());
//                            } else {
//                                json.setSuccess(false);
//                                json.setMsg("产品的型号不匹配");
//                            }
                        } else {
                            json.setSuccess(false);
                            json.setMsg("没有需要匹配双证的产品");
                        }
                    } else {
                        json.setSuccess(false);
                        json.setMsg("没有需要匹配双证的产品");
                    }
                    con = true;
                    break;
                }
            }
            if (!con) {
                json.setSuccess(false);
                json.setMsg("库存质量状态为待检");
            }

        } else {
            json.setSuccess(false);
            json.setMsg("没有此序列号库存");
        }

        return json;

    }
}
