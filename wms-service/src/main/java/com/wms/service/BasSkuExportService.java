package com.wms.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.wms.constant.Constant;
import com.wms.entity.BasSkuHistory;
import com.wms.mybatis.dao.BasSkuHistoryMybatisDao;
import com.wms.mybatis.dao.FirstBusinessApplyMybatisDao;
import com.wms.vo.BasSkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.BasSku;
import com.wms.entity.enumerator.ContentTypeEnum;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.MybatisCriteria;
import com.wms.query.BasSkuQuery;
import com.wms.utils.BeanConvertUtil;
import com.wms.utils.BeanUtils;
import com.wms.utils.DateUtil;
import com.wms.utils.SfcUserLoginUtil;
import com.wms.utils.exception.ExcelException;
import com.wms.utils.ExcelUtil;
import com.wms.vo.form.BasSkuExportForm;
import com.wms.vo.form.BasSkuForm;

/**
 * 汇出资料用service
 * Word Excel Txt Cvs
 * @author 
 * @Date 
 */
@Service("basSkuExportService")
public class BasSkuExportService {
	
	@Autowired
	private BasSkuMybatisDao basSkuMybatisDao;
	@Autowired
	private FirstBusinessApplyMybatisDao firstBusinessApplyMybatisDao;

	@Autowired
	private BasSkuHistoryMybatisDao basSkuHistoryMybatisDao;
	
