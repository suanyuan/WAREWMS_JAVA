package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.BasCodesMybatisDao;
import com.wms.mybatis.dao.BasPackageMybatisDao;
import com.wms.mybatis.dao.InvLotLocIdMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.InvLotAttQuery;
import com.wms.query.pda.PdaInventoryQuery;
import com.wms.utils.StringUtil;
import com.wms.vo.InvLotAttVO;
import com.wms.vo.Json;
import com.wms.vo.form.pda.PageForm;
import com.wms.vo.pda.PdaInvLotLocId;
import com.wms.vo.pda.PdaInventoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("invLotLocIdService")
public class InvLotLocIdService extends BaseService {

	@Autowired
	private InvLotLocIdMybatisDao invLotLocIdMybatisDao;

	@Autowired
	private BasSkuService basSkuService;

	@Autowired
    private BasPackageMybatisDao basPackageMybatisDao;

	@Autowired
	private BasCodesMybatisDao basCodesMybatisDao;

	public EasyuiDatagrid<InvLotAttVO> getPagedDatagrid(EasyuiDatagridPager pager, InvLotAttQuery query) {
        EasyuiDatagrid<InvLotAttVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria criteria = new MybatisCriteria();
        criteria.setCurrentPage(pager.getPage());
        criteria.setPageSize(pager.getRows());
        criteria.setCondition(query);
        InvLotAttVO invLotAttVO = null;
        List<InvLotAttVO> invLotAttVOList = new ArrayList<>();
        List<InvLotAtt> invLotAttList = invLotLocIdMybatisDao.queryByList(criteria);
        for (InvLotAtt invLotAtt : invLotAttList) {
            invLotAttVO = new InvLotAttVO();
            BeanUtils.copyProperties(invLotAtt, invLotAttVO);
            invLotAttVOList.add(invLotAttVO);
        }
        int total = invLotLocIdMybatisDao.queryByCount(criteria);
        datagrid.setTotal(Long.parseLong(total+""));
        datagrid.setRows(invLotAttVOList);
        return datagrid;
	}

    /**
     * 获取扫码库位产品的库存数据,可能有多条
     * @param query 详见Controller描述
     * @return ~
     */
    public Json queryInventorys(PdaInventoryQuery query) {

        Json json = new Json();
        List<InvLotLocId> invLotLocIdList  = invLotLocIdMybatisDao.queryInventorys(query);

        if (invLotLocIdList.size() == 0) {

            json.setSuccess(false);
            json.setMsg("查无此产品在库位上的库存数据");
            return json;
        }

        PdaInventoryVO inventoryVO;
        List<PdaInventoryVO> inventoryVOList = new ArrayList<>();
        for (InvLotLocId invLotLocId : invLotLocIdList) {

            if (invLotLocId.getInvLotAtt() == null) continue;

            inventoryVO = new PdaInventoryVO();
            inventoryVO.setInvLotAtt(invLotLocId.getInvLotAtt());

            BasSku basSku = basSkuService.getSkuInfo(invLotLocId.getCustomerid(), invLotLocId.getSku());
            if (basSku == null) continue;

            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            if (basPackage == null) continue;

            inventoryVO.setBasPackage(basPackage);
            inventoryVO.setBasSku(basSku);
            inventoryVO.setInvLotAtt(invLotLocId.getInvLotAtt());

            inventoryVO.setAvailablePiece(invLotLocId.getQty().intValue());
            inventoryVO.setAvailableNumber(invLotLocId.getQty().intValue() * basPackage.getQty1().intValue());

            inventoryVOList.add(inventoryVO);
        }

        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_MSG);
        String jsonStr = JSON.toJSONString(inventoryVOList, SerializerFeature.DisableCircularReferenceDetect);
        json.setObj(JSONObject.parseArray(jsonStr, PdaInventoryVO.class));
        return json;
    }

    /**
     * 获取在库的产品记录，多库位, 多货主
     * @param query lotatt04, lotatt05, GTIN, SKU, otherCode（自赋码、序列号）
     * @return ~
     */
    public Json queryInventoryForScan(PdaInventoryQuery query, PageForm pageForm) {

        Json json = new Json();

        query.setStart(pageForm.getStart());
        query.setPageSize(pageForm.getPageSize());
        List<InvLotLocId> invLotLocIdList = invLotLocIdMybatisDao.queryInventoryForScan(query);

        PdaInvLotLocId pdaInvLotLocId;
        List<PdaInvLotLocId> pdaInvLotLocIdList = new ArrayList<>();
        for (InvLotLocId invLotLocId : invLotLocIdList) {

            pdaInvLotLocId = new PdaInvLotLocId();
            BeanUtils.copyProperties(invLotLocId, pdaInvLotLocId);

            //包装规格
            BasSku basSku = invLotLocId.getBasSku();
            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            String jsonStr = JSON.toJSONString(basPackage, SerializerFeature.DisableCircularReferenceDetect);
            pdaInvLotLocId.setBasPackage(JSONObject.parseObject(jsonStr, BasPackage.class));

            //单位
            BasCodes basCodesQuery = new BasCodes();
            basCodesQuery.setCodeid("UOM");
            basCodesQuery.setCode(basSku.getDefaultreceivinguom());
            BasCodes basCodes = basCodesMybatisDao.queryById(basCodesQuery);
            if (basCodes != null) {
                pdaInvLotLocId.getBasSku().setDefaultreceivinguom(basCodes.getCodenameC());
            }

            //同批件数
            if (invLotLocId.getInvLotAtt() != null && StringUtil.isNotEmpty(invLotLocId.getInvLotAtt().getLotatt04())) {

                double sameBatchNum = invLotLocIdMybatisDao.sumSameBatchInventory(invLotLocId.getCustomerid(), invLotLocId.getInvLotAtt().getLotatt04());
                pdaInvLotLocId.setSameBatchNum(sameBatchNum);
            }else {

                pdaInvLotLocId.setSameBatchNum(1);
            }

            pdaInvLotLocIdList.add(pdaInvLotLocId);
        }

        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_MSG);
        String jsonStr = JSON.toJSONString(pdaInvLotLocIdList, SerializerFeature.DisableCircularReferenceDetect);
        json.setObj(JSONObject.parseArray(jsonStr, PdaInvLotLocId.class));
        return json;
    }
}