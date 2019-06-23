package com.wms.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wms.easyui.EasyuiDatagridPager;

/**
 *基礎資料庫操作實作類型
 * @Date 2012/5/11
 * @author OwenHuang
 */
@Repository("baseDao")
public abstract class BaseDao<T extends Serializable, PK extends Serializable, IQuery> {
	
	private Class<T> entityClass;
	   
	@SuppressWarnings({ "unchecked"})
	public BaseDao() {
		this.entityClass = null;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		Session session = sessionFactory.getCurrentSession();
		if(session == null || !session.isOpen() || !session.isConnected()){
			session = sessionFactory.openSession();
		}
		return session;
	}

	public void flush(){
		this.getCurrentSession().flush();
	}
	
	public void clear(){
		this.getCurrentSession().clear();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> loadAll(){
		return this.createCriteria().list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return this.createCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
        return (T) this.getCurrentSession().get(entityClass, id);
    }
	
	public String save(T entity) {
		return this.getCurrentSession().save(entity).toString();
	}

	public void update(T entity) {
		this.getCurrentSession().update(entity);
	}

	public void saveOrUpdate(T entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	public void merge(T entity) {
		this.getCurrentSession().merge(entity);
	}

	public void delete(T entity) {
		this.getCurrentSession().delete(entity);
	}
	
	public Criteria createCriteria() {
        return this.getCurrentSession().createCriteria(entityClass);
    }
	
	@SuppressWarnings("unchecked")
	public List<T> getListByQuery(IQuery query){
		return this.getQueryCriteria(query).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
	
	@SuppressWarnings("unchecked")
	public T getUniqueByQuery(IQuery query){
		return (T) this.getQueryCriteria(query).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
	}
	
	public Long countAll(IQuery query){
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}
	
	public abstract List<T> getPagedDatagrid(EasyuiDatagridPager pager, IQuery query);
	
	protected abstract Criteria getQueryCriteria(IQuery query);
	
	public SQLQuery getSqlQuery(String queryString){
		return this.getCurrentSession().createSQLQuery(queryString);
	}
	
	public int executeSQL(String queryString){
		return this.getSqlQuery(queryString).executeUpdate();
	}
	
	public Object getUniqueResultQuery(String queryString){
		return this.getCurrentSession().createQuery(queryString).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getListQuery(String queryString){
		return this.getCurrentSession().createQuery(queryString).list();
	}
	
	public Query getQuery(String queryString){
		return this.getCurrentSession().createQuery(queryString);
	}

}
