package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.vo.pda.PdaDocAsnDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.mybatis.dao.DocAsnDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.DocOrderDetail;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocAsnDetailForm;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.DocOrderDetailQuery;

@Service("docAsnDetailService")
public class DocAsnDetailService extends BaseService {

	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;

	public EasyuiDatagrid<DocAsnDetailVO> getPagedDatagrid(EasyuiDatagridPager pager, DocAsnDetailQuery query) {
		EasyuiDatagrid<DocAsnDetailVO> datagrid = new EasyuiDatagrid<DocAsnDetailVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByPageList(mybatisCriteria);
		DocAsnDetailVO docAsnDetailVO = null;
		List<DocAsnDetailVO> docAsnDetailVOList = new ArrayList<DocAsnDetailVO>();
		for (DocAsnDetail docAsnDetail : docAsnDetailList) {
			docAsnDetailVO = new DocAsnDetailVO();
			BeanUtils.copyProperties(docAsnDetail, docAsnDetailVO);
			docAsnDetailVOList.add(docAsnDetailVO);
		}
		datagrid.setTotal((long) docAsnDetailsMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docAsnDetailVOList);
		return datagrid;
	}

	public Json addDocAsnDetail(DocAsnDetailForm docAsnDetailForm) throws Exception {
		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(docAsnDetailForm.getAsnno());
		/*获取新的明细行号*/
		long orderlineno = docAsnDetailsMybatisDao.getAsnlinenoById(docAsnDetailQuery);
		DocAsnDetail docAsnDetail = new DocAsnDetail();
		BeanUtils.copyProperties(docAsnDetailForm, docAsnDetail);
		docAsnDetail.setAsnlineno(orderlineno + 1);
		docAsnDetail.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
		docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docAsnDetailsMybatisDao.add(docAsnDetail);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}

	public Json editDocAsnDetail(DocAsnDetailForm docAsnDetailForm) {
		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(docAsnDetailForm.getAsnno());
		docAsnDetailQuery.setAsnlineno(docAsnDetailForm.getAsnlineno());
		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(docAsnDetailQuery);
		BeanUtils.copyProperties(docAsnDetailForm, docAsnDetail);
		docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
		docAsnDetailsMybatisDao.update(docAsnDetail);
		json.setSuccess(true);
		json.setMsg("提交成功");
		return json;
	}

	public Json deleteDocAsnDetail(String asnno, long asnlineno) {
		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(asnno);
		docAsnDetailQuery.setAsnlineno(asnlineno);
		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(docAsnDetailQuery);
		if(docAsnDetail != null){
			docAsnDetailsMybatisDao.delete(docAsnDetail);
		}
		json.setSuccess(true);
		json.setMsg("删除成功");
		return json;
	}
	
	public Json receiveDocAsnDetail(String asnno, long asnlineno) {
		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(asnno);
		docAsnDetailQuery.setAsnlineno(asnlineno);
		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(docAsnDetailQuery);
		String result = "";
		if(docAsnDetail != null){
			docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			docAsnDetail.setWarehouseid(SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
			docAsnDetailsMybatisDao.receiveByAsn(docAsnDetail);
			result = docAsnDetail.getResult();
			System.out.println("<---------------------------->");
			System.out.println(result);
			System.out.println("<---------------------------->");
		}
		if (result.substring(0,3).equals("000")) {
			json.setSuccess(true);
			json.setMsg("收货成功！");
		} else {
			json.setSuccess(false);
			json.setMsg("收货失败！"+result);
		}
		return json;
	}
	
	public List<EasyuiCombobox> getDocAsnDetailCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByAll();
		if(docAsnDetailList != null && docAsnDetailList.size() > 0){
			for(DocAsnDetail docAsnDetail : docAsnDetailList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docAsnDetail.getAsnlineno()));
				combobox.setValue(docAsnDetail.getAsnno());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

    /**
     * 通过效期反推获取收货明细
     * @param asnno 收货任务单号
     * @param lotatt 批次属性-效期 lotatt06
     * @return 收货明细
     */
	public PdaDocAsnDetailVO queryDetailByLotatt(String asnno, String lotatt) {

	    PdaDocAsnDetailVO pdaDocAsnDetailVO = new PdaDocAsnDetailVO();
	    DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryDetailByLotatt(asnno, lotatt);
	    if (docAsnDetail != null) {

            BeanUtils.copyProperties(docAsnDetail, pdaDocAsnDetailVO);
            pdaDocAsnDetailVO.setModel(docAsnDetail.getBasSku().getDescrE());
        }
	    return pdaDocAsnDetailVO;
    }

}