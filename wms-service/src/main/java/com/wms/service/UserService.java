package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.easyui.EasyuiTree;
import com.wms.entity.User;
import com.wms.entity.UserLogin;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.*;
import com.wms.query.*;
import com.wms.utils.*;
import com.wms.utils.comparator.SfcUserComparator;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.Json;
import com.wms.vo.SfcUserVO;
import com.wms.vo.UserVO;
import com.wms.vo.form.UserForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	 * @param
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
	 * @param
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
	 * @param
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
		sfcUser.setEditWho(SfcUserLoginUtil.getLoginUser().getId());
		
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

		if (id.equals("admin")) {
			return Json.error("无法删除系统管理员账号(admin)");
		}

		Json json = new Json();
		SfcUser sfcUser = sfcUserMybatisDao.queryById(id);
		if(sfcUser != null){
			//更新节点用户的id吧，诚康项目中没有
//			UserLogin tempUserLogin = sfcUserLoginMybatisDao.queryById("temp");
//
//			MybatisCriteria mybatisCriteria = new MybatisCriteria();
//			SfcUserLoginQuery query = new SfcUserLoginQuery();
//			query.setNodeId(userLogin.getNodeId());
//			mybatisCriteria.setCondition(query);
//			List<UserLogin> subUserLoginList = sfcUserLoginMybatisDao.queryByList(mybatisCriteria);
//			for(UserLogin subUserLogin : subUserLoginList){
//				subUserLogin.setParentNodeId(tempUserLogin.getNodeId());
//                sfcUserLoginMybatisDao.update(subUserLogin);
//			}
            sfcUserMybatisDao.delete(sfcUser);
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
			sfcUserVO.setBirthday(DateUtil.format(sfcUser.getBirthday(), "yyyy-MM-dd"));
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



	public void exportUserIdDataToExcel(HttpServletResponse response, UserQuery form) throws IOException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setOrderByClause("create_time desc");
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getUserIdLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "账号管理";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="账号管理"+timeNow;
			// excel要导出的数据
			List<SfcUser> userList = sfcUserMybatisDao.queryListByAll();
			Collections.sort(userList, new SfcUserComparator());// 排序
			// 导出
			if (userList == null || userList.size() == 0) {
				System.out.println("题库为空");
			}else {

					for(SfcUser u : userList){

				//男女
					if(u.getGender()!=null){
						u.setGender(u.getGender().equals("M")?"男":"女");
					}
					//使用中
					if(u.getEnable()!=null){
						u.setEnableName(u.getEnable()==1?"是":"否");
					}
					//国籍
						if(u.getCountry()!=null){
                           u.setCountryName(u.getCountry().getCountryName());
						}
					//角色
					    if(u.getRoleSet()!=null){
							for (SfcRole sfcRole : u.getRoleSet()) {
								u.setRoleName(sfcRole.getRoleName());
								break;
							}
						}
					//仓库
						if(u.getWarehouseSet()!=null){
							for (SfcWarehouse sfcWarehouse : u.getWarehouseSet()) {
								u.setWarehouseName(sfcWarehouse.getWarehouseName());
							}
						}
					//日期转换
					if(u.getBirthday()!=null){
                       u.setBirthdayName(sdf.format(u.getBirthday()));
					}
					if(u.getCreateTime()!=null){
						u.setCreateTimeName(sdf.format(u.getCreateTime()));

					}
					if(u.getLastLoginTime()!=null){
						u.setLastLoginTimeName(sdf.format(u.getLastLoginTime()));

					}
					if(u.getEditTime()!=null){
						u.setEditTimeName(sdf.format(u.getEditTime()));

					}
				}
			}
				//将list集合转化为excle
				ExcelUtil.listToExcel(userList, fieldMap, sheetName,-1,response,fileName);
				System.out.println("导出成功~~~~");

		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getUserIdLeadToFiledPublicQuestionBank() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("userName", "名称");
		superClassMap.put("id", "用户ID");
		superClassMap.put("gender", "性别");
		superClassMap.put("enableName", "使用中");
		superClassMap.put("birthdayName", "生日");
		superClassMap.put("countryName", "国籍");
		superClassMap.put("email", "信箱");
		superClassMap.put("roleName", "角色");
		superClassMap.put("warehouseName", "仓库");
		superClassMap.put("lastLoginTimeName", "最后登入日期");
		superClassMap.put("createTimeName", "创建日期");
		superClassMap.put("createWho", "创建人");
		superClassMap.put("editTimeName", "编辑时间");
		superClassMap.put("editWho", "编辑人");

		return superClassMap;
	}
}
