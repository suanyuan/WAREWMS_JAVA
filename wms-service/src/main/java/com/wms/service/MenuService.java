package com.wms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wms.easyui.EasyuiTree;
import com.wms.mybatis.dao.SfcMenuMybatisDao;
import com.wms.mybatis.dao.SfcRoleMybatisDao;
import com.wms.mybatis.dao.SfcUserMybatisDao;
import com.wms.mybatis.entity.SfcMenu;
import com.wms.mybatis.entity.SfcRole;
import com.wms.mybatis.entity.SfcUser;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.SfcMenuQuery;
import com.wms.query.SfcRoleQuery;
import com.wms.utils.ResourceUtil;
import com.wms.utils.comparator.SfcMenuComparator;
import com.wms.vo.Json;
import com.wms.vo.SfcMenuVO;
import com.wms.vo.form.MenuForm;

@Service("menuService")
public class MenuService extends BaseService {

	@Autowired
	private SfcUserMybatisDao sfcUserMybatisDao;
	@Autowired
	private SfcMenuMybatisDao sfcMenuMybatisDao;
	@Autowired
	private SfcRoleMybatisDao sfcRoleMybatisDao;
	
	/**
	 * 取得左列Menu Tree
	 * @param session
	 * @return
	 */
	@Cacheable(value = "menuCache", key = "#sfcUserLogin.id")
	public Set<EasyuiTree> showMenuTree(SfcUserLogin sfcUserLogin) {
		Set<EasyuiTree> easyuiTreeSet = new TreeSet<EasyuiTree>();
		if(sfcUserLogin != null){
			SfcUser sfcUser = sfcUserMybatisDao.queryListById(sfcUserLogin);
			List<SfcMenu> allMenuList = new ArrayList<SfcMenu>();
			for(SfcRole sfcRole : sfcUser.getRoleSet()){;
				for(SfcMenu sfcMenu : sfcRole.getMenuSet()){
					allMenuList.add(sfcMenu);
				}
			}
			
			if(allMenuList != null && allMenuList.size() > 0){
				Collections.sort(allMenuList, new SfcMenuComparator());// 排序
				for(SfcMenu menu : allMenuList){
					if(StringUtils.isEmpty(menu.getParentId())){
						easyuiTreeSet.add(this.getMenuTreeNode(menu, allMenuList));
					}
				}
			}
		}
		return easyuiTreeSet;
	}

	/**
	 * 取得Menu Tree所有子節點
	 * @param menu
	 * @param allMenuList 
	 * @return
	 */
	private EasyuiTree getMenuTreeNode(SfcMenu sfcMenu, List<SfcMenu> allMenuList) {
		EasyuiTree tree = new EasyuiTree();
		tree.setId(sfcMenu.getId());
		tree.setText(sfcMenu.getMenuName());
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("menuId", sfcMenu.getId());
		attributes.put("src", sfcMenu.getUrl());
		attributes.put("type", sfcMenu.getMenuType());
		attributes.put("seq", sfcMenu.getDisplaySeq());
		tree.setAttributes(attributes);
		
		List<SfcMenu> subMenuList = new ArrayList<SfcMenu>();
		for(SfcMenu subMenu : allMenuList){
			if(subMenu.getParentId()!=null){
				if(subMenu.getParentId().equals(sfcMenu.getId())){
					subMenuList.add(subMenu);
				}
			}
		}
		
		if(subMenuList != null && subMenuList.size() > 0){
			Collections.sort(subMenuList, new SfcMenuComparator());// 排序
			
			Set<EasyuiTree> subTreeSet = new TreeSet<EasyuiTree>();
			for (SfcMenu subMenu : subMenuList) {
				subTreeSet.add(this.getMenuTreeNode(subMenu, allMenuList));
			}
			
			if(subTreeSet.size() > 0){
				tree.setState("closed");
				tree.setChildren(subTreeSet);
			}
		}
		return tree;
	}

