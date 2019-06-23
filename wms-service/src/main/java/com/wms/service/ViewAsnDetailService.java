package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.ViewAsnDetailDao;
import com.wms.entity.ViewAsnDetail;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.ViewAsnDetailVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.ViewAsnDetailForm;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ViewAsnDetailMybatisDao;
import com.wms.query.ViewAsnDetailQuery;

@Service("viewAsnDetailService")
public class ViewAsnDetailService extends BaseService {

	@Autowired
	private ViewAsnDetailDao viewAsnDetailDao;
	
	@Autowired
	private ViewAsnDetailMybatisDao ViewAsnDetailMybatisDao;

	public EasyuiDatagrid<ViewAsnDetailVO> getPagedDatagrid(EasyuiDatagridPager pager, ViewAsnDetailQuery query) {
		EasyuiDatagrid<ViewAsnDetailVO> datagrid = new EasyuiDatagrid<ViewAsnDetailVO>();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ViewAsnDetail> viewAsnDetailList = ViewAsnDetailMybatisDao.queryByPageList(mybatisCriteria);
		ViewAsnDetailVO viewAsnDetailVO = null;
		List<ViewAsnDetailVO> viewAsnDetailVOList = new ArrayList<ViewAsnDetailVO>();
		for (ViewAsnDetail viewAsnDetail : viewAsnDetailList) {
			viewAsnDetailVO = new ViewAsnDetailVO();
			BeanUtils.copyProperties(viewAsnDetail, viewAsnDetailVO);
			viewAsnDetailVOList.add(viewAsnDetailVO);
		}
		datagrid.setTotal((long) ViewAsnDetailMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(viewAsnDetailVOList);
		return datagrid;
	}

	public Json addViewAsnDetail(ViewAsnDetailForm viewAsnDetailForm) throws Exception {
		Json json = new Json();
		ViewAsnDetail viewAsnDetail = new ViewAsnDetail();
		BeanUtils.copyProperties(viewAsnDetailForm, viewAsnDetail);
		viewAsnDetailDao.save(viewAsnDetail);
		json.setSuccess(true);
		return json;
	}

	public Json editViewAsnDetail(ViewAsnDetailForm viewAsnDetailForm) {
		Json json = new Json();
		ViewAsnDetail viewAsnDetail = viewAsnDetailDao.findById(viewAsnDetailForm.getAsnno());
		BeanUtils.copyProperties(viewAsnDetailForm, viewAsnDetail);
		viewAsnDetailDao.update(viewAsnDetail);
		json.setSuccess(true);
		return json;
	}

	public Json deleteViewAsnDetail(String id) {
		Json json = new Json();
		ViewAsnDetail viewAsnDetail = viewAsnDetailDao.findById(id);
		if(viewAsnDetail != null){
			viewAsnDetailDao.delete(viewAsnDetail);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getViewAsnDetailCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<ViewAsnDetail> viewAsnDetailList = viewAsnDetailDao.findAll();
		if(viewAsnDetailList != null && viewAsnDetailList.size() > 0){
			for(ViewAsnDetail viewAsnDetail : viewAsnDetailList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(viewAsnDetail.getAsnno()));
				combobox.setValue(viewAsnDetail.getAsnno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}