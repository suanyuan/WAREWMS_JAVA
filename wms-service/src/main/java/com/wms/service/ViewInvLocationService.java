package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.ViewInvLocation;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewInvLocationMybatisDao;
import com.wms.query.ViewInvLocationQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.ViewInvLocationVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("viewInvLocationService")
public class ViewInvLocationService extends BaseService {

	@Autowired
	private ViewInvLocationMybatisDao viewInvLocationMybatisDao;

	public EasyuiDatagrid<ViewInvLocationVO> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvLocationQuery query) {
		EasyuiDatagrid<ViewInvLocationVO> datagrid = new EasyuiDatagrid<ViewInvLocationVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ViewInvLocation> viewInvLocationList = viewInvLocationMybatisDao.queryByPageList(mybatisCriteria);
		ViewInvLocationVO viewInvLocationVO = null;
		List<ViewInvLocationVO> viewInvLocationVOList = new ArrayList<ViewInvLocationVO>();
		for (ViewInvLocation viewInvLocation : viewInvLocationList) {
			viewInvLocationVO = new ViewInvLocationVO();
			BeanUtils.copyProperties(viewInvLocation, viewInvLocationVO);
			viewInvLocationVOList.add(viewInvLocationVO);
		}
		datagrid.setTotal((long)viewInvLocationMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(viewInvLocationVOList);
		return datagrid;
	}
//根据条件查询库所有列表 不分页
	public EasyuiDatagrid<ViewInvLocationVO> getPagedDatagridAll(ViewInvLocationQuery query) {
		EasyuiDatagrid<ViewInvLocationVO> datagrid = new EasyuiDatagrid<ViewInvLocationVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ViewInvLocation> viewInvLocationList = viewInvLocationMybatisDao.queryByListAll(mybatisCriteria);
		ViewInvLocationVO viewInvLocationVO = null;
		List<ViewInvLocationVO> viewInvLocationVOList = new ArrayList<ViewInvLocationVO>();
		for (ViewInvLocation viewInvLocation : viewInvLocationList) {
			viewInvLocationVO = new ViewInvLocationVO();
			BeanUtils.copyProperties(viewInvLocation, viewInvLocationVO);
			viewInvLocationVOList.add(viewInvLocationVO);
		}
		datagrid.setTotal((long)viewInvLocationMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(viewInvLocationVOList);
		return datagrid;
	}

}