package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptInOut;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptInOutQuery;

@Repository("rptInOutDao")
public class RptInOutDao extends BaseDao<RptInOut, String, RptInOutQuery> {

	public List<RptInOut> getPagedDatagrid(EasyuiDatagridPager pager, RptInOutQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptInOutQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptInOutQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(query.getAdqty() != null){
			criteria.add(Restrictions.eq("adqty", query.getAdqty()));
		}
		if(StringUtils.isNotEmpty(query.getCommodity())){
			criteria.add(Restrictions.like("commodity", query.getCommodity(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDescrC())){
			criteria.add(Restrictions.like("descrC", query.getDescrC(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDescrE())){
			criteria.add(Restrictions.like("descrE", query.getDescrE(), MatchMode.ANYWHERE));
		}
		if(query.getFvqty() != null){
			criteria.add(Restrictions.eq("fvqty", query.getFvqty()));
		}
		if(query.getIbqty() != null){
			criteria.add(Restrictions.eq("ibqty", query.getIbqty()));
		}
		if(query.getIvqty() != null){
			criteria.add(Restrictions.eq("ivqty", query.getIvqty()));
		}
		if(query.getObqty() != null){
			criteria.add(Restrictions.eq("obqty", query.getObqty()));
		}
		if(StringUtils.isNotEmpty(query.getPackid())){
			criteria.add(Restrictions.like("packid", query.getPackid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPackuom1())){
			criteria.add(Restrictions.like("packuom1", query.getPackuom1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkuDescrC())){
			criteria.add(Restrictions.like("skuDescrC", query.getSkuDescrC(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkuDescrE())){
			criteria.add(Restrictions.like("skuDescrE", query.getSkuDescrE(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkutext())){
			criteria.add(Restrictions.like("skutext", query.getSkutext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getStartdate())){
			criteria.add(Restrictions.like("startdate", query.getStartdate(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getStartdatetext())){
			criteria.add(Restrictions.like("startdatetext", query.getStartdatetext(), MatchMode.ANYWHERE));
		}
		if(query.getTrqty() != null){
			criteria.add(Restrictions.eq("trqty", query.getTrqty()));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine1())){
			criteria.add(Restrictions.like("userdefine1", query.getUserdefine1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine2())){
			criteria.add(Restrictions.like("userdefine2", query.getUserdefine2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine3())){
			criteria.add(Restrictions.like("userdefine3", query.getUserdefine3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserid())){
			criteria.add(Restrictions.like("userid", query.getUserid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}