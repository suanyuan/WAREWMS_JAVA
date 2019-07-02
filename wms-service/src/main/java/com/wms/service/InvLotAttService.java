package com.wms.service;

import java.util.ArrayList;
import java.util.List;

import com.wms.mybatis.dao.InvLotAttMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.InvLotAtt;
import com.wms.vo.InvLotAttVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.InvLotAttForm;
import com.wms.query.InvLotAttQuery;

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