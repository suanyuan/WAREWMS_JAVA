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
		url : '<c:url value="/couRequestDetailsController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '待输入标题',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : true,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'id',
		columns : [[
			{field: 'cycleCountno',		title: '待输入栏位0',	width: 50 },
			{field: 'cycleCountlineno',		title: '待输入栏位1',	width: 50 },
			{field: 'customerid',		title: '待输入栏位2',	width: 50 },
			{field: 'sku',		title: '待输入栏位3',	width: 50 },
			{field: 'locationid',		title: '待输入栏位4',	width: 50 },
			{field: 'qtyInv',		title: '待输入栏位5',	width: 50 },
			{field: 'qtyAct',		title: '待输入栏位6',	width: 50 },
			{field: 'lotatt04',		title: '待输入栏位7',	width: 50 },
			{field: 'lotatt05',		title: '待输入栏位8',	width: 50 },
			{field: 'addtime',		title: '待输入栏位9',	width: 50 },
			{field: 'addwho',		title: '待输入栏位10',	width: 50 },
			{field: 'edittime',		title: '待输入栏位11',	width: 50 },
			{field: 'editwho',		title: '待输入栏位12',	width: 50 },
			{field: 'userdefined1',		title: '待输入栏位13',	width: 50 },
			{field: 'userdefined2',		title: '待输入栏位14',	width: 50 },
			{field: 'userdefined3',		title: '待输入栏位15',	width: 50 }
		]],
		onDblClickCell: function(index,field,value){
			edit();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
			event.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			ezuiMenu.menu('show', {
				left : event.pageX,
				top : event.pageY
			});
		},onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/couRequestDetailsController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
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
var add = function(){
	processType = 'add';
	$('#couRequestDetailsId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			cycleCountno : row.cycleCountno,
			cycleCountlineno : row.cycleCountlineno,
			customerid : row.customerid,
			sku : row.sku,
			locationid : row.locationid,
			qtyInv : row.qtyInv,
			qtyAct : row.qtyAct,
			lotatt04 : row.lotatt04,
			lotatt05 : row.lotatt05,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			userdefined1 : row.userdefined1,
			userdefined2 : row.userdefined2,
			userdefined3 : row.userdefined3
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'couRequestDetailsController.do?delete',
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
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/couRequestDetailsController.do?edit"/>';
	}else{
		url = '<c:url value="/couRequestDetailsController.do?add"/>';
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
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		cycleCountno : $('#cycleCountno').val(),
		cycleCountlineno : $('#cycleCountlineno').val(),
		customerid : $('#customerid').val(),
		sku : $('#sku').val(),
		locationid : $('#locationid').val(),
		qtyInv : $('#qtyInv').val(),
		qtyAct : $('#qtyAct').val(),
		lotatt04 : $('#lotatt04').val(),
		lotatt05 : $('#lotatt05').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		edittime : $('#edittime').val(),
		editwho : $('#editwho').val(),
		userdefined1 : $('#userdefined1').val(),
		userdefined2 : $('#userdefined2').val(),
		userdefined3 : $('#userdefined3').val()
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
							<th>待输入名称0：</th><td><input type='text' id='cycleCountno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称1：</th><td><input type='text' id='cycleCountlineno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称2：</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称3：</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称4：</th><td><input type='text' id='locationid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称5：</th><td><input type='text' id='qtyInv' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称6：</th><td><input type='text' id='qtyAct' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称7：</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称8：</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称9：</th><td><input type='text' id='addtime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称10：</th><td><input type='text' id='addwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称11：</th><td><input type='text' id='edittime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称12：</th><td><input type='text' id='editwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称13：</th><td><input type='text' id='userdefined1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称14：</th><td><input type='text' id='userdefined2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称15：</th><td><input type='text' id='userdefined3' class='easyui-textbox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='couRequestDetailsId' name='couRequestDetailsId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='cycleCountno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='cycleCountlineno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='locationid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='qtyInv' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='qtyAct' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='lotatt04' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='lotatt05' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='userdefined1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='userdefined2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='userdefined3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
