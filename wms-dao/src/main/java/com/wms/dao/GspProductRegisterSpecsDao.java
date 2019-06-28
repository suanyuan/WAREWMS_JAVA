package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.GspProductRegisterSpecs;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.GspProductRegisterSpecsQuery;

@Repository("gspProductRegisterSpecsDao")
public class GspProductRegisterSpecsDao extends BaseDao<GspProductRegisterSpecs, String, GspProductRegisterSpecsQuery> {

	public List<GspProductRegisterSpecs> getPagedDatagrid(EasyuiDatagridPager pager, GspProductRegisterSpecsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(GspProductRegisterSpecsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(GspProductRegisterSpecsQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getSpecsId())){
			criteria.add(Restrictions.like("specsId", query.getSpecsId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductRegisterId())){
			criteria.add(Restrictions.like("productRegisterId", query.getProductRegisterId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSpecsName())){
			criteria.add(Restrictions.like("specsName", query.getSpecsName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductCode())){
			criteria.add(Restrictions.like("productCode", query.getProductCode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductName())){
			criteria.add(Restrictions.like("productName", query.getProductName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductRemark())){
			criteria.add(Restrictions.like("productRemark", query.getProductRemark(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductModel())){
			criteria.add(Restrictions.like("productModel", query.getProductModel(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductionAddress())){
			criteria.add(Restrictions.like("productionAddress", query.getProductionAddress(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getBarCode())){
			criteria.add(Restrictions.like("barCode", query.getBarCode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUnit())){
			criteria.add(Restrictions.like("unit", query.getUnit(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPackingUnit())){
			criteria.add(Restrictions.like("packingUnit", query.getPackingUnit(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCategories())){
			criteria.add(Restrictions.like("categories", query.getCategories(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConversionRate())){
			criteria.add(Restrictions.like("conversionRate", query.getConversionRate(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLlong())){
			criteria.add(Restrictions.like("llong", query.getLlong(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWide())){
			criteria.add(Restrictions.like("wide", query.getWide(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHight())){
			criteria.add(Restrictions.like("hight", query.getHight(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductLine())){
			criteria.add(Restrictions.like("productLine", query.getProductLine(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getManageCategories())){
			criteria.add(Restrictions.like("manageCategories", query.getManageCategories(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPacking_Require())){
			criteria.add(Restrictions.like("packing_Require", query.getPacking_Require(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getStorageCondition())){
			criteria.add(Restrictions.like("storageCondition", query.getStorageCondition(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTransportCondition())){
			criteria.add(Restrictions.like("transportCondition", query.getTransportCondition(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCreateId())){
			criteria.add(Restrictions.like("createId", query.getCreateId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditId())){
			criteria.add(Restrictions.like("editId", query.getEditId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getIsUse())){
			criteria.add(Restrictions.like("isUse", query.getIsUse(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatName1())){
			criteria.add(Restrictions.like("alternatName1", query.getAlternatName1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatName2())){
			criteria.add(Restrictions.like("alternatName2", query.getAlternatName2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatName3())){
			criteria.add(Restrictions.like("alternatName3", query.getAlternatName3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatName4())){
			criteria.add(Restrictions.like("alternatName4", query.getAlternatName4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatName5())){
			criteria.add(Restrictions.like("alternatName5", query.getAlternatName5(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}