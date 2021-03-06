package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
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

@Service("drugControlService")
public class DrugControlService extends BaseService {

	@Autowired
	private SearchBasCustomerMybatisDao searchBasCustomerMybatisDao;
	@Autowired
	private BasCodesService basCodesService;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HHmmss");

	/**************************************委托客户****************************************/
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
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="委托客户"+timeNow;
			// excel要导出的数据
			List<SearchBasCustomer> searchBasCustomerList = searchBasCustomerMybatisDao.querySearchCustomer(mybatisCriteria);
			// 导出
			if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (SearchBasCustomer s: searchBasCustomerList) {
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
				ExcelUtil.listToExcel(searchBasCustomerList, fieldMap, sheetName,-1,response,fileName);
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
		superClassMap.put("clientStartDate", "开始委托时间");
		superClassMap.put("clientEndDate", "停止委托时间");
		superClassMap.put("clientTerm", "委托期限");
		superClassMap.put("isChineseLabel", "是否委托加贴中文标签");
		superClassMap.put("clientContent", "委托业务范围");
		return superClassMap;
	}

	/******************************   货主商品   *********************************/

	public EasyuiDatagrid<CustomerProduct> getCustomerProductPagedDatagrid(EasyuiDatagridPager pager, CustomerProduct query) {
		EasyuiDatagrid<CustomerProduct> datagrid = new EasyuiDatagrid<CustomerProduct>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		System.out.println();
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<CustomerProduct> searchCustomerProductList = searchBasCustomerMybatisDao.queryCustomerProduct(mybatisCriteria);
		datagrid.setTotal((long) searchBasCustomerMybatisDao.queryCustomerProductByCount(mybatisCriteria));
		datagrid.setRows(searchCustomerProductList);
		return datagrid;
	}


    public void exportCustomerProductDataToExcel(HttpServletResponse response, CustomerProduct form) throws IOException {
        Cookie cookie = new Cookie("exportToken",form.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());
        try {
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getCustomerProductLeadToFiledPublicQuestionBank();
            // excel的sheetName
            String sheetName = "货主商品";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="货主商品"+timeNow;
            // excel要导出的数据
            List<CustomerProduct> searchBasCustomerList = searchBasCustomerMybatisDao.queryCustomerProduct(mybatisCriteria);
            // 导出
            if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
                System.out.println("题库为空");
            }else {
                for (CustomerProduct s: searchBasCustomerList) {
//                    //时间格式转换
                    System.out.println(1);
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    Date date=null;
                    try {
                        if(s.getProductRegisterExpiryDate()!=null) {
                            date = sdf.parse(s.getProductRegisterExpiryDate());
                        }
                    } catch (ParseException e) {
                        continue;
                    }finally {
                        if(date!=null) {
                            s.setProductRegisterExpiryDate(sdf.format(date));
                        }
                    }

                    try {
                        if(s.getApproveDate()!=null) {
                            date = sdf.parse(s.getApproveDate());
                        }
                    } catch (ParseException e) {
                        continue;
                    }finally {
                        if(date!=null) {
                            s.setApproveDate(sdf.format(date));
                        }
                    }

//                    //中文标签转换
//                    if(s.getIsChineseLabel()!=null){
//                        if(s.getIsChineseLabel().equals("1")){
//                            s.setIsChineseLabel("是");
//                        }else if(s.getIsChineseLabel().equals("0")){
//                            s.setIsChineseLabel("否");
//                        }else{
//                            s.setIsChineseLabel("");
//                        }
//                    }
//
                }


                //将list集合转化为excle
                ExcelUtil.listToExcel(searchBasCustomerList, fieldMap, sheetName,-1,response,fileName);
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
    public LinkedHashMap<String, String> getCustomerProductLeadToFiledPublicQuestionBank() {

        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
        superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("productCode", "产品代码");
        superClassMap.put("productName", "委托方货品名称");
        superClassMap.put("specsName", "规格/型号");
//        superClassMap.put("productModel", "型号");
        superClassMap.put("productRegisterNo", "产品注册证号/备案凭证号");
		superClassMap.put("approveDate", "批准日期");
		superClassMap.put("productRegisterExpiryDate", "有效期");
        superClassMap.put("enterpriseSc", "生产企业");
        superClassMap.put("licenseOrRecordNo", "生产许可证号/备案号");
        superClassMap.put("unit", "单位");
        superClassMap.put("storageCondition", "储存条件");

        return superClassMap;
    }





	/**************************************库存信息****************************************/
	public EasyuiDatagrid<SearchInvLocation> showSearchInvLocationDatagrid(EasyuiDatagridPager pager, SearchInvLocation query) {
		EasyuiDatagrid<SearchInvLocation> datagrid = new EasyuiDatagrid<SearchInvLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchInvLocation> searchInvLocationList = searchBasCustomerMybatisDao.querySearchInvLocation(mybatisCriteria);
		for (SearchInvLocation searchInvLocation : searchInvLocationList) {
			//计算库存数量
			if(searchInvLocation.getQty()!=null&&searchInvLocation.getQty1()!=null) {
				searchInvLocation.setQtyeach(searchInvLocation.getQty() * searchInvLocation.getQty1());
			}
		}
		datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchInvLocationCount(mybatisCriteria));
		datagrid.setRows(searchInvLocationList);
		return datagrid;
	}
	//库存信息统计
	public EasyuiDatagrid<SearchInvLocation> showSearchInvLocationDatagridSum(EasyuiDatagridPager pager, SearchInvLocation query) {
		EasyuiDatagrid<SearchInvLocation> datagrid = new EasyuiDatagrid<SearchInvLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchInvLocation> searchInvLocationList = searchBasCustomerMybatisDao.querySearchInvLocationSum(mybatisCriteria);
		for (SearchInvLocation searchInvLocation : searchInvLocationList) {
			//计算库存数量
			if(searchInvLocation.getQty()!=null&&searchInvLocation.getQty1()!=null) {
				searchInvLocation.setQtyeach(searchInvLocation.getQty() * searchInvLocation.getQty1());
			}
		}
		datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchInvLocationCount(mybatisCriteria));
		datagrid.setRows(searchInvLocationList);
		return datagrid;
	}


