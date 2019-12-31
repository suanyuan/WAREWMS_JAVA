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
var ezuiDatagrid;       //主页datagrid

var ezuiDialogMov;     //库存移动
var ezuiFormMov;       //库存移动
var ezuiDialogMovAll;     //库存移动 多条
var ezuiFormMovAll;       //库存移动 多条

var ezuiDialogAdj;     //库存调整
var ezuiFormAdj;       //库存调整

var ezuiFormHold;      //库存冻结
var ezuiDialogHold;    //库存冻结

var ezuiCustDataDialog;      //客户编码dialog
var ezuiCustDataDialogId;     //客户编码dialog
var ezuiLocDataDialog;         //库位选择框dialog
var ezuiLocDataDialogId;       //库位选择框dialog
var ezuiSkuDataDialog;         //产品名称选择框
var ezuiSkuDataDialogId;       //产品名称选择框
var productDialog_viewInvLotatt;//主页产品代码选择框

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();            //右键菜单
	ezuiFormAdj = $('#ezuiFormAdj').form();      //库存调整
	ezuiFormMov = $('#ezuiFormMov').form();      //库存移动
	ezuiFormMovAll = $('#ezuiFormMovAll').form();//库存移动 多条
	ezuiFormHold = $('#ezuiFormHold').form();    //库存冻结
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
		singleSelect:false,
		showFooter:true,
		columns : [[
			{field:'ck',checkbox:true},
			{field: 'fmlocation', title: '库位', width: 100},
// 			{field: 'pkey',		title: 'No.',	width: 41 },        
			{field: 'fmcustomerid',		title: '货主',	width: 71 },
			{field: 'fmsku',		title: '产品代码',	width: 150 },
			{field: 'lotatt12', title: '产品名称', width: 250},
			{field: 'lotatt04', title: '生产批号', width: 110},
			{field: 'lotatt05', title: '序列号', width: 110},
			{field: 'fmqty',		title: '库存件数',	width: 71 },
			{field: 'fmqtyEach',		title: '库存数量',	width: 71 },
			{field: 'qtyallocated',		title: '分配件数',	width: 71 },
			{field: 'qtyallocatedEach',		title: '分配数量',	width: 71 },
			{field: 'qtyavailed',		title: '可用件数',	width: 71 },
			{field: 'qtyavailedEach',		title: '可用数量',	width: 71 },
			// {field: 'qtyholded',		title: '冻结件数',	width: 71 },
			// {field: 'qtyholdedEach',		title: '冻结数量',	width: 71 },
			{field: 'totalgrossweight',		title: '毛重',	width: 71 },
			{field: 'totalcubic',		title: '体积',	width: 71 },

			{field: 'uom',		title: '单位',	width: 71 },
			{field: 'fmlotnum',		title: '批次',	width: 81 },

			{field: 'lotatt06', title: '注册证号', width: 150},//加个字段
			{field: 'skudescre', title: '规格型号', width: 103},
			{field: 'lotatt07', title: '灭菌批号', width: 120},
			{field: 'lotatt03', title: '入库日期', width: 91},
			{field: 'lotatt01', title: '生产日期', width: 112},
			{field: 'lotatt02', title: '有效期/失效期', width: 113},
			{field: 'lotatt08', title: '供应商', width: 120},
			{field: 'lotatt09', title: '样品属性', width: 100,formatter:YP_TYPstatusFormatter},
			{field: 'lotatt11', title: '存储条件', width: 100},
			{field: 'lotatt10', title: '质量状态', width: 100, formatter:ZL_TYPstatusFormatter},
			{field: 'onholdlocker', title: '冻结状态', width: 100, formatter:holdStatusFormatter},

			// {field: 'fmid',		title: '跟踪号',	width: 71 },
			// {field: 'lpn',		title: 'LPN',	width: 71 },
			// {field: 'price',		title: '价值',	width: 71 },
			{field: 'warehouseid',		title: '仓库编码',	width: 71 }
		]],
		onDblClickCell: function(index,field,value){
			edit();
		},
		 onLoadSuccess:function(data){
			 ezuiDatagrid.datagrid('reloadFooter',[
				 {lotatt12:'总计:',fmqty:data.rows[0].fmqtySum,fmqtyEach:data.rows[0].fmqtyEachSum,
					 qtyallocated:data.rows[0].qtyallocatedSum,qtyallocatedEach:data.rows[0].qtyallocatedEachSum,
					 qtyavailed:data.rows[0].qtyavailedSum,qtyavailedEach:data.rows[0].qtyavailedEachSum,
					 qtyholded:0, qtyholdedEach:0}
			 ]);

			 ajaxBtn($('#menuId').val(), '<c:url value="/viewInvLotattController.do?getBtn"/>', ezuiMenu);
			 $(this).datagrid('unselectAll');
 		}
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
//产品代码控件初始化 载入公用弹窗页面
	$("#toolbar #fmsku").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				productDialog_viewInvLotatt = $('#ezuiSkuSearchDialog').dialog({
					modal: true,
					title: '查询',
					href: sy.bp() + "/basSkuController.do?toSearchDialog&target=viewInvLotatt",
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
	$("#toolbar #skudescrc").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiSkuDataDialog #sku").textbox('clear');
				ezuiSkuDataClick();
				ezuiSkuDataSearch();
			}
		}]
	});
