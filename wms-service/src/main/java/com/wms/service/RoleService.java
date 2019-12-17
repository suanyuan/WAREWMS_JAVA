package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.mybatis.dao.SfcBtnMybatisDao;
import com.wms.mybatis.dao.SfcMenuMybatisDao;
import com.wms.mybatis.dao.SfcRoleMybatisDao;
import com.wms.mybatis.entity.SfcBtn;
import com.wms.mybatis.entity.SfcMenu;
import com.wms.mybatis.entity.SfcRole;
import com.wms.query.SfcBtnQuery;
import com.wms.query.SfcRoleQuery;
import com.wms.utils.comparator.SfcMenuComparator;
import com.wms.vo.Json;
import com.wms.vo.SfcRoleVO;
import com.wms.vo.form.RoleForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("roleService")
public class RoleService extends BaseService {

	@Autowired
	private SfcRoleMybatisDao sfcRoleMybatisDao;
	@Autowired
	private SfcBtnMybatisDao sfcBtnMybatisDao;
	@Autowired
	private SfcMenuMybatisDao sfcMenuMybatisDao;
	
	public EasyuiDatagrid<SfcRoleVO> getRoleDatagrid() {
		EasyuiDatagrid<SfcRoleVO> datagrid = new EasyuiDatagrid<SfcRoleVO>();
		List<SfcRole> sfcRoleList = sfcRoleMybatisDao.queryRoleListByAll();
		List<SfcRoleVO> sfcRoleVOList = new ArrayList<SfcRoleVO>();
		List<SfcMenu> sfcMenuList = null;
		SfcRoleVO sfcRoleVO = null;
		for (SfcRole sfcRole : sfcRoleList) {
			System.out.println(sfcRole.getMenuSet());
			sfcRoleVO = new SfcRoleVO();
			sfcMenuList = new ArrayList<SfcMenu>();
			BeanUtils.copyProperties(sfcRole, sfcRoleVO);
			sfcMenuList.addAll(sfcRole.getMenuSet());
			Collections.sort(sfcMenuList, new SfcMenuComparator());
			sfcRoleVO.setMenuList(sfcMenuList);
			
			sfcRoleVOList.add(sfcRoleVO);
		}
		datagrid.setTotal(sfcRoleMybatisDao.queryByCount());
		datagrid.setRows(sfcRoleVOList);
		return datagrid;
	}

	@CacheEvict(value = "btnCache", allEntries = true)
	@Transactional
	public Json addRole(RoleForm roleForm) {
		Json json = new Json();
		SfcRole sfcRole = new SfcRole();
		BeanUtils.copyProperties(roleForm, sfcRole);
		Set<SfcBtn> sfcBtnSet = null;
		if(StringUtils.isNotEmpty(roleForm.getBtns())){
			sfcBtnSet = new HashSet<SfcBtn>();
			String[] btnIdArray = roleForm.getBtns().split(",");
			for(String btnId : btnIdArray){
				SfcBtnQuery sfcBtnQuery = new SfcBtnQuery();
				sfcBtnQuery.setId(btnId);
				sfcBtnSet.add((SfcBtn) sfcBtnMybatisDao.queryById(sfcBtnQuery));
			}
		}
		sfcRole.setBtnSet(sfcBtnSet);
		
		sfcRoleMybatisDao.add(sfcRole);
		if(StringUtils.isNotEmpty(roleForm.getBtns())){
			sfcRoleMybatisDao.deleteBtnByRole(sfcRole);
			sfcRoleMybatisDao.addBtnByRole(sfcRole);
		}
		json.setSuccess(true);
		return json;
	}
	@CacheEvict(value = "btnCache", allEntries = true)
	@Transactional
	public Json editMenuBtn(RoleForm roleForm) {
		Json json = new Json();
		String menuID=roleForm.getRoleId();
		String roleID=roleForm.getRoleIDMenu();
		String[] btnId= roleForm.getBtns().split(",");
		sfcMenuMybatisDao.deleteMenuBtn(menuID,roleID);
		for (String btnID : btnId) {
			sfcMenuMybatisDao.addMenuBtn(menuID,btnID,roleID);
		}

		json.setSuccess(true);
		return json;
	}
	@CacheEvict(value = "btnCache", allEntries = true)
	@Transactional
	public Json editRole(RoleForm roleForm) {
		Json json = new Json();
		SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
		sfcRoleQuery.setId(roleForm.getRoleId());
		SfcRole sfcRole = sfcRoleMybatisDao.queryBtnListById(sfcRoleQuery);
		BeanUtils.copyProperties(roleForm, sfcRole);
		Set<SfcBtn> sfcBtnSet = null;
		if(StringUtils.isNotEmpty(roleForm.getBtns())){
			sfcBtnSet = new HashSet<SfcBtn>();
			String[] btnIdArray = roleForm.getBtns().split(",");
			for(String btnId : btnIdArray){
				SfcBtnQuery sfcBtnQuery = new SfcBtnQuery();
				sfcBtnQuery.setId(btnId);
				sfcBtnSet.add((SfcBtn) sfcBtnMybatisDao.queryById(sfcBtnQuery));
			}
		}
		sfcRole.setBtnSet(sfcBtnSet);
		
		sfcRoleMybatisDao.updateBySelective(sfcRole);
//		if(StringUtils.isNotEmpty(roleForm.getBtns())){
//			sfcRoleMybatisDao.deleteBtnByRole(sfcRole);
//			sfcRoleMybatisDao.addBtnByRole(sfcRole);
//		}
		json.setSuccess(true);
		return json;
	}

	@CacheEvict(value = "btnCache", allEntries = true)
	@Transactional
	public Json deleteRole(String id) {
		Json json = new Json();
		SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
		sfcRoleQuery.setId(id);
		SfcRole sfcRole = sfcRoleMybatisDao.queryBtnListById(sfcRoleQuery);
		if(sfcRole != null){
			sfcRoleMybatisDao.deleteBtnByRole(sfcRole);
			sfcRoleMybatisDao.delete(sfcRole);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getRoleCombobox(){
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<SfcRole> sfcRoleList = sfcRoleMybatisDao.queryListByAll();
		if(sfcRoleList != null && sfcRoleList.size() > 0){
			for(SfcRole sfcRole : sfcRoleList){
				combobox = new EasyuiCombobox();
				combobox.setId(sfcRole.getId());
				combobox.setValue(sfcRole.getRoleName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
}
