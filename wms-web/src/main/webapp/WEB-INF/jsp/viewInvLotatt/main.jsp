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
var ezuiDatagrid;
var ezuiFormAdj;
var ezuiFormMov;
var ezuiDialogAdj;
var ezuiDialogMov;
var ezuiDialogHold;

var ezuiCustDataDialog;
var ezuiCustDataDialogId;

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiFormAdj = $('#ezuiFormAdj').form();
	ezuiFormMov = $('#ezuiFormMov').form();
	ezuiFormHold = $('#ezuiFormHold').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/viewInvLotattController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '库存管理_按商品/库位/批次',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'id',
		columns : [[
// 			{field: 'pkey',		title: 'No.',	width: 41 },        
			{field: 'fmcustomerid',		title: '货主',	width: 71 },
			{field: 'fmsku',		title: '商品编码',	width: 91 },
			{field: 'skudescrc',		title: '商品名称',	width: 151 },
			{field: 'fmlocation',		title: '库位',	width: 81 },
			{field: 'fmqty',		title: '库存数量',	width: 71 },
			{field: 'qtyallocated',		title: '分配数量',	width: 71 },
			{field: 'qtyavailed',		title: '可用数量',	width: 71 },
			{field: 'qtyholded',		title: '冻结数量',	width: 71 },
// 			{field: 'netweight',		title: '净重',	width: 71 },
			{field: 'totalgrossweight',		title: '毛重',	width: 71 },
			{field: 'totalcubic',		title: '体积',	width: 71 },
			{field: 'lotatt01',		title: '生产日期',	width: 71 },
			{field: 'lotatt02',		title: '失效日期',	width: 71 },
			{field: 'lotatt03',		title: '入库日期',	width: 71 },
			{field: 'lotatt04',		title: '库存状态',	width: 71 },
			{field: 'lotatt05',		title: '批属5',	width: 71 },
			{field: 'lotatt06',		title: '批属6',	width: 71 },
			{field: 'fmlotnum',		title: '批号',	width: 81 },
			{field: 'iPa',		title: '待上架数量',	width: 71 },
			{field: 'toadjqty',		title: '待调整数量',	width: 71 },
			{field: 'iMv',		title: '待移入数量',	width: 71 },
			{field: 'oMv',		title: '待移出数量',	width: 71 },
			{field: 'qtyrpin',		title: '补货待上架',	width: 71 },
			{field: 'qtyrpout',		title: '补货待下架',	width: 71 },
			{field: 'uom',		title: '单位',	width: 71 },
			{field: 'fmid',		title: '跟踪号',	width: 71 },
			{field: 'lpn',		title: 'LPN',	width: 71 },
			{field: 'price',		title: '价值',	width: 71 },
			{field: 'warehouseid',		title: '仓库编码',	width: 71 }
		]],
		onDblClickCell: function(index,field,value){
			edit();
		},
// 		onRowContextMenu : function(event, rowIndex, rowData) {
// 			event.preventDefault();
// 			$(this).datagrid('unselectAll');
// 			$(this).datagrid('selectRow', rowIndex);
// 			ezuiMenu.menu('show', {
// 				left : event.pageX,
// 				top : event.pageY
// 			});
// 		},onLoadSuccess:function(data){
// 			ajaxBtn($('#menuId').val(), '<c:url value="/viewInvLotattController.do?getBtn"/>', ezuiMenu);
// 			$(this).datagrid('unselectAll');
// 		}
	});
	//查询条件货主字段初始化
	$("#fmcustomerid").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
	//货主查询弹框初始化
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');
	ezuiDialogAdj = $('#ezuiDialogAdj').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogAdjBtn',
		onClose : function() {
			ezuiFormClear(ezuiFormAdj);
		}
	}).dialog('close');
	ezuiDialogMov = $('#ezuiDialogMov').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogMovBtn',
		onClose : function() {
			ezuiFormClear(ezuiFormMov);
		}
	}).dialog('close');
	ezuiDialogHold = $('#ezuiDialogHold').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogHoldBtn',
		onClose : function() {
			ezuiFormClear(ezuiFormHold);
		}
	}).dialog('close');
});