	private static final String ORDER_HEAD = "客户代码,产品,是否激活,中文名称,英文名称,条形码,包装代码,单位,首次入库,重量,体积,单价";
	public void exportSkuData(HttpServletResponse response, BasSkuExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		BasSkuForm basSkuForm = new BasSkuForm();
		
		basSkuForm.setCustomerid(form.getCustomerid());
		basSkuForm.setSku(form.getSku());
		basSkuForm.setActiveFlag(form.getActiveFlag());
		
		StringBuilder filename = new StringBuilder();
		filename.append("sku_report_").append(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS")).append(".csv");
		StringBuilder sb = new StringBuilder();
		sb.append("attachment; filename=")
		  .append(URLEncoder.encode(filename.toString(),"UTF-8"));filename.setLength(0);
		response.setHeader("Content-disposition", sb.toString());sb.setLength(0);
		
		try(BufferedWriter csvWtriter = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), 4096)){
			List<String> dataList = new ArrayList<String>();
			BasSkuQuery query = new BasSkuQuery();
			BeanUtils.copyProperties(basSkuForm, query);
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			List<BasSku> skuList = basSkuMybatisDao.queryByList(mybatisCriteria);
			
			for(BasSku sku : skuList){
				sb.append(sku.getCustomerid()).append(",")
				  .append(sku.getSku()).append(",")
				  .append(sku.getActiveFlag()).append(",")
				  .append(sku.getDescrC()).append(",")
				  .append(sku.getDescrE()==null?"":sku.getDescrE()).append(",")
				  .append(sku.getAlternateSku1()==null?"":sku.getAlternateSku1()).append(",")
				  .append(sku.getPackid()).append(",")
				  .append(sku.getReservedfield01()==null?"":sku.getReservedfield01()).append(",")
				  .append(sku.getFirstinbounddate()==null?"":sku.getFirstinbounddate()).append(",")
				  .append(sku.getGrossweight()==null?0:sku.getGrossweight()).append(",")
				  .append(sku.getCube()==null?0:sku.getCube()).append(",")
				  .append(sku.getPrice()==null?0:sku.getPrice()).append(",");
				dataList.add(sb.toString());
				sb.setLength(0);
			}
			csvWtriter.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));  
            writeRow(ORDER_HEAD, csvWtriter);
            for (String row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
		}
	}
	
    private void writeRow(String row, BufferedWriter csvWriter) throws IOException {
        csvWriter.write(row);
        csvWriter.newLine();
    }
	

	public void exportSkuDataToExcel(HttpServletResponse response, BasSkuExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);	
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());
		
		BasSkuForm basSkuForm = new BasSkuForm();
		
		basSkuForm.setCustomerid(form.getCustomerid());
		basSkuForm.setSku(form.getSku());
		basSkuForm.setActiveFlag(form.getActiveFlag());
		
		try {  
	        // 获取前台传来的数据
	        //String cutomerid = form.getCustomerid();  
	        //String sku = form.getSku();  
	        //String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");  
	        //String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");  
	        BasSkuQuery query = new BasSkuQuery();
			BeanUtils.copyProperties(basSkuForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
	        // excel表格的表头，map  
	        LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();  
	        // excel的sheetName  
	        String sheetName = "产品档案";  
	        // excel要导出的数据  
	        List<BasSku> basSkuList = basSkuMybatisDao.queryByPageList(mybatisCriteria);
	        // 导出  
	        if (basSkuList == null || basSkuList.size() == 0) {
	        	//System.out.println("题库为空");  
	        }else {

				for (BasSku basSku : basSkuList) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=null;

					if("1".equals(basSku.getActiveFlag())){
						basSku.setActiveFlag("是");
					}else if("0".equals(basSku.getActiveFlag())){
						basSku.setActiveFlag("否");
					}

					if(Constant.CODE_CATALOG_FIRSTSTATE_PASS.equals(basSku.getFirstop())){
						basSku.setFirstop("审核通过");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_USELESS.equals(basSku.getFirstop())){
						basSku.setFirstop("已报废");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_NEW.equals(basSku.getFirstop())){
						basSku.setFirstop("新建");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_STOP.equals(basSku.getFirstop())){
						basSku.setFirstop("已停止");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING.equals(basSku.getFirstop())){
						basSku.setFirstop("审核中");
					}

					if("1".equals(basSku.getReservedfield09())){
						basSku.setReservedfield09("是");
					}else if("0".equals(basSku.getReservedfield09())){
						basSku.setReservedfield09("否");
					}
					if("1".equals(basSku.getSkuGroup7())){
						basSku.setSkuGroup7("是");
					}else if("0".equals(basSku.getSkuGroup7())){
						basSku.setSkuGroup7("否");
					}
					if("1".equals(basSku.getSkuGroup8())){
						basSku.setSkuGroup8("是");
					}else if("0".equals(basSku.getSkuGroup8())){
						basSku.setSkuGroup8("否");
					}
					if("FLL".equals(basSku.getReservedfield07())){
						basSku.setReservedfield07("非冷链");
					}else if("LC".equals(basSku.getReservedfield07())){
						basSku.setReservedfield07("冷藏");
					}else if("LD".equals(basSku.getReservedfield07())){
						basSku.setReservedfield07("冷冻");
					}
					if(basSku.getReservedfield10()!=null){
						basSku.setReservedfield10(basSku.getReservedfield10()+"天");
					}


					if(basSku.getAddtime()!=null) {
						basSku.setAddtimeDc(sdf.format(basSku.getAddtime()));
					}
					if(basSku.getEdittime()!=null) {
						basSku.setEdittimeDc(sdf.format(basSku.getEdittime()));
					}



					//所有供应商
//					String content = "";
//					BasSku bs = new BasSku();
//					bs.setSku(basSku.getSku());
//					bs.setCustomerid(basSku.getCustomerid());
//					List<String> sup = firstBusinessApplyMybatisDao.selectSupplierNamesByProductAndState(bs);
//					int a = 1;
//					for (String supNanme : sup) {
//						if (a == 1) {
//							content = supNanme;
//						}
//						if (supNanme != null && a != 1) {
//							System.out.println();
//							content = content + "," + supNanme;
//						}
//						a++;
//					}
//					basSku.setSupplierNames(content);
				}


	            ExcelUtil.listToExcel(basSkuList, fieldMap, sheetName, response);
	        	//System.out.println("导出成功~~~~");  
	        }  
	    } catch (ExcelException e) {  
	        e.printStackTrace();  
	    }  
	}

	/**
	 * 得到导出Excle时题型的英中文map
	 *
	 * @return 返回题型的属性map
	 */
	public LinkedHashMap<String, String> getLeadToFiledPublicQuestionBank() {
	
		LinkedHashMap<String, String> superClassMap = new LinkedHashMap<String, String>();
		superClassMap.put("firstop", "首营状态");
		superClassMap.put("activeFlag", "激活");
		superClassMap.put("customerid", "货主代码");
		superClassMap.put("clientName", "货主名称");
		superClassMap.put("skuGroup6Name", "默认供应商");
		superClassMap.put("productLineName", "产品线");
		superClassMap.put("reservedfield09", "医疗器械标志");
		superClassMap.put("reservedfield03", "注册证");
		superClassMap.put("sku", "产品代码");
		superClassMap.put("reservedfield01", "产品名称");
		superClassMap.put("reservedfield02", "产品描述");
		superClassMap.put("descrC", "规格");
		superClassMap.put("descrE", "型号");
		superClassMap.put("unit", "单位");
		superClassMap.put("descr", "包装规格");


		superClassMap.put("skuGroup5", "运输条件");//
		superClassMap.put("skuGroup4", "储存条件");
		superClassMap.put("reservedfield14", "生产企业");
		superClassMap.put("reservedfield06", "生产许可证号/备案号");//
		superClassMap.put("skuGroup9", "产地");//
		superClassMap.put("skuGroup7", "双证");
		superClassMap.put("skuGroup8", "产品合格证");//
		superClassMap.put("skuGroup2", "附卡类别");//
		superClassMap.put("reservedfield07", "冷链标志");//
		superClassMap.put("reservedfield08", "灭菌标志");//
		superClassMap.put("reservedfield10", "养护周期（天）");
		superClassMap.put("reservedfield13", "包装单位");//
		superClassMap.put("skuGroup3", "包装要求");//
		superClassMap.put("skuhigh", "长");//
		superClassMap.put("skulength", "宽");//
		superClassMap.put("skuwidth", "高");//
		superClassMap.put("reservedfield11", "重量");//
		superClassMap.put("reservedfield12", "商品条码");//
		superClassMap.put("alternateSku1", "自附码1");//
		superClassMap.put("alternateSku2", "自附码2");//
		superClassMap.put("alternateSku3", "自附码3");//
		superClassMap.put("alternateSku4", "自附码4");//
		superClassMap.put("alternateSku5", "自附码5");//


//		superClassMap.put("reservedfield04", "管理分类");
//		superClassMap.put("reservedfield05", "分类目录");


//		superClassMap.put("supplierNames", "所有供应商");


		superClassMap.put("addwho", "创建人");
		superClassMap.put("addtimeDc", "创建时间");
		superClassMap.put("editwho", "编辑人");
		superClassMap.put("edittimeDc", "编辑时间");

		return superClassMap;
	}


	/**************************************产品档案历史导出****************************************/

	public void exportSkuHistoryDataToExcel(HttpServletResponse response, BasSkuExportForm form) throws IOException {
		Cookie cookie = new Cookie("exportToken",form.getToken());
		cookie.setMaxAge(60);
		response.addCookie(cookie);
		response.setContentType(ContentTypeEnum.csv.getContentType());

		BasSkuForm basSkuForm = new BasSkuForm();

		basSkuForm.setCustomerid(form.getCustomerid());
		basSkuForm.setSku(form.getSku());
		basSkuForm.setActiveFlag(form.getActiveFlag());

		try {
			// 获取前台传来的数据
			//String cutomerid = form.getCustomerid();
			//String sku = form.getSku();
			//String cutomeridId = new String(cutomerid.getBytes("iso-8859-1"), "utf-8");
			//String skuId = new String(sku.getBytes("iso-8859-1"), "utf-8");
			BasSkuQuery query = new BasSkuQuery();
			BeanUtils.copyProperties(basSkuForm, query);
			query.setCustomerSet(SfcUserLoginUtil.getLoginUser().getCustomerSet());
			MybatisCriteria mybatisCriteria = new MybatisCriteria();
			mybatisCriteria.setCondition(BeanConvertUtil.bean2Map(query));
			// excel表格的表头，map
			LinkedHashMap<String, String> fieldMap = getLeadToFiledPublicQuestionBank();
			// excel的sheetName
			String sheetName = "产品档案历史";
			// excel要导出的数据
			List<BasSkuHistory> basSkuHistoryList = basSkuHistoryMybatisDao.queryByList(mybatisCriteria);
			// 导出
			if (basSkuHistoryList == null || basSkuHistoryList.size() == 0) {
				//System.out.println("题库为空");
			}else {

				for (BasSkuHistory basSkuHistory : basSkuHistoryList) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=null;

					if("1".equals(basSkuHistory.getActiveFlag())){
						basSkuHistory.setActiveFlag("是");
					}else if("0".equals(basSkuHistory.getActiveFlag())){
						basSkuHistory.setActiveFlag("否");
					}
					if(Constant.CODE_CATALOG_FIRSTSTATE_PASS.equals(basSkuHistory.getFirstop())){
						basSkuHistory.setFirstop("审核通过");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_USELESS.equals(basSkuHistory.getFirstop())){
						basSkuHistory.setFirstop("已报废");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_NEW.equals(basSkuHistory.getFirstop())){
						basSkuHistory.setFirstop("新建");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_STOP.equals(basSkuHistory.getFirstop())){
						basSkuHistory.setFirstop("已停止");
					}else if(Constant.CODE_CATALOG_FIRSTSTATE_CHECKING.equals(basSkuHistory.getFirstop())){
						basSkuHistory.setFirstop("审核中");
					}
					if("1".equals(basSkuHistory.getReservedfield09())){
						basSkuHistory.setReservedfield09("是");
					}else if("0".equals(basSkuHistory.getReservedfield09())){
						basSkuHistory.setReservedfield09("否");
					}
					if("1".equals(basSkuHistory.getSkuGroup7())){
						basSkuHistory.setSkuGroup7("是");
					}else if("0".equals(basSkuHistory.getSkuGroup7())){
						basSkuHistory.setSkuGroup7("否");
					}
					if("1".equals(basSkuHistory.getSkuGroup8())){
						basSkuHistory.setSkuGroup8("是");
					}else if("0".equals(basSkuHistory.getSkuGroup8())){
						basSkuHistory.setSkuGroup8("否");
					}
					if("FLL".equals(basSkuHistory.getReservedfield07())){
						basSkuHistory.setReservedfield07("非冷链");
					}else if("LC".equals(basSkuHistory.getReservedfield07())){
						basSkuHistory.setReservedfield07("冷藏");
					}else if("LD".equals(basSkuHistory.getReservedfield07())){
						basSkuHistory.setReservedfield07("冷冻");
					}
					System.out.println();
					if(basSkuHistory.getReservedfield10()!=null){
						basSkuHistory.setReservedfield10(basSkuHistory.getReservedfield10()+"天");
					}
					if(basSkuHistory.getAddtime()!=null) {
						basSkuHistory.setAddtimeDc(sdf.format(basSkuHistory.getAddtime()));
					}
					if(basSkuHistory.getEdittime()!=null) {
						basSkuHistory.setEdittimeDc(sdf.format(basSkuHistory.getEdittime()));
					}
				}
				ExcelUtil.listToExcel(basSkuHistoryList, fieldMap, sheetName, response);
				//System.out.println("导出成功~~~~");
			}
		} catch (ExcelException e) {
			e.printStackTrace();
		}
	}




}
