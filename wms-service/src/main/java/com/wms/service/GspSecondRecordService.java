package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspOperateDetail;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.GspOperateDetailMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.GspOperateDetailQuery;
import com.wms.utils.RandomUtil;
import com.wms.vo.GspSecondRecordVO;
import com.wms.vo.form.GspOperateDetailForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.mybatis.dao.GspSecondRecordMybatisDao;
import com.wms.entity.GspSecondRecord;
import com.wms.vo.Json;
import com.wms.vo.form.GspSecondRecordForm;
import com.wms.query.GspSecondRecordQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("gspSecondRecordService")
public class GspSecondRecordService extends BaseService {

	@Autowired
	private GspSecondRecordMybatisDao gspSecondRecordMybatisDao;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	@Autowired
	private GspOperateDetailMybatisDao gspOperateDetailMybatisDao;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

	public Json addGspSecondRecord(GspSecondRecordForm gspSecondRecordForm) throws Exception {
		Json json = new Json();
		GspSecondRecord gspSecondRecord = new GspSecondRecord();
		BeanUtils.copyProperties(gspSecondRecordForm, gspSecondRecord);
		gspSecondRecord.setCreateId(getLoginUserId());
		gspSecondRecord.setIsUse(Constant.IS_USE_YES);
        gspSecondRecord.setCreateDate(new Date());
		gspSecondRecordMybatisDao.add(gspSecondRecord);
		json.setSuccess(true);
		return json;
	}

