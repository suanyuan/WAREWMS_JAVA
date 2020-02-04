package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.UserStatisticsPerformance;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.SfcUserMybatisDao;
import com.wms.mybatis.dao.UserStatisticsPerformanceMybatisDao;
import com.wms.mybatis.entity.SfcUser;
import com.wms.query.UserStatisticsPerformanceQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.Json;
import com.wms.vo.UserStatisticsPerformanceVO;
import com.wms.vo.form.UserStatisticsPerformanceForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service("userStatisticsPerformanceService")
public class UserStatisticsPerformanceService extends BaseService {

	@Autowired
	private UserStatisticsPerformanceMybatisDao userStatisticsPerformanceDao;
	@Autowired
	private SfcUserMybatisDao sfcUserMybatisDao;

	public EasyuiDatagrid<UserStatisticsPerformanceVO> getPagedDatagrid(EasyuiDatagridPager pager, UserStatisticsPerformanceQuery query) {
		EasyuiDatagrid<UserStatisticsPerformanceVO> datagrid = new EasyuiDatagrid<UserStatisticsPerformanceVO>();
		//先通过用户ID查询用户信息
		SfcUser sfcUserByName = sfcUserMybatisDao.queryByName(query.getUserId());
		if(sfcUserByName != null){
			query.setUserId(sfcUserByName.getId());
		}

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<UserStatisticsPerformance> userStatisticsPerformanceList = userStatisticsPerformanceDao.queryByList(mybatisCriteria);

		//根据用户ID 查找用户信息 获取用户名
		List<UserStatisticsPerformanceVO> userStatisticsPerformanceVOList = new ArrayList<UserStatisticsPerformanceVO>();
		for (UserStatisticsPerformance userStatisticsPerformance : userStatisticsPerformanceList) {
			SfcUser sfcUserById = sfcUserMybatisDao.queryById(userStatisticsPerformance.getUserId());
			UserStatisticsPerformanceVO userStatisticsPerformanceVO = new UserStatisticsPerformanceVO();
			userStatisticsPerformance.setUserId(sfcUserById.getUserName());
			BeanUtils.copyProperties(userStatisticsPerformance, userStatisticsPerformanceVO);
			userStatisticsPerformanceVOList.add(userStatisticsPerformanceVO);
		}
		datagrid.setTotal((long)userStatisticsPerformanceDao.queryByCount(mybatisCriteria));
		datagrid.setRows(userStatisticsPerformanceVOList);
		return datagrid;
	}

	public Json addUserStatisticsPerformance(UserStatisticsPerformanceForm userStatisticsPerformanceForm) throws Exception {
		Json json = new Json();
		UserStatisticsPerformance userStatisticsPerformance = new UserStatisticsPerformance();
		BeanUtils.copyProperties(userStatisticsPerformanceForm, userStatisticsPerformance);
		userStatisticsPerformanceDao.add(userStatisticsPerformance);
		json.setSuccess(true);
		return json;
	}

	public Json editUserStatisticsPerformance(UserStatisticsPerformanceForm userStatisticsPerformanceForm) {
		Json json = new Json();
		UserStatisticsPerformance userStatisticsPerformance = userStatisticsPerformanceDao.queryById(userStatisticsPerformanceForm.getUserId());
		BeanUtils.copyProperties(userStatisticsPerformanceForm, userStatisticsPerformance);
		userStatisticsPerformanceDao.update(userStatisticsPerformance);
		json.setSuccess(true);
		return json;
	}

	public Json deleteUserStatisticsPerformance(String id) {
		Json json = new Json();
		UserStatisticsPerformance userStatisticsPerformance = userStatisticsPerformanceDao.queryById(id);
		if(userStatisticsPerformance != null){
			userStatisticsPerformanceDao.delete(userStatisticsPerformance);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getUserStatisticsPerformanceCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<UserStatisticsPerformance> userStatisticsPerformanceList = userStatisticsPerformanceDao.queryListByAll();
		if(userStatisticsPerformanceList != null && userStatisticsPerformanceList.size() > 0){
			for(UserStatisticsPerformance userStatisticsPerformance : userStatisticsPerformanceList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(userStatisticsPerformance.getId()));
				combobox.setValue(userStatisticsPerformance.getUserdefine1());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	/**
	 * 导出上架任务清单
	 */
	public void exportDocPaDataToExcel(HttpServletResponse response, String token, String userId) throws IOException {
		Cookie cookie = new Cookie("exportToken",token);
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());

		try {
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName ="用户绩效";
			// excel要导出的数据

			//先通过用户ID查询用户信息
			UserStatisticsPerformance userStatisticsPerformance = new UserStatisticsPerformance();
			SfcUser sfcUserByName = sfcUserMybatisDao.queryByName(userId);
			if(sfcUserByName != null){
				userStatisticsPerformance.setUserId(sfcUserByName.getId());
			}
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(userStatisticsPerformance));
			List<UserStatisticsPerformance> userStatisticsPerformanceList = userStatisticsPerformanceDao.queryByList(mybatisCriteria);
			List<UserStatisticsPerformanceVO> exportVOs = new ArrayList<>();
			UserStatisticsPerformanceVO userStatisticsPerformanceVO;

			for (UserStatisticsPerformance userStatisticsPerformances : userStatisticsPerformanceList) {
				userStatisticsPerformanceVO = new UserStatisticsPerformanceVO();
				userStatisticsPerformanceVO.setUserId(userId);
				userStatisticsPerformanceVO.setPerfDate(userStatisticsPerformances.getPerfDate());
				userStatisticsPerformanceVO.setPerfOrder(userStatisticsPerformances.getPerfOrder());
				userStatisticsPerformanceVO.setPerfPa(userStatisticsPerformances.getPerfPa());
				userStatisticsPerformanceVO.setPerfQc(userStatisticsPerformances.getPerfQc());
				userStatisticsPerformanceVO.setPerfPick(userStatisticsPerformances.getPerfPick());
				userStatisticsPerformanceVO.setPerfRecheck(userStatisticsPerformances.getPerfRecheck());
				exportVOs.add(userStatisticsPerformanceVO);
			}
			// 导出
			if (exportVOs.size() == 0) {
				System.out.println("用户绩效内容为空");
			}else {
				//将list集合转化为excle
				ExcelUtil.listToExcel(exportVOs, fieldMap, sheetName,65535, response,userId);
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();

		superClassMap.put("userId","用户名");
		superClassMap.put("perfDate","统计日期");
		superClassMap.put("perfOrder", "订单绩效");
		superClassMap.put("perfPa", "上架绩效");
		superClassMap.put("perfQc", "验收绩效");
		superClassMap.put("perfPick", "拣货绩效");
		superClassMap.put("perfRecheck", "复核绩效");

		return superClassMap;
	}
}