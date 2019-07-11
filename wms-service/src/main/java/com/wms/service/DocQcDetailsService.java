package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.constant.Constant;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSku;
import com.wms.entity.DocQcDetails;
import com.wms.entity.GspEnterpriseInfo;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.dao.*;
import com.wms.mybatis.entity.pda.PdaGspProductRegister;
import com.wms.query.DocQcDetailsQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.query.pda.PdaDocQcDetailQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocQcDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocQcDetailsForm;
import com.wms.vo.pda.PdaDocQcDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docQcDetailsService")
public class DocQcDetailsService extends BaseService {

	@Autowired
	private DocQcDetailsMybatisDao docQcDetailsDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private GspEnterpriseInfoMybatisDao enterpriseInfoMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private GspProductRegisterMybatisDao productRegisterMybatisDao;



	public EasyuiDatagrid<DocQcDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocQcDetailsQuery query) {
        EasyuiDatagrid<DocQcDetailsVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<DocQcDetails> docQcHeaderList = docQcDetailsDao.queryByPageList(mybatisCriteria);
        DocQcDetailsVO docQcHeaderVO = null;
        List<DocQcDetailsVO> docQcHeaderVOList = new ArrayList<>();
        for (DocQcDetails docPaDetails : docQcHeaderList) {
            docQcHeaderVO = new DocQcDetailsVO();
            BeanUtils.copyProperties(docPaDetails, docQcHeaderVO);
            docQcHeaderVOList.add(docQcHeaderVO);
        }
        datagrid.setTotal((long) docQcDetailsDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docQcHeaderVOList);
        return datagrid;
	}

	public Json addDocQcDetails(DocQcDetailsForm docQcDetailsForm) throws Exception {
		Json json = new Json();
		DocQcDetails docQcDetails = new DocQcDetails();
		BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
		docQcDetailsDao.add(docQcDetails);
		json.setSuccess(true);
		return json;
	}

	//TODO WARNING!! 此处不可用个，查询条件欠缺 no + lineno
	public Json editDocQcDetails(DocQcDetailsForm docQcDetailsForm) {
		Json json = new Json();
		DocQcDetails docQcDetails = docQcDetailsDao.queryById(docQcDetailsForm.getQcno());
		BeanUtils.copyProperties(docQcDetailsForm, docQcDetails);
		docQcDetailsDao.update(docQcDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocQcDetails(String id) {
		Json json = new Json();
		DocQcDetails docQcDetails = docQcDetailsDao.queryById(id);
		if(docQcDetails != null){
			docQcDetailsDao.delete(docQcDetails);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getDocQcDetailsCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<DocQcDetails> docQcDetailsList = docQcDetailsDao.findAll();
//		if(docQcDetailsList != null && docQcDetailsList.size() > 0){
//			for(DocQcDetails docQcDetails : docQcDetailsList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(docQcDetails.getId()));
//				combobox.setValue(docQcDetails.getDocQcDetailsName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

    /**
     * 获取验收详情，可能存在多条
     * @param query ~
     * @return ~
     */
    public List<PdaDocQcDetailVO> queryDocQcDetail(PdaDocQcDetailQuery query) {

        //获取BasSku
        PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();
        BeanUtils.copyProperties(query, basSkuQuery);
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);

        query.setSku(basSku.getSku());
        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryDocQcDetail(query);
        List<PdaDocQcDetailVO> pdaDocQcDetailVOList = new ArrayList<>();
        PdaDocQcDetailVO pdaDocQcDetailVO;

        for (DocQcDetails docQcDetails : docQcDetailsList) {

            pdaDocQcDetailVO = new PdaDocQcDetailVO();
            BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);

            //SKU - 型号/规格
            BasSku subBasSku = new BasSku();
            BeanUtils.copyProperties(basSku, subBasSku);//为了解决FastJson的循环引用问题
            pdaDocQcDetailVO.setBasSku(subBasSku);

            //批次
            InvLotAtt lotAtt = invLotAttMybatisDao.queryById(docQcDetails.getLotnum());
//            InvLotAttQuery lotAttQuery = new InvLotAttQuery();
//            BeanUtils.copyProperties(query, lotAttQuery);
//            InvLotAtt lotAtt = invLotAttMybatisDao.queryForScan(lotAttQuery);
            pdaDocQcDetailVO.setInvLotAtt(lotAtt);

            //历史注册证(+生产企业详情)
            List<PdaGspProductRegister> registerList = productRegisterMybatisDao.queryHistoryRegister(subBasSku.getSku(), subBasSku.getCustomerid());
            //TODO 为了解决FastJson的循环引用问题 肯定有别的方法，待优化
            String jsonStr1 = JSON.toJSONString(registerList, SerializerFeature.DisableCircularReferenceDetect);
            pdaDocQcDetailVO.setProductRegisterList(JSONObject.parseArray(jsonStr1, PdaGspProductRegister.class));

            //当前批次-产品注册证对应的 生产厂家
            PdaGspProductRegister productRegister = productRegisterMybatisDao.queryByNo(lotAtt.getLotatt06());
            //为了解决FastJson的循环引用问题
            String jsonStr2 = JSON.toJSONString(productRegister.getEnterpriseInfo(), SerializerFeature.DisableCircularReferenceDetect);
            pdaDocQcDetailVO.setEnterpriseInfo(JSONObject.parseObject(jsonStr2, GspEnterpriseInfo.class));

            pdaDocQcDetailVOList.add(pdaDocQcDetailVO);
        }


	    return pdaDocQcDetailVOList;
    }

    /**
     * 查询任务明细列表
     * @param qcno ~
     * @return ~
     */
    public List<PdaDocQcDetailVO> queryDocQcList(String qcno, int pageNum) {

        List<PdaDocQcDetailVO> docQcDetailVOList = new ArrayList<>();
        PdaDocQcDetailVO pdaDocQcDetailVO;

        List<DocQcDetails> docQcDetailsList = docQcDetailsDao.queryDocQcList(qcno, (pageNum - 1) * Constant.pageSize, Constant.pageSize);
        for (DocQcDetails docQcDetails : docQcDetailsList) {

            pdaDocQcDetailVO = new PdaDocQcDetailVO();
            BeanUtils.copyProperties(docQcDetails, pdaDocQcDetailVO);
            docQcDetailVOList.add(pdaDocQcDetailVO);
        }

        return docQcDetailVOList;
    }
}