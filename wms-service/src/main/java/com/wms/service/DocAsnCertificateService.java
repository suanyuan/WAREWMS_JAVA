package com.wms.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.DocAsnCertificateMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.service.importdata.ImportDocAsnCertificateDataService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.DocAsnCertificateDao;
import com.wms.entity.DocAsnCertificate;
import com.wms.vo.DocAsnCertificateVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocAsnCertificateForm;
import com.wms.query.DocAsnCertificateQuery;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("docAsnCertificateService")
public class DocAsnCertificateService extends BaseService {

	@Autowired
	private DocAsnCertificateMybatisDao docAsnCertificateMybatisDao;

	@Autowired
	private ImportDocAsnCertificateDataService importDocAsnCertificateDataService;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public EasyuiDatagrid<DocAsnCertificateVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnCertificateQuery query) {

		EasyuiDatagrid<DocAsnCertificateVO> datagrid = new EasyuiDatagrid<DocAsnCertificateVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		DocAsnCertificateVO gspProductRegisterSpecsVO = null;
		List<DocAsnCertificateVO> basGtnVOList = new ArrayList<DocAsnCertificateVO>();
		List<DocAsnCertificate> gspProductRegisterSpecsList = docAsnCertificateMybatisDao.queryByList(criteria);


		//System.out.println(query.getSpecsName()+"==============query================================"+query.getProductRegisterNo());
		for (DocAsnCertificate gspProductRegisterSpecs : gspProductRegisterSpecsList) {
			//System.out.println(gspProductRgisterSpecs.getCreateDate()+"==============================================");
			gspProductRegisterSpecsVO = new DocAsnCertificateVO();
			BeanUtils.copyProperties(gspProductRegisterSpecs, gspProductRegisterSpecsVO);
			if (gspProductRegisterSpecs.getAddtime() != null) {
				gspProductRegisterSpecsVO.setAddtime(simpleDateFormat.format(gspProductRegisterSpecs.getAddtime()));
			}
			if (gspProductRegisterSpecs.getEdittime() != null) {
				gspProductRegisterSpecsVO.setEdittime(simpleDateFormat.format(gspProductRegisterSpecs.getEdittime()));
			}

			basGtnVOList.add(gspProductRegisterSpecsVO);
		}
		int total = docAsnCertificateMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}

	public Json addDocAsnCertificate(DocAsnCertificateForm docAsnCertificateForm) throws Exception {
		Json json = new Json();
		DocAsnCertificate docAsnCertificate = new DocAsnCertificate();
		BeanUtils.copyProperties(docAsnCertificateForm, docAsnCertificate);

		docAsnCertificate.setAddtime(new Date());
		docAsnCertificate.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		docAsnCertificate.setEdittime(new Date());
		docAsnCertificate.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docAsnCertificateMybatisDao.add(docAsnCertificate);
		json.setSuccess(true);
		return json;
	}

	public Json editDocAsnCertificate(DocAsnCertificateForm docAsnCertificateForm) {
		Json json = new Json();
		//DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.findById(docAsnCertificateForm.getSku());
		//BeanUtils.copyProperties(docAsnCertificateForm, docAsnCertificate);
		docAsnCertificateMybatisDao.update(docAsnCertificateForm);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocAsnCertificate(DocAsnCertificateQuery query) {
		Json json = new Json();
		//DocAsnCertificate docAsnCertificate = docAsnCertificateMybatisDao.findById(id);
		if(query != null){
			docAsnCertificateMybatisDao.delete(query);
		}
		json.setSuccess(true);
		return json;
	}

	public Json getDocAsnCertificateInfo(DocAsnCertificateQuery query){
		DocAsnCertificate docAsnCerdtificate = docAsnCertificateMybatisDao.queryById(query);
		if(docAsnCerdtificate == null){
			return Json.error("质量合格证信息不存在！");
		}
		DocAsnCertificateVO docAsnCertificateVO = new DocAsnCertificateVO();

		BeanUtils.copyProperties(docAsnCerdtificate, docAsnCertificateVO);

		docAsnCertificateVO.setAddtime(simpleDateFormat.format(docAsnCerdtificate.getAddtime()));
		docAsnCertificateVO.setEdittime(simpleDateFormat.format(new Date()));

		return Json.success("",docAsnCertificateVO);
	}


	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("docAsnCertificate_template.xls"));
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
				toClient.flush();
				System.out.println();
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
			json = importDocAsnCertificateDataService.importExcelData(excelFile);
		}
		return json;
	}

}