package com.wms.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.entity.BasCarrierLicense;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.WaybillStatisticsDao;
import com.wms.entity.WaybillStatistics;
import com.wms.vo.WaybillStatisticsVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.WaybillStatisticsForm;
import com.wms.query.WaybillStatisticsQuery;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("waybillStatisticsService")
public class WaybillStatisticsService extends BaseService {

	@Autowired
	private WaybillStatisticsMybatisDao WaybillStatisticsMybatisDao;



	@Autowired
	private WaybillStatisticsMybatisDao waybillStatisticsMybatisDao;

	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;


	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

	@Autowired
	private BasCarrierLicenseMybatisDao basCarrierLicenseMybatisDao;


	public EasyuiDatagrid<WaybillStatisticsVO> getPagedDatagrid(EasyuiDatagridPager pager, WaybillStatisticsQuery query) {
        EasyuiDatagrid<WaybillStatisticsVO> datagrid = new EasyuiDatagrid<WaybillStatisticsVO>();

        MybatisCriteria mybatisCriteria = new MybatisCriteria();

        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(query);
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        mybatisCriteria.setOrderByClause("`year`+0,`month`+0,`day`+0 asc");

        List<WaybillStatistics> waybillStatisticsList = WaybillStatisticsMybatisDao.queryByList(mybatisCriteria);
		WaybillStatisticsVO waybillStatisticsVO = null;
		List<WaybillStatisticsVO> waybillStatisticsVOList = new ArrayList<WaybillStatisticsVO>();
		for (WaybillStatistics waybillStatistics : waybillStatisticsList) {
			waybillStatisticsVO = new WaybillStatisticsVO();
			BeanUtils.copyProperties(waybillStatistics, waybillStatisticsVO);
			waybillStatisticsVOList.add(waybillStatisticsVO);
		}
		datagrid.setTotal((long)WaybillStatisticsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(waybillStatisticsVOList);
		return datagrid;
	}

	public Json addWaybillStatistics(WaybillStatisticsForm waybillStatisticsForm) throws Exception {
		Json json = new Json();
		WaybillStatistics waybillStatistics = new WaybillStatistics();
		BeanUtils.copyProperties(waybillStatisticsForm, waybillStatistics);
		waybillStatistics.setId(RandomUtil.getUUID());
		waybillStatistics.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		waybillStatistics.setEditId(SfcUserLoginUtil.getLoginUser().getId());
//		waybillStatistics.setCreateDate();
		WaybillStatisticsMybatisDao.add(waybillStatistics);
		json.setSuccess(true);
		return json;
	}

	public Json editWaybillStatistics(WaybillStatisticsForm waybillStatisticsForm) {
		Json json = new Json();
		WaybillStatistics waybillStatistics = WaybillStatisticsMybatisDao.queryById(waybillStatisticsForm.getWaybillStatisticsId());
		BeanUtils.copyProperties(waybillStatisticsForm, waybillStatistics);
        WaybillStatisticsMybatisDao.updateBySelective(waybillStatistics);
		json.setSuccess(true);
		return json;
	}

	public Json deleteWaybillStatistics(String id) {
		Json json = new Json();
		WaybillStatistics waybillStatistics = WaybillStatisticsMybatisDao.queryById(id);
		if(waybillStatistics != null){
            WaybillStatisticsMybatisDao.delete(waybillStatistics);
		}
		json.setSuccess(true);
		return json;
	}

	//承运商订单统计更新接口
	public void wsUpdate() {
		Json json = new Json();
//		List<BasCarrierLicense> bList = basCarrierLicenseMybatisDao.queryByList(criteria);
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
//				if(waybillStatisticsList.size()==0){
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

								WaybillStatistics ws = new WaybillStatistics();

								ws.setCarrierName(gspEnterpriseInfo.getShorthandName());
								ws.setEnterpriseId(basCarrierLicense.getEnterpriseId());
								ws.setYear(y);
								ws.setMonth(a+"");
								ws.setDay(b+"");
								WaybillStatistics s = waybillStatisticsMybatisDao.selectByTimeAndCarrier(ws);


								waybillStatistics.setOrderNum(orderNum);
								waybillStatistics.setId(s.getId());
								waybillStatisticsMybatisDao.updateBySelective(waybillStatistics);
							}

						}
					}

			}
		}

		json.setSuccess(true);
//		return json;
	}


	public void exportWaybillStatisticsDataToExcel(HttpServletResponse response, WaybillStatisticsForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());

//		InvLotAttForm invLotAttForm = new InvLotAttForm();
//        invLotAttForm.setIdList(form.getIdList());

//        basCustomerForm.setCustomerType(form.getCustomerType());
//        basCustomerForm.setCustomerid(form.getCustomerid());
//        basCustomerForm.setDescrC(form.getDescrC());
//        basCustomerForm.setActiveFlag(form.getActiveFlag());
//        basCustomerForm.setEnterpriseNo(form.getEnterpriseNo());
		try {
			WaybillStatisticsQuery query = new WaybillStatisticsQuery();
			//权限控制
//			query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
//			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			com.wms.utils.BeanUtils.copyProperties(form, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "承运商运单统计";
			// excel要导出的数据
//            List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryByList(mybatisCriteria); //要权限！james
			mybatisCriteria.setOrderByClause("`year`+0,`month`+0,`day`+0 asc");
			List<WaybillStatistics> waybillStatisticsList = WaybillStatisticsMybatisDao.queryByList(mybatisCriteria);

//			EasyuiDatagridPager page = new EasyuiDatagridPager();
//			EasyuiDatagrid<WaybillStatisticsVO> pagedDatagrid = getPagedDatagrid(page, query);
//			List<WaybillStatisticsVO> waybillStatisticsVOList = pagedDatagrid.getRows();


			// 导出
			if (waybillStatisticsList == null || waybillStatisticsList.size() == 0) {
				System.out.println("承运商运单统计为空");
			}else {
				//将list集合转化为excle
				for (WaybillStatistics WaybillStatistics : waybillStatisticsList) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

				}

				ExcelUtil.listToExcel(waybillStatisticsList, fieldMap, sheetName, response);
//                System.out.println("导出成功~~~~");
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
		superClassMap.put("carrierName", "承运商名称");
		superClassMap.put("year", "年");
		superClassMap.put("month", "月");
		superClassMap.put("day", "日");
		superClassMap.put("orderNum", "发运单数");
		superClassMap.put("complaintNum", "投诉单数");
		superClassMap.put("missingNum", "丢件");
		superClassMap.put("damageNum", "破损");
		superClassMap.put("lagNum", "到件延迟");
		superClassMap.put("otherNum", "其他");
		superClassMap.put("remark", "详情说明");


		return superClassMap;
	}





	public List<EasyuiCombobox> getWaybillStatisticsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<WaybillStatistics> waybillStatisticsList = WaybillStatisticsMybatisDao.queryByAll();
		if(waybillStatisticsList != null && waybillStatisticsList.size() > 0){
			for(WaybillStatistics waybillStatistics : waybillStatisticsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(waybillStatistics.getId()));
				combobox.setValue(waybillStatistics.getCarrierName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}