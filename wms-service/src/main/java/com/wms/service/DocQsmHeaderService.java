package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocQsmDetails;
import com.wms.entity.DocQsmHeader;
import com.wms.mybatis.dao.DocQsmDetailsMybatisDao;
import com.wms.mybatis.dao.DocQsmHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("docQsmHeaderService")
public class DocQsmHeaderService extends BaseService {

	@Autowired
	private DocQsmHeaderMybatisDao docQsmHeaderMybatisDao;
	@Autowired
	private DocQsmDetailsMybatisDao docQsmDetailsMybatisDao;
	@Autowired
	private CommonService commonService;
//主页datagrid分页
	public EasyuiDatagrid<DocQsmHeader> getPagedDatagrid(EasyuiDatagridPager pager, DocQsmHeader query) {
		EasyuiDatagrid<DocQsmHeader> datagrid = new EasyuiDatagrid<DocQsmHeader>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocQsmHeader> docQsmHeaderList = docQsmHeaderMybatisDao.queryByList(mybatisCriteria);
		datagrid.setTotal((long)docQsmHeaderMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docQsmHeaderList);
		return datagrid;
	}


//增加头档 生成主单号
	public Json add(DocQsmHeader form) {
		Json json = new Json();
		    String  warehouseid =SfcUserLoginUtil.getLoginUser().getWarehouse().getId();
			String  addwho =SfcUserLoginUtil.getLoginUser().getId();
			String  qcudocno=commonService.generateSeq("QCUNO",warehouseid);
			if(qcudocno!=null) {
				DocQsmHeader HeaderForm = new DocQsmHeader();
				HeaderForm.setQcudocno(qcudocno);
				HeaderForm.setStatus(form.getStatus());
				HeaderForm.setCustomerid(form.getCustomerid());
				HeaderForm.setNotes(form.getNotes());
				HeaderForm.setAddtime(new Date());
				HeaderForm.setAddwho(addwho);
				HeaderForm.setWarehouseid(warehouseid);
				docQsmHeaderMybatisDao.add(HeaderForm);
				json.setSuccess(true);
				json.setMsg(qcudocno);
			}else{
				json.setSuccess(false);
				json.setMsg("生成单号失败!");
				}
			return json;
	}
//编辑头档
	public Json edit(DocQsmHeader form) {
		Json json = new Json();
		String  warehouseid =SfcUserLoginUtil.getLoginUser().getWarehouse().getId();
		String  editwho =SfcUserLoginUtil.getLoginUser().getId();
		DocQsmHeader docQsmHeader=docQsmHeaderMybatisDao.queryById(form.getQcudocno());
		if(docQsmHeader!=null){
			DocQsmHeader headerForm = new DocQsmHeader();
			headerForm.setQcudocno(form.getQcudocno());
			headerForm.setNotes(form.getNotes());
			headerForm.setEdittime(new Date());
			headerForm.setEditwho(editwho);
			docQsmHeaderMybatisDao.updateBySelective(headerForm);
			json.setSuccess(true);
			json.setMsg(form.getNotes());
		}else{
			json.setSuccess(false);
			json.setMsg("修改资料失败!单号对应资料不存在!");
		}
		return json;
	}
//删除
	public Json delete(String id) {
		Json json = new Json();
		DocQsmHeader docQsmHeader = docQsmHeaderMybatisDao.queryById(id);
		if(docQsmHeader != null){
			if(docQsmHeader.getHedi01()!=null&&docQsmHeader.getHedi01()!=""){
				json.setSuccess(false);
				json.setMsg("上游单号不可删除!");
				return json;
			}
			if(docQsmHeader.getStatus().equals("00")||docQsmHeader.getStatus().equals("90")) {
				docQsmHeaderMybatisDao.delete(docQsmHeader);
				docQsmDetailsMybatisDao.delete(docQsmHeader);
				json.setSuccess(true);
				json.setMsg("删除成功!");
			}else{
				json.setSuccess(false);
				json.setMsg("只有任务创建和取消的的任务单才可以删除!");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("此任务单在库存中不存在!");
		}

		return json;
	}

/**
	 * 关单
	 * 目前行状态只有 00 && 40
	 *
	 * @param form ~
	 * @return ~
	 */
	public Json close(DocQsmHeader form) {
		Json json=new Json();
		DocQsmHeader docQsmHeader = docQsmHeaderMybatisDao.queryById(form.getQcudocno());
		if(docQsmHeader==null){
			json.setSuccess(false);
			json.setMsg("不存在此条库存!");
			return  json;
		}
		switch (docQsmHeader.getStatus()) {
			case "00":
			case "30":
				json.setSuccess(false);
				json.setMsg("任务单[" + form.getQcudocno() + "]有未完成的管理任务，不可结束!");
				break;
			case "40":
				form.setStatus("99");
				docQsmHeaderMybatisDao.updateBySelective(form);
				DocQsmDetails docQsmDetails=new DocQsmDetails();
				docQsmDetails.setQcudocno(form.getQcudocno());
				docQsmDetails.setQcustatus("99");
				docQsmDetailsMybatisDao.update(docQsmDetails);
				json.setSuccess(true);
				json.setMsg("任务单[" + form.getQcudocno() + "]操作成功!");
				break;
			default:
				json.setSuccess(false);
				json.setMsg("任务单[" + form.getQcudocno() + "]当前状态不可操作!");
				break;
		}

		return json;
	}
/**
	 * 取消单
	 * 目前行状态只有 00 && 40
	 *
	 * @param form ~
	 * @return ~
	 */
	public Json cancel(DocQsmHeader form) {
		Json json=new Json();
		DocQsmHeader docQsmHeader = docQsmHeaderMybatisDao.queryById(form.getQcudocno());
		if(docQsmHeader==null){
			json.setSuccess(false);
			json.setMsg("不存在此条库存!");
			return  json;
		}
		switch (docQsmHeader.getStatus()) {
			case "00":
				form.setStatus("90");
				docQsmHeaderMybatisDao.updateBySelective(form);
				DocQsmDetails docQsmDetails=new DocQsmDetails();
				docQsmDetails.setQcudocno(form.getQcudocno());
				docQsmDetails.setQcustatus("90");
				docQsmDetailsMybatisDao.update(docQsmDetails);
				json.setSuccess(true);
				json.setMsg("任务单[" + form.getQcudocno() + "]操作成功!");
				break;
			default:
				json.setSuccess(false);
				json.setMsg("任务单[" + form.getQcudocno() + "]当前状态不可操作!");
				break;
		}

		return json;
	}


//生成质量状态管理
//	public Json commitQualityStatus(DocQsmHeaderForm form){
//		Json json = new Json();
//		InvLotLocId locId=new InvLotLocId();
//		locId.setCustomerid(form.getCustomerid());
//		locId.setLotnum(form.getLotnum());
//		locId.setSku(form.getSku());
//		locId.setLocationid(form.getLocationid());
//		InvLotLocId   invLotLocId=invLotLocIdMybatisDao.queryByKeyLotatt(locId);
//		if(invLotLocId!=null){
//			String  warehouseid =SfcUserLoginUtil.getLoginUser().getWarehouse().getId();
//			String  addwho =SfcUserLoginUtil.getLoginUser().getId();
//			String  qcudocno=commonService.generateSeq("QCUNO",warehouseid);
//			if(qcudocno!=null) {
//				DocQsmHeaderForm HeaderForm = new DocQsmHeaderForm();
//				HeaderForm.setQcudocno(qcudocno);
//				HeaderForm.setQcustatus("00");
//				HeaderForm.setLotatt14(invLotLocId.getLotatt14()==null?"":invLotLocId.getLotatt14());
//				HeaderForm.setCustomerid(invLotLocId.getCustomerid());
//				HeaderForm.setLotatt03(invLotLocId.getLotatt03());
//				HeaderForm.setLotatt08(invLotLocId.getLotatt08()==null?"":invLotLocId.getLotatt08());
//				HeaderForm.setSku(invLotLocId.getSku());
//				HeaderForm.setLotatt12(invLotLocId.getLotatt12()==null?"":invLotLocId.getLotatt12());
//				HeaderForm.setLotatt06(invLotLocId.getLotatt06()==null?"":invLotLocId.getLotatt06());
//				HeaderForm.setDescrc(invLotLocId.getDescrc());
//				HeaderForm.setLotatt04(invLotLocId.getLotatt04()==null?"":invLotLocId.getLotatt04());
//				HeaderForm.setLotatt05(invLotLocId.getLotatt05()==null?"":invLotLocId.getLotatt05());
//				HeaderForm.setLotatt07(invLotLocId.getLotatt07()==null?"":invLotLocId.getLotatt07());
//				HeaderForm.setLotatt01(invLotLocId.getLotatt01());
//				HeaderForm.setLotatt02(invLotLocId.getLotatt02());
//				HeaderForm.setLocqty(invLotLocId.getQty());
//				HeaderForm.setQty(form.getQty());
//				HeaderForm.setLotatt15(invLotLocId.getLotatt15()==null?"":invLotLocId.getLotatt15());
//				HeaderForm.setReservedfield06(invLotLocId.getReservedfield06());
//				HeaderForm.setLotatt10(invLotLocId.getLotatt10());
//				HeaderForm.setChangeProcess(invLotLocId.getLotatt10());
//				HeaderForm.setLocationid(invLotLocId.getLocationid());
//				HeaderForm.setFinddate(new Date());
//				HeaderForm.setRemarks(form.getRemarks());
//				HeaderForm.setRecordingDate(new Date());
//				HeaderForm.setRecordingPeople(addwho);
//				HeaderForm.setLotnum(invLotLocId.getLotnum());
//				docQsmHeaderMybatisDao.add(HeaderForm);
//				json.setSuccess(false);
//				json.setMsg("生成成功!");
//			}else {
//				json.setSuccess(false);
//				json.setMsg("生成单号失败!");
//			}
//		}else{
//			json.setSuccess(false);
//			json.setMsg("查询不到此条库存");
//		}
//		return json;
//	}

//提交质量状态管理
//	public Json commitQualityStatusWork(DocQsmHeaderForm form) {
//		Json json = new Json();
//		InvLotLocId locId = new InvLotLocId();
//		locId.setCustomerid(form.getCustomerid());
//		locId.setLotnum(form.getLotnum());
//		locId.setSku(form.getSku());
//		locId.setLocationid(form.getLocationid());
//		InvLotLocId invLotLocId = invLotLocIdMybatisDao.queryByKeyLotatt(locId);
//		if (invLotLocId != null) {
//			String warehouseid = SfcUserLoginUtil.getLoginUser().getWarehouse().getId();
//			String addwho = SfcUserLoginUtil.getLoginUser().getId();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("warehouseid", warehouseid);//仓库
//			map.put("customerid", invLotLocId.getCustomerid());//货主
//			map.put("sku", invLotLocId.getSku());             //产品代码
//			map.put("lotnum", invLotLocId.getLotnum());       //批次
//			map.put("locationid", invLotLocId.getLocationid());   //库位
//			map.put("fmqcstatus", invLotLocId.getLotatt10());   //开始质量状态
//			map.put("toqcstatus", invLotLocId.getLotatt10().equals("HG")?"BHG":"HG");   //结束质量状态
//			map.put("locqty",form.getLocqty());   //库位原件数
//			map.put("qty",form.getQty());       //处理不合格件数
//			map.put("userid",addwho);
//			docQsmHeaderMybatisDao.qualityStatus(map);
//					String result = map.get("result").toString();
//					if (result.substring(0, 3).equals("000")) {
//						form.setQcustatus("40");
//						form.setTreatmentDate(new Date());
//						form.setLotatt10(invLotLocId.getLotatt10().equals("HG")?"BHG":"HG");
//						form.setChangeProcess(form.getChangeProcess()+">"+form.getLotatt10());
//						docQsmHeaderMybatisDao.updateBySelective(form);
//						json.setSuccess(true);
//						json.setMsg("质量状态变更成功！");
//					} else {
//						json.setSuccess(true);
//						json.setMsg(result);
//					}
//			 } else {
//				json.setSuccess(false);
//				json.setMsg("查询不到此条库存");
//			}
//			return json;
//		}
	/**
	 * 打印docQsm
	 */
//	public List<DocQsmHeader> docQsmToPdf(String qcudocno) {
//		List<DocQsmHeader> docQsmHeaderList=new ArrayList<>();
//		//获得单子
//		DocQsmHeader docQsmHeader=docQsmHeaderMybatisDao.queryByqcudocno(qcudocno);
//	   //供应商名称
//		if(docQsmHeader.getLotatt08()!=null) {
//				String loatt08=docQsmHeader.getLotatt08();
//				BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(loatt08);
//				if(basCustomer!=null) {
//					docQsmHeader.setLotatt08(basCustomer.getDescrC());
//				}
//			}
//		//计算数量
//		docQsmHeader.setQtyeach(docQsmHeader.getQty()*docQsmHeader.getQty1());
//		//日期格式转换
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String time="";
//		if(docQsmHeader.getLotatt02()!=null) {
//			time = sdf.format(docQsmHeader.getLotatt02());
//		}
//		docQsmHeader.setLotatt02Ex(time);
//       //质量状态变更过程
//       if(docQsmHeader.getChangeProcess()!=null){
//       	 String changeP=docQsmHeader.getChangeProcess();
//       	  if(changeP.equals("HG")){
//       	  	  docQsmHeader.setChangeProcess("合格");
//		  }else if(changeP.equals("BHG")){
//			  docQsmHeader.setChangeProcess("不合格");
//		  }else  if(changeP.equals("HG>BHG")){
//			  docQsmHeader.setChangeProcess("合格>不合格");
//		  }else if(changeP.equals("BHG>HG")){
//			  docQsmHeader.setChangeProcess("不合格>合格");
//		  }else{
//
//		  }
//	   }
//		docQsmHeaderList.add(docQsmHeader);
//		return docQsmDetailsList;
//	}
}