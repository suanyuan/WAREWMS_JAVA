package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.DocPkRecords;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocPkRecordsQuery;

@Repository("docPkRecordsDao")
public class DocPkRecordsDao extends BaseDao<DocPkRecords, String, DocPkRecordsQuery> {

	public List<DocPkRecords> getPagedDatagrid(EasyuiDatagridPager pager, DocPkRecordsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(DocPkRecordsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(DocPkRecordsQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getOrderno())){
			criteria.add(Restrictions.like("orderno", query.getOrderno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkudesce())){
			criteria.add(Restrictions.like("skudesce", query.getSkudesce(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAllocationdetailsid())){
			criteria.add(Restrictions.like("allocationdetailsid", query.getAllocationdetailsid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLocationid())){
			criteria.add(Restrictions.like("locationid", query.getLocationid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotnum())){
			criteria.add(Restrictions.like("lotnum", query.getLotnum(), MatchMode.ANYWHERE));
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