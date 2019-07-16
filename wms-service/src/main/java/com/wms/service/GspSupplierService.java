package com.wms.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.constant.Constant;
import com.wms.entity.FirstReviewLog;
import com.wms.mybatis.dao.FirstReviewLogMybatisDao;
import com.wms.mybatis.dao.GspSupplierMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.GspSupplierDao;
import com.wms.entity.GspSupplier;
import com.wms.vo.GspSupplierVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspSupplierForm;
import com.wms.query.GspSupplierQuery;
import org.springframework.transaction.annotation.Transactional;

@Service("gspSupplierService")
public class GspSupplierService extends BaseService {

	@Autowired
	private GspSupplierDao gspSupplierDao;
	@Autowired
	private GspSupplierMybatisDao GspSupplierMybatisDao;
	@Autowired
	private CommonService commonService;
	@Autowired
	private FirstReviewLogMybatisDao firstReviewLogMybatisDao;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public EasyuiDatagrid<GspSupplierVO> getPagedDatagrid(EasyuiDatagridPager pager, GspSupplierQuery query) {
//		EasyuiDatagrid<GspSupplierVO> datagrid = new EasyuiDatagrid<GspSupplierVO>();
//		List<GspSupplier> gspSupplierList = gspSupplierDao.getPagedDatagrid(pager, query);
//		GspSupplierVO gspSupplierVO = null;
//		List<GspSupplierVO> gspSupplierVOList = new ArrayList<GspSupplierVO>();
//		for (GspSupplier gspSupplier : gspSupplierList) {
//			gspSupplierVO = new GspSupplierVO();
//			BeanUtils.copyProperties(gspSupplier, gspSupplierVO);
//			gspSupplierVOList.add(gspSupplierVO);
//		}
//		datagrid.setTotal(gspSupplierDao.countAll(query));
//		datagrid.setRows(gspSupplierVOList);
//		return datagrid;
		//System.out.println(query.getOperateType()+"========query.getOperateType()=======");
		EasyuiDatagrid<GspSupplierVO> datagrid = new EasyuiDatagrid<GspSupplierVO>();
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCurrentPage(pager.getPage());
		criteria.setPageSize(pager.getRows());
		criteria.setCondition(query);
		GspSupplierVO gspSupplierVO = null;
		List<GspSupplierVO> basGtnVOList = new ArrayList<GspSupplierVO>();
		List<GspSupplier> basGtnList = GspSupplierMybatisDao.queryByList(criteria);
		for (GspSupplier gspSupplier : basGtnList) {
			gspSupplierVO = new GspSupplierVO();
			BeanUtils.copyProperties(gspSupplier, gspSupplierVO);
			if(gspSupplier.getCreateDate()!=null){
				gspSupplierVO.setCreateDate(simpleDateFormat.format(gspSupplier.getCreateDate()));
			}
			if(gspSupplier.getEditDate()!=null){
				gspSupplierVO.setEditDate(simpleDateFormat.format(gspSupplier.getEditDate()));
			}
			if(gspSupplier.getClientStartDate()!=null){
				gspSupplierVO.setClientStartDate(simpleDateFormat.format(gspSupplier.getClientStartDate()));
			}
			if(gspSupplier.getClientEndDate()!=null){
				gspSupplierVO.setClientEndDate(simpleDateFormat.format(gspSupplier.getClientEndDate()));
			}
			basGtnVOList.add(gspSupplierVO);
		}

		int total = GspSupplierMybatisDao.queryByCount(criteria);
		datagrid.setTotal(Long.parseLong(total+""));
		datagrid.setRows(basGtnVOList);
		return datagrid;
	}

    @Transactional
	public Json addGspSupplier(GspSupplierForm gspSupplierForm) throws Exception {
		Json json = new Json();
		GspSupplier gspSupplier = new GspSupplier();
		BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
		System.out.println("SfcUserLoginUtil.getLoginUser().getId()========="+SfcUserLoginUtil.getLoginUser().getId());
		gspSupplier.setSupplierId(commonService.generateSeq(Constant.APLSUPNO, SfcUserLoginUtil.getLoginUser().getWarehouse().getId()));
		FirstReviewLog firstReviewLog = new FirstReviewLog();
		firstReviewLog.setReviewId(RandomUtil.getUUID());
		firstReviewLog.setReviewTypeId(gspSupplier.getSupplierId());
		firstReviewLog.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		firstReviewLog.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		firstReviewLog.setCreateDate(new Date());
		firstReviewLog.setEditDate(new Date());
		if("1".equals(gspSupplier.getIsCheck())){
			firstReviewLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_NEW);//审核状态 新建 代码
			gspSupplier.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_NEW);//首营状态 新建 代码
		}else if("0".equals(gspSupplier.getIsCheck())){
			firstReviewLog.setApplyState(Constant.CODE_CATALOG_CHECKSTATE_PASS);//审核状态 已通过 代码
			gspSupplier.setFirstState(Constant.CODE_CATALOG_FIRSTSTATE_PASS);//首营状态 审核通过 代码
			firstReviewLog.setApplyContent("不需要审核直接下发");
		}


		firstReviewLogMybatisDao.add(firstReviewLog);

		GspSupplierMybatisDao.add(gspSupplier);
		json.setSuccess(true);
		return json;
	}

	public Json editGspSupplier(GspSupplierForm gspSupplierForm) {
		Json json = new Json();
		//GspSupplier gspSupplier = gspSupplierDao.findById(gspSupplierForm.getSupplierId());
		//BeanUtils.copyProperties(gspSupplierForm, gspSupplier);
		GspSupplierMybatisDao.updateBySelective(gspSupplierForm);
		json.setSuccess(true);
		return json;
	}
	public Json getGspSupplierInfo(String supplierId){
		GspSupplierVO gspSupplierVO = new GspSupplierVO();
		System.out.println("supplierId==========="+supplierId);
		GspSupplier gspSupplier = GspSupplierMybatisDao.queryById(supplierId);
		BeanUtils.copyProperties(gspSupplier, gspSupplierVO);
		gspSupplierVO.setEditDate(simpleDateFormat.format(new Date()));
		gspSupplierVO.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		gspSupplierVO.setCreateDate(simpleDateFormat.format(gspSupplier.getCreateDate()));
		if(gspSupplier.getClientStartDate()!=null){
			gspSupplierVO.setClientStartDate(simpleDateFormat.format(gspSupplier.getClientStartDate()));
		}
		if(gspSupplier.getClientEndDate()!=null){
			gspSupplierVO.setClientEndDate(simpleDateFormat.format(gspSupplier.getClientEndDate()));

		}
		//System.out.println("gspSupplierVO============="+gspSupplierVO.getCreateDate()+"==========="+gspSupplierVO.getCreateDate());
		if(gspSupplier == null){
			return Json.error("不存在！");
		}

		return Json.success("",gspSupplierVO);
	}
	public Json deleteGspSupplier(String id) {
		Json json = new Json();
		//GspSupplier gspSupplier = gspSupplierDao.findById(id);
		if(id != null){
			GspSupplierMybatisDao.deleteNotUse(id);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspSupplierCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<GspSupplier> gspSupplierList = gspSupplierDao.findAll();
		if(gspSupplierList != null && gspSupplierList.size() > 0){
			for(GspSupplier gspSupplier : gspSupplierList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspSupplier.getSupplierId()));
				combobox.setValue(gspSupplier.getEnterpriseId());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}