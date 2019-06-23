package com.wms.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.Role;
import com.wms.query.RoleQuery;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role, String, RoleQuery> {

	public List<Role> findAll(){
		return this.getListQuery("from Role");
	}

	public Long countAll() {
		return (Long)this.getUniqueResultQuery("select count(*) from Role");
	}

	@Override
	public List<Role> getPagedDatagrid(EasyuiDatagridPager pager, RoleQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countAll(RoleQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Criteria getQueryCriteria(RoleQuery query) {
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getRoleName())){
			criteria.add(Restrictions.eq("roleName", query.getRoleName()));
		}
		return criteria;
	}
}
