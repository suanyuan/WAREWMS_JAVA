package com.wms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.Menu;
import com.wms.query.MenuQuery;

@Repository("menuDao")
public class MenuDao extends BaseDao<Menu, String, MenuQuery> {

	@SuppressWarnings("unchecked")
	public List<Menu> findByParentId(String parentId){
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.eq("parentId", parentId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Menu> findByMenuType(String menuType) {
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.eq("menuType", menuType));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);  
		return criteria.list();
	}

	@Override
	public List<Menu> getPagedDatagrid(EasyuiDatagridPager pager, MenuQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countAll(MenuQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Criteria getQueryCriteria(MenuQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
}
