package com.wms.service;

import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocAsnHeader;
import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.DocAsnHeaderMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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



    /**
     * 打印order检查记录
     */
    public List<DocAsnHeader> docAsnToExcel(String asnno) {
        List<DocAsnHeader> docAsnHeaderList=new ArrayList<>();
        //获得单头
        DocAsnHeader docAsnHeader=docAsnHeaderMybatisDao.queryById(asnno);
        //获得细单
        List<DocAsnDetail> docAsnDetailList=docAsnDetailsMybatisDao.queryByAsnNo(asnno);
         double  expectedqtySum=0;  //总计
         double  receivedqtySum=0;
        if(docAsnHeader!=null&&docAsnDetailList!=null){
            for (DocAsnDetail docAsnDetail : docAsnDetailList) {
//去小数点
                docAsnDetail.setExpectedqty(docAsnDetail.getExpectedqty().setScale(1));
                docAsnDetail.setReceivedqty(docAsnDetail.getReceivedqty().setScale(1));
//计算总计
                expectedqtySum+=docAsnDetail.getExpectedqty().doubleValue();
                receivedqtySum+=docAsnDetail.getReceivedqty().doubleValue();
                docAsnDetail.setExpectedqtySum(new BigDecimal(expectedqtySum));
                docAsnDetail.setReceivedqtySum(new BigDecimal(receivedqtySum));
//双证

            }
            docAsnHeader.setDetails(docAsnDetailList);
        }
        docAsnHeaderList.add(docAsnHeader);
        return docAsnHeaderList;
    }




}
