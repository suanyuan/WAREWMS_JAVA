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
var ezuiConservationForm;       //养护作业dialog form
var ezuiConservationDialog;     //养护作业dialog
var ezuiDatagrid;              //主页datagrid
var ezuiCVDataDialog;         //养护单号选择框
var ezuiCVDataDialogId;       //养护单号选择框

var ezuiCustDataDialog;        //货主
var ezuiCustDataDialogId;      //货主
var ezuiSkuDataDialog;         //产品名称选择框
var ezuiSkuDataDialogId;       //产品名称选择框
var productDialog_docMtDetails;//主页产品代码选择框
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
	ezuiConservationForm = $('#ezuiConservationForm').form();   //养护作业dialog form
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docMtDetailsController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '养护作业',
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
		columns : [[
			{field:'ck',checkbox:true},
			// {field: 'mtno',		title: '养护单号',	width: 100 },
			{field: 'mtlineno',		title: '养护行号',	width: 75 },
			{field: 'linestatus',		title: '行状态',	width: 100,formatter:MT_STSstatusFormatter },
			{field: 'locationid',		title: '库位',	width: 100 },
			{field: 'inventoryage',		title: '库龄',	width: 80 },
			{field: 'customerid',		title: '货主',	width: 100 },
			{field: 'sku',		title: '产品代码',	width: 140 },
			{field: 'descrc',		title: '规格',	width: 140 },
			{field: 'descre',		title: '型号',	width: 140 },
			{field: 'lotatt12',		title: '产品名称',	width: 200 },
			{field: 'mtqtyExpected',		title: '待养护件数',	width: 100 },
			{field: 'mtqtyEachExpected',		title: '待养护数量',	width: 100 },
			{field: 'mtqtyCompleted',		title: '完成养护件数',	width: 100 },
			{field: 'mtqtyEachCompleted',		title: '完成养护数量',	width: 100 },
			{field: 'uom',		title: '单位',	width: 100 },
			{field: 'checkFlag',		title: '检查结论',	width: 100,formatter:QualifiedOrFailed },
			{field: 'conclusion',		title: '养护结论',	width: 100,formatter:QualifiedOrFailed },
			{field: 'conversewho',		title: '养护人',	width: 100 },
			{field: 'conversedate',		title: '养护日期',	width: 100 },
			{field: 'lotatt04',		title: '生产批号',	width: 100 },
			{field: 'lotatt05',		title: '序列号',	width: 100 },
			{field: 'lotatt07',		title: '灭菌批号',	width: 150 },
			{field: 'lotatt01',		title: '生产日期',	width: 134 },
			{field: 'lotatt02',		title: '有效期/失效期',	width: 134 },
			{field: 'lotatt06',		title: '注册证号',	width: 200 },
			{field: 'lotatt11',		title: '存储条件',	width: 100 },
			{field: 'lotatt15',		title: '生产企业',	width: 250 },
			{field: 'lotnum',		title: '批次号',	width: 100 },
			{field: 'remark',		title: '备注',	width: 150 },
			{field: 'addtime',		title: '创建时间',	width: 134 },
			{field: 'addwho',		title: '创建人',	width: 100 },
			{field: 'edittime',		title: '编辑时间',	width: 134 },
			{field: 'editwho',		title: '编辑人',	width: 100 }
		]],
		onDblClickCell: function(index,field,value){

		},
	     onLoadSuccess:function(data){
			<%--ajaxBtn($('#menuId').val(), '<c:url value="/docQcHeaderController.do?getBtn"/>', ezuiMenu);--%>
			$(this).datagrid('unselectAll');
		}
	});
//养护单号放大镜初始化
	$("#toolbar #mtno").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCVDataDialog #mtno").textbox('clear');
				ezuiCVDataClick();
				ezuiCVDataDialogSearch();
			}
		}]
	});
