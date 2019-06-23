package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptPackPerformance;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptPackPerformanceQuery;

@Repository("rptPackPerformanceDao")
public class RptPackPerformanceDao extends BaseDao<RptPackPerformance, String, RptPackPerformanceQuery> {

	public List<RptPackPerformance> getPagedDatagrid(EasyuiDatagridPager pager, RptPackPerformanceQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptPackPerformanceQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptPackPerformanceQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(query.getFee() != null){
			criteria.add(Restrictions.eq("fee", query.getFee()));
		}
		if(StringUtils.isNotEmpty(query.getIssuepartyname())){
			criteria.add(Restrictions.like("issuepartyname", query.getIssuepartyname(), MatchMode.ANYWHERE));
		}
		if(query.getMulirate() != null){
			criteria.add(Restrictions.eq("mulirate", query.getMulirate()));
		}
		if(StringUtils.isNotEmpty(query.getOrderType())){
			criteria.add(Restrictions.like("orderType", query.getOrderType(), MatchMode.ANYWHERE));
		}
		if(query.getQty() != null){
			criteria.add(Restrictions.eq("qty", query.getQty()));
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