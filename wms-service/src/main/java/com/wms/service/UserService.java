package com.wms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.wms.mybatis.dao.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wms.dao.UserDao;
import com.wms.dao.UserLoginDao;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.easyui.EasyuiTree;
import com.wms.entity.User;
import com.wms.entity.UserLogin;
import com.wms.mybatis.entity.SfcCountry;
import com.wms.mybatis.entity.SfcCustomer;
import com.wms.mybatis.entity.SfcRole;
import com.wms.mybatis.entity.SfcUser;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.SfcWarehouse;
import com.wms.query.SfcCustomerQuery;
import com.wms.query.SfcRoleQuery;
import com.wms.query.SfcUserLoginQuery;
import com.wms.query.SfcWarehouseQuery;
import com.wms.query.UserQuery;
import com.wms.utils.EncryptUtil;
import com.wms.utils.MailUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.SmsUtil;
import com.wms.utils.comparator.SfcUserComparator;
import com.wms.utils.vo.MailVO;
import com.wms.vo.Json;
import com.wms.vo.SfcUserVO;
import com.wms.vo.UserVO;
import com.wms.vo.form.UserForm;

@Service("userService")
public class UserService extends BaseService {
	//@Autowired
	//private UserDao userDao;
	@Autowired
	private SfcUserMybatisDao sfcUserMybatisDao;
	@Autowired
	private SfcRoleMybatisDao sfcRoleMybatisDao;
	@Autowired
	private SfcCountryMybatisDao sfcCountryMybatisDao;
	//@Autowired
	//private UserLoginDao userLoginDao;
	@Autowired
	private SfcUserLoginMybatisDao sfcUserLoginMybatisDao;
	@Autowired
	private JavaMailSender mailSender;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public EasyuiDatagrid<UserVO> getPagedDatagrid(EasyuiDatagridPager pager, UserQuery query) {
		/*EasyuiDatagrid<UserVO> datagrid = new EasyuiDatagrid<UserVO>();
		List<User> userList = userDao.getPagedDatagrid(pager, query);
		UserVO userVO = null;
		List<UserVO> UserVOList = new ArrayList<UserVO>();
		for (User user : userList) {
			userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			UserVOList.add(userVO);
		}
		datagrid.setTotal(userDao.countAll(query));
		datagrid.setRows(UserVOList);
		return datagrid;*/
		EasyuiDatagrid<UserVO> datagrid = new EasyuiDatagrid<>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_time desc");
		List<User> userList = sfcUserMybatisDao.queryByList(mybatisCriteria);
		UserVO userVO = null;
		List<UserVO> userVOList = new ArrayList<>();
		if(userList!=null && userList.size()>0){
			for(User u : userList){
				userVO = new UserVO();
				BeanUtils.copyProperties(u,userVO);
				userVOList.add(userVO);
			}
		}

		int count = sfcUserMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setRows(userVOList);
		datagrid.setTotal((long)count);

		return datagrid;
	}
	
	/**
	 * 取得管理表單所有資料
	 * @param session
	 * @return
	 */
	public Set<SfcUserVO> getUserTreegrid() {
		Set<SfcUserVO> sfcUserVOSet = new TreeSet<SfcUserVO>();
		List<SfcUser> allUserList = sfcUserMybatisDao.queryListByAll();
		Collections.sort(allUserList, new SfcUserComparator());// 排序
		for(SfcUser sfcUser : allUserList){
			if(StringUtils.isEmpty(sfcUser.getParentNodeId())){
//                SfcUserVO sfcUserVO = new SfcUserVO();
			    //sfcUser.setCreateTime(new Date());

				sfcUserVOSet.add(this.getUserTreegridNode(sfcUser, allUserList));

			}
		}
		return sfcUserVOSet;
	}

