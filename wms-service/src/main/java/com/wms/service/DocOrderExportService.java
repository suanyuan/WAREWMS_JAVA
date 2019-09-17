package com.wms.service;

import com.wms.entity.BasCustomer;
import com.wms.entity.BasSku;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.OrderDetailsForNormalMybatisDao;
import com.wms.mybatis.dao.OrderHeaderForNormalMybatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 *
 * @author
 * @Date
 */
@Service("docOrderExportService")
public class DocOrderExportService {


    @Autowired
    private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
    @Autowired
    private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
    @Autowired
    private BasSkuService basSkuService;
    @Autowired
    private BasCustomerMybatisDao basCustomerMybatisDao;



    /**
     * 打印order检查记录
     */
    public List<OrderHeaderForNormal> docOrderToExcel(String order) {
        List<OrderHeaderForNormal> orderHeaderForNormalList=new ArrayList<>();
        //获得单头
        OrderHeaderForNormal orderHeaderForNormal=orderHeaderForNormalMybatisDao.queryById(order);
        //获得细单
        List<OrderDetailsForNormal> orderDetailsForNormalList=orderDetailsForNormalMybatisDao.queryByOrderNo(order);
        if(orderHeaderForNormal!=null&&orderDetailsForNormalList!=null){
//            头像显示地址和电话  发运结算方式
            orderHeaderForNormal.setExcaddress1(orderHeaderForNormal.getCAddress1()==null?"":orderHeaderForNormal.getCAddress1());
            orderHeaderForNormal.setExctel1(orderHeaderForNormal.getCTel1()==null?"":orderHeaderForNormal.getCTel1());
            if(orderHeaderForNormal.getUserdefine1()!=null) {
                orderHeaderForNormal.setUserdefine1(getU1Name(orderHeaderForNormal.getUserdefine1()));
            }
            if(orderHeaderForNormal.getUserdefine2()!=null) {
                orderHeaderForNormal.setUserdefine2(getU2Name(orderHeaderForNormal.getUserdefine2()));
            }
            double qtyorderedSum=0;   //订货件数总和
            double qtyorderedEachSum=0;   //订货数量总和
            for (OrderDetailsForNormal d : orderDetailsForNormalList) {
//                供应商中文
                BasCustomer basCustomer=basCustomerMybatisDao.queryByCustomerId(d.getLotatt08());
                if(basCustomer!=null){
                    d.setLotatt08(basCustomer.getDescrC());
                }
                //计算总数
                qtyorderedSum+=d.getQtyordered();
                qtyorderedEachSum+=d.getQtyorderedEach();

                d.setQtyorderedSum(qtyorderedSum);
                d.setQtyorderedEachSum(qtyorderedEachSum);
                BasSku basSku = basSkuService.getSkuInfo(d.getCustomerid(),d.getSku());
//                细单部分01转换是否
                if(basSku!=null){
                    d.setCard(getYesNo(basSku.getSkuGroup2()));
                    d.setReport(getYesNo(basSku.getSkuGroup8()));
                    d.setDoublec(getYesNo(basSku.getSkuGroup7()));
                    d.setDescrc(basSku.getDescrC());
                }
            }

            orderHeaderForNormal.setOrderDetailsForNormalList(orderDetailsForNormalList);
        }
        orderHeaderForNormalList.add(orderHeaderForNormal);
        return orderHeaderForNormalList;
    }


    private String getYesNo(String obj){
        if(obj == null || obj.equals("0")||obj.equals("")){
            return "否";
        }else{
            return "是";
        }
    }
    private String getU1Name(String obj){
        if(obj.equals("KY")){
            return "空运";
        }else if(obj.equals("HY")){
            return "海运";
        }else{
            return "陆运";
        }
    }
    private String getU2Name(String obj){
        if(obj.equals("JF")){
            return "季付";
        }else{
            return "月结";
        }
    }

}
