package com.wms.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("firstBusinessApplyService")
public class FirstBusinessApplyService extends BaseService {

	@Autowired
	private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;
	@Autowired
	private FirstBusinessProductApplyMybatisDao firstBusinessProductApplyMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	@Autowired
	private GspSupplierMybatisDao gspSupplierMybatisDao;

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
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;
	@Autowired
	private  FirstReviewLogMybatisDao firstReviewLogMybatisDao;
	@Autowired
	private  BasCustomerMybatisDao basCustomerMybatisDao;

	@Autowired
	private GspVerifyService gspVerifyService;

	@Autowired
	private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;

	public EasyuiDatagrid<FirstBusinessApplyVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstBusinessApplyQuery query) {
		EasyuiDatagrid<FirstBusinessApplyVO> datagrid = new EasyuiDatagrid<FirstBusinessApplyVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		//query.setIsUse(Constant.IS_USE_YES);

		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		criteria.setOrderByClause("t1.create_date desc");
		List<FirstBusinessApplyResult> firstBusinessApplyList = firstBusinessApplyMybatisDao.queryPageList(criteria);
		FirstBusinessApplyVO firstBusinessApplyVO = null;
		List<FirstBusinessApplyVO> firstBusinessApplyVOList = new ArrayList<FirstBusinessApplyVO>();
		for (FirstBusinessApplyResult firstBusinessApply : firstBusinessApplyList) {
			firstBusinessApplyVO = new FirstBusinessApplyVO();
			BeanUtils.copyProperties(firstBusinessApply, firstBusinessApplyVO);
			firstBusinessApplyVO.setCreateDate(DateUtil.format(firstBusinessApply.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
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
        String[] arrId = id.split(",");

        for(String DelId : arrId){
            FirstBusinessApply firstBusinessApply = firstBusinessApplyMybatisDao.queryById(DelId);
            if(firstBusinessApply != null){
                firstBusinessApplyMybatisDao.delete(firstBusinessApply);
            }
        }

		json.setSuccess(true);
		return json;
	}



	public Json getInfo(String applyId){
        FirstBusinessApplyVO firstBusinessApplyVO = new FirstBusinessApplyVO();
		System.out.println("supplierId==========="+applyId);
		FirstBusinessProductApplyResult firstBusinessApply = firstBusinessProductApplyMybatisDao.queryEditByApplyId(applyId);
//		BeanUtils.copyProperties(firstBusinessApply, firstBusinessApplyVO);
		if(firstBusinessApply == null){
			return Json.error("不存在！");
		}
		return Json.success("",firstBusinessApply);
	}

	public Json queryFirstBusinessApply(String id){
		FirstBusinessApply firstBusinessApply = firstBusinessApplyMybatisDao.queryById(id);
		if(firstBusinessApply!=null){
			FirstBusinessApplyVO vo = new FirstBusinessApplyVO();
			BeanUtils.copyProperties(firstBusinessApply,vo);
			BasCustomer client = basCustomerService.selectCustomerById(firstBusinessApply.getClientId(),Constant.CODE_CUS_TYP_OW,"");
			if(client!=null){
				vo.setClientName(client.getDescrC());
				vo.setClientEnterpeiseId(client.getEnterpriseId());
			}
			BasCustomer supplier = basCustomerService.selectCustomerById(firstBusinessApply.getSupplierId(),Constant.CODE_CUS_TYP_VE,"");
			if(supplier!=null){
				vo.setSupplierName(supplier.getDescrC());
				vo.setSupplierEnterpeiseId(supplier.getEnterpriseId());
			}
			GspProductRegisterSpecs gspProductRegisterSpecs = gspProductRegisterSpecsMybatisDao.queryById(firstBusinessApply.getSpecsId());
			if(gspProductRegisterSpecs.getProductName()!=null){
				vo.setProductName(gspProductRegisterSpecs.getProductName());
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
//				firstBusinessProductApplyPageVO.setSpecsId(result.getSpecsId());
				firstBusinessProductApplyPageVO.setProductModel(result.getProductModel());
				firstBusinessProductApplyPageVO.setSpecsId(result.getSpecsId());
				firstBusinessProductApplyPageVO.setSupplierName(result.getSupplierName());
				firstBusinessProductApplyPageVO.setCustomerid(result.getCustomerid());
				firstBusinessProductApplyPageVO.setProductRegisterNo(result.getProductRegisterNo());
				firstBusinessProductApplyPageVO.setProductRegisterId(result.getProductRegisterId());
				voList.add(firstBusinessProductApplyPageVO);
			}
		}
		int count = firstBusinessProductApplyMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(voList);
		return datagrid;
	}

	@Transactional
	public Json addApply(String clientId,String supplierArr,String productArr,String productLine,boolean isReType,String applyId){
		try{

			String oldApplyId = "";
			FirstBusinessApply oldApply = null;
			GspProductRegisterSpecs oldProduct=null;
			GspProductRegister oldgpr =null;
			if(isReType){
				//发起新申请
				oldApplyId = applyId;
				oldApply = firstBusinessApplyMybatisDao.queryById(applyId);
				if(oldApply==null){
					Json.error("单号不存在");
				}
//				BasSku oldSku =basSkuMybatisDao.queryById();
				oldProduct = gspProductRegisterSpecsMybatisDao.queryById(oldApply.getSpecsId());
//				oldProduct  = gspProductRegisterSpecsMybatisDao.selectProductByCompare(oldApply.getSpecsId());
				if(oldProduct==null){
					Json.error("产品已经失效");
				}
				oldProduct=	gspProductRegisterSpecsMybatisDao.selectBasSkuByCompare(clientId,oldProduct.getProductCode());
				GspProductRegisterSpecs oldProductR =gspProductRegisterSpecsMybatisDao.queryById(oldApply.getSpecsId());


				oldgpr = gspProductRegisterMybatisDao.selectProductRegisterByCompare(oldProductR.getProductRegisterId());

				BasCustomer oldSup =basCustomerMybatisDao.queryByIdType(oldApply.getSupplierId(),Constant.CODE_CUS_TYP_VE);

			}

			if("".equals(clientId)){
				return Json.error("请选择委托客户");
			}

			/*if("".equals(supplierId)){
				return Json.error("请选择供应商");
			}*/

			if("".equals(productArr)){
				return Json.error("请选择首营产品");
			}
			String[] arrSup = supplierArr.split(",");
            String[] arr = productArr.split(",");
            for(String supplierId : arrSup){
				//检查经营范围
				for(String specsId :arr){
					Json checkScopeResult =gspVerifyService.verifyOperate(clientId,supplierId,specsId);
					if(!checkScopeResult.isSuccess()){
						return checkScopeResult;
					}
				}
			}
//			for(int a =0;arr.length>0;a++){
			boolean flag = true;
			int n = 0;
			for(String specsId : arr) {
				String supplierId = arrSup[n];
				FirstBusinessApply firstBusinessApply1= new FirstBusinessApply();
				firstBusinessApply1.setSupplierId(supplierId);
				firstBusinessApply1.setSpecsId(specsId);
				firstBusinessApply1.setClientId(clientId);
				int num = firstBusinessApplyMybatisDao.selectFirstBusinessBySupplierAndProduct(firstBusinessApply1);
				if(num>0){
					flag = false;
				}
				n++;
			}
			if(!flag){
				return 	Json.error("存在同一货主 供应商与产品！  不能重复申请！");
			}
			//specsId   产品id
			int np = 0;
			for(String specsId : arr){
				String supplierId = arrSup[np];
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

//			    String[] arr = productArr.split(",");

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
				//新产品详情
				GspProductRegisterSpecs g = gspProductRegisterSpecsMybatisDao.queryById(specsId);
				//新产品注册证详情
				GspProductRegister gpr = gspProductRegisterMybatisDao.queryById(g.getProductRegisterId());
				//新供应商详情
				BasCustomer sup =basCustomerMybatisDao.queryByIdType(supplierId,Constant.CODE_CUS_TYP_VE);

				if(sup==null){
					return 	Json.error("供应商失效！");
				}
				//货主详情
				BasCustomer cli =basCustomerMybatisDao.queryByIdType(clientId,Constant.CODE_CUS_TYP_OW);
				if(cli==null){
					return 	Json.error("货主失效！");
				}
				String ProductName = "无";
				String ProductRegisterNo = "无";
				String SpecsName = "无";
				String clientName = "无";
				String supName = "无";
				String content ="无";
				if(isReType==false){
					if(g.getProductName()!=null && !"".equals(g.getProductName()) ){
						ProductName = g.getProductName();
					}
					if(g.getSpecsName()!=null && !"".equals(g.getSpecsName()) ){
						SpecsName = g.getSpecsName();
					}

					if(gpr!=null){
						if(gpr.getProductRegisterNo()!=null && !"".equals(gpr.getProductRegisterNo()) ){
							ProductRegisterNo = gpr.getProductRegisterNo();
						}
					}else{
						ProductRegisterNo = "无";
					}

					if(sup.getDescrC()!=null && !"".equals(sup.getDescrC())){
						supName = sup.getDescrC();
					}
					if(cli.getDescrC()!=null && !"".equals(cli.getDescrC())){
						clientName = cli.getDescrC();
					}
					content ="委托方:"+clientName+" 供应商:"+supName+" 产品名称:"+ProductName+" 产品代码:"+g.getProductCode()+
							" 规格:"+SpecsName+" 产品注册证:"+ProductRegisterNo;
				}else if(isReType==true){
					//发起新申请   变更内容
//					//新申请信息
					GspProductRegister newGPR= null;
					GspProductRegisterSpecs newGPRS  = gspProductRegisterSpecsMybatisDao.selectProductByCompare(specsId);
					GspProductRegisterSpecs newGPRSR =gspProductRegisterSpecsMybatisDao.queryById(specsId);//過程
					if(newGPRSR.getProductRegisterId()!=null){
						newGPR = gspProductRegisterMybatisDao.selectProductRegisterByCompare(newGPRSR.getProductRegisterId());
					}

					if(false){
						//无变更
						content = "从申请单号"+oldApplyId+"变更到申请单号"+no+" :产品无变更,  ";
						if(g.getProductName()!=null && !"".equals(g.getProductName()) ){
							ProductName = g.getProductName();
						}
						if(g.getSpecsName()!=null && !"".equals(g.getSpecsName()) ){
							SpecsName = g.getSpecsName();
						}
						if(gpr!=null){
							if(gpr.getProductRegisterNo()!=null && !"".equals(gpr.getProductRegisterNo()) ){
								ProductRegisterNo = gpr.getProductRegisterNo();
							}
						}else{
							ProductRegisterNo = "无";
						}

						if(sup.getDescrC()!=null && !"".equals(sup.getDescrC())){
							supName = sup.getDescrC();
						}
						if(cli.getDescrC()!=null && !"".equals(cli.getDescrC())){
							clientName = cli.getDescrC();
						}
						content =content+"委托方:"+clientName+" 供应商:"+supName+" 产品名称:"+ProductName+" 产品代码:"+g.getProductCode()+
								" 规格:"+SpecsName+" 产品注册证:"+ProductRegisterNo;
						;
					}else{
						CompareUtil<GspProductRegisterSpecs> compareUtil = new CompareUtil<GspProductRegisterSpecs>();
						CompareUtil<GspProductRegister> compareUtilGPR = new CompareUtil<GspProductRegister>();

						//To 需要把产品档案查出来用产品基础信息类接收 然后对比新旧信息 存入内容





						content = "从申请单号"+oldApplyId+"变更到申请单号"+no+"["+
								compareUtil.compareT(oldProduct,newGPRS,GspProductRegisterSpecs.class.getName(),GspProductRegisterSpecs.class.getSimpleName(),"")+
								compareUtilGPR.compareT(oldgpr,newGPR,GspProductRegister.class.getName(),GspProductRegister.class.getSimpleName(),"")+


								"]";
					}

				}

                firstReviewLogForm.setApplyContent(content);
				firstReviewLogService.addFirstReviewLog(firstReviewLogForm);

				np++;
			}
			return Json.success("申请成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("申请失败");
	}


	public class CompareUtil<T> {

		public List<String> compareT(T t1,T t2,String className,String simpleClassName,String type){
			List<String> list = new ArrayList<String>();
			//内容没改变直接返回
			String result ="";
			String s = "1";
			String b = "1";
			if(t1!=null){
				if(t1.equals(t2)){
					list.add(result);
					return list;
				}
			}else {
				s = "0";
			}
			if(t2==null){
				b= "0";
			}
			try {
				Class c = Class.forName(className);
				//读取t1和t2中的所有属性
				Field[] fields = c.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field field =fields[i];
					field.setAccessible(true);
					Object value1=null;
					Object value2=null;
					if("1".equals(s)){
						value1=field.get(t1);
					}
//					field.get(t1);
					if("1".equals(b)){
						value2=field.get(t2);
					}
					Date date = new Date();
					SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
					try{
						value2 = sdf.format(value2);
					}catch (Exception e){
					}
					try{
						value1 = sdf.format(value1);
					}catch (Exception e){
					}
					//判断这两个值是否相等
					if(!isValueEquals(value1,value2)){
//						Map map = new HashMap();
//						map.put("name",field.getName());
//						map.put("before",value1);
//						map.put("after",value2);
//						list.add(map);
						if("GspProductRegisterSpecs".equals(simpleClassName)){
							result = " 原产品"+GspProductRegisterSpecsChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为"+": "+(value2==null|| value2==""? "无":value2);
						}else if("GspProductRegister".equals(simpleClassName)){
							result = " 原产品注册证"+GspProductRegisterChange(field.getName())+": "+(value1==null|| value1==""? "无":value1)+
									",变更为 "+":"+(value2==null || value2==""? "无":value2);
						}

						list.add(result);
					}

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return list;
		}
		private boolean isValueEquals(Object value1, Object value2) {
			if(value1==null&&value2==null){
				return true;
			}
			if(value1==null&&value2!=null){
				return false;
			}
			if((value1==null||value1==""||value1==" ") && (value2==""||value2==null||value2==" ")){
				return true;
			}
			if(value1.equals(value2)){
				return true;
			}
			return false;
		}
		public String GspProductRegisterChange(String zd){
			if("productRegisterNo".equals(zd))return "产品注册证/备案";
			if("approvalDepartment".equals(zd))return "审批部门";
			if("productNameMain".equals(zd))return "产品名称";
			if("approveDate".equals(zd))return "批准日期";
			if("classifyId".equals(zd))return "管理分类";
			if("productRegisterExpiryDate".equals(zd))return "有效期至";
			if("productRegisterVersion".equals(zd))return "注册证/备案版本";
			if("attachmentUrl".equals(zd))return "注册证/备案附件";
			if("classifyCatalog".equals(zd))return "分类目录";
			if("structureAndComposition".equals(zd))return "结构及组成";
			if("productionAddress".equals(zd))return "产地";
			if("applyScope".equals(zd))return "适用范围";
			if("storageConditions".equals(zd))return "储存条件";
			if("expectUse".equals(zd))return "预期用途";
			if("transportConditionMain".equals(zd))return "产品运输条件";
			if("mainPart".equals(zd))return "主要组成部分";
			if("enterpriseId".equals(zd))return "注册人名称/生产企业";
			if("productRegsiterUrl".equals(zd))return "附件";
			if("licenseOrRecordNol".equals(zd))return "生产许可证号/备案号";
			if("otherContent".equals(zd))return "其他内容";
			if("productRegisterAddress".equals(zd))return "注册人住所";
			if("remark".equals(zd))return "备注";
			if("productProductionAddress".equals(zd))return "生产地址";
			if("agentName".equals(zd))return "代理人名称";
			if("agentAddress".equals(zd))return "代理人住所";
			return "";
		}
		public String GspProductRegisterSpecsChange(String zd){
			if("medicalDeviceMark".equals(zd))return "医疗器械标志";
			if("productName".equals(zd))return "产品名称";
			if("productRegisterNo".equals(zd))return "注册证编号";
			if("productRemark".equals(zd))return "产品描述";
			if("productCode".equals(zd))return "产品代码";
			if("unit".equals(zd))return "单位";
			if("specsName".equals(zd))return "规格";
			if("packagingUnit".equals(zd))return "包装单位";
			if("productModel".equals(zd))return "型号";
			if("packingRequire".equals(zd))return "包装要求";
			if("packingUnit".equals(zd))return "包装规格";
			if("llong".equals(zd))return "长";
			if("transportCondition".equals(zd))return "运输条件";
			if("wide".equals(zd))return "宽";
			if("storageCondition".equals(zd))return "储存条件";
			if("hight".equals(zd))return "高";
			if("enterpriseName".equals(zd))return "生产企业";
			if("wight".equals(zd))return "重量";
			if("licenseOrRecordNo".equals(zd))return "生产许可证号/备案号";
			if("barCode".equals(zd))return "商品条码";
			if("productionAddress".equals(zd))return "产地";
			if("alternatName1".equals(zd))return "自赋码1";
			if("isDoublec".equals(zd))return "双证";
			if("alternatName2".equals(zd))return "自赋码2";
			if("isCertificate".equals(zd))return "产品合格证";
			if("alternatName3".equals(zd))return "自赋码3";
			if("attacheCardCategory".equals(zd))return "附卡类别";
			if("alternatName4".equals(zd))return "自赋码4";
			if("coldHainMark".equals(zd))return "冷链标志";
			if("alternatName5".equals(zd))return "自赋码5";
			if("sterilizationMarkers".equals(zd))return "灭菌标志";
			if("maintenanceCycle".equals(zd))return "养护周期(天)";

			return "";
		}

	}


	public Json editApply(String id,String clientId,String supplierArr,String productArr,String productLine){



		if("".equals(clientId)){
			return Json.error("请选择委托客户");
		}

		/*if("".equals(supplierId)){
			return Json.error("请选择供应商");
		}*/

		if("".equals(productArr)){
			return Json.error("请选择首营产品");
		}

		Json checkScopeResult =gspVerifyService.verifyOperate(clientId,supplierArr,productArr);
		if(!checkScopeResult.isSuccess()){
			return checkScopeResult;
		}


		FirstBusinessApply firstBusinessApplyC =  firstBusinessApplyMybatisDao.queryById(id);
		if(clientId.equals(firstBusinessApplyC.getClientId()) && supplierArr.equals(firstBusinessApplyC.getSupplierId()) && productArr.equals(firstBusinessApplyC.getSpecsId())){

		}else {
			boolean flag = true;
			FirstBusinessApply firstBusinessApply1= new FirstBusinessApply();
			firstBusinessApply1.setSupplierId(supplierArr);
			firstBusinessApply1.setSpecsId(productArr);
			firstBusinessApply1.setClientId(clientId);
			int num = firstBusinessApplyMybatisDao.selectFirstBusinessBySupplierAndProduct(firstBusinessApply1);
			if(num>0){
				flag = false;
			}

			if(!flag){
				return 	Json.error("存在同一货主 供应商与产品！  不能重复申请！");
			}
		}
//		String[] arrSup = supplierArr.split(",");
//		String[] arr = productArr.split(",");

//		if(arrSup.length>1){
//
//		}



		try{
			FirstBusinessApply firstBusinessApply = new FirstBusinessApply();
			firstBusinessApply.setApplyId(id);
			firstBusinessApply.setClientId(clientId);
			firstBusinessApply.setSupplierId(supplierArr);
			firstBusinessApply.setProductline(productLine);
			firstBusinessApply.setEditDate(new Date());
			firstBusinessApply.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			firstBusinessApplyMybatisDao.updateBySelective(firstBusinessApply);




			FirstBusinessProductApply firstBusinessProductApply = new FirstBusinessProductApply();
			firstBusinessProductApply.setProductApplyId(id);
			firstBusinessProductApply.setApplyId(id);
			firstBusinessProductApply.setSpecsId(productArr);
			firstBusinessProductApply.setEditDate(new Date());
			firstBusinessProductApply.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			firstBusinessProductApplyMybatisDao.updateBySelective(firstBusinessProductApply);


//			firstBusinessApplyMybatisDao.delete(id);
//			firstBusinessProductApplyMybatisDao.delete(id);
//			firstReviewLogMybatisDao.delete(id);   //删除记录？

//			addApply(clientId,supplierArr,productArr,productLine);

//			FirstBusinessApply oldApply = firstBusinessApplyMybatisDao.queryById(id);
//			if(oldApply!=null){
//
//				if(!oldApply.getFirstState().equals(Constant.CODE_CATALOG_FIRSTSTATE_NEW)){
//					return Json.error("不是新建状态的申请单无法修改");
//				}
//
//				FirstBusinessApply update = new FirstBusinessApply();
//				update.setApplyId(id);
//				update.setIsUse(Constant.IS_USE_NO);
//				firstBusinessApplyMybatisDao.updateBySelective(update);
//
//				MybatisCriteria mybatisCriteria = new MybatisCriteria();
//				FirstBusinessProductApplyQuery query = new FirstBusinessProductApplyQuery();
//				query.setIsUse(Constant.IS_USE_YES);
//				query.setApplyId(id);
//				mybatisCriteria.setCondition(query);
//				List<FirstBusinessProductApplyResult> list = firstBusinessProductApplyMybatisDao.queryPageList(mybatisCriteria);
//
//				for(FirstBusinessProductApplyResult result : list){
//					FirstBusinessProductApply firstUpdate = new FirstBusinessProductApply();
//					firstUpdate.setProductApplyId(result.getProductApplyId());
//					firstUpdate.setIsUse(Constant.IS_USE_NO);
//					firstBusinessProductApplyMybatisDao.updateBySelective(firstUpdate);
//				}
//
//
				return Json.success("资料更新成功");
////				return addApply(clientId,supplierArr,productArr,productLine);
//			}
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("申请失败");
	}

	public Json addConfirmApply(String id){

        Json json = new Json();
        String[] arrId = id.split(",");

		try{

            for(String DelId : arrId){
//                FirstBusinessApply firstBusinessApply = firstBusinessApplyMybatisDao.queryById(DelId);
//                if(firstBusinessApply != null){
//                    firstBusinessApplyMybatisDao.delete(firstBusinessApply);
//                }
                FirstBusinessApply firstBusinessApply = new FirstBusinessApply();
                firstBusinessApply.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING);
                firstBusinessApply.setApplyId(DelId);
                firstBusinessApplyMybatisDao.updateBySelective(firstBusinessApply);

                //更新申请记录
//                firstReviewLogService.updateFirstReviewByNo(DelId,Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING);

                FirstReviewLog f = new FirstReviewLog();
                f.setReviewTypeId(DelId);
                f.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING);
                f.setCheckDateQc(null);
                f.setCheckIdQc("");
                f.setCheckRemarkQc("");
                f.setCheckIdHead("");
                f.setCheckDateHead(null);
                f.setCheckRemarkHead("");

                firstReviewLogMybatisDao.updateBytiJIAOSHENH(f);



            }


			return Json.error("操作成功");

		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("操作失败");
	}


	@Transactional
	public Json addReApply(String id){
		try{
            String[] arrId = id.split(",");
            for(String DelId : arrId){
                Json json = queryFirstBusinessApply(DelId);
                if(!json.isSuccess() || json.getObj() == null){
                    return Json.error("没有查询到对应的申请单号");
                }
            }
			//重新申请


            boolean type = true;
            String content = "";
            for(String DelId : arrId) {
                //TODO 失效已下发的数据
                dataPublishService.cancelData(DelId);

                //修改原数据为失效
                FirstBusinessApply firstBusinessApply = new FirstBusinessApply();
                firstBusinessApply.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
                firstBusinessApply.setApplyId(DelId);
                firstBusinessApply.setIsUse(Constant.IS_USE_NO);
                firstBusinessApplyMybatisDao.updateBySelective(firstBusinessApply);
                //更新申请记录
//                firstReviewLogService.updateFirstReviewByNo(DelId, Constant.CODE_CATALOG_CHECKSTATE_FAIL);


                //重新插入单据
                FirstBusinessApply newApply = firstBusinessApplyMybatisDao.queryById(DelId);

                //获取产品最新的产品id
				GspProductRegisterSpecs  gprs =gspProductRegisterSpecsMybatisDao.selectNewBySpecsId(newApply.getSpecsId());
				if(gprs==null){
					return Json.error("产品已失效");
				}
				//获取供应商最新的供应商id
				BasCustomer s =basCustomerMybatisDao.selectNewBySupplier(newApply.getSupplierId());
				if(s==null){
					return Json.error("供应商已失效");
				}


                Json result = firstBusinessProductApplyService.getListByApplyIdNoUse(DelId);

                if (result.isSuccess()) {
                    List<FirstBusinessProductApply> list = (List<FirstBusinessProductApply>) result.getObj();
                    if (list != null && list.size() > 0) {
//					List<String> arrlist = new ArrayList<>();
//					for(FirstBusinessProductApply f : list){
//						arrlist.add(f.getSpecsId());
//					}


                        Json result11=   addApply(newApply.getClientId(), s.getCustomerid(), gprs.getSpecsId(), newApply.getProductline(),true,DelId);
                        if(!result11.isSuccess()){
                            content = content+"  单号"+DelId+" "+ result11.getMsg();
                            type =false;
                        }
                    }
                }
                if(!type){
                    return Json.error(content);
                }
            }


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

	/**
	 * 更新申请中的产品申请单供应商编号
	 * @param supplerId
	 * @return
	 */
	public Json updateCheckingApplySupplierNo(String supplerId){
		Long sum = firstBusinessApplyMybatisDao.updateCheckingApplySupplierNo(supplerId);
		if(sum == 0){
			return Json.error("更新失败");
		}
		return Json.success("更新成功");
	}

	/**
	 * 更新申请中的产品申请单产品基础信息编号
	 * @param clientId
	 * @return

	public Json updateCheckingApplySpecId(String specId){
		Long sum = firstBusinessApplyMybatisDao.updateCheckingApplySpecId(specId);
		if(sum == 0){
			return Json.error("更新失败");
		}
		return Json.success("更新成功");
	}*/

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
        if("1".equals(spec.getMedicalDeviceMark())){
            List<GspOperateDetailVO> registerDetailVo = gspOperateDetailService.queryOperateDetailByLicense(spec.getProductRegisterId());
            if(registerDetailVo == null || registerDetailVo.size() == 0){
                return Json.error("产品注册证没有选择经营范围");
            }
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