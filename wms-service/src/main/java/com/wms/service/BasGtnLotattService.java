package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.BasGtnLotatt;
import com.wms.mybatis.dao.BasGtnLotattMybatisDao;
import com.wms.mybatis.dao.InvLotAttMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.entity.IdSequence;
import com.wms.query.BasGtnLotattQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.StringUtil;
import com.wms.vo.BasGtnLotattVO;
import com.wms.vo.Json;
import com.wms.vo.form.BasGtnLotattForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("basGtnLotattService")
public class BasGtnLotattService extends BaseService {

	@Autowired
	private BasGtnLotattMybatisDao basGtnLotattMybatisDao;

	@Autowired
	private InvLotAttMybatisDao invLotAttMybatisDao;

	public EasyuiDatagrid<BasGtnLotattVO> getPagedDatagrid(EasyuiDatagridPager pager, BasGtnLotattQuery query) {
        EasyuiDatagrid<BasGtnLotattVO> datagrid = new EasyuiDatagrid<>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
        List<BasGtnLotatt> basGtnLotattList = basGtnLotattMybatisDao.queryByPageList(mybatisCriteria);
        BasGtnLotattVO basGtnLotattVO = null;
        List<BasGtnLotattVO> basGtnLotattVOList = new ArrayList<>();
        for (BasGtnLotatt basGtnLotatt : basGtnLotattList) {
            basGtnLotattVO = new BasGtnLotattVO();
            BeanUtils.copyProperties(basGtnLotatt, basGtnLotattVO);
            basGtnLotattVOList.add(basGtnLotattVO);
        }
        datagrid.setTotal((long) basGtnLotattMybatisDao.queryByCount(mybatisCriteria));
        datagrid.setRows(basGtnLotattVOList);
        return datagrid;
	}

	public Json addBasGtnLotatt(BasGtnLotattForm basGtnLotattForm) throws Exception {
		Json json = new Json();
		BasGtnLotatt basGtnLotatt = new BasGtnLotatt();
		BeanUtils.copyProperties(basGtnLotattForm, basGtnLotatt);
        basGtnLotattMybatisDao.add(basGtnLotatt);
		json.setSuccess(true);
		return json;
	}

    /**
     * 根据批次属性明细插入扫码对照表，先查再插(导入预入库通知单的时候)
     * @param query customerid, sku, lotatt02, lotatt04, lotatt05
     */
	public void queryInsertGtnLotatt(BasGtnLotattQuery query) {

	    if (StringUtil.isNotEmpty(query.getLotatt04()) || StringUtil.isNotEmpty(query.getLotatt05())) {

	        String asnno = query.getAddasnno();
	        query.setAddasnno(null);
            MybatisCriteria mybatisCriteria = new MybatisCriteria();
            mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
            List<BasGtnLotatt> basGtnLotattList = basGtnLotattMybatisDao.queryByList(mybatisCriteria);
            BasGtnLotatt basGtnLotatt = new BasGtnLotatt();
            if (basGtnLotattList == null || basGtnLotattList.size() == 0) {

                IdSequence idSequence = new IdSequence(SfcUserLoginUtil.getLoginUser().getWarehouse().getId(), "CN", IdSequence.SEQUENCE_TYPE_LOT_NUM);
                invLotAttMybatisDao.getIdSequence(idSequence);
                BeanUtils.copyProperties(query, basGtnLotatt);
                basGtnLotatt.setAddasnno(asnno);
                basGtnLotatt.setLotnum(idSequence.getResultNo());
                basGtnLotatt.setAddtime(new java.sql.Date((new Date()).getTime()));
                basGtnLotattMybatisDao.add(basGtnLotatt);
            }
        }
    }

//	public Json editBasGtnLotatt(BasGtnLotattForm basGtnLotattForm) {
//		Json json = new Json();
//		BasGtnLotatt basGtnLotatt = basGtnLotattMybatisDao.queryById(basGtnLotattForm.getLotnum());
//		BeanUtils.copyProperties(basGtnLotattForm, basGtnLotatt);
//		basGtnLotattDao.update(basGtnLotatt);
//		json.setSuccess(true);
//		return json;
//	}
//
//	public Json deleteBasGtnLotatt(String id) {
//		Json json = new Json();
//		BasGtnLotatt basGtnLotatt = basGtnLotattDao.findById(id);
//		if(basGtnLotatt != null){
//			basGtnLotattDao.delete(basGtnLotatt);
//		}
//		json.setSuccess(true);
//		return json;
//	}
//
//	public List<EasyuiCombobox> getBasGtnLotattCombobox() {
//		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
//		EasyuiCombobox combobox = null;
//		List<BasGtnLotatt> basGtnLotattList = basGtnLotattDao.findAll();
//		if(basGtnLotattList != null && basGtnLotattList.size() > 0){
//			for(BasGtnLotatt basGtnLotatt : basGtnLotattList){
//				combobox = new EasyuiCombobox();
//				combobox.setId(String.valueOf(basGtnLotatt.getId()));
//				combobox.setValue(basGtnLotatt.getBasGtnLotattName());
//				comboboxList.add(combobox);
//			}
//		}
//		return comboboxList;
//	}



}