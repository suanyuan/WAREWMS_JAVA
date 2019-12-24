package com.wms.service;

import com.alibaba.fastjson.JSONArray;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.SfcRole;
import com.wms.query.BasCustomerQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.BasCustomerVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasCustomerForm;
import com.wms.vo.form.GspReceivingForm;
import com.wms.vo.form.GspSupplierForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("basCustomerService")
public class BasCustomerService extends BaseService {

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

	@Autowired
	private BasSkuHistoryMybatisDao basSkuHistoryMybatisDao;

	@Autowired
	private GspReceivingAddressMybatisDao gspReceivingAddressMybatisDao;

	@Autowired
	private GspReceivingMybatisDao gspReceivingMybatisDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;

	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
	@Autowired
	private BasCustomerHistoryMybatisDao basCustomerHistoryMybatisDao;
	@Autowired
	private GspSupplierMybatisDao gspSupplierMybatisDao;
	@Autowired
	private GspEnterpriseInfoService gspEnterpriseInfoService;
    @Autowired
    private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;
    @Autowired
    private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;

    @Autowired
    private GspFirstRecordMybatisDao gspFirstRecordMybatisDao;
    @Autowired
    private GspSecondRecordMybatisDao gspSecondRecordMybatisDao;
    @Autowired
    private GspMedicalRecordMybatisDao gspMedicalRecordMybatisDao;
	@Autowired
	private SfcRoleMybatisDao sfcRoleMybatisDao;

