package com.wms.service.importdata;

import com.wms.entity.CouRequestDetails;
import com.wms.entity.CouRequestHeader;
import com.wms.entity.ImportCouRequestData;
import com.wms.mybatis.dao.CouRequestDetailsMybatisDao;
import com.wms.mybatis.dao.CouRequestHeaderMybatisDao;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.Json;
import com.wms.vo.form.CouRequestDetailsForm;
import com.wms.vo.form.CouRequestHeaderForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service("importCouRequestDataService")
public class ImportCouRequestDataService {



    @Autowired
    private CouRequestDetailsMybatisDao couRequestDetailsMybatisDao;
    @Autowired
    private CouRequestHeaderMybatisDao couRequestHeaderMybatisDao;


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
            String sheetName = "couRequest_template";
            //excel的表头与文字对应，获取excel表头
            LinkedHashMap<String, String> map = getLeadInFiledPublicQuestionBank();
            //获取需要导入的具体的表
            Class cou = new ImportCouRequestData().getClass();
            //excel转化成的list集合
            List<ImportCouRequestData> CouList = null;
            try {
                //调用excle共用类，转化成list
                CouList = ExcelUtil.excelToList(in, sheetName, cou, map,null);
            } catch (ExcelException e) {
                e.printStackTrace();
            }
            //保存实体集合
            List<CouRequestDetailsForm> importDataList = this.listToBean(CouList, resultMsg);
            if (true) {

                if (true) {
                    System.out.println("=============");
                    this.saveCouRequest(importDataList, resultMsg);// 转成订单资料存入资料库
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

    private List<CouRequestDetailsForm> listToBean(List<ImportCouRequestData> CouList, StringBuilder resultMsg) {
        StringBuilder rowResult = new StringBuilder();
        List<CouRequestDetailsForm> importData = new ArrayList<CouRequestDetailsForm>();
        CouRequestDetailsForm importDataForm = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatRQ = new SimpleDateFormat("yyyy-MM-dd");
        //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format.setLenient(false);
        formatRQ.setLenient(false);

        for (ImportCouRequestData dataArray : CouList) {
            importDataForm = new CouRequestDetailsForm();
            int arrayIndex = 0;
//盘点单号
            try {
                if (StringUtils.isEmpty(dataArray.getCycleCountno())) {
                    throw new Exception();
                }

                importDataForm.setCycleCountno(dataArray.getCycleCountno());
            } catch (Exception e) {
                rowResult.append("[盘点单号],未输入").append(" ");
            }



//行号
            try {
                if (StringUtils.isEmpty(dataArray.getCycleCountlineno()+"")) {
                    throw new Exception();
                }
                importDataForm.setCycleCountlineno(Integer.parseInt(dataArray.getCycleCountlineno()));
            } catch (Exception e) {
                rowResult.append("[行号],未输入").append(" ");
            }
//盘点件数
            try {
                if (ExcelUtil.isNotNumeric(dataArray.getQtyInv()+"")) {
                    throw new Exception();
                }
                importDataForm.setQtyInv(Double.parseDouble(dataArray.getQtyInv()));
            } catch (Exception e) {
                rowResult.append("[盘点件数],应为大于1的数字").append(" ");
            }
//实际盘点件数
            try {
                if (ExcelUtil.isNotNumeric(dataArray.getQtyAct()+"")) {
                    throw new Exception();
                }
                importDataForm.setQtyAct(Double.parseDouble(dataArray.getQtyAct()));
            } catch (Exception e) {
                rowResult.append("[实际盘点件数],应为大于1的数字").append(" ");
            }
//计算差异
            if(rowResult.length()==0){
                importDataForm.setUserdefined1((importDataForm.getQtyAct()-importDataForm.getQtyInv())+"");
            }
//备注
            try {
                if (StringUtils.isEmpty(dataArray.getRemarks())) {

                }
                importDataForm.setUserdefined2(dataArray.getRemarks());
            } catch (Exception e) {

            }
//盘点日期
            try {
                if (StringUtils.isEmpty(dataArray.getCountdate())) {
                    throw new Exception();
                }
                importDataForm.setEdittime(DateUtil.parse(dataArray.getCountdate(),"yyyy-MM-dd"));

            } catch (Exception e) {
                rowResult.append("[盘点日期],未输入或者格式错误:格式应为yyyy-MM-dd").append(" ");

            }
//盘点人
            try {
                if (StringUtils.isEmpty(dataArray.getCountwho())) {
                    throw new Exception();
                }
                importDataForm.setEditwho(dataArray.getCountwho());
            } catch (Exception e) {
                rowResult.append("[盘点人],未输入").append(" ");

            }

//判断是否有错误
            if (rowResult.length() > 0) {
                if (rowResult.lastIndexOf("，") > -1) {
                    rowResult.deleteCharAt(rowResult.lastIndexOf("，"));
                }
                System.out.println();
                resultMsg.append("行号:").append(dataArray.getCycleCountlineno()).append("资料有错 ").append(rowResult).append(" ");
                rowResult.setLength(0);
            } else {
                importData.add(importDataForm);
            }

        }
        return importData;
    }

    public LinkedHashMap<String, String> getLeadInFiledPublicQuestionBank() {
        // excel的表头与文字对应
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("盘点单号","cycleCountno");
        map.put("行号","cycleCountlineno");
        map.put("货主","customerid");
        map.put("产品代码","sku");
        map.put("产品名称","reservedfield01");
        map.put("规格/型号","descrc");
        map.put("生产批号","lotatt04");
        map.put("序列号","lotatt05");
        map.put("库存件数","qtyInv");
        map.put("库位","locationid");
        map.put("实际盘点件数","qtyAct");
        map.put("盘点差异","difference");
        map.put("产品线","productLineName");
        map.put("备注","remarks");
        map.put("盘点日期","countdate");
        map.put("盘点人","countwho");
        map.put("复核人","reviewerwho");
        return map;
    }


    @Transactional
    public void saveCouRequest(List<CouRequestDetailsForm> importDataList, StringBuilder resultMsg) {
        CouRequestDetails couRequestDetails = null;
        for (CouRequestDetailsForm importDataForm : importDataList) {
            couRequestDetails = new CouRequestDetails();
            BeanUtils.copyProperties(importDataForm, couRequestDetails);
//设置复核人
            couRequestDetails.setUserdefined3(SfcUserLoginUtil.getLoginUser().getId());

            couRequestDetailsMybatisDao.updateBySelective(couRequestDetails);
            resultMsg.append("行号：").append(importDataForm.getCycleCountlineno()).append("资料导入成功").append(" ");
        }
//盘点完成  主单状态改为完全盘点
        CouRequestHeaderForm form=new CouRequestHeaderForm();
        form.setCycleCountno(importDataList.get(0).getCycleCountno());
        form.setEndtime(new Date());
        form.setStatus("40");
        couRequestHeaderMybatisDao.updateBySelective(form);
    }



}
