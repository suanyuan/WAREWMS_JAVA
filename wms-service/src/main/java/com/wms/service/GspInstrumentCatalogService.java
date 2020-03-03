package com.wms.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.GspInstrumentCatalogMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.service.importdata.ImportGspInstrumentCatalogDataService;
import com.wms.utils.*;
import com.wms.utils.exception.ExcelException;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspInstrumentCatalog;
import com.wms.vo.GspInstrumentCatalogVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspInstrumentCatalogForm;
import com.wms.query.GspInstrumentCatalogQuery;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("gspInstrumentCatalogService")
public class GspInstrumentCatalogService extends BaseService {

	@Autowired
	private GspInstrumentCatalogMybatisDao gspInstrumentCatalogMybatisDao;
	@Autowired
	private ImportGspInstrumentCatalogDataService importGspInstrumentCatalogDataService;

	public EasyuiDatagrid<GspInstrumentCatalogVO> getPagedDatagrid(EasyuiDatagridPager pager, GspInstrumentCatalogQuery query) {
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("c.instrument_catalog_no,bc.CodeName_C");
		EasyuiDatagrid<GspInstrumentCatalogVO> datagrid = new EasyuiDatagrid<GspInstrumentCatalogVO>();
		List<GspInstrumentCatalog> gspInstrumentCatalogList = gspInstrumentCatalogMybatisDao.queryByList(mybatisCriteria);
		GspInstrumentCatalogVO gspInstrumentCatalogVO = null;
		List<GspInstrumentCatalogVO> gspInstrumentCatalogVOList = new ArrayList<GspInstrumentCatalogVO>();
		for (GspInstrumentCatalog gspInstrumentCatalog : gspInstrumentCatalogList) {
			gspInstrumentCatalogVO = new GspInstrumentCatalogVO();
			BeanUtils.copyProperties(gspInstrumentCatalog, gspInstrumentCatalogVO);
			gspInstrumentCatalogVOList.add(gspInstrumentCatalogVO);
		}
		int count = gspInstrumentCatalogMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspInstrumentCatalogVOList);
		return datagrid;
	}

	public Json addGspInstrumentCatalog(GspInstrumentCatalogForm gspInstrumentCatalogForm) throws Exception {
		Json json = new Json();
		GspInstrumentCatalog gspInstrumentCatalog = new GspInstrumentCatalog();
		BeanUtils.copyProperties(gspInstrumentCatalogForm, gspInstrumentCatalog);
		gspInstrumentCatalog.setInstrumentCatalogId(RandomUtil.getUUID());
		gspInstrumentCatalog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		gspInstrumentCatalog.setIsUse(Constant.IS_USE_YES);
		gspInstrumentCatalog.setCretaeDate(new Date());
		gspInstrumentCatalogMybatisDao.add(gspInstrumentCatalog);
		json.setSuccess(true);
		return json;
	}

	public Json editGspInstrumentCatalog(GspInstrumentCatalogForm gspInstrumentCatalogForm) {
		Json json = new Json();
		GspInstrumentCatalog gspInstrumentCatalog = gspInstrumentCatalogMybatisDao.queryById(gspInstrumentCatalogForm.getInstrumentCatalogId());
		BeanUtils.copyProperties(gspInstrumentCatalogForm, gspInstrumentCatalog);
		gspInstrumentCatalog.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		gspInstrumentCatalog.setEditDate(new Date());
		gspInstrumentCatalogMybatisDao.updateBySelective(gspInstrumentCatalog);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspInstrumentCatalog(String id) {
		Json json = new Json();
		GspInstrumentCatalog gspInstrumentCatalog = gspInstrumentCatalogMybatisDao.queryById(id);
		if(gspInstrumentCatalog != null){
			gspInstrumentCatalogMybatisDao.delete(gspInstrumentCatalog);
		}
		json.setSuccess(true);
		return json;
	}

	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("gspInstrumentCatalog_template.xls"));
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
			json = importGspInstrumentCatalogDataService.importExcelData(excelFile);
		}
		return json;
	}

	public void exportDataToExcel(HttpServletResponse response, GspInstrumentCatalogQuery form) throws IOException {
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
			String sheetName = "器械目录";
			// excel要导出的数据
			List<GspInstrumentCatalog> gspProductRegisterSpecsList = gspInstrumentCatalogMybatisDao.queryByList(mybatisCriteria);
			// 导出


			if (gspProductRegisterSpecsList == null || gspProductRegisterSpecsList.size() == 0) {
				System.out.println("器械目录为空");
			}else {
				for (GspInstrumentCatalog s: gspProductRegisterSpecsList) {
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
					if(s.getCretaeDate()!=null) {
						s.setCretaeDateDc(sdf.format(s.getCretaeDate()));
					}

					if(s.getEditDate()!=null) {
						s.setEditDateDc(sdf.format(s.getEditDate()));
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
		superClassMap.put("instrumentCatalogNo", "编号");
		superClassMap.put("instrumentCatalogName", "名称");

		superClassMap.put("instrumentCatalogRemark", "描述");
		superClassMap.put("classifyId", "分类");
		superClassMap.put("version", "版本");
		superClassMap.put("createId", "创建人");
		superClassMap.put("cretaeDateDc", "创建时间");
		superClassMap.put("editId", "修改人");
		superClassMap.put("editDateDc", "修改时间");


		return superClassMap;
	}


	public List<EasyuiCombobox> getGspInstrumentCatalogCombobox(String version) {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		GspInstrumentCatalogQuery query = new GspInstrumentCatalogQuery();
		if(!StringUtils.isEmpty(version)){
			query.setVersion(version);
			mybatisCriteria.setDoPage(false);
		}
		query.setIsUse(Constant.IS_USE_YES);
		mybatisCriteria.setCondition(query);
		List<GspInstrumentCatalog> gspInstrumentCatalogList = gspInstrumentCatalogMybatisDao.queryByList(mybatisCriteria);
		if(gspInstrumentCatalogList != null && gspInstrumentCatalogList.size() > 0){
			for(GspInstrumentCatalog gspInstrumentCatalog : gspInstrumentCatalogList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspInstrumentCatalog.getInstrumentCatalogId()));
				combobox.setValue(gspInstrumentCatalog.getInstrumentCatalogName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public EasyuiDatagrid<GspInstrumentCatalogVO> getPagedDatagridEnterprise(EasyuiDatagridPager pager, GspInstrumentCatalogQuery query) {
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("c.instrument_catalog_no,bc.CodeName_C");
		EasyuiDatagrid<GspInstrumentCatalogVO> datagrid = new EasyuiDatagrid<GspInstrumentCatalogVO>();
		List<GspInstrumentCatalog> gspInstrumentCatalogList = gspInstrumentCatalogMybatisDao.queryByList(mybatisCriteria);
		GspInstrumentCatalogVO gspInstrumentCatalogVO = null;
		List<GspInstrumentCatalogVO> gspInstrumentCatalogVOList = new ArrayList<GspInstrumentCatalogVO>();
		for (GspInstrumentCatalog gspInstrumentCatalog : gspInstrumentCatalogList) {
			gspInstrumentCatalogVO = new GspInstrumentCatalogVO();
			BeanUtils.copyProperties(gspInstrumentCatalog, gspInstrumentCatalogVO);
			gspInstrumentCatalogVOList.add(gspInstrumentCatalogVO);
		}
		int count = gspInstrumentCatalogMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspInstrumentCatalogVOList);
		return datagrid;
	}

	public GspInstrumentCatalog getGspInstrumentCatalog(String id){
		return gspInstrumentCatalogMybatisDao.queryById(id);
	}
}