package com.wms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.*;
import com.wms.mybatis.dao.*;
import com.wms.query.BasCustomerQuery;
import com.wms.query.DocMtDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocMtDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.form.DocMtDetailsForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docMtDetailsService")
public class DocMtDetailsService extends BaseService {

	@Autowired
	private DocMtDetailsMybatisDao docMtDetailsMybatisDao;

	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;

	@Autowired
	private BasCustomerMybatisDao basCustomerMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	@Autowired
	private BasPackageMybatisDao basPackageMybatisDao;

	public EasyuiDatagrid<DocMtDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocMtDetailsQuery query) {
		EasyuiDatagrid<DocMtDetailsVO> datagrid = new EasyuiDatagrid<DocMtDetailsVO>();
		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByList(new MybatisCriteria());
		DocMtDetailsVO docMtDetailsVO = null;
		List<DocMtDetailsVO> docMtDetailsVOList = new ArrayList<DocMtDetailsVO>();
		for (DocMtDetails docMtDetails : docMtDetailsList) {
			docMtDetailsVO = new DocMtDetailsVO();
			BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);
			docMtDetailsVOList.add(docMtDetailsVO);
		}
		datagrid.setTotal((long)docMtDetailsMybatisDao.queryByCount(new MybatisCriteria()));
		datagrid.setRows(docMtDetailsVOList);
		return datagrid;
	}

	public Json addDocMtDetails(DocMtDetailsForm docMtDetailsForm) throws Exception {
		Json json = new Json();
		DocMtDetails docMtDetails = new DocMtDetails();
		BeanUtils.copyProperties(docMtDetailsForm, docMtDetails);
		docMtDetailsMybatisDao.add(docMtDetails);
		json.setSuccess(true);
		return json;
	}

	public Json editDocMtDetails(DocMtDetailsForm docMtDetailsForm) {
		Json json = new Json();
		DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryById(docMtDetailsForm.getMtno());
		BeanUtils.copyProperties(docMtDetailsForm, docMtDetails);
		docMtDetailsMybatisDao.update(docMtDetails);
		json.setSuccess(true);
		return json;
	}

	public Json deleteDocMtDetails(String id) {
		Json json = new Json();
		DocMtDetails docMtDetails = docMtDetailsMybatisDao.queryById(id);
		if(docMtDetails != null){
			docMtDetailsMybatisDao.delete(docMtDetails);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getDocMtDetailsCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByAll();
		if(docMtDetailsList != null && docMtDetailsList.size() > 0){
			for(DocMtDetails docMtDetails : docMtDetailsList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(docMtDetails.getMtno()));
				combobox.setValue(docMtDetails.getLotnum());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

    /**
     * 根据扫码结果查询养护明细
     * 可能存在不同货主的相同产品
     * @param query ~
     * @return ~
     */
	public List<DocMtDetailsVO> queryMtDetail(DocMtDetailsQuery query) {

	    MybatisCriteria mybatisCriteria = new MybatisCriteria();
	    mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	    List<DocMtDetails> docMtDetailsList= docMtDetailsMybatisDao.queryByList(mybatisCriteria);
	    List<DocMtDetailsVO> docMtDetailsVOList = new ArrayList<>();
	    DocMtDetailsVO docMtDetailsVO;
        for (DocMtDetails docMtDetails : docMtDetailsList) {

            docMtDetailsVO = new DocMtDetailsVO();
            BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);

            //客户档案
            BasCustomer basCustomer = basCustomerMybatisDao.queryByCustomerId(docMtDetails);
            if (basCustomer == null) continue;
            String jsonStr = JSON.toJSONString(basCustomer, SerializerFeature.DisableCircularReferenceDetect);
            docMtDetailsVO.setBasCustomer(JSONObject.parseObject(jsonStr, BasCustomer.class));

            //批次属性
            InvLotAtt invLotAtt = invLotAttMybatisDao.queryById(docMtDetails);
            if (invLotAtt == null) continue;
            docMtDetailsVO.setInvLotAtt(invLotAtt);

            //产品档案
            BasSku basSku = basSkuMybatisDao.queryById(docMtDetails);
            if (basSku == null) continue;
            docMtDetailsVO.setBasSku(basSku);

            //包装规格
            BasPackage basPackage = basPackageMybatisDao.queryById(basSku.getPackid());
            if (basPackage == null) continue;
            docMtDetailsVO.setBasPackage(basPackage);

            docMtDetailsVOList.add(docMtDetailsVO);
        }
 	    return docMtDetailsVOList;
    }
}