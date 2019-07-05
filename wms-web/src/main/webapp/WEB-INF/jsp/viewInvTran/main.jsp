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
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/viewInvTranController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '库存事务',
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
		queryParams:{
			transactiontime : $('#transactiontime').datetimebox('getValue'),
			addtime : $('#addtime').datetimebox('getValue'),
		},
		columns : [[
			{field: 'transactionid',		title: '事务编号',	width: 81 },
			{field: 'transactiontypeName',		title: '事务类型',	width: 64 },
			{field: 'docno',		title: '单据编号',	width: 94 },
			{field: 'doclineno',		title: '单据行号',	width: 44 },
			{field: 'doctypeName',		title: '单据类型',	width: 94 },
			{field: 'statusName',		title: '事务状态',	width: 74 },
			{field: 'transactiontime',		title: '事务时间',	width: 108 },
			{field: 'fmcustomerid',		title: 'FM客户编码',	width: 74 },
			{field: 'fmsku',		title: 'FM商品编码',	width: 104 },
			{field: 'fmqtyEach',		title: 'FM单位数量',	width: 74 },
			{field: 'fmqty',		title: 'FM库存数量',	width: 74 },
			{field: 'fmuomName',		title: 'FM单位',	width: 74 },
			{field: 'fmlocation',		title: 'FM库位',	width: 94 },
			{field: 'fmlotnum',		title: 'FM批号',	width: 94 },
			{field: 'fmlotatt01',		title: 'FM生产日期',	width: 108 },
			{field: 'fmlotatt02',		title: 'FM失效日期',	width: 108 },
			{field: 'fmlotatt03',		title: 'FM入库日期',	width: 108 },
			{field: 'fmlotatt04',		title: 'FM库存状态',	width: 74 },
			{field: 'fmlotatt05',		title: 'FM批次属性5',	width: 74 },
			{field: 'fmlotatt06',		title: 'FM批次属性6',	width: 74 },
			{field: 'fmpackid',		title: 'FM箱号',	width: 74 },
			{field: 'tocustomerid',		title: 'TO客户编码',	width: 74 },
			{field: 'tosku',		title: 'TO商品编码',	width: 104 },
			{field: 'toqtyEach',		title: 'TO单位数量',	width: 74 },
			{field: 'toqty',		title: 'TO库存数量',	width: 74 },
			{field: 'touomName',		title: 'TO单位',	width: 74 },
			{field: 'totalcubic',		title: 'TO体积',	width: 74 },
			{field: 'totalgrossweight',		title: 'TO重量',	width: 74 },
			{field: 'tolocation',		title: 'TO库位',	width: 94 },
			{field: 'tolotnum',		title: 'TO批号',	width: 94 },
			{field: 'tolotatt01',		title: 'TO生产日期',	width: 108 },
			{field: 'tolotatt02',		title: 'TO失效日期',	width: 108 },
			{field: 'tolotatt03',		title: 'TO入库日期',	width: 108 },
			{field: 'tolotatt04',		title: 'TO库存状态',	width: 74 },
			{field: 'tolotatt05',		title: 'TO批次属性5',	width: 74 },
			{field: 'tolotatt06',		title: 'TO批次属性6',	width: 74 },
			{field: 'topackid', 	title: 'TO箱号',	width: 74 },
			{field: 'reasoncode',		title: '原因代码',	width: 44 },
			{field: 'reason',		title: '原因',	width: 54 },
			{field: 'addtime',		title: '系统时间',	width: 108 },
			{field: 'editwho',		title: '系统人员',	width: 74 },
			{field: 'operator',		title: '操作人员',	width: 74 },
			{field: 'transactiontype',		title: '事务类型C',	width: 1 },
			{field: 'doctype',		title: '单据类型C',	width: 1 },
			{field: 'status',		title: '单据状态C',	width: 1 }
// 			{field: 'userdefine1',		title: '自定义1',	width: 1 },
// 			{field: 'userdefine2',		title: '自定义2',	width: 1 },
// 			{field: 'userdefine3',		title: '自定义3',	width: 1 },
// 			{field: 'fmid',		title: 'FM跟踪号',	width: 1 },
// 			{field: 'toid',		title: 'TO跟踪号',	width: 1 }
		]],
		onDblClickCell: function(index,field,value){
			edit();
		},
		onSelect: function(rowIndex, rowData) {
			if (rowIndex - 1){
				if (rowData.transactiontype == 'IN' && rowData.status == '99') {
					$('#ezuiBtn_cancelReceive').linkbutton('enable');
					$('#ezuiBtn_cancelShipment').linkbutton('disable');
				}else if(rowData.transactiontype == 'SO' && rowData.status == '99'){
					$('#ezuiBtn_cancelReceive').linkbutton('disable');
					$('#ezuiBtn_cancelShipment').linkbutton('enable');
				}else{
					$('#ezuiBtn_cancelReceive').linkbutton('disable');
					$('#ezuiBtn_cancelShipment').linkbutton('disable');
				};
			};
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
// 			ajaxBtn($('#menuId').val(), '<c:url value="/viewInvTranController.do?getBtn"/>', ezuiMenu);
// 			$(this).datagrid('unselectAll');
// 		}
	});
	
	$('#transactiontime').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });

	$('#addtime').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
});

