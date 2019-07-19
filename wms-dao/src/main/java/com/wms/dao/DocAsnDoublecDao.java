package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.DocAsnDoublec;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.DocAsnDoublecQuery;

@Repository("docAsnDoublecDao")
public class DocAsnDoublecDao extends BaseDao<DocAsnDoublec, String, DocAsnDoublecQuery> {

	public List<DocAsnDoublec> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnDoublecQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(DocAsnDoublecQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(DocAsnDoublecQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getDoublecno())){
			criteria.add(Restrictions.like("doublecno", query.getDoublecno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getCustomerid())){
			criteria.add(Restrictions.like("customerid", query.getCustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext1())){
			criteria.add(Restrictions.like("context1", query.getContext1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext2())){
			criteria.add(Restrictions.like("context2", query.getContext2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext3())){
			criteria.add(Restrictions.like("context3", query.getContext3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext4())){
			criteria.add(Restrictions.like("context4", query.getContext4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext5())){
			criteria.add(Restrictions.like("context5", query.getContext5(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext6())){
			criteria.add(Restrictions.like("context6", query.getContext6(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext7())){
			criteria.add(Restrictions.like("context7", query.getContext7(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getContext8())){
			criteria.add(Restrictions.like("context8", query.getContext8(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getMatchFlag())){
			criteria.add(Restrictions.like("matchFlag", query.getMatchFlag(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getAddwho())){
			criteria.add(Restrictions.like("addwho", query.getAddwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}