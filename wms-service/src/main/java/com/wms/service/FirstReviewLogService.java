package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.*;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.utils.RedisUtil;
import com.wms.utils.SfcUserLoginUtil;
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

@Service("firstReviewLogService")
public class FirstReviewLogService extends BaseService {

	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;
	@Autowired
	private DataPublishService dataPublishService;

	public EasyuiDatagrid<FirstReviewLogVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstReviewLogQuery query) {
		EasyuiDatagrid<FirstReviewLogVO> datagrid = new EasyuiDatagrid<FirstReviewLogVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setOrderByClause("create_date desc");
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

	public Json checkFirstReview(String id,String remark){
		try{
			SfcUserLogin userLogin = SfcUserLoginUtil.getLoginUser();
			Json json = new Json();
			FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(id);
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
				updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE);
			}else if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE)){
				if(!userLogin.getUserGrade().equals(Constant.USER_GRADE_HEAD) && !userLogin.getUserGrade().equals(Constant.USER_GRADE_QCHEAD)){
					return Json.error("没有审核权限");
				}
				updateLog.setCheckIdHead(userLogin.getId());
				updateLog.setCheckDateHead(new Date());
				updateLog.setCheckRemarkHead(remark);
				updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_PASS);

				//下发数据
				dataPublishService.publishData(firstReviewLog.getReviewTypeId());

			}
			updateLog.setReviewId(id);
			firstReviewLogMybatisDao.updateBySelective(updateLog);
			json.setMsg("操作成功");
			return json;
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("操作失败");
		}
	}

	public Json returnCheck(String id,String remark){
		try{
			Json json = new Json();
			FirstReviewLog firstReviewLog = firstReviewLogMybatisDao.queryById(id);
			FirstReviewLog updateLog = new FirstReviewLog();
			//未审核
			if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_QCCHECKING)){
				updateLog.setCheckIdQc(SfcUserLoginUtil.getLoginUser().getId());
				updateLog.setCheckDateQc(new Date());
				updateLog.setCheckRemarkQc(remark);
			}else if(firstReviewLog.getApplyState().equals(Constant.CODE_CATALOG_CHECKSTATE_RESPONSIBLE)){
				updateLog.setCheckIdHead(SfcUserLoginUtil.getLoginUser().getId());
				updateLog.setCheckDateHead(new Date());
				updateLog.setCheckRemarkHead(remark);
			}
			updateLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_FAIL);
			updateFirstReviewByNo(firstReviewLog.getReviewTypeId(),Constant.CODE_CATALOG_CHECKSTATE_FAIL);
			updateLog.setReviewId(id);
			firstReviewLogMybatisDao.updateBySelective(updateLog);
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
}