package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.ViewAsnDetail;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.ViewAsnDetailQuery;

@Repository("viewAsnDetailDao")
public class ViewAsnDetailDao extends BaseDao<ViewAsnDetail, String, ViewAsnDetailQuery> {

	public List<ViewAsnDetail> getPagedDatagrid(EasyuiDatagridPager pager, ViewAsnDetailQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(ViewAsnDetailQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(ViewAsnDetailQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAddtime())){
			criteria.add(Restrictions.like("addtime", query.getAddtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnno())){
			criteria.add(Restrictions.like("asnno", query.getAsnno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnreference1())){
			criteria.add(Restrictions.like("asnreference1", query.getAsnreference1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCodenameC())){
			criteria.add(Restrictions.like("codenameC", query.getCodenameC(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCodenameC1())){
			criteria.add(Restrictions.like("codenameC1", query.getCodenameC1(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getEdittime())){
			criteria.add(Restrictions.like("edittime", query.getEdittime(), MatchMode.ANYWHERE));
		}
		if(query.getExpectedqty() != null){
			criteria.add(Restrictions.eq("expectedqty", query.getExpectedqty()));
		}
		if(StringUtils.isNotEmpty(query.getLastreceivingtime())){
			criteria.add(Restrictions.like("lastreceivingtime", query.getLastreceivingtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getNotes())){
			criteria.add(Restrictions.like("notes", query.getNotes(), MatchMode.ANYWHERE));
		}
		if(query.getReceivedqty() != null){
			criteria.add(Restrictions.eq("receivedqty", query.getReceivedqty()));
		}
		if(StringUtils.isNotEmpty(query.getReceivedtime())){
			criteria.add(Restrictions.like("receivedtime", query.getReceivedtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReservedfield01())){
			criteria.add(Restrictions.like("reservedfield01", query.getReservedfield01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine2())){
			criteria.add(Restrictions.like("userdefine2", query.getUserdefine2(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}