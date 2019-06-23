package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptSoDaily;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptSoDailyQuery;

@Repository("rptSoDailyDao")
public class RptSoDailyDao extends BaseDao<RptSoDaily, String, RptSoDailyQuery> {

	public List<RptSoDaily> getPagedDatagrid(EasyuiDatagridPager pager, RptSoDailyQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptSoDailyQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptSoDailyQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAddress())){
			criteria.add(Restrictions.like("address", query.getAddress(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnreference1inreport())){
			criteria.add(Restrictions.like("asnreference1inreport", query.getAsnreference1inreport(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnreference2inreport())){
			criteria.add(Restrictions.like("asnreference2inreport", query.getAsnreference2inreport(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnreference3inreport())){
			criteria.add(Restrictions.like("asnreference3inreport", query.getAsnreference3inreport(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarriername())){
			criteria.add(Restrictions.like("carriername", query.getCarriername(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCommodity())){
			criteria.add(Restrictions.like("commodity", query.getCommodity(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConsigneename())){
			criteria.add(Restrictions.like("consigneename", query.getConsigneename(), MatchMode.ANYWHERE));
		}
		if(query.getCubic() != null){
			criteria.add(Restrictions.eq("cubic", query.getCubic()));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(query.getDoclineno() != null){
			criteria.add(Restrictions.eq("doclineno", query.getDoclineno()));
		}
		if(StringUtils.isNotEmpty(query.getExpdeliverytime())){
			criteria.add(Restrictions.like("expdeliverytime", query.getExpdeliverytime(), MatchMode.ANYWHERE));
		}
		if(query.getGrossweight() != null){
			criteria.add(Restrictions.eq("grossweight", query.getGrossweight()));
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
		if(StringUtils.isNotEmpty(query.getNotes())){
			criteria.add(Restrictions.like("notes", query.getNotes(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOrderno())){
			criteria.add(Restrictions.like("orderno", query.getOrderno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOrdertime())){
			criteria.add(Restrictions.like("ordertime", query.getOrdertime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOrdertype())){
			criteria.add(Restrictions.like("ordertype", query.getOrdertype(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPackid())){
			criteria.add(Restrictions.like("packid", query.getPackid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPackuom1())){
			criteria.add(Restrictions.like("packuom1", query.getPackuom1(), MatchMode.ANYWHERE));
		}
		if(query.getQtyordered() != null){
			criteria.add(Restrictions.eq("qtyordered", query.getQtyordered()));
		}
		if(query.getQtyshipped() != null){
			criteria.add(Restrictions.eq("qtyshipped", query.getQtyshipped()));
		}
		if(StringUtils.isNotEmpty(query.getShipmenttime())){
			criteria.add(Restrictions.like("shipmenttime", query.getShipmenttime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getShipmenttimetext())){
			criteria.add(Restrictions.like("shipmenttimetext", query.getShipmenttimetext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkuDescrC())){
			criteria.add(Restrictions.like("skuDescrC", query.getSkuDescrC(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkuDescrE())){
			criteria.add(Restrictions.like("skuDescrE", query.getSkuDescrE(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkugroup())){
			criteria.add(Restrictions.like("skugroup", query.getSkugroup(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkutext())){
			criteria.add(Restrictions.like("skutext", query.getSkutext(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUdf1())){
			criteria.add(Restrictions.like("udf1", query.getUdf1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUdf2())){
			criteria.add(Restrictions.like("udf2", query.getUdf2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUdf3())){
			criteria.add(Restrictions.like("udf3", query.getUdf3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}