package com.wms.easyui;

/**
 * 設定 EasyUI Datagrid 基本資料
 * @Date 2012/5/28
 * @author OwenHuang
 */
public class EasyuiDatagridPager {
	private int page = 1;//起始頁
	private int rows = 10;//每頁顯示資料筆數
	private String countBy = "id";//排序欄位名稱
	private String sortBy = "id";//排序欄位名稱
	private String orderBy = "asc";//升冪、降冪排序(asc,desc)
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getCountBy() {
		return countBy;
	}
	public void setCountBy(String countBy) {
		this.countBy = countBy;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	@Override
	public String toString() {
		return "EasyuiDatagridPager [page=" + page + ", rows=" + rows + ", countBy=" + countBy + ", sortBy=" + sortBy
				+ ", orderBy=" + orderBy + "]";
	}
}
