package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.UserSession;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.UserSessionQuery;

@Repository("userSessionDao")
public class UserSessionDao extends BaseDao<UserSession, String, UserSessionQuery> {

	public List<UserSession> getPagedDatagrid(EasyuiDatagridPager pager, UserSessionQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(UserSessionQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(UserSessionQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getUserId())){
			criteria.add(Restrictions.like("userId", query.getUserId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserSession())){
			criteria.add(Restrictions.like("userSession", query.getUserSession(), MatchMode.ANYWHERE));
		}
		if(query.getState() != null){
			criteria.add(Restrictions.eq("state", query.getState()));
		}
		return criteria;
	}
}