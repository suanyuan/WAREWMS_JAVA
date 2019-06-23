package com.wms.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.entity.ViewInvLotatt;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.ViewInvLotattQuery;

@Repository("viewInvLotattDao")
public class ViewInvLotattDao extends BaseDao<ViewInvLotatt, String, ViewInvLotattQuery> {

	public List<ViewInvLotatt> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvLotattQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(ViewInvLotattQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(ViewInvLotattQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAllocationrule())){
			criteria.add(Restrictions.like("allocationrule", query.getAllocationrule(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatesku1())){
			criteria.add(Restrictions.like("alternatesku1", query.getAlternatesku1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatesku2())){
			criteria.add(Restrictions.like("alternatesku2", query.getAlternatesku2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatesku3())){
			criteria.add(Restrictions.like("alternatesku3", query.getAlternatesku3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatesku4())){
			criteria.add(Restrictions.like("alternatesku4", query.getAlternatesku4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternatesku5())){
			criteria.add(Restrictions.like("alternatesku5", query.getAlternatesku5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConfiglist01())){
			criteria.add(Restrictions.like("configlist01", query.getConfiglist01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getConfiglist02())){
			criteria.add(Restrictions.like("configlist02", query.getConfiglist02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmcustomerid())){
			criteria.add(Restrictions.like("fmcustomerid", query.getFmcustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmid())){
			criteria.add(Restrictions.like("fmid", query.getFmid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlocation())){
			criteria.add(Restrictions.like("fmlocation", query.getFmlocation(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotnum())){
			criteria.add(Restrictions.like("fmlotnum", query.getFmlotnum(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getImageaddress())){
			criteria.add(Restrictions.like("imageaddress", query.getImageaddress(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt01())){
			criteria.add(Restrictions.like("lotatt01", query.getLotatt01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt01text())){
			criteria.add(Restrictions.like("lotatt01text", query.getLotatt01text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt02())){
			criteria.add(Restrictions.like("lotatt02", query.getLotatt02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt02text())){
			criteria.add(Restrictions.like("lotatt02text", query.getLotatt02text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt03())){
			criteria.add(Restrictions.like("lotatt03", query.getLotatt03(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt03text())){
			criteria.add(Restrictions.like("lotatt03text", query.getLotatt03text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt04())){
			criteria.add(Restrictions.like("lotatt04", query.getLotatt04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt04text())){
			criteria.add(Restrictions.like("lotatt04text", query.getLotatt04text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt05())){
			criteria.add(Restrictions.like("lotatt05", query.getLotatt05(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt05text())){
			criteria.add(Restrictions.like("lotatt05text", query.getLotatt05text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt06())){
			criteria.add(Restrictions.like("lotatt06", query.getLotatt06(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt06text())){
			criteria.add(Restrictions.like("lotatt06text", query.getLotatt06text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt07())){
			criteria.add(Restrictions.like("lotatt07", query.getLotatt07(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt07text())){
			criteria.add(Restrictions.like("lotatt07text", query.getLotatt07text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt08())){
			criteria.add(Restrictions.like("lotatt08", query.getLotatt08(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt08text())){
			criteria.add(Restrictions.like("lotatt08text", query.getLotatt08text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt09())){
			criteria.add(Restrictions.like("lotatt09", query.getLotatt09(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt09text())){
			criteria.add(Restrictions.like("lotatt09text", query.getLotatt09text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt10())){
			criteria.add(Restrictions.like("lotatt10", query.getLotatt10(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt10text())){
			criteria.add(Restrictions.like("lotatt10text", query.getLotatt10text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt11())){
			criteria.add(Restrictions.like("lotatt11", query.getLotatt11(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt11text())){
			criteria.add(Restrictions.like("lotatt11text", query.getLotatt11text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt12())){
			criteria.add(Restrictions.like("lotatt12", query.getLotatt12(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt12text())){
			criteria.add(Restrictions.like("lotatt12text", query.getLotatt12text(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLpn())){
			criteria.add(Restrictions.like("lpn", query.getLpn(), MatchMode.ANYWHERE));
		}
		if(query.getNetweight() != null){
			criteria.add(Restrictions.eq("netweight", query.getNetweight()));
		}
		if(query.getOMv() != null){
			criteria.add(Restrictions.eq("oMv", query.getOMv()));
		}
		if(query.getPkey() != null){
			criteria.add(Restrictions.eq("pkey", query.getPkey()));
		}
		if(query.getPrice() != null){
			criteria.add(Restrictions.eq("price", query.getPrice()));
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
		if(query.getQtyrpin() != null){
			criteria.add(Restrictions.eq("qtyrpin", query.getQtyrpin()));
		}
		if(query.getQtyrpout() != null){
			criteria.add(Restrictions.eq("qtyrpout", query.getQtyrpout()));
		}
		if(StringUtils.isNotEmpty(query.getReservedfield01())){
			criteria.add(Restrictions.like("reservedfield01", query.getReservedfield01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReservedfield02())){
			criteria.add(Restrictions.like("reservedfield02", query.getReservedfield02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReservedfield03())){
			criteria.add(Restrictions.like("reservedfield03", query.getReservedfield03(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReservedfield04())){
			criteria.add(Restrictions.like("reservedfield04", query.getReservedfield04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReservedfield05())){
			criteria.add(Restrictions.like("reservedfield05", query.getReservedfield05(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getRotationid())){
			criteria.add(Restrictions.like("rotationid", query.getRotationid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkudescrc())){
			criteria.add(Restrictions.like("skudescrc", query.getSkudescrc(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkudescre())){
			criteria.add(Restrictions.like("skudescre", query.getSkudescre(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkugroup1())){
			criteria.add(Restrictions.like("skugroup1", query.getSkugroup1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkugroup2())){
			criteria.add(Restrictions.like("skugroup2", query.getSkugroup2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkugroup3())){
			criteria.add(Restrictions.like("skugroup3", query.getSkugroup3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkugroup4())){
			criteria.add(Restrictions.like("skugroup4", query.getSkugroup4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkugroup5())){
			criteria.add(Restrictions.like("skugroup5", query.getSkugroup5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSoftallocationrule())){
			criteria.add(Restrictions.like("softallocationrule", query.getSoftallocationrule(), MatchMode.ANYWHERE));
		}
		if(query.getToadjqty() != null){
			criteria.add(Restrictions.eq("toadjqty", query.getToadjqty()));
		}
		if(query.getTotalcubic() != null){
			criteria.add(Restrictions.eq("totalcubic", query.getTotalcubic()));
		}
		if(query.getTotalgrossweight() != null){
			criteria.add(Restrictions.eq("totalgrossweight", query.getTotalgrossweight()));
		}
		if(StringUtils.isNotEmpty(query.getUom())){
			criteria.add(Restrictions.like("uom", query.getUom(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}