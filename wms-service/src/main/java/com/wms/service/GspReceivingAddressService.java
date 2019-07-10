package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.entity.PCD;
import com.wms.mybatis.dao.GspReceivingAddressMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspReceivingAddressDao;
import com.wms.entity.GspReceivingAddress;
import com.wms.vo.GspReceivingAddressVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspReceivingAddressForm;
import com.wms.query.GspReceivingAddressQuery;

@Service("gspReceivingAddressService")
public class GspReceivingAddressService extends BaseService {

	@Autowired
	private GspReceivingAddressDao gspReceivingAddressDao;

	@Autowired
	private GspReceivingAddressMybatisDao gspReceivingAddressMybatisDao;

	public EasyuiDatagrid<GspReceivingAddressVO> getPagedDatagrid(EasyuiDatagridPager pager, GspReceivingAddressQuery query) {
		EasyuiDatagrid<GspReceivingAddressVO> datagrid = new EasyuiDatagrid<GspReceivingAddressVO>();

		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryByList(criteria);
		GspReceivingAddressVO gspReceivingAddressVO = null;
		List<GspReceivingAddressVO> gspReceivingAddressVOList = new ArrayList<GspReceivingAddressVO>();
		for (GspReceivingAddress gspReceivingAddress : gspReceivingAddressList) {
			gspReceivingAddressVO = new GspReceivingAddressVO();
			BeanUtils.copyProperties(gspReceivingAddress, gspReceivingAddressVO);
			gspReceivingAddressVOList.add(gspReceivingAddressVO);
		}
		datagrid.setTotal((long) gspReceivingAddressMybatisDao.queryByCount(criteria));
		datagrid.setRows(gspReceivingAddressVOList);
		return datagrid;
	}

	public Json addGspReceivingAddress(GspReceivingAddressForm gspReceivingAddressForm) throws Exception {
		Json json = new Json();
		GspReceivingAddress gspReceivingAddress = new GspReceivingAddress();
		BeanUtils.copyProperties(gspReceivingAddressForm, gspReceivingAddress);
		gspReceivingAddress.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		gspReceivingAddress.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		gspReceivingAddress.setReceivingAddressId(RandomUtil.getUUID());

		//todo 关联收货单位id
		gspReceivingAddress.setReceivingId(RandomUtil.getUUID());
		gspReceivingAddressMybatisDao.add(gspReceivingAddress);
		json.setSuccess(true);
		return json;
	}

	public Json editGspReceivingAddress(GspReceivingAddressForm gspReceivingAddressForm) {
		Json json = new Json();
		GspReceivingAddress gspReceivingAddress = gspReceivingAddressDao.findById(gspReceivingAddressForm.getReceivingAddressId());
		BeanUtils.copyProperties(gspReceivingAddressForm, gspReceivingAddress);
		gspReceivingAddressDao.update(gspReceivingAddress);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspReceivingAddress(String id) {
		Json json = new Json();
		GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryById(id);
		if(gspReceivingAddress != null){
			gspReceivingAddressMybatisDao.delete(gspReceivingAddress);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspReceivingAddressCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressDao.findAll();
		if(gspReceivingAddressList != null && gspReceivingAddressList.size() > 0){
			for(GspReceivingAddress gspReceivingAddress : gspReceivingAddressList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspReceivingAddress.getReceivingId()));
				combobox.setValue(gspReceivingAddress.getReceivingAddressId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}


	public List<PCD> findPCDByPid(int pid) {
		return gspReceivingAddressMybatisDao.findPCDByPid(pid);
	}

}