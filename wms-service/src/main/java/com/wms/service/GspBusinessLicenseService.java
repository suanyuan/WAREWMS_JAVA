package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import com.wms.vo.GspBusinessLicenseVO;
import com.wms.vo.form.GspOperateDetailForm;
import com.wms.vo.form.pda.LoginForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspBusinessLicense;
import com.wms.vo.Json;
import com.wms.vo.form.GspBusinessLicenseForm;
import com.wms.query.GspBusinessLicenseQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("gspBusinessLicenseService")
public class GspBusinessLicenseService extends BaseService {

	@Autowired
	private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

	public Json addGspBusinessLicense(GspBusinessLicenseForm gspBusinessLicenseForm) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = new GspBusinessLicense();
		BeanUtils.copyProperties(gspBusinessLicenseForm, gspBusinessLicense);
		gspBusinessLicense.setCreateId(getLoginUserId());
		gspBusinessLicense.setIsUse(Constant.IS_USE_YES);
		gspBusinessLicenseMybatisDao.add(gspBusinessLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editGspBusinessLicense(GspBusinessLicenseForm gspBusinessLicenseForm) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(gspBusinessLicenseForm.getBusinessId().toString());
		BeanUtils.copyProperties(gspBusinessLicenseForm, gspBusinessLicense);
		gspBusinessLicense.setEditId(getLoginUserId());
		gspBusinessLicense.setEditDate(new Date());
		gspBusinessLicenseMybatisDao.updateBySelective(gspBusinessLicense);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspBusinessLicense(String id) {
		Json json = new Json();
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(id);
		if(gspBusinessLicense != null){
			gspBusinessLicenseMybatisDao.delete(gspBusinessLicense);
		}
		json.setSuccess(true);
		return json;
	}

	public GspBusinessLicense getGspBusinessLicense(String id) {
		GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(id);
		return gspBusinessLicense;
	}

	public GspBusinessLicense getGspBusinessLicenseBy(GspBusinessLicenseQuery gspBusinessLicenseQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspBusinessLicenseQuery);
		List<GspBusinessLicense> licenses = gspBusinessLicenseMybatisDao.queryByList(mybatisCriteria);
		if(licenses!=null && licenses.size()>0){
			return licenses.get(0);
		}
		return null;
	}

	public void updateGspBusinessLicenseActiveTag(String enterpriseId,String tag) {
		gspBusinessLicenseMybatisDao.updateGspBusinessLicenseActiveTag(enterpriseId, tag);
	}

	public void updateGspBusinessLicenseTagById(String id,String tag) {
		GspBusinessLicenseForm form = new GspBusinessLicenseForm();
		form.setBusinessId(id);
		form.setIsUse(tag);
		GspBusinessLicense gspBusinessLicense = new GspBusinessLicense();
		BeanUtils.copyProperties(form,gspBusinessLicense);
		gspBusinessLicenseMybatisDao.updateBySelective(gspBusinessLicense);
	}

