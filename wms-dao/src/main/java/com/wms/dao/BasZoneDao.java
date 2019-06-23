package com.wms.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.entity.BasZone;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.BasZoneQuery;

@Repository("basZoneDao")
public class BasZoneDao extends BaseDao<BasZone, String, BasZoneQuery> {

	public List<BasZone> getPagedDatagrid(EasyuiDatagridPager pager, BasZoneQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(BasZoneQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(BasZoneQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getZone())){
			criteria.add(Restrictions.like("zone", query.getZone(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAllowCsRpl())){
			criteria.add(Restrictions.like("allowCsRpl", query.getAllowCsRpl(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAllowEaRpl())){
			criteria.add(Restrictions.like("allowEaRpl", query.getAllowEaRpl(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getBaselocation())){
			criteria.add(Restrictions.like("baselocation", query.getBaselocation(), MatchMode.ANYWHERE));
		}
		if(query.getCubage() != null){
			criteria.add(Restrictions.eq("cubage", query.getCubage()));
		}
		if(StringUtils.isNotEmpty(query.getDescr())){
			criteria.add(Restrictions.like("descr", query.getDescr(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFacilityId())){
			criteria.add(Restrictions.like("facilityId", query.getFacilityId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPicktoloc())){
			criteria.add(Restrictions.like("picktoloc", query.getPicktoloc(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPutawaytoloc())){
			criteria.add(Restrictions.like("putawaytoloc", query.getPutawaytoloc(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRoute())){
			criteria.add(Restrictions.like("route", query.getRoute(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getZonegroup())){
			criteria.add(Restrictions.like("zonegroup", query.getZonegroup(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}