	/**
	 * 取得User TreeGrid所有子節點
	 * @param user
	 * @param allUserList
	 * @return
	 */
	private SfcUserVO getUserTreegridNode(SfcUser sfcUser, List<SfcUser> allUserList) {
		SfcUserVO sfcUserVO = new SfcUserVO();
		BeanUtils.copyProperties(sfcUser, sfcUserVO);
		
		List<SfcUser> subUserList = new ArrayList<SfcUser>();
		for(SfcUser subUser : allUserList){
			if(sfcUser.getNodeId().equals(subUser.getParentNodeId())){
				subUserList.add(subUser);
			}
		}
		if(sfcUser.getCreateTime()!=null){
        	sfcUserVO.setCreateTime(simpleDateFormat.format(sfcUser.getCreateTime()));
		}
		if(sfcUser.getLastLoginTime()!=null){
			sfcUserVO.setLastLoginTime(simpleDateFormat.format(sfcUser.getLastLoginTime()));
		}
		if(sfcUser.getBirthday()!=null){
			sfcUserVO.setBirthday(simpleDateFormat.format(sfcUser.getBirthday()));
		}
		if(subUserList != null && subUserList.size() > 0){
			Collections.sort(subUserList, new SfcUserComparator());// 排序
			
			SfcUserVO subUserVo = null;
			Map<String, String> parent = null;
			List<SfcUserVO> childrenList = new ArrayList<SfcUserVO>();
			for (SfcUser subUser : subUserList) {
				subUserVo = this.getUserTreegridNode(subUser, allUserList);
				
				parent = new HashMap<String, String>();
				parent.put("parentNodeId", sfcUser.getNodeId());
				parent.put("parentName", sfcUser.getUserName());
				subUserVo.setParent(parent);
				childrenList.add(subUserVo);
			}
			
			if(childrenList.size() > 0){
				sfcUserVO.setState("closed");
				sfcUserVO.setChildren(childrenList);
			}
		}
		return sfcUserVO;
	}
	
	public Set<EasyuiTree> getUserTree() {
		Set<EasyuiTree> easyuiTreeSet = new TreeSet<EasyuiTree>();
		List<SfcUser> allUserList = sfcUserMybatisDao.queryListByAll();
			
		if(allUserList != null && allUserList.size() > 0){
			Collections.sort(allUserList, new SfcUserComparator());// 排序
			for(SfcUser sfcUser : allUserList){
				if(StringUtils.isEmpty(sfcUser.getParentNodeId())){
					easyuiTreeSet.add(this.getUserTreeNode(sfcUser, allUserList));
				}
			}
		}
		return easyuiTreeSet;
	}

	/**
	 * 取得User Tree所有子節點
	 * @param user
	 * @param allUserList 
	 * @return
	 */
	private EasyuiTree getUserTreeNode(SfcUser sfcUser, List<SfcUser> allUserList) {
		EasyuiTree tree = new EasyuiTree();
		tree.setId(sfcUser.getNodeId());
		tree.setText(sfcUser.getUserName());
		List<SfcUser> subUserList = new ArrayList<SfcUser>();
		for(SfcUser subUser : allUserList){
			if(sfcUser.getNodeId().equals(subUser.getParentNodeId())){
				if(subUser.getId() == SfcUserLoginUtil.getLoginUser().getId()){
					continue;
				}
				subUserList.add(subUser);
			}
		}
		
		if(subUserList != null && subUserList.size() > 0){
			Collections.sort(subUserList, new SfcUserComparator());// 排序
			Set<EasyuiTree> subTreeSet = new TreeSet<EasyuiTree>();
			
			for (SfcUser subUser : subUserList) {
				subTreeSet.add(this.getUserTreeNode(subUser, allUserList));
			}
			if(subTreeSet.size() > 0){
				tree.setState("closed");
				tree.setChildren(subTreeSet);
			}
		}
		return tree;
	}
	
	public void addUser(User user) throws Exception {
		sfcUserMybatisDao.add(user);
	}
	
