package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasCustomer;
import com.wms.entity.DocQsmDetails;
import com.wms.entity.DocQsmHeader;
import com.wms.entity.InvLotLocId;
import com.wms.mybatis.dao.*;
import com.wms.query.DocQsmDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocQsmDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQsmDetailsForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("docQsmDetailsService")
public class DocQsmDetailsService extends BaseService {

	@Autowired
	private DocQsmDetailsMybatisDao docQsmDetailsMybatisDao;
	@Autowired
	private DocQsmHeaderMybatisDao docQsmHeaderMybatisDao;
	@Autowired
	private InvLotLocIdMybatisDao invLotLocIdMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;
//主页datagrid分页
	public EasyuiDatagrid<DocQsmDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocQsmDetailsQuery query) {
		EasyuiDatagrid<DocQsmDetailsVO> datagrid = new EasyuiDatagrid<DocQsmDetailsVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocQsmDetails> docQsmDetailsList = docQsmDetailsMybatisDao.queryByList(mybatisCriteria);
		DocQsmDetailsVO docQsmDetailsVO = null;
		List<DocQsmDetailsVO> docQsmDetailsVOList = new ArrayList<DocQsmDetailsVO>();
		for (DocQsmDetails docQsmDetails : docQsmDetailsList) {
			docQsmDetailsVO = new DocQsmDetailsVO();
			BeanUtils.copyProperties(docQsmDetails, docQsmDetailsVO);
			//供应商名称
			if(docQsmDetailsVO.getLotatt08()!=null) {
				String loatt08=docQsmDetailsVO.getLotatt08();
				BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(loatt08);
				if(basCustomer!=null) {
					docQsmDetailsVO.setLotatt08text(loatt08);
					docQsmDetailsVO.setLotatt08(basCustomer.getDescrC());
				}
			}
			//计算数量
			docQsmDetailsVO.setQtyeach(docQsmDetailsVO.getQty()*docQsmDetailsVO.getQty1());

			docQsmDetailsVOList.add(docQsmDetailsVO);
		}
		datagrid.setTotal((long)docQsmDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docQsmDetailsVOList);
		return datagrid;
	}
//生成质量状态管理 细单
	public Json commitQualityStatus(DocQsmDetailsForm form){
		Json json = new Json();
		InvLotLocId locId=new InvLotLocId();
		locId.setCustomerid(form.getCustomerid());
		locId.setLotnum(form.getLotnum());
		locId.setSku(form.getSku());
		locId.setLocationid(form.getLocationid());
		InvLotLocId   invLotLocId=invLotLocIdMybatisDao.queryByKeyLotatt(locId);
		DocQsmHeader docQsmHeader = docQsmHeaderMybatisDao.queryById(form.getQcudocno());
		if(invLotLocId!=null&&docQsmHeader!=null){
			long qcudoclineno=docQsmDetailsMybatisDao.getqcudoclinenoById(form.getQcudocno());
			String  warehouseid =SfcUserLoginUtil.getLoginUser().getWarehouse().getId();
			String  addwho =SfcUserLoginUtil.getLoginUser().getId();
				DocQsmDetailsForm detailsForm = new DocQsmDetailsForm();
				detailsForm.setQcudocno(form.getQcudocno());
				detailsForm.setQcudoclineno((int)qcudoclineno+1);
				detailsForm.setQcustatus("00");
				detailsForm.setLotatt14(invLotLocId.getLotatt14()==null?"":invLotLocId.getLotatt14());
				detailsForm.setCustomerid(invLotLocId.getCustomerid());
				detailsForm.setLotatt03(invLotLocId.getLotatt03());
				detailsForm.setLotatt08(invLotLocId.getLotatt08()==null?"":invLotLocId.getLotatt08());
				detailsForm.setSku(invLotLocId.getSku());
				detailsForm.setLotatt12(invLotLocId.getLotatt12()==null?"":invLotLocId.getLotatt12());
				detailsForm.setLotatt06(invLotLocId.getLotatt06()==null?"":invLotLocId.getLotatt06());
				detailsForm.setDescrc(invLotLocId.getDescrc());
				detailsForm.setLotatt04(invLotLocId.getLotatt04()==null?"":invLotLocId.getLotatt04());
				detailsForm.setLotatt05(invLotLocId.getLotatt05()==null?"":invLotLocId.getLotatt05());
				detailsForm.setLotatt07(invLotLocId.getLotatt07()==null?"":invLotLocId.getLotatt07());
				detailsForm.setLotatt01(invLotLocId.getLotatt01());
				detailsForm.setLotatt02(invLotLocId.getLotatt02());
				detailsForm.setLocqty(invLotLocId.getQty());
				detailsForm.setQty(form.getQty());
				detailsForm.setLotatt15(invLotLocId.getLotatt15()==null?"":invLotLocId.getLotatt15());
				detailsForm.setReservedfield06(invLotLocId.getReservedfield06());
				detailsForm.setLotatt10(invLotLocId.getLotatt10());
				detailsForm.setChangeProcess(invLotLocId.getLotatt10());
				detailsForm.setLocationid(invLotLocId.getLocationid());
				detailsForm.setFinddate(new Date());
				detailsForm.setRemarks(form.getRemarks());
				detailsForm.setRecordingDate(new Date());
				detailsForm.setRecordingPeople(addwho);
				detailsForm.setLotnum(invLotLocId.getLotnum());
				docQsmDetailsMybatisDao.add(detailsForm);
				json.setSuccess(true);
				json.setMsg("生成细单成功!");
		}else{
			json.setSuccess(false);
			json.setMsg("查询不到此条库存");
		}
		return json;
	}
