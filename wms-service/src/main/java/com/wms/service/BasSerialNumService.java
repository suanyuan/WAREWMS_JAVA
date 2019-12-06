package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSerialNum;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSerialNumMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.OrderDetailsForNormalMybatisDao;
import com.wms.query.BasSerialNumQuery;
import com.wms.service.importdata.ImportBasSerialNumDataService;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.ResourceUtil;
import com.wms.utils.exception.ExcelException;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service("basSerialNumService")
public class BasSerialNumService extends BaseService {

	@Autowired
	private BasSerialNumMybatisDao basSerialNumDao;
    @Autowired
	private ImportBasSerialNumDataService importBasSerialNumDataService;
    @Autowired
	private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;

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

	/**
	 * 根据序列号删除出库日期出库单号为空的数据
	 * @param id
	 * @return
	 */
	public Json deleteBasSerialNum(String id) {
		Json json = new Json();
		int num=basSerialNumDao.deleteById(id);
		if(num>0){
			json.setSuccess(true);
			json.setMsg("删除成功!");
		}else {
			json.setSuccess(false);
			json.setMsg("当前状态不可删除!");
		}
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

	//导出
	public void exportDataToExcel(HttpServletResponse response, BasSerialNumQuery form) throws IOException {
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
			String sheetName = "入库序列号管理";
			// excel要导出的数据
			List<BasSerialNum> gspProductRegisterSpecsList = basSerialNumDao.queryByList(mybatisCriteria);
			// 导出


			if (gspProductRegisterSpecsList == null || gspProductRegisterSpecsList.size() == 0) {
				System.out.println("入库序列号管理为空");
			}else {
				for (BasSerialNum s: gspProductRegisterSpecsList) {
//                    FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
//                    BeanUtils.copyProperties(s, firstReviewLogForm);


					//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=null;


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
		superClassMap.put("deliveryNum", "发货凭证号");
		superClassMap.put("materialNum", "产品代码");
		superClassMap.put("batchNum", "批号");
		superClassMap.put("serialNum", "序列号");
		superClassMap.put("userdefine1", "入库时间");
		superClassMap.put("userdefine2", "出库时间");
		superClassMap.put("userdefine3", "出库单号");
		superClassMap.put("addtime", "创建时间");
		superClassMap.put("addwho", "创建人");
		superClassMap.put("edittime", "编辑时间");
		superClassMap.put("editwho", "编辑人");


		return superClassMap;
	}



	/**
	 * 通过发运订单号，查看关联的发货凭证号是否导入了入库序列号
	 * 并且验证总数和出库总数是否对的上
	 * @param orderno 出库单号
	 */
	public Json countSerialNum4Match(String orderno) {

		int importedCount = basSerialNumDao.countSerialNum4Match(orderno);
		if (importedCount == 0) {
			return Json.error(orderno + ":未导入序列号，请至入库序列号管理中完善数据!");
		}
		int orderedCount = orderDetailsForNormalMybatisDao.sumSerialNumRecordRequired(orderno);
		if (orderedCount != importedCount) {
			return Json.error(orderno + ":已导入的序列号条数与出库订单件数合计不匹配，请进行排查!");
		}
		return Json.success("");
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