package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.ViewInvHold;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.ViewInvHoldQuery;

@Repository("viewInvHoldDao")
public class ViewInvHoldDao extends BaseDao<ViewInvHold, String, ViewInvHoldQuery> {

	public List<ViewInvHold> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvHoldQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(ViewInvHoldQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(ViewInvHoldQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomername())){
			criteria.add(Restrictions.like("customername", query.getCustomername(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFromtime())){
			criteria.add(Restrictions.like("fromtime", query.getFromtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHoldby())){
			criteria.add(Restrictions.like("holdby", query.getHoldby(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHoldbyName())){
			criteria.add(Restrictions.like("holdbyName", query.getHoldbyName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHoldcode())){
			criteria.add(Restrictions.like("holdcode", query.getHoldcode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHoldcodeName())){
			criteria.add(Restrictions.like("holdcodeName", query.getHoldcodeName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHoldflag())){
			criteria.add(Restrictions.like("holdflag", query.getHoldflag(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getInventoryholdid())){
			criteria.add(Restrictions.like("inventoryholdid", query.getInventoryholdid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLocationid())){
			criteria.add(Restrictions.like("locationid", query.getLocationid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt01())){
			criteria.add(Restrictions.like("lotatt01", query.getLotatt01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt02())){
			criteria.add(Restrictions.like("lotatt02", query.getLotatt02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt03())){
			criteria.add(Restrictions.like("lotatt03", query.getLotatt03(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt04())){
			criteria.add(Restrictions.like("lotatt04", query.getLotatt04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt05())){
			criteria.add(Restrictions.like("lotatt05", query.getLotatt05(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt06())){
			criteria.add(Restrictions.like("lotatt06", query.getLotatt06(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt07())){
			criteria.add(Restrictions.like("lotatt07", query.getLotatt07(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt08())){
			criteria.add(Restrictions.like("lotatt08", query.getLotatt08(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt09())){
			criteria.add(Restrictions.like("lotatt09", query.getLotatt09(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt10())){
			criteria.add(Restrictions.like("lotatt10", query.getLotatt10(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt11())){
			criteria.add(Restrictions.like("lotatt11", query.getLotatt11(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt12())){
			criteria.add(Restrictions.like("lotatt12", query.getLotatt12(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotnum())){
			criteria.add(Restrictions.like("lotnum", query.getLotnum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOfffromtime())){
			criteria.add(Restrictions.like("offfromtime", query.getOfffromtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReason())){
			criteria.add(Restrictions.like("reason", query.getReason(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkudescrc())){
			criteria.add(Restrictions.like("skudescrc", query.getSkudescrc(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTraceid())){
			criteria.add(Restrictions.like("traceid", query.getTraceid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWhooff())){
			criteria.add(Restrictions.like("whooff", query.getWhooff(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWhoon())){
			criteria.add(Restrictions.like("whoon", query.getWhoon(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}