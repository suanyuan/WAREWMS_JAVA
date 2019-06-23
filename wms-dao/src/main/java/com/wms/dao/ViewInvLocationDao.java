package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.ViewInvLocation;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.ViewInvLocationQuery;

@Repository("viewInvLocationDao")
public class ViewInvLocationDao extends BaseDao<ViewInvLocation, String, ViewInvLocationQuery> {

	public List<ViewInvLocation> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvLocationQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(ViewInvLocationQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("fmcustomerid"));//
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(ViewInvLocationQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getFmcustomerid())){
			criteria.add(Restrictions.like("fmcustomerid", query.getFmcustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlocation())){
			criteria.add(Restrictions.like("fmlocation", query.getFmlocation(), MatchMode.ANYWHERE));
		}
		if(query.getFmqty() != null){
			criteria.add(Restrictions.eq("fmqty", query.getFmqty()));
		}
		if(StringUtils.isNotEmpty(query.getFmsku())){
			criteria.add(Restrictions.like("fmsku", query.getFmsku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmuomName())){
			criteria.add(Restrictions.like("fmuomName", query.getFmuomName(), MatchMode.ANYWHERE));
		}
		if(query.getIMv() != null){
			criteria.add(Restrictions.eq("iMv", query.getIMv()));
		}
		if(query.getIPa() != null){
			criteria.add(Restrictions.eq("iPa", query.getIPa()));
		}
		if(query.getIRp() != null){
			criteria.add(Restrictions.eq("iRp", query.getIRp()));
		}
		if(query.getOMv() != null){
			criteria.add(Restrictions.eq("oMv", query.getOMv()));
		}
		if(query.getORp() != null){
			criteria.add(Restrictions.eq("oRp", query.getORp()));
		}
		if(query.getQtyallocated() != null){
			criteria.add(Restrictions.eq("qtyallocated", query.getQtyallocated()));
		}
		if(query.getQtyavailed() != null){
			criteria.add(Restrictions.eq("qtyavailed", query.getQtyavailed()));
		}
		if(query.getQtyholded() != null){
			criteria.add(Restrictions.eq("qtyholded", query.getQtyholded()));
		}
		if(StringUtils.isNotEmpty(query.getSkudescrc())){
			criteria.add(Restrictions.like("skudescrc", query.getSkudescrc(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkudescre())){
			criteria.add(Restrictions.like("skudescre", query.getSkudescre(), MatchMode.ANYWHERE));
		}
		if(query.getTotalcubic() != null){
			criteria.add(Restrictions.eq("totalcubic", query.getTotalcubic()));
		}
		if(query.getTotalgrossweight() != null){
			criteria.add(Restrictions.eq("totalgrossweight", query.getTotalgrossweight()));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}