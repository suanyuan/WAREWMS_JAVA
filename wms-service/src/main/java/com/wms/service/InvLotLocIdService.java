package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.InvLotAtt;
import com.wms.entity.InvLotLocId;
import com.wms.mybatis.dao.InvLotAttMybatisDao;
import com.wms.mybatis.dao.InvLotLocIdMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.IdSequence;
import com.wms.query.DocMtHeaderQuery;
import com.wms.query.InvLotAttQuery;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.InvLotAttVO;
import com.wms.vo.Json;
import com.wms.vo.form.InvLotAttForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("invLotLocIdService")
public class InvLotLocIdService extends BaseService {

	@Autowired
	private InvLotLocIdMybatisDao invLotLocIdMybatisDao;

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
//根据养护时间段查出in_lot_att_id的list
	public List<InvLotLocId> getLotLocIdistListByMaintenanceTime(DocMtHeaderQuery query) {


        return null;
	}



}