	public EasyuiDatagrid<BasCustomerVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCustomerQuery query) {
		EasyuiDatagrid<BasCustomerVO> datagrid = new EasyuiDatagrid<BasCustomerVO>();


		query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());

		//登录用户角色是货主就只显示该货主的数据
		List<SfcRole> sfcUsersList =sfcRoleMybatisDao.queryRoleByID(SfcUserLoginUtil.getLoginUser().getId());
		for (SfcRole sfcRole:sfcUsersList){
			if(sfcRole.getRoleName().equals("货主") && query.getCustomerType().equals("OW")){
				query.setCustomerid(SfcUserLoginUtil.getLoginUser().getId());
			}
			if(sfcRole.getRoleName().equals("货主") && query.getCustomerType().equals("VE")){
                BasCustomerVO basCustomerVO = null;
                List<BasCustomerVO> basCustomerVOList = new ArrayList<BasCustomerVO>();
                List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryListByCustomerid(SfcUserLoginUtil.getLoginUser().getId());
                for(BasCustomer basCustomer:basCustomerList){
                    basCustomerVO = new BasCustomerVO();
                    basCustomerVO.setCustomerType(basCustomer.getCustomerType());
                    basCustomerVO.setCustomerid(basCustomer.getCustomerid());
                    basCustomerVO.setDescrC(basCustomer.getDescrC());
                    basCustomerVO.setDescrE(basCustomer.getDescrE());
                    basCustomerVO.setActiveFlag(basCustomer.getActiveFlag());
                    basCustomerVOList.add(basCustomerVO);
                }
                Long total  = Long.parseLong(basCustomerList.size()+"");
                datagrid.setTotal(total);
                datagrid.setRows(basCustomerVOList);
                return datagrid;
            }
		}

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		if(query.getIdList()!=null&&query.getIdList()!="" ){
			List<String> enterpriseIdList = jsonToList(query.getIdList(),String.class);
			mybatisCriteria.setIdList(enterpriseIdList);
		}
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		mybatisCriteria.setOrderByClause("addtime desc");

		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryByPageList(mybatisCriteria);
		BasCustomerVO basCustomerVO = null;
		List<BasCustomerVO> basCustomerVOList = new ArrayList<BasCustomerVO>();
		for (BasCustomer basCustomer : basCustomerList) {
			basCustomerVO = new BasCustomerVO();
			BeanUtils.copyProperties(basCustomer, basCustomerVO);


			GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(basCustomer.getEnterpriseId());
			if (gspEnterpriseInfo != null) {
				basCustomerVO.setEnterpriseName(gspEnterpriseInfo.getEnterpriseName());
				basCustomerVO.setShorthandName(gspEnterpriseInfo.getShorthandName());
				basCustomerVO.setEnterpriseNo(gspEnterpriseInfo.getEnterpriseNo());
				basCustomerVO.setContacts(gspEnterpriseInfo.getContacts());
				basCustomerVO.setContactsPhone(gspEnterpriseInfo.getContactsPhone());
				basCustomerVO.setEnterpriseType(gspEnterpriseInfo.getEnterpriseType());
				basCustomerVO.setRemark(gspEnterpriseInfo.getRemark());
			}
			//供应商对应 所有货主
			if(Constant.CODE_CUS_TYP_VE.equals(basCustomerVO.getCustomerType())){
				List<GspSupplier> supList = gspSupplierMybatisDao.queryListByEnterpriseId(basCustomerVO.getEnterpriseId());
				String content = "";
				for(GspSupplier sup:supList){
				    if(sup.getCustomerName()!=null){
                        content =content + sup.getCustomerName()+",";
                    }
				}
				basCustomerVO.setAllClient(content);
			}else{
				basCustomerVO.setAllClient("");
			}
			//收货单位 对应 所有货主
			if(Constant.CODE_CUS_TYP_CO.equals(basCustomerVO.getCustomerType())){
				List<GspReceiving> clientList = gspReceivingMybatisDao.querySHTGByEnterpriseId(basCustomerVO.getEnterpriseId());
				String content = "";
				for(GspReceiving sup:clientList){
					if(sup.getClientId()!=null){
						content =content + sup.getClientId()+",";
					}
				}
				basCustomerVO.setReceivingAllClient(content);
			}else{
				basCustomerVO.setReceivingAllClient("");
			}



			basCustomerVOList.add(basCustomerVO);
		}
		Long total  = Long.parseLong(basCustomerList.size()+"");
		datagrid.setTotal(total);
		datagrid.setRows(basCustomerVOList);
		return datagrid;
	}

    public EasyuiDatagrid<BasCustomerVO> getPagedDatagridByCustomer(EasyuiDatagridPager pager, BasCustomerQuery query) {
        EasyuiDatagrid<BasCustomerVO> datagrid = new EasyuiDatagrid<BasCustomerVO>();

        query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(query);
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));

        mybatisCriteria.setOrderByClause("addtime desc");
        List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryByPageListByCustomer(mybatisCriteria);
        BasCustomerVO basCustomerVO = null;
        List<BasCustomerVO> basCustomerVOList = new ArrayList<BasCustomerVO>();
        for (BasCustomer basCustomer : basCustomerList) {
            basCustomerVO = new BasCustomerVO();
            BeanUtils.copyProperties(basCustomer, basCustomerVO);
            GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(basCustomer.getEnterpriseId());
            if (gspEnterpriseInfo != null) {
                System.out.println();
                basCustomerVO.setEnterpriseName(gspEnterpriseInfo.getEnterpriseName());
                basCustomerVO.setShorthandName(gspEnterpriseInfo.getShorthandName());
                basCustomerVO.setEnterpriseNo(gspEnterpriseInfo.getEnterpriseNo());
                basCustomerVO.setContacts(gspEnterpriseInfo.getContacts());
                basCustomerVO.setContactsPhone(gspEnterpriseInfo.getContactsPhone());
                basCustomerVO.setEnterpriseType(gspEnterpriseInfo.getEnterpriseType());
                basCustomerVO.setRemark(gspEnterpriseInfo.getRemark());
//
            }


            basCustomerVOList.add(basCustomerVO);
        }
        datagrid.setTotal((long) basCustomerMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(basCustomerVOList);
        return datagrid;
    }




	/*public Json addBasCustomer(BasCustomerForm basCustomerForm) throws Exception {
		Json json = new Json();
		BasCustomer basCustomer = new BasCustomer();
		BeanUtils.copyProperties(basCustomerForm, basCustomer);
		basCustomerMybatisDao.add(basCustomer);
		json.setSuccess(true);
		return json;
	}*/


	//单独收货单位的无需审核的下发
	@Transactional
	public Json addBasCustomer(BasCustomerForm basCustomerForm) throws Exception {
		Json json = null;
		try {
			StringBuilder resultMsg = new StringBuilder();
			this.validateCustomer(basCustomerForm, resultMsg);// 验证客户是否存在
			json = new Json();
			if (resultMsg.length() == 0) {


				BasCustomer basCustomer = new BasCustomer();

				basCustomer.setCustomerType("CO");
//				BasCustomer basCustomer1 = new BasCustomer();
//				basCustomer1.setEnterpriseId(basCustomerForm.getEnterpriseId());
//				basCustomer1.setCustomerType("CO");
//				basCustomer1 = basCustomerMybatisDao.queryByenterId(basCustomer1);//俩条数据不能有一样的企业id
//
				GspReceivingForm gspReceivingForm = new GspReceivingForm();
				gspReceivingForm.setEnterpriseId(basCustomerForm.getEnterpriseId());
				gspReceivingForm.setClientId(basCustomerForm.getClientId());
				int num1 = gspReceivingMybatisDao.selectByClientIdAndReceiving(gspReceivingForm);
				if(num1>0){
					return Json.error("同一货主和收获单位！不能重复申请！");
				}

				basCustomer.setEnterpriseId(basCustomerForm.getEnterpriseId());
				int num = basCustomerMybatisDao.selectBySelective(basCustomer);
				if(num!=0){
					basCustomerMybatisDao.delete(basCustomer);
				}



				 basCustomer = new BasCustomer();
				String number = commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				BeanUtils.copyProperties(basCustomerForm,basCustomer);

				basCustomer.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
				basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
				basCustomer.setAddtime(new Date());
				basCustomer.setEdittime(new Date());


				//插入一条首营申请日志记录
				FirstReviewLog firstReviewLog = new FirstReviewLog();
				firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstReviewLog.setReviewTypeId(number);
				firstReviewLog.setCreateDate(new Date());
				firstReviewLog.setEditDate(new Date());
				firstReviewLog.setApplyContent("无需审核");
				firstReviewLog.setApplyState("40");
				firstReviewLog.setReviewId(RandomUtil.getUUID());



				GspReceiving gspReceiving = new GspReceiving();
				BeanUtils.copyProperties(basCustomerForm,gspReceiving);



				gspReceiving.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_PASS);
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setReceivingId(number);
				String id ="";
				List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryByReceivingId(id);
				if (gspReceivingAddressList != null && gspReceivingAddressList.size()!=0) {
					for (GspReceivingAddress gspReceivingAddress : gspReceivingAddressList){
						if (gspReceivingAddress.getIsDefault()=="1"){
						basCustomer.setReceivingAddressId(gspReceivingAddress.getReceivingAddressId());
						}
						gspReceivingAddress.setReceivingId(gspReceiving.getReceivingId());
						gspReceivingAddressMybatisDao.updateBySelective(gspReceivingAddress);
					}

				}
                //gspReceivingMybatisDao.queryById()
//                 GspReceiving gspReceiving1 = new GspReceiving();
//                gspReceiving1.setEnterpriseId(basCustomerForm.getEnterpriseId());


				gspReceivingMybatisDao.add(gspReceiving);

				firstReviewLogMybatisDao.add(firstReviewLog);
				GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoService.getGspEnterpriseInfo(basCustomer.getEnterpriseId());
				basCustomer.setBankaccount(number);
				basCustomer.setDescrC(gspEnterpriseInfo.getEnterpriseName());
                basCustomer.setCustomerid(commonService.generateSeq(Constant.BASRECNO, SfcUserLoginUtil.getLoginUser().getId()));
                basCustomer.setActiveFlag(Constant.IS_USE_YES);
//                if(basCustomerForm.getIsUse()=="1"){
					basCustomer.setBillclass(Constant.CODE_CATALOG_FIRSTSTATE_PASS);
//				}else if(basCustomerForm.getIsUse()=="0"){
//					basCustomer.setBillclass(Constant.CODE_CATALOG_FIRSTSTATE_USELESS);
//				}
                basCustomerMybatisDao.add(basCustomer);

				json.setSuccess(true);
				json.setMsg("资料处理成功！");
			} else {
				json.setSuccess(false);
				json.setMsg(resultMsg.toString());
				return json;
			}
		} catch (BeansException e) {
			throw new Exception("系统忙！");
		}
		return json;
	}


	public Json supplierAddCustomer(GspSupplierForm gspSupplierForm) {
		Json json = new Json();
		BasCustomer basCustomer = new BasCustomer();
		basCustomer.setCustomerType("VE");
		basCustomer.setEnterpriseId(gspSupplierForm.getEnterpriseId());
		int num = basCustomerMybatisDao.selectBySelective(basCustomer);
		if(num!=0){
			basCustomerMybatisDao.delete(basCustomer);
		}

		basCustomer = new BasCustomer();
		basCustomer.setCustomerType("VE");
		basCustomer.setEnterpriseId(gspSupplierForm.getEnterpriseId());
		basCustomer.setCustomerid(commonService.generateSeq(Constant.BASSUPNO, SfcUserLoginUtil.getLoginUser().getId()));
		basCustomer.setOperateType(gspSupplierForm.getEnterpriseType());
		basCustomer.setActiveFlag("1");
		basCustomer.setDescrC(commonService.generateSeq(Constant.BASSUPNO, SfcUserLoginUtil.getLoginUser().getId()));
		basCustomer.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomer.setAddtime(new Date());
		basCustomer.setEdittime(new Date());
		basCustomerMybatisDao.add(basCustomer);



		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json clientAddCustomer(BasCustomerForm basCustomerForm,String flag) {
		//try{
			boolean fff = true;
			int supNum =0;
			Json json = new Json();
			BasCustomer basCustomerQuery = new BasCustomer();
			basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());
			basCustomerQuery.setCustomerid(basCustomerForm.getCustomerid());
//			basCustomerQuery.setEnterpriseId(basCustomerForm.getEnterpriseId());
			BasCustomer basCustomerHistory = new BasCustomer();
			if("Supplier".equals(flag) ){
				GspEnterpriseInfo g =  gspEnterpriseInfoMybatisDao.queryById(basCustomerForm.getEnterpriseId());
				basCustomerQuery.setDescrC(g.getEnterpriseName());
				basCustomerHistory = basCustomerMybatisDao.selectSupplierByIdTypeActiveFlag(basCustomerQuery);
				supNum = gspSupplierMybatisDao.countByEnterpriseIdAnd40(basCustomerForm.getEnterpriseId());

			}else if("Client".equals(flag)){
				basCustomerHistory = basCustomerMybatisDao.selectByIdTypeActiveFlag(basCustomerQuery);
			}else if("Receving".equals(flag)){
				GspEnterpriseInfo g =  gspEnterpriseInfoMybatisDao.queryById(basCustomerForm.getEnterpriseId());
				basCustomerQuery.setDescrC(g.getEnterpriseName());
				basCustomerHistory = basCustomerMybatisDao.selectSupplierByIdTypeActiveFlag(basCustomerQuery);
//				supNum = gspSupplierMybatisDao.countByEnterpriseIdAnd40(basCustomerForm.getEnterpriseId());
			}
			//int num = basCustomerMybatisDao.selectBySelective(basCustomerQuery);

			if(basCustomerHistory!=null){
				//BasCustomer basCustomer = new BasCustomer();
				//BeanUtils.copyProperties(basCustomerHistory,basSkuHistory);
				BasCustomerHistory basCustomerHistoryQ = new BasCustomerHistory();
				BeanUtils.copyProperties(basCustomerHistory,basCustomerHistoryQ);
				basCustomerHistoryQ.setClientContent(basCustomerHistory.getClientContent());
				basCustomerHistoryQ.setClientEndDate(basCustomerHistory.getClientEndDate());
				basCustomerHistoryQ.setClientStartDate(basCustomerHistory.getClientStartDate());
				basCustomerHistoryQ.setClientTerm(basCustomerHistory.getClientTerm());
				basCustomerHistoryQ.setContractUrl(basCustomerHistory.getContractUrl());

				//TODO 插入history.add(basSkuHistory);
                //相同供应商相同货主第一次下发不删  第二次删
//                if("Supplier".equals(flag)){
//                    if(supNum==1){
				basCustomerHistoryMybatisDao.add(basCustomerHistoryQ);
				basCustomerMybatisDao.deleteBascustomerByCustomerID(basCustomerHistory.getCustomerid(),basCustomerHistory.getCustomerType());
//                    }
//                }else{
//					basCustomerHistoryMybatisDao.add(basCustomerHistoryQ);
//                    basCustomerMybatisDao.deleteBascustomerByCustomerID(basCustomerHistory.getCustomerid(),basCustomerHistory.getCustomerType());
//
//                }

			}

			BasCustomer basCustomer = new BasCustomer();
			BeanUtils.copyProperties(basCustomerForm,basCustomer);
		/*basCustomer.setCustomerType(basCustomerForm.getCustomerType());
		basCustomer.setEnterpriseId(basCustomerForm.getEnterpriseId());
		basCustomer.setCustomerid(basCustomerForm.getCustomerid());
		basCustomer.setOperateType(basCustomerForm.getOperateType());
		basCustomer.setActiveFlag(basCustomerForm.getActiveFlag());
		basCustomer.setDescrC(basCustomerForm.getDescrC());*/
//			basCustomer.setSupContractNo();
			basCustomer.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			basCustomer.setAddtime(new Date());
			basCustomer.setEdittime(new Date());


			basCustomerMybatisDao.add(basCustomer);


			json.setSuccess(true);
			json.setMsg("资料处理成功！");
			return json;
		/*}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}*/
	}




	public Json editBasCustomer(BasCustomerForm basCustomerForm) {
		Json json = new Json();
		BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
		basCustomerQuery.setCustomerid(basCustomerForm.getCustomerid());
		basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());
		basCustomerQuery.setEnterpriseId(basCustomerForm.getEnterpriseId());
		BasCustomer basCustomer = basCustomerMybatisDao.queryById(basCustomerQuery);
		BeanUtils.copyProperties(basCustomerForm, basCustomer);
		
		basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		//换证customerId 给错
		basCustomerMybatisDao.updateBySelective(basCustomer);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json editBasCustomerByEnterpriseId(BasCustomerForm basCustomerForm) {
		Json json = new Json();
		BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
//		basCustomerQuery.setCustomerid(basCustomerForm.getCustomerid());
		basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());
		basCustomerQuery.setEnterpriseId(basCustomerForm.getEnterpriseId());
