package com.wms.dao;

import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.ProductLine;
import com.wms.query.ProductLineQuery;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productLineDao")
public class ProductLineDao extends BaseDao<ProductLine, String, ProductLineQuery> {

	public List<ProductLine> getPagedDatagrid(EasyuiDatagridPager pager, ProductLineQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(ProductLineQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(ProductLineQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(query.getProductLineId() != null){
			criteria.add(Restrictions.eq("productLineId", query.getProductLineId()));
		}
		if(StringUtils.isNotEmpty(query.getEnterpriseName())){
			criteria.add(Restrictions.like("enterpriseName", query.getEnterpriseName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getName())){
			criteria.add(Restrictions.like("name", query.getName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getExpression())){
			criteria.add(Restrictions.like("expression", query.getExpression(), MatchMode.ANYWHERE));
		}
		if(query.getCreateId() != null){
			criteria.add(Restrictions.eq("createId", query.getCreateId()));
		}
		if(query.getEditId() != null){
			criteria.add(Restrictions.eq("editId", query.getEditId()));
		}
		if(StringUtils.isNotEmpty(query.getIsUse())){
			criteria.add(Restrictions.like("isUse", query.getIsUse(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}