/* 查询条件清空按钮 */
var ezuiToolbarClear = function(){
	$("#transactiontype").combobox('clear');//
	$("#doctype").combobox('clear');
	$("#status").combobox('clear');
	$("#transactiontime").datetimebox('clear');/*$("#transactiontime").datetimebox({
		value:'1900-01-01 00:00'
	});*/
	$("#addtime").datetimebox('clear');/*$("#addtime").datetimebox({
		value:ordertimeDateTo(new Date())
	});*/
	$("#docno").textbox('clear');
	$("#operator").textbox('clear');
	$("#editwho").textbox('clear');
	$("#fmcustomerid").textbox('clear');
	$("#fmlocation").textbox('clear');
	$("#fmsku").textbox('clear');
	$("#tocustomerid").textbox('clear');
	$("#tolocation").textbox('clear');
	$("#tosku").textbox('clear');
};

/* 获取起始日期 */
var ordertimeDate = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 00:00';
};
/* 获取结束日期 */
var ordertimeDateTo = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 23:59';
};

var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			addtime : row.addtime,
			doclineno : row.doclineno,
			docno : row.docno,
			doctype : row.doctype,
			doctypeName : row.doctypeName,
			editwho : row.editwho,
			fmcustomerid : row.fmcustomerid,
			fmid : row.fmid,
			fmlocation : row.fmlocation,
			fmlotatt01 : row.fmlotatt01,
			fmlotatt02 : row.fmlotatt02,
			fmlotatt03 : row.fmlotatt03,
			fmlotatt04 : row.fmlotatt04,
			fmlotatt05 : row.fmlotatt05,
			fmlotatt06 : row.fmlotatt06,
			fmlotnum : row.fmlotnum,
			fmpackid : row.fmpackid,
			fmqty : row.fmqty,
			fmqtyEach : row.fmqtyEach,
			fmsku : row.fmsku,
			fmuom : row.fmuom,
			fmuomName : row.fmuomName,
			operator : row.operator,
			reason : row.reason,
			reasoncode : row.reasoncode,
			status : row.status,
			statusName : row.statusName,
			tocustomerid : row.tocustomerid,
			toid : row.toid,
			tolocation : row.tolocation,
			tolotatt01 : row.tolotatt01,
			tolotatt02 : row.tolotatt02,
			tolotatt03 : row.tolotatt03,
			tolotatt04 : row.tolotatt04,
			tolotatt05 : row.tolotatt05,
			tolotatt06 : row.tolotatt06,
			tolotnum : row.tolotnum,
			topackid : row.topackid,
			toqty : row.toqty,
			toqtyEach : row.toqtyEach,
			tosku : row.tosku,
			totalcubic : row.totalcubic,
			totalgrossweight : row.totalgrossweight,
			totalnetweight : row.totalnetweight,
			totalprice : row.totalprice,
			touom : row.touom,
			touomName : row.touomName,
			transactionid : row.transactionid,
			transactiontime : row.transactiontime,
			transactiontype : row.transactiontype,
			transactiontypeName : row.transactiontypeName,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 取消收货 */
