package com.wms.service;

import java.util.*;

import com.wms.constant.Constant;
import com.wms.entity.GspProductRegisterSpecs;
import com.wms.mybatis.dao.GspProductRegisterMybatisDao;
import com.wms.mybatis.dao.GspProductRegisterSpecsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.GspProductRegisterSpecsQuery;
import com.wms.utils.DateUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.GspProductRegisterSpecsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.GspProductRegister;
import com.wms.vo.GspProductRegisterVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.GspProductRegisterForm;
import com.wms.query.GspProductRegisterQuery;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("gspProductRegisterService")
public class GspProductRegisterService extends BaseService {

	@Autowired
	private GspProductRegisterMybatisDao gspProductRegisterMybatisDao;
	@Autowired
	private GspProductRegisterSpecsMybatisDao gspProductRegisterSpecsMybatisDao;

	/**
	 * 查询分页数据
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspProductRegisterVO> getPagedDatagrid(EasyuiDatagridPager pager, GspProductRegisterQuery query) {
		EasyuiDatagrid<GspProductRegisterVO> datagrid = new EasyuiDatagrid<GspProductRegisterVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");
		List<GspProductRegister> gspProductRegisterList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
		GspProductRegisterVO gspProductRegisterVO = null;
		List<GspProductRegisterVO> gspProductRegisterVOList = new ArrayList<GspProductRegisterVO>();
		for (GspProductRegister gspProductRegister : gspProductRegisterList) {
			gspProductRegisterVO = new GspProductRegisterVO();
			BeanUtils.copyProperties(gspProductRegister, gspProductRegisterVO);
			gspProductRegisterVOList.add(gspProductRegisterVO);
		}
		int count = gspProductRegisterMybatisDao.queryByCount(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(gspProductRegisterVOList);
		return datagrid;
	}

	public Json addGspProductRegister(GspProductRegisterForm gspProductRegisterForm) throws Exception {
		if(!checkRep(gspProductRegisterForm.getProductRegisterNo())){
			return Json.error("产品注册证编号重复");
		}

		GspProductRegister gspProductRegister = new GspProductRegister();
		BeanUtils.copyProperties(gspProductRegisterForm, gspProductRegister);
		gspProductRegister.setProductRegisterId(RandomUtil.getUUID());
		gspProductRegister.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		gspProductRegister.setApproveDate(DateUtil.parse(gspProductRegisterForm.getApproveDate(),"yyyy-MM-dd"));
		gspProductRegister.setProductRegisterExpiryDate(DateUtil.parse(gspProductRegisterForm.getProductRegisterExpiryDate(),"yyyy-MM-dd"));
		gspProductRegisterMybatisDao.add(gspProductRegister);
		return Json.success("操作成功",gspProductRegister.getProductRegisterId());
	}

	public Json editGspProductRegister(GspProductRegisterForm gspProductRegisterForm) throws Exception{
		Json json = new Json();
		GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(gspProductRegisterForm.getProductRegisterId());
		BeanUtils.copyProperties(gspProductRegisterForm, gspProductRegister);
		gspProductRegister.setApproveDate(DateUtil.parse(gspProductRegisterForm.getApproveDate(),"yyyy-MM-dd"));
		gspProductRegister.setProductRegisterExpiryDate(DateUtil.parse(gspProductRegisterForm.getProductRegisterExpiryDate(),"yyyy-MM-dd"));
		gspProductRegister.setEditDate(new Date());
		gspProductRegister.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		gspProductRegisterMybatisDao.update(gspProductRegister);
		json.setSuccess(true);
		return json;
	}

	public Json deleteGspProductRegister(String id) {
		Json json = new Json();
		GspProductRegister gspProductRegister = gspProductRegisterMybatisDao.queryById(id);
		if(gspProductRegister != null){
			gspProductRegisterMybatisDao.delete(gspProductRegister);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getGspProductRegisterCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		Map<String,Object> query = new HashMap<>();
		query.put("is_use", Constant.IS_USE_YES);
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");
		EasyuiCombobox combobox = null;
		List<GspProductRegister> gspProductRegisterList = gspProductRegisterMybatisDao.queryByList(mybatisCriteria);
		if(gspProductRegisterList != null && gspProductRegisterList.size() > 0){
			for(GspProductRegister gspProductRegister : gspProductRegisterList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(gspProductRegister.getProductRegisterId()));
				combobox.setValue(gspProductRegister.getProductRegisterNo()+"-"+gspProductRegister.getProductRegisterName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	/**
	 * 查询产品注册证规格
	 * @param pager
	 * @param query
	 * @return
	 */
	public EasyuiDatagrid<GspProductRegisterSpecsVO> queryProductPageListByRegisterId(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query){
		EasyuiDatagrid<GspProductRegisterSpecsVO> datagrid = new EasyuiDatagrid<>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		query.setIsUse(Constant.IS_USE_YES);
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("create_date desc");

		List<GspProductRegisterSpecs> list = gspProductRegisterSpecsMybatisDao.queryByListUnBind(mybatisCriteria);
		GspProductRegisterSpecsVO gspProductRegisterSpecsVO = null;
		List<GspProductRegisterSpecsVO> voList = new ArrayList<>();
		if(list!=null){
			for(GspProductRegisterSpecs specs : list){
				gspProductRegisterSpecsVO = new GspProductRegisterSpecsVO();
				gspProductRegisterSpecsVO.setSpecsId(specs.getSpecsId());
				gspProductRegisterSpecsVO.setProductCode(specs.getProductCode());
				gspProductRegisterSpecsVO.setProductName(specs.getProductName());
				gspProductRegisterSpecsVO.setSpecsName(specs.getSpecsName());
				gspProductRegisterSpecsVO.setProductModel(specs.getProductModel());
				voList.add(gspProductRegisterSpecsVO);
			}
		}
		int count = gspProductRegisterSpecsMybatisDao.queryByCountUnBind(mybatisCriteria);
		datagrid.setTotal(Long.parseLong(count+""));
		datagrid.setRows(voList);
		return datagrid;
	}

