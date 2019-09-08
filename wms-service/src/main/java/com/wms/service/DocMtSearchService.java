package com.wms.service;

import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.DocMtDetails;
import com.wms.mybatis.dao.DocMtDetailsMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.DocMtDetailsQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.vo.DocMtDetailsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("docMtSearchService")
public class DocMtSearchService extends BaseService {

	@Autowired
	private DocMtDetailsMybatisDao docMtDetailsMybatisDao;

    /**
     * 养护作业界面分页显示 根据mtno
     * @param pager
     * @param query
     * @return
     */
	public EasyuiDatagrid<DocMtDetailsVO> getPagedDatagrid(EasyuiDatagridPager pager, DocMtDetailsQuery query) {
		EasyuiDatagrid<DocMtDetailsVO> datagrid = new EasyuiDatagrid<DocMtDetailsVO>();
		List<DocMtDetailsVO> docMtDetailsVOList = new ArrayList<DocMtDetailsVO>();
		MybatisCriteria mybatisCriteria = new MybatisCriteria();
		mybatisCriteria.setCurrentPage(pager.getPage());
		mybatisCriteria.setPageSize(pager.getRows());
		mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocMtDetails> docMtDetailsList = docMtDetailsMybatisDao.queryByListSearch(mybatisCriteria);
		DocMtDetailsVO docMtDetailsVO = null;
		for (DocMtDetails docMtDetails : docMtDetailsList) {
			docMtDetailsVO = new DocMtDetailsVO();
			docMtDetails.setMtqtyExpected(docMtDetails.getMtqtyExpected()-docMtDetails.getMtqtyCompleted());
			docMtDetails.setMtqtyEachExpected(docMtDetails.getMtqtyEachExpected()-docMtDetails.getMtqtyEachCompleted());
			BeanUtils.copyProperties(docMtDetails, docMtDetailsVO);
			docMtDetailsVOList.add(docMtDetailsVO);
		}
		datagrid.setTotal((long)docMtDetailsMybatisDao.queryByCountLotatt(mybatisCriteria));
		datagrid.setRows(docMtDetailsVOList);
		return datagrid;
	}


}