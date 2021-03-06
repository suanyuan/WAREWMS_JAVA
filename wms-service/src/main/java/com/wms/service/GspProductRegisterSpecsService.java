package com.wms.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspProductRegister;
import com.wms.entity.ProductRegisterRelation;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.service.importdata.ImportAsnDataService;
import com.wms.service.importdata.ImportGspProductRegisterSpecsDataService;
import com.wms.utils.*;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.GspEnterpriseInfoVO;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspProductRegisterSpecsDao;
import com.wms.entity.GspProductRegisterSpecs;
import com.wms.vo.GspProductRegisterSpecsVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterSpecsForm;
import com.wms.query.GspProductRegisterSpecsQuery;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("gspProductRegisterSpecsService")
public class GspProductRegisterSpecsService extends BaseService {

	//@Autowired
	//private GspProductRegisterSpecsDao gspProductRegisterSpecsDao;
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
	@Autowired
	private ImportAsnDataService importAsnDataService;
	@Autowired
	private ImportGspProductRegisterSpecsDataService importGspProductRegisterSpecsDataService;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private ProductRegisterRelationMybatisDao productRegisterRelationMybatisDao;
	@Autowired
	private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;



	@Autowired
	private BasCodesService basCodesService;

	@Autowired
	private DataPublishService dataPublishService;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public EasyuiDatagrid<GspProductRegisterSpecsVO> getPagedDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {

		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<GspProductRegisterSpecsVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		criteria.setOrderByClause("create_date desc");

		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
		List<GspProductRegisterSpecsVO> basGtnVOList = new ArrayList<GspProductRegisterSpecsVO>();
		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsMybatisDao.queryByList(criteria);
		List<EasyuiCombobox> EasyuiCombobox =  basCodesService.getBy(Constant.CODE_CATALOG_UOM);

		//System.out.println(query.getSpecsName()+"==============query================================"+query.getProductRegisterNo());
		for (GspProductRegisterSpecs gspProductRegisterSpecs : gspProductRegisterSpecsList) {
			//System.out.println(gspProductRegisterSpecs.getCreateDate()+"==============================================");
			gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
			if(gspProductRegisterSpecs.getCreateDate()!=null){
				gspProductRegisterSpecsVO.setCreateDate(simpleDateFormat.format(gspProductRegisterSpecs.getCreateDate()));
			}
			if(gspProductRegisterSpecs.getEditDate()!=null){
				gspProductRegisterSpecsVO.setEditDate(simpleDateFormat.format(gspProductRegisterSpecs.getEditDate()));
			}
			;
			for(EasyuiCombobox unit:EasyuiCombobox){
				if(unit.getId().equals(gspProductRegisterSpecsVO.getUnit())){
					gspProductRegisterSpecsVO.setUnit(unit.getValue());

				}
			}
			//System.out.println(gspProductRegisterSpecs.getCreateDate()+"=============================================="+gspProductRegisterSpecsVO.getCreateDate());
			basGtnVOList.add(gspProductRegisterSpecsVO);
		}

		int total = gspProductRegisterSpecsMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}


	public EasyuiDatagrid<GspProductRegisterSpecsVO> getPagedProductSUPDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {
		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<GspProductRegisterSpecsVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		criteria.setOrderByClause("create_date desc");
		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
		List<GspProductRegisterSpecsVO> basGtnVOList = new ArrayList<GspProductRegisterSpecsVO>();
		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsMybatisDao.queryProductSUPByList(criteria);
		for (GspProductRegisterSpecs gspProductRegisterSpecs : gspProductRegisterSpecsList) {
			System.out.println(gspProductRegisterSpecs.getCreateDate()+"==============================================");
			gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
			if(gspProductRegisterSpecs.getCreateDate()!=null){
				gspProductRegisterSpecsVO.setCreateDate(simpleDateFormat.format(gspProductRegisterSpecs.getCreateDate()));
			}
			if(gspProductRegisterSpecs.getEditDate()!=null){
				gspProductRegisterSpecsVO.setEditDate(simpleDateFormat.format(gspProductRegisterSpecs.getEditDate()));
			}
			basGtnVOList.add(gspProductRegisterSpecsVO);
		}
		int total = gspProductRegisterSpecsMybatisDao.queryProductSUPByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}


