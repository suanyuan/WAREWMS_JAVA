package com.wms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wms.entity.DocMtDetails;
import com.wms.entity.InvLotLocId;
import com.wms.mybatis.dao.DocMtDetailsMybatisDao;
import com.wms.mybatis.dao.DocMtHeaderMybatisDao;
import com.wms.mybatis.dao.InvLotLocIdMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.SfcUserLoginUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wms.entity.DocMtHeader;
import com.wms.vo.DocMtHeaderVO;
import com.wms.vo.Json;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.vo.form.DocMtHeaderForm;
import com.wms.query.DocMtHeaderQuery;

@Service("docMtHeaderService")
public class DocMtHeaderService extends BaseService {

	@Autowired
	private DocMtHeaderMybatisDao docMtHeaderMybatisDao;
    @Autowired
    private DocMtDetailsMybatisDao docMtDetailsMybatisDao;
    @Autowired
	private InvLotLocIdMybatisDao invLotLocIdMybatisDao;

//分页查询
	public EasyuiDatagrid<DocMtHeaderVO> getPagedDatagrid(EasyuiDatagridPager pager, DocMtHeaderQuery query) {
		EasyuiDatagrid<DocMtHeaderVO> datagrid = new EasyuiDatagrid<DocMtHeaderVO>();
        MybatisCriteria mybatisCriteria = new MybatisCriteria();
        mybatisCriteria.setCurrentPage(pager.getPage());
        mybatisCriteria.setPageSize(pager.getRows());
        mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
		List<DocMtHeader> docMtHeaderList = docMtHeaderMybatisDao.queryByList(mybatisCriteria);
		DocMtHeaderVO docMtHeaderVO = null;
		List<DocMtHeaderVO> docMtHeaderVOList = new ArrayList<DocMtHeaderVO>();
		for (DocMtHeader docMtHeader : docMtHeaderList) {
			docMtHeaderVO = new DocMtHeaderVO();
			BeanUtils.copyProperties(docMtHeader, docMtHeaderVO);
			docMtHeaderVOList.add(docMtHeaderVO);
		}
		datagrid.setTotal((long)docMtHeaderMybatisDao.queryByCount(mybatisCriteria));
		datagrid.setRows(docMtHeaderVOList);
		return datagrid;
	}
//获得养护计划
	public List<InvLotLocId> getGenerationInfo(DocMtHeaderQuery query) {
//	   查询时间为空返回空数据
	    if(query.getFromdate()==null||query.getTodate()==null||query.getFromdate().equals("")||query.getTodate().equals("")){
	        return  new ArrayList<InvLotLocId>();
        }
        long fromdate=StringtoDate(query.getFromdate()).getTime(); //获得开始时间
        long todate=StringtoDate(query.getTodate()).getTime();     //获得结束时间
        List<InvLotLocId> lotLocIdListA=new ArrayList<InvLotLocId>();//用于筛选的养护计划
        List<InvLotLocId> lotLocIdListFA=new ArrayList<InvLotLocId>();//返回前端的养护计划
//1.查出in_lot_att_id 可以生成养护计划的所有sku
         List<InvLotLocId> lotLocIdList=invLotLocIdMybatisDao.getLotLocIdistListByMaintenanceTime();
//2.根据时间段查出_doc_mt_deatil 主单状态99 40  生成养护计划的sku
        List<DocMtDetails> docMtDetailsListA=docMtDetailsMybatisDao.getDocMtDetailsList();
 //3.查出_doc_mt_deatil 主单状态00 30 不可生成养护计划的sku
        List<DocMtDetails> docMtDetailsListD=docMtDetailsMybatisDao.getDocMtDetailsListByStatus();
 //遍历1 如果2中1.sku==2.sku 那么1中的养护日期将改为2的
        Boolean con;
        for (InvLotLocId invLotLocId : lotLocIdList) {
             con=true;
             //判断可养护库存件数是否为0 为0跳出本次循环
            invLotLocId.setQty(invLotLocId.getQty()-invLotLocId.getQtyallocated()-invLotLocId.getQtyonhold());//库存件数-分配件数-冻结件数
            if(invLotLocId.getQty()<=0){
                continue;
            }
//             先从2中获得养护计划
            for (DocMtDetails a : docMtDetailsListA) {
                if(invLotLocId.getLotnum().equals(a.getLotnum())&&invLotLocId.getSku().equals(a.getSku())&&invLotLocId.getCustomerid().equals(a.getCustomerid())){
                    long date=a.getConversedatetest().getTime();
                    if(fromdate<=date&&todate>=date) {
                        invLotLocId.setLotatt03test(a.getConversedatetest());//变更养护时间
                        invLotLocId.setFromDate(StringtoDate(query.getFromdate()));
                        invLotLocId.setToDate(StringtoDate(query.getTodate()));
                        lotLocIdListA.add(invLotLocId);
                    }
                    con=false;
                    break;
                }
            }
//            如果2中没有 则判断1中时间段是否符合  如果符合 加入养护计划
           if(con){
               long date=invLotLocId.getLotatt03test().getTime();
               if(fromdate<=date&&todate>=date){
                   invLotLocId.setFromDate(StringtoDate(query.getFromdate()));
                   invLotLocId.setToDate(StringtoDate(query.getTodate()));
                   lotLocIdListA.add(invLotLocId);
               }
           }
        }
 //遍历3 3中有的1中将去除
        for (InvLotLocId  invLotLocId: lotLocIdListA) {
            con=true;
            for (DocMtDetails d : docMtDetailsListD) {
                if(invLotLocId.getLotnum().equals(d.getLotnum())&&invLotLocId.getSku().equals(d.getSku())&&invLotLocId.getCustomerid().equals(d.getCustomerid())){
                  con=false;
                   break;
                }
            }
            if(con){
                lotLocIdListFA.add(invLotLocId);
            }
        }
		return lotLocIdListFA;
	}
//获得养护计划
	public Json ToGenerationInfo(DocMtHeaderQuery query) {
          Json json=new Json();
        /*获取新的号*/
        Map<String ,Object> map=new HashMap<String, Object>();
        map.put("warehouseid", SfcUserLoginUtil.getLoginUser().getWarehouse().getId());
        docMtHeaderMybatisDao.getIdSequence(map);
        String resultCode = map.get("resultCode").toString();
        String resultNo = map.get("resultNo").toString();
        if (resultCode.substring(0,3).equals("000")) {
            DocMtHeader docMtHeader=new DocMtHeader();
            docMtHeader.setMtno(resultNo);
            docMtHeader.setMtstatus("00");
            docMtHeader.setMttype("*");
            docMtHeader.setFromdate(StringtoDate(query.getFromdate()));
            docMtHeader.setTodate(StringtoDate(query.getTodate()));
            docMtHeader.setAddwho(SfcUserLoginUtil.getLoginUser().getId());
            docMtHeader.setWarehouseid(SfcUserLoginUtil.getLoginUser().getId());
//            docMtHeaderMybatisDao.add(docMtHeader);
//            if(docMtHeaderMybatisDao.queryById(docMtHeader)==null){
//                return null;
//            }

//循环加入细单
            List<InvLotLocId> invLotLocIdList = getGenerationInfo(query);
            for (InvLotLocId invLotLocId : invLotLocIdList) {


            }
            json.setSuccess(true);
            json.setMsg("生成计划成功!");
        }else{
            json.setSuccess(true);
            json.setMsg("生成计划失败!");
        }
        return  json;
	}
//增加
	public Json addDocMtHeader(DocMtHeaderForm docMtHeaderForm) throws Exception {
		Json json = new Json();
		DocMtHeader docMtHeader = new DocMtHeader();
		BeanUtils.copyProperties(docMtHeaderForm, docMtHeader);
		docMtHeaderMybatisDao.add(docMtHeader);
		json.setSuccess(true);
		return json;
	}
//编辑
	public Json editDocMtHeader(DocMtHeaderForm docMtHeaderForm) {
		Json json = new Json();
		DocMtHeader docMtHeader = docMtHeaderMybatisDao.queryById(docMtHeaderForm.getMtno());
		BeanUtils.copyProperties(docMtHeaderForm, docMtHeader);
		docMtHeaderMybatisDao.updateBySelective(docMtHeader);
		json.setSuccess(true);
		return json;
	}
//删除
	public Json deleteDocMtHeader(String id) {
		Json json = new Json();
		DocMtHeader docMtHeader = docMtHeaderMybatisDao.queryById(id);
		if(docMtHeader != null){
			docMtHeaderMybatisDao.delete(docMtHeader);
		}
		json.setSuccess(true);
		return json;
	}

	public List<EasyuiCombobox> getDocMtHeaderCombobox() {
		List<EasyuiCombobox> comboboxList = new ArrayList<EasyuiCombobox>();
		EasyuiCombobox combobox = null;
		List<DocMtHeader> docMtHeaderList = docMtHeaderMybatisDao.queryListByAll();
		if(docMtHeaderList != null && docMtHeaderList.size() > 0){
			for(DocMtHeader docMtHeader : docMtHeaderList){
				combobox = new EasyuiCombobox();
				combobox.setId(docMtHeader.getMtno());
				combobox.setValue(docMtHeader.getUserdefine1());
				comboboxList.add(combobox);
			}
		}
		return comboboxList;
	}

//string转date
	public Date StringtoDate(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
             date=format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}