	/**
	 * 取得管理表單所有資料
	 * @param session
	 * @return
	 */
	public Set<SfcMenuVO> showMenuTreegrid(HttpSession session) {
		SfcUserLogin sfcUserLogin = (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo());
		Set<SfcMenuVO> sfcMenuVoSet = new TreeSet<SfcMenuVO>();
		if(sfcUserLogin != null){
			SfcUser sfcUser = sfcUserMybatisDao.queryListById(sfcUserLogin);
			List<SfcMenu> allMenuList = new ArrayList<SfcMenu>();
			for(SfcRole sfcRole : sfcUser.getRoleSet()){
				for(SfcMenu sfcMenu : sfcRole.getMenuSet()){
					allMenuList.add(sfcMenu);
				}
			}
			
			Collections.sort(allMenuList, new SfcMenuComparator());// 排序
			for(SfcMenu sfcMenu : allMenuList){
				if(StringUtils.isEmpty(sfcMenu.getParentId())){
					sfcMenuVoSet.add(this.getMenuTreegridNode(sfcMenu, allMenuList));
				}
			}
		}
		return sfcMenuVoSet;
	}

	/**
	 * 取得Menu TreeGrid所有子節點
	 * @param menu
	 * @param allMenuList
	 * @return
	 */
	private SfcMenuVO getMenuTreegridNode(SfcMenu sfcMenu, List<SfcMenu> allMenuList) {
		SfcMenuVO sfcMenuVO = new SfcMenuVO();
		SfcMenuQuery sfcMenuQuery = new SfcMenuQuery();
		sfcMenuQuery.setId(sfcMenu.getId());
		sfcMenu = sfcMenuMybatisDao.queryRoleListById(sfcMenuQuery);
		BeanUtils.copyProperties(sfcMenu, sfcMenuVO);
		sfcMenuVO.setRoleSet(sfcMenu.getRoleSet());
		
		List<SfcMenu> subMenuList = new ArrayList<SfcMenu>();
		for(SfcMenu subMenu : allMenuList){
			if(subMenu.getParentId()!=null){
				if(subMenu.getParentId().equals(sfcMenu.getId())){
					subMenuList.add(subMenu);
				}
			}
		}
		
		if(subMenuList != null && subMenuList.size() > 0){
			Collections.sort(subMenuList, new SfcMenuComparator());// 排序
			
			SfcMenuVO subMenuVo = null;
			Map<String, String> parent = null;
			Set<SfcMenuVO> childrenSet = new TreeSet<SfcMenuVO>();
			for (SfcMenu subMenu : subMenuList) {
				subMenuVo = this.getMenuTreegridNode(subMenu, allMenuList);
				parent = new HashMap<String, String>();
				parent.put("menuId", sfcMenu.getId());
				parent.put("menuName", sfcMenu.getMenuName());
				subMenuVo.setParent(parent);
				childrenSet.add(subMenuVo);
			}
			
			if(childrenSet.size() > 0){
				sfcMenuVO.setState("closed");
				sfcMenuVO.setChildren(childrenSet);
			}
		}
		return sfcMenuVO;
	}
	
	/**
	 * 新增功能列表資料
	 * @param menuForm
	 * @param userLogin
	 * @return
	 */
	@CacheEvict(value = "menuCache",  key = "#sfcUserLogin.id")
	@Transactional
	public Json addMenu(MenuForm menuForm, SfcUserLogin sfcUserLogin) {
		Json json = new Json();
		
		SfcMenu sfcMenu = new SfcMenu();
		BeanUtils.copyProperties(menuForm, sfcMenu);
		
		SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
		
		Set<SfcRole> sfcRoleSet = new HashSet<SfcRole>();
		sfcRoleQuery.setId("1");
		SfcRole sfcRole = sfcRoleMybatisDao.queryMenuListById(sfcRoleQuery);
		sfcRoleSet.add(sfcRole);
		String[] roleIdArray = menuForm.getRoleList().split(",");
		for(String roleId : roleIdArray){
			sfcRoleQuery.setId(roleId);
			sfcRole = sfcRoleMybatisDao.queryMenuListById(sfcRoleQuery);
			sfcRoleSet.add(sfcRole);
		}
		
		sfcMenu.setRoleSet(sfcRoleSet);
		sfcMenuMybatisDao.add(sfcMenu);
		sfcMenuMybatisDao.deleteRoleByMenu(sfcMenu);
		sfcMenuMybatisDao.addRoleByMenu(sfcMenu);
		json.setSuccess(true);
		return json;
	}
	