	public Json addGspProductRegisterSpecs(GspProductRegisterSpecsForm gspProductRegisterSpecsForm) throws Exception {
		Json json = new Json();

		System.out.println("==================gspProductRegisterSpecsForm.getIsCertificate()="+gspProductRegisterSpecsForm.getIsDoublec());
		GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
		BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		gspProductRegisterSpecs.setSpecsId(RandomUtil.getUUID());
		ProductRegisterRelation relation = new ProductRegisterRelation();

		if(gspProductRegisterSpecsForm.getProductRegisterId()!=null&& gspProductRegisterSpecsForm.getProductRegisterId()!=""){
			//新增的有注册证
			//注册证和产品关系是否已存在
			GspProductRegisterSpecs g1 = gspProductRegisterSpecsMybatisDao.selectByProductCodeAndProductRegister(gspProductRegisterSpecs.getProductCode(),gspProductRegisterSpecs.getProductRegisterId());
			GspProductRegister gpr = gspProductRegisterMybatisDao.queryById(gspProductRegisterSpecs.getProductRegisterId());

			if(g1!=null){
				return  Json.error("该产品注册证关系已经存在！");
			}

			relation.setId(RandomUtil.getUUID());
			relation.setSpecsId(gspProductRegisterSpecs.getSpecsId());
			relation.setProductCode(gspProductRegisterSpecs.getProductCode());
			relation.setProductRegisterId(gspProductRegisterSpecs.getProductRegisterId());
			relation.setProductRegisterNo(gpr.getProductRegisterNo());
			relation.setActiveFlag("0");
			relation.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			relation.setEditId(SfcUserLoginUtil.getLoginUser().getId());

		}else{
			//没有传入注册证
			if("0".equals(gspProductRegisterSpecs.getMedicalDeviceMark())){
				GspProductRegisterSpecs g2 = gspProductRegisterSpecsMybatisDao.selectByProductCode(gspProductRegisterSpecs.getProductCode());
				if(g2!=null){
					if(g2 !=null  ){
						return Json.error("产品已存在！");
					}
				}
			}
		}


		gspProductRegisterSpecs.setProductName(gspProductRegisterSpecs.getProductNameMain());
		gspProductRegisterSpecsMybatisDao.add(gspProductRegisterSpecs);


		if(gspProductRegisterSpecsForm.getProductRegisterId()!=null){
			productRegisterRelationMybatisDao.add(relation);
		}


		json.setSuccess(true);
		json.setMsg("资料添加成功");
		return json;
	}

	public Json editGspProductRegisterSpecs(GspProductRegisterSpecsForm gspProductRegisterSpecsForm,String productRegisterId) {
		Json json = new Json();

		GspProductRegisterSpecs oldSpecs = gspProductRegisterSpecsMybatisDao.queryById(gspProductRegisterSpecsForm.getSpecsId());
		if(oldSpecs == null){
			return Json.error("查询不到对应的产品基础信息");
		}



		//判断如果产品注册证号变更需要触发换证
//		if(StringUtils.isEmpty(productRegisterId)){
//			if(gspProductRegisterSpecsForm.getProductRegisterId()!=null && oldSpecs.getProductRegisterId()!=null){
//				if(!gspProductRegisterSpecsForm.getProductRegisterId().equals(oldSpecs.getProductRegisterId())){
//					dataPublishService.cancelDataBySpecsId(oldSpecs);
//				}
//			}
//		}else{
//			if(productRegisterId!=null && oldSpecs.getProductRegisterId()!=null){
//				if(!productRegisterId.equals(oldSpecs.getProductRegisterId())){
//					dataPublishService.cancelDataBySpecsId(oldSpecs);
//				}
//			}
//		}


		GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
		BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		//GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsDao.findById(gspProductRegisterSpecsForm.getSpecsId());
		//BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		gspProductRegisterSpecs.setProductName(gspProductRegisterSpecs.getProductNameMain());

		gspProductRegisterSpecsMybatisDao.updateBySelective(gspProductRegisterSpecs);
		json.setSuccess(true);
		return json;
	}