//货主查询弹框弹出start=========================
var ezuiCustDataClick = function(){
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
	url : '<c:url value="/basCustomerController.do?showDatagrid&activeFlag=Y&customerType=OW"/>',
	method:'POST',
	toolbar : '#ezuiCustToolbar',
	title: '客户档案',
	pageSize : 50,
	pageList : [50, 100, 200],
	fit: true,
	border: false,
	fitColumns : true,
	nowrap: false,
	striped: true,
	collapsible:false,
	pagination:true,
	rownumbers:true,
	singleSelect:true,
	idField : 'id',
	columns : [[
				{field: 'customerid',	title: '客户代码',	width: 15},
				{field: 'descrC',		title: '中文名称',	width: 50},
				{field: 'descrE',		title: '英文名称',	width: 50},
				{field: 'customerTypeName',	title: '类型',	width: 15},
				{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
					return rowData.activeFlag == 'Y' ? '是' : '否';
	            }}
			]],
	onDblClickCell: function(index,field,value){
		selectCust();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
// 			ajaxBtn($('#menuId').val(), '<c:url value="/cosCommonAddressController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','Y').combobox('setText','是');
	ezuiCustDataDialog.dialog('open');
};
//货主查询弹框查询按钮
var ezuiCustDataDialogSearch = function(){
	ezuiCustDataDialogId.datagrid('load', {
		customerid : $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		/* customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue') */
	});
};
//货主查询弹框选择按钮
var selectCust = function(){
	processType = 'selectCust';
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#fmcustomerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function(){
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================

/* 导出start */
var doExport = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_export').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		
		param.put("fmcustomerid", $('#fmcustomerid').val());
		param.put("fmlocation", $('#fmlocation').val());
		param.put("fmsku", $('#fmsku').val());
		param.put("lotatt01", $('#lotatt01').datebox("getValue"));
		param.put("lotatt01text", $('#lotatt01text').datebox("getValue"));
		param.put("lotatt02", $('#lotatt02').datebox("getValue"));
		param.put("lotatt02text", $('#lotatt02text').datebox("getValue"));
		param.put("lotatt03", $('#lotatt03').datebox("getValue"));
		param.put("lotatt03text", $('#lotatt03text').datebox("getValue"));
		param.put("lotatt04", $('#lotatt04').combobox('getValue'));
		param.put("lotatt05", $('#lotatt05').val());
		param.put("lotatt06", $('#lotatt06').val());

		//--导出Excel
		var formId = ajaxDownloadFile(sy.bp()+"/viewInvLotattController.do?exportViewInvLotattDataToExcel", param);
		downloadCheckTimer = window.setInterval(function () {
			window.clearInterval(downloadCheckTimer);
			$('#'+formId).remove();
			$('#ezuiBtn_export').linkbutton('enable');
			$.messager.progress('close');
			$.messager.show({
				msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
			});
		}, 1000);
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};
/* 导出end */

//库存管理start
var adj = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiFormAdj.form('load',{
			allocationrule : row.allocationrule,
			alternatesku1 : row.alternatesku1,
			alternatesku2 : row.alternatesku2,
			alternatesku3 : row.alternatesku3,
			alternatesku4 : row.alternatesku4,
			alternatesku5 : row.alternatesku5,
			configlist01 : row.configlist01,
			configlist02 : row.configlist02,
			fmcustomerid : row.fmcustomerid,
			fmid : row.fmid,
			fmlocation : row.fmlocation,
			fmlotnum : row.fmlotnum,
			fmqty : row.fmqty,
			fmsku : row.fmsku,
			fmuomName : row.fmuomName,
			iMv : row.iMv,
			iPa : row.iPa,
			imageaddress : row.imageaddress,
			lotatt01 : row.lotatt01,
			lotatt01text : row.lotatt01text,
			lotatt02 : row.lotatt02,
			lotatt02text : row.lotatt02text,
			lotatt03 : row.lotatt03,
			lotatt03text : row.lotatt03text,
			lotatt04 : row.lotatt04,
			lotatt04text : row.lotatt04text,
			lotatt05 : row.lotatt05,
			lotatt05text : row.lotatt05text,
			lotatt06 : row.lotatt06,
			lotatt06text : row.lotatt06text,
			lotatt07 : row.lotatt07,
			lotatt07text : row.lotatt07text,
			lotatt08 : row.lotatt08,
			lotatt08text : row.lotatt08text,
			lotatt09 : row.lotatt09,
			lotatt09text : row.lotatt09text,
			lotatt10 : row.lotatt10,
			lotatt10text : row.lotatt10text,
			lotatt11 : row.lotatt11,
			lotatt11text : row.lotatt11text,
			lotatt12 : row.lotatt12,
			lotatt12text : row.lotatt12text,
			lpn : row.lpn,
			netweight : row.netweight,
			oMv : row.oMv,
			pkey : row.pkey,
			price : row.price,
			qtyallocated : row.qtyallocated,
			qtyavailed : row.qtyavailed,
			qtyholded : row.qtyholded,
			qtyrpin : row.qtyrpin,
			qtyrpout : row.qtyrpout,
			reservedfield01 : row.reservedfield01,
			reservedfield02 : row.reservedfield02,
			reservedfield03 : row.reservedfield03,
			reservedfield04 : row.reservedfield04,
			reservedfield05 : row.reservedfield05,
			rotationid : row.rotationid,
			skudescrc : row.skudescrc,
			skudescre : row.skudescre,
			skugroup1 : row.skugroup1,
			skugroup2 : row.skugroup2,
			skugroup3 : row.skugroup3,
			skugroup4 : row.skugroup4,
			skugroup5 : row.skugroup5,
			softallocationrule : row.softallocationrule,
			toadjqty : row.toadjqty,
			totalcubic : row.totalcubic,
			totalgrossweight : row.totalgrossweight,
			uom : row.uom,
			warehouseid : row.warehouseid
		});
		ezuiDialogAdj.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

