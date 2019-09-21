package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.SearchBasCustomer;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.SearchBasCustomerMybatisDao;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.exception.ExcelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service("searchBasCustomerService")
public class SearchBasCustomerService extends BaseService {

	@Autowired
	private SearchBasCustomerMybatisDao searchBasCustomerMybatisDao;



	public EasyuiDatagrid<SearchBasCustomer> getPagedDatagrid(EasyuiDatagridPager pager, SearchBasCustomer query) {
		EasyuiDatagrid<SearchBasCustomer> datagrid = new EasyuiDatagrid<SearchBasCustomer>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchBasCustomer> searchBasCustomerList = searchBasCustomerMybatisDao.querySearchCustomer(mybatisCriteria);
		datagrid.setTotal((long) searchBasCustomerMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(searchBasCustomerList);
		return datagrid;
	}




	public void exportBasCustomerDataToExcel(HttpServletResponse response, SearchBasCustomer form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "委托客户";
			// excel要导出的数据
			List<SearchBasCustomer> searchBasCustomerList = searchBasCustomerMybatisDao.querySearchCustomer(mybatisCriteria);
			// 导出
			if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (SearchBasCustomer s: searchBasCustomerList) {
//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					Date date=null;
					try {
						if(s.getLicenseExpiryDate()!=null) {
							date = sdf.parse(s.getLicenseExpiryDate());
						}
					} catch (ParseException e) {
                        continue;
					}finally {
						if(date!=null) {
							s.setLicenseExpiryDate(sdf.format(date));
						}
					}

//中文标签转换
					if(s.getIsChineseLabel()!=null){
						if(s.getIsChineseLabel().equals("1")){
							s.setIsChineseLabel("是");
						}else if(s.getIsChineseLabel().equals("0")){
							s.setIsChineseLabel("否");
						}else{
							s.setIsChineseLabel("");
						}
					}
//委托期限
                 if(s.getClientTerm()!=null){
                 	s.setClientTerm(s.getClientTerm()+"天");
				 }
				}


				//将list集合转化为excle
				ExcelUtil.listToExcel(searchBasCustomerList, fieldMap, sheetName, response);
				System.out.println("导出成功~~~~");
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
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("licenseNumber", "营业执照编号");
		superClassMap.put("residence", "住所");
		superClassMap.put("juridicalPerson", "法定代表人");
		superClassMap.put("headName", "企业负责人");
		superClassMap.put("businessResidence", "经营场所");
		superClassMap.put("warehouseAddress", "库房地址");
		superClassMap.put("businessScope", "经营范围");
		superClassMap.put("licenseNo", "经营许可证号");
		superClassMap.put("licenseExpiryDate", "许可证效期");
		superClassMap.put("registrationAuthorityL", "发证机关");
		superClassMap.put("recordNo", "备案凭证号");
		superClassMap.put("registrationAuthorityR", "发证机关");
		superClassMap.put("clientStartDate", "合同开始时间");
		superClassMap.put("clientEndDate", "合同结束时间");
		superClassMap.put("clientTerm", "委托期限");
		superClassMap.put("isChineseLabel", "是否贴中文标签");
		superClassMap.put("clientContent", "委托业务范围");
		return superClassMap;
	}

}