package com.wms.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.User;
import com.wms.entity.UserLogin;
import com.wms.query.UserQuery;

@Repository("userDao")
public class UserDao extends BaseDao<User, String, UserQuery> {
	@Override
	public List<User> getPagedDatagrid(EasyuiDatagridPager pager, UserQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	protected Criteria getQueryCriteria(UserQuery query) {
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getUserName())){
			criteria.add(Restrictions.eq("userName", query.getUserName()));
		}
		if(StringUtils.isNotEmpty(query.getNodeId())){
			criteria.add(Restrictions.eq("nodeId", query.getNodeId()));
		}
		if(StringUtils.isNotEmpty(query.getMerchantId())){
			criteria.add(Restrictions.eq("merchantId", query.getMerchantId()));
		}
		criteria.addOrder(Order.asc("createTime"));
		return criteria;
	}
	
	public UserLogin sqlFindSessionIdById(String id) {
		return (UserLogin)this.getSqlQuery("select * from admin_user where user_id = :id").addEntity(UserLogin.class).setParameter("id", id).uniqueResult();
	}
}