package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.GspEnterpriseInfoQuery;

@Repository("gspEnterpriseInfoDao")
public class GspEnterpriseInfoDao extends BaseDao<GspEnterpriseInfo, String, GspEnterpriseInfoQuery> {

	public List<GspEnterpriseInfo> getPagedDatagrid(EasyuiDatagridPager pager, GspEnterpriseInfoQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//criteria.addOrder(Order.desc("createDate"));
		return criteria.list();
	}

	public Long countAll(GspEnterpriseInfoQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(GspEnterpriseInfoQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		/*if(query.getEnterpriseId() != null){
			criteria.add(Restrictions.eq("enterpriseId", query.getEnterpriseId()));
		}*/
		if(StringUtils.isNotEmpty(query.getEnterpriseNo())){
			criteria.add(Restrictions.like("enterpriseNo", query.getEnterpriseNo(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getShorthandName())){
			criteria.add(Restrictions.like("shorthandName", query.getShorthandName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEnterpriseName())){
			criteria.add(Restrictions.like("enterpriseName", query.getEnterpriseName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEnterpriseType())){
			criteria.add(Restrictions.like("enterpriseType", query.getEnterpriseType(), MatchMode.ANYWHERE));
		}
		/*if(StringUtils.isNotEmpty(query.getContacts())){
			criteria.add(Restrictions.like("contacts", query.getContacts(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContactsPhone())){
			criteria.add(Restrictions.like("contactsPhone", query.getContactsPhone(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRemark())){
			criteria.add(Restrictions.like("remark", query.getRemark(), MatchMode.ANYWHERE));
		}
		if(query.getCreateId() != null){
			criteria.add(Restrictions.eq("createId", query.getCreateId()));
		}
		if(query.getEditId() != null){
			criteria.add(Restrictions.eq("editId", query.getEditId()));
		}*/
		if(StringUtils.isNotEmpty(query.getIsUse())){
			criteria.add(Restrictions.like("isUse", query.getIsUse(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}