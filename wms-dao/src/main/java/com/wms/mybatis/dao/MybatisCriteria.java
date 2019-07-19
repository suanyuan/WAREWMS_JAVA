package com.wms.mybatis.dao;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * 公用条件查询类
 */
public class MybatisCriteria {
	/**
	 * 存放条件查询值
	 */
	private Map<String, Object> condition;

	/**
	 * 是否相异
	 */
	protected boolean distinct;
	
	private int currentPage = 1; // 当前页
	private int totalCount = 0; // 总行数
	private int pageSize = 10; // 页大小
	private boolean doPage = true;//是否启动分页

	/**
	 * 排序字段
	 */
	protected String orderByClause;
	/**
	 * 分页字段
	 */
	protected String limitClause;

	protected MybatisCriteria(MybatisCriteria example) {
		this.orderByClause = example.orderByClause;
		this.condition = example.condition;
		this.distinct = example.distinct;
		this.limitClause = example.limitClause;
	}

	public MybatisCriteria() {
		condition = new HashMap<String, Object>();
	}

	public void clear() {
		condition.clear();
		orderByClause = null;
		distinct = false;
		limitClause = null;
	}

	/**
	 * @param condition
	 *            查询的条件名称
	 * @param value
	 *            查询的值
	 */
	public MybatisCriteria put(String condition, Object value) {
		this.condition.put(condition, value);
		return (MybatisCriteria) this;
	}

	/**
	 * @param orderByClause
	 *            排序字段
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * @param distinct
	 *            是否相异
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public String getLimitClause() {
		if(doPage){
			int minRow = (this.currentPage - 1) * this.pageSize + 1;
			int maxRow = this.currentPage * this.pageSize;
			//limitClause = " and row_num between " + minRow + " and " + maxRow;
			limitClause = " LIMIT " + (minRow-1) + "," + maxRow;
		}
		return limitClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 得到总行数
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 得到总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		int pageCount = this.totalCount / this.pageSize + 1;
		// 如果模板==0，且总数大于1，则减一
		if ((this.totalCount % this.pageSize == 0) && pageCount > 1)
			pageCount--;
		return pageCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setCondition(Object o){
		try{
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(o);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					if(propertyUtilsBean.getNestedProperty(o, name)!=null){
						condition.put(name,propertyUtilsBean.getNestedProperty(o, name));
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

	}

	public boolean isDoPage() {
		return doPage;
	}

	public void setDoPage(boolean doPage) {
		this.doPage = doPage;
	}
}