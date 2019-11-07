package com.wms.controller;

import com.wms.constant.Constant;
import com.wms.easyui.EasyuiCombobox;
import com.wms.easyui.EasyuiDatagrid;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.entity.order.OrderHeaderForNormal;
import com.wms.mybatis.entity.SfcUserLogin;
import com.wms.query.ActAllocationDetailsQuery;
import com.wms.query.OrderHeaderForNormalQuery;
import com.wms.service.DocOrderExportService;
import com.wms.service.OrderHeaderForNormalService;
import com.wms.utils.ResourceUtil;
import com.wms.utils.annotation.Login;
import com.wms.utils.editor.CustomDateEditor;
import com.wms.utils.exception.ExcelException;
import com.wms.vo.ActAllocationDetailsVO;
import com.wms.vo.Json;
import com.wms.vo.OrderHeaderForNormalVO;
import com.wms.vo.form.DocOrderHeaderExportForm;
import com.wms.vo.form.OrderHeaderForNormalForm;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("docOrderHeaderController")
public class DocOrderHeaderController {

    @Autowired
    private OrderHeaderForNormalService orderHeaderForNormalService;

    @Autowired
    private DocOrderExportService docOrderExportService;

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, "orderStartTime", new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Date.class, "orderEndTime", new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Date.class, "requiredDeliveryTime", new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Date.class, "orderTime", new CustomDateEditor(dateTimeFormat, true));
        binder.registerCustomEditor(Date.class, "lastShipmentTime", new CustomDateEditor(dateTimeFormat, true));
    }

    @Login
    @RequestMapping(params = "toMain")
    public ModelAndView toMain(String menuId) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("menuId", menuId);
        return new ModelAndView("docOrderHeader/main", model);
    }

    @Login
    @RequestMapping(params = "showDatagrid")
    @ResponseBody
    public EasyuiDatagrid<OrderHeaderForNormalVO> showDatagrid(EasyuiDatagridPager pager, OrderHeaderForNormalQuery query) {
        return orderHeaderForNormalService.getPagedDatagrid(pager, query);
    }

    @Login
    @RequestMapping(params = "showAllocation")
    @ResponseBody
    public EasyuiDatagrid<ActAllocationDetailsVO> showAllocation(EasyuiDatagridPager pager, String ordero) {
        ActAllocationDetailsQuery query = new ActAllocationDetailsQuery();
        query.setOrderno(ordero);
        return orderHeaderForNormalService.getPageAllocation(pager, query);
    }

    @Login
    @RequestMapping(params = "add")
    @ResponseBody
    public Json add(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
        Json json = orderHeaderForNormalService.add(orderHeaderForNormalForm);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "edit")
    @ResponseBody
    public Json edit(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
        Json json = orderHeaderForNormalService.edit(orderHeaderForNormalForm);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "delete")
    @ResponseBody
    public Json delete(String orderno) {
        Json json = orderHeaderForNormalService.delete(orderno);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "allocation")
    @ResponseBody
    public Json allocation(String orderNo) {
        Json json = orderHeaderForNormalService.allocation(orderNo);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "deAllocation")
    @ResponseBody
    public Json deAllocation(String orderNo) {
        Json json = orderHeaderForNormalService.deAllocation(orderNo);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "picking")
    @ResponseBody
    public Json picking(String orderNo) {
        Json json = orderHeaderForNormalService.picking(orderNo);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "unPicking")
    @ResponseBody
    public Json unPicking(String orderNo) {
        Json json = orderHeaderForNormalService.unPicking(orderNo);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "shipment")
    @ResponseBody
    public Json shipment(OrderHeaderForNormalForm orderHeaderForNormalForm) throws Exception {
        Json json = orderHeaderForNormalService.shipment(orderHeaderForNormalForm);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "cancel")
    @ResponseBody
    public Json cancel(String orderno) {
        Json json = orderHeaderForNormalService.cancel(orderno);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

	/*@Login
	@RequestMapping(params = "exportPickingPdf")
	public void exportPickingPdf(HttpServletResponse response, String orderNo) throws Exception {
		try {
			orderHeaderForNormalService.exportPickingPdf(response, orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

//	@Login
//	@RequestMapping(params = "exportReceiptPdf")
//	public void exportReceiptPdf(HttpServletResponse response, String orderNo) throws Exception {
//		try {
//			orderHeaderForNormalService.exportReceiptPdf(response, orderNo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


    @Login
    @RequestMapping(params = "importExcelData")
    @ResponseBody
    public Json importExcelData(MultipartHttpServletRequest mhsr) throws Exception {
        return orderHeaderForNormalService.importExcelData(mhsr);
    }

    @Login
    @RequestMapping(params = "exportTemplate", method = RequestMethod.POST)
    public void exportTemplate(HttpServletResponse response, String token) throws Exception {
        orderHeaderForNormalService.exportTemplate(response, token);
    }

    @Login
    @RequestMapping(params = "getBtn")
    @ResponseBody
    public Json getBtn(String id, HttpSession session) {
        return orderHeaderForNormalService.getBtn(id, (SfcUserLogin) session.getAttribute(ResourceUtil.getUserInfo()));
    }

    @Login
    @RequestMapping(params = "getOrderTypeCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getOrderTypeCombobox() {
        return orderHeaderForNormalService.getOrderTypeCombobox();
    }

    @Login
    @RequestMapping(params = "getOrderStatusCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getOrderStatusCombobox() {
        return orderHeaderForNormalService.getOrderStatusCombobox();
    }

    @Login
    @RequestMapping(params = "getReleasestatusCombobox")
    @ResponseBody
    public List<EasyuiCombobox> getReleasestatusCombobox() {
        return orderHeaderForNormalService.getReleasestatusCombobox();
    }

    @Login
    @RequestMapping(params = "getRefOutOrder")
    @ResponseBody
    public List<EasyuiCombobox> getRefOutOrder() {
        return orderHeaderForNormalService.getRefOut();
    }


    /**
     * 打印拣货
     */
    @Login
    @RequestMapping(params = "exportPackingPdf")
    public String exportPackingPdf(Model model, @RequestParam(value = "orderno") String orderno) {

        String[] s = orderno.split(",");
        List<OrderHeaderForNormal> orderHeaderForNormal = new ArrayList<OrderHeaderForNormal>();
        for (String a : s) {
            orderHeaderForNormal.add(orderHeaderForNormalService.exportPickingPdf(a));
        }
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(orderHeaderForNormal);
        model.addAttribute("url", "WEB-INF/jasper/reportOrderHeader.jasper");
        model.addAttribute("format", Constant.JASPER_PDF);
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";
    }

    /**
     * 随货清单
     *
     * @throws Exception
     */
    @Login
    @RequestMapping(params = "exportAccompanyingPdf")
    public String exportAccompanyingPdf(Model model, @RequestParam(value = "orderno") String orderno) throws Exception {

        String[] s = orderno.split(",");
        List<OrderHeaderForNormal> orderHeaderForNormal = new ArrayList<OrderHeaderForNormal>();
        for (String a : s) {
            orderHeaderForNormal.add(orderHeaderForNormalService.exportAccompanyingPdf(a));
        }
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(orderHeaderForNormal);
        String customer = orderHeaderForNormal.get(0).getCustomerid();
        if (customer.equals("JSGR")) {
            //1 国润
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingJSGR.jasper");
        } else if (customer.equals("JSML")) {
            //2 明伦
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingJSML.jasper");
        } else if (customer.equals("MY")) {
            //3 妙有
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingMY.jasper");
        } else if (customer.equals("JSJY")) {
            //4 嘉意
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingJSJY.jasper");
        } else if (customer.equals("BL")) {
            //5 佰礼
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingBL.jasper");
        } else if (customer.equals("YG")) {
            //6 亦舸
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingYG.jasper");
        } else if (customer.equals("WQ")) {
            //7 稳勤
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingWQ.jasper");
        } else if (customer.equals("BDL")) {
            //8 百多力
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingBDL.jasper");
        } else if (customer.equals("BZ")) {
            //9 标准
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingBZ.jasper");
        } else if (customer.equals("HQ")) {
            //10 宏确
            model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingHQ.jasper");
        } else {
            //原随货单
            model.addAttribute("url", "WEB-INF/jasper/reportOrderHeader1.jasper");
        }
        model.addAttribute("format", Constant.JASPER_PDF);
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";
    }

	@Login
	@RequestMapping(params = "exportAccompanyingExcel")
	public String exportAsnDataToExcel(Model model,@RequestParam(value = "orderno") String orderno){

		List<OrderHeaderForNormal> orderHeaderForNormal = new ArrayList<OrderHeaderForNormal>();
		orderHeaderForNormal.add(orderHeaderForNormalService.exportAccompanyingPdf(orderno));
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(orderHeaderForNormal);
		String customer = orderHeaderForNormal.get(0).getCustomerid();
		if (customer.equals("JSGR")) {
			//1 国润
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingJSGR.jasper");
		} else if (customer.equals("JSML")) {
			//2 明伦
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingJSML.jasper");
		} else if (customer.equals("MY")) {
			//3 妙有
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingMY.jasper");
		} else if (customer.equals("JSJY")) {
			//4 嘉意
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingJSJY.jasper");
		} else if (customer.equals("BL")) {
			//5 佰礼
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingBL.jasper");
		} else if (customer.equals("YG")) {
			//6 亦舸
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingYG.jasper");
		} else if (customer.equals("WQ")) {
			//7 稳勤
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingWQ.jasper");
		} else if (customer.equals("BDL")) {
			//8 百多力
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingBDL.jasper");
		} else if (customer.equals("BZ")) {
			//9 标准
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingBZ.jasper");
		} else if (customer.equals("HQ")) {
			//10 宏确
			model.addAttribute("url", "WEB-INF/jasper/reportAcoompanyingHQ.jasper");
		} else {
			//原随货单
			model.addAttribute("url", "WEB-INF/jasper/reportOrderHeader1.jasper");
		}
		model.addAttribute("format", Constant.JASPER_XLS);
		model.addAttribute("jrMainDataSource", jrDataSource);
		return "iReportView";
	}


	@Login
    @RequestMapping(params = "getLotAttBySkuCustomerId", method = RequestMethod.POST)
    @ResponseBody
    public List<EasyuiCombobox> getLotAttBySkuCustomerId(String sku, String customerId) {
        return orderHeaderForNormalService.getLotAttBySkuCustomerId(sku, customerId);
    }

    @Login
    @RequestMapping(params = "doRefOut", method = RequestMethod.POST)
    @ResponseBody
    public Json doRefOut(String orderno, String refOrderno) throws Exception {
        return orderHeaderForNormalService.doRefOut(orderno, refOrderno);
    }

    @Login
    @RequestMapping(params = "reqDouble", method = RequestMethod.POST)
    @ResponseBody
    public Json reqDouble(String orderno) throws Exception {
        return orderHeaderForNormalService.reqDouble(orderno);
    }

    /**
     * 打印质量合格证
     *
     * @param response
     * @param orderCodeList
     * @throws Exception
     */
    @Login
    @RequestMapping(params = "printH")
    public void printH(HttpServletResponse response, String orderCodeList) throws Exception {
        try {
            orderHeaderForNormalService.printH(response, orderCodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Login
    @RequestMapping(params = "printExpress")
    public String printExpress(HttpServletResponse response, String orderCodeList, Model model) throws Exception {
        try {
            orderHeaderForNormalService.printExpress(response, orderCodeList, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "iReportView";
    }


    @Login
    @RequestMapping(params = "copyDetailGo", method = RequestMethod.POST)
    @ResponseBody
    public Json copyDetailGo(String generalNO, String detailOrderno, String customerid, String soreference2, String copyFlag) throws Exception {
        Json json = orderHeaderForNormalService.copyDetailGo(generalNO, detailOrderno, customerid, soreference2, copyFlag);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    @Login
    @RequestMapping(params = "qlOrderDetails", method = RequestMethod.POST)
    @ResponseBody
    public Json qlOrderDetails(String orderno) {
        Json json = orderHeaderForNormalService.qlOrderDetails(orderno);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    //导出序列号
    @Login
    @RequestMapping(params = "exportbasSerialNumToExcel")
    public void exportbasSerialNumToExcel(HttpServletResponse response, OrderHeaderForNormalForm orderNofrom) throws Exception {
        orderHeaderForNormalService.exportbasSerialNumToExcel(response, orderNofrom);
    }

    //导出
    @Login
    @RequestMapping(params = "exportOrderNoToExcel")
    public String exportOrderDataToExcel(Model model, String orderno) {
        List<OrderHeaderForNormal> orderHeaderForNormalList = docOrderExportService.docOrderToExcel(orderno);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(orderHeaderForNormalList);
        model.addAttribute("url", "WEB-INF/jasper/reportDocOrderDetails.jasper");
        model.addAttribute("format", Constant.JASPER_XLS);
        model.addAttribute("jrMainDataSource", jrDataSource);
        return "iReportView";
    }

    //导出Excel格式所有信息
    @Login
    @RequestMapping(params = "exportOrderNoToExcel1")
    public void exportOrderDataToExcel1(HttpServletResponse response, DocOrderHeaderExportForm from) throws UnsupportedEncodingException, ExcelException {
        docOrderExportService.docOrderToExcel1(response, from);
    }

    //检查orderno是否存在
    @Login
    @RequestMapping(params = "isexportOrderNo")
    @ResponseBody
    public Json isexportOrderNo(String orderno) throws Exception {
        return orderHeaderForNormalService.isexportOrderNo(orderno);
    }


    //快递投诉
    @Login
    @RequestMapping(params = "courierComplaint")
    @ResponseBody
    public Json courierComplaint(OrderHeaderForNormalForm orderHeaderForNormalForm) {
        Json json = orderHeaderForNormalService.courierComplaint(orderHeaderForNormalForm);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

    //回写快递单号/签回单号
    @Login
    @RequestMapping(params = "writeBackExpressBtnCommit")
    @ResponseBody
    public Json writeBackExpressBtnCommit(OrderHeaderForNormalForm orderHeaderForNormalForm) {
        Json json = orderHeaderForNormalService.writeBackExpressBtnCommit(orderHeaderForNormalForm);
        if (json == null) {
            json = new Json();
            json.setMsg(ResourceUtil.getProcessResultMsg(json.isSuccess()));
        }
        return json;
    }

}