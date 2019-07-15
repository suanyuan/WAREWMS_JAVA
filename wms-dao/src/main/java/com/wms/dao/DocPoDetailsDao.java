package com.wms.dao;

import java.util.List;

import com.wms.query.DocPoDetailsQuery;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.DocPoDetails;
import com.wms.easyui.EasyuiDatagridPager;

@Repository("docPoDetailsDao")
public class DocPoDetailsDao extends BaseDao<DocPoDetails, String, DocPoDetailsQuery> {

	public List<DocPoDetails> getPagedDatagrid(EasyuiDatagridPager pager, DocPoDetailsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(DocPoDetailsQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(DocPoDetailsQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getPono())){
			criteria.add(Restrictions.like("pono", query.getPono(), MatchMode.ANYWHERE));
		}
		if(query.getPolineno() != null){
			criteria.add(Restrictions.eq("polineno", query.getPolineno()));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSku())){
			criteria.add(Restrictions.like("sku", query.getSku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkudescrc())){
			criteria.add(Restrictions.like("skudescrc", query.getSkudescrc(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSkudescre())){
			criteria.add(Restrictions.like("skudescre", query.getSkudescre(), MatchMode.ANYWHERE));
		}
		if(query.getOrderedqty() != null){
			criteria.add(Restrictions.eq("orderedqty", query.getOrderedqty()));
		}
		if(query.getOrderedqtyEach() != null){
			criteria.add(Restrictions.eq("orderedqtyEach", query.getOrderedqtyEach()));
		}
		if(query.getReceivedqty() != null){
			criteria.add(Restrictions.eq("receivedqty", query.getReceivedqty()));
		}
		if(query.getReceivedqtyEach() != null){
			criteria.add(Restrictions.eq("receivedqtyEach", query.getReceivedqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getUom())){
			criteria.add(Restrictions.like("uom", query.getUom(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPackid())){
			criteria.add(Restrictions.like("packid", query.getPackid(), MatchMode.ANYWHERE));
		}
		if(query.getTotalcubic() != null){
			criteria.add(Restrictions.eq("totalcubic", query.getTotalcubic()));
		}
		if(query.getTotalgrossweight() != null){
			criteria.add(Restrictions.eq("totalgrossweight", query.getTotalgrossweight()));
		}
		if(query.getTotalnetweight() != null){
			criteria.add(Restrictions.eq("totalnetweight", query.getTotalnetweight()));
		}
		if(query.getTotalprice() != null){
			criteria.add(Restrictions.eq("totalprice", query.getTotalprice()));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine1())){
			criteria.add(Restrictions.like("userdefine1", query.getUserdefine1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine2())){
			criteria.add(Restrictions.like("userdefine2", query.getUserdefine2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine3())){
			criteria.add(Restrictions.like("userdefine3", query.getUserdefine3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine4())){
			criteria.add(Restrictions.like("userdefine4", query.getUserdefine4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine5())){
			criteria.add(Restrictions.like("userdefine5", query.getUserdefine5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getNotes())){
			criteria.add(Restrictions.like("notes", query.getNotes(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt12())){
			criteria.add(Restrictions.like("lotatt12", query.getLotatt12(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt13())){
			criteria.add(Restrictions.like("lotatt13", query.getLotatt13(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt14())){
			criteria.add(Restrictions.like("lotatt14", query.getLotatt14(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt15())){
			criteria.add(Restrictions.like("lotatt15", query.getLotatt15(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt16())){
			criteria.add(Restrictions.like("lotatt16", query.getLotatt16(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt17())){
			criteria.add(Restrictions.like("lotatt17", query.getLotatt17(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLotatt18())){
			criteria.add(Restrictions.like("lotatt18", query.getLotatt18(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPolinestatus())){
			criteria.add(Restrictions.like("polinestatus", query.getPolinestatus(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi01())){
			criteria.add(Restrictions.like("dEdi01", query.getDEdi01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi02())){
			criteria.add(Restrictions.like("dEdi02", query.getDEdi02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi03())){
			criteria.add(Restrictions.like("dEdi03", query.getDEdi03(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi04())){
			criteria.add(Restrictions.like("dEdi04", query.getDEdi04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi05())){
			criteria.add(Restrictions.like("dEdi05", query.getDEdi05(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi06())){
			criteria.add(Restrictions.like("dEdi06", query.getDEdi06(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi07())){
			criteria.add(Restrictions.like("dEdi07", query.getDEdi07(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi08())){
			criteria.add(Restrictions.like("dEdi08", query.getDEdi08(), MatchMode.ANYWHERE));
		}
		if(query.getDEdi09() != null){
			criteria.add(Restrictions.eq("dEdi09", query.getDEdi09()));
		}
		if(query.getDEdi10() != null){
			criteria.add(Restrictions.eq("dEdi10", query.getDEdi10()));
		}
		if(query.getQtyreleased() != null){
			criteria.add(Restrictions.eq("qtyreleased", query.getQtyreleased()));
		}
		return criteria;
	}
}