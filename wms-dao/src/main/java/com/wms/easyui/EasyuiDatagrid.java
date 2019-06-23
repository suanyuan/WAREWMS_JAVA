package com.wms.easyui;

import java.util.List;

/**
 * EasyUI Datagrid JSON object
 * 
 * @Date 2012/5/28
 * @author OwenHuang
 */
public class EasyuiDatagrid<T> {
	private Long total;// 總筆數
	private List<T> rows;// 全部資料

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
