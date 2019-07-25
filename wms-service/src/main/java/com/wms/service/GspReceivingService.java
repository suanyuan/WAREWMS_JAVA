package com.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.SfcWarehouse;
import com.wms.query.BasCustomerQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.form.BasCustomerForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspReceivingDao;
import com.wms.vo.GspReceivingVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspReceivingForm;
import com.wms.query.GspReceivingQuery;
import org.springframework.transaction.annotation.Transactional;

@Service("gspReceivingService")
public class GspReceivingService extends BaseService {

	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

	@Autowired
	private GspReceivingDao gspReceivingDao;

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;

	@Autowired
	private GspReceivingMybatisDao gspReceivingMybatisDao;





	@Autowired
	private GspReceivingAddressMybatisDao gspReceivingAddressMybatisDao;

	public EasyuiDatagrid<GspReceivingVO> getPagedDatagrid(EasyuiDatagridPager pager, GspReceivingQuery query)  {
		EasyuiDatagrid<GspReceivingVO> datagrid = new EasyuiDatagrid<GspReceivingVO>();

		try {
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCurrentPage(pager.getPage());
			mybatisCriteria.setPageSize(pager.getRows());
			mybatisCriteria.setOrderByClause("edit_date desc");
			mybatisCriteria.setCondition(query);
			//mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			List<GspReceiving> gspReceivingList = gspReceivingMybatisDao.queryByList(mybatisCriteria);

			List<GspReceivingVO> gspReceivingVOList = new ArrayList<GspReceivingVO>();
			GspReceivingVO gspReceivingVO = null;
			/*List<GspEnterpriseInfo> gspEnterpriseInFoList = gspEnterpriseInfoMybatisDao.queryByList(mybatisCriteria);
			if (gspEnterpriseInFoList != null) {
				for (GspEnterpriseInfo gspEnterpriseInfo: gspEnterpriseInFoList){
					gspReceivingVO = new GspReceivingVO();
					gspReceivingVO.setShorthandName(gspEnterpriseInfo.getShorthandName());
					gspReceivingVO.setEnterpriseNo(gspEnterpriseInfo.getEnterpriseNo());
					gspReceivingVO.setEnterpriseName(gspEnterpriseInfo.getShorthandName());
					gspReceivingVOList.add(gspReceivingVO);
				}
			}*/




			for (GspReceiving gspReceiving : gspReceivingList) {
				gspReceivingVO = new GspReceivingVO();


				GspReceivingAddress gspReceivingAddress = gspReceivingAddressMybatisDao.queryIsDefault(gspReceiving);
				GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(gspReceiving.getEnterpriseId());
				BasCustomer basCustomer1 = new BasCustomer();
				basCustomer1.setEnterpriseId(gspReceiving.getEnterpriseId());
				basCustomer1.setCustomerType("CO");
				BasCustomer basCustomer = basCustomerMybatisDao.queryByenterId(basCustomer1);
				BeanUtils.copyProperties(gspReceiving, gspReceivingVO);
				if (gspReceivingAddress!=null ){

					gspReceivingVO.setDeliveryAddress(gspReceivingAddress.getDeliveryAddress());
					gspReceivingVO.setContacts(gspReceivingAddress.getContacts());
					gspReceivingVO.setPhone(gspReceivingAddress.getPhone());
					//gspReceivingVO.setDeliveryAddress(gspReceivingAddress.getDeliveryAddress());

					/*GspCustomer gspCustomer = gspCustomerMybatisDao.queryById(gspReceiving.getClientId());

					if (gspCustomer!=null) {
						gspReceivingVO.setIsCooperation(gspCustomer.getIsCooperation());
					}*/
				}
				if ( gspEnterpriseInfo!=null){
					gspReceivingVO.setEnterpriseName(gspEnterpriseInfo.getEnterpriseName());
					gspReceivingVO.setEnterpriseNo(gspEnterpriseInfo.getEnterpriseNo());
					gspReceivingVO.setShorthandName(gspEnterpriseInfo.getShorthandName());

				}
				if ( basCustomer!=null){
					gspReceivingVO.setCustomerid(basCustomer.getCustomerid());
				}
				gspReceivingVOList.add(gspReceivingVO);
			}
			datagrid.setTotal((long) gspReceivingMybatisDao.queryByCount(mybatisCriteria));
			datagrid.setRows(gspReceivingVOList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datagrid;
	}
	@Transactional
	public Json addGspReceiving(GspReceivingForm gspReceivingForm,String newreceivingId) throws Exception {
		Json json = new Json();
		try {
			GspReceiving gspReceiving = new GspReceiving();

			//发起新申请
			if (StringUtils.isNotEmpty(gspReceivingForm.getReceivingId())){
				GspReceiving oldgspReceiving =gspReceivingMybatisDao.queryById(gspReceivingForm.getReceivingId());
				oldgspReceiving.setIsUse("0");
				gspReceivingMybatisDao.updateBySelective(oldgspReceiving);

				BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
				gspReceiving.setIsUse("1");
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setReceivingId(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				gspReceiving.setFirstState("00");

				gspReceivingMybatisDao.add(gspReceiving);
			}else {

				GspReceiving gspReceiving1 =gspReceivingMybatisDao.queryByEnterpriseId(gspReceivingForm.getEnterpriseId());
				if(gspReceiving1!=null){
					return Json.error("同一个收货单位不能重复申请！");
				}

				// 新增
				/*if (newreceivingId!=null&&newreceivingId!=""){

					BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
					gspReceiving.setIsUse("1");
					gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
					gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
					gspReceiving.setReceivingId(newreceivingId);
					gspReceiving.setFirstState("00");

				}else {*/
				BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
				gspReceiving.setIsUse("1");
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setReceivingId(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				gspReceiving.setFirstState("00");
				String id ="";
				List<GspReceivingAddress> gspReceivingAddressList = gspReceivingAddressMybatisDao.queryByReceivingId(id);
				if (gspReceivingAddressList != null && gspReceivingAddressList.size()!=0) {
					for (GspReceivingAddress gspReceivingAddress : gspReceivingAddressList){

						gspReceivingAddress.setReceivingId(gspReceiving.getReceivingId());
						gspReceivingAddressMybatisDao.updateBySelective(gspReceivingAddress);
					}

				}
				gspReceivingMybatisDao.add(gspReceiving);
			}



			//插入一条首营申请日志记录
			FirstReviewLog firstReviewLog = new FirstReviewLog();
			firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLog.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLog.setReviewTypeId(gspReceivingForm.getReceivingId());
			firstReviewLog.setApplyState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
			firstReviewLog.setReviewId(RandomUtil.getUUID());
			firstReviewLogMybatisDao.add(firstReviewLog);
		} catch (BeansException e) {
			throw new Exception("服务器忙!");
		}

		json.setSuccess(true);
		return json;
	}




	public Json confirmApply(GspReceivingForm gspReceivingForm) throws Exception {
		Json json = new Json();
		try {
			FirstReviewLog firstReviewLog = new FirstReviewLog();
			GspReceiving gspReceiving=	gspReceivingMybatisDao.queryById(gspReceivingForm.getReceivingId());
			if (gspReceiving == null) {
				return null;
			}
			gspReceiving.setFirstState("10");
			gspReceivingMybatisDao.updateBySelective(gspReceiving);
			firstReviewLog.setReviewTypeId(gspReceiving.getReceivingId());
			//插入一条首营申请日志记录
			firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLog.setApplyState("00");
			firstReviewLog.setReviewId(RandomUtil.getUUID());
			firstReviewLogMybatisDao.add(firstReviewLog);
		} catch (BeansException e) {
			throw new Exception("服务器忙!");
		}

		json.setSuccess(true);
		return json;
	}

	public GspReceiving validateReceiv(String receivingId) throws Exception {

		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(receivingId);
		return gspReceiving;
	}

	public Json editGspReceiving(GspReceivingForm gspReceivingForm) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(gspReceivingForm.getReceivingId());
		BeanUtils.copyProperties(gspReceivingForm, gspReceiving);
		gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
        gspReceivingMybatisDao.updateBySelective(gspReceiving);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspReceiving(String id) {
		Json json = new Json();
		GspReceiving gspReceiving = gspReceivingMybatisDao.queryById(id);
		if(gspReceiving != null){
            gspReceivingMybatisDao.delete(gspReceiving);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspReceivingCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspReceiving> gspReceivingList = gspReceivingDao.findAll();
		if(gspReceivingList != null && gspReceivingList.size() > 0){
			for(GspReceiving gspReceiving : gspReceivingList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspReceiving.getReceivingId()));
				combobox.setValue(gspReceiving.getSupplierId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public GspReceiving getGspReceiving(String id){
		return gspReceivingMybatisDao.queryById(id);
	}

	/**
	 * 更新首营状态
	 * @param no
	 * @param state
	 * @return
	 */
	public Json updateFirstState(String no,String state){
		Long result = gspReceivingMybatisDao.updateFirstState(no,state);
		if(result>0){
			return Json.success("更新申请单首营状态成功");
		}
		return Json.error("更新申请单首营状态失败");
	}

/*	public Json addBasCustomer(BasCustomerForm basCustomerForm) throws Exception {
		*//*Json json = null;
		try {


			StringBuilder resultMsg = new StringBuilder();
			BasCustomerService.validateCustomer(basCustomerForm, resultMsg);// 验证客户是否存在

			json = new Json();


			if (resultMsg.length() == 0) {
				BasCustomer basCustomer = new BasCustomer();

				if (StringUtils.isNotEmpty(basCustomerForm.getReceivingId())){
					GspReceiving oldgspReceiving =gspReceivingMybatisDao.queryById(basCustomerForm.getReceivingId());
					oldgspReceiving.setIsUse("0");
					gspReceivingMybatisDao.updateBySelective(oldgspReceiving);
				}
				BasCustomerQuery customerQuery = new BasCustomerQuery();
				customerQuery.setEnterpriseId(basCustomerForm.getEnterpriseId());
				customerQuery.setCustomerType(basCustomerForm.getCustomerType());


				BasCustomer oldbasCustomer=basCustomerMybatisDao.queryById(customerQuery);

				if (oldbasCustomer != null) {
					BasSkuHistory basSkuHistory = new BasSkuHistory();
					BeanUtils.copyProperties(oldbasCustomer,basSkuHistory);
					basCustomerMybatisDao.deleteBascustomer(oldbasCustomer.getEnterpriseId(),oldbasCustomer.getCustomerType());
					basSkuHistoryMybatisDao.add(basSkuHistory);
				}
				//下发到客户档案
				BeanUtils.copyProperties(basCustomerForm, basCustomer);
				basCustomer.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
				basCustomer.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
				basCustomer.setCustomerid(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				basCustomer.setEnterpriseId(basCustomerForm.getEnterpriseId());
				basCustomer.setActiveFlag(basCustomerForm.getIsUse());
				//
				basCustomerMybatisDao.add(basCustomer);
				GspReceiving gspReceiving = new GspReceiving();

				BeanUtils.copyProperties(basCustomerForm,gspReceiving);
				gspReceiving.setFirstState("40");
				gspReceiving.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				gspReceiving.setEditId(SfcUserLoginUtil.getLoginUser().getId());
				if (StringUtils.isNotEmpty(basCustomerForm.getNewreceivingId())){

					gspReceiving.setReceivingId(basCustomerForm.getNewreceivingId());
				}else {

					gspReceiving.setReceivingId(commonService.generateSeq(Constant.APLRECNO,SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
				}
				gspReceivingMybatisDao.add(gspReceiving);

				//插入一条首营申请日志记录
				FirstReviewLog firstReviewLog = new FirstReviewLog();
				firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstReviewLog.setReviewTypeId(basCustomerForm.getReceivingId());
				firstReviewLog.setApplyContent("不需要申请直接下发");
				firstReviewLog.setApplyState("40");
				firstReviewLog.setReviewId(RandomUtil.getUUID());
				firstReviewLogMybatisDao.add(firstReviewLog);


			} else {
				json.setSuccess(false);
				json.setMsg(resultMsg.toString());
				return json;
			}
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
		} catch (BeansException e) {
			throw new Exception("系统忙！");
		}
		return json;
	}*//*
		return basCustomerService.xiaFaBasCustomer(basCustomerForm);

	}*/

}