package com.wms.dao;

import java.util.List;

import com.wms.entity.DocPoHeader;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocPoHeaderQuery;

@Repository("docPoHeaderDao")
public class DocPoHeaderDao extends BaseDao<DocPoHeader, String, DocPoHeaderQuery> {

	public List<DocPoHeader> getPagedDatagrid(EasyuiDatagridPager pager, DocPoHeaderQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(DocPoHeaderQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(DocPoHeaderQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getPono())){
			criteria.add(Restrictions.like("pono", query.getPono(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPotype())){
			criteria.add(Restrictions.like("potype", query.getPotype(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPostatus())){
			criteria.add(Restrictions.like("postatus", query.getPostatus(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPoreference1())){
			criteria.add(Restrictions.like("poreference1", query.getPoreference1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPoreference2())){
			criteria.add(Restrictions.like("poreference2", query.getPoreference2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPoreference3())){
			criteria.add(Restrictions.like("poreference3", query.getPoreference3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPoreference4())){
			criteria.add(Restrictions.like("poreference4", query.getPoreference4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getPoreference5())){
			criteria.add(Restrictions.like("poreference5", query.getPoreference5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierid())){
			criteria.add(Restrictions.like("supplierid", query.getSupplierid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierName())){
			criteria.add(Restrictions.like("supplierName", query.getSupplierName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierAddress1())){
			criteria.add(Restrictions.like("supplierAddress1", query.getSupplierAddress1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierAddress2())){
			criteria.add(Restrictions.like("supplierAddress2", query.getSupplierAddress2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierAddress3())){
			criteria.add(Restrictions.like("supplierAddress3", query.getSupplierAddress3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierAddress4())){
			criteria.add(Restrictions.like("supplierAddress4", query.getSupplierAddress4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierCity())){
			criteria.add(Restrictions.like("supplierCity", query.getSupplierCity(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierProvince())){
			criteria.add(Restrictions.like("supplierProvince", query.getSupplierProvince(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierCountry())){
			criteria.add(Restrictions.like("supplierCountry", query.getSupplierCountry(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierZip())){
			criteria.add(Restrictions.like("supplierZip", query.getSupplierZip(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEdisendflag())){
			criteria.add(Restrictions.like("edisendflag", query.getEdisendflag(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierContact())){
			criteria.add(Restrictions.like("supplierContact", query.getSupplierContact(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierMail())){
			criteria.add(Restrictions.like("supplierMail", query.getSupplierMail(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierFax())){
			criteria.add(Restrictions.like("supplierFax", query.getSupplierFax(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierTel1())){
			criteria.add(Restrictions.like("supplierTel1", query.getSupplierTel1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getSupplierTel2())){
			criteria.add(Restrictions.like("supplierTel2", query.getSupplierTel2(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getNotes())){
			criteria.add(Restrictions.like("notes", query.getNotes(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi01())){
			criteria.add(Restrictions.like("hEdi01", query.getHedi01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi02())){
			criteria.add(Restrictions.like("hEdi02", query.getHedi02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi03())){
			criteria.add(Restrictions.like("hEdi03", query.getHedi03(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi04())){
			criteria.add(Restrictions.like("hEdi04", query.getHedi04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi05())){
			criteria.add(Restrictions.like("hEdi05", query.getHedi05(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi06())){
			criteria.add(Restrictions.like("hEdi06", query.getHedi06(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi07())){
			criteria.add(Restrictions.like("hEdi07", query.getHedi07(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getHedi08())){
			criteria.add(Restrictions.like("hEdi08", query.getHedi08(), MatchMode.ANYWHERE));
		}
		if(query.getHedi09() != null){
			criteria.add(Restrictions.eq("hEdi09", query.getHedi09()));
		}
		if(query.getHedi10() != null){
			criteria.add(Restrictions.eq("hEdi10", query.getHedi10()));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCreatesource())){
			criteria.add(Restrictions.like("createsource", query.getCreatesource(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReleasestatus())){
			criteria.add(Restrictions.like("releasestatus", query.getReleasestatus(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefinea())){
			criteria.add(Restrictions.like("userdefinea", query.getUserdefinea(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefineb())){
			criteria.add(Restrictions.like("userdefineb", query.getUserdefineb(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}