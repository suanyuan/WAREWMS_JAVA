package com.wms.service;


import com.wms.dao.ProductLineDao;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;


import com.wms.entity.BasCustomer;
import com.wms.entity.ProductLine;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.ProductLineMybatisDao;

import com.wms.query.ProductLineQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.Json;
import com.wms.vo.ProductLineVO;
import com.wms.vo.form.ProductLineForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("productLineService")
public class ProductLineService extends BaseService {

	@Autowired
	private ProductLineDao productLineDao;

	@Autowired
	private ProductLineMybatisDao productLineMybatisDao;

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

	public EasyuiDatagrid<ProductLineVO> getPagedDatagrid(EasyuiDatagridPager pager, ProductLineQuery query) throws Exception {
		EasyuiDatagrid<ProductLineVO> datagrid = new EasyuiDatagrid<ProductLineVO>();

		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setOrderByClause("edit_date desc");
		//mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));


		ProductLineVO productLineVO = null;
		List<ProductLine> productLineList = productLineMybatisDao.queryByList(mybatisCriteria);
		List<ProductLineVO> productLineVOList = new ArrayList<ProductLineVO>();
		if (productLineList != null && productLineList.size()!=0) {
			for (ProductLine productLine : productLineList) {
				productLineVO = new ProductLineVO();
				BeanUtils.copyProperties(productLine, productLineVO);
				BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(productLine.getCustomerid());
				if (basCustomer != null) {
					productLineVO.setDescrC(basCustomer.getDescrC());

				}
				productLineVOList.add(productLineVO);
			}

			datagrid.setTotal((long) productLineMybatisDao.queryByCount(mybatisCriteria));
			datagrid.setRows(productLineVOList);
		}

		return datagrid;
	}

	public Json addProductLine(ProductLineForm productLineForm) throws Exception {
		Json json = new Json();
		ProductLine productLine = new ProductLine();
		BeanUtils.copyProperties(productLineForm, productLine);

		productLine.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		productLine.setEditId(SfcUserLoginUtil.getLoginUser().getId());

		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		productLine.setIsUse("1");
		productLineMybatisDao.insert(productLine);


		json.setSuccess(true);
		json.setMsg("添加成功");
		return json;
	}
	/*public Json insertProductLine(ProductLineForm productLineForm) throws Exception {
		Json json = new Json();
		ProductLine productLine = new ProductLine();

		BeanUtils.copyProperties(productLineForm, productLine);
		productLineMybatisDao.add(productLine);
		json.setSuccess(true);
		return json;
	}
*/
	/*public Json editProductLine(ProductLineForm productLineForm) {
		Json json = new Json();
		ProductLine productLine = productLineDao.findById(productLineForm.getProductLineId().toString());
		BeanUtils.copyProperties(productLineForm, productLine);
		productLineDao.update(productLine);
		json.setSuccess(true);
		return json;
	}
*/

	public Json updateProductLine(ProductLineForm productLineForm) {
		Json json = new Json();

		ProductLineQuery productLineQuery = new ProductLineQuery();
		productLineQuery.setProductLineId(productLineForm.getProductLineId());
		productLineQuery.setEnterpriseName(productLineForm.getEnterpriseName());
		productLineQuery.setName(productLineForm.getName());


		ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);

		BeanUtils.copyProperties(productLineForm, productLine);

		//productLine.setCreateId(SfcUserLoginUtil.getLoginUser().getId());
		productLine.setEditId(SfcUserLoginUtil.getLoginUser().getId());

		productLine.setIsUse("1");

		productLineMybatisDao.updateBySelective(productLine);
		json.setSuccess(true);
		json.setMsg("修改成功");
		return json;
	}
	public Json deleteProductLine(String id) {
		Json json = new Json();
		ProductLine productLine = productLineMybatisDao.queryById(id);

		if(productLine != null){

			productLine.setEditId(SfcUserLoginUtil.getLoginUser().getId());
			productLineMybatisDao.delete(productLine);
		}
		json.setSuccess(true);
		json.setMsg("删除成功");
		return json;
	}

	public List<EasyuiCombobox> getProductLineCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<ProductLine> productLineList = productLineDao.findAll();
		if(productLineList != null && productLineList.size() > 0){
			for(ProductLine productLine : productLineList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(productLine.getProductLineId()));
				combobox.setValue(productLine.getEnterpriseName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}