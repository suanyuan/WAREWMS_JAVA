package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.mybatis.dao.DocOrderDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.entity.DocOrderDetail;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocOrderDetailVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocOrderDetailForm;
import com.wms.query.DocOrderDetailQuery;

@Service("docOrderDetailService")
public class DocOrderDetailService extends BaseService {

	@Autowired
	private DocOrderDetailsMybatisDao docOrderDetailsMybatisDao;

	public EasyuiDatagrid<DocOrderDetailVO> getPagedDatagrid(EasyuiDatagridPager pager, DocOrderDetailQuery query) {
		EasyuiDatagrid<DocOrderDetailVO> datagrid = new EasyuiDatagrid<DocOrderDetailVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocOrderDetail> docOrderDetailList = docOrderDetailsMybatisDao.queryByPageList(mybatisCriteria);
		DocOrderDetailVO docOrderDetailVO = null;
		List<DocOrderDetailVO> docOrderDetailVOList = new ArrayList<DocOrderDetailVO>();
		for (DocOrderDetail docOrderDetail : docOrderDetailList) {
			docOrderDetailVO = new DocOrderDetailVO();
			BeanUtils.copyProperties(docOrderDetail, docOrderDetailVO);
			docOrderDetailVOList.add(docOrderDetailVO);
		}
		datagrid.setTotal((long) docOrderDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docOrderDetailVOList);
		return datagrid;
	}

	public Json addDocOrderDetail(DocOrderDetailForm docOrderDetailForm) throws Exception {
		Json json = new Json();
		DocOrderDetailQuery docOrderDetailQuery = new DocOrderDetailQuery();
		docOrderDetailQuery.setOrderno(docOrderDetailForm.getOrderno());
		/*获取新的订单明细行号*/
		long orderlineno = docOrderDetailsMybatisDao.getOrderlinenoById(docOrderDetailQuery);
		DocOrderDetail docOrderDetail = new DocOrderDetail();
		BeanUtils.copyProperties(docOrderDetailForm, docOrderDetail);
		docOrderDetail.setOrderlineno(orderlineno + 1);
		docOrderDetail.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		docOrderDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docOrderDetailsMybatisDao.add(docOrderDetail);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json editDocOrderDetail(DocOrderDetailForm docOrderDetailForm) {
		Json json = new Json();
		DocOrderDetailQuery docOrderDetailQuery = new DocOrderDetailQuery();
		docOrderDetailQuery.setOrderno(docOrderDetailForm.getOrderno());
		docOrderDetailQuery.setOrderlineno(docOrderDetailForm.getOrderlineno());
		DocOrderDetail docOrderDetail = docOrderDetailsMybatisDao.queryById(docOrderDetailQuery);
		BeanUtils.copyProperties(docOrderDetailForm, docOrderDetail);
		docOrderDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docOrderDetailsMybatisDao.update(docOrderDetail);
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public Json deleteDocOrderDetail(String orderno, long orderlineno) {
		Json json = new Json();
		DocOrderDetailQuery docOrderDetailQuery = new DocOrderDetailQuery();
		docOrderDetailQuery.setOrderno(orderno);
		docOrderDetailQuery.setOrderlineno(orderlineno);
		DocOrderDetail docOrderDetail = docOrderDetailsMybatisDao.queryById(docOrderDetailQuery);
		if(docOrderDetail != null){
			docOrderDetailsMybatisDao.delete(docOrderDetail);
		}
		json.setSuccess(true);
		json.setMsg("资料处理成功！");
		return json;
	}

	public List<EasyuiCombobox> getDocOrderDetailCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocOrderDetail> docOrderDetailList = docOrderDetailsMybatisDao.queryByAll();
		if(docOrderDetailList != null && docOrderDetailList.size() > 0){
			for(DocOrderDetail docOrderDetail : docOrderDetailList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docOrderDetail.getOrderlineno()));
				combobox.setValue(docOrderDetail.getOrderno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

}