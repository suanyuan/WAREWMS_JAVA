package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.dao.RptAsnDetailDao;
import com.wms.entity.BasSku;
import com.wms.entity.RptAsnDetail;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.BasSkuVO;
import com.wms.vo.RptAsnDetailVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.RptAsnDetailForm;
import com.wms.mybatis.dao.BasLocationMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.RptAsnDetailMybatisDao;
import com.wms.query.RptAsnDetailQuery;

@Service("rptAsnDetailService")
public class RptAsnDetailService extends BaseService {

	//@Autowired
	//private RptAsnDetailDao rptAsnDetailDao;
	
	@Autowired
	private RptAsnDetailMybatisDao rptAsnDetailMybatisDao;

	public EasyuiDatagrid<RptAsnDetailVO> getPagedDatagrid(EasyuiDatagridPager pager, RptAsnDetailQuery query) {
		EasyuiDatagrid<RptAsnDetailVO> datagrid = new EasyuiDatagrid<RptAsnDetailVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<RptAsnDetail> rptAsnDetailList = rptAsnDetailMybatisDao.queryByPageList(mybatisCriteria);
		RptAsnDetailVO rptAsnDetailVO = null;
		List<RptAsnDetailVO> rptAsnDetailVOList = new ArrayList<RptAsnDetailVO>();
		for (RptAsnDetail rptAsnDetail : rptAsnDetailList) {
			rptAsnDetailVO = new RptAsnDetailVO();
			BeanUtils.copyProperties(rptAsnDetail, rptAsnDetailVO);
			rptAsnDetailVOList.add(rptAsnDetailVO);
		}
		datagrid.setTotal((long) rptAsnDetailMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(rptAsnDetailVOList);
		return datagrid;
	}

	public Json addRptAsnDetail(RptAsnDetailForm rptAsnDetailForm) throws Exception {
		Json json = new Json();
		RptAsnDetail rptAsnDetail = new RptAsnDetail();
		BeanUtils.copyProperties(rptAsnDetailForm, rptAsnDetail);
		rptAsnDetailMybatisDao.add(rptAsnDetail);
		json.setSuccess(true);
		return json;
	}

	public Json editRptAsnDetail(RptAsnDetailForm rptAsnDetailForm) {
		Json json = new Json();
		RptAsnDetail rptAsnDetail = rptAsnDetailMybatisDao.queryById(rptAsnDetailForm.getAsnno());
		BeanUtils.copyProperties(rptAsnDetailForm, rptAsnDetail);
		rptAsnDetailMybatisDao.update(rptAsnDetail);
		json.setSuccess(true);
		return json;
	}

	public Json deleteRptAsnDetail(String id) {
		Json json = new Json();
		RptAsnDetail rptAsnDetail = rptAsnDetailMybatisDao.queryById(id);
		if(rptAsnDetail != null){
			rptAsnDetailMybatisDao.delete(rptAsnDetail);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRptAsnDetailCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<RptAsnDetail> rptAsnDetailList = rptAsnDetailMybatisDao.queryByAll();
		if(rptAsnDetailList != null && rptAsnDetailList.size() > 0){
			for(RptAsnDetail rptAsnDetail : rptAsnDetailList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(rptAsnDetail.getAsnno()));
				combobox.setValue(rptAsnDetail.getAsnno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}