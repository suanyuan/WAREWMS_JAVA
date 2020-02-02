package com.wms.controller;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wms.entity.BasCarrierLicense;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.WaybillStatistics;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.utils.RandomUtil;
import com.wms.vo.BasCarrierLicenseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.service.WaybillStatisticsService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.vo.Json;
import com.wms.vo.WaybillStatisticsVO;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.WaybillStatisticsForm;
import com.wms.query.WaybillStatisticsQuery;

@Controller
@RequestMapping("waybillStatisticsController")
public class WaybillStatisticsController {

	@Autowired
	private WaybillStatisticsService waybillStatisticsService;

	@Autowired
	private WaybillStatisticsMybatisDao waybillStatisticsMybatisDao;

	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;


	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private BasCarrierLicenseMybatisDao basCarrierLicenseMybatisDao;

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Login
	@RequestMapping(params = "toMain")
	public ModelAndView toMain(String menuId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuId", menuId);



		MybatisCriteria criteria = new MybatisCriteria();
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		String monthNOw = String.valueOf(date.get(Calendar.MONTH));

		//通过年份查询发运单数
//		List<WaybillStatistics> waybillStatisticsList = waybillStatisticsMybatisDao.queryByYear(year);
//		if(waybillStatisticsList==null){
//
//		}
		//系统里  查询所有已完成订单的年份
		List<BasCarrierLicense> bList = basCarrierLicenseMybatisDao.queryByList(criteria);

		List<String> yearList = orderHeaderForNormalMybatisDao.selectALLOrderYear("");
		if(yearList!=null){
			for(String y:yearList){
				// 统计里  查询是否有该年的统计
				List<WaybillStatistics> waybillStatisticsList = waybillStatisticsMybatisDao.queryByYear(y);
				if(waybillStatisticsList.size()==0){
					//有该年的订单，但没有该年的统计    添加统计
					int month= 12;
					int day = 31;
					WaybillStatistics waybillStatistics =new WaybillStatistics();
					waybillStatistics.setYear(y);
					for(int a=1;a<=month;a++){
						String  orderNum = "";
						String aa =  a+"";
						waybillStatistics.setMonth(aa);
						for(int b=1;b<=day;b++){
							String bb =  b+"";
							 aa =  a+"";
							waybillStatistics.setDay(bb);
							if(a<10)aa="0"+aa;
							if(b<10)bb="0"+bb;
							String edittime = y+"-"+aa+"-"+bb;
//							yearList1 = orderHeaderForNormalMybatisDao.selectALLOrderYear(edittime);

							for(BasCarrierLicense  basCarrierLicense:bList){


								GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(basCarrierLicense.getEnterpriseId());
								orderNum = orderHeaderForNormalMybatisDao.selectOrderByTime(edittime,basCarrierLicense.getCarrierLicenseId());
								waybillStatistics.setOrderNum(orderNum);
								waybillStatistics.setCarrierName(gspEnterpriseInfo.getShorthandName());
								waybillStatistics.setEnterpriseId(basCarrierLicense.getEnterpriseId());

								waybillStatistics.setId(RandomUtil.getUUID());
								waybillStatisticsMybatisDao.add(waybillStatistics);
							}

						}
					}
				}else{

//					//有该年的订单，有该年的统计		更新当前时间推前一个月的发运单数
//
//					Calendar c = Calendar.getInstance();
//					c.setTime(new Date());
//					c.add(Calendar.MONTH, -1);
//					Date m = c.getTime();
//					String mon = format.format(m);
//
//					String orderNum = orderHeaderForNormalMybatisDao.selectOrderByTime("","");
//
//
//					WaybillStatistics waybillStatistics =new WaybillStatistics();
//
//					waybillStatistics.setOrderNum(orderNum);
//
//
//					waybillStatisticsMybatisDao.updateBySelective(waybillStatistics);
				}
			}
		}


		return new ModelAndView("waybillStatistics/main", model);
	}


    //承运商订单统计更新接口
    @Login
    @RequestMapping(params = "wsUpdate")
    @ResponseBody
    public void wsUpdate() throws Exception {
        waybillStatisticsService.wsUpdate();
    }

    //承运商运单统计导出
    @Login
    @RequestMapping(params = "exportWaybillStatisticsDataToExcel")
    public void exportWaybillStatisticsDataToExcel(HttpServletResponse response, WaybillStatisticsForm form) throws Exception {
        waybillStatisticsService.exportWaybillStatisticsDataToExcel(response, form);
    }

	@Login
	@RequestMapping(params = "showDatagrid")
	@ResponseBody
	public EasyuiDatagrid<WaybillStatisticsVO> showDatagrid(EasyuiDatagridPager pager, WaybillStatisticsQuery query) {
		return waybillStatisticsService.getPagedDatagrid(pager, query);
	}

	@Login
	@RequestMapping(params = "add")
	@ResponseBody
	public Json add(WaybillStatisticsForm waybillStatisticsForm) throws Exception {
		Json json = waybillStatisticsService.addWaybillStatistics(waybillStatisticsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "edit")
	@ResponseBody
	public Json edit(WaybillStatisticsForm waybillStatisticsForm) throws Exception {
		Json json = waybillStatisticsService.editWaybillStatistics(waybillStatisticsForm);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "delete")
	@ResponseBody
	public Json delete(String id) {
		Json json = waybillStatisticsService.deleteWaybillStatistics(id);
		if(json == null){
			json = new Json();
		}
			json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
		return json;
	}

	@Login
	@RequestMapping(params = "getBtn")
	@ResponseBody
	public Json getBtn(String id, HttpSession session) {
		return waybillStatisticsService.getBtn(id, (SfcUserLogin)session.getAttribute(ResourceUtil.getUserInfo()));
	}

	@Login
	@RequestMapping(params = "getCombobox")
	@ResponseBody
	public List<EasyuiCombobox> getCombobox() {
		return waybillStatisticsService.getWaybillStatisticsCombobox();
	}

}