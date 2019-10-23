package com.wms.service.importdata;

import com.wms.entity.BasLocation;
import com.wms.entity.ImportDocPaData;
import com.wms.mybatis.dao.BasLocationMybatisDao;
import com.wms.mybatis.entity.pda.PdaDocPaDetailForm;
import com.wms.mybatis.entity.pda.PdaDocPaEndForm;
import com.wms.query.BasLocationQuery;
import com.wms.result.PdaResult;
import com.wms.service.DocPaDetailsService;
import com.wms.service.DocPaHeaderService;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.Json;
import com.wms.vo.pda.PdaDocPaHeaderVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("importPaDataService")
public class ImportPaDataService {

    @Autowired
    private DocPaHeaderService docPaHeaderService;

    @Autowired
    private DocPaDetailsService docPaDetailsService;

    @Autowired
    private BasLocationMybatisDao basLocationMybatisDao;

    /**
     * 导入上架结果
     */
    public Json importExcelData(MultipartFile excelFile) {

        Json json = new Json();
        StringBuilder resultMsg = new StringBuilder();
        InputStream in;

        try {
            // 获取前台exce的输入流
            in = excelFile.getInputStream();

            //获取sheetName名字
            String sheetName = "上架任务清单";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取需要导入的具体的表
            Class data = ImportDocPaData.class;
            //excel转化成的list集合
            List<ImportDocPaData> dataList = null;
            try {
                //调用excle共用类，转化成list
                dataList = ExcelUtil.excelToList(in, sheetName, data, map,null);
            } catch (ExcelException e) {
                e.printStackTrace();
                json.setSuccess(false);
                json.setMsg("数据解析失败");
                return json;
            }
            //保存实体集合
            List<PdaDocPaDetailForm> importDataList = this.listToBean(dataList, resultMsg);

            //判断上架单状态
            if (importDataList.size() > 0) {

                PdaDocPaDetailForm pdaDocPaDetailForm = importDataList.get(0);
                PdaDocPaHeaderVO pdaDocPaHeaderVO = docPaHeaderService.queryByPano(pdaDocPaDetailForm.getPano());
                if (!pdaDocPaHeaderVO.getPastatus().equals("00")) {

                    json.setSuccess(false);
                    json.setMsg("上架单只有在订单创建状态下，才可进行PC端上架操作！");
                    return json;
                }
            }

            //更新上架件数
            json = validateLocation(importDataList);
            if (!json.isSuccess()) return json;

            this.save(importDataList, resultMsg);
        } catch (IOException e1) {
            e1.printStackTrace();
            json.setSuccess(false);
            json.setMsg("系统错误");
            return json;
        }
        json.setMsg(resultMsg.toString());
        json.setSuccess(true);
        return json;
    }

    /**
     * 将excel解析出的结果数据转换成 DocPaDetailsService.putawayGoods中所需的传参内容
     */
    private List<PdaDocPaDetailForm> listToBean(List<ImportDocPaData> dataList, StringBuilder resultMsg) {

        StringBuilder rowResult = new StringBuilder();
        List<PdaDocPaDetailForm> importData = new ArrayList<>();
        PdaDocPaDetailForm pdaDocPaDetailForm = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
        //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format.setLenient(false);
        formatRQ.setLenient(false);

        for (ImportDocPaData importDocPaData : dataList) {

            pdaDocPaDetailForm = new PdaDocPaDetailForm();
            int arrayIndex = 0;

            //上架任务单号
            try {

                if (StringUtils.isEmpty(importDocPaData.getPano())) throw new Exception();
                pdaDocPaDetailForm.setPano(importDocPaData.getPano());
            } catch (Exception e) {

                rowResult.append("[上架任务单号],未输入").append(" ");
            }

            //任务行号
            try {

                if (StringUtils.isEmpty(importDocPaData.getPalineno())) throw new Exception();
                pdaDocPaDetailForm.setPalineno(importDocPaData.getPalineno());
            } catch (Exception e) {

                rowResult.append("[任务行号],未输入").append(" ");
            }

            //货主
            try {

                if (StringUtils.isEmpty(importDocPaData.getCustomerid())) throw new Exception();
                pdaDocPaDetailForm.setCustomerid(importDocPaData.getCustomerid());
            } catch (Exception e) {

                rowResult.append("[货主],未输入").append(" ");
            }

            //生产日期
            try {

                if (StringUtil.isNotyMdDate(importDocPaData.getLotatt01())) throw new Exception();
                pdaDocPaDetailForm.setLotatt01(importDocPaData.getLotatt01());
            } catch (Exception e) {

                rowResult.append("[生产日期],未输入或者格式错误:格式应为yyyy-MM-dd").append(" ");
            }

            //效期
            try {

                if (StringUtil.isNotyMdDate(importDocPaData.getLotatt02())) throw new Exception();
                pdaDocPaDetailForm.setUserdefine2(importDocPaData.getLotatt02());
            } catch (Exception e) {

                rowResult.append("[有效期/失效期],未输入或者格式错误:格式应为yyyy-MM-dd").append(" ");
            }

            //上架件数
            try {

                if (ExcelUtil.isNotNumeric(importDocPaData.getPutwayqtyCompleted() + "")) throw new Exception();
                pdaDocPaDetailForm.setPaqty(new BigDecimal(importDocPaData.getPutwayqtyCompleted()));
            } catch (Exception e) {

                rowResult.append("[已上架件数],应为大于0的数字").append(" ");
            }

            //库位
            try {

                if (StringUtils.isEmpty(importDocPaData.getUserdefine1())) throw new Exception();
                pdaDocPaDetailForm.setUserdefine1(importDocPaData.getUserdefine1());
            } catch (Exception e) {

                rowResult.append("[库位],未输入").append(" ");
            }

            //上架人
            try {

                if (StringUtils.isEmpty(importDocPaData.getEditwho())) throw new Exception();
                pdaDocPaDetailForm.setEditwho(importDocPaData.getEditwho());
            } catch (Exception e) {

                rowResult.append("[上架人],未输入").append(" ");
            }

            if (rowResult.length() > 0) {
                if (rowResult.lastIndexOf("，") > -1) {
                    rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
                }
                resultMsg.append("任务行号:").append(importDocPaData.getPalineno()).append("资料有错 ").append(rowResult).append(" ");
                rowResult.setLength(0);
            } else {
                pdaDocPaDetailForm.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
                pdaDocPaDetailForm.setUserdefine3(importDocPaData.getLotatt04());
                pdaDocPaDetailForm.setUserdefine4(importDocPaData.getLotatt05());
                pdaDocPaDetailForm.setUserdefine5("DJ");
                pdaDocPaDetailForm.setOtherCode(importDocPaData.getSku());
                importData.add(pdaDocPaDetailForm);
            }

        }
        return importData;
    }


