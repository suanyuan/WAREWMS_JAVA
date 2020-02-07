package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.DocAsnDetail;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocAsnDetailQuery;

@Repository("docAsnDetailDao")
public class DocAsnDetailDao extends BaseDao<DocAsnDetail, String, DocAsnDetailQuery> {

	public List<DocAsnDetail> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnDetailQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(DocAsnDetailQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(DocAsnDetailQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAsnno())){
			criteria.add(Restrictions.like("asnno", query.getAsnno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternativedescrC())){
			criteria.add(Restrictions.like("alternativedescrC", query.getAlternativedescrC(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAlternativesku())){
			criteria.add(Restrictions.like("alternativesku", query.getAlternativesku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnlinefilter())){
			criteria.add(Restrictions.like("asnlinefilter", query.getAsnlinefilter(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContainerid())){
			criteria.add(Restrictions.like("containerid", query.getContainerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCreatesource())){
			criteria.add(Restrictions.like("createsource", query.getCreatesource(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getDEdi11())){
			criteria.add(Restrictions.like("dEdi11", query.getDEdi11(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi12())){
			criteria.add(Restrictions.like("dEdi12", query.getDEdi12(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi13())){
			criteria.add(Restrictions.like("dEdi13", query.getDEdi13(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi14())){
			criteria.add(Restrictions.like("dEdi14", query.getDEdi14(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi15())){
			criteria.add(Restrictions.like("dEdi15", query.getDEdi15(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi16())){
			criteria.add(Restrictions.like("dEdi16", query.getDEdi16(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi17())){
			criteria.add(Restrictions.like("dEdi17", query.getDEdi17(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi18())){
			criteria.add(Restrictions.like("dEdi18", query.getDEdi18(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi19())){
			criteria.add(Restrictions.like("dEdi19", query.getDEdi19(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDEdi20())){
			criteria.add(Restrictions.like("dEdi20", query.getDEdi20(), MatchMode.ANYWHERE));
		}
		if(query.getDamagedqtyEach() != null){
			criteria.add(Restrictions.eq("damagedqtyEach", query.getDamagedqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		if(query.getExpectedqty() != null){
			criteria.add(Restrictions.eq("expectedqty", query.getExpectedqty()));
		}
		if(query.getExpectedqtyEach() != null){
			criteria.add(Restrictions.eq("expectedqtyEach", query.getExpectedqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getHoldrejectcode())){
			criteria.add(Restrictions.like("holdrejectcode", query.getHoldrejectcode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHoldrejectreason())){
			criteria.add(Restrictions.like("holdrejectreason", query.getHoldrejectreason(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getLinestatus())){
			criteria.add(Restrictions.like("linestatus", query.getLinestatus(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getNotes())){
			criteria.add(Restrictions.like("notes", query.getNotes(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOperator())){
			criteria.add(Restrictions.like("operator", query.getOperator(), MatchMode.ANYWHERE));
		}
		if(query.getOverrcvpercentage() != null){
			criteria.add(Restrictions.eq("overrcvpercentage", query.getOverrcvpercentage()));
		}
		if(StringUtils.isNotEmpty(query.getPackid())){
			criteria.add(Restrictions.like("packid", query.getPackid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPalletizemethod())){
			criteria.add(Restrictions.like("palletizemethod", query.getPalletizemethod(), MatchMode.ANYWHERE));
		}
		if(query.getPalletizeqtyEach() != null){
			criteria.add(Restrictions.eq("palletizeqtyEach", query.getPalletizeqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getPlantoloc())){
			criteria.add(Restrictions.like("plantoloc", query.getPlantoloc(), MatchMode.ANYWHERE));
		}
		if(query.getPolineno() != null){
			criteria.add(Restrictions.eq("polineno", query.getPolineno()));
		}
		if(StringUtils.isNotEmpty(query.getPono())){
			criteria.add(Restrictions.like("pono", query.getPono(), MatchMode.ANYWHERE));
		}
		if(query.getPrereceivedqtyEach() != null){
			criteria.add(Restrictions.eq("prereceivedqtyEach", query.getPrereceivedqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getPrintlabel())){
			criteria.add(Restrictions.like("printlabel", query.getPrintlabel(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductstatus())){
			criteria.add(Restrictions.like("productstatus", query.getProductstatus(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getProductstatusDescr())){
			criteria.add(Restrictions.like("productstatusDescr", query.getProductstatusDescr(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQcstatus())){
			criteria.add(Restrictions.like("qcstatus", query.getQcstatus(), MatchMode.ANYWHERE));
		}
		if(query.getReceivedqty() != null){
			criteria.add(Restrictions.eq("receivedqty", query.getReceivedqty()));
		}
		if(query.getReceivedqtyEach() != null){
			criteria.add(Restrictions.eq("receivedqtyEach", query.getReceivedqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getReceivinglocation())){
			criteria.add(Restrictions.like("receivinglocation", query.getReceivinglocation(), MatchMode.ANYWHERE));
		}
		if(query.getReferencelineno() != null){
			criteria.add(Restrictions.eq("referencelineno", query.getReferencelineno()));
		}
		if(query.getRejectedqty() != null){
			criteria.add(Restrictions.eq("rejectedqty", query.getRejectedqty()));
		}
		if(query.getRejectedqtyEach() != null){
			criteria.add(Restrictions.eq("rejectedqtyEach", query.getRejectedqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getReserveFlag())){
			criteria.add(Restrictions.like("reserveFlag", query.getReserveFlag(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getUom())){
			criteria.add(Restrictions.like("uom", query.getUom(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getUserdefine6())){
			criteria.add(Restrictions.like("userdefine6", query.getUserdefine6(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}