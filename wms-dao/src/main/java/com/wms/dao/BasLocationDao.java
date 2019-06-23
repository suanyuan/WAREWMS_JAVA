package com.wms.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.entity.BasLocation;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.BasLocationQuery;

@Repository("basLocationDao")
public class BasLocationDao extends BaseDao<BasLocation, String, BasLocationQuery> {

	public List<BasLocation> getPagedDatagrid(EasyuiDatagridPager pager, BasLocationQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(BasLocationQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(BasLocationQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getLocationid())){
			criteria.add(Restrictions.like("locationid", query.getLocationid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(query.getCountcapacity() != null){
			criteria.add(Restrictions.eq("countcapacity", query.getCountcapacity()));
		}
		if(query.getCscount() != null){
			criteria.add(Restrictions.eq("cscount", query.getCscount()));
		}
		if(query.getCubiccapacity() != null){
			criteria.add(Restrictions.eq("cubiccapacity", query.getCubiccapacity()));
		}
		if(StringUtils.isNotEmpty(query.getDemand())){
			criteria.add(Restrictions.like("demand", query.getDemand(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEnvironment())){
			criteria.add(Restrictions.like("environment", query.getEnvironment(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFacilityId())){
			criteria.add(Restrictions.like("facilityId", query.getFacilityId(), MatchMode.ANYWHERE));
		}
		if(query.getHeight() != null){
			criteria.add(Restrictions.eq("height", query.getHeight()));
		}
		if(query.getLength() != null){
			criteria.add(Restrictions.eq("length", query.getLength()));
		}
		if(StringUtils.isNotEmpty(query.getLocationattribute())){
			criteria.add(Restrictions.like("locationattribute", query.getLocationattribute(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLocationcategory())){
			criteria.add(Restrictions.like("locationcategory", query.getLocationcategory(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLocationhandling())){
			criteria.add(Restrictions.like("locationhandling", query.getLocationhandling(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLocationusage())){
			criteria.add(Restrictions.like("locationusage", query.getLocationusage(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLocgroup1())){
			criteria.add(Restrictions.like("locgroup1", query.getLocgroup1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLocgroup2())){
			criteria.add(Restrictions.like("locgroup2", query.getLocgroup2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLoclevel())){
			criteria.add(Restrictions.like("loclevel", query.getLoclevel(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLogicalsequence())){
			criteria.add(Restrictions.like("logicalsequence", query.getLogicalsequence(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLoseidFlag())){
			criteria.add(Restrictions.like("loseidFlag", query.getLoseidFlag(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getMixFlag())){
			criteria.add(Restrictions.like("mixFlag", query.getMixFlag(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getMixLotflag())){
			criteria.add(Restrictions.like("mixLotflag", query.getMixLotflag(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPicklogicalsequence())){
			criteria.add(Restrictions.like("picklogicalsequence", query.getPicklogicalsequence(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPickzone())){
			criteria.add(Restrictions.like("pickzone", query.getPickzone(), MatchMode.ANYWHERE));
		}
		if(query.getPlcount() != null){
			criteria.add(Restrictions.eq("plcount", query.getPlcount()));
		}
		if(StringUtils.isNotEmpty(query.getPutawayzone())){
			criteria.add(Restrictions.like("putawayzone", query.getPutawayzone(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getStatus())){
			criteria.add(Restrictions.like("status", query.getStatus(), MatchMode.ANYWHERE));
		}
		if(query.getWeightcapacity() != null){
			criteria.add(Restrictions.eq("weightcapacity", query.getWeightcapacity()));
		}
		if(query.getWidth() != null){
			criteria.add(Restrictions.eq("width", query.getWidth()));
		}
		if(query.getXcoord() != null){
			criteria.add(Restrictions.eq("xcoord", query.getXcoord()));
		}
		if(query.getXdistance() != null){
			criteria.add(Restrictions.eq("xdistance", query.getXdistance()));
		}
		if(query.getYcoord() != null){
			criteria.add(Restrictions.eq("ycoord", query.getYcoord()));
		}
		if(query.getYdistance() != null){
			criteria.add(Restrictions.eq("ydistance", query.getYdistance()));
		}
		if(query.getZcoord() != null){
			criteria.add(Restrictions.eq("zcoord", query.getZcoord()));
		}
		criteria.addOrder(Order.asc("locationid"));
		return criteria;
	}
}