package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.mybatis.dao.GspOperateLicenseMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.GspOperateLicenseQuery;
import com.wms.vo.GspOperateLicenseVO;
import com.wms.vo.form.GspOperateDetailForm;
import com.wms.vo.form.GspOperateLicenseForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspOperateLicense;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("gspOperateLicenseService")
public class GspOperateLicenseService extends BaseService {

	@Autowired
	private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;

	public Json addGspOperateLicense(GspOperateLicenseForm gspOperateLicenseForm) throws Exception {
		Json json = new Json();
		GspOperateLicense gspOperateLicense = new GspOperateLicense();
		BeanUtils.copyProperties(gspOperateLicenseForm, gspOperateLicense);
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
		List<GspOperateLicense> list = gspOperateLicenseMybatisDao.queryByList(mybatisCriteria);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public void updateGspOperateLicenseActiveTag(String id,String tag) {
		GspOperateLicenseForm form = new GspOperateLicenseForm();
		form.setEnterpriseId(id);
		form.setIsUse(tag);
		gspOperateLicenseMybatisDao.updateBySelective(form);
	}

	/**
	 * 保存许可证信息方法
	 * @param enterpriceId 企业信息主键
	 * @param operateLicenseFormStr 许可证提交json
	 * @param operateDetailStr 经营范围json
	 * @param gspOperateLicenseId 许可证主键
	 * @param opType 操作类型
	 * @return
	 */
	public Json addGspOperateLicense(String enterpriceId,String operateLicenseFormStr,String operateDetailStr,String gspOperateLicenseId,String opType){
		try{
			GspOperateLicenseForm gspOperateLicenseForm = JSON.parseObject(operateLicenseFormStr,GspOperateLicenseForm.class);
			GspOperateDetailForm gspOperateDetailForm = JSON.parseObject(operateDetailStr,GspOperateDetailForm.class);
			if(StringUtils.isEmpty(enterpriceId)){
				return Json.error("请先保存企业基础信息");
			}
			if(gspOperateLicenseForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspOperateLicenseForm)){
				return Json.error("许可证信息不全！");
			}
			if(gspOperateDetailForm == null || com.wms.utils.BeanUtils.isEmptyFrom(gspOperateDetailForm)){
				return Json.error("必须选择许可证经营范围！");
			}

			return Json.error("");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("系统错误");
		}
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
		if(query.getEnterpriseId().equals("")){
			MybatisCriteria criteria = new MybatisCriteria();
			criteria.setCondition(query);
			criteria.setCurrentPage(pager.getPage());
			criteria.setPageSize(pager.getRows());
			criteria.setOrderByClause("create_date desc");

			List<GspOperateLicense> operateLicenses = gspOperateLicenseMybatisDao.queryByList(criteria);
			for(GspOperateLicense g : operateLicenses){
				GspOperateLicenseVO vo = new GspOperateLicenseVO();
				com.wms.utils.BeanUtils.copyProperties(g,vo);
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