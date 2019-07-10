package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasSkuHistory;
import com.wms.mybatis.dao.BasSkuHistoryMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasSkuHistoryQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.BasSkuHistoryVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasSkuHistoryForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("basSkuHistoryService")
public class BasSkuHistoryService extends BaseService {

	@Autowired
	private BasSkuHistoryMybatisDao basSkuHistoryDao;

	public EasyuiDatagrid<BasSkuHistoryVO> getPagedDatagrid(EasyuiDatagridPager pager, BasSkuHistoryQuery query) {
        EasyuiDatagrid<BasSkuHistoryVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<BasSkuHistory> docQcHeaderList = basSkuHistoryDao.queryByPageList(mybatisCriteria);
        BasSkuHistoryVO docQcHeaderVO = null;
        List<BasSkuHistoryVO> docQcHeaderVOList = new ArrayList<>();
        for (BasSkuHistory docPaDetails : docQcHeaderList) {
            docQcHeaderVO = new BasSkuHistoryVO();
            BeanUtils.copyProperties(docPaDetails, docQcHeaderVO);
            docQcHeaderVOList.add(docQcHeaderVO);
        }
        datagrid.setTotal((long) basSkuHistoryDao.queryByCount(mybatisCriteria));
        datagrid.setRows(docQcHeaderVOList);
        return datagrid;
	}

	public Json addBasSkuHistory(BasSkuHistoryForm basSkuHistoryForm) throws Exception {
		Json json = new Json();
		BasSkuHistory basSkuHistory = new BasSkuHistory();
		BeanUtils.copyProperties(basSkuHistoryForm, basSkuHistory);
		basSkuHistoryDao.add(basSkuHistory);
		json.setSuccess(true);
		return json;
	}

	public Json editBasSkuHistory(BasSkuHistoryForm basSkuHistoryForm) {
		Json json = new Json();
		BasSkuHistory basSkuHistory = basSkuHistoryDao.queryById(basSkuHistoryForm.getSku());
		BeanUtils.copyProperties(basSkuHistoryForm, basSkuHistory);
		basSkuHistoryDao.update(basSkuHistory);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBasSkuHistory(String id) {
		Json json = new Json();
		BasSkuHistory basSkuHistory = basSkuHistoryDao.queryById(id);
		if(basSkuHistory != null){
			basSkuHistoryDao.delete(basSkuHistory);
		}
		json.setSuccess(true);
		return json;
	}

//	public List<EasyuiCombobox> getBasSkuHistoryCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<BasSkuHistory> basSkuHistoryList = basSkuHistoryDao.queryByAll();
//		if(basSkuHistoryList != null && basSkuHistoryList.size() > 0){
//			for(BasSkuHistory basSkuHistory : basSkuHistoryList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(basSkuHistory.getId()));
//				combobox.setValue(basSkuHistory.getBasSkuHistoryName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}

}