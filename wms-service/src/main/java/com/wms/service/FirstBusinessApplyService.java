package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.entity.FirstBusinessProductApply;
import com.wms.entity.GspProductRegisterSpecs;
import com.wms.mybatis.dao.FirstBusinessApplyMybatisDao;
import com.wms.mybatis.dao.FirstBusinessProductApplyMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.FirstBusinessProductApplyQuery;
import com.wms.result.FirstBusinessApplyResult;
import com.wms.result.FirstBusinessProductApplyResult;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.FirstBusinessProductApplyPageVO;
import com.wms.vo.GspProductRegisterSpecsVO;
import com.wms.vo.form.FirstReviewLogForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.FirstBusinessApply;
import com.wms.vo.FirstBusinessApplyVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.FirstBusinessApplyForm;
import com.wms.query.FirstBusinessApplyQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("firstBusinessApplyService")
public class FirstBusinessApplyService extends BaseService {

	@Autowired
	private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;
	@Autowired
	private FirstBusinessProductApplyMybatisDao firstBusinessProductApplyMybatisDao;
	@Autowired
	private FirstReviewLogService firstReviewLogService;

	public EasyuiDatagrid<FirstBusinessApplyVO> getPagedDatagrid(EasyuiDatagridPager pager, FirstBusinessApplyQuery query) {
		EasyuiDatagrid<FirstBusinessApplyVO> datagrid = new EasyuiDatagrid<FirstBusinessApplyVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		query.setIsUse(Constant.IS_USE_YES);
		criteria.setCondition(query);
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setOrderByClause("t1.create_date desc");
		List<FirstBusinessApplyResult> firstBusinessApplyList = firstBusinessApplyMybatisDao.queryPageList(criteria);
		FirstBusinessApplyVO firstBusinessApplyVO = null;
		List<FirstBusinessApplyVO> firstBusinessApplyVOList = new ArrayList<FirstBusinessApplyVO>();
		for (FirstBusinessApply firstBusinessApply : firstBusinessApplyList) {
			firstBusinessApplyVO = new FirstBusinessApplyVO();
			BeanUtils.copyProperties(firstBusinessApply, firstBusinessApplyVO);
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
		firstBusinessApplyMybatisDao.add(firstBusinessApply);
		json.setSuccess(true);
		return json;
	}

	public Json editFirstBusinessApply(FirstBusinessApplyForm firstBusinessApplyForm) {
		Json json = new Json();
		FirstBusinessApply firstBusinessApply = firstBusinessApplyMybatisDao.queryById(firstBusinessApplyForm.getApplyId());
		BeanUtils.copyProperties(firstBusinessApplyForm, firstBusinessApply);
		firstBusinessApplyMybatisDao.update(firstBusinessApply);
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
			return Json.success("",firstBusinessApply);
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
				voList.add(firstBusinessProductApplyPageVO);
			}
		}
		int count = firstBusinessProductApplyMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(voList);
		return datagrid;
	}

	public Json addApply(String clientId,String supplierId,String productArr){
		try{
			FirstBusinessApply firstBusinessApply = new FirstBusinessApply();
			firstBusinessApply.setApplyId(RandomUtil.getUUID());
			firstBusinessApply.setClientId(clientId);
			firstBusinessApply.setSupplierId(supplierId);
			firstBusinessApply.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstBusinessApplyMybatisDao.add(firstBusinessApply);

			String[] arr = productArr.split(",");
			for(String specsId : arr){
				FirstBusinessProductApply firstBusinessProductApply = new FirstBusinessProductApply();
				firstBusinessProductApply.setProductApplyId(RandomUtil.getUUID());
				firstBusinessProductApply.setApplyId(firstBusinessApply.getApplyId());
				firstBusinessProductApply.setSpecsId(specsId);
				firstBusinessProductApply.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
				firstBusinessProductApplyMybatisDao.add(firstBusinessProductApply);
			}

			//添加申请记录
			FirstReviewLogForm firstReviewLogForm = new FirstReviewLogForm();
			firstReviewLogForm.setReviewId(RandomUtil.getUUID());
			firstReviewLogForm.setApplyState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);
			firstReviewLogForm.setReviewTypeId("产品");
			firstReviewLogForm.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			firstReviewLogService.addFirstReviewLog(firstReviewLogForm);

			return Json.success("申请成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return Json.error("申请失败");
	}

}