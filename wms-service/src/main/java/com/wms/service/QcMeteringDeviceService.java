package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.QcMeteringDeviceMybatisDao;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.QcMeteringDevice;
import com.wms.vo.QcMeteringDeviceVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.QcMeteringDeviceForm;
import com.wms.query.QcMeteringDeviceQuery;

@Service("qcMeteringDeviceService")
public class QcMeteringDeviceService extends BaseService {

	@Autowired
	private QcMeteringDeviceMybatisDao qcMeteringDeviceMybatisDao;

	public EasyuiDatagrid<QcMeteringDeviceVO> getPagedDatagrid(EasyuiDatagridPager pager, QcMeteringDeviceQuery query) {
		EasyuiDatagrid<QcMeteringDeviceVO> datagrid = new EasyuiDatagrid<QcMeteringDeviceVO>();
		MybatisCriteria criteria = new MybatisCriteria();

		if(query.getIdList()!=null&&query.getIdList()!="" ){
			List<String> enterpriseIdList = jsonToList(query.getIdList(),String.class);
			criteria.setIdList(enterpriseIdList);
		}
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		criteria.setOrderByClause("edit_date desc");


		List<QcMeteringDevice> qcMeteringDeviceList = qcMeteringDeviceMybatisDao.queryByList(criteria);
		QcMeteringDeviceVO qcMeteringDeviceVO = null;
		List<QcMeteringDeviceVO> qcMeteringDeviceVOList = new ArrayList<QcMeteringDeviceVO>();
		for (QcMeteringDevice qcMeteringDevice : qcMeteringDeviceList) {
			qcMeteringDeviceVO = new QcMeteringDeviceVO();
			BeanUtils.copyProperties(qcMeteringDevice, qcMeteringDeviceVO);
			qcMeteringDeviceVOList.add(qcMeteringDeviceVO);
		}
		datagrid.setTotal((long) qcMeteringDeviceMybatisDao.queryByCount(criteria));
		datagrid.setRows(qcMeteringDeviceVOList);
		return datagrid;
	}

	public Json addQcMeteringDevice(QcMeteringDeviceForm qcMeteringDeviceForm) throws Exception {
		Json json = new Json();
		QcMeteringDevice qcMeteringDevice = new QcMeteringDevice();
		BeanUtils.copyProperties(qcMeteringDeviceForm, qcMeteringDevice);
		qcMeteringDevice.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		qcMeteringDevice.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		qcMeteringDevice.setCalId(RandomUtil.getUUID());
		qcMeteringDeviceMybatisDao.add(qcMeteringDevice);
		json.setSuccess(true);
		return json;
	}

	public Json editQcMeteringDevice(QcMeteringDeviceForm qcMeteringDeviceForm) {
		Json json = new Json();
		QcMeteringDevice qcMeteringDevice = qcMeteringDeviceMybatisDao.queryById(qcMeteringDeviceForm.getCalId());
		BeanUtils.copyProperties(qcMeteringDeviceForm, qcMeteringDevice);
		qcMeteringDeviceMybatisDao.updateBySelective(qcMeteringDevice);
		json.setSuccess(true);
		return json;
	}

	public Json deleteQcMeteringDevice(String id) {
		Json json = new Json();
		QcMeteringDevice qcMeteringDevice = qcMeteringDeviceMybatisDao.queryById(id);
		if(qcMeteringDevice != null){
			qcMeteringDeviceMybatisDao.delete(qcMeteringDevice);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getQcMeteringDeviceCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		List<QcMeteringDevice> qcMeteringDeviceList = qcMeteringDeviceMybatisDao.queryByList(mybatisCriteria);
		if(qcMeteringDeviceList != null && qcMeteringDeviceList.size() > 0){
			for(QcMeteringDevice qcMeteringDevice : qcMeteringDeviceList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(qcMeteringDevice.getCalId()));
				combobox.setValue(qcMeteringDevice.getCalName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	/**
	 * 获取营业执照过期数据
	 *
	 * @return
	 */
	public Json getQcMeteringDeviceOutTime(Integer diffCount){
		List<QcMeteringDevice> list = qcMeteringDeviceMybatisDao.getQcMeteringDeviceOutTime(diffCount);
		if(list!=null && list.size()>0){
			return Json.success("",list);
		}
		return Json.error("");
	}



	/**
	 * json 转 List<T>
	 */
	public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
		return ts;
	}
}