//提交质量状态管理
	public Json commitQualityStatusWork(DocQsmDetailsForm form) {
		Json json = new Json();
		InvLotLocId locId = new InvLotLocId();
		locId.setCustomerid(form.getCustomerid());
		locId.setLotnum(form.getLotnum());
		locId.setSku(form.getSku());
		locId.setLocationid(form.getLocationid());
		InvLotLocId invLotLocId = invLotLocIdMybatisDao.queryByKeyLotatt(locId);

		if (invLotLocId != null) {
			String warehouseid = SfcUserLoginUtil.getLoginUser().getWarehouse().getId();
			String addwho = SfcUserLoginUtil.getLoginUser().getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("warehouseid", warehouseid);//仓库
			map.put("customerid", invLotLocId.getCustomerid());//货主
			map.put("sku", invLotLocId.getSku());             //产品代码
			map.put("lotnum", invLotLocId.getLotnum());       //批次
			map.put("locationid", invLotLocId.getLocationid());   //库位
			if(form.getUserdefine1()!=null){
				map.put("fmqcstatus", invLotLocId.getLotatt10());   //开始质量状态
				map.put("toqcstatus", form.getLotatt10());
			}else {
				map.put("fmqcstatus", invLotLocId.getLotatt10());   //开始质量状态
				map.put("toqcstatus", invLotLocId.getLotatt10().equals("HG") ? "BHG" : "HG");   //结束质量状态
			}
			map.put("locqty",form.getLocqty());   //库位原件数
			map.put("qty",form.getQty());       //处理不合格件数
			map.put("userid",addwho);
			docQsmDetailsMybatisDao.qualityStatus(map);
					String result = map.get("result").toString();
					if (result.substring(0, 3).equals("000")) {
						form.setQcustatus("40");
						form.setTreatmentDate(new Date());
						if(form.getUserdefine1()==null) {
							form.setLotatt10(invLotLocId.getLotatt10().equals("HG") ? "BHG" : "HG");
							form.setChangeProcess(form.getChangeProcess() + ">" + form.getLotatt10());
						}
						docQsmDetailsMybatisDao.updateBySelective(form);
						//判断主单状态  根据明细状态数量
					   String status="-1";
						DocQsmDetailsQuery query=new DocQsmDetailsQuery();
						query.setQcudocno(form.getQcudocno());
						query.setQcustatus("00");
                      int num1=docQsmDetailsMybatisDao.queryByListqcustatus(query);
						query.setQcustatus("40");
                      int num2=docQsmDetailsMybatisDao.queryByListqcustatus(query);
                      if(num1>0&&num2>0){
						  status="30";
					  }else if(num1>0&&num2==0){
						  status="00";
					  }else{
                      	  status="40";
					  }
                      //修改主单状态
                       DocQsmHeader qsmHeader=new DocQsmHeader();
						qsmHeader.setQcudocno(form.getQcudocno());
						qsmHeader.setStatus(status);
						docQsmHeaderMybatisDao.updateBySelective(qsmHeader);
						json.setSuccess(true);
						json.setMsg(status);
					} else {
						json.setSuccess(false);
						json.setMsg(result);
					}
			 } else {
				json.setSuccess(false);
				json.setMsg("查询不到此条库存");
			}
			return json;
		}
