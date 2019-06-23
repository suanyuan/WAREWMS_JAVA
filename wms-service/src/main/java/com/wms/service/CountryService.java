package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.wms.dao.CountryDao;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.Country;
import com.wms.mybatis.dao.SfcCountryMybatisDao;
import com.wms.mybatis.entity.SfcCountry;
import com.wms.query.CountryQuery;
import com.wms.vo.CountryVO;
import com.wms.vo.Json;
import com.wms.vo.form.CountryForm;

@Service("countryService")
public class CountryService extends BaseService {
	
	@Autowired
	private CountryDao countryDao;
	@Autowired
	private SfcCountryMybatisDao sfcCountryMybatisDao;
	
	public EasyuiDatagrid<CountryVO> getCountryDatagrid(EasyuiDatagridPager pager, CountryQuery query) {
		EasyuiDatagrid<CountryVO> datagrid = new EasyuiDatagrid<CountryVO>();
		List<Country> countryList = countryDao.getPagedDatagrid(pager, query);
		
		CountryVO countryVO = null;
		List<CountryVO> countryVOList = new ArrayList<CountryVO>();
		for (Country country : countryList) {
			countryVO = new CountryVO();
			BeanUtils.copyProperties(country, countryVO);
			countryVOList.add(countryVO);
		}
		datagrid.setTotal(countryDao.countAll(query));
		datagrid.setRows(countryVOList);
		return datagrid;
	}

	@CacheEvict(value = "countryCache", allEntries = true)
	public Json addCountry(CountryForm countryForm) throws Exception {
		Json json = new Json();
		Country country = new Country();
		BeanUtils.copyProperties(countryForm, country);
		country.setId(countryDao.getCountryId());
		countryDao.save(country);
		json.setSuccess(true);
		return json;
	}
	
	@CacheEvict(value = "countryCache", allEntries = true)
	public Json editCountry(CountryForm countryForm) {
//		this.testAtomikos();
		Json json = new Json();
		Country country = countryDao.findById(countryForm.getCountryId());
		BeanUtils.copyProperties(countryForm, country);
		countryDao.update(country);
		json.setSuccess(true);
		
		return json;
	}

	@CacheEvict(value = "countryCache", allEntries = true)
	public Json deleteCountry(int id) {
		Json json = new Json();
		Country country = countryDao.findById(id);
		if(country != null){
			countryDao.delete(country);
		}
		json.setSuccess(true);
		return json;
	}
	
	public List<EasyuiCombobox> getCountryCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<SfcCountry> countryList = sfcCountryMybatisDao.queryListByAll();
		if(countryList != null && countryList.size() > 0){
			for(SfcCountry sfcCountry : countryList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(sfcCountry.getId()));
				combobox.setValue(sfcCountry.getCountryName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
}
