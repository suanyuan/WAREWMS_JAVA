package com.wms.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.GspProductRegisterSpecsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.service.importdata.ImportAsnDataService;
import com.wms.service.importdata.ImportGspProductRegisterSpecsDataService;
import com.wms.utils.RandomUtil;
import com.wms.utils.ResourceUtil;
import org.apache.avalon.framework.configuration.ConfigurationException;
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

	@Autowired
	private GspProductRegisterSpecsDao gspProductRegisterSpecsDao;
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
	@Autowired
	private ImportAsnDataService importAsnDataService;
	@Autowired
	private ImportGspProductRegisterSpecsDataService importGspProductRegisterSpecsDataService;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public EasyuiDatagrid<GspProductRegisterSpecsVO> getPagedDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {
//		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<GspProductRegisterSpecsVO>();
//		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsDao.getPagedDatagrid(pager, query);
//		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
//		List<GspProductRegisterSpecsVO> gspProductRegisterSpecsVOList = new ArrayList<GspProductRegisterSpecsVO>();
//		for (GspProductRegisterSpecs gspProductRegisterSpecs : gspProductRegisterSpecsList) {
//			gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
//			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
//			gspProductRegisterSpecsVOList.add(gspProductRegisterSpecsVO);
//		}
//		datagrid.setTotal(gspProductRegisterSpecsDao.countAll(query));
//		datagrid.setRows(gspProductRegisterSpecsVOList);
//		return datagrid;
		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<GspProductRegisterSpecsVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
		List<GspProductRegisterSpecsVO> basGtnVOList = new ArrayList<GspProductRegisterSpecsVO>();
		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsMybatisDao.queryByList(criteria);


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


			//System.out.println(gspProductRegisterSpecs.getCreateDate()+"=============================================="+gspProductRegisterSpecsVO.getCreateDate());

		basGtnVOList.add(gspProductRegisterSpecsVO);
	}

		int total = gspProductRegisterSpecsMybatisDao.queryByCount(criteria);
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
		System.out.println(gspProductRegisterSpecs.getIsCertificate()+"==================gspProductRegisterSpecs.getIsCertificate()="+gspProductRegisterSpecs.getIsDoublec());
//		gspProductRegisterSpecs.setEditDate(new Date());
		gspProductRegisterSpecsMybatisDao.add(gspProductRegisterSpecs);
		json.setSuccess(true);
		return json;
	}

	public Json editGspProductRegisterSpecs(GspProductRegisterSpecsForm gspProductRegisterSpecsForm) {
		Json json = new Json();
		GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
		BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		//GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsDao.findById(gspProductRegisterSpecsForm.getSpecsId());
		//BeanUtils.copyProperties(gspProductRegisterSpecsForm, gspProductRegisterSpecs);
		gspProductRegisterSpecsMybatisDao.updateBySelective(gspProductRegisterSpecs);
		json.setSuccess(true);
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
		System.out.println("======excelFile.getSize()=="+excelFile.getSize()+"======="+excelFile.getInputStream().getClass().getName());
		if(excelFile != null && excelFile.getSize() > 0){
			json = importGspProductRegisterSpecsDataService.importExcelData(excelFile);
		}
		return json;
	}

	public Json deleteGspProductRegisterSpecs(String id) {
		Json json = new Json();
		//GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.findById(id);
		if(id != null){
			gspProductRegisterSpecsMybatisDao.deleteByid	(id);
		}
		json.setSuccess(true);
		return json;
	}


	public Json getGspProductRegisterSpecsInfo(String id){
		GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.selectById(id);
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
		List<GspProductRegisterSpecs> gspProductRegisterSpecsList = gspProductRegisterSpecsDao.findAll();
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

}