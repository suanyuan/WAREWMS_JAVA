package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.*;
import com.wms.result.FirstBusinessApplyResult;
import com.wms.result.FirstBusinessProductApplyResult;
import com.wms.utils.DateUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.*;
import com.wms.vo.form.FirstReviewLogForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstBusinessApplyForm;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("firstBusinessApplyService")
public class FirstBusinessApplyService extends BaseService {

	@Autowired
	private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;
	@Autowired
	private FirstBusinessProductApplyMybatisDao firstBusinessProductApplyMybatisDao;
	@Autowired
	private FirstReviewLogService firstReviewLogService;
	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;
	@Autowired
	private FirstBusinessProductApplyService firstBusinessProductApplyService;
	@Autowired
	private DataPublishService dataPublishService;
	@Autowired
	private GspSupplierService gspSupplierService;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	@Autowired
	private GspOperateLicenseService gspOperateLicenseService;
	@Autowired
	private GspProductRegisterService gspProductRegisterService;
	@Autowired
	private GspProductRegisterSpecsService gspProductRegisterSpecsService;
	@Autowired
	private BasCustomerService basCustomerService;


	public EasyuiDatagrid<FirstBusinessApplyVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstBusinessApplyQuery query) {
		EasyuiDatagrid<FirstBusinessApplyVO> datagrid = new EasyuiDatagrid<FirstBusinessApplyVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		//query.setIsUse(Constant.IS_USE_YES);
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setOrderByClause("t1.create_date desc");
		List<FirstBusinessApplyResult> firstBusinessApplyList = firstBusinessApplyMybatisDao.queryPageList(criteria);
		FirstBusinessApplyVO firstBusinessApplyVO = null;
		List<FirstBusinessApplyVO> firstBusinessApplyVOList = new ArrayList<FirstBusinessApplyVO>();
		for (FirstBusinessApplyResult firstBusinessApply : firstBusinessApplyList) {
			firstBusinessApplyVO = new FirstBusinessApplyVO();
			BeanUtils.copyProperties(firstBusinessApply, firstBusinessApplyVO);
			firstBusinessApplyVO.setCreateDate(DateUtil.format(firstBusinessApply.getCreateDate(),"yyyy-MM-dd"));
			firstBusinessApplyVOList.add(firstBusinessApplyVO);
		}
		Long count = firstBusinessApplyMybatisDao.queryPageListCount(criteria);
		datagrid.setTotal(count);
		datagrid.setRows(firstBusinessApplyVOList);
		return datagrid;
	}

	public Json addFirstBusinessApply(FirstBusinessApplyForm firstBusinessApplyForm) throws Exception {
		Json json = new Json();
		FirstBusinessApply firstBusinessApply = new FirstBusinessApply();



		BeanUtils.copyProperties(firstBusinessApplyForm, firstBusinessApply);
		firstBusinessApply.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		firstBusinessApply.setIsUse(Constant.IS_USE_YES);
		firstBusinessApplyMybatisDao.add(firstBusinessApply);
		json.setSuccess(true);
		return json;
	}

	public Json editFirstBusinessApply(FirstBusinessApplyForm firstBusinessApplyForm) {
		Json json = new Json();
		FirstBusinessApply firstBusinessApply = firstBusinessApplyMybatisDao.queryById(firstBusinessApplyForm.getApplyId());
		BeanUtils.copyProperties(firstBusinessApplyForm, firstBusinessApply);
		firstBusinessApplyMybatisDao.updateBySelective(firstBusinessApply);
		json.setSuccess(true);
		return json;
	}

	public Json deleteFirstBusinessApply(String id) {
		Json json = new Json();
		FirstBusinessApply firstBusinessApply = firstBusinessApplyMybatisDao.queryById(id);
		if(firstBusinessApply != null){
			firstBusinessApplyMybatisDao.delete(firstBusinessApply);
		}
		json.setSuccess(true);
		return json;
	}

	public Json queryFirstBusinessApply(String id){
		FirstBusinessApply firstBusinessApply = firstBusinessApplyMybatisDao.queryById(id);
		if(firstBusinessApply!=null){
			FirstBusinessApplyVO vo = new FirstBusinessApplyVO();
			BeanUtils.copyProperties(firstBusinessApply,vo);
			BasCustomer client = basCustomerService.selectCustomerById(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW);
			if(client!=null){
				vo.setClientName(client.getDescrC());
			}
			BasCustomer supplier = basCustomerService.selectCustomerById(firstBusinessApply.getSupplierId(),Constant.CODE_CUS_TYP_VE);
			if(supplier!=null){
				vo.setSupplierName(supplier.getDescrC());
			}
			return Json.success("",vo);
		}
		return Json.error("");
	}

	/**
	 * 产品首营申请列表
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<FirstBusinessProductApplyPageVO> queryFirstBusinessApplyProduct(EasyuiDatagridPager pager, FirstBusinessProductApplyQuery query){
		EasyuiDatagrid<FirstBusinessProductApplyPageVO> datagrid = new EasyuiDatagrid<>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setIsUse(Constant.IS_USE_YES);
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");

		List<FirstBusinessProductApplyResult> list = firstBusinessProductApplyMybatisDao.queryPageList(mybatisCriteria);
		FirstBusinessProductApplyPageVO firstBusinessProductApplyPageVO = null;
		List<FirstBusinessProductApplyPageVO> voList = new ArrayList<>();
		if(list!=null){
			for(FirstBusinessProductApplyResult result : list){
				firstBusinessProductApplyPageVO = new FirstBusinessProductApplyPageVO();
				firstBusinessProductApplyPageVO.setProductApplyId(result.getProductApplyId());
				firstBusinessProductApplyPageVO.setProductCode(result.getProductCode());
				firstBusinessProductApplyPageVO.setProductName(result.getProductName());
				firstBusinessProductApplyPageVO.setSpecsName(result.getSpecsName());
				firstBusinessProductApplyPageVO.setProductModel(result.getProductModel());
				firstBusinessProductApplyPageVO.setSpecsId(result.getSpecsId());
				firstBusinessProductApplyPageVO.setSupplierName(result.getSupplierName());
				voList.add(firstBusinessProductApplyPageVO);
			}
		}
		int count = firstBusinessProductApplyMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(voList);
		return datagrid;
	}

	public Json addApply(String clientId,String supplierId,String productArr,String productLine){
		try{

			if("".equals(clientId)){
				return Json.error("请选择委托客户");
			}

			/*if("".equals(supplierId)){
				return Json.error("请选择供应商");
			}*/

			if("".equals(productArr)){
				return Json.error("请选择首营产品");
			}

			//检查经营范围
			Json checkScopeResult = checkBusinessScope(clientId,supplierId,productArr);
			if(!checkScopeResult.isSuccess()){
				return checkScopeResult;
			}
			String[] arr = productArr.split(",");
