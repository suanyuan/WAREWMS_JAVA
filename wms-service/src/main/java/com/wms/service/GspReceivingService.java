package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.entity.GspCustomer;
import com.wms.entity.GspReceivingAddress;
import com.wms.mybatis.dao.GspCustomerMybatisDao;
import com.wms.mybatis.dao.GspReceivingAddressMybatisDao;
import com.wms.mybatis.dao.GspReceivingMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspReceivingDao;
import com.wms.entity.GspReceiving;
import com.wms.vo.GspReceivingVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspReceivingForm;
import com.wms.query.GspReceivingQuery;

@Service("gspReceivingService")
public class GspReceivingService extends BaseService {

	@Autowired
	private GspReceivingDao gspReceivingDao;

	@Autowired
	private GspReceivingMybatisDao gspReceivingMybatisDao;


	@Autowired
	private GspCustomerMybatisDao gspCustomerMybatisDao;


	@Autowired
	private GspReceivingAddressMybatisDao gspReceivingAddressMybatisDao;

	public EasyuiDatagrid<GspReceivingVO> getPagedDatagrid(EasyuiDatagridPager pager, GspReceivingQuery query) {
		EasyuiDatagrid<GspReceivingVO> datagrid = new EasyuiDatagrid<GspReceivingVO>();

		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCurrentPage(pager.getPage());
			mybatisCriteria.setPageSize(pager.getRows());
			mybatisCriteria.setCondition(query);
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			List<GspReceiving> gspReceivingList = gspReceivingMybatisDao.queryByList(mybatisCriteria);

			GspReceivingVO gspReceivingVO = null;
			List<GspReceivingVO> gspReceivingVOList = new ArrayList<GspReceivingVO>();
			for (GspReceiving gspReceiving : gspReceivingList) {
				gspReceivingVO = new GspReceivingVO();
				GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryById(gspReceiving.getReceivingId());
				BeanUtils.copyProperties(gspReceiving, gspReceivingVO);
				if (gspReceivingAddress!=null){

					gspReceivingVO.setDeliveryAddress(gspReceivingAddress.getDeliveryAddress());
					gspReceivingVO.setContacts(gspReceivingAddress.getContacts());
					gspReceivingVO.setPhone(gspReceivingAddress.getPhone());
					GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(gspReceiving.getClientId());

					gspReceivingVO.setIsCooperation(gspCustomer.getIsCooperation());
				}

				gspReceivingVOList.add(gspReceivingVO);
			}
			datagrid.setTotal((long) gspReceivingMybatisDao.queryByCount(mybatisCriteria));
			datagrid.setRows(gspReceivingVOList);
		} catch (Exception e) {
			return null;
		}
		return datagrid;
	}

	public Json addGspReceiving(GspReceivingForm gspReceivingForm) throws Exception {
		Json json = new Json();
		GspReceiving gspReceiving = new GspReceiving();

		BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
        gspReceiving.setIsUse("1");
        gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
        gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
        gspReceiving.setReceivingId(RandomUtil.getUUID());
        gspReceivingMybatisDao.add(gspReceiving);


		json.setSuccess(true);
		return json;
	}

	public Json editGspReceiving(GspReceivingForm gspReceivingForm) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(gspReceivingForm.getReceivingId());
		BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
		gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
        gspReceivingMybatisDao.updateBySelective(gspReceiving);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspReceiving(String id) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(id);
		if(gspReceiving != null){
            gspReceivingMybatisDao.delete(gspReceiving);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspReceivingCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspReceiving> gspReceivingList = gspReceivingDao.findAll();
		if(gspReceivingList != null && gspReceivingList.size() > 0){
			for(GspReceiving gspReceiving : gspReceivingList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspReceiving.getReceivingId()));
				combobox.setValue(gspReceiving.getSupplierId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}