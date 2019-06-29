package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.BasCarrierLicense;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.BasCarrierLicenseQuery;

@Repository("basCarrierLicenseDao")
public class BasCarrierLicenseDao extends BaseDao<BasCarrierLicense, String, BasCarrierLicenseQuery> {

	public List<BasCarrierLicense> getPagedDatagrid(EasyuiDatagridPager pager, BasCarrierLicenseQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(BasCarrierLicenseQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(BasCarrierLicenseQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(query.getCarrierLicenseId() != null){
			criteria.add(Restrictions.eq("carrierLicenseId", query.getCarrierLicenseId()));
		}
		if(StringUtils.isNotEmpty(query.getRoadNumber())){
			criteria.add(Restrictions.like("roadNumber", query.getRoadNumber(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRoadNumberTerm())){
			criteria.add(Restrictions.like("roadNumberTerm", query.getRoadNumberTerm(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRoadAuthorityPermit())){
			criteria.add(Restrictions.like("roadAuthorityPermit", query.getRoadAuthorityPermit(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRoadBusinessScope())){
			criteria.add(Restrictions.like("roadBusinessScope", query.getRoadBusinessScope(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarrierNo())){
			criteria.add(Restrictions.like("carrierNo", query.getCarrierNo(), MatchMode.ANYWHERE));
		}
		if(query.getClientTerm() != null){
			criteria.add(Restrictions.eq("clientTerm", query.getClientTerm()));
		}
		if(StringUtils.isNotEmpty(query.getCarrierAuthorityPermit())){
			criteria.add(Restrictions.like("carrierAuthorityPermit", query.getCarrierAuthorityPermit(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarrierBusinessScope())){
			criteria.add(Restrictions.like("carrierBusinessScope", query.getCarrierBusinessScope(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCreateId())){
			criteria.add(Restrictions.like("createId", query.getCreateId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditId())){
			criteria.add(Restrictions.like("editId", query.getEditId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getActiveFlag())){
			criteria.add(Restrictions.like("activeFlag", query.getActiveFlag(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}