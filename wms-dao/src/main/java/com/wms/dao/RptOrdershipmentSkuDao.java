package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptOrdershipmentSku;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptOrdershipmentSkuQuery;

@Repository("rptOrdershipmentSkuDao")
public class RptOrdershipmentSkuDao extends BaseDao<RptOrdershipmentSku, String, RptOrdershipmentSkuQuery> {

	public List<RptOrdershipmentSku> getPagedDatagrid(EasyuiDatagridPager pager, RptOrdershipmentSkuQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptOrdershipmentSkuQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptOrdershipmentSkuQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(query.getCnt() != null){
			criteria.add(Restrictions.eq("cnt", query.getCnt()));
		}
		if(StringUtils.isNotEmpty(query.getCodenameC())){
			criteria.add(Restrictions.like("codenameC", query.getCodenameC(), MatchMode.ANYWHERE));
		}
		if(query.getCube() != null){
			criteria.add(Restrictions.eq("cube", query.getCube()));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDeliveryno())){
			criteria.add(Restrictions.like("deliveryno", query.getDeliveryno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getIssuepartyid())){
			criteria.add(Restrictions.like("issuepartyid", query.getIssuepartyid(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getOrdertype())){
			criteria.add(Restrictions.like("ordertype", query.getOrdertype(), MatchMode.ANYWHERE));
		}
		if(query.getQtyEach() != null){
			criteria.add(Restrictions.eq("qtyEach", query.getQtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getSoreference1())){
			criteria.add(Restrictions.like("soreference1", query.getSoreference1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSoreference2())){
			criteria.add(Restrictions.like("soreference2", query.getSoreference2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSostatus())){
			criteria.add(Restrictions.like("sostatus", query.getSostatus(), MatchMode.ANYWHERE));
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