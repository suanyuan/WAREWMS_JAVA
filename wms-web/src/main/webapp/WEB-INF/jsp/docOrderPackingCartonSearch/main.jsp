<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>
var processType;
var ezuiMenu;                 //右键菜单
var ezuiForm;                 //一级dialog form
var ezuiDialog;               //一级dialog
var ezuiDatagrid;              //主页datagrid

var ezuiCustDataDialog;        //货主
var ezuiCustDataDialogId;      //货主
var ezuiSkuDataDialog;         //产品名称选择框
var ezuiSkuDataDialogId;       //产品名称选择框
var productDialog_docQcSearch;//主页产品代码选择框
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
	ezuiDatagrid = $('#ezuiDatagrid').datagrid(	{
		url: '<c:url value="/docOrderPackingCartonSearchController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#toolbar',
		title: '复核记录查询',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: false,
		columns: [[
			{field: 'orderno', title: '出库单号', width: 124},
			{field: 'traceid',		title: '箱号',	width: 150 },
			{field: 'packingflag', title: '是否装箱完成', width: 90,formatter:yesOrNoFormatter},
			{field: 'lotatt10', title: '质量状态', width: 80, formatter: ZL_TYPstatusFormatter},
			{field: 'customerid', title: '货主代码', width: 134},
			{field: 'shippershortname', title: '货主简称', width: 134},
			{field: 'lotatt08', title: '供应商', width: 134},
			{field: 'sku', title: '产品代码', width: 150},
			{field: 'lotatt12', title: '产品名称', width: 200},
			{field: 'lotatt06', title: '注册证号', width: 200},
			{field: 'skudesce', title: '规格/型号', width: 150},
			{field: 'lotatt04', title: '生产批号', width: 110},
            {field: 'lotatt07',title: '灭菌批号',	width: 110 },
			{field: 'lotatt05', title: '序列号', width: 110},
			{field: 'lotatt01', title: '生产日期', width: 100},
			{field: 'lotatt03', title: '入库日期', width: 100},
			{field: 'lotatt02', title: '有效期/失效期', width: 100},
			{field: 'lotatt11', title: '存储条件', width: 100},
			{field: 'lotatt15', title: '生产企业', width: 200},
			{field: 'reservedfield06', title: '生产许可证号/备案号', width: 150},
			{field: 'qty', title: '装箱件数', width: 100},
			{field: 'qtyEach', title: '装箱数量', width: 100},
			{field: 'uom', title: '单位', width: 70},
			{field: 'description', title: '复核说明', width: 160},
			{field: 'conclusion', title: '复核结论', width: 160},
			{field: 'editwho', title: '复核人', width: 100},
			{field: 'edittime', title: '复核时间', width: 134},
			{field: 'addtime', title: '创建时间', width: 134},
			{field: 'addwho', title: '创建人', width: 100},
			{field: 'lotatt14', title: '入库单号', width: 100},
			{field: 'lotatt09', title: '产品属性', width: 100,formatter:YP_TYPstatusFormatter},
			{field: 'lotatt13', title: '双证', width: 100,formatter:Asn_DoublecstatusFormatter},
			{field: 'qty1', title: '换算率', width: 100}
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
			ajaxBtn($('#menuId').val(), '<c:url value="/docOrderPackingCartonSearchController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
//一级dialog初始化
	ezuiDialog = $('#ezuiDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '#ezuiDialogBtn',
		onClose: function () {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
//产品名称选择弹框
    ezuiSkuDataDialog = $('#ezuiSkuDataDialog').dialog({
        modal: true,
        title: '<spring:message code="common.dialog.title"/>',
        buttons: '',
        onOpen: function () {

        },
        onClose: function () {

        }
    }).dialog('close');
//货主查询弹框初始化
    ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
        modal: true,
        title: '<spring:message code="common.dialog.title"/>',
        buttons: '',
        onOpen: function () {

        },
        onClose: function () {

        }
    }).dialog('close');

//产品代码控件初始化 载入公用弹窗页面
    $("#toolbar #sku").textbox({
        icons: [{
            iconCls: 'icon-search',
            handler: function (e) {
                productDialog_docQcSearch = $('#ezuiSkuSearchDialog').dialog({
                    modal: true,
                    title: '查询',
                    href: sy.bp() + "/basSkuController.do?toSearchDialog&target=docQcSearch",
                    width: 850,
                    height: 500,
                    cache: false,
                    onClose: function () {

                    }
                })
            }
        }]
    });
//主页产品名称查询
    $("#toolbar #lotatt12").textbox({
        icons: [{
            iconCls: 'icon-search',
            handler: function (e) {
                $("#ezuiSkuDataDialog #sku").textbox('clear');
                ezuiSkuDataClick();
                ezuiSkuDataSearch();
            }
        }]
    });
//查询条件货主字段初始化
    $("#toolbar #customerid").textbox({
        icons: [{
            iconCls: 'icon-search',
            handler: function (e) {
                $("#ezuiCustDataDialog #customerid").textbox('clear');
                ezuiCustDataClick();
                ezuiCustDataDialogSearch();
            }
        }]
    });

});


//打印验收报告
var printQcSearch = function(){
	var orderno =$('#orderno').val();                //出库单号
	var traceid= $('#traceid').val();                   //箱号
	var lotatt10= $('#lotatt10').combobox('getValue');  //z质量状态
	var skudesce=$('#skudesce').val();                     //规格
	var customerid= $('#customerid').val();              //货主代码
	var shippershortname= $('#shippershortname').val();   //货主简称
	var sku= $('#sku').val();                              //产品代码
	var lotatt12= $('#lotatt12').val();                    //产品名称
	var lotatt08 =$('#lotatt08').val();                     //供应商
	var lotatt15= $('#lotatt15').val();                    //生产企业
	var edittimeStart= $('#edittimeStart').datebox('getValue');  //复核日期
	var edittimeEnd= $('#edittimeEnd').datebox('getValue');      //复核日期
	var lotatt14= $('#lotatt14').textbox('getValue');      //入库单号
	var packingflag= $('#packingflag').combobox('getValue');      //是否装箱完成

	//必须点击查询按钮。
	if (null === orderno || orderno === '') {
		showMsg("请先选择(出库单号)再进行打印操作......");
	} else {
		window.open(sy.bp()+"/docOrderPackingCartonSearchController.do?printQcSearch&orderno="+orderno+
				"&traceid="+traceid+"&lotatt10="+lotatt10+"&skudesce="+skudesce+"&customerid="+customerid+"&shippershortname="
				+shippershortname+"&sku="+sku+ "&lotatt12="+lotatt12+"&lotatt08="+lotatt08+"&lotatt15="
				+lotatt15+"&edittimeStart="+edittimeStart+"&edittimeEnd="+edittimeEnd+"&lotatt14="+lotatt14+"&packingflag="+packingflag);
	}
}


//货主查询弹框弹出start=========================
var ezuiCustDataClick = function () {
    ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
        url: '<c:url value="/basCustomerController.do?showDatagrid"/>',
        method: 'POST',
        toolbar: '#ezuiCustToolbar',
        title: '客户档案',
        pageSize: 50,
        pageList: [50, 100, 200],
        fit: true,
        border: false,
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        queryParams: {
            activeFlag: '1',
            customerType: 'OW'
        },
        idField: 'id',
        columns: [[
            {field: 'customerid', title: '客户代码', width: 15},
            {field: 'descrC', title: '中文名称', width: 50},
            {field: 'descrE', title: '英文名称', width: 50},
            {field: 'customerTypeName', title: '类型', width: 15},
            {
                field: 'activeFlag', title: '激活', width: 15, formatter: function (value, rowData, rowIndex) {
                    return rowData.activeFlag == '1' ? '是' : '否';
                }
            }
        ]],
        onDblClickCell: function (index, field, value) {
            selectCust();
        },
        onRowContextMenu: function (event, rowIndex, rowData) {
        }, onLoadSuccess: function (data) {
            $(this).datagrid('unselectAll');
        }
    });
    $("#ezuiCustDataDialog #customerType").combobox('setValue', 'OW').combobox('setText', '货主');
    $("#ezuiCustDataDialog #activeFlag").combobox('setValue', '1').combobox('setText', '是');
    ezuiCustDataDialog.dialog('open');
};

//货主查询弹框查询按钮
var ezuiCustDataDialogSearch = function () {
    ezuiCustDataDialogId.datagrid('load', {
        customerid: $("#ezuiCustDataDialog #customerid").textbox("getValue"),
        customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
        activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
    });
};
//货主查询弹框选择按钮
var selectCust = function () {
    var row = ezuiCustDataDialogId.datagrid('getSelected');
    if (row) {
        $("#toolbar #customerid").textbox('setValue', row.customerid);
        ezuiCustDataDialog.dialog('close');
    }
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function () {
    $("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================

// 产品名称选择弹框-
var ezuiSkuDataClick = function () {
    // $("#ezuiSkuDataDialog #customerid").textbox('setValue',$("#ezuiDetailsForm #customerid").textbox("getValue")).textbox('readonly', true);
    // $("#ezuiSkuDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
    ezuiSkuDataDialogId = $('#ezuiSkuDataDialogId').datagrid({
        url: '<c:url value="/basSkuController.do?showDatagrid"/>',
        method: 'POST',
        toolbar: '#ezuiSkuToolbar',
        title: '产品档案',
        pageSize: 50,
        pageList: [50, 100, 200],
        fit: true,
        border: false,
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        // queryParams:{
        // 	customerid : $("#ezuiDetailsForm #customerid").textbox("getValue"),
        // 	activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
        // },
        idField: 'sku',
        columns: [[
            {field: 'customerid', title: '客户代码', width: 80},
            {field: 'sku', title: '产品代码', width: 160},
            {field: 'reservedfield01', title: '产品名称', width: 160},
            {field: 'descrE', title: '英文名称', width: 160},
            {
                field: 'activeFlag', title: '激活', width: 40, formatter: function (value, rowData, rowIndex) {
                    return rowData.activeFlag == '1' ? '是' : '否';
                }
            },
            // {field: 'alternateSku1', title: '商品条码', width: 120},
            // {field: 'packid', title: '包装代码', width: 80},
            // {field: 'qty', title: '库存数', width: 60},
            // {field: 'qtyallocated', title: '分配数', width: 60},
            // {field: 'qtyonhold', title: '冻结数', width: 60}
        ]],
        onDblClickCell: function (index, field, value) {
            selectSku();
        },
        onRowContextMenu: function (event, rowIndex, rowData) {
        }, onLoadSuccess: function (data) {
            $(this).datagrid('unselectAll');
        }
    });

    ezuiSkuDataDialog.dialog('open');
};
// 产品名称选择弹框查询-
var ezuiSkuDataSearch = function () {
    ezuiSkuDataDialogId.datagrid('load', {
        customerid: $("#ezuiSkuDataDialog #customerid").textbox("getValue"),
        sku: $("#ezuiSkuDataDialog #sku").textbox("getValue"),
        activeFlag: $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
    });
};
// 产品名称选择弹框清空
var ezuiSkuToolbarClear = function () {
    $("#ezuiSkuDataDialog #sku").textbox('clear');
    $("#ezuiSkuDataDialog #customerid").textbox('clear');
    $("#ezuiSkuDataDialog #activeFlag").combobox('clear');
};
// 产品名称选择-主页
var selectSku = function () {
    var row = ezuiSkuDataDialogId.datagrid('getSelected');
    if (row) {
        $("#toolbar #lotatt12").textbox('setValue', row.reservedfield01);
        ezuiSkuDataDialog.dialog('close');
    }
};

// 主页产品代码框选择
function choseSelect_product_docQcSearch(row) {
    var sku;
    if (row) {
        sku = row;
    } else {
        row = $("#productSearchGrid_docQcSearch").datagrid("getSelections");
        if (row) {
            sku = row[0]
        }
    }
    if (sku) {
        $("#toolbar #sku").textbox("setValue", sku.sku);
    }
    productDialog_docQcSearch.dialog("close");
}


/* 导出start */
var doExport = function () {
    if (navigator.cookieEnabled) {
        $('#ezuiBtn_export').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);

        param.put("orderno", $('#orderno').val());         //复核单号
        param.put("traceid", $('#traceid').val());          //箱号
		param.put("lotatt10", $('#lotatt10').combobox('getValue'));  //z质量状态
		param.put("skudesce", $('#skudesce').val());                     //规格
		param.put("customerid",$('#customerid').val());              //货主代码
		param.put("shippershortname",$('#shippershortname').val());   //货主简称
        param.put("sku",$('#sku').val()),                              //产品代码
        param.put("lotatt12",$('#lotatt12').val()),                     //产品名称
        param.put("lotatt08",$('#lotatt08').val()),                     //供应商
        param.put("lotatt15",$('#lotatt15').val()),                     //生产企业
        param.put("edittimeStart", $('#edittimeStart').datebox('getValue'));  //复核日期
		param.put("edittimeEnd",$('#edittimeEnd').datebox('getValue'));      //复核日期
		param.put("lotatt14",$('#lotatt14').textbox('getValue'));      //入库单号
		param.put("packingflag", $('#packingflag').combobox('getValue'));//验收状态

        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp() + "/docOrderPackingCartonSearchController.do?exportDocOrderPackingCartonSearchDataToExcel", param);
        downloadCheckTimer = window.setInterval(function () {
            window.clearInterval(downloadCheckTimer);
            $('#' + formId).remove();
            $('#ezuiBtn_export').linkbutton('enable');
            $.messager.progress('close');
            $.messager.show({
                msg: "<spring:message code='common.message.export.success'/>",
                title: "<spring:message code='common.message.prompt'/>"
            });
        }, 1000);
    } else {
        $.messager.show({
            msg: "<spring:message code='common.navigator.cookieEnabled.false'/>",
            title: "<spring:message code='common.message.prompt'/>"
        });
    }
};
/* 导出end */
//主页查询
var doSearch = function() {

	ezuiDatagrid.datagrid('load', {
		orderno: $('#orderno').val(),                   //出库单号
		traceid: $('#traceid').val(),                   //箱号
		lotatt10: $('#lotatt10').combobox('getValue'),  //z质量状态
		skudesce: $('#skudesce').val(),                     //规格
		customerid: $('#customerid').val(),              //货主代码
		shippershortname: $('#shippershortname').val(),   //货主简称
		sku: $('#sku').val(),                              //产品代码
		lotatt12: $('#lotatt12').val(),                     //产品名称
		lotatt08: $('#lotatt08').val(),                     //供应商
		lotatt15: $('#lotatt15').val(),                     //生产企业
		edittimeStart: $('#edittimeStart').datebox('getValue'),  //复核日期
		edittimeEnd: $('#edittimeEnd').datebox('getValue'),      //复核日期
		lotatt14: $('#lotatt14').textbox('getValue'),      //入库单号
		packingflag: $('#packingflag').combobox('getValue'),      //是否装箱完成

	});
}
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<%--主页datagrid工具栏--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>出库单号</th><td><input type='text' id='orderno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>箱号</th><td><input type='text' id='traceid' class='easyui-textbox' size='16' data-options=''/></td>
	                         <th>质量状态</th><td><input type='text' id='lotatt10' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?qcState"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
	                      <th>规格</th><td><input type='text' id='skudesce' class='easyui-textbox' size='16' data-options=''/></td>


						</tr>
						<tr>
							<th>货主代码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>货主简称</th><td><input type='text' id='shippershortname' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品代码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品名称</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>供应商</th><td><input type='text' id='lotatt08' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产企业</th><td><input type='text' id='lotatt15' class='easyui-textbox' size='16' data-options=''/></td>
							<th>复核日期</th><td><input type='text' id='edittimeStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='edittimeEnd' class='easyui-datebox' size='16' data-options=''/></td>


						</tr>
						<tr>
                            <th>入库单号</th><td><input type='text' id='lotatt14' class='easyui-textbox' size='16' data-options=''/></td>
                            <th>是否装箱完成</th><td><input type='text' id='packingflag' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getYesOrNoCombobox"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>

                            <td colspan="2">
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                                <a onclick='doExport()' id='ezuiBtn_check' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导出</a>

                            </td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
					<a onclick='printQcSearch()' id='ezuiBtn_printQcSeacrch' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印复核报告</a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
<%--一级dialog--%>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='docQcHeaderId' name='docQcHeaderId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='qcno' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入1</th>
					<td><input type='text' name='pano' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入2</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入3</th>
					<td><input type='text' name='qcreference1' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入4</th>
					<td><input type='text' name='qcreference2' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入5</th>
					<td><input type='text' name='qcreference3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='qcreference4' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入7</th>
					<td><input type='text' name='qcreference5' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入8</th>
					<td><input type='text' name='qctype' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入9</th>
					<td><input type='text' name='qcstatus' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入10</th>
					<td><input type='text' name='qccreationtime' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入11</th>
					<td><input type='text' name='userdefine1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='userdefine2' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入13</th>
					<td><input type='text' name='userdefine3' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入14</th>
					<td><input type='text' name='userdefine4' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入15</th>
					<td><input type='text' name='userdefine5' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入16</th>
					<td><input type='text' name='notes' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>创建时间</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>创建人</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>编辑时间</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>编辑人</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入21</th>
					<td><input type='text' name='qcPrintFlag' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入22</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

<!-- 货主选择弹框 -->
    <div id='ezuiCustDataDialog' style="width:700px;height:480px;padding:10px 20px">
        <div class='easyui-layout' data-options='fit:true,border:false'>
            <div data-options="region:'center'">
                <div id='ezuiCustToolbar' class='datagrid-toolbar' style="">
                    <fieldset>
                        <legend><spring:message code='common.button.query'/></legend>
                        <table>
                            <tr>
                                <th>客户：</th>
                                <td>
                                    <input type='text' id='customerid' name="customerid" class='easyui-textbox' size='12'
                                           data-options='prompt:"请输入客户代码"'/></td>
                                <th>类型：</th>
                                <td>
                                    <input type='text' id='customerType' name="customerType" class='easyui-combobox'
                                           size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
                                </td>
                                <th>激活：</th>
                                <td>
                                    <input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox' size='8'
                                           data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'Y', value: '是'},
																																{id: 'N', value: '否'}
																															]"/>
                                </td>
                                <td>
                                    <a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton'
                                       data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                                    <a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton'
                                       data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
                                    <a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton'
                                       data-options='plain:true,iconCls:"icon-remove"'
                                       href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <div id='ezuiCustDialogBtn'></div>
                </div>
                <table id='ezuiCustDataDialogId'></table>
            </div>
        </div>
    </div>
    <div id='ezuiCustDialogBtn'>
        <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
                code='common.button.commit'/></a>
        <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
                code='common.button.close'/></a>
    </div>
<!--产品代码查询弹窗 -->
    <div id="ezuiSkuSearchDialog">

    </div>
<%--导入页面--%>
    <c:import url='/WEB-INF/jsp/docOrderPackingCartonSearch/skuDialog.jsp'/>
</body>
</html>
