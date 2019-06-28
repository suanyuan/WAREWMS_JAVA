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
		url : '<c:url value="/gspProductRegisterController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
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
			{field: 'productRegisterId',		title: '主键',	width: 28 ,hidden:true},
			{field: 'productRegisterNo',		title: '注册证编号',	width: 28 },
            {field: 'classifyId',		title: '管理分类',	width: 28 },
            {field: 'classifyCatalog',		title: '分类目录',	width: 28 }
			{field: 'productName',		title: '产品名称',	width: 28 },
			{field: 'productRegisterName',		title: '注册人名称',	width: 28 },
			{field: 'productRegisterAddress',		title: '注册人住所',	width: 28 },
			{field: 'productProductionAddress',		title: '生产地址',	width: 28 },
			{field: 'approveDate',		title: '批准日期',	width: 28 },
			{field: 'productRegisterExpiryDate',		title: '有效期至',	width: 28 },
			{field: 'productRegisterVersion',		title: '注册证版本',	width: 28 },
			{field: 'version',		title: '待输入栏位17',	width: 28 },
			{field: 'checkerId',		title: '待输入栏位18',	width: 28 },
			{field: 'checkDate',		title: '待输入栏位19',	width: 28 },
			{field: 'createId',		title: '待输入栏位20',	width: 28 },
			{field: 'createDate',		title: '待输入栏位21',	width: 28 },
			{field: 'editId',		title: '待输入栏位22',	width: 28 },
			{field: 'editDate',		title: '待输入栏位23',	width: 28 },
			{field: 'isUse',		title: '待输入栏位24',	width: 28 }

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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspProductRegisterController.do?getBtn"/>', ezuiMenu);
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
	$('#gspProductRegisterId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			productRegisterId : row.productRegisterId,
			productRegisterNo : row.productRegisterNo,
			productName : row.productName,
			productRegisterName : row.productRegisterName,
			productRegisterAddress : row.productRegisterAddress,
			productProductionAddress : row.productProductionAddress,
			agentName : row.agentName,
			applyScope : row.applyScope,
			mainPart : row.mainPart,
			expectUse : row.expectUse,
			storageConditions : row.storageConditions,
			productExpiryDate : row.productExpiryDate,
			otherContent : row.otherContent,
			remark : row.remark,
			approveDate : row.approveDate,
			productRegisterExpiryDate : row.productRegisterExpiryDate,
			productRegisterVersion : row.productRegisterVersion,
			version : row.version,
			checkerId : row.checkerId,
			checkDate : row.checkDate,
			createId : row.createId,
			createDate : row.createDate,
			editId : row.editId,
			editDate : row.editDate,
			isUse : row.isUse,
			attachmentUrl : row.attachmentUrl,
			classifyId : row.classifyId,
			classifyCatalog : row.classifyCatalog
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
					url : 'gspProductRegisterController.do?delete',
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
		url = '<c:url value="/gspProductRegisterController.do?edit"/>';
	}else{
		url = '<c:url value="/gspProductRegisterController.do?add"/>';
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
		productRegisterId : $('#productRegisterId').val(),
		productRegisterNo : $('#productRegisterNo').val(),
		productName : $('#productName').val(),
		productRegisterName : $('#productRegisterName').val(),
		productRegisterAddress : $('#productRegisterAddress').val(),
		productProductionAddress : $('#productProductionAddress').val(),
		agentName : $('#agentName').val(),
		applyScope : $('#applyScope').val(),
		mainPart : $('#mainPart').val(),
		expectUse : $('#expectUse').val(),
		storageConditions : $('#storageConditions').val(),
		productExpiryDate : $('#productExpiryDate').val(),
		otherContent : $('#otherContent').val(),
		remark : $('#remark').val(),
		approveDate : $('#approveDate').val(),
		productRegisterExpiryDate : $('#productRegisterExpiryDate').val(),
		productRegisterVersion : $('#productRegisterVersion').val(),
		version : $('#version').val(),
		checkerId : $('#checkerId').val(),
		checkDate : $('#checkDate').val(),
		createId : $('#createId').val(),
		createDate : $('#createDate').val(),
		editId : $('#editId').val(),
		editDate : $('#editDate').val(),
		isUse : $('#isUse').val(),
		attachmentUrl : $('#attachmentUrl').val(),
		classifyId : $('#classifyId').val(),
		classifyCatalog : $('#classifyCatalog').val()
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
							<th>管理分类：</th><td><input type='text' id='classifyId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>分类目录：</th><td><input type='text' id='classifyCatalog' class='easyui-textbox' size='16' data-options=''/></td>
							<th>注册证编号：</th><td><input type='text' id='productRegisterNo' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品名称：</th><td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>版本：</th><td><input type='text' id='productRegisterVersion' class='easyui-textbox' size='16' data-options=''/></td>
							<th>创建时间起始：</th><td><input type='text' id='createDateBegin' class='easyui-textbox' size='16' data-options=''/></td>
							<th>创建时间结束：</th><td><input type='text' id='createDateEnd' class='easyui-textbox' size='16' data-options=''/></td>
							<th>是否有效：</th><td><input type='text' id='isUse' class='easyui-textbox' size='16' data-options=''/></td>
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
			<input type='hidden' id='gspProductRegisterId' name='gspProductRegisterId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='productRegisterId' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='productRegisterNo' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='productName' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='productRegisterName' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='productRegisterAddress' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='productProductionAddress' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='agentName' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='applyScope' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='mainPart' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='expectUse' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='storageConditions' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='productExpiryDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='otherContent' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='remark' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='approveDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='productRegisterExpiryDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入16</th>
					<td><input type='text' name='productRegisterVersion' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入17</th>
					<td><input type='text' name='version' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入18</th>
					<td><input type='text' name='checkerId' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入19</th>
					<td><input type='text' name='checkDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入20</th>
					<td><input type='text' name='createId' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入21</th>
					<td><input type='text' name='createDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入22</th>
					<td><input type='text' name='editId' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入23</th>
					<td><input type='text' name='editDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入24</th>
					<td><input type='text' name='isUse' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入25</th>
					<td><input type='text' name='attachmentUrl' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入26</th>
					<td><input type='text' name='classifyId' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入27</th>
					<td><input type='text' name='classifyCatalog' class='easyui-textbox' size='16' data-options='required:true'/></td>
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
