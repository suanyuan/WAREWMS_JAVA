package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptOrderPackingship;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptOrderPackingshipQuery;

@Repository("rptOrderPackingshipDao")
public class RptOrderPackingshipDao extends BaseDao<RptOrderPackingship, String, RptOrderPackingshipQuery> {

	public List<RptOrderPackingship> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderPackingshipQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptOrderPackingshipQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptOrderPackingshipQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAddtime())){
			criteria.add(Restrictions.like("addtime", query.getAddtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddtimetext())){
			criteria.add(Restrictions.like("addtimetext", query.getAddtimetext(), MatchMode.ANYWHERE));
		}
		if(query.getCube() != null){
			criteria.add(Restrictions.eq("cube", query.getCube()));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDescrC())){
			criteria.add(Restrictions.like("descrC", query.getDescrC(), MatchMode.ANYWHERE));
		}
		if(query.getGrossweight() != null){
			criteria.add(Restrictions.eq("grossweight", query.getGrossweight()));
		}
		if(StringUtils.isNotEmpty(query.getLastshipmenttime())){
			criteria.add(Restrictions.like("lastshipmenttime", query.getLastshipmenttime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLastshipmenttimetext())){
			criteria.add(Restrictions.like("lastshipmenttimetext", query.getLastshipmenttimetext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOrderno())){
			criteria.add(Restrictions.like("orderno", query.getOrderno(), MatchMode.ANYWHERE));
		}
		if(query.getQty() != null){
			criteria.add(Restrictions.eq("qty", query.getQty()));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSoreference1())){
			criteria.add(Restrictions.like("soreference1", query.getSoreference1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSoreference3())){
			criteria.add(Restrictions.like("soreference3", query.getSoreference3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTraceid())){
			criteria.add(Restrictions.like("traceid", query.getTraceid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}