package com.wms.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.text.ParseException;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.ExcelUtil;
import com.wms.utils.RedisUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.GspCustomerVO;
import com.wms.vo.GspSupplierVO;
import com.wms.vo.form.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.FirstReviewLogMybatisDao;
import com.wms.vo.FirstReviewLogVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.FirstReviewLogQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service("firstReviewLogService")
public class FirstReviewLogService extends BaseService {

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;
	@Autowired
	private DataPublishService dataPublishService;
	@Autowired
	private FirstBusinessApplyService firstBusinessApplyService;

	public EasyuiDatagrid<FirstReviewLogVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstReviewLogQuery query) {
		EasyuiDatagrid<FirstReviewLogVO> datagrid = new EasyuiDatagrid<FirstReviewLogVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setOrderByClause("create_date desc,edit_date desc,check_date_qc desc,check_date_head desc");
		List<FirstReviewLog> firstReviewLogList = firstReviewLogMybatisDao.queryByList(criteria);
		FirstReviewLogVO firstReviewLogVO = null;
		List<FirstReviewLogVO> firstReviewLogVOList = new ArrayList<FirstReviewLogVO>();
		for (FirstReviewLog firstReviewLog : firstReviewLogList) {
			firstReviewLogVO = new FirstReviewLogVO();
			BeanUtils.copyProperties(firstReviewLog, firstReviewLogVO);
			firstReviewLogVOList.add(firstReviewLogVO);
		}
		int count = firstReviewLogMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(firstReviewLogVOList);
		return datagrid;
	}

	public Json addFirstReviewLog(FirstReviewLogForm firstReviewLogForm) throws Exception {
		Json json = new Json();
		FirstReviewLog firstReviewLog = new FirstReviewLog();
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.add(firstReviewLog);
		json.setSuccess(true);
		return json;
	}

	public Json editFirstReviewLog(FirstReviewLogForm firstReviewLogForm) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(firstReviewLogForm.getReviewId());
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.updateBySelective(firstReviewLog);
		json.setSuccess(true);
		return json;
	}

	public Json updateByReviewTypeId(FirstReviewLogForm firstReviewLogForm) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = new FirstReviewLog();
		BeanUtils.copyProperties(firstReviewLogForm, firstReviewLog);
		firstReviewLogMybatisDao.updateByReviewTypeId(firstReviewLog);
		json.setSuccess(true);
		return json;
	}



	public Json deleteFirstReviewLog(String id) {
		Json json = new Json();
		FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(id);
		if(firstReviewLog != null){
			firstReviewLogMybatisDao.delete(firstReviewLog);
		}
		json.setSuccess(true);
		return json;
	}

	/**
	 * 审核通过
	 * @param id 单号
	 * @param remark 备注
 	 * @return
	 */
	public Json checkFirstReview(String id,String remark){
		try{
			String[] arr = id.split(",");
			Json json = new Json();
			for(String reviewId : arr) {
				SfcUserLogin userLogin = SfcUserLoginUtil.getLoginUser();

				FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(reviewId);
				if(firstReviewLog == null){
					return Json.error("查询不到对应的申请");
				}
				FirstReviewLog updateLog = new FirstReviewLog();
				//未审核
				if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING)){
					if(!userLogin.getUserGrade().equals(Constant.USER_GRADE_QC) && !userLogin.getUserGrade().equals(Constant.USER_GRADE_QCHEAD)){
						return Json.error("没有审核权限");
					}
					updateLog.setCheckIdQc(userLogin.getId());
					updateLog.setCheckDateQc(new Date());
					updateLog.setCheckRemarkQc(remark);
					updateLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE);
					//updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE);

				}else if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE)){
					if(!userLogin.getUserGrade().equals(Constant.USER_GRADE_HEAD) && !userLogin.getUserGrade().equals(Constant.USER_GRADE_QCHEAD)){
						return Json.error("没有审核权限");
					}
					updateLog.setCheckIdHead(userLogin.getId());
					updateLog.setCheckDateHead(new Date());
					updateLog.setCheckRemarkHead(remark);
					updateLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_PASS);
					//updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_PASS);

					//更新首营状态
					dataPublishService.updateFirstState(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_FIRSTSTATE_PASS);

					//下发数据
					dataPublishService.publishData(firstReviewLog.getReviewTypeId());

				}
				updateLog.setReviewId(reviewId);
				firstReviewLogMybatisDao.updateBySelective(updateLog);
			}
			json.setMsg("操作成功");
			return json;
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}
	}

	/**
	 * 驳回
	 * @param id 单号
	 * @param remark 备注
	 * @return
	 */
	public Json returnCheck(String id,String remark){



		try{

            String[] arr = id.split(",");
            Json json = new Json();
            for(String reviewId : arr) {

                FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(reviewId);
                FirstReviewLog updateLog = new FirstReviewLog();
                //质量部审核
                if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING)){
                    updateLog.setCheckIdQc(SfcUserLoginUtil.getLoginUser().getId());
                    updateLog.setCheckDateQc(new Date());
                    updateLog.setCheckRemarkQc(remark);
                }else if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE)){
                    updateLog.setCheckIdHead(SfcUserLoginUtil.getLoginUser().getId());
                    updateLog.setCheckDateHead(new Date());
                    updateLog.setCheckRemarkHead(remark);
                }
                //更新审核状态
                updateLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_FAIL);
                updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_FAIL);
                updateLog.setReviewId(reviewId);
                firstReviewLogMybatisDao.updateBySelective(updateLog);

                //更新首营申请单状态
                dataPublishService.updateFirstState(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_FIRSTSTATE_NEW);
            }
			json.setMsg("操作成功");
			json.setSuccess(true);
			return json;
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}
	}

	public Json updateFirstReviewByNo(String no,String state){
		Long result = firstReviewLogMybatisDao.updateFirstReviewByNo(no,state,SfcUserLoginUtil.getLoginUser().getId());
		if(result>0){
			return Json.success("");
		}
		return Json.error("");
	}


	public void exportFirstReviewLogDataToExcel(HttpServletResponse response, FirstReviewLogQuery form) throws IOException {
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
			List<FirstReviewLog> searchBasCustomerList = firstReviewLogMybatisDao.queryByList(mybatisCriteria);
			// 导出


			if (searchBasCustomerList == null || searchBasCustomerList.size() == 0) {
				System.out.println("题库为空");
			}else {
				for (FirstReviewLog s: searchBasCustomerList) {
//                    FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
//                    BeanUtils.copyProperties(s, firstReviewLogForm);


				    //时间格式转换
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=null;

					//状态
					if(s.getApplyState()!=null &&s.getApplyState()!="" ){
						if(Constant.CODE_CATALOG_CHECKSTATE_NEW.equals(s.getApplyState())){
							s.setApplyState("新建");
						}else if(Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING.equals(s.getApplyState())){
							s.setApplyState("质量部审核");
						}else if(Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE.equals(s.getApplyState())){
							s.setApplyState("负责人审核");
						}else if(Constant.CODE_CATALOG_CHECKSTATE_PASS.equals(s.getApplyState())){
							s.setApplyState("已通过");
						}else if(Constant.CODE_CATALOG_CHECKSTATE_FAIL.equals(s.getApplyState())){
							s.setApplyState("未通过");
						}
					}

					//时间转格式

//						if(s.getCheckDateQc()!=null) {
//							date = s.getCheckDateQc();
//						}
					if(s.getCheckDateQc()!=null) {
						s.setCheckDateQcDC(sdf.format(s.getCheckDateQc()));
					}
					if(s.getCheckDateHead()!=null) {
                        s.setCheckDateHeadDC(sdf.format(s.getCheckDateHead()));
					}
					if(s.getCreateDate()!=null) {
                        s.setCreateDateDC(sdf.format(s.getCreateDate()));
					}


                    //申请类型
                    if(s.getReviewTypeId()!=null &&s.getReviewTypeId()!=""){
						if(s.getReviewTypeId().contains("CUS")){
							s.setApplyType("委托客户");
						}else if(s.getReviewTypeId().contains("SUP")){
							s.setApplyType("供应商");
						}else if(s.getReviewTypeId().contains("PRO")){
							s.setApplyType("产品");
						}else if(s.getReviewTypeId().contains("REC")){
							s.setApplyType("收货单位");
						}
                    }
//
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
		superClassMap.put("applyState", "状态");
		superClassMap.put("applyType", "申请类型");
		superClassMap.put("reviewTypeId", "申请单编号");
		superClassMap.put("applyContent", "内容");
		superClassMap.put("checkIdQc", "质量部审核人");
		superClassMap.put("checkDateQcDC", "审核时间");
		superClassMap.put("checkRemarkQc", "备注");
		superClassMap.put("checkIdHead", "负责人审核");
		superClassMap.put("checkDateHeadDC", "负责人审核时间");
		superClassMap.put("checkRemarkHead", "备注");
		superClassMap.put("createDateDC", "创建时间");

		return superClassMap;
	}



}