package com.wms.service;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasGtn;
import com.wms.entity.BasSku;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaDocAsnDetailForm;
import com.wms.query.DocAsnDetailQuery;
import com.wms.query.InvLotAttQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocAsnDetailQuery;
import com.wms.result.PdaResult;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.DocAsnDetailVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocAsnDetailForm;
import com.wms.vo.pda.PdaDocAsnDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

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

        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByPageList(mybatisCriteria);
	    if (docAsnDetailList.size() == 1) {

            DocAsnDetail docAsnDetail = docAsnDetailList.get(0);
            BeanUtils.copyProperties(docAsnDetail, pdaDocAsnDetailVO);

            //通过GTIN获取SKU
//            String sku = "";
//            BasGtn basGtn = basGtnMybatisDao.queryByGTIN(query.getGTIN());
//            if (basGtn == null) {
//
//                InvLotAttQuery invLotAttQuery = new InvLotAttQuery();
//                BeanUtils.copyProperties(query, invLotAttQuery);
//                mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(invLotAttQuery));
//                List<InvLotAtt> invLotAttList = invLotAttMybatisDao.queryByList(mybatisCriteria);
//                if (invLotAttList.size() == 1) {
//
//                    InvLotAtt invLotAtt = invLotAttList.get(0);
//                    sku = invLotAtt.getSku();
//                }
//            }else {
//
//                sku = basGtn.getSku();
//            }
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("customerid", docAsnDetail.getCustomerid());
//            map.put("sku", sku);
//            BasSku basSku = basSkuMybatisDao.queryById(map);

            PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
            BeanUtils.copyProperties(query, basSkuQuery);
            BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

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

        if (form == null) {

            return new PdaResult(PdaResult.CODE_FAILURE, "提交数据缺失");
        }
        //通过asnno+扫描到的批次属性 获取入库明细档
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(form));
        List<DocAsnDetail> docAsnDetailList = docAsnDetailsMybatisDao.queryByPageList(mybatisCriteria);
        if (docAsnDetailList.size() == 1) {

            /* 配置收货存储数据
             * 操作人员需set一下，不然会从docAsnDetail copy出来
             * receivingTime 代码里获取下
             * receivinglocation 固定 STAGE01
             * DocAsnDetail里面有warehouseid，copy之后重设一下,否则会为空
             * */
            DocAsnDetail docAsnDetail = docAsnDetailList.get(0);
            String warehouseid = form.getWarehouseid();
            BeanUtils.copyProperties(docAsnDetail, form);
            form.setWarehouseid(warehouseid);
            form.setEditwho("Gizmo");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            form.setReceivingtime(dateFormat.format(new Date()));
            form.setReceivinglocation("STAGE01");

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
        }else {

            return new PdaResult(PdaResult.CODE_FAILURE, "预入库明细档数据重复或缺失");
        }

    }
}