	public Json editGspSecondRecord(GspSecondRecordForm gspSecondRecordForm) {
		Json json = new Json();
		GspSecondRecord gspSecondRecord = gspSecondRecordMybatisDao.queryById(gspSecondRecordForm.getRecordId());
		BeanUtils.copyProperties(gspSecondRecordForm, gspSecondRecord);
		gspSecondRecordMybatisDao.updateBySelective(gspSecondRecord);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspSecondRecord(String id) {
		Json json = new Json();
		GspSecondRecord gspSecondRecord = gspSecondRecordMybatisDao.queryById(id);
		if(gspSecondRecord != null){
			gspSecondRecordMybatisDao.delete(gspSecondRecord);
		}
		json.setSuccess(true);
		return json;
	}

	public GspSecondRecord getGspSecondRecord(String id) {
		GspSecondRecord gspSecondRecord = gspSecondRecordMybatisDao.queryById(id);
		return gspSecondRecord;
	}

	public GspSecondRecord getGspSecondRecordBy(GspSecondRecordQuery gspSecondRecordQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspSecondRecordQuery);
		mybatisCriteria.setOrderByClause("create_date desc");

		List<GspSecondRecord> list = gspSecondRecordMybatisDao.queryByList(mybatisCriteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void updateGspSecondRecordActiveTag(String id,String tag) {
		GspSecondRecord form = new GspSecondRecord();
		form.setEnterpriseId(id);
		form.setIsUse(tag);
		gspSecondRecordMybatisDao.updateBySelective(form);
	}

	public void updateGspSecondRecordTagById(String id,String tag) {
		GspSecondRecord form = new GspSecondRecord();
		form.setRecordId(id);
		form.setIsUse(tag);
		gspSecondRecordMybatisDao.updateBySelective(form);
	}

	/**
	 * 保存许可证信息方法
	 * @param enterpriceId 企业信息主键
	 * @param gspSecondRecordForm 许可证提交json
	 * @param operateDetailStr 经营范围json
	 * @param gspSecondRecordId 许可证主键
	 * @param opType 操作类型
	 * @return
	 */
	public Json addGspSecondRecord(String enterpriceId,String is_h,boolean enterpriseIsNewVersion,String oldEnterpriseId,GspSecondRecordForm gspSecondRecordForm,String operateDetailStr,String gspSecondRecordId,String opType)throws Exception{
		//try{
			//GspSecondRecordForm gspSecondRecordForm = JSON.parseObject(secondRecordFormStr,GspSecondRecordForm.class);
			List<GspOperateDetailForm> gspOperateDetailForm = JSON.parseArray(operateDetailStr,GspOperateDetailForm.class);
			if(StringUtils.isEmpty(enterpriceId)){
				return Json.error("请先保存企业基础信息");
			}
			if(gspSecondRecordForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspSecondRecordForm)){
				return Json.error("备案凭证信息不全！");
			}
			if(gspOperateDetailForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspOperateDetailForm)){
				return Json.error("必须选择许可证经营范围！");
			}

		GspEnterpriseInfo gspEnterpriseInfo =  gspEnterpriseInfoMybatisDao.selectEnterpriseProductRegister(oldEnterpriseId);
		if(gspEnterpriseInfo!=null) {
			//是生产企业
			opType = Constant.LICENSE_SUBMIT_UPDATE;
		}else if(oldEnterpriseId==null){
			opType = Constant.LICENSE_SUBMIT_ADD;
		}
			//提交
			if(opType.equals(Constant.LICENSE_SUBMIT_ADD)){
				//新增
				if(gspSecondRecordId == null || "".equals(gspSecondRecordId)){
					gspSecondRecordId = RandomUtil.getUUID();
					gspSecondRecordForm.setEnterpriseId(enterpriceId);
					gspSecondRecordForm.setRecordId(gspSecondRecordId);
					gspSecondRecordForm.setIsUse(Constant.IS_USE_YES);
					addGspSecondRecord(gspSecondRecordForm);

					if(gspOperateDetailForm.size()>0){
						for(GspOperateDetailForm g : gspOperateDetailForm){
							g.setEnterpriseId(gspSecondRecordId);
							gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_RECORD);
						}
					}
				}else{//更新
					gspSecondRecordForm.setRecordId(gspSecondRecordId);
					editGspSecondRecord(gspSecondRecordForm);
					gspOperateDetailService.deleteGspOperateDetail(gspSecondRecordId,Constant.LICENSE_TYPE_RECORD);
					if(gspOperateDetailForm.size()>0){
						for(GspOperateDetailForm g : gspOperateDetailForm){
							g.setEnterpriseId(gspSecondRecordId);
							gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_RECORD);
						}
					}
				}
			}else if(opType.equals(Constant.LICENSE_SUBMIT_UPDATE)){//换证
				//把旧证数据作废
				if(Constant.LICENSE_SUBMIT_UPDATE.equals(is_h)) {
					updateGspSecondRecordTagById(gspSecondRecordId, Constant.IS_USE_NO);
				}
				//查询换证后报废企业的所有历史营业执照
				if(gspEnterpriseInfo==null || enterpriseIsNewVersion ) {
					GspSecondRecordQuery query = new GspSecondRecordQuery();
					EasyuiDatagridPager pager = new EasyuiDatagridPager();
					MybatisCriteria criteria = new MybatisCriteria();
					if (oldEnterpriseId != null && enterpriseIsNewVersion) {
						query.setEnterpriseId(oldEnterpriseId);
						criteria.setCondition(query);
						criteria.setCurrentPage(pager.getPage());
						criteria.setPageSize(9999);
						List<GspSecondRecord> gS = gspSecondRecordMybatisDao.queryByList(criteria);
						//循环插入新建的企业版本中
						for (GspSecondRecord gspSecondRecord : gS) {
							String oldLicense = gspSecondRecord.getRecordId();
							gspSecondRecord.setRecordId(RandomUtil.getUUID());
							gspSecondRecord.setEnterpriseId(enterpriceId);
							gspSecondRecord.setCreateId(getLoginUserId());
							gspSecondRecordMybatisDao.add(gspSecondRecord);
							//历史营业证照的所有经营范围
							GspOperateDetailQuery operateDetailQuery = new GspOperateDetailQuery();
							operateDetailQuery.setLicenseId(oldLicense);
							criteria.setCondition(operateDetailQuery);
							List<GspOperateDetail> GspOperateDetailList = gspOperateDetailMybatisDao.queryByList(criteria);
							if (GspOperateDetailList.size() > 0) {
								for (GspOperateDetail od : GspOperateDetailList) {
									GspOperateDetailForm god = new GspOperateDetailForm();
									god.setOperateId(od.getOperateId());
									god.setEnterpriseId(gspSecondRecord.getRecordId());
									gspOperateDetailService.addGspOperateDetail(god, Constant.LICENSE_TYPE_RECORD);
								}
							}
						}
					}
				}

				//保存新证数据
				if(Constant.LICENSE_SUBMIT_UPDATE.equals(is_h)) {
					String newOperateLicenseId = RandomUtil.getUUID();
					gspSecondRecordForm.setEnterpriseId(enterpriceId);
					gspSecondRecordForm.setRecordId(newOperateLicenseId);
					gspSecondRecordForm.setIsUse(Constant.IS_USE_YES);
					addGspSecondRecord(gspSecondRecordForm);

					if (gspOperateDetailForm.size() > 0) {
						for (GspOperateDetailForm g : gspOperateDetailForm) {
							g.setEnterpriseId(newOperateLicenseId);
							gspOperateDetailService.addGspOperateDetail(g, Constant.LICENSE_TYPE_RECORD);
						}
					}
				}
			}

			return Json.success("保存备案凭证信息成功");
		/*}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("系统错误");
		}*/
	}

	/**
	 * 查询二类备案凭证历史记录
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspSecondRecordVO> getGspSecondRecordHistory(EasyuiDatagridPager pager, GspSecondRecordQuery query){
		EasyuiDatagrid<GspSecondRecordVO> datagrid = new EasyuiDatagrid<>();
		List<GspSecondRecordVO> gspOperateLicenseVOList = new ArrayList<>();
		if(!query.getEnterpriseId().equals("")){
			MybatisCriteria criteria = new MybatisCriteria();
			criteria.setCondition(query);
			criteria.setCurrentPage(pager.getPage());
			criteria.setPageSize(pager.getRows());
			criteria.setOrderByClause("create_date desc");

			List<GspSecondRecord> operateLicenses = gspSecondRecordMybatisDao.queryByList(criteria);
			for(GspSecondRecord g : operateLicenses){
				GspSecondRecordVO vo = new GspSecondRecordVO();
				BeanUtils.copyProperties(g,vo);
				vo.setCreateId(g.getCreateId());
				gspOperateLicenseVOList.add(vo);
			}
			int total = gspSecondRecordMybatisDao.queryByCount(criteria);
			datagrid.setTotal(Long.parseLong(total+""));
			datagrid.setRows(gspOperateLicenseVOList);
		}else {
			datagrid.setTotal(0L);
			datagrid.setRows(gspOperateLicenseVOList);
		}
		return datagrid;
	}
}