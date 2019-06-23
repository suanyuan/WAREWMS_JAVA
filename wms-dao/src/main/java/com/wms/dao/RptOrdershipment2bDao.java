package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptOrdershipment2b;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptOrdershipment2bQuery;

@Repository("rptOrdershipment2bDao")
public class RptOrdershipment2bDao extends BaseDao<RptOrdershipment2b, String, RptOrdershipment2bQuery> {

	public List<RptOrdershipment2b> getPagedDatagrid(EasyuiDatagridPager pager, RptOrdershipment2bQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptOrdershipment2bQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptOrdershipment2bQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAddtime())){
			criteria.add(Restrictions.like("addtime", query.getAddtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddtimetext())){
			criteria.add(Restrictions.like("addtimetext", query.getAddtimetext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCAddress1())){
			criteria.add(Restrictions.like("cAddress1", query.getCAddress1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCAddress2())){
			criteria.add(Restrictions.like("cAddress2", query.getCAddress2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCCity())){
			criteria.add(Restrictions.like("cCity", query.getCCity(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCProvince())){
			criteria.add(Restrictions.like("cProvince", query.getCProvince(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCTel1())){
			criteria.add(Restrictions.like("cTel1", query.getCTel1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCTel2())){
			criteria.add(Restrictions.like("cTel2", query.getCTel2(), MatchMode.ANYWHERE));
		}
		if(query.getCnt() != null){
			criteria.add(Restrictions.eq("cnt", query.getCnt()));
		}
		if(StringUtils.isNotEmpty(query.getCodenameC())){
			criteria.add(Restrictions.like("codenameC", query.getCodenameC(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConsigneeid())){
			criteria.add(Restrictions.like("consigneeid", query.getConsigneeid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConsigneename())){
			criteria.add(Restrictions.like("consigneename", query.getConsigneename(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(query.getEdi09() != null){
			criteria.add(Restrictions.eq("edi09", query.getEdi09()));
		}
		if(query.getEdi10() != null){
			criteria.add(Restrictions.eq("edi10", query.getEdi10()));
		}
		if(StringUtils.isNotEmpty(query.getLastshipmenttime())){
			criteria.add(Restrictions.like("lastshipmenttime", query.getLastshipmenttime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLastshipmenttimetext())){
			criteria.add(Restrictions.like("lastshipmenttimetext", query.getLastshipmenttimetext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getNotes())){
			criteria.add(Restrictions.like("notes", query.getNotes(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOrderno())){
			criteria.add(Restrictions.like("orderno", query.getOrderno(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}