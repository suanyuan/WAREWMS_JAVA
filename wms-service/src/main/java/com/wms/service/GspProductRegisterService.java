package com.wms.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wms.constant.Constant;
import com.wms.entity.FirstBusinessApply;
import com.wms.entity.GspOperateDetail;
import com.wms.entity.GspProductRegisterSpecs;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.GspProductRegisterSpecsQuery;
import com.wms.service.importdata.ImportGspProductRegisterDataService;
import com.wms.utils.*;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.GspOperateDetailVO;
import com.wms.vo.GspProductRegisterSpecsVO;
import com.wms.vo.form.GspOperateDetailForm;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspProductRegister;
import com.wms.vo.GspProductRegisterVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterForm;
import com.wms.query.GspProductRegisterQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.SAXException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("gspProductRegisterService")
public class GspProductRegisterService extends BaseService {

	@Autowired
	private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	@Autowired
	private ImportGspProductRegisterDataService importGspProductRegisterDataService;
	@Autowired
	private DataPublishService dataPublishService;
	@Autowired
	private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;
	@Autowired
	private ProductRegisterRelationMybatisDao productRegisterRelationMybatisDao;

	/**
	 * 查询分页数据
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspProductRegisterVO> getPagedDatagrid(EasyuiDatagridPager pager, GspProductRegisterQuery query) {
		EasyuiDatagrid<GspProductRegisterVO> datagrid = new EasyuiDatagrid<GspProductRegisterVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
        if(query.getIdList()!=null&&query.getIdList()!="" ){
            List<String> enterpriseIdList = jsonToList(query.getIdList(),String.class);
            mybatisCriteria.setIdList(enterpriseIdList);
        }


		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");
		List<GspProductRegister> gspProductRegisterList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
		GspProductRegisterVO gspProductRegisterVO = null;
		List<GspProductRegisterVO> gspProductRegisterVOList = new ArrayList<GspProductRegisterVO>();
		for (GspProductRegister gspProductRegister : gspProductRegisterList) {
			gspProductRegisterVO = new GspProductRegisterVO();
			BeanUtils.copyProperties(gspProductRegister, gspProductRegisterVO);
			gspProductRegisterVOList.add(gspProductRegisterVO);
		}
		int count = gspProductRegisterMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspProductRegisterVOList);
		return datagrid;
	}
	//增加
	public Json addGspProductRegister(GspProductRegisterForm gspProductRegisterForm) throws Exception {
		if(!checkRep(gspProductRegisterForm.getProductRegisterNo())){
			return Json.error("产品注册证编号重复");
		}

		if(StringUtils.isEmpty(gspProductRegisterForm.getEnterpriseId())){
			return Json.error("请填写生产企业");
		}
		try{
			GspProductRegister gspProductRegister = new GspProductRegister();
			BeanUtils.copyProperties(gspProductRegisterForm, gspProductRegister);
			gspProductRegister.setProductRegisterId(RandomUtil.getUUID());
			gspProductRegister.setIsUse(Constant.IS_USE_YES);
			gspProductRegister.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			gspProductRegister.setApproveDate(DateUtil.parse(gspProductRegisterForm.getApproveDate(),"yyyy-MM-dd"));
			gspProductRegister.setProductRegisterExpiryDate(DateUtil.parse(gspProductRegisterForm.getProductRegisterExpiryDate(),"yyyy-MM-dd"));
			gspProductRegister.setVersion(gspProductRegister.getProductRegisterId());
//			gspProductRegister.setProductRegisterNo();
			String  textContent =gspProductRegister.getProductRegisterNo().trim();
			while (textContent.startsWith("　")) {//这里判断是不是全角空格
				textContent = textContent.substring(1, textContent.length()).trim();
			}
			while (textContent.endsWith("　")) {
				textContent = textContent.substring(0, textContent.length() - 1).trim();
			}
			gspProductRegister.setProductRegisterNo(textContent);

			gspProductRegisterMybatisDao.add(gspProductRegister);

			//保存经营范围
			if(!StringUtils.isEmpty(gspProductRegisterForm.getChoseScope())){
				String jsonStr = gspOperateDetailService.initScope(gspProductRegisterForm.getChoseScope());
				List<GspOperateDetailForm> operateDetailForms = JSON.parseArray(jsonStr, GspOperateDetailForm.class);
				if(operateDetailForms.size()>0){
					for(GspOperateDetailForm g : operateDetailForms){
						g.setEnterpriseId(gspProductRegister.getProductRegisterId());
						gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_REGISTER);
					}
				}
			}
			return Json.success("操作成功",gspProductRegister.getProductRegisterId());
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.success("保存失败");
		}
	}
	//修改
	public Json editGspProductRegister(GspProductRegisterForm gspProductRegisterForm) throws Exception{
		try{
			GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(gspProductRegisterForm.getProductRegisterId());

			if(gspProductRegister == null){
				return Json.error("查询不到对应的产品注册证信息");
			}
//			int num  =gspProductRegisterMybatisDao.countByproductResisterNo(gspProductRegisterForm.getProductRegisterNo());

			if(!gspProductRegisterForm.getProductRegisterNo().equals(gspProductRegister.getProductRegisterNo())){
				//换了注册证号
				if(!checkRep(gspProductRegisterForm.getProductRegisterNo())){
					return Json.error("产品注册证编号重复");
				}
//				!checkRep(gspProductRegisterForm.getProductRegisterNo());
			}
//			if(!checkRep(gspProductRegisterForm.getProductRegisterNo())){
//				return Json.error("产品注册证编号重复");
//			}


			if(gspProductRegisterForm.getOpType().equals("update")){
				/////////新关系下没有换证逻辑存在了


				//注册证下产品是否首营   没首营直接换证
				// 	首营过则判断产品状态  首营审核中不能换证
				// 	首营审核通过换证 报废产品新建一条产品关联新注册证 报废首营申请新建一条首营关联新产品  报废产品档案
				//	首营新建（直接换证报废报废产品新建一条产品关联新注册证 删除原新建的首营和新建的GSP审核  新增一个新建首营关联新产品 新增一个新建gsp审核）

				//有问题 拦截无效
				List<FirstBusinessApply> firstBusinessApplyList = firstBusinessApplyMybatisDao.selectByProductRegisterId(gspProductRegisterForm.getProductRegisterId());
				if(firstBusinessApplyList!=null && firstBusinessApplyList.size()>0){
					for(FirstBusinessApply f:firstBusinessApplyList){
						if(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING.equals(f.getFirstState())){
							return Json.error("注册证关联产品存在首营审核中的申请，无法换证！");
						}else if(Constant.CODE_CATALOG_FIRSTSTATE_NEW.equals(f.getFirstState())){
							return Json.error("注册证关联产品存在首营新建的申请，无法换证！");
						}else if(Constant.CODE_CATALOG_FIRSTSTATE_PASS.equals(f.getFirstState())){

						}
					}
				}


                //失效原注册证，数据
                gspProductRegister.setIsUse(Constant.IS_USE_NO);
                gspProductRegisterMybatisDao.updateBySelective(gspProductRegister);
				//失效注册证分类范围
                List<GspOperateDetailVO> gspOperateDetailList = gspOperateDetailService.queryOperateDetailByLicense(gspProductRegister.getProductRegisterId());
                if(gspOperateDetailList!=null && gspOperateDetailList.size()>0){
                    for(GspOperateDetailVO v : gspOperateDetailList){
                        gspOperateDetailService.invalidGspOperateDetail(v.getLicenseId(),Constant.LICENSE_TYPE_REGISTER);
                    }
                }
				//新建注册证
                GspProductRegister newGspProductRegister = new GspProductRegister();
                BeanUtils.copyProperties(gspProductRegisterForm, newGspProductRegister);
                newGspProductRegister.setProductRegisterId(RandomUtil.getUUID());
                newGspProductRegister.setIsUse(Constant.IS_USE_YES);
                newGspProductRegister.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
                newGspProductRegister.setApproveDate(DateUtil.parse(gspProductRegisterForm.getApproveDate(),"yyyy-MM-dd"));
                newGspProductRegister.setProductRegisterExpiryDate(DateUtil.parse(gspProductRegisterForm.getProductRegisterExpiryDate(),"yyyy-MM-dd"));
                newGspProductRegister.setVersion(gspProductRegister.getVersion());
                gspProductRegisterMybatisDao.add(newGspProductRegister);

                //保存经营范围
                if(!StringUtils.isEmpty(gspProductRegisterForm.getChoseScope())){
                    String jsonStr = gspOperateDetailService.initScope(gspProductRegisterForm.getChoseScope());
                    List<GspOperateDetailForm> operateDetailForms = JSON.parseArray(jsonStr, GspOperateDetailForm.class);
                    if(operateDetailForms.size()>0){
                        for(GspOperateDetailForm g : operateDetailForms){
                            g.setEnterpriseId(newGspProductRegister.getProductRegisterId());
                            gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_REGISTER);
                        }
                    }
                }

				//TODO 失效已下发数据
				//dataPublishService.publishData(gspProductRegister.getProductRegisterId());
				dataPublishService.cancelPubilseDataByRegisterId(gspProductRegister.getProductRegisterId(),newGspProductRegister.getProductRegisterId());

			}else{
//				if(gspProductRegister.getCheckerId()!=null && !StringUtils.isEmpty(gspProductRegister.getCheckerId())){
//					return Json.error("已经审核的产品注册证不能直接修改，需要进行换证并重新首营审核");
//				}
                BeanUtils.copyProperties(gspProductRegisterForm, gspProductRegister);
                gspProductRegister.setApproveDate(DateUtil.parse(gspProductRegisterForm.getApproveDate(),"yyyy-MM-dd"));
                gspProductRegister.setProductRegisterExpiryDate(DateUtil.parse(gspProductRegisterForm.getProductRegisterExpiryDate(),"yyyy-MM-dd"));
                gspProductRegister.setEditDate(new Date());
                gspProductRegister.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspProductRegister.setIsUse(Constant.IS_USE_YES);
                gspProductRegisterMybatisDao.updateBySelective(gspProductRegister);

                //保存经营范围
                if(!StringUtils.isEmpty(gspProductRegisterForm.getChoseScope())) {
                    String jsonStr = gspOperateDetailService.initScope(gspProductRegisterForm.getChoseScope());
                    List<GspOperateDetailForm> operateDetailForms = JSON.parseArray(jsonStr, GspOperateDetailForm.class);

                    gspOperateDetailService.deleteGspOperateDetail(gspProductRegister.getProductRegisterId(),Constant.LICENSE_TYPE_REGISTER);
                    if(operateDetailForms.size()>0){
                        for(GspOperateDetailForm g : operateDetailForms){
                            g.setEnterpriseId(gspProductRegister.getProductRegisterId());
                            gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_REGISTER);
                        }
                    }
                }
            }

			return Json.success("操作成功",gspProductRegister.getProductRegisterId());
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.success("保存失败");
		}
	}

	public Json deleteGspProductRegister(String id) {
		Json json = new Json();


		GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(id);

		int num  = gspProductRegisterSpecsMybatisDao.queryByListBind(id);
		if(num>0){
			return Json.error("注册证下有产品无法删除");
		}

		int num1  = gspProductRegisterSpecsMybatisDao.queryKUCUN(id);
		if(num1>0){
			return Json.error("注册证下有库存无法删除");
		}

		if(gspProductRegister != null){
			gspProductRegisterMybatisDao.delete(gspProductRegister);
		}

		json.setMsg("删除成功");
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspProductRegisterCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		Map<String,Object> query = new HashMap<>();
		query.put("is_use", Constant.IS_USE_YES);
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");
		EasyuiCombobox combobox = null;
		List<GspProductRegister> gspProductRegisterList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
		if(gspProductRegisterList != null && gspProductRegisterList.size() > 0){
			for(GspProductRegister gspProductRegister : gspProductRegisterList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspProductRegister.getProductRegisterId()));
				combobox.setValue(gspProductRegister.getProductRegisterNo()+"-"+gspProductRegister.getProductRegisterName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public Json confirmSubmit(String id){
		try{
			if(StringUtils.isEmpty(id)){
				return Json.error("请选择要确认的数据");
			}

			GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(id);
			if (StringUtil.isEmpty(gspProductRegister.getAttachmentUrl())) {
			    return Json.error("请上传注册证/备案附件");
            }

			gspProductRegister.setCheckDate(new Date());
			gspProductRegister.setCheckerId(SfcUserLoginUtil.getLoginUser().getId());
			gspProductRegisterMybatisDao.updateBySelective(gspProductRegister);

//				firstReviewLogService.updateFirstReviewByNo(s,Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING);
			return Json.success("确认成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}

	}

	/**
	 * 查询产品注册证规格
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspProductRegisterSpecsVO> queryProductPageListByRegisterId(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query){
		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		if(!StringUtils.isEmpty(query.getIsUse())){
			query.setIsUse(Constant.IS_USE_YES);
		}
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");

		List<GspProductRegisterSpecs> list = gspProductRegisterSpecsMybatisDao.queryByListUnBind(mybatisCriteria);
		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
		List<GspProductRegisterSpecsVO> voList = new ArrayList<>();
		if(list!=null){
			for(GspProductRegisterSpecs specs : list){
				gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
				gspProductRegisterSpecsVO.setSpecsId(specs.getSpecsId());
				gspProductRegisterSpecsVO.setProductCode(specs.getProductCode());
				gspProductRegisterSpecsVO.setProductName(specs.getProductName());
				gspProductRegisterSpecsVO.setSpecsName(specs.getSpecsName());
				gspProductRegisterSpecsVO.setProductModel(specs.getProductModel());
				voList.add(gspProductRegisterSpecsVO);
			}
		}
		int count = gspProductRegisterSpecsMybatisDao.queryByCountUnBind(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(voList);
		return datagrid;
	}

	public GspProductRegister queryById(String id){
		return gspProductRegisterMybatisDao.queryById(id);
	}

	/**
	 * 绑定产品
	 * @param gspProductRegisterId
	 * @param specId
	 * @return
	 */
	public Json bindProduct(String gspProductRegisterId,String specId){
//		if(gspProductRegisterId.equals("")){
//			return Json.error("产品注册证为空");
//		}

		if(specId.equals("")){
			return Json.error("请选择需要绑定的产品");
		}


        GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(gspProductRegisterId);
//        if(gspProductRegister == null){
//            return Json.error("查询不到对应的注册证");
//        }
//        if(gspProductRegister.getCheckerId()!=null && !"".equals(gspProductRegister.getCheckerId())){
//            return Json.error("已经审核的产品注册证不能直接修改，需要进行换证并重新首营审核");
//        }

		try{
			String[] arr = specId.split(",");
			for(String str : arr){
				System.out.println(str);
				GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
				gspProductRegisterSpecs.setSpecsId(str);
				gspProductRegisterSpecs.setProductRegisterId(gspProductRegisterId);
				if(gspProductRegister.getStorageConditions()!=null){
					gspProductRegisterSpecs.setStorageCondition(gspProductRegister.getStorageConditions());
				}
				gspProductRegisterSpecs.setEnterpriseName(gspProductRegister.getEnterpriseName());
				gspProductRegisterSpecs.setLicenseOrRecordNo(gspProductRegister.getLicenseOrRecordNol());
				gspProductRegisterSpecs.setProductionAddress(gspProductRegister.getProductionAddress());
				gspProductRegisterSpecsMybatisDao.updateBySelective(gspProductRegisterSpecs);
			}
			return Json.success("绑定成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("绑定失败");
		}
	}

	/**
	 * 解除产品绑定
	 * @param specId
	 * @return
	 */
	public Json unBindProduct(String gspProductRegisterId,String specId){
		if(specId.equals("")){
			return Json.error("请选择需要解除绑定的产品");
		}
		GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(gspProductRegisterId);
		if(gspProductRegister == null){
            return Json.error("查询不到对应的注册证");
        }
//        if(gspProductRegister.getCheckerId()!=null && !"".equals(gspProductRegister.getCheckerId())){
//			return Json.error("已经审核的产品注册证不能直接修改，需要进行换证并重新首营审核");
//		}


		try{
			String[] arr = specId.split(",");
			for(String str : arr){
				System.out.println(str);

				//失效产品 清空注册证id
				GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
				gspProductRegisterSpecs.setSpecsId(str);
				gspProductRegisterSpecs.setProductRegisterId("");
				gspProductRegisterSpecs.setIsUse("0");
				gspProductRegisterSpecsMybatisDao.updateBySelective(gspProductRegisterSpecs);

				//删除关联
				productRegisterRelationMybatisDao.deleteByProductAndregister(specId,gspProductRegisterId);
				//TODO 报废该解绑产品的已下发产品首营

				List<String>  applyidList = firstBusinessApplyMybatisDao.selectBySpecsId(specId);
				for(String applyId:applyidList){
					dataPublishService.cancelData(applyId);
				}

			}
			return Json.success("解除绑定成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("解除绑定失败");
		}
	}


	public boolean checkRep(String registerNo){
//		MybatisCriteria criteria = new MybatisCriteria();
//		GspProductRegisterQuery query = new GspProductRegisterQuery();
//		query.setProductRegisterNo(registerNo);
//		query.setIsUse(Constant.IS_USE_YES);
//		criteria.setCondition(query);
//		List<GspProductRegister> list = gspProductRegisterMybatisDao.queryByList(criteria);

        List<PdaGspProductRegister> list = gspProductRegisterMybatisDao.queryAllByNo(registerNo);

		if(list!=null && list.size()>0){
			return false;
		}
		return true;
	}

//下载导入模板
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream())) {
			File file = new File(ResourceUtil.getImportRootPath("productRegister_template.xls"));
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
	public Json importExcelData(MultipartHttpServletRequest mhsr) throws UnsupportedEncodingException, IOException, ConfigurationException, BarcodeException, SAXException {
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		//System.out.println("======excelFile.getSize()=="+excelFile.getSize()+"======="+excelFile.getInputStream().getClass().getName());
		if(excelFile != null && excelFile.getSize() > 0){
			json = importGspProductRegisterDataService.importExcelData(excelFile);
		}
		return json;
	}

	public void exportDataToExcel(HttpServletResponse response, GspProductRegisterQuery form) throws IOException {
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
			// excel要导出的数据
			List<GspProductRegister> searchBasCustomerList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
			// 导出


			if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (GspProductRegister gspProductRegister: searchBasCustomerList) {
//                    FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
//                    BeanUtils.copyProperties(s, firstReviewLogForm);


					//时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
					Date date=null;


					if("1".equals(gspProductRegister.getIsUse())){
						gspProductRegister.setIsUse("是");
					}else if("0".equals(gspProductRegister.getIsUse())){
						gspProductRegister.setIsUse("否");
					}


					//时间转格式

					if(gspProductRegister.getApproveDate()!=null) {
						gspProductRegister.setApproveDateDc(sdf1.format(gspProductRegister.getApproveDate()));
					}
					if(gspProductRegister.getProductRegisterExpiryDate()!=null) {
						gspProductRegister.setProductRegisterExpiryDateDc(sdf1.format(gspProductRegister.getProductRegisterExpiryDate()));
					}
					if(gspProductRegister.getCheckDate()!=null) {
						gspProductRegister.setCheckDateDc(sdf.format(gspProductRegister.getCheckDate()));
					}
					if(gspProductRegister.getCreateDate()!=null) {
						gspProductRegister.setCreateDateDc(sdf.format(gspProductRegister.getCreateDate()));
					}
//
//
//
////
				}
//                List<FirstReviewLog> searchBasCustomerFormList  = new ArrayList<FirstReviewLog>();

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
	public LinkedHashMap<String, String> getCustomerProductLeadToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("isUse", "是否有效");
		superClassMap.put("productRegisterNo", "注册证编号/备案号");
		superClassMap.put("attachmentUrl", "注册证编号/备案附件");
		superClassMap.put("classifyId", "管理分类");
		superClassMap.put("classifyCatalog", "分类目录");
		superClassMap.put("productNameMain", "产品名称");
		superClassMap.put("approveDateDc", "批准日期");
		superClassMap.put("productRegisterExpiryDateDc", "有效期至");
		superClassMap.put("productRegisterVersionName", "注册证/备案版本");
		superClassMap.put("checkerId", "审核人");
		superClassMap.put("checkDateDc", "审核时间");
		superClassMap.put("createId", "创建人");
		superClassMap.put("createDateDc", "创建时间");
		return superClassMap;
	}









//根据ProductRegisterNo查询
	public GspProductRegister queryByRegisterNo(String registerNo){
		return  gspProductRegisterMybatisDao.queryByNo(registerNo);
	}
//根据productNameMain查询
	public GspProductRegister queryByproductNameMain(String productNameMain){
		return  gspProductRegisterMybatisDao.queryByproductNameMain(productNameMain);
	}

	//查询历史证照信息
	public EasyuiDatagrid<GspProductRegisterVO> showHistoryDatagrid(EasyuiDatagridPager pager, GspProductRegisterQuery query){
		EasyuiDatagrid<GspProductRegisterVO> datagrid = new EasyuiDatagrid<GspProductRegisterVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCurrentPage(pager.getPage());
		if(StringUtils.isEmpty(query.getVersion())){
			query.setVersion("empty");
		}
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");
		List<GspProductRegister> gspProductRegisterList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
		GspProductRegisterVO gspProductRegisterVO = null;
		List<GspProductRegisterVO> gspProductRegisterVOList = new ArrayList<GspProductRegisterVO>();
		for (GspProductRegister gspProductRegister : gspProductRegisterList) {
			gspProductRegisterVO = new GspProductRegisterVO();
			BeanUtils.copyProperties(gspProductRegister, gspProductRegisterVO);
			//查询器械目录
			List<GspOperateDetailVO> detailList = gspOperateDetailService.queryOperateDetailByLicense(gspProductRegister.getProductRegisterId());
			if(detailList!=null && detailList.size()>0){
				gspProductRegisterVO.setChoseScope(detailList.get(0).getOperateId());
			}
			gspProductRegisterVOList.add(gspProductRegisterVO);
		}
		int count = gspProductRegisterMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspProductRegisterVOList);
		return datagrid;
	}

	//根据ProductRegisterNo查询所有
	public List<PdaGspProductRegister> queryAllByRegisterNo(String registerNo){
		return gspProductRegisterMybatisDao.queryAllByNo(registerNo);
	}


    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        @SuppressWarnings("unchecked")
        List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
        return ts;
    }


}