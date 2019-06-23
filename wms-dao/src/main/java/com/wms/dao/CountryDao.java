package com.wms.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.Country;
import com.wms.query.CountryQuery;

@Repository("countryDao")
public class CountryDao extends BaseDao<Country, Integer, CountryQuery> {

	@SuppressWarnings("unchecked")
	public List<Country> getPagedDatagrid(EasyuiDatagridPager pager, CountryQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.addOrder(Order.asc("countryEngName"));
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	public Long countAll(CountryQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}
	
	protected Criteria getQueryCriteria(CountryQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(!StringUtils.isEmpty(query.getCountryName())){
			criteria.add(Restrictions.like("countryName", sb.append("%").append(query.getCountryName()).append("%").toString()));sb.setLength(0);
		}
		if(!StringUtils.isEmpty(query.getCountryEngName())){
			criteria.add(Restrictions.like("countryEngName", sb.append("%").append(query.getCountryEngName()).append("%").toString()));sb.setLength(0);
		}
		return criteria;
	}
	
	public int getCountryId() {
		return (Integer)this.getUniqueResultQuery("select max(id) from Country") + 1;
	}
}
