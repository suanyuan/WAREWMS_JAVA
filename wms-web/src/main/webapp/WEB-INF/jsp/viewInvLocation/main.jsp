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
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;

var ezuiCustDataDialog;
var ezuiCustDataDialogId;

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/viewInvLocationController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '库存余量_按商品/库位',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns :true,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'customerid',
		columns : [[
            {field: 'lotatt14',		title: '入库单号',	width: 91 },
            {field: 'customerid',		title: '货主',	width: 71 },
			{field: 'lotatt03',		title: '入库日期',	width: 71 },
			{field: 'lotatt12',		title: '产品代码',	width: 71 },
			{field: 'lotatt12',		title: '产品名称',	width: 71 },
			{field: 'lotatt06',		title: '注册证号/备案凭证号',	width: 71 },
			{field: 'sku',		title: '规格型号',	width: 71 },
			{field: 'lotatt04',		title: '生产批号',	width: 71 },
			{field: 'lotatt05',		title: '序列号',	width: 71 },
			{field: 'lotatt07',		title: '灭菌批号',	width: 71 },
			{field: 'lotatt01',		title: '生产日期',	width: 71 },
			{field: 'lotatt02',		title: '有效期/失效期',	width: 71 },
			{field: 'lotatt08',		title: '供应商',	width: 71 },
			{field: 'lotatt08',		title: '库存数量',	width: 71 },
			{field: 'lotatt08',		title: '单位',	width: 71 },
			{field: 'lotatt11',		title: '存储条件',	width: 71 },
			{field: 'enterpriseName',		title: '生产厂家',	width: 71 },

			{field: 'lotatt10',		title: '质量状态',	width: 71 },
			{field: 'lotatt10',		title: '库位',	width: 71 },
            {field: 'name',		title: '产品线',	width: 71 },
            {field: 'lotatt10',		title: '备注',	width: 71 },
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
// 			ajaxBtn($('#menuId').val(), '<c:url value="/viewInvLocationController.do?getBtn"/>', ezuiMenu);
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
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
});

var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
            customerid : row.customerid,
            lotatt14 : row.lotatt14,
            lotatt03 : row.lotatt03,
            lotatt01 : row.lotatt01,
            lotatt02 : row.lotatt02,
            lotatt04 : row.lotatt04,
            lotatt05 : row.lotatt05,
            lotatt10 : row.lotatt10,
            lotatt07 : row.lotatt07,
            lotatt11 : row.lotatt11,
            lotatt08 : row.lotatt08,
            lotatt12 : row.lotatt12,
            lotatt06 : row.lotatt06,
            sku : row.sku,
            qtyallocated : row.qtyallocated,
            qtyholded : row.qtyholded,
            fmuomName : row.fmuomName,
            iPa : row.iPa,
            iMv : row.iMv,
            oMv : row.oMv,
            iRp : row.iRp,
            oRp : row.oRp,
            totalcubic : row.totalcubic,
            totalgrossweight : row.totalgrossweight,
			name : row.name,
            enterpriseName : row.enterpriseName,
			warehouseid : row.warehouseid
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

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
		param.put("skudescrc", $('#skudescrc').val());

		//--导出Excel
		var formId = ajaxDownloadFile(sy.bp()+"/viewInvLocationController.do?exportViewInvLocationDataToExcel", param);
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

var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		fmcustomerid : $('#fmcustomerid').val(),
		fmlocation : $('#fmlocation').val(),
		fmsku : $('#fmsku').val(),
		skudescrc : $('#skudescrc').val(),
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
							<th>商品编码</th><td><input type='text' id='fmsku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>库位</th><td><input type='text' id='fmlocation' class='easyui-textbox' size='16' data-options=''/></td>
							<th>商品名称</th><td><input type='text' id='skudescrc' class='easyui-textbox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
<%-- 					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a> --%>
<%-- 					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a> --%>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查看</a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='viewInvLocationId' name='viewInvLocationId'/>
			<table>
				<tr>
					<th>货主</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>入库单号</th>
					<td><input type='text' name='lotatt14' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>入库日期</th>
					<td><input type='text' name='lotatt03' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>产品代码</th>
					<td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>产品名称</th>
					<td><input type='text' name='lotatt12' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>注册证号/备案凭证号</th>
					<td><input type='text' name='lotatt06' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>规格</th>
					<td><input type='text' name='qtyallocated' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>冻结数量</th>
					<td><input type='text' name='qtyholded' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>单位</th>
					<td><input type='text' name='fmuomName' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待上架数量</th>
					<td><input type='text' name='iPa' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待移入数量</th>
					<td><input type='text' name='iMv' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待移出数量</th>
					<td><input type='text' name='oMv' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>补货待上架</th>
					<td><input type='text' name='iRp' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>补货待下架</th>
					<td><input type='text' name='oRp' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>体积</th>
					<td><input type='text' name='totalcubic' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>毛重</th>
					<td><input type='text' name='totalgrossweight' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>仓库编码</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
<%-- 		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
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
