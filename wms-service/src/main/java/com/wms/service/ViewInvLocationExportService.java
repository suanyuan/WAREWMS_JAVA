package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.ViewInvLocation;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvLocationMybatisDao;
import com.wms.query.ViewInvLocationQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.form.ViewInvLocationExportForm;
import com.wms.vo.form.ViewInvLocationForm;
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
@Service("viewInvLocationExportService")
public class ViewInvLocationExportService {

    @Autowired
    private ViewInvLocationMybatisDao viewInvLocationMybatisDao;

    @Autowired
    private BasCodesService basCodesService;


    public void exportViewInvLocationDataToExcel(HttpServletResponse response, ViewInvLocationExportForm form) throws IOException {
        Cookie cookie = new Cookie("exportToken", form.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());

        ViewInvLocationForm viewInvLocationForm = new ViewInvLocationForm();
//根据查询条件导出excel
        viewInvLocationForm.setFmcustomerid(form.getFmcustomerid());
        viewInvLocationForm.setFmsku(form.getFmsku());
        viewInvLocationForm.setLotatt12(form.getLotatt12());
        viewInvLocationForm.setLotatt14(form.getLotatt14());
        viewInvLocationForm.setFmlocation(form.getFmlocation());
        viewInvLocationForm.setName(form.getName());
        viewInvLocationForm.setLotatt04(form.getLotatt04());
        viewInvLocationForm.setLotatt05(form.getLotatt05());
        viewInvLocationForm.setLotatt02Start(form.getLotatt02Start());
        viewInvLocationForm.setLotatt02End(form.getLotatt02End());
        viewInvLocationForm.setLotatt03Start(form.getLotatt03Start());
        viewInvLocationForm.setLotatt03End(form.getLotatt03End());
        viewInvLocationForm.setLotatt10(form.getLotatt10());

        try {
            ViewInvLocationQuery query = new ViewInvLocationQuery();
            //权限控制
            query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
            BeanUtils.copyProperties(viewInvLocationForm, query);
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
            // excel的sheetName
            String sheetName = "库存查询结果";
            // excel要导出的数据
            List<ViewInvLocation> vList = viewInvLocationMybatisDao.queryByListExport(mybatisCriteria); //要权限！james
            //质量状态
            List<EasyuiCombobox> Lotatt10List = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
            for (ViewInvLocation viewInvLocation : vList)
            {

               //数量类型去小数点
                if(viewInvLocation.getFmqty()!=null) {
                    viewInvLocation.setFmqty(viewInvLocation.getFmqty().setScale(1));
                }
                if(viewInvLocation.getFmqtyEach()!=null) {
                    viewInvLocation.setFmqtyEach(viewInvLocation.getFmqtyEach().setScale(1));
                }
                if(viewInvLocation.getQty1()!=null) {
                    viewInvLocation.setQty1(viewInvLocation.getQty1().setScale(1));
                }
               //双证
                if(viewInvLocation.getLotatt13()!=null) {
                    if (viewInvLocation.getLotatt13().equals("1")) {
                        viewInvLocation.setLotatt13("已匹配");
                    }
                }
                for (EasyuiCombobox easyuiCombobox : Lotatt10List)
                {
                    if(viewInvLocation.getLotatt10()!=null) {
                        //质量状态id对比
                        if (viewInvLocation.getLotatt10().equals(easyuiCombobox.getId())) {
                            viewInvLocation.setLotatt10(easyuiCombobox.getValue());
                            break;
                        }
                    }
                }
                //样品属性
                if(viewInvLocation.getLotatt09()!=null){
                   String lotatt09=viewInvLocation.getLotatt09();
                   if(lotatt09.equals("ZC")){
                       viewInvLocation.setLotatt09("正常");
                   }else if(lotatt09.equals("TS")){
                       viewInvLocation.setLotatt09("投诉");
                   }else if(lotatt09.equals("YP")){
                       viewInvLocation.setLotatt09("样品");

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


        superClassMap.put("lotatt14", "入库单号");
        superClassMap.put("fmcustomerid", "货主");
        superClassMap.put("lotatt03", "入库日期");
        superClassMap.put("fmsku", "产品代码");
        superClassMap.put("lotatt12", "产品名称");
        superClassMap.put("lotatt06", "注册证号/备案凭证号");
        superClassMap.put("skudescre", "规格型号");
        superClassMap.put("lotatt04", "生产批号");
        superClassMap.put("lotatt05", "序列号");
        superClassMap.put("lotatt07", "灭菌批号");
        superClassMap.put("lotatt01", "生产日期");
        superClassMap.put("lotatt02", "有效期/失效期");
        superClassMap.put("fmqty", "库存件数");
        superClassMap.put("fmqtyEach", "库存数量");
        superClassMap.put("qty1", "换算率");
        superClassMap.put("defaultreceivinguom", "单位");
        superClassMap.put("reservedfield02", "产品描述");
        superClassMap.put("lotatt11", "存储条件");
        superClassMap.put("enterpriseName", "生产企业");
        superClassMap.put("lotatt09", "样品属性");
        superClassMap.put("lotatt10", "质量状态");
        superClassMap.put("fmlocation", "库位");
        superClassMap.put("name", "产品线");
        superClassMap.put("lotatt13", "双证");
        //id重复无法赋值
        //superClassMap.put("","备注");


        return superClassMap;
    }




}