	public Json addrelation() throws Exception {
		Json json = new Json();
		List<GspProductRegisterSpecs> specsList =gspProductRegisterSpecsMybatisDao.selectProductRegisterRelation();
		for(GspProductRegisterSpecs specs:specsList){
			ProductRegisterRelation relation = new ProductRegisterRelation();
			relation.setId(RandomUtil.getUUID());
			relation.setActiveFlag("1");
			relation.setProductRegisterId(specs.getProductRegisterId());
			relation.setSpecsId(specs.getSpecsId());
			relation.setProductRegisterNo(specs.getProductRegisterNo());
			relation.setProductCode(specs.getProductCode());
			productRegisterRelationMybatisDao.add(relation);

		}

		return json;
	}
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("productRegisterSpecs_template.xls"));
			response.reset();
			Cookie cookie = new Cookie("downloadToken",token);
			cookie.setMaxAge(60);
			response.addCookie(cookie);
			response.setContentType(ContentTypeEnum.stream.getContentType());
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
			response.addHeader("Content-Length", "" + file.length());

			try(InputStream fis = new BufferedInputStream(new FileInputStream(file))){
				byte[] buffer = new byte[fis.available()];
				System.out.println();
				 fis.read(buffer);
				toClient.write(buffer);
				toClient.flush();
			}catch(IOException ex){
//				log.error(ExceptionUtil.getExceptionMessage(ex));
			}
		} catch (Exception e) {
//			log.error(ExceptionUtil.getExceptionMessage(e));
		}
	}






	public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		//System.out.println("======excelFile.getSize()=="+excelFile.getSize()+"======="+excelFile.getInputStream().getClass().getName());
		if(excelFile != null && excelFile.getSize() > 0){
			json = importGspProductRegisterSpecsDataService.importExcelData(excelFile);
		}
		return json;
	}

	public void exportDataToExcel(HttpServletResponse response, GspProductRegisterSpecsQuery form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "产品基础信息";
			// excel要导出的数据
			List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsMybatisDao.queryByList(mybatisCriteria);
			// 导出


			if (gspProductRegisterSpecsList == null || gspProductRegisterSpecsList.size() == 0) {
				System.out.println("产品基础信息为空");
			}else {
				for (GspProductRegisterSpecs s: gspProductRegisterSpecsList) {
//                    FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
//                    BeanUtils.copyProperties(s, firstReviewLogForm);


					//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=null;

					if("1".equals(s.getIsUse())){
						s.setIsUse("有效");
					}else if("0".equals(s.getIsUse())){
						s.setIsUse("失效");
					}
					if("1".equals(s.getIsCertificate())){
						s.setIsCertificate("是");
					}else if("0".equals(s.getIsCertificate())){
						s.setIsCertificate("否");
					}
					if("1".equals(s.getIsDoublec())){
						s.setIsDoublec("是");
					}else if("0".equals(s.getIsDoublec())){
						s.setIsDoublec("否");
					}
					List<EasyuiCombobox> unitList =  basCodesService.getBy(Constant.CODE_CATALOG_UOM);
					for(EasyuiCombobox box:unitList){
						if(box.getId().equals(s.getUnit())){
							s.setUnit(box.getValue());
						}
					}

					if(s.getCreateDate()!=null) {
						s.setCreateDateDC(sdf.format(s.getCreateDate()));
					}
					if(s.getEditDate()!=null) {
						s.setEditDateDC(sdf.format(s.getEditDate()));
					}

					if(s.getMaintenanceCycle()!=null){
						s.setMaintenanceCycle(s.getMaintenanceCycle()+"天");
					}
//
				}
//                List<FirstReviewLog> searchBasCustomerFormList  = new ArrayList<FirstReviewLog>();

				//将list集合转化为excle
				ExcelUtil.listToExcel(gspProductRegisterSpecsList, fieldMap, sheetName, response);
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
	public LinkedHashMap<String, String> getToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("isUse", "是否有效");
		superClassMap.put("productRegisterNo", "注册证编号");
		superClassMap.put("productCode", "产品代码");

		superClassMap.put("productName", "产品名称");
		superClassMap.put("productRemark", "产品描述");
		superClassMap.put("specsName", "规格");
		superClassMap.put("productModel", "型号");
		superClassMap.put("unit", "单位");
		superClassMap.put("packingUnit", "包装规格");

		superClassMap.put("storageCondition", "储存条件");
		superClassMap.put("enterpriseName", "生产企业");

		superClassMap.put("isDoublec", "双证");
		superClassMap.put("isCertificate", "产品合格证");

		superClassMap.put("maintenanceCycle", "养护周期");


		superClassMap.put("createId", "创建人");
		superClassMap.put("createDateDC", "创建时间");
		superClassMap.put("editId", "编辑人");
		superClassMap.put("editDateDC", "编辑时间");

		return superClassMap;
	}










	public Json deleteGspProductRegisterSpecs(String id) {
		Json json = new Json();
		GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.queryById(id);
		if(gspProductRegisterSpecs != null){
			gspProductRegisterSpecsMybatisDao.delete(id);
			productRegisterRelationMybatisDao.deleteByProductAndregister(id,gspProductRegisterSpecs.getProductRegisterId());

		}


		json.setSuccess(true);
		return json;
	}
	//主页grid点击编辑页面获取数据
	public Json getGspProductRegisterSpecsInfo(String id){
         //	根据specs_id查出单挑getGspProductRegisterSpecs
		GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.selectById(id);
//		if(gspProductRegisterSpecs.getEnterpriseId()!=null) {
//			GspEnterpriseInfo info = new GspEnterpriseInfo();
//			//通过查到的gspProductRegisterSpecs中的getEnterpriseId创建对象
//			info.setEnterpriseId(gspProductRegisterSpecs.getEnterpriseId());
//			//通过getEnterpriseId查出生产企业信息
//			GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(info);
//			if(gspEnterpriseInfo.getEnterpriseName() !=null){
//				gspProductRegisterSpecs.setEnterpriseName(gspEnterpriseInfo.getEnterpriseName());
//
//			}
			//产品许可证 备案号
//			if(gspEnterpriseInfo.getLicenseNo()!=null){
//				gspProductRegisterSpecs.setLicenseNo(gspEnterpriseInfo.getLicenseNo());
//			}else{
//				gspProductRegisterSpecs.setLicenseNo(gspEnterpriseInfo.getRecordNo());
//			}
//		}
		if(gspProductRegisterSpecs == null){
			return Json.error("企业信息不存在！");
		}
		gspProductRegisterSpecs.setProductNameMain(gspProductRegisterSpecs.getProductName());
		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
		BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);

		gspProductRegisterSpecsVO.setCreateDate(simpleDateFormat.format(gspProductRegisterSpecs.getCreateDate()));
		gspProductRegisterSpecsVO.setEditDate(simpleDateFormat.format(new Date()));

		return Json.success("",gspProductRegisterSpecsVO);
	}
	public Json getInfoByProductCode(String productCode){
		GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.selectByProductCode(productCode);
		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
		BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);

		gspProductRegisterSpecsVO.setCreateDate(simpleDateFormat.format(gspProductRegisterSpecs.getCreateDate()));
		gspProductRegisterSpecsVO.setEditDate(simpleDateFormat.format(new Date()));
		if(gspProductRegisterSpecsVO == null){
			return Json.error("企业信息不存在！");
		}
		return Json.success("",gspProductRegisterSpecsVO);
	}


	public List<EasyuiCombobox> getGspProductRegisterSpecsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsMybatisDao.queryListByAll();
		if(gspProductRegisterSpecsList != null && gspProductRegisterSpecsList.size() > 0){
			for(GspProductRegisterSpecs gspProductRegisterSpecs : gspProductRegisterSpecsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspProductRegisterSpecs.getSpecsId()));
				combobox.setValue(gspProductRegisterSpecs.getAlternatName1());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<GspProductRegisterSpecs> querySpecByRegisterId(String registerId){
		GspProductRegisterSpecsQuery query = new GspProductRegisterSpecsQuery();
		query.setProductRegisterId(registerId);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		return gspProductRegisterSpecsMybatisDao.queryByList(criteria);
	}

}