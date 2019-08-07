package com.wms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wms.constant.Constant;
import com.wms.dao.GspBusinessLicenseDao;
import com.wms.entity.GspBusinessLicense;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.mybatis.dao.BasCarrierLicenseMybatisDao;
import com.wms.mybatis.dao.GspBusinessLicenseMybatisDao;
import com.wms.mybatis.dao.GspEnterpriseInfoMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.RandomUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.form.BasCarrierLicenseFormString;
import com.wms.vo.form.GspCustomerForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.BasCarrierLicenseDao;
import com.wms.entity.BasCarrierLicense;
import com.wms.vo.BasCarrierLicenseVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.BasCarrierLicenseForm;
import com.wms.query.BasCarrierLicenseQuery;
import org.springframework.transaction.annotation.Transactional;

@Service("basCarrierLicenseService")
public class BasCarrierLicenseService extends BaseService {


	@Autowired
	private BasCarrierLicenseMybatisDao basCarrierLicenseMybatisDao;

	@Autowired
	private GspEnterpriseInfoMybatisDao gspEnterpriseInfoMybatisDao;

    @Autowired
    private GspBusinessLicenseMybatisDao gspBusinessLicenseMybatisDao;

//    @Autowired
//    private GspEnterpriseInfoMybatisDao GspEnterpriseInfoMybatisDao;

