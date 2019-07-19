package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.GspSupplier;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.GspSupplierQuery;

@Repository("gspSupplierDao")
public class GspSupplierDao extends BaseDao<GspSupplier, String, GspSupplierQuery> {

	public List<GspSupplier> getPagedDatagrid(EasyuiDatagridPager pager, GspSupplierQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(GspSupplierQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(GspSupplierQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getSupplierId())){
			criteria.add(Restrictions.like("supplierId", query.getSupplierId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEnterpriseId())){
			criteria.add(Restrictions.like("enterpriseId", query.getEnterpriseId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getIsCheck())){
			criteria.add(Restrictions.like("isCheck", query.getIsCheck(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOperateType())){
			criteria.add(Restrictions.like("operateType", query.getOperateType(), MatchMode.ANYWHERE));
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
		return criteria;
	}


}