var mov = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiFormMov.form('load',{
			allocationrule : row.allocationrule,
			alternatesku1 : row.alternatesku1,
			alternatesku2 : row.alternatesku2,
			alternatesku3 : row.alternatesku3,
			alternatesku4 : row.alternatesku4,
			alternatesku5 : row.alternatesku5,
			configlist01 : row.configlist01,
			configlist02 : row.configlist02,
			fmcustomerid : row.fmcustomerid,
			fmid : row.fmid,
			fmlocation : row.fmlocation,
			fmlotnum : row.fmlotnum,
			fmqty : row.fmqty,
			fmsku : row.fmsku,
			fmuomName : row.fmuomName,
			iMv : row.iMv,
			iPa : row.iPa,
			imageaddress : row.imageaddress,
			lotatt01 : row.lotatt01,
			lotatt01text : row.lotatt01text,
			lotatt02 : row.lotatt02,
			lotatt02text : row.lotatt02text,
			lotatt03 : row.lotatt03,
			lotatt03text : row.lotatt03text,
			lotatt04 : row.lotatt04,
			lotatt04text : row.lotatt04text,
			lotatt05 : row.lotatt05,
			lotatt05text : row.lotatt05text,
			lotatt06 : row.lotatt06,
			lotatt06text : row.lotatt06text,
			lotatt07 : row.lotatt07,
			lotatt07text : row.lotatt07text,
			lotatt08 : row.lotatt08,
			lotatt08text : row.lotatt08text,
			lotatt09 : row.lotatt09,
			lotatt09text : row.lotatt09text,
			lotatt10 : row.lotatt10,
			lotatt10text : row.lotatt10text,
			lotatt11 : row.lotatt11,
			lotatt11text : row.lotatt11text,
			lotatt12 : row.lotatt12,
			lotatt12text : row.lotatt12text,
			lpn : row.lpn,
			netweight : row.netweight,
			oMv : row.oMv,
			pkey : row.pkey,
			price : row.price,
			qtyallocated : row.qtyallocated,
			qtyavailed : row.qtyavailed,
			qtyholded : row.qtyholded,
			qtyrpin : row.qtyrpin,
			qtyrpout : row.qtyrpout,
			reservedfield01 : row.reservedfield01,
			reservedfield02 : row.reservedfield02,
			reservedfield03 : row.reservedfield03,
			reservedfield04 : row.reservedfield04,
			reservedfield05 : row.reservedfield05,
			rotationid : row.rotationid,
			skudescrc : row.skudescrc,
			skudescre : row.skudescre,
			skugroup1 : row.skugroup1,
			skugroup2 : row.skugroup2,
			skugroup3 : row.skugroup3,
			skugroup4 : row.skugroup4,
			skugroup5 : row.skugroup5,
			softallocationrule : row.softallocationrule,
			toadjqty : row.toadjqty,
			totalcubic : row.totalcubic,
			totalgrossweight : row.totalgrossweight,
			uom : row.uom,
			warehouseid : row.warehouseid
		});
		ezuiDialogMov.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

