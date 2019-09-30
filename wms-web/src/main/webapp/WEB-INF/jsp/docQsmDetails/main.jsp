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
var ezuiMenu;
var ezuiDatagrid;                 //主页datagird

var ezuiDialog;               //二级dialog
var ezuiForm;                //二级dialog form
var ezuiDetailsDatagrid;      //二级datagrid

var ezuiDetailsDialog;      //三级dialog
var ezuiDetailsForm;      //三级dialog form

var generatemanagementDialog;        //生成质量状态管理dialog
var generatemanagementDialogId;      //生成质量状态管理datagird

var upstreamDialog;        //上游指定库存dialog
var upstreamDialogId;      //上游指定库存 datagird


var qualityStatusForm;       //库存信息选择dialog form
var qualityStatusDialog;     //库存信息选择dialog

var qualityStatusWorkForm;       //生成质量状态选择dialog form
var qualityStatusWorkDialog;     //生成质量状态选择dialog

var ezuiCustDataDialog;       //货主编码选择弹框页面
var ezuiCustDataDialogId;     //货主编码选择弹框
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDetailsForm = $('#ezuiDetailsForm').form();
	qualityStatusForm = $('#qualityStatusForm').form();
	qualityStatusWorkForm = $('#qualityStatusWorkForm').form();
//主页datagrid
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docQsmHeaderController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '质量状态管理',
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
		rowStyler:function(index,row){
			if(row.hedi01!=null||row.hedi02!=null){
				return 'color:blue;';
			}
		},
		columns : [[
			{field: 'qcudocno',		title: '管理任务号',	width: 150 },
			{field: 'status',		title: '管理状态',	width: 150,formatter:qcustatusFormatter },
			{field: 'customerid',		title: '货主',	width: 150 },
			{field: 'warehouseid',		title: '仓库编码',	width: 150 },
			{field: 'hedi01',		title: '质量变更单号',	width: 150 },
			{field: 'hedi02',		title: '参考编号',	width: 150 },
			{field: 'addtime',		title: '创建时间',	width: 150 },
			{field: 'addwho',		title: '创建人',	width: 150 },
			{field: 'edittime',		title: '编辑时间',	width: 150 },
			{field: 'editwho',		title: '编辑人',	width: 150 },
			{field: 'notes',		title: '备注',	width: 200 },

		]],
		onDblClickCell: function(index,field,value){
			edit();
		}
		,onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
//二级页面订单明细列表datagird（编辑数据窗口）
	ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
		url : '<c:url value="/docQsmDetailsController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#detailToolbar',
		title: '质量状态管理细单',
		pageSize: 50,
		pageList: [50, 100, 200],
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		rowStyler:function(index,row){
			if(row.userdefine1!=null||row.userdefine2!=null){
				return 'color:blue;';
			}
		},
		columns: [[
			{field: 'qcudocno',		title: '管理任务号',	width: 150 },
			{field: 'qcudoclineno',		title: '行号',	width: 70 },
			{field: 'qcustatus',		title: '管理状态',	width: 150,formatter:qcustatusFormatter },
			{field: 'lotatt14',		title: '入库单号',	width: 150 },
			{field: 'customerid',		title: '货主',	width: 150 },
			{field: 'lotatt03',		title: '入库日期',	width: 150 },
			{field: 'lotatt08',		title: '供应商',	width: 150 },
			{field: 'lotatt08text',		title: '供应商代码',	width: 150,hidden: true },
			{field: 'businesstype',		title: '业务类型',	width: 150 },
			{field: 'sku',		title: '产品代码',	width: 150 },
			{field: 'lotatt12',		title: '产品名称',	width: 150 },
			{field: 'lotatt06',		title: '注册证号/备案凭证号',	width: 150 },
			{field: 'descrc',		title: '规格/型号',	width: 150 },
			{field: 'lotatt04',		title: '生产批号',	width: 150 },
			{field: 'lotatt05',		title: '序列号',	width: 150 },
			{field: 'lotatt07',		title: '灭菌批号',	width: 150 },
			{field: 'lotatt01',		title: '生产日期',	width: 150 },
			{field: 'lotatt02',		title: '有效期/失效期',	width: 150 },
			{field: 'locqty',		title: '原库存件数',	width: 70,hidden:true},
			{field: 'qty',		title: '件数',	width: 70 },
			{field: 'qtyeach',		title: '数量',	width: 70},
			{field: 'lotatt15',		title: '生产企业',	width: 100 },
			{field: 'reservedfield06', title: '生产许可证号/备案号', width: 150},
			{field: 'lotatt10',		title: '质量状态',	width: 100,formatter:ZL_TYPstatusFormatter },
			{field: 'changeProcess',		title: '质量状态变更过程',	width: 120,formatter:ZL_TYPstatusFormatter },
			{field: 'locationid',		title: '库位',	width: 150 },
			{field: 'finddate',		title: '发现日期',	width: 150 },
			{field: 'failedDescription',		title: '不合格说明',	width: 200 },
			{field: 'customeridTreatment',		title: '货主处置措施',	width: 150 },
			{field: 'treatmentDate',		title: '处置日期',	width: 150 },
			{field: 'remarks',		title: '备注',	width: 150 },
			{field: 'recordingDate',		title: '记录日期',	width: 150 },
			{field: 'recordingPeople',		title: '记录人',	width: 100 },
			{field: 'lotnum',		title: '批次',	width: 100 },
			{field: 'userdefine1',		title: '金蝶判重码',	width: 100 },
			{field: 'userdefine2',		title: '订单对应入库单号',	width: 100 }
		]],
		onDblClickCell: function (index, field, value) {
			detailedit();
		},
		onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
			$(this).datagrid("resize", {height: 320});
		}
	});
