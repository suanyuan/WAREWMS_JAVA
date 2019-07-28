package com.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wms.entity.BasSku;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.mybatis.dao.OrderDetailsForNormalMybatisDao;
import com.wms.mybatis.dao.OrderHeaderForNormalMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.OrderDetailsForNormalVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.OrderDetailsForNormalForm;
import com.wms.query.OrderDetailsForNormalQuery;
import com.wms.query.OrderHeaderForNormalQuery;

@Service("orderDetailsForNormalService")
public class OrderDetailsForNormalService extends BaseService {

	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
	@Autowired
	private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
	@Autowired
	private BasSkuService basSkuService;

	public EasyuiDatagrid<OrderDetailsForNormalVO> getPagedDatagrid(EasyuiDatagridPager pager, OrderDetailsForNormalQuery query) {
		EasyuiDatagrid<OrderDetailsForNormalVO> datagrid = new EasyuiDatagrid<OrderDetailsForNormalVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<OrderDetailsForNormal> orderDetailsForNormalList = orderDetailsForNormalMybatisDao.queryByPageList(mybatisCriteria);
		OrderDetailsForNormalVO orderDetailsForNormalVO = null;
		List<OrderDetailsForNormalVO> orderDetailsForNormalVOList = new ArrayList<OrderDetailsForNormalVO>();
		for (OrderDetailsForNormal orderDetailsForNormal : orderDetailsForNormalList) {
			orderDetailsForNormalVO = new OrderDetailsForNormalVO();
			BeanUtils.copyProperties(orderDetailsForNormal, orderDetailsForNormalVO);
			orderDetailsForNormalVOList.add(orderDetailsForNormalVO);
		}
		datagrid.setTotal((long) orderDetailsForNormalMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(orderDetailsForNormalVOList);
		return datagrid;
	}

	public Json add(OrderDetailsForNormalForm orderDetailsForNormalForm) throws Exception {
		Json json = new Json();
		OrderDetailsForNormalQuery orderDetailsForNormalQuery = new OrderDetailsForNormalQuery();
		orderDetailsForNormalQuery.setOrderno(orderDetailsForNormalForm.getOrderno());
		/*获取新的订单明细行号*/
		int orderLineNo = orderDetailsForNormalMybatisDao.getOrderLineNoById(orderDetailsForNormalQuery);
		OrderDetailsForNormal orderDetailsForNormal = new OrderDetailsForNormal();
		BeanUtils.copyProperties(orderDetailsForNormalForm, orderDetailsForNormal);

		/**
		 * 关联包装代码
		 */
		BasSku basSku = basSkuService.getSkuInfo(orderDetailsForNormalForm.getCustomerid(),orderDetailsForNormalForm.getSku());
		if(basSku!=null){
			orderDetailsForNormal.setUom(basSku.getPackid());
		}
		orderDetailsForNormal.setOrderlineno((double) orderLineNo + 1);
		orderDetailsForNormal.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		orderDetailsForNormal.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		orderDetailsForNormalMybatisDao.add(orderDetailsForNormal);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json edit(OrderDetailsForNormalForm orderDetailsForNormalForm) {
		Json json = new Json();
		OrderDetailsForNormalQuery orderDetailsForNormalQuery = new OrderDetailsForNormalQuery();
		orderDetailsForNormalQuery.setOrderno(orderDetailsForNormalForm.getOrderno());
		orderDetailsForNormalQuery.setOrderlineno(orderDetailsForNormalForm.getOrderlineno());
		OrderDetailsForNormal orderDetailsForNormal = orderDetailsForNormalMybatisDao.queryById(orderDetailsForNormalQuery);
		BeanUtils.copyProperties(orderDetailsForNormalForm, orderDetailsForNormal);
		BasSku basSku = basSkuService.getSkuInfo(orderDetailsForNormalForm.getCustomerid(),orderDetailsForNormalForm.getSku());
		if(basSku!=null){
			orderDetailsForNormal.setUom(basSku.getPackid());
		}
		orderDetailsForNormal.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		orderDetailsForNormalMybatisDao.updateBySelective(orderDetailsForNormal);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json delete(String orderNo, int orderLineNo) {
		Json json = new Json();
		OrderDetailsForNormalQuery orderDetailsForNormalQuery = new OrderDetailsForNormalQuery();
		orderDetailsForNormalQuery.setOrderno(orderNo);
		orderDetailsForNormalQuery.setOrderlineno((double) orderLineNo);
		OrderDetailsForNormal orderDetailsForNormal = orderDetailsForNormalMybatisDao.queryById(orderDetailsForNormalQuery);
		if(orderDetailsForNormal != null){
			orderDetailsForNormalMybatisDao.delete(orderDetailsForNormal);
		}
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}
	/**
	 * 按明细行分配
	 */
	public Json allocation(String orderNo, String orderLineNoList) {
		Json json = new Json();
		if (StringUtils.isNotEmpty(orderLineNoList)) {
			String[] orderLineNoArray = orderLineNoList.split(",");
			for (String orderLineNo : orderLineNoArray) {
				OrderDetailsForNormalQuery orderDetailsForNormalQuery = new OrderDetailsForNormalQuery();
				orderDetailsForNormalQuery.setOrderno(orderNo);
				orderDetailsForNormalQuery.setOrderlineno(Double.valueOf(orderLineNo));
				OrderDetailsForNormal orderDetailsForNormal = orderDetailsForNormalMybatisDao.queryById(orderDetailsForNormalQuery);
				if (orderDetailsForNormal != null) {
					if (orderDetailsForNormal.getLinestatus().equals("00") ||
						orderDetailsForNormal.getLinestatus().equals("30") ||
						orderDetailsForNormal.getLinestatus().equals("40")) {
						Map<String ,Object> map=new HashMap<String, Object>();
						map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						map.put("orderNo", orderNo);
						map.put("orderLineNo", orderLineNo);
						map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
						orderDetailsForNormalMybatisDao.allocationByOrderLine(map);
						String result = map.get("result").toString();
						if (result != null && result.length() > 0) {
							if (result.substring(0,3).equals("000")) {
								continue;
							} else {
								json.setSuccess(false);
								json.setMsg("分配处理失败:"+ result);
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("分配处理失败！" );
							return json;
						}
					}
				}
			}
			OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
			orderHeaderForNormalQuery.setOrderNo(orderNo);
			OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
			json.setSuccess(true);
			json.setMsg("分配处理成功！");
			json.setObj(orderHeaderForNormal);
			return json;
		} else {
			json.setSuccess(false);
			json.setMsg("分配处理成功！");
			return json;
		}
	}
	/**
	 * 按明细行取消分配
	 */
	public Json deAllocation(String orderNo, String orderLineNoList) {
		Json json = new Json();
		if (StringUtils.isNotEmpty(orderLineNoList)) {
			String[] orderLineNoArray = orderLineNoList.split(",");
			for (String orderLineNo : orderLineNoArray) {
				OrderDetailsForNormalQuery orderDetailsForNormalQuery = new OrderDetailsForNormalQuery();
				orderDetailsForNormalQuery.setOrderno(orderNo);
				orderDetailsForNormalQuery.setOrderlineno(Double.valueOf(orderLineNo));
				OrderDetailsForNormal orderDetailsForNormal = orderDetailsForNormalMybatisDao.queryById(orderDetailsForNormalQuery);
				if (orderDetailsForNormal != null) {
					if (orderDetailsForNormal.getLinestatus().equals("00") ||
						orderDetailsForNormal.getLinestatus().equals("30") ||
						orderDetailsForNormal.getLinestatus().equals("40")) {
						Map<String ,Object> map=new HashMap<String, Object>();
						map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						map.put("orderNo", orderNo);
						map.put("orderLineNo", orderLineNo);
						map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
						orderDetailsForNormalMybatisDao.deAllocationByOrderLine(map);
						String result = map.get("result").toString();
						if (result != null && result.length() > 0) {
							if (result.substring(0,3).equals("000")) {
								continue;
							} else {
								json.setSuccess(false);
								json.setMsg("分配取消失败:"+ result);
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("分配取消失败！" );
							return json;
						}
					}
				}
			}
			OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
			orderHeaderForNormalQuery.setOrderNo(orderNo);
			OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
			json.setSuccess(true);
			json.setMsg("分配取消成功！");
			json.setObj(orderHeaderForNormal);
			return json;
		} else {
			json.setSuccess(false);
			json.setMsg("分配取消成功！");
			return json;
		}
	}
}