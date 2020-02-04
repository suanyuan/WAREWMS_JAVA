package com.wms.mybatis.dao;


import com.wms.mybatis.entity.SfcCustomer;
import com.wms.mybatis.entity.SfcUser;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.mybatis.entity.SfcWarehouse;
import com.wms.query.SfcCustomerQuery;
import com.wms.query.SfcWarehouseQuery;

import java.util.List;
/**
 * 
 * <br>
 * <b>功能：</b>SfcUserDao<br>
 */
public interface SfcUserMybatisDao extends BaseDao {

	public SfcUser queryListById(SfcUserLogin sfcUserLogin);

	public <T> T queryByName(Object name);

	public SfcWarehouse queryWarehouseById(SfcWarehouseQuery sfcWarehouseQuery);

	public List<SfcWarehouse> queryDefaultWarehouseListByUser(SfcUser sfcUser);

	public List<SfcWarehouse> queryWarehouseByAll();

	public SfcCustomer queryCustomerById(SfcCustomerQuery sfcCustomerQuery);

	public List<SfcCustomer> queryCustomerByAll();

	public void addRoleByUser(SfcUser sfcUser);

	public void deleteRoleByUser(SfcUser sfcUser);

	public void addWarehouseByUser(SfcUser sfcUser);

	public void deleteWarehouseByUser(SfcUser sfcUser);

	public void addCustomerByUser(SfcUser sfcUser);

	public void deleteCustomerByUser(SfcUser sfcUser);
	
}
