package com.wms.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.UserLogin;
import com.wms.query.UserLoginQuery;

@Repository("userLoginDao")
public class UserLoginDao extends BaseDao<UserLogin, String, UserLoginQuery>  {
	
	@Override
	public List<UserLogin> getPagedDatagrid(EasyuiDatagridPager pager, UserLoginQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.addOrder(Order.desc("createTime"));
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	protected Criteria getQueryCriteria(UserLoginQuery query) {
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getUserName())){
			criteria.add(Restrictions.eq("userName", query.getUserName()));
		}
		if(StringUtils.isNotEmpty(query.getUserId())){
			criteria.add(Restrictions.eq("id", query.getUserId()));
		}
		if(StringUtils.isNotEmpty(query.getKeyWorld())){
			criteria.add(Restrictions.or(Restrictions.eq("id", query.getKeyWorld()),Restrictions.eq("userName", query.getKeyWorld())));
		}
		return criteria;
	}
	
	public List<UserLogin> findChildrenByParentNodeId(String nodeId) {
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.eq("parentNodeId", nodeId)).add(Restrictions.eq("enable", new Byte("1")));
		return criteria.list();
	}
}
