package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.WaybillStatistics;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.WaybillStatisticsQuery;

@Repository("waybillStatisticsDao")
public class WaybillStatisticsDao extends BaseDao<WaybillStatistics, String, WaybillStatisticsQuery> {

	public List<WaybillStatistics> getPagedDatagrid(EasyuiDatagridPager pager, WaybillStatisticsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(WaybillStatisticsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(WaybillStatisticsQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getEnterpriseId())){
			criteria.add(Restrictions.like("enterpriseId", query.getEnterpriseId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarrierName())){
			criteria.add(Restrictions.like("carrierName", query.getCarrierName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getYear())){
			criteria.add(Restrictions.like("year", query.getYear(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getMonth())){
			criteria.add(Restrictions.like("month", query.getMonth(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDay())){
			criteria.add(Restrictions.like("day", query.getDay(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOrderNum())){
			criteria.add(Restrictions.like("orderNum", query.getOrderNum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getComplaintNum())){
			criteria.add(Restrictions.like("complaintNum", query.getComplaintNum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getMissingNum())){
			criteria.add(Restrictions.like("missingNum", query.getMissingNum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDamageNum())){
			criteria.add(Restrictions.like("damageNum", query.getDamageNum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLagNum())){
			criteria.add(Restrictions.like("lagNum", query.getLagNum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOtherNum())){
			criteria.add(Restrictions.like("otherNum", query.getOtherNum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRemark())){
			criteria.add(Restrictions.like("remark", query.getRemark(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCreateId())){
			criteria.add(Restrictions.like("createId", query.getCreateId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditId())){
			criteria.add(Restrictions.like("editId", query.getEditId(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefind1())){
			criteria.add(Restrictions.like("userdefind1", query.getUserdefind1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefind2())){
			criteria.add(Restrictions.like("userdefind2", query.getUserdefind2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefind3())){
			criteria.add(Restrictions.like("userdefind3", query.getUserdefind3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefind4())){
			criteria.add(Restrictions.like("userdefind4", query.getUserdefind4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefind5())){
			criteria.add(Restrictions.like("userdefind5", query.getUserdefind5(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}