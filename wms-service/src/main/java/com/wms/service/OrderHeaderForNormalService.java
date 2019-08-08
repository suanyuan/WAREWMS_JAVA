package com.wms.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.ActAllocationDetails;
import com.wms.entity.BasPackage;
import com.wms.entity.BasSku;
import com.wms.entity.InvLotAtt;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.*;
import com.wms.result.OrderStatusResult;
import com.wms.service.importdata.ImportOrderDataService;
import com.wms.utils.*;
import com.wms.vo.ActAllocationDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.OrderHeaderForNormalForm;
import com.wms.vo.form.pda.PageForm;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderHeaderForNormalService")
public class OrderHeaderForNormalService extends BaseService {
	
	static Logger logger = Logger.getLogger(OrderHeaderForNormalService.class.getName());
	
	private static final String END_POINT = ResourceUtil.geteEndpoint();

	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private DocOrderPackingMybatisDao docOrderPackingMybatisDao;
	@Autowired
	private BasCustomerService basCustomerService;
	@Autowired
	private BasSkuService basSkuService;
	@Autowired
	private BasPackageService basPackageService;
	@Autowired
	private ActAllocationDetailsMybatisDao actAllocationDetailsMybatisDao;
	
//	@Autowired
//	private DocOrderImportMybatisDao docOrderImportMybatisDao;
//
	@Autowired
	private ImportOrderDataService importOrderDataService;
	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;
	@Autowired
	private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
	/**
	 * 订单列表显示
	 */
	public EasyuiDatagrid<OrderHeaderForNormalVO> getPagedDatagrid(EasyuiDatagridPager pager, OrderHeaderForNormalQuery query) {
		EasyuiDatagrid<OrderHeaderForNormalVO> datagrid = new EasyuiDatagrid<OrderHeaderForNormalVO>();
		query.setWarehouseId(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		mybatisCriteria.setOrderByClause("addtime desc");
		List<OrderHeaderForNormal> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryByList(mybatisCriteria);
		OrderHeaderForNormalVO orderHeaderForNormalVO = null;
		List<OrderHeaderForNormalVO> orderHeaderForNormalVOList = new ArrayList<OrderHeaderForNormalVO>();
		for (OrderHeaderForNormal orderHeaderForNormal : orderHeaderForNormalList) {
			orderHeaderForNormalVO = new OrderHeaderForNormalVO();
			BeanUtils.copyProperties(orderHeaderForNormal, orderHeaderForNormalVO);
			orderHeaderForNormalVOList.add(orderHeaderForNormalVO);
		}
		datagrid.setTotal((long) orderHeaderForNormalMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(orderHeaderForNormalVOList);
		return datagrid;
	}

	public EasyuiDatagrid<ActAllocationDetailsVO> getPageAllocation(EasyuiDatagridPager pager, ActAllocationDetailsQuery query){
		EasyuiDatagrid<ActAllocationDetailsVO> datagrid = new EasyuiDatagrid<ActAllocationDetailsVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<ActAllocationDetails> orderHeaderForNormalList = actAllocationDetailsMybatisDao.queryByList(mybatisCriteria);
		List<ActAllocationDetailsVO> actAllocationDetailsVOList = new ArrayList<>();
		ActAllocationDetailsVO vo = null;
		for(ActAllocationDetails act : orderHeaderForNormalList){
			vo = new ActAllocationDetailsVO();
			BeanUtils.copyProperties(act,vo);
			BasSku basSku = basSkuService.getSkuInfo(act.getCustomerid(),act.getSku());
			BasPackageQuery packQuery = new BasPackageQuery();
			packQuery.setPackid(basSku.getPackid());
			BasPackage basPackage = basPackageService.queryBasPackBy(packQuery);
			vo.setSkuName(basSku.getDescrC());
			vo.setPickName(basPackage.getDescr());
			actAllocationDetailsVOList.add(vo);
		}
		datagrid.setTotal((long) actAllocationDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(actAllocationDetailsVOList);
		return datagrid;
	}
	/**
	 * 新增订单
	 */
	public Json add(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
		Json json = new Json();
		//获取新的订单号
		/*Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		orderHeaderForNormalMybatisDao.getIdSequence(map);
		String resultCode = map.get("resultCode").toString();
		String resultNo = map.get("resultNo").toString();*/

		String resultNo = commonService.generateSeq("ORDERNO",SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		//if (resultCode.substring(0,3).equals("000")) {
		if(!StringUtils.isEmpty(resultNo)){
			OrderHeaderForNormal orderHeaderForNormal = new OrderHeaderForNormal();
			BeanUtils.copyProperties(orderHeaderForNormalForm, orderHeaderForNormal);
			orderHeaderForNormal.setOrderno(resultNo);
			orderHeaderForNormal.setOrdertype("SO");
			orderHeaderForNormal.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			orderHeaderForNormal.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
			orderHeaderForNormal.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			orderHeaderForNormal.setOrdertime(new Date());
			orderHeaderForNormal.setEdittime(new Date());
			orderHeaderForNormal.setEdisendflag(Constant.IS_USE_YES);
			orderHeaderForNormal.setArchiveflag(Constant.IS_USE_YES);
			orderHeaderForNormalMybatisDao.add(orderHeaderForNormal);
			json.setSuccess(true);
			json.setMsg("资料处理成功！");
			json.setObj(orderHeaderForNormal);
			return json;
		} else {
			json.setSuccess(false);
			//json.setMsg(resultCode);
			return json;
		}
	}
	/**
	 * 编辑订单
	 */
	public Json edit(OrderHeaderForNormalForm orderHeaderForNormalForm) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderHeaderForNormalForm.getOrderno());
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		Date addtime = orderHeaderForNormal.getAddtime();
		BeanUtils.copyProperties(orderHeaderForNormalForm, orderHeaderForNormal);
		orderHeaderForNormal.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		orderHeaderForNormal.setAddtime(addtime);
		orderHeaderForNormalMybatisDao.update(orderHeaderForNormal);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}
	/**
	 * 删除订单
	 */
	public Json delete(String orderno) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderno);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if(orderHeaderForNormal != null){
			if (orderHeaderForNormal.getSostatus().equals("00")) {
				if (orderHeaderForNormal.getAddwho().equals("EDI")) {
					json.setSuccess(false);
					json.setMsg("EDI订单,不能删除!");
					return json;
				} else {
					orderHeaderForNormalMybatisDao.delete(orderHeaderForNormal);
					return Json.success("000");
				}
			} else {
				json.setSuccess(false);
				json.setMsg("当前状态订单,不能删除!");
				return json;
			}
		}
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}
	/**
	 * 分配
	 */
	public Json allocation(String orderNo) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			if (orderHeaderForNormal.getSostatus().equals("00") || orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {
				Map<String ,Object> map=new HashMap<String, Object>();
				//map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("orderNo", orderNo);
				map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
				//map.put("result", "");
				orderHeaderForNormalMybatisDao.allocationByOrder(map);
				String result = map.get("result").toString();
				json.setSuccess(true);
				json.setMsg(result);
				return json;
			} else {
				json.setSuccess(true);
				json.setMsg("当前状态订单,不能操作分配!");
				return json;
			}
		} else {
			json.setSuccess(true);
			json.setMsg("订单分配完成，请查询分配结果");
			return json;
		}
	}
	/**
	 * 取消分配
	 */
	public Json deAllocation(String orderNo) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			if (orderHeaderForNormal.getSostatus().equals("00") || orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {
				Map<String ,Object> map=new HashMap<String, Object>();
				//map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("orderNo", orderNo);
				map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
				orderHeaderForNormalMybatisDao.deAllocationByOrder(map);
				String result = map.get("result").toString();
				json.setSuccess(true);
				json.setMsg(result);
				return json;
			} else {
				json.setSuccess(true);
				json.setMsg("当前状态订单,不能取消分配!");
				return json;
			}
		} else {
			json.setSuccess(true);
			json.setMsg("000");
			return json;
		}
	}

	/**
	 * 拣货
	 */
	public Json picking(String orderNo) {
		Json json = new Json();
		//
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			//判断订单状态
			if (orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {
				List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByAllocationDetailsId(orderNo);
				if(allocationDetailsIdList!=null && allocationDetailsIdList.size()>0){
					for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
						Map<String ,Object> map=new HashMap<String, Object>();
						//map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
						map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
						orderHeaderForNormalMybatisDao.pickingByOrder(map);
						String pickResult = map.get("result").toString();
						if (pickResult != null && pickResult.length() > 0) {
							if (pickResult.equals("000")) {
								continue;
							} else {
								json.setSuccess(false);
								json.setMsg("出库处理失败：" + pickResult);
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("出库处理失败：订单数据异常！");
							return json;
						}
					}
					return Json.success("拣货成功");
				}else {
					return Json.success("拣货失败,查询不到对应的分配明细");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("拣货处理失败：当前状态订单,不能操作拣货!");
				return json;
			}
		}else {
			json.setSuccess(false);
			json.setMsg("拣货处理失败：订单数据异常！");
			return json;
		}
	}

	/**
	 * 取消拣货
	 */
	public Json unPicking(String orderNo) {
		Json json = new Json();
		//
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			int sosStatus = 0;
			sosStatus = Integer.parseInt(orderHeaderForNormal.getSostatus());
			//判断订单状态
			if (sosStatus<=40 || sosStatus>60) {
				json.setSuccess(false);
				json.setMsg("取消拣货处理失败：当前状态订单,不能操作取消拣货!");
				return json;
			}else{
				List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByUnAllocationDetailsId(orderNo);
				if(allocationDetailsIdList!=null){
					for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
						Map<String ,Object> map=new HashMap<String, Object>();
						//map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
						map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
						orderHeaderForNormalMybatisDao.unPickingByOrder(map);
						String pickResult = map.get("result").toString();
						if (pickResult != null && pickResult.length() > 0) {
							if (pickResult.equals("000")) {
								continue;
							} else {
								json.setSuccess(false);
								json.setMsg("取消拣货处理失败：" + pickResult);
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("取消拣货处理失败：订单数据异常！");
							return json;
						}
					}
					return Json.success("取消拣货成功");
				}else {
					return Json.success("取消拣货失败");
				}
			}
		}else {
			json.setSuccess(false);
			json.setMsg("取消拣货处理失败：订单数据异常！");
			return json;
		}
	}

	/**
	 * 发运
	 */
	public Json shipment(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
		Json json = new Json();
		//
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderHeaderForNormalForm.getOrderno());
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if (orderHeaderForNormal != null) {
			//判断订单状态
			if (orderHeaderForNormal.getSostatus().equals("30") || orderHeaderForNormal.getSostatus().equals("40")) {
				//
//				try {
//					StockInXmlVo stockInXmlVo = new StockInXmlVo();
//					stockInXmlVo.setOrderCode(orderHeaderForNormal.getOrderCode());
//					stockInXmlVo.setOrderStatus(28);
//					String xmldata = JaxbUtil.convertToXml(stockInXmlVo, false);
//					logger.error("orderHeaderForNormalService-推送：" + xmldata);
//					ResponseVO responseVO = new ServiceControllerProxy(END_POINT).updateOrder(xmldata);
//					logger.error("orderHeaderForNormalService-接收：" + responseVO.getSuccess());
//					if (responseVO.getSuccess() == false) {
//						json.setSuccess(false);
//						json.setMsg("出库处理失败：订单尚未配载！");
//						return json;
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				//操作拣货
				List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByAllocationDetailsId(orderHeaderForNormalForm.getOrderno());
				if (allocationDetailsIdList != null) {
					for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
						Map<String ,Object> map=new HashMap<String, Object>();
						map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
						map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
						orderHeaderForNormalMybatisDao.pickingByOrder(map);
						String pickResult = map.get("result").toString();
						if (pickResult != null && pickResult.length() > 0) {
							if (pickResult.equals("000")) {
								continue;
							} else {
								json.setSuccess(false);
								json.setMsg("出库处理失败：" + pickResult);
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("出库处理失败：订单数据异常！");
							return json;
						}
					}
					//操作发运
					Map<String ,Object> map=new HashMap<String, Object>();
					//map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
					map.put("orderNo", orderHeaderForNormalForm.getOrderno());
					map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
					orderHeaderForNormalMybatisDao.shipmentByOrder(map);
					String shippmentResult = map.get("result").toString();
					if (shippmentResult != null && shippmentResult.length() > 0) {
						if (shippmentResult.equals("000")) {
							orderHeaderForNormalQuery.setCurrentTime(new Date());
							orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
							json.setSuccess(true);
							json.setMsg("000");
							json.setObj(orderHeaderForNormal);
							return json;
						} else {
							json.setSuccess(false);
							json.setMsg("出库处理失败：" + shippmentResult);
							return json;
						}
					} else {
						json.setSuccess(false);
						json.setMsg("出库处理失败：订单数据异常！");
						return json;
					}
				} else {
					json.setSuccess(false);
					json.setMsg("出库处理失败：订单数据异常！");
					return json;
				}
			} else if (orderHeaderForNormal.getSostatus().equals("50") ||
					orderHeaderForNormal.getSostatus().equals("60") ||
					orderHeaderForNormal.getSostatus().equals("62") ||
					orderHeaderForNormal.getSostatus().equals("63")) {
				//
//				try {
//					StockInXmlVo stockInXmlVo = new StockInXmlVo();
//					stockInXmlVo.setOrderCode(orderHeaderForNormal.getOrderCode());
//					stockInXmlVo.setOrderStatus(28);
//					String xmldata = JaxbUtil.convertToXml(stockInXmlVo, false);
//					logger.error("orderHeaderForNormalService-推送：" + xmldata);
//					ResponseVO responseVO = new ServiceControllerProxy(END_POINT).updateOrder(xmldata);
//					logger.error("orderHeaderForNormalService-接收：" + responseVO.getSuccess());
//					if (responseVO.getSuccess() == false) {
//						json.setSuccess(false);
//						json.setMsg("出库处理失败：订单尚未配载！");
//						return json;
//					}
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				//拣货/装箱状态订单直接操作发运
				Map<String ,Object> map=new HashMap<String, Object>();
				map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("orderNo", orderHeaderForNormalForm.getOrderno());
				map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
				orderHeaderForNormalMybatisDao.shipmentByOrder(map);
				String shippmentResult = map.get("result").toString();
				if (shippmentResult != null && shippmentResult.length() > 0) {
					if (shippmentResult.equals("000")) {
						orderHeaderForNormalQuery.setCurrentTime(new Date());
						orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
						json.setSuccess(true);
						json.setMsg("出库处理成功！");
						json.setObj(orderHeaderForNormal);
						return json;
					} else {
						json.setSuccess(false);
						json.setMsg("出库处理失败：" + shippmentResult);
						return json;
					}
				} else {
					json.setSuccess(false);
					json.setMsg("出库处理失败：订单数据异常！");
					return json;
				}
			} else {
				json.setSuccess(false);
				json.setMsg("出库处理失败：当前状态订单,不能操作出库!");
				return json;
			}
		} else {
			json.setSuccess(false);
			json.setMsg("出库处理失败：订单数据异常！");
			return json;
		}
	}
	/**
	 * 取消订单
	 */
	public Json cancel(String orderNo) {
		Json json = new Json();
		OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
		orderHeaderForNormalQuery.setOrderno(orderNo);
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
		if(orderHeaderForNormal != null){
			if (orderHeaderForNormal.getSostatus().equals("00")) {
				//创建状态订单直接操作取消
				Map<String ,Object> map = new HashMap<String, Object>();
				//map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("orderNo", orderNo);
				map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
				orderHeaderForNormalMybatisDao.cancelByOrder(map);
				String cancelResult = map.get("result").toString();
				if (cancelResult != null && cancelResult.length() > 0) {
					if (cancelResult.equals("000")) {
						orderHeaderForNormalQuery.setCurrentTime(new Date());
						orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
						json.setSuccess(true);
						json.setMsg("出库取消成功！");
						json.setObj(orderHeaderForNormal);
						return json;
					} else {
						json.setSuccess(false);
						json.setMsg("出库取消失败：" + cancelResult);
						return json;
					}
				} else {
					json.setSuccess(false);
					json.setMsg("出库取消失败！");
					return json;
				}
			} else if (orderHeaderForNormal.getSostatus().equals("30") ||
					orderHeaderForNormal.getSostatus().equals("40")) {
				//分配状态订单先取消分配再取消订单
				Map<String ,Object> map = new HashMap<String, Object>();
				map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
				map.put("orderNo", orderNo);
				map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
				orderHeaderForNormalMybatisDao.deAllocationByOrder(map);
				String allocationResult = map.get("result").toString();
				if (allocationResult != null && allocationResult.length() > 0) {
					if (allocationResult.equals("000")) {
						Map<String ,Object> cancelMap = new HashMap<String, Object>();
						cancelMap.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						cancelMap.put("orderNo", orderNo);
						cancelMap.put("userId", SfcUserLoginUtil.getLoginUser().getId());
						orderHeaderForNormalMybatisDao.cancelByOrder(map);
						String cancelResult = map.get("result").toString();
						if (cancelResult != null && cancelResult.length() > 0) {
							if (cancelResult.equals("000")) {
								orderHeaderForNormalQuery.setCurrentTime(new Date());
								orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
								json.setSuccess(true);
								json.setMsg("出库取消成功！");
								json.setObj(orderHeaderForNormal);
								return json;
							} else {
								json.setSuccess(false);
								json.setMsg("出库取消失败：" + cancelResult);
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("出库取消失败！");
							return json;
						}
					} else {
						json.setSuccess(false);
						json.setMsg("出库取消失败：" + allocationResult);
						return json;
					}
				} else {
					json.setSuccess(false);
					json.setMsg("出库取消失败！");
					return json;
				}
			} else if (orderHeaderForNormal.getSostatus().equals("50") ||
					orderHeaderForNormal.getSostatus().equals("60") ||
					orderHeaderForNormal.getSostatus().equals("62") ||
					orderHeaderForNormal.getSostatus().equals("63")) {
				//拣货/装箱状态订单先取消拣货再取消分配最后取消订单
				List<OrderHeaderForNormal> allocationDetailsIdList = orderHeaderForNormalMybatisDao.queryByUnAllocationDetailsId(orderNo);
				if (allocationDetailsIdList != null) {
					//取消拣货
					for (OrderHeaderForNormal allocationDetailsId : allocationDetailsIdList) {
						Map<String ,Object> map=new HashMap<String, Object>();
						map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
						map.put("allocationDetailsId", allocationDetailsId.getAllocationDetailsId());
						map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
						orderHeaderForNormalMybatisDao.unPickingByOrder(map);
						String pickResult = map.get("result").toString();
						if (pickResult != null && pickResult.length() > 0) {
							if (pickResult.equals("000")) {
								continue;
							} else {
								json.setSuccess(false);
								json.setMsg("出库取消失败：" + pickResult);
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("出库取消失败！");
							return json;
						}
					}
					//取消分配
					Map<String ,Object> map = new HashMap<String, Object>();
					map.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
					map.put("orderNo", orderNo);
					map.put("userId", SfcUserLoginUtil.getLoginUser().getId());
					orderHeaderForNormalMybatisDao.deAllocationByOrder(map);
					String allocationResult = map.get("result").toString();
					if (allocationResult != null && allocationResult.length() > 0) {
						if (allocationResult.equals("000")) {
							//取消订单
							Map<String ,Object> cancelMap = new HashMap<String, Object>();
							cancelMap.put("warehouseId", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
							cancelMap.put("orderNo", orderNo);
							cancelMap.put("userId", SfcUserLoginUtil.getLoginUser().getId());
							orderHeaderForNormalMybatisDao.cancelByOrder(map);
							String cancelResult = map.get("result").toString();
							if (cancelResult != null && cancelResult.length() > 0) {
								if (cancelResult.equals("000")) {
									orderHeaderForNormalQuery.setCurrentTime(new Date());
									orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderHeaderForNormalQuery);
									json.setSuccess(true);
									json.setMsg("出库取消成功！");
									json.setObj(orderHeaderForNormal);
									return json;
								} else {
									json.setSuccess(false);
									json.setMsg("出库取消失败：" + cancelResult);
									return json;
								}
							} else {
								json.setSuccess(false);
								json.setMsg("出库取消失败！");
								return json;
							}
						} else {
							json.setSuccess(false);
							json.setMsg("出库取消失败：" + allocationResult);
							return json;
						}
					} else {
						json.setSuccess(false);
						json.setMsg("出库取消失败！");
						return json;
					}
				} else {
					json.setSuccess(false);
					json.setMsg("出库取消失败！");
					return json;
				}
			} else {
				json.setSuccess(false);
				json.setMsg("出库取消失败：当前状态订单,不能操作取消！");
				return json;
			}
		} else {
			json.setSuccess(false);
			json.setMsg("出库取消成功！");
			return json;
		}
	}
	
	public Json importExcelData(MultipartHttpServletRequest mhsr){
		Json json = null;
		MultipartFile excelFile = mhsr.getFile("uploadData");
		if(excelFile != null && excelFile.getSize() > 0){
			json = importOrderDataService.importExcelData(excelFile);
		}
		return json;
	}
	
	public void exportTemplate(HttpServletResponse response, String token) {
		try(OutputStream toClient = new BufferedOutputStream(response.getOutputStream());) {
			File file = new File(ResourceUtil.getImportRootPath("order_template.xls"));
			response.reset();
			Cookie cookie = new Cookie("downloadToken",token);
			cookie.setMaxAge(60);	
			response.addCookie(cookie);
			response.setContentType(ContentTypeEnum.stream.getContentType());
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			
			try(InputStream fis = new BufferedInputStream(new FileInputStream(file))){
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				toClient.write(buffer);
				toClient.flush();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportPickingPdf(HttpServletResponse response, String orderNo) {
		StringBuilder sb = new StringBuilder();
		try (OutputStream os = response.getOutputStream()){
			sb.append("inline; filename=")
			  .append(URLEncoder.encode("拣货单PDF","UTF-8"))
			  .append(".pdf");
			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
			response.setContentType(ContentTypeEnum.pdf.getContentType());
			
			Document document = null;
			AcroFields form = null;
			PdfStamper stamper = null;
			PdfImportedPage page = null;
			ByteArrayOutputStream baos = null;

			if (StringUtils.isNotEmpty(orderNo)) {
				
				document = new Document(PDFUtil.getTemplate("wms_picking_jhck.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();
				
				int totalNum = 0;
				List<OrderDetailsForNormal> detailsList = null;
				int row = 10;
				int pageSize = 0;
				detailsList = new ArrayList<OrderDetailsForNormal>();
				OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
				orderHeaderForNormalQuery.setOrderno(orderNo);
				OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryByPickingList(orderHeaderForNormalQuery);
				if(orderHeaderForNormal == null){
				    return;
                }

				if(orderHeaderForNormal.getOrderDetailsForNormalList()==null){
					return;
				}
				for(OrderDetailsForNormal orderDetails : orderHeaderForNormal.getOrderDetailsForNormalList()){
					totalNum++;
					detailsList.add(orderDetails);
				}
				pageSize = (int)Math.ceil((double)totalNum / row);
				for(int i = 0 ; i < pageSize ; i++){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					baos = new ByteArrayOutputStream();
					stamper = new PdfStamper(PDFUtil.getTemplate("wms_picking_jhck.pdf"), baos);
					form = stamper.getAcroFields();
					form.setField("orderno", orderHeaderForNormal.getOrderno());
					//basCustomerService.selectCustomerById(orderHeaderForNormal.getCustomerId(), Constant.);
					form.setField("expectedShipmentTime", DateUtil.format(orderHeaderForNormal.getExpectedshipmenttime1(),"yyyy-MM-dd"));
					form.setField("carrierName", orderHeaderForNormal.getCarriername());
					form.setField("consigneeName", orderHeaderForNormal.getConsigneename());
					form.setField("cContact", orderHeaderForNormal.getCContact());
					form.setField("userdefine1", orderHeaderForNormal.getUserdefine1());
					form.setField("cAddress1", orderHeaderForNormal.getCAddress1());
					form.setField("c_Tel1", orderHeaderForNormal.getCTel1());
					form.setField("userdefine2", orderHeaderForNormal.getUserdefine2());
					form.setField("sOReference1", orderHeaderForNormal.getSoreference1());
					form.setField("notes", orderHeaderForNormal.getNotes());

					for(int j = 0 ; j < row ; j++){
						if(totalNum > (row * i + j)){
							BasSku basSku = basSkuService.getSkuInfo(orderHeaderForNormal.getCustomerid(),detailsList.get(row * i + j).getSku());
							form.setField("location."+(j), detailsList.get(row * i + j).getLocation());
							form.setField("sku."+(j), detailsList.get(row * i + j).getSku());
							form.setField("skuN."+(j), detailsList.get(row * i + j).getSkuName());
							form.setField("regNo."+(j), basSku.getReservedfield03());
							form.setField("desc."+(j), basSku.getDescrE());
							form.setField("batchNo."+(j), detailsList.get(row * i + j).getLotatt04());
							form.setField("seriNo."+(j), detailsList.get(row * i + j).getLotatt05());
							form.setField("ill."+(j), detailsList.get(row * i + j).getLotatt01());
							form.setField("lot01."+(j), detailsList.get(row * i + j).getLotatt02());
							form.setField("qtyE."+(j), detailsList.get(row * i + j).getQtyallocated().toString());
							form.setField("uom."+(j), detailsList.get(row * i + j).getUom());
							form.setField("qty."+(j), "");
							form.setField("double."+(j), basSku.getSkuGroup7());
							form.setField("card."+(j), basSku.getSkuGroup2());
							form.setField("report."+(j), basSku.getSkuGroup8());
							form.setField("remark."+(j), "");
						}
					}
					form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(orderHeaderForNormal.getOrderno(), 800)));
					stamper.setFormFlattening(true);
					stamper.close();
					page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
					pdfCopy.addPage(page);
				}
				document.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public void exportReceiptPdf(HttpServletResponse response, String orderNo) {
//		StringBuilder sb = new StringBuilder();
//		try (OutputStream os = response.getOutputStream()){
//			sb.append("inline; filename=")
//			  .append(URLEncoder.encode("拣货单PDF","UTF-8"))
//			  .append(".pdf");
//			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
//			response.setContentType(ContentTypeEnum.pdf.getContentType());
//
//			Document document = null;
//			AcroFields form = null;
//			PdfStamper stamper = null;
//			PdfImportedPage page = null;
//			ByteArrayOutputStream baos = null;
//
//			if (StringUtils.isNotEmpty(orderNo)) {
//
//				OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
//				orderHeaderForNormalQuery.setOrderNo(orderNo);
//
//				OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryPrintTemplate(orderHeaderForNormalQuery);
//
//				if (orderHeaderForNormal.getPrintTemplate() != null && orderHeaderForNormal.getPrintTemplate().length() > 0) {
//					//贝业签收单打印模板
//					if (orderHeaderForNormal.getPrintTemplate().equals("wms_receipt_beiye.pdf")) {
//
//
//						document = new Document(PDFUtil.getTemplate("wms_receipt_beiye.pdf").getPageSize(1));
//						PdfCopy pdfCopy = new PdfCopy(document, os);
//						document.open();
//
//						int totalNum = 0;
//						int row = 10;
//						int pageSize = 0;
//						DocOrderImportQuery docOrderImportQuery = new DocOrderImportQuery();
//						docOrderImportQuery.setOrderNo(orderNo);
//
//						DocReceiptHeader docReceiptHeader = docOrderImportMybatisDao.queryReceiptByBeiYe(docOrderImportQuery);
//						List<DocReceiptDetails> docReceiptDetailsList = new ArrayList<DocReceiptDetails>();
//
//						for(DocReceiptDetails docReceiptDetails : docReceiptHeader.getDocReceiptDetailsList()){
//							totalNum++;
//							docReceiptDetailsList.add(docReceiptDetails);
//						}
//
//						pageSize = (int)Math.ceil((double)totalNum / row);
//						for(int i = 0 ; i < pageSize ; i++){
//							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//							baos = new ByteArrayOutputStream();
//							stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt_beiye.pdf"), baos);
//							form = stamper.getAcroFields();
//							form.setField("customerOrderCode", docReceiptHeader.getCustomerOrderCode() == null ? "" : docReceiptHeader.getCustomerOrderCode());
//							form.setField("externalOrderCode", docReceiptHeader.getExternalOrderCode() == null ? "" : docReceiptHeader.getExternalOrderCode());
//							form.setField("orderDate", sdf.format(new Date()));
//							form.setField("city", docReceiptHeader.getCity() == null ? "" : docReceiptHeader.getCity());
//							form.setField("name", docReceiptHeader.getName() == null ? "" : docReceiptHeader.getName());
//							form.setField("address", docReceiptHeader.getAddress() == null ? "" : docReceiptHeader.getAddress());
//							form.setField("tel", docReceiptHeader.getTel() == null ? "" : docReceiptHeader.getTel());
//							for(int j = 0 ; j < row ; j++){
//								if(totalNum > (row * i + j)){
//									form.setField("seq"+(j+1), String.valueOf(j+1));
//									form.setField("itemCode"+(j+1), docReceiptDetailsList.get(row * i + j).getItemCode());
//									form.setField("itemName"+(j+1), docReceiptDetailsList.get(row * i + j).getItemName());
//									form.setField("cartons"+(j+1), docReceiptDetailsList.get(row * i + j).getQty());
//									form.setField("quantity"+(j+1), docReceiptDetailsList.get(row * i + j).getQty());
//									form.setField("lotnum"+(j+1), "*");
//								}
//							}
//							stamper.setFormFlattening(true);
//							stamper.close();
//							page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
//							pdfCopy.addPage(page);
//						}
//						document.close();
//					}
//				} else {
//
//					document = new Document(PDFUtil.getTemplate("wms_receipt.pdf").getPageSize(1));
//					PdfCopy pdfCopy = new PdfCopy(document, os);
//					document.open();
//
//					int totalNum = 0;
//					int row = 10;
//					int pageSize = 0;
//					List<OrderDetailsForNormal> detailsList = new ArrayList<OrderDetailsForNormal>();
//					OrderHeaderForNormal orderHeader = orderHeaderForNormalMybatisDao.queryByReceiptList(orderHeaderForNormalQuery);
//					for(OrderDetailsForNormal orderDetails : orderHeader.getOrderDetailsForNormalList()){
//						totalNum++;
//						detailsList.add(orderDetails);
//					}
//
//					pageSize = (int)Math.ceil((double)totalNum / row);
//					for(int i = 0 ; i < pageSize ; i++){
//						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//						baos = new ByteArrayOutputStream();
//						stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt.pdf"), baos);
//						form = stamper.getAcroFields();
//						form.setField("orderCode", orderHeader.getOrderCode() == null ? "" : orderHeader.getOrderCode());
//						form.setField("custName", orderHeader.getCustomerShortName());
//						form.setField("cartonQty", orderHeader.getBoxQty() == null ? "0" : String.valueOf(orderHeader.getBoxQty()));
//						form.setField("name", orderHeader.getConsigneeName() == null ? "" : orderHeader.getConsigneeName());
//						form.setField("address", orderHeader.getAddress() == null ? "" : orderHeader.getAddress());
//						form.setField("tel", orderHeader.getTel() == null ? "" : orderHeader.getTel());
//						form.setField("requiredTime", orderHeader.getRequiredDeliveryTime() == null ? "" : format.format(orderHeader.getRequiredDeliveryTime()));
//						form.setField("notes", orderHeader.getNotes() == null ? "" : orderHeader.getNotes());
//						form.setField("totalAmt", String.valueOf(orderHeader.getTotalPrice()));
//						for(int j = 0 ; j < row ; j++){
//							if(totalNum > (row * i + j)){
//								form.setField("seq"+(j+1), String.valueOf(detailsList.get(row * i + j).getOrderLineNo()));
//								form.setField("skuCode"+(j+1), detailsList.get(row * i + j).getSku());
//								form.setField("skuName"+(j+1), detailsList.get(row * i + j).getSkuName());
//								form.setField("qty"+(j+1), String.valueOf(detailsList.get(row * i + j).getQtyOrdered()));
//								form.setField("unit"+(j+1), detailsList.get(row * i + j).getUnit() == null ? "" : detailsList.get(row * i + j).getUnit());
//								form.setField("price"+(j+1), String.valueOf(detailsList.get(row * i + j).getTotalPrice()));
//								form.setField("totalPrice"+(j+1), String.valueOf(detailsList.get(row * i + j).getQtyOrdered() * detailsList.get(row * i + j).getTotalPrice()));
//							}
//						}
//						form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(orderHeader.getOrderCode(), 800)));
//						stamper.setFormFlattening(true);
//						stamper.close();
//						page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
//						pdfCopy.addPage(page);
//					}
//					document.close();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public List<EasyuiCombobox> getOrderTypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<OrderStatusResult> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryOrderType();
		if(orderHeaderForNormalList != null && orderHeaderForNormalList.size() > 0){
			//下拉框添加数据
			for(OrderStatusResult orderHeaderForNormal : orderHeaderForNormalList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(orderHeaderForNormal.getOrderType()));
				combobox.setValue(orderHeaderForNormal.getOrderTypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getOrderStatusCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<OrderStatusResult> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryOrderStatus();
		if(orderHeaderForNormalList != null && orderHeaderForNormalList.size() > 0){
			//下拉框添加数据
			for(OrderStatusResult orderHeaderForNormal : orderHeaderForNormalList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(orderHeaderForNormal.getOrderStatus()));
				combobox.setValue(orderHeaderForNormal.getOrderStatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getReleasestatusCombobox(){
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<OrderStatusResult> orderHeaderForNormalList = orderHeaderForNormalMybatisDao.queryReleaseStatus();
		if(orderHeaderForNormalList != null && orderHeaderForNormalList.size() > 0){
			//下拉框添加数据
			for(OrderStatusResult orderHeaderForNormal : orderHeaderForNormalList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(orderHeaderForNormal.getOrderStatus()));
				combobox.setValue(orderHeaderForNormal.getOrderStatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public OrderHeaderForNormal getOrderHeader(OrderHeaderForNormalQuery query) {
		query.setOrderno(query.getOrderno().replace(",", ""));
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(query);
		return orderHeaderForNormal;
	}

	public void exportBatchPdf(HttpServletResponse response, String orderCodeList) {
		StringBuilder sb = new StringBuilder();
		try (OutputStream os = response.getOutputStream()){
			sb.append("inline; filename=")
					.append(URLEncoder.encode("PDF","UTF-8"))
					.append(".pdf");
			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
			response.setContentType(ContentTypeEnum.pdf.getContentType());

			Document document = null;
			AcroFields form = null;
			PdfStamper stamper = null;
			PdfImportedPage page = null;
			ByteArrayOutputStream baos = null;

			if (StringUtils.isNotEmpty(orderCodeList)) {

				document = new Document(PDFUtil.getTemplate("wms_packing_jhck.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();
				//分割orderCodeList中的主单号pano
				String[] orderCodeArray = orderCodeList.split(",");

				for (String orderCode : orderCodeArray) {
					//根据主单pano查询对象DocPaHeader
					/*DocPaHeader docPaHeader = docPaHeaderDao.queryById(orderCode);
					if(docPaHeader!=null){

						int totalNum = 0;
						int row = 15;//每页条数
						int pageSize = 0;

						DocPaDetailsQuery query = new DocPaDetailsQuery();
						query.setAsnno(orderCode);
						//根据主单pano获取子单所有的产品 orderCode==pano
						List<DocPaDetails> detailsList =  docPaDetailsService.queryDocPdaDetails(orderCode);

						totalNum = detailsList.size();//总条数
						pageSize = (int)Math.ceil( (double) totalNum / row);//总页数
						for(int i=0;i<pageSize;i++){//单头内容
							baos = new ByteArrayOutputStream();
							stamper = new PdfStamper(PDFUtil.getTemplate("wms_receipt_jhck.pdf"), baos);
							form = stamper.getAcroFields();
							form.setField("putwayCode",docPaHeader.getPano());
							form.setField("receviedDdate", DateUtil.format(docPaHeader.getAddtime(),"yyyy-MM-dd"));
							form.setField("warehouseid", docPaHeader.getWarehouseid());
							form.setField("custName", docPaHeader.getCustomerid());
							form.setField("supplier", "");
							form.setField("notes", docPaHeader.getNotes());
							form.setField("page", "第"+(i+1)+"页,共"+pageSize+"页");
							//form.setField("barCode1", docPaHeader.getAsnno());
							form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(docPaHeader.getPano(), 800)));
							//form.replacePushbuttonField("orderCodeImg1", PDFUtil.genPdfButton(form, "orderCodeImg1", BarcodeGeneratorUtil.genBarcode(docPaHeader.getAsnno(), 800)));

							for(int j=0;j<row;j++){//主单产品明细
								if(totalNum > (row*i+j)){
									DocPaDetails docPaDetails = detailsList.get(row * i + j);
									BasSku basSku = basSkuService.getSkuInfo(docPaHeader.getCustomerid(),detailsList.get(row * i + j).getSku());
									//根据某一个订单明细查询关联的Doc_Asn_Details
									DocAsnDetailQuery queryDetail = new DocAsnDetailQuery();
									queryDetail.setAsnno(docPaDetails.getAsnno());
									queryDetail.setAsnlineno(docPaDetails.getAsnlineno());
									DocAsnDetailVO detailVO = docAsnDetailService.queryDocAsnDetail(queryDetail);

									form.setField("sku."+j, docPaDetails.getSku());
									form.setField("skuN."+j, basSku.getDescrC());
									form.setField("regNo."+j, basSku.getReservedfield03());
									form.setField("desc."+j, basSku.getDescrE());
									form.setField("batchNo."+j, docPaDetails.getUserdefine3());
									form.setField("seriNo."+j, docPaDetails.getUserdefine4());
									form.setField("ill."+j, detailVO.getLotatt07());
									form.setField("lot01."+j, detailVO.getLotatt01());
									form.setField("lot02."+j, detailVO.getLotatt02());
									form.setField("qtyE."+j, docPaDetails.getAsnqtyExpected().toString());
									form.setField("uom."+j, basSku.getDefaultreceivinguom());
									form.setField("qty."+j, docPaDetails.getPutwayqtyExpected().toString());
									form.setField("qtyA."+j, docPaDetails.getPutwayqtyCompleted().toString());
								}
							}
							stamper.setFormFlattening(true);
							stamper.close();
							page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
							pdfCopy.addPage(page);
						}
					}*/

				}
				document.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public List<OrderHeaderForNormalVO> getUndoneList(PageForm form) {

        List<OrderHeaderForNormal> orderHeaderForNormals = orderHeaderForNormalMybatisDao.queryPackageList(form.getStart(), form.getPageSize());

        OrderHeaderForNormalVO orderHeaderForNormalVO;
        List<OrderHeaderForNormalVO> orderHeaderForNormalVOS = new ArrayList<>();

        for (OrderHeaderForNormal orderHeaderForNormal : orderHeaderForNormals) {

            orderHeaderForNormalVO = new OrderHeaderForNormalVO();
            BeanUtils.copyProperties(orderHeaderForNormal, orderHeaderForNormalVO);
            orderHeaderForNormalVOS.add(orderHeaderForNormalVO);
        }

        return orderHeaderForNormalVOS;
    }

    public OrderHeaderForNormalVO queryByOrderno(String orderno) {

        OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderno);
        OrderHeaderForNormalVO headerVO = new OrderHeaderForNormalVO();

        if (orderHeaderForNormal != null) {

            BeanUtils.copyProperties(orderHeaderForNormal, headerVO);
        }

        return headerVO;
    }

	public void exportAccompanyingPdf(HttpServletResponse response, String orderNo) {
		StringBuilder sb = new StringBuilder();
		try (OutputStream os = response.getOutputStream()){
			sb.append("inline; filename=")
					.append(URLEncoder.encode("出库随货同行单PDF","UTF-8"))
					.append(".pdf");
			response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
			response.setContentType(ContentTypeEnum.pdf.getContentType());

			Document document = null;
			AcroFields form = null;
			PdfStamper stamper = null;
			PdfImportedPage page = null;
			ByteArrayOutputStream baos = null;

			if (StringUtils.isNotEmpty(orderNo)) {

				document = new Document(PDFUtil.getTemplate("wms_shipped_jhck.pdf").getPageSize(1));
				PdfCopy pdfCopy = new PdfCopy(document, os);
				document.open();

				int totalNum = 0;
				List<OrderDetailsForNormal> detailsList = null;
				int row = 10;
				int pageSize = 0;
				detailsList = new ArrayList<OrderDetailsForNormal>();
				OrderHeaderForNormalQuery orderHeaderForNormalQuery = new OrderHeaderForNormalQuery();
				orderHeaderForNormalQuery.setOrderno(orderNo);
				OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryByPickingList(orderHeaderForNormalQuery);
				if(orderHeaderForNormal == null){
					return;
				}

				if(orderHeaderForNormal.getOrderDetailsForNormalList()==null){
					return;
				}
				for(OrderDetailsForNormal orderDetails : orderHeaderForNormal.getOrderDetailsForNormalList()){
					totalNum++;
					detailsList.add(orderDetails);
				}
				pageSize = (int)Math.ceil((double)totalNum / row);
				for(int i = 0 ; i < pageSize ; i++){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					baos = new ByteArrayOutputStream();
					stamper = new PdfStamper(PDFUtil.getTemplate("wms_shipped_jhck.pdf"), baos);
					form = stamper.getAcroFields();
					form.setField("orderno", orderHeaderForNormal.getOrderno());
					//basCustomerService.selectCustomerById(orderHeaderForNormal.getCustomerId(), Constant.);
					form.setField("expectedShipmentTime", DateUtil.format(orderHeaderForNormal.getExpectedshipmenttime1(),"yyyy-MM-dd"));
					form.setField("carrierName", orderHeaderForNormal.getCarriername());
					form.setField("consigneeName", orderHeaderForNormal.getConsigneename());
					form.setField("cContact", orderHeaderForNormal.getCContact());
					form.setField("userdefine1", orderHeaderForNormal.getUserdefine1());
					form.setField("cAddress1", orderHeaderForNormal.getCAddress1());
					form.setField("c_Tel1", orderHeaderForNormal.getCTel1());
					form.setField("userdefine2", orderHeaderForNormal.getUserdefine2());
					form.setField("sOReference1", orderHeaderForNormal.getSoreference1());
					form.setField("notes", orderHeaderForNormal.getNotes());

					for(int j = 0 ; j < row ; j++){
						if(totalNum > (row * i + j)){
							BasSku basSku = basSkuService.getSkuInfo(orderHeaderForNormal.getCustomerid(),detailsList.get(row * i + j).getSku());
							form.setField("lineNo."+j,j+1+"");
							form.setField("location."+(j), detailsList.get(row * i + j).getLocation());
							form.setField("sku."+(j), detailsList.get(row * i + j).getSku());
							form.setField("skuN."+(j), detailsList.get(row * i + j).getSkuName());
							form.setField("regNo."+(j), basSku.getReservedfield03());
							form.setField("desc."+(j), basSku.getDescrE());
							form.setField("batchNo."+(j), detailsList.get(row * i + j).getLotatt04());
							form.setField("seriNo."+(j), detailsList.get(row * i + j).getLotatt05());
							form.setField("ill."+(j), detailsList.get(row * i + j).getLotatt01());
							form.setField("lot01."+(j), detailsList.get(row * i + j).getLotatt02());
							form.setField("qtyE."+(j), detailsList.get(row * i + j).getQtyallocated().toString());
							form.setField("uom."+(j), detailsList.get(row * i + j).getUom());
							form.setField("qty."+(j), "");
							form.setField("double."+(j), basSku.getSkuGroup7());
							form.setField("card."+(j), basSku.getSkuGroup2());
							form.setField("report."+(j), basSku.getSkuGroup8());
							form.setField("remark."+(j), "");
						}
					}
					form.replacePushbuttonField("orderCodeImg", PDFUtil.genPdfButton(form, "orderCodeImg", BarcodeGeneratorUtil.genBarcode(orderHeaderForNormal.getOrderno(), 800)));
					stamper.setFormFlattening(true);
					stamper.close();
					page = pdfCopy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
					pdfCopy.addPage(page);
				}
				document.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据sku和customerid
	 * @param sku
	 * @param customerId
	 * @return
	 */
	public List<EasyuiCombobox> getLotAttBySkuCustomerId(String sku,String customerId){
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		InvLotAttQuery query = new InvLotAttQuery();
		query.setCustomerid(customerId);
		query.setSku(sku);
		mybatisCriteria.setCondition(query);
		mybatisCriteria.setDistinct(true);
		List<EasyuiCombobox> comboboxList = new ArrayList<>();
		List<InvLotAtt> list = invLotAttMybatisDao.queryLotNoRepet(mybatisCriteria);
		if(list!=null && list.size()>0){
			for(InvLotAtt i : list){
				/*Map<String,Object> option = new HashMap<>();
				option.put("lotatt09",i.getLotatt09());
				option.put("lotatt10",i.getLotatt10());
				option.put("lotatt14",i.getLotatt14());
				option.put("lotatt15",i.getLotatt15());*/
				EasyuiCombobox com = new EasyuiCombobox();
				com.setId(i.getLotatt04());
				com.setValue(i.getLotatt04());
				//com.setOption(option);
				comboboxList.add(com);
			}
		}
		return comboboxList;
	}

	public List<EasyuiCombobox> getRefOut(){
		MybatisCriteria criteria = new MybatisCriteria();
		OrderHeaderForNormalQuery query = new OrderHeaderForNormalQuery();
		query.setOrderStatus("80");
		criteria.setCondition(query);
		List<EasyuiCombobox> comboboxList = new ArrayList<>();
		List<OrderHeaderForNormal> list = orderHeaderForNormalMybatisDao.queryByList(criteria);
		if(list!=null && list.size()>0){
			for(OrderHeaderForNormal h : list){
				EasyuiCombobox comb = new EasyuiCombobox();
				comb.setId(h.getOrderno());
				comb.setValue(h.getOrderno());
				comboboxList.add(comb);
			}
		}
		return comboboxList;
	}

	/**
	 * 引用出库
	 * @param orderno
	 * @return
	 */
	public Json doRefOut(String orderno,String refOrderno) throws Exception{
		OrderHeaderForNormal head = orderHeaderForNormalMybatisDao.queryById(orderno);
		if(head == null || !head.getSostatus().equals("00")){
			return Json.error("只有新建状态的出库单才能引用出库");
		}
		//已选出库单明细
		OrderDetailsForNormalQuery query = new OrderDetailsForNormalQuery();
		query.setOrderno(orderno);
		MybatisCriteria criteria = new MybatisCriteria();
		criteria.setCondition(query);
		List<OrderDetailsForNormal> detailsForNormals = orderDetailsForNormalMybatisDao.queryByPageList(criteria);
		if(detailsForNormals!=null && detailsForNormals.size()>0){
			OrderDetailsForNormalQuery queryRef = new OrderDetailsForNormalQuery();
			queryRef.setOrderno(refOrderno);
			MybatisCriteria criteriaRef = new MybatisCriteria();
			criteriaRef.setCondition(queryRef);
			List<OrderDetailsForNormal> detailsForNormalsRef = orderDetailsForNormalMybatisDao.queryByPageList(criteriaRef);
			if(detailsForNormalsRef == null || detailsForNormalsRef.size() == 0){
				return Json.error("操作失败，引用出库单明细为空");
			}
			Map<String,OrderDetailsForNormal> map = new HashMap<>();
			for(OrderDetailsForNormal detail : detailsForNormalsRef){
				map.put(getKey(detail),detail);
			}
			//判断是否明细正确
			for(OrderDetailsForNormal d : detailsForNormals){
				OrderDetailsForNormal dRef = map.get(getKey(d));
				if(dRef == null){
					return Json.error("操作失败，引用单据明细不匹配");
				}
			}
			//分配库存
			Json result = allocation(orderno);
			if(result.isSuccess()){
				head = orderHeaderForNormalMybatisDao.queryById(orderno);
				if(head.getSostatus().equals("40")){
					Json json = picking(orderno);
					if(json.isSuccess()){
						OrderHeaderForNormalForm form = new OrderHeaderForNormalForm();
						form.setOrderno(orderno);
						result = shipment(form);
						if(result.isSuccess()){
							return Json.error("发运成功");
						}else {
							return Json.error("发运失败");
						}
					}else {
						return Json.error("拣货失败");
					}
				}else{
					return Json.error("分配库存失败");
				}
			}else {
				return Json.error("分配库存失败");
			}
		}else{
			return Json.error("操作失败，出库单明细为空");
		}
	}

	private String getKey(OrderDetailsForNormal detail){
		return detail.getSku()+""+detail.getLotatt01()+detail.getLotatt02()+detail.getLotatt03()+detail.getLotatt04()
				+detail.getLotatt05()+detail.getLotatt06()+detail.getLotatt07()+detail.getLotatt08()+detail.getLotatt09()
				+detail.getLotatt10()+detail.getLotatt11()+detail.getLotatt12()+detail.getLotatt13()+detail.getLotatt13();
	}

}