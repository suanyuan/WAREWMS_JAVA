package com.wms.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocAsnDoublecMybatisDao;
import com.wms.mybatis.dao.GspProductRegisterSpecsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.service.importdata.ImportDocAsnDoublecDataService;
import com.wms.utils.ResourceUtil;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.DocAsnDoublecDao;
import com.wms.entity.DocAsnDoublec;
import com.wms.vo.DocAsnDoublecVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocAsnDoublecForm;
import com.wms.query.DocAsnDoublecQuery;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("docAsnDoublecService")
public class DocAsnDoublecService extends BaseService {

//	@Autowired
//	private DocAsnDoublecDao docAsnDoublecDao;
	@Autowired
	private ImportDocAsnDoublecDataService importDocAsnDoublecDataService;
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
	@Autowired
	private DocAsnDoublecMybatisDao docAsnDoublecMybatisDao;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


	public EasyuiDatagrid<DocAsnDoublecVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnDoublecQuery query) {
//		EasyuiDatagrid<DocAsnDoublecVO> datagrid = new EasyuiDatagrid<DocAsnDoublecVO>();
//		List<DocAsnDoublec> docAsnDoublecList = docAsnDoublecDao.getPagedDatagrid(pager, query);
//		DocAsnDoublecVO docAsnDoublecVO = null;
//
//		List<DocAsnDoublecVO> docAsnDoublecVOList = new ArrayList<DocAsnDoublecVO>();
//		for (DocAsnDoublec docAsnDoublec : docAsnDoublecList) {
//			docAsnDoublecVO = new DocAsnDoublecVO();
//			BeanUtils.copyProperties(docAsnDoublec, docAsnDoublecVO);
//			docAsnDoublecVOList.add(docAsnDoublecVO);
//		}
//		datagrid.setTotal(docAsnDoublecDao.countAll(query));
//		datagrid.setRows(docAsnDoublecVOList);
//		return datagrid;
		EasyuiDatagrid<DocAsnDoublecVO> datagrid = new EasyuiDatagrid<DocAsnDoublecVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		DocAsnDoublecVO gspProductRegisterSpecsVO = null;
		List<DocAsnDoublecVO> basGtnVOList = new ArrayList<DocAsnDoublecVO>();
		List<DocAsnDoublec> gspProductRegisterSpecsList = docAsnDoublecMybatisDao.queryByList(criteria);


		//System.out.println(query.getSpecsName()+"==============query================================"+query.getProductRegisterNo());
		for (DocAsnDoublec gspProductRegisterSpecs : gspProductRegisterSpecsList) {
			//System.out.println(gspProductRgisterSpecs.getCreateDate()+"==============================================");
			gspProductRegisterSpecsVO = new DocAsnDoublecVO();
			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
			if (gspProductRegisterSpecs.getAddtime() != null) {
				gspProductRegisterSpecsVO.setAddtime(simpleDateFormat.format(gspProductRegisterSpecs.getAddtime()));
			}
			if (gspProductRegisterSpecs.getEdittime() != null) {
				gspProductRegisterSpecsVO.setEdittime(simpleDateFormat.format(gspProductRegisterSpecs.getEdittime()));
			}

			basGtnVOList.add(gspProductRegisterSpecsVO);
		}
		int total = docAsnDoublecMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}

	public Json addDocAsnDoublec(DocAsnDoublecForm docAsnDoublecForm) throws Exception {
		Json json = new Json();
		DocAsnDoublec docAsnDoublec = new DocAsnDoublec();
		BeanUtils.copyProperties(docAsnDoublecForm, docAsnDoublec);
		docAsnDoublecMybatisDao.add(docAsnDoublec);
		json.setSuccess(true);
		return json;
	}

	public Json editDocAsnDoublec(DocAsnDoublecForm docAsnDoublecForm) {
		Json json = new Json();
		DocAsnDoublec docAsnDoublec = docAsnDoublecMybatisDao.queryById(docAsnDoublecForm.getDoublecno());
		BeanUtils.copyProperties(docAsnDoublecForm, docAsnDoublec);
		docAsnDoublecMybatisDao.updateBySelective(docAsnDoublec);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocAsnDoublec(String id) {
		Json json = new Json();
		System.out.println("id================================="+id);
		String[] str = id.split(",");
		for(int a=0;a<str.length;a++){
System.out.println(str[a]);
			docAsnDoublecMybatisDao.delete(str[a]);
		}
		//DocAsnDoublec docAsnDoublec = docAsnDoublecDao.findById();
//		if(docAsnDoublec != null){
//			docAsnDoublecMybatisDao.delete(docAsnDoublec);
//		}
		json.setSuccess(true);
		return json;
	}
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("docAsnDoublec_template.xls"));
			response.reset();
			Cookie cookie = new Cookie("downloadToken",token);
			cookie.setMaxAge(60);
			response.addCookie(cookie);
			response.setContentType(ContentTypeEnum.stream.getContentType());
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
			response.addHeader("Content-Length", "" + file.length());

			try(InputStream fis = new BufferedInputStream(new FileInputStream(file))){
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				toClient.write(buffer);
				System.out.println();
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
			json = importDocAsnDoublecDataService.importExcelData(excelFile);
		}
		return json;
	}
//	public List<EasyuiCombobox> getDocAsnDoublecCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocAsnDoublec> docAsnDoublecList = docAsnDoublecDao.findAll();
//		if(docAsnDoublecList != null && docAsnDoublecList.size() > 0){
//			for(DocAsnDoublec docAsnDoublec : docAsnDoublecList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docAsnDoublec.getCustomerid()));
//				combobox.setValue(docAsnDoublec.getMatchFlag());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

}