//细单指定库存 提交
	public Json upstreamT(DocQsmDetailsForm form) {
		Json json = new Json();
		InvLotLocId locId=new InvLotLocId();
		locId.setCustomerid(form.getCustomerid());
		locId.setLotnum(form.getLotnum());
		locId.setSku(form.getSku());
		locId.setLocationid(form.getLocationid());
		InvLotLocId   invLotLocId=invLotLocIdMybatisDao.queryByKeyLotatt(locId);
		DocQsmDetails docQsmDetails = docQsmDetailsMybatisDao.queryById(form);
		if(invLotLocId!=null&&docQsmDetails!=null){
			DocQsmDetailsForm detailsForm = new DocQsmDetailsForm();
			detailsForm.setQcudocno(form.getQcudocno());
			detailsForm.setQcudoclineno(form.getQcudoclineno());
			detailsForm.setLotatt14(invLotLocId.getLotatt14()==null?"":invLotLocId.getLotatt14());
			detailsForm.setCustomerid(invLotLocId.getCustomerid());
			detailsForm.setLotatt03(invLotLocId.getLotatt03());
			detailsForm.setLotatt08(invLotLocId.getLotatt08()==null?"":invLotLocId.getLotatt08());
			detailsForm.setSku(invLotLocId.getSku());
//			detailsForm.setLotatt12(invLotLocId.getLotatt12()==null?"":invLotLocId.getLotatt12());
			detailsForm.setLotatt06(invLotLocId.getLotatt06()==null?"":invLotLocId.getLotatt06());
			detailsForm.setDescrc(invLotLocId.getDescrc());
			detailsForm.setLotatt04(invLotLocId.getLotatt04()==null?"":invLotLocId.getLotatt04());
			detailsForm.setLotatt05(invLotLocId.getLotatt05()==null?"":invLotLocId.getLotatt05());
			detailsForm.setLotatt07(invLotLocId.getLotatt07()==null?"":invLotLocId.getLotatt07());
			detailsForm.setLotatt01(invLotLocId.getLotatt01());
			detailsForm.setLotatt02(invLotLocId.getLotatt02());
			detailsForm.setLocqty(invLotLocId.getQty());
			detailsForm.setLotatt15(invLotLocId.getLotatt15()==null?"":invLotLocId.getLotatt15());
			detailsForm.setReservedfield06(invLotLocId.getReservedfield06());
			detailsForm.setLocationid(invLotLocId.getLocationid());
			detailsForm.setLotnum(invLotLocId.getLotnum());
			docQsmDetailsMybatisDao.updateBySelective(detailsForm);
			json.setSuccess(true);
			json.setMsg("指定库存成功!");
		}else{
			json.setSuccess(false);
			json.setMsg("查询不到此条库存");
		}
		return json;
		}
//删除
	public Json deleteDocQsmDetails(DocQsmDetailsForm form) {
		Json json = new Json();
		DocQsmHeader  docQsmHeader=docQsmHeaderMybatisDao.queryById(form.getQcudocno());
		DocQsmDetails docQsmDetails = docQsmDetailsMybatisDao.queryById(form);
		if(docQsmHeader != null&&docQsmDetails!=null){
			//判断是否上游
			if(docQsmHeader.getHedi01()!=null&&docQsmHeader.getHedi01()!=""){
				json.setSuccess(false);
				json.setMsg("上游单号不可删除!");
				return json;
			}
			//判断只有新建状态细单可以删除
			if(!docQsmHeader.getStatus().equals("00")||!docQsmDetails.getQcustatus().equals("00")){
				json.setSuccess(false);
				json.setMsg("只有主单细单为新建状态才可以删除!");
			}else{
				docQsmDetailsMybatisDao.deletelineno(docQsmDetails);
				json.setSuccess(false);
				json.setMsg("操作成功!");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("不存在此库存!");
		}

		return json;
	}

/**
	 * 打印docQsm
	 */
	public List<DocQsmDetails> docQsmToPdf(String qcudocno) {
		List<DocQsmDetails> docQsmDetailsList=new ArrayList<>();
		//获得单子
		DocQsmDetails docQsmDetails=docQsmDetailsMybatisDao.queryByqcudocno(qcudocno);
	   //供应商名称
		if(docQsmDetails.getLotatt08()!=null) {
				String loatt08=docQsmDetails.getLotatt08();
				BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(loatt08);
				if(basCustomer!=null) {
					docQsmDetails.setLotatt08(basCustomer.getDescrC());
				}
			}
		//计算数量
		docQsmDetails.setQtyeach(docQsmDetails.getQty()*docQsmDetails.getQty1());
		//日期格式转换
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time="";
		if(docQsmDetails.getLotatt02()!=null) {
			time = sdf.format(docQsmDetails.getLotatt02());
		}
		docQsmDetails.setLotatt02Ex(time);
       //质量状态变更过程
       if(docQsmDetails.getChangeProcess()!=null){
       	 String changeP=docQsmDetails.getChangeProcess();
       	  if(changeP.equals("HG")){
       	  	  docQsmDetails.setChangeProcess("合格");
		  }else if(changeP.equals("BHG")){
			  docQsmDetails.setChangeProcess("不合格");
		  }else  if(changeP.equals("HG>BHG")){
			  docQsmDetails.setChangeProcess("合格>不合格");
		  }else if(changeP.equals("BHG>HG")){
			  docQsmDetails.setChangeProcess("不合格>合格");
		  }else{

		  }
	   }
		docQsmDetailsList.add(docQsmDetails);
		return docQsmDetailsList;
	}
}