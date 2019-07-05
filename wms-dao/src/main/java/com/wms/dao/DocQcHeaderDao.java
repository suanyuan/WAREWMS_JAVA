package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.DocQcHeader;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocQcHeaderQuery;

@Repository("docQcHeaderDao")
public class DocQcHeaderDao extends BaseDao<DocQcHeader, String, DocQcHeaderQuery> {

	public List<DocQcHeader> getPagedDatagrid(EasyuiDatagridPager pager, DocQcHeaderQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(DocQcHeaderQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(DocQcHeaderQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getQcno())){
			criteria.add(Restrictions.like("qcno", query.getQcno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAsnno())){
			criteria.add(Restrictions.like("asnno", query.getAsnno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQcreference1())){
			criteria.add(Restrictions.like("qcreference1", query.getQcreference1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQcreference2())){
			criteria.add(Restrictions.like("qcreference2", query.getQcreference2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQcreference3())){
			criteria.add(Restrictions.like("qcreference3", query.getQcreference3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQcreference4())){
			criteria.add(Restrictions.like("qcreference4", query.getQcreference4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQcreference5())){
			criteria.add(Restrictions.like("qcreference5", query.getQcreference5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQctype())){
			criteria.add(Restrictions.like("qctype", query.getQctype(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getQcstatus())){
			criteria.add(Restrictions.like("qcstatus", query.getQcstatus(), MatchMode.ANYWHERE));
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
		if(StringUtils.isNotEmpty(query.getQcPrintFlag())){
			criteria.add(Restrictions.like("qcPrintFlag", query.getQcPrintFlag(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getWarehouseid())){
			criteria.add(Restrictions.like("warehouseid", query.getWarehouseid(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}