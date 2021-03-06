package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspOperateDetail;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.GspOperateDetailMybatisDao;
import com.wms.mybatis.dao.GspOperateLicenseMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.GspOperateDetailQuery;
import com.wms.query.GspOperateLicenseQuery;
import com.wms.utils.RandomUtil;
import com.wms.vo.GspOperateLicenseVO;
import com.wms.vo.form.GspOperateDetailForm;
import com.wms.vo.form.GspOperateLicenseForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspOperateLicense;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("gspOperateLicenseService")
public class GspOperateLicenseService extends BaseService {

	@Autowired
	private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
    @Autowired
    private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;
    @Autowired
    private GspOperateDetailMybatisDao gspOperateDetailMybatisDao;

	public Json addGspOperateLicense(GspOperateLicenseForm gspOperateLicenseForm) {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = new GspOperateLicense();
		BeanUtils.copyProperties(gspOperateLicenseForm, gspOperateLicense);
		gspOperateLicense.setCreateId(getLoginUserId());
		gspOperateLicense.setIsUse(Constant.IS_USE_YES);
		gspOperateLicense.setCreateDate(new Date());
		gspOperateLicenseMybatisDao.add(gspOperateLicense);
		json.setSuccess(true);
		return json;
	}

	public Json editGspOperateLicense(GspOperateLicenseForm gspOperateLicenseForm) {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.queryById(gspOperateLicenseForm.getOperateId());
		BeanUtils.copyProperties(gspOperateLicenseForm, gspOperateLicense);
		gspOperateLicenseMybatisDao.updateBySelective(gspOperateLicense);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspOperateLicense(String id) {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.queryById(id);
		if(gspOperateLicense != null){
			gspOperateLicenseMybatisDao.delete(gspOperateLicense);
		}
		json.setSuccess(true);
		return json;
	}

	public GspOperateLicense getGspOperateLicense(String id) {
		GspOperateLicense gspOperateLicense = gspOperateLicenseMybatisDao.queryById(id);
		return gspOperateLicense;
	}

	public GspOperateLicense getGspOperateLicenseBy(GspOperateLicenseQuery gspOperateLicenseQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspOperateLicenseQuery);
		mybatisCriteria.setOrderByClause("create_date desc");
		List<GspOperateLicense> list = gspOperateLicenseMybatisDao.queryByList(mybatisCriteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void updateGspOperateLicenseActiveTag(String id,String tag) {
		GspOperateLicense form = new GspOperateLicense();
		form.setEnterpriseId(id);
		form.setIsUse(tag);
		gspOperateLicenseMybatisDao.updateBySelective(form);
	}

	public void updateGspBusinessLicenseTagById(String id,String tag) {
		GspOperateLicense form = new GspOperateLicense();
		form.setOperateId(id);
		form.setIsUse(tag);
		gspOperateLicenseMybatisDao.updateBySelective(form);
	}

	/**
	 * 保存许可证信息方法
	 * @param enterpriceId 企业信息主键
	 * @param gspOperateLicenseForm 许可证提交json
	 * @param operateDetailStr 经营范围json
	 * @param gspOperateLicenseId 许可证主键
	 * @param opType 操作类型
	 * @return
	 */
	public Json addGspOperateLicense(String enterpriceId,String is_h,boolean enterpriseIsNewVersion,String oldEnterpriseId,GspOperateLicenseForm gspOperateLicenseForm,String operateDetailStr,String gspOperateLicenseId,String opType)throws Exception{
		//try{
			//GspOperateLicenseForm gspOperateLicenseForm = JSON.parseObject(operateLicenseFormStr,GspOperateLicenseForm.class);
			List<GspOperateDetailForm> gspOperateDetailForm = JSON.parseArray(operateDetailStr,GspOperateDetailForm.class);
			if(StringUtils.isEmpty(enterpriceId)){
				return Json.error("请先保存企业基础信息");
			}
			if(gspOperateLicenseForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspOperateLicenseForm)){
				return Json.error("许可证信息不全！");
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

			String LicenseType = gspOperateLicenseForm.getOperateType();
			//提交
			if(opType.equals(Constant.LICENSE_SUBMIT_ADD)){
				//新增
				if(gspOperateLicenseId == null || "".equals(gspOperateLicenseId)){
					gspOperateLicenseId = RandomUtil.getUUID();
					gspOperateLicenseForm.setEnterpriseId(enterpriceId);
					gspOperateLicenseForm.setOperateId(gspOperateLicenseId);
					gspOperateLicenseForm.setIsUse(Constant.IS_USE_YES);
//					gspOperateLicenseForm.setOpType(opType);
					addGspOperateLicense(gspOperateLicenseForm);

					if(gspOperateDetailForm.size()>0){
						for(GspOperateDetailForm g : gspOperateDetailForm){
							g.setEnterpriseId(gspOperateLicenseId);
							gspOperateDetailService.addGspOperateDetail(g,LicenseType);
						}
					}
				}else{//更新
					gspOperateLicenseForm.setOperateId(gspOperateLicenseId);
					editGspOperateLicense(gspOperateLicenseForm);
					gspOperateDetailService.deleteGspOperateDetail(gspOperateLicenseId,LicenseType);
					if(gspOperateDetailForm.size()>0){
						for(GspOperateDetailForm g : gspOperateDetailForm){
							g.setEnterpriseId(gspOperateLicenseId);
							gspOperateDetailService.addGspOperateDetail(g,LicenseType);
						}
					}
				}
			}else if(opType.equals(Constant.LICENSE_SUBMIT_UPDATE)){//换证
				//把旧证数据作废
				if(Constant.LICENSE_SUBMIT_UPDATE.equals(is_h)) {
					updateGspBusinessLicenseTagById(gspOperateLicenseId, Constant.IS_USE_NO);
				}


				//查询换证后报废企业的所有历史营业执照
				if(gspEnterpriseInfo==null || enterpriseIsNewVersion ) {
					GspOperateLicenseQuery query = new GspOperateLicenseQuery();
					EasyuiDatagridPager pager = new EasyuiDatagridPager();
					MybatisCriteria criteria = new MybatisCriteria();
					if (oldEnterpriseId != null && enterpriseIsNewVersion) {
						query.setEnterpriseId(oldEnterpriseId);
						query.setOperateType(gspOperateLicenseForm.getOperateType());
						criteria.setCondition(query);
						criteria.setCurrentPage(pager.getPage());
						criteria.setPageSize(9999);
						List<GspOperateLicense> gO = gspOperateLicenseMybatisDao.queryByList(criteria);
						//循环插入新建的企业版本中
						for (GspOperateLicense gspOperateOrProdLicense : gO) {
							String oldLicense = gspOperateOrProdLicense.getOperateId();
							gspOperateOrProdLicense.setOperateId(RandomUtil.getUUID());
							gspOperateOrProdLicense.setEnterpriseId(enterpriceId);
							gspOperateOrProdLicense.setOperateType(LicenseType);
							gspOperateOrProdLicense.setCreateId(getLoginUserId());
							gspOperateLicenseMybatisDao.add(gspOperateOrProdLicense);
							//历史营业证照的所有经营范围
							GspOperateDetailQuery operateDetailQuery = new GspOperateDetailQuery();
							operateDetailQuery.setLicenseId(oldLicense);
							criteria.setCondition(operateDetailQuery);
							List<GspOperateDetail> GspOperateDetailList = gspOperateDetailMybatisDao.queryByList(criteria);
							if (GspOperateDetailList.size() > 0) {
								for (GspOperateDetail od : GspOperateDetailList) {
									GspOperateDetailForm god = new GspOperateDetailForm();
									god.setOperateId(od.getOperateId());
									god.setEnterpriseId(gspOperateOrProdLicense.getOperateId());
									gspOperateDetailService.addGspOperateDetail(god, od.getLicenseType());
								}
							}
						}
					}
				}


				if(Constant.LICENSE_SUBMIT_UPDATE.equals(is_h)){
					//保存新证数据
					String newOperateLicenseId = RandomUtil.getUUID();
					gspOperateLicenseForm.setEnterpriseId(enterpriceId);
					gspOperateLicenseForm.setOperateType(LicenseType);

					gspOperateLicenseForm.setOperateId(newOperateLicenseId);
					gspOperateLicenseForm.setIsUse(Constant.IS_USE_YES);
					addGspOperateLicense(gspOperateLicenseForm);

					if(gspOperateDetailForm.size()>0){
						for(GspOperateDetailForm g : gspOperateDetailForm){
							g.setEnterpriseId(newOperateLicenseId);
							gspOperateDetailService.addGspOperateDetail(g,LicenseType);
						}
					}
				}
			}

			return Json.error("保存许可证信息失败");
		/*}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("系统错误");
		}*/
	}

	/**
	 * 查询经营许可证历史记录
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspOperateLicenseVO> getGspOperateLicenseHistory(EasyuiDatagridPager pager, GspOperateLicenseQuery query){
		EasyuiDatagrid<GspOperateLicenseVO> datagrid = new EasyuiDatagrid<>();
		List<GspOperateLicenseVO> gspOperateLicenseVOList = new ArrayList<>();
		if(!query.getEnterpriseId().equals("")){
			MybatisCriteria criteria = new MybatisCriteria();
			criteria.setCondition(query);
			criteria.setCurrentPage(pager.getPage());
			criteria.setPageSize(pager.getRows());
			criteria.setOrderByClause("create_date desc");

			List<GspOperateLicense> operateLicenses = gspOperateLicenseMybatisDao.queryByList(criteria);
			for(GspOperateLicense g : operateLicenses){
				GspOperateLicenseVO vo = new GspOperateLicenseVO();
				BeanUtils.copyProperties(g,vo);
				vo.setBusinessScope(g.getBusinessScope());
				gspOperateLicenseVOList.add(vo);
			}
			int total = gspOperateLicenseMybatisDao.queryByCount(criteria);
			datagrid.setTotal(Long.parseLong(total+""));
			datagrid.setRows(gspOperateLicenseVOList);
		}else {
			datagrid.setTotal(0L);
			datagrid.setRows(gspOperateLicenseVOList);
		}
		return datagrid;
	}



}