	@Transactional
	public Json addUser(UserForm userForm) throws Exception {
		Json json = new Json();
		SfcUserLoginQuery sfcUserLoginQuery = new SfcUserLoginQuery();
		System.out.println(userForm+"=============");
		sfcUserLoginQuery.setId(userForm.getUserId());
		sfcUserLoginQuery.setWarehouseId(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());;
		SfcUserLogin sfcUserLogin = sfcUserLoginMybatisDao.queryById(sfcUserLoginQuery);
		if(sfcUserLogin == null){
			SfcUser sfcUser = new SfcUser();
			BeanUtils.copyProperties(userForm, sfcUser);
			sfcUser.setId(userForm.getUserId());
			sfcUser.setNodeId(RandomUtil.getUUID().replaceAll("-", ""));
			
			if(userForm.getUserType() > 0){
				//新增的是员工
				String pwd = null;
				if(StringUtils.isNotEmpty(userForm.getCosPassword())){
					pwd = userForm.getCosPassword();
				}else{
					pwd = RandomUtil.genPassword(5);
				}
				sfcUser.setPwd(EncryptUtil.md5AndSha(pwd));
				if(StringUtils.isNotEmpty(userForm.getCountryId())){
					sfcUser.setCountry((SfcCountry) sfcCountryMybatisDao.queryById(Integer.parseInt(userForm.getCountryId())));
				}
				sfcUser.setCreateTime(new Date());
				
				Set<SfcRole> sfcRoleSet = new HashSet<SfcRole>();
				if(StringUtils.isEmpty(userForm.getMerchantId())){
					String[] roleIdArray = userForm.getRole().split(",");
					for(String roleId : roleIdArray){
						SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
						sfcRoleQuery.setId(roleId);
						sfcRoleSet.add(sfcRoleMybatisDao.queryMenuListById(sfcRoleQuery));
					}
				}else{
					SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
					sfcRoleQuery.setRoleName("客户子帐号");
					sfcRoleSet.add(sfcRoleMybatisDao.queryUniqueIdByName(sfcRoleQuery));
				}
				sfcUser.setRoleSet(sfcRoleSet);
				Set<SfcWarehouse> sfcWarehouseSet = new HashSet<SfcWarehouse>();
				if(StringUtils.isNotEmpty(userForm.getWarehouse())){
					String[] warehouseIdArray = userForm.getWarehouse().split(",");
					for(String warehouseId : warehouseIdArray){
						SfcWarehouseQuery sfcWarehouseQuery = new SfcWarehouseQuery();
						sfcWarehouseQuery.setId(warehouseId);
						SfcWarehouse sfcWarehouse = sfcUserMybatisDao.queryWarehouseById(sfcWarehouseQuery);
						if (warehouseId.equals(userForm.getDefaultWarehouse())) {
							sfcWarehouse.setDefaultFlag("Y");
						} else {
							sfcWarehouse.setDefaultFlag("N");
						}
						sfcWarehouseSet.add(sfcWarehouse);
					}
				}
				sfcUser.setWarehouseSet(sfcWarehouseSet);
				Set<SfcCustomer> sfcCustomerSet = new HashSet<SfcCustomer>();
				if(StringUtils.isNotEmpty(userForm.getCustomer())){
					String[] customerIdArray = userForm.getCustomer().split(",");
					for(String customerId : customerIdArray){
						SfcCustomerQuery sfcCustomerQuery = new SfcCustomerQuery();
						sfcCustomerQuery.setId(customerId);
						sfcCustomerSet.add(sfcUserMybatisDao.queryCustomerById(sfcCustomerQuery));
					}
				}
				sfcUser.setCustomerSet(sfcCustomerSet);
				System.out.println(pwd);
				/*if(StringUtils.isNotEmpty(userForm.getEmail())){
					MailVO mailVO = new MailVO();
					mailVO.setTo(userForm.getEmail());
					mailVO.setSubject("密碼信");
					mailVO.setContent(String.format(ResourceUtil.getMailPwdTemplate(), sfcUser.getUserName(), pwd));
					MailUtil.sendMail(mailVO, mailSender);
				}
				
				if(StringUtils.isNotEmpty(userForm.getMerchantId()) && userForm.getUserId().matches("[0-9]+")){
					System.out.println("发送密码短信");
					SmsUtil.sendSubAccountPwd(pwd, userForm.getUserId());
//					SendSMSUtil.sendMesPost(userForm.getUserId(), String.format(ResourceUtil.getMobilePwdTemplate(), userForm.getUserId(), pwd));
				}*/
			}
			if(!StringUtils.isEmpty(sfcUser.getUserGrade())){
				if(sfcUser.getUserGrade().equals("1,2") || sfcUser.getUserGrade().equals("2,1")){
					sfcUser.setUserGrade("11");
				}else if(sfcUser.getUserGrade().equals("1")){
					sfcUser.setUserGrade("10");
				}else if(sfcUser.getUserGrade().equals("2")){
					sfcUser.setUserGrade("01");
				}
			}

			sfcUserMybatisDao.add(sfcUser);
			if(userForm.getUserType() > 0){
				if(StringUtils.isNotEmpty(userForm.getRole())){
					sfcUserMybatisDao.deleteRoleByUser(sfcUser);
					sfcUserMybatisDao.addRoleByUser(sfcUser);
				}
				if(StringUtils.isNotEmpty(userForm.getWarehouse())){
					sfcUserMybatisDao.deleteWarehouseByUser(sfcUser);
					sfcUserMybatisDao.addWarehouseByUser(sfcUser);
				}
				if(StringUtils.isNotEmpty(userForm.getCustomer())){
					sfcUserMybatisDao.deleteCustomerByUser(sfcUser);
					sfcUserMybatisDao.addCustomerByUser(sfcUser);
				}
			}
			json.setSuccess(true);
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		}else{
			json.setSuccess(false);
			json.setMsg("此帐号已经被注册过！");
		}
		return json;
	}
	
