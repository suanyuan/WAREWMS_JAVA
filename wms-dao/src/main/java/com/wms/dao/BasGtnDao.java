package com.wms.dao;

import java.util.List;

import com.wms.entity.BasGtn;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.BasGtnQuery;

@Repository("basGtnDao")
public class BasGtnDao extends BaseDao<BasGtn, String, BasGtnQuery> {

	public List<BasGtn> getPagedDatagrid(EasyuiDatagridPager pager, BasGtnQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(BasGtnQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(BasGtnQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getGtncode())){
			criteria.add(Restrictions.like("gtncode", query.getGtncode(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}