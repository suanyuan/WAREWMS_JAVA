package com.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wms.constant.Constant;
import com.wms.mybatis.dao.GspFirstRecordMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import com.wms.vo.form.GspOperateDetailForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.wms.dao.GspFirstRecordDao;
import com.wms.entity.GspFirstRecord;
import com.wms.vo.GspFirstRecordVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspFirstRecordForm;
import com.wms.query.GspFirstRecordQuery;

@Service("gspFirstRecordService")
public class GspFirstRecordService extends BaseService {

	@Autowired
	private GspFirstRecordMybatisDao gspFirstRecordMybatisDao;


	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	/**
	 * 查询一类备案凭证历史记录
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspFirstRecordVO> getGspFirstRecordHistory(EasyuiDatagridPager pager, GspFirstRecordQuery query) {
		EasyuiDatagrid<GspFirstRecordVO> datagrid = new EasyuiDatagrid<>();
		List<GspFirstRecordVO> gspOperateLicenseVOList = new ArrayList<>();
		if(!query.getEnterpriseId().equals("")){
			MybatisCriteria criteria = new MybatisCriteria();
			criteria.setCondition(query);
			criteria.setCurrentPage(pager.getPage());
			criteria.setPageSize(pager.getRows());
			criteria.setOrderByClause("create_date desc");

			List<GspFirstRecord> operateLicenses = gspFirstRecordMybatisDao.queryByList(criteria);
			for(GspFirstRecord g : operateLicenses){
				GspFirstRecordVO vo = new GspFirstRecordVO();
				BeanUtils.copyProperties(g,vo);
				gspOperateLicenseVOList.add(vo);
			}
			int total = gspFirstRecordMybatisDao.queryByCount(criteria);
			datagrid.setTotal(Long.parseLong(total+""));
			datagrid.setRows(gspOperateLicenseVOList);
		}else {
			datagrid.setTotal(0L);
			datagrid.setRows(gspOperateLicenseVOList);
		}
		return datagrid;
	}



//	getGspFirstRecordHistory
	public Json addGspFirstRecord(GspFirstRecordForm gspFirstRecordForm) throws Exception {
		Json json = new Json();
		GspFirstRecord gspFirstRecord = new GspFirstRecord();
		BeanUtils.copyProperties(gspFirstRecordForm, gspFirstRecord);
		gspFirstRecord.setCreateDate(new Date());
		gspFirstRecord.setCreateId(getLoginUserId());
		gspFirstRecord.setIsUse(Constant.IS_USE_YES);
		gspFirstRecordMybatisDao.add(gspFirstRecord);
		json.setSuccess(true);
		return json;
	}



	/**
	 * 营业执照新增
	 * @param enterpriceId 企业id
	 * @param gspFirstRecordForm
	 * @param operateDetailStr
	 * @param gspFirstRecordId 营业执照id
	 * @return
	 */
	public Json addGspFirstRecord(String enterpriceId,String oldEnterpriseId,GspFirstRecordForm gspFirstRecordForm,String operateDetailStr,String gspFirstRecordId,String opType) throws Exception{
		//try{
		//GspBusinessLicenseForm gspBusinessLicenseForm = JSON.parseObject(businessFormStr,GspBusinessLicenseForm.class);
		List<GspOperateDetailForm> gspOperateDetailForm = JSON.parseArray(operateDetailStr,GspOperateDetailForm.class);
		if(StringUtils.isEmpty(enterpriceId)){
			return Json.error("请先保存企业基础信息");
		}
		if(gspFirstRecordForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspFirstRecordForm)){
			return Json.error("营业执照信息不全！");
		}
			/*if(gspOperateDetailForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspOperateDetailForm)){
				return Json.error("必须选择营业执照经营范围！");
			}*/
		//提交
		if(opType.equals(Constant.LICENSE_SUBMIT_ADD)){
			//新增
			if(gspFirstRecordId == null || "".equals(gspFirstRecordId)){
				gspFirstRecordId = RandomUtil.getUUID();
				gspFirstRecordForm.setEnterpriseId(enterpriceId);
				gspFirstRecordForm.setRecordId(gspFirstRecordId);
				gspFirstRecordForm.setIsUse(Constant.IS_USE_YES);
				addGspFirstRecord(gspFirstRecordForm);

				if(gspOperateDetailForm.size()>0){
					for(GspOperateDetailForm g : gspOperateDetailForm){
						g.setEnterpriseId(gspFirstRecordId);
						gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_FIRSTRECORD);
					}
				}
			}else{//更新
				gspFirstRecordForm.setRecordId(gspFirstRecordId);
				editGspFirstRecord(gspFirstRecordForm);
				gspOperateDetailService.deleteGspOperateDetail(gspFirstRecordId,Constant.LICENSE_TYPE_FIRSTRECORD);
				if(gspOperateDetailForm.size()>0){
					for(GspOperateDetailForm g : gspOperateDetailForm){
						g.setEnterpriseId(gspFirstRecordId);
						gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_FIRSTRECORD);
					}
				}
			}
		}else if(opType.equals(Constant.LICENSE_SUBMIT_UPDATE)){//换证
			//把旧证数据作废
			updateGspBusinessLicenseTagById(gspFirstRecordId,Constant.IS_USE_NO);


			//查询换证后报废企业的所有历史营业执照
			GspFirstRecordQuery query = new GspFirstRecordQuery();
			EasyuiDatagridPager pager = new EasyuiDatagridPager();
			MybatisCriteria criteria = new MybatisCriteria();
			if(oldEnterpriseId!=null) {
				query.setEnterpriseId(oldEnterpriseId);
				criteria.setCondition(query);
				criteria.setCurrentPage(pager.getPage());
				criteria.setPageSize(9999);
				List<GspFirstRecord> gF = gspFirstRecordMybatisDao.queryByList(criteria);
				//循环插入新建的企业版本中
				for (GspFirstRecord gspOperateOrProdLicense : gF) {
					gspOperateOrProdLicense.setRecordId(RandomUtil.getUUID());
					gspOperateOrProdLicense.setEnterpriseId(enterpriceId);
					gspOperateOrProdLicense.setCreateId(getLoginUserId());
					gspFirstRecordMybatisDao.add(gspOperateOrProdLicense);
				}
			}


			//保存新证数据
			String newBusinessLicenseId = RandomUtil.getUUID();
			gspFirstRecordForm.setEnterpriseId(enterpriceId);
			gspFirstRecordForm.setRecordId(newBusinessLicenseId);
			gspFirstRecordForm.setIsUse(Constant.IS_USE_YES);
			addGspFirstRecord(gspFirstRecordForm);

			if(gspOperateDetailForm.size()>0){
				for(GspOperateDetailForm g : gspOperateDetailForm){
					g.setEnterpriseId(newBusinessLicenseId);
					gspOperateDetailService.addGspOperateDetail(g,Constant.LICENSE_TYPE_FIRSTRECORD);
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


	public Json editGspFirstRecord(GspFirstRecordForm gspFirstRecordForm) {
		Json json = new Json();
		GspFirstRecord gspFirstRecord = gspFirstRecordMybatisDao.queryById(gspFirstRecordForm.getRecordId());
		BeanUtils.copyProperties(gspFirstRecordForm, gspFirstRecord);
		gspFirstRecordMybatisDao.updateBySelective(gspFirstRecord);
		json.setSuccess(true);
		return json;
	}

	public void updateGspBusinessLicenseTagById(String id,String tag) {
		GspFirstRecordForm form = new GspFirstRecordForm();
		form.setRecordId(id);
		form.setIsUse(tag);
		GspFirstRecord gspBusinessLicense = new GspFirstRecord();
		BeanUtils.copyProperties(form,gspBusinessLicense);
		gspFirstRecordMybatisDao.updateBySelective(gspBusinessLicense);
	}

	public Json deleteGspFirstRecord(String id) {
		Json json = new Json();
		GspFirstRecord gspFirstRecord = gspFirstRecordMybatisDao.queryById(id);
		if(gspFirstRecord != null){
			gspFirstRecordMybatisDao.delete(gspFirstRecord);
		}
		json.setSuccess(true);
		return json;
	}
	//
	public GspFirstRecord getGspFirstRecordBy(GspFirstRecordQuery gspFirstRecordQuery){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCondition(gspFirstRecordQuery);
		mybatisCriteria.setOrderByClause("create_date desc");

		List<GspFirstRecord> list = gspFirstRecordMybatisDao.queryByList(mybatisCriteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<EasyuiCombobox> getGspFirstRecordCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspFirstRecord> gspFirstRecordList = gspFirstRecordMybatisDao.queryListByAll();
		if(gspFirstRecordList != null && gspFirstRecordList.size() > 0){
			for(GspFirstRecord gspFirstRecord : gspFirstRecordList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspFirstRecord.getRecordId()));
				combobox.setValue(gspFirstRecord.getHeadName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}