var hold = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiFormHold.form('load',{
			allocationrule : row.allocationrule,
			alternatesku1 : row.alternatesku1,
			alternatesku2 : row.alternatesku2,
			alternatesku3 : row.alternatesku3,
			alternatesku4 : row.alternatesku4,
			alternatesku5 : row.alternatesku5,
			configlist01 : row.configlist01,
			configlist02 : row.configlist02,
			fmcustomerid : row.fmcustomerid,
			fmid : row.fmid,
			fmlocation : row.fmlocation,
			fmlotnum : row.fmlotnum,
			fmqty : row.fmqty,
			fmsku : row.fmsku,
			fmuomName : row.fmuomName,
			iMv : row.iMv,
			iPa : row.iPa,
			imageaddress : row.imageaddress,
			lotatt01 : row.lotatt01,
			lotatt01text : row.lotatt01text,
			lotatt02 : row.lotatt02,
			lotatt02text : row.lotatt02text,
			lotatt03 : row.lotatt03,
			lotatt03text : row.lotatt03text,
			lotatt04 : row.lotatt04,
			lotatt04text : row.lotatt04text,
			lotatt05 : row.lotatt05,
			lotatt05text : row.lotatt05text,
			lotatt06 : row.lotatt06,
			lotatt06text : row.lotatt06text,
			lotatt07 : row.lotatt07,
			lotatt07text : row.lotatt07text,
			lotatt08 : row.lotatt08,
			lotatt08text : row.lotatt08text,
			lotatt09 : row.lotatt09,
			lotatt09text : row.lotatt09text,
			lotatt10 : row.lotatt10,
			lotatt10text : row.lotatt10text,
			lotatt11 : row.lotatt11,
			lotatt11text : row.lotatt11text,
			lotatt12 : row.lotatt12,
			lotatt12text : row.lotatt12text,
			lpn : row.lpn,
			netweight : row.netweight,
			oMv : row.oMv,
			pkey : row.pkey,
			price : row.price,
			qtyallocated : row.qtyallocated,
			qtyavailed : row.qtyavailed,
			qtyholded : row.qtyholded,
			qtyrpin : row.qtyrpin,
			qtyrpout : row.qtyrpout,
			reservedfield01 : row.reservedfield01,
			reservedfield02 : row.reservedfield02,
			reservedfield03 : row.reservedfield03,
			reservedfield04 : row.reservedfield04,
			reservedfield05 : row.reservedfield05,
			rotationid : row.rotationid,
			skudescrc : row.skudescrc,
			skudescre : row.skudescre,
			skugroup1 : row.skugroup1,
			skugroup2 : row.skugroup2,
			skugroup3 : row.skugroup3,
			skugroup4 : row.skugroup4,
			skugroup5 : row.skugroup5,
			softallocationrule : row.softallocationrule,
			toadjqty : row.toadjqty,
			totalcubic : row.totalcubic,
			totalgrossweight : row.totalgrossweight,
			uom : row.uom,
			warehouseid : row.warehouseid
		});
		ezuiDialogHold.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

var commitAdj = function(){
	url = '<c:url value="/viewInvLotattController.do?adj"/>';
	ezuiFormAdj.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiFormAdj.form('validate')){
				$.messager.progress({
					text : '<spring:message code="common.message.data.processing"/>', interval : 100
				});
				return true;
			}else{
				return false;
			}
		},
		success : function(data) {
			var msg='';
			try {
				var result = $.parseJSON(data);
				if(result.success){
					msg = result.msg;
					ezuiDatagrid.datagrid('reload');
					ezuiDialogAdj.dialog('close');
				}else{
					msg = '<font color="red">' + result.msg + '</font>';
				}
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			}
		}
	});
};

var commitMov = function(){
	url = '<c:url value="/viewInvLotattController.do?mov"/>';
	ezuiFormMov.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiFormMov.form('validate')){
				$.messager.progress({
					text : '<spring:message code="common.message.data.processing"/>', interval : 100
				});
				return true;
			}else{
				return false;
			}
		},
		success : function(data) {
			var msg='';
			try {
				var result = $.parseJSON(data);
				if(result.success){
					msg = result.msg;
					ezuiDatagrid.datagrid('reload');
					ezuiDialogMov.dialog('close');
				}else{
					msg = '<font color="red">' + result.msg + '</font>';
				}
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			}
		}
	});
};