var cancelReceive = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.ajax({
			url : 'viewInvTranController.do?cancelReceive',
			data : {transactionid : row.transactionid},
			type : 'POST',
			dataType : 'JSON',
			success : function(result){
				var msg = '';
				try {
					msg = result.msg;
				} catch (e) {
					msg = '取消收货异常';
				} finally {
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					ezuiDatagrid.datagrid('reload');
				}
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 取消发货 */
var cancelShipment = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.ajax({
			url : 'viewInvTranController.do?cancelShipment',
			data : {transactionid : row.transactionid},
			type : 'POST',
			dataType : 'JSON',
			success : function(result){
				var msg = '';
				try {
					msg = result.msg;
				} catch (e) {
					msg = '取消发货异常';
				} finally {
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					ezuiDatagrid.datagrid('reload');
				}
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
	if(navigator.cookieEnabled){
		$('#ezuiBtn_export').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		
		param.put("transactiontype", $('#transactiontype').combobox('getValue'));
		param.put("doctype", $('#doctype').combobox('getValue'));
		param.put("status", $('#status').combobox('getValue'));
		param.put("transactiontime", $('#transactiontime').datetimebox('getValue'));
		param.put("addtime", $('#addtime').datetimebox('getValue'));
		param.put("docno", $('#docno').val());
		param.put("operator", $('#operator').val());
		param.put("editwho", $('#editwho').val());
		param.put("fmcustomerid", $('#fmcustomerid').val());
		param.put("fmlocation", $('#fmlocation').val());
		param.put("fmsku", $('#fmsku').val());
		param.put("tocustomerid", $('#tocustomerid').val());
		param.put("tolocation", $('#tolocation').val());
		param.put("tosku", $('#tosku').val());

		//--导出Excel
		var formId = ajaxDownloadFile(sy.bp()+"/viewInvTranController.do?exportViewInvTranDataToExcel", param);
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
		transactiontype : $('#transactiontype').combobox('getValue'),
		doctype : $('#doctype').combobox('getValue'),
		status : $('#status').combobox('getValue'),
		transactiontime : $('#transactiontime').datetimebox('getValue'),
		addtime : $('#addtime').datetimebox('getValue'),//transactiontimeTo
		docno : $('#docno').val(),
		operator : $('#operator').val(),
		editwho : $('#editwho').val(),
		fmcustomerid : $('#fmcustomerid').val(),
		fmlocation : $('#fmlocation').val(),
		fmsku : $('#fmsku').val(),
		tocustomerid : $('#tocustomerid').val(),
		tolocation : $('#tolocation').val(),
		tosku : $('#tosku').val(),
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
							<th>事务类型</th><td><input type='text' id='transactiontype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/viewInvTranController.do?getTransactionTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
							<th>事务状态</th><td><input type='text' id='status' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/viewInvTranController.do?getStatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
							<th>系统人员</th><td><input type='text' id='editwho' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>单证类型</th><td><input type='text' id='doctype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/viewInvTranController.do?getDocTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
							<th>单证号</th><td><input type='text' id='docno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>操作人员</th><td><input type='text' id='operator' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>事务日期</th><td><input type='text' id='transactiontime' class='easyui-datetimebox' size='16' data-options="editable:false,
																																			required:true,
																																			showSeconds:false,
																																			value:ordertimeDate(new Date())"/></td>
							<th>至</th><td><input type='text' id='addtime' class='easyui-datetimebox' size='16' data-options="editable:false,
																																required:true,
																																showSeconds:false,
																																value:ordertimeDateTo(new Date())"/></td>
						</tr>
						<tr>
							<th>FM客户编码</th><td><input type='text' id='fmcustomerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>FM产品编码</th><td><input type='text' id='fmsku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>FM库位</th><td><input type='text' id='fmlocation' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>	
							<th>TO客户编码</th><td><input type='text' id='tocustomerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>TO产品编码</th><td><input type='text' id='tosku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>TO库位</th><td><input type='text' id='tolocation' class='easyui-textbox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查看明细</a>
					<a onclick='cancelReceive();' id='ezuiBtn_cancelReceive' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>取消收货</a>
					<a onclick='cancelShipment();' id='ezuiBtn_cancelShipment' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>取消发货</a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='viewInvTranId' name='viewInvTranId'/>
			<table>
				<tr>
					<th>事务编号</th>
					<td><input type='text' name='transactionid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>事务类型</th>
					<td><input type='text' name='transactiontypeName' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>单据类型</th>
					<td><input type='text' name='doctypeName' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>事务状态</th>
					<td><input type='text' name='statusName' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>单据号</th>
					<td><input type='text' name='docno' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>单据行号</th>
					<td><input type='text' name='doclineno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>系统时间</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>事务时间</th>
					<td><input type='text' name='transactiontime' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>系统人员</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>操作人员</th>
					<td><input type='text' name='operator' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>原因</th>
					<td><input type='text' name='reason' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>原因代码</th>
					<td><input type='text' name='reasoncode' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr><th></th></tr>
				<tr>
					<th>FM客户编码</th>
					<td><input type='text' name='fmcustomerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM商品编码</th>
					<td><input type='text' name='fmsku' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM库位</th>
					<td><input type='text' name='fmlocation' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>	
					<th>FM库存数量</th>
					<td><input type='text' name='fmqty' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM单位数量</th>
					<td><input type='text' name='fmqtyEach' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM单位</th>
					<td><input type='text' name='fmuom' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>	
					<th>FM生产日期</th>
					<td><input type='text' name='fmlotatt01' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM失效日期</th>
					<td><input type='text' name='fmlotatt02' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM入库日期</th>
					<td><input type='text' name='fmlotatt03' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>	
					<th>FM库存状态</th>
					<td><input type='text' name='fmlotatt04' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM批属5</th>
					<td><input type='text' name='fmlotatt05' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>FM批属6</th>
					<td><input type='text' name='fmlotatt06' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr><th></th></tr>
				<tr>
					<th>TO客户编码</th>
					<td><input type='text' name='tocustomerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO商品编码</th>
					<td><input type='text' name='tosku' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO库位</th>
					<td><input type='text' name='tolocation' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>	
					<th>TO库存数量</th>
					<td><input type='text' name='toqty' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO单位数量</th>
					<td><input type='text' name='toqtyEach' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO单位</th>
					<td><input type='text' name='touom' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>	
					<th>TO体积</th>
					<td><input type='text' name='totalcubic' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO重量</th>
					<td><input type='text' name='totalgrossweight' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>TO生产日期</th>
					<td><input type='text' name='tolotatt01' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO失效日期</th>
					<td><input type='text' name='tolotatt02' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO入库日期</th>
					<td><input type='text' name='tolotatt03' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>	
					<th>TO库存状态</th>
					<td><input type='text' name='tolotatt04' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO批属5</th>
					<td><input type='text' name='tolotatt05' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>TO批属6</th>
					<td><input type='text' name='tolotatt06' class='easyui-textbox' size='16' data-options='required:true'/></td>
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
</body>
</html>