//主页库位选择
	$("#toolbar #fmlocation").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiLocDataDialog #locationid").textbox('clear');
				ezuiLocDataClick('S');
				ezuiLocDataSearch();
			}
		}]
	});
//库位移动多条页放大镜
	$("#ezuiDialogMovAll #lotatt11text").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiLocDataDialog #locationid").textbox('clear');
				ezuiLocDataClick('M');
				ezuiLocDataSearch();
			}
		}]
	});
//库位移动单条页面放大镜
	$("#ezuiDialogMov #lotatt11text").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiLocDataDialog #locationid").textbox('clear');
				ezuiLocDataClick('O');
				ezuiLocDataSearch();
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
//库位选择弹框初始化
	ezuiLocDataDialog = $('#ezuiLocDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//库存调整初始化
	ezuiDialogAdj = $('#ezuiDialogAdj').dialog({
		modal : true,
		title : '库存调整',
		buttons : '#ezuiDialogAdjBtn',
		onClose : function() {
			ezuiFormClear(ezuiFormAdj);
		}
	}).dialog('close');
//库存移动初始化
	ezuiDialogMov = $('#ezuiDialogMov').dialog({
		modal : true,
		title : '单条库存移动',
		buttons : '#ezuiDialogMovBtn',
		onClose : function() {
			ezuiFormClear(ezuiFormMov);
		}
	}).dialog('close');
//库存多条移动页面初始化
	ezuiDialogMovAll = $('#ezuiDialogMovAll').dialog({
		modal : true,
		title : '多条库存移动',
		buttons : '#ezuiDialogMovAllBtn',
		onClose : function() {
			ezuiFormClear(ezuiFormMovAll);
		}
	}).dialog('close');
//库存冻结初始化
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
	url : '<c:url value="/basCustomerController.do?showDatagrid&activeFlag=1&customerType=OW"/>',
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
					return rowData.activeFlag == '1' ? '是' : '否';
	            }}
			]],
	onDblClickCell: function(index,field,value){
		selectCust();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
// 			ajaxBtn($('#menuId').val(), '<c:url value="/viewInvLotattController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combobox('setText','是');
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
		param.put("lotatt04", $('#lotatt04').val());
		param.put("lotatt10", $('#lotatt10').combobox('getValue'));
		param.put("lotatt05", $('#lotatt05').val());
		param.put("lotatt06", $('#lotatt06').val());
		param.put("lotatt12", $('#skudescrc').val());
		param.put("onholdlocker", $('#onholdlocker').combobox('getValue'));

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

//库存调整
var adj = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var num=rows.length;
	var locs=new Map();
//设置库位不可调整
	if(num==1){
		for (var i = 0; i < rows.length; i++) {
			var loc=rows[i].fmlocation;
			if(loc=='SORTATION01'||loc=='STAGE01'||loc=='YY-01-01-01'||loc=='DX-01-01-01'){
				locs.set(loc,"");
			}

		}
		if(locs.size>0){
			var result="";
			for(var key of locs.keys()){
				result+=key+",";
			}
			$.messager.show({
				msg :result+"不可调整", title : '<spring:message code="common.message.prompt"/>'
			});
			return;
		}
	}

	//单条调整
	if(num==1){
		//待检产品库位不可移动
		if(rows[0].lotatt10=="DJ"){
			$.messager.show({
				msg : '产品质量状态为待检不可调整!', title : '<spring:message code="common.message.prompt"/>'
			});
			return;
		}
		ezuiFormAdj.form('load',{

			fmcustomerid : rows[0].fmcustomerid,
			lotatt12Show:rows[0].lotatt12,
			fmlocation : rows[0].fmlocation,
			fmlotnum : rows[0].fmlotnum,
			fmqty : rows[0].fmqty,
			fmsku : rows[0].fmsku,
			qtyallocated : rows[0].qtyallocated,
			qtyavailed : rows[0].qtyavailed,
			qtyholded : rows[0].qtyholded,
			skudescrc:rows[0].skudescrc,
			lotatt04:rows[0].lotatt04,
			lotatt05:rows[0].lotatt05

		});
		ezuiDialogAdj.dialog('open');

	}else if(num>1) {
		$.messager.show({
			msg : '库位调整请单条操作!', title : '<spring:message code="common.message.prompt"/>'
		});

	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//库存移动
var mov = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var num=rows.length;
	var locs=new Map();

//冻结库位不可移动
//待检产品库位不可移动
//设置库位不可移出
	if(num>0){
		for (var i = 0; i < rows.length; i++) {
			var onholdlocker=rows[i].onholdlocker;
			var lotatt10=rows[i].lotatt10;
			if(lotatt10=='DJ'||onholdlocker=='99'){
				$.messager.show({
					msg :"库位状态为[库存冻结]或产品质量状态为[待检]不可调整!", title : '<spring:message code="common.message.prompt"/>'
				});
				return;
			}

		}
		for (var i = 0; i < rows.length; i++) {
			var loc=rows[i].fmlocation;
			if(loc=='SORTATION01'){
				locs.set(loc,"");
			}

		}

	}
	if(locs.size>0){
		 var result="";
		for(var key of locs.keys()){
			result+=key+",";
		}
		$.messager.show({
			msg :result+"库位不可移动", title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
	//单条移动
	if(num==1){
		ezuiFormMov.form('load',{

			fmcustomerid : rows[0].fmcustomerid,
			lotatt12Show:rows[0].lotatt12,
			fmlocation : rows[0].fmlocation,
			fmlotnum : rows[0].fmlotnum,
			lotatt01 : rows[0].lotatt01,
			lotatt02 : rows[0].lotatt02,
			lotatt04 : rows[0].lotatt04,
			fmqty : rows[0].fmqty,
			fmsku : rows[0].fmsku,
			qtyallocated : rows[0].qtyallocated,
			qtyavailed : rows[0].qtyavailed,
			qtyholded : rows[0].qtyholded,
			skudescrc:rows[0].skudescrc,

		});
		ezuiDialogMov.dialog('open');
	//	多条移动
	}else if(num>1) {
		var fmlocationString=new String();
		var fmqtySum=0;
		for (var i = 0; i < rows.length; i++) {
			fmlocationString+=rows[i].fmlocation+",";
			fmqtySum +=rows[i].fmqty;
		}
		ezuiFormMovAll.form('load',{


			// fmcustomerid : rows[0].fmcustomerid,
			// fmid : rows[0].fmid,
			 fmlocation : fmlocationString,
			// fmlotnum : rows[0].fmlotnum,
			 fmqty :fmqtySum,
			lotatt11:fmqtySum
			// fmsku : rows[0].fmsku,
			// qtyallocated : rows[0].qtyallocated,
			// qtyavailed : rows[0].qtyavailed,
			// qtyholded : rows[0].qtyholded,
			// warehouseid : rows[0].warehouseid,
			// skudescrc:rows[0].skudescrc
		});
		ezuiDialogMovAll.dialog('open');

	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//库存冻结
var hold = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var forms=[];
	var data=null;
	var msg='';
	url = '<c:url value="/viewInvLotattController.do?hold"/>';
	if(rows.length>0){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '是否冻结选择的项目?', function(confirm) {
			if(confirm){
				$.messager.progress({
					text: '<spring:message code="common.message.data.processing"/>', interval: 100
				});
				for (var i = 0; i < rows.length; i++) {
					data=new Object();
					data.customerid=rows[i].fmcustomerid;
					data.sku=rows[i].fmsku;
					data.lotnum=rows[i].fmlotnum;
					data.locationid=rows[i].fmlocation;
					forms.push(data);

				}

				$.ajax({
					url :url,
					data:"forms="+JSON.stringify(forms),
					type : 'POST',
					dataType : 'JSON',
					success: function (result) {
						try{
							if(result.success){
								msg=result.msg;
								ezuiDatagrid.datagrid('reload');
								$.messager.show({
									msg:msg, title : '<spring:message code="common.message.prompt"/>'
								});
								$.messager.progress('close');
							}else{
								msg=result.msg;
								ezuiDatagrid.datagrid('reload');
								$.messager.show({
									msg :msg, title : '<spring:message code="common.message.prompt"/>'
								});
								$.messager.progress('close');
							}
						}catch (e) {
							$.messager.show({
								msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
						}
					}
				});
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//库存解冻
var nohold = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var forms=[];
	var data=null;
	var msg='';
	url = '<c:url value="/viewInvLotattController.do?nohold"/>';
	if(rows.length>0){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '是否解冻选择的项目?', function(confirm) {
			if(confirm){
				$.messager.progress({
					text: '<spring:message code="common.message.data.processing"/>', interval: 100
				});
				for (var i = 0; i < rows.length; i++) {
					data=new Object();
					data.customerid=rows[i].fmcustomerid;
					data.sku=rows[i].fmsku;
					data.lotnum=rows[i].fmlotnum;
					data.locationid=rows[i].fmlocation;
					forms.push(data);

				}

				$.ajax({
					url :url,
					data:"forms="+JSON.stringify(forms),
					type : 'POST',
					dataType : 'JSON',
					success: function (result) {
						try{
							if(result.success){
								msg=result.msg;
								ezuiDatagrid.datagrid('reload');
								$.messager.show({
									msg:msg, title : '<spring:message code="common.message.prompt"/>'
								});
								$.messager.progress('close');
							}else{
								msg=result.msg;
								ezuiDatagrid.datagrid('reload');
								$.messager.show({
									msg :msg, title : '<spring:message code="common.message.prompt"/>'
								});
								$.messager.progress('close');
							}
						}catch (e) {
							$.messager.show({
								msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
						}
					}
				});
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//库存调整提交
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
//库存移动提交
var commitMov = function(){
	url = '<c:url value="/viewInvLotattController.do?mov"/>';
	//判断目标库位是否可以移动
	var location=$('#ezuiDialogMov #lotatt11text').val();
	if(!ismove(location)){
		$.messager.show({
			msg :'此库位不可移动', title : '<spring:message code="common.message.prompt"/>'
		});
		$.messager.progress('close');
		return;
	}
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
//库存移动多条提交
var commitMovAll = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var forms=[];
	var data=null;
	var msg='';
	url = '<c:url value="/viewInvLotattController.do?movList"/>';
	//判断目标库位是否可以移动
	var location=$('#ezuiDialogMovAll #lotatt11text').val();
      if(!ismove(location)){
		  $.messager.show({
			  msg :'此库位不可移动', title : '<spring:message code="common.message.prompt"/>'
		  });
		  $.messager.progress('close');
      	return;
	  }
	for (var i = 0; i < rows.length; i++) {
		data=new Object();
		data.fmcustomerid=rows[i].fmcustomerid;
		data.fmsku=rows[i].fmsku;
		data.fmlotnum=rows[i].fmlotnum;
		data.fmlocation=rows[i].fmlocation;
		data.fmid=rows[i].fmid;
		data.fmqty=rows[i].fmqty;
		data.lotatt11=rows[i].qtyavailed;
		data.lotatt11text=$('#ezuiDialogMovAll #lotatt11text').val();
		data.lotatt12=$('#ezuiDialogMovAll #lotatt12').combobox("getValue");
		data.lotatt12text=$('#ezuiDialogMovAll #lotatt12text').val();
		forms.push(data);

	}

	if(ezuiFormMovAll.form('validate')) {
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});

			$.ajax({
				url: url,
				data:"forms="+JSON.stringify(forms),
				dataType: 'json',
				error: function (a,b,c) {
					//alert(a+b+c);
				},
				success: function (result) {
					try{
					  if(result.success){
					  	  msg=result.msg;
						  ezuiDatagrid.datagrid('reload');
						  ezuiDialogMovAll.dialog('close');
						  $.messager.show({
							  msg:msg, title : '<spring:message code="common.message.prompt"/>'
						  });
						  $.messager.progress('close');
					  }else{
						  msg=result.msg;
						  ezuiDatagrid.datagrid('reload');
						  ezuiDialogMovAll.dialog('close');
						  $.messager.show({
							  msg :msg, title : '<spring:message code="common.message.prompt"/>'
						  });
						  $.messager.progress('close');
					  }
					}catch (e) {
						$.messager.show({
							msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
						});
						$.messager.progress('close');
					}
				}
			});

	}else{
		msg = '<font color="red">' +'请输入完整!'+ '</font>';
		$.messager.show({
				msg : msg, title : '<spring:message code="common.message.prompt"/>'
						});
		$.messager.progress('close');

	}
	<%--	ezuiFormMov.form('submit', {--%>
	<%--		onSubmit : function(){--%>
	<%--			if(ezuiFormMov.form('validate')){--%>
	<%--				$.messager.progress({--%>
	<%--					text : '<spring:message code="common.message.data.processing"/>', interval : 100--%>
	<%--				});--%>
	<%--				return true;--%>
	<%--			}else{--%>
	<%--				return false;--%>
	<%--			}--%>
	<%--		},--%>
	<%--		success : function(data) {--%>
	<%--			var msg='';--%>
	<%--			try {--%>
	<%--				var result = $.parseJSON(data);--%>
	<%--				if(result.success){--%>
	<%--					msg = result.msg;--%>
	<%--					ezuiDatagrid.datagrid('reload');--%>
	<%--					ezuiDialogMov.dialog('close');--%>
	<%--				}else{--%>
	<%--					msg = '<font color="red">' + result.msg + '</font>';--%>
	<%--				}--%>
	<%--			} catch (e) {--%>
	<%--				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';--%>
	<%--				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;--%>
	<%--			} finally {--%>
	<%--				$.messager.show({--%>
	<%--					msg : msg, title : '<spring:message code="common.message.prompt"/>'--%>
	<%--				});--%>
	<%--				$.messager.progress('close');--%>
	<%--			}--%>
	<%--		}--%>
	<%--	});--%>



};
//库存冻结提交
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
//主页查询
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
		lotatt04 : $('#lotatt04').textbox("getValue"),
		lotatt10 : $('#lotatt10').combobox('getValue'),
		lotatt05 : $('#lotatt05').val(),
		lotatt06 : $('#lotatt06').val(),
		lotatt12 : $('#skudescrc').val(),
		onholdlocker : $('#onholdlocker').combobox('getValue'),
	});
};
/* 库位选择弹框查询 */
var ezuiLocDataSearch = function () {
	ezuiLocDataDialogId.datagrid('load', {
		locationid: $("#ezuiLocDataDialog #locationid").textbox("getValue"),
		locationcategory: $("#ezuiLocDataDialog #locationcategory").combobox("getValue")

	});
};
/* 库位选择弹框清空 */
var ezuiLocToolbarClear = function () {
	$("#ezuiLocDataDialog #locationid").textbox('clear');
	$("#ezuiLocDataDialog #locationcategory").combobox('clear');
};
/* 库位选择弹框 */
var ezuiLocDataClick = function (type) {
	ezuiLocDataDialogId = $('#ezuiLocDataDialogId').datagrid({
		url: '<c:url value="/basLocationController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiLocToolbar',
		title: '库位选择',
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
		idField: 'locationid',
		columns: [[
			{field: 'locationid', title: '库位', width: 80},
			{field: 'locationusageName', title: '库位使用', width: 100},
			{field: 'locationcategoryName', title: '库位类型', width: 100},
			{field: 'locationattributeName', title: '库位属性', width: 100},
			{field: 'putawayzoneName', title: '上架区', width: 100},
			{field: 'pickzoneName', title: '拣货区', width: 100}
		]],
		onDblClickCell: function (index, field, value) {
			selectLocation(type);
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	ezuiLocDataDialog.dialog('open');
};
/* 库位选择 */
var selectLocation = function (type) {
	var row = ezuiLocDataDialogId.datagrid('getSelected');
	if (row) {
		if(type=='O'){
		   $("#ezuiDialogMov #lotatt11text").textbox('setValue', row.locationid);
		    ezuiLocDataDialog.dialog('close');
		}else if(type=='M'){
			$("#ezuiDialogMovAll #lotatt11text").textbox('setValue', row.locationid);
			ezuiLocDataDialog.dialog('close');
		}else{
			$("#toolbar #fmlocation").textbox('setValue', row.locationid);
			ezuiLocDataDialog.dialog('close');
		}
	}
	;
};
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
		$("#toolbar #skudescrc").textbox('setValue', row.reservedfield01);
		ezuiSkuDataDialog.dialog('close');
	}
};

// 主页产品代码框选择
function choseSelect_product_viewInvLotatt(row) {
	var sku;
	if (row) {
		sku = row;
	} else {
		row = $("#productSearchGrid_viewInvLotatt").datagrid("getSelections");
		if (row) {
			sku = row[0]
		}
	}
	if (sku) {
		$("#toolbar #fmsku").textbox("setValue", sku.sku);
	}
	productDialog_viewInvLotatt.dialog("close");
}
//判断库位可否移动
var ismove=function (location) {
    var con=true;
	if(location=='DX-01-01-01'||location=='YY-01-01-01'||location=='STAGE01'||location=='SORTATION01'){
		con=false;
	}
   return con;
}
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<%--主页datagrid--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>货主编码</th><td><input type='text' id='fmcustomerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>库位</th><td><input type='text' id='fmlocation' name='fmlocation' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品代码</th><td><input type='text' id='fmsku' class='easyui-textbox' size='16' data-options=''/></td>

						</tr>
						<tr>
							<th>生产日期</th><td><input type='text' id='lotatt01' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>至</th><td><input type='text' id='lotatt01text' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>产品名称</th><td><input type='text' id='skudescrc' class='easyui-textbox' size='16' data-options=''/></td>

						</tr>
						<tr>
							<th>效期</th><td><input type='text' id='lotatt02' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>至</th><td><input type='text' id='lotatt02text' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>质量状态</th><td><input type='text' id='lotatt10' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?qcState"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>

						</tr>
						<tr>
							<th>入库日期</th><td><input type='text' id='lotatt03' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>至</th><td><input type='text' id='lotatt03text' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
							<th>注册证号/备案凭证号</th><td><input type='text' id='lotatt06' class='easyui-textbox' size='16' data-options=''/></td>


						</tr>
						<tr>
							<th>序列号</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产批号</th>
							<td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>冻结状态</th>
							<td><input type='text' id='onholdlocker' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	valueField: 'id',
																																     textField: 'value',
																																     data: [{
			                                                                                                                           id: '99',
			                                                                                                                           value: '库存冻结'
		                                                                                                                          },{
			                                                                                                                           id: '0',
			                                                                                                                           value: '库存解冻'
		                                                                                                                          }]"/></td>

							<td colspan="2">
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='adj();' id='ezuiBtn_adj' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.adj'/></a>
					<a onclick='mov();' id='ezuiBtn_mov' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.mov'/></a>
  			        <a onclick='hold();' id='ezuiBtn_hold' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.hold'/></a>
  			        <a onclick='nohold();' id='ezuiBtn_nohold' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>库存解冻</a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
<%-- 					<a onclick='adj();' id='ezuiBtn_importAdj' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.adjBatch'/></a> --%>
<%-- 					<a onclick='mov();' id='ezuiBtn_importMov' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.movBatch'/></a> --%>
<%-- 					<a onclick='hold();' id='ezuiBtn_importHold' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.holdBatch'/></a> --%>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
<%--库存调整页面--%>
	<div id='ezuiDialogAdj' style='padding: 10px;'>
		<form id='ezuiFormAdj' method='post'>
			<input type='hidden' id='viewInvLotattId' name='viewInvLotattId'/>
			<table>
				<tr>
					<th>库位</th>
					<td><input type='text' name='fmlocation' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>货主</th>
					<td><input type='text' name='fmcustomerid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>

				</tr>
				<tr>

					<th>库存件数</th>
					<td><input type='text' name='fmqty' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>产品代码</th>
					<td><input type='text' name='fmsku' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>分配件数</th>
					<td><input type='text' name='qtyallocated' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>产品名称</th>
					<td><input type='text' name='lotatt12Show' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>


				</tr>
				<tr>
					<th>可用件数</th>
					<td><input type='text' name='qtyavailed' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>生产批号</th>
					<td><input type='text' name='lotatt04' class='easyui-textbox' size='16' data-options='editable:false'/></td>


				</tr>

				<tr>
					<th>冻结件数</th>
					<td><input type='text' name='qtyholded' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<td style="display: none"><input type='text' name='fmlotnum' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>序列号</th>
					<td><input type='text' name='lotatt05' class='easyui-textbox' size='16' data-options='editable:false'/></td>


				</tr>
				<tr>
					<th>调整件数</th>
					<td><input type='text' name='lotatt11' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>规格型号</th>
					<td><input type='text' name='skudescrc' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>

				</tr>
				<tr>
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
					<td><input type='text' name='lotatt12text' class='easyui-textbox' size='16' data-options=''/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogAdjBtn'>
		<a onclick='commitAdj();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialogAdj");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--库存移动页面--%>
	<div id='ezuiDialogMov' style='padding: 10px;'>
		<form id='ezuiFormMov' method='post'>
			<input type='hidden' id='viewInvLotattId' name='viewInvLotattId'/>
			<table>
				<tr>

					<th>货主</th>
					<td><input type='text' name='fmcustomerid' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>产品代码</th>
					<td><input type='text' name='fmsku' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>

				</tr>
				<tr>

					<th>库存件数</th>
					<td><input type='text' name='fmqty' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>产品名称</th>
					<td><input type='text' name='lotatt12Show' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>

				</tr>
				<tr>
					<th>分配件数</th>
					<td><input type='text' name='qtyallocated' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<td style="display: none"><input type='text' name='fmlotnum' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>批号</th>
					<td><input type='text' name='lotatt04' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>

				</tr>
				<tr>
					<th>可用件数</th>
					<td><input type='text' name='qtyavailed' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>规格型号</th>
					<td><input type='text' name='skudescrc' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>


				</tr>
				<tr>
					<th>冻结件数</th>
					<td><input type='text' name='qtyholded' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>移动件数</th>
					<td><input type='text' name='lotatt11' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>原库位</th>
					<td><input type='text' name='fmlocation' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>目标库位</th>
					<td><input type='text' id="lotatt11text" name='lotatt11text' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
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
					<td><input type='text' name='lotatt12text' class='easyui-textbox' size='16' data-options=''/></td>
				</tr>
				<tr>
					<th>生产日期</th>
					<td><input type='text'  id='lotatt01' name='lotatt01' class='easyui-datebox' size='16' data-options=''/></td>
					<th>有效期/失效期</th>
					<td><input type='text' id="lotatt02" name='lotatt02' class='easyui-datebox' size='16' data-options=''/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogMovBtn'>
		<a onclick='commitMov();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialogMov");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--库存多条移动页面--%>
	<div id='ezuiDialogMovAll' style='padding: 10px;'>
		<form id='ezuiFormMovAll' method='post'>
			<input type='hidden' id='viewInvLotattId' name='viewInvLotattId'/>
			<table>
<%--				<tr>--%>
<%--					<th>仓库编码</th>--%>
<%--				    <td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>

<%--				<tr>--%>

					<th>库存件数</th>
					<td><input type='text' name='fmqty' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
					<th>移动件数</th>
					<td><input type='text' name='lotatt11' class='easyui-textbox' size='16' data-options='required:true,editable:false'/></td>
				</tr>
				<tr>
					<th>原库位</th>
					<td><input type='text' name='fmlocation' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>目标库位</th>
					<td><input type='text' id="lotatt11text" name='lotatt11text' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>移动原因</th>
					<td><input type='text' id="lotatt12" name='lotatt12' class='easyui-combobox' size='16' data-options="required:true,panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'OK', value: '正常'},
																											{id: 'SP', value: '异常'},
																											{id: 'CS', value: '残损'}
																										]"/></td>
					<th>原因描述</th>
					<td><input type='text'  id="lotatt12text" name='lotatt12text' class='easyui-textbox' size='16' data-options=''/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogMovAllBtn'>
		<a onclick='commitMovAll();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialogMovAll");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--库存冻结页面--%>
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
<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
	
<!-- 客户编码选择弹框 -->
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
																																{id: '1', value: '是'},
																																{id: '0', value: '否'}
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
<%--库位选择弹窗--%>
	<div id='ezuiLocDataDialog'  style="width:900px;height:480px;padding:10px 20px"   >
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiLocToolbar' class='datagrid-toolbar'   style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>库位：</th><td>
								<input type='text' id='locationid' name="locationid" class='easyui-textbox'  size='12' data-options=''/></td>
								<th>库位类型：</th><td>
								<input type='text' id='locationcategory' name="locationcategory" class='easyui-combobox' size='12' data-options="panelHeight:'auto',
																																		    editable:false,
																																			url:'<c:url value="/basLocationController.do?getCatTypeCombobox"/>',
																																			valueField: 'id',
																																			textField: 'value'"/></td>
								<td>
									<a onclick='ezuiLocDataSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectLocation();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiLocToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
					<div id='ezuiLocDialogBtn'> </div>
				</div>
				<table id='ezuiLocDataDialogId' ></table>
			</div>
		</div>
	</div>
	<div id='ezuiLocDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<!--产品代码查询弹窗 -->
	<div id="ezuiSkuSearchDialog">

	</div>

	<%--导入页面--%>
	<c:import url='/WEB-INF/jsp/viewInvLotatt/skuDialog.jsp'/>
</body>
</html>