    //@Autowired
	//private BasCarrierLicenseDao basCarrierLicenseDao;


	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public EasyuiDatagrid<BasCarrierLicenseVO> getPagedDatagrid(EasyuiDatagridPager pager, BasCarrierLicenseQuery query) {
		EasyuiDatagrid<BasCarrierLicenseVO> datagrid = new EasyuiDatagrid<BasCarrierLicenseVO>();

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("edit_date desc");
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<BasCarrierLicense> basCarrierLicenseList = basCarrierLicenseMybatisDao.queryByList(mybatisCriteria);
		BasCarrierLicenseVO basCarrierLicenseVO = null;
		List<BasCarrierLicenseVO> basCarrierLicenseVOList = new ArrayList<BasCarrierLicenseVO>();
		for (BasCarrierLicense basCarrierLicense : basCarrierLicenseList) {
			basCarrierLicenseVO = new BasCarrierLicenseVO();
			BeanUtils.copyProperties(basCarrierLicense, basCarrierLicenseVO);
			GspEnterpriseInfo gspEnterpriseInfo = gspEnterpriseInfoMybatisDao.queryById(basCarrierLicense.getEnterpriseId());
			if (gspEnterpriseInfo != null) {

				basCarrierLicenseVO.setEnterpriseName(gspEnterpriseInfo.getEnterpriseName());
			}
			basCarrierLicenseVO.setCreateDate(simpleDateFormat.format(basCarrierLicense.getCreateDate()));
			basCarrierLicenseVO.setEditDate(simpleDateFormat.format(basCarrierLicense.getEditDate()));
			basCarrierLicenseVO.setCarrierDate(basCarrierLicense.getCarrierDate());
			basCarrierLicenseVO.setCarrierEndDate(basCarrierLicense.getCarrierEndDate());
			/*if(basCarrierLicense.getCarrierDate()!=null){
				basCarrierLicenseVO.setCarrierDate(simpleDateFormat.format(basCarrierLicense.getCarrierDate()));
			}
			if(basCarrierLicense.getCarrierEndDate()!=null){
				basCarrierLicenseVO.setCarrierEndDate(simpleDateFormat.format(basCarrierLicense.getCarrierEndDate()));
			}*/
			basCarrierLicenseVOList.add(basCarrierLicenseVO);
		}
		datagrid.setTotal((long) basCarrierLicenseMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(basCarrierLicenseVOList);
		return datagrid;
	}

	/*public Json addBasCarrierLicense(BasCarrierLicenseForm basCarrierLicenseForm) throws Exception {
		Json json = new Json();
		BasCarrierLicense basCarrierLicense = new BasCarrierLicense();
		GspBusinessLicense gspBusinessLicense = new GspBusinessLicense();
		basCarrierLicense.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		basCarrierLicense.setEditId(SfcUserLoginUtil.getLoginUser().getId());
		BeanUtils.copyProperties(basCarrierLicenseForm, basCarrierLicense);
		BeanUtils.copyProperties(basCarrierLicenseForm, gspBusinessLicense);
		basCarrierLicenseMybatisDao.add(basCarrierLicense);

		basCarrierLicenseMybatisDao.add(gspBusinessLicense);
		json.setSuccess(true);

		return json;
	}*/

	/**
	 * 对承运商的新增
	 * @param basCarrierLicenseForm
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Json addBasCarrierLicense(BasCarrierLicenseFormString basCarrierLicenseForm) throws Exception {
		Json json = null;
		try {
			json = new Json();
			BasCarrierLicense basCarrierLicense = new BasCarrierLicense();
			GspBusinessLicense gspBusinessLicense = new GspBusinessLicense();
			//GspCustomerForm gspCustomerForm = new GspCustomerForm();

			BeanUtils.copyProperties(basCarrierLicenseForm.getBasCarrierLicenseForm(), basCarrierLicense);
			BeanUtils.copyProperties(basCarrierLicenseForm.getGspBusinessLicenseForm(), gspBusinessLicense);
		//	BeanUtils.copyProperties(basCarrierLicenseForm.getGspCustomerForm(), gspCustomerForm);
			basCarrierLicense.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			basCarrierLicense.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			basCarrierLicense.setActiveFlag("1");

			gspBusinessLicense.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
			gspBusinessLicense.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			gspBusinessLicense.setIsUse("1");
			gspBusinessLicense.setBusinessId(RandomUtil.getUUID());
			gspBusinessLicense.setEnterpriseId(basCarrierLicenseForm.getBasCarrierLicenseForm().getEnterpriseId());
			BasCarrierLicense basCarrierLicense1 = basCarrierLicenseMybatisDao.queryUseByEnterId(basCarrierLicenseForm.getBasCarrierLicenseForm().getEnterpriseId());
			if(basCarrierLicense1!=null){
				json.setMsg("已有相同承运商！");
				return json;
			}
			basCarrierLicenseMybatisDao.add(basCarrierLicense);

			gspBusinessLicenseMybatisDao.add(gspBusinessLicense);

		//	gspCustomerService.addGspCustomer(gspCustomerForm);

			json.setMsg("资料添加成功");
			json.setSuccess(true);
		} catch (BeansException e) {
			e.printStackTrace();
		}

		return json;
	}
	/**
	 * 对承运商的编辑
	 * @param basCarrierLicenseForm
	 * @return
	 */
	public Json editBasCarrierLicense(BasCarrierLicenseFormString basCarrierLicenseForm) {
		Json json = new Json();
		if (basCarrierLicenseForm.getBasCarrierLicenseForm().getCarrierLicenseId()!=null){
			BasCarrierLicense basCarrierLicense = basCarrierLicenseMybatisDao.queryById(basCarrierLicenseForm.getBasCarrierLicenseForm().getCarrierLicenseId());
			BeanUtils.copyProperties(basCarrierLicenseForm.getBasCarrierLicenseForm(), basCarrierLicense);
			basCarrierLicense.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			basCarrierLicenseMybatisDao.updateBySelective(basCarrierLicense);
		}

		System.out.println(basCarrierLicenseForm.getGspBusinessLicenseForm().getBusinessId());
		if (basCarrierLicenseForm.getGspBusinessLicenseForm().getBusinessId()!=null){
			GspBusinessLicense gspBusinessLicense = gspBusinessLicenseMybatisDao.queryById(basCarrierLicenseForm.getGspBusinessLicenseForm().getBusinessId());
			BeanUtils.copyProperties(basCarrierLicenseForm.getGspBusinessLicenseForm(),gspBusinessLicense);
			gspBusinessLicense.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			gspBusinessLicenseMybatisDao.updateBySelective(gspBusinessLicense);
		}



		json.setSuccess(true);
		return json;
	}

	/**
	 * 停止合作承运商
	 * @param id
	 * @return
	 */
	public Json deleteBasCarrierLicense(String id) {
		Json json = new Json();
		BasCarrierLicense basCarrierLicense = basCarrierLicenseMybatisDao.queryById(id);
		if(basCarrierLicense != null){
			basCarrierLicenseMybatisDao.delete(basCarrierLicense);
		}
		json.setSuccess(true);
        return json;
	}

	public List<EasyuiCombobox> getBasCarrierLicenseCombobox() {
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		BasCarrierLicenseQuery query = new BasCarrierLicenseQuery();
		query.setActiveFlag(Constant.IS_USE_YES);
		mybatisCriteria.setCondition(query);
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<BasCarrierLicense> basCarrierLicenseList = basCarrierLicenseMybatisDao.queryByList(mybatisCriteria);
		if(basCarrierLicenseList != null && basCarrierLicenseList.size() > 0){
			for(BasCarrierLicense basCarrierLicense : basCarrierLicenseList){
				combobox = new EasyuiCombobox();
				combobox.setId(basCarrierLicense.getCarrierLicenseId());
                GspEnterpriseInfo gspEnterprise = gspEnterpriseInfoMybatisDao.queryById(basCarrierLicense.getEnterpriseId());
				combobox.setValue(gspEnterprise.getShorthandName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public Json getBasCarrierLicenseInfo(String enterpriceId){

		BasCarrierLicense basCarrierLicense = basCarrierLicenseMybatisDao.queryById(enterpriceId);
		if(basCarrierLicense == null){
			return Json.error("企业信息不存在！");
		}
		return Json.success("",basCarrierLicense);
	}


	public BasCarrierLicense queryByEnterId(BasCarrierLicense BasCarrierLicense){
		return basCarrierLicenseMybatisDao.queryByEnterId(BasCarrierLicense);
	}

}