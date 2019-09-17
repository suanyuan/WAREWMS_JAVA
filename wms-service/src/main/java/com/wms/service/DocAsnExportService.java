package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.BasCustomer;
import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 *
 * @author
 * @Date
 */
@Service("docAsnExportService")
public class DocAsnExportService {


    @Autowired
    private DocAsnHeaderMybatisDao docAsnHeaderMybatisDao;
    @Autowired
    private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;
    @Autowired
    private BasCodesService basCodesService;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;



    /**
     * 打印order检查记录
     */
    public List<DocAsnHeader> docAsnToExcel(String asnno) {
        List<DocAsnHeader> docAsnHeaderList=new ArrayList<>();
        //获得单头
        DocAsnHeader docAsnHeader=docAsnHeaderMybatisDao.queryById(asnno);
        //获得细单
        List<DocAsnDetail> docAsnDetailList=docAsnDetailsMybatisDao.queryByAsnNo(asnno);

        //入库类型
        List<EasyuiCombobox>  list=basCodesService.getBy("ASN_TYP");
        for (EasyuiCombobox e : list) {
            if(e.getId().equals(docAsnHeader.getAsntype())){
                docAsnHeader.setAsntypeName(e.getValue());
                break;
            }
        }


        double  expectedqtySum=0;  //总计
         double  receivedqtySum=0;
        if(docAsnHeader!=null&&docAsnDetailList!=null){

 //日期格式转换
            SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
            String time=sdf.format(docAsnHeader.getExpectedarrivetime1());
            docAsnHeader.setMdate(time);
            for (DocAsnDetail docAsnDetail : docAsnDetailList) {

 //                供应商中文
                BasCustomer basCustomer=basCustomerMybatisDao.queryByCustomerId(docAsnDetail.getLotatt08());
                if(basCustomer!=null){
                    docAsnDetail.setLotatt08(basCustomer.getDescrC());
                }
//去小数点
                docAsnDetail.setExpectedqty(docAsnDetail.getExpectedqty().setScale(1));
                docAsnDetail.setReceivedqty(docAsnDetail.getReceivedqty().setScale(1));
//计算总计
                expectedqtySum+=docAsnDetail.getExpectedqty().doubleValue();
                receivedqtySum+=docAsnDetail.getReceivedqty().doubleValue();
                docAsnDetail.setExpectedqtySum(new BigDecimal(expectedqtySum).setScale(1));
                docAsnDetail.setReceivedqtySum(new BigDecimal(receivedqtySum).setScale(1));
//双证

            }
            docAsnHeader.setDetails(docAsnDetailList);
//冷链标记
            Map<String,Object> map=new HashMap<>();
            map.put("customerid",docAsnDetailList.get(0).getCustomerid());
            map.put("sku",docAsnDetailList.get(0).getSku());
            BasSku basSku=basSkuMybatisDao.queryById(map);
            if(basSku!=null){
                docAsnHeader.setColdTag(basSku.getReservedfield07());
         //冷链标记
        List<EasyuiCombobox>  cold=basCodesService.getBy("LL_TYP");
        for (EasyuiCombobox e : cold) {
            if(e.getId().equals(docAsnHeader.getColdTag())){
                docAsnHeader.setColdTag(e.getValue());
                break;
            }
        }

            }
        }

        docAsnHeaderList.add(docAsnHeader);
        return docAsnHeaderList;
    }




}