	/**
	 * 营业执照新增
	 * @param enterpriceId 企业id
	 * @param gspBusinessLicenseForm
	 * @param operateDetailStr
	 * @param gspBusinessLicenseId 营业执照id
	 * @return
	 */
	public Json addGspBusinessLicense(String enterpriceId,String oldEnterpriseId,GspBusinessLicenseForm gspBusinessLicenseForm,String operateDetailStr,String gspBusinessLicenseId,String opType) throws Exception{
		//try{
			//GspBusinessLicenseForm gspBusinessLicenseForm = JSON.parseObject(businessFormStr,GspBusinessLicenseForm.class);
			List<GspOperateDetailForm> gspOperateDetailForm = new ArrayList<>();//JSON.parseArray(operateDetailStr,GspOperateDetailForm.class);
			if(StringUtils.isEmpty(enterpriceId)){
				return Json.error("请先保存企业基础信息");
			}
			if(gspBusinessLicenseForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspBusinessLicenseForm)){
				return Json.error("营业执照信息不全！");
			}
			/*if(gspOperateDetailForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspOperateDetailForm)){
				return Json.error("必须选择营业执照经营范围！");
			}*/
			//提交
			if(opType.equals(Constant.LICENSE_SUBMIT_ADD)){
				//新增
				if(gspBusinessLicenseId == null || "".equals(gspBusinessLicenseId)){
					gspBusinessLicenseId = RandomUtil.getUUID();
					gspBusinessLicenseForm.setEnterpriseId(enterpriceId);
					gspBusinessLicenseForm.setBusinessId(gspBusinessLicenseId);
					gspBusinessLicenseForm.setIsUse(Constant.IS_USE_YES);
					addGspBusinessLicense(gspBusinessLicenseForm);

					if(gspOperateDetailForm.size()>0){
						for(GspOperateDetailForm g : gspOperateDetailForm){
							g.setEnterpriseId(gspBusinessLicenseId);
							gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_BUSINESS);
						}
					}
				}else{//更新
					gspBusinessLicenseForm.setBusinessId(gspBusinessLicenseId);
					editGspBusinessLicense(gspBusinessLicenseForm);
					gspOperateDetailService.deleteGspOperateDetail(gspBusinessLicenseId,Constant.LICENSE_TYPE_BUSINESS);
					if(gspOperateDetailForm.size()>0){
						for(GspOperateDetailForm g : gspOperateDetailForm){
							g.setEnterpriseId(gspBusinessLicenseId);
							gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_BUSINESS);
						}
					}
				}
			}else if(opType.equals(Constant.LICENSE_SUBMIT_UPDATE)){//换证
				//把旧证数据作废
				updateGspBusinessLicenseTagById(gspBusinessLicenseId,Constant.IS_USE_NO);
				//查询换证后报废企业的所有历史营业执照

				GspBusinessLicenseQuery query = new GspBusinessLicenseQuery();
				EasyuiDatagridPager pager = new EasyuiDatagridPager();
				MybatisCriteria criteria = new MybatisCriteria();
				query.setEnterpriseId(oldEnterpriseId);
				criteria.setCondition(query);
				criteria.setCurrentPage(pager.getPage());
				criteria.setPageSize(9999);
				List<GspBusinessLicense> gB = gspBusinessLicenseMybatisDao.queryByList(criteria);
				//循环插入新建的企业版本中
				for(GspBusinessLicense  gspBusinessLicense:gB){
					gspBusinessLicense.setBusinessId(RandomUtil.getUUID());
					gspBusinessLicense.setEnterpriseId(enterpriceId);
//					gspBusinessLicense.setCreateDate(new Date());
					gspBusinessLicense.setCreateId(getLoginUserId());
					gspBusinessLicenseMybatisDao.add(gspBusinessLicense);
				}
//
				//保存新证数据
				String newBusinessLicenseId = RandomUtil.getUUID();
				gspBusinessLicenseForm.setEnterpriseId(enterpriceId);
				gspBusinessLicenseForm.setBusinessId(newBusinessLicenseId);
				gspBusinessLicenseForm.setIsUse(Constant.IS_USE_YES);
				addGspBusinessLicense(gspBusinessLicenseForm);

				//TODO 证照详情表(GspOperateDetail)没加
				if(gspOperateDetailForm.size()>0){
					for(GspOperateDetailForm g : gspOperateDetailForm){
						g.setEnterpriseId(newBusinessLicenseId);
						gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_BUSINESS);
					}
				}
			}
			return Json.error("保存营业执照失败");
		/*}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("系统错误");
		}*/
	}

	/**
	 * 查询营业执照历史记录
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspBusinessLicenseVO> getGspBusinessLicenseHistory(EasyuiDatagridPager pager, GspBusinessLicenseQuery query){
		EasyuiDatagrid<GspBusinessLicenseVO> datagrid = new EasyuiDatagrid<>();
		List<GspBusinessLicenseVO> gspBusinessLicenseVOList = new ArrayList<>();
		if(!query.getEnterpriseId().equals("")){
			MybatisCriteria criteria = new MybatisCriteria();

			criteria.setCurrentPage(pager.getPage());
			criteria.setPageSize(pager.getRows());
			criteria.setOrderByClause("create_date desc");
//			GspEnterpriseInfo gspEnterpriseInfo =gspEnterpriseInfoMybatisDao.queryById(query.getEnterpriseId());
//			if("1".equals(gspEnterpriseInfo.getIsUse())){
//
//			}else{
//				query.setIsUse("0");
//				criteria.setTotalCount(1);
//			}
			criteria.setCondition(query);
			List<GspBusinessLicense> businessLicenseVOS = gspBusinessLicenseMybatisDao.queryByList(criteria);
			for(GspBusinessLicense g : businessLicenseVOS){
				GspBusinessLicenseVO vo = new GspBusinessLicenseVO();
				org.springframework.beans.BeanUtils.copyProperties(g,vo);
				gspBusinessLicenseVOList.add(vo);
			}
			int total = gspBusinessLicenseMybatisDao.queryByCount(criteria);
			datagrid.setTotal(Long.parseLong(total+""));
			datagrid.setRows(gspBusinessLicenseVOList);
		}else {
			datagrid.setTotal(0L);
			datagrid.setRows(gspBusinessLicenseVOList);
		}
		return datagrid;
	}

}