var commitHold = function(){
	url = '<c:url value="/viewInvLotattController.do?hold"/>';
	ezuiFormHold.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiFormHold.form('validate')){
				$.messager.progress({
					text : '<spring:message code="common.message.data.processing"/>', interval : 100
				});
				return true;
			}else{
				return false;
			}
		},
		success : function(data) {
			var msg='';
			try {
				var result = $.parseJSON(data);
				if(result.success){
					msg = result.msg;
					ezuiDatagrid.datagrid('reload');
					ezuiDialogHold.dialog('close');
				}else{
					msg = '<font color="red">' + result.msg + '</font>';
				}
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			}
		}
	});
};

var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		fmcustomerid : $('#fmcustomerid').val(),
		fmlocation : $('#fmlocation').val(),
		fmsku : $('#fmsku').val(),
		lotatt01 : $('#lotatt01').datebox("getValue"),   
		lotatt01text : $('#lotatt01text').datebox("getValue"),
		lotatt02 : $('#lotatt02').datebox("getValue"),
		lotatt02text : $('#lotatt02text').datebox("getValue"),
		lotatt03 : $('#lotatt03').datebox("getValue"),
		lotatt03text : $('#lotatt03text').datebox("getValue"),
		lotatt04 : $('#lotatt04').combobox('getValue'),
		lotatt05 : $('#lotatt05').val(),
		lotatt06 : $('#lotatt06').val(),
	});
};
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>客户编码</th><td><input type='text' id='fmcustomerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>库位</th><td><input type='text' id='fmlocation' name='fmlocation' class='easyui-textbox' size='16' data-options=''/></td>
							<th>商品编码</th><td><input type='text' id='fmsku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>商品名称</th><td><input type='text' id='skudescrc' class='easyui-textbox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
							</td>
						</tr>
						<tr>
							<th>生产日期</th><td><input type='text' id='lotatt01' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>至</th><td><input type='text' id='lotatt01text' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
						</tr>
						<tr>
							<th>失效日期</th><td><input type='text' id='lotatt02' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>至</th><td><input type='text' id='lotatt02text' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
						</tr>
						<tr>
							<th>入库日期</th><td><input type='text' id='lotatt03' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>至</th><td><input type='text' id='lotatt03text' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
						</tr>
						<tr>	
							<th>库存状态</th><td><input type='text' id='lotatt04' class='easyui-combobox' size='16' data-options="required:false,panelHeight:'auto',
																																editable:true,
																																valueField: 'id',
																																textField: 'value',
																																data: [
																																{id: 'HG', value: '合格'}, 
																																{id: 'CC', value: '残次'}, 
																															]"/></td>
							<th>批属5</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
							<th>批属6</th><td><input type='text' id='lotatt06' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='adj();' id='ezuiBtn_adj' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.adj'/></a>
					<a onclick='mov();' id='ezuiBtn_mov' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.mov'/></a>
					<a onclick='hold();' id='ezuiBtn_hold' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.hold'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