	public GspProductRegister queryById(String id){
		return gspProductRegisterMybatisDao.queryById(id);
	}

	/**
	 * 绑定产品
	 * @param gspProductRegisterId
	 * @param specId
	 * @return
	 */
	public Json bindProduct(String gspProductRegisterId,String specId){
		if(gspProductRegisterId.equals("")){
			return Json.error("产品注册证为空");
		}

		if(specId.equals("")){
			return Json.error("请选择需要绑定的产品");
		}

		try{
			String[] arr = specId.split(",");
			for(String str : arr){
				System.out.println(str);
				GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
				gspProductRegisterSpecs.setSpecsId(str);
				gspProductRegisterSpecs.setProductRegisterId(gspProductRegisterId);
				gspProductRegisterSpecsMybatisDao.updateBySelective(gspProductRegisterSpecs);
			}
			return Json.success("绑定成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("绑定失败");
		}
	}

	/**
	 * 解除产品绑定
	 * @param specId
	 * @return
	 */
	public Json unBindProduct(String specId){
		if(specId.equals("")){
			return Json.error("请选择需要解除绑定的产品");
		}
		try{
			String[] arr = specId.split(",");
			for(String str : arr){
				System.out.println(str);
				GspProductRegisterSpecs gspProductRegisterSpecs = new GspProductRegisterSpecs();
				gspProductRegisterSpecs.setSpecsId(str);
				gspProductRegisterSpecs.setProductRegisterId("");
				gspProductRegisterSpecsMybatisDao.updateBySelective(gspProductRegisterSpecs);
			}
			return Json.success("解除绑定成功");
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return Json.error("解除绑定失败");
		}
	}


	public boolean checkRep(String registerNo){
		MybatisCriteria criteria = new MybatisCriteria();
		GspProductRegisterQuery query = new GspProductRegisterQuery();
		query.setProductRegisterNo(registerNo);
		criteria.setCondition(query);
		List<GspProductRegister> list = gspProductRegisterMybatisDao.queryByList(criteria);
		if(list!=null && list.size()>0){
			return false;
		}
		return true;
	}
}