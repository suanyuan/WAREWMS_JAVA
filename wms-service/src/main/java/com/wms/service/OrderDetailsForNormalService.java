package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasPackage;
import com.wms.entity.BasSku;
import com.wms.entity.order.OrderDetailsForNormal;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.dao.*;
import com.wms.query.BasPackageQuery;
import com.wms.query.OrderDetailsForNormalQuery;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.Json;
import com.wms.vo.OrderDetailsForNormalVO;
import com.wms.vo.form.OrderDetailsForNormalForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderDetailsForNormalService")
public class OrderDetailsForNormalService extends BaseService {

	@Autowired
	private OrderHeaderForNormalMybatisDao orderHeaderForNormalMybatisDao;
	@Autowired
	private OrderDetailsForNormalMybatisDao orderDetailsForNormalMybatisDao;
	@Autowired
	private BasSkuService basSkuService;
	@Autowired
	private BasPackageService basPackageService;
	@Autowired
	private BasCustomerService basCustomerService;
	@Autowired
	private GspProductRegisterService gspProductRegisterService;
	@Autowired
	private GspOperateLicenseService gspOperateLicenseService;
	@Autowired
	private GspOperateDetailService gspOperateDetailService;
	@Autowired
	private BasPackageMybatisDao basPackageMybatisDao;
	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	public EasyuiDatagrid<OrderDetailsForNormalVO> getPagedDatagrid(EasyuiDatagridPager pager, OrderDetailsForNormalQuery query) {
		EasyuiDatagrid<OrderDetailsForNormalVO> datagrid = new EasyuiDatagrid<OrderDetailsForNormalVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<OrderDetailsForNormal> orderDetailsForNormalList = orderDetailsForNormalMybatisDao.queryByPageList(mybatisCriteria);

		OrderDetailsForNormal orderDetailsForNormalSum = new OrderDetailsForNormal();
		if(query.getOrderno()!=null){
			orderDetailsForNormalSum = orderDetailsForNormalMybatisDao.queryBySum(query.getOrderno());
        }
		double zero = 0;
		OrderDetailsForNormalVO orderDetailsForNormalVO = null;
		List<OrderDetailsForNormalVO> orderDetailsForNormalVOList = new ArrayList<OrderDetailsForNormalVO>();
		if(orderDetailsForNormalList.size()==0){
			orderDetailsForNormalVO = new OrderDetailsForNormalVO();
			orderDetailsForNormalVO.setQtyorderedSum(zero);//qtyordered 订货件数
			orderDetailsForNormalVO.setQtyallocatedSum(zero);//allocated 分配件数
			orderDetailsForNormalVO.setQtypickedSum(zero);//picked 拣货件数
			orderDetailsForNormalVO.setQtyshippedSum(zero);//shipped 发货件数
			orderDetailsForNormalVO.setQtyorderedEachSum(zero);//qtyorderedEach 订货数量
			orderDetailsForNormalVO.setQtyallocatedEachSum(zero);//allocatedEach 分配数量
			orderDetailsForNormalVO.setQtypickedEachSum(zero);//pickedEach 拣货数量
			orderDetailsForNormalVO.setQtyshippedEachSum(zero);//shippedEach 发货数量
			orderDetailsForNormalVOList.add(orderDetailsForNormalVO);
		}
		for (OrderDetailsForNormal orderDetailsForNormal : orderDetailsForNormalList) {
			orderDetailsForNormalVO = new OrderDetailsForNormalVO();
			BeanUtils.copyProperties(orderDetailsForNormal, orderDetailsForNormalVO);

			Map<String, Object> param2 = new HashMap<>();
			param2.put("customerid", orderDetailsForNormal.getCustomerid());
			param2.put("sku", orderDetailsForNormal.getSku());
			BasSku basSku1 = basSkuMybatisDao.queryById(param2);
			orderDetailsForNormalVO.setSkuName(basSku1.getReservedfield01()); //产品名
			orderDetailsForNormalVO.setDescrc(basSku1.getDescrC());//规格
			if(orderDetailsForNormalSum!=null){
				orderDetailsForNormalVO.setQtyorderedSum(orderDetailsForNormalSum.getQtyordered());//qtyordered 订货件数
				orderDetailsForNormalVO.setQtyallocatedSum(orderDetailsForNormalSum.getQtyallocated());//allocated 分配件数
				orderDetailsForNormalVO.setQtypickedSum(orderDetailsForNormalSum.getQtypicked());//picked 拣货件数
				orderDetailsForNormalVO.setQtyshippedSum(orderDetailsForNormalSum.getQtyshipped());//shipped 发货件数
				orderDetailsForNormalVO.setQtyorderedEachSum(orderDetailsForNormalSum.getQtyorderedEach());//qtyorderedEach 订货数量
				orderDetailsForNormalVO.setQtyallocatedEachSum(orderDetailsForNormalSum.getQtyallocatedEach());//allocatedEach 分配数量
				orderDetailsForNormalVO.setQtypickedEachSum(orderDetailsForNormalSum.getQtypickedEach());//pickedEach 拣货数量
				orderDetailsForNormalVO.setQtyshippedEachSum(orderDetailsForNormalSum.getQtyshippedEach());//shippedEach 发货数量
			}
			orderDetailsForNormalVOList.add(orderDetailsForNormalVO);
		}

		datagrid.setTotal((long) orderDetailsForNormalMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(orderDetailsForNormalVOList);
		return datagrid;
	}
	public Json add(OrderDetailsForNormalForm orderDetailsForNormalForm) throws Exception {
		Json json = new Json();
		String orderNo = orderDetailsForNormalForm.getOrderno();
		OrderHeaderForNormal orderHeaderForNormal = orderHeaderForNormalMybatisDao.queryById(orderNo);
		if(orderHeaderForNormal == null){
			return Json.error("没有对应的表头单据");
		}

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
			BasPackageQuery query = new BasPackageQuery();
			query.setPackid(basSku.getPackid());
			BasPackage basPackage = basPackageService.queryBasPackBy(query);
			orderDetailsForNormal.setUom(basPackage.getPackuom1());
			if(basSku.getNetweight() != null){
				orderDetailsForNormal.setNetweight(basSku.getNetweight().doubleValue());
			}else{
				orderDetailsForNormal.setNetweight(0d);
			}
			if(basSku.getGrossweight()!=null){
				orderDetailsForNormal.setGrossweight(basSku.getGrossweight().doubleValue());
			}else {
				orderDetailsForNormal.setGrossweight(0d);
			}
			orderDetailsForNormal.setQtyorderedEach(orderDetailsForNormal.getQtyordered()*basPackage.getQty1().doubleValue());
		}

		//判断经营范围是否匹配
		/*Json operateJson = isRightOperate(orderHeaderForNormal.getCustomerid(),orderHeaderForNormal.getConsigneeid(),orderDetailsForNormalForm.getSku());
		if(!operateJson.isSuccess()){
			return operateJson;
		}*/
		//效期
		//证照期限

		orderDetailsForNormal.setLotatt14(orderHeaderForNormal.getSoreference2());
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

			//数量换算
            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            orderDetailsForNormal.setQtyorderedEach(orderDetailsForNormal.getQtyordered() * basPackage.getQty1().doubleValue());
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
			orderHeaderForNormalQuery.setOrderno(orderNo);
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
			orderHeaderForNormalQuery.setOrderno(orderNo);
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

	public Json isRightOperate(String customerId,String receId,String sku){
		//TODO 经营范围匹配修改（经营生产拆分后需要区分证照）
		return Json.success("ok");
		/*BasSku basSku = basSkuService.getSkuInfo(customerId,sku);
		BasCustomer basCustomer = basCustomerService.selectCustomerById(receId, Constant.CODE_CUS_TYP_CO);
		if(basSku == null){
			return Json.error("查询不到产品档案");
		}
		if(basCustomer == null){
			return Json.error("查询不到收货单位");
		}
		String gspregisterNo = basSku.getReservedfield03();
		String enterpriseId = basCustomer.getEnterpriseId();
		GspOperateLicenseQuery query = new GspOperateLicenseQuery();
		query.setEnterpriseId(enterpriseId);
		query.setIsUse(Constant.IS_USE_YES);
		GspProductRegister gspProductRegister = gspProductRegisterService.queryByRegisterNo(gspregisterNo);
		GspOperateLicense gspOperateLicense = gspOperateLicenseService.getGspOperateLicenseBy(query);

		List<GspOperateDetailVO> registerOperate = gspOperateDetailService.queryOperateDetailByLicense(gspProductRegister.getProductRegisterId());
		List<GspOperateDetailVO> operateDetailVOS = gspOperateDetailService.queryOperateDetailByLicense(gspOperateLicense.getOperateId());

		if(registerOperate == null || registerOperate.size() == 0){
			return Json.error("注册证分类目录为空");
		}

		if(operateDetailVOS == null || operateDetailVOS.size() == 0){
			return Json.error("经营许可证经营范围为空");
		}

		List<String> arrregister = new ArrayList<>();
		List<String> arroperate = new ArrayList<>();

		for(GspOperateDetailVO v : registerOperate){
			arrregister.add(v.getOperateId());
		}
		for(GspOperateDetailVO v : operateDetailVOS){
			arroperate.add(v.getOperateId());
		}
		for(String s : arrregister){
			if(arroperate.toString().indexOf(s)==-1){
				return Json.error("经营范围不匹配");
			}
		}
		return Json.success("");*/
	}
}