//产品代码控件初始化 载入公用弹窗页面
	$("#toolbar #sku").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				productDialog_docMtDetails = $('#ezuiSkuSearchDialog').dialog({
					modal: true,
					title: '查询',
					href: sy.bp() + "/basSkuController.do?toSearchDialog&target=docMtDetails",
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
//一级dialog初始化
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
//养护作业dialog
	ezuiConservationDialog = $('#ezuiConservationDialog').dialog({
		modal : true,
		width:270,
		height:230,
		title : '资料',
		buttons : '#ezuiConservationDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiConservationForm);
		}
	}).dialog('close');
//养护单号选择弹框
	ezuiCVDataDialog = $('#ezuiCVDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

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
});
//增加
var add = function(){
	processType = 'add';
	$('#docQcHeaderId').val(0);
	ezuiDialog.dialog('open');
};
//修改
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			mtno : row.mtno,
			pano : row.pano,
			customerid : row.customerid,
			qcreference1 : row.qcreference1,
			qcreference2 : row.qcreference2,
			qcreference3 : row.qcreference3,
			qcreference4 : row.qcreference4,
			qcreference5 : row.qcreference5,
			qctype : row.qctype,
			qcstatus : row.qcstatus,
			qccreationtime : row.qccreationtime,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5,
			notes : row.notes,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			qcPrintFlag : row.qcPrintFlag,
			warehouseid : row.warehouseid
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//删除
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docQcHeaderController.do?delete',
					data : {id : row.id},
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
//一级dialog提交
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docQcHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docQcHeaderController.do?add"/>';
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
					msg = result.msg;
					ezuiDatagrid.datagrid('reload');
					ezuiDialog.dialog('close');
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
//养护作业提交
var commitConservation = function(){
	url = '<c:url value="/docMtDetailsController.do?submitDocMtList"/>';
	var rows = ezuiDatagrid.datagrid('getChecked');
	var forms=[];
	var data=null;
	var msg='';
	var isCan=true;//是否可以养护
	for (var i = 0; i < rows.length; i++) {
		 var linestatus=rows[i].linestatus;
		 //判断细单养护状态
		 if(linestatus!='00'){
		 	msg="养护行号:"+rows[i].mtlineno+" ,已养护不可重复养护!"
			isCan=false;
		 	break;
		 }
		data=new Object();
		data.mtno=rows[i].mtno;              //养护单号
		data.mtlineno=rows[i].mtlineno;      //养护行号
		data.checkFlag=$('#ezuiConservationForm #checkFlag').textbox('getValue');    //检查结论
		data.conclusion=$('#ezuiConservationForm #conclusion').combobox('getValue');  //养护结论
		data.remark=$('#ezuiConservationForm #remark').textbox('getValue');         //备注
		//判断养护的行数
		if(rows.length==1){
			var mtqtyExpectedC=$('#ezuiConservationForm #mtqtyExpectedC').textbox('getValue');
		    data.mtqtyCompleted=mtqtyExpectedC;
		}else{
			var mtqtyNumAll=rows[i].mtqtyExpected-rows[i].mtqtyCompleted;
			data.mtqtyCompleted=mtqtyNumAll;
		}
		forms.push(data);

	}
    if(!isCan){
		$.messager.show({
			msg :msg, title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
	if(ezuiConservationForm.form('validate')) {
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});

			$.ajax({
				url:url,
				data:"forms="+JSON.stringify(forms),
				dataType: 'json',
				error: function () {

				},
				success: function (result) {
					try{
						if(result.success){
							msg=result.msg;
						 	ezuiDatagrid.datagrid('reload');
							ezuiConservationDialog.dialog('close');
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
						 }else{
							msg=result.msg;
							ezuiDatagrid.datagrid('reload');
							ezuiConservationDialog.dialog('close');
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
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

};
//养护作业
var ConservationWork = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var num=rows.length;
    if(num==1){
		 $("#ezuiConservationForm #mtqtyExpected").textbox('enable');
		 $("#ezuiConservationForm #mtqtyExpectedC").textbox('enable');
		 var mtqtyExpected =rows[0].mtqtyExpected
		ezuiConservationForm.form('load',{
			mtqtyExpected:mtqtyExpected

		});
		ezuiConservationDialog.dialog('open');
	}else if(num>1){
		 $("#ezuiConservationForm #mtqtyExpected").textbox('disable');
           $("#ezuiConservationForm #mtqtyExpectedC").textbox('disable');
		$("#ezuiConservationForm #mtqtyExpected").textbox('setValue','所有');
		$("#ezuiConservationForm #mtqtyExpectedC").textbox('setValue','所有');
		ezuiConservationDialog.dialog('open');
	}
    else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//主页查询
var doSearch = function() {
	if ($('#mtno').val() == null || $('#mtno').val() == "") {
		ezuiDatagrid.datagrid('load', {
			mtno: $('#mtno').val(),                            //养护单号
			customerid: $('#customerid').val(),              //货主
			sku: $('#sku').val(),              //产品代码
			linestatus: $('#linestatus').combobox('getValue'),  //养护状态
			lotatt04:$('#lotatt04').val(),
			lotatt05:$('#lotatt05').val(),
			lotatt12:$('#lotatt12').val(),
			descrc:$('#descrc').val(),
			productLineName:$('#productLineName').combobox('getText'),
		});
		$.messager.show({
			msg: '请先选择养护单号!', title: '<spring:message code="common.message.prompt"/>'
		});
	} else {
		ezuiDatagrid.datagrid('load', {
			mtno: $('#mtno').val(),                            //养护单号
			customerid: $('#customerid').val(),              //批次
			sku: $('#sku').val(),              //产品代码
			linestatus: $('#linestatus').combobox('getValue'),  //养护状态
			lotatt04:$('#lotatt04').val(),
			lotatt05:$('#lotatt05').val(),
			lotatt12:$('#lotatt12').val(),
			descrc:$('#descrc').val(),
			productLineName:$('#productLineName').combobox('getText'),

		});

	}
}

/* 单号选择弹框查询 */
var ezuiCVDataDialogSearch = function () {
	ezuiCVDataDialogId.datagrid('load', {
		mtno: $("#ezuiCVDataDialog #mtno").textbox("getValue"),
		mtstatus: $("#ezuiCVDataDialog #mtstatus").combobox("getValue")
	});
};
/* 单号选择弹框清空 */
var ezuiCVToolbarClear = function () {
	$("#ezuiCVDataDialog #mtno").textbox('clear');
	$("#ezuiCVDataDialog #mtstatus").combobox('clear');
};
/* 单号选择弹框 */
var ezuiCVDataClick = function () {
	ezuiCVDataDialogId = $('#ezuiCVDataDialogId').datagrid({
		url: '<c:url value="/docMtHeaderController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiCVToolbar',
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
		columns: [[
			{field: 'mtno', title: '养护单号', width: 100},
			{field: 'mtstatus', title: '养护状态', width: 100,formatter:MT_STSstatusFormatter},
			{field: 'mttype', title: '养护类型', width: 100,formatter:MT_TYPstatusFormatter},
			{field: 'fromdate', title: '开始时间', width: 100},
			{field: 'todate', title: '结束时间', width: 100}
		]],
		onDblClickCell: function (index, field, value) {
			selectConservation();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	ezuiCVDataDialog.dialog('open');
};
/* 单号选择 */
var selectConservation = function () {
	var row = ezuiCVDataDialogId.datagrid('getSelected');
	if (row) {
		// $("#toolbar #pano").textbox('setValue', row.pano);
		$("#toolbar #mtno").textbox('setValue', row.mtno);
		doSearch();
		ezuiCVDataDialog.dialog('close');
	}
	;
};

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
function choseSelect_product_docMtDetails(row) {
	var sku;
	if (row) {
		sku = row;
	} else {
		row = $("#productSearchGrid_docMtDetails").datagrid("getSelections");
		if (row) {
			sku = row[0]
		}
	}
	if (sku) {
		$("#toolbar #sku").textbox("setValue", sku.sku);
	}
	productDialog_docMtDetails.dialog("close");
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
<%--							<th>上架单号</th><td><input type='text' id='pano' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<th>养护单号</th><td><input type='text' id='mtno' class='easyui-textbox' size='16' data-options='editable: false'/></td>
							<th>养护状态</th><td><input type='text' id='linestatus' class='easyui-combobox' size='16' data-options=" panelHeight: 'auto',
							                                                                                                        editable: false,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '00',
																																        value: '未养护'},
																																       {label: '40',
																																         value: '已养护'},
																																       {label: '',
																																         value: '全部'}]"/></td>
	                        <th>产品线</th><td><input type='text' id='productLineName' class='easyui-combobox' size='16' data-options="
																										url:'<c:url value="/productLineController.do?getCombobox"/>',
																										valueField: 'id',
																										textField: 'value'"/></td>
						</tr>
						<tr>
	                        <th>货主代码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品代码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
	                        <th>产品名称</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>

						</tr>
						<tr>
							<th>规格</th><td><input type='text' id='descrc' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产批号</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>序列号</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
							<td colspan="2">
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='ConservationWork()' id='ezuiBtn_check' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>养护作业</a>
<%--					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
<%--					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>--%>
<%--					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
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
					<td><input type='text' name='mtno' class='easyui-textbox' size='16' data-options='required:true'/></td>
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

<%--养护单号弹窗--%>
	<div id='ezuiCVDataDialog'  style="width:900px;height:480px;padding:10px 20px"   >
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiCVToolbar' class='datagrid-toolbar'   style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>养护单号</th><td><input type='text' id='mtno' class='easyui-textbox' size='16' data-options=''/></td>
								<th>养护状态</th><td><input type='text' id='mtstatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getMtStatus"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
								<td>
									<a onclick='ezuiCVDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectConservation();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiCVToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
					<div id='ezuiCVDialogBtn'> </div>
				</div>
				<table id='ezuiCVDataDialogId' ></table>
			</div>
		</div>
	</div>
	<div id='ezuiCVDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiCVDataDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--养护作业点击弹窗--%>
	<div id='ezuiConservationDialog' style='padding: 10px;'>
		<form id='ezuiConservationForm' method='post'>
			<table>

				<tr>
					<th>待养护件数</th><td><input type='text' id='mtqtyExpected' name="mtqtyExpected" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

				</tr>
				<tr>
					<th>养护件数</th><td><input type='text' id='mtqtyExpectedC' name="mtqtyExpectedC" class='easyui-textbox' size='20' data-options="required:true"/></td>

				</tr>
				<tr>
					<th>检查结论</th>
					<td><input type='text' id='checkFlag' name="checkFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getQualifiedOrFailedCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
				</tr>
				<tr>
					<th>养护结论</th>
					<td><input type='text' id='conclusion' name="conclusion" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getQualifiedOrFailedCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td><input type='text' id='remark' name="remark" class='easyui-textbox' size='20' data-options=""/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiConservationDialogBtn'>
		<a onclick='commitConservation();' class='easyui-linkbutton' href='javascript:void(0);'>养护确认</a>
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
	<c:import url='/WEB-INF/jsp/docMtDetails/skuDialog.jsp'/>
</body>
</html>
