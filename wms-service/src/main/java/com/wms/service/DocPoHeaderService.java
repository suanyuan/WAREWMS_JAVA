package com.wms.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.entity.DocPoDetails;
import com.wms.entity.DocPoHeader;
import com.wms.mybatis.dao.DocPoDetailsMybatisDao;
import com.wms.mybatis.dao.DocPoHeaderMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.dao.DocPoHeaderDao;
import com.wms.vo.DocPoHeaderVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocPoHeaderForm;
import com.wms.query.DocPoHeaderQuery;

@Service("docPoHeaderService")
public class DocPoHeaderService extends BaseService {

	@Autowired
	private DocPoHeaderDao docPoHeaderDao;
	@Autowired
    private DocPoHeaderMybatisDao docPoHeaderMybatisDao;
	@Autowired
	private DocPoDetailsMybatisDao docPoDetailsMybatisDao;
//模糊条件查询
	public EasyuiDatagrid<DocPoHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocPoHeaderQuery query) {
		EasyuiDatagrid<DocPoHeaderVO> datagrid = new EasyuiDatagrid<DocPoHeaderVO>();
		//query.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocPoHeader> docPoHeaderList = docPoHeaderMybatisDao.queryByList(mybatisCriteria);
		DocPoHeaderVO docPoHeaderVO = null;
		List<DocPoHeaderVO> docPoHeaderVOList = new ArrayList<DocPoHeaderVO>();
		for (DocPoHeader docPoHeader : docPoHeaderList) {
			docPoHeaderVO = new DocPoHeaderVO();
			BeanUtils.copyProperties(docPoHeader, docPoHeaderVO);
//			docPoHeaderVO.setAddtime(dataformat.parse(docPoHeader.getAddtime().toString()));
			docPoHeaderVOList.add(docPoHeaderVO);
		}
		datagrid.setTotal((long) docPoHeaderMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docPoHeaderVOList);
		return datagrid;
	}
//增加采购订单
	public Json addDocPoHeader(DocPoHeaderForm docPoHeaderForm) throws Exception {
		Json json = new Json();
		/*获取新的订单号*/
		Map<String ,Object> map=new HashMap<String, Object>();
		map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		docPoHeaderMybatisDao.getIdSequence(map);
		String resultCode = map.get("resultCode").toString();
		String resultNo = map.get("resultNo").toString();
		if (resultCode.substring(0,3).equals("000")) {
		DocPoHeader docPoHeader = new DocPoHeader();
		BeanUtils.copyProperties(docPoHeaderForm, docPoHeader);
		docPoHeader.setPono(resultNo);
		docPoHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		docPoHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		docPoHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docPoHeaderMybatisDao.add(docPoHeader);
		json.setSuccess(true);
		json.setMsg(resultNo);
		return json;
		} else {
			json.setSuccess(false);
			json.setMsg(resultCode);
			return json;
		}
	}
//编辑采购订单
	public Json editDocPoHeader(DocPoHeaderForm docPoHeaderForm) {
		Json json = new Json();
		DocPoHeaderQuery docPoHeaderQuery=new DocPoHeaderQuery();
		docPoHeaderQuery.setPono(docPoHeaderForm.getPono());
		DocPoHeader docPoHeader = docPoHeaderMybatisDao.queryById(docPoHeaderQuery);
		BeanUtils.copyProperties(docPoHeaderForm, docPoHeader);
		docPoHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
		docPoHeader.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docPoHeaderMybatisDao.updateBySelective(docPoHeader);
		json.setSuccess(true);
		return json;
	}
//取消采购订单
	public Json cancelDocPoHeader(String id) {
		Json json = new Json();
		DocPoHeader poHeader=new DocPoHeader();
		poHeader.setPono(id);
		poHeader.setPostatus("90");
		DocPoDetails poDetails=new DocPoDetails();
		poDetails.setPono(id);
		poDetails.setPolinestatus("90");
		DocPoHeader docPoHeader = docPoHeaderMybatisDao.queryById(poHeader);
		if(docPoHeader != null){
			docPoHeaderMybatisDao.updateBySelective(poHeader);
			docPoDetailsMybatisDao.updateBySelective(poDetails);
		}
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}
//获取采购类型
	public List<EasyuiCombobox> getPotypeCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocPoHeader> docPoHeaderList = docPoHeaderMybatisDao.getPotypeCombobox();
		if(docPoHeaderList != null && docPoHeaderList.size() > 0){
			for(DocPoHeader docPoHeader : docPoHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docPoHeader.getPotype()));
				combobox.setValue(docPoHeader.getPotypeName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
//获取采购状态
	public List<EasyuiCombobox> getPostatusCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocPoHeader> docPoHeaderList = docPoHeaderMybatisDao.getPostatusCombobox();
		if(docPoHeaderList != null && docPoHeaderList.size() > 0){
			for(DocPoHeader docPoHeader : docPoHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docPoHeader.getPostatus()));
				combobox.setValue(docPoHeader.getPostatusName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}
//获取仓库
	public List<EasyuiCombobox> getWarehouseCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocPoHeader> docPoHeaderList = docPoHeaderMybatisDao.getWarehouseCombobox();
		if(docPoHeaderList != null && docPoHeaderList.size() > 0){
			for(DocPoHeader docPoHeader : docPoHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docPoHeader.getWarehouseid()));
				combobox.setValue(docPoHeader.getWarehouseName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}