package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.QcMeteringDevice;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.QcMeteringDeviceQuery;

@Repository("qcMeteringDeviceDao")
public class QcMeteringDeviceDao extends BaseDao<QcMeteringDevice, String, QcMeteringDeviceQuery> {

	public List<QcMeteringDevice> getPagedDatagrid(EasyuiDatagridPager pager, QcMeteringDeviceQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(QcMeteringDeviceQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(QcMeteringDeviceQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getCalId())){
			criteria.add(Restrictions.like("calId", query.getCalId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCalName())){
			criteria.add(Restrictions.like("calName", query.getCalName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCalNumber())){
			criteria.add(Restrictions.like("calNumber", query.getCalNumber(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCalTerm())){
			criteria.add(Restrictions.like("calTerm", query.getCalTerm(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCalCardUrl())){
			criteria.add(Restrictions.like("calCardUrl", query.getCalCardUrl(), MatchMode.ANYWHERE));
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