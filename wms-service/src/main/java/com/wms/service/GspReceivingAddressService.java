package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspReceivingAddress;
import com.wms.entity.PCD;
import com.wms.mybatis.dao.GspReceivingAddressMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.GspReceivingAddressQuery;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.GspReceivingAddressVO;
import com.wms.vo.Json;
import com.wms.vo.form.GspReceivingAddressForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("gspReceivingAddressService")
public class GspReceivingAddressService extends BaseService {

	//@Autowired
	//private GspReceivingAddressDao gspReceivingAddressDao;

	@Autowired
	private GspReceivingAddressMybatisDao gspReceivingAddressMybatisDao;

	@Autowired
	private CommonService commonService;


	public Json qyReceivingInfo(String customerid){

		List<GspReceivingAddress> gspReceivingInfo = gspReceivingAddressMybatisDao.qyReceivingInfo(customerid);
		GspReceivingAddress gspReceivingAddress =gspReceivingInfo.get(0);
		Json json = new Json();
		json.setMsg(JSON.toJSONString(gspReceivingAddress));
		json.setSuccess(true);
		return json;
	}
	public EasyuiDatagrid<GspReceivingAddressVO> getPagedDatagrid(EasyuiDatagridPager pager, String receivingId) {
		EasyuiDatagrid<GspReceivingAddressVO> datagrid = new EasyuiDatagrid<GspReceivingAddressVO>();
		GspReceivingAddressQuery query =new GspReceivingAddressQuery();
		query.setReceivingId(receivingId);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);

		List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryByReceivingId(receivingId);
		GspReceivingAddressVO gspReceivingAddressVO = null;
		List<GspReceivingAddressVO> gspReceivingAddressVOList = new ArrayList<GspReceivingAddressVO>();
		if (gspReceivingAddressList!=null){
		for (GspReceivingAddress gspReceivingAddress : gspReceivingAddressList) {
			gspReceivingAddressVO = new GspReceivingAddressVO();
			BeanUtils.copyProperties(gspReceivingAddress, gspReceivingAddressVO);
			gspReceivingAddressVOList.add(gspReceivingAddressVO);
		}

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
		gspReceivingAddressMybatisDao.add(gspReceivingAddress);
		json.setSuccess(true);

		return json;
	}

	public Json editGspReceivingAddress(GspReceivingAddressForm gspReceivingAddressForm) {
		Json json = new Json();
		GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryByAddressId(gspReceivingAddressForm.getReceivingAddressId());
		BeanUtils.copyProperties(gspReceivingAddressForm, gspReceivingAddress);
		gspReceivingAddressMybatisDao.updateBySelective(gspReceivingAddress);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspReceivingAddress(String id) {
		Json json = new Json();
		GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryByAddressId(id);
		if(gspReceivingAddress != null){
			gspReceivingAddressMybatisDao.delete(gspReceivingAddress);
		}
		json.setSuccess(true);
		return json;
	}

	public Json deleteAddress() {
		Json json = new Json();
		String id="";
		List<GspReceivingAddress> list = gspReceivingAddressMybatisDao.queryByReceivingId(id);
		if(list != null && list.size()>0){
			for (GspReceivingAddress gspReceivingAddress : list){
				gspReceivingAddressMybatisDao.delete(gspReceivingAddress);
				json.setSuccess(true);
			}
		}
		return json;
	}

	public List<EasyuiCombobox> getGspReceivingAddressCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryListByAll();
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


	public Json addDefaultGspReceivingAddress(GspReceivingAddressForm gspReceivingAddressForm) throws Exception {
				Json json = new Json();
				GspReceivingAddress gspReceivingAddress = new GspReceivingAddress();


		List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryByReceivingId(gspReceivingAddressForm.getReceivingId());
		if (gspReceivingAddressList != null&&gspReceivingAddressList.size()!=0) {

			for (GspReceivingAddress gspReceivingAddress1: gspReceivingAddressList){
				//如果是默认覆盖
				if ("1".equals(gspReceivingAddress1.getIsDefault())){
					gspReceivingAddress1.setIsDefault("0");
					gspReceivingAddressMybatisDao.updateBySelective(gspReceivingAddress1);
				}

			}
		}
		//新增默认地址
			BeanUtils.copyProperties(gspReceivingAddressForm, gspReceivingAddress);
			gspReceivingAddress.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			gspReceivingAddress.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			gspReceivingAddress.setReceivingAddressId(RandomUtil.getUUID());
		gspReceivingAddressMybatisDao.add(gspReceivingAddress);
		json.setSuccess(true);

		return json;
	}


	public Json editDefaultGspReceivingAddress(GspReceivingAddressForm gspReceivingAddressForm) {
		Json json = new Json();
		List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryByReceivingId(gspReceivingAddressForm.getReceivingId());
		if (gspReceivingAddressList != null&&gspReceivingAddressList.size()!=0) {
			for (GspReceivingAddress gspReceivingAddress1: gspReceivingAddressList){
				//如果是默认覆盖
				if ("1".equals(gspReceivingAddress1.getIsDefault())){
					gspReceivingAddress1.setIsDefault("0");
					gspReceivingAddressMybatisDao.updateBySelective(gspReceivingAddress1);
				}

			}
		}

		//getReceivingAddressId为空
		GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryByAddressId(gspReceivingAddressForm.getReceivingAddressId());
		//		????

		BeanUtils.copyProperties(gspReceivingAddressForm, gspReceivingAddress);
		gspReceivingAddressMybatisDao.updateBySelective(gspReceivingAddress);
		json.setSuccess(true);
		return json;
	}

}