package com.wms.service;

import com.wms.entity.DocMtSearchExportForm;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocMtDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.exception.ExcelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 *
 * @author
 * @Date
 */
@Service("docMtSearchExportService")
public class DocMtSearchExportService {


    @Autowired
    private DocMtDetailsMybatisDao docMtDetailsMybatisDao;



    public void exportDocMtSearchDataToExcel(HttpServletResponse response, DocMtSearchExportForm form) throws IOException {
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
            String sheetName = "养护记录查询";
            // excel要导出的数据
            List<DocMtSearchExportForm> vList = docMtDetailsMybatisDao.queryByListExport(mybatisCriteria);
            for (DocMtSearchExportForm form1 : vList) {
                //计算件数数量
                form1.setMtqty_Expected(form1.getMtqty_Expected()-form1.getMtqty_Completed());
                form1.setMtqty_Each_Expected(form1.getMtqty_Each_Expected()-form1.getMtqty_Each_Completed());
                //行状态
                String s=form1.getLinestatus();
                if(s.equals("00")){
                    form1.setLinestatus("任务创建");
                }else if(s.equals("30")){
                    form1.setLinestatus("部分养护");
                }else if(s.equals("40")){
                    form1.setLinestatus("完全养护");
                }else{
                    form1.setLinestatus("");

                }
                //检查结论养护结论
                String f=form1.getCheck_Flag();
                if(f.equals("1")){
                    form1.setCheck_Flag("合格");
                }else  if(f.equals("0")){
                    form1.setCheck_Flag("不合格");
                }else  if(f.equals("2")){
                    form1.setCheck_Flag("未检查");
                }else{
                    form1.setCheck_Flag("");
                }
                String l=form1.getConclusion();
                if(l.equals("1")){
                    form1.setConclusion("合格");
                }else  if(l.equals("0")){
                    form1.setConclusion("不合格");
                }else  if(l.equals("2")){
                    form1.setConclusion("未检查");
                }else{
                    form1.setConclusion("");
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


        superClassMap.put("mtno", "养护单号");
        superClassMap.put("linestatus", "行状态");
        superClassMap.put("locationid", "库位");
        superClassMap.put("inventoryage", "库龄");
        superClassMap.put("customerid", "货主代码");
        superClassMap.put("sku", "产品代码");
        superClassMap.put("descrc", "规格/型号");
        superClassMap.put("lotatt12", "产品名称");
        superClassMap.put("mtqty_Expected", "待养护件数");
        superClassMap.put("mtqty_Each_Expected", "待养护数量");
        superClassMap.put("mtqty_Completed", "完成养护件数");
        superClassMap.put("mtqty_Each_Completed", "完成养护数量");
        superClassMap.put("uom", "单位");
        superClassMap.put("check_Flag", "检查结论");
        superClassMap.put("conclusion", "养护结论");
        superClassMap.put("conversewho", "养护人");
        superClassMap.put("conversedate", "养护日期");
        superClassMap.put("lotatt04", "生产批号");
        superClassMap.put("lotatt05", "序列号");
        superClassMap.put("lotatt07", "灭菌批号");
        superClassMap.put("lotatt01", "生产日期");
        superClassMap.put("lotatt03", "入库日期");
        superClassMap.put("lotatt02", "有效期/失效期");
        superClassMap.put("lotatt06", "注册证号");
        superClassMap.put("lotatt11", "存储条件");
        superClassMap.put("productLineName", "产品线");
        superClassMap.put("lotatt15", "生产企业");
        superClassMap.put("reservedfield06", "生产许可证号/备案号");
        superClassMap.put("lotnum", "批次号");
        superClassMap.put("remark", "备注");
//        superClassMap.put("editwho", "养护人");
//        superClassMap.put("edittime", "养护时间");
        superClassMap.put("addtime", "创建时间");
        superClassMap.put("addwho", "创建人");
        superClassMap.put("lotatt14", "入库单号");


        return superClassMap;
    }




}
