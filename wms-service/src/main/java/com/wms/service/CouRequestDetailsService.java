package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.CouRequestDetailsMybatisDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.CouRequestDetails;
import com.wms.vo.CouRequestDetailsVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.CouRequestDetailsForm;
import com.wms.query.CouRequestDetailsQuery;

@Service("couRequestDetailsService")
public class CouRequestDetailsService extends BaseService {

	@Autowired
	private CouRequestDetailsMybatisDao couRequestDetailsMybatisDao;

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
		if(couRequestDetails != null){
			couRequestDetailsMybatisDao.delete(couRequestDetails);
		}
		json.setSuccess(true);
		return json;
	}


	}
