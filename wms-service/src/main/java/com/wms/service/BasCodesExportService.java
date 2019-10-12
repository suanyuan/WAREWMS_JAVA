package com.wms.service;

import com.wms.entity.BasCodes;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasCodesMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasCodesQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.ExcelUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.form.BasCodesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Service("BasCodesExportService")
public class BasCodesExportService {
    @Autowired
    private BasCodesMybatisDao basCodesMybatisDao;

    public void exportViewInvTranDataToExcel(HttpServletResponse response, BasCodesForm form) throws IOException {
        Cookie cookie = new Cookie("exportToken",form.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());

        BasCodesForm basCodesForm = new BasCodesForm();
        basCodesForm.setCodeid(form.getCodeid());
        basCodesForm.setUdf2(form.getUdf2());
        basCodesForm.setCode(form.getCode());
        basCodesForm.setCodenameC(form.getCodenameC());
        basCodesForm.setCodenameE(form.getCodenameE());
        basCodesForm.setShowSequence(form.getShowSequence());
        basCodesForm.setAddtime(form.getAddtime());
        basCodesForm.setAddwho(form.getAddwho());
        basCodesForm.setEdittime(form.getEdittime());
        basCodesForm.setEditwho(form.getEditwho());

        try {
            BasCodesQuery query = new BasCodesQuery();
            //权限控制
            query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
            query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
            BeanUtils.copyProperties(basCodesForm, query);
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
            // excel的sheetName
            String sheetName = "库存查询结果";
            // excel要导出的数据
            List<BasCodes> vList = basCodesMybatisDao.queryByList(mybatisCriteria); //要权限！james
            // 导出
            if (vList == null || vList.size() == 0) {
                System.out.println("题库为空");
            }else {
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

        superClassMap.put("codeid", "码表类型");
        superClassMap.put("udf2", "类型描述");
        superClassMap.put("code", "类型编码");
        superClassMap.put("codenameC", "中文描述");
        superClassMap.put("codenameE", "英文描述");
        superClassMap.put("showSequence", "显示顺序");
        superClassMap.put("addtime", "添加时间");
        superClassMap.put("addwho", "添加人");
        superClassMap.put("edittime", "修改时间");
        superClassMap.put("editwho", "修改人");

        return superClassMap;
    }
}