//		System.out.println();
//		BasCustomer basCustomer = basCustomerMybatisDao.queryById(basCustomerQuery);
//		String customerId = basCustomer.getCustomerid();
		BasCustomer basCustomer = basCustomerMybatisDao.querySupplierByBankaccount(basCustomerForm.getBankaccount());
		if(basCustomer==null){
			return Json.error("客户不存在");
		}
		String customerId = basCustomer.getCustomerid();
		BeanUtils.copyProperties(basCustomerForm, basCustomer);
		basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomer.setCustomerid(customerId);
		//换证customerId 给错

		basCustomerMybatisDao.updateBySelective(basCustomer);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json editBasCustomerSupByEnterpriseId(BasCustomerForm basCustomerForm) {
		Json json = new Json();
        int num=0;
		BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
		basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());
		basCustomerQuery.setEnterpriseId(basCustomerForm.getEnterpriseId());
//		BasCustomer basCustomer = basCustomerMybatisDao.querySupplierByBankaccount(basCustomerForm.getBankaccount());
		BasCustomer basCustomer = basCustomerMybatisDao.queryByenterId(basCustomerQuery);


        if(basCustomerForm.getBankaccount().indexOf(Constant.APLSUPNO)!=-1){
            //供应商多货主
             num = gspSupplierMybatisDao.countByEnterpriseIdAnd40(basCustomerForm.getEnterpriseId());
        }else if(basCustomerForm.getBankaccount().indexOf(Constant.APLRECNO)!=-1){
            //收货单位多货主
             num = gspReceivingMybatisDao.countByEnterpriseIdAnd40(basCustomerForm.getEnterpriseId());
        }

		String customerId = basCustomer.getCustomerid();
		BeanUtils.copyProperties(basCustomerForm, basCustomer);
		basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomer.setCustomerid(customerId);
		//换证customerId 给错
		if(num==0){
			basCustomerMybatisDao.updateBySelective(basCustomer);
		}
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}


	public Json editBasCustomerByCustomerId(BasCustomerForm basCustomerForm) {
		Json json = new Json();
		BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
		basCustomerQuery.setCustomerid(basCustomerForm.getCustomerid());
		basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());

		BasCustomer basCustomer = basCustomerMybatisDao.queryByIdType(basCustomerForm.getCustomerid(),basCustomerForm.getCustomerType());
        if(basCustomer==null){
            json.setSuccess(true);
            json.setMsg("资料已经失效！");
            return json;
        }
		BeanUtils.copyProperties(basCustomerForm, basCustomer);

		basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		basCustomerMybatisDao.updateBySelective(basCustomer);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}
	
	private void validateCustomer(BasCustomerForm basCustomerForm, StringBuilder resultMsg) {
		BasCustomerQuery basCustomerQuery = new BasCustomerQuery();
		basCustomerQuery.setCustomerid(basCustomerForm.getCustomerid());
		basCustomerQuery.setCustomerType(basCustomerForm.getCustomerType());
		BasCustomer basCustomer = basCustomerMybatisDao.queryById(basCustomerQuery);
		if(basCustomer != null){
			resultMsg.append("客户代码：").append(basCustomerForm.getCustomerid())
					 .append("，客户类型：").append(basCustomerForm.getCustomerType()).append("，重复").append(" ");
		}
	}	

	public Json deleteBasCustomer(String enterpriseId, String customertype) {
		Json json = new Json();
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setEnterpriseId(enterpriseId);
		customerQuery.setCustomerType(customertype);

		BasCustomer basCustomer = basCustomerMybatisDao.queryById(customerQuery);
		if (basCustomer != null) {
			basCustomerMybatisDao.delete(basCustomer);
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
		}
		return json;
	}


	public BasCustomer selectCustomer(String enterpriseId, String customertype) {


		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setEnterpriseId(enterpriseId);
		customerQuery.setCustomerType(customertype);

		BasCustomer basCustomer = null;
		try {
			basCustomer = basCustomerMybatisDao.queryById(customerQuery);
		} catch (Exception e) {
			return null;
		}


		return basCustomer;
	}

	public BasCustomer selectCustomerById(String customerID, String customertype) {


		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setCustomerid(customerID);
		customerQuery.setCustomerType(customertype);
		customerQuery.setActiveFlag(Constant.IS_USE_YES);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(customerQuery);


		List<BasCustomer> list = basCustomerMybatisDao.queryByList(criteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public BasCustomer selectCustomerById(String customerID, String customertype,String isUse) {


		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setCustomerid(customerID);
		customerQuery.setCustomerType(customertype);
		if(!StringUtils.isEmpty(isUse)){
			customerQuery.setActiveFlag(isUse);
		}
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(customerQuery);


		List<BasCustomer> list = basCustomerMybatisDao.queryByList(criteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Json goonBasCustomer(String enterpriseId, String customertype) {
		Json json = new Json();
		BasCustomerQuery customerQuery = new BasCustomerQuery();
		customerQuery.setEnterpriseId(enterpriseId);
		customerQuery.setCustomerType(customertype);
		BasCustomer basCustomer = basCustomerMybatisDao.queryById(customerQuery);
		if (basCustomer != null) {
			//增加一个效验企业信息是否有效
			GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryByEnterpriseId(basCustomer.getEnterpriseId());
			if (gspEnterpriseInfo == null){
				json.setSuccess(false);
				json.setMsg("资料处理失败-该企业信息已经失效!");
			}else{
				basCustomerMybatisDao.goon(basCustomer);
				json.setSuccess(true);
				json.setMsg("资料处理成功！");

			}
		}
		return json;
	}
	public List<EasyuiCombobox> getCustomerTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryCustomerTypeByAll();
		if(basCustomerList != null && basCustomerList.size() > 0){
			for(BasCustomer basCustomer : basCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCustomer.getCustomerType());
				combobox.setValue(basCustomer.getCustomerTypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	public List<EasyuiCombobox> getCustomerNameCombobox(String type) {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		if(type==null ){
			type="";
		}
		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryCustomerNameByAll(type);
		if(basCustomerList != null && basCustomerList.size() > 0){
			for(BasCustomer basCustomer : basCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCustomer.getDescrC());
				combobox.setValue(basCustomer.getDescrC());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	public List<EasyuiCombobox> getCustomerNameComboboxAA(String type) {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		if(type==null ){
			type="";
		}
		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryCustomerNameByAll(type);
		if(basCustomerList != null && basCustomerList.size() > 0){
			for(BasCustomer basCustomer : basCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCustomer.getCustomerid());
				combobox.setValue(basCustomer.getDescrC());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}


	public List<EasyuiCombobox> getOperateTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryOperateTypeByAll();
		if(basCustomerList != null && basCustomerList.size() > 0){
			for(BasCustomer basCustomer : basCustomerList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCustomer.getOperateType());
				combobox.setValue(basCustomer.getOperateTypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
	/**
	 * 获取收货地址基础信息
	 * @param enterpriseId 企业信息流水号
	 * @return
	 */
	public Json getReceivingAddressInfo(String enterpriseId, String receivingAddressId){
		 GspReceivingAddress gspReceivingAddress = basCustomerMybatisDao.getReceivingAddressInfo(receivingAddressId);
		if(gspReceivingAddress == null){
			return Json.error("收货地址信息不存在！");
		}
		return Json.success("",gspReceivingAddress);
	}

	public void exportBasCustomerDataToExcel(HttpServletResponse response, BasCustomerForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());

		BasCustomerForm basCustomerForm = new BasCustomerForm();

		basCustomerForm.setCustomerType(form.getCustomerType());
		basCustomerForm.setCustomerid(form.getCustomerid());
		basCustomerForm.setDescrC(form.getDescrC());
		basCustomerForm.setActiveFlag(form.getActiveFlag());
		basCustomerForm.setEnterpriseNo(form.getEnterpriseNo());
		try {
			BasCustomerQuery query = new BasCustomerQuery();
			//权限控制
			query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			com.wms.utils.BeanUtils.copyProperties(basCustomerForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "客户档案查询结果";
			// excel要导出的数据
			List<BasCustomer> basCustomerList = basCustomerMybatisDao.queryByList(mybatisCriteria); //要权限！james
			EasyuiDatagridPager page = new EasyuiDatagridPager();
			EasyuiDatagrid<BasCustomerVO> pagedDatagrid = getPagedDatagrid(page, query);
			List<BasCustomerVO> basCustomerVOList = pagedDatagrid.getRows();


			// 导出
			if (basCustomerVOList == null || basCustomerVOList.size() == 0) {
				System.out.println("题库为空");
			}else {
				//将list集合转化为excle
				for (BasCustomerVO basCustomerVO : basCustomerVOList) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

                    GspBusinessLicense BusinessLicense = gspBusinessLicenseMybatisDao.selectCompareByEnterpriseId(basCustomerVO.getEnterpriseId());
                    GspOperateLicense GspProdLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId(),Constant.LICENSE_TYPE_PROD);
                    GspOperateLicense GspGspOperateLicense = gspOperateLicenseMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId(),Constant.LICENSE_TYPE_OPERATE);
                    GspFirstRecord GspFirstRecord = gspFirstRecordMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId());
                    GspSecondRecord GspSecondRecord = gspSecondRecordMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId());
                    GspMedicalRecord GspMedicalRecord = gspMedicalRecordMybatisDao.selectCompareByEnterprisId(basCustomerVO.getEnterpriseId());


                    if(BusinessLicense!=null){
                        basCustomerVO.setSocialCreditCode(BusinessLicense.getSocialCreditCode());
                        basCustomerVO.setLicenseNumber(BusinessLicense.getLicenseNumber());
                        if(BusinessLicense.getBusinessStartDate()!=null){
                            basCustomerVO.setBusinessStartDate(sdf.format(BusinessLicense.getBusinessStartDate()));
                        }else{
                            basCustomerVO.setBusinessStartDate("无期限");
                        }
                        if(BusinessLicense.getBusinessEndDate()!=null){
                            basCustomerVO.setBusinessEndDate(sdf.format(BusinessLicense.getBusinessEndDate()));
                        }else{
                            basCustomerVO.setBusinessEndDate("无期限");
                        }
                    }else{
                        basCustomerVO.setSocialCreditCode("无");
                        basCustomerVO.setLicenseNumber("无");
                        basCustomerVO.setBusinessStartDate("无");
                        basCustomerVO.setBusinessEndDate("无");
                    }

                    if(GspProdLicense!=null){
                        basCustomerVO.setProdLicenseNo(GspProdLicense.getLicenseNo());
                        basCustomerVO.setProdLicenseExpiryDate(sdf.format(GspProdLicense.getLicenseExpiryDate()));
                    }else{
                        basCustomerVO.setProdLicenseNo("无");
                        basCustomerVO.setProdLicenseExpiryDate("无");
                    }

                    if(GspGspOperateLicense!=null){
                        basCustomerVO.setOperateLicenseNo(GspGspOperateLicense.getLicenseNo());
                        basCustomerVO.setOperateLicenseExpiryDate(sdf.format(GspGspOperateLicense.getLicenseExpiryDate()));
                    }else{
                        basCustomerVO.setOperateLicenseNo("无");
                        basCustomerVO.setOperateLicenseExpiryDate("无");
                    }

                    if(GspFirstRecord!=null){
                        basCustomerVO.setFirstRecordNo(GspFirstRecord.getRecordNo());
                        basCustomerVO.setFirstRecordApproveDate(sdf.format(GspFirstRecord.getApproveDate()));
                    }else{
                        basCustomerVO.setFirstRecordNo("无");
                        basCustomerVO.setFirstRecordApproveDate("无");
                    }
                    if(GspSecondRecord!=null){
                        basCustomerVO.setSecondRecordNo(GspSecondRecord.getRecordNo());
                        basCustomerVO.setSecondRecordApproveDate(sdf.format(GspSecondRecord.getApproveDate()));
                    }else{
                        basCustomerVO.setSecondRecordNo("无");
                        basCustomerVO.setSecondRecordApproveDate("无");
                    }
                    if(GspMedicalRecord!=null){
                        basCustomerVO.setMedicalRegisterNo(GspMedicalRecord.getMedicalRegisterNo());
                        basCustomerVO.setMedicalRegisterApproveDate(sdf.format(GspMedicalRecord.getApproveDate()));
                    }else{
                        basCustomerVO.setMedicalRegisterNo("无");
                        basCustomerVO.setMedicalRegisterApproveDate("无");
                    }


					if ("1".equals(basCustomerVO.getActiveFlag())) {
						basCustomerVO.setActiveFlag("是");
					}else if("0".equals(basCustomerVO.getActiveFlag())){
						basCustomerVO.setActiveFlag("否");
					}

					if(Constant.CODE_CUS_TYP_VE.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("供应商");
					}else if(Constant.CODE_CUS_TYP_OW.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("货主");
					}else if(Constant.CODE_CUS_TYP_CO.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("收货单位");
					}else if(Constant.CODE_CUS_TYP_WH.equals(basCustomerVO.getCustomerType())){
						basCustomerVO.setCustomerType("主体");
					}


					if(basCustomerVO.getClientStartDate()!=null) {
						basCustomerVO.setClientStartDateDc(sdf.format(basCustomerVO.getClientStartDate()));
					}
					if(basCustomerVO.getClientEndDate()!=null) {
						basCustomerVO.setClientEndDateDc(sdf.format(basCustomerVO.getClientEndDate()));
					}



					if(basCustomerVO.getClientTerm()!=null&& !"".equals(basCustomerVO.getClientTerm())){
						basCustomerVO.setClientTerm(basCustomerVO.getClientTerm()+"天");
					}

					if ("1".equals(basCustomerVO.getIsChineseLabel())) {
						basCustomerVO.setIsChineseLabel("是");
					}else if("0".equals(basCustomerVO.getIsChineseLabel())){
						basCustomerVO.setIsChineseLabel("否");
					}

				}



				ExcelUtil.listToExcel(basCustomerVOList, fieldMap, sheetName, response);
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
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {

		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("activeFlag", "是否合作");
		superClassMap.put("customerType", "客户类型");
		superClassMap.put("customerid", "客户代码");
		superClassMap.put("descrC", "客户名称");
		superClassMap.put("enterpriseNo", "企业代码");
		superClassMap.put("shorthandName", "简称");
		superClassMap.put("socialCreditCode", "营业执照统一社会会信用代码");
        superClassMap.put("licenseNumber", "营业执照编号");
        superClassMap.put("businessStartDate", "营业执照开始时间");
        superClassMap.put("businessEndDate", "营业执照结束时间");
        superClassMap.put("prodLicenseNo", "生产许可编号");
        superClassMap.put("prodLicenseExpiryDate", "生产许可有效期");
        superClassMap.put("firstRecordNo", "一类生产备案许可编号");
        superClassMap.put("firstRecordApproveDate", "一类生产备案发证日期");
        superClassMap.put("operateLicenseNo", "经营许可证编号");
        superClassMap.put("operateLicenseExpiryDate", "经营许可有效期");
        superClassMap.put("secondRecordNo", "二类生产备案许可编号");
        superClassMap.put("secondRecordApproveDate", "二类生产备案发证日期");
        superClassMap.put("medicalRegisterNo", "医疗机构登记号许可编号");
        superClassMap.put("medicalRegisterApproveDate", "医疗机构登记号发证日期");
//		superClassMap.put("enterpriseName", "企业名称");
		superClassMap.put("allClient", "供应商对应货主");
		superClassMap.put("contacts", "联系人");
		superClassMap.put("contactsPhone", "联系人电话");
		superClassMap.put("supContractNo", "合同编号");
		superClassMap.put("contractUrl", "合同文件");
		superClassMap.put("clientContent", "委托/合同内容");
		superClassMap.put("clientStartDateDc", "委托/合同开始时间");
		superClassMap.put("clientEndDateDc", "委托/合同结束时间");
		superClassMap.put("clientTerm", "委托/合同结束时间 ");
		superClassMap.put("isChineseLabel", "是否贴中文标签");
		//superClassMap.put("descrC", "中文描述");
		//superClassMap.put("descrE", "英文描述");
		//superClassMap.put("operateType", "类型");
		superClassMap.put("notes", "备注");
		return superClassMap;
	}


	public BasCustomer getQueryBy(){
	    return null;
    }

	/**
	 * 根据customerid 查询所有供应商
	 * @param customerId
	 * @return
	 */
	public List<BasCustomer> querySupplierByCustomer(String customerId){
		return basCustomerMybatisDao.querySupplierByCustomer(customerId);
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