	@CacheEvict(value = "btnCache", allEntries = true)
	@Transactional
	public Json editUser(UserForm userForm) {
		Json json = new Json();
		SfcUserLogin sfcUserLogin = new SfcUserLogin();
		sfcUserLogin.setId(userForm.getUserId());
		SfcUser sfcUser = sfcUserMybatisDao.queryListById(sfcUserLogin);
		BeanUtils.copyProperties(userForm, sfcUser);
		
		if(userForm.getUserType() > 0){
			if(StringUtils.isNotEmpty(userForm.getCosPassword())){
				sfcUser.setPwd(EncryptUtil.md5AndSha(userForm.getCosPassword()));
			}
			if(StringUtils.isNotEmpty(userForm.getCountryId())){
				sfcUser.setCountry((SfcCountry) sfcCountryMybatisDao.queryById(Integer.parseInt(userForm.getCountryId())));
			}
			if(StringUtils.isNotEmpty(userForm.getRole())){
				Set<SfcRole> sfcRoleSet = new HashSet<SfcRole>();
				String[] roleIdArray = userForm.getRole().split(",");
				for(String roleId : roleIdArray){
					SfcRoleQuery sfcRoleQuery = new SfcRoleQuery();
					sfcRoleQuery.setId(roleId);
					sfcRoleSet.add(sfcRoleMybatisDao.queryMenuListById(sfcRoleQuery));
				}
				sfcUser.setRoleSet(sfcRoleSet);
			}
			if(StringUtils.isNotEmpty(userForm.getWarehouse())){
				Set<SfcWarehouse> sfcWarehouseSet = new HashSet<SfcWarehouse>();
				String[] warehouseIdArray = userForm.getWarehouse().split(",");
				for(String warehouseId : warehouseIdArray){
					SfcWarehouseQuery sfcWarehouseQuery = new SfcWarehouseQuery();
					sfcWarehouseQuery.setId(warehouseId);
					SfcWarehouse sfcWarehouse = sfcUserMybatisDao.queryWarehouseById(sfcWarehouseQuery);
					if (warehouseId.equals(userForm.getDefaultWarehouse())) {
						sfcWarehouse.setDefaultFlag("Y");
					} else {
						sfcWarehouse.setDefaultFlag("N");
					}
					sfcWarehouseSet.add(sfcWarehouse);
				}
				sfcUser.setWarehouseSet(sfcWarehouseSet);
			}
			if(StringUtils.isNotEmpty(userForm.getCustomer())){
				Set<SfcCustomer> sfcCustomerSet = new HashSet<SfcCustomer>();
				String[] customerIdArray = userForm.getCustomer().split(",");
				for(String customerId : customerIdArray){
					SfcCustomerQuery sfcCustomerQuery = new SfcCustomerQuery();
					sfcCustomerQuery.setId(customerId);
					sfcCustomerSet.add(sfcUserMybatisDao.queryCustomerById(sfcCustomerQuery));
				}
				sfcUser.setCustomerSet(sfcCustomerSet);
			}
		}

		if(!StringUtils.isEmpty(sfcUser.getUserGrade())){
			if(sfcUser.getUserGrade().equals("1,2") || sfcUser.getUserGrade().equals("2,1")){
				sfcUser.setUserGrade("11");
			}else if(sfcUser.getUserGrade().equals("1")){
				sfcUser.setUserGrade("10");
			}else if(sfcUser.getUserGrade().equals("2")){
				sfcUser.setUserGrade("01");
			}
		}

		sfcUserMybatisDao.updateBySelective(sfcUser);
		if(userForm.getUserType() > 0){
			if(StringUtils.isNotEmpty(userForm.getRole())){
				sfcUserMybatisDao.deleteRoleByUser(sfcUser);
				sfcUserMybatisDao.addRoleByUser(sfcUser);
			}
			if(StringUtils.isNotEmpty(userForm.getWarehouse())){
				sfcUserMybatisDao.deleteWarehouseByUser(sfcUser);
				sfcUserMybatisDao.addWarehouseByUser(sfcUser);
			}
			sfcUserMybatisDao.deleteCustomerByUser(sfcUser);
			if(StringUtils.isNotEmpty(userForm.getCustomer())){
				sfcUserMybatisDao.addCustomerByUser(sfcUser);
			}
		}
		json.setSuccess(true);
		json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	public Json deleteUser(String id) {
		Json json = new Json();
		UserLogin userLogin = sfcUserLoginMybatisDao.queryById(id);
		if(userLogin != null && userLogin.getUserType() == 0){
			UserLogin tempUserLogin = sfcUserLoginMybatisDao.queryById("temp");

			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			SfcUserLoginQuery query = new SfcUserLoginQuery();
			query.setNodeId(userLogin.getNodeId());
			mybatisCriteria.setCondition(query);
			List<UserLogin> subUserLoginList = sfcUserLoginMybatisDao.queryByList(mybatisCriteria);
			for(UserLogin subUserLogin : subUserLoginList){
				subUserLogin.setParentNodeId(tempUserLogin.getNodeId());
                sfcUserLoginMybatisDao.update(subUserLogin);
			}
            sfcUserLoginMybatisDao.delete(userLogin);
		}
		json.setSuccess(true);
		return json;
	}
	
	public SfcUserVO getUser(SfcUserLogin sfcUserLogin) {
		SfcUserVO sfcUserVO = null;
		if(sfcUserLogin != null){
			SfcUser sfcUser = sfcUserMybatisDao.queryListById(sfcUserLogin);
			sfcUserVO = new SfcUserVO();
			BeanUtils.copyProperties(sfcUser, sfcUserVO);
		}
		return sfcUserVO;
	}
	public List<EasyuiCombobox> getSupervisorCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		UserLogin userLogin = sfcUserLoginMybatisDao.queryById("supervisor");

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        SfcUserLoginQuery query = new SfcUserLoginQuery();
        query.setNodeId(userLogin.getNodeId());
        mybatisCriteria.setCondition(query);
        List<UserLogin> userLoginList = sfcUserLoginMybatisDao.queryByList(mybatisCriteria);
		//List<UserLogin> userLoginList = userLoginDao.findChildrenByParentNodeId(userLogin.getNodeId());
		if(userLoginList != null && userLoginList.size() > 0){
			for(UserLogin subUserLogin : userLoginList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(subUserLogin.getId()));
				combobox.setValue(subUserLogin.getUserName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getWarehouseCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<SfcWarehouse> warehouseIdList = sfcUserMybatisDao.queryWarehouseByAll();
		if(warehouseIdList != null && warehouseIdList.size() > 0){
			for(SfcWarehouse warehouseId : warehouseIdList){
				combobox = new EasyuiCombobox();
				combobox.setId(warehouseId.getId());
				combobox.setValue(warehouseId.getWarehouseName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getDefaultWarehouseCombobox(UserForm userForm) {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		SfcUser sfcUser = new SfcUser();
		sfcUser.setId(userForm.getUserId());

		Set<SfcWarehouse> sfcWarehouseSet = new HashSet<SfcWarehouse>();
		if(StringUtils.isNotEmpty(userForm.getWarehouse())){
			String[] warehouseIdArray = userForm.getWarehouse().split(",");
			for(String warehouseId : warehouseIdArray){
				SfcWarehouseQuery sfcWarehouseQuery = new SfcWarehouseQuery();
				sfcWarehouseQuery.setId(warehouseId);
				sfcWarehouseSet.add(sfcUserMybatisDao.queryWarehouseById(sfcWarehouseQuery));
			}
		}
		sfcUser.setWarehouseSet(sfcWarehouseSet);
		List<SfcWarehouse> warehouseIdList = sfcUserMybatisDao.queryDefaultWarehouseListByUser(sfcUser);
		if(warehouseIdList != null && warehouseIdList.size() > 0){
			for(SfcWarehouse warehouseId : warehouseIdList){
				combobox = new EasyuiCombobox();
				combobox.setId(warehouseId.getId());
				combobox.setValue(warehouseId.getWarehouseName());
				if (warehouseId.getDefaultFlag().equals("Y")) {
					combobox.setSelected(true);
				} else {
					combobox.setSelected(false);
				}
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getCustomerCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<SfcCustomer> customerIdList = sfcUserMybatisDao.queryCustomerByAll();
		if(customerIdList != null && customerIdList.size() > 0){
			for(SfcCustomer customerId : customerIdList){
				combobox = new EasyuiCombobox();
				combobox.setId(customerId.getId());
				combobox.setValue(customerId.getCustomerName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
}