<%-- 					<a onclick='adj();' id='ezuiBtn_importAdj' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.adjBatch'/></a> --%>
<%-- 					<a onclick='mov();' id='ezuiBtn_importMov' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.movBatch'/></a> --%>
<%-- 					<a onclick='hold();' id='ezuiBtn_importHold' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.holdBatch'/></a> --%>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialogAdj' style='padding: 10px;'>
		<form id='ezuiFormAdj' method='post'>
			<input type='hidden' id='viewInvLotattId' name='viewInvLotattId'/>
			<table>
				<tr>
					<th>仓库编码</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>货主</th>
					<td><input type='text' name='fmcustomerid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>商品编码</th>
					<td><input type='text' name='fmsku' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>商品名称</th>
					<td><input type='text' name='skudescrc' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>库位</th>
					<td><input type='text' name='fmlocation' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>跟踪号</th>
					<td><input type='text' name='fmid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>批号</th>
					<td><input type='text' name='fmlotnum' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>原数量</th>
					<td><input type='text' name='fmqty' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>可用数量</th>
					<td><input type='text' name='qtyavailed' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>冻结数量</th>
					<td><input type='text' name='qtyholded' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>目标数量</th>
					<td><input type='text' name='lotatt11' class='easyui-numberbox' size='16' data-options='required:true,min:0'/></td>
					<th>调整原因</th>
					<td><input type='text' name='lotatt12' class='easyui-combobox' size='16' data-options="required:true,panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'CL', value: '盘亏'}, 
																											{id: 'OK', value: '正常'}, 
																											{id: 'SP', value: '异常'},
																											{id: 'KT', value: '加工损耗'},
																											{id: 'CS', value: '残损'}
																										]"/></td>
					<th>原因描述</th>
					<td><input type='text' name='lotatt12text' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogAdjBtn'>
		<a onclick='commitAdj();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialogAdj");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiDialogMov' style='padding: 10px;'>
		<form id='ezuiFormMov' method='post'>
			<input type='hidden' id='viewInvLotattId' name='viewInvLotattId'/>
			<table>
				<tr>
					<th>仓库编码</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>货主</th>
					<td><input type='text' name='fmcustomerid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>商品编码</th>
					<td><input type='text' name='fmsku' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>商品名称</th>
					<td><input type='text' name='skudescrc' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>数量</th>
					<td><input type='text' name='fmqty' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>可用数量</th>
					<td><input type='text' name='qtyavailed' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>冻结数量</th>
					<td><input type='text' name='qtyholded' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>原库位</th>
					<td><input type='text' name='fmlocation' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>跟踪号</th>
					<td><input type='text' name='fmid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>批号</th>
					<td><input type='text' name='fmlotnum' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>目标库位</th>
					<td><input type='text' name='lotatt11text' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>移动数量</th>
					<td><input type='text' name='lotatt11' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>移动原因</th>
					<td><input type='text' name='lotatt12' class='easyui-combobox' size='16' data-options="required:true,panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'OK', value: '正常'}, 
																											{id: 'SP', value: '异常'},
																											{id: 'CS', value: '残损'}
																										]"/></td>
					<th>原因描述</th>
					<td><input type='text' name='lotatt12text' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogMovBtn'>
		<a onclick='commitMov();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialogMov");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiDialogHold' style='padding: 10px;'>
		<form id='ezuiFormHold' method='post'>
			<input type='hidden' id='viewInvLotattId' name='viewInvLotattId'/>
			<table>
				<tr>
					<th>仓库编码</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>货主</th>
					<td><input type='text' name='fmcustomerid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>商品编码</th>
					<td><input type='text' name='fmsku' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>商品名称</th>
					<td><input type='text' name='skudescrc' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>库位</th>
					<td><input type='text' name='fmlocation' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>跟踪号</th>
					<td><input type='text' name='fmid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>批号</th>
					<td><input type='text' name='fmlotnum' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>数量</th>
					<td><input type='text' name='fmqty' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>可用数量</th>
					<td><input type='text' name='qtyavailed' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>冻结数量</th>
					<td><input type='text' name='qtyholded' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>冻结原因</th>
					<td><input type='text' name='lotatt12' class='easyui-combobox' size='16' data-options="required:true,panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'OK', value: '正常'}, 
																											{id: 'SP', value: '异常'},
																											{id: 'CS', value: '残损'}
																										]"/></td>
					<th>原因描述</th>
					<td><input type='text' name='lotatt12text' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogHoldBtn'>
		<a onclick='commitHold();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialogHold");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
	
	<!-- 客户选择弹框 -->
	<div id='ezuiCustDataDialog'  style="width:700px;height:480px;padding:10px 20px"   >
	<div class='easyui-layout' data-options='fit:true,border:false'>
	<div data-options="region:'center'">
		<div id='ezuiCustToolbar' class='datagrid-toolbar'   style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>客户：</th><td>
								<input type='text' id='customerid' name="customerid" class='easyui-textbox'  size='12' data-options='prompt:"请输入客户代码"'/></td>
								<th>类型：</th><td>
								<input type='text' id='customerType' name="customerType" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
								<th>激活：</th><td>
								<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'Y', value: '是'}, 
																																{id: 'N', value: '否'}
																															]"/></td>
								<td>
									<a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
	<div id='ezuiCustDialogBtn'> </div>
	</div>
		<table id='ezuiCustDataDialogId' ></table> 
	</div>
	</div>
	</div>
	<div id='ezuiCustDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
</body>
</html>
