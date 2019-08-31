package com.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wms.constant.Constant;
import com.wms.dao.UserLoginDao;
import com.wms.mybatis.dao.SfcUserLoginMybatisDao;
import com.wms.mybatis.dao.SfcUserMybatisDao;
import com.wms.mybatis.dao.UserSessionMybatisDao;
import com.wms.mybatis.entity.SfcUser;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.SfcWarehouse;
import com.wms.query.SfcUserLoginQuery;
import com.wms.result.PdaResult;
import com.wms.utils.EncryptUtil;
import com.wms.utils.RandomUtil;
import com.wms.vo.form.pda.LoginForm;
import com.wms.vo.form.pda.WereHouseForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.UserSessionDao;
import com.wms.entity.UserSession;
import com.wms.vo.UserSessionVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.UserSessionForm;
import com.wms.query.UserSessionQuery;

@Service("userSessionService")
public class UserSessionService extends BaseService {

    @Autowired
    private UserSessionMybatisDao userSessionMybatisDao;
    @Autowired
    private SfcUserLoginMybatisDao sfcUserLoginMybatisDao;

    public EasyuiDatagrid<UserSessionVO> getPagedDatagrid(EasyuiDatagridPager pager, UserSessionQuery query) {
//		EasyuiDatagrid<UserSessionVO> datagrid = new EasyuiDatagrid<UserSessionVO>();
//		List<UserSession> userSessionList = userSessionMybatisDao.queryByList(pager, query);
//		UserSessionVO userSessionVO = null;
//		List<UserSessionVO> userSessionVOList = new ArrayList<UserSessionVO>();
//		for (UserSession userSession : userSessionList) {
//			userSessionVO = new UserSessionVO();
//			BeanUtils.copyProperties(userSession, userSessionVO);
//			userSessionVOList.add(userSessionVO);
//		}
//		datagrid.setTotal(userSessionDao.countAll(query));
//		datagrid.setRows(userSessionVOList);
//		return datagrid;
        return null;
    }

    public Json addUserSession(UserSessionForm userSessionForm) throws Exception {
        Json json = new Json();
        UserSession userSession = new UserSession();
        BeanUtils.copyProperties(userSessionForm, userSession);
        userSessionMybatisDao.add(userSession);
        json.setSuccess(true);
        return json;
    }

    public Json editUserSession(UserSessionForm userSessionForm) {
        Json json = new Json();
        UserSession userSession = userSessionMybatisDao.queryById(userSessionForm.getUserSessionId() + "");
        BeanUtils.copyProperties(userSessionForm, userSession);
        userSessionMybatisDao.update(userSession);
        json.setSuccess(true);
        return json;
    }

    public Json deleteUserSession(String id) {
        Json json = new Json();
        UserSession userSession = userSessionMybatisDao.queryById(id);
        if (userSession != null) {
            userSessionMybatisDao.delete(userSession);
        }
        json.setSuccess(true);
        return json;
    }

    public Map<String,Object> login(LoginForm form) {
        Map<String,Object> result = new HashMap<>();
        SfcUserLoginQuery query = new SfcUserLoginQuery();
        query.setWarehouseId(form.getWareHouseId());
        query.setId(form.getUserId());
        SfcUserLogin sfcUserLogin = sfcUserLoginMybatisDao.queryById(query);
        if (sfcUserLogin == null) {
            result.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "用户不存在"));
            return result;
        }
        if(sfcUserLogin.getPwd().equals(EncryptUtil.md5AndSha(form.getPwd()))){
            Map<String,Object> userInfo = new HashMap<>();
            userInfo.put("token", RandomUtil.getUUID());
            userInfo.put("userInfo", sfcUserLogin);
            result.put(Constant.DATA, userInfo);
            result.put(Constant.RESULT, new PdaResult(PdaResult.CODE_SUCCESS, Constant.SUCCESS_MSG));
        }else{
            result.put(Constant.RESULT, new PdaResult(PdaResult.CODE_FAILURE, "密码不正确"));
        }
        return result;
    }

    /**
     * 查询用户可用仓库列表
     * @param wereHouseForm id(userid)
     * @return ~
     */
    public Json queryWereHouseByUser(WereHouseForm wereHouseForm) {

        Json json = new Json();

        List<SfcWarehouse> warehouseList = sfcUserLoginMybatisDao.queryWarehouseByUser(wereHouseForm);
        if (warehouseList == null || warehouseList.size() == 0) {

            json.setSuccess(false);
            json.setMsg("查无当前用户的仓库数据");
            return json;
        }

        json.setSuccess(true);
        json.setMsg(Constant.SUCCESS_MSG);
        json.setObj(warehouseList);
        return json;
    }
}