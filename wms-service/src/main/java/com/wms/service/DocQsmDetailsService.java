package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.BasCustomerMybatisDao;
import com.wms.mybatis.dao.DocQsmDetailsMybatisDao;
import com.wms.mybatis.dao.InvLotLocIdMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocQsmDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocQsmDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQsmDetailsForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("docQsmDetailsService")
public class DocQsmDetailsService extends BaseService {

	@Autowired
	private DocQsmDetailsMybatisDao docQsmDetailsMybatisDao;
	@Autowired
	private InvLotLocIdMybatisDao invLotLocIdMybatisDao;
	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;
	@Autowired
	private CommonService commonService;
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
					docQsmDetailsVO.setLotatt08(basCustomer.getDescrC());
				}
			}
			docQsmDetailsVOList.add(docQsmDetailsVO);
		}
		datagrid.setTotal((long)docQsmDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docQsmDetailsVOList);
		return datagrid;
	}
//生成质量状态管理
	public Json commitQualityStatus(DocQsmDetailsForm form){
		Json json = new Json();
		InvLotLocId locId=new InvLotLocId();
		locId.setCustomerid(form.getCustomerid());
		locId.setLotnum(form.getLotnum());
		locId.setSku(form.getSku());
		locId.setLocationid(form.getLocationid());
		InvLotLocId   invLotLocId=invLotLocIdMybatisDao.queryByKeyLotatt(locId);
		if(invLotLocId!=null){
			String  warehouseid =SfcUserLoginUtil.getLoginUser().getWarehouse().getId();
			String  addwho =SfcUserLoginUtil.getLoginUser().getId();
			String  qcudocno=commonService.generateSeq("QCUNO",warehouseid);
			if(qcudocno!=null) {
				DocQsmDetailsForm detailsForm = new DocQsmDetailsForm();
				detailsForm.setQcudocno(qcudocno);
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
				detailsForm.setLocqty(Math.round(invLotLocId.getQty()));
				detailsForm.setQty(form.getQty());
				detailsForm.setLotatt15(invLotLocId.getLotatt15()==null?"":invLotLocId.getLotatt15());
				detailsForm.setReservedfield06(invLotLocId.getReservedfield06());
				detailsForm.setLotatt10(invLotLocId.getLotatt10());
				detailsForm.setLocationid(invLotLocId.getLocationid());
				detailsForm.setFinddate(new Date());
				detailsForm.setRemarks(form.getRemarks());
				detailsForm.setRecordingDate(new Date());
				detailsForm.setRecordingPeople(addwho);
				detailsForm.setLotnum(invLotLocId.getLotnum());
				docQsmDetailsMybatisDao.add(detailsForm);
				json.setSuccess(false);
				json.setMsg("生成成功!");
			}else {
				json.setSuccess(false);
				json.setMsg("生成单号失败!");
			}
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
			map.put("locqty",form.getLocqty());   //库位原件数
			map.put("qty",form.getQty());       //处理不合格件数
			map.put("userid",addwho);
			docQsmDetailsMybatisDao.qualityStatus(map);
					String result = map.get("result").toString();
					if (result.substring(0, 3).equals("000")) {
						form.setQcustatus("40");
						form.setTreatmentDate(new Date());
						docQsmDetailsMybatisDao.updateBySelective(form);
						json.setSuccess(true);
						json.setMsg("质量状态变更成功！");
					} else {
						json.setSuccess(true);
						json.setMsg(result);
					}
			 } else {
				json.setSuccess(false);
				json.setMsg("查询不到此条库存");
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
	public Json close(DocQsmDetailsForm form) {
		Json json=new Json();
		DocQsmDetails docQsmDetails = docQsmDetailsMybatisDao.queryById(form.getQcudocno());
		switch (docQsmDetails.getQcustatus()) {
			case "00":
				json.setSuccess(false);
				json.setMsg("任务单[" + form.getQcudocno() + "]有未完成的管理任务，不可结束!");
				break;
			case "30":
			case "40":
				form.setQcustatus("99");
				docQsmDetailsMybatisDao.updateBySelective(form);
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
	public Json cancel(DocQsmDetailsForm form) {
		Json json=new Json();
		DocQsmDetails docQsmDetails = docQsmDetailsMybatisDao.queryById(form.getQcudocno());
		switch (docQsmDetails.getQcustatus()) {
			case "00":
				form.setQcustatus("90");
				docQsmDetailsMybatisDao.updateBySelective(form);
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

	public Json deleteDocQsmDetails(String id) {
		Json json = new Json();
		DocQsmDetails docQsmDetails = docQsmDetailsMybatisDao.queryById(id);
		if(docQsmDetails != null){
			if(docQsmDetails.getQcustatus().equals("00")||docQsmDetails.getQcustatus().equals("90")) {
				docQsmDetailsMybatisDao.delete(docQsmDetails);
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


}