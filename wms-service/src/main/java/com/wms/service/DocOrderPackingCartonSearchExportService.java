package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.DocOrderPackingCarton;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocOrderPackingCartonMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.exception.ExcelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 *
 * @author
 * @Date
 */
@Service("docOrderPackingCartonSearchExportService")
public class DocOrderPackingCartonSearchExportService {


    @Autowired
    private DocOrderPackingCartonMybatisDao docOrderPackingCartonMybatisDao;

    @Autowired
    private BasCodesService basCodesService;


    public void exportDocQcSearchDataToExcel(HttpServletResponse response, DocOrderPackingCarton form) throws IOException {
        Cookie cookie = new Cookie("exportToken", form.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());



        try {
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
            // excel的sheetName
            String sheetName = "复核记录查询";
            // excel要导出的数据
            List<DocOrderPackingCarton> vList = docOrderPackingCartonMybatisDao.queryByList(mybatisCriteria);
            for (DocOrderPackingCarton form1 : vList) {
                //计算件数
                form1.setQtyEach(form1.getQty()*form1.getQty1());
                //质量状态
                List<EasyuiCombobox> Lotatt10List = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
                for (EasyuiCombobox easyuiCombobox : Lotatt10List)
                {
                    //质量状态id对比
                    if (form1.getLotatt10().equals(easyuiCombobox.getId())) {
                        form1.setLotatt10(easyuiCombobox.getValue());
                        break;
                    }
                }
                //是否装箱
                if(form1.getPackingflag()!=null){
                    if(form1.getPackingflag().equals("1")){
                        form1.setPackingflag("是");
                    }else{
                        form1.setPackingflag("否");
                    }
                }
                //双证
                if(form1.getLotatt13()!=null){
                    if(form1.getLotatt13().equals("1")){
                        form1.setLotatt13("已匹配");
                    }else{
                        form1.setLotatt13("");
                    }
                }
                //产品属性
                List<EasyuiCombobox> Lotatt09List = basCodesService.getBy(Constant.CODE_CATALOG_SAMPLEATTR);
                for (EasyuiCombobox easyuiCombobox : Lotatt09List)
                {
                    //状态id对比
                    if (form1.getLotatt09().equals(easyuiCombobox.getId())) {
                        form1.setLotatt09(easyuiCombobox.getValue());
                        break;
                    }
                }
                //时间格式转换
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                form1.setEdittimeShow(formatter.format(form1.getEdittime()));
                form1.setAddtimeShow(formatter.format(form1.getAddtime()));

            }

            // 导出
            if (vList == null || vList.size() == 0) {
                System.out.println("题库为空");
            } else {
                //将list集合转化为excle
                ExcelUtil.listToExcel(vList, fieldMap, sheetName, response);
                System.out.println("导出成功~~~~");
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到导出Excle时题型的英中文map
     *
     * @return 返回题型的属性map
     */
    public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {

        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();


        superClassMap.put("orderno", "出库单号");
        superClassMap.put("traceid", "箱号");
        superClassMap.put("packingflag", "是否装箱完成");
        superClassMap.put("lotatt10", "质量状态");
        superClassMap.put("customerid", "货主代码");
        superClassMap.put("shippershortname", "货主简称");
        superClassMap.put("lotatt08", "供应商");
        superClassMap.put("sku", "产品代码");
        superClassMap.put("lotatt12", "产品名称");
        superClassMap.put("lotatt06", "注册证号");
        superClassMap.put("skudesce", "规格/型号");
        superClassMap.put("lotatt04", "生产批号");
        superClassMap.put("lotatt07", "灭菌批号");
        superClassMap.put("lotatt05", "序列号");
        superClassMap.put("lotatt01", "生产日期");
        superClassMap.put("lotatt03", "入库日期");
        superClassMap.put("lotatt02", "有效期/失效期");
        superClassMap.put("lotatt11", "存储条件");
        superClassMap.put("lotatt15", "生产企业");
        superClassMap.put("reservedfield06", "生产许可证号/备案号");
        superClassMap.put("qty", "装箱件数");
        superClassMap.put("qtyEach", "装箱数量");
        superClassMap.put("uom", "单位");
        superClassMap.put("description", "复核说明");
        superClassMap.put("conclusion", "复核结论");
        superClassMap.put("editwho", "复核人");
        superClassMap.put("edittimeShow", "复核时间");
        superClassMap.put("addtimeShow", "创建时间");
        superClassMap.put("addwho", "创建人");
        superClassMap.put("lotatt14", "入库单号");
        superClassMap.put("lotatt09", "产品属性");
        superClassMap.put("lotatt13", "双证");
        superClassMap.put("qty1", "转换率");


        return superClassMap;
    }




}