//			for(int a =0;arr.length>0;a++){
			boolean flag = true;
			for(String specsId : arr) {
				FirstBusinessApply firstBusinessApply1= new FirstBusinessApply();
				firstBusinessApply1.setSupplierId(supplierId);
				firstBusinessApply1.setSpecsId(specsId);
				firstBusinessApply1.setClientId(clientId);
				int num = firstBusinessApplyMybatisDao.selectFirstBusinessBySupplierAndProduct(firstBusinessApply1);
				if(num>0){
					flag = false;
				}
			}
			if(!flag){
				return 	Json.error("同一货主 供应商与产品！  不能重复申请！");
			}
			//specsId   产品id
			for(String specsId : arr){
				FirstBusinessApply firstBusinessApply = new FirstBusinessApply();
				String no = commonService.generateSeq(Constant.APLPRONO, SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				firstBusinessApply.setApplyId(no);
				firstBusinessApply.setClientId(clientId);
				firstBusinessApply.setSupplierId(supplierId);
				firstBusinessApply.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstBusinessApply.setIsUse(Constant.IS_USE_YES);
				firstBusinessApply.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
				firstBusinessApply.setProductline(productLine);
				firstBusinessApplyMybatisDao.add(firstBusinessApply);

//			String[] arr = productArr.split(",");

				FirstBusinessProductApply firstBusinessProductApply = new FirstBusinessProductApply();
				firstBusinessProductApply.setProductApplyId(no);
				firstBusinessProductApply.setApplyId(firstBusinessApply.getApplyId());
				firstBusinessProductApply.setSpecsId(specsId);
				firstBusinessProductApply.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstBusinessProductApply.setIsUse(Constant.IS_USE_YES);

				firstBusinessProductApplyMybatisDao.add(firstBusinessProductApply);

				//添加申请记录
				FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
				firstReviewLogForm.setReviewId(RandomUtil.getUUID());
				firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
				firstReviewLogForm.setReviewTypeId(no);
				firstReviewLogForm.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstReviewLogService.addFirstReviewLog(firstReviewLogForm);
			}
			return Json.success("申请成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("申请失败");
	}

	public Json editApply(String id,String clientId,String supplierId,String productArr,String productLine){
		if("".equals(clientId)){
			return Json.error("请选择委托客户");
		}

		/*if("".equals(supplierId)){
			return Json.error("请选择供应商");
		}*/

		if("".equals(productArr)){
			return Json.error("请选择首营产品");
		}

		try{
			FirstBusinessApply oldApply = firstBusinessApplyMybatisDao.queryById(id);
			if(oldApply!=null){

				if(!oldApply.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_NEW)){
					return Json.error("不是新建状态的申请单无法修改");
				}

				FirstBusinessApply update = new FirstBusinessApply();
				update.setApplyId(id);
				update.setIsUse(Constant.IS_USE_NO);
				firstBusinessApplyMybatisDao.updateBySelective(update);

				MybatisCriteria mybatisCriteria = new MybatisCriteria();
				FirstBusinessProductApplyQuery query = new FirstBusinessProductApplyQuery();
				query.setIsUse(Constant.IS_USE_YES);
				query.setApplyId(id);
				mybatisCriteria.setCondition(query);
				List<FirstBusinessProductApplyResult> list = firstBusinessProductApplyMybatisDao.queryPageList(mybatisCriteria);

				for(FirstBusinessProductApplyResult result : list){
					FirstBusinessProductApply firstUpdate = new FirstBusinessProductApply();
					firstUpdate.setProductApplyId(result.getProductApplyId());
					firstUpdate.setIsUse(Constant.IS_USE_NO);
					firstBusinessProductApplyMybatisDao.updateBySelective(firstUpdate);
				}

				return addApply(clientId,supplierId,productArr,productLine);
			}
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("申请失败");
	}

	public Json addConfirmApply(String id){
		try{
			FirstBusinessApply firstBusinessApply = new FirstBusinessApply();
			firstBusinessApply.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING);
			firstBusinessApply.setApplyId(id);
			firstBusinessApplyMybatisDao.updateBySelective(firstBusinessApply);

			//更新申请记录
			firstReviewLogService.updateFirstReviewByNo(id,Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING);

			return Json.error("操作成功");

		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("操作失败");
	}

	public Json addReApply(String id){
		try{
			//重新申请
			Json json = queryFirstBusinessApply(id);
			if(!json.isSuccess() || json.getObj() == null){
				return Json.error("没有查询到对应的申请单号");
			}

			//重新插入单据
			/*FirstBusinessApply newApply = firstBusinessApplyMybatisDao.queryById(id);
			Json result = firstBusinessProductApplyService.getListByApplyIdNoUse(id);
			if(result.isSuccess()){
				List<FirstBusinessProductApply> list = (List<FirstBusinessProductApply>)result.getObj();
				if(list!=null && list.size()>0){
					List<String> arrlist = new ArrayList<>();
					for(FirstBusinessProductApply f : list){
						arrlist.add(f.getSpecsId());
					}
					//addApply(newApply.getClientId(),newApply.getSupplierId(),arrlist.toString(),"");
				}
			}*/

			//TODO 失效已下发的数据
			dataPublishService.cancelData(id);

			//修改原数据为失效
			FirstBusinessApply firstBusinessApply = new FirstBusinessApply();
			firstBusinessApply.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
			firstBusinessApply.setApplyId(id);
			firstBusinessApply.setIsUse(Constant.IS_USE_NO);
			firstBusinessApplyMybatisDao.updateBySelective(firstBusinessApply);
			//更新申请记录
			firstReviewLogService.updateFirstReviewByNo(id,Constant.CODE_CATALOG_CHECKSTATE_FAIL);

			return Json.success("操作成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("操作失败");
	}
//根据委托客户获取产品线
	public List<EasyuiCombobox> getProductLineByEnterpriseId(String customerId){

		List<EasyuiCombobox> comboboxList = new ArrayList<>();
		BasCustomer basCustomer = basCustomerService.selectCustomerById(customerId,Constant.CODE_CUS_TYP_OW );
		if(basCustomer==null){
			return  comboboxList;
		}
		GspEnterpriseInfo info = gspEnterpriseInfoService.getGspEnterpriseInfo(basCustomer.getEnterpriseId());
		if(basCustomer!=null && info!=null){
			MybatisCriteria criteria = new MybatisCriteria();
			ProductLineQuery query = new ProductLineQuery();
			query.setCustomerid(info.getEnterpriseNo());
			query.setIsUse(Constant.IS_USE_YES);
			criteria.setCondition(query);
			List<ProductLine> lineList = productLineMybatisDao.queryByList(criteria);
			if(lineList!=null){
				for(ProductLine p : lineList){
					EasyuiCombobox combobox = new EasyuiCombobox();
					combobox.setId(p.getProductLineId());
					combobox.setValue(p.getName());
					comboboxList.add(combobox);
				}
			}
		}
		return comboboxList;
	}

	/**
	 * 更新首营状态
	 * @param no
	 * @param state
	 * @return
	 */
	public Json updateFirstState(String no,String state){
		Long result = firstBusinessApplyMybatisDao.updateBusinessByNo(no,state);
		if(result>0){
			return Json.success("更新申请单首营状态成功");
		}
		return Json.error("更新申请单首营状态失败");
	}

	public Json checkBusinessScope(String clientId,String supplierId,String productArr){
		//委托客户scope是否需要判断
		BasCustomer basCustomer = basCustomerService.selectCustomerById(supplierId,Constant.CODE_CUS_TYP_VE);
		if(basCustomer == null){
			return Json.error("查询不到对应的供应商");
		}

		GspOperateLicenseQuery query = new GspOperateLicenseQuery();
		query.setIsUse(Constant.IS_USE_YES);
		query.setEnterpriseId(basCustomer.getEnterpriseId());
		GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);
		if(gspOperateLicense == null){
			return Json.error("供应商查询不到有效的生产营业执照信息");
		}
		//TODO 如果委托方是供应商不需要进行判断

		List<GspOperateDetailVO> operateDetails = gspOperateDetailService.queryOperateDetailByLicense(gspOperateLicense.getOperateId());

		return operateDetailIsRight(operateDetails,productArr);
	}

	/**
	 * 经营范围比对
	 * @param
	 * @return
	 */
	private Json operateDetailIsRight(List<GspOperateDetailVO> operateDetails,String productArr){
		if(operateDetails == null || productArr.equals("")){
			return Json.error("没有选择经营范围");
		}

		String[] productArrList = productArr.split(",");
		List<String> arrlicense = new ArrayList<>();
		List<String> arroperate = new ArrayList<>();

		Json productSpec = gspProductRegisterSpecsService.getGspProductRegisterSpecsInfo(productArrList[0]);
		if(productSpec == null){
			return Json.error("没有查询到对应的产品");
		}
		GspProductRegisterSpecsVO spec = (GspProductRegisterSpecsVO)productSpec.getObj();

		List<GspOperateDetailVO> registerDetailVo = gspOperateDetailService.queryOperateDetailByLicense(spec.getProductRegisterId());
		if(registerDetailVo == null || registerDetailVo.size() == 0){
			return Json.error("产品注册证没有选择经营范围");
		}

		/*for(GspOperateDetailVO v : operateDetails){
			arrlicense.add(v.getOperateId());
		}
		for(GspOperateDetailVO v : registerDetailVo){
			arroperate.add(v.getOperateId());
		}
		for(String s : arroperate){
			if(arrlicense.toString().indexOf(s)==-1){
				return Json.error("供应商生经营产/许可证和产品注册证范围不匹配");
			}
		}*/
		return Json.success("");


	}

}