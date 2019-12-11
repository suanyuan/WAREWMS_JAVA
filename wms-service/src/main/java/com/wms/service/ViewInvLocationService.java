package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.ViewInvLocation;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.SfcRoleMybatisDao;
import com.wms.mybatis.dao.ViewInvLocationMybatisDao;
import com.wms.mybatis.entity.SfcRole;
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
	@Autowired
	private SfcRoleMybatisDao sfcRoleMybatisDao;

	public EasyuiDatagrid<ViewInvLocationVO> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvLocationQuery query) {
		EasyuiDatagrid<ViewInvLocationVO> datagrid = new EasyuiDatagrid<ViewInvLocationVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());

		//登录用户角色是货主就只显示该货主的数据
		List<SfcRole> sfcUsersList =sfcRoleMybatisDao.queryRoleByID(SfcUserLoginUtil.getLoginUser().getId());
		for (SfcRole sfcRole:sfcUsersList){
			if(sfcRole.getRoleName().equals("货主")){
				query.setCustomerid(SfcUserLoginUtil.getLoginUser().getId());
			}
		}

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ViewInvLocation> viewInvLocationList = viewInvLocationMybatisDao.queryByPageList(mybatisCriteria);
		ViewInvLocation viewInvLocationSum=viewInvLocationMybatisDao.queryByListSum(mybatisCriteria);
		ViewInvLocationVO viewInvLocationVO = null;
		List<ViewInvLocationVO> viewInvLocationVOList = new ArrayList<ViewInvLocationVO>();
		for (ViewInvLocation viewInvLocation : viewInvLocationList) {
			viewInvLocationVO = new ViewInvLocationVO();
			BeanUtils.copyProperties(viewInvLocation, viewInvLocationVO);
			//总计
			viewInvLocationVO.setFmqtySum(viewInvLocationSum.getFmqtySum());
			viewInvLocationVO.setFmqtyEachSum(viewInvLocationSum.getFmqtyEachSum());
			viewInvLocationVO.setQtyallocatedSum(viewInvLocationSum.getQtyallocatedSum());
			viewInvLocationVO.setQtyallocatedEachSum(viewInvLocationSum.getQtyallocatedEachSum());
			viewInvLocationVO.setQtyavailedSum(viewInvLocationSum.getQtyavailedSum());
			viewInvLocationVO.setQtyavailedEachSum(viewInvLocationSum.getQtyavailedEachSum());
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