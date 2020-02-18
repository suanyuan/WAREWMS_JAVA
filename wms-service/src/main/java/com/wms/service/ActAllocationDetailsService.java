package com.wms.service;


import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.ActAllocationDetails;
import com.wms.mybatis.dao.ActAllocationDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.ActAllocationDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.ActAllocationDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.ActAllocationDetailsForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("actAllocationDetailsService")
public class ActAllocationDetailsService extends BaseService {

	@Autowired
	private ActAllocationDetailsMybatisDao actAllocationDetailsDao;

	public EasyuiDatagrid<ActAllocationDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, ActAllocationDetailsQuery query) {

		EasyuiDatagrid<ActAllocationDetailsVO> datagrid = new EasyuiDatagrid<ActAllocationDetailsVO>();

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsDao.queryByActDetailsList(mybatisCriteria);
		ActAllocationDetailsVO actAllocationDetailsVO = null;
		List<ActAllocationDetailsVO> actAllocationDetailsVOS = new ArrayList<ActAllocationDetailsVO>();
		for (ActAllocationDetails actAllocationDetails : actAllocationDetailsList) {
			actAllocationDetailsVO = new ActAllocationDetailsVO();
			BeanUtils.copyProperties(actAllocationDetails, actAllocationDetailsVO);
			actAllocationDetailsVOS.add(actAllocationDetailsVO);
		}
		datagrid.setTotal((long)actAllocationDetailsDao.queryByActDetailsCount(mybatisCriteria));
		datagrid.setRows(actAllocationDetailsVOS);
		return datagrid;
	}

	public Json addActAllocationDetails(ActAllocationDetailsForm actAllocationDetailsForm) throws Exception {
		Json json = new Json();
		ActAllocationDetails actAllocationDetails = new ActAllocationDetails();
		BeanUtils.copyProperties(actAllocationDetailsForm, actAllocationDetails);
		actAllocationDetailsDao.add(actAllocationDetails);
		json.setSuccess(true);
		return json;
	}

	public Json editActAllocationDetails(ActAllocationDetailsForm actAllocationDetailsForm) {
		Json json = new Json();
		ActAllocationDetails actAllocationDetails = actAllocationDetailsDao.queryById(actAllocationDetailsForm.getAllocationdetailsid());
		BeanUtils.copyProperties(actAllocationDetailsForm, actAllocationDetails);
		actAllocationDetailsDao.update(actAllocationDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteActAllocationDetails(String id) {
		Json json = new Json();
		ActAllocationDetails actAllocationDetails = actAllocationDetailsDao.queryById(id);
		if(actAllocationDetails != null){
			actAllocationDetailsDao.delete(actAllocationDetails);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getActAllocationDetailsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<ActAllocationDetails> actAllocationDetailsList = actAllocationDetailsDao.queryListByAll();
		if(actAllocationDetailsList != null && actAllocationDetailsList.size() > 0){
			for(ActAllocationDetails actAllocationDetails : actAllocationDetailsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(actAllocationDetails.getAllocationdetailsid()));
				combobox.setValue(actAllocationDetails.getAllocationdetailsid());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}