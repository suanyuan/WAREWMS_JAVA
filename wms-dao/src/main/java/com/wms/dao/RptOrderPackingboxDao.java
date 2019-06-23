package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptOrderPackingbox;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptOrderPackingboxQuery;

@Repository("rptOrderPackingboxDao")
public class RptOrderPackingboxDao extends BaseDao<RptOrderPackingbox, String, RptOrderPackingboxQuery> {

	public List<RptOrderPackingbox> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderPackingboxQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptOrderPackingboxQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptOrderPackingboxQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAddress())){
			criteria.add(Restrictions.like("address", query.getAddress(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddtime())){
			criteria.add(Restrictions.like("addtime", query.getAddtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternateSku1())){
			criteria.add(Restrictions.like("alternateSku1", query.getAlternateSku1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarrierid())){
			criteria.add(Restrictions.like("carrierid", query.getCarrierid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConsigneeid())){
			criteria.add(Restrictions.like("consigneeid", query.getConsigneeid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDescrC())){
			criteria.add(Restrictions.like("descrC", query.getDescrC(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getSoreference2())){
			criteria.add(Restrictions.like("soreference2", query.getSoreference2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTel())){
			criteria.add(Restrictions.like("tel", query.getTel(), MatchMode.ANYWHERE));
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