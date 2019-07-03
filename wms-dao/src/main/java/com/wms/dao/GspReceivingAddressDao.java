package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.GspReceivingAddress;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.GspReceivingAddressQuery;

@Repository("gspReceivingAddressDao")
public class GspReceivingAddressDao extends BaseDao<GspReceivingAddress, String, GspReceivingAddressQuery> {

	public List<GspReceivingAddress> getPagedDatagrid(EasyuiDatagridPager pager, GspReceivingAddressQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(GspReceivingAddressQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(GspReceivingAddressQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getReceivingAddressId())){
			criteria.add(Restrictions.like("receivingAddressId", query.getReceivingAddressId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReceivingId())){
			criteria.add(Restrictions.like("receivingId", query.getReceivingId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSellerName())){
			criteria.add(Restrictions.like("sellerName", query.getSellerName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCountry())){
			criteria.add(Restrictions.like("country", query.getCountry(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProvince())){
			criteria.add(Restrictions.like("province", query.getProvince(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCity())){
			criteria.add(Restrictions.like("city", query.getCity(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDistrict())){
			criteria.add(Restrictions.like("district", query.getDistrict(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDeliveryAddress())){
			criteria.add(Restrictions.like("deliveryAddress", query.getDeliveryAddress(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getZipcode())){
			criteria.add(Restrictions.like("zipcode", query.getZipcode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContacts())){
			criteria.add(Restrictions.like("contacts", query.getContacts(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPhone())){
			criteria.add(Restrictions.like("phone", query.getPhone(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getIsDefault())){
			criteria.add(Restrictions.like("isDefault", query.getIsDefault(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCreateId())){
			criteria.add(Restrictions.like("createId", query.getCreateId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditId())){
			criteria.add(Restrictions.like("editId", query.getEditId(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}