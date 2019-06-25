package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.GspBusinessLicense;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.GspBusinessLicenseQuery;

@Repository("gspBusinessLicenseDao")
public class GspBusinessLicenseDao extends BaseDao<GspBusinessLicense, String, GspBusinessLicenseQuery> {

	public List<GspBusinessLicense> getPagedDatagrid(EasyuiDatagridPager pager, GspBusinessLicenseQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(GspBusinessLicenseQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(GspBusinessLicenseQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(query.getBusinessId() != null){
			criteria.add(Restrictions.eq("businessId", query.getBusinessId()));
		}
		if(query.getEnterpriseId() != null){
			criteria.add(Restrictions.eq("enterpriseId", query.getEnterpriseId()));
		}
		if(StringUtils.isNotEmpty(query.getLicenseNumber())){
			criteria.add(Restrictions.like("licenseNumber", query.getLicenseNumber(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSocialCreditCode())){
			criteria.add(Restrictions.like("socialCreditCode", query.getSocialCreditCode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLicenseName())){
			criteria.add(Restrictions.like("licenseName", query.getLicenseName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLicenseType())){
			criteria.add(Restrictions.like("licenseType", query.getLicenseType(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getResidence())){
			criteria.add(Restrictions.like("residence", query.getResidence(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getJuridicalPerson())){
			criteria.add(Restrictions.like("juridicalPerson", query.getJuridicalPerson(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRegisteredCapital())){
			criteria.add(Restrictions.like("registeredCapital", query.getRegisteredCapital(), MatchMode.ANYWHERE));
		}
		if(query.getIsLong() != null){
			criteria.add(Restrictions.eq("isLong", query.getIsLong()));
		}
		if(StringUtils.isNotEmpty(query.getBusinessScope())){
			criteria.add(Restrictions.like("businessScope", query.getBusinessScope(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRegistrationAuthority())){
			criteria.add(Restrictions.like("registrationAuthority", query.getRegistrationAuthority(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAttachmentUrl())){
			criteria.add(Restrictions.like("attachmentUrl", query.getAttachmentUrl(), MatchMode.ANYWHERE));
		}
		if(query.getCreateId() != null){
			criteria.add(Restrictions.eq("createId", query.getCreateId()));
		}
		if(query.getEditId() != null){
			criteria.add(Restrictions.eq("editId", query.getEditId()));
		}
		if(StringUtils.isNotEmpty(query.getIsUse())){
			criteria.add(Restrictions.like("isUse", query.getIsUse(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}