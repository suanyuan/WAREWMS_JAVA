package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptOrderPackingPerform;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptOrderPackingPerformQuery;

@Repository("rptOrderPackingPerformDao")
public class RptOrderPackingPerformDao extends BaseDao<RptOrderPackingPerform, String, RptOrderPackingPerformQuery> {

	public List<RptOrderPackingPerform> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderPackingPerformQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptOrderPackingPerformQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptOrderPackingPerformQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(query.getQtyordered() != null){
			criteria.add(Restrictions.eq("qtyordered", query.getQtyordered()));
		}
		if(StringUtils.isNotEmpty(query.getSdate())){
			criteria.add(Restrictions.like("sdate", query.getSdate(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSdatetext())){
			criteria.add(Restrictions.like("sdatetext", query.getSdatetext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTtim())){
			criteria.add(Restrictions.like("ttim", query.getTtim(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserId())){
			criteria.add(Restrictions.like("userId", query.getUserId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserName())){
			criteria.add(Restrictions.like("userName", query.getUserName(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}