	/**
	 * 修改功能列表資料
	 * @param menuForm
	 * @return
	 */
	@CacheEvict(value = "menuCache", allEntries = true)
	@Transactional
	public Json editMenu(MenuForm menuForm) {
		Json json = new Json();

		SfcMenuQuery sfcMenuQuery = new SfcMenuQuery();
		SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
		sfcMenuQuery.setId(menuForm.getMenuId());
		SfcMenu sfcMenu = sfcMenuMybatisDao.queryById(sfcMenuQuery);
		String oldParentId = sfcMenu.getParentId();
		BeanUtils.copyProperties(menuForm, sfcMenu);
		
		if(sfcMenu.getId().equals(sfcMenu.getParentId())){
			sfcMenu.setParentId(oldParentId);
		}
		
		Set<SfcRole> sfcRoleSet = new HashSet<SfcRole>();
		sfcRoleQuery.setId("1");
		SfcRole sfcRole = sfcRoleMybatisDao.queryMenuListById(sfcRoleQuery);
		sfcRoleSet.add(sfcRole);
		String[] roleIdArray = menuForm.getRoleList().split(",");
		for(String roleId : roleIdArray){
			sfcRoleQuery.setId(roleId);
			sfcRole = sfcRoleMybatisDao.queryMenuListById(sfcRoleQuery);
			sfcRoleSet.add(sfcRole);
		}
		
		//sfcMenu.getRoleSet().clear();
		//menuDao.flush();//這一句搞我一整天...
		
		sfcMenu.setRoleSet(sfcRoleSet);
		sfcMenuMybatisDao.updateBySelective(sfcMenu);
		sfcMenuMybatisDao.deleteRoleByMenu(sfcMenu);
		sfcMenuMybatisDao.addRoleByMenu(sfcMenu);
		
		json.setSuccess(true);
		return json;
	}

	/**
	 * 刪除功能列表資料
	 * @param id
	 * @return
	 */
	@CacheEvict(value = "menuCache", allEntries = true)
	public Json deleteMenu(String id) {
		Json json = new Json();
		SfcMenuQuery sfcMenuQuery = new SfcMenuQuery();
		sfcMenuQuery.setId(id);
		SfcMenu sfcMenu = sfcMenuMybatisDao.queryById(sfcMenuQuery);
		recursiveDelete(sfcMenu);
		json.setSuccess(true);
		return json;
	}
	
	/**
	 * 遞歸刪除功能列表
	 * @param menu
	 */
	private void recursiveDelete(SfcMenu sfcMenu){
		if(sfcMenu != null){
			SfcMenuQuery sfcMenuQuery = new SfcMenuQuery();
			sfcMenuQuery.setId(sfcMenu.getId());
			List<SfcMenu> subMenuList = sfcMenuMybatisDao.queryByParentId(sfcMenuQuery);
			if(subMenuList != null && subMenuList.size() > 0){
				for(SfcMenu subMenu : subMenuList){
					recursiveDelete(subMenu);
				}
			}
			//menu.getRoleSet().clear();
			//menuDao.flush();
			
			sfcMenuMybatisDao.deleteRoleByMenu(sfcMenu);
			sfcMenuMybatisDao.delete(sfcMenu);
		}
	}
}
