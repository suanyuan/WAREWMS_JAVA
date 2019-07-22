package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocAsnDetail;
import com.wms.entity.InvLotAtt;
import com.wms.mybatis.dao.InvLotAttMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.IdSequence;
import com.wms.query.InvLotAttQuery;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.vo.InvLotAttVO;
import com.wms.vo.Json;
import com.wms.vo.form.InvLotAttForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("invLotAttService")
public class InvLotAttService extends BaseService {

	@Autowired
	private InvLotAttMybatisDao invLotAttDao;

	public EasyuiDatagrid<InvLotAttVO> getPagedDatagrid(EasyuiDatagridPager pager, InvLotAttQuery query) {
        EasyuiDatagrid<InvLotAttVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria criteria = new MybatisCriteria();
        criteria.setCurrentPage(pager.getPage());
        criteria.setPageSize(pager.getRows());
        criteria.setCondition(query);
        InvLotAttVO invLotAttVO = null;
        List<InvLotAttVO> invLotAttVOList = new ArrayList<>();
        List<InvLotAtt> invLotAttList = invLotAttDao.queryByList(criteria);
        for (InvLotAtt invLotAtt : invLotAttList) {
            invLotAttVO = new InvLotAttVO();
            BeanUtils.copyProperties(invLotAtt, invLotAttVO);
            invLotAttVOList.add(invLotAttVO);
        }
        int total = invLotAttDao.queryByCount(criteria);
        datagrid.setTotal(Long.parseLong(total+""));
        datagrid.setRows(invLotAttVOList);
        return datagrid;
	}

	public Json addInvLotAtt(InvLotAttForm invLotAttForm) throws Exception {
		Json json = new Json();
		InvLotAtt invLotAtt = new InvLotAtt();
		BeanUtils.copyProperties(invLotAttForm, invLotAtt);
		invLotAttDao.add(invLotAtt);
		json.setSuccess(true);
		return json;
	}

	public Json editInvLotAtt(InvLotAttForm invLotAttForm) {
		Json json = new Json();
		InvLotAtt invLotAtt = invLotAttDao.queryById(invLotAttForm.getLotnum());
		BeanUtils.copyProperties(invLotAttForm, invLotAtt);
		invLotAttDao.update(invLotAtt);
		json.setSuccess(true);
		return json;
	}

	public Json deleteInvLotAtt(String id) {
		Json json = new Json();
		InvLotAtt invLotAtt = invLotAttDao.queryById(id);
		if(invLotAtt != null){
			invLotAttDao.delete(invLotAtt);
		}
		json.setSuccess(true);
		return json;
	}

    /**
     * 导入预入库通知单的时候，判断下是否有这个批次记录,如果有就返回，没有就插入新的
     * @param docAsnDetail customerid, sku, lotatt01~18
     * @return ~
     */
	public InvLotAtt queryInsertLotatts(DocAsnDetail docAsnDetail) {

        InvLotAttQuery query = new InvLotAttQuery();
        query.setCustomerid(docAsnDetail.getCustomerid());
        query.setSku(docAsnDetail.getSku());
        query.setLotatt01(docAsnDetail.getLotatt01());
        query.setLotatt02(docAsnDetail.getLotatt02());
        query.setLotatt03(docAsnDetail.getLotatt03());
        query.setLotatt04(docAsnDetail.getLotatt04());
        query.setLotatt05(docAsnDetail.getLotatt05());
        query.setLotatt06(docAsnDetail.getLotatt06());
        query.setLotatt07(docAsnDetail.getLotatt07());
        query.setLotatt08(docAsnDetail.getLotatt08());
        query.setLotatt09(docAsnDetail.getLotatt09());
        query.setLotatt10(docAsnDetail.getLotatt10());
        query.setLotatt11(docAsnDetail.getLotatt11());
        query.setLotatt12(docAsnDetail.getLotatt12());
        query.setLotatt13(docAsnDetail.getLotatt13());
        query.setLotatt14(docAsnDetail.getLotatt14());
        query.setLotatt15(docAsnDetail.getLotatt15());
        query.setLotatt16(docAsnDetail.getLotatt16());
        query.setLotatt17(docAsnDetail.getLotatt17());
        query.setLotatt18(docAsnDetail.getLotatt18());

        InvLotAtt invLotAtt = invLotAttDao.queryByLotatts(query);
        if (invLotAtt == null || invLotAtt.getLotnum() == null) {

            invLotAtt = new InvLotAtt();
            BeanUtils.copyProperties(query, invLotAtt);
            IdSequence idSequence = new IdSequence(SfcUserLoginUtil.getLoginUser().getWarehouse().getId(), "CN", IdSequence.SEQUENCE_TYPE_LOT_NUM);
            invLotAttDao.getIdSequence(idSequence);
            invLotAtt.setLotnum(idSequence.getResultNo());
            invLotAtt.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
            invLotAtt.setAddtime(new java.sql.Date((new Date()).getTime()));
            invLotAtt.setReceivingtime(new java.sql.Date((new Date()).getTime()));
            invLotAttDao.add(invLotAtt);
        }
        return invLotAtt;
    }

//	public List<EasyuiCombobox> getInvLotAttCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<InvLotAtt> invLotAttList = invLotAttDao.queryByAll();
//		if(invLotAttList != null && invLotAttList.size() > 0){
//			for(InvLotAtt invLotAtt : invLotAttList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(invLotAtt.getLotnum()));
//				combobox.setValue(invLotAtt.getInvLotAttName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

}