    public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
        // excel的表头与文字对应
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("上架任务单号","pano");
        map.put("任务行号","palineno");
        map.put("货主","customerid");
        map.put("产品代码","sku");
        map.put("产品名称","reservedfield01");
        map.put("规格/型号","descrc");
        map.put("生产批号","lotatt04");
        map.put("序列号","lotatt05");
        map.put("灭菌批号","lotatt07");
        map.put("生产日期","lotatt01");
        map.put("有效期/失效期","lotatt02");
        map.put("收货件数","asnqtyExpected");
        map.put("已上架件数","putwayqtyCompleted");
        map.put("库位","userdefine1");
        map.put("上架人","editwho");
        return map;
    }

    private Json validateLocation(List<PdaDocPaDetailForm> importDataList) {

        StringBuilder resultMsg = new StringBuilder();
        BasLocation loc;
        BasLocationQuery locQuery = new BasLocationQuery();
        Json json = Json.success("");

        Map<String, Object> existLocation = new HashMap<>();
        for (PdaDocPaDetailForm pdaDocPaDetailForm : importDataList) {

            if (StringUtils.isNotEmpty(pdaDocPaDetailForm.getUserdefine1())) {

                if (existLocation.get(pdaDocPaDetailForm.getUserdefine1()) != null) {

                    if (existLocation.get(pdaDocPaDetailForm.getUserdefine1()).equals("0")) {

                        json.setSuccess(false);
                        resultMsg.append("任务行号:").append(pdaDocPaDetailForm.getPalineno()).append("[库位]：").append(pdaDocPaDetailForm.getUserdefine1()).append("，查无资料").append(" ");
                        continue;
                    }else {//1

                        continue;
                    }
                }

                locQuery.setLocationid(pdaDocPaDetailForm.getUserdefine1());
                loc = basLocationMybatisDao.queryById(locQuery);
                if (loc == null) {

                    json.setSuccess(false);
                    resultMsg.append("任务行号:").append(pdaDocPaDetailForm.getPalineno()).append("[库位]：").append(pdaDocPaDetailForm.getUserdefine1()).append("，查无资料").append(" ");
                    existLocation.put(pdaDocPaDetailForm.getUserdefine1(), "0");
                }else {

                    existLocation.put(pdaDocPaDetailForm.getUserdefine1(), "1");
                }
            }
        }
        json.setMsg(resultMsg.toString());
        return json;
    }

    @Transactional
    public void save(List<PdaDocPaDetailForm> importDataList, StringBuilder resultMsg) {

        for (PdaDocPaDetailForm paDetailForm : importDataList) {

            PdaResult paResult = docPaDetailsService.putawayGoods(paDetailForm);
            if (paResult.getErrorCode() == PdaResult.CODE_SUCCESS) {

                resultMsg.append("任务行号：").append(paDetailForm.getPalineno()).append(" 上架导入成功").append(" ");
            }else {

                resultMsg.append("任务行号：").append(paDetailForm.getPalineno()).append(" 上架导入失败，").append(paResult.getMsg()).append(" ");
            }
        }

        //上架完成
        if (importDataList.size() > 0) {

            PdaDocPaDetailForm pdaDocPaDetailForm = importDataList.get(0);
            PdaDocPaEndForm pdaDocPaEndForm = new PdaDocPaEndForm();
            pdaDocPaEndForm.setPano(pdaDocPaDetailForm.getPano());
            pdaDocPaEndForm.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
            docPaHeaderService.endTask(pdaDocPaEndForm);
        }
    }
}
