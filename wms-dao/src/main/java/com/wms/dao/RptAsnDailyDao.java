package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.RptAsnDaily;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.RptAsnDailyQuery;

@Repository("rptAsnDailyDao")
public class RptAsnDailyDao extends BaseDao<RptAsnDaily, String, RptAsnDailyQuery> {

	public List<RptAsnDaily> getPagedDatagrid(EasyuiDatagridPager pager, RptAsnDailyQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(RptAsnDailyQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(RptAsnDailyQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAsncreatetime())){
			criteria.add(Restrictions.like("asncreatetime", query.getAsncreatetime(), MatchMode.ANYWHERE));
		}
		if(query.getAsnlineno() != null){
			criteria.add(Restrictions.eq("asnlineno", query.getAsnlineno()));
		}
		if(StringUtils.isNotEmpty(query.getAsnno())){
			criteria.add(Restrictions.like("asnno", query.getAsnno(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getAsnreference4inreport())){
			criteria.add(Restrictions.like("asnreference4inreport", query.getAsnreference4inreport(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnreference5inreport())){
			criteria.add(Restrictions.like("asnreference5inreport", query.getAsnreference5inreport(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsntype())){
			criteria.add(Restrictions.like("asntype", query.getAsntype(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarrierid())){
			criteria.add(Restrictions.like("carrierid", query.getCarrierid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCarriername())){
			criteria.add(Restrictions.like("carriername", query.getCarriername(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCommodity())){
			criteria.add(Restrictions.like("commodity", query.getCommodity(), MatchMode.ANYWHERE));
		}
		if(query.getCubic() != null){
			criteria.add(Restrictions.eq("cubic", query.getCubic()));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(query.getGrossweight() != null){
			criteria.add(Restrictions.eq("grossweight", query.getGrossweight()));
		}
		if(StringUtils.isNotEmpty(query.getInbounddate())){
			criteria.add(Restrictions.like("inbounddate", query.getInbounddate(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getInbounddatetext())){
			criteria.add(Restrictions.like("inbounddatetext", query.getInbounddatetext(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getPackid())){
			criteria.add(Restrictions.like("packid", query.getPackid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPackuom1())){
			criteria.add(Restrictions.like("packuom1", query.getPackuom1(), MatchMode.ANYWHERE));
		}
		if(query.getQty() != null){
			criteria.add(Restrictions.eq("qty", query.getQty()));
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
		if(StringUtils.isNotEmpty(query.getSupplierid())){
			criteria.add(Restrictions.like("supplierid", query.getSupplierid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSuppliername())){
			criteria.add(Restrictions.like("suppliername", query.getSuppliername(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getUdf4())){
			criteria.add(Restrictions.like("udf4", query.getUdf4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUdf5())){
			criteria.add(Restrictions.like("udf5", query.getUdf5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}