package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSerialNum;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSerialNumMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasSerialNumQuery;
import com.wms.service.importdata.ImportBasSerialNumDataService;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ResourceUtil;
import com.wms.vo.BasSerialNumVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasSerialNumForm;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service("basSerialNumService")
public class BasSerialNumService extends BaseService {

	@Autowired
	private BasSerialNumMybatisDao basSerialNumDao;
    @Autowired
	private ImportBasSerialNumDataService importBasSerialNumDataService;
//显示主页datagrid
	public EasyuiDatagrid<BasSerialNumVO> getPagedDatagrid(EasyuiDatagridPager pager, BasSerialNumQuery query) {
		EasyuiDatagrid<BasSerialNumVO> datagrid = new EasyuiDatagrid<BasSerialNumVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<BasSerialNum> basSerialNumList = basSerialNumDao.queryByList(mybatisCriteria);
		BasSerialNumVO basSerialNumVO = null;
		List<BasSerialNumVO> basSerialNumVOList = new ArrayList<BasSerialNumVO>();
		for (BasSerialNum basSerialNum : basSerialNumList) {
			basSerialNumVO = new BasSerialNumVO();
			BeanUtils.copyProperties(basSerialNum, basSerialNumVO);
			basSerialNumVOList.add(basSerialNumVO);
		}
		datagrid.setTotal((long)basSerialNumDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basSerialNumVOList);
		return datagrid;
	}

	public Json addBasSerialNum(BasSerialNumForm basSerialNumForm) throws Exception {
		Json json = new Json();
		BasSerialNum basSerialNum = new BasSerialNum();
		BeanUtils.copyProperties(basSerialNumForm, basSerialNum);
		basSerialNumDao.add(basSerialNum);
		json.setSuccess(true);
		return json;
	}

	public Json editBasSerialNum(BasSerialNumForm basSerialNumForm) {
		Json json = new Json();
		BasSerialNum basSerialNum = basSerialNumDao.queryById(basSerialNumForm.getSerialNum());
		BeanUtils.copyProperties(basSerialNumForm, basSerialNum);
		basSerialNumDao.update(basSerialNum);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasSerialNum(String id) {
		Json json = new Json();
		BasSerialNum basSerialNum = basSerialNumDao.queryById(id);
		if(basSerialNum != null){
			basSerialNumDao.delete(basSerialNum);
		}
		json.setSuccess(true);
		json.setMsg("删除成功!");
		return json;
	}


//下载导入模板
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream())) {
			File file = new File(ResourceUtil.getImportRootPath("BasSerialNum_template.xls"));
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
//导入
	public Json importExcelData(MultipartHttpServletRequest mhsr){
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		//System.out.println("======excelFile.getSize()=="+excelFile.getSize()+"======="+excelFile.getInputStream().getClass().getName());
		if(excelFile != null && excelFile.getSize() > 0){
      		json = importBasSerialNumDataService.importExcelData(excelFile);
		}
		return json;
	}





//
//	public List<EasyuiCombobox> getBasSerialNumCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<BasSerialNum> basSerialNumList = basSerialNumDao.();
//		if(basSerialNumList != null && basSerialNumList.size() > 0){
//			for(BasSerialNum basSerialNum : basSerialNumList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(basSerialNum.getId()));
//				combobox.setValue(basSerialNum.getBasSerialNumName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

}