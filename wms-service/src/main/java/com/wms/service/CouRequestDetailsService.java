package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.CouRequestDetails;
import com.wms.entity.CouRequestHeader;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.CouRequestDetailsMybatisDao;
import com.wms.mybatis.dao.CouRequestHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasSkuQuery;
import com.wms.query.CouRequestDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.CouRequestDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.CouRequestDetailsForm;
import com.wms.vo.form.pda.PageForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("couRequestDetailsService")
public class CouRequestDetailsService extends BaseService {

    @Autowired
    private CouRequestDetailsMybatisDao couRequestDetailsMybatisDao;
    @Autowired
    private CouRequestHeaderMybatisDao couRequestHeaderMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;

    //分页查询
    public EasyuiDatagrid<CouRequestDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, CouRequestDetailsQuery query) {
        EasyuiDatagrid<CouRequestDetailsVO> datagrid = new EasyuiDatagrid<CouRequestDetailsVO>();
        List<CouRequestDetails> couRequestDetailsList = couRequestDetailsMybatisDao.queryByList(null);
        CouRequestDetailsVO couRequestDetailsVO = null;
        List<CouRequestDetailsVO> couRequestDetailsVOList = new ArrayList<CouRequestDetailsVO>();
        for (CouRequestDetails couRequestDetails : couRequestDetailsList) {
            couRequestDetailsVO = new CouRequestDetailsVO();
            BeanUtils.copyProperties(couRequestDetails, couRequestDetailsVO);
            couRequestDetailsVOList.add(couRequestDetailsVO);
        }
        datagrid.setTotal(couRequestDetailsMybatisDao.queryTotalCount());
        datagrid.setRows(couRequestDetailsVOList);
        return datagrid;
    }

    //通过查询条件和盘点单号获取细单
    public List<CouRequestDetailsVO> getcouRequestInfoBycycleCountno(CouRequestDetailsQuery query) {

        List<CouRequestDetails> couRequestDetailsList = couRequestDetailsMybatisDao.queryListById(query);
        CouRequestDetailsVO couRequestDetailsVO = null;
        List<CouRequestDetailsVO> couRequestDetailsVOList = new ArrayList<CouRequestDetailsVO>();
        for (CouRequestDetails couRequestDetails : couRequestDetailsList) {
            couRequestDetailsVO = new CouRequestDetailsVO();
            BeanUtils.copyProperties(couRequestDetails, couRequestDetailsVO);
            couRequestDetailsVOList.add(couRequestDetailsVO);
        }
        return couRequestDetailsVOList;
    }

    public Json addCouRequestDetails(CouRequestDetailsForm couRequestDetailsForm) throws Exception {
        Json json = new Json();
        CouRequestDetails couRequestDetails = new CouRequestDetails();
        BeanUtils.copyProperties(couRequestDetailsForm, couRequestDetails);
        couRequestDetailsMybatisDao.add(couRequestDetails);
        json.setSuccess(true);
        return json;
    }

    public Json editCouRequestDetails(CouRequestDetailsForm couRequestDetailsForm) {
        Json json = new Json();
        CouRequestDetails couRequestDetails = couRequestDetailsMybatisDao.queryById(couRequestDetailsForm.getCycleCountlineno());
        BeanUtils.copyProperties(couRequestDetailsForm, couRequestDetails);
        couRequestDetailsMybatisDao.update(couRequestDetails);
        json.setSuccess(true);
        return json;
    }

    public Json deleteCouRequestDetails(String id) {
        Json json = new Json();
        CouRequestDetails couRequestDetails = couRequestDetailsMybatisDao.queryById(id);
        if (couRequestDetails != null) {
            couRequestDetailsMybatisDao.delete(couRequestDetails);
        }
        json.setSuccess(true);
        return json;
    }

    /**
     * 扫码查询产品盘点任务明细
     * @param query GTIN(Deprecated), otherCode, lotatt04, lotatt05, SKU, cycleCountno, 自赋码
     * @return ~
     */
    public Json queryCouRequestDetail(CouRequestDetailsQuery query) {

        Json json = new Json();

        List<CouRequestDetails> couRequestDetailsList = couRequestDetailsMybatisDao.queryCouRequestDetails(query);
        if (couRequestDetailsList.size() == 0){

            json.setSuccess(false);
            json.setMsg("查无此产品的盘点明细");
            return json;
        }else if (couRequestDetailsList.size() > 1) {

            json.setSuccess(false);
            json.setMsg("1，此库位上此产品存在多货主，请校验库存；\n2，请扫描此产品的GS1条码");
            return json;
        }else {

            CouRequestDetailsVO couRequestDetailsVO = new CouRequestDetailsVO();
            CouRequestDetails couRequestDetails = couRequestDetailsList.get(0);
            BeanUtils.copyProperties(couRequestDetails, couRequestDetailsVO);

            BasSkuQuery basSkuQuery = new BasSkuQuery(couRequestDetails.getCustomerid(), couRequestDetails.getSku());
            BasSku basSku = basSkuMybatisDao.queryById(basSkuQuery);
            if (basSku != null) {

                couRequestDetailsVO.setDescrc(basSku.getDescrC());
                couRequestDetailsVO.setGoodsName(basSku.getReservedfield01());
            }

            json.setSuccess(true);
            json.setMsg(Constant.SUCCESS_MSG);
            json.setObj(couRequestDetailsVO);
            return json;
        }
    }

    /**
     * 盘点提交
     * @param form  cycleCountno, cycleCountlineno, qtyAct
     * @return ~
     */
    public Json couSubmit(CouRequestDetailsForm form) {

        Json json = new Json();

        CouRequestDetails couRequestDetails = couRequestDetailsMybatisDao.queryById(form);
        if (couRequestDetails == null) {

            json.setSuccess(false);
            json.setMsg("查无此产品的盘点明细");
            return json;
        }
        double qtyDifference = StringUtil.isNotEmpty(couRequestDetails.getUserdefined1()) ? Double.valueOf(couRequestDetails.getUserdefined1()) : 0.0;
        double qtyAct = couRequestDetails.getQtyAct() + form.getQtyAct();
        String userdefine1 = qtyDifference == 0.0 ? String.valueOf(form.getQtyAct() - couRequestDetails.getQtyInv()) : String.valueOf(form.getQtyAct() + qtyDifference);
        couRequestDetails.setUserdefined1(userdefine1);
        couRequestDetails.setQtyAct(qtyAct);
        couRequestDetails.setEditwho(form.getEditwho());
        couRequestDetailsMybatisDao.update(couRequestDetails);

        CouRequestHeader couRequestHeader = couRequestHeaderMybatisDao.queryById(form.getCycleCountno());
        couRequestHeader.setStatus("30");
        couRequestHeaderMybatisDao.update(couRequestHeader);

        json.setSuccess(true);
        json.setMsg(Constant.SUBMIT_SUCCESS_MSG);
        return json;
    }

    /**
     * 盘点清单
     * @param cycleCountno 盘点单号
     * @return ~
     */
    public Json couRequestList(String cycleCountno, PageForm pageForm) {

        Json json = new Json();
        CouRequestDetailsQuery query = new CouRequestDetailsQuery();
        query.setCycleCountno(cycleCountno);
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        mybatisCriteria.setCurrentPage(pageForm.getPageNum());
        mybatisCriteria.setPageSize(pageForm.getPageSize());
        mybatisCriteria.setOrderByClause("qty_act asc, locationid desc, customerid desc, sku desc, lotatt04 desc, lotatt05 desc");
        List<CouRequestDetails> couRequestDetailsList = couRequestDetailsMybatisDao.queryByList(mybatisCriteria);

        List<CouRequestDetailsVO> couRequestDetailsVOS = new ArrayList<>();
        CouRequestDetailsVO couRequestDetailsVO;
        for (CouRequestDetails couRequestDetails : couRequestDetailsList) {

            couRequestDetailsVO = new CouRequestDetailsVO();
            BeanUtils.copyProperties(couRequestDetails, couRequestDetailsVO);
            couRequestDetailsVOS.add(couRequestDetailsVO);
        }
        json.setMsg(Constant.SUCCESS_MSG);
        json.setSuccess(true);
        json.setObj(couRequestDetailsVOS);
        return json;
    }
}

