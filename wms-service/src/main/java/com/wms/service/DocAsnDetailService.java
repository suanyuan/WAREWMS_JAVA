package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasGtn;
import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.dao.*;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.InvLotAttQuery;
import com.wms.query.pda.PdaDocAsnDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnDetailForm;
import com.wms.vo.form.pda.PdaDocAsnDetailForm;
import com.wms.vo.pda.PdaDocAsnDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("docAsnDetailService")
public class DocAsnDetailService extends BaseService {

	@Autowired
	private DocAsnDetailsMybatisDao docAsnDetailsMybatisDao;

	@Autowired
	private BasPackageMybatisDao basPackageMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private BasGtnMybatisDao basGtnMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

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

    /**
     * 收货提交
     * @param form pda上传表单数据
     * @return 结论
     */
	public PdaResult receiveGoods(PdaDocAsnDetailForm form) {

	    PdaResult result = new PdaResult();
//	    if (form != null) {
//
//            //通过asnno+asnlineno获取入库明细档
//            Map<String, Object> detailMap = new HashMap<>();
//            detailMap.put("asnno", form.getAsnno());
//            detailMap.put("asnlineno", form.getAsnlineno());
//            DocAsnDetail docAsnDetail = docAsnDetailsMybatisDao.queryById(detailMap);
//
//            //配置收货存储数据
//            PdaDocAsnDetailQuery query = new PdaDocAsnDetailQuery();
//            BeanUtils.copyProperties(docAsnDetail, query);
//            query.setWarehouseid(form.getWarehouseid());
//            query.setReceivedqty(form.getReceivedqty());
//            query.setEditwho("wyt");
//            query.setReceivinglocation("STAGE01");
////	        query.setTotalcubic(docAsnDetail.getTotalcubic().toString());
////	        query.setTotalgrossweight(docAsnDetail.getTotalgrossweight().toString());
////	        query.setTotalprice(docAsnDetail.getTotalprice().toString());
//
//            docAsnDetailsMybatisDao.receiveGoods(query);
//            if (query.getResult().equals(Constant.PROCEDURE_OK)) {
//
//                result.setErrorCode(PdaResult.CODE_SUCCESS);
//                result.setMsg("收货成功");
//                return result;
//            } else {
//
//                result.setErrorCode(PdaResult.CODE_FAILURE);
//                result.setMsg(query.getResult());
//                return result;
//            }
//        }
        result.setErrorCode(PdaResult.CODE_FAILURE);
        result.setMsg("收货数据缺失");
        return result;
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

	    //TODO 别忘了开开
//	    if (query.getLotatt02() != null) {
//
//	        StringBuilder stringBuilder = new StringBuilder(query.getLotatt02());
//	        stringBuilder.insert(4, "-");
//	        stringBuilder.insert(2, "-");
//	        stringBuilder.insert(0, "20");
//	        query.setLotatt02(stringBuilder.toString());
//        }

	    //TODO 新增一个查询方法
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByPageList(mybatisCriteria);
	    if (docAsnDetailList.size() == 1) {

            DocAsnDetail docAsnDetail = docAsnDetailList.get(0);
            BeanUtils.copyProperties(docAsnDetail, pdaDocAsnDetailVO);

            //通过GTIN获取SKU
            String sku = "";
            BasGtn basGtn = basGtnMybatisDao.queryByGTIN(query.getGTIN());
            if (basGtn == null) {

                //TODO 新增一个查询方法
                InvLotAttQuery invLotAttQuery = new InvLotAttQuery();
                BeanUtils.copyProperties(query, invLotAttQuery);
                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotAttQuery));
                List<InvLotAtt> invLotAttList = invLotAttMybatisDao.queryByList(mybatisCriteria);
                if (invLotAttList.size() == 1) {

                    InvLotAtt invLotAtt = invLotAttList.get(0);
                    sku = invLotAtt.getSku();
                }
            }else {

                sku = basGtn.getSku();
            }

            Map<String, Object> map = new HashMap<>();
            map.put("customerid", docAsnDetail.getCustomerid());
            map.put("sku", sku);
            BasSku basSku = basSkuMybatisDao.queryById(map);

            pdaDocAsnDetailVO.setBasSku(basSku);
        }
	    return pdaDocAsnDetailVO;
    }
}