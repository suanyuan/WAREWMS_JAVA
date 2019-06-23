package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptOrderdetailHis;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptOrderdetailHisQuery;

@Repository("rptOrderdetailHisDao")
public class RptOrderdetailHisDao extends BaseDao<RptOrderdetailHis, String, RptOrderdetailHisQuery> {

	public List<RptOrderdetailHis> getPagedDatagrid(EasyuiDatagridPager pager, RptOrderdetailHisQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptOrderdetailHisQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptOrderdetailHisQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAddtime())){
			criteria.add(Restrictions.like("addtime", query.getAddtime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddtimetext())){
			criteria.add(Restrictions.like("addtimetext", query.getAddtimetext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCAddress1())){
			criteria.add(Restrictions.like("cAddress1", query.getCAddress1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCAddress2())){
			criteria.add(Restrictions.like("cAddress2", query.getCAddress2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCCity())){
			criteria.add(Restrictions.like("cCity", query.getCCity(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCProvince())){
			criteria.add(Restrictions.like("cProvince", query.getCProvince(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarrierid())){
			criteria.add(Restrictions.like("carrierid", query.getCarrierid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConsigneeid())){
			criteria.add(Restrictions.like("consigneeid", query.getConsigneeid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDescrC())){
			criteria.add(Restrictions.like("descrC", query.getDescrC(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHEdi02())){
			criteria.add(Restrictions.like("hEdi02", query.getHEdi02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHEdi04())){
			criteria.add(Restrictions.like("hEdi04", query.getHEdi04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getIssuepartyid())){
			criteria.add(Restrictions.like("issuepartyid", query.getIssuepartyid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLastshipmenttime())){
			criteria.add(Restrictions.like("lastshipmenttime", query.getLastshipmenttime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLastshipmenttimetext())){
			criteria.add(Restrictions.like("lastshipmenttimetext", query.getLastshipmenttimetext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOrderno())){
			criteria.add(Restrictions.like("orderno", query.getOrderno(), MatchMode.ANYWHERE));
		}
		if(query.getPrice() != null){
			criteria.add(Restrictions.eq("price", query.getPrice()));
		}
		if(query.getQtyordered() != null){
			criteria.add(Restrictions.eq("qtyordered", query.getQtyordered()));
		}
		if(query.getQtyshipped() != null){
			criteria.add(Restrictions.eq("qtyshipped", query.getQtyshipped()));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSoreference1())){
			criteria.add(Restrictions.like("soreference1", query.getSoreference1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSoreference2())){
			criteria.add(Restrictions.like("soreference2", query.getSoreference2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSostatus())){
			criteria.add(Restrictions.like("sostatus", query.getSostatus(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine5())){
			criteria.add(Restrictions.like("userdefine5", query.getUserdefine5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWaveno())){
			criteria.add(Restrictions.like("waveno", query.getWaveno(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}