//二级dialog
	ezuiDialog = $('#ezuiDialog').dialog({
		modal: true,
		left:200,
		top:50,
		height:550,
		width:900,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '#ezuiDialogBtn',
		onClose: function () {
			ezuiFormClear(ezuiForm);

		}
	}).dialog('close');
//三级dialog 明细单
	ezuiDetailsDialog = $('#ezuiDetailsDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDetailsDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiDetailsForm);
		}
	}).dialog('close');

//库存信息dialog
	generatemanagementDialog = $('#generatemanagementDialog').dialog({
		modal: true,
		height:600,
		width:900,
		title: '库存信息',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {
		}
	}).dialog('close');
//上游指定库存 库存信息dialog
	upstreamDialog = $('#upstreamDialog').dialog({
		modal: true,
		height:600,
		width:900,
		title: '库存信息',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {
		}
	}).dialog('close');
//库存信息选择dialog
	qualityStatusDialog = $('#qualityStatusDialog').dialog({
		modal : true,
		width:300,
		height:200,
		title : '生成',
		buttons : '#qualityStatusDialogBtn',
		onClose : function() {
			ezuiFormClear(qualityStatusForm);
		}
	}).dialog('close');
//生成质量状态选择dialog
	qualityStatusWorkDialog = $('#qualityStatusWorkDialog').dialog({
		modal:true,
		width:270,
		height:220,
		title : '提交',
		buttons : '#qualityStatusWorkDialogBtn',
		onClose : function() {
			ezuiFormClear(qualityStatusWorkForm);
		}
	}).dialog('close');