	public void exportSearchInvLocationDataToExcel(HttpServletResponse response,SearchInvLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getSearchInvLocationLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "库存信息";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="库存信息"+timeNow;
			// excel要导出的数据
			List<SearchInvLocation> searchInvLocationList = searchBasCustomerMybatisDao.querySearchInvLocation(mybatisCriteria);
			// 导出
			if (searchInvLocationList == null || searchInvLocationList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (SearchInvLocation s: searchInvLocationList) {
//					//时间格式转换
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					Date date=null;
//					try {
//						if(s.getLicenseExpiryDate()!=null) {
//							date = sdf.parse(s.getLicenseExpiryDate());
//						}
//					} catch (ParseException e) {
//						continue;
//					}finally {
//						if(date!=null) {
//							s.setLicenseExpiryDate(sdf.format(date));
//						}
//					}
//
					//库存数量
					if(s.getQty()!=null&&s.getQty1()!=null) {
						s.setQtyeach(s.getQty() * s.getQty1());
					}
					//质量状态
					if (s.getLotatt10() != null) {
						List<EasyuiCombobox> comboboxList = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
						for (EasyuiCombobox easyuiCombobox : comboboxList) {
                           if(s.getLotatt10().equals(easyuiCombobox.getId())){
                           	  s.setLotatt10(easyuiCombobox.getValue());
                           	  break;
						   }
						}
					}
					//生产批号/序列号
//                    if(!s.getLotatt04().isEmpty()){
//                        if(!s.getLotatt05().isEmpty()){
//                            s.setLotatt04(s.getLotatt04()+"/"+s.getLotatt05());
//                        }
//                    }else if(!s.getLotatt05().isEmpty()){
//                        s.setLotatt04(s.getLotatt05());
//                    }
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(searchInvLocationList, fieldMap, sheetName,-1,response,fileName);
				System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}
	//库存信息-统计
	public void exportSearchInvLocationDataToExcelSum(HttpServletResponse response,SearchInvLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getSearchInvLocationLeadToFiledPublicQuestionBankSum();
			// excel的sheetName
			String sheetName = "库存信息";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="库存信息"+timeNow;
			// excel要导出的数据
			List<SearchInvLocation> searchInvLocationList = searchBasCustomerMybatisDao.querySearchInvLocationSum(mybatisCriteria);
			// 导出
			if (searchInvLocationList == null || searchInvLocationList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (SearchInvLocation s: searchInvLocationList) {
//					//时间格式转换
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					Date date=null;
//					try {
//						if(s.getLicenseExpiryDate()!=null) {
//							date = sdf.parse(s.getLicenseExpiryDate());
//						}
//					} catch (ParseException e) {
//						continue;
//					}finally {
//						if(date!=null) {
//							s.setLicenseExpiryDate(sdf.format(date));
//						}
//					}
//
					//库存数量
					if(s.getQty()!=null&&s.getQty1()!=null) {
						s.setQtyeach(s.getQty() * s.getQty1());
					}
					//质量状态
					if (s.getLotatt10() != null) {
						List<EasyuiCombobox> comboboxList = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
						for (EasyuiCombobox easyuiCombobox : comboboxList) {
							if(s.getLotatt10().equals(easyuiCombobox.getId())){
								s.setLotatt10(easyuiCombobox.getValue());
								break;
							}
						}
					}
					//生产批号/序列号
//                    if(!s.getLotatt04().isEmpty()){
//                        if(!s.getLotatt05().isEmpty()){
//                            s.setLotatt04(s.getLotatt04()+"/"+s.getLotatt05());
//                        }
//                    }else if(!s.getLotatt05().isEmpty()){
//                        s.setLotatt04(s.getLotatt05());
//                    }
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(searchInvLocationList, fieldMap, sheetName,-1,response,fileName);
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
	public LinkedHashMap<String, String> getSearchInvLocationLeadToFiledPublicQuestionBank() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("descrc", "规格/型号");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
		superClassMap.put("lotatt04", "生产批号/序列号");
		superClassMap.put("lotatt01Andlotatt02", "生产日期和有效期(或者失效期)");
//		superClassMap.put("qty", "库存件数");
		superClassMap.put("qtyeach", "库存数量");
		superClassMap.put("uom", "单位");
		superClassMap.put("locationid", "库存地点(货架号)");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("notes", "备注");
//		superClassMap.put("qty1", "换算率");
		return superClassMap;
	}
     /*库存信息-统计*/
	public LinkedHashMap<String, String> getSearchInvLocationLeadToFiledPublicQuestionBankSum() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("supplierName", "供应商");
		superClassMap.put("lotatt14", "入库单号");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("inventoryage", "库龄(天)");
		superClassMap.put("sku", "产品代码");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("descrc", "规格/型号");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("reservedfield06", "生产企业许可证号/备案凭证号");
		superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
		superClassMap.put("lotatt04", "生产批号");
		superClassMap.put("lotatt05", "序列号");
		superClassMap.put("lotatt01", "生产日期");
		superClassMap.put("lotatt02", "有效期/失效期");
		superClassMap.put("qty", "库存件数");
		superClassMap.put("qtyeach", "库存数量");
		superClassMap.put("uom", "单位");
		superClassMap.put("locationid", "库存地点(货架号)");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("productLineName", "产品线");
		superClassMap.put("notes", "备注");
		superClassMap.put("qty1", "换算率");
		return superClassMap;
	}







	/**************************************入库信息****************************************/
	//药监核查-入库信息
	public EasyuiDatagrid<SearchEnterInvLocation> showSearchEnterInvLocationDatagrid(EasyuiDatagridPager pager, SearchEnterInvLocation query) {
		EasyuiDatagrid<SearchEnterInvLocation> datagrid = new EasyuiDatagrid<SearchEnterInvLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchEnterInvLocation> searchEnterInvLocationList = searchBasCustomerMybatisDao.querySearchEnterInvLocation(mybatisCriteria);
		datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchEnterInvLocationCount(mybatisCriteria));
		datagrid.setRows(searchEnterInvLocationList);
		return datagrid;
	}
	//统计分析-入库信息
	public EasyuiDatagrid<SearchEnterInvLocation> showSearchEnterInvLocationDatagridSum(EasyuiDatagridPager pager, SearchEnterInvLocation query) {
		EasyuiDatagrid<SearchEnterInvLocation> datagrid = new EasyuiDatagrid<SearchEnterInvLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchEnterInvLocation> searchEnterInvLocationList = searchBasCustomerMybatisDao.querySearchEnterInvLocationSum(mybatisCriteria);
		datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchEnterInvLocationCount(mybatisCriteria));
		datagrid.setRows(searchEnterInvLocationList);
		return datagrid;
	}


	public void exportSearchEnterInvLocationDataToExcel(HttpServletResponse response,SearchEnterInvLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getSearchEnterInvLocationLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "入库信息";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="入库信息"+timeNow;
			// excel要导出的数据
			List<SearchEnterInvLocation> searchEnterInvLocationList = searchBasCustomerMybatisDao.querySearchEnterInvLocation(mybatisCriteria);
			// 导出
			if (searchEnterInvLocationList != null && searchEnterInvLocationList.size() != 0) {
				for (SearchEnterInvLocation s: searchEnterInvLocationList) {
//					//时间格式转换
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					Date date=null;
//					try {
//						if(s.getLicenseExpiryDate()!=null) {
//							date = sdf.parse(s.getLicenseExpiryDate());
//						}
//					} catch (ParseException e) {
//						continue;
//					}finally {
//						if(date!=null) {
//							s.setLicenseExpiryDate(sdf.format(date));
//						}
//					}
//
					//库存数量
//					if(s.getQty()!=null&&s.getQty1()!=null) {
//						s.setQtyeach(s.getQty() * s.getQty1());
//					}
					//质量状态
					if (s.getLotatt10() != null) {
						List<EasyuiCombobox> comboboxList = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
						for (EasyuiCombobox easyuiCombobox : comboboxList) {
                           if(s.getLotatt10().equals(easyuiCombobox.getId())){
                           	  s.setLotatt10(easyuiCombobox.getValue());
                           	  break;
						   }
						}
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(searchEnterInvLocationList, fieldMap, sheetName,-1,response,fileName);
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}
	//统计分析-入库信息
	public void exportSearchEnterInvLocationDataToExcelSum(HttpServletResponse response,SearchEnterInvLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getSearchEnterInvLocationLeadToFiledPublicQuestionBankSum();
			// excel的sheetName
			String sheetName = "入库信息";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="入库信息"+timeNow;
			// excel要导出的数据
			List<SearchEnterInvLocation> searchEnterInvLocationList = searchBasCustomerMybatisDao.querySearchEnterInvLocationSum(mybatisCriteria);
			// 导出
			if (searchEnterInvLocationList != null && searchEnterInvLocationList.size() != 0) {
				for (SearchEnterInvLocation s: searchEnterInvLocationList) {
//					//时间格式转换
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					Date date=null;
//					try {
//						if(s.getLicenseExpiryDate()!=null) {
//							date = sdf.parse(s.getLicenseExpiryDate());
//						}
//					} catch (ParseException e) {
//						continue;
//					}finally {
//						if(date!=null) {
//							s.setLicenseExpiryDate(sdf.format(date));
//						}
//					}
//
					//库存数量
//					if(s.getQty()!=null&&s.getQty1()!=null) {
//						s.setQtyeach(s.getQty() * s.getQty1());
//					}
					//质量状态
					if (s.getLotatt10() != null) {
						List<EasyuiCombobox> comboboxList = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
						for (EasyuiCombobox easyuiCombobox : comboboxList) {
							if(s.getLotatt10().equals(easyuiCombobox.getId())){
								s.setLotatt10(easyuiCombobox.getValue());
								break;
							}
						}
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(searchEnterInvLocationList, fieldMap, sheetName,-1,response,fileName);
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
	public LinkedHashMap<String, String> getSearchEnterInvLocationLeadToFiledPublicQuestionBank() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("lotatt14", "单据号");
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("type", "入库类型");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("descrc", "规格/型号");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
		superClassMap.put("lotatt04", "生产批号/序列号");
		superClassMap.put("lotatt01Andlotatt02", "生产日期和有效期(或者失效期)");
//		superClassMap.put("qty", "库存件数");
		superClassMap.put("qtyeach", "数量");
		superClassMap.put("uom", "单位");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("locationid", "库存地点(货架号)");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("notes", "备注");
//		superClassMap.put("qty1", "换算率");
		return superClassMap;
	}
    //统计分析-入库信息
	public LinkedHashMap<String, String> getSearchEnterInvLocationLeadToFiledPublicQuestionBankSum() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("lotatt14", "入库单号");
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("type", "入库类型");
		superClassMap.put("sku", "产品代码");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("descrc", "规格/型号");
		superClassMap.put("productLineName", "产品线");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("reservedfield06", "生产企业许可证号/备案凭证号");
		superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
		superClassMap.put("lotatt04", "生产批号");
		superClassMap.put("lotatt05", "序列号");
		superClassMap.put("lotatt01", "生产日期");
		superClassMap.put("lotatt02", "有效期/失效期");
 		superClassMap.put("qty", "件数");
		superClassMap.put("qtyeach", "数量");
		superClassMap.put("uom", "单位");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("locationid", "库存地点(货架号)");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("notes", "备注");
//		superClassMap.put("qty1", "换算率");
		return superClassMap;
	}





    /**************************************出库信息****************************************/
    public EasyuiDatagrid<SearchOutInvLocation> showSearchOutInvLocationDatagrid(EasyuiDatagridPager pager, SearchOutInvLocation query) {
        EasyuiDatagrid<SearchOutInvLocation> datagrid = new EasyuiDatagrid<SearchOutInvLocation>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<SearchOutInvLocation> searchOutInvLocationList = searchBasCustomerMybatisDao.querySearchOutInvLocation(mybatisCriteria);
        for (SearchOutInvLocation searchOutInvLocation : searchOutInvLocationList) {
        	//计算数量
			if(searchOutInvLocation.getQty()!=null&&searchOutInvLocation.getQty1()!=null) {
				searchOutInvLocation.setQtyeach(searchOutInvLocation.getQty() * searchOutInvLocation.getQty1());
			}
        }
        datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchOutInvLocationCount(mybatisCriteria));
        datagrid.setRows(searchOutInvLocationList);
        return datagrid;
    }
//统计分析-出库信息
    public EasyuiDatagrid<SearchOutInvLocation> showSearchOutInvLocationDatagridSum(EasyuiDatagridPager pager, SearchOutInvLocation query) {
        EasyuiDatagrid<SearchOutInvLocation> datagrid = new EasyuiDatagrid<SearchOutInvLocation>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<SearchOutInvLocation> searchOutInvLocationList = searchBasCustomerMybatisDao.querySearchOutInvLocationSum(mybatisCriteria);
        for (SearchOutInvLocation searchOutInvLocation : searchOutInvLocationList) {
            //计算数量
            if(searchOutInvLocation.getQty()!=null&&searchOutInvLocation.getQty1()!=null) {
                searchOutInvLocation.setQtyeach(searchOutInvLocation.getQty() * searchOutInvLocation.getQty1());
            }
        }
        datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchOutInvLocationCount(mybatisCriteria));
        datagrid.setRows(searchOutInvLocationList);
        return datagrid;
    }


    public void exportSearchOutInvLocationDataToExcel(HttpServletResponse response, SearchOutInvLocation form) throws IOException {
        Cookie cookie = new Cookie("exportToken",form.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());
        try {
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getSearchOutInvLocationLeadToFiledPublicQuestionBank();
            // excel的sheetName
            String sheetName = "出库信息";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="出库信息"+timeNow;
            // excel要导出的数据
            List<SearchOutInvLocation> searchOutInvLocationList = searchBasCustomerMybatisDao.querySearchOutInvLocation(mybatisCriteria);
            // 导出
            if (searchOutInvLocationList == null || searchOutInvLocationList.size() == 0) {
                System.out.println("题库为空");
            }else {
                for (SearchOutInvLocation s: searchOutInvLocationList) {
//					//时间格式转换
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					Date date=null;
//					try {
//						if(s.getLicenseExpiryDate()!=null) {
//							date = sdf.parse(s.getLicenseExpiryDate());
//						}
//					} catch (ParseException e) {
//						continue;
//					}finally {
//						if(date!=null) {
//							s.setLicenseExpiryDate(sdf.format(date));
//						}
//					}
//
                    //库存数量
                    if(s.getQty()!=null&&s.getQty1()!=null) {
                        s.setQtyeach(s.getQty() * s.getQty1());
                    }
                }
                //将list集合转化为excle
                ExcelUtil.listToExcel(searchOutInvLocationList, fieldMap, sheetName,-1,response,fileName);
                System.out.println("导出成功~~~~");
            }
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }
//统计分析-出库信息
    public void exportSearchOutInvLocationDataToExcelSum(HttpServletResponse response, SearchOutInvLocation form) throws IOException {
        Cookie cookie = new Cookie("exportToken",form.getToken());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        response.setContentType(ContentTypeEnum.csv.getContentType());
        try {
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
            // excel表格的表头，map
            LinkedHashMap<String, String> fieldMap = getSearchOutInvLocationLeadToFiledPublicQuestionBankSum();
            // excel的sheetName
            String sheetName = "出库信息";
            //导出表格名称
            String timeNow=sdf.format(new Date());
            String fileName="出库信息"+timeNow;
            // excel要导出的数据
            List<SearchOutInvLocation> searchOutInvLocationList = searchBasCustomerMybatisDao.querySearchOutInvLocationSum(mybatisCriteria);
            // 导出
            if (searchOutInvLocationList == null || searchOutInvLocationList.size() == 0) {
                System.out.println("题库为空");
            }else {
                for (SearchOutInvLocation s: searchOutInvLocationList) {
//					//时间格式转换
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					Date date=null;
//					try {
//						if(s.getLicenseExpiryDate()!=null) {
//							date = sdf.parse(s.getLicenseExpiryDate());
//						}
//					} catch (ParseException e) {
//						continue;
//					}finally {
//						if(date!=null) {
//							s.setLicenseExpiryDate(sdf.format(date));
//						}
//					}
//
                    //库存数量
                    if(s.getQty()!=null&&s.getQty1()!=null) {
                        s.setQtyeach(s.getQty() * s.getQty1());
                    }
                    //质量状态
                    if (s.getLotatt10() != null) {
                        List<EasyuiCombobox> comboboxList = basCodesService.getBy(Constant.CODE_CATALOG_QCSTATE);
                        for (EasyuiCombobox easyuiCombobox : comboboxList) {
                            if(s.getLotatt10().equals(easyuiCombobox.getId())){
                                s.setLotatt10(easyuiCombobox.getValue());
                                break;
                            }
                        }
                    }
                }
                //将list集合转化为excle
                ExcelUtil.listToExcel(searchOutInvLocationList, fieldMap, sheetName,-1,response,fileName);
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
    public LinkedHashMap<String, String> getSearchOutInvLocationLeadToFiledPublicQuestionBank() {
        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
        superClassMap.put("enterpriseName", "委托方企业名称");
        superClassMap.put("lotatt03", "出库日期");
        superClassMap.put("type", "出库类型");
        superClassMap.put("lotatt12", "产品名称");
        superClassMap.put("descrc", "规格/型号");
        superClassMap.put("lotatt15", "生产企业");
        superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
        superClassMap.put("lotatt04", "生产批号/序列号");
		superClassMap.put("lotatt01Andlotatt02", "生产日期和有效期(或者失效期)");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("qtyeach", "数量");

		superClassMap.put("uom", "单位");
//		superClassMap.put("qty", "库存件数");
		superClassMap.put("consigneeID", "收货客户名称");
		superClassMap.put("caddress1", "收货地址");
		superClassMap.put("contact", "联系人");
        superClassMap.put("ctel1", "联系电话");
        superClassMap.put("notes", "备注");
//        superClassMap.put("qty1", "换算率");
        return superClassMap;
    }
    //统计分析-出库信息
    public LinkedHashMap<String, String> getSearchOutInvLocationLeadToFiledPublicQuestionBankSum() {
        LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
        superClassMap.put("orderno", "出库单号");
        superClassMap.put("enterpriseName", "委托方企业名称");
        superClassMap.put("lotatt03", "出库日期");
        superClassMap.put("type", "出库类型");
        superClassMap.put("sku", "产品代码");
        superClassMap.put("lotatt12", "产品名称");
        superClassMap.put("descrc", "规格/型号");
		superClassMap.put("productLineName", "产品线");
		superClassMap.put("lotatt15", "生产企业");
        superClassMap.put("reservedfield06", "生产企业许可证号/备案凭证号");
        superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
        superClassMap.put("lotatt04", "生产批号");
        superClassMap.put("lotatt05", "序列号");
        superClassMap.put("lotatt01", "生产日期");
        superClassMap.put("lotatt02", "有效期/失效期");
        superClassMap.put("lotatt11", "储存条件");
        superClassMap.put("qty", "件数");
        superClassMap.put("qtyeach", "数量");

        superClassMap.put("uom", "单位");
        superClassMap.put("locationid", "库存地点(货架号)");
        superClassMap.put("lotatt10", "质量状态");
        superClassMap.put("consigneeID", "收货客户名称");
        superClassMap.put("caddress1", "收货地址");
        superClassMap.put("contact", "联系人");
        superClassMap.put("ctel1", "联系电话");
        superClassMap.put("notes", "备注");
//        superClassMap.put("qty1", "换算率");
        return superClassMap;
    }





	/**
	 * 药监核查-历史数据
	 */

	/**************************************历史委托客户****************************************/
	public EasyuiDatagrid<SearchBasCustomer> getPagedDatagridBasCustomerHistory(EasyuiDatagridPager pager, SearchBasCustomer query) {
		EasyuiDatagrid<SearchBasCustomer> datagrid = new EasyuiDatagrid<SearchBasCustomer>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchBasCustomer> searchBasCustomerList = searchBasCustomerMybatisDao.querySearchBasCustomerHistory(mybatisCriteria);
		for (SearchBasCustomer s: searchBasCustomerList) {
			//时间格式转换
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				  s.setLicenseExpiryDate(sdf.format(sdf.parse(s.getLicenseExpiryDate())));
				  s.setClientStartDate(sdf.format(sdf.parse(s.getClientStartDate())));
				  s.setClientEndDate(sdf.format(sdf.parse(s.getClientEndDate())));

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchBasCustomerHistoryCount(mybatisCriteria));
		datagrid.setRows(searchBasCustomerList);
		return datagrid;
	}
	public void exportBasCustomerHistoryDataToExcel(HttpServletResponse response, SearchBasCustomer form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getBasCustomerHistoryLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "历史委托客户";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="历史委托客户"+timeNow;
			// excel要导出的数据
			List<SearchBasCustomer> searchBasCustomerList = searchBasCustomerMybatisDao.querySearchBasCustomerHistory(mybatisCriteria);
			// 导出
			if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (SearchBasCustomer s: searchBasCustomerList) {
					//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						s.setLicenseExpiryDate(sdf.format(sdf.parse(s.getLicenseExpiryDate())));
						s.setClientStartDate(sdf.format(sdf.parse(s.getClientStartDate())));
						s.setClientEndDate(sdf.format(sdf.parse(s.getClientEndDate())));

					} catch (ParseException e) {
						e.printStackTrace();
					}
					//委托期限
					if(s.getClientTerm()!=null){
						s.setClientTerm(s.getClientTerm()+"月");
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(searchBasCustomerList, fieldMap, sheetName,-1,response,fileName);
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
	public LinkedHashMap<String, String> getBasCustomerHistoryLeadToFiledPublicQuestionBank() {
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
		superClassMap.put("clientStartDate", "开始委托时间");
		superClassMap.put("clientEndDate", "停止委托时间");
		superClassMap.put("clientTerm", "委托期限");
		superClassMap.put("isChineseLabel", "是否委托加贴中文标签");
		superClassMap.put("clientContent", "委托业务范围");
		return superClassMap;
	}


	/******************************   历史货主商品   *********************************/

	public EasyuiDatagrid<CustomerProduct> getCustomerProductHistoryPagedDatagrid(EasyuiDatagridPager pager, CustomerProduct query) {
		EasyuiDatagrid<CustomerProduct> datagrid = new EasyuiDatagrid<CustomerProduct>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		System.out.println();
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<CustomerProduct> searchCustomerProductList = searchBasCustomerMybatisDao.queryCustomerHistoryProduct(mybatisCriteria);
		datagrid.setTotal((long) searchBasCustomerMybatisDao.queryCustomerProductHistoryByCount(mybatisCriteria));
		datagrid.setRows(searchCustomerProductList);
		return datagrid;
	}


	public void exportCustomerProductHistoryDataToExcel(HttpServletResponse response, CustomerProduct form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getCustomerProductHistoryLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "历史货主商品";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="历史货主商品"+timeNow;
			// excel要导出的数据
			List<CustomerProduct> searchBasCustomerList = searchBasCustomerMybatisDao.queryCustomerHistoryProduct(mybatisCriteria);
			// 导出
			if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (CustomerProduct s: searchBasCustomerList) {
//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						s.setApproveDate(sdf.format(sdf.parse(s.getApproveDate())));
						s.setProductRegisterExpiryDate(sdf.format(sdf.parse(s.getProductRegisterExpiryDate())));

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}


				//将list集合转化为excle
				ExcelUtil.listToExcel(searchBasCustomerList, fieldMap, sheetName,-1,response,fileName);
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
	public LinkedHashMap<String, String> getCustomerProductHistoryLeadToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("productName", "委托方货品名称");
		superClassMap.put("specsName", "规格/型号");
//        superClassMap.put("productModel", "型号");
		superClassMap.put("productRegisterNo", "产品注册证号/备案凭证号");
		superClassMap.put("approveDate", "批准日期");
		superClassMap.put("productRegisterExpiryDate", "有效期");
		superClassMap.put("enterpriseSc", "生产企业");
		superClassMap.put("licenseOrRecordNo", "生产许可证号/备案号");
		superClassMap.put("unit", "单位");
		superClassMap.put("storageCondition", "储存条件");

		return superClassMap;
	}



	/**************************************历史入库信息****************************************/
	public EasyuiDatagrid<SearchEnterInvLocation> showSearchEnterInvLocationHistoryDatagrid(EasyuiDatagridPager pager, SearchEnterInvLocation query) {
		EasyuiDatagrid<SearchEnterInvLocation> datagrid = new EasyuiDatagrid<SearchEnterInvLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchEnterInvLocation> searchEnterInvLocationList = searchBasCustomerMybatisDao.querySearchEnterInvLocationHistory(mybatisCriteria);
		datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchEnterInvLocationHistoryCount(mybatisCriteria));
		datagrid.setRows(searchEnterInvLocationList);
		return datagrid;
	}

	public void exportSearchEnterInvLocationHistoryDataToExcel(HttpServletResponse response,SearchEnterInvLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getSearchEnterInvLocationHistoryLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "历史入库信息";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="历史入库信息"+timeNow;
			// excel要导出的数据
			List<SearchEnterInvLocation> searchEnterInvLocationList = searchBasCustomerMybatisDao.querySearchEnterInvLocationHistory(mybatisCriteria);
			// 导出
			if (searchEnterInvLocationList == null || searchEnterInvLocationList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (SearchEnterInvLocation s: searchEnterInvLocationList) {
//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						s.setLotatt03(sdf.format(sdf.parse(s.getLotatt03())));

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(searchEnterInvLocationList, fieldMap, sheetName,-1,response,fileName);
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
	public LinkedHashMap<String, String> getSearchEnterInvLocationHistoryLeadToFiledPublicQuestionBank() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("lotatt03", "入库日期");
		superClassMap.put("type", "入库类型");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("descrc", "规格/型号");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
		superClassMap.put("lotatt04", "生产批号/序列号");
		superClassMap.put("lotatt01Andlotatt02", "生产日期和有效期(或者失效期)");
//		superClassMap.put("qty", "库存件数");
		superClassMap.put("qtyeach", "数量");
		superClassMap.put("uom", "单位");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("locationid", "库存地点(货架号)");
		superClassMap.put("lotatt10", "质量状态");
		superClassMap.put("notes", "备注");
//		superClassMap.put("qty1", "换算率");
		return superClassMap;
	}



	/**************************************历史出库信息****************************************/
	public EasyuiDatagrid<SearchOutInvLocation> showSearchOutInvLocationHistoryDatagrid(EasyuiDatagridPager pager, SearchOutInvLocation query) {
		EasyuiDatagrid<SearchOutInvLocation> datagrid = new EasyuiDatagrid<SearchOutInvLocation>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SearchOutInvLocation> searchOutInvLocationList = searchBasCustomerMybatisDao.querySearchOutInvLocationHistory(mybatisCriteria);
		datagrid.setTotal((long) searchBasCustomerMybatisDao.querySearchOutInvLocationHistoryCount(mybatisCriteria));
		datagrid.setRows(searchOutInvLocationList);
		return datagrid;
	}

	public void exportSearchOutInvLocationHistoryDataToExcel(HttpServletResponse response, SearchOutInvLocation form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getSearchOutInvLocationHistoryLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "历史出库信息";
			//导出表格名称
			String timeNow=sdf.format(new Date());
			String fileName="历史出库信息"+timeNow;
			// excel要导出的数据
			List<SearchOutInvLocation> searchOutInvLocationList = searchBasCustomerMybatisDao.querySearchOutInvLocationHistory(mybatisCriteria);
			// 导出
			if (searchOutInvLocationList == null || searchOutInvLocationList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (SearchOutInvLocation s: searchOutInvLocationList) {
					//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						s.setLotatt03(sdf.format(sdf.parse(s.getLotatt03())));

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				//将list集合转化为excle
				ExcelUtil.listToExcel(searchOutInvLocationList, fieldMap, sheetName,-1,response,fileName);
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
	public LinkedHashMap<String, String> getSearchOutInvLocationHistoryLeadToFiledPublicQuestionBank() {
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("enterpriseName", "委托方企业名称");
		superClassMap.put("lotatt03", "出库日期");
		superClassMap.put("type", "出库类型");
		superClassMap.put("lotatt12", "产品名称");
		superClassMap.put("descrc", "规格/型号");
		superClassMap.put("lotatt15", "生产企业");
		superClassMap.put("lotatt06", "产品注册证号/备案凭证号");
		superClassMap.put("lotatt04", "生产批号/序列号");
		superClassMap.put("lotatt01Andlotatt02", "生产日期和有效期(或者失效期)");
		superClassMap.put("lotatt11", "储存条件");
		superClassMap.put("qtyeach", "数量");

		superClassMap.put("uom", "单位");
//		superClassMap.put("qty", "库存件数");
		superClassMap.put("consigneeID", "收货客户名称");
		superClassMap.put("caddress1", "收货地址");
		superClassMap.put("contact", "联系人");
		superClassMap.put("ctel1", "联系电话");
		superClassMap.put("notes", "备注");
//        superClassMap.put("qty1", "换算率");
		return superClassMap;
	}
}