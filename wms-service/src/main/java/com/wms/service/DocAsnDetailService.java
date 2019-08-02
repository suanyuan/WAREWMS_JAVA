package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocAsnDetailForm;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocAsnDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnDetailForm;
import com.wms.vo.pda.PdaDocAsnDetailVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("docAsnDetailService")
public class DocAsnDetailService extends BaseService {

	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private BasGtnMybatisDao basGtnMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private BasGtnLotattService basGtnLotattService;

	@Autowired
	private InvLotAttService invLotAttService;

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

	    DocAsnDetail docAsnDetail = new DocAsnDetail();
	    BeanUtils.copyProperties(docAsnDetailForm, docAsnDetail);
        //判断预入库明细里面的sku和客户id下的18个批属是否存在
        InvLotAtt invLotAtt = invLotAttService.queryInsertLotatts(docAsnDetail);
        //判断是否要插入扫码批次匹配表
        basGtnLotattService.queryInsertGtnLotatt(invLotAtt, docAsnDetailForm.getAsnno());

		Json json = new Json();
		DocAsnDetailQuery docAsnDetailQuery = new DocAsnDetailQuery();
		docAsnDetailQuery.setAsnno(docAsnDetailForm.getAsnno());
		/*获取新的明细行号*/
		int orderlineno = docAsnDetailsMybatisDao.getAsnlinenoById(docAsnDetailQuery);
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
			if(StringUtils.isEmpty(docAsnDetail.getReceivinglocation())){
				//docAsnDetail.setPlantoloc("STAGE01");
				docAsnDetail.setReceivinglocation("STAGE01");
			}
			docAsnDetail.setEditwho(SfcUserLoginUtil.getLoginUser().getId());
			docAsnDetail.setReceivedtime(new Date());
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
     * 获取入库预报
     * @param query ~
     * @return 明细
     */
	public PdaDocAsnDetailVO queryDocAsnDetail(PdaDocAsnDetailQuery query) {

	    PdaDocAsnDetailVO pdaDocAsnDetailVO = new PdaDocAsnDetailVO();

        PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
        BeanUtils.copyProperties(query, basSkuQuery);
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

        if (basSku == null) {
            pdaDocAsnDetailVO.setBasSku(null);
            return pdaDocAsnDetailVO;
        }

        query.setSku(basSku.getSku());
        DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryForScan(query);
        if (docAsnDetail != null) {
            BeanUtils.copyProperties(docAsnDetail, pdaDocAsnDetailVO);
            pdaDocAsnDetailVO.setBasSku(basSku);
        }
        return pdaDocAsnDetailVO;
    }

    /**
     * 收货提交
     * @param form pda上传表单数据
     * @return 结论
     */
    public PdaResult receiveGoods(PdaDocAsnDetailForm form) {

        PdaBasSkuQuery skuQuery = new PdaBasSkuQuery();
        PdaDocAsnDetailQuery detailQuery = new PdaDocAsnDetailQuery();
        BeanUtils.copyProperties(form, skuQuery);
        BeanUtils.copyProperties(form, detailQuery);

        //SKU
        BasSku basSku = basSkuMybatisDao.queryForScan(skuQuery);

        if (basSku == null) return new PdaResult(PdaResult.CODE_FAILURE, "产品档案缺失");

        //DocAsnDetails
        detailQuery.setSku(basSku.getSku());
        DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryForScan(detailQuery);

        if (docAsnDetail == null) return new PdaResult(PdaResult.CODE_FAILURE, "收货明细数据缺失");

        BeanUtils.copyProperties(docAsnDetail, form);
        form.setEditwho("Gizmo");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        form.setReceivingtime(dateFormat.format(new Date()));
        form.setReceivinglocation("STAGE01");

        //收货
        try {
            docAsnDetailsMybatisDao.receiveGoods(form);
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
        }
        if (form.getResult().equals(Constant.PROCEDURE_OK)) {

            return new PdaResult(PdaResult.CODE_SUCCESS, "收货成功");
        } else {

            return new PdaResult(PdaResult.CODE_FAILURE, form.getResult());
        }
    }

	/**
	 * 获取入库预报
	 * @param query ~
	 * @return 明细
	 */
	public DocAsnDetailVO queryDocAsnDetail(DocAsnDetailQuery query) {

		DocAsnDetailVO docAsnDetailVO = new DocAsnDetailVO();

		DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(query);
		if (docAsnDetail != null) {
			BeanUtils.copyProperties(docAsnDetail, docAsnDetailVO);
		}
		return docAsnDetailVO;
	}
}