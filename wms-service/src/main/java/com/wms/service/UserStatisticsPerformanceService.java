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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("userStatisticsPerformanceService")
public class UserStatisticsPerformanceService extends BaseService {

	@Autowired
	private UserStatisticsPerformanceMybatisDao userStatisticsPerformanceDao;
	@Autowired
	private SfcUserMybatisDao sfcUserMybatisDao;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


	public EasyuiDatagrid<UserStatisticsPerformanceVO> getPagedDatagrid(EasyuiDatagridPager pager, UserStatisticsPerformanceQuery query) throws ParseException {
		EasyuiDatagrid<UserStatisticsPerformanceVO> datagrid = new EasyuiDatagrid<UserStatisticsPerformanceVO>();

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<UserStatisticsPerformance> userStatisticsPerformanceList = userStatisticsPerformanceDao.queryByList(mybatisCriteria);


		List<UserStatisticsPerformanceVO> userStatisticsPerformanceVOList = new ArrayList<UserStatisticsPerformanceVO>();
		for (UserStatisticsPerformance userStatisticsPerformance : userStatisticsPerformanceList) {
			UserStatisticsPerformanceVO userStatisticsPerformanceVO = new UserStatisticsPerformanceVO();
			userStatisticsPerformance.setPerfDate(simpleDateFormat.format(simpleDateFormat.parse(userStatisticsPerformance.getPerfDate())));
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
	public void exportDocPaDataToExcel(HttpServletResponse response, String token, UserStatisticsPerformanceQuery usp) throws IOException {
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
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(usp));
			List<UserStatisticsPerformance> userStatisticsPerformanceList = userStatisticsPerformanceDao.queryByList(mybatisCriteria);
			List<UserStatisticsPerformanceVO> exportVOs = new ArrayList<>();
			UserStatisticsPerformanceVO userStatisticsPerformanceVO;

			for (UserStatisticsPerformance userStatisticsPerformances : userStatisticsPerformanceList) {
				userStatisticsPerformanceVO = new UserStatisticsPerformanceVO();
				userStatisticsPerformanceVO.setUserId(userStatisticsPerformances.getUserId());
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
				ExcelUtil.listToExcel(exportVOs, fieldMap, sheetName,65535, response,null);
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

	/**
	 * 统计前一天所有人效绩
	 */
	public  void performanceStatistics() {

		//获得前一天时间
		Date dNow = new Date();
		Date dBefore = new Date();

		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
    	calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
//		calendar.add(Calendar.DAY_OF_MONTH,-0);
		dBefore = calendar.getTime();   //得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前一天
		defaultStartDate = defaultStartDate + " 00:00:00";
		String defaultEndDate = defaultStartDate.substring(0, 10) + " 23:59:59";

		//获得所有人效绩 并且插入数据库
		try {

			UserStatisticsPerformanceQuery query = new UserStatisticsPerformanceQuery();
			query.setAddtimeBegin(defaultStartDate);
			query.setAddtimeEnd(defaultEndDate);
			List<UserStatisticsPerformance> userStatisticsPerformanceList = userStatisticsPerformanceDao.performanceStatisticsList(query);
			if (userStatisticsPerformanceList.size() > 0) {
				for (UserStatisticsPerformance performance : userStatisticsPerformanceList) {
					performance.setPerfDate(sdf.format(dBefore));//统计日期
					userStatisticsPerformanceDao.add(performance);
				}
			}

		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
}