package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.GspInstrumentCatalog;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.GspMedicalRecordMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import com.wms.vo.form.GspOperateDetailForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.wms.dao.GspMedicalRecordDao;
import org.apache.commons.lang.StringUtils;
import com.wms.entity.GspMedicalRecord;
import com.wms.vo.GspMedicalRecordVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspMedicalRecordForm;
import com.wms.query.GspMedicalRecordQuery;

@Service("gspMedicalRecordService")
public class GspMedicalRecordService extends BaseService {

	@Autowired
	private GspMedicalRecordMybatisDao gspMedicalRecordMybatisDao;
	@Autowired
	private GspInstrumentCatalogService gspInstrumentCatalogService;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;


//	//证照历史信息列表
//	public EasyuiDatagrid<GspMedicalRecordVO> getPagedDatagrid(EasyuiDatagridPager pager, GspMedicalRecordQuery query) {
//		EasyuiDatagrid<GspMedicalRecordVO> datagrid = new EasyuiDatagrid<>();
//		List<GspMedicalRecordVO> gspBusinessLicenseVOList = new ArrayList<>();
//		if(!query.getMedicalRegisterNo().equals("")){
//			MybatisCriteria criteria = new MybatisCriteria();
//			criteria.setCondition(query);
//			criteria.setCurrentPage(pager.getPage());
//			criteria.setPageSize(pager.getRows());
//			criteria.setOrderByClause("create_date desc");
//
//			List<GspMedicalRecord> businessLicenseVOS = gspMedicalRecordMybatisDao.queryByList(criteria);
//			for(GspMedicalRecord g : businessLicenseVOS){
//				GspMedicalRecordVO vo = new GspMedicalRecordVO();
//				org.springframework.beans.BeanUtils.copyProperties(g,vo);
//				gspBusinessLicenseVOList.add(vo);
//			}
//			int total = gspMedicalRecordMybatisDao.queryByCount(criteria);
//			datagrid.setTotal(Long.parseLong(total+""));
//			datagrid.setRows(gspBusinessLicenseVOList);
//		}else {
//			datagrid.setTotal(0L);
//			datagrid.setRows(gspBusinessLicenseVOList);
//		}
//		return datagrid;
//	}

	public Json addGspMedicalRecord(GspMedicalRecordForm gspMedicalRecordForm) throws Exception {
		Json json = new Json();
		GspMedicalRecord gspMedicalRecord = new GspMedicalRecord();

		BeanUtils.copyProperties(gspMedicalRecordForm, gspMedicalRecord);
		gspMedicalRecord.setCreateDate(new Date());
		gspMedicalRecord.setCreateId(getLoginUserId());
		gspMedicalRecord.setIsUse(Constant.IS_USE_YES);
		gspMedicalRecordMybatisDao.add(gspMedicalRecord);
		json.setSuccess(true);
		return json;
	}
	public GspMedicalRecord getGspMedicalRecordBy(GspMedicalRecordQuery gspMedicalRecordQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspMedicalRecordQuery);
		mybatisCriteria.setOrderByClause("create_date desc");

		List<GspMedicalRecord> licenses = gspMedicalRecordMybatisDao.queryByList(mybatisCriteria);
		if(licenses!=null && licenses.size()>0){
			return licenses.get(0);
		}
		return null;
	}
	/**
	 * 保存许可证信息方法
	 * @param enterpriceId 企业信息主键
	 * @param gspMedicalRecordForm 许可证提交json
	 * @param operateDetailStr 经营范围json
	 * @param gspMedicalRecordId 许可证主键
	 * @param opType 操作类型
	 * @return
	 */
	public Json addGspMedicalRecord(String enterpriceId,String is_h,boolean enterpriseIsNewVersion,String oldEnterpriseId,GspMedicalRecordForm gspMedicalRecordForm,String operateDetailStr,String gspMedicalRecordId,String opType)throws Exception{
		//try{
		//GspOperateLicenseForm gspOperateLicenseForm = JSON.parseObject(operateLicenseFormStr,GspOperateLicenseForm.class);
		List<GspOperateDetailForm> gspOperateDetailForm = new ArrayList<>(); //JSON.parseArray(operateDetailStr,GspOperateDetailForm.class);
		if(StringUtils.isEmpty(enterpriceId)){
			return Json.error("请先保存企业基础信息");
		}
		if(gspMedicalRecordForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspMedicalRecordForm)){
			return Json.error("许可证信息不全！");
		}
