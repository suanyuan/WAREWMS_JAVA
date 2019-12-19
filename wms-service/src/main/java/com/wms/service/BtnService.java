package com.wms.service;

import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.mybatis.dao.SfcBtnMybatisDao;
import com.wms.mybatis.entity.SfcBtn;
import com.wms.query.SfcBtnQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.Json;
import com.wms.vo.SfcBtnVO;
import com.wms.vo.form.BtnForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("BtnService")
public class BtnService extends BaseService {

	@Autowired
	private SfcBtnMybatisDao sfcBtnMybatisDao;

	public EasyuiDatagrid<SfcBtnVO> getPagedDatagrid(EasyuiDatagridPager pager, SfcBtnQuery query) {
		EasyuiDatagrid<SfcBtnVO> datagrid = new EasyuiDatagrid<SfcBtnVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<SfcBtn> sfcBtnList = sfcBtnMybatisDao.queryByPageList(mybatisCriteria);
		SfcBtnVO sfcBtnVO = null;
		List<SfcBtnVO> sfcBtnVOList = new ArrayList<SfcBtnVO>();
		for (SfcBtn sfcBtn : sfcBtnList) {
			sfcBtnVO = new SfcBtnVO();
			BeanUtils.copyProperties(sfcBtn, sfcBtnVO);
			sfcBtnVOList.add(sfcBtnVO);
		}
		datagrid.setTotal((long) sfcBtnMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(sfcBtnVOList);
		return datagrid;
	}

	public Json addBtn(BtnForm btnForm) throws Exception {
		Json json = new Json();
		SfcBtn sfcBtn = new SfcBtn();
		BeanUtils.copyProperties(btnForm, sfcBtn);
		sfcBtnMybatisDao.add(sfcBtn);
		json.setSuccess(true);
		return json;
	}

	public Json editBtn(BtnForm btnForm) {
		Json json = new Json();
		SfcBtnQuery sfcBtnQuery = new SfcBtnQuery();
		sfcBtnQuery.setId(btnForm.getBtnId());
		SfcBtn sfcBtn = sfcBtnMybatisDao.queryById(sfcBtnQuery);
		BeanUtils.copyProperties(btnForm, sfcBtn);
		sfcBtnMybatisDao.updateBySelective(sfcBtn);
		json.setSuccess(true);
		return json;
	}

	public Json deleteBtn(String id) {
		Json json = new Json();
		SfcBtnQuery sfcBtnQuery = new SfcBtnQuery();
		sfcBtnQuery.setId(id);
		SfcBtn sfcBtn = sfcBtnMybatisDao.queryById(sfcBtnQuery);
		if(sfcBtn != null){
			sfcBtnMybatisDao.delete(sfcBtn);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getBtnCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<SfcBtn> sfcBtnList = sfcBtnMybatisDao.queryListByAll();
		if(sfcBtnList != null && sfcBtnList.size() > 0){
			for(SfcBtn sfcBtn : sfcBtnList){
				combobox = new EasyuiCombobox();
				combobox.setId(String.valueOf(sfcBtn.getId()));
				combobox.setValue(sfcBtn.getBtnChsName());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

	public Json findAll() {
		Json json = new Json();
		List<SfcBtn> btlList = sfcBtnMybatisDao.queryListByAll();
		StringBuilder sb = new StringBuilder();
		for(SfcBtn sfcBtn : btlList){
			sb.append(sfcBtn.getBtnName()).append(",");
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.lastIndexOf(","));
			json.setSuccess(true);
			json.setObj(sb.toString());sb.setLength(0);
		}
		return json;
	}
    public Json find(String btnArray,String roleId,String menuId) {
        Json json = new Json();
        StringBuilder sb = new StringBuilder();

        String[] btlAll = btnArray.split(",");//全部按钮
        List<String> btnListAll = Arrays.asList(btlAll);
        List<SfcBtn> btlList = sfcBtnMybatisDao.queryListRole(menuId,roleId);
        List<String> btnLists = new ArrayList<>();//用户在该表单可用按钮
        for (SfcBtn btnlist:btlList){
            btnLists.add(btnlist.getBtnName());
        }
        for(int i=0;i<btnListAll.size();i++){
            if(btnLists.contains(btnListAll.get(i))){
                continue;
            }else {
                sb.append(btnListAll.get(i)).append(",");
            }
        }
        if(sb.length() > 0 &&  !roleId.equals("1")){
            sb.deleteCharAt(sb.lastIndexOf(","));
            json.setSuccess(true);
            json.setObj(sb.toString());
        }
        return json;
    }
}