package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.Btn;
import com.wms.query.BtnQuery;

@Repository("btnDao")
public class BtnDao extends BaseDao<Btn, String, BtnQuery> {

	public List<Btn> getPagedDatagrid(EasyuiDatagridPager pager, BtnQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(BtnQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(BtnQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(!StringUtils.isEmpty(query.getBtnName())){
			criteria.add(Restrictions.like("btnName", sb.append("%").append(query.getBtnName()).append("%").toString()));sb.setLength(0);
		}
		if(!StringUtils.isEmpty(query.getBtnChsName())){
			criteria.add(Restrictions.like("btnChsName", sb.append("%").append(query.getBtnChsName()).append("%").toString()));sb.setLength(0);
		}
		if(query.getBtnLevel() != null){
			criteria.add(Restrictions.eq("btnLevel", query.getBtnLevel()));
		}
		return criteria;
	}
}