//		if(gspOperateDetailForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspOperateDetailForm)){
//			return Json.error("必须选择许可证经营范围！");
//		}
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
			if(gspMedicalRecordId == null || "".equals(gspMedicalRecordId)){
				gspMedicalRecordId = RandomUtil.getUUID();
				gspMedicalRecordForm.setEnterpriseId(enterpriceId);
				gspMedicalRecordForm.setMedicalId(gspMedicalRecordId);
				gspMedicalRecordForm.setIsUse(Constant.IS_USE_YES);
//					gspOperateLicenseForm.setOpType(opType);
				addGspMedicalRecord(gspMedicalRecordForm);

				if(gspOperateDetailForm.size()>0){
					for(GspOperateDetailForm g : gspOperateDetailForm){
						g.setEnterpriseId(gspMedicalRecordId);
						gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_MEDICAL);
					}
				}
			}else{//更新
				gspMedicalRecordForm.setMedicalId(gspMedicalRecordId);
				editGspMedicalRecord(gspMedicalRecordForm);

				gspOperateDetailService.deleteGspOperateDetail(gspMedicalRecordId,Constant.LICENSE_TYPE_MEDICAL);
				if(gspOperateDetailForm.size()>0){
					for(GspOperateDetailForm g : gspOperateDetailForm){
						g.setEnterpriseId(gspMedicalRecordId);
						gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_MEDICAL);
					}
				}
			}
		}else if(opType.equals(Constant.LICENSE_SUBMIT_UPDATE)){//换证
			//把旧证数据作废
			if(Constant.LICENSE_SUBMIT_UPDATE.equals(is_h)) {
				updateGspMedicalRecordTagById(gspMedicalRecordId, Constant.IS_USE_NO);
			}

			//查询换证后报废企业的所有历史营业执照
			if(gspEnterpriseInfo==null || enterpriseIsNewVersion ) {
				GspMedicalRecordQuery query = new GspMedicalRecordQuery();
				EasyuiDatagridPager pager = new EasyuiDatagridPager();
				MybatisCriteria criteria = new MybatisCriteria();
				if (oldEnterpriseId != null && enterpriseIsNewVersion) {
					query.setEnterpriseId(oldEnterpriseId);
					criteria.setCondition(query);
					criteria.setCurrentPage(pager.getPage());
					criteria.setPageSize(9999);
					List<GspMedicalRecord> gM = gspMedicalRecordMybatisDao.queryByList(criteria);
					//循环插入新建的企业版本中
					for (GspMedicalRecord gspMedicalRecord : gM) {
						gspMedicalRecord.setMedicalId(RandomUtil.getUUID());
						gspMedicalRecord.setEnterpriseId(enterpriceId);
//				gspMedicalRecord.setCreateDate(new Date());
						gspMedicalRecord.setCreateId(getLoginUserId());
						gspMedicalRecordMybatisDao.add(gspMedicalRecord);
					}
				}
			}





			//保存新证数据
			if(Constant.LICENSE_SUBMIT_UPDATE.equals(is_h)) {
				String newOperateLicenseId = RandomUtil.getUUID();
				gspMedicalRecordForm.setEnterpriseId(enterpriceId);
				gspMedicalRecordForm.setMedicalId(newOperateLicenseId);
				gspMedicalRecordForm.setIsUse(Constant.IS_USE_YES);
				addGspMedicalRecord(gspMedicalRecordForm);

				if (gspOperateDetailForm.size() > 0) {
					for (GspOperateDetailForm g : gspOperateDetailForm) {
						g.setEnterpriseId(newOperateLicenseId);
						gspOperateDetailService.addGspOperateDetail(g, Constant.LICENSE_TYPE_MEDICAL);
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
	 * 查询营业执照历史记录
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspMedicalRecordVO> getGspMedicalRecordHistory(EasyuiDatagridPager pager, GspMedicalRecordQuery query){
		EasyuiDatagrid<GspMedicalRecordVO> datagrid = new EasyuiDatagrid<>();
		List<GspMedicalRecordVO> gspMedicalRecordVOList = new ArrayList<>();
		if(!query.getEnterpriseId().equals("")){
			MybatisCriteria criteria = new MybatisCriteria();
			criteria.setCondition(query);
			criteria.setCurrentPage(pager.getPage());
			criteria.setPageSize(pager.getRows());
			criteria.setOrderByClause("create_date desc");
			System.out.println();
			List<GspMedicalRecord> medicalRecordVOS = gspMedicalRecordMybatisDao.queryByList(criteria);
			for(GspMedicalRecord g : medicalRecordVOS){
				GspMedicalRecordVO vo = new GspMedicalRecordVO();
				org.springframework.beans.BeanUtils.copyProperties(g,vo);
				gspMedicalRecordVOList.add(vo);
			}
			int total = gspMedicalRecordMybatisDao.queryByCount(criteria);
			datagrid.setTotal(Long.parseLong(total+""));
			datagrid.setRows(gspMedicalRecordVOList);
		}else {
			datagrid.setTotal(0L);
			datagrid.setRows(gspMedicalRecordVOList);
		}
		return datagrid;
	}

	public Json editGspMedicalRecord(GspMedicalRecordForm gspMedicalRecordForm) {
		Json json = new Json();
		GspMedicalRecord gspMedicalRecord = gspMedicalRecordMybatisDao.queryById(gspMedicalRecordForm.getMedicalId());
		BeanUtils.copyProperties(gspMedicalRecordForm, gspMedicalRecord);
		gspMedicalRecord.setEditDate(new Date());
		gspMedicalRecord.setEditId(getLoginUserId());
//		gspMedicalRecord.setIsUse(Constant.IS_USE_YES);
		gspMedicalRecordMybatisDao.updateBySelective(gspMedicalRecord);
		json.setSuccess(true);
		return json;
	}

	public void updateGspMedicalRecordTagById(String id,String tag) {
		GspMedicalRecordForm form = new GspMedicalRecordForm();
		form.setMedicalId(id);
		form.setIsUse(tag);
		GspMedicalRecord gspMedicalRecord = new GspMedicalRecord();
		BeanUtils.copyProperties(form,gspMedicalRecord);
		gspMedicalRecordMybatisDao.updateBySelective(gspMedicalRecord);
	}

	public Json deleteGspMedicalRecord(String id) {
		Json json = new Json();
		GspMedicalRecord gspMedicalRecord = gspMedicalRecordMybatisDao.queryById(id);
		if(gspMedicalRecord != null){
			gspMedicalRecordMybatisDao.delete(gspMedicalRecord);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspMedicalRecordCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspMedicalRecord> gspMedicalRecordList = gspMedicalRecordMybatisDao.queryListByAll();
		if(gspMedicalRecordList != null && gspMedicalRecordList.size() > 0){
			for(GspMedicalRecord gspMedicalRecord : gspMedicalRecordList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspMedicalRecord.getMedicalId()));
				combobox.setValue(gspMedicalRecord.getMedicalName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}