package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.DocQcSearchExportForm;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocQcDetailsMybatisDao;
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
    private DocQcDetailsMybatisDao docQcDetailsMybatisDao;

    @Autowired
    private BasCodesService basCodesService;


    public void exportDocQcSearchDataToExcel(HttpServletResponse response, DocQcSearchExportForm form) throws IOException {
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
            String sheetName = "验收记录查询";
            // excel要导出的数据
            List<DocQcSearchExportForm> vList = docQcDetailsMybatisDao.queryByListExport(mybatisCriteria);
            for (DocQcSearchExportForm form1 : vList) {
                //计算件数
                form1.setQcqty_Expected(form1.getQcqty_Expected()-form1.getQcqty_Completed());
                //计算数量
                form1.setQcqty_ExpectedEach(form1.getQcqty_Expected()*form1.getQty1());
                form1.setQcqty_CompletedEach(form1.getQcqty_Completed()*form1.getQty1());
                //时间转换
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                //行状态
                String s=form1.getLinestatus();
                if(s.equals("00")){
                    form1.setLinestatus("未验收");
                }else if(s.equals("30")){
                    form1.setLinestatus("部分验收");
                }else if(s.equals("40")){
                    form1.setLinestatus("已验收");
                }else{
                    form1.setLinestatus("");

                }
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


        superClassMap.put("qcno", "验收单号");
        superClassMap.put("linestatus", "行状态");
        superClassMap.put("lotatt10", "质量状态");
        superClassMap.put("userdefine1", "库位");
        superClassMap.put("customerid", "货主代码");
        superClassMap.put("shippershortname", "货主简称");
        superClassMap.put("lotatt08", "供应商");
        superClassMap.put("sku", "产品代码");
        superClassMap.put("lotatt12", "产品名称");
        superClassMap.put("lotatt06", "注册证号");
        superClassMap.put("descrc", "规格/型号");
        superClassMap.put("lotatt04", "生产批号");
        superClassMap.put("lotatt07", "灭菌批号");
        superClassMap.put("lotatt05", "序列号");
        superClassMap.put("lotatt01", "生产日期");
        superClassMap.put("lotatt03", "入库日期");
        superClassMap.put("lotatt02", "有效期/失效期");
        superClassMap.put("lotatt11", "存储条件");
        superClassMap.put("lotatt15", "生产企业");
        superClassMap.put("reservedfield06", "生产许可证号/备案号");
        superClassMap.put("qcqty_Expected", "待验收件数");
        superClassMap.put("qcqty_Completed", "已验收件数");
        superClassMap.put("qcqty_ExpectedEach", "待验收数量");
        superClassMap.put("qcqty_CompletedEach", "已验收数量");
        superClassMap.put("qcdescr", "验收说明");
        superClassMap.put("editwho", "验收人");
        superClassMap.put("edittime", "验收时间");
        superClassMap.put("addtime", "创建时间");
        superClassMap.put("addwho", "创建人");
        superClassMap.put("lotatt14", "入库单号");
        superClassMap.put("qty1", "转换率");


        return superClassMap;
    }




}
