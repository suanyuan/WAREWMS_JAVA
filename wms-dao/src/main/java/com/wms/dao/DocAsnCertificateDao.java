package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.DocAsnCertificate;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocAsnCertificateQuery;

@Repository("docAsnCertificateDao")
public class DocAsnCertificateDao extends BaseDao<DocAsnCertificate, String, DocAsnCertificateQuery> {

	public List<DocAsnCertificate> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnCertificateQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(DocAsnCertificateQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(DocAsnCertificateQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt04())){
			criteria.add(Restrictions.like("lotatt04", query.getLotatt04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCertificateContext())){
			criteria.add(Restrictions.like("certificateContext", query.getCertificateContext(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}