//货主代码选择弹框
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//查询控件初始化
	$("#ezuiForm #customerid").textbox({
		// editable:false,
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
});
//主页新增按钮
var add = function () {
	processType = 'add';
//初始化ezuiDetailsDatagrid
 	$('#ezuiDetailsDatagrid').datagrid('load', {qcudocno: '-1'});
 	$("#ezuiForm #status").combobox('setValue',"00");
	$("#ezuiForm #customerid").textbox({disabled:false});
	$("#ezuiBtn_recommit").linkbutton('enable');
	$("#ezuiBtn_generatemanagement").linkbutton('enable');
	ezuiDialog.dialog('open');
};
//主单编辑
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		//判断是否上游
		if(row.hedi01!=null||row.hedi02!=null){
			$("#ezuiBtn_generatemanagement").linkbutton('disable');
			$("#ezuiBtn_detaildel").linkbutton('disable');
		}else{
			$("#ezuiBtn_generatemanagement").linkbutton('enable');
			$("#ezuiBtn_detaildel").linkbutton('enable');
		}
		ezuiForm.form('load',{
			qcudocno : row.qcudocno,
			status : row.status,
			customerid : row.customerid,
            notes:row.notes
		});
		$('#ezuiDetailsDatagrid').datagrid('load',{qcudocno:row.qcudocno});
		$("#ezuiForm #customerid").textbox({disabled:true});
		$("#ezuiBtn_recommit").linkbutton('enable');
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//主单删除
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docQsmHeaderController.do?delete',
					data : {id : row.qcudocno},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							msg = result.msg;
						} catch (e) {
							msg = '<spring:message code="common.message.data.delete.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							ezuiDatagrid.datagrid('reload');
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
//细单编辑
var detailedit = function(){
	var row = ezuiDetailsDatagrid.datagrid('getSelected');
	if(row){
		//判断是否需要指定库存
		if(row.userdefine1!=null||row.userdefine2!=null){
			if(row.qcustatus=="00") {
				$("#ezuiBtn_detailsCommit").linkbutton('enable');
			}else{
				$("#ezuiBtn_detailsCommit").linkbutton('disable');
			}
		}else {
			$("#ezuiBtn_detailsCommit").linkbutton('disable');
		}
		var  lotatt10=ZL_TYPstatusFormatter(row.lotatt10);
		var  changeProcess=ZL_TYPstatusFormatter(row.changeProcess);
		ezuiDetailsForm.form('load',{
			qcudocno : row.qcudocno,
			qcudoclineno : row.qcudoclineno,
			qcustatus : row.qcustatus,
			lotatt14 : row.lotatt14,
			customerid : row.customerid,
			lotatt03:row.lotatt03,
			lotatt08:row.lotatt08,
			businesstype:row.businesstype,
			sku:row.sku,
			lotatt12:row.lotatt12,
			lotatt06:row.lotatt06,
			descrc:row.descrc,
			lotatt04:row.lotatt04,
			lotatt05:row.lotatt05,
			lotatt07:row.lotatt07,
			lotatt01:row.lotatt01,
			lotatt02:row.lotatt02,
			qty:row.qty,
			qtyeach:row.qtyeach,
			lotatt15:row.lotatt15,
			reservedfield06:row.reservedfield06,
			lotatt10:lotatt10,
			changeProcess:changeProcess,
			locationid:row.locationid,
			finddate:row.finddate,
			failedDescription:row.failedDescription,
			customeridTreatment:row.customeridTreatment,
			treatmentDate:row.treatmentDate,
			remarks:row.remarks,
			recordingDate:row.recordingDate,
			recordingPeople:row.recordingPeople
		});
		ezuiDetailsDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//细单删除
var detaildel = function(){
	var row = ezuiDetailsDatagrid.datagrid('getSelected');
	if(row){
		var data=new Object();
		data.qcudocno=row.qcudocno;
		data.qcudoclineno=row.qcudoclineno;
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docQsmDetailsController.do?delete',
					data :data,
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							msg = result.msg;
						} catch (e) {
							msg = '<spring:message code="common.message.data.delete.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							ezuiDetailsDatagrid.datagrid('reload');
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
//主单提交
var commitZ = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docQsmHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docQsmHeaderController.do?add"/>';
	}
	ezuiForm.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiForm.form('validate')){
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
					msg ="修改资料成功!";
					$("#ezuiForm #customerid").textbox({disabled:true});
					if(processType == 'add'){
						$("#ezuiForm #qcudocno").textbox('setValue',result.msg);
						$("#ezuiBtn_recommit").linkbutton('disable');
					}else {
						$("#ezuiForm #notes").textbox('setValue',result.msg);
					}
				}else{
					msg = '<font color="red">' + result.msg + '</font>';
				}
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				ezuiDatagrid.datagrid('reload');
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			}
		}
	});
};
//细单指定库存
var detailsCommitZ = function(){
	var row = ezuiDetailsDatagrid.datagrid('getSelected');
	if(row) {
        var customerid=row.customerid;
		var sku=row.sku;
		var lotatt04=row.lotatt04;
		var lotatt05=row.lotatt05;
		var lotatt08text=row.lotatt08text;
		var lotatt08=row.lotatt08;
		var lotatt10=row.lotatt10=="HG"?"BHG":"HG";
		$("#upstreamToolbar #customerid").textbox('setValue',customerid);
		$("#upstreamToolbar #sku").textbox('setValue',sku);
		$("#upstreamToolbar #lotatt04").textbox('setValue',lotatt04);
		$("#upstreamToolbar #lotatt05").textbox('setValue',lotatt05);
		$("#upstreamToolbar #lotatt08").textbox('setValue',lotatt08);
		$("#upstreamToolbar #lotatt10").textbox('setValue',lotatt10=="HG"?"合格":"不合格");
		upstreamDialogId = $('#upstreamDialogId').datagrid({
			url: '<c:url value="/viewInvLotattController.do?showDatagrid"/>',
			method: 'POST',
			toolbar: '#upstreamToolbar',
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
			singleSelect: true,
			queryParams: {
				fmcustomerid:customerid,
				fmsku:sku,
				lotatt04:lotatt04,
				lotatt05:lotatt05,
				lotatt08:lotatt08text,
				lotatt10:lotatt10,
			},
			columns: [[
				{field: 'fmlocation', title: '库位', width: 100},
				{field: 'fmcustomerid', title: '货主', width: 100},
				{field: 'fmsku', title: '产品代码', width: 150},
				{field: 'lotatt12', title: '产品名称', width: 200},
				{field: 'lotatt10', title: '质量状态', width: 100, formatter: ZL_TYPstatusFormatter},
				{field: 'fmqty', title: '库存件数', width: 71},
				{field: 'fmqtyEach', title: '库存数量', width: 71},
				{field: 'qtyallocated', title: '分配件数', width: 71},
				{field: 'qtyallocatedEach', title: '分配数量', width: 71},
				{field: 'qtyavailed', title: '可用件数', width: 71},
				{field: 'qtyavailedEach', title: '可用数量', width: 71},
				// {field: 'totalgrossweight',		title: '毛重',	width: 71 },
				// {field: 'totalcubic',		title: '体积',	width: 71 },
				//
				// {field: 'uom',		title: '单位',	width: 71 },
				{field: 'fmlotnum', title: '批次', width: 81},

				{field: 'lotatt06', title: '注册证号', width: 150},//加个字段
				{field: 'skudescre', title: '规格型号', width: 103},
				{field: 'lotatt05', title: '序列号', width: 110},
				{field: 'lotatt04', title: '生产批号', width: 95},
				{field: 'lotatt07', title: '灭菌批号', width: 120},
				{field: 'lotatt03', title: '入库日期', width: 91},
				{field: 'lotatt01', title: '生产日期', width: 112},
				{field: 'lotatt02', title: '有效期/失效期', width: 113},
				{field: 'lotatt08', title: '供应商', width: 120},
				{field: 'lotatt09', title: '样品属性', width: 100, formatter: YP_TYPstatusFormatter},
				{field: 'lotatt11', title: '存储条件', width: 100},
				{field: 'onholdlocker', title: '冻结状态', width: 100, formatter: holdStatusFormatter},
				{field: 'warehouseid', title: '仓库编码', width: 71}
			]],
			onDblClickCell: function (index, field, value) {
				upstreamT();
			},
			onRowContextMenu: function (event, rowIndex, rowData) {
			},
			onLoadSuccess: function (data) {
				$(this).datagrid('unselectAll');
			}
		});
		upstreamDialog.dialog('open');
	}
};
//细单指定库存 提交
var upstreamT=function () {
	var row = upstreamDialogId.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '确定指定此条库存!', function(confirm) {
			if(confirm) {
				$.messager.progress({
					text: '<spring:message code="common.message.data.processing"/>', interval: 100
				});
				var data=new Object();
				data.qcudocno=$("#ezuiDetailsForm #qcudocno").textbox('getValue');
				data.qcudoclineno=$("#ezuiDetailsForm #qcudoclineno").textbox('getValue');
				data.locationid=row.fmlocation;
				data.sku=row.fmsku;
				data.customerid=row.fmcustomerid;
				data.lotnum=row.fmlotnum;
				$.ajax({
					url : 'docQsmDetailsController.do?upstreamT',
					data :data,
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							msg = result.msg;
						} catch (e) {
							msg = '<spring:message code="common.message.data.delete.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
							ezuiDetailsDatagrid.datagrid('reload');
							upstreamDialog.dialog('close');
							ezuiDetailsDialog.dialog('close');
						}
					}
				});

			}});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}

}
//主页查询
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		qcudocno : $('#qcudocno').val(),
		status : $('#status').combobox('getValue'),

	});
};
//生成管理start=========================
var generatemanagement = function () {
	var qcuno=$("#ezuiForm #qcudocno").textbox('getValue');
	var status=$("#ezuiForm #status").textbox('getValue');
	var customerid=$("#ezuiForm #customerid").textbox('getValue');
	if(qcuno==null||qcuno==""){
		$.messager.show({
			msg : '请先生成主单!', title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
	if(status!="00"){
		$.messager.show({
			msg : '只有新建任务单可增加细单!', title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
	$("#generatemanagementToolbar #customerid").textbox('setValue',customerid);
	$("#generatemanagementDialog #lotatt04").textbox('clear');
	$("#generatemanagementDialog #lotatt05").textbox('clear');
	$("#generatemanagementDialog #lotatt10").combobox('clear');
	generatemanagementDialogId = $('#generatemanagementDialogId').datagrid({
		url: '<c:url value="/viewInvLotattController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#generatemanagementToolbar',
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
		singleSelect: true,
		queryParams:{
			fmcustomerid:customerid
		},
		columns: [[
			{field: 'fmlocation', title: '库位', width: 100},
			{field: 'fmcustomerid',		title: '货主',	width: 100 },
			{field: 'fmsku',		title: '产品代码',	width: 150 },
			{field: 'lotatt12', title: '产品名称', width: 200},
			{field: 'lotatt10', title: '质量状态', width: 100, formatter:ZL_TYPstatusFormatter},
			{field: 'fmqty',		title: '库存件数',	width: 71 },
			{field: 'fmqtyEach',		title: '库存数量',	width: 71 },
			{field: 'qtyallocated',		title: '分配件数',	width: 71 },
			{field: 'qtyallocatedEach',		title: '分配数量',	width: 71 },
			{field: 'qtyavailed',		title: '可用件数',	width: 71 },
			{field: 'qtyavailedEach',		title: '可用数量',	width: 71 },
			// {field: 'totalgrossweight',		title: '毛重',	width: 71 },
			// {field: 'totalcubic',		title: '体积',	width: 71 },
			//
			// {field: 'uom',		title: '单位',	width: 71 },
			{field: 'fmlotnum',		title: '批次',	width: 81 },

			{field: 'lotatt06', title: '注册证号', width: 150},//加个字段
			{field: 'skudescre', title: '规格型号', width: 103},
			{field: 'lotatt05', title: '序列号', width: 110},
			{field: 'lotatt04', title: '生产批号', width: 95},
			{field: 'lotatt07', title: '灭菌批号', width: 120},
			{field: 'lotatt03', title: '入库日期', width: 91},
			{field: 'lotatt01', title: '生产日期', width: 112},
			{field: 'lotatt02', title: '有效期/失效期', width: 113},
			{field: 'lotatt08', title: '供应商', width: 120},
			{field: 'lotatt09', title: '样品属性', width: 100,formatter:YP_TYPstatusFormatter},
			{field: 'lotatt11', title: '存储条件', width: 100},
			{field: 'onholdlocker', title: '冻结状态', width: 100, formatter:holdStatusFormatter},
			{field: 'warehouseid',		title: '仓库编码',	width: 71 }
		]],
		onDblClickCell: function (index, field, value) {
			generatemanagementT();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		},
		onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	generatemanagementDialog.dialog('open');
};

//生成质量状态管理查询
var generatemanagementDialogSearch = function () {
	generatemanagementDialogId.datagrid('load', {
		lotatt04: $("#generatemanagementDialog #lotatt04").textbox("getValue"),
		lotatt05: $("#generatemanagementDialog #lotatt05").textbox("getValue"),
		lotatt10:$("#generatemanagementDialog #lotatt10").combobox("getValue"),
		fmcustomerid:$("#generatemanagementDialog #customerid").textbox("getValue")
	});
};
//生成质量状态管理清除
var generatemanagementToolbarClear = function () {
	$("#generatemanagementDialog #lotatt04").textbox('clear');
	$("#generatemanagementDialog #lotatt05").textbox('clear');
	$("#generatemanagementDialog #lotatt10").combobox('clear');
};
//生成管理end==========================

//双击或者选择弹出库存信息输入框
var generatemanagementT=function () {
	var row = generatemanagementDialogId.datagrid('getSelected');
	if(row){
		$("#qualityStatusForm #fmqty").textbox('setValue',row.fmqty);
		$("#qualityStatusForm #fmqtyF").textbox('setValue',"");
		$("#qualityStatusForm #remarks").textbox('setValue',"");
		qualityStatusDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}
//提交库存信息输入框
var commitQualityStatus=function () {
	var row = generatemanagementDialogId.datagrid('getSelected');
	if(row){
		var remarks=$("#qualityStatusForm #remarks").textbox('getValue');
		var fmqtyF=$("#qualityStatusForm #fmqtyF").textbox('getValue');
		if(fmqtyF>row.qtyavailed){
			$.messager.show({
				msg : '请输入小于可用件数的数字!', title : '<spring:message code="common.message.prompt"/>'
			});
			return;
		}
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});
		var data=new Object();
		data.qcudocno=$("#ezuiForm #qcudocno").textbox('getValue');
		data.locationid=row.fmlocation;
		data.sku=row.fmsku;
		data.customerid=row.fmcustomerid;
		data.lotnum=row.fmlotnum;
		data.qty=fmqtyF;
		data.remarks=remarks;
		$.ajax({
			url : 'docQsmDetailsController.do?commitQualityStatus',
			data :data,
			type : 'POST',
			dataType : 'JSON',
			success : function(result){
				var msg = '';
				try {
					msg = result.msg;
				} catch (e) {
					msg = '<spring:message code="common.message.data.delete.failed"/>';
				} finally {
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');
					qualityStatusDialog.dialog('close');
					ezuiDetailsDatagrid.datagrid('load',{qcudocno:$("#ezuiForm #qcudocno").textbox('getValue')});

				}
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}


//双击或者选择弹出质量状态作业输入框
var qualityStatusWork=function () {
	var row = ezuiDetailsDatagrid.datagrid('getSelected');
	if(row){
		if(row.qcustatus!='00'){
			$.messager.show({
				msg : '只有管理状态为任务创建才可以操作!', title : '<spring:message code="common.message.prompt"/>'
			});
			return;
		}
		//判断上游
		if(row.lotnum==null||row.locationid==null){
			$.messager.show({
				msg : '上游传入数据请先双击指定库位!', title : '<spring:message code="common.message.prompt"/>'
			});
			return;
		}
		$("#qualityStatusWorkForm #locqty").textbox('setValue',row.locqty);
		$("#qualityStatusWorkForm #qty").textbox('setValue',row.qty);
		$("#qualityStatusWorkForm #failedDescription").textbox('setValue',"");
		$("#qualityStatusWorkForm #customeridTreatment").textbox('setValue',"");
		qualityStatusWorkDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}
//提交质量状态作业输入框
var commitQualityStatusWork=function () {
	var row = ezuiDetailsDatagrid.datagrid('getSelected');
	if(row){
		var failedDescription=$("#qualityStatusWorkForm #failedDescription").textbox('getValue');
		var customeridTreatment=$("#qualityStatusWorkForm #customeridTreatment").textbox('getValue');
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});
		var data=new Object();
		data.qcudocno=row.qcudocno;
		data.qcudoclineno=row.qcudoclineno
		data.sku=row.sku;
		data.customerid=row.customerid;
		data.lotnum=row.lotnum;
		data.locationid=row.locationid;
		data.qty=row.qty;
		data.locqty=row.locqty;
		data.lotatt10=row.lotatt10;
		data.changeProcess=row.changeProcess;
		data.failedDescription=failedDescription;
		data.customeridTreatment=customeridTreatment;
		if(row.userdefine1!=null||row.userdefine2!=null){
			data.userdefine1='1';
		}
		$.ajax({
			url : 'docQsmDetailsController.do?commitQualityStatusWork',
			data :data,
			type : 'POST',
			dataType : 'JSON',
			success : function(result){
				var msg = '';
				try {
					if(result.success){
						msg="质量状态变更成功!";
						$("#ezuiForm #status").combobox('setValue',result.msg);
					}else{
						msg=result.msg;
					}
                   } catch (e) {
					msg = '<spring:message code="common.message.data.delete.failed"/>';
				} finally {
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');
					qualityStatusWorkDialog.dialog('close');
					ezuiDatagrid.datagrid('reload');
					ezuiDetailsDatagrid.datagrid('reload');
				}
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}

//关闭计划单
var closeP= function(){
	url = '<c:url value="/docQsmHeaderController.do?close"/>';
	var row = ezuiDatagrid.datagrid('getSelected');
	var msg='';
	if(row) {
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '确认关闭任务单!', function (confirm) {
			if (confirm) {
				var data = new Object();
				data.qcudocno = row.qcudocno;
				$.messager.progress({
					text: '<spring:message code="common.message.data.processing"/>', interval: 100
				});
				$.ajax({
					url: url,
					data: data,
					dataType: 'json',
					error: function (a, b, c) {
						//alert(a + b + c);
					},
					success: function (result) {
						try {
							if (result.success) {
								msg = result.msg;
							} else {
								msg = result.msg;

							}
						} catch (e) {

							msg: '数据错误!';

						} finally {
							$.messager.show({
								msg: msg, title: '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
							ezuiDatagrid.datagrid('reload');
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
//取消计划单
var cancel= function(){
	url = '<c:url value="/docQsmHeaderController.do?cancel"/>';
	var row = ezuiDatagrid.datagrid('getSelected');
	var msg='';
	if(row) {
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '确认取消任务单!', function (confirm) {
			if (confirm) {
				var data = new Object();
				data.qcudocno = row.qcudocno;

				$.messager.progress({
					text: '<spring:message code="common.message.data.processing"/>', interval: 100
				});
				$.ajax({
					url: url,
					data: data,
					dataType: 'json',
					error: function (a, b, c) {
						//alert(a + b + c);
					},
					success: function (result) {
						try {
							if (result.success) {
								msg = result.msg;
							} else {
								msg = result.msg;

							}
						} catch (e) {

							msg: '数据错误!';

						} finally {
							$.messager.show({
								msg: msg, title: '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
							ezuiDatagrid.datagrid('reload');
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
/* 导出start */
var doExport = function(){
    var row = ezuiDatagrid.datagrid('getSelected');
    if(row) {
        if (navigator.cookieEnabled) {
            var qcudocno=row.qcudocno;
            $('#ezuiBtn_export').linkbutton('disable');
            //--导出Excel
            // window.open(sy.bp() + "/docOrderHeaderController.do?exportOrderNoToExcel&orderno="+order);
            var formId = ajaxDownloadFile(sy.bp()+ "/docQsmDetailsController.do?exportDocQsmDataToPdf&qcudocno="+qcudocno);
            downloadCheckTimer = window.setInterval(function () {
                window.clearInterval(downloadCheckTimer);
                // $('#' + formId).remove();
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
        ;
    }else{
        $.messager.show({
            msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
        });
    }
};
/* 导出end */



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
		$("#ezuiForm #customerid").textbox('setValue', row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function () {
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================
</script>
</head>
<body>
<%--主页datagrid查询--%>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>管理任务号</th><td><input type='text' id='qcudocno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>管理状态</th><td><input type='text' id='status' class='easyui-combobox' size='16' data-options=" panelHeight: 'auto',
							                                                                                                        editable: false,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '00',
																																        value: '任务创建'},
																																        {label: '30',
																																        value: '部分完成'},
																																       {label: '40',
																																         value: '任务完成'},
																																       {label: '90',
																																         value: '任务取消'},
																																        {label: '99',
																																         value: '任务关闭'}]"/></td>

							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                                <a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
                            </td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>增加</a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>编辑</a>
					<a onclick='closeP();' id='ezuiBtn_closeP' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>关闭计划单</a>
					<a onclick='cancel();' id='ezuiBtn_cancel' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>取消计划单</a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>

<!-- 库存信息 非待检-->
	<div id='generatemanagementDialog' style="width:700px;height:480px;padding:10px 20px">
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='generatemanagementToolbar' class='datagrid-toolbar' style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>货主</th>
								<td>
									<input type='text' id='customerid' name="customerid" class='easyui-textbox' size='12'
										   data-options='editable: false'/></td>
								<td>
								<th>生产批号</th>
								<td>
									<input type='text' id='lotatt04' name="lotatt04" class='easyui-textbox' size='12'
										   data-options=''/></td>
								<td>
								<th>序列号</th>
								<td>
									<input type='text' id='lotatt05' name="lotatt05" class='easyui-textbox' size='12'
										   data-options=''/></td>
								<td>
								<th>质量状态</th>
								<td>
									<input type='text' id='lotatt10' name="lotatt10" class='easyui-combobox' size='12' data-options=" panelHeight: 'auto',
							                                                                                                        editable: false,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: 'HG',
																																        value: '合格'},
																																       {label: 'BHG',
																																         value: '不合格'}]"/></td>
							<tr>
						    </tr>
								<td colspan="4">
									<a onclick='generatemanagementDialogSearch();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='generatemanagementToolbarClear();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-remove"'
									   href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
									<a onclick='generatemanagementT();' id='ezuiBtn_generatemanagementT' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成质量状态管理</a>

								</td>
							</tr>
						</table>
					</fieldset>
					<div id='generatemanagementDialogBtn'></div>
				</div>
				<table id='generatemanagementDialogId'></table>
			</div>
		</div>
	</div>
	<div id='generatemanagementDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#generatemanagementDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.close'/></a>
	</div>
<!--上游指定 库存信息 非待检-->
<div id='upstreamDialog' style="width:700px;height:480px;padding:10px 20px">
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options="region:'center'">
			<div id='upstreamToolbar' class='datagrid-toolbar' style="">
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>货主</th>
							<td>
								<input type='text' id='customerid' name="customerid" class='easyui-textbox' size='12'
									   data-options='editable: false'/></td>
							<td>
							<th>产品代码</th>
							<td>
								<input type='text' id='sku' name="sku" class='easyui-textbox' size='12'
									   data-options='editable: false'/></td>
							<td>
							<th>生产批号</th>
							<td>
								<input type='text' id='lotatt04' name="lotatt04" class='easyui-textbox' size='12'
									   data-options='editable: false'/></td>
							<td>
						</tr>
						<tr>
							<th>序列号</th>
							<td>
								<input type='text' id='lotatt05' name="lotatt05" class='easyui-textbox' size='12'
									   data-options='editable: false'/></td>
							<td>
							<th>供应商</th>
							<td>
								<input type='text' id='lotatt08' name="lotatt08" class='easyui-textbox' size='12'
									   data-options='editable: false'/></td>
							<td>
							<th>质量状态</th>
							<td>
								<input type='text' id='lotatt10' name="lotatt10" class='easyui-textbox' size='12' data-options=""/></td>


							<td><a onclick='upstreamT();' id='ezuiBtn_upstreamT' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>指定库存</a></td>

						</tr>

					</table>
				</fieldset>
				<div id='upstreamDialogBtn'></div>
			</div>
			<table id='upstreamDialogId'></table>
		</div>
	</div>
</div>
<div id='upstreamDialogBtn'>
	<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
			code='common.button.commit'/></a>
	<a onclick='ezuiDialogClose("#upstreamDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
			code='common.button.close'/></a>
</div>
<%--库存信息选择点击弹窗--%>
<div id='qualityStatusDialog' style='padding: 10px;'>
	<form id='qualityStatusForm' method='post'>
		<table>
			<input type='hidden' id='qty1' name="qty1" />
			<tr>
				<th>库存件数</th><td><input type='text' id='fmqty' name="fmqty" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

			</tr>
			<tr>
				<th>变更件数</th><td><input type='text' id='fmqtyF' name="fmqtyF" class='easyui-textbox' size='20' data-options="required:true"/></td>

			</tr>
			<tr>
			  <th>备注</th><td><input type='text' id='remarks' name="remarks" class='easyui-textbox' size='20' data-options=""/></td>

			</tr>
		</table>
	</form>
</div>
<div id='qualityStatusDialogBtn'>
	<a onclick='commitQualityStatus();' class='easyui-linkbutton' href='javascript:void(0);'>生成</a>
</div>
<%--生成质量状态选择点击弹窗--%>
<div id='qualityStatusWorkDialog' style='padding: 10px;'>
	<form id='qualityStatusWorkForm' method='post'>
		<table>
			<tr>
				<th>库存件数</th><td><input type='text' id='locqty' name="locqty" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

			</tr>
			<tr>
				<th>变更件数</th><td><input type='text' id='qty' name="qty" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

			</tr>
			<tr>
			  <th>变更说明</th><td><input type='text' id='failedDescription' name="failedDescription" class='easyui-textbox' size='20' data-options=""/></td>

			</tr>
			<tr>
			  <th>货主处置措施</th><td><input type='text' id='customeridTreatment' name="customeridTreatment" class='easyui-textbox' size='20' data-options=""/></td>

			</tr>
		</table>
	</form>
</div>
<div id='qualityStatusWorkDialogBtn'>
	<a onclick='commitQualityStatusWork();' class='easyui-linkbutton' href='javascript:void(0);'>生成</a>
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
</body>
<c:import url='/WEB-INF/jsp/docQsmDetails/dialog.jsp'/>
<c:import url='/WEB-INF/jsp/docQsmDetails/detailsDialog.jsp'/>

</html>
