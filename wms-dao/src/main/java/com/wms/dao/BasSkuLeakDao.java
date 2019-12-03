package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.BasSkuLeak;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.BasSkuLeakQuery;

@Repository("basSkuLeakDao")
public class BasSkuLeakDao extends BaseDao<BasSkuLeak, String, BasSkuLeakQuery> {

	public List<BasSkuLeak> getPagedDatagrid(EasyuiDatagridPager pager, BasSkuLeakQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(BasSkuLeakQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(BasSkuLeakQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getStandard())){
			criteria.add(Restrictions.like("standard", query.getStandard(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt06())){
			criteria.add(Restrictions.like("lotatt06", query.getLotatt06(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt11())){
			criteria.add(Restrictions.like("lotatt11", query.getLotatt11(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUnit())){
			criteria.add(Restrictions.like("unit", query.getUnit(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductline())){
			criteria.add(Restrictions.like("productline", query.getProductline(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplier())){
			criteria.add(Restrictions.like("supplier", query.getSupplier(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getUserdefine4())){
			criteria.add(Restrictions.like("userdefine4", query.getUserdefine4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine5())){
			criteria.add(Restrictions.like